# CurrentLens — Development and Conversation Ledger

This is the evolving memory of the project across ChatGPT conversations. It records what happened, what was intended, what failed, why it failed, how it was corrected, what not to repeat and what remains uncertain. Append rather than silently rewriting history.

## Why this memory exists

The user clarified that they are not transferring the project to another AI product or external developer. They are continuing CurrentLens in new ChatGPT conversations when an earlier chat reaches its context limit. The next chat should resume without the user manually repeating the vision, visual feedback, prior failures, rejected options, pending work or workflow.

The user explicitly wants:
- Every material change and commit recorded.
- Failed attempts and exact causes/fixes retained.
- Approaches that did not work never repeated.
- Screenshots/photos/recordings and exact visual feedback captured where actually available.
- Build method, decisions, product goals, pending work and rejected ideas.
- README kept basic/public, not turned into the memory store.
- Detail preserved without fabricating unavailable image/log evidence.

## Product requirements and conversation feedback

### Editorial/product requirements
- Strict UPSC CSE product, not general news aggregator.
- Suppress celebrity gossip, film/TV promotion, box office, reality show drama, routine sports scores, generic viral content.
- Require defensible GS paper + specific official syllabus theme + explainable reason.
- Official syllabus plus latest available official Prelims/Mains PYQs as baseline.
- Positive, false-positive and false-negative fixtures.
- Keyword match is not validated score, probability or question prediction.
- Publisher RSS is not primary corroboration. Prefer PIB, Gazette, ministries, Parliament, PRS, RBI, Supreme Court, statutory/regulatory bodies.
- Four tabs only: Today, Explore, Search, You. Saved in You. No Archive.
- Offline WordNet, local-first, Asia/Kolkata, foreground-only reading time, quiz persist on submit.
- No accounts, cloud AI, analytics, telemetry, tracking, paid APIs, runtime dictionary networking; network only RSS/public accessible publisher pages. No paywall bypass/cookies/proxies/fallback JS/DOM storage.

### Reliability/performance/user reports
- Build100 passed CI but app reportedly crashed immediately on the Android test device. User explicitly required crash investigation first.
- PR #7 and #9 added code-level defensive startup/performance work and local crash diagnostics. Neither establishes physical runtime root cause.
- Search field reportedly far above keyboard, leaving awkward gap; Search bar rise/fall/morph animation reported stuttery.
- Avoid expensive per-cell blur, duplicate decode and frequent GPU readbacks. Use trace-backed fixes.
- User wants Apple-quality visual polish but no speculative churn and no smoothness trade-offs.

### User’s design feedback/reference images
- PR #3 body documents supplied Android test device screenshot defects: flat headers; Explore filter chips visually separate; active Today lens blurring selected icon; oversized dark inset image frame; keyboard/system-bar backdrop; empty loading state.
- Later Search/IME complaint: search field too far above keyboard; stuttery movement/morph.
- Launcher icon latest feedback: prior output looked too AI-generated; Liquid Glass design, central newspaper/news-sheet symbol in rounded square/circle-like tile, recognizable and name-independent.
- Exact earlier screenshots/video bytes are not preserved in repo; only text descriptions from PR/user context. Do not invent exact pixel/colour details. Future image attachments need an evidence record; do not place private screenshots in public repo without approval.

## Product and technical phases

### A. Project bootstrap / early build (3 Nov)
- Initial CurrentLens Android MVP and one APK workflow.
- Early workflow repeatedly failed on Android SDK packages and dependency coordinates.
- Implemented four-tab Liquid Glass interface and public article reader.
- Early failed dependency coordinate PrismalAGSL:Prismal was corrected through published Maven/JitPack coordinates. Current requirement is PrismalAGSL v1.0.4 and MIT notice.
- Build config iterated through SDK platform availability; workflow now uses API37 platform/build-tools and Gradle9.5.
- Fixes for reader HTML escaping/CSS string construction, selection JSON, glass-tab API, duplicate settings screen.

### B. Navigation/Search/Archive evolution (3 Nov)
- Some earlier commit briefly restored Archive and moved Search into Explore.
- Later commit replaced Archive with morphing Search and four-tab navigation.
- Final product rule: exactly Today/Explore/Search/You; no Archive; Saved inside You.
- Native text selection, reader progress/callbacks and Search morphing evolved through iterative fixes.

### C. RSS/reader/security/stats (3 Nov)
- Bounded RSS/dictionary network response sizes.
- Public reader content cache; quiz provenance labels.
- Reader navigation limited to verified publisher hosts.
- Short-article completion and per-article progress reset fixes.
- You dashboard/study stats.
- Reading sessions count only confirmed foreground reading.
- Quiz answers persist only after submit and legacy read URL markers retained.
- RSS feed provenance and India-local date parsing; explicit RFC1123 offsets preserved.
- Reader HTML sanitization and JavaScript scoped to sanitised articles.
- Do not weaken public-host allow-list, sanitization, response limits or paywall restrictions.

### D. Offline dictionary/selection/popovers (3–5 Nov)
- Replaced Android selection menu with in-app dictionary trigger; later preserved/restored native WebView selection and PROCESS_TEXT behaviour.
- Offline WordNet DB and reusable DB connection.
- Definition overlay anchored to selected word and constrained to reader viewport.
- Fixed punctuation/Unicode/multi-word selection, anchor coordinates and touch cancellation.
- Added bounded LRU cache, POS display, compact-first expandable definitions and WordNet exception-lemma lookup.
- Fixed stale auto-define on new touch/interrupted gesture/multi-touch.
- Improved short-reader completion stability and wide mobile article tables.
- CMUdict US pronunciation support was added and later removed entirely (032cb41f7ca5). Do not restore unless asked.
- Preserve WordNet attribution and licences.

### E. Search/IME/adaptive glass (4–5 Nov)
- Search morphs from bottom tab above keyboard.
- Fixed Compose keyboard APIs, synchronized morph with IME, observed IME insets inside Compose, measured Search anchor fallback/capsule geometry.
- Saved reader position/resume.
- Glass materials/motion and toolbar-band sampling refined.
- Despite earlier geometry work, user later reported field gap/stutter still present. Must remeasure actual device.
- Do not add multiple samplers or per-cell GPU readbacks.

### F. Editorial classifier hardening (5–6 Nov)
- PR #1: whole-token/phrase matching to prevent RBI inside arbitrary, IPL inside multiple, actor inside factor; no section-based weak escape; specific syllabus required; narrow policy exceptions must be headline-grounded; public health/disaster themes; matched signals exposed.
- PR #2 corrected economy test expected four signals (RBI, repo rate, inflation, monetary).
- #96 passed; #97 failed stale expected count; #98 passed after test correction.
- PR #6 draft adds GS I Geography, Explore/Search category, rationale cards, accessibility and labelled tests. Not in main at this snapshot.
- Preserve policy exceptions for real institutional/governance action.

### G. Glass/navigation UI and startup reliability (6 Nov)
- PR #3: restrained Prismal toolbar/Explore filters, icon-only bottom bar, crisp selected glyph above lens, Search geometry, removed second 30dp blurred image per card, one Coil image + one-time 9-point palette sampling, radial wash, squircle cards, static skeletons, off-main cache work and transparent Search/IME surfaces.
- Build #99 failed unresolved CalendarMonth and AccountCircle icon imports; #5 fixed; #100 passed.
- User reported Build100 immediate Android test device launch crash. CI success did not mean device runtime works.
- PR #7 moved startup restoration off main, cached Today filters, incrementally updated bookmarks/read state, reduced glass readback cadence, bounded image decode, palette guards, OEM system-bar defensive guard and icon revert; #101 passed.
- PR #9 added local-only crash diagnostics/recovery, off-main editorial index, removed recurring feed readback in favour theme-derived contrast, memoized filters, hardened preferences, Search/nav polish; #102 failed later due draw-scope.
- PR #10 fixed Search draw-scope error; #103 passed.
- No Android test device runtime verification. User-reported crash remains open.

### H. brrrNEWS branding/icon (6 Nov)
- Added SVG, Android vector foreground, adaptive/round icons, background resource, app label/copy/theme changes.
- Initially brrrNEWS; follow-up commits changed visible capitalization and SVG aria-label to brrrNews.
- Builds #104 and #112 passed, intervening #105–#111 cancelled, #113–#115 cancelled, #116 passed.
- User's exact earlier branding preference brrrNEWS versus main brrrNews remains to confirm.
- Icon user intent and vector geometry in DESIGN_EVIDENCE.md.

## PR history and status

