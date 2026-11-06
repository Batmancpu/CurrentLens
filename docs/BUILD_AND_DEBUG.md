# CurrentLens — Build, Test, Release and Debug Record

## Application and toolchain at snapshot

- Repository: Batmancpu/CurrentLens. Repository name remains CurrentLens.
- Android namespace/application ID: com.mangoloads.currentlens.
- app/build.gradle.kts: minSdk 26, targetSdk 35, compileSdk 37; Java 17; versionCode 2; versionName 0.2.0.
- Kotlin + Jetpack Compose.
- Runtime libraries inspected: AndroidX Activity Compose, Compose/Material 3, Lifecycle Runtime, Coil Compose 2.7.0, Kotlin Coroutines Android 1.9.0, Jsoup 1.23.2, PrismalAGSL v1.0.4.
- JUnit 4.13.2.
- Open English WordNet 2025+ converted to bundled SQLite at build time; Android platform SQLite at runtime.
- Manifest: INTERNET only; cleartext traffic disabled; Android backup disabled.
- Notices: THIRD_PARTY_NOTICES.md; PrismalAGSL MIT and WordNet attribution in app/src/main/assets/licenses/.

## Single Actions workflow

Path: .github/workflows/build-apk.yml.

- Trigger: push to main only; no pull_request/workflow_dispatch trigger.
- permissions: contents:write.
- Concurrency: currentlens-android-build; cancel-in-progress false.
- Runner ubuntu-latest; timeout 40 min.
- Java Temurin 17.
- Android platform/build tools android-37.0 and build-tools;37.0.0.
- Gradle 9.5.0.
- Python dictionary-builder tests; download Open English WordNet 2025+ archive; size check 5–25 MB; build app/src/main/assets/wordnet.db with >=100,000 unique words.
- Build command: gradle --no-daemon --stacktrace :app:testDebugUnitTest :app:assembleDebug.
- Artifact: CurrentLens-v0.2.0-debug-apk, app/build/outputs/apk/debug/app-debug.apk, 14-day retention.
- Release: softprops/action-gh-release@v2, tag v0.2.0-build-[run_number], debug-signed APK prerelease, make_latest false.

Critical risk: Any push to main runs this workflow with release-writing permission. A merge/push to main can publish a debug APK prerelease. During review do not push main, merge PRs, change workflow, dispatch/rerun or publish releases.

## Latest verified CI/Release

- Main HEAD 73b8a0d1ba33503fede02db2c3c0af082d724a12.
- Latest Actions run #116 completed successfully.
- Complete Actions history: 116 runs, pages 1 and 2.
- Outcomes: 73 success, 33 failure, 10 cancelled; 0 queued/running at capture.
- Run #116 principal steps all succeeded: checkout, Java, Android SDK, platform install, Gradle, offline WordNet generation, JVM tests/APK compile, artifact upload and Release publication.
- Run ID 42209037417.
- Artifact CurrentLens-v0.2.0-debug-apk, artifact ID 13497522975, ZIP size 38,920,865 bytes, sha256 19337c3fca739067227a2d7f1d295f5eade7288599278b0e436789ebc2964374, expires 20 Nov 2026 11:18:55 UTC.
- Release asset: https://github.com/Batmancpu/CurrentLens/releases/download/v0.2.0-build-116/app-debug.apk; asset size 39,461,810 bytes.
- Release tag: https://github.com/Batmancpu/CurrentLens/releases/tag/v0.2.0-build-116.
- Debug-signed testing APK only; not production or Play Store.
- CI success is not device launch, visual, TalkBack, GPU or frame-time verification.

## Known failure patterns and confirmed fixes

| Run | Failure evidence | Follow-up / correction |
|---|---|---|
| #1 | Android SDK setup: sdkmanager failed after “Failed to find package tools”. | SDK setup evolved; do not reintroduce obsolete tools package requirement. |
| #5 | Platform install: “Failed to find package platforms;android-37”. | SDK platform configuration changed; verify runner availability before editing. |
| #8 | Could not resolve com.github.styropyr0:PrismalAGSL:Prismal. | Later coordinate/JitPack work fixed resolution. Do not reuse bad version Prismal. |
| #10/#18 | compileDebugKotlin failed; extracted excerpt lacks first diagnostic. | Root cause not established here. Inspect full log before attributing. |
| #38 | 40 tests completed, 1 failed: FeedDateParserTest.preservesExplicitRfc1123Offset at line 16. | Follow-up commits f2938bf / 61d212f addressed Article/feed date parsing and compact RFC1123 offsets. Preserve explicit publisher offsets. |
| #53 | Kotlin compile failed; first compiler diagnostic missing from excerpt. | Root cause not established. |
| #97 | Stale economy signal-count test expectation after mapping gained fourth signal. | PR #2 / commit 6cf84ba updated fixture from 3 to 4 actual matches: rbi, repo rate, inflation, monetary. |
| #99 | Unresolved Compose imports CalendarMonth and AccountCircle. | PR #5 / commit 17b47e4 added supported imports. |
| #102 | Search glass draw callback called Compose theme local outside composable scope. | PR #10 / commit ba06a8f captures surface colour in composable scope before callback. |
| #113–#115 | Branding push sequence caused queued workflow runs to be cancelled by later commits. | No manual reruns; #112 and #116 completed. #116 is latest successful current-main run. |

