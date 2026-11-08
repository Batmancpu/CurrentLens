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


## Stability 0.2.5 — Build #161 and device result

- Run #161: https://github.com/Batmancpu/CurrentLens/actions/runs/42240793437.
- Commit: `5f478cab7865df9fda351a6de117c8d7cbf1d395`.
- CI: WordNet generation, Gradle unit tests, APK compile, signing-key restore and artifact upload succeeded; Release publication was skipped.
- Artifact: `CurrentLens-v0.2.5-stability-debug-apk`, ID `13517165145`, 14-day retention.
- Owner tested 0.2.5. It still produced `CRASH_NATIVE (5)` / signal 11 after the checkpoint `Full brrrNews composition requested from stability probe`. The plain Compose/Material probe was visible in the supplied recording; the subsequent full-app transition returned to launcher/recovery. Root cause is not established.
- The video and raw report remain private; no media or device identifiers were committed.

## Stability 0.2.6 — staged rendering isolation build

- Branch: the existing PR #16 branch only.
- Adds incremental test modes: (1) a single root GraphicsLayer capture without a glass draw, (2) one full Prismal glass surface on that source, (3) the hidden second tab capture plus merged source, then (4) full app with existing effects.
- Build version code/name: 8 / `0.2.6-stability`; same Stability signing key for update installation.
- Workflow concurrency is scoped per Git ref and cancels older in-progress builds on the same ref only. A Stability push will not cancel a main-branch build.
- CI result/artifact must be appended after run completion. This is a diagnostic build, not a fix claim.


## Stability 0.2.7 — Prismal source-capture correction

- Branch: existing draft PR #16, `fix/early-startup-crash-diagnostics-2026-11-06`; no new branch.
- Version code/name: 9 / `0.2.7-stability`; package suffix remains `.stability`; same dedicated Stability signing key allows update installation over 0.2.6.
- Source diagnosis: the root content `Column` was itself the Prismal backdrop capture target, even though glass descendants inside that same subtree sampled the same layer. Prismal v1.0.6 documents capture for background-only content; this self-referential layer usage is the strongest source-level defect consistent with the full-app native first-render failure.
- Correction: place the captured source on a background-only sibling containing only the app theme background and subtle radial ambient glows. All content and glass surfaces are siblings above it. The original Prismal automatic pipeline and blur/refraction/chromatic/specular/depth/spring effects are preserved.
- The previous separate tab-glyph capture/merged droplet source remains unchanged.
- Workflow artifact and prerelease labels are updated to 0.2.7. The active PR branch still produces an Actions artifact only; Release publication is skipped.
- Owner declined further staged device testing; none was run. CI success is not physical-device verification and the native signal-11 causal link remains unverified.


## Live Actions and artifact snapshot — 7 November 2026
- Full Actions history was paginated: 166 runs total across two pages (91 success, 44 failure, 31 cancelled); zero queued or in-progress runs at inspection.
- Latest overall run #166 / https://github.com/Batmancpu/CurrentLens/actions/runs/42275323743 succeeded on PR #16 head d63c22d621a5e8c4c293d1c1b856e8afca478ead. WordNet builder tests, offline DB generation (151,456 unique words), :app:testDebugUnitTest, :app:assembleDebug and artifact upload passed. Gradle compilation/tests took about 1m34s; this is CI evidence only.
- Artifact: CurrentLens-v0.2.8-stability-debug-apk, ID 13533102905, 38,996,079 bytes, SHA-256 ZIP digest c107b424d120f25aa6316f7db6fe4e287769d33c8f021af02ec3b89ef59b4948, 14-day retention. Release publication was skipped.
- Latest main run remains #116 / https://github.com/Batmancpu/CurrentLens/actions/runs/42209037417, success on main SHA 73b8a0d1ba33503fede02db2c3c0af082d724a12; it published v0.2.0-build-116. The latest main Release does not contain the unmerged PR #16 changes.
- PR #18's read-only pull-request build / isolated release-write workflow passed run #165 / https://github.com/Batmancpu/CurrentLens/actions/runs/42260538402; PR #18 remains open/draft. Main still has the older push-only workflow with contents: write at workflow scope. Reconcile PR #16's branch-specific workflow and PR #18 before any merge; no workflow was changed in this review.
- The current PR #16 privacy-minimisation change adds focused JVM tests. Its new natural CI run must be checked before describing those tests as passed. No Actions workflow was manually dispatched or rerun.


## Live CI snapshot — 7 November 2026, 11:38 IST

- Workflow: .github/workflows/build-apk.yml; push and pull_request triggers include main, PR #16's branch and black-box-bihar-news. Build job permissions are read-only; the separate publish job runs only on push to main and has contents:write.
- Actions history was fully paginated: 200 runs (100 + 100 + 0), no queued/in-progress jobs.
- Run #200 (42341245934) succeeded on exact parent SHA cfe0b3e8319374df5cb29423f04a79c77d4fe10e. WordNet builder tests and database generation, :app:testDebugUnitTest, :app:assembleDebug and APK artifact upload succeeded; publish job skipped.
- Artifact: 13560769755, CurrentLens-v0.2.8-stability-debug-apk, 39,059,340 bytes, 14-day retention. Non-main stability build uses application ID com.mangoloads.currentlens.stability and label brrrNews Stability; it installs separately from the normal app.
- Latest published Release remains v0.2.0-build-116. No feature-branch Release was created.
- The Appearance low-effects commit authored from cfe0b3e is not covered by run #200; wait for its own natural run. Do not dispatch/rerun manually.


## Live CI snapshot — 7 November 2026 (after runs #210–#211)

