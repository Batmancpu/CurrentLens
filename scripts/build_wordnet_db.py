#!/usr/bin/env python3
"""Build an indexed offline SQLite lexicon from Open English WordNet."""
from __future__ import annotations

import argparse
import hashlib
import hmac
import io
import os
import sqlite3
import tempfile
import unicodedata
import zipfile
from pathlib import Path
from typing import Iterable

DATA_FILES = {
    "data.noun": "noun",
    "data.verb": "verb",
    "data.adj": "adjective",
    "data.adv": "adverb",
}
EXCEPTION_FILES = {
    "noun.exc": "noun",
    "verb.exc": "verb",
    "adj.exc": "adjective",
    "adv.exc": "adverb",
}
WORD_PUNCTUATION_TRANSLATION = str.maketrans({
    "\u2018": "'", "\u2019": "'", "\u02bc": "'", "\uff07": "'",
    "\u2010": "-", "\u2011": "-", "\u2012": "-", "\u2013": "-",
    "\u2014": "-", "\u2015": "-", "\u2212": "-", "\ufe63": "-", "\uff0d": "-",
})


def normalize_lemma(value: str) -> str:
    """Match Unicode-normalised publisher selections to canonical offline dictionary keys."""
    translated = value.strip().lower().translate(WORD_PUNCTUATION_TRANSLATION)
    return unicodedata.normalize("NFC", translated)


def parse_data_line(line: str, part_of_speech: str) -> tuple[list[str], str, str] | None:
    stripped = line.strip()
    if not stripped or "|" not in stripped:
        return None
    metadata, gloss = stripped.split("|", 1)
    tokens = metadata.split()
    if len(tokens) < 6:
        return None
    try:
        word_count = int(tokens[3], 16)
    except ValueError:
        return None
    if not 1 <= word_count <= 64:
        return None
    first_lemma = 4
    if len(tokens) < first_lemma + word_count * 2:
        return None
    lemmas = []
    for index in range(word_count):
        lemma = normalize_lemma(tokens[first_lemma + index * 2])
        if lemma and all(char.isalnum() or char in "_'-" for char in lemma):
            lemmas.append(lemma)
    gloss = " ".join(gloss.strip().split())[:1800]
    return (lemmas, part_of_speech, gloss) if lemmas and gloss else None


def iter_text_lines(archive: zipfile.ZipFile, member: str) -> Iterable[str]:
    with archive.open(member, "r") as stream:
        with io.TextIOWrapper(stream, encoding="utf-8", errors="replace", newline=None) as reader:
            yield from reader


def verify_archive_sha256(archive_path: Path, expected_sha256: str) -> str:
    """Verify the exact upstream WordNet archive before parsing or writing output."""
    expected = expected_sha256.strip().lower()
    if len(expected) != 64 or any(char not in "0123456789abcdef" for char in expected):
        raise ValueError("Expected WordNet SHA-256 must contain exactly 64 hexadecimal characters")

    digest = hashlib.sha256()
    with archive_path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    actual = digest.hexdigest()
    if not hmac.compare_digest(actual, expected):
        raise ValueError(
            f"WordNet archive SHA-256 mismatch: expected {expected}, received {actual}"
        )
    return actual