Other failed runs: #6,#7,#9,#11–#16,#19,#22,#28,#37,#40,#43,#48,#55,#57,#62,#80,#87,#88,#94. Retrieve exact logs when touching those features; do not infer root cause.

## Local test/build commands

CI build command:
gradle --no-daemon --stacktrace :app:testDebugUnitTest :app:assembleDebug

Dictionary builder:
python3 -m unittest discover -s scripts -p 'test_*.py' -v

The CI downloads WordNet at build time and generates a local SQLite asset; the app does not request dictionary data at runtime.

## Evidence discipline

- Include branch/commit SHA and exact Actions run number.
- State which exact stages passed/failed.
- Link artifact or Release only after verifying it exists.
- State debug-signed vs production-signed.
- Never imply CI proves app opens or scrolls smoothly on user's Android test device.
- If tests are added in a PR but not run, say “added, not executed”.
- Never dispatch/rerun Actions or publish release during ordinary review.


## PR #16 — diagnostic startup build status (6 November 2026)

- Draft PR: https://github.com/Batmancpu/CurrentLens/pull/16.
- Branch: fix/early-startup-crash-diagnostics-2026-11-06.
- Head: b66315c2f82f7785e7dfd96577d83d203770faad.
- Base main: 73b8a0d1ba33503fede02db2c3c0af082d724a12.
- Seven commits ahead, zero behind; five changed files: manifest, new Application class, crash diagnostics, MainActivity, tests.
- Tests have been added but not executed. No PR build ran. The final PR commit made diagnostics setup/checkpoint I/O best-effort so diagnostic storage failures cannot block launch. The current workflow triggers only on push to main, not pull_request or workflow_dispatch. No build was dispatched/rerun and no APK/Release was produced from this branch.
- Build #116 is the last verified build of old main code and must not be treated as including PR #16.
- If PR #16 is merged, it triggers the current main-push workflow, which publishes a debug APK prerelease. Account for this release side effect before merge.
- A build/test pass will not prove the Android test device app launches. Physical cold-start, retry and device report are required.


## Superseding crash investigation — Stability 0.2.3 to 0.2.5 (6 November 2026)

This section supersedes the earlier PR #16 snapshot above where its head, renderer policy or test status differ.

### Stability 0.2.3 — Build #140 (run 42234708089)
- CI: WordNet generation, JVM unit tests, APK build, signing-key restore and artifact upload succeeded.
- The build forced Prismal Legacy on API 33+ (AGSL and RenderEffect disabled; sampled GraphicsLayer/backdrop capture remained).
- Owner-tested APK still exited with Android `CRASH_NATIVE` / signal 11. The last persisted application checkpoint was `MainActivity.setContent returned`; no managed Java/Kotlin exception was captured.
- The partial tombstone text included ART finalizer/monitor-wait frames and a stack-pointer diagnostic heuristic. This is insufficient to identify a specific crashing thread or source-level root cause. No private model, build fingerprint, process/thread ID, exact timestamp or raw trace is retained in this repository.

### Stability 0.2.4 — Build #148 (run 42237621678)
- CI succeeded and produced the privacy-minimised diagnostics build.
- It still had the temporary Legacy override, so it is not the final visual restoration decision and does not prove device stability.
- Diagnostics were changed to omit device/build identifiers, exact times, IDs, exception messages and raw native traces. Old local reports containing device identifiers are cleared on upgrade. There is no automatic upload.

### Stability 0.2.5 — Build #158 (run 42239425526)
- Branch: `fix/early-startup-crash-diagnostics-2026-11-06`; source head before the documentation import: `3767fe1e47ff563e8ae3f97a3542db60f5613d56`.
- Version code 7 / version name `0.2.5-stability`; package suffix `.stability`; dedicated persistent Stability signing key.
- CI result: success — dictionary builder tests, WordNet generation, Gradle unit tests, APK assembly and artifact upload all passed. Release publication was skipped.
- Artifact: `CurrentLens-v0.2.5-stability-debug-apk`, artifact ID `13513676549`, 14-day retention.
- Production rendering is restored: `CurrentLensApplication` no longer forces Prismal Legacy; the normal build enters the full app with Prismal's normal automatic Liquid Glass pipeline.
- Stability-only build first shows `StabilityLaunchProbe`, a plain Compose/Material screen without Prismal backdrops or glass drawing. Its button then enters the full app with the original pipeline enabled. This separates first-screen startup from full-app composition for the next physical test.
- `StartupRenderPolicy.kt` and its forced-Legacy test were removed. No production branch, Release or main commit was changed.
- Device validation is still pending; do not label the crash fixed.

### Runtime acceptance is mandatory
CI establishes that the code compiles and JVM tests pass. It cannot establish that the app launches on the owner's device. Use the acceptance sequence in root `AGENTS.md` and record the actual device result separately from CI.