| PR | Status | Summary |
|---|---|---|
| #1 | Merged | Whole-token strict editorial matching, explicit syllabus signals, policy exceptions, public health/disaster, matched signals. |
| #2 | Merged | Fixed stale economy test expectation (4 signals). |
| #3 | Merged | Liquid Glass header/Explore, clear nav glyph, image palette wash, no duplicate 30dp blur, squircle cards, skeletons, transparent IME, off-main cache work. |
| #4 | Closed unmerged | Earlier duplicate icon import follow-up. |
| #5 | Merged | Compose navigation imports fixing #99. |
| #6 | Open draft | Geography/editorial/card rationale/accessibility, diverged 13 behind. |
| #7 | Merged | Startup defensive/off-main restoration and reduced rendering workload; not device-proven. |
| #8 | Open draft | Off-main session/quiz/position/read-marker persistence; diverged 13 behind. |
| #9 | Merged | Local crash diagnostics, off-main editorial index, theme-derived contrast, preferences and UI polish. |
| #10 | Merged | Search glass draw-scope compile fix. |
| #11 | Open draft | Date precedence/cache retention; diverged 13 behind. |
| #12 | Open draft | Atom alternate link parsing; diverged 13 behind. |
| #13 | Open draft | Adaptive glass contrast restoration; diverged 13 behind. |
| #14 | Open draft | ChatGPT continuity memory docs; being rebuilt on latest main, README deliberately excluded. |

Open PRs #6/#8/#11/#12/#13 all base on old ba06a8f main snapshot and diverged from latest main (13 commits behind). Do not merge blindly.

## Main commit history

Every commit subject/SHA/timestamp is in docs/MAIN_COMMIT_INDEX.md. File-level summaries for selected high-impact changes whose diffs were inspected are in docs/COMMIT_DETAILS.md; do not infer full intent for other commits from subject alone. (119 items captured from two GitHub API pages). Open diffs for exact file-by-file detail. This ledger groups commit phases; the index is canonical for chronology/SHA.

## CI failure chain and do-not-repeat list

- #1: sdkmanager couldn't find package tools. Do not re-add obsolete tools pkg.
- #5: couldn't find platform android-37. Verify runner SDK packages.
- #8: couldn't resolve PrismalAGSL:Prismal. Do not reuse this coordinate.
- #38: FeedDateParserTest.preservesExplicitRfc1123Offset failed. Preserve explicit RFC1123 offsets.
- #97: test expected 3 economy signals but implementation produced 4; PR #2 fixed.
- #99: unresolved Compose imports CalendarMonth and AccountCircle; PR #5 fixed.
- #102: theme local called inside non-composable draw callback; PR #10 captured it in composable scope.
- #10/#18/#53 compile failure exact diagnostics not preserved in excerpt; root cause not established.
- Other failed run numbers and exact evidence in BUILD_AND_DEBUG.md.
- Don't assume CI success = Android test device crash fixed.
- Don't reintroduce substring match; section-based relevance shortcut; Archive; quiz persistence before submit; background reading-time count; duplicate blurred image draw; per-cell blur; high-frequency GPU readback; CMUdict pronunciation without request; unsafe reader JS or paywall workarounds.

## Continuity update protocol

At end of each material session:
1. Update PROJECT_HANDOFF with current main/PR/CI/issues.
2. Append detailed session entry with request, files, before/after, commit/PR, failed attempts and fixes, tests, CI/device evidence, risks.
3. Update KNOWN_ISSUES and DESIGN_EVIDENCE.
4. Update MAIN_COMMIT_INDEX after new main commit(s).
5. Update BUILD_AND_DEBUG after CI build failure/success.
6. Add durable rules to DECISION_LOG.
7. Keep README out unless specifically requested.


## 2026-11-06 — Build same-user ChatGPT continuity memory (PR #14)

### User intent
The user clarified that they are not replacing the development owner or handing CurrentLens to another AI product. They switch ChatGPT conversations when a previous conversation reaches its context limit. The next conversation should resume with the complete history and not ask the user to restate it. They explicitly asked for extreme detail: original intent, every material change, commits, failures, exact causes/fixes, rejected approaches, what not to repeat, visual feedback and submitted image/photo details. README must remain a basic public app overview and was explicitly excluded from this work.

### Implementation
Created a durable, multi-document memory system:
- AGENTS.md — continuity rulebook, mandatory reading order, no-main/no-merge/no-workflow-dispatch rules and mandatory per-change logging.
- docs/CHATGPT_CONTINUITY.md — explains one ChatGPT Project per ongoing project, moving the current chat into it, adding files and instructions, and limitations of project-file sync.
- docs/PROJECT_HANDOFF.md — dated live state, product, source architecture, current branch/PR/build state and priorities.
- docs/SESSION_LEDGER.md — chronological product/feature phases, prior PRs, failure/fix history and this documentation session.
- docs/MAIN_COMMIT_INDEX.md — all 119 main commits from GitHub's paginated API with UTC timestamp, full SHA link and subject.
- docs/COMMIT_DETAILS.md — inspected diff-level summaries for major implementation milestones; unexpanded commits link to their canonical diffs.
- docs/KNOWN_ISSUES.md — separates user-reported, verified, draft-PR and unverified defects.
- docs/DESIGN_EVIDENCE.md — visual intent, image/icon source asset details, screenshot descriptions, naming discrepancy and future evidence protocol.
- docs/BUILD_AND_DEBUG.md — current toolchain/workflow, Run #116 details, exact known failure patterns and what remains unverified.
- docs/DECISION_LOG.md — durable product/engineering decisions and continuity decision.

### Exact commits on the PR branch
1. c2603bfa34ba00874c64c863e47710273120bb97 — initial nine continuity files; parent current main 73b8a0d1ba33503fede02db2c3c0af082d724a12.
2. b30b1f75f87f0f7e779f4d1feaf8d5f1e8c0af6c — add docs/COMMIT_DETAILS.md.
3. f819b3d839feb32c1d96fe16619835645e8e8602 — add COMMIT_DETAILS to mandatory reading order and Project/handoff instructions.

### PR #14 state before the current continuity refresh
- PR #14: https://github.com/Batmancpu/CurrentLens/pull/14
- Draft/open; branch docs/ai-agent-handoff-2026-11-06.
- Base main 73b8a0d1ba33503fede02db2c3c0af082d724a12.
- Previous branch head b549b9c4f02a154d39fdb0ef807380a58a52b797; compare was 5 commits ahead / 0 behind, 10 docs-only files, 1,258 additions, 0 deletions.
- Current refresh adds crash-session details and updates all relevant status registers. Check live PR #14 for its newest head/commit count after this journal commit.
- README not in diff. No application source, assets, permissions, dependencies or workflow config changed.
- Main commit index still contains 119 rows. PR diff path list checked previously; no README file.
- No PR CI run is available/expected because the single workflow triggers only on push to main. No build was dispatched/rerun and no release was published.
- PR was left draft and unmerged. A merge will run the current main push workflow and publish a debug APK prerelease.

### Limitations and next action
- Original historical screenshots/video bytes are not in the repository; the docs preserve text observations from PR #3 and the recent icon discussion, but do not invent exact pixels.
- Future image assets should be stored in the private CurrentLens ChatGPT Project or otherwise privately, with metadata in DESIGN_EVIDENCE.md. Do not upload personal screenshots into public GitHub without explicit approval.
- The user must create/use a ChatGPT Project named CurrentLens, move this conversation into it if the Move to project option is available, add the ten docs as Project sources, and start future chats inside that Project. Project files should be refreshed after material repository updates.
- README intentionally untouched.


## 2026-11-06 — Investigate launcher-startup crash and add early diagnostics (PR #16)

### User report
The user reports the latest app still behaves as follows: tap launcher icon → Android shows opening animation → no app UI is visible → Android returns to launcher and the app appears closed. This matches the earlier Build 100 cold-start report on the Android test device, but there is no fresh crash report/logcat/tombstone in this conversation.

### Repository inspection and findings
- Inspected current main manifest, MainActivity startup/first composition, LaunchCrashDiagnostics, LocalStore, NewsRepository, DictionaryActivity/WordNet database session, build Gradle settings, workflow and project source inventory.
- Main has no custom Application class; launcher activity is MainActivity.
- Main LaunchCrashDiagnostics.install() was called inside MainActivity.onCreate(), too late for failures during ContentProvider initialisation.
- The app depends on PrismalAGSL v1.0.4. Its merged manifest includes androidx.startup.InitializationProvider and metadata for PrismalAgslInitializer. AndroidX Startup runs provider initializers at application startup before Activity creation. Prismal's v1.0.4 initializer itself only binds the asset manager when API >=33; this does not implicate Prismal as the cause. It does mean a managed exception in any earlier provider startup could precede the old activity-level handler. Official references: https://developer.android.com/topic/libraries/app-startup and https://developer.android.com/reference/androidx/startup/InitializationProvider.
- The previous recovery UI was implemented in Compose. That is not a robust recovery surface if the failure involves Compose or Prismal drawing.
- Main CI Build #116 succeeded but only establishes that the earlier main code compiled and its JVM tests ran; it does not establish that the Android test device device launches.
- No device-level evidence identifies a particular code line as the root cause. The exact crash remains unverified; do not present the instrumentation change as a guaranteed app fix.

### PR #16 implementation
PR: https://github.com/Batmancpu/CurrentLens/pull/16
Branch: fix/early-startup-crash-diagnostics-2026-11-06
Head: b66315c2f82f7785e7dfd96577d83d203770faad
Base: main 73b8a0d1ba33503fede02db2c3c0af082d724a12
Status: open draft; seven commits ahead, zero behind; five files changed.

