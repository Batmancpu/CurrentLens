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


## 2026-11-08 — Preserve genuine box-office taxation policy coverage

- Updated existing draft PR #27, branch `fix/editorial-action-gate-blackbox-2026-11-07`; no duplicate branch or PR was created.
- Defect: the hard-noise list rejected every headline containing “box office”, including a genuine ministry clarification of GST treatment. This contradicted the product rule to retain genuine institutional/regulatory developments even when they touch a noisy subject.
- Fix: retain box-office wording as soft noise; add the specific syllabus-linked subject “GST on box office collections” and the explicit “clarifies/clarified” action forms. A generic box-office report still has no institutional-action exception and remains rejected.
- Added a labelled synthetic false-negative recovery fixture for “Finance Ministry clarifies GST on box-office collections”; retained the existing false-positive fixture for a generic box-office/GST collections update.
- Updated the official-baseline fixture register. This is synthetic regression coverage, not a live-feed precision/recall result or an official PYQ claim.
- Verification at commit time: pending natural PR CI. No device/runtime claim.


### Commit and verification detail

- Base branch: `black-box-bihar-news`; observed base head `ad16cecf06add374b3e19989d0829919d0055114`. PR #27's recorded base/merge-base is `3a08bb95b0f9a0fed8ec11dae055f0ac2817a523`; compare reports 32 commits ahead, 12 behind, diverged, and GitHub mergeable=false. Do not merge the stale branch without reconciliation.
- Code commit: `e17926a6337fcceae6951d28242b36ee76dadcbc` — `UpscFeedPolicy.kt`.
- Test commit: `c4c28ba3d9a2e32d84dbec3e5bf032e4f2152b54` — `UpscFeedPolicyTest.kt`.
- Baseline fixture commit: `fa2eab8f3083c9466d62b4e49092ab09de64f438` — `docs/UPSC_EDITORIAL_BASELINE.md`.
- Ledger entry commit: `fd18c98c7af5621bb81963dde4a15c8d5164e826` — `docs/SESSION_LEDGER.md`.
- Baseline wording correction: `b08b0902f69d5fb4cbc11490ab9cec50103d3db2` — clarifies that generic box-office wording remains soft noise, not a hard exclusion.
- Natural PR run #254 (`42402006467`) was still in progress at the last check, at `Compile APK and run JVM tests`. Its head `fa2eab8f` includes the code, test and initial baseline fixture commits; the later two commits are documentation-only. No result is claimed for the new code/test change yet. Prior green run #250 applies only to the earlier head `a5dddb3697b18167b195f79a60b4f14468f789c3`.
- No device/runtime verification was available. No live-feed or official-PYQ claim is made.


### CI failure and correction — 2026-11-08

- Natural PR run #254 (`42402006467`) failed at `:app:testDebugUnitTest`: 291 tests completed, 1 failed, `UpscFeedPolicyTest.currentGstPolicyActionMapsToGsThreeEconomyButBoxOfficeFramingIsRejected`, helper line 23. WordNet generation passed; APK artifact upload was skipped.
- Root cause established from the exact source and failing fixture: the first edit removed “box office” from the **soft** `noiseTerms` list instead of from `nonOverrideableNoiseTerms`. The generic hard exclusion therefore remained and correctly blocked the new policy fixture. This was an implementation mistake in the proposed correction, not a Gradle/compiler/environment failure.
- Corrective commit: `3d57f0714f06579955d6efba1ebee72002dda081`. Restores “box office”/“box-office” to soft noise and removes only those generic phrases from the hard-exclusion list. Generic box-office reporting remains rejected unless the headline satisfies the narrow institutional-action + exact GST-subject rule.
- The positive fixture and existing generic box-office false-positive fixture are unchanged. Natural CI for the corrective code commit is pending; do not claim it passes until the run completes.


## 2026-11-08 04:39 IST / 23:09 UTC — Close institutional film-promotion action-gate bypass