def build_database(
    archive_path: Path,
    output_path: Path,
    expected_sha256: str,
    min_unique_words: int = 100_000,
    min_archive_bytes: int = 5_000_000,
) -> int:
    if not archive_path.is_file() or archive_path.stat().st_size < min_archive_bytes:
        raise ValueError("The Open English WordNet archive is missing or unexpectedly small")
    verify_archive_sha256(archive_path, expected_sha256)
    output_path.parent.mkdir(parents=True, exist_ok=True)
    fd, temporary_name = tempfile.mkstemp(prefix="wordnet-", suffix=".sqlite", dir=output_path.parent)
    os.close(fd)
    temporary_path = Path(temporary_name)
    try:
        with zipfile.ZipFile(archive_path, "r") as archive:
            by_basename = {}
            for name in archive.namelist():
                base = Path(name).name
                if base in DATA_FILES or base in EXCEPTION_FILES:
                    by_basename.setdefault(base, name)
            missing = set(DATA_FILES) - by_basename.keys()
            if missing:
                raise ValueError("WNDB archive is missing: " + ", ".join(sorted(missing)))
            database = sqlite3.connect(temporary_path)
            try:
                database.execute("PRAGMA page_size=4096")
                database.execute("PRAGMA journal_mode=OFF")
                database.execute("PRAGMA synchronous=OFF")
                database.execute("PRAGMA temp_store=MEMORY")
                database.execute("""CREATE TABLE entries (
                    word TEXT NOT NULL, pos TEXT NOT NULL, definition TEXT NOT NULL,
                    synonyms TEXT NOT NULL, UNIQUE(word, pos, definition)
                )""")
                database.execute("""CREATE TABLE exceptions (
                    inflected TEXT NOT NULL, pos TEXT NOT NULL, base_forms TEXT NOT NULL,
                    PRIMARY KEY(inflected, pos)
                )""")
                database.execute("""CREATE TABLE metadata (
                    key TEXT PRIMARY KEY, value TEXT NOT NULL
                )""")
                metadata = [
                    ("dataset", "Open English WordNet 2025+"),
                    ("dataset_license", "CC BY 4.0"),
                    ("underlying_database", "Princeton WordNet 3.1"),
                    ("attribution", "Open English WordNet team; Princeton WordNet 3.1"),
                ]
                database.executemany("INSERT INTO metadata VALUES (?, ?)", metadata)

                rows = []
                for filename, pos in DATA_FILES.items():
                    for line in iter_text_lines(archive, by_basename[filename]):
                        parsed = parse_data_line(line, pos)
                        if parsed is None:
                            continue
                        lemmas, _, definition = parsed
                        for lemma in lemmas:
                            synonyms = ", ".join(
                                item.replace("_", " ") for item in lemmas if item != lemma
                            )[:500]
                            rows.append((lemma, pos, definition, synonyms))
                        if len(rows) >= 5000:
                            database.executemany(
                                "INSERT OR IGNORE INTO entries VALUES (?, ?, ?, ?)", rows
                            )
                            rows.clear()
                if rows:
                    database.executemany("INSERT OR IGNORE INTO entries VALUES (?, ?, ?, ?)", rows)

                for filename, pos in EXCEPTION_FILES.items():
                    member = by_basename.get(filename)
                    if member is None:
                        continue
                    exceptions = []
                    for line in iter_text_lines(archive, member):
                        tokens = [normalize_lemma(token) for token in line.strip().split()]
                        valid = all(
                            token and all(char.isalnum() or char in "_'-" for char in token)
                            for token in tokens
                        )
                        if 2 <= len(tokens) <= 16 and valid:
                            exceptions.append((tokens[0], pos, " ".join(tokens[1:])))
                    database.executemany(
                        "INSERT OR REPLACE INTO exceptions VALUES (?, ?, ?)", exceptions
                    )

                unique_words = database.execute("SELECT COUNT(DISTINCT word) FROM entries").fetchone()[0]
                sense_count = database.execute("SELECT COUNT(*) FROM entries").fetchone()[0]
                if unique_words < min_unique_words or sense_count < min_unique_words:
                    raise ValueError(
                        f"Dictionary is unexpectedly small: {unique_words} words, {sense_count} senses"
                    )
                database.execute("PRAGMA user_version=2027")
                database.commit()
                database.execute("VACUUM")
            finally:
                database.close()
        os.replace(temporary_path, output_path)
        return unique_words
    except Exception:
        temporary_path.unlink(missing_ok=True)
        raise


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("archive", type=Path)
    parser.add_argument("output", type=Path)
    parser.add_argument("--expected-sha256", required=True)
    parser.add_argument("--min-unique-words", type=int, default=100_000)
    args = parser.parse_args()
    count = build_database(
        args.archive,
        args.output,
        expected_sha256=args.expected_sha256,
        min_unique_words=args.min_unique_words,
    )
    print(
        f"Built offline WordNet dictionary: {count:,} unique words -> "
        f"{args.output} ({args.output.stat().st_size:,} bytes)"
    )


if __name__ == "__main__":
    main()