Seven PR commits:
1. e81939b32dca807abb59a987e33c739751550e30 — early Application handler, crash report and native recovery implementation.
2. 356515458e9b689ac152a0f202113dfac324c365 — make early crash report formatting test-safe.
3. d70caac2dd0f2247667df12226c2ff3cc845222b — detect a startup interruption even without a Java exception stack.
4. e865c60473a19809a71d916bc7792bfd6861537a — avoid Android process API calls from pure report-formatting tests.
5. d5e0bd8c42d1cec6f4707ef550340162493acc7c — make synthetic startup-diagnostic writing best-effort so diagnostics do not abort launch.
6. 28a6950fe9e1c0e53f48deacc92cbcd7e89dc4e8 — correct interruption-report test assertion to match exact report wording.
7. b66315c2f82f7785e7dfd96577d83d203770faad — keep Application diagnostic installation and startup checkpoint handling best-effort so diagnostics I/O cannot block launch.

Files and effects:
- app/src/main/AndroidManifest.xml — registers .CurrentLensApplication.
- app/src/main/java/com/mangoloads/currentlens/CurrentLensApplication.kt — installs diagnostics in attachBaseContext, before AndroidX Startup providers; records Application lifecycle stage.
- app/src/main/java/com/mangoloads/currentlens/LaunchCrashDiagnostics.kt — writes local exception reports via temp file + fd.sync + rename/fallback; captures app/device/build/Android/PID/thread/last stage; stores startup stage markers; if a previous process ended before first three Compose frame callbacks and no exception report exists, creates an explicitly labelled interruption report (not a fabricated Java stack trace); clears the marker only after the first three frame callbacks.
- app/src/main/java/com/mangoloads/currentlens/MainActivity.kt — reads prior report and shows native Android Views recovery screen with selectable text/copy action rather than re-entering Compose; logs Activity/Compose checkpoints; marks launch healthy after three frame callbacks.
- app/src/test/java/com/mangoloads/currentlens/LaunchCrashDiagnosticsTest.kt — tests stack report detail, interrupted-start semantics, and size bound.

### Verification / limitations
- All five files were fetched and source-reviewed on the PR branch after the final commit.
- GitHub compare against current main reported 7 commits ahead, 0 behind; only those five paths changed.
- Tests have been added but not executed. No local Gradle build was run. The final PR commit also made diagnostic setup/checkpoint I/O best-effort so a diagnostics failure cannot abort Application startup.
- Existing workflow triggers only on push to main; it has no pull_request or workflow_dispatch trigger. No build was dispatched/rerun, no APK produced, no Release created.
- No physical Android test device test is possible from this environment. PR #16 was left draft and unmerged.
- This change primarily fixes a gap in crash evidence collection/recovery. It does not prove that Prismal, Compose or another component is the crash's underlying cause.

### Required next device validation
After PR #16 has been reviewed/merged and its new debug APK installed:
1. Tap the app icon and observe cold launch.
2. If it returns to launcher, open it once more.
3. If the recovery screen appears, copy the full local report and paste it into the CurrentLens chat.
4. If there is still no report/recovery screen, obtain Android bugreport/logcat/tombstone/ANR evidence. A native/system kill may not produce a Java/Kotlin uncaught-exception report.


## 6 November 2026 — repeated native launch crash, restore Liquid Glass, isolate first app screen

### User request and confirmed evidence
- The owner reported that Stability 0.2.3 / earlier Build #140 still crashed on launch and asked for a complete source review, restoration of expensive visual effects, fewer branches, and durable cross-agent documentation.
- The new local report again identified Android `CRASH_NATIVE` / signal 11. Its last recorded app checkpoint was `MainActivity.setContent returned`; no managed Java/Kotlin exception was reported.
- The partial native tombstone excerpt contained ART finalizer/monitor frames and the system's stack-pointer heuristic. This is not enough to establish the faulting thread or source-level root cause. It differs from the earlier Mali/HWUI/Skia signature from 0.2.2. Do not overstate either interpretation.
- No raw report, exact device metadata, build fingerprint, PID/TID, precise timestamp, memory address or Base64 trace has been committed to this public repository.

### Source changes on the existing PR #16 branch
Branch: `fix/early-startup-crash-diagnostics-2026-11-06`.

- `app/src/main/java/com/mangoloads/currentlens/CurrentLensApplication.kt`: removed the forced Prismal Legacy override. Normal builds now keep Prismal's automatic pipeline; the full Liquid Glass design is restored.
- `app/src/main/java/com/mangoloads/currentlens/MainActivity.kt`: only the Gradle Stability test build shows the plain launch probe initially. Normal builds go directly to `BrrrNewsApp`. The probe's button records a separate checkpoint before entering the complete app.
- `app/src/main/java/com/mangoloads/currentlens/StabilityLaunchProbe.kt`: added a small plain Compose/Material first screen that avoids Prismal backdrop capture and glass draw modifiers; after user action it enters the complete app with effects active.
- `app/src/main/java/com/mangoloads/currentlens/LaunchCrashDiagnostics.kt`: retained local diagnostics but removed device/build identifiers, process IDs, exact timestamps and raw native trace export from shareable reports; removed exception message/full source-location detail; strips PID/time prefix from visible startup stages; removes older detailed reports containing device identifiers on app upgrade.
- `app/src/test/java/com/mangoloads/currentlens/LaunchCrashDiagnosticsTest.kt`: tests privacy-minimised report content, startup-stage sanitisation and detection of legacy detailed reports.
- `app/build.gradle.kts`: Stability-only build flag and version 0.2.5 / versionCode 7.
- `.github/workflows/build-apk.yml`: labels the branch APK as v0.2.5; Release publication remains main-only.
- Removed obsolete `StartupRenderPolicy.kt` and its forced-Legacy unit test.
- Documentation from the continuity branch is imported in the same active PR, avoiding a separate docs-only branch/PR.

### Relevant commits before the documentation import
- `731d172da2630d2807f3cc6d82dd181bd1ed71a9` — Stability-only probe selection in MainActivity.
- `032fc5d3096e0d4d0940d84b75e96f7266ebc7fd` — restore Prismal automatic pipeline in Application.
- `7f8f3190042f70e332b03b4e6c5aac18bdee6d62` — version 0.2.5 and Stability BuildConfig flag.
- `6b2283cfde4cfa29b1c1381853c995112aba2289` — allow probe to mark its own stable first frames.
- `c8468fd00f43ac857117c0e63568d4dd16fe83e8` — correct probe button layout.
- `d3754dabcf760fc05fb1652adea0b7ebab046c54` / `3767fe1e47ff563e8ae3f97a3542db60f5613d56` — remove forced Legacy policy and test.
- `eb270def5b20a95a910ab5406dcb724d5577b42f` — privacy-safe recovery wording.
- The source branch head before this docs import was `3767fe1e47ff563e8ae3f97a3542db60f5613d56`.

### Verification and limits
- Actions Build #158, run `42239425526`, completed successfully. Dictionary-builder tests, WordNet generation, Gradle/JVM tests, APK compile and artifact upload passed. Release publication was skipped.
- Artifact: `CurrentLens-v0.2.5-stability-debug-apk`, ID `13513676549`, 14-day retention.
- The downloaded artifact was extracted and confirmed present in the work container. APK SHA-256: `e44b8341d53a0e2fd41925f4d621ed6e0be5b25747a7ed8fbb0ea094d518abae`.
- Physical device test has not yet happened. Build #158 does not prove the probe or complete Liquid Glass app survives cold launch. Do not state that the crash is fixed.
- Keep PR #16 draft/unmerged. Main branch remains untouched.


## 6 November 2026 — Stability 0.2.5 probe result and staged Prismal isolation

### Owner evidence
- User supplied a privacy-minimised 0.2.5 Stability report: `CRASH_NATIVE (5)`, signal 11, with last stage `Full brrrNews composition requested from stability probe`. The report excludes raw trace, device identifiers, process IDs and exact timestamps.
- User supplied a 21.36-second screen recording. Visual review shows the plain Compose/Material probe is displayed; after the full-app transition, the process returns to the Android launcher and the crash-recovery screen is seen again. The recording is private evidence and is not copied to the public repo.
- This supports the distinction “basic probe can render; full-app transition fails.” It does not prove whether the exact trigger is a Prismal layer, another Compose node, image decode, or another library/native finalizer.

### Source review
- The previous 0.2.3 Legacy override disabled AGSL and RenderEffect but still retained Prismal `GraphicsLayer` recording/sampling and `drawLayer`; failure under Legacy therefore did not exclude the backdrop-capture path.
- Reviewed PrismalAGSL v1.0.6 source for `PrismalGlassLayer`, its modifier, merged sources and draw modifier. The layer path uses `rememberGraphicsLayer`, `GraphicsLayer.record`, and `drawLayer`; the draw node creates/releases a GraphicsLayer and may set an offscreen compositing strategy when RenderEffect is supported.
- The ordinary production build and Stability full-app path remain on Prismal automatic pipeline with existing full visual effects. No visual downgrade is applied.

