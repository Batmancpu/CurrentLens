#!/usr/bin/env python3
"""Build an indexed offline SQLite lexicon from Open English WordNet and CMUdict."""
from __future__ import annotations

import argparse
import io
import os
import re
import sqlite3
import tempfile
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
CMUDICT_REVISION = "74790861f652b15e4ac49015a90074ad62a27690"

ARPABET_TO_IPA = {
    "AA": "ɑ", "AE": "æ", "AH": "ʌ", "AO": "ɔ", "AW": "aʊ", "AY": "aɪ",
    "B": "b", "CH": "tʃ", "D": "d", "DH": "ð", "EH": "ɛ", "ER": "ɝ",
    "EY": "eɪ", "F": "f", "G": "ɡ", "HH": "h", "IH": "ɪ", "IY": "i",
    "JH": "dʒ", "K": "k", "L": "l", "M": "m", "N": "n", "NG": "ŋ",
    "OW": "oʊ", "OY": "ɔɪ", "P": "p", "R": "ɹ", "S": "s", "SH": "ʃ",
    "T": "t", "TH": "θ", "UH": "ʊ", "UW": "u", "V": "v", "W": "w",
    "Y": "j", "Z": "z", "ZH": "ʒ",
}
VOWEL_PHONES = frozenset({
    "AA", "AE", "AH", "AO", "AW", "AY", "EH", "ER", "EY",
    "IH", "IY", "OW", "OY", "UH", "UW",
})
# Common English onset clusters used only to place syllable stress marks readably.
LEGAL_ONSET_CLUSTERS = {
    ("P", "L"), ("P", "R"), ("B", "L"), ("B", "R"), ("T", "R"), ("D", "R"),
    ("K", "L"), ("K", "R"), ("G", "L"), ("G", "R"), ("F", "L"), ("F", "R"),
    ("TH", "R"), ("SH", "R"), ("S", "P"), ("S", "T"), ("S", "K"), ("S", "M"),
    ("S", "N"), ("S", "L"), ("S", "W"), ("S", "F"), ("K", "W"), ("G", "W"),
    ("P", "Y"), ("B", "Y"), ("T", "Y"), ("D", "Y"), ("K", "Y"), ("G", "Y"),
    ("F", "Y"), ("H", "Y"), ("M", "Y"), ("N", "Y"), ("L", "Y"), ("R", "Y"),
    ("S", "Y"), ("V", "Y"), ("Z", "Y"), ("S", "P", "L"), ("S", "P", "R"),
    ("S", "T", "R"), ("S", "K", "L"), ("S", "K", "R"), ("S", "K", "W"),
}


def arpabet_to_ipa(phones: list[str]) -> str | None:
    """Convert CMUdict phones to a readable US IPA form; stress placement is approximate."""
    parsed: list[tuple[str, int | None]] = []
    for token in phones:
        stress = int(token[-1]) if token and token[-1] in "012" else None
        base = token[:-1] if stress is not None else token
        if base not in ARPABET_TO_IPA:
            return None
        if base in VOWEL_PHONES and stress is None:
            return None
        parsed.append((base, stress))

    nuclei = [index for index, (base, _) in enumerate(parsed) if base in VOWEL_PHONES]
    if not nuclei:
        return None

    syllable_starts = [0]
    for previous, current in zip(nuclei, nuclei[1:]):
        cluster = tuple(base for base, _ in parsed[previous + 1:current])
        onset_length = 0 if not cluster else 1
        for size in (3, 2):
            if len(cluster) >= size and cluster[-size:] in LEGAL_ONSET_CLUSTERS:
                onset_length = size
                break
        syllable_starts.append(current - onset_length)

    syllables = []
    for index, start in enumerate(syllable_starts):
        end = syllable_starts[index + 1] if index + 1 < len(syllable_starts) else len(parsed)
        chunk = parsed[start:end]
        nucleus = next((item for item in chunk if item[0] in VOWEL_PHONES), None)
        if nucleus is None:
            return None
        stress = nucleus[1]
        prefix = "ˈ" if stress == 1 else "ˌ" if stress == 2 else ""
        symbols = []
        for base, phone_stress in chunk:
            symbol = ARPABET_TO_IPA[base]
            if base == "AH" and phone_stress == 0:
                symbol = "ə"
            elif base == "ER" and phone_stress == 0:
                symbol = "ɚ"
            symbols.append(symbol)
        syllables.append(prefix + "".join(symbols))
    return "/" + "".join(syllables) + "/"


