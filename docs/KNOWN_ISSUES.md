# CurrentLens — Known Issues and Verification Register

Status vocabulary:
- Verified on main: directly present/reproduced in current main code or CI evidence.
- User-reported: user observed it; durable reproduction evidence may be absent.
- Draft PR: proposed on branch, not shipped.
- Unverified: insufficient device/test evidence.
- Resolved in code: code path changed; runtime/user outcome is not automatically proven.

Snapshot main: 73b8a0d1ba33503fede02db2c3c0af082d724a12 (6 Nov 2026).

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
- PR #3 describes supplied Android test device screenshots, but original image/video bytes are not stored in this repo snapshot.
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
- Same user previously reported a cold-start failure on the Android test device around Build 100. Do not assume the old and new events have identical causes until log evidence matches.
- Main's prior handler was installed only in MainActivity.onCreate(). PrismalAGSL v1.0.4 registers an AndroidX Startup InitializationProvider through its merged manifest, which is initialised before Activity creation. Prismal's initializer itself only binds the asset manager; this identifies an instrumentation blind spot, not proof that Prismal is the crashing component. Android references: https://developer.android.com/topic/libraries/app-startup and https://developer.android.com/reference/androidx/startup/InitializationProvider.
- PR #16: https://github.com/Batmancpu/CurrentLens/pull/16 (draft, unmerged; branch head b66315c2f82f7785e7dfd96577d83d203770faad).
- PR #16 installs the local handler from Application.attachBaseContext(), records startup checkpoints, writes detailed exception reports and falls back to a plain Android View recovery screen. It can also record a clearly labelled startup interruption without a managed stack trace. Diagnostic setup/checkpoint I/O is best-effort. All diagnostics setup/checkpoint I/O is best-effort and must not itself abort Application startup.
- No current Android test device logcat/tombstone/ANR/local crash report is available. Do not claim fixed from source review or CI.
- After review/merge and installation, retry launch and copy the native recovery report. If no report appears, obtain Android bugreport/logcat/tombstone/ANR evidence; native/system kills may not invoke a Java uncaught-exception handler.


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
- Status: **Resolved in code on draft PR #16; native-crash causality remains unverified without another device trace.**
- Source review found that `MainActivity.BrrrNewsApp` applied `.prismalGlassLayer(backdrop)` to the complete content `Column`, while descendant surfaces such as the home header, Explore/Search glass controls and reader/settings glass sampled that same `backdrop`.
- PrismalAGSL v1.0.6 specifies capture for background content with glass drawn above it. Its modifier draws the child subtree and then records that subtree into the backing GraphicsLayer. Descendant glass surfaces reading the same layer create a self-referential capture/sampling path, a strong explanation for a failure that begins only when the full UI is composed and rendered.
- Correction: move the captured layer to a background-only sibling and keep all app content/glass surfaces outside the captured subtree. Add subtle ambient background gradients so the full glass effects still have a non-flat source.
- Preserve the original automatic Prismal pipeline and all blur, refraction, chromatic, specular, depth and spring settings. The separate tab-glyph capture/merged droplet source remains intact.
- The Stability build is advanced to 0.2.7 / versionCode 9 so it can be installed as an update to 0.2.6 with the same test signing key.
- No new user test was requested or run. CI result is recorded separately; the native signal-11 causal link is still a strong diagnosis, not device-proven fact. No raw trace, device identifier or user media is committed.


### KL-019 — Opaque Android process-exit description in copyable diagnostics
- Status: Evidence-backed privacy defect; focused correction added to draft PR #16; natural CI verification pending.
- The full-app recovery screen exposes a selectable/copyable startup report. The previous describeExitInfo() copied ApplicationExitInfo.description directly and also included importance plus PSS/RSS. The free-form platform description is not an allow-listed field and may contain low-level system details; the UI's promise that the report omits raw system traces was therefore stronger than the implementation could guarantee.
- The PR #16 correction exports only the numeric exit reason and numeric status/signal. It no longer forwards the opaque description, importance or memory measurements. Java/Kotlin reports continue to omit exception messages and raw addresses/traces.
- Added focused JVM assertions for the exact allow-listed summary and the interrupted-startup report. Tests are added, not yet executed for this new commit.
- This change improves report minimisation only. It does not establish the native launch crash's root cause or prove the app launches on a physical device.


### KL-020 — Quiz option selected-semantics receiver shadowing

- **Status:** Resolved in code and natural CI on `black-box-bihar-news@6f9b3c1e1a484233721863d0b736734c0d282cd5`; Android/TalkBack runtime remains unverified.
- The natural push build on pre-fix SHA `947310a157e03df25dde3856d2686d18ffca68e8` failed at `MainActivity.kt:4238:61` because `.semantics { selected = chosen }` resolved to the local `Map<Int, Int>` answer state rather than the semantics receiver's Boolean property.
- Correction: `.semantics { this.selected = chosen }`. The change is one line, preserves the intended selected-state semantics and does not alter quiz answer/scoring/submission persistence.
- PR #28's direct child commit was fast-forwarded into the active integration branch. Natural run #209 passed on the exact code head in PR context; natural active-branch run #210 passed the same exact SHA with `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload.
- This is a compile defect, not evidence of a device crash. TalkBack selected-state behaviour, font scaling and physical-device layout still require a real-device check.


### KL-021 — Unbounded local diagnostic-file reads during startup

- **Status:** Code-evidenced cold-start memory risk; bounded-read correction proposed in draft PR [#35](https://github.com/Batmancpu/CurrentLens/pull/35). No measured launch-time impact or crash causality is claimed.
- `CurrentLensApplication.attachBaseContext()` calls `LaunchCrashDiagnostics.beginStartup()` before AndroidX Startup providers. That path previously used unbounded `readText()` for the previous stage and existing crash report. Older pre-privacy diagnostic formats are known to have included Base64 system traces and device/build metadata, so a large legacy/corrupted report could be fully materialised on the startup main thread before cleanup.
- The recovery-screen `read()` path also read the entire report before applying `take(MAX_REPORT_CHARS)`.
- PR #35 adds a bounded-prefix reader, limits stage reads to 512 characters and report reads/display to 30,000 characters, and discards oversized diagnostic reports by file length without loading them.
- Focused JVM tests cover prefix length, zero-length reads, oversized-report rejection, legacy metadata cleanup and retention of ordinary reports. Tests are added; natural PR CI is pending.
- The change does not move the ActivityManager historical-exit query or all diagnostics off the startup path. It does not establish the native launch crash's root cause or prove device stability. OPPO cold/warm launch and Perfetto evidence remain unavailable.