### Change in this session
- Extend the Stability-only probe to test one root GraphicsLayer capture, one full Prismal glass surface, a second hidden tab capture plus merged backdrop, then the complete app.
- Each stage writes an app-private coarse checkpoint synchronously before changing Compose state. Three stable frames mark the stage complete. The exported report continues to omit device/build identifiers, PIDs/TIDs, timestamps, raw traces and exception messages.
- Bump Stability-only build to 0.2.6 / versionCode 8. Production application ID/version behavior outside the Stability build is unchanged apart from the shared app version metadata.
- Update Actions concurrency to cancel obsolete builds only within the same Git ref; this avoids a burst of commits creating redundant builds while not cancelling a main-branch build because the Stability branch was pushed.

### Verification and limits
- CI must run on the single existing PR #16 branch after this batched commit. Record exact unit-test/WordNet/build/artifact outcomes when the run completes.
- No device launch or GPU verification is possible here. Do not claim the native crash is fixed; 0.2.6 is an isolation build.
- Preserve the current main SHA and do not merge or publish a Release.


## 6 November 2026 — One source-level diagnosis and Prismal capture correction

### Diagnosis
- The 0.2.5 Stability report and supplied recording show the plain Compose/Material probe can render, but the request to enter the complete app is followed by a native signal-11 process exit. Earlier partial GPU/HWUI/Skia frames point to the rendering path but do not identify a faulting source line.
- Reviewed the live `MainActivity.kt` and PrismalAGSL v1.0.6 implementation. The app attached `.prismalGlassLayer(backdrop)` to the entire main content `Column`; that subtree contains glass surfaces that also sample `backdrop`. Prismal's modifier draws the subtree and then captures it into the same layer. This is a self-referential capture/sampling dependency and violates Prismal's documented background-only usage pattern.
- This is the strongest concrete source-level defect found and is consistent with the full-app first-render crash. The partial tombstone does not prove it was the exact SIGSEGV instruction; preserve that uncertainty.

### Code change
- Commit `8c5f29d3a53669b3d810e4318d949caef4922f1a` — `fix: isolate Prismal backdrop from glass surfaces`.
- In `MainActivity.kt`, move `prismalGlassLayer(backdrop)` from the full content `Column` to a separate background-only full-screen sibling. That source contains only the theme background and two subtle radial ambient glows; all app content and all glass surfaces render above it and are excluded from the captured subtree.
- Keep the normal automatic Prismal pipeline and all current blur, refraction, chromatic aberration, specular, depth, adaptive tuning and spring effects. Keep the independent tab-glyph source/merged droplet path; this correction targets the more fundamental self-capture defect rather than weakening the navigation glass.
- Advance the Stability package to versionCode 9 / versionName `0.2.7-stability` for update installation using the existing Stability signing key; update workflow artifact/release labels to 0.2.7.
- No network, telemetry, permissions, raw crash data or user media were added.

### Verification and limits
- The owner declined another staged diagnostic test. No additional device test was requested or run.
- The PR branch CI build should verify WordNet generation, Gradle/JVM tests, APK compilation, signing and artifact upload. Those results must be reported independently.
- Do not claim the native crash is definitively fixed from code or CI alone. Runtime causality remains unverified; a later owner-initiated normal launch is sufficient to supply the next evidence if they choose.
- Work remains on existing draft PR #16. Main is not changed, no merge or Release is performed.

## 7 November 2026 — recording-led probe and UI corrections
- Reviewed the full 438.8-second user recording locally; no video or frame was copied to the public repository.
- At ~25 seconds, the recovery screen identifies CRASH_NATIVE (5) / signal 11 and last requested stage Probe stage 2 requested — single full Liquid Glass surface.
- This revises the earlier interpretation: the report is specific to the Stage 2 diagnostic probe. Later in the same recording the complete brrrNews app opens and the user navigates Today, Explore, Search, Reader, Appearance and You. Returning to launcher at the end is not proof of another crash.
- The version is treated as 0.2.7-stability based on the owner's confirmation that this was the latest GitHub APK.
- SingleLiquidGlassProbe captured a parent Column containing the test glass surface, while that surface sampled the same PrismalBackdrop. This recreated the self-referential GraphicsLayer capture/sampling architecture already removed from the main app. Stage 2 now captures a background-only sibling; the glass surface is drawn above/outside that source.
- Stability build now starts the complete app directly; the diagnostic menu no longer interrupts normal launch.
- NewsCard displays publisher publication date/time in Asia/Kolkata and labels configured RSS as secondary publisher reporting. Appearance adds Clear/Balanced/Frosted presets and a live glass preview. Reader hides the completion prompt while a dictionary definition overlay is open.
- Quiz wording states that prompts use RSS summary context and original skills practice; it does not claim full-text comprehension or official PYQs.
- Version advances to 0.2.8 / versionCode 10, retaining the Stability package suffix and signing identity.
- Added a JVM test for compact IST date labels. CI must be checked before sharing the next APK.
- No further user device-test round is requested. CI does not replace runtime evidence already present in the private recording. PR #16 remains draft/unmerged; main and Release state remain untouched.


## 7 November 2026 — minimise the copyable Android process-exit report
- Review source: draft PR #16, branch fix/early-startup-crash-diagnostics-2026-11-06, parent/source head d63c22d621a5e8c4c293d1c1b856e8afca478ead; main remains 73b8a0d1ba33503fede02db2c3c0af082d724a12.
- Evidence: MainActivity.showCrashRecoveryScreen() displays the local report in selectable text and provides a Copy action. LaunchCrashDiagnostics.describeExitInfo() previously passed the platform's free-form ApplicationExitInfo.description into that report and also exposed importance and PSS/RSS. The description is opaque and not constrained to the repository's privacy allow-list; the app therefore could not guarantee that copied reports omitted all low-level system details.
- Change: replace the free-form exit description with a pure, bounded formatter that emits only the Android exit-reason enum and numeric status/signal. Remove importance, PSS/RSS and the raw platform description from the shareable report. Existing local-only storage, no-upload behaviour, startup checkpoints and original Prismal rendering are unchanged.
- Tests: replace the string-injection test with exact assertions for CRASH_NATIVE (5) and status 11; assert that the report has no system-description, PSS/RSS or importance fields. A second test asserts the formatter's exact allow-listed output. Tests are added; the natural branch CI result is pending at commit time.
- Files changed: LaunchCrashDiagnostics.kt, LaunchCrashDiagnosticsTest.kt, KNOWN_ISSUES.md, SESSION_LEDGER.md, BUILD_AND_DEBUG.md and PROJECT_HANDOFF.md.
- Scope/limits: privacy minimisation only; no claim that the native startup crash is fixed. No physical-device run, raw crash trace, user media, workflow edit, manual Actions dispatch/rerun, main edit, merge or Release.


## 2026-11-07 11:38 IST — Appearance low-effects fallback and slider recomposition control

- **Repository / branch:** Batmancpu/CurrentLens, black-box-bihar-news. Verified parent HEAD cfe0b3e8319374df5cb29423f04a79c77d4fe10e; implementation commit SHA is assigned by GitHub after this entry is authored and must be added during the next ledger refresh.
- **Branch freshness:** complete inventory 28/28; black-box-bihar-news is 97 ahead / 0 behind main and 40 ahead / 0 behind PR #16. No source branch, main, PR base, Release or workflow was changed.
- **Purpose:** make the Appearance preference app-wide and accessible while reducing unnecessary whole-root recomposition during slider movement.
- **Changes:** added local reduceTransparency persistence (default false); introduced pure GlassRenderingPolicy; applied the reduced profile to Home header, Explore rail, Search filter capsule/morph, full/compact tab material, Reader toolbar/completion and definition overlay. Reduced mode removes live blur/refraction/chromatic aberration/depth/shadow, retains a lower specular edge, raises interactive tint opacity to at least 0.24 and pauses the existing 8×8 adaptive luminance probe. No second glass engine or new backdrop capture was introduced.
- **Appearance interaction:** slider drag updates only previewGlassAlpha in the settings screen; parent glassAlpha and SharedPreferences are updated once on drag completion or preset selection. This avoids writing/recomposing the shared root for each slider pixel. The low-effects Switch is labelled and retains a 56dp row.
- **Tests:** added GlassRenderingPolicyTest for normal/reduced optical parameters and extended GlassLuminanceActivationPolicyTest to prove reduced mode pauses sampling. Tests are authored but not yet run on the resulting commit.
- **Previous CI evidence:** run #200 passed on parent SHA cfe0b3e8319374df5cb29423f04a79c77d4fe10e, artifact 13560769755; it does not validate this batch. Await the natural workflow for the new exact head; no manual dispatch/rerun.
- **Risks / unverified:** no OPPO Reno10 5G runtime screenshot, launch, accessibility, Perfetto/frame-jank, GPU-overdraw, battery or thermal measurements. Do not claim measured performance improvement. The root background-only source remains shared; the existing tab glyph/merged source is unchanged.
- **Next:** verify natural CI on the new exact head; then review Saved/stat filter surfaces and Quiz/Settings contextual controls. Re-read live branch graph before any further code change.


