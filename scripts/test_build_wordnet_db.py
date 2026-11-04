import sqlite3
import tempfile
import unittest
import zipfile
from pathlib import Path
from build_wordnet_db import build_database, parse_cmudict_line, parse_data_line


class WordNetParserTests(unittest.TestCase):
    def test_parses_lemmas_and_gloss(self):
        line = "00001740 03 n 02 entity 0 being 0 001 @ 00001930 n 0000 | that which is perceived or known\n"
        self.assertEqual(parse_data_line(line, "noun"), (
            ["entity", "being"], "noun", "that which is perceived or known"
        ))

    def test_skips_malformed_and_header_lines(self):
        self.assertIsNone(parse_data_line("  index.vrb 2 0\n", "verb"))
        self.assertIsNone(parse_data_line("broken line | gloss\n", "noun"))

    def test_converts_cmudict_phones_to_stress_marked_us_ipa(self):
        self.assertEqual(
            parse_cmudict_line("BANANA B AH0 N AE1 N AH0"),
            ("banana", "/bəˈnænə/", "B AH0 N AE1 N AH0"),
        )
        self.assertEqual(
            parse_cmudict_line("COMPUTER K AH0 M P Y UW1 T ER0"),
            ("computer", "/kəmˈpjutɚ/", "K AH0 M P Y UW1 T ER0"),
        )
        self.assertEqual(
            parse_cmudict_line("CAT K AE1 T"),
            ("cat", "/ˈkæt/", "K AE1 T"),
        )
        self.assertIsNone(parse_cmudict_line(";;; CMUdict header comment"))
        self.assertIsNone(parse_cmudict_line("UNMAPPED ZZ1"))

    def test_builds_indexed_database_and_morphology_exceptions(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            archive_path = root / "wordnet.zip"
            pronunciation_path = root / "cmudict.dict"
            output_path = root / "assets" / "wordnet.db"
            with zipfile.ZipFile(archive_path, "w") as archive:
                archive.writestr(
                    "dict/data.noun",
                    "00001740 03 n 02 entity 0 being 0 000 | that which is perceived or known\n",
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
                archive.writestr("dict/noun.exc", "children child\n")
                archive.writestr("dict/verb.exc", "ran run\n")
                archive.writestr("dict/adj.exc", "better good\n")
                archive.writestr("dict/adv.exc", "")
            pronunciation_path.write_text(
                ";;; CMUdict fixture\n"
                "CAT K AE1 T\n"
                "CAT(2) K AE0 T\n"
                "BANANA B AH0 N AE1 N AH0\n",
                encoding="ascii",
            )
            self.assertEqual(
                build_database(
                    archive_path,
                    output_path,
                    min_unique_words=4,
                    min_archive_bytes=0,
                    pronunciation_path=pronunciation_path,
                    min_pronunciation_words=2,
                    min_pronunciation_bytes=0,
                ),
                5,
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
                pronunciation = database.execute(
                    "SELECT ipa, arpabet FROM pronunciations WHERE word='cat'"
                ).fetchone()
                self.assertEqual(pronunciation, ("/ˈkæt/", "K AE1 T"))
                pronunciation_count = database.execute(
                    "SELECT COUNT(*) FROM pronunciations"
                ).fetchone()[0]
                self.assertEqual(pronunciation_count, 2)
                version = database.execute("PRAGMA user_version").fetchone()[0]
                self.assertEqual(version, 2026)
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
                self.assertEqual(metadata["pronunciation_dataset"], "CMU Pronouncing Dictionary (CMUdict)")
            finally:
                database.close()


if __name__ == "__main__":
    unittest.main()