- **Repository / branch:** Batmancpu/CurrentLens; fix/editorial-action-gate-blackbox-2026-11-07.
- **PR:** [#27 — Port strict institutional-action gate to black-box integration](https://github.com/Batmancpu/CurrentLens/pull/27), targeting black-box-bihar-news. Main remains 73b8a0d1ba33503fede02db2c3c0af082d724a12 and is untouched.
- **Code commit:** 67cc900e29d6687d7c13f388ec1658cdbf24c2ca, parent cef7f1267cf2ca67ceacaf2acd9cfcbfa7dbf6fc.
- **Evidence:** the proposed strict gate's ordered regex could still match “Ministry announces new film highlighting the Cinematograph Act” as institution → action → policy subject. Its hard-noise list covered “film about/on” but not “film highlighting”. The same bypass applied to documentary promotion that highlighted celebrity-endorsement guidelines.
- **Change:** add narrow hard-exclusion phrases for film/documentary/series/biopic content-highlighting, celebrating, depicting, featuring, starring, exploring or showcasing policy subjects. Keep the existing positive control for a Ministry issuing actual film-certification rules.
- **Tests:** add two labelled synthetic false-positive fixtures that first assert the classifier has a mapping, then assert editorial rejection. Update docs/UPSC_EDITORIAL_BASELINE.md with the additional false-positive example. These are synthetic fixtures, not live-feed measurements or official PYQs.
- **Verification:** no natural Actions run or check suite was created for code commit 67cc900e at the time of this ledger entry; commit status/check-runs returned zero checks. Do not claim the new tests passed. No manual workflow dispatch/rerun.
- **Integration risk:** PR #27 is still diverged from its integration base and GitHub reports it unmergeable. The code change is not active in black-box-bihar-news until reconciled and validated on the current base.
- **Scope / privacy:** no source feeds, network permissions, dependencies, storage schema, analytics, telemetry, main branch, workflow configuration or Release changed. Rollback is limited to the added hard-noise phrases and two fixtures.
- **Next:** reconcile PR #27 with the latest integration branch, obtain natural CI on the combined head, and test adjacent promotional verbs against genuine regulatory headlines before calling the editorial gate ready.


## 2026-11-08 05:37 IST / 00:07 UTC — Close movie/documentary/series content-framing bypass

- **Repository / branch:** Batmancpu/CurrentLens; `fix/editorial-action-gate-blackbox-2026-11-07`.
- **PR:** [#27 — Port strict institutional-action gate to black-box integration](https://github.com/Batmancpu/CurrentLens/pull/27), targeting `black-box-bihar-news`. Main remains `73b8a0d1ba33503fede02db2c3c0af082d724a12` and is untouched.
- **Evidence:** the existing hard-noise list covered several `film` and `documentary` constructions but omitted equivalent `movie celebrating`, `documentary portraying`, and `series featuring` forms. The headline “Government announces new movie celebrating the Cinematograph Act” could therefore satisfy institution → action → policy-subject matching and bypass soft entertainment noise.
- **Change:** add a headline-only content-framing pattern for film/movie/documentary/series relation verbs and biopic framing. It is checked as non-overridable promotional noise before the policy exception; actual “Government issues new film certification rules” remains a positive control.
- **Tests:** add three synthetic false-positive fixtures, each requiring a classifier match before asserting editorial rejection. Update the official-baseline note with the examples. These are synthetic regression fixtures, not live-feed measurements or official PYQs.
- **Verification:** this commit is pushed to the existing draft PR; its natural PR workflow is expected to validate it. No manual dispatch or rerun. Do not claim the new tests pass until the exact new head's job logs complete.
- **Integration risk:** PR #27 remains diverged from its integration base and was reported unmergeable before this commit. The change is not active in `black-box-bihar-news` until reconciled and validated on the current base.
- **Scope / privacy:** no feed sources, network destinations, permissions, dependencies, storage schema, telemetry, analytics, workflow configuration, main branch or Release changed. Rollback is limited to the new headline pattern and its fixtures/documentation.
- **Next:** inspect natural CI for the exact new head, then reconcile PR #27 with the current integration branch and validate the combined gate.

The resulting GitHub commit SHA is recorded in the PR history; this entry intentionally does not attempt to embed its own SHA.