## 2026-11-07 14:31–14:35 IST / 09:01–09:05 UTC — Quiz selected-state compile repair on the consolidation branch

- **Repository / active branch:** Batmancpu/CurrentLens, `black-box-bihar-news`. Pre-fix HEAD `947310a157e03df25dde3856d2686d18ffca68e8`; corrective source commit `6f9b3c1e1a484233721863d0b736734c0d282cd5`, direct child. Main remains `73b8a0d1ba33503fede02db2c3c0af082d724a12`; no main write.
- **Branch freshness:** 32/32 remote refs enumerated; page 2 empty. 14 open and 14 closed PRs. Active branch is 101 ahead / 0 behind main and 44 ahead / 0 behind PR #16. PR #27's head advanced to `9b70ed1c7c39bb511399c6284c7e8833f4672618` during this audit; it remains separate editorial work. Full table/disposition is in docs/BRANCH_CONSOLIDATION.md §11.
- **Failure evidence:** natural run #208 on `947310a` failed in `:app:compileDebugKotlin` at `MainActivity.kt:4238:61`: “Assignment type mismatch: actual type is 'Boolean', but 'Map<Int, Int>' was expected.” The failing QuizScreen option modifier used `.semantics { selected = chosen }` while the composable's local answer state is also named `selected: Map<Int, Int>`.
- **Root cause:** Kotlin name resolution selected the local answer map rather than the semantics receiver's Boolean property. The complete QuizScreen and its article-keyed questions/answers/submission state were inspected; no data or persistence change was needed.
- **Correction:** the exact one-line receiver qualification `.semantics { this.selected = chosen }` was taken from PR #28's direct child commit and fast-forwarded into the active branch with an expected-head lease (no force update). GitHub marked PR #28 merged/closed into black-box; its source branch remains. No other branch or PR was changed.
- **Behaviour:** selected-state accessibility semantics are restored; quiz answer selection, score calculation, question identity, submit-only persistence, and back/Done behaviour are unchanged.
- **Verification:** PR run #209 passed on exact code head `6f9b3c1`. Active branch push run #210 passed on the same exact SHA: four WordNet builder tests, 151,456 unique words / 50,950,144-byte database, `:app:testDebugUnitTest`, `:app:assembleDebug`, artifact upload. Artifact `13568511654`, 39,082,053 bytes, SHA-256 `9eeb4bbdbfd08f9f8f5e94a5eda0251b41d1c441933653c0ef326246669458de`; publish job skipped. Latest overall run #211 passed on PR #27 head `9b70ed1`, not on the active branch.
- **Warnings:** existing non-blocking WebView `scale`, Compose `updateTransition`, ArrowBack API, Node.js 20 action-runtime, `punycode` and `url.parse()` deprecation warnings remain. They were not changed in this focused repair.
- **Rejected scope expansion:** no further UI changes were added before verifying the compiler fix; no workflow, dependency, package, app identity, network, storage schema, main branch or Release change.
- **Risks / unverified:** TalkBack selected-state runtime, font scaling, OPPO Reno10 cold/warm launch, visual animation, Reduce Motion, Perfetto/frame timing, GPU overdraw, allocation/memory, thermal and battery were not measured. CI is not device verification.
- **Next:** re-read all branch heads/PR diffs and current Actions before another screen-sized Liquid Glass pass; review remaining transient dialogs/sheets/menus while preserving high-contrast reading/data surfaces.


## 2026-11-07 22:24 IST — Reader Aa menu pinning and completion collision guard

- **Repository / active line:** Batmancpu/CurrentLens, `black-box-bihar-news`; pre-change head `818afb47eaadc685f133eaffc3efd199392627e4`, direct parent `ec86b2fef2f5e86c3b95f2a2a2269bfef6158053`. Main remains `73b8a0d1ba33503fede02db2c3c0af082d724a12`; comparison is 109 ahead / 0 behind main and 52 ahead / 0 behind PR #16's corrected Prismal/stability head.
- **Freshness gate:** all 37 remote branches, 20 open PRs and 14 closed PRs were re-enumerated. PR #34 is the active consolidation draft; PR #29/#30/#31 changes are already represented. PR #27/#32/#33 remain independent; #25/#26 remain pending combined publisher-resource security review. No other branch is a safer/newer UI baseline.
- **Prior CI:** natural push run #242 and exact PR #34 run #243 both passed on parent SHA 818afb47; WordNet generation, JVM tests, APK assembly and artifact upload steps succeeded. Artifacts 13580578348 (39,094,947 bytes) and 13580718014 (39,094,949 bytes); publish jobs skipped. Raw job-log retrieval returned BlobNotFound; step summaries are the available evidence.
- **Change:** moved `textSizeMenuExpanded` state from `ReaderGlassToolbar` to `ReaderScreen`; `ReaderChromePolicy.shouldPinChrome` now includes an open Aa menu alongside definition, native selection ActionMode, IME and accessibility. The existing `ReaderWebView.setReaderChromePinned` path keeps the toolbar visible and cancels pending hide while the menu is open. Completion CTA is suppressed until the menu closes.
- **Behaviour preserved:** font-size adjustment/clamping, source/date provenance, article loading and saved reading position, native Copy/Select/share, dictionary selection bridge, publisher fallback restrictions and quiz submit-only persistence are unchanged.
- **Tests:** added pure JVM coverage for each pinning reason, Aa-menu pin/reveal, no-pin baseline and completion suppression. Tests are added but not run on the new commit yet.
- **Rendering/performance:** no new Prismal surface, capture, sampler/readback, blur/refraction, shader, graphics layer, image decode, dependency, timer, network request or persistence operation. Existing Aa dropdown and root background-only source are reused; completion surface is not composed while the menu is open. No measured GPU/frame/memory/battery improvement is claimed.
- **CI / device:** new commit awaits its natural push run; no manual dispatch/rerun. OPPO Reno10 launch, menu collision, TalkBack, Reduce Motion, font scaling, Perfetto/frame timing, GPU overdraw, allocations, memory, thermal and battery remain UNVERIFIED.
- **Next:** inspect the exact new-head CI run before further UI work; then review remaining transient controls. Keep PR #27/#32/#33 and #25/#26 on separate dependency/security reviews.


## 2026-11-08 05:24 IST — Shared Reduce Motion consistency pass

- **Repository / baseline:** `Batmancpu/CurrentLens`; active branch `black-box-bihar-news` at source head `d9a4d1eac035172da0f8d7c40a984eb6c40c1b8b`. Main remains `73b8a0d1ba33503fede02db2c3c0af082d724a12`; compare 121 ahead / 0 behind. PR #16's corrected background-only Prismal/stability head is an ancestor (64 ahead / 0 behind). PR #34 remains the sole open consolidation PR to main.
- **Freshness:** 40 remote branches (40 + 0 pages), 37 PRs (23 open, 14 closed) reviewed. PR #27 is newer by timestamp but editorial-only and diverged; PR #37's RowScope correction is already present in current SearchMorphField; PR #36's IST-midnight refresh is a separate diverged Today/index change; PR #35 diagnostics, #32/#33 NewsRepository and #25/#26 publisher security remain separate reviews. No source branch was modified.
- **Pre-change CI:** natural PR run #279 and push run #278 both passed on exact source SHA `d9a4d1eac035172da0f8d7c40a984eb6c40c1b8b`. Both full job logs were inspected. WordNet builder tests 4/4; 151,456 unique words generated; `:app:testDebugUnitTest` and `:app:assembleDebug` passed; APK artifact upload passed. PR artifact #13594284223 (39,107,398 bytes; SHA-256 `1959a089d31d4a96ee851749d5a3753b9b42e1a2283b381b50dd2462d2762803`); push artifact #13594681752 (39,107,393 bytes; SHA-256 `5fb4503525d5fa4f8a9b77213cc07f196345ae41cd7ae243ce3e746f4ad17000`). Both publish jobs skipped.
- **Actions / Release:** 279 runs fully paginated (100 + 100 + 79): 166 success, 61 failure, 52 cancelled; queue empty. Latest published Release remains `v0.2.0-build-116`; no Release created.
- **Audit:** Home's shared `AnimatedContent` destination fade did not consult the existing root Reduce Motion state. Reader chrome separately called `ValueAnimator.areAnimatorsEnabled()`, while DefinitionGlassOverlay always used spring scale and spring content-size animation. This produced inconsistent behaviour across otherwise unified transitions.
- **Implementation:** introduced `LocalGlassReduceMotion`, sourced from the existing lifecycle-updated root state. Home destination crossfade snaps when Reduce Motion is enabled; Reader chrome uses the same state and resolves its transition duration to zero; DefinitionGlassOverlay snaps scale and content-size, retaining a short 90ms opacity fade. Normal motion preserves the existing 190/120ms destination fade, 160ms Reader chrome and spring definition morph.
- **Interaction preservation:** no change to native Android Copy/Select/share ActionMode, dictionary selection bridge, anchor coordinate/placement policy, reader scroll-direction hysteresis, selection/IME/accessibility pinning, completion action or search focus.
- **Paired optimization:** no new live glass surface, backdrop capture/readback, blur/refraction, shader, graphics layer, bitmap decode, dependency, timer, network or persistence path. No per-pixel Compose state update or polling. This is a source-level cost assessment only.
- **Tests / status:** no dedicated Compose UI test was added for animation-spec wiring. The new exact head awaits its own natural push/PR CI; runs #278/#279 validate only the parent SHA. OPPO Reno10 Reduce Motion, visual, TalkBack, font-scale, selection/IME, frame-time, GPU, memory, thermal and battery checks remain UNVERIFIED.
- **Safety:** no main write, merge, retarget, workflow edit, manual dispatch/rerun, Release publication, source-branch mutation or deletion.


 
## 2026-11-08 06:23 IST / 00:53 UTC — Appearance preview control-state parity

