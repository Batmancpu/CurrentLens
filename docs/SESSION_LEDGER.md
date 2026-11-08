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

## 8 November 2026 — verify CurrentLens APK update-signing continuity

### Evidence and defect

- Re-enumerated live main, branches, open/closed PRs, Actions history/queue, workflow, latest job logs/artifacts and Releases before repository changes.
- Main remains 73b8a0d1ba33503fede02db2c3c0af082d724a12. Active integration branch was black-box-bihar-news@cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84; PR #34 is the existing draft consolidation PR.
- Downloaded the actual APKs from main Actions artifacts #13497753209 (run #112) and #13497522975 (run #116). Both source commits use applicationId=com.mangoloads.currentlens and versionCode=2, but parsing the APK v2 signing block produced different signer certificate SHA-256 fingerprints:
  - #112: dba85a1c1ac6b3a7b829850353724e7a847e2fa456a13ab3f983d66c245f30dd.
  - #116: 28c1fc47020366d8ad1fd689935039b7b3c8b79675e93112a68e2e47452adb64.
- Main's Gradle file has no explicit signing configuration and its workflow does not restore a stable main signing key. The active integration branch configures its cached key only when stabilityTest=true; the main build path does not set that property.
- Android package updates require a matching signing identity. The old builds therefore cannot update one another. The private keys cannot be recovered from the public certificates.
- The manifest sets android:allowBackup=false; an uninstall to cross the signing boundary deletes app-private local data. This migration risk is explicit and must be owner-accepted before the first stable-key Release.

### Isolated proposal

- Branch created from the exact active integration SHA: fix/stable-release-signing-2026-11-08, parent cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84.
- Proposed files: app/build.gradle.kts, .github/workflows/build-apk.yml, scripts/verify_apk_signing.sh, docs/RELEASE_SIGNING.md, plus handoff/issue/build/decision/branch/ledger registers.
- Main builds require a private PKCS#12 keystore via four GitHub Actions secrets and fail closed when absent. Non-main builds keep the separate cached Stability test key and package suffix. A post-assembly apksigner check compares the APK signer SHA-256 with the certificate exported from the configured keystore before artifact upload.
- No private key or password is committed. No runtime dependency, permission, network, analytics or telemetry change.
- The main secret-backed signing path cannot be exercised from a pull_request run; the PR build can verify the Stability-key path. Owner must configure and securely back up the release key before any main push using this workflow.
- Verification status at authoring: code and docs staged in one branch commit; local shell syntax check and natural PR CI pending. No physical-device test. No direct-main edit, merge, manual dispatch/rerun or Release publication.
### PR opening and secret-scope hardening

- Draft PR #39: https://github.com/Batmancpu/CurrentLens/pull/39; base black-box-bihar-news; initial commit 69cf8a8e14f417f025721197f1013e0b53d87051.
- Follow-up review caught a security boundary issue before CI: the first workflow draft mapped release secrets into a step that also ran on PR refs. The workflow is split into two mutually exclusive steps; release secrets are now injected only into the main-branch push step, while pull_request builds receive only the cached Stability test key. No PR workflow step receives the main release secrets.
- This is a pre-CI correction. Natural PR validation remains pending; no dispatch/rerun.
- Follow-up: pin the main release certificate fingerprint in repository variable CURRENTLENS_RELEASE_CERT_SHA256. The main-only step checks the keystore against this public SHA-256 before Gradle; the post-build verifier checks the APK signer against the same pin. This prevents a changed secret from silently rotating the APK signing identity.
## 8 November 2026 — signer-verifier CI correction

- PR #39 run #317 on head 7af010e726038395eb6d2c02c3ddd067145dc266 completed with failure at the new signer-verification step. WordNet generation, JVM unit tests and :app:assembleDebug all passed; artifact upload was correctly skipped.
- The verifier's first parser captured stdout only and matched one exact SHA-256 label. The run produced no matching captured line, so the verifier failed closed before upload. This is a verifier-output parsing failure, not evidence that the APK was signed with the wrong key.
- Updated the verifier to capture both stdout/stderr, accept SHA-256/SHA256 label variants case-insensitively, and print safe certificate output if parsing still fails. A new natural PR run is required; no dispatch/rerun was used.