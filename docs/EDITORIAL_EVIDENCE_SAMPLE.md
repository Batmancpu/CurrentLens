# Small source-labelled editorial sample — 19 November 2026

This is a **regression design register**, not a representative sample of publisher RSS articles and **not** a measured precision/recall benchmark. Sources below are official primary documents consulted to establish what the development fixtures mean. CurrentLens does not ingest PIB as an RSS feed or independently verify primary documents for each publisher story.

The baseline is the [official UPSC CSE 2026 Mains syllabus](https://www.upsc.gov.in/sites/default/files/Notif-CSP-2026-Engl-060226Rev.pdf). Relevant syllabus topics include GS II government policies/governance, GS III farm subsidies, and GS III Infrastructure (energy, ports, roads, airports, railways).

| Evidence | Official primary source and date | Human-labelled expected paper/theme | Regression status | Limit |
| --- | --- | --- | --- | --- |
| DPDP Consent Manager registration framework | [PIB PRID 2336151](https://www.pib.gov.in/PressReleseDetailm.aspx?PRID=2336151&lang=1&reg=1), 14 Nov 2026 | Positive: GS II, Governance (regulatory implementation) | Source-backed title/body and negative digital-portal fixture passed #427 / 0.3.5 | Live RSS coverage and classifier population accuracy unmeasured |
| Cabinet Nutrient Based Subsidy, Rabi 2026–27 | [PIB PRID 2334972](https://www.pib.gov.in/PressReleseDetailm.aspx?PRID=2334972&lang=1&reg=1), 11 Nov 2026 | Positive: GS III, Agriculture (farm subsidies) | Source-backed title-only and negative consumer-subsidy fixture passed #428 / 0.3.6 | No proof the 14 configured publisher feeds carry it |
| NHAI multi-lane free-flow tolling standardisation | [PIB PRID 2333654](https://www.pib.gov.in/PressReleasePage.aspx?PRID=2333654&lang=1&reg=3), 6 Nov 2026 | Positive: GS III, Infrastructure (roads) | Title-only fixture and narrow policy map in 0.3.7; passed run #429 | Real feed headlines may be worded differently |
| Continuation of Stand-Up India Scheme | [PIB PRID 2334978](https://www.pib.gov.in/PressReleasePage.aspx?PRID=2334978&lang=1&reg=48), 11 Nov 2026 | Positive public policy: GS II vulnerable-section welfare **or** GS III inclusive growth/finance; paper disambiguation needs editorial review | **Not yet included in regression assertions**; avoid fabricating one clear paper from an ambiguous source | Needs labelled real publisher excerpts and negative variants |
| VISTAR official-statistics modernisation | [PIB PRID 2332480](https://www.pib.gov.in/PressReleasePage.aspx?PRID=2332480&lang=2&reg=3), 4 Nov 2026 | Positive: GS II Governance (statistics policy) is a candidate; public-data governance details need explicit audit | **Not yet included in regression assertions** | Do not count a generic `scheme` signal as validated subject accuracy |

## Synthetic rejection controls — not real-story corpus labels

- A generic consumer broadband subsidy must not be classified as a farm subsidy (existing JVM fixture).
- A generic digital portal must not inherit the DPDP statutory-regulation mapping (existing JVM fixture).
- A highway travel-photo story or cinema event near a toll plaza must not be classified as a transport-infrastructure policy announcement (0.3.7 JVM fixtures, passed run #429).

Next evaluation: collect a reproducible, dated set of publisher RSS headlines/excerpts from each configured source under its terms, label inclusion/exclusion and exact paper/theme **before** inspecting classifier output, and record both false positives and false negatives. Separate primary-document evidence from the publisher feed's source/date/URL. Do not add runtime PIB scraping or user tracking as a shortcut.

## Publisher-page sample, 24 November 2026

These are real publisher-page titles, not independently collected RSS items. Labels were set before checking the candidate. Title-only tests cover three positives; the existing synthetic negative controls remain. No population accuracy estimate.

| Publisher article | Expected mapping | Official corroboration |
| --- | --- | --- |
| [Cabinet fertiliser subsidy](https://indianexpress.com/article/business/fertiliser-subsidy-rabi-season-2026-27-cabinet-approval-10959144/), 11 Nov | GS III Agriculture | [PIB 2334972](https://www.pib.gov.in/PressReleseDetailm.aspx?PRID=2334972&lang=1&reg=1), same date: approved P&K rates and tentative budget about Rs 62,370 crore. |
| [Barrier-less toll lanes](https://indianexpress.com/article/business/barrier-less-tolling-system-new-highways-10943979/), Oct 30 | GS III Infrastructure | [PIB 2333654](https://www.pib.gov.in/PressReleasePage.aspx?PRID=2333654&lang=1&reg=3), later 6 Nov: standardized toll infrastructure and rationalized lane count, not every numerical detail. |
| [COP31 climate summit explainer](https://indianexpress.com/article/explained/explained-climate/cop31-antalya-turkey-un-summit-india-agenda-2026-expert-explains-10953189/), 10 Nov | GS III Environment | No per-claim primary source checked in this batch. |

The first two exact article URLs get a local claim-limited primary-source evidence record. No other URL or generic keyword match does.