- **Repository / branch:** Batmancpu/CurrentLens, `black-box-bihar-news`; selected parent `31e3bf753f91002b26b1e02e281899d10d0e590f`; base `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. The selected branch remains 122 ahead / 0 behind main and 65 ahead / 0 behind PR #16's corrected background-only Prismal baseline.
- **Freshness:** 40 branches and 37 PRs were re-enumerated. PR #37's RowScope repair is already present; PR #36 IST rollover remains a separate diverged MainActivity change; PR #30/#31/#29 policies are represented. No other branch was changed or merged.
- **Files:** `MainActivity.kt`, `GlassSurfacePolicy.kt`, `GlassSurfacePolicyTest.kt`, `docs/LIQUID_GLASS_UI_ROADMAP.md`, `docs/BRANCH_CONSOLIDATION.md`, and this ledger.
- **Behaviour:** the Appearance preview's mini Search surface, rim and selected navigation fill now interpolate with Clear↔Frosted intensity; Reduce Transparency raises their opacity/rim. The Search preview sample has a 48dp minimum height. The outer `PrismalTransientSurface` remains the only live-glass preview; slider drag stays local and commits to the app-wide preference only on release or preset selection.
- **Tests:** added pure JVM assertions for clear/frosted endpoints, monotonic intensity, reduced-transparency floors and non-finite fallback. Tests are added but await the next natural exact-head CI run.
- **Rendering/cost:** no new Prismal surface, capture/readback, blur/refraction, shader, graphics layer, image decode, dependency, timer, network or persistence operation. Only bounded scalar alpha calculations were added; no measured performance claim.
- **CI parent evidence:** exact parent SHA passed natural PR run #281 and push run #280. PR #281 full job log: WordNet builder 4/4, 151,456-word dictionary, `:app:testDebugUnitTest`, `:app:assembleDebug`, artifact upload; artifact `13596471410` (39,107,655 bytes). Push #280 also passed all build steps; artifact `13595943420` (39,107,647 bytes). The new commit requires its own natural run; no manual dispatch/rerun.
- **Unverified:** OPPO Reno10 appearance, launch, visual contrast, accessibility, Reduce Motion, IME, frame timing, GPU overdraw, memory, thermal and battery remain UNVERIFIED. No release, main write, merge or source-branch mutation.


## 2026-11-08 15:55 IST / 10:25 UTC — Extend RSS parser hardening for inline Atom content

- **Request / priority:** continue the strict UPSC CurrentLens quality review; preserve publisher provenance, avoid new network paths, and fix evidence-backed RSS false negatives without duplicating existing proposals.
- **Freshness gate:** canonical main remains `73b8a0d1ba33503fede02db2c3c0af082d724a12`. Active integration `black-box-bihar-news@90508dad6d8ce1327051f78eedf8273a1c5010cc`, PR #34 draft/open, 140 ahead / 0 behind main. Repository inventory: 44 branches, 27 open PRs, 14 closed PRs. Full Actions history paginated: 330 runs (199 success, 67 failure, 64 cancelled), queue/in-progress 0. Latest natural integration PR run #330 passed on exact head; WordNet builder 4/4, 151,456-word database, `:app:testDebugUnitTest`, `:app:assembleDebug`, artifact upload; release job skipped. Artifact 13612618168, 39,121,080 bytes, SHA-256 `e5556d4be8c6e760329f9919caad83af93f822611fa6b6be9762355f868e1f9a`. Latest Release by publication timestamp remains v0.2.0-build-116. Main workflow remains push-only / workflow-wide contents:write; integration branch has PR validation and isolated release-write.
- **Evidence:** main's `NewsRepository.parse()` and the existing PR #41 parser treated local-name `content` only as a URL-backed image candidate. A valid Atom entry may supply readable content in `atom:content` without a separate `summary`; inline text/html/xhtml was silently ignored. RFC 4287 defines Atom content constructs, including text, HTML and XHTML. This is a standards-based parser false-negative risk, not a claim about a currently measured live-feed item.
- **Change:** reused existing draft PR #41 / branch `perf/bound-rss-field-values-2026-11-08` rather than creating a duplicate. Added `FeedFieldPolicy.isInlineAtomTextContent`; parser now reads inline Atom content only when the namespace is Atom, the type is an accepted text/HTML/XHTML form (or default text), and neither external `src` nor URL-backed media is present. Media RSS URL content continues through the image path. External Atom `src` is not fetched, so no network capability is added. Existing field bounds and `FeedXmlTextReader` remain in use.
- **Tests added:** two policy tests cover accepted Atom inline types, external `src`, non-text media and Media RSS; one XML-reader fixture covers nested Atom XHTML content and parser position before the next sibling. Existing PR #41 tests also cover bounded field values, supplementary Unicode, Atom XHTML summary, plain text/entities, CDATA and parser cursor behaviour.
- **Files on PR #41:** `FeedFieldPolicy.kt`, `NewsRepository.kt`, `FeedFieldPolicyTest.kt`, `FeedXmlTextReaderTest.kt`. Code commit `e4ca4a522dd236052142aa31dc518979bfafe891`, parent `9acf330c8e6b3fa1cb215e754b093bb59e666059`. PR #41 title updated to “Bound RSS fields and preserve inline Atom text constructs”; base remains main; 8 commits, 6 files, draft/open/mergeable.
- **Verification:** exact-head commit status and check-runs both contain zero entries; tests are **added, not run**. Main workflow is push-only, and no local build environment was available (container clone failed because github.com DNS resolution was unavailable). No workflow dispatch/rerun. No PR merge or Release.
- **Risks / limits:** accepted inline MIME types are deliberately conservative; unknown/binary types and external Atom `src` are ignored. This does not make the parser streaming; the existing whole-document RSS byte ceiling remains. Relative Atom links, feed concurrency/entry limits and publisher-host validation remain separate PRs. No live-feed precision/recall or memory benchmark is claimed.
- **Other findings:** integration editorial gate still admits generic policy/action combinations and uses a global syllabus-signal list; PR #27 remains the existing unvalidated correction. PR #32/#38, #33, #25/#26, #35 and #40 remain separate reconciliation work.
- **Device / visual:** no fresh OPPO Reno10 logcat/tombstone/Perfetto. Cold-start root cause, UI screenshots, TalkBack, IME, font scale, Reduce Motion, frame timing, GPU, memory, thermal and battery remain unverified.
- **Safety:** no direct main edit, merge, workflow change, manual dispatch/rerun, Release publication or branch deletion.


## 2026-11-08 16:05 IST / 10:35 UTC — Constrain RSS feed redirects to publisher hosts (PR #40)

- **Evidence:** main and the current integration `NewsRepository.fetchOne()` opened configured HTTPS RSS endpoints with `HttpURLConnection` default redirect-following enabled. Only the initial feed URL was configured; automatic 3xx handling could issue a subsequent request to a foreign host before any article-link validation. This violated the intended network boundary even though the feed's item links were separately being validated in PR #40.
- **Dependency check:** PR #40 already owned publisher-host validation for RSS article destinations and changed `NewsRepository.kt` / `PublisherHostPolicy.kt`. No existing PR was found that validated the RSS endpoint's redirect chain. Extended PR #40 rather than opening a duplicate host-policy PR.
- **Implementation:** set `instanceFollowRedirects = false`; validate the configured initial feed URL and each redirect target with `PublisherHostPolicy` before opening the next connection; resolve relative `Location` references against the current URL; allow only 301/302/303/307/308; reject missing/oversized locations, unsupported statuses, cross-publisher/lookalike hosts, cleartext downgrade, credentials, non-standard ports and chains beyond five redirects. Added `bbci.co.uk` because the configured BBC feed host is `feeds.bbci.co.uk`. No new cookie, proxy, JavaScript, DOM-storage, external asset or fallback path was added.
- **Tests added:** four `PublisherRedirectPolicyTest` methods cover standard redirect statuses, relative/absolute same-publisher resolution, cross-host/lookalike/cleartext rejection and missing/oversized/exhausted chains. Extended `PublisherHostPolicyTest` for the configured BBC feed hostname. Tests are **added, not run**.
- **Files on PR #40:** `NewsRepository.kt`, `PublisherHostPolicy.kt`, new `PublisherRedirectPolicy.kt`, `PublisherHostPolicyTest.kt`, new `PublisherRedirectPolicyTest.kt`. Commits `73fcadd6c05aa314b8a7302d6f8bffbe9bd6d401` and `552b96e7948ac5ade821a711ea8dd225984824a9`; PR #40 head is `552b96e7948ac5ade821a711ea8dd225984824a9`, base main, 6 commits / 7 files, draft/open/mergeable.
- **Verification:** PR #40 exact-head commit has zero status entries and zero check-runs. Main's workflow is push-only; no local Gradle environment was available (container clone could not resolve github.com). No workflow dispatch/rerun. No merge or Release.
- **Integration conflict:** PR #41 independently changes `NewsRepository.kt` for bounded fields and Atom text constructs. Both PRs remain main-based; reconcile parser, redirect and article-link changes together before integration. PR #40 does not include PR #41's field/XHTML work.
- **CI / workflow audit:** the documentation-only commit on PR #34 produced natural runs #331 (push) and #332 (pull_request), both passed on exact PR #34 head `9efd103a0cd8a272bbce3c61140e40bfa6a8b707`. WordNet builder 4/4; 151,456 words; `:app:testDebugUnitTest`; `:app:assembleDebug`; artifact upload; release jobs skipped. Artifacts 13613817606 (39,121,079 bytes, SHA-256 `c7b46466803b5da8e78c58410f51c0a2db5110c11f7bae2d0983c336934daa00`) and 13613339920 (39,121,080 bytes, SHA-256 `4b1c580f4224a731a1523e2df5e477ab64efb6543e6f3bea3b5ba63b059e4245`). These runs do not validate PR #40 or #41.
- **Current priorities:** strict editorial action/syllabus gate PR #27; RSS redirect/article-host PR #40; bounded fields/Atom inline content PR #41; RSS concurrency/entry budgets #32/#38; Atom relative-link selection #33; image/WebView resource restrictions #25/#26; bounded startup diagnostic reads #35.
- **Device / visual:** no fresh OPPO Reno10 logcat/tombstone/Perfetto. Native launch causality, screenshots, TalkBack, IME, font scale, Reduce Motion, frame timing, GPU, memory, thermal and battery remain unverified.
- **Safety:** no main write, merge, workflow modification, manual dispatch/rerun, Release publication or branch deletion.


## 2026-11-08 19:28 IST / 2026-11-08 13:58 UTC — Saved Previously Opened eligibility classification reuse

- **Repository / source:** Batmancpu/CurrentLens; owner-selected branch `black-box-bihar-news`; pre-change parent `261c387bb196e1bb74d5a20df9b3d1bc4f9ebc18`. Base remains `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; PR #34 remains the single open/draft consolidation PR. The branch was 148 ahead / 0 behind main, 91 ahead / 0 behind PR #16 and 78 ahead / 0 behind the former PR #19 UI line.
- **Freshness:** complete inventory rechecked: 44 branches, 41 PRs (27 open, 14 closed). PR #37's Search RowScope repair, #36's Asia/Kolkata rollover, #30's 48dp target policy and #31's off-main Appearance restore are already represented. PR #39 signing/workflow migration remains gated on owner-managed secrets and data-migration approval; #40/#41 and #32/#33/#38 remain separate RSS/parser reviews; #25/#26 require joint publisher-resource security review. No other branch was changed.
- **Parent CI:** exact parent passed natural push #345 and PR #346. Full PR log: WordNet builder 4/4, 151,456 unique words (50,950,144 bytes), `:app:testDebugUnitTest`, `:app:assembleDebug`, artifact upload; BUILD SUCCESSFUL in 1m30s. Artifact #13618970693 (39,120,818 bytes; SHA-256 `26b04cf919118ba3c0fec84b8efb95affbd4e19763477454e9bfaf9f68b1e32a`). These runs do not validate the new head.
- **Files changed:** `MainActivity.kt` caches the exact relevance result for Previously Opened candidates and passes it to visible NewsCards; `UpscFeedPolicy.kt` adds `eligibleRelevance` while preserving the existing `shouldInclude(Article): Boolean` API and noise short-circuit; `EditorialConsolidationRegressionTest.kt` covers exactly-once classification and noise rejection before classifier invocation; roadmap, branch-consolidation ledger and project handoff record the audit and paired-cost rationale.
- **Before / after:** Previously Opened eligibility classified an article, then NewsCard classified the same article again for syllabus metadata. Now one relevance result per candidate drives eligibility and the visible card. Eligibility terms, ordering, 15-card cap, bookmark/read semantics, feed contents, image request/decode/palette and card appearance are unchanged.
- **Alternatives rejected:** no broad cross-feed relevance-index rewrite; no per-card glass/blur; no duplicated cache keyed by display metadata; no change to editorial rules or network/storage semantics. Keep the change isolated to the Saved Previously Opened section.
- **Tests:** added two JVM tests to `EditorialConsolidationRegressionTest`: the eligible path invokes the supplied classifier once and returns that exact result; editorial noise returns null without invoking the classifier. Tests are **added, not run** until the natural exact-head workflow completes.
- **CI:** natural push/PR runs on the new commit are pending; no dispatch/rerun. Parent #345/#346 are not evidence for the new commit. The branch workflow is read-only for build/test with isolated main-push Release write; publish jobs are skipped on branch/PR runs.
- **Performance/material:** no additional render pass, Prismal surface, backdrop capture/readback, blur/refraction, shader, graphics layer, image decode, dependency, timer, network request or persistence operation. One duplicate classifier invocation is removed per eligible Previously Opened card; this is source-level evidence only, not a measured frame/memory/battery win.
- **Device / risks:** OPPO Reno10 cold/warm launch, Saved visual layout, TalkBack, font scale, Reduce Motion, frame timing/jank, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. No device-backed launch or performance claim.
- **Safety:** no main write, PR merge/retarget, force update, workflow change, manual dispatch/rerun, Release publication, package identity change or source-branch deletion. The new commit SHA is in the GitHub branch history and will be recorded in the next ledger refresh.