def parse_cmudict_line(line: str) -> tuple[str, str, str] | None:
    stripped = line.strip()
    if not stripped or stripped.startswith(";;;"):
        return None
    tokens = stripped.split()
    if len(tokens) < 2:
        return None
    word = re.sub(r"\(\d+\)$", "", tokens[0]).lower()
    if not word or not all(char.isalpha() or char in "'-" for char in word):
        return None
    phones = tokens[1:]
    ipa = arpabet_to_ipa(phones)
    if ipa is None:
        return None
    return word, ipa, " ".join(phones)


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
        lemma = tokens[first_lemma + index * 2].strip().lower()
        if lemma and all(char.isalnum() or char in "_'-" for char in lemma):
            lemmas.append(lemma)
    gloss = " ".join(gloss.strip().split())[:1800]
    return (lemmas, part_of_speech, gloss) if lemmas and gloss else None


def iter_text_lines(archive: zipfile.ZipFile, member: str) -> Iterable[str]:
    with archive.open(member, "r") as stream:
        with io.TextIOWrapper(stream, encoding="utf-8", errors="replace", newline=None) as reader:
            yield from reader


def build_database(
    archive_path: Path,
    output_path: Path,
    min_unique_words: int = 100_000,
    min_archive_bytes: int = 5_000_000,
    pronunciation_path: Path | None = None,
    min_pronunciation_words: int = 0,
    min_pronunciation_bytes: int = 1_000_000,
) -> int:
    if not archive_path.is_file() or archive_path.stat().st_size < min_archive_bytes:
        raise ValueError("The Open English WordNet archive is missing or unexpectedly small")
    if min_pronunciation_words > 0 and pronunciation_path is None:
        raise ValueError("A pronunciation file is required by the configured minimum")
    if pronunciation_path is not None and (
        not pronunciation_path.is_file()
        or pronunciation_path.stat().st_size < min_pronunciation_bytes
    ):
        raise ValueError("The CMUdict pronunciation file is missing or unexpectedly small")

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
                database.execute("""CREATE TABLE pronunciations (
                    word TEXT PRIMARY KEY, ipa TEXT NOT NULL, arpabet TEXT NOT NULL
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
                if pronunciation_path is not None:
                    metadata.extend([
                        ("pronunciation_dataset", "CMU Pronouncing Dictionary (CMUdict)"),
                        ("pronunciation_license", "Carnegie Mellon University custom permissive license"),
                        ("pronunciation_source_revision", CMUDICT_REVISION),
                    ])
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
                        tokens = line.strip().lower().split()
                        if 2 <= len(tokens) <= 16:
                            exceptions.append((tokens[0], pos, " ".join(tokens[1:])))
                    database.executemany(
                        "INSERT OR REPLACE INTO exceptions VALUES (?, ?, ?)", exceptions
                    )

                if pronunciation_path is not None:
                    pronunciation_rows = []
                    with pronunciation_path.open("r", encoding="ascii", errors="replace") as source:
                        for line in source:
                            parsed = parse_cmudict_line(line)
                            if parsed is None:
                                continue
                            pronunciation_rows.append(parsed)
                            if len(pronunciation_rows) >= 5000:
                                # Base entries occur before numbered variants; INSERT OR IGNORE
                                # preserves the dictionary's primary pronunciation.
                                database.executemany(
                                    "INSERT OR IGNORE INTO pronunciations VALUES (?, ?, ?)",
                                    pronunciation_rows,
                                )
                                pronunciation_rows.clear()
                    if pronunciation_rows:
                        database.executemany(
                            "INSERT OR IGNORE INTO pronunciations VALUES (?, ?, ?)",
                            pronunciation_rows,
                        )

                unique_words = database.execute("SELECT COUNT(DISTINCT word) FROM entries").fetchone()[0]
                sense_count = database.execute("SELECT COUNT(*) FROM entries").fetchone()[0]
                pronunciation_count = database.execute(
                    "SELECT COUNT(*) FROM pronunciations"
                ).fetchone()[0]
                if unique_words < min_unique_words or sense_count < min_unique_words:
                    raise ValueError(
                        f"Dictionary is unexpectedly small: {unique_words} words, {sense_count} senses"
                    )
                if pronunciation_count < min_pronunciation_words:
                    raise ValueError(
                        f"Pronunciation dictionary is unexpectedly small: {pronunciation_count} words"
                    )
                database.execute("PRAGMA user_version=2026")
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
    parser.add_argument("--min-unique-words", type=int, default=100_000)
    parser.add_argument("--pronunciations", type=Path)
    parser.add_argument("--min-pronunciation-words", type=int, default=0)
    args = parser.parse_args()
    count = build_database(
        args.archive,
        args.output,
        min_unique_words=args.min_unique_words,
        pronunciation_path=args.pronunciations,
        min_pronunciation_words=args.min_pronunciation_words,
    )
    with sqlite3.connect(args.output) as database:
        pronunciation_count = database.execute(
            "SELECT COUNT(*) FROM pronunciations"
        ).fetchone()[0]
    print(
        f"Built offline WordNet dictionary: {count:,} unique words -> "
        f"{args.output} ({args.output.stat().st_size:,} bytes)"
    )
    print(f"Added offline US pronunciations: {pronunciation_count:,} words")


if __name__ == "__main__":
    main()
