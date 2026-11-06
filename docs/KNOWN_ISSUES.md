# CurrentLens — Known Issues and Verification Register

Status vocabulary:
- Verified on main: directly present/reproduced in current main code or CI evidence.
- User-reported: user observed it; durable reproduction evidence may be absent.
- Draft PR: proposed on branch, not shipped.
- Unverified: insufficient device/test evidence.
- Resolved in code: code path changed; runtime/user outcome is not automatically proven.

Snapshot main: 73b8a0d1ba33503fede02db2c3c0af082d724a12 (6 Nov 2026).

## Highest-priority reliability

### KL-001 — OPPO cold-start crash
- Status: User-reported; root cause unverified; not certified fixed.
- User reported installed Build 100 exited immediately on open on OPPO Reno10 5G.
- PR #7 moved cached articles/bookmarks/read history/session/quiz restore off UI, cached feed filters, bounded image decode, guarded palette sampling, guarded OEM system-bar styling and reverted icon substitution. Merged as 81de60c.
- PR #9 added bounded local-only uncaught-exception diagnostics/recovery, moved shared feed index off composition path, hardened SharedPreferences restore, reduced rendering work. Merged as 2475970.
- PR #10 fixed Search glass compile scope. Main ba06a8f passed Build #103. Main later received branding commits; Build #116 succeeded.
- No OPPO logcat, local crash report, tombstone, ANR or physical cold-launch proof available in captured evidence.
- Do not claim crash fixed from CI. Retrieve local crash report if recovery UI appears, or capture logcat/tombstone. Test cold, restored-state and warm launch separately.

### KL-002 — Main-thread local persistence
- Status: Draft PR #8; not shipped/verified.
- Main LocalStore.kt/MainActivity paths perform synchronous preference/JSON operations for reading-session history, quiz history, reader positions and read markers in some interaction/lifecycle paths.
- PR #8 moves operations to IO and uses mutexes/ordering to avoid save/clear races.
- Risks: async UI state updates, process death during writes, unbounded read-marker set, lifetime unique-read semantics.
- PR #8 now 13 commits behind/diverged; do not blindly merge/reimplement.

### KL-003 — Read-history storage unbounded
- Status: Known design risk; PR #8 does not fix retention.
- Read-marker URL membership is retained to preserve lifetime unique-read counts. Any cap/pruning requires product/migration decision and clear metric semantics. Do not silently truncate.

## Search, IME and visuals

### KL-004 — Search vertical gap and transition stutter
- Status: User-reported; physical-device verification outstanding.
- Search field reportedly sits far above keyboard; rise/fall/morph is stuttery.
- Historical Search/IME work: b48ccfb, 2154ed0, f52863d, 4f87667, 5ec5d9f, ef3622b.
- Capture current OPPO screen recording with keyboard open/closing; measure IME insets and field-bottom-to-keyboard-top gap. No speculative offset constants.

### KL-005 — Adaptive glass contrast differs from older descriptions
- Status: Main is theme-derived; adaptive restoration is Draft PR #13.
- PR #9 removed recurring 8x8 readback from feed in favour of theme-derived contrast. Probe implementation remains in source.
- PR #13 proposes one shared bottom-band probe, low cadence and foreground/home/Search gating; it is 13 commits behind/diverged.
- No physical-device GPU/frame-time proof. Verify active call site before describing as shipped.

### KL-006 — Explore chip accessibility
- Status: Draft PR #6.
- PR #6 proposes 48dp filter hit targets and selected-state TalkBack semantics; not on main. 13 commits behind/diverged.
- Verify TalkBack and touch on device after integration.

### KL-007 — Reader font-scale propagation
- Status: Needs physical review.
- WebView CSS sizing may not automatically track Android system font scale. Verify on large system font settings.

## Editorial trust

### KL-008 — Geography false negatives
- Status: Draft PR #6; not in main.
- Main UpscEngine rules lack dedicated GS I Geography rule per source inspection. PR #6 adds Geography themes/category.
- PR #6 is 10 ahead/13 behind latest main, diverged. Review/rebase before relying on it.

### KL-009 — Primary-source corroboration
- Status: Open product gap.
- Main has 14 configured publisher RSS feeds: The Hindu 7, Indian Express 4, BBC 1, DW 1, Al Jazeera 1.
- Publisher RSS URL/excerpt/timestamp is publisher provenance, not independent primary-source corroboration.
- No per-story independently checked primary-source record exists in main.
- Future record should store primary document URL, issuing authority, publication date and exact claim supported; otherwise display “not independently corroborated”.
- Never fabricate official evidence or describe a publisher RSS feed as a ministry/PIB source.

### KL-010 — Atom alternate link selection
- Status: Draft PR #12; not shipped.
- Main parser uses a single link variable; Atom rel=self may overwrite rel=alternate depending on element order.
- PR #12 collects candidates and prefers safe HTML rel=alternate, with focused tests.
- PR #12 1 ahead/13 behind and diverged.

### KL-011 — Publication-date precedence/cache
- Status: Draft PR #11; not shipped.
- Main can let Atom updated overwrite published; unparseable dates can remain cached; arbitrary future dates are not bounded.
- PR #11 prefers pubDate/published, uses updated only as fallback, excludes undated/stale/far-future unsaved stories while keeping bookmarks.
- PR #11 2 ahead/13 behind, diverged.
- URL duplicate detection remains exact; tracking parameters can create duplicates. Do not strip all query parameters blindly.

### KL-012 — Editorial relevance validation
- Status: Heuristic + synthetic tests only.
- Main uses whole-token/phrase matching plus rule-based GS map/suppression; not statistically validated.
- Synthetic fixtures do not prove precision/recall. Do not report score as validated or predict exam questions.
- Evaluate both false positives and false negatives on labelled real corpus.
- Main includes broad film noise term per source review; PR #6 proposes retaining thin-film photovoltaics while excluding film promotion.

## Branding and design

### KL-013 — Canonical app-name capitalization
- Status: User preference/code discrepancy.
- User previously specified brrrNEWS (lowercase b + three lowercase r's + uppercase NEWS).
- Main commits first set brrrNEWS, later changed display/copy/theme/README/artwork aria-label to brrrNews.
- Confirm before future branding changes.

### KL-014 — Original screenshots/videos missing from repository
- Status: Historical descriptions only.
- PR #3 describes supplied OPPO screenshots, but original image/video bytes are not stored in this repo snapshot.
- Design observations are in docs/DESIGN_EVIDENCE.md; exact dimensions/colours/coordinates cannot be reconstructed.
- Do not publish private screenshot assets without explicit approval.

## Open PR state at snapshot

- #6 open draft: 10 ahead / 13 behind latest main, diverged.
- #8 open draft: 3 ahead / 13 behind, diverged.
- #11 open draft: 2 ahead / 13 behind, diverged.
- #12 open draft: 1 ahead / 13 behind, diverged.
- #13 open draft: 1 ahead / 13 behind, diverged.
- #14 continuity documentation PR: being rebuilt on latest main; README excluded.
- No PR branch CI result is implied by passing Build #116.


### KL-015 — Latest launcher cold-start exits back to Android launcher
- Status: User-reported; root cause unverified; PR #16 adds earlier diagnostics/recovery but does not prove the root fix.
- Current symptom: tap app icon → opening animation → no visible app screen → Android returns to launcher.
- Same user previously reported a cold-start failure on OPPO Reno10 around Build 100. Do not assume the old and new events have identical causes until log evidence matches.
- Main's prior handler was installed only in MainActivity.onCreate(). PrismalAGSL v1.0.4 registers an AndroidX Startup InitializationProvider through its merged manifest, which is initialised before Activity creation. This identifies an instrumentation blind spot, not proof that Prismal is the crashing component.
- PR #16: https://github.com/Batmancpu/CurrentLens/pull/16 (draft, unmerged; branch head 28a6950fe9e1c0e53f48deacc92cbcd7e89dc4e8).
- PR #16 installs the local handler from Application.attachBaseContext(), records startup checkpoints, writes detailed exception reports and falls back to a plain Android View recovery screen. It can also record a clearly labelled startup interruption without a managed stack trace.
- No current OPPO logcat/tombstone/ANR/local crash report is available. Do not claim fixed from source review or CI.
- After review/merge and installation, retry launch and copy the native recovery report. If no report appears, obtain Android bugreport/logcat/tombstone/ANR evidence; native/system kills may not invoke a Java uncaught-exception handler.
