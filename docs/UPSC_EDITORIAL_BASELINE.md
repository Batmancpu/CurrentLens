# UPSC editorial baseline

**Snapshot:** 7 November 2026

## Official syllabus

Use the current UPSC Civil Services Examination 2026 notice, **Notice No. 05/2026-CSE, Section III**, as the syllabus source of truth:

- [UPSC CSE 2026 examination notice (PDF)](https://www.upsc.gov.in/sites/default/files/Notif-CSP-2026-Engl-060226Rev.pdf)
- [UPSC CSE 2026 examination page](https://www.upsc.gov.in/examinations/Civil%20Services%20%28Preliminary%29%20Examination%2C%202026)

For topical current-affairs mapping, the relevant baseline includes Prelims General Studies Paper I and Mains General Studies Papers I–IV. Prelims General Studies Paper II (CSAT) remains part of the examination but is an aptitude paper, not a topical-news classification target.

The GS I geography scope explicitly includes physical geography, distribution of key natural resources, location factors for industries, geophysical phenomena, and changes in critical geographical features. The Geography rule in CurrentLens is intended to cover these syllabus themes without treating a generic place-name or a broad publisher section as sufficient evidence.

The GS II Parliament and State Legislatures scope explicitly covers structure, functioning, conduct of business, powers and privileges, and issues arising from these. A bare mention of Parliament is therefore not a sufficient syllabus theme; default-feed eligibility requires a concrete legislative process, committee, privilege or parliamentary-function signal.

## Official previous-year question-paper window

The latest ten official CSE paper years available at this snapshot are **2017–2026 inclusive**:

- [UPSC Previous Year Question Papers — current page](https://www.upsc.gov.in/examinations/previous-question-papers)
- [UPSC Previous Year Question Papers — archives](https://www.upsc.gov.in/examinations/previous-question-papers/archives)

Use Prelims GS Paper I and Mains GS Papers I–IV from this window as the topical coverage reference. The official 2026 page lists Prelims GS Papers I–II and Mains GS Papers I–IV; the archive lists the earlier CSE years through 2017.

This is a coverage baseline, **not** a frequency-weighted prediction model. The current keyword rules have not been statistically validated against all questions in the ten-year corpus. Do not describe a matched-term count as a relevance score, probability, validated ranking, or prediction of a future UPSC question.

## Editorial decision contract

1. A default-feed item must map to a defensible GS paper and a named syllabus theme.
2. The card exposes the matched RSS terms and the syllabus connection as an **indicative reason**, not as proof that the full article is relevant.
3. Entertainment, celebrity, box-office, reality-show, routine sports and generic viral noise is excluded unless the headline itself identifies a concrete law, regulatory action, institutional decision or policy change.
4. Publisher RSS titles, excerpts, publication dates and feed URLs are publisher-supplied metadata. They are **not** primary-source corroboration.
5. Verify material claims against the original public document. CurrentLens does not yet attach an independently verified official-source document to each publisher report.
6. A question is labelled an official PYQ only after its year, paper and official UPSC source have been verified. Original practice prompts remain labelled as original or PYQ-style.

## Labelled editorial fixtures

The unit-test fixtures are synthetic examples designed to expose classification errors; they are **not** verbatim UPSC questions.

| Label | Example | Expected decision | Reason |
|---|---|---|---|
| False-negative recovery | “Monsoon onset shifts across the Indo-Gangetic plain”; excerpt mentions rainfall distribution and river-basin flows | Include — GS I, Geography | Physical geography and changing water systems are explicit GS I syllabus themes |
| Positive | “RBI holds repo rate amid inflation concerns” | Include — GS III, Indian Economy | Monetary policy, repo rate and inflation are specific economy signals |
| False-positive rejection | “Film trailer celebrates Himalayan glaciers”; excerpt mentions glacier and river-basin imagery | Exclude | Entertainment noise in the headline overrides incidental geography words in the excerpt |
| False-positive rejection | Generic consumer-technology trend story in an “UPSC” publisher section | Exclude | A broad section label and generic technology wording do not establish a specific syllabus theme |
| False-positive rejection | “Parliament opens a new visitor centre and cafeteria” | Exclude | An institution-only mention does not establish Parliament’s structure, functioning, conduct of business, powers or privileges | 
| False-negative recovery | “Parliament passes a bill to amend electoral rules” | Include — GS II, Constitution/Polity | A concrete legislative action and Representation of the People Act theme are explicit GS II signals | 
| Positive | “Standing committee reviews a bill on parliamentary privileges” | Include — GS II, Constitution/Polity | Committee scrutiny and parliamentary privileges map to the syllabus’ functioning and powers themes |
| False-positive rejection | Celebrity film-release story whose excerpt merely mentions film-certification rules | Exclude | A policy exception must be established by a concrete action in the headline itself |
| False-positive rejection | “Cricket star's film on national sports policy releases Friday” | Exclude | A policy noun inside a promotional headline is not an institutional action on the policy |
| Positive | “Government issues new broadcasting regulations for OTT platforms” | Include — GS II, Governance | A named government actor issues a concrete regulatory instrument |

The fixtures are regression checks for this deterministic rule set. They do not establish precision/recall, validate a score, or predict examination questions.

## Source-provenance limitation

The current RSS registry is publisher-led (The Hindu, Indian Express, BBC, DW and Al Jazeera). The stored `feedUrl` records which publisher feed supplied an item; it does not prove that the report is supported by PIB, the Gazette of India, a ministry, Parliament, PRS, RBI, the Supreme Court or a statutory/regulatory body. Do not display publisher RSS metadata as an official primary source. Official-source feeds and per-story corroboration require endpoint validation and an explicit provenance model before being presented as verified.
