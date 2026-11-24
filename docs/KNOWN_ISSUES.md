# CurrentLens — Known Issues and Verification Register

## 24 November 2026 — emulator infrastructure correction

Run #433 reached successful JVM tests and release assembly, but the non-blocking emulator step exited 124 at 10:44:37 UTC after the 180-second ADB transport wait. No PASS checkpoint; the app was never installed/tested by this step. GitHub marks a continue-on-error step success, so its exit log is authoritative. Candidate 0.4.2 starts ADB before the emulator, explicitly sets console port 5554 and uses software swiftshader rendering, checks acceleration and process liveness, and prints bounded runner-only boot logs on transport/boot failure. Cause not yet established; one bounded retry, not a claim of runtime reliability.

## 24 November 2026 — verified 0.4.0 / android-21

- [Run #432](https://github.com/Batmancpu/CurrentLens/actions/runs/45375991778) completed successfully, both build and publish jobs. Exact source `a66bf8af0ea6aefa42045f9dbf9c5a30fbfa3fec`, [tag android-21](https://github.com/Batmancpu/CurrentLens/releases/tag/android-21), one signed APK (30,880,094 bytes), SHA-256 `4e2c742be1c0f598ca1ed9288de84121f5a7b00791557c3bb39167513417c371`, pinned release certificate unchanged. JVM backup merge and codec round-trip tests passed. Physical file-picker/storage verification outstanding.
- 0.4.1 prepared to extract Reader document generation and improve accessible typography, add a Reader document regression test and bounded debug-emulator runtime smoke, including five launches, warm return, Saved/Reader, Search and offline dictionary definition. Device-specific visual/performance/TalkBack tests remain distinct. The smoke step is non-blocking; inspect actual logs.

Status vocabulary:
- Verified on main: directly present/reproduced in current main code or CI evidence.
- User-reported: user observed it; durable reproduction evidence may be absent.
- Draft PR: proposed on branch, not shipped.
- Unverified: insufficient device/test evidence.
- Resolved in code: code path changed; runtime/user outcome is not automatically proven.

Live snapshot: app source `3fe98e8ebdf28c5ab11f37e3de727dee691a704e`, release `android-18`, [run #429](https://github.com/Batmancpu/CurrentLens/actions/runs/44581595658) passed; verified 24 November 2026. Some detailed Stability/PR history below is retained from its dated snapshot; where a status conflicts, current main and the live entries below are authoritative. No open PRs were returned by the latest GitHub check.

## Highest-priority reliability

### KL-001 — Android test device cold-start crash
- Status: User-reported; root cause unverified; not certified fixed.
- User reported installed Build 100 exited immediately on open on the Android test device.
- PR #7 moved cached articles/bookmarks/read history/session/quiz restore off UI, cached feed filters, bounded image decode, guarded palette sampling, guarded OEM system-bar styling and reverted icon substitution. Merged as 81de60c.
- PR #9 added bounded local-only uncaught-exception diagnostics/recovery, moved shared feed index off composition path, hardened SharedPreferences restore, reduced rendering work. Merged as 2475970.
- PR #10 fixed Search glass compile scope. Main ba06a8f passed Build #103. Main later received branding commits; Build #116 succeeded.
- No Android test device logcat, local crash report, tombstone, ANR or physical cold-launch proof available in captured evidence.
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
- Capture current Android test device screen recording with keyboard open/closing; measure IME insets and field-bottom-to-keyboard-top gap. No speculative offset constants.

### KL-005 — Adaptive glass contrast differs from older descriptions
- Status: Shared adaptive sampling is in current main; physical-device appearance/performance remain unverified.
- `rememberBottomBandGlassLuminance` supplies Navigation and Search from one 8x8 bottom-band sample. Sampling is paused for feed motion, Reader/quiz, Settings, background and Reduce Transparency states.
- No physical-device GPU/frame-time proof. Do not claim visual or performance correctness from source/CI alone.

### KL-006 — Explore chip accessibility
- Status: Minimum-size and selected semantics are present in current main; TalkBack/touch validation remains pending.
- Explore category chips use 48dp minimum height and `Role.Tab`; GS/topic filter pills use 48dp minimum dimensions and selectable semantics. Run #426 compiled the current main code; no TalkBack device result is available.

### KL-007 — Reader font-scale propagation
- Status: Implemented and passed the current JVM build; physical review pending.
- Reader CSS applies Android's configured font scale to persisted `Aa` sizing, and the publisher fallback derives `WebSettings.textZoom` from it. The control remains disabled until its stored value loads. Run #426 passed; verify large system font settings and definition-selection anchors on-device.

## Editorial trust

### KL-008 — Geography false negatives
- Status: A GS I Geography rule and regression coverage are present in current main; the rule remains heuristic and real-story coverage is incomplete.
- `UpscEngine` maps physical geography, monsoon, rivers, resources, industry location and geophysical phenomena to the Geography syllabus link. Explore/category/search integration and false-positive safeguards have dedicated regression tests. Run #426 passed JVM CI, which does not establish editorial precision/recall.

### KL-009 — Primary-source corroboration
- Status: Open product gap.
- Main has 14 configured publisher RSS feeds: The Hindu 7, Indian Express 4, BBC 1, DW 1, Al Jazeera 1.
- Publisher RSS URL/excerpt/timestamp is publisher provenance, not independent primary-source corroboration.
- No per-story independently checked primary-source record exists in main.
- Future record should store primary document URL, issuing authority, publication date and exact claim supported; otherwise display “not independently corroborated”.
- Never fabricate official evidence or describe a publisher RSS feed as a ministry/PIB source.

### KL-010 — Atom alternate link selection
- Status: Candidate collection and alternate-link preference are present in current main; live publisher behavior remains unverified.
- The parser resolves candidates against effective `xml:base`, selects safe `rel=alternate` HTML where available, and no longer relies on the last encountered link. `NewsRepositoryTest` covers a relative alternate link and an unsafe alternate. Run #426 passed JVM CI.

### KL-011 — Publication-date precedence/cache
- Status: Original-date precedence and cache-level invalid/future-date rejection are present. 0.3.7 also filters invalid/future entries before the incoming shortlist limit; run #429 passed.
- `FeedPublicationDatePolicy` prefers valid RSS `pubDate` / Atom `published`, then uses `updated` and generic dates as fallbacks. Timezone-less values use Asia/Kolkata. Prior to 0.3.7, `ArticleCachePolicy.retain` already rejected entries more than five minutes into the future unless explicitly saved, but `NewsRepository.fetchAll()` sorted/took only 250 before that cache check. Enough future-dated feed items could displace legitimate ones. The candidate screens undated/future items before this limit and retains the same cache/bookmark semantics. `NewsRepositoryTest` models 300 bad future items plus current and slightly future items. **Focused regression and JVM suite passed run #429.**
- URL duplicate detection remains exact; tracking parameters can create duplicates. Do not strip all query parameters blindly.

### KL-012 — Editorial relevance validation
- Status: Heuristic + synthetic tests only.
- Main uses whole-token/phrase matching plus rule-based GS map/suppression; not statistically validated.
- Synthetic fixtures do not prove precision/recall. Do not report score as validated or predict exam questions.
- Evaluate both false positives and false negatives on labelled real corpus.
- Current main includes a narrow thin-film photovoltaic/solar-cell science exception while retaining suppression for film promotion; fixture coverage does not establish real-story precision/recall.

## Branding and design

### KL-013 — Canonical app-name capitalization
- Status: User preference/code discrepancy.
- User previously specified brrrNEWS (lowercase b + three lowercase r's + uppercase NEWS).
- Main commits first set brrrNEWS, later changed display/copy/theme/README/artwork aria-label to brrrNews.
- Confirm before future branding changes.

### KL-014 — Original screenshots/videos missing from repository
- Status: Historical descriptions only.
- PR #3 describes supplied Android test device screenshots, but original image/video bytes are not stored in this repo snapshot.
- Design observations are in docs/DESIGN_EVIDENCE.md; exact dimensions/colours/coordinates cannot be reconstructed.
- Do not publish private screenshot assets without explicit approval.

## Historical PR state — 6 November snapshot

- #6 open draft: 10 ahead / 13 behind latest main, diverged.
- #8 open draft: 3 ahead / 13 behind, diverged.
- #11 open draft: 2 ahead / 13 behind, diverged.
- #12 open draft: 1 ahead / 13 behind, diverged.
- #13 open draft: 1 ahead / 13 behind, diverged.
- #14 continuity documentation PR: being rebuilt on latest main; README excluded.
- No PR branch CI result is implied by passing Build #116.


### KL-015 — Latest launcher cold-start exits back to Android launcher
- Status: Historical user report; current cold-start outcome and source-level root cause are unverified. Diagnostics are now on main, but the owner has not confirmed repeated 0.3.4 launches.
- Current symptom: tap app icon → opening animation → no visible app screen → Android returns to launcher.
- Same user previously reported a cold-start failure on the Android test device around Build 100. Do not assume the old and new events have identical causes until log evidence matches.
- Main's prior handler was installed only in MainActivity.onCreate(). PrismalAGSL v1.0.4 registers an AndroidX Startup InitializationProvider through its merged manifest, which is initialised before Activity creation. Prismal's initializer itself only binds the asset manager; this identifies an instrumentation blind spot, not proof that Prismal is the crashing component. Android references: https://developer.android.com/topic/libraries/app-startup and https://developer.android.com/reference/androidx/startup/InitializationProvider.
- PR #16 is historical: its early-handler/checkpoint/recovery work was consolidated into current main. The link and old branch head are not current open-PR state.
- Current main installs a local handler early, records startup checkpoints, writes local exception reports and provides a recovery screen. Diagnostic setup/checkpoint I/O is best-effort and must not abort application startup.
- No current Android test device logcat/tombstone/ANR/local crash report is available. Do not claim fixed from source review or CI.
- On 0.3.4, retry cold launch and use the minimised recovery report if one appears. If no report appears, obtain Android bugreport/logcat/tombstone/ANR evidence; native/system kills may not invoke a Java uncaught-exception handler.


### KL-016 — Native crash persists under Prismal Legacy; full-cause isolation pending
- Status: User-reproduced on Stability 0.2.3; root cause still unverified.
- The 0.2.3 test build forced Prismal Legacy on Android API 33+ (AGSL and RenderEffect disabled), but the owner supplied a new `CRASH_NATIVE` / signal 11 startup report. The last persisted app stage was `MainActivity.setContent returned`; the next `BrrrNewsApp` composition checkpoint had not been persisted.
- A partial native tombstone excerpt contains ART finalizer/monitor wait symbols and a stack-pointer heuristic. It does not, by itself, prove that ART finalization, recursion, Prismal or a named app function caused the failure. The report did not capture a Java/Kotlin exception.
- The new trace differs from the earlier Mali/HWUI/Skia trace. Keep both signatures as evidence; do not collapse them into one confirmed cause.
- Stability 0.2.5 adds a plain Compose/Material first-launch probe, then a manual transition to the full app with Prismal's normal pipeline. The automatic Legacy override was removed. Build #158 passed CI; physical-device result remains pending.
- Required next test: confirm probe cold-launch, tap into the complete Liquid Glass app, then repeat at least five cold/warm launches. If failure occurs, note whether it happened before or after the full-app button using the privacy-minimised local report.
- Do not publish raw traces, build fingerprints, device model/code, PIDs/TIDs, exact timestamps or memory addresses. See root `AGENTS.md`.


### KL-017 — Full-app first-frame native crash after the plain probe (Stability 0.2.5)
- Status: User-reproduced on physical device; source-level root cause unverified.
- Shareable report: `CRASH_NATIVE (5)`, signal 11; last coarse startup stage: `Full brrrNews composition requested from stability probe`; no Java/Kotlin exception.
- The attached 21.36-second recording shows the plain Compose/Material startup probe rendering; after the user transitions to the complete app, the process returns to the launcher/recovery flow. This narrows the failure to the full-app transition/initial composition or rendering path, not the plain probe.
- The 0.2.3 Legacy attempt still crashed. Legacy disables AGSL and RenderEffect but retains Prismal's sampled GraphicsLayer capture/draw path; it therefore did not rule out GraphicsLayer capture, merged sources or offscreen composition.
- Normal/production rendering remains on Prismal's automatic pipeline with full effects; no forced Legacy override is present in the current PR branch.
- Stability 0.2.6 adds three isolated, incremental checks: root capture only; one full Liquid Glass surface; two captured layers plus merged tab backdrop. Full app remains a separate final button.
- The 0.2.5 report and recording contain no raw identifiers in the repository. Do not upload the video or any raw tombstone to GitHub.
- Root cause must remain “not established” until a failing stage is reproduced with a sanitised symbol-level trace or a code-level trigger and then verified on repeated device launches.


### KL-018 — Prismal backdrop self-capture in the full app
- Status: **Background-only capture is in current main and shipped; native-crash causality remains unverified without another device trace.**
- Source review found that `MainActivity.BrrrNewsApp` applied `.prismalGlassLayer(backdrop)` to the complete content `Column`, while descendant surfaces such as the home header, Explore/Search glass controls and reader/settings glass sampled that same `backdrop`.
- PrismalAGSL v1.0.6 specifies capture for background content with glass drawn above it. Its modifier draws the child subtree and then records that subtree into the backing GraphicsLayer. Descendant glass surfaces reading the same layer create a self-referential capture/sampling path, a strong explanation for a failure that begins only when the full UI is composed and rendered.
- Correction: move the captured layer to a background-only sibling and keep all app content/glass surfaces outside the captured subtree. Add subtle ambient background gradients so the full glass effects still have a non-flat source.
- Preserve the original automatic Prismal pipeline and all blur, refraction, chromatic, specular, depth and spring settings. The separate tab-glyph capture/merged droplet source remains intact.
- The Stability build is advanced to 0.2.7 / versionCode 9 so it can be installed as an update to 0.2.6 with the same test signing key.
- No new user test was requested or run. CI result is recorded separately; the native signal-11 causal link is still a strong diagnosis, not device-proven fact. No raw trace, device identifier or user media is committed.


### KL-019 — Opaque Android process-exit description in copyable diagnostics
- Status: Numeric allow-list correction is in current main and passed release CI; cold-start causality remains a separate question.
- The full-app recovery screen exposes a selectable/copyable startup report. The previous describeExitInfo() copied ApplicationExitInfo.description directly and also included importance plus PSS/RSS. The free-form platform description is not an allow-listed field and may contain low-level system details; the UI's promise that the report omits raw system traces was therefore stronger than the implementation could guarantee.
- Current main exports only the numeric exit reason and numeric status/signal. It does not forward the opaque description, importance or memory measurements. Java/Kotlin reports continue to omit exception messages and raw addresses/traces.
- Focused JVM assertions cover the exact allow-listed summary and interrupted-startup report; current release run #426 passed.
- This change improves report minimisation only. It does not establish the native launch crash's root cause or prove the app launches on a physical device.


### KL-020 — Quiz option selected-semantics receiver shadowing

- **Status:** Resolved in code and natural CI on `black-box-bihar-news@6f9b3c1e1a484233721863d0b736734c0d282cd5`; Android/TalkBack runtime remains unverified.
- The natural push build on pre-fix SHA `947310a157e03df25dde3856d2686d18ffca68e8` failed at `MainActivity.kt:4238:61` because `.semantics { selected = chosen }` resolved to the local `Map<Int, Int>` answer state rather than the semantics receiver's Boolean property.
- Correction: `.semantics { this.selected = chosen }`. The change is one line, preserves the intended selected-state semantics and does not alter quiz answer/scoring/submission persistence.
- PR #28's direct child commit was fast-forwarded into the active integration branch. Natural run #209 passed on the exact code head in PR context; natural active-branch run #210 passed the same exact SHA with `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload.
- This is a compile defect, not evidence of a device crash. TalkBack selected-state behaviour, font scaling and physical-device layout still require a real-device check.


### KL-022 — Inline Atom content is discarded when no summary is present

- **Status:** Corrected in current main and passed release #426 JVM CI; no live-feed format audit has been made.
- `NewsRepository.parse()` reads inline Atom content only for the Atom namespace and accepted text/HTML/XHTML types, preserves URL-backed Media RSS image handling, and does not fetch external Atom `src` references.
- Focused tests cover the accepted types, external `src`, binary media, Media RSS and nested-XHTML parser cursor. This remains a parser coverage result, not a measured live-feed or editorial-recall result; the whole-document parser still has a separate streaming/memory limit.
- This is a standards-derived false-negative risk, not an observed live-feed count or measured editorial recall result. It adds no network request and does not make the 2 MiB whole-document parser streaming. Relative Atom links, entry ceilings, concurrency and publisher-host validation remain separate issues.


### KL-023 — RSS endpoint redirects are not constrained to the configured publisher

- **Status:** Publisher-bound redirect checks are in current main and passed release #426 JVM CI; live redirect chains remain unverified.
- `NewsRepository` disables automatic redirects, validates the initial feed host and each resolved `Location` against the configured publisher's HTTPS allowlist, accepts standard redirect codes, caps the chain at five and fails closed on off-publisher/lookalike/cleartext/credential/non-standard-port targets. `bbci.co.uk` is included for the configured `feeds.bbci.co.uk` source.
- Focused redirect-policy and feed-host tests are present. This closes the code-level network-boundary defect; it does not prove every live publisher feed's redirect chain. Image URLs and WebView subresources remain separate policies.


### KL-024 — Apple-inspired Reader fit and finish
- Status: First implementation batch is shipped in 0.3.4 and passed run #426; physical-device review remains pending.
- The owner asked for an Apple-style Reader “closest to the Apple product.” No CurrentLens Reader screenshot/reference was supplied in this batch, so the current design uses a deliberate editorial hierarchy rather than claiming pixel parity: readable title/body typography, constrained line length, persisted `Aa` sizing, Android font scale, source/date, save/share, restored progress and an indicative syllabus context.
- The syllabus card is derived from publisher RSS metadata and explicitly says it is not independent primary-source verification. It is omitted for unmapped articles. Share remains limited to URLs allowed by `PublisherHostPolicy`.
- Acceptance still requires phone review of toolbar fit, light/dark presentation, font scale, pinch zoom, text selection/definition anchors, bookmark state, share chooser, scroll/progress restore and the fallback page. CI is not visual/device evidence.

### KL-025 — DPDP implementation can map to a generic technology label
- Status: Resolved in code for the official example; the focused fixture and full JVM suite passed in run #427. Live publisher-feed mapping and editorial precision/recall remain unverified.
- The official PIB release of 14 November 2026 describes the Consent Manager registration framework under the Digital Personal Data Protection Act and the Data Protection Board. The current classifier recognized generic `digital` and `data protection` signals but did not give the named regulatory actors enough weight for GS-II Governance.
- The official 2026 UPSC syllabus places government policy/intervention, statutory and regulatory bodies, and governance/accountability under GS II. The local change adds only DPDP-specific regulatory entities/terms to Governance and the feed eligibility allowlist.
- Verification: the official fixture maps to GS II / Governance and remains eligible with explainable matched terms; the generic digital portal fixture is suppressed. Both passed run #427 on source `307a209...`. This one official example does not establish editorial precision/recall. Live publisher feed behavior remains unverified.

### KL-026 — Nutrient Based Subsidy headline may miss the GS-III farm-subsidy mapping
- Status: Narrow phrase mapping and official-title regression fixture shipped in 0.3.6 / versionCode 17. The fixture, release assembly, signer check and publication passed run #428 on source `779c642...`. Live-feed behavior and editorial precision/recall remain unverified.
- The official [PIB Rabi 2026-27 release](https://www.pib.gov.in/PressReleseDetailm.aspx?PRID=2334972&lang=1&reg=1) uses “Nutrient Based Subsidy” in the headline and describes fertilizer subsidies for farmers. The official [UPSC CSE 2026 notice](https://www.upsc.gov.in/sites/default/files/Notif-CSP-2026-Engl-060226Rev.pdf) explicitly lists direct and indirect farm subsidies under GS III.
- Static review found the headline phrase absent from both the GS-III Agriculture keywords and specific eligibility signals. The candidate adds only `nutrient based subsidy` to those lists. Its title-only fixture asserts GS III / Agriculture, the matched phrase and inclusion; a generic consumer-internet subsidy headline is the negative case.
- This source-backed fixture is not a labelled-corpus precision/recall result and does not verify the live publisher feed.

### KL-027 — National-highway tolling infrastructure missed by the GS mapping
- Status: Missing dedicated GS-III Infrastructure rule verified in 0.3.6 source; narrow mapping/test shipped in 0.3.7, **CI verified in run #429**.
- The official 6 November 2026 [PIB NHAI notice](https://www.pib.gov.in/PressReleasePage.aspx?PRID=2333654&lang=1&reg=3) concerns standardised multi-lane free-flow tolling infrastructure and new guidelines. The [UPSC CSE 2026 syllabus](https://www.upsc.gov.in/sites/default/files/Notif-CSP-2026-Engl-060226Rev.pdf) explicitly includes Infrastructure (roads, energy, ports, airports and railways) under GS III.
- Candidate adds specific phrases (e.g. multi-lane free-flow tolling, highway infrastructure) without opening a generic one-word `road`/`infrastructure` eligibility escape. A headline-only positive and two unrelated-event negatives are added. These cases do not establish real publisher-feed recall or corpus metrics.

## 24 November 2026 candidate continuation

## 24 November 2026 — feed health, shared editorial index and primary evidence candidate 0.3.9

- Owner request remains complete the full roadmap. Prepared against source `cd457b25e05d8f0289a9948ee364ea7cae136d50` while its CI #430 was active; do not advance this detached candidate until #430 is terminal and its result is reconciled.
- Changes: `NewsRepository.kt`, `NetworkLimits.kt`, `FeedHealthPolicy.kt`, `FeedHealthPanel.kt` distinguish publisher errors, unsafe redirects, limits, invalid XML, timeout/TLS/network, no valid entries and undated results. Per-source report and explicit clipboard action expose only fixed source labels/counts; no telemetry. Block duplicate refresh during initial load. Preserve cached items on failed refresh.
- `EditorialFeedIndexPolicy.kt` retains the eligible relevance mapping built on Dispatchers.Default, passed to Today/Explore/Search cards, avoiding repeated classification on the UI path. This is a source-level workload reduction; no device speed/frame claim.
- `PrimaryEvidencePolicy.kt` and `PrimaryEvidenceButton.kt` add an exact publisher URL-linked, editorially checked claim and official source link to native Reader chrome. No WebView allowlist expansion or runtime scraping; unknown stories never receive a verified label. `MainActivity.kt` additionally shows publisher/date in the excerpt fallback. Two official documents opened and manually checked 24 November: PIB 2334972 (Cabinet Rabi subsidy) and PIB 2333654 (NHAI guidelines); exact article URLs documented in editorial register. Scope is individual claims only, not full article verification.
- `UpscEngine.kt` and `UpscFeedPolicy.kt` add narrow tolling/climate diplomacy phrases. `PublisherEditorialSampleTest.kt` uses three public publisher headlines with pre-labelled themes. Added tests for feed status, no article URL in copied reports, evidence identity boundaries, shared relevance index. Version 0.3.9 / code 20; signing unchanged. Candidate tests and device checks not yet run.
- Live feed audit: direct git/curl network unavailable in this container (DNS), browser open of all 14 RSS endpoints failed to expose parseable XML (unsupported MIME types, restricted URL or 403 from browser). These are tool limitations, not proof of publisher downtime. No representative RSS sample or measured corpus accuracy available. Next add safe live-feed check in CI or on-device, portable local data, then address runtime acceptance.

## 24 November 2026 — verified intermediate release 0.3.8

- Exact source `cd457b25e05d8f0289a9948ee364ea7cae136d50`: [run #430](https://github.com/Batmancpu/CurrentLens/actions/runs/45374496213) completed successfully, both build and publish jobs. WordNet tests/bundling, JVM tests and release assembly, package/version and signing verification passed. [android-19](https://github.com/Batmancpu/CurrentLens/releases/tag/android-19) was published from that exact source; one APK, 30,847,326 bytes. SHA-256 `56285460833cb7b296a59acb4557d32a06074346f12f1acfc2d76605ae9b5d10`, same pinned certificate `af046601fe3a36851e645908e759abbf2caedfe748e2be02817991444884c580`. GitHub asset digest matches notes. [APK](https://github.com/Batmancpu/CurrentLens/releases/download/android-19/CurrentLens.apk).
- This verifies the first new code batch, including regression tests for the eligibility budget and local Review policy. UI, note round-trip on a handset, accessibility and performance remain unverified.
- The owner's newer whole-roadmap request supersedes earlier stop-after-one-release snapshots. Continue with the prepared 0.3.9 batch on main, only after reconciling the prior run. No failed build or signing steps.

- Additional 0.3.9 change: add `scripts/check_rss_feeds.py` and `scripts/test_check_rss_feeds.py` and one non-blocking, two-minute live publisher smoke step in the existing build workflow for explicit publication runs only. It reads configured source URLs directly from Kotlin, validates HTTPS publisher redirects and bounded XML size, counts raw entries and reports errors without logging article URLs. It is server-side availability evidence, not app parser or handset proof. The smoke step does not receive signing secrets and cannot block publication solely due to publisher availability. Test results pending.
