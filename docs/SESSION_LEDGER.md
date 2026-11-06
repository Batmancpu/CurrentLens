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
- Build100 passed CI but app reportedly crashed immediately on OPPO Reno10 5G. User explicitly required crash investigation first.
- PR #7 and #9 added code-level defensive startup/performance work and local crash diagnostics. Neither establishes physical runtime root cause.
- Search field reportedly far above keyboard, leaving awkward gap; Search bar rise/fall/morph animation reported stuttery.
- Avoid expensive per-cell blur, duplicate decode and frequent GPU readbacks. Use trace-backed fixes.
- User wants Apple-quality visual polish but no speculative churn and no smoothness trade-offs.

### User’s design feedback/reference images
- PR #3 body documents supplied OPPO screenshot defects: flat headers; Explore filter chips visually separate; active Today lens blurring selected icon; oversized dark inset image frame; keyboard/system-bar backdrop; empty loading state.
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
- User reported Build100 immediate OPPO launch crash. CI success did not mean device runtime works.
- PR #7 moved startup restoration off main, cached Today filters, incrementally updated bookmarks/read state, reduced glass readback cadence, bounded image decode, palette guards, OEM system-bar defensive guard and icon revert; #101 passed.
- PR #9 added local-only crash diagnostics/recovery, off-main editorial index, removed recurring feed readback in favour theme-derived contrast, memoized filters, hardened preferences, Search/nav polish; #102 failed later due draw-scope.
- PR #10 fixed Search draw-scope error; #103 passed.
- No OPPO runtime verification. User-reported crash remains open.

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
- Don't assume CI success = OPPO crash fixed.
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
The user reports the latest app still behaves as follows: tap launcher icon → Android shows opening animation → no app UI is visible → Android returns to launcher and the app appears closed. This matches the earlier Build 100 cold-start report on OPPO Reno10 5G, but there is no fresh crash report/logcat/tombstone in this conversation.

### Repository inspection and findings
- Inspected current main manifest, MainActivity startup/first composition, LaunchCrashDiagnostics, LocalStore, NewsRepository, DictionaryActivity/WordNet database session, build Gradle settings, workflow and project source inventory.
- Main has no custom Application class; launcher activity is MainActivity.
- Main LaunchCrashDiagnostics.install() was called inside MainActivity.onCreate(), too late for failures during ContentProvider initialisation.
- The app depends on PrismalAGSL v1.0.4. Its merged manifest includes androidx.startup.InitializationProvider and metadata for PrismalAgslInitializer. AndroidX Startup runs provider initializers at application startup before Activity creation. Prismal's v1.0.4 initializer itself only binds the asset manager when API >=33; this does not implicate Prismal as the cause. It does mean a managed exception in any earlier provider startup could precede the old activity-level handler. Official references: https://developer.android.com/topic/libraries/app-startup and https://developer.android.com/reference/androidx/startup/InitializationProvider.
- The previous recovery UI was implemented in Compose. That is not a robust recovery surface if the failure involves Compose or Prismal drawing.
- Main CI Build #116 succeeded but only establishes that the earlier main code compiled and its JVM tests ran; it does not establish that the OPPO device launches.
- No device-level evidence identifies a particular code line as the root cause. The exact crash remains unverified; do not present the instrumentation change as a guaranteed app fix.

### PR #16 implementation
PR: https://github.com/Batmancpu/CurrentLens/pull/16
Branch: fix/early-startup-crash-diagnostics-2026-11-06
Head: b66315c2f82f7785e7dfd96577d83d203770faad
Base: main 73b8a0d1ba33503fede02db2c3c0af082d724a12
Status: open draft; seven commits ahead, zero behind; five files changed.

Six PR commits:
1. e81939b32dca807abb59a987e33c739751550e30 — early Application handler, crash report and native recovery implementation.
2. 356515458e9b689ac152a0f202113dfac324c365 — make early crash report formatting test-safe.
3. d70caac2dd0f2247667df12226c2ff3cc845222b — detect a startup interruption even without a Java exception stack.
4. e865c60473a19809a71d916bc7792bfd6861537a — avoid Android process API calls from pure report-formatting tests.
5. d5e0bd8c42d1cec6f4707ef550340162493acc7c — make synthetic startup-diagnostic writing best-effort so diagnostics do not abort launch.
6. b66315c2f82f7785e7dfd96577d83d203770faad — correct interruption-report test assertion to match exact report wording.

Files and effects:
- app/src/main/AndroidManifest.xml — registers .CurrentLensApplication.
- app/src/main/java/com/mangoloads/currentlens/CurrentLensApplication.kt — installs diagnostics in attachBaseContext, before AndroidX Startup providers; records Application lifecycle stage.
- app/src/main/java/com/mangoloads/currentlens/LaunchCrashDiagnostics.kt — writes local exception reports via temp file + fd.sync + rename/fallback; captures app/device/build/Android/PID/thread/last stage; stores startup stage markers; if a previous process ended before first three Compose frame callbacks and no exception report exists, creates an explicitly labelled interruption report (not a fabricated Java stack trace); clears the marker only after the first three frame callbacks.
- app/src/main/java/com/mangoloads/currentlens/MainActivity.kt — reads prior report and shows native Android Views recovery screen with selectable text/copy action rather than re-entering Compose; logs Activity/Compose checkpoints; marks launch healthy after three frame callbacks.
- app/src/test/java/com/mangoloads/currentlens/LaunchCrashDiagnosticsTest.kt — tests stack report detail, interrupted-start semantics, and size bound.

### Verification / limitations
- All five files were fetched and source-reviewed on the PR branch after the final commit.
- GitHub compare against current main reported 6 commits ahead, 0 behind; only those five paths changed.
- Tests have been added but not executed. No local Gradle build was run. The final PR commit also made diagnostic setup/checkpoint I/O best-effort so a diagnostics failure cannot abort Application startup.
- Existing workflow triggers only on push to main; it has no pull_request or workflow_dispatch trigger. No build was dispatched/rerun, no APK produced, no Release created.
- No physical OPPO test is possible from this environment. PR #16 was left draft and unmerged.
- This change primarily fixes a gap in crash evidence collection/recovery. It does not prove that Prismal, Compose or another component is the crash's underlying cause.

### Required next device validation
After PR #16 has been reviewed/merged and its new debug APK installed:
1. Tap the app icon and observe cold launch.
2. If it returns to launcher, open it once more.
3. If the recovery screen appears, copy the full local report and paste it into the CurrentLens chat.
4. If there is still no report/recovery screen, obtain Android bugreport/logcat/tombstone/ANR evidence. A native/system kill may not produce a Java/Kotlin uncaught-exception report.