## 2026-11-09 01:27 IST / 2026-11-08 19:57 UTC — Port off-main startup diagnostics into the active consolidation branch

- **Source:** `black-box-bihar-news@3173bd9a4af5483d134fda1d5b756a2f5657d03d`; PR #34 draft/open to main. Pre-change branch: 155 ahead / 0 behind main. Complete inventory: 46 branches, 43 PRs (29 open, 14 closed). The exact resulting commit SHA is recorded by GitHub/PR #34 and will be copied in the next ledger refresh.
- **Freshness:** PR #16 `ebea0aac68ce2cf17aad376304f7915fe937e807` is 7 ahead / 98 behind active from common base `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. Four Appearance changes are superseded by the active combined snapshot; three diagnostic changes were adapted. PR #43 `cb080e547ad4469dcf81c1f04e866e89c6f86b42` is 8 ahead / 155 behind; its remaining settle-callback/Exception-only guard change is deferred. PR #30 and #37 fixes are already present.
- **Files:** `CurrentLensApplication.kt`, `LaunchCrashDiagnostics.kt`, `LaunchCrashDiagnosticsTest.kt`, `MainActivity.kt`, roadmap, branch-consolidation record, project handoff and this ledger.
- **Behaviour:** Application inspection/exit-history work runs on IO behind completion gates; the uncaught handler is still installed immediately. Activity shows a temporary native progress placeholder, reads the prior report on IO, then enters the existing Compose/Prismal tree. Checkpoint writes, healthy marker and recovery-report deletion run on IO.
- **Bounds/privacy:** startup-stage reads capped at 512 characters; report reads capped at 30,000; oversized legacy reports rejected by file length before loading. Final crash-report flush remains synchronous on the uncaught-exception thread. No identifiers/raw traces, network, telemetry, permissions or cloud services added.
- **Tests:** 13 pure JVM tests, including bounded prefix read and oversized/legacy report rejection, are added but not run on this new head. Natural exact-head CI is pending.
- **Performance:** no new Prismal surface, capture/readback, blur, shader, graphics layer, image decode, dependency or timer. Disk/binder work moved off main; no measured startup, memory, frame-time, battery or thermal gain claimed.
- **Parent CI:** #367/#368 passed on parent `3173bd9a4af5483d134fda1d5b756a2f5657d03d`; WordNet 151,456 unique words, unit tests, APK assembly and artifact upload. Artifact #13630179831 (39,122,992 bytes). Release skipped. Parent runs do not validate this change.
- **Device:** native launch-crash root cause remains unestablished; OPPO, Reader/IME/selection, accessibility, Reduce Motion and Perfetto remain **UNVERIFIED**.
- **Safety:** no main edit, merge/retarget, workflow change, manual dispatch/rerun, Release publication, package identity change or branch deletion.


## 2026-11-09 14:33 IST / 2026-11-09 09:03 UTC — Definition popup adopts the shared transient material