- Active workflow: `.github/workflows/build-apk.yml`, blob `6cef6ca19f3babd331c9deb59be9da0067c6fa66`. It triggers natural push and pull_request builds for main, PR #16's branch and black-box-bihar-news. Build/test permissions are read-only; the separate publish job runs only on a main push and has contents:write.
- Main workflow remains blob `37197497288d9f0a60f4d1cfa8453e3dee52a902`, push-only with workflow-wide contents:write. Do not claim a future PR-to-main build is naturally validated until main's workflow is safely reconciled.
- Actions history: 211 runs fully paginated (100 + 100 + 11; page 4 empty): 125 success, 54 failure, 32 cancelled; no queued/in-progress runs.
- Run #208 failed on black-box pre-fix SHA `947310a157e03df25dde3856d2686d18ffca68e8` at `MainActivity.kt:4238:61` because the local Quiz answer map shadowed the Compose semantics receiver's Boolean `selected` property.
- PR #28's one-line `.semantics { this.selected = chosen }` correction was fast-forwarded into black-box at `6f9b3c1e1a484233721863d0b736734c0d282cd5`; PR #28 closed/merged into this branch only.
- Run #209 passed on PR #28's exact head; run #210 passed on the active branch push at the same exact SHA. Run #210: four WordNet builder tests passed; database generated 151,456 unique words / 50,950,144 bytes; `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload succeeded. Artifact `13568511654`, 39,082,053 bytes, SHA-256 `9eeb4bbdbfd08f9f8f5e94a5eda0251b41d1c441933653c0ef326246669458de`. Publish job skipped.
- Latest overall run #211 passed on PR #27's independent head `9b70ed1c7c39bb511399c6284c7e8833f4672618`; artifact `13567364771`, 39,084,359 bytes. It does not validate black-box. PR #27 remains open/draft.
- Latest published Release remains `v0.2.0-build-116`. No feature-branch Release was created; no manual dispatch/rerun.
- Existing deprecation warnings (WebView scale, Compose updateTransition, ArrowBack icon, Node.js 20 action runtime, punycode and url.parse) are non-blocking and remain outside this repair.
- OPPO device launch, screenshots, accessibility, animation, Perfetto/frame timing, GPU/memory/thermal/battery remain UNVERIFIED.

## 8 November 2026 — verified main APK signing discontinuity

- Main runs #112 and #116 were both successful push builds for com.mangoloads.currentlens, versionCode=2, versionName=0.2.0.
- Downloaded and inspected their actual APKs from Actions artifacts #13497753209 and #13497522975. The APK v2 signing-block certificates differ:
  - Run #112: dba85a1c1ac6b3a7b829850353724e7a847e2fa456a13ab3f983d66c245f30dd.
  - Run #116: 28c1fc47020366d8ad1fd689935039b7b3c8b79675e93112a68e2e47452adb64.
- This is artifact-level evidence that the default runner debug signing identity changed between main builds. Android rejects an in-place update when package signatures differ.
- The integration branch's stable cached key is used only when -PstabilityTest=true; main does not set that flag and still falls back to the runner debug key.
- Proposed stacked draft branch: fix/stable-release-signing-2026-11-08 (base black-box-bihar-news). It requires a private main-release keystore in four GitHub Actions secrets, fails closed if missing, and verifies the built APK's signer certificate against the configured keystore before upload.
- CI on the non-main branch can verify the separate Stability key path. The main secret-backed signing path remains unverified until the owner configures the secrets and a main push runs naturally.
- Migration risk: the private keys for builds #112/#116 cannot be recovered from the APK certificates. The first stable-key APK cannot update those installs; allowBackup=false means uninstall removes local data. Owner acknowledgement/data-preservation decision is required before merge or first stable-key Release.
- PR #39 workflow review split the signing preparation into mutually exclusive main-release and non-main Stability steps. The main keystore secrets are not mapped into pull_request jobs. This correction was made before the first natural PR run.
- The main signing step also requires the public repository variable CURRENTLENS_RELEASE_CERT_SHA256 and checks the keystore's DER certificate fingerprint against it before Gradle. The APK verifier checks the assembled signer against the same pin. This prevents an accidental secret-key rotation from silently producing an incompatible update.
## 8 November 2026 — signer-verifier CI correction

- PR #39 run #317 on head 7af010e726038395eb6d2c02c3ddd067145dc266 completed with failure at the new signer-verification step. WordNet generation, JVM unit tests and :app:assembleDebug all passed; artifact upload was correctly skipped.
- The verifier's first parser captured stdout only and matched one exact SHA-256 label. The run produced no matching captured line, so the verifier failed closed before upload. This is a verifier-output parsing failure, not evidence that the APK was signed with the wrong key.
- Updated the verifier to capture both stdout/stderr, accept SHA-256/SHA256 label variants case-insensitively, and print safe certificate output if parsing still fails. A new natural PR run is required; no dispatch/rerun was used.
## 8 November 2026 — signer output format and secret scope

- PR #39 run #318 passed WordNet generation, JVM unit tests and APK assembly, then failed closed in the signer parser. Actual apksigner output uses the prefix 'V2 Signer: certificate SHA-256 digest:' rather than the assumed 'Signer #1 certificate SHA-256 digest:'.
- This is a parser-format mismatch, not a certificate mismatch. The verifier now extracts all SHA-256 certificate lines independent of signer-prefix format, deduplicates fingerprints across signing schemes, and requires exactly one distinct 64-hex signer. The next natural PR run is the verification point.
- The workflow now passes private store/key passwords only to the main Gradle build and main verifier steps (or the non-main test steps); passwords are no longer persisted through GITHUB_ENV for artifact-upload actions.