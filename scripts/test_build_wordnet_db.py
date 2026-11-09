import hashlib
import sqlite3
import tempfile
import unittest
import zipfile
from pathlib import Path
from build_wordnet_db import build_database, parse_data_line, verify_archive_sha256


class WordNetParserTests(unittest.TestCase):
    def test_parses_lemmas_and_gloss(self):
        line = "00001740 03 n 02 entity 0 being 0 001 @ 00001930 n 0000 | that which is perceived or known\n"
        self.assertEqual(parse_data_line(line, "noun"), (
            ["entity", "being"], "noun", "that which is perceived or known"
        ))

    def test_normalizes_unicode_lemmas_and_typographic_punctuation(self):
        line = (
            "00001740 03 n 03 cafe\u0301 0 can’t 0 state–of–the–art 0 000 "
            "| words with accents and publisher punctuation\n"
        )
        self.assertEqual(
            parse_data_line(line, "noun"),
            (["café", "can't", "state-of-the-art"], "noun",
             "words with accents and publisher punctuation"),
        )

    def test_skips_malformed_and_header_lines(self):
        self.assertIsNone(parse_data_line("  index.vrb 2 0\n", "verb"))
        self.assertIsNone(parse_data_line("broken line | gloss\n", "noun"))

    def test_accepts_exact_archive_sha256(self):
        with tempfile.TemporaryDirectory() as directory:
            archive_path = Path(directory) / "wordnet.zip"
            archive_path.write_bytes(b"pinned-wordnet-fixture")
            expected = hashlib.sha256(archive_path.read_bytes()).hexdigest().upper()
            self.assertEqual(verify_archive_sha256(archive_path, expected), expected.lower())

    def test_rejects_mismatched_archive_sha256_before_creating_output(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            archive_path = root / "wordnet.zip"
            output_path = root / "assets" / "wordnet.db"
            archive_path.write_bytes(b"untrusted-wordnet-fixture")
            with self.assertRaisesRegex(ValueError, "SHA-256 mismatch"):
                build_database(
                    archive_path,
                    output_path,
                    expected_sha256="0" * 64,
                    min_unique_words=1,
                    min_archive_bytes=0,
                )
            self.assertFalse(output_path.exists())
            self.assertFalse(output_path.parent.exists())

    def test_rejects_malformed_expected_sha256(self):
        with tempfile.TemporaryDirectory() as directory:
            archive_path = Path(directory) / "wordnet.zip"
            archive_path.write_bytes(b"wordnet")
            with self.assertRaisesRegex(ValueError, "64 hexadecimal"):
                verify_archive_sha256(archive_path, "not-a-sha256")

    def test_builds_wordnet_only_database_and_morphology_exceptions(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            archive_path = root / "wordnet.zip"
            output_path = root / "assets" / "wordnet.db"
            with zipfile.ZipFile(archive_path, "w") as archive:
                archive.writestr(
                    "dict/data.noun",
                    "00001740 03 n 02 entity 0 being 0 000 | that which is perceived or known\n"
                    "00001741 03 n 01 cafe\u0301 0 000 | a small coffeehouse\n",
                )
                archive.writestr(
                    "dict/data.verb",
                    "00002000 03 v 01 run 0 000 00 | move fast by using one's feet\n",
                )
                archive.writestr(
                    "dict/data.adj",
                    "00003000 03 a 01 bright 0 000 | emitting or reflecting much light\n",
                )
                archive.writestr(
                    "dict/data.adv",
                    "00004000 03 r 01 quickly 0 000 | with rapid movements\n",
                )
                archive.writestr("dict/noun.exc", "children child\ncafe\u0301s café\n")
                archive.writestr("dict/verb.exc", "ran run\n")
                archive.writestr("dict/adj.exc", "better good\n")
                archive.writestr("dict/adv.exc", "")
            expected_sha256 = hashlib.sha256(archive_path.read_bytes()).hexdigest()
            self.assertEqual(
                build_database(
                    archive_path,
                    output_path,
                    expected_sha256=expected_sha256,
                    min_unique_words=4,
                    min_archive_bytes=0,
                ),
                6,
            )
            database = sqlite3.connect(output_path)
            try:
                row = database.execute(
                    "SELECT definition, synonyms FROM entries WHERE word='entity'"
                ).fetchone()
                self.assertEqual(row, ("that which is perceived or known", "being"))
                exception = database.execute(
                    "SELECT base_forms FROM exceptions WHERE inflected='children'"
                ).fetchone()
                self.assertEqual(exception[0], "child")
                unicode_entry = database.execute(
                    "SELECT definition FROM entries WHERE word='café'"
                ).fetchone()
                self.assertEqual(unicode_entry[0], "a small coffeehouse")
                unicode_exception = database.execute(
                    "SELECT base_forms FROM exceptions WHERE inflected='cafés'"
                ).fetchone()
                self.assertEqual(unicode_exception[0], "café")
                version = database.execute("PRAGMA user_version").fetchone()[0]
                self.assertEqual(version, 2027)
                entries_plan = database.execute(
                    "EXPLAIN QUERY PLAN SELECT definition FROM entries WHERE word = ?",
                    ("entity",),
                ).fetchall()
                exceptions_plan = database.execute(
                    "EXPLAIN QUERY PLAN SELECT base_forms FROM exceptions WHERE inflected = ?",
                    ("children",),
                ).fetchall()
                self.assertTrue(any("sqlite_autoindex_entries_1" in row[-1] for row in entries_plan))
                self.assertTrue(any("sqlite_autoindex_exceptions_1" in row[-1] for row in exceptions_plan))
                metadata = dict(database.execute("SELECT key, value FROM metadata"))
                self.assertEqual(metadata["dataset"], "Open English WordNet 2025+")
                self.assertEqual(
                    set(metadata),
                    {"dataset", "dataset_license", "underlying_database", "attribution"}
                )
                tables = {
                    row[0] for row in database.execute(
                        "SELECT name FROM sqlite_master WHERE type='table'"
                    )
                }
                self.assertEqual(tables, {"entries", "exceptions", "metadata"})
                schema_sql = " ".join(
                    row[0] or "" for row in database.execute(
                        "SELECT sql FROM sqlite_master WHERE type='table'"
                    )
                ).lower()
                self.assertNotIn("ipa", schema_sql)
                self.assertNotIn("arpabet", schema_sql)
                self.assertNotIn("pronunciation", schema_sql)
                self.assertFalse(any("pronunciation" in key.lower() for key in metadata))
                self.assertFalse(any("cmu" in value.lower() for value in metadata.values()))
            finally:
                database.close()


if __name__ == "__main__":
    unittest.main()