- **Request / product principle:** continue the app-wide Liquid Glass system while pairing every optical effect with a bounded, reusable cost strategy; retain calm, high-contrast definition content and native Android selection actions.
- **Source / branch:** `Batmancpu/CurrentLens`, `black-box-bihar-news`; parent `2b1878e58744f901eb66aca7fa792ce06488c08c`, 171 ahead / 0 behind `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; main is merge base. App-code commit `fb2e75c3f82f18896bfa5dff623a9ba9da263d15` is a direct first-parent child, 172 ahead / 0 behind. PR #34 remains open/draft and unmerged.
- **Freshness:** complete graph rechecked: 51 branches, 32 open PRs, 15 closed PRs; all second pages empty. PR #47's full three-file diff was reviewed (3 ahead / 0 behind main; 3 ahead / 172 behind active) and its values were adapted into the existing shared `GlassSurfacePolicy` rather than adding a second definition-specific policy. PR #47/source branch remain untouched. PR #48 is a separate workflow/WordNet builder security proposal; it was not ported because it overlaps the same workflow and is outside this UI batch. PR #45/#46, #43/#44, #30 and #37 are already represented.
- **Files:** `MainActivity.kt`, `GlassSurfacePolicy.kt`, `GlassSurfacePolicyTest.kt`, `LIQUID_GLASS_UI_ROADMAP.md`, `BRANCH_CONSOLIDATION.md`, and this ledger.
- **Behaviour:** DefinitionGlassOverlay now shares the transient 22dp/5dp/2.5px/3.5px/0.001/0.42 tier, with definition tint clamped to 0.08–0.22 before the existing Reduce Transparency floor. The existing anchored Layout, safe bounds, selected word, Copy/Done, expansion, scroll, Back handling, Reduce Motion snap and native Copy/Select/share ActionMode are unchanged.
- **Shared policy:** PrismalTransientSurface now uses named chromatic/tint tokens. Valid existing transient alpha values are unchanged; the new helper safely handles non-finite preference values and permits a context-specific minimum without duplicating optical constants.
- **Tests:** three pure JVM tests cover shared optical tokens, definition tint scale/floor/endpoints and non-finite fallback. Tests are **added, not yet verified on this head** at this ledger update.
- **Paired optimization:** configured popup blur falls 12→5dp, refraction inputs 22/24→2.5/3.5px, chromatic 0.015→0.001. Same single Prismal surface/root backdrop; zero additional captures/readbacks, render passes, image decodes, dependencies, timers, network or persistence. This is a source-level parameter reduction only; no measured GPU/frame-time, memory, battery or thermal gain is claimed.
- **Parent CI:** natural push #401 and PR #402 passed on parent `2b1878e58744f901eb66aca7fa792ce06488c08c`; WordNet builder 4/4, 151,456 words / 50,950,144 bytes, JVM tests, APK assembly and artifact upload passed. PR artifact #13656181730 (39,138,281 bytes, SHA-256 `cb0baae48de6e8ffc88844400871b331ce6ff7baf7bb0184773fe274463f08eb`). Parent runs do not validate this change. Natural #403/#404 started on exact app-code commit and were in progress; no result claimed.
- **Device:** OPPO Reno10 popup appearance/anchor/selection, TalkBack, font scale, Reduce Motion/Transparency, frame timing, GPU, memory, thermal and battery remain **UNVERIFIED**.
- **Safety:** no main write, merge/retarget, workflow change, manual dispatch/rerun, Release publication, package identity change, source-branch mutation or deletion.

## 2026-11-09 14:39 IST / 2026-11-09 09:06:59 UTC — Correct the transient tint fallback test

- Trigger: natural runs #403/#404 failed after the DefinitionGlassOverlay transient-material pass; #405 reproduced the same failure on the documentation-only head. Full logs show WordNet builder 4/4 and database generation succeeded, then :app:testDebugUnitTest failed one assertion: GlassSurfacePolicyTest > transientTintFallsBackForNonFinitePreferenceAndMinimum, line 334 (334 tests, one failure). APK assembly/artifact upload were skipped. PR run #406 was cancelled by the subsequent test-fix push.
- Diagnosis: the production helper correctly maps non-finite glass intensity to AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA (0.28). The test incorrectly computed expected tint from 0.32 (0.1344) instead of the fallback (0.28 × 0.42 = 0.1176).
- Fix: commit 59c54cedadd1ad97a96c00d63cb3d020343ee2cd changes only GlassSurfacePolicyTest.kt: derive expected from the default preference and assert 0.1176. Production code and visual policy remain unchanged.
- Verification: natural PR run #408 passed on exact commit 59c54cedadd1ad97a96c00d63cb3d020343ee2cd: WordNet 151,456 words / 50,950,144 bytes, JVM tests, debug APK assembly and artifact upload. Artifact #13661132492 (39,139,519 bytes; SHA-256 3a80e7430940d9289dd48de2c36c011f056f2606b252abea287490f62889bd5c). Natural push run #407 was still in progress; no result claimed.
- Safety/device: no workflow, main, release, package, networking, WebView, persistence or source-branch change. OPPO Reno10 appearance, native selection, TalkBack, frame-time, GPU, memory, thermal and battery remain UNVERIFIED.

## 14 November 2026 (IST) — project takeover and signing assessment

- Owner requested project leadership, a point-by-point Git-backed handoff across chats, review of implemented and leftover work, and signing that preserves Android updates over the current install.
- Repository: Batmancpu/CurrentLens. Branch: black-box-bihar-news, draft PR #34. Reviewed parent: 8daadee450ee59a8cffb3227b8460c2bbf6de794. Main/merge base: 73b8a0d1ba33503fede02db2c3c0af082d724a12. Source review and git refs showed 177 integration commits ahead, 0 behind. PR #39 supplies the existing signing draft and shares parent cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84 with integration. Its local merge simulation identified four documentation conflicts.
- Changed files: root AGENTS.md gets a current resume pointer, PROJECT_HANDOFF.md gets a current baseline, PROJECT_AUDIT.md records owner direction, evidence and priorities, and this ledger records the session. Existing design and incident history is retained. Rollback is the docs commit revert. Product code, workflows and APKs were outside this documentation change.
- Verification: integration WordNet builder unit tests 4/4 and PR #48 fixture tests 7/7 using `python3 -m unittest discover -s scripts -p 'test_*.py' -v` on each exact source. `bash -n` passed for the #39 signer-verifier script. Existing #414 PR build passed on audited source, including JVM tests/APK upload. Main proposed signer and real-device behavior remain unverified.
- Next: get exact installed build source or package/versionCode and public signer fingerprint. The evidence shows main #112 and #116 have different certificates. The main and Stability packages have different application IDs. Preserve app-private data and the existing install until update identity and any migration are resolved.


## 14 November 2026 (IST) — main-only consolidation and fresh signed APK line

- Owner direction includes “we can, you know, start again,” “All that is being done should be done in the main branch,” and “Do not run multiple workflows at the same time.” The owner will delete old branches after accepting the consolidated APK. This supersedes the prior migration and draft-branch decisions.
- Baseline: existing main 73b8a0d and integration e14b284. Enumerated all 51 branch refs. Preserved integration history and reconciled unique proposals in focused feed/Reader, editorial, and release/continuity batches. Full branch dispositions and remaining live checks are in CONSOLIDATION.md.
- Feed/Reader batch: NewsRepository, link/field/XML, entry and concurrency policies, publisher asset policy, ArticleReaderRepository and WebView cleanup/wiring. Reused branch fixtures and shared publisher rules.
- Editorial batch: UpscEngine/UpscFeedPolicy and fixtures. Preserved shared relevance indexing and combined policy-action/selected-signal rules with GST mapping.
- Release and continuity batch: build-apk.yml, app Gradle version, WordNet builder/fixtures, signing scripts, .gitignore, AGENTS.md, current handoff, roadmap, release and branch disposition records. Main pushes validate one serialized run. A manual run publishes only the verified APK. Owner-controlled signing setup is pending until a durable keystore backup and GitHub secrets are in place.
- Local checks before upload: Python WordNet 7/7, shell syntax and git diff checks. Kotlin and Android build acceptance are recorded by the exact main Actions run. Native launch, visual performance and install-over are handset checks.
- Main run [#417](https://github.com/Batmancpu/CurrentLens/actions/runs/43581929693) on `8eddf66` passed SDK setup and pinned WordNet, then failed before Kotlin compilation because Gradle has no `:app:testReleaseUnitTest` task. Corrected the workflow to use the previously verified `:app:testDebugUnitTest` task and independently assemble the release variant. Fixed the source ref spelling in the disposition register. Await the new exact-head run before starting publication.
- Main run [#418](https://github.com/Batmancpu/CurrentLens/actions/runs/43582067854) reached Kotlin test compilation after WordNet and production compilation. The carried editorial fixtures referenced two omitted private helpers, `assertSyllabusMapped` and `assertRejectedDespiteKeywordMatch`. Restored both helpers from #27; production code is unchanged in this correction. A local fixture also confirmed that the signing verifier accepts matching standard and V2 signer lines and rejects a different pinned fingerprint. A real release signer remains pending.
