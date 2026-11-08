# Live implementation batch — Reader WebView permanent-disposal guard (9 November 2026, 00:31 IST)

**Source:** `black-box-bihar-news@58c2e5d99f87701eb7e6f68a53bfadcf8eb9c8e4`  
**Implementation commit:** `86e095d858784b59b231f64f647aed869698f992` (parent `58c2e5d99f87701eb7e6f68a53bfadcf8eb9c8e4`; **154 ahead / 0 behind main**).  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` — main remains the merge base.  
**PR:** #34 remains open/draft → main, unmerged and unretargeted. This pass stays on the owner-selected consolidation branch.

## Branch freshness and dependency gate

- Re-enumerated all **46 remote branches** (page 1 = 46; page 2 = 0), **29 open PRs** and **14 closed PRs**. The selected branch head is `58c2e5d99f87701eb7e6f68a53bfadcf8eb9c8e4`; PR #34 matches it. Main remains `73b8a0d1ba33503fede02db2c3c0af082d724a12`.
- Newest relevant independent branch is PR #43, `fix/release-reader-webview-resources-2026-11-09@d1ec6fa51bf002cdf9f20cbfe6edbfa327eeb614`: **5 ahead / 0 behind main**, but **5 ahead / 154 behind black-box-bihar-news**. Its complete three-file delta is limited to `MainActivity.kt`, `ReaderWebViewReleaseGuard.kt` and its JVM test. It is not a viable app baseline; its lifecycle fix was reviewed and selectively ported below. PR #43's exact head has **0 check-runs and 0 commit statuses**, so no source-branch CI is claimed.
- PR #42 is **6 ahead / 3 behind** the selected line and changes only UPSC editorial engine/feed policy/tests/baseline; it was not ported in this UI pass. PR #39 is **6 ahead / 19 behind** and changes signing/workflow/Gradle; its owner-managed secrets, certificate pinning and first-install/local-data migration remain a separate gate. PR #16 is **7 ahead / 97 behind** and carries separate startup/diagnostic changes; no old MainActivity was copied.
- PR #37's Search RowScope repair is already reflected in the active 48dp in-capsule clear slot; PR #30's TouchTargetPolicy, #31's off-main appearance snapshot and #36's Asia/Kolkata policy are represented. The older `brrrNEWS` material branch is **4 ahead / 60 behind** and its smaller GlassSurfacePolicy is superseded by the active shared tier policy. `ui/liquid-glass-performance`, `ui/liquid-glass-system-continuation` and `redesign/v0.3-search-reader` are not safe baselines.
- PR #34 is the only branch modified. No other source branch, main, release or PR was changed.

## Exact parent CI, Actions, workflow and Release

- Fully paginated **364 Actions runs** (100 + 100 + 100 + 64): **226 success / 68 failure / 70 cancelled**; queued/in-progress **0**.
- Exact parent SHA `58c2e5d99f87701eb7e6f68a53bfadcf8eb9c8e4` passed natural push run [#363](https://github.com/Batmancpu/CurrentLens/actions/runs/42508268534) and PR run [#364](https://github.com/Batmancpu/CurrentLens/actions/runs/42508274576). The PR job's full log and every step were inspected: WordNet builder **4/4**, offline dictionary **151,456 unique words / 50,950,144 bytes**, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload all passed; build completed in 56 seconds. Artifact [#13628606698](https://github.com/Batmancpu/CurrentLens/actions/runs/42508274576/artifacts/13628606698) is **39,118,210 bytes**, SHA-256 `a6ab2c44ed3d971d76daa488a49661231c15d781711a00158b7df0e8e1db7d0e`, expires **22 November 2026 18:07:43 UTC**. Push artifact #13627942749 is 39,118,211 bytes. Publish jobs were skipped.
- The active `.github/workflows/build-apk.yml` uses read-only build/test permissions and a separate main-push-only Release-write job; natural PR and push triggers include `black-box-bihar-news`. Main's workflow remains push-only with workflow-wide `contents: write`. Latest published Release remains `v0.2.0-build-116`; no Release was created.
- PR #43's source head remains unvalidated; its selective port on implementation commit `86e095d858784b59b231f64f647aed869698f992` passed exact-head natural push run [#365](https://github.com/Batmancpu/CurrentLens/actions/runs/42513792786) and PR run [#366](https://github.com/Batmancpu/CurrentLens/actions/runs/42513796390). Both jobs passed WordNet builder 4/4, 151,456 unique words / 50,950,144-byte database, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload; build times were 57s and 61s. Push artifact [#13630731515](https://github.com/Batmancpu/CurrentLens/actions/runs/42513792786/artifacts/13630731515) is 39,122,994 bytes (ZIP SHA-256 `a6c812e2d8a9236f688aa9af9003ddc22bc2a78f94a5c41dd3ebd4d79f1a743b`); PR artifact [#13630621916](https://github.com/Batmancpu/CurrentLens/actions/runs/42513796390/artifacts/13630621916) is 39,122,992 bytes (ZIP SHA-256 `bcc28a9a47df203d99b4621427d7bf11c627264fca83074e4ec87c899567b93e`). Both expire 22 November 2026 19:13 UTC. Publish jobs were skipped. Full push and PR logs were inspected. Non-blocking Node.js 20/action-runtime and upstream `punycode`/`url.parse()` deprecation warnings remain.

## Implementation — release WebView only on permanent Compose disposal

- Current `ReaderScreen` owns one `AndroidView` WebView. Its existing `onDetachedFromWindow()` flushes reading position and cancels transient work, but detachment is not equivalent to permanent disposal and must not destroy a WebView during a temporary window transition.
- Added the `AndroidView.onRelease` path. It calls `releaseForDisposal()` only when Compose permanently releases the reader, then clears the remembered holder only if it still points to that exact instance.
- The one-shot `ReaderWebViewReleaseGuard` flushes the last debounced reading position, stops loading, cancels delayed save/restore/short-page-end/chrome-hide work, disables the selection bridge, clears Compose callbacks/client closures, disables JavaScript and DOM storage for the disposed instance, clears history, removes the WebView from its parent, removes children and finally destroys it.
- Temporary detach remains non-destructive. Active reader JavaScript policy, publisher fallback permissions, native ActionMode/Copy/Select/share, dictionary selection, reading-session accounting, saved position semantics and quiz submit-only persistence are unchanged.
- Reviewed PR #43's full patch rather than copying it blindly: its failure-isolation test omitted the recorded `remove-parent` action from the expected call list. The port corrects that assertion and checks idempotence even after one cleanup action throws.

## Paired resource/material strategy and coverage

| Component | Material tier | Effect/capture delta | Reuse and fallback | Evidence |
|---|---|---|---|---|
| Reader WebView permanent disposal | No glass; lifecycle-only | No new blur, refraction, Prismal pass, backdrop capture/readback, shader or image decode | Existing single Reader WebView; one-shot teardown; temporary detach remains safe | JVM guard tests added; device memory/jank remains unmeasured |

- This change removes retained native WebView/client/callback work after permanent Reader exit at source level; it is **not** a measured memory, frame-time, battery or thermal improvement. No glass effect was added, so there is no additional render cost to offset.

## Tests, risks and remaining verification

- Added JVM tests for ordered one-shot cleanup and for continuing through later teardown actions after a failure, including parent removal and destroy. Existing ReaderChrome/selection policies are unchanged.
- Implementation commit `86e095d858784b59b231f64f647aed869698f992` is validated by exact-head runs #365/#366 above. This documentation-only follow-up advances the branch after that validated code commit; its own natural push/PR run is pending and must be inspected before another implementation batch.
- OPPO Reno10 WebView disposal/re-entry, launch stability, screenshots, native selection, TalkBack, font scaling, Reduce Motion, IME, Perfetto/frame timing, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. CI compilation/JVM tests do not prove device lifecycle correctness.

## Next

- Inspect the exact new-head natural push and PR jobs, full logs and artifact. If they pass, continue the remaining whole-app visual consistency audit; preserve the one background-only root capture, expanded-only tab glyph source, the existing low-cadence shared luminance probe and calm high-contrast article/data surfaces.

---

# Live implementation batch — Search hierarchy de-duplication (8 November 2026, 23:27 IST)

**Source:** `black-box-bihar-news@6160d58cd54719c83d8dace2f99ba2104e27efcb`  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` — source is **152 ahead / 0 behind**, with main as merge base.  
**PR:** #34 remains open/draft → main, unmerged and unretargeted. This pass stays on the owner-selected consolidation branch.

## Branch freshness and dependency gate

- Re-enumerated the complete remote inventory: **45 branches** (page 1 = 45, page 2 = 0) and **42 PRs** (**28 open / 14 closed**). The default branch remains main. The selected branch head is `6160d58cd54719c83d8dace2f99ba2104e27efcb` (8 November 17:05:33 UTC, “perf(ui): coalesce feed scroll idle settling”); PR #34 head matches it and targets main.
- PR #42, `fix/gst-council-syllabus-disambiguation-2026-11-08@2629cfbe4ee6ece7a41fd13d35dc987abc1e26b8`, is **6 ahead / 1 behind** the selected line. Its complete diff is limited to `UpscEngine.kt`, `UpscFeedPolicy.kt`, their tests and `UPSC_EDITORIAL_BASELINE.md`; it is editorial-only and was not ported into this UI pass.
- PR #16, `fix/early-startup-crash-diagnostics-2026-11-06@ebea0aac68ce2cf17aad376304f7915fe937e807`, is **7 ahead / 95 behind**. Its newer startup/diagnostic and MainActivity/LocalStore changes overlap separate PR #35 diagnostic work and are not Search prerequisites; no diagnostic code was copied and no native launch fix is claimed.
- PR #37's Search RowScope compile correction is already present in the active 48dp trailing clear slot; PR #30's TouchTargetPolicy, #31's IO-restored appearance preferences and #36's Asia/Kolkata day policy are also present. The old `brrrNEWS` line is **4 ahead / 58 behind** and its small GlassSurfacePolicy is superseded by the active supporting/transient/preview/filter policy. `ui/liquid-glass-system-continuation` is 68 behind; `ui/liquid-glass-performance` is 3 ahead / 170 behind; `redesign/v0.3-search-reader` is 246 behind. None is a safe newer UI baseline.
- PR #39's signing/workflow migration remains owner-gated on secrets, certificate pinning and first-install/local-data migration. RSS/parser/security proposals (#25, #26, #32, #33, #38, #40, #41), persistence proposals (#8, #22, #29), and editorial proposals (#6, #21, #27, #42) were not changed. No source branch was modified.

## Exact parent CI, Actions, workflow and Release

- Fully paginated **362 Actions runs** (100 + 100 + 100 + 62): **224 success / 68 failure / 70 cancelled**, with **0 queued or in-progress**.
- Exact parent SHA `6160d58cd54719c83d8dace2f99ba2104e27efcb` passed natural push run [#361](https://github.com/Batmancpu/CurrentLens/actions/runs/42503199872) and pull-request run [#362](https://github.com/Batmancpu/CurrentLens/actions/runs/42503204114). Both build jobs passed WordNet builder tests (**4/4**), generated **151,456 unique words** (**50,950,144 bytes**), passed `:app:testDebugUnitTest` and `:app:assembleDebug`, and uploaded the debug APK. PR artifact [#13625902905](https://github.com/Batmancpu/CurrentLens/actions/runs/42503204114/artifacts/13625902905) is **39,124,172 bytes**, SHA-256 `ca03ff3dce7fa10a8b13f84cbe0eb7ebda245bec00cfa96ea9c9a1081ae48a9d`, expires **22 November 2026 17:07:33 UTC**. Both Release jobs were skipped. The full PR build log and every job step were inspected.
- The active `.github/workflows/build-apk.yml` uses read-only build/test permissions, natural push/PR triggers for the integration branch and a separate main-push-only Release-write job. Main's workflow remains push-only with workflow-wide `contents: write`. Latest published Release remains `v0.2.0-build-116`; no Release was created.

## Implementation — remove the competing inline Search glass slab

- Search already has the shared glass page header, the morphing Search capsule and a functional Filters action. Its additional inline `drawPrismalGlass` panel repeated the “UPSC-relevant articles / syllabus-linked topics” hierarchy and placed another full glass slab between those controls.
- Replaced that panel with one compact **48dp plain status row**: result count when no filters are active, or the existing active-filter summary when filters are selected; the existing `GlassActionPill` remains the sole inline Filters affordance. It stays a static tinted/rimmed control rather than another live backdrop consumer.
- The filter sheet, topic/GS/subject state, active-filter count, query classification/index reuse, search-clear callback, focus, keyboard/IME docking and root backdrop ownership are unchanged. No duplicate control or second clear affordance was introduced.

## Paired material/performance strategy

- **Removed:** one resting Search-feed `drawPrismalGlass` surface and its 7dp blur, 3dp/4dp refraction, specular, shadow and inset effects.
- **Retained:** the one shared root background-only capture, the existing morphing Search capsule, the static `GlassActionPill`, and the conditional 5dp `PrismalTransientSurface` only while the filter sheet is open. No new sampler/readback, shader, graphics layer, image decode, dependency, timer, network or persistence path.
- The result/status row derives only from the already-computed filtered list and filter state; no new classifier pass or per-pixel state. This is a source-level reduction of one active glass surface, not a measured frame-time, GPU, memory, battery or thermal improvement.

## Tests and remaining verification

- No new pure-policy test was warranted: filter state, classification, navigation and IME semantics are unchanged; this is a Compose hierarchy/material-scope change. Parent run #362 validates the source before this change only; the new commit requires its own natural push/PR run. No manual dispatch/rerun.
- OPPO Reno10 Search hierarchy, light/dark contrast, filter target reachability, keyboard/cursor/IME behaviour, TalkBack, font scaling, Reduce Motion, launch stability, Perfetto/frame timing, GPU overdraw, allocation/memory, thermal and battery remain **UNVERIFIED**. No device or performance claim is made.

## Next

- Inspect the exact new-head natural push/PR jobs, complete logs and APK artifact before another implementation pass. Continue the whole-app audit only where a concrete inconsistency remains; preserve the one root background-only capture, expanded-only tab glyph source, static content surfaces and low-cadence shared luminance probe.

---

# Live implementation batch — coalesced feed scroll settling (8 November 2026, 22:25 IST)

**Source:** `black-box-bihar-news@5863f29ab4e598230492b8c43d2f0d2d882112d5`  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`  
**PR:** #34 remains open/draft → main; no retarget or merge. This change stays on the owner-selected consolidation branch.

## Freshness and dependency gate

- Re-enumerated all **45 remote branches** (page 1 = 45; page 2 = 0) and **42 PRs** (**28 open / 14 closed**; page 2 = 0). Default branch remains `main`. Before this implementation, `black-box-bihar-news` was **151 ahead / 0 behind main**, with main as merge base; PR #34 head matched it.
- The only branch ahead of the selected line with a newer timestamp is PR #42, `fix/gst-council-syllabus-disambiguation-2026-11-08@2629cfbe4ee6ece7a41fd13d35dc987abc1e26b8`, **6 ahead / 0 behind**. Its complete comparison is limited to `UpscEngine.kt`, `UpscFeedPolicy.kt`, their tests and `UPSC_EDITORIAL_BASELINE.md`; it is editorial-only and does not change the UI baseline, so it was not ported into this UI pass.
- PR #16 `fix/early-startup-crash-diagnostics-2026-11-06@ebea0aac68ce2cf17aad376304f7915fe937e807` is **7 ahead / 94 behind** the selected line (merge base `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`). Its newer diagnostic/startup changes are not Reader/navigation prerequisites and overlap PR #35 diagnostic work; no diagnostic code was copied and no native launch fix is claimed.
- PR #37's RowScope Search-clear correction is present in the current `SearchMorphField`: the 48dp trailing slot is reserved and `AnimatedVisibility` is inside a `Row`. PR #30's `TouchTargetPolicy`, PR #31's IO-restored appearance preferences and PR #36's Asia/Kolkata day policy are present in the active tree. The old `brrrNEWS` line is **4 ahead / 57 behind**; its 1,104-byte `GlassSurfacePolicy` is superseded by the active 6,222-byte policy covering supporting, transient, preview and filter-control tiers. The stale `ui/liquid-glass-performance` line is **3 ahead / 169 behind**; `redesign/v0.3-search-reader` is **245 behind**. Neither is a baseline.
- PR #39 remains a separate signing/workflow/Gradle migration requiring owner-managed secrets, certificate pinning and an explicit first-install/local-data migration decision. RSS/parser/network proposals (#25, #26, #32, #33, #38, #40, #41), persistence proposals (#8, #22, #29), and editorial proposals (#6, #21, #27) were not modified by this UI pass. No source branch was deleted or changed.

## Exact parent CI, Actions, workflow and Release

- Exact parent SHA `5863f29ab4e598230492b8c43d2f0d2d882112d5` passed natural push run [#357](https://github.com/Batmancpu/CurrentLens/actions/runs/42498635717) and pull-request run [#358](https://github.com/Batmancpu/CurrentLens/actions/runs/42498640377). Run #358's complete build log confirms WordNet builder **4/4**, offline dictionary generation **151,456 unique words / 50,950,144 bytes**, `:app:testDebugUnitTest`, `:app:assembleDebug`, **BUILD SUCCESSFUL in 1m 12s**, and artifact upload. The separate publish job was skipped.
- PR artifact [#13624666099](https://github.com/Batmancpu/CurrentLens/actions/runs/42498640377/artifacts/13624666099) is **39,123,522 bytes**, ZIP SHA-256 `9180dff13acee7f4627c6aed120a8afe0ccc15a27b84d1dc134aa588b4540ce9`, expires **22 November 2026 16:13:29 UTC**. It validates the parent SHA only; the new commit below requires its own natural run.
- Actions history is fully paginated: **360 runs** (**222 success / 68 failure / 70 cancelled**), with **0 queued or in-progress**. Latest global run #360 is PR #42 on its separate editorial head `2629cfbe...`; it does not validate this UI branch. No manual dispatch/rerun.
- Active branch workflow blob `6cef6ca19f3babd331c9deb59be9da0067c6fa66` has read-only build/test permissions, natural push/PR triggers, and a separate main-push-only Release-write job. Main's workflow remains push-only with workflow-wide `contents: write`; PR #34's natural validation is from the active branch workflow. Latest Release remains `v0.2.0-build-116` (published 6 November 2026 11:18:59Z); no Release was created.

## Implementation — one idle-settle job per scroll gesture

- `BrrrNewsApp` previously cancelled and launched a 550ms coroutine on every non-zero `NestedScrollConnection.onPostScroll` delta. That kept the shared 8×8 adaptive-luminance probe paused correctly, but created avoidable coroutine/job churn during long feed drags and flings.
- `onPostScroll` now only marks the feed active and cancels a pending settle. The existing 550ms idle settle is scheduled once from `onPostFling`, after the drag/fling has completed; a new delta cancels that pending job. Tab/destination changes and composition disposal retain their existing cancellation/reset paths.
- Feed direction thresholds, hysteresis, top reset, compact/expanded nav state, eligible destinations, 550ms quiet window, 4s/6s probe cadence, single 8×8 readback source and Reduce Transparency/Reader/Settings/background gates are unchanged. No polling, per-pixel Compose state, second sampler, or additional backdrop source.

## Paired performance and accessibility strategy

- **Render/effect delta:** 0 new or removed Prismal surfaces, blur/refraction passes, backdrop captures/readbacks, shaders, image decodes, graphics layers, dependencies, network calls or persistence operations.
- **Cost-control change:** removes repeated coroutine creation/cancellation from the scroll-delta path; at most one settle coroutine is created per completed drag/fling. The existing 550ms pause remains, so the shared luminance sampler does not resume during an active gesture.
- Existing `FeedNavigationPolicyTest` continues to cover directional threshold boundaries, hysteresis, ineligible destinations, top reset and tracker reset. No new pure-policy test was warranted because navigation policy and state transitions are unchanged; the exact-head natural build must compile the new nested-scroll callback.
- This is source-level cost containment, **not** a measured frame-time, jank, memory, GPU, thermal or battery improvement. OPPO Reno10 screenshots, scroll traces, Perfetto/frame timing, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. Startup/launcher stability and native selection behaviour also remain device-unverified.

## Next

- Inspect the new exact-head natural push and PR jobs, complete logs and artifact before another implementation pass. Continue the whole-app coverage audit only where a concrete inconsistency remains; preserve calm high-contrast article/quiz reading surfaces, one background-only root capture, expanded-only tab glyph capture and the existing low-cadence shared luminance probe.
- No main write, merge, retarget, force update, manual workflow dispatch/rerun, Release publication, package/app identity change or source-branch deletion.

---

# Live implementation batch — Reader hidden-chrome tap reveal (8 November 2026, 21:29 IST)

**Pre-change source:** \`black-box-bihar-news@f65d72488497ed59ed492d4d24b6e8a2572f4185\`  
**Base:** \`main@73b8a0d1ba33503fede02db2c3c0af082d724a12\`  
**PR:** #34 remains open/draft → main; no retarget or merge. The implementation and this roadmap update are committed together on the existing owner-selected branch.

## Branch freshness and dependency gate
- Re-enumerated the complete remote branch endpoint: **44 branches** (page 1 = 44; page 2 = 0), and **41 PRs** (**27 open / 14 closed**; page 2 empty). Default \`main\` is unchanged. The selected branch is **150 ahead / 0 behind main**, with main as merge base.
- Current open PRs: #41, #40, #39, #38, #37, #36, #35, #34, #33, #32, #31, #30, #29, #27, #26, #25, #24, #22, #21, #18, #17, #16, #13, #12, #11, #8 and #6. Closed PRs: #28, #23, #20, #19, #15, #14, #10, #9, #7, #5, #4, #3, #2 and #1.
- Since the prior complete 44-row inventory, only two branch heads changed: \`black-box-bihar-news@f65d72488497ed59ed492d4d24b6e8a2572f4185\` (2026-11-08 15:05:55Z, “ui: unify Search action pill continuous geometry”, PR #34 open/draft → main) and \`fix/early-startup-crash-diagnostics-2026-11-06@ebea0aac68ce2cf17aad376304f7915fe937e807\` (2026-11-08 15:11:25Z, “docs: record off-main diagnostics verification [skip ci]”, PR #16 open/draft → main). The other 42 branch names, heads, timestamps/messages and PR relationships match the preceding full inventory table in \`BRANCH_CONSOLIDATION.md\`.
- PR #16 is **7 commits ahead / 93 behind** this selected branch (merge base \`6ab1e6a02ac8e91027f21900d64d63b9f2e6a890\`). Its appearance-alpha/first-read changes are superseded by the active branch's richer \`LocalAppearancePreferences\` (glass alpha + Reduce Transparency) and IO-restored snapshot. Its two newer code commits move startup diagnostic work off the main thread across \`CurrentLensApplication\`, \`LaunchCrashDiagnostics\` and the top-level \`MainActivity\` startup path; these are not Reader prerequisites. PR #35 also changes \`LaunchCrashDiagnostics\` and its tests, so those diagnostic hunks need one joint review before porting. No startup/device-stability claim is made.
- PR #19's old UI head is **0 ahead / 80 behind** this branch; its Reader/Search implementation is already in the consolidated ancestry. PR #37's Search RowScope repair, #36's Asia/Kolkata rollover, #30's 48dp target policy, #31's appearance IO snapshot, #29's bookmark persistence and the conservative shared adaptive-contrast policy are represented.
- PR #39 remains a separate signing/workflow proposal (**6 ahead / 15 behind**) requiring owner-managed secrets, certificate pinning and explicit install/local-data migration approval. PR #40/#41 and #32/#33/#38 remain separate RSS/parser/network review work; #25/#26 require a joint publisher-resource security review; #27 is editorial-only. The stale \`ui/liquid-glass-performance\` and \`redesign/v0.3-search-reader\` branches remain excluded. No other branch was modified.

## Exact parent CI and repository state
- Fully paginated **356 Actions runs**: **219 success, 68 failure, 69 cancelled**; queued/in-progress: **0**.
- Exact parent SHA \`f65d72488497ed59ed492d4d24b6e8a2572f4185\` passed natural push run [#354](https://github.com/Batmancpu/CurrentLens/actions/runs/42493059399) and PR run [#355](https://github.com/Batmancpu/CurrentLens/actions/runs/42493063222). The PR build job passed checkout, Java, signing-key restore, Android SDK/platform setup, Gradle, WordNet builder tests (**4/4**), offline dictionary generation (**151,456 unique words / 50,950,144 bytes**), \`:app:testDebugUnitTest\`, \`:app:assembleDebug\` and artifact upload; Release job skipped.
- PR artifact [#13622606443](https://github.com/Batmancpu/CurrentLens/actions/runs/42493063222/artifacts/13622606443) is **39,122,460 bytes**; push artifact #13621439638 is **39,000,680 bytes**. These validate the parent only; the new Reader commit requires its own natural run.
- Latest global run #356 is a separate PR #16 run on code head \`5e36e52915f1232cdfefc7dc28abd5a5360b388b\`; PR #16's current \`ebea0aac...\` head is a documentation-only \`[skip ci]\` child. Run #356 does not validate this branch.
- Active workflow blob \`6cef6ca19f3babd331c9deb59be9da0067c6fa66\` keeps build/test read-only, natural push/PR triggers and a separate main-push-only Release-write job. Main's workflow remains push-only with broader write permission. Latest Release remains \`v0.2.0-build-116\` (published 6 November 2026 11:18:59Z); no Release was created.

## Reader interaction gap and implementation
- Reader auto-hide already reveals on upward scroll and pins controls for definition, native selection ActionMode, IME, enabled accessibility services and the open Aa menu. It did not reveal hidden controls on a short intentional tap in the reading canvas.
- Added pure \`ReaderChromePolicy.shouldRevealOnTap\`: a tap must complete within **360ms**, remain within Android touch slop, and have no active/pending native selection or pinned transient state.
- \`ReaderWebView\` records only the existing ACTION_DOWN/MOVE/UP/CANCEL gesture stream. A qualifying tap calls the existing reveal path only when chrome is hidden; the original event is always forwarded to \`super.onTouchEvent\`, so links and native Copy/Select/share/selection behaviour are not consumed or replaced. Local ActionMode tracking closes the timing gap before Compose pin state propagates. Article changes and detach clear gesture/selection state.
- The existing 160ms hide settle, directional thresholds, completion dock, definition anchor, WebView permissions, publisher fallback, saved reading position and quiz persistence are unchanged.

## Policy tests and state matrix
- Added JVM coverage for a valid short tap and inclusive 360ms boundary; long-press and touch-slop rejection; selection, pending-definition and pinned-state suppression; and invalid measurement rejection.
- Reader states remain one shared chrome component: **visible**, **hidden while reading**, **tap-revealed**, **upward-scroll revealed**, **definition-open**, **native ActionMode/selection pinned**, **IME/accessibility/Aa pinned**, and **completion visible/dismissed**. No duplicate toolbar or bottom bar is introduced.
- Existing Reduce Motion path still sets Reader chrome transition duration to zero (snap); no new spring or continuous animation is added. Device smoothness and native selection behaviour remain unverified.

## Paired rendering/performance strategy
- **Added/removed render passes:** 0. New Prismal surfaces, blur/refraction, backdrop captures/readbacks, shaders, graphics layers, image decodes, dependencies, timers, network calls and persistence operations: **0**.
- The only new work is constant-time gesture bookkeeping on the existing WebView touch stream and one pure policy call on ACTION_UP; Compose visibility changes only once when hidden chrome is revealed. No scroll-pixel state updates, polling or new capture source.
- This is source-level cost containment, not a measured frame-time, memory, GPU, thermal or battery improvement. The OPPO Reno10 remains the target device, but screenshots, logcat/tombstone, TalkBack, IME, Reduce Motion, Perfetto/frame timing, GPU overdraw, memory/allocation, thermal and battery are **UNVERIFIED**.

## Next
- Inspect the new exact-head natural push/PR jobs, full logs and artifact before another implementation pass. Then continue the whole-app coverage review without expanding live blur on repeated cards or adding another backdrop system.
- No main write, merge, retarget, force update, manual dispatch/rerun, Release publication, package identity change, or source-branch deletion.

---

# Live implementation batch — Search filter action corner parity (8 November 2026, 20:28 IST)

**Source:** black-box-bihar-news@0e94d82be37a891dd4546a3f856a9cd47b5687d6
**Base:** main@73b8a0d1ba33503fede02db2c3c0af082d724a12
**PR:** #34 remains open/draft, targets main, and is not merged or retargeted.

## Branch freshness and dependency decision
- Complete remote inventory: 44 branches (page 1 = 44; page 2 = 0); 41 PRs (27 open, 14 closed). The owner-selected consolidation branch remains 149 commits ahead / 0 behind main.
- PR #16 is newer by timestamp at c9dc394e920542d1e329c123bcf354310753b9a7 (4 commits ahead / 92 behind the consolidation branch). Its four-commit delta only adds appearance-alpha normalization and moves the first alpha read into the startup IO snapshot. The active branch already has the richer LocalAppearancePreferences snapshot (glass alpha plus Reduce Transparency), AppearancePreferencesPolicy.create/normalize, LocalStore.appearancePreferences, and restores both values inside LocalAppSnapshot on Dispatchers.IO; the current JVM test covers finite/range fallback and Reduce Transparency. This is a functional superset, so the four commits are superseded and were not cherry-picked.
- PR #37's RowScope Search-clear repair, #36's Asia/Kolkata midnight policy, #30's 48dp icon-target policy, #31's off-main appearance snapshot, #29's off-main bookmark persistence, #13's conservative adaptive contrast, and the earlier PR #19 Reader UI are already represented in the active branch.
- PR #39 remains a separate stable-signing proposal requiring owner-managed secrets, certificate pinning and explicit install/data migration approval. PRs #40/#41 and #32/#33/#38 touch RSS/network/parser boundaries and remain separate joint review work; #25/#26 require a combined publisher-resource security review; #35 is diagnostic-only; #27 is editorial-only. No other branch was modified.
- Stale ui/liquid-glass-performance and redesign/v0.3-search-reader remain excluded. PR #34 is the only branch/PR modified by this automation.

## Exact-head verification before this batch
- Natural push run #347 and pull-request run #348 both passed on exact parent SHA 0e94d82be37a891dd4546a3f856a9cd47b5687d6. Full logs show WordNet builder 4/4, 151,456 unique offline words (50,950,144-byte database), :app:testDebugUnitTest, :app:assembleDebug and APK artifact upload all succeeded; both Release jobs were skipped.
- PR artifact #13620755048 is 39,122,676 bytes and expires 22 November 2026 14:08 UTC. Push artifact #13620281982 is 39,122,679 bytes. These runs validate the parent, not the new commit.
- PR #16's separate exact-head push run #352 passed on c9dc394e920542d1e329c123bcf354310753b9a7; artifact #13620729002 is 38,996,580 bytes. This validates PR #16 only, not PR #34.
- Actions history is fully paginated: 352 runs (216 success, 67 failure, 69 cancelled), queue empty. Latest published Release remains v0.2.0-build-116; no Release was published.

## Implementation — shared continuous control geometry
- Whole-app source audit found one remaining functional glass control that still constructed RoundedCornerShape: Search's GlassActionPill. All sibling filter/category chips use the immutable shared glassControlShape (ContinuousCornerShape, 16dp).
- Replaced that one remembered RoundedCornerShape instance with glassControlShape. The Search Filters action now shares the exact same continuous geometry as the rest of the control system; its static fill/rim, active-count label, 48dp minimum height, Button role, state description, sheet action, keyboard dismissal and filter state are unchanged.

## Paired optimization and verification
- Added/removed render passes: 0. Added/removed live blur/refraction, Prismal surfaces, backdrop captures/readbacks, shaders, graphics layers, image decodes, dependencies, timers, network calls and persistence operations: 0.
- Reuses the existing immutable shared Shape instance rather than constructing a separate rounded shape during composition. No measured allocation/frame-time/battery improvement is claimed.
- No new policy test was warranted for a one-line reference to the already-used shared shape. The new exact-head natural push/PR runs are pending; do not treat #347/#348 or #352 as validation of this new commit.
- OPPO Reno10 visuals, touch/IME behaviour, TalkBack, font scaling, Reduce Motion, startup, Perfetto/frame timing, GPU overdraw, memory/allocation churn, thermal and battery remain UNVERIFIED.

## Next
- Inspect the new exact-head natural push and pull-request jobs, complete logs and artifact before another UI change. Continue the whole-app material audit only where a concrete inconsistency is present; preserve one root background-only capture, the expanded-only hidden tab glyph source, and calm high-contrast article/quiz content.
- No main write, merge, retarget, force update, manual dispatch/rerun, Release publication, app identity change or source-branch deletion.

# Live implementation batch — Saved “Previously Opened” relevance reuse
**Audit:** 8 November 2026, 19:28 IST  
**Pre-change source:** `black-box-bihar-news@261c387bb196e1bb74d5a20df9b3d1bc4f9ebc18`  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`  
**PR:** #34 remains the single open/draft consolidation PR targeting main. This commit is created on the existing owner-selected branch; its own SHA is recorded in the next ledger refresh.

## Branch-freshness and dependency gate
- Re-enumerated the complete remote branch list: **44 branches** (page 1 complete; page 2 empty), and **41 PRs** (**27 open / 14 closed**). Default main is unchanged. Before this batch, `black-box-bihar-news` was **148 ahead / 0 behind main**, **91 ahead / 0 behind PR #16**, and **78 ahead / 0 behind the former PR #19 UI head**. It remains the freshest coherent owner-authorised integration line containing the background-only Prismal root capture, Reader/Search/feed navigation, current material system and reconciled application code.
- PR #34 is open/draft, based on `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; no retarget, merge or main write.
- Rechecked relevant branch proposals and actual diffs: PR #37's Search RowScope repair, #36's Asia/Kolkata rollover, #30's 48dp target policy and #31's off-main Appearance restore are already represented. PR #39 remains a separate signing/workflow migration requiring owner-managed secrets, a pinned certificate and explicit first-install/local-data migration approval. PRs #40/#41 are main-based RSS redirect/parser proposals; #32/#33/#38 overlap parser/network work; #25/#26 require a joint publisher-resource security review; #27 is editorial-only; #35 is diagnostic-only. No source branch was modified. The divergent `brrrNEWS`, old `ui/liquid-glass-performance` and `redesign/v0.3-search-reader` branches are not safe baselines.
- Main's workflow remains push-only with workflow-wide `contents: write`. The active integration workflow has read-only build/test permissions, natural push/PR validation for the integration line, and a separate main-push-only Release-write job; no workflow change was made.

## Parent exact-head Actions verification
- Fully paginated **346 Actions runs** (100 + 100 + 100 + 46): **213 success, 67 failure, 66 cancelled**; queued/in-progress: **0**.
- Natural push run [#345](https://github.com/Batmancpu/CurrentLens/actions/runs/42483678631) and pull-request run [#346](https://github.com/Batmancpu/CurrentLens/actions/runs/42483682905) passed on exact parent SHA `261c387bb196e1bb74d5a20df9b3d1bc4f9ebc18`. Full PR job log: WordNet builder **4/4**, offline dictionary **151,456 unique words / 50,950,144 bytes**, `:app:testDebugUnitTest`, `:app:assembleDebug`, and APK artifact upload all succeeded; build completed in **1m 30s**. Both Release jobs were skipped.
- PR artifact [#13618970693](https://github.com/Batmancpu/CurrentLens/actions/runs/42483682905/artifacts/13618970693): **39,120,818 bytes**, ZIP SHA-256 `26b04cf919118ba3c0fec84b8efb95affbd4e19763477454e9bfaf9f68b1e32a`, expires **22 November 2026 13:12:57 UTC**. Push artifact #13619005997 is also 39,120,818 bytes. These runs validate the parent only; the new commit awaits its own natural push/PR run.

## Implementation — one eligibility classification for previously opened cards
- Previously Opened candidates were classified in `UpscFeedPolicy.shouldInclude` to decide eligibility, then classified again by `NewsCard` to build the card's syllabus metadata.
- `UpscFeedPolicy.eligibleRelevance` now returns the exact `Relevance` used by the existing eligibility decision. `shouldInclude` remains the same boolean API and delegates to this method; the title/description/section noise gate and policy exceptions are unchanged, and the classifier still short-circuits for rejected noise.
- `SavedFeed` builds one ID-keyed map for previously opened candidates, filters the visible top 15 from that map, and passes the same relevance to each `NewsCard`. Ordering, 15-item cap, bookmark/read URL semantics, GS/subject/date filters and editorial eligibility are unchanged.
- Added two pure JVM regression tests: the eligible path invokes its classifier exactly once and returns that result; a noise-rejected story exits before invoking the classifier. Existing editorial positive/negative fixtures remain in place.

## Paired cost and coverage
| Surface | Material / reason | Effect/capture scope | Reuse strategy | Accessibility/fallback | Evidence |
|---|---|---|---|---|---|
| You → Saved → Previously Opened | Standard high-contrast NewsCard; no additional glass is warranted on article content | **0** new blur, refraction, Prismal surface, capture/readback, shader or graphics layer | One eligibility result per candidate is reused for both inclusion and card metadata; the existing single Coil request and same-decoded-bitmap palette path remain unchanged | Existing article-card semantics, bookmark action and native Reader selection are untouched | Source diff + focused tests added; exact-head CI pending |

- Render-pass/effect delta: **0**. New backdrop captures/readbacks, blur surfaces, shaders, image decodes, dependencies, timers, network calls and persistence operations: **0**. The expected reduction is one duplicate classifier invocation per eligible Previously Opened card; this is source-level evidence, not a measured frame-time, allocation, memory or battery win.
- OPPO Reno10 launch, Saved visual layout, TalkBack, font scaling, Reduce Motion, frame timing/jank, GPU overdraw, memory/allocation churn, thermal and battery remain **UNVERIFIED**. CI compilation cannot establish device stability or animation smoothness.

## Next
- Inspect the exact new-head natural push and PR jobs, complete logs and artifact before the next UI batch. Continue the final whole-app transient-control/shared-header audit without adding another material stack or changing quiz/read-history semantics.
- No main write, merge, retarget, force update, workflow dispatch/rerun, Release publication, app/package identity change or source-branch deletion.

---

# Live implementation batch — Saved-feed relevance index reuse
**Audit:** 8 November 2026, 18:38 IST  
**Source branch / SHA before this batch:** `black-box-bihar-news@d21ff270011b80ff6fd4ad6fcbaf7ce15cb8b494`  
**Implementation commit:** `21fee7f625a211962bd9e94c0846226d0501196d`  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; PR #34 remains open/draft and unmerged.

## Freshness and dependency gate
- Re-enumerated all **44 remote branches** (page 1: 44; page 2: 0) and all **41 PRs** (27 open, 14 closed; page 2: 0). Default `main` is unchanged. The owner-selected integration branch is **145 commits ahead / 0 behind main** and **88 ahead / 0 behind PR #16**; the corrected background-only Prismal capture and consolidated Reader/Search/feed-navigation lineage remain ancestors.
- PR #34 is the single consolidation draft: `black-box-bihar-news` → `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. Its code head for this batch is `21fee7f625a211962bd9e94c0846226d0501196d`, a direct child of `d21ff270011b80ff6fd4ad6fcbaf7ce15cb8b494`; the documentation audit is a direct child of the implementation commit.
- The newest independent commit is PR #41's `perf/bound-rss-field-values-2026-11-08@f3fa5daf3242e4d69b1db4666eca3b009821c6e2` (12 ahead / 145 behind this integration line). Its reviewed diff is parser-only: FeedFieldPolicy, FeedXmlTextReader, NewsRepository, build/test dependency and parser tests; it does not change SavedFeed/StudyStats and is not a UI baseline. PR #40 `fix/validate-rss-article-hosts-2026-11-08@552b96e7948ac5ade821a711ea8dd225984824a9` (6 ahead / 145 behind) is a separate RSS URL/redirect policy. Neither was blindly ported.
- PR #39 `fix/stable-release-signing-2026-11-08@4250de5040a8ca0efbf96a990218ef7d69ef26e8` is 6 ahead / 10 behind and changes workflow/build signing and migration documentation; it remains gated on owner-managed secrets and explicit first-install/local-data migration approval.
- PR #37's Search-clear RowScope repair is already present in the active SearchMorphField; PR #30's 48dp target policy, PR #31's off-main appearance restore and PR #13's more conservative shared 8×8 luminance probe are represented. The divergent `brrrNEWS` line is 4 ahead / 51 behind and its fixed-intensity material is superseded; `ui/liquid-glass-performance` is 3 ahead / 163 behind and `redesign/v0.3-search-reader` is 239 behind. No other branch was modified.

## Parent exact-head Actions verification
- Fully paginated **340 workflow runs** (100 + 100 + 100 + 40; page 5 empty): **209 success, 67 failure, 64 cancelled**; queued/in-progress: **0**.
- Natural push run [#339](https://github.com/Batmancpu/CurrentLens/actions/runs/42478159581) and PR run [#340](https://github.com/Batmancpu/CurrentLens/actions/runs/42478162564) passed on exact parent SHA `d21ff270011b80ff6fd4ad6fcbaf7ce15cb8b494`. Full job logs show WordNet builder tests and offline database generation (151,456 unique words; 50,950,144 bytes), `:app:testDebugUnitTest`, `:app:assembleDebug` and APK artifact upload all succeeded; build completed successfully in 1m 30s. Publish jobs skipped.
- PR artifact [#13616009073](https://github.com/Batmancpu/CurrentLens/actions/runs/42478162564/artifacts/13616009073) is 39,119,842 bytes; ZIP SHA-256 `59fb508f433027c43183c24dc34ba205b326f2b080761c92705acc51ffe815cb`; expires 22 November 2026 12:01:55 UTC. Push artifact #13616506663 is 39,119,834 bytes; ZIP SHA-256 `5b9eb6bc4804f99ae5dead80b187545ecc093b7ca2120b3910e92901bee9d42b`.
- These runs validate only the parent SHA. Natural CI for implementation commit `21fee7f6` and the following documentation head is pending; no pass is claimed for the new Saved-feed change.
- Active `.github/workflows/build-apk.yml` uses read-only build/test permissions and natural push/pull_request triggers for main, PR #16's base and this branch; Release write is isolated to main pushes. Main's own workflow remains push-only with broader write permission. Latest published Release remains `v0.2.0-build-116` (6 November 2026 11:18:59 UTC); no Release was created.

## Implementation — one Saved-feed relevance index
- SavedFeed previously classified each saved article separately to build Subject choices, again inside `StudyStatsPolicy.filterArticles`, and again when a visible `NewsCard` had no relevance override.
- It now builds one `ArticleRelevanceIndexPolicy` map keyed by the actual classifier inputs (article ID, title and description), then reuses it for Subject choices, GS/Subject/date filtering and visible saved cards. Changes to publication date, source, section or image do not invalidate the classifier result.
- `StudyStatsPolicy.filterArticles` accepts an optional cached map and preserves its original classifier fallback for callers that do not supply one or whose article ID is absent. Filter semantics, Asia/Kolkata publication-date handling, bookmark identity, previously-opened behaviour and saved-card presentation are unchanged.

## Coverage and paired-cost matrix — Saved
| Screen/component | Material tier / reason | Effect and capture scope | Reuse / fallback | Accessibility and tests | Measured result |
|---|---|---|---|---|---|
| Saved filter group | Tier 2 interactive glass; Published/GS/Subject choices need one coherent control hierarchy | Existing single `GlassFilterGroup` Prismal draw; no new surface, capture or readback | Existing `GlassFilterPill`, shared Clear↔Frosted tokens and low-effects fallback | Selected radio semantics and 48dp minimum targets retained; existing filter tests | Render-pass delta 0; device visuals UNVERIFIED |
| Saved article cards | Tier 4 high-contrast content; headlines and imagery remain readable | Standard NewsCard surface; one Coil request/decode and existing cached palette glow | Same relevance map feeds the card's syllabus metadata; existing single-decode path unchanged | Bookmark target and article semantics unchanged; JVM filter/index tests | No additional blur/capture/decode; frame/memory/battery effects UNVERIFIED |

## Paired optimization, tests and next step
- Keyword classification changes from separate subject/filter passes plus per-visible-card classification to **one index build per relevant content-input change**; subsequent filter changes use map lookups while retaining the existing date/sort work. The index is invalidated only by ID/title/description changes.
- Added `StudyStatsTest.savedArticleFiltersReuseSuppliedRelevanceIndex`, deliberately supplying a cached GS III/Economy result for a headline that would otherwise classify as GS II/Constitution. The existing no-map test continues to cover the fallback path.
- No Prismal/AGSL surface, blur/refraction, shader, graphics layer, backdrop capture/readback, image decode, dependency, timer, polling, network request, persistence path or quiz-data change was introduced. This is a source-level CPU-work reduction; no frame-time, memory, battery or thermal improvement is claimed without measurement.
- Next: inspect natural CI for the exact new branch head and full logs/artifact, then continue the final whole-app visual/accessibility/performance audit. OPPO Reno10 launch, screenshots, TalkBack, font scaling, Reduce Motion, IME, Perfetto/frame jank, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. No main write, PR merge/retarget, manual dispatch/rerun, Release publication, package identity change or source-branch deletion.

---

# Live implementation batch — Reader completion chrome reveal
**Audit:** 8 November 2026, 13:26 IST  
**Source branch / SHA before this batch:** `black-box-bihar-news@5f7e8cfe7c536dec2923a67348fb028916ca637c`  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; PR #34 remains open/draft and unmerged.

## Freshness and dependency gate
- Re-enumerated all **43 remote branches** (page 1: 43; page 2: 0) and all **40 PRs** (26 open, 14 closed). Main is unchanged. The active branch is **136 commits ahead / 0 behind main** and **79 ahead / 0 behind PR #16**; its lineage contains the corrected background-only Prismal capture, consolidated Reader/Search/feed-navigation work and the current app architecture.
- PR #34 is the sole active consolidation PR: `black-box-bihar-news@5f7e8cfe7c536dec2923a67348fb028916ca637c` → `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`, open/draft, mergeable. The branch remains the freshest coherent UI baseline.
- PR #39 (`fix/stable-release-signing-2026-11-08@4250de5040a8ca0efbf96a990218ef7d69ef26e8`) is 6 ahead / 1 behind the active head; its diff is workflow, Gradle signing and release documentation only. It requires owner-managed secrets and a first-install/data-migration decision; it is not a UI baseline and was not ported.
- PR #37's RowScope repair is already in the active Search clear implementation; PR #30's 48dp TouchTargetPolicy and PR #31's off-main appearance restore are represented. PR #13's adaptive-contrast intent is already integrated with the more conservative shared 8×8 probe at 4s/6s cadence, paused during scrolling and outside foreground home.
- The divergent `brrrNEWS` line is 4 ahead / 42 behind the active branch; its fixed-intensity supporting material is superseded by the active Clear↔Frosted-aware GlassSurfacePolicy and shared card geometry. `ui/liquid-glass-system-continuation-2026-11-07` is 52 behind; `redesign/v0.3-search-reader` is 230 behind. Neither is a safe baseline.
- PR #40's publisher-bound RSS URL validation, PRs #25/#26 publisher-resource security, #35 diagnostic-read bounds, #36 IST rollover, #38 parser budget and #32/#33 RSS changes remain separate review streams; no blind cherry-pick or source-branch mutation.

## Parent exact-head Actions verification
- Fully paginated **322 workflow runs** (100 + 100 + 100 + 22): 192 success, 67 failure, 63 cancelled; queued/in-progress runs: **0**.
- Natural push run [#321](https://github.com/Batmancpu/CurrentLens/actions/runs/42457787922) and pull-request run [#322](https://github.com/Batmancpu/CurrentLens/actions/runs/42457791091) both passed on the exact parent SHA `5f7e8cfe7c536dec2923a67348fb028916ca637c`. Full job logs were inspected: WordNet builder 4/4, 151,456 unique words (50,950,144-byte database), `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload all succeeded. Publish job skipped.
- PR artifact [#13608536799](https://github.com/Batmancpu/CurrentLens/actions/runs/42457791091) is 39,117,807 bytes, SHA-256 `b65f0f9beb0a0468beaa7e99e33c122aed6f160987e0d0ca898b9019db1aea3f`, expires 22 November 2026 07:14 UTC. Push artifact #13607972960 is the same size and expires 22 November 2026 07:13 UTC.
- Active workflow `.github/workflows/build-apk.yml` uses read-only build permissions and natural push/pull_request triggers for main, the PR #16 base and this branch; the separate contents:write publish job runs only on a main push. Main's own workflow remains push-only with contents:write. Latest published Release remains `v0.2.0-build-116` (6 November 2026 11:18:59 UTC); no Release was created by #321/#322.

## Implementation — completion must reveal its contextual action
- ReaderWebView's final downward `onScrollChanged` can schedule the 160ms chrome-hide callback immediately before the end detector reports true completion. Because the completion dock is gated by `readerChromeVisible`, it could disappear just after the reader reached the end.
- Added a small pure `ReaderChromePolicy.reveal(state)` transition that restores visibility and clears directional accumulation. ReaderWebView now cancels a pending hide and reveals its existing chrome when true completion is reported; it does **not** pin controls permanently. Subsequent downward reading can still hide the toolbar/action layer normally.
- The same reveal policy is used for existing pinned/top resets. Native selection ActionMode, definition anchoring, IME/accessibility pinning, saved reading position, completion dismissal and five-question submit-only persistence are unchanged.

## Coverage and paired-cost matrix — Reader completion
| Screen/component | Material tier / reason | Effect and capture scope | Reuse / fallback | Accessibility and tests | Measured result |
|---|---|---|---|---|---|
| Reader toolbar + completion action | Tier 1/2 transient glass; source/Aa controls and the contextual completion CTA must be discoverable at the actual end state | Reuses the existing Reader toolbar/completion surfaces and one root background-only backdrop; no added blur, capture, readback or render pass | Existing PrismalTransientSurface; existing 160ms auto-hide and Reduced Motion timing remain unchanged | Pure policy test covers explicit reveal and hysteresis reset; selection, definition, IME, accessibility and native ActionMode paths unchanged | Source audit: 0 new render passes/captures; visual timing and OPPO cost UNVERIFIED |

## Tests, risks and next step
- Added `ReaderChromePolicyTest.explicitRevealRestoresHiddenChromeAndClearsDirectionalAccumulation`. Natural CI must run on the new exact commit; the successful #321/#322 runs validate only the parent SHA above.
- No new timer, polling, frame callback, WebView JavaScript, network, image decode, persistence path, dependency, shader, blur, refraction, graphics layer or GPU readback was introduced.
- OPPO Reno10 screenshots/logcat/tombstone, reader-end dock visibility, native selection collision, TalkBack, font scaling, Reduce Motion, frame timing/jank, GPU overdraw, allocation/memory, thermal and battery remain **UNVERIFIED**. CI success does not prove device launch or animation smoothness.
- Next: inspect the new exact-head natural CI jobs/logs/artifact, then continue the remaining transient-control and whole-app consistency audit. No main write, PR merge/retarget, manual dispatch/rerun, Release publication, package identity change or source-branch deletion.

---

# Live implementation batch — Today feed rhythm and loading accessibility
**Audit:** 8 November 2026, 12:31 IST  
**Source branch / SHA:** `black-box-bihar-news@cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84`  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; consolidation PR #34 remains open/draft and unmerged.

- **Freshness gate:** all 43 remote branches were enumerated (43 on page 1, 0 on page 2); all 40 PRs were reviewed (26 open, 14 closed). The selected line is 135 commits ahead / 0 behind main, 78 ahead / 0 behind PR #16's corrected background-only Prismal/stability ancestor, and 65 ahead / 0 behind the former PR #19 UI branch now incorporated into this line. PR #39 is 6 ahead / 0 behind the current head, but its diff is limited to workflow/build signing and release documentation; it is not a newer UI baseline. PR #37's Search-clear RowScope repair is already present in active history; PR #30's TouchTargetPolicy and PR #31's off-main appearance restore are represented. PR #36 (IST midnight refresh), #35 (diagnostic-read bounds), #38 (parser entry budget), #32/#33 (RSS concurrency/Atom URL handling) and #25/#26 (publisher resource security) remain independent proposals and were not blindly ported. The divergent brrrNEWS and stale UI branches were not selected. No other branch was modified.
- **Parent exact-head CI:** natural push run [#313](https://github.com/Batmancpu/CurrentLens/actions/runs/42453306122) and PR run [#314](https://github.com/Batmancpu/CurrentLens/actions/runs/42453310279) passed on `cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84`. Run #314's full job log shows offline WordNet generation (151,456 unique words; 50,950,144-byte database), `:app:testDebugUnitTest`, `:app:assembleDebug`, and artifact upload all succeeded; build reported `BUILD SUCCESSFUL in 1m 28s`. Artifact [#13606870590](https://github.com/Batmancpu/CurrentLens/actions/runs/42453310279/artifacts/13606870590) is 39,115,995 bytes and expires 22 November 2026 06:07 UTC. Publish job skipped. Run #320 passed on PR #39's separate signing branch, not this UI head. Actions history: 320 runs (190 success, 67 failure, 63 cancelled), queue 0, in-progress 0.
- **Workflow / release:** active integration workflow has read-only build/test permissions and isolates Release write to main pushes. Main's workflow remains push-only with write permission; no main-target PR validation is inferred. Latest published Release remains `v0.2.0-build-116`; no Release was published.
- **Implementation:** Today’s “Updating / relevant stories” status is a polite live region, so refresh/loading transitions can be announced without making each skeleton placeholder a separate TalkBack stop. Skeleton cards clear their decorative semantics. Weekend Reading now uses the same continuous 29dp feed-card geometry and zero elevation as article/skeleton surfaces; inner article actions use continuous geometry. Feed cards, skeletons and reading content remain standard high-contrast surfaces, not glass.
- **Paired optimization:** reuses `FeedCardGeometryPolicy` and the existing `ContinuousCornerShape`; no new Prismal surface, backdrop capture/readback, blur, refraction, shader, graphics layer, image decode, dependency, timer, polling or persistence. Live-region semantics change only when the status text changes; skeleton geometry remains static. No frame-time, memory, battery or GPU improvement is claimed.
- **Tests:** exact new-head natural CI is pending; parent #313/#314 do not validate this commit. No Compose screenshot test was added for the status semantics or weekend shape; existing JVM policy tests remain unchanged.
- **Coverage / paired-cost matrix:** Today weekend panel = tier 4 clear content surface; loading skeleton = non-glass decorative content state; one shared background-only Prismal source remains unchanged. Accessibility status is polite and skeletons are hidden from traversal. Render-pass/capture delta = 0. OPPO Reno10 screenshots, TalkBack runtime, font scaling, Reduce Motion, launch stability, IME, frame timing, GPU overdraw, allocation/memory, thermal and battery remain **UNVERIFIED**.

**Next:** inspect the exact new-head natural push/PR jobs, full logs and artifact; then finish the whole-app consistency pass without adding glass to dense content or repeated feed cards.

---

# Live implementation batch — Search relevance index reuse
**Audit:** 8 November 2026, 11:26 IST  
**Source branch / SHA:** `black-box-bihar-news@35b290226a0b480390611a20e1d8081b4c567180`  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; consolidation PR #34 remains open/draft and unmerged. The source is 134 commits ahead / 0 behind main and 77 ahead / 0 behind PR #16's corrected background-only Prismal/stability baseline `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.

- **Freshness gate:** all 41 remote branches were enumerated (page 1: 41, page 2: 0); all 38 PRs were reviewed (24 open, 14 closed), including current heads and relevant full diffs. `black-box-bihar-news` remains the latest coherent owner-selected integration line. PR #37's Search-clear RowScope repair, PR #30's 48dp icon-target policy, PR #31's off-main appearance restore, PR #29 bookmark persistence and PR #13's conservative shared luminance probe are already represented in the active tree. PR #36 (IST midnight refresh) and #35 (diagnostic-read bounds) remain independent; #38 is parser-only and overlaps the #32/#33 feed-parser review; #25/#26 remain a joint publisher-resource security review. The divergent `brrrNEWS`, stale `ui/liquid-glass-performance` and `redesign/v0.3-search-reader` lines are not newer UI baselines. No source branch was modified.
- **Parent exact-head CI:** natural push run [#311](https://github.com/Batmancpu/CurrentLens/actions/runs/42449279845) and PR run [#312](https://github.com/Batmancpu/CurrentLens/actions/runs/42449282621) both passed on source SHA `35b290226a0b480390611a20e1d8081b4c567180`. Full job logs show WordNet builder 4/4, 151,456 unique words (50,950,144-byte database), `:app:testDebugUnitTest`, `:app:assembleDebug` and APK artifact upload passed. PR artifact [#13604848543](https://github.com/Batmancpu/CurrentLens/actions/runs/42449282621/artifacts/13604848543) is 39,112,976 bytes, SHA-256 `54e2db37d1045253f46b61d3051cf638817e99c9d05cffd6d2e2dc67679c0245`; publish job skipped. Actions history: 312 runs (186 success, 65 failure, 61 cancelled), queue 0, in-progress 0. Integration build/test permissions remain read-only with a separate main-push Release-write job; main's workflow remains push-only with `contents: write`. Latest published Release remains `v0.2.0-build-116`; no Release was published.
- **Implementation:** SearchFeed now builds one immutable relevance index keyed by article ID for stable lookup, with title and description as the classifier's only inputs. Subject choices, GS/subject filtering, query matching and visible Search-card syllabus metadata reuse the same result. `ExploreFilters` accepts an optional cached `Relevance` and resolves all GS/Geography query tokens from it, with at most one fallback classification for other callers.
- **Paired optimization:** removes repeated full-feed classification on every Search query/filter change and avoids a second classification for each visible Search card. The index invalidates only when classifier inputs change; publisher, date, section, image and feed-endpoint metadata do not trigger reclassification. No new Prismal surface, backdrop capture/readback, blur/refraction, shader, graphics layer, image decode, dependency, timer, network request or persistence path. This is a source-level cost reduction; no GPU/frame/memory/battery improvement is claimed without profiling.
- **Tests:** added `ArticleRelevanceIndexPolicyTest` for key invalidation boundaries, exactly-once index construction/order and supplied cached relevance for GS/Geography queries. Existing `ExploreFiltersTest` remains unchanged and continues to cover category/query semantics. Exact new-head natural CI is pending; parent #311/#312 do not validate this code.
- **Coverage / paired-cost matrix:** Search keeps its existing tier-1/2 glass surfaces and one shared root backdrop; this batch adds no material or render pass. Classification is a pure CPU index reused by Search filters and cards, with stable Compose keys and no per-query classifier pass. TalkBack, font scaling, IME, Reduce Motion, OPPO Reno10 visuals/launch, frame timing, GPU overdraw, memory, thermal and battery remain **UNVERIFIED**.

**Next:** inspect the exact new-head natural CI jobs/logs/artifact. If they pass, continue the final Today loading/skeleton and whole-app consistency audit; keep physical-device and performance measurements explicitly unverified.

---

# Live implementation batch — Search filter action material parity
**Audit:** 8 November 2026, 10:27 IST  
**Source baseline:** `black-box-bihar-news@748683f156ea05f650a49603f86750e8902551ed`  
**Code commit:** `45a3264319dfb16cd43a6912a354c1625054c8dc` (direct child of the source baseline)  
**PR:** #34 remains open/draft, unmerged, based on `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. The code head is 133 ahead / 0 behind main and 76 ahead / 0 behind PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.

- **Freshness gate:** all 41 remote branches (page 1: 41, page 2: 0) and all 38 PRs (24 open, 14 closed) were re-enumerated. The owner-selected consolidated line remains the latest coherent UI baseline. PR #37's Search clear RowScope fix is already in the active SearchMorphField; its branch remains 6 ahead / 13 behind and was not replayed. PR #38 is parser-only (8 ahead / 7 behind, FeedEntryBudgetPolicy/NewsRepository/tests); PR #36 is a separate Asia/Kolkata date/index refresh (3 ahead / 14 behind); PR #35 is startup diagnostics; PR #27 is editorial-only. PRs #32/#33 still overlap NewsRepository and remain a joint review; PRs #25/#26 remain a joint publisher-resource security review. PRs #29/#30/#31 and PR #13's conservative adaptive-contrast policy are represented in the integration tree. No source branch was modified.
- **Implementation:** replaced SearchFeed's Material3 FilterChip action with one `GlassActionPill` inside the existing Search header glass group. It uses the shared Clear↔Frosted intensity and Reduce Transparency state, a static selected/idle tint and rim, a 48dp minimum target, Button role and explicit “Filters active” / “No filters active” state description. The active filter count remains visible in its label. Tapping still hides the keyboard and opens the existing filter sheet; category/GS/subject values and search query are unchanged.
- **Material hierarchy:** this is tier 2 interactive control glass within the existing single Search header surface. It is not another live Prismal surface and does not add a competing glass bar.
- **Paired optimization:** reuses the root background-only backdrop and existing Search header draw surface; only static fill/rim drawing was added. No additional capture/readback, blur/refraction, sampler, shader, graphics layer, image decode, dependency, polling, network request or persistence path. No GPU/frame/memory/battery improvement is claimed from source inspection.
- **Tests:** added `actionPillFillAlpha` / `actionPillBorderAlpha` to the existing `GlassSurfacePolicy`, with JVM coverage for Clear/Frosted endpoints, active-vs-idle hierarchy, Reduce Transparency floors and non-finite fallback. The Compose button itself has no screenshot test; natural Android compile/JVM CI is the integration check.
- **Natural CI:** code head `45a3264319dfb16cd43a6912a354c1625054c8dc` triggered push run [#309](https://github.com/Batmancpu/CurrentLens/actions/runs/42449182759) and PR run [#310](https://github.com/Batmancpu/CurrentLens/actions/runs/42449186429); both were in progress at this documentation sync. On #309, SDK/Gradle/WordNet steps passed and compile/JVM tests were running; #310 was still generating the offline WordNet database. No pass or artifact is claimed yet. Parent head `748683f156ea05f650a49603f86750e8902551ed` passed #307/#308; PR artifact [#13602938183](https://github.com/Batmancpu/CurrentLens/actions/runs/42445244470/artifacts/13602938183) is 39,111,930 bytes (SHA-256 `e5767c219b24cff2526bba8e4c94676e0a12cfe8a390a95738a5be31f4078138`). Parent runs do not validate this Search change. No manual dispatch/rerun.
- **Workflow / release:** integration workflow keeps build/test permissions read-only and Release write isolated to main pushes. Main's workflow remains push-only; no main-target PR validation is inferred. Latest published testing release remains `v0.2.0-build-116`; no Release was published.
- **Unverified:** OPPO Reno10 Search visuals, TalkBack state announcement, font scaling, keyboard/IME interaction, animation, launch stability, frame timing/jank, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**.

## Coverage and paired-cost matrix — Search filter action
| Screen/component | Material tier / reason | Effect and capture scope | Reuse / fallback | Accessibility and tests | Measured result |
|---|---|---|---|---|---|
| Search header “Filters” action | Tier 2 interactive glass; a primary filter-sheet entry point must be discoverable and consistent with the surrounding Search control material | Static fill/rim only inside the existing Search header Prismal surface; no new capture | Existing `GlassSurfacePolicy`, Clear↔Frosted tokens and Reduce Transparency floors; high-contrast static fallback | Button role, active-state description, visible filter count, 48dp minimum; JVM alpha policy tests | No new live pass; device metrics UNVERIFIED |

**Next:** inspect #309/#310's exact-head job results, full logs and artifacts. If they pass, continue the remaining whole-app transient-control/Today-loading consistency audit; keep all device and performance measurements explicitly unverified.

---

# Live implementation batch — Appearance editor supporting-material parity
**Audit:** 8 November 2026, 09:27 IST  
**Active branch / PR:** `black-box-bihar-news@7ee0976c18c2987854f870b51d5531e9b9185fe6`; PR #34 remains open/draft against `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. The active line is **131 commits ahead / 0 behind main** and **74 ahead / 0 behind** PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.

- **Freshness gate:** all 41 remote branches were enumerated (page 1: 41; page 2: 0), and all 38 PRs were reviewed (24 open, 14 closed). The current owner-selected integration line remains the latest coherent UI baseline; the complete current branch/head/timestamp/PR map is recorded in `docs/BRANCH_CONSOLIDATION.md`. PR #37's RowScope Search-clear repair is already present in the active `MainActivity.kt`. PR #38 is newer parser-only work; its exact head passed natural run #306, but it remains separate because it overlaps the `NewsRepository` parsing/concurrency work in PRs #32/#33. PR #36 (IST date rollover) and #35 (diagnostic-read bounds) remain independent. PRs #30/#31 are already represented. The divergent `brrrNEWS`, old `ui/liquid-glass-performance`, and `redesign/v0.3-search-reader` branches are not newer UI baselines.
- **Source review:** re-read the full `AppearanceGlassEditor`, `GlassSettingsScreen`, `SupportingGlassCard`, shared `GlassSurfacePolicy`, root appearance state and complete `MainActivity.kt`. The Appearance editor's primary controls card was still a plain Material `surfaceVariant` card, while its explanatory panel and the rest of You/Stats already used the shared supporting material.
- **Implementation:** replaced only the Appearance editor's plain card wrapper with `SupportingGlassCard`. The control panel now shares the same 22dp continuous geometry, theme-aware near-opaque content surface, Clear↔Frosted tint and cached rim/tint brushes as the rest of the supporting system. The slider, Clear/Balanced/Frosted presets, Reduce Transparency switch, child-local preview state and save-on-release/preset semantics are unchanged.
- **Material hierarchy:** the editor controls use tier 3 supporting glass; the miniature Search/navigation preview below remains the screen's single tier 1/2 live `PrismalTransientSurface`; the article sample remains a calm, readable supporting content surface. No second live blur panel was introduced.
- **Paired optimization:** one settings-only card now draws the existing static tint gradient and rim through `SupportingGlassCard`; its brushes are remembered by theme/intensity. No additional Prismal surface, backdrop capture/readback, blur, refraction, shader, graphics layer, image decode, dependency, timer, network request or persistence path. This is a source-level cost assessment, not a measured GPU/frame/memory/battery improvement.
- **Accessibility / state:** the existing slider and presets retain 48dp minimum interaction sizing and descriptive semantics; Reduce Transparency continues to remove live blur/refraction/adaptive sampling. Dragging remains editor-local; app-wide preference persists only on release or preset selection. No quiz, reader, navigation, filter or content semantics changed.
- **Tests:** no new policy was introduced. Existing `GlassSurfacePolicyTest` covers supporting-surface opacity, Clear/Frosted endpoints, non-finite fallback, preview contrast and supporting-card geometry. The new head still requires its own natural CI.
- **Parent exact-head CI:** natural push run [#299](https://github.com/Batmancpu/CurrentLens/actions/runs/42441329214) and pull-request run [#300](https://github.com/Batmancpu/CurrentLens/actions/runs/42441332518) both passed on parent `7ee0976c18c2987854f870b51d5531e9b9185fe6`. WordNet generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and APK upload succeeded; artifacts #13601299645 and #13602145334 were each 39,112,177 bytes. Publish jobs skipped. These validate the parent, not this new card change.
- **Latest separate CI:** run [#306](https://github.com/Batmancpu/CurrentLens/actions/runs/42443089324) passed on PR #38 parser head `adade1ed8494c66b7e567de87f61f3dac3da7788`; artifact #13602895434 (39,112,397 bytes). It is not a test of the UI branch.
- **Actions / workflow:** 306 runs fully paginated (100 + 100 + 100 + 6): 181 success, 65 failure, 60 cancelled; no queued or in-progress runs. The integration workflow keeps build/test permissions read-only and Release write isolated to main pushes. Main's own workflow remains push-only; no future PR-to-main validation is inferred from a branch push.
- **Release:** latest published testing prerelease remains `v0.2.0-build-116` (6 November 2026); no Release was published by these runs.
- **Device / performance:** OPPO Reno10 launch, Appearance screenshots, TalkBack, font scaling, Reduce Motion, IME/safe-area behaviour, frame timing, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. No smoothness or battery improvement is claimed.
- **Next:** inspect the natural push/PR runs on the new exact head; after they complete, continue the remaining whole-app consistency pass. No merge, main write, retarget, manual dispatch/rerun, Release publication, package change, source-branch mutation or branch deletion.

## Coverage and paired-cost matrix — Appearance editor
| Screen/component | Material tier / reason | Effect and capture scope | Reuse / fallback | Accessibility and tests | Measured result |
|---|---|---|---|---|---|
| Appearance control panel | Tier 3 supporting glass; an interactive settings panel should share the app's material language without becoming a large live blur | One static tint/rim on the existing settings card; no new live capture | Shared `SupportingGlassCard`, `GlassSurfacePolicy`, remembered brushes; near-opaque content fallback | Slider/presets remain 48dp; Reduce Transparency switch and existing policy tests | No new live pass; device metrics UNVERIFIED |
| Appearance miniature preview | Tier 1/2 live control glass; demonstrates Clear↔Frosted navigation/search | One existing compact `PrismalTransientSurface`; static child controls and article sample | Shared intensity tokens; no nested glass consumer | Existing slider, preset and reduced-transparency semantics | No new capture/readback; device metrics UNVERIFIED |

---

# Live implementation batch — Unified glass filter-control hierarchy
**Audit:** 8 November 2026  
**Active branch / PR:** `black-box-bihar-news`; code commit `a15984673f0b2c8b3cc032a38cfc7d08cdddc87b`; PR #34 remains open/draft against `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`.  
**Source baseline:** `black-box-bihar-news@df5a75cff6fe9e75570d5daec64cf3d1db8ae726`; the implementation is a direct child. The selected line is 130 commits ahead / 0 behind main and 73 ahead / 0 behind PR #16's corrected background-only Prismal/stability baseline `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.

- **Freshness gate:** 41 remote branches and 38 PRs (24 open, 14 closed) were fully paginated. No newer coherent UI baseline supersedes the owner-selected integration line. `brrrNEWS` is a divergent sibling (4 ahead / 35 behind); its earlier supporting-glass implementation is superseded by the more complete current `GlassSurfacePolicy` and shared card system. PR #37's Search RowScope compile repair is already present. PR #36's IST-midnight feed refresh and PR #35's bounded diagnostic reads remain separate changes; PR #38's RSS parser proposal is unported after its natural test failure. PRs #30 (48dp targets) and #31 (off-main appearance restore) are already represented in the active line.
- **Implementation:** introduced one reusable `GlassFilterPill` for Search's filter sheet, Saved, Reading Stats, Quiz & Practice Stats, and Appearance presets. Selected and unselected fills/rims track Clear↔Frosted intensity; Reduce Transparency applies stronger static contrast floors. The Search “Filters” action now has a 48dp minimum height.
- **Accessibility:** each choice pill exposes RadioButton role and selected state, has a minimum 48dp width and height, and keeps full labels in horizontally scrollable rows rather than truncating them. Appearance presets use the live preview intensity, not a stale committed value.
- **Paired optimization:** the pills use static tint and border inside the already-existing `GlassFilterGroup` / `PrismalTransientSurface`; no new Prismal surface, backdrop capture/readback, blur, refraction, shader, graphics layer, image decode, dependency, network request or persistence path was added. No per-chip live glass effect was introduced.
- **Tests:** `GlassSurfacePolicyTest` now covers Clear/Frosted endpoints, selected-vs-unselected hierarchy, reduced-transparency floors, non-finite preference fallback and the shared 16dp pill geometry.
- **Natural CI:** exact code head `a15984673f0b2c8b3cc032a38cfc7d08cdddc87b` triggered push run #297 and PR run #298; both were in progress at this documentation sync, so no pass is claimed. The parent head `df5a75cff6fe9e75570d5daec64cf3d1db8ae726` passed natural runs #295 (push) and #296 (PR): WordNet builder 4/4, 151,456-word offline database, `:app:testDebugUnitTest`, `:app:assembleDebug` and APK artifact upload. Parent artifacts: #295 `13600453110` (39,111,082 bytes; SHA-256 `56a1e963541407d28c87dac55cf77761e4dee58fbe2805ad5d8ee29059842c80`); #296 `13600826632` (39,111,083 bytes; SHA-256 `47de6ba07042d58f1539f28689b567f0c72f9cc610faed96dc759b6f3165e3cb`). Those runs validate the parent, not the new filter code.
- **Actions / workflow:** 296 runs were fully paginated (100 + 100 + 96; page 4 empty): 177 success, 64 failure, 55 cancelled. Before #297/#298 there were no queued or in-progress runs; the two new exact-head runs are the only active work observed. The integration workflow keeps build/test read-only and Release write isolated to main pushes; no manual dispatch/rerun was used.
- **Release:** latest published tag remains `v0.2.0-build-116`; no Release was published.
- **Device / performance:** OPPO Reno10 screenshots, TalkBack, font scaling, Reduce Motion, IME/safe-area behaviour, frame timing, GPU overdraw, allocation/memory, thermal and battery remain **UNVERIFIED**. Source inspection establishes only that this batch adds static fills/rims and no new live render pass; no measured performance gain is claimed.
- **Next:** inspect the natural #297/#298 results on this exact code head, then continue the whole-app filter/secondary-screen consistency audit. No merge, main write, retarget, branch deletion, source-branch mutation or Release publication.

## Coverage and paired-cost matrix — current filter batch

| Screen/component | Material tier / reason | Effect and capture scope | Reuse / fallback | Accessibility and tests | Measured result |
|---|---|---|---|---|---|
| Search filter sheet | Interactive control glass; active filter choices need an unmistakable selected state | Static pill fill/rim inside its existing single transient glass surface | Shared `GlassFilterPill` and `GlassSurfacePolicy`; Reduce Transparency raises fill/rim | RadioButton role, selected state, 48dp minimum, horizontal scrolling; policy JVM tests | No new live effect; device metrics UNVERIFIED |
| Saved / Reading Stats / Quiz Stats filters | Interactive control glass; distinguish Date, GS paper and Subject without adding more glass groups | Static pill fill/rim inside each existing shared `GlassFilterGroup` | Same helper and intensity policy; full option labels remain horizontally scrollable | RadioButton role, selected state, 48dp minimum; policy JVM tests | No new capture/readback; device metrics UNVERIFIED |
| Appearance presets | Interactive control glass; Clear/Balanced/Frosted should match the live slider preview | Static fill/rim inside the existing Appearance control card | Explicit preview intensity and reduced-transparency state; no extra backdrop consumer | RadioButton role, selected state, 48dp minimum; policy JVM tests | No new live effect; device metrics UNVERIFIED |

---

# Live implementation batch — Shared shell optical clarity and compact-mode source lifecycle
**Freshness audit:** 8 November 2026, 07:41 IST; **latest code/test-fix head:** 8 November 2026, 07:50 IST; **exact-head CI completed:** 08:23 IST.

- **Active branch / PR:** `black-box-bihar-news@400496d3a24e7ed9e1f2d4b62c964aa07ebb7348`; PR #34 remains open/draft against `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. Current lineage: 128 ahead / 0 behind main and 71 ahead / 0 behind PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.
- **Commit chain:** code change `c6deffb96a89d56642374456e92477fbabb8e4b0` (parent `c7d50bb2`); documentation sync `12f5831122067cf7996b14d8b8aed4d8dc3bbd4d`; evidence-backed test correction `400496d3a24e7ed9e1f2d4b62c964aa07ebb7348` (current head, parent `12f58311`).
- **Freshness gate:** 41 remote branches and 38 PRs (24 open, 14 closed) were fully re-enumerated. PR #38 is a direct child of former head `c7d50bb2`, now 2 ahead / 3 behind the active head from that merge base; its diff is only `NewsRepository.kt` and `NewsRepositoryTest.kt`. It is not a newer UI baseline. PR #37's RowScope repair is already present; PR #36 date rollover, #35 diagnostic reads, #27 editorial gate, #32/#33 feed parsing and #25/#26 publisher security remain separate. PRs #29/#30/#31 and PR #13's conservative adaptive-contrast policy are represented.
- **UI / cost change:** `HomeGlassHeader` now uses a shared 9dp-blur / 3dp-height / 4dp-amount profile, chromatic aberration 0.0015, specular 0.22, shadow 0.16, inset 0.04 and tint multiplier 0.10 (previously 13dp, 4/6dp, 0.003, 0.28, 0.20, 0.05 and 0.13). The existing single Prismal header surface and one root background-only backdrop remain. This is a parameter reduction, not a measured performance claim.
- **Navigation lifecycle:** hidden tab-glyph capture and merged droplet source are instantiated only inside the expanded navigation branch; compact mode retains the same single active-destination pill and disposes those unused sources. No second bar, extra sampler/readback, new Prismal surface or root capture.
- **Tests:** `GlassOpticsPolicyTest` checks the bounded header profile. Natural run #289 on `c6deffb9` and run #291 on `12f58311` each failed one assertion because the test incorrectly compared 9dp header blur with the 5.5dp resting droplet blur. The production profile was not changed; test fix `400496d3` compares 9dp with the header's prior 13dp value.
- **Exact-head natural CI:** PR run [#294](https://github.com/Batmancpu/CurrentLens/actions/runs/42438138680) and push run [#293](https://github.com/Batmancpu/CurrentLens/actions/runs/42438135082) both **passed** on exact head `400496d3`. Both completed WordNet builder 4/4, 151,456 unique words (50,950,144-byte database), `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload. PR artifact [13600736327](https://github.com/Batmancpu/CurrentLens/actions/runs/42438138680/artifacts/13600736327), 39,111,083 bytes, expires 22 November 2026 02:22:10 UTC. Push artifact [13599529948](https://github.com/Batmancpu/CurrentLens/actions/runs/42438135082/artifacts/13599529948), 39,111,082 bytes, expires 22 November 2026 02:23:30 UTC. Publish jobs skipped; only Node.js 20/action-runtime deprecation warnings.
- **Separate PR #38 failure:** natural run [#288](https://github.com/Batmancpu/CurrentLens/actions/runs/42437757601) failed two `NewsRepositoryTest` cases at parse call sites (lines 26 and 49) with `java.lang.RuntimeException`; the Actions log does not expose the underlying cause. No APK artifact was uploaded. This is outside the UI branch and was not ported.
- **Actions / workflow / Release:** 294 runs fully paginated (100 + 100 + 94; page 4 empty): 175 success, 64 failure, 55 cancelled, 0 in progress, 0 queued. Integration `.github/workflows/build-apk.yml` uses read-only build/test permissions and a separate main-push-only Release-write job; main's workflow remains push-only. Latest published Release remains `v0.2.0-build-116`; no Release was published.
- **Device / next:** OPPO Reno10 launch, header contrast, compact-nav morph, TalkBack, font scaling, Reduce Motion, safe areas, frame/jank, GPU, memory, thermal and battery remain **UNVERIFIED**. Next: continue the whole-app shared-shell/transient-control audit, while keeping physical-device claims explicitly unverified. No manual dispatch/rerun, merge, main write, Release publication, package change or source-branch mutation.

---

---

# Live implementation batch — Quiz controls and current branch audit
**Audit time:** 8 November 2026, 07:32 IST

- **Active branch:** `black-box-bihar-news`; source baseline `94a1e9a3a6c0cc92abc41cfe332ebf821ed1e351`, parent of the Quiz implementation. Source baseline was **123 ahead / 0 behind** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` and **66 ahead / 0 behind** PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. The code commit is `b06557acb2923d62c8c4f18e666b584f89243242`; this documentation sync is its direct child. PR #34 remains the only draft consolidation PR; no merge or main write.
- **Freshness gate:** 40 remote branches and 37 PRs were re-enumerated (23 open, 14 closed). Every branch head, commit timestamp/message and PR relationship is recorded in `docs/BRANCH_CONSOLIDATION.md` under the 8 November 07:32 IST live inventory. No newer coherent UI baseline supersedes the owner-selected integration line. PR #27 has a later editorial-only head; PR #36 is a separate Asia/Kolkata date/index change; neither is a replacement UI baseline.
- **Relevant branch dispositions:** PR #37's RowScope compile repair is already represented by `black-box-bihar-news` commit `6f777f8777b3168383361dc79109b3d1d75b612b`; the later PR #37 branch is documentation-only relative to that fix. PR #36's `AsiaKolkataDayPolicy.kt` and 11-line `MainActivity.kt` rollover change remain deferred for separate reconciliation (its head is 3 ahead / 5 behind from merge base `4a7a8f8c`). PR #35 is bounded startup diagnostics, not Quiz/UI work. PRs #32/#33 both change `NewsRepository.kt` and require joint review; PRs #25/#26 require joint publisher-resource security review. PRs #29/#30/#31 are represented in the active branch. PR #16 is an ancestor, PR #13's conservative adaptive-contrast policy is integrated, and PR #18's read-only workflow intent is represented. Stale `ui/liquid-glass-performance` and `redesign/v0.3-search-reader` remain excluded.
- **Implementation:** Quiz answer buttons now use a static, glass-derived tint/rim hierarchy: idle, selected, correct, incorrect and submitted-disabled. Correct/incorrect states are explicitly announced through `stateDescription`; touch targets are 52dp. Success colour is theme-aware. The existing five question cards and explanations remain standard high-contrast content surfaces.
- **Quiz action area:** the existing single Prismal footer now includes a 3dp determinate answered-count indicator. It derives directly from the existing local selection map; no polling, animation loop, persistence or extra glass surface. Lazy question items now have stable keys and a shared content type.
- **Paired optimization:** no per-option blur/refraction, new Prismal surface, backdrop capture/readback, shader, graphics layer, image decode, dependency, network call or storage change. The only added drawing is each existing button's static fill/border plus one small progress track; question cards remain non-glass. No GPU/frame/battery gain is claimed from source inspection.
- **Tests:** new `QuizOptionStylePolicyTest` covers state mapping, selected/feedback edge hierarchy, reduced-transparency alpha floors and alpha bounds. No Compose screenshot test was added; the natural Android compile/JVM run is the direct integration check. Quiz attempt persistence remains submit-only.
- **Parent exact-head CI:** natural push run [#282](https://github.com/Batmancpu/CurrentLens/actions/runs/42432088153) and PR run [#283](https://github.com/Batmancpu/CurrentLens/actions/runs/42432090573) both passed on parent `94a1e9a3`. WordNet builder 4/4, 151,456-word offline database, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload succeeded; Release jobs skipped. Artifacts: push `13597982220` (39,108,166 bytes, SHA-256 `d6fb26eebbf2a536f7cdcd0345cb1232da3000839e050814f3dcbfb7bf4e3728`) and PR `13598101806` (39,108,165 bytes, SHA-256 `516771f2caf5aa91513fca7e48e64ee31b7ab3e37488b3b8a08eba11fb8b4a10`). Full PR job log and push job steps were inspected; only action-runtime/Node URL-parser deprecation warnings appeared. These runs validate the parent, **not** the Quiz commit.
- **New-head CI:** the Quiz code commit triggered natural push/PR workflows. No manual dispatch or rerun. Its exact-head result must be checked after completion; the parent artifacts above are not substitutes.
- **Release/device:** latest published Release remains `v0.2.0-build-116`; no Release was published. OPPO Reno10 launch, screenshots, TalkBack, font scaling, dark/light visual contrast, selection/IME, Reduce Motion runtime, frame timing, GPU overdraw, allocation/memory, thermal and battery remain **UNVERIFIED**.
- **Next:** inspect exact natural CI for the final docs-synced head, then continue a whole-app pass on shared header/filter/Reader safe-area consistency. No merge, retarget, branch deletion or source-branch mutation.

## Live implementation update — 8 November 2026, 06:23 IST

- **Authoritative source:** `black-box-bihar-news@31e3bf753f91002b26b1e02e281899d10d0e590f`; base `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. Current comparison: **122 ahead / 0 behind main** (merge base main), and **65 ahead / 0 behind** PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. PR #34 is the sole open/draft consolidation PR.
- **Freshness gate:** all **40 remote branches** were enumerated (page 1: 40; page 2: 0), and all **37 PRs** were reviewed (23 open, 14 closed). The selected branch is the newest coherent UI/integration lineage, not a timestamp-only choice. PR #37's RowScope repair is already present in current SearchMorphField; PR #36's IST-midnight Today refresh remains a separate, diverged MainActivity/date-policy change (3 ahead / 4 behind) and is not copied. PR #35 is diagnostics-only; #30's 48dp policy and #31's off-main appearance restore are already represented; #29 persistence is represented. PR #27 is editorial-only; #25/#26 require a joint publisher-resource security review; #32/#33 jointly touch NewsRepository; #24 is based on divergent brrrNEWS. The old liquid-glass-performance and v0.3 branches remain stale.
- **Natural CI:** all **281 Actions runs** were paginated (100 + 100 + 81): 168 success, 61 failure, 52 cancelled; no queued or in-progress runs. Latest exact-head PR run [#281](https://github.com/Batmancpu/CurrentLens/actions/runs/42427714236) and push run [#280](https://github.com/Batmancpu/CurrentLens/actions/runs/42427710695) both passed on `31e3bf753f91002b26b1e02e281899d10d0e590f`. Both completed checkout, Java, signing-key restore, Android SDK/platform, Gradle, WordNet builder 4/4, 151,456-word offline dictionary generation (50,950,144 bytes), `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload. Full logs were inspected; PR artifact `13596471410` (39,107,655 bytes), push artifact `13595943420` (39,107,647 bytes). Release jobs skipped.
- **Workflow / Release:** integration workflow `.github/workflows/build-apk.yml` keeps build/test read-only and Release write isolated to main pushes; it naturally validates pull requests targeting black-box. Main's workflow remains push-only, so this is not a PR-to-main validation claim. Latest published Release remains `v0.2.0-build-116` (6 November 2026); no new Release.
- **Implementation in this pass:** Appearance's miniature Search control and selected navigation state now visibly track Clear↔Frosted intensity; reduced-transparency mode raises the preview's control opacity/rim while the outer Prismal surface remains the only live-glass preview. Search preview target height is 48dp. Slider preview remains child-local and app-wide persistence still commits only on release or preset selection.
- **Cost / tests:** no new Prismal surface, backdrop capture/readback, blur/refraction, shader, graphics layer, image decode, dependency, timer, network request or persistence path. Three bounded alpha interpolations reuse `GlassSurfacePolicy`; static preview brushes/surfaces remain cached or simple draw parameters. Added pure JVM endpoint, monotonicity, reduced-transparency and non-finite fallback tests. New head awaits its own natural CI.
- **Unverified:** OPPO Reno10 visual preview, launch, TalkBack, font scaling, Reduce Motion, IME, frame timing, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. No measured performance improvement is claimed.
- **Next:** inspect the exact new-head natural push/PR runs and full logs before another screen batch. No main write, merge, manual dispatch/rerun, Release publication, source-branch mutation or deletion.

---

## Live implementation update — 8 November 2026, 05:24 IST

- **Authoritative source:** `black-box-bihar-news@d9a4d1eac035172da0f8d7c40a984eb6c40c1b8b`; base `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. Compare: **121 ahead / 0 behind main**, merge base main; **64 ahead / 0 behind** PR #16's corrected stability/Prismal head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. PR #34 is the single open/draft consolidation PR from this branch to main. No other branch is being modified.
- **Freshness gate:** all **40 remote branches** were enumerated (page 1: 40; page 2: 0), and all **37 PRs** were reviewed (23 open, 14 closed). The current branch remains the newest coherent UI/integration lineage. PR #27 has a later commit timestamp but is 39 ahead / 22 behind from an older merge base and changes only UPSC editorial policy; it is not a newer UI baseline. PR #37's RowScope repair is already present in current SearchMorphField; its source branch remains untouched. PR #36's IST-midnight refresh is a separate MainActivity/date-index change on a diverged older base and is deferred for its own reconciliation. PR #35 is diagnostics-only; #32/#33 overlap NewsRepository; #25/#26 need a joint publisher-resource security review. #29/#30/#31 changes are already represented. `brrrNEWS` is a divergent sibling; the old liquid-glass-performance and v0.3 branches remain stale.
- **Natural CI history:** all **279 Actions runs** were paginated (100 + 100 + 79): 166 success, 61 failure, 52 cancelled; no queued or in-progress run. Latest exact-head runs are [#279 pull request](https://github.com/Batmancpu/CurrentLens/actions/runs/42423643681) and [#278 push](https://github.com/Batmancpu/CurrentLens/actions/runs/42423639735), both successful on the exact source SHA above. Both completed checkout, Java, signing-key restore, Android SDK/platform setup, Gradle, WordNet builder tests (4/4), 151,456-word offline database generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and APK artifact upload. Full logs for both jobs were inspected; #279 reports `BUILD SUCCESSFUL in 1m 30s`, #278 in 1m 28s. Release jobs were skipped.
- **Artifacts:** PR run #279 artifact [13594284223](https://github.com/Batmancpu/CurrentLens/actions/runs/42423643681/artifacts/13594284223), 39,107,398 bytes, SHA-256 `1959a089d31d4a96ee851749d5a3753b9b42e1a2283b381b50dd2462d2762803`, expires 21 November 2026 23:07:41 UTC. Push run #278 artifact `13594681752`, 39,107,393 bytes, SHA-256 `5fb4503525d5fa4f8a9b77213cc07f196345ae41cd7ae243ce3e746f4ad17000`. These validate the parent head `d9a4d1e`, not this new reduced-motion commit.
- **Workflow / Release:** `.github/workflows/build-apk.yml` on the integration branch uses read-only build/test permissions and a separate main-push-only Release-write job; natural PR validation covers the black-box branch. Main's workflow remains push-only, so do not describe this as PR-to-main validation. Latest published Release remains `v0.2.0-build-116`; no new Release was published.
- **Implementation in this pass:** centralised the existing lifecycle-updated system Reduce Motion state in `LocalGlassReduceMotion`. Home destination `AnimatedContent` now snaps its crossfade when Reduce Motion is enabled; Reader chrome uses the same state and zero-duration transitions; the definition popover snaps scale/content-size and keeps only a short 90ms opacity fade. The native text-selection menu, dictionary bridge, anchored placement, Reader scroll policy and completion semantics are unchanged.
- **Cost / verification:** no new Prismal surface, backdrop capture/readback, blur/refraction, shader, graphics layer, bitmap decode, dependency, timer, network request or persistence path. One CompositionLocal carries an existing lifecycle state; no per-pixel state or polling was added. This is a code-level cost assessment, not a measured performance claim. The new commit awaits its own natural CI.
- **Unverified:** OPPO Reno10 launch, visual fidelity, selection/IME collision, TalkBack, font scaling, Reduce Motion runtime, scroll smoothness, frame timing, GPU overdraw, allocation/memory, thermal and battery remain **UNVERIFIED**. CI success does not prove device launch stability or animation performance.
- **Next:** inspect the exact new head's natural CI before another UI change; then continue the whole-app shared-header/transient-control audit and separately reconcile PR #36's IST rollover if still appropriate. No merge, main write, manual dispatch/rerun, Release publication, branch deletion or source-branch mutation.

# Liquid Glass UI Roadmap

**Status:** Cohesive Prismal coverage spans the shared shell, full/compact navigation, Search, Explore filters, Reader auto-hide, definition and completion controls, You/Saved/stats supporting materials, Appearance preview and Quiz transient chrome. The latest shell pass reduces the shared header's optical intensity and limits hidden tab-glyph/merged droplet sources to expanded navigation; no measured device-performance claim is made.
**Feature branch:** `black-box-bihar-news` — sole active integration/UI branch by explicit owner direction.
**Source baseline for this pass:** `black-box-bihar-news@c7d50bb2bafe29a43c106e4fb71b607e9830adea`; code commit `c6deffb9`, docs sync `12f58311`, test correction `400496d3`.
**Latest exact active-branch validation:** natural PR run #294 and push run #293 both passed on `400496d3`; WordNet 4/4, 151,456-word offline database, JVM tests, APK assembly and artifact upload all passed.
**Actions history:** 294 runs fully paginated (100 + 100 + 94): 175 success, 64 failure, 55 cancelled; no queued or in-progress runs.
**Workflow / Releases:** integration workflow has read-only build/test and isolated main-push Release write; main remains push-only. Latest published Release `v0.2.0-build-116`; no Release published by runs #278/#279.
**Branch graph:** 41 remote branches; 38 PRs (24 open, 14 closed). PR #34 is the only consolidation PR this task updates; PR #38 remains a separate failing RSS parser proposal.
**Safety / device status:** no main write, PR merge/retarget, workflow change, manual dispatch/rerun, Release publication, package identity change, source-branch mutation or deletion. OPPO Reno10 launch, visual, accessibility, motion, Perfetto/frame, GPU, memory, thermal and battery evidence remains **UNVERIFIED**.

## Current iteration — Reduced Motion consistency — 8 November 2026, 05:24 IST

- **State source:** `LocalGlassReduceMotion` exposes the existing lifecycle-updated root preference to nested screens. It does not introduce a second preference or polling source.
- **Destination transition:** Home's shared `AnimatedContent` crossfade snaps when system animations are disabled; normal motion retains the existing 190ms/120ms fade.
- **Reader chrome:** toolbar/completion visibility uses the same state and resolves to zero-duration transitions when Reduce Motion is enabled.
- **Definition overlay:** spatial scale and content-size motion snap; opacity remains a restrained 90ms fade under Reduce Motion and 115ms normally. Anchored placement, selection bridge, native ActionMode and Copy/Select/share behaviour remain unchanged.
- **Paired optimization:** no additional glass surface, capture/readback, shader, blur/refraction pass, graphics layer, image decode, dependency or frame callback. Motion work is removed rather than replaced by a heavier effect.
- **Tests:** no dedicated Compose UI test was added for this animation-spec wiring; exact-head natural CI must compile the changed composables and run the existing JVM suite. Parent runs #278/#279 do not validate this new commit.
- **Device checks:** system Reduce Motion behaviour, TalkBack, font scaling, native selection, visual transitions and frame timing remain UNVERIFIED on OPPO Reno10.

## Historical branch freshness audit — 7 November 2026, 13:00 IST (superseded by the current run record)

- All 31 remote branches were enumerated (page 1: 31; page 2: 0), all 13 open PRs and 13 closed PRs reviewed, and relevant full diffs/commit comparisons inspected. The exact 31-head table and disposition are in docs/BRANCH_CONSOLIDATION.md §10.
- black-box-bihar-news@3a08bb95 remains the owner-selected active integration branch: 99 ahead / 0 behind main and 42 ahead / 0 behind PR #16. It is a direct descendant of PR #16's corrected background-only Prismal/stability head. The branch was rechecked immediately before the implementation commit.
- brrrNEWS@e6fcfc25 is a divergent sibling (4 commits unique to brrrNEWS and 5 unique to black-box; common ancestor e411c394). Its GlassSurfacePolicy.kt and test are byte-identical to black-box; Reader saved-position restoration on IO is already present in black-box. Its workflow/branding/documentation changes and PR #24's separate editorial policy are not adopted. The explicit active-branch instruction remains authoritative.
- PR #25 and #26 are open publisher-image/subresource privacy changes. Their MainActivity hunks are limited to NewsCard/Reader resource paths and do not overlap this QuizScreen batch; they remain untouched pending a separate shared-host/security reconciliation. PR #24 is editorial-only and remains on brrrNEWS. No source branch was merged, deleted or modified.
- PR #19, #20 and #23 remain closed/merged into black-box only. PR #16 remains open on main; PR #18's compatible read-only workflow intent is represented in the active branch. The old ui/liquid-glass-performance and redesign/v0.3-search-reader branches remain excluded.
- The active workflow is read-only for build/test and only grants contents: write to the separate main-push Release job. Main still has no pull_request trigger; a future draft PR to main must not be described as PR-to-main CI-validated until that base workflow is reconciled.
- Run #203 validates the exact pre-Quiz head; #204 validates PR #26 only and #202 validates PR #25 only. No queue remains. OPPO runtime, launch stability, visual motion, TalkBack, Reduce Motion, Perfetto/frame timing, GPU overdraw, allocation/memory, thermal and battery remain UNVERIFIED.

## Historical branch audit and reduced-transparency batch — 7 November 2026, 11:38 IST (superseded by the live audit above)

- The complete remote inventory is 28 branches (page 1: 28; page 2: 0). **black-box-bihar-news** is the freshest coherent consolidation line: 97 ahead / 0 behind **main**; 40 ahead / 0 behind PR #16's corrected stability head.
- **brrrNEWS** is a diverged sibling (4 ahead / 3 behind; merge base e411c394e8d48b3f547b6d6fb9039a8935bd05eb). Its GlassSurfacePolicy.kt and test are byte-identical to this branch; MainActivity.kt differs only in formatting around the restored Reader-position state. Do not switch to or merge the sibling: its remaining differences include workflow/documentation/app-build metadata.
- PR #24 (fix/editorial-policy-exception-guard-2026-11-07) remains open against brrrNEWS, not this branch. Its separate editorial policy change is not part of this visual batch and has not been ported. PR #11 still targets an older black-box-bihar-news base SHA; do not merge it blindly.
- Rechecked source equivalence: ReadHistoryPolicy.kt and its test match PR #22's branch; ArticleCachePolicy.kt and ArticleProvenancePolicy.kt match PR #11's branch; FeedLinkPolicy.kt and its test match PR #12's branch. Current adaptive luminance sampling remains the conservative 4s/6s policy; PR #13's branch is 1.5s/2.5s and is not copied.
- Open PRs: #24, #22, #21, #18, #17, #16, #13, #12, #11, #8 and #6. Closed/merged into consolidation: #19, #20 and #23. No PR from black-box-bihar-news to main exists yet; no merge or branch deletion was performed.

### Appearance batch

- Added a persisted local reduceTransparency preference, defaulting to off. The Appearance screen exposes an accessible 56dp row with a labelled Switch.
- GlassRenderingPolicy keeps the normal Prismal material unchanged; when enabled it sets live blur/refraction/chromatic aberration to zero, disables depth/shadow, reduces specular intensity and raises interactive-surface tint to at least 0.24 for clearer, more opaque controls. Applied to the shared header, Explore rail, Search filter capsule and morph, full/compact tab material, Reader toolbar/completion action and definition popover.
- The existing single background-only root backdrop remains the only shared page source; no new capture, readback, bitmap decode, dependency or graphics layer was added. The existing 8×8 adaptive luminance probe is paused while the preference is enabled; normal-mode cadence remains 4s Android 12+ / 6s older.
- Appearance slider movement now updates only a local preview state; the app-wide glassAlpha preference is committed on drag completion or preset selection, avoiding root-state persistence/recomposition on every slider tick.
- Added pure JVM tests for reduced material parameters and sampler suspension. The last successful natural run (#200) validates parent SHA cfe0b3e8319374df5cb29423f04a79c77d4fe10e only; the new commit must receive its own natural run. No device screenshots, OPPO jank/Perfetto, battery or thermal measurements exist; those remain UNVERIFIED.
- Next: verify the exact new head's natural build and tests, then continue Saved/stats filter consistency and Quiz/Settings transient controls. Do not claim device performance improvement from policy code alone.

## Paired optimization matrix

| Component | Material/effect scope | Reuse and invalidation | Fallback/accessibility | Evidence |
|---|---|---|---|---|
| Shared shell, Search, filters and Reader chrome | Existing single background-only Prismal source; small transient surfaces only | One root capture; one hidden glyph source only while expanded; stable policy state | Reduce Transparency disables blur/refraction; Reduce Motion snaps; TalkBack and 48dp controls retained | Source audit; device cost UNVERIFIED |
| You, Saved, stats and quiz question cards | Static supporting tint/rim or opaque content surfaces | Theme/material brushes remembered; no per-card live blur | Near-opaque high-contrast surfaces; font scaling | Source audit; frame/GPU/memory UNVERIFIED |
| Quiz answer controls | Static button tint/border; no live glass effect | Pure state/alpha policy; stable LazyColumn keys/content type | 52dp target; selected + stateDescription; reduced-transparency raises tint/rim | Four JVM policy tests added in Quiz batch; natural CI pending |
| Quiz footer | One existing Prismal surface plus a 3dp determinate indicator | Progress derived from local selected-count; no timer/polling | Text count remains visible; buttons retain 48dp targets | Source audit; device smoothness UNVERIFIED |

## Product and material principles

Liquid Glass is a functional interaction material, not a universal card background. Use it to clarify navigation, toolbars, search/filter controls and transient actions. Keep article text, article cards, statistics, quiz questions and other dense information surfaces calm, high-contrast and easy to read.

Use one coherent material hierarchy:

1. **Primary floating glass:** navigation and compact Reader chrome; strongest restrained refraction/specular response.
2. **Interactive control glass:** search, category/filter rails, toolbars, contextual controls and popovers.
3. **Supporting surfaces:** quiet translucent tint, continuous corners and restrained edge/depth treatments on dashboard/stat destinations where useful; no per-card live blur.
4. **Editorial/content surfaces:** opaque or near-opaque readable surfaces for article text, cards, statistics and quiz questions.

Preserve the existing PrismalAGSL implementation. Do not create a parallel glass engine, recursively capture descendants, add dependencies, or disable all glass to work around a rendering defect. The root backdrop must remain a background-only sibling; the UI and glass surfaces stay outside the captured source.

## Verified branch selection and dependency register

A fresh complete inventory on 7 November 2026 at 09:30 IST returned **26 remote branches** (page 1: 26; page 2: 0). The repository default remains `main` at `73b8a0d1ba33503fede02db2c3c0af082d724a12`. The selected integration line is `black-box-bihar-news`, not a timestamp-only branch choice: before this batch it was `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`, 94 commits ahead / 0 behind main and 37 ahead / 0 behind PR #16. It descends from PR #16's corrected background-only Prismal/launch baseline and contains the merged PR #19 Reader, PR #20 read-only stacked CI, and PR #23 editorial-gate work. The supporting-surface change in this batch is based directly on that verified head; no other branch was modified.

The branch-by-branch head SHA, last commit timestamp/message, PR relationship and comparison treatment are recorded in `docs/BRANCH_CONSOLIDATION.md` under the 7 November 09:30 IST freshness audit. Important non-ancestor work was reviewed rather than blindly merged:

- PR #11's date/cache/provenance policy is present in current `ArticleCachePolicy`, `ArticleProvenancePolicy`, `FeedPublicationDatePolicy`, and repository code; its branch remains divergent (3 ahead / 94 behind) and is not merged wholesale.
- PR #22 / PR #8 read-history changes are represented by the current ordered 5,000-entry new-marker window plus preserved legacy unordered markers; the source branches remain divergent and are not merged.
- PR #13's original 1.5 s / 2.5 s adaptive probe cadence was not copied. The current single shared probe uses the documented 4 s / 6 s cadence and pauses outside foreground home/while scrolling; physical-device cost remains unverified.
- PR #6 / #21 editorial policies, PR #12 Atom link selection and PR #17 URL identity are represented by selectively ported current policies/tests. PR #18 / #20 workflow intent is already in the single current workflow.
- PR #16 is the integration ancestor. PR #19, #20 and #23 are merged into this integration branch only. PR #11 is still open against this branch but its head is divergent; do not merge it without re-review.
- Older `ui/liquid-glass-performance` and `redesign/v0.3-search-reader` remain stale; no branch was deleted.

Recheck every branch head and dependency before each future implementation commit. Never reset, force-push, overwrite or discard another branch's commits.

## Screen coverage matrix

| Screen / component | Material tier | Intended treatment | Current status |
|---|---|---|---|
| Shared home header | Primary floating glass | Clear translucent header, restrained edge/specular, stable height across destinations | Shared 9dp-blur / 3×4dp-refraction profile now reduces the dark-slab risk; OPPO contrast remains unverified |
| Four-tab navigation | Primary floating glass | One droplet bar with selected glyph aligned to its hit region | Hidden glyph capture and merged droplet source exist only in expanded state; compact mode releases both |
| Compact feed navigation | Primary floating glass | Morph the same bar into one active-destination pill on eligible feed scroll only | Implemented; state policy validated in runs #186 and #187; physical animation still needs device validation |
| Search morph field | Interactive control glass | Preserve tab-to-IME geometry/focus; trailing clear affordance only for non-empty expanded query | 48dp clear target and existing onClear/focus path integrated; filter clearing remains independent |
| Search results hierarchy | Plain status row + static control glass | One result/active-filter label beside the existing Filters action; no redundant inline live-glass slab | One 7dp blur/refraction panel removed; conditional filter sheet and morph field retained; device hierarchy unverified |
| Explore category rail | Interactive control glass | One shared capsule; selected fill/border and >=48dp targets | 48dp target, selected semantics and Geography filter integrated |
| Today / Explore / Search article cards | Editorial content | Continuous corners, clear typography, image-derived ambient colour; no live blur per card | Existing standard cards, single Coil request and same-bitmap palette |
| You dashboard | Supporting glass | Theme-aware translucent tint/rim, continuous 22dp geometry, single-line metric values | Static surface opacity preserves readability; shared Clear↔Frosted intensity now scales cached tint/rim tokens; no live blur; OPPO contrast unverified |
| Saved | One shared interactive Prismal filter group + editorial cards | Date/GS/subject rails share one 22dp rounded control surface; story cards remain calm, high-contrast content | `GlassFilterGroup` batch authored; one panel, three 48dp horizontal rails; selected filters and existing saved data semantics unchanged |
| Reading / Quiz Stats | One shared interactive Prismal filter group + supporting glass data cards | Date/GS/subject controls use one small live-glass group; metric/day/attempt values stay high contrast | Supporting cards use the same committed Clear↔Frosted tint/rim intensity; no live blur; summaries memoized |
| Reader toolbar | Primary floating glass | Back, source/date/status and compact Aa; direction-aware auto-hide plus short content-tap reveal while hidden | Implemented; tap preserves native links/selection; pinned during selection, definition, IME and accessibility states; device motion unverified |
| Reader progress | Minimal indicator | Reuse existing saved progress; no polling or animated readback | Implemented in this batch |
| Reader article canvas | Editorial content | Clear, high-contrast text; responsive max width and paragraph rhythm | Existing responsive CSS; keep non-glass |
| Reader completion action | Transient contextual glass | Compact, dismissible, only after true completion; hide with reader chrome and avoid definition/action-mode overlap | Implemented; physical safe-area/selection checks unverified |
| Definition popover | Interactive contextual glass | Anchored Prismal surface, full selected word and compact accessible actions | Existing anchored surface; title/actions improved in this batch |
| Quiz questions and explanations | Editorial content | Calm, high-contrast content cards; no glass behind dense question text | Continuous-corner standard cards; no live blur; quiz data semantics unchanged |
| Quiz answer controls | Interactive control glass (static tint/rim) | Distinguish idle/selected/correct/incorrect states with accessible feedback and 52dp targets | Static button fill/border only; no backdrop capture or per-option blur; policy tests cover states and reduced-transparency alpha |
| Quiz action dock | Primary transient glass | Keep progress and submit/done actions reachable without obscuring questions | Reuses the existing single Prismal footer; one small progress track reads selected-count state; no polling or extra surface |
| Quiz action controls | Interactive control glass | One compact glass identity/header and one safe-area-aware submission dock; keep Done/Submit discoverable | Implemented in the Quiz action-chrome batch; two small surfaces reuse the root backdrop |
| Appearance settings | Standard settings surface + one interactive control-glass preview | Clear↔Frosted slider/presets, four-destination preview, readable article sample and low-effects switch | One 5dp Prismal control preview plus a static SupportingGlassCard sample both reflect the preview intensity; slider state is child-scoped and commits app-wide only on release; device validation pending |
| Search filter sheet | Interactive transient glass | One rounded Prismal surface behind the active topic/GS/subject controls; filter state and reset semantics remain unchanged | Shared 5dp transient material; existing root backdrop only; device safe-area and drag behaviour unverified |
| Reader Aa menu | Interactive transient glass | Compact anchored text-size menu; preserve the existing 16–28sp actions and clamping | Same shared transient material, mounted only while expanded; font-size behaviour unchanged |
| Other dialogs, menus and sheets | Contextual controls | Use glass only when transient context benefits; preserve native OS selection/share UI | Review individually; no global glass overlay |

## Paired performance matrix

| Component | Live effect / scope | Cost-control strategy | Fallback / verification |
|---|---|---|---|
| Root backdrop | One full-window background-only Prismal capture source | Capture background pixels only; no descendants; reuse same source | Do not reintroduce self-capture; code reviewed, device launch still unverified |
| Navigation | One floating capsule plus expanded-only tab-glyph source/merged droplet | Create hidden glyph capture and merged droplet source only while expanded; compact pill uses the same shared bar surface | No new sampler/readback; compact state disposes unused sources; OPPO GPU cost unmeasured |
| Home header | One 9dp-blur / low-refraction glass surface | Reuse root background-only source; static profile reduces blur/refraction/chromatic work; no duplicate capture | Theme-derived contrast; Reduce Transparency still removes live optics; device GPU cost unmeasured |
| Search | One morphing capsule + one static glass Filters pill; no inline live-blur panel | Reuse existing anchor/dock geometry and root backdrop; result/status row is plain text | Preserve IME; clear and Filters controls retain 48dp targets; no extra readback |
| Explore filters | One shared capsule, not one glass surface per chip | Reuse same backdrop; keep chip fills/borders as simple drawing | >=48dp target and selected semantics still require device/TalkBack checks |
| Feed cards | No live blur | One Coil request per image; palette sampled from that decoded bitmap; cached per article | Standard content card if palette unavailable; no duplicate decode |
| Reader toolbar | One 8dp-blur glass toolbar, 60dp minimum height | Reuse root backdrop; one small bounded surface; Aa menu exists only when opened; tap reveal adds no surface/effect | Static material/Reduce Motion fallback; tap is one ACTION_UP policy check; device GPU cost unmeasured |
| Reader progress | 2dp content indicator | Reuse existing debounced `savedReadingProgress`; no polling, WebView injection or readback | Hidden at zero/completion; visual timing needs device validation |
| Completion dock | One 7dp-blur surface, only after end detection | Render only when complete, not dismissed and no definition open | No large persistent panel; safe-area and collision checks still need device validation |
| Definition popover | Existing anchored Prismal surface | Keep one existing popover; do not add another capture; action buttons use native Compose controls | Compact scroll bounds retained; selection/ActionMode requires device validation |
| Search filter sheet / Reader Aa menu | One 5dp-blur, low-refraction transient surface while open | Shared `PrismalTransientSurface` consumes the existing background-only root backdrop; no extra capture/readback, no per-chip/item blur, and the two screens cannot show these surfaces together | `GlassRenderingPolicy` removes live optics for Reduce Transparency; policy constants covered by `GlassSurfacePolicyTest`; frame/GPU/memory/battery cost unmeasured |
| You / Reading / Quiz Stats supporting cards | No live blur; nearly opaque theme-aware content surface with intensity-scaled static tint/rim | `LocalGlassIntensity` carries only the committed preference; brushes are remembered by theme and normalized tint/rim values; summaries remain memoized | No capture/readback/image work added; policy tests cover Clear/Balanced/Frosted and invalid values; device/frame/GPU/memory/battery cost unmeasured |
| Saved / Reading / Quiz Stats filter group | One 5dp-blur / low-refraction Prismal surface per visible subpage, containing three compact 48dp rails | Reuses the one background-only root source; no per-row/per-chip blur, no new capture/readback; option de-duplication is remembered | Reduce Transparency routes blur/refraction through `GlassRenderingPolicy`; FilterChip keeps selected semantics and >=48dp width/height; GPU/frame/memory/battery cost unmeasured |
| Appearance preview | One 5dp-blur / low-refraction `PrismalTransientSurface` plus one static supporting content sample | Reuse the root background-only backdrop; only the small preview's local material token follows slider movement; the app-wide intensity changes on release/preset selection; no image decode or root-state update per drag tick | `GlassSurfacePolicyTest` covers both tiers and Clear/Balanced/Frosted values; OPPO rendering, frame timing, memory, thermal and battery remain unverified |
| Quiz chrome | Two small Prismal control surfaces (header + bottom dock) | Reuse the one background-only root source; 7–8dp bounded blur/refraction; no per-question glass, new capture or readback | Reduce Transparency uses GlassRenderingPolicy; 48dp actions and safe-area padding; device frame/GPU/battery cost unmeasured |

## Implemented batch — Reader toolbar and completion

**Changes on this feature branch:**

- Replaced the plain cramped Reader row with a compact Prismal glass toolbar containing Back, publisher identity, the publisher-supplied date in IST, source-status wording and an Aa entry point.
- Moved the existing font-size range (16–28sp) behind an accessible dropdown. The menu uses deterministic clamping and is only composed when opened.
- Added a subtle 2dp progress line that reuses the existing debounced `savedReadingProgress` state. No new WebView polling, scroll observer, GPU readback or JavaScript was added.
- Replaced the large fixed “Finished reading?” card with a compact Prismal completion dock. It appears only after the existing end detector fires, hides while a definition or native selection ActionMode is open, remains dismissible, and retains the five-question CTA with explicit “not official PYQs” provenance.
- Reworked the definition title/actions so the selected word can use two lines and Copy/More/Done are compact icon actions with accessible descriptions and 48dp targets.
- Added `ReaderChromePolicy` and focused JVM tests for font-size bounds and completion visibility.

**Rendering delta:** one small Reader toolbar surface is now active while reading; one small completion surface is active only after completion. Both reuse the existing root backdrop. The definition popover remains the existing single anchored surface. No per-card blur, extra backdrop capture, sampler, image decode, GPU readback, dependency, network request or persistent data format was added.

**Subsequently implemented:** Reader chrome auto-hide/directional pinning, 48dp Search clear, feed-only compact navigation, dashboard/stat supporting surfaces, Appearance live preview, shared low-effects/reduced-transparency preference and the Saved/stat filter group. The current Quiz action-chrome batch adds a glass header and safe-area submission dock while keeping questions and explanations on standard surfaces.

## Verification status and limitations

- The exact Saved/stat filter-group head 3a08bb95b0f9a0fed8ec11dae055f0ac2817a523 passed natural run #203; full job steps/log, WordNet output, JVM test task, APK assembly and artifact upload were inspected. Run #204 is PR #26 and #202 is PR #25; neither validates the active branch.
- This Quiz action-chrome commit has no CI result yet. Wait for its natural push run on the exact new SHA before declaring it compiled or passing.
- No OPPO Reno10 5G install, screenshot, TalkBack session, logcat/tombstone, Perfetto trace, GPU frame-time, memory, thermal or battery measurement is available. Visual transitions, contrast on image backdrops, real-device launch stability and resource cost remain UNVERIFIED.
- Supporting-card cost controls: no Prismal capture, live blur, refraction, sampler, readback, graphics layer, image decode or dependency; cached tint/rim only. The Saved/stat filter batch adds one shared 5dp-blur/low-refraction panel on the active Saved or Stats page, reusing the root background-only source; no per-chip surface or new readback. Quiz chrome adds only two small active surfaces, with no per-question blur or additional capture. These are code-scope statements, not measured performance wins.
- The native launch fault remains unproven as fixed until a post-change physical-device launch test is available.

## Next implementation sequence

1. Validate this supporting-material tone pass on its exact new head via natural push CI; inspect the complete job log and artifact before another UI batch.
2. Audit remaining dialogs/sheets/menus and whole-app light/dark, large-text, TalkBack and Reduce Motion states without turning dense content into live glass.
3. Keep the single draft consolidation review on black-box-bihar-news; main remains push-only, so branch CI must not be described as PR-to-main validation.
4. Measure on OPPO Reno10 5G (or explicitly keep metrics unverified): startup, scroll jank/frame timing, allocation churn, GPU overdraw/layers, thermal and battery against the same base build.

## Consolidation implementation log

The following work is represented on `black-box-bihar-news`; source PRs/branches remain intact and must not be deleted until final review:

| Source work | Consolidated treatment | Verification |
|---|---|---|
| PR #16 startup / network / Prismal | Integration ancestor; background-only capture and publisher guards retained | Run #172 baseline passed; prior PR #16 run #167 passed |
| PR #20 + PR #18 workflow | Compatible read-only build / separately gated release workflow combined; branch PR validation enabled | Run #172 onward natural pushes; run #183 passed |
| PR #19 Reader Liquid Glass | Reader toolbar, Aa, progress, completion dock and definition improvements integrated | Run #173 and #183 passed |
| PR #11 dates/cache/provenance | Ported policy and parser precedence into current NewsRepository; publisher RSS clearly secondary | Run #174 and #183 passed |
| PR #12 Atom alternate links | HTML alternate / RSS text / safe HTTP(S) link selection ported into current guarded parser | Run #175 and #183 passed |
| PR #8 + #22 persistence/history | IO + mutex wrappers and capped ordered recent markers; legacy StringSet preserved | Run #176, #182 and #183 passed |
| PR #17 URL deduplication | Canonical comparison; exact bookmark/read URL variant wins before newest publication | Run #182 and #183 passed |
| PR #6 + #21 editorial | GS I Geography, bill/Finance Bill, heat-wave; film/viral/sports safeguards; labelled synthetic tests | Run #182 and #183 passed |
| PR #13 adaptive glass contrast | Shared 8×8 band probe, foreground/idle-only, 4s/6s cadence, pauses during feed motion, Reader, Settings and background | Run #187 passed; physical-device profile unverified |
| Feed-only compact navigation | Same bar morphs to active-label pill only on Today/Explore/Search; You/nested surfaces remain expanded | Run #186 policy tests and #187 integration build passed; visual motion needs device validation |
| Supporting glass surfaces | Shared non-blurred tier for You metrics, practice-empty and destinations plus stats day/attempt cards | Integrated; current active branch run #203 passed; device cost remains unmeasured |
| Quiz action chrome | Two small Prismal surfaces for header identity and submit/done actions; content cards remain standard | This batch; natural CI pending on its exact new head |

### Deliberately excluded / superseded

- Older `ui/liquid-glass-performance-2026-11-06` and `redesign/v0.3-search-reader` are stale; surviving useful changes are already present in merged ancestry or superseded.
- PR #15's per-hop RSS and article-page redirect validation is present in the integration code together with the publisher-host policy and focused tests. The branch was not copied wholesale; the equivalent safeguards are retained in the newer feed and Reader repositories.
- No old `MainActivity.kt` was copied wholesale from PR #6/#11/#13/#17/#19/#22. Their independent policies and tests were reconciled while preserving the current Reader, redirect and backdrop code.
- PR #13's original 1.5 s / 2.5 s readback cadence was rejected. The integrated probe is less frequent and idle-only; it still needs OPPO profiling.
- A “bug-free” claim is not supported by CI. Physical-device launch, TalkBack, Reduce Motion, visual transitions, frame timing, memory, thermal and battery remain outstanding.

## Current validation record

- Run #182, exact SHA `f031bf21229f43a992384cc23fa0f4da57e547a1`: passed JVM tests, APK assembly and artifact upload.
- Run #183, exact SHA `d80916691eb5e72ee5448bd543433cdabe2a1e5a`: passed Reader auto-hide and Search clear-target work.
- Runs #184/#185 exposed one new navigation threshold test assertion; it was corrected to 43px + 1px without reducing the 44px threshold.
- Run #186, exact SHA `6f15200325d3d333b3abff16c6f290fc81287b9c`: passed the corrected compact-navigation policy tests and APK build.
- Run #187, exact SHA `ebdfdd7b22c9164ab4ea24a6fa744e706081fc7f`: passed the UI, dashboard and Appearance preview integration; artifact ID `13559292205`, 39,052,427 bytes.
- Run #189, PR #23 head `9d754953a29c0355ce55306ce4fd5463193c9dbb`: passed the more-specific parliamentary syllabus gate and regression tests.
- Run #190, integration SHA `67d1f6a32b483535dcecf2d0f803e8ce5cac7e16`: passed after consolidation documentation update.
- The parliamentary signal gate (PR #23) is merged into `black-box-bihar-news`; current merged head is `46c7d77f66a1a2eba87bad77e5345f2b0e1ec3b2`. Natural post-merge run #192 is in progress; wait for that exact run before claiming the newest head passed.
- Release publishing is skipped off main. No manual dispatch/rerun; no main edit/merge.
- CI does not verify physical-device launch, animation smoothness, accessibility or performance. These remain unverified.


## Supporting-surface implementation batch — 7 November 2026

**Source / lineage:** `black-box-bihar-news`, baseline `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`; 94 ahead / 0 behind `main`, 37 ahead / 0 behind PR #16. New code commit: `5e966a1b2d27bc08e01af3cd40e6573d6e8328be`.

- Added `GlassSurfacePolicy` as shared tokens for 22dp continuous geometry, 20sp metric values, nearly opaque supporting fills, low-alpha theme-aware tint and restrained rim.
- Added one reusable `SupportingGlassCard` used by You metric tiles, the practice-empty panel, library destinations, Reading Stats day rows and Quiz Stats attempt rows. It uses the existing Prismal rounded geometry but deliberately **does not** capture, blur, refract or sample the backdrop.
- Kept dense text/data on high-opacity surfaces; metric values are single-line and labels are bounded to two lines to avoid the clipping/wrapping seen in the recording.
- Memoized You totals, Reading/Quiz Stats summaries and SavedFeed's saved/previously-opened/filter derivations by their actual immutable inputs. This avoids reclassifying every article on unrelated filter recompositions.
- Added `GlassSurfacePolicyTest` for contrast reserve, tint/rim bounds and shared geometry/type scale.

**Rendering delta:** one cached vertical tint brush and one cached rim brush per composed supporting card; no new blur pass, root capture, tab-glyph capture, sampler/readback, graphics layer, bitmap work, network or persistent data change. Existing lazy composition remains bounded to visible list items. No measured frame-time or battery improvement is claimed.

**Verification:** parent run #193 passed at exact SHA `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`; artifact `13559372948`. It does not validate this new commit. Natural CI for `5e966a1` is pending. OPPO/TalkBack/visual/performance checks remain unverified.


## Reader resume restoration off-main batch — 7 November 2026

**Selected development branch:** `black-box-bihar-news`; source before this iteration `c8afed0a78c536a36567c286f19fdafcada1ac70`. This remains the single active consolidation branch under the owner's explicit branch instruction.

**Fresh sibling-branch review:** `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` is a newer, divergent rename/validation line (4 commits ahead / 2 behind `black-box-bihar-news`, common ancestor `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`). Its commit `e161a9a53a3d85d06b4908eb690aecb02a716015` (“perf(reader): restore saved position off the UI thread”) was reviewed and selectively ported. Its other changes were not copied:
- `d4f0458` changes the single workflow's branch filter and artifact naming to `brrrNEWS`; the active branch's read-only PR workflow remains unchanged.
- `c9a1d53` changes the visible app label/docs to uppercase `brrrNEWS`; not adopted because this task preserves the existing app identity.
- `e6fcfc2` repeats the supporting-glass implementation already present in `black-box-bihar-news` as `5e966a1`; no duplicate system was added.
- PR #24 targets `brrrNEWS` and changes only `UpscFeedPolicy.kt`, its tests and editorial baseline documentation. It is editorial-only and was not ported into this UI pass.

**Implementation:** `ReaderScreen` no longer reads and parses the saved Reader-position JSON synchronously during composition. A per-article `LaunchedEffect` restores the existing value on `Dispatchers.IO`, publishes it to Compose state, and only then allows the existing WebView content/fallback load to queue its resume point. The existing linear progress indicator remains visible while the resume point is being restored. Article-key scoping cancels stale restore work when the user changes articles.

**Data and behaviour:** no preference key, JSON format, retention rule, WebView permission, publisher fallback, JavaScript/DOM-storage policy, scroll-save debounce, completion rule, selection bridge or quiz persistence semantics changed. The existing `ReaderPositionPolicy` and `ReaderPositionPolicyTest` remain the policy/geometry regression coverage; this batch adds coroutine sequencing rather than a new policy, so no wrapper-only test was added.

**Paired performance strategy:** removes the bounded SharedPreferences/JSON lookup from the Reader composition path and performs one restore read on IO per article open. It adds no timer, polling, network request, bitmap decode, blur, shader, graphics layer, Prismal capture, sampler or readback. This is a source-level main-thread-work reduction, not a measured frame-time or battery result.

**Verification:** pre-iteration run #196 passed on exact source SHA `c8afed0`; run #195 passed on `5e966a1`. The new Reader resume-I/O commit requires its own natural push workflow. OPPO Reno10 launch/scroll traces, restore timing, memory, thermal and battery remain **UNVERIFIED**.

**Next implementation:** after natural CI verifies the current shared filter group, polish the Quiz action area and transient sheets/menus with one restrained control-glass treatment where it improves hierarchy. Keep question/answer content clear and high contrast; preserve the single root backdrop source.


## Saved and statistics filter-group batch — 7 November 2026

**Source:** `black-box-bihar-news@2e2c15f5189ec6c60fa1f07f384bce4aa1734ea0`; this commit is its direct child. No other branch is changed.

- Replaced three independent plain filter rows on Saved, Reading Stats and Quiz Stats with one shared `GlassFilterGroup` per visible page. Each group contains Published/Date, GS paper and Subject rails, using a single rounded Prismal material rather than separate glass plates.
- Compact rows put their labels beside horizontally scrollable options. Every FilterChip is constrained to at least 48dp width and height; Material selected/role semantics remain intact. Existing filter state, option values, date interpretation, Saved data, statistics and quiz submit-only persistence are unchanged.
- Search's existing filter sheet remains on its prior implementation; Today/Explore, You dashboard, Reader, Quiz and Settings are not given a new filter surface in this batch.

**Paired rendering/performance strategy:** one 5dp blur / 2.5dp refraction-height / 3.5dp refraction-amount Prismal surface per active Saved/Stats page, reusing the one background-only root capture. No per-chip or per-row blur, new capture, sampler/readback, shader, graphics layer, image decode, network request or dependency. Existing `GlassRenderingPolicy` routes Reduce Transparency to its low-effects fallback; `GlassSurfacePolicy` supplies the shared 22dp geometry. No performance improvement is claimed without device measurements.

**Tests:** no new policy/state logic was introduced; the panel reuses the already-tested `GlassRenderingPolicy` and `GlassSurfacePolicy`. Natural CI must run the full `:app:testDebugUnitTest` suite and assemble the APK on this exact new head. Visual density, TalkBack, 48dp touch behaviour and animation remain device checks.

**Validation:** the grouped-filter commit is covered by run #203 on exact SHA 3a08bb95b0f9a0fed8ec11dae055f0ac2817a523; artifact 13565296151, 39,078,047 bytes. OPPO Reno10 5G screenshots, Perfetto/frame timing, GPU overdraw, memory, thermal and battery remain UNVERIFIED.


## Quiz action-chrome batch — 7 November 2026

**Source:** black-box-bihar-news@3a08bb95b0f9a0fed8ec11dae055f0ac2817a523, rechecked immediately before the fast-forward-only update. No other branch or workflow is changed.

- Added a compact Prismal glass header with Back, “Your reading check”, the five-question context and an accessible answered-count description.
- Added one bottom Prismal dock for answered count, Done and Submit; it uses navigation-bar safe-area padding and 48dp action targets. The existing submit callback remains the only persistence boundary; no draft or unsubmitted quiz state is stored.
- Kept the source note, question cards, options and explanations on high-contrast standard surfaces. Question/result cards now use the shared continuous-corner geometry; answer options have 48dp minimum height and expose selected state semantics.
- Quiz relevance/questions/answer state now key off article identity plus title/description, avoiding stale questions or answer state if a cached article is refreshed under the same URL identity.

**Paired rendering/performance strategy:** two small visible Prismal surfaces (header and bottom dock) reuse the existing background-only root capture. Blur/refraction are bounded to 7–8dp and routed through GlassRenderingPolicy; no per-question blur, second capture, sampler/readback, bitmap decode, graphics layer, polling, dependency or network work is added. Reduce Transparency uses the existing static/low-effects path. No frame-time, GPU, memory or battery improvement is claimed without device measurements.

**Tests and behaviour:** no new persistence policy was introduced; submit-only quiz storage is unchanged. Existing JVM tests remain the compile/regression gate. Option selected semantics and minimum hit size are represented in Compose modifiers; TalkBack, font scaling, selection and real-device layout remain device checks.

**Validation:** natural CI pending for this exact commit. Run #203 validates only the source head before this batch; PR #25/#26 runs #202/#204 validate their own heads. OPPO Reno10 screenshots, launch/scroll traces, Perfetto, frame timing, overdraw, allocation/memory, thermal and battery remain UNVERIFIED.


## Current implementation and verification update — 7 November 2026

### Quiz action-chrome compile repair

- The existing Quiz action-chrome batch added two small Prismal control surfaces (identity header and submit/done dock). Questions, options, provenance notes and explanations remain on ordinary high-contrast content surfaces; submit-only persistence is unchanged.
- The first natural integration push, run #208 on `947310a157e03df25dde3856d2686d18ffca68e8`, failed at `MainActivity.kt:4238:61`: `selected = chosen` resolved to the local `Map<Int, Int>` answer state rather than the Compose semantics receiver's Boolean selected property.
- The full `QuizScreen` and its article-keyed state/dependencies were inspected. The one-line correction is `.semantics { this.selected = chosen }`; it preserves selected-state accessibility semantics and changes no answer, score or persistence behaviour.
- PR #28's exact one-line child commit was fast-forwarded into `black-box-bihar-news`; PR #28 is closed/merged into this integration branch only. The source branch remains. No other branch was modified.
- Natural PR run #209 passed on the same code head. Natural active-branch push run #210 then passed WordNet generation, all four Python builder tests, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload. Release job skipped.
- Latest overall run #211 passed on PR #27's separate editorial head. That PR remains open/draft and was not ported during this UI/build-repair iteration.

### Material/performance audit retained

- The Prismal root capture remains a background-only sibling; the captured tree contains only the theme background and two ambient gradients. App content and all glass surfaces remain siblings above it. No self-capture was reintroduced.
- The shared 8×8 luminance probe remains one source at 4s on Android 12+ / 6s on older Android, paused while backgrounded, Reader/Quiz/Settings is open, feed motion is active, or Reduce Transparency is enabled.
- Compact feed navigation composes one shared bar morph; the full glyph source, selected droplet and merged sampling surface are used only in the expanded branch. The compact active-label pill is a 54dp navigation control with an explicit expand action.
- Search clear remains inside the expanded morph capsule, appears for non-empty queries, uses a 48dp target and only clears the query. Reader chrome is pinned for dictionary definition, native selection ActionMode, IME and enabled accessibility services.
- These are source-level checks, not measured performance wins. No additional capture, sampler, per-card blur, bitmap decode, graphics layer or dependency was added by the compile repair.

### Next implementation pass

This note preceded the transient-control commit below. The exact transient-surface head 59022ed4dd20513ccd2a00b38c6e8b822057e938 is now validated by natural push run #214 (full JVM suite, APK assembly and artifact upload succeeded; publish job skipped). The next isolated pass is the separately validated PR #29 bookmark-persistence change; it is a storage/performance change, not a visual-material change. OPPO visual and performance verification remain separate from CI.



## Transient Search and Reader controls batch — 7 November 2026

**Implementation base:** `black-box-bihar-news@16eb549c3235b321ecf7ef1c5c5270817e336489`; branch freshness and all 33 remote refs were rechecked before editing. The implementation is one direct child on the same branch; natural CI is pending for that new head.

- Added the shared `PrismalTransientSurface` wrapper and explicit `GlassSurfacePolicy` tokens for a restrained 5dp blur, 2.5dp refraction height, 3.5dp refraction amount and 0.42 tint scale.
- Search's Refine Articles sheet now has a transparent Material shell and one rounded Prismal surface. The drag handle is inside that surface; Topic, GS paper, Subject, Reset and Done retain their original state and callbacks. The three filter rails remain standard 48dp chips; no per-chip blur or duplicated filter panel was added.
- Reader's Aa dropdown now uses the same shared surface while expanded. The existing smaller/larger/current-size actions, font-size bounds, dismiss behaviour and reader text state are unchanged.
- Both transient surfaces consume the existing background-only `PrismalBackdrop`. They are conditionally composed only while their own sheet/menu is open; Search and Reader are mutually exclusive destinations. No new capture, sampler, readback, bitmap decode, graphics layer, shader, timer, dependency, network request or persistence change was added.
- Reduce Transparency routes the shared material through the existing zero-blur/zero-refraction fallback and higher tint reserve. Added policy-bound tests to `GlassSurfacePolicyTest`; the full `:app:testDebugUnitTest` suite and APK assembly must run naturally on this exact commit.
- **Unverified:** OPPO Reno10 screenshots, sheet drag/IME/safe-area interaction, Aa popup placement, TalkBack, Reduce Motion, Perfetto/frame timing, GPU overdraw, allocation/memory, thermal and battery. No device or performance claim is made.

## Bookmark persistence off-main batch — 7 November 2026

Source / integration lineage: active branch was rechecked at black-box-bihar-news@59022ed4dd20513ccd2a00b38c6e8b822057e938, 103 ahead / 0 behind main@73b8a0d1ba33503fede02db2c3c0af082d724a12. PR #29 source perf/off-main-bookmark-persistence-2026-11-07@a64857b5efa62ea053c0c96e5f374fd35924b340 is one commit ahead / two behind the active head from merge base 6f9b3c1e1a484233721863d0b736734c0d282cd5; only its reviewed bookmark delta is being ported. No source branch was merged, reset, rewritten or deleted.

- LocalStore.toggleBookmark is now a suspend operation on Dispatchers.IO; a dedicated bookmarkWriteMutex serializes the SharedPreferences read/modify/write so rapid taps cannot overwrite one another.
- BrrrNewsApp reuses its existing persistenceScope and NonCancellable persistence pattern; Compose bookmark state is updated only after the IO mutation returns. All Today/Explore/Search/Saved NewsCard callbacks continue to share this single path.
- Added pure BookmarkSetPolicy and four JVM tests covering add, remove, blank URL, preservation of unrelated bookmarks and reversible toggles.
- Data preservation: the full user bookmark set remains unbounded by design; no cap, eviction, migration, preference-key change or URL-normalisation change is introduced. Mutex protection is in-process only; SharedPreferences apply() remains asynchronous. UI state may reflect the tap after the IO round trip.
- Paired performance strategy: removes the SharedPreferences full-set read/copy/mutation from the Compose click callback and serializes the work off-main. No rendering pass, glass material, Prismal capture, sampler/readback, blur, image decode, bitmap work, network request, dependency or GPU layer was added. This is a source-level main-thread-work reduction, not a measured jank, latency, memory, battery or thermal improvement.
- Source verification: PR #29 natural run #212 passed on its exact source head; full job log inspected, including four WordNet-builder tests, :app:testDebugUnitTest, :app:assembleDebug and artifact upload (artifact 13569115360, 39,084,383 bytes). The current integration head before this port passed natural run #214 with the same build/test/upload pipeline and artifact 13570367489; neither run validates the new integration commit.
- New integration verification: pending the next natural push run on this exact commit. No manual dispatch/rerun and no Release publication.
- Unverified: OPPO Reno10 tap latency, rapid repeated bookmark toggles, lifecycle cancellation, TalkBack feedback, memory/allocation, frame timing, battery and thermal behaviour. CI does not establish device performance.

Next: after natural CI validates this exact head, perform the Appearance preview cost pass and whole-app visual consistency audit; retain the rule that only one preview glass surface is live at a time and that slider drag changes local preview state rather than rebuilding the root backdrop.


## Appearance preview single-surface batch — 7 November 2026

**Source and branch freshness:** `black-box-bihar-news@f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb`, 104 ahead / 0 behind `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` and 47 ahead / 0 behind PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. This is the owner-selected coherent integration line; no newer relevant UI branch supersedes it. The complete live inventory still has 33 branches. `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` remains divergent (4 ahead / 10 behind; merge base `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`); its supporting-glass implementation is already represented, while its branding/workflow changes are not adopted. PR #27 now points to `33ee2194b51c07b7f00ef9f58b1d82444a050678` (9 ahead / 5 behind this branch, merge base `3a08bb95b0f9a0fed8ec11dae055f0ac2817a523`); its complete diff is confined to `UpscEngine.kt`, `UpscFeedPolicy.kt`, policy tests and editorial documentation, so it is not a UI baseline. PR #29 remains open on its original source branch; its one-commit bookmark-I/O delta was selectively ported and validated here without merging or modifying the source branch. PR #25/#26 remain separate publisher-resource/security work; PR #24 targets divergent `brrrNEWS`; PR #18 workflow intent, PR #19 Reader UI, PR #20 stacked CI, PR #23 editorial gate and PR #28 quiz compile repair are represented in this integration line. Other source branches remain untouched. No branches were deleted.

**Implementation:**
- Replaced the two simultaneously live `PrismalGlassSurface` panels on Appearance with one shared `PrismalTransientSurface`. It reuses the existing background-only root backdrop and the shared 5dp blur / 2.5dp refraction-height / 3.5dp refraction-amount / 0.42 tint-scale policy; the maximum preview tint remains bounded by the wrapper's 0.22 clamp.
- Moved the intensity slider, Clear/Balanced/Frosted presets and Reduce Transparency switch onto one ordinary Material settings card. The preview now shows the shared header, a lightly tinted Search/Filters control, a high-contrast article sample and all four primary destinations (Today, Explore, Search, You). The article sample and inner preview controls are not separate live-glass surfaces.
- Extracted `AppearanceGlassEditor` so slider drag updates only its local preview state; the persisted app-wide `glassAlpha` callback runs on drag completion or preset selection, not on every slider tick. The existing local preference, preset values, Reduce Transparency behaviour and four-destination navigation are unchanged.
- The Appearance screen remains excluded from adaptive luminance sampling by the existing `settingsOpen` activation gate. Reduce Transparency continues to remove blur/refraction/depth and raise the tint reserve through the shared policy.

**Paired rendering/performance strategy:** live preview surfaces reduced from two to one; the sole live surface is the existing bounded transient-control tier. No new backdrop capture, sampler/readback, graphics layer, shader, timer, image decode, network request, dependency or persistence format was added. The standard settings/article cards use no live blur. This is a source-level reduction in simultaneously active preview effects, not a measured GPU, frame-time, memory, battery or thermal improvement.

**Tests / verification:** no new policy was required; the implementation reuses existing `GlassSurfacePolicyTest` bounds for the transient tier and `GlassRenderingPolicyTest` coverage for zero-optics/reduced-transparency fallback. Natural CI must run the full `:app:testDebugUnitTest` suite and `:app:assembleDebug` on the new exact child commit. Parent run #215 validates only `f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb`. No manual dispatch/rerun or Release publication is permitted.

**Unverified:** OPPO Reno10 screenshots, actual slider-response smoothness, sheet/preview insets, TalkBack, font scaling, Reduce Motion, Perfetto/frame timing, GPU overdraw, allocations/memory, thermal and battery. No physical-device performance claim is made.

**Next:** after the exact new head's natural CI completes, perform the remaining whole-app visual/performance audit (especially dashboard/stat geometry, supporting-glass contrast over light/dark backdrops and transient-control consistency); keep device checks explicitly unverified until real OPPO evidence exists.


## Startup Appearance preference restore on IO — 7 November 2026, 17:27 IST

**Freshness and source review**
- Rechecked all 36 remote branch heads, all 18 open and 14 closed PRs, current workflow and complete Actions history immediately before the port.
- Integration source is `black-box-bihar-news@ee82dfde140aeb0d1ca80146379a0a2818c695df`; it is 105 ahead / 0 behind main and 48 ahead / 0 behind PR #16. PR #31 is a direct descendant (5 ahead / 0 behind, merge base exactly `ee82dfde140aeb0d1ca80146379a0a2818c695df`), so its narrow code delta is compatible with the active architecture. No older UI branch was selected.
- PR #31's five-commit sequence was reviewed; the final diff touches only the four code/test files copied below. Its source branch and open draft PR remain unchanged.

**Implementation**
- Removed the first `LocalStore.glassAlpha(context)` and `LocalStore.reduceTransparency(context)` reads from Compose state initializers. `BrrrNewsApp` now starts with the existing default material values and restores persisted appearance alongside the existing `LocalAppSnapshot` inside its established `Dispatchers.IO` block.
- Added `LocalAppearancePreferences` / `AppearancePreferencesPolicy`; both settings are read from one SharedPreferences handle, glass alpha is normalized to the supported finite 0.12–0.55 range, and invalid values fall back to 0.28.
- `LocalStore.glassAlpha` and `reduceTransparency` delegate to the shared snapshot; save operations use the same normalization policy. No preference key, data format or user setting was changed.
- The restored appearance values are assigned before `localStateLoaded` becomes true, alongside the other local collections. Source scan of the active `MainActivity.kt` found no remaining direct call sites to the two synchronous appearance getters.

**Paired performance and rendering strategy**
- The first SharedPreferences XML load now occurs in the existing IO restore rather than on the Compose/UI thread; it is not duplicated in a second coroutine or extra startup task.
- No Prismal surface, blur/refraction pass, backdrop capture, sampler/readback, image decode, graphics layer, shader, timer, dependency, network request or persistence format was added. The current single background-only backdrop and Appearance one-surface preview remain unchanged.
- **Visual trade-off to verify:** until the IO snapshot returns, the first composed frame uses the default 0.28 glass alpha and Reduce Transparency off. A user with custom settings may see a one-time material adjustment after restore; no device screenshot is available to establish whether it is perceptible.

**Tests and CI evidence**
- Added pure JVM normalization/snapshot tests for lower/upper bounds, finite values, NaN/positive infinity fallback and Reduce Transparency preservation.
- PR #31's exact source head passed natural run [#227](https://github.com/Batmancpu/CurrentLens/actions/runs/42367156421): WordNet generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload all succeeded. Source artifact `13571813251`, 39,096,319 bytes, SHA-256 `4ef3cb151f40045128b16b7a47a19dcdb1decc9674eabbf9a66929d37dd15497`.
- The active parent passed run [#219](https://github.com/Batmancpu/CurrentLens/actions/runs/42366560250), but neither run validates this new consolidated commit. Await the new head's natural push run; no manual dispatch/rerun.
- Current full Actions inventory: 235 runs (136 success, 58 failure, 41 cancelled), no active/queued jobs. Latest overall run #235 passes on PR #32's separate RSS-concurrency head. Latest published Release is still `v0.2.0-build-116`.

**Risks / unverified**
- OPPO Reno10 cold-start timing, first-frame preference transition, visual continuity, memory/allocation, Perfetto/frame timing, GPU overdraw, thermal and battery remain **UNVERIFIED**. Moving the read off main is a code-path correction, not a measured startup or battery improvement.
- Main's workflow remains push-only; no PR-to-main validation is claimed. No Release publication, merge, main write, branch deletion or source-branch mutation occurred.

**Next**
- Verify the natural CI run on the exact new `black-box-bihar-news` head before another implementation batch. Next safe candidates are the narrowly scoped 48dp icon-target policy from PR #30 (3 ahead / 1 behind the active line; reapply against current code rather than fast-forward) and PR #32's bounded RSS concurrency policy (2 ahead / 0 behind; separate performance batch). Keep publisher asset/subresource proposals (#25/#26) out until their shared-host and redirect boundaries receive a full security review.


## 48dp icon-action target batch — 7 November 2026

**Freshness and integration source**
- Re-enumerated all 36 remote branches (page 1 complete; page 2 empty), 18 open / 14 closed pull requests, latest `main`, workflow, Actions queue/history and Releases. Default `main` remains `73b8a0d1ba33503fede02db2c3c0af082d724a12`.
- Continued on the owner-selected `black-box-bihar-news@d75723dd6368262c311b13912caf21a07804150a`, 106 commits ahead / 0 behind `main` and 49 ahead / 0 behind PR #16's corrected startup/Prismal base `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. The background-only root capture remains present; glass surfaces are siblings and do not sample their own descendants.
- PR #30 (`a11y/48dp-icon-targets-2026-11-07@25107ce905b70bde1a41282ffd1d606819ebf58d`) was reviewed in full. It is 3 commits ahead / 2 behind the active head from merge base `f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb`; its entire implementation is the three undersized icon targets plus a pure policy/test. The small changes were re-applied against current `MainActivity.kt`; the source branch and draft PR remain untouched. `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` is divergent (12 ahead / 4 behind this active line) and was not used.
- PR #31's appearance-preference IO work is already represented on this branch. PR #32's bounded RSS concurrency and PRs #25/#26's publisher resource/security changes remain separate; no unrelated code was ported. PR #27 is editorial-only and does not change the UI baseline.

**Implementation**
- Added `TouchTargetPolicy` with a 48dp minimum and applied it to the shared Home header Back action (44dp), Refresh action (46dp) and NewsCard bookmark action (40dp).
- Search clear, Reader Back/Aa, completion dismiss, and Explore category chips already meet the 48dp minimum; the category rail retains selectable-tab semantics and horizontal scrolling.
- Preserved existing action callbacks, bookmark IO/mutex path, header geometry, card content, four destinations, and Search/Reader state. The NewsCard still uses one Coil request and samples its palette from that same decoded bitmap.

**Paired cost and rendering strategy**
- No Prismal surface, blur/refraction pass, backdrop capture, sampler/readback, shader, graphics layer, image decode, network request or persistence change was added. Only the three existing hitboxes grow to 48dp; the NewsCard section label already ellipsizes, so the additional width does not alter article data or layout semantics.
- The one background-only root capture and expanded-only tab glyph/droplet sampling remain unchanged. The compact navigation continues to use one shared surface rather than compositing two bars.

**Tests and CI**
- Added `TouchTargetPolicyTest`: 40/44/46dp requests clamp to 48dp; compliant 48/56dp requests are preserved.
- Parent head `d75723dd6368262c311b13912caf21a07804150a` passed natural run [#236](https://github.com/Batmancpu/CurrentLens/actions/runs/42370605827): WordNet database generated with 151,456 unique words, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload all succeeded. Parent artifact `13573458582` (39,096,319 bytes; SHA-256 `625f7faafed71606a0ec6eb1c15705fe9f2bb94eed5a20fb74f37f0f6282559a`). This does not validate the new commit; await its natural push run.
- Latest overall run [#238](https://github.com/Batmancpu/CurrentLens/actions/runs/42378821627) passed on PR #27's separate editorial head `627014500a7a983377b7c2fc82ca0cd238f776b9`; it is not a validation of this UI change. The full Actions history contains 238 runs (139 success, 58 failure, 41 cancelled), with no queued or in-progress runs at audit time.
- Latest Release remains `v0.2.0-build-116`. The active branch workflow is read-only for build/test; main's workflow is still push-only, so no PR-to-main validation is claimed. No manual dispatch/rerun or Release publication.

**Unverified / next**
- OPPO Reno10 target reachability, visual spacing, TalkBack focus order, font scaling, launch stability, frame timing, GPU overdraw, allocations, thermal and battery remain **UNVERIFIED**. CI/JVM success cannot establish real-device usability or performance.
- After this exact head's natural CI passes, continue the whole-app accessibility and optical-consistency audit; keep the 8×8 adaptive probe at its existing 4s/6s idle-only cadence and do not add per-card capture or readback.


## Reader and Quiz transient material consolidation — 7 November 2026, 20:30 IST

**Branch freshness and dependency review**
- Re-enumerated all 37 remote branches (page 1 complete, page 2 empty), 19 open PRs, 14 closed PRs, current workflow, fully paginated Actions history/queue, latest jobs/logs/artifacts and Releases.
- Source parent is black-box-bihar-news@48b54808807f5b15e637b6177524fd923741e1f7. It is 107 ahead / 0 behind main and 50 ahead / 0 behind PR #16; the corrected background-only Prismal capture and read-only stacked-PR workflow remain present. PR #16 is still open/draft and unmerged.
- brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96 is divergent (4 ahead / 13 behind the selected line). Its supporting-card material, SavedFeed memoization and off-main Reader-position restore are already represented in black-box-bihar-news; branch-specific branding/workflow/docs changes are not adopted. ui/liquid-glass-system-continuation is 0 ahead / 23 behind; ui/liquid-glass-performance and redesign/v0.3 remain stale.
- PR #30's 48dp target policy, PR #31's IO appearance snapshot and PR #29's serialized off-main bookmark mutation are already represented in the selected branch. Their source PRs/branches remain untouched. PR #32's RSS concurrency and PR #33's relative Atom-link parser remain separate non-UI changes; #33's run #240 passed on its exact PR head, not on this branch. PRs #25/#26 remain held for separate publisher-host/subresource security reconciliation. No other branch was changed.

**Implementation**
- Replaced the four remaining bespoke transient-control material configurations with the existing PrismalTransientSurface: Reader source/date/Aa toolbar, Reader completion dock, Quiz header and Quiz submit dock.
- All four now use the shared tier: 5dp blur, 2.5dp refraction height, 3.5dp refraction amount, 0.001 chromatic aberration and the existing 0.42 tint scale bounded to 0.05–0.22. The helper continues to respect the persisted Reduce Transparency preference.
- Reader source/date/status, Aa menu, 48dp actions, navigation-bar padding, completion eligibility/dismissal, chrome auto-hide, native ActionMode and dictionary-selection bridge are unchanged. Quiz question/option cards remain normal high-contrast content; submit-only quiz persistence and scoring semantics are unchanged.
- The Definition popover retains its stronger, compact contextual tier; it remains anchored and uses the same root background-only backdrop.

**Paired rendering/performance strategy**
- Replaced Reader toolbar optics (8dp blur, 13/15dp refraction, 0.006 chromatic), completion optics (7dp, 10/12dp, 0.004), Quiz header optics (8dp, 10/12dp, 0.003) and Quiz submit-dock optics (7dp, 8/10dp, 0.002) with one bounded parameter set.
- Existing surface count and shared root backdrop ownership are unchanged. No new capture, sampler/readback, shader, graphics layer, image decode, timer, dependency, network request or persistence operation was added. Reduce Transparency still zeroes live optics and pauses adaptive sampling through the existing policy.
- This is a source-level reduction and consolidation of effect parameters, not a measured GPU/frame-time, memory, battery or thermal improvement.

**Tests and CI**
- Existing GlassSurfacePolicyTest already bounds the transient tier; GlassRenderingPolicyTest covers the low-effects fallback. No duplicate policy or new dependency was added.
- Parent run #239 passed on the exact source baseline; run #240 passed on separate PR #33 code. Neither validates this new commit. The new active-branch push must receive its own natural run; no manual dispatch/rerun.
- Current branch workflow remains read-only for build/test. Main still lacks a pull_request trigger; no PR-to-main validation or Release publication is claimed.

**Unverified / next**
- OPPO Reno10 screenshots, visual contrast over live feed backdrops, toolbar/definition collision, Reduce Motion, TalkBack focus, frame timing, GPU overdraw, allocation churn, memory, thermal and battery remain UNVERIFIED.
- After natural CI validates this exact commit, continue the whole-app optical/accessibility pass; retain the single background-only root capture, expanded-only tab glyph sampling and 4s/6s foreground-idle adaptive luminance cadence.


## Supporting-card material tone pass — 7 November 2026, 21:26 IST

**Freshness / dependency gate**
- Re-enumerated all 37 remote branches (page 1 complete, page 2 empty), all 19 open and 14 closed PRs, the default branch, current branch refs, workflow, fully paginated Actions history/queue, latest run/jobs/log/artifact and Releases.
- Implementation source is `black-box-bihar-news@ec86b2fef2f5e86c3b95f2a2a2269bfef6158053`, a direct child of `48b54808807f5b15e637b6177524fd923741e1f7`; it is 108 ahead / 0 behind `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` and 51 ahead / 0 behind PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. The current head was re-read immediately before the fast-forward-only update.
- `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` remains divergent (4 ahead / 14 behind; merge base `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`); its useful supporting-glass implementation is already represented. `ui/liquid-glass-system-continuation` is 24 behind; `ui/liquid-glass-performance` is 3 ahead / 126 behind; neither is a valid baseline.
- PR #29/#30/#31's bookmark-IO, 48dp-target and appearance-restore changes remain selectively represented; their source branches/PRs are untouched. PR #13 adaptive contrast is already integrated with the conservative 4s/6s shared-probe policy. PR #32 RSS concurrency and PR #33 relative Atom parsing are separate non-UI deltas (2 ahead / 3 behind and 4 ahead / 1 behind respectively); they were not mixed into this visual pass. PR #25/#26 remain separate publisher-host/subresource security proposals (7 ahead / 10 behind and 1 ahead / 9 behind) pending one combined security review. PR #27/#24 are editorial/policy work, not UI baselines.
- PR #16 remains open/draft and unmerged; PR #19/#20/#23/#28 are closed/merged into the integration branch only. No source branch was modified or deleted.

**Implementation**
- Tuned the existing shared `GlassSurfacePolicy` used by `SupportingGlassCard`: dark surface alpha 0.96→0.93 and tint 0.085→0.11; light surface alpha 0.985→0.965 and tint 0.045→0.07. Rim alpha is slightly moderated (dark 0.22→0.20; light 0.14→0.13) to keep the edge restrained as the body tint becomes more visible.
- This applies consistently to You metrics, the practice-empty panel, library destination rows, Reading Stats metrics/day rows, Quiz Stats metrics/attempt rows and the Appearance explanatory surface. The existing 22dp continuous shape, typography, data, filter state and navigation remain unchanged.
- Supporting surfaces remain ordinary Compose Cards with a static theme-aware tint and remembered gradient/rim brushes. Article cards, quiz questions/options, definition text and reading canvas remain clear content surfaces.

**Paired rendering / performance strategy**
- No PrismalGlassSurface, backdrop capture, blur, refraction, sampler/readback, shader, graphics layer, image decode, network request, timer, dependency or persistence operation was added. The one background-only root capture, hidden tab glyph source and existing active transient surfaces are unchanged.
- The material change reuses the existing Card, one vertical tint brush and one border brush per composed card; aggregate calculations and list keys remain memoized/stable. No additional live effect or off-screen glass surface is introduced.
- This is a source-level material-contrast adjustment, not a measured frame-time, GPU, memory, battery or thermal improvement.

**Tests / CI**
- Updated `GlassSurfacePolicyTest` to assert bounded near-opaque dark/light supporting surfaces, stronger but restrained theme-aware tint, a moderated rim and the unchanged bounded transient tier.
- Parent head ec86b2fef2f5e86c3b95f2a2a2269bfef6158053 passed natural run [#241](https://github.com/Batmancpu/CurrentLens/actions/runs/42385017675): WordNet builder 4/4, 151,456 unique words, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload all succeeded. Artifact `13579222899`, 39,094,929 bytes, SHA-256 `d60b7e0d261c0ccac6bb5b407a642bd54183cca7abf1601b9fae541645bc838f`.
- The new child commit must receive its own natural push run; no manual dispatch/rerun. The main workflow is still push-only, so a future PR-to-main does not receive a base-workflow PR check. No Release is published.

**Unverified / next**
- OPPO Reno10 screenshots, real light/dark contrast, large font scaling, TalkBack focus, Reduce Motion, card scrolling/jank, GPU overdraw, allocations/memory, startup, thermal and battery remain UNVERIFIED.
- After this exact head's natural CI passes, continue the remaining transient-sheet/menu and whole-app consistency audit; retain the 8×8 adaptive probe's 4s/6s foreground-idle cadence and never add per-card capture/readback.


## Reader Aa menu pinning — 7 November 2026, 22:24 IST

**Freshness / baseline**
- Re-enumerated all 37 remote branches (page 1: 37, page 2: 0), 20 open PRs and 14 closed PRs; paginated all 243 Actions runs (100 + 100 + 43; page 4 empty) and confirmed the queue is empty.
- Implementation source is `black-box-bihar-news@818afb47eaadc685f133eaffc3efd199392627e4`, a direct child of `ec86b2fef2f5e86c3b95f2a2a2269bfef6158053); 109 ahead / 0 behind main and 52 ahead / 0 behind PR #16's corrected stability/Prismal head. PR #34 remains the sole consolidation draft; no other branch is used as the baseline.
- PR #30's 48dp touch-target policy, PR #31's off-main appearance restore and PR #29's serialized bookmark persistence are already represented in the active branch. Their source branches remain unchanged. PR #32 (RSS concurrency), #33 (relative Atom links) and #27 (editorial action gate) remain separate; PR #25/#26 remain held for combined publisher-resource security review. The old Liquid Glass and v0.3 branches are not baselines.

**Implementation**
- Hoisted `textSizeMenuExpanded` from `ReaderGlassToolbar` to `ReaderScreen`, keyed by article URL, and passed it back to the toolbar through a controlled state/callback.
- Added pure `ReaderChromePolicy.shouldPinChrome`; an open Aa menu now joins definition selection, native ActionMode, IME and enabled accessibility services as a chrome-pinning condition. The existing `ReaderWebView.setReaderChromePinned` path reveals the toolbar and cancels a pending hide without adding scroll polling or per-pixel Compose state.
- The completion dock is suppressed while the Aa menu is open, then becomes eligible again when the menu is dismissed. Font-size steps/clamping, source/date provenance, article reload semantics, native Copy/Select/share, dictionary selection and WebView privacy settings are unchanged.
- Added JVM policy coverage for each pinning reason, menu-open reveal behaviour and completion suppression. These tests are **added, not yet run** on this new commit.

**Paired rendering / performance strategy**
- No new Prismal surface, blur/refraction parameter, backdrop capture, sampler/readback, shader, graphics layer, image decode, dependency, timer, network request or persistence operation was added.
- The Aa dropdown continues to use its existing small `PrismalTransientSurface` and the one background-only root source. Only the already-mounted Reader chrome state is pinned while the menu is visible; the completion surface is not composed in that state.
- This is a state/lifecycle correctness change, not a measured frame-time, GPU, memory, battery or thermal improvement.

**CI / release**
- Parent SHA `818afb47eaadc685f133eaffc3efd199392627e4` passed natural push run [#242](https://github.com/Batmancpu/CurrentLens/actions/runs/42389254191) and exact PR #34 run [#243](https://github.com/Batmancpu/CurrentLens/actions/runs/42389283591). Both build jobs passed WordNet generation, JVM tests, APK assembly and artifact upload; PR artifact `13580718014` is 39,094,949 bytes. Publish jobs were skipped.
- The new Reader pinning commit has **no CI result yet**; wait for its natural branch run. Main remains unchanged; no workflow edit, manual dispatch/rerun, Release publication, PR merge or source-branch mutation occurred.

**Unverified / next**
- OPPO Reno10 screenshots, cold/warm launch, menu/toolbar collision, native selection, TalkBack, Reduce Motion, font scaling, animation smoothness, Perfetto/frame timing, GPU overdraw, allocation/memory, thermal and battery remain **UNVERIFIED**.
- After the new exact head's natural CI completes, continue the transient-surface/accessibility audit; separately reconcile PR #27/#32/#33 only after full dependency review, and keep #25/#26 behind a combined publisher-host/subresource security review.


## Grouped-filter legibility pass — 7 November 2026, 23:26 IST

**Freshness and validation gate**
- Re-enumerated all 38 remote branches and all 35 PRs (21 open, 14 closed), checked current `main`, the complete Actions history/queue, workflow, latest runs/jobs/logs/artifacts and Releases. Source parent is `black-box-bihar-news@ed6c94190296c8d7af400b80f6fe26f2252fafe1`; it remains the owner-selected line and a direct descendant of PR #16's corrected background-only Prismal/stability baseline.
- The active branch's exact parent passed natural push run [#244](https://github.com/Batmancpu/CurrentLens/actions/runs/42394156457) and PR run [#245](https://github.com/Batmancpu/CurrentLens/actions/runs/42394162322). The latest overall run #251 belongs to PR #35's separate bounded-diagnostic branch; it is not treated as active-branch validation.
- PR #30's 48dp target policy and PR #31's off-main appearance snapshot are already present in current source; PR #29 bookmark IO is also represented. Their source branches remain untouched. PR #35 is a direct child with six diagnostic-only commits and its own successful natural CI; it is not folded into this screen-sized pass. PR #27 remains editorial-only. PR #32/#33 both touch `NewsRepository.kt` and require joint reconciliation; PR #25/#26 remain behind a combined security review. No other branch is a safer UI baseline.

**Implementation**
- `FilterChipRow` (Search filter sheet) and `CompactFilterRow` (Saved, Reading Stats and Quiz Stats) now use 12sp labels instead of 11sp, semibold selected labels and medium unselected labels.
- Both grouped filter implementations enforce at least 48dp width and height per chip. Existing `FilterChip` selected semantics, option order, callbacks, filters and saved-data meaning are unchanged.
- `FilterChipRow` now memoizes its distinct option list with `remember(options)`, matching `CompactFilterRow`; this avoids rebuilding the same de-duplicated list on selected-filter recompositions.

**Paired material/performance strategy**
- Existing single `GlassFilterGroup` / Search-sheet `PrismalTransientSurface` ownership is unchanged. No new Prismal surface, blur/refraction pass, backdrop capture, sampler/readback, shader, graphics layer, image decode, dependency, timer, network request or persistence operation is added.
- The filter chips remain controls within their existing shared glass group; article cards, statistics data and quiz questions remain high-contrast content surfaces. No per-chip blur or capture is introduced.
- The list memoization is source-level allocation avoidance only; no frame-time, memory, GPU, battery or thermal improvement is claimed.

**Tests / CI**
- No new policy test was added for a typography/layout-only adjustment. Existing Compose/JVM source is unchanged outside the two filter composables; the new exact head must receive its own natural push/PR build and `:app:testDebugUnitTest` result.
- Do not dispatch or rerun Actions. No CI result from #244/#245/#250/#251 validates the new commit.

**Unverified / next**
- OPPO Reno10 filter chip reachability, font scaling, horizontal scrolling, TalkBack focus, visual balance and animation remain **UNVERIFIED**. Device Perfetto/frame timing, GPU overdraw, allocation/memory, thermal and battery remain unmeasured.
- Next pass: inspect Today skeleton/card proportions and shared header/navigation spacing against the supplied recording; keep repeated feed cards free of per-card live blur and preserve the one shared background-only Prismal source.


## Feed-card and loading-skeleton geometry parity — 8 November 2026, 00:24 IST

**Branch freshness and dependency gate**
- Re-enumerated all 38 remote branches (page 1 complete, page 2 empty), all 35 PRs (21 open, 14 closed), current main, current PR #34, the complete Actions history (254 runs across 100 + 100 + 54), queue, workflow, latest run jobs/logs/artifacts and Releases.
- Implementation parent: black-box-bihar-news@ad16cecf06add374b3e19989d0829919d0055114, direct child of ed6c94190296c8d7af400b80f6fe26f2252fafe1; comparison is 111 ahead / 0 behind main@73b8a0d1ba33503fede02db2c3c0af082d724a12 and 54 ahead / 0 behind PR #16's corrected background-only Prismal/stability head 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890. PR #34 remains the single open draft integration PR targeting main@73b8a0d1ba33503fede02db2c3c0af082d724a12.
- brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96 is divergent (4 ahead / 17 behind); its GlassSurfacePolicy, SupportingGlassCard, Saved/You/stats memoization and supporting-surface implementation are already represented in the current integration tree. PR #30's 48dp target, PR #31's off-main appearance restore and PR #29's serialized bookmark persistence are also represented; their source branches remain untouched.
- ui/liquid-glass-system-continuation is 27 commits behind; ui/liquid-glass-performance is a stale divergent sibling; redesign/v0.3-search-reader is stale. None supersedes the selected line. PR #35 diagnostic-file reads, PR #32 RSS concurrency, PR #33 relative Atom parsing, PR #25/#26 publisher-resource security, PR #27 editorial action-gating and PR #24's brrrNEWS-based editorial exception remain separate; no unrelated source branch was modified.

**Implementation**
- Added pure FeedCardGeometryPolicy tokens shared by NewsCard and FeedSkeletonCard: responsive 16:9 image ratio, continuous 29dp card / 23dp image corners, matching 3dp outer inset, content padding and vertical rhythm, 48dp bookmark-action footprint, metadata-row height and title/description line heights.
- Loading placeholders now mirror both source/action and date/provenance rows, reserve the real bookmark hit-target footprint, and place text bars inside the same line-height slots as the article typography. This removes the known 176dp-vs-190dp image mismatch and the missing 48dp metadata-action space that could shift the first loaded cards.
- Today, Explore and Search skeleton rows now have stable loading keys and a shared content type. Article IDs/content types, filters, article text, card click/selection, bookmark persistence and feed ordering are unchanged.
- No new glass surface, blur/refraction, backdrop capture, sampler/readback, shader, graphics layer, shimmer, image decode, network request, timer, dependency or persistence operation was added. NewsCard retains one Coil request and palette extraction from that same decoded bitmap.

**Tests / CI**
- Added FeedCardGeometryPolicyTest for the 16:9 ratio, shared continuous geometry, 48dp action parity and typography rhythm.
- Exact parent ad16cecf06add374b3e19989d0829919d0055114 passed natural PR run #253 (https://github.com/Batmancpu/CurrentLens/actions/runs/42399278100), including WordNet generation, :app:testDebugUnitTest, :app:assembleDebug and artifact upload. Artifact 13584638074 is 39,095,814 bytes. This does not validate the new commit; its natural push/PR runs must complete. No manual dispatch/rerun.
- Latest overall run #254 is a separate PR #27 editorial branch failure: 291 tests completed, one UpscFeedPolicyTest.currentGstPolicyActionMapsToGsThreeEconomyButBoxOfficeFramingIsRejected assertion failed. It does not run on or invalidate this UI branch and is not changed here.
- Latest published Release remains v0.2.0-build-116; no Release was published by #253.

**Paired optimization and unverified checks**
- Shared immutable geometry tokens prevent skeleton/article layout drift; stable LazyColumn keys/content types improve item identity/reuse. The existing single background-only Prismal root source, expanded-only tab glyph source and 4s/6s foreground-idle adaptive luminance cadence are unchanged.
- No frame-time, jank, memory, GPU-overdraw, allocation, thermal or battery improvement is claimed from source inspection. OPPO Reno10 visual transition, font scaling, TalkBack, Reduce Motion, scroll smoothness, launch and Perfetto checks remain UNVERIFIED.
- Next: after exact-head natural CI passes, recheck Today/Explore/Search header-to-feed spacing and validate the skeleton-to-article transition on the OPPO device; retain standard non-glass article cards and no per-card blur.

## You, Saved and statistics supporting-surface consistency — 8 November 2026, 01:29 IST

**Freshness and dependency gate**
- Re-enumerated the complete 38-branch remote inventory (page 1 returned 38; page 2 empty), all 35 PRs (21 open, 14 closed), default branch and the owner-selected integration PR. Main remains 73b8a0d1ba33503fede02db2c3c0af082d724a12; the selected line before this batch was black-box-bihar-news@f2ad2b57e06863f04ee22c2b0d89ae3b0775fb32, 112 ahead / 0 behind main. PR #34 remains the sole consolidation draft, open and targeting main; no retarget or merge.
- PR #16's corrected background-only Prismal/stability line remains an ancestor. PR #19's Reader/Search implementation, PR #20's read-only stacked workflow, the selected PR #11/#12/#17 feed policies and the PR #13 conservative contrast policy remain represented in the integration tree.
- Inspected the independent accessibility/performance proposals against the current source: PR #30's 48dp TouchTargetPolicy is already used by the shared header and bookmark action; PR #31's AppearancePreferencesPolicy is restored with the existing IO snapshot; PR #29's bookmark read/modify/write is serialized on IO. Their source branches remain untouched.
- PR #35's bounded diagnostic reads are a separate startup-hardening proposal, not included in this UI pass. PR #32 and #33 each passed their own exact-head natural CI but both change NewsRepository.kt; keep them for one joint parser/concurrency reconciliation. PR #27's latest head has no natural PR run; do not integrate it. PR #25/#26 remain behind a combined publisher-resource security review. Divergent brrrNEWS, stale ui/liquid-glass-performance and redesign/v0.3-search-reader are not baselines.
- The exact pre-batch active head f2ad2b57e06863f04ee22c2b0d89ae3b0775fb32 passed natural PR run #256 (run ID 42404197533): WordNet builder, :app:testDebugUnitTest, :app:assembleDebug and artifact upload succeeded; publish job skipped. Artifact 13586850838 is 39,102,734 bytes (SHA-256 6be6840c650537a5d29e95397576bcb4f71cc92635f0be8890ac6f89adf8a703). This validates only the pre-batch head, not the changes below.

**Implementation**
- Added SupportingCardGeometryPolicy to the existing surface-policy file: shared 16dp content padding, 6dp internal rhythm, 12dp section spacing, 9dp metric gaps, 88dp metric tiles, 80dp library destinations, 76dp statistic rows and 104dp empty-state panels.
- Standardised MetricTile, DestinationRow, You dashboard spacing, Saved empty states, Reading Stats daily rows and Quiz Stats attempt/empty surfaces. Saved, reading and quiz empty states now use one quiet supporting surface with high-contrast text instead of detached floating copy.
- Kept the existing 22dp continuous supporting-card shape, theme-aware static tint/rim, nearly opaque surface and cached brushes. No live blur/refraction, additional backdrop source, sampler/readback, shader, graphics layer, image decode, classification pass, dependency, timer, network or persistence path was added.
- Quiz questions/options, submit-only quiz persistence, article content cards, reader text, feed order and filter semantics are unchanged.

**Tests / paired optimization**
- Added a focused JVM assertion for shared padding, spacing and minimum content/touch footprints in GlassSurfacePolicyTest.
- The optimization is deliberately a geometry/reuse pass: all supporting cards reuse the existing static SupportingGlassCard material and cached rim/tint brushes; no additional GPU render pass or blur surface is introduced. This is source-level scope evidence only, not a measured frame-time or memory win.
- The policy, test and UI commits are d8b7146e3546e0e248bb5db766ccb0ec8ac09184, 41b572e77f1a7eca48abb11729ac5dd175b3ee69 and dfcc792887cd56d7e5272ca4087c6517c88b3a44. Their exact code/test head ccce7beb506e85ca9d39ad0c23fcb054598f7bcc passed natural PR run #265 (run ID 42409282209): WordNet builder 4/4, offline dictionary 151,456 unique words, :app:testDebugUnitTest, :app:assembleDebug and artifact upload all succeeded; publish job skipped. Artifact 13588558658 is 39,105,323 bytes, SHA-256 c5cafdc0a4d64d62763742a6090d98986a258d122056aa668e25e79352700fe1. Full 203,461-character job log was inspected; only existing API/action deprecation warnings remain. This documentation update creates a newer head and is not covered by #265; await its natural run. No manual dispatch/rerun.

**Unverified / next**
- OPPO Reno10 visual balance, metric clipping at large font scale, TalkBack traversal, minimum tap-target reachability, short-screen overflow, animation, launch, Perfetto/frame timing, GPU overdraw, allocations/memory, thermal and battery remain UNVERIFIED.
- Next: inspect the exact natural run for the final documentation head, then continue the whole-app transient-control and shared-header spacing audit. Keep the one background-only Prismal capture, expanded-only tab glyph source, one Coil decode and foreground-idle adaptive-luminance cadence unchanged.

## Search clear focus retention and opacity transition — 8 November 2026, 02:28 IST

**Freshness and dependency gate**
- Re-enumerated the full remote branch inventory: 39 branches (page 1 complete, page 2 empty), 36 pull requests (22 open, 14 closed), the default branch, current integration PR, Actions history/queue, workflow, exact run jobs/logs/artifacts and Releases.
- Source is `black-box-bihar-news@4a7a8f8c1db651d8b06d636836c8221569938ff8`, 118 commits ahead / 0 behind `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; PR #34 remains open/draft, targeting main. The corrected PR #16 stability/background-only Prismal lineage and the consolidated Reader/Search/navigation work are ancestors of this branch.
- New PR #36 is a direct child (3 ahead / 0 behind) adding an Asia/Kolkata midnight refresh policy and focused tests. Its exact PR head passed natural run #268, but it is a separate Today-date change and remains an independent open proposal; it was not merged or folded into this screen-sized Search pass. PR #35's bounded diagnostic reads remain a divergent startup-only proposal (6 ahead / 8 behind); no startup claim is made. PR #30 48dp targets, #31 off-main appearance restore and #29 serialized bookmark IO are already represented. PR #24's brrrNEWS-based material code is superseded by the current SupportingGlassCard/GlassSurfacePolicy implementation; the stale Liquid Glass and v0.3 branches remain non-baselines.
- Exact active parent 4a7a8f8c1db651d8b06d636836c8221569938ff8 passed natural PR run [#267](https://github.com/Batmancpu/CurrentLens/actions/runs/42409496553) and push run [#266](https://github.com/Batmancpu/CurrentLens/actions/runs/42409492764). Run #267 completed WordNet builder tests (4/4), generated the 151,456-word offline dictionary, passed `:app:testDebugUnitTest` and `:app:assembleDebug`, and uploaded artifact `13588613947` (39,105,323 bytes; SHA-256 `383369cd4c23c227920f7e1dae2c7d2ca4c9e773f93e0ffb98988b5ed96a1090`). Full 203,194-character job log was inspected; only existing deprecation/packaging warnings remain. Publish job skipped.
- Latest overall run #268 is PR #36's exact head, not the Search/UI branch. The Actions history has 268 runs (100 + 100 + 68), queue is empty; latest Release remains `v0.2.0-build-116`. No Release was published.

**Implementation**
- Kept the clear affordance inside the existing expanded SearchMorphField capsule and retained its 48dp target, TalkBack label and non-empty-query/active-morph visibility rule.
- Reserved the 48dp trailing slot whenever the field is active, so clearing the query no longer changes the BasicTextField's available width or shifts its text/cursor. The icon now uses a small opacity-only enter/exit transition; Reduce Motion snaps the visibility change.
- The existing root Reduce Motion state is passed into SearchMorphField. Clearing changes only `searchQuery`, then explicitly re-requests the existing FocusRequester and keyboard; Search remains expanded and GS/subject/category filters are untouched.
- No new Prismal surface, backdrop capture, sampler/readback, blur/refraction pass, shader, image decode, network request, persistence operation or dependency was added. The only new animation is bounded to the 48dp clear control; the shared root background-only capture and IME morph geometry are unchanged.

**Tests, CI and unverified checks**
- No new JVM test was added for a Compose-only opacity/focus interaction. Existing Search morph, 48dp touch-target, feed-navigation and Reader-chrome policy tests remain in the suite. The exact new head must receive its own natural push/PR CI; do not dispatch or rerun Actions.
- OPPO Reno10 keyboard retention, cursor stability, filter preservation, TalkBack announcement, Reduce Motion behaviour, font scaling and morph smoothness remain UNVERIFIED. No frame-time, GPU, memory, battery or thermal improvement is claimed from source inspection.

**Next**
- After the exact-head natural run completes, continue the shared-header/transient-control consistency audit and inspect Appearance preview fidelity against the actual shared material tokens. Preserve the one root backdrop source, expanded-only tab glyph source and existing 4s/6s foreground-idle adaptive luminance cadence.


## Supporting-surface intensity binding — 8 November 2026, 04:29 IST

**Freshness and dependency gate**
- Re-enumerated all 40 remote branches (page 1: 40, page 2: 0), all 37 pull requests (23 open, 14 closed), main, the active integration PR, the complete 277-run Actions history (100 + 100 + 77; page 4 empty), queue, workflow, latest jobs/logs/artifacts and Releases.
- The only authorised source is `black-box-bihar-news@6f777f8777b3168383361dc79109b3d1d75b612b`, 120 ahead / 0 behind `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`, merge base main. PR #34 remains open/draft, head equal to this branch, base main. PR #16's corrected background-only Prismal capture remains an ancestor. No branch creation, retarget, merge or force update.
- The new PR #37 Search RowScope repair is already functionally present in the current integration tree; its separate branch also contains continuity documentation and is not merged or modified. PR #36's Asia/Kolkata midnight refresh remains a separate candidate based on an older integration parent. PRs #32/#33 both overlap `NewsRepository.kt`; PR #35 is startup-diagnostic-only. Existing PR #30 48dp targets, #31 off-main appearance restore and #29 serialized bookmark persistence are represented in the current tree; no wholesale branch replay.
- The integration workflow `.github/workflows/build-apk.yml` has read-only build/test permissions and a separate main-push-only release-write job. It naturally covers push and pull-request validation for black-box. Main's workflow remains push-only with broader write permission; branch CI is not claimed as a main-targeted PR check.
- Exact current head passed natural push run [#276](https://github.com/Batmancpu/CurrentLens/actions/runs/42418532716) and pull-request run [#277](https://github.com/Batmancpu/CurrentLens/actions/runs/42418537305). Both completed WordNet builder/database generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload; publish job skipped. Run #277 artifact #13593000344 is 39,106,297 bytes, SHA-256 `1ae88bf11d4a2e4c8cefabea0f9abd59af6939e52265558922c7177a22aa4235`, expires 21 November 2026. These runs validate the pre-change head only.
- Latest published Release remains `v0.2.0-build-116` (6 November 2026). No new Release or manual workflow dispatch/rerun.

**Implementation**
- Added a root `LocalGlassIntensity` sourced from the committed appearance preference. Supporting cards across You, Saved, Reading Stats, Quiz Stats and the Settings explanation now derive their static tint and rim from the same Clear↔Frosted material scale.
- Kept supporting card surface opacity fixed (0.93 dark / 0.965 light) to protect dense text and statistics. Tint transitions are bounded from 0.09→0.145 dark / 0.055→0.095 light; rim transitions are 0.17→0.23 dark / 0.11→0.16 light. The default 0.28 preference stays close to the existing appearance.
- The Appearance preview locally overrides the material intensity and uses the same `SupportingGlassCard` for its article sample, so the preview demonstrates both the primary transient control tier and the quieter content-support tier. App-wide preference changes remain committed on slider release or preset selection.

**Paired optimization and tests**
- No new live blur, backdrop source, sampler/readback, shader, graphics layer, image decode, network request, dependency or persistence path. Supporting cards remain static gradients/rims over a near-opaque content surface.
- Tint/rim brushes are remembered by theme and resolved alpha. The app-level CompositionLocal changes only when the preference is committed; per-drag updates stay scoped to the Appearance preview and its one supporting sample.
- Extended `GlassSurfacePolicyTest` for exact Clear/Balanced/Frosted tint/rim bounds and non-finite fallback. Existing content-opacity, transient-effect and shared-geometry assertions remain.
- This new head awaits its own natural CI run. Do not use #276/#277 as validation of this commit and do not dispatch/rerun workflows.

**Unverified / next**
- OPPO Reno10 appearance fidelity, card text contrast at large font scale, TalkBack, Reduce Motion, frame timing, jank, allocation/memory, GPU overdraw, thermal and battery remain UNVERIFIED. No performance win is claimed from code inspection.
- Next: inspect this exact head's natural CI logs/artifact; then run the final whole-app shared-header, transient-control, accessibility and performance audit. Keep article text and repeated feed cards non-glass/no-per-card blur, one root background-only source, expanded-only tab glyph capture and the 4s/6s foreground-idle luminance cadence.


---

# Live implementation batch — Today index follows Asia/Kolkata midnight
**Audit:** 8 November 2026, 14:23 IST  
**Source branch / parent SHA:** `black-box-bihar-news@d5fab2ae9aa097e24180d831d6e0e8d64e441b43`  
**Base:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; PR #34 remains open/draft, unmerged, targeting main.

## Freshness and dependency gate
- Re-enumerated the complete remote inventory: **44 branches** (page 1: 44, page 2: 0) and **41 PRs** (**27 open, 14 closed**). Exact branch heads/timestamps/messages and PR head/base refs are recorded in `docs/BRANCH_CONSOLIDATION.md`.
- Active integration head `d5fab2ae9aa097e24180d831d6e0e8d64e441b43` is **138 ahead / 0 behind main** and **81 ahead / 0 behind** PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. PR #34 head/base match the active branch and unchanged main.
- PR #36 (`fix/today-ist-midnight-rollover-2026-11-08@5fbd14e6ef9be33d04a882ba463d4c72473419e2`) is 3 commits ahead / 20 behind the current integration head, based on `black-box-bihar-news@4a7a8f8c1db651d8b06d636836c8221569938ff8`. Its isolated delta is `AsiaKolkataDayPolicy.kt`, the BrrrNewsApp date-state/timer insertion and two pure JVM tests. Its exact source head passed natural run #268; this selective port needs its own exact-head natural CI.
- PR #37's Search-clear RowScope repair is already present in the active Search field. PR #30's 48dp target policy and PR #31's off-main appearance restore are represented. PR #39 remains separate because its stable signing path needs owner-managed secrets and an explicit first-install/local-data migration decision. PRs #40/#41 are unvalidated main-based RSS security/field-bound proposals that need reconciliation against the newer `NewsRepository`; PR #38 overlaps #32/#33 parser work. The divergent `brrrNEWS`, old Liquid Glass and v0.3 branches are not valid baselines.
- No other branch was changed. No main write, merge, retarget, force update, source-branch mutation, manual workflow dispatch/rerun or Release publication.

## Parent exact-head natural CI
- PR run [#326](https://github.com/Batmancpu/CurrentLens/actions/runs/42461549072) and push run [#325](https://github.com/Batmancpu/CurrentLens/actions/runs/42461544503) passed on exact parent SHA `d5fab2ae9aa097e24180d831d6e0e8d64e441b43`. Full PR job log: WordNet builder 4/4; 151,456 unique words (50,950,144-byte database); `:app:testDebugUnitTest`; `:app:assembleDebug`; APK artifact upload. Gradle reported `BUILD SUCCESSFUL in 1m 9s`; Release job skipped.
- PR artifact [#13610236262](https://github.com/Batmancpu/CurrentLens/actions/runs/42461549072/artifacts/13610236262): 39,118,591 bytes; ZIP SHA-256 `db1f34ac9d690e85d398f5b25d5defea6686b5af477b6f70653f3b5fdf464b44`; expires 22 November 2026 08:09 UTC.
- Actions history is fully paginated: **326 runs** (195 success, 67 failure, 64 cancelled); queue/in-progress **0**. Active workflow `.github/workflows/build-apk.yml` has read-only build/test permissions, natural push/PR triggers for main, PR #16's base and black-box, and a separate main-push-only Release-write job. Latest Release remains `v0.2.0-build-116`; no Release was published.

## Implementation
- Replaced BrrrNewsApp's immutable composition-time Today date with a state-backed Asia/Kolkata calendar date.
- While the app is resumed, one cancellable coroutine waits directly until the next IST midnight, refreshes the date, and lets the existing `LaunchedEffect(articles, todayDate, localStateLoaded)` rebuild the editorial index. The timer is cancelled on pause/stop and the date is refreshed immediately on resume.
- This fixes a foreground-across-midnight stale Today index without refetching RSS, changing publisher timestamps, altering Explore retention, or touching saved/read/quiz data. Manual wall-clock changes while continuously resumed are not polled; resume refresh covers changes after backgrounding.

## Paired cost control, tests and remaining verification
- One lifecycle-scoped delay, no polling, no background wakeup, no new network request, classification pass, backdrop capture/readback, blur/refraction, shader, graphics layer, image decode, dependency or persistence path. Existing off-main feed-index build and state invalidation are reused.
- Added pure tests for the 18:30 UTC / 00:00 IST date boundary and the next-midnight delay (1 second before boundary; 24 hours at boundary). The source PR's run #268 passed, but this integration commit still requires its own natural CI.
- No material/render-pass changes. OPPO Reno10 midnight transition, device launch, TalkBack, font scaling, Reduce Motion, frame timing/jank, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**.

**Next:** inspect the new exact-head natural push/PR jobs, full logs and APK artifact; then continue the remaining transient-control and whole-app consistency audit without adding glass to dense reading/data surfaces.

 
## Live implementation batch — cache NewsCard palette glow draw resources (8 November 2026, 15:28 IST)

**Freshness and dependency gate**
- Re-enumerated all **44 remote branches** (page 1 complete; page 2 empty), all **41 pull requests** (**27 open, 14 closed**), and all branch head SHAs, commit timestamps/messages and PR head/base refs. The active source is black-box-bihar-news@bf87623548e72a6e3fc757a26e7ad7af44a73cf0, PR #34's exact open/draft head; comparison to main@73b8a0d1ba33503fede02db2c3c0af082d724a12 is **139 ahead / 0 behind** with main as merge base. PR #16's corrected background-only Prismal/stability line is an ancestor; PR #19 Reader/Search and PR #20 read-only stacked workflow changes are already consolidated in this branch. No branch was created, retargeted, merged or modified.
- The current branch already contains the PR #37 Search clear RowScope repair, PR #36 Asia/Kolkata midnight refresh, and the current equivalents of PR #30's 48dp target policy, PR #31's off-main appearance restore and PR #29's serialized bookmark persistence. The current exact-head CI confirms the integrated Search/Today source compiles.
- Kept separate: PR #39 stable signing is 6 ahead / 4 behind this branch and changes signing/build workflow plus migration documentation; it is not a UI baseline. PRs #40/#41 and #38/#32/#33 are independent RSS host/field/parser/concurrency work with divergent NewsRepository changes. PR #35 diagnostic-read bounds, PR #25/#26 publisher-resource restrictions and PR #27 editorial gating remain separate reviews. The old brrrNEWS, ui/liquid-glass-performance, ui/liquid-glass-system, continuation and redesign/v0.3-search-reader branches are not fresher safe UI baselines; their compare results are divergent or behind.
- Actions history is fully paginated: **328 runs** (100 + 100 + 100 + 28); queued and in-progress runs are **0**. The latest natural PR run [#328](https://github.com/Batmancpu/CurrentLens/actions/runs/42465403337) and push run [#327](https://github.com/Batmancpu/CurrentLens/actions/runs/42465399086) both passed on the exact parent SHA bf87623548e72a6e3fc757a26e7ad7af44a73cf0. PR job #128683637204 completed checkout, Java/signing cache, Android SDK/platform, Gradle, WordNet builder/database, :app:testDebugUnitTest, :app:assembleDebug and artifact upload successfully; the separate publish job was skipped. Artifact [#13611522689](https://github.com/Batmancpu/CurrentLens/actions/runs/42465403337/artifacts/13611522689) is **39,120,459 bytes**, SHA-256 5dbedc5c5445255c8b177093421dfc65974cb7c03f4028b4d7488e17751bcca2, expiring 22 November 2026 09:03 UTC. This validates the parent only, not the new commit below.
- .github/workflows/build-apk.yml remains read-only for build/test with a separately gated main-push Release-write job. Latest published Release remains v0.2.0-build-116; no Release was published.

**Implementation**
- Changed only NewsCard's existing ambient image-palette glow from drawBehind to drawWithCache. The radial Brush, overscan bounds and rounded-corner geometry are now cached against the card's measured size and current decoded-image palette; a palette or size change rebuilds the cache, ordinary draw invalidations reuse it.
- Preserved the single Coil ImageRequest, one decoded bitmap palette sample, card shape/content, image sizing and standard non-glass content surface. No per-card blur/glass, second decode, new Prismal surface, backdrop capture/readback, shader, dependency, network, persistence or new animation was introduced. Existing graphicsLayer(clip = false) is unchanged to preserve the glow's overflow drawing.
- Paired optimization is allocation/cache scope only: it removes repeated radial-gradient Brush and geometry construction during redraws. This is source-level evidence, not a measured frame-time, allocation, memory, GPU, battery or thermal improvement.

**Tests and verification**
- No new pure-policy test was added because the change is a Compose draw-cache lifecycle substitution with no changed policy output; existing feed geometry and app JVM tests remain the regression suite. The next natural workflow must validate this exact commit; no manual dispatch/rerun.
- OPPO Reno10 palette appearance, clipping, scroll jank, frame timing, allocation churn, GPU overdraw, memory, thermal and battery remain **UNVERIFIED**. CI compile success is not device-launch or visual proof.

**Next**
- Inspect this exact head's natural PR run, complete job steps/logs and APK artifact before another UI change. Then complete the shared header/transient-control and whole-app consistency audit without adding blur to repeated feed cards or dense reading/data surfaces.


## Appearance settings adopt the shared glass page header — 8 November 2026

**Branch freshness and source**
- Re-enumerated the complete remote graph: **44 branches** (page 1 = 44, page 2 = 0) and **41 pull requests** (**27 open, 14 closed**). The complete per-branch head, commit timestamp/message and PR relationship table is appended to \`docs/BRANCH_CONSOLIDATION.md\`.
- Owner-selected source remains \`black-box-bihar-news\`. The pre-change source was \`8c1239a6762cacfce281233b96298096bf28a3a6\`, **142 ahead / 0 behind** \`main@73b8a0d1ba33503fede02db2c3c0af082d724a12\`; PR #34 head matched that source, was open/draft and targeted main. This screen-sized commit is \`f73ea24fe71489813df3f576ee843d564b51de56\`, direct child of that source; current branch/PR #34 head matches and is **143 ahead / 0 behind main**. No retarget, merge, force update or source-branch mutation.
- PR #16's corrected background-only Prismal/stability lineage remains an ancestor. The current branch is 86 commits ahead of that PR #16 head. PR #37's Search RowScope correction is already present in the active Search field; PR #30's \`TouchTargetPolicy.kt\`, PR #36's Asia/Kolkata day policy/test, PR #31's appearance-preference policy and PR #29's bookmark-set policy match current source blobs and were not replayed.
- Kept separate: PR #39 stable signing is divergent (6 ahead / 7 behind the pre-change source) and requires owner-managed signing secrets, a pinned certificate fingerprint and an explicit first-install/local-data migration decision. PRs #40/#41 are main-based RSS redirect/field changes; #32/#33/#38 are overlapping parser/concurrency proposals; #35 is diagnostic-only; #25/#26 require a joint publisher-resource security review; #27 is editorial-only. The adaptive-contrast source branch #13 differs from the current conservative implementation and was not copied. Divergent \`brrrNEWS\`, stale \`ui/liquid-glass-performance\`, old Liquid Glass siblings and \`redesign/v0.3-search-reader\` are not baselines.

**Parent exact-head CI**
- The pre-change source \`8c1239a6762cacfce281233b96298096bf28a3a6\` passed natural push run [#333](https://github.com/Batmancpu/CurrentLens/actions/runs/42472112589) and PR run [#334](https://github.com/Batmancpu/CurrentLens/actions/runs/42472116961). Both build jobs completed checkout, Java, stable test-signing cache, Android SDK/platform, Gradle, WordNet builder tests (4/4), 151,456-word offline dictionary generation, \`:app:testDebugUnitTest\`, \`:app:assembleDebug\` and artifact upload; the publish job was skipped.
- PR artifact [#13614265719](https://github.com/Batmancpu/CurrentLens/actions/runs/42472116961/artifacts/13614265719): **39,121,080 bytes**, SHA-256 \`95dd164522c666932f4dccb52b8c918c164a4879f0e42474b3cb161aa8750c11\`, expires 22 November 2026 10:38 UTC. Push artifact [#13614275602](https://github.com/Batmancpu/CurrentLens/actions/runs/42472112589/artifacts/13614275602): **39,121,079 bytes**, SHA-256 \`507da7fd7fcddd2c23c485f73ae194473cc546cce9f4911ab5dc7a41537b2942\`, expires 22 November 2026 10:38 UTC.
- Actions history is fully paginated: **334 runs** (100 + 100 + 100 + 34), **203 success / 67 failure / 64 cancelled**, queue/in-progress **0**. The current branch workflow retains read-only build/test permissions and an isolated main-push Release-write job; main's workflow remains push-only with broader write permission. Latest Release remains \`v0.2.0-build-116\` (6 November 2026); no Release was published. These runs validate the parent, not commit \`f73ea24\`; the new exact head awaits its natural push/PR runs.

**Implementation**
- Replaced the Appearance screen's plain scrolling 52dp title/back row with the existing \`HomeGlassHeader\`: the same 24dp continuous geometry, 9dp low-cost header profile, provenance-safe title/subtitle hierarchy and 48dp back target used by Today/Explore/Search and You subpages. The header stays fixed while only the editor/content column scrolls.
- Reused the existing background-only root Prismal capture; no new capture source, sampler, readback, shader, graphics layer, image decode, dependency, network request or persistence path was added. This introduces one additional compact live header surface on Settings using the existing header component; it does not create a second glass pipeline. The Appearance preview remains its existing single compact \`PrismalTransientSurface\`, with static supporting content beneath it.
- \`GlassLuminanceActivationPolicy\` continues to pause the shared 8×8 bottom-band readback while Settings is open; its 4s/6s cadence and foreground/feed-motion/Reader/Reduce Transparency gates are unchanged. Slider drag remains child-scoped to the preview; app-wide intensity is committed only on release or preset selection, so the new header does not recompose for each preview tick.
- No policy/state semantics changed, so no new JVM policy test was added. Existing UI/policy tests and the next exact-head natural compile/test workflow are the regression checks.

**Unverified / next**
- OPPO Reno10 Settings header/preview appearance, pinned-header scroll behaviour, back-target reachability, TalkBack, large font scale, Reduce Motion/Reduce Transparency, frame timing, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. No measured performance win is claimed.
- After exact-head natural CI completes, continue the final whole-app transient-control and shared-header consistency audit. Do not increase adaptive readback cadence or add per-card live blur.

 
## Live implementation batch — unified continuous geometry for glass controls (8 November 2026, 17:22 IST)

**Branch freshness and source**
- Re-enumerated all **44 remote branches** (complete first page; second page empty) and **41 pull requests** (**27 open, 14 closed**). Default branch remains main at 73b8a0d1ba33503fede02db2c3c0af082d724a12. The owner-selected active line is black-box-bihar-news at pre-change head c7f3711ddb13a4bd595bbde7d003f641f58eb705, **144 ahead / 0 behind main**; PR #34 is open/draft and targets main. The source is **87 ahead / 0 behind PR #16's corrected background-only Prismal/stability head** 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890. The former PR #19 UI branch is fully ancestral to this line (active branch is 74 commits ahead).
- PR #37's Search RowScope fix, #30's 48dp TouchTargetPolicy, #31's off-main AppearancePreferencesPolicy and #36's AsiaKolkataDayPolicy/tests are already represented in the active code; no duplicate port. PR #13's older adaptive contrast differs from the active conservative shared 8×8 probe (4s/6s, foreground-home only, paused during feed motion/Reader/Settings/background). PR #39 remains a divergent signing/workflow proposal requiring owner-managed secrets, pinned certificate and explicit local-data migration approval. PRs #40/#41 and #32/#33/#38 remain overlapping RSS/parser reviews; #25/#26 require joint publisher-resource security review; #35 is diagnostic-only. No source branch was modified.
- Main's latest workflow remains push-only with workflow-wide contents:write. The active integration workflow is .github/workflows/build-apk.yml at 6cef6ca19f3babd331c9deb59be9da0067c6fa66: read-only build/test and a separate contents:write publish job restricted to main pushes. PR #34 remains the only consolidation PR; no retarget, merge or main write.

**Parent exact-head natural CI**
- Before this batch, exact head c7f3711ddb13a4bd595bbde7d003f641f58eb705 passed natural push run [#337](https://github.com/Batmancpu/CurrentLens/actions/runs/42474559492) and PR run [#338](https://github.com/Batmancpu/CurrentLens/actions/runs/42474565983). Both build jobs completed checkout, Java, cached stability test key, Android SDK/platform, Gradle, WordNet builder, :app:testDebugUnitTest, :app:assembleDebug and APK artifact upload; separate publish jobs were skipped. Full logs show 151,456 unique offline WordNet words (50,950,144-byte database), BUILD SUCCESSFUL in 1m33s (push) and 1m30s (PR).
- PR artifact [#13614966470](https://github.com/Batmancpu/CurrentLens/actions/runs/42474565983/artifacts/13614966470): **39,119,852 bytes**, SHA-256 1a8b4939f3b1000433919c01c3dd013d206fbbcb4f4fb80bd36dc89fa0b688c3, expires 22 November 2026 11:12 UTC. Push artifact #13614399835: **39,119,848 bytes**, SHA-256 6ecf491fcea2d7433129ffa896d6055418e70d4a0d3acdfb07195bdaded33ef5, expires 22 November 2026 11:12 UTC.
- Fully paginated Actions history: **338 runs** (207 success, 67 failure, 64 cancelled), queued/in-progress **0**. Latest published Release remains v0.2.0-build-116; no Release was created by #337/#338. These runs validate the parent only; the new shape batch needs its own natural exact-head run.

**Implementation — continuous control geometry**
- Replaced the remaining conventional RoundedCornerShape usage on Explore category chips, shared filter/action pills, the Search filter sheet, Reader Aa menu and Appearance preview Search control with the same custom continuous-corner geometry used by the app's existing feed/supporting surfaces. Centralised 16dp control, 28dp filter-sheet and 18dp Reader-menu radii in GlassSurfacePolicy.
- Added one immutable shared Shape instance per radius; the Explore, Saved/Stats, Search, Reader and Appearance controls now reuse them instead of constructing separate pill shapes during recomposition. The filter-sheet and Aa-menu outer containers now match their inner Prismal surfaces. Skeleton line ends retain their simple 6dp rounded geometry; article/quiz content surfaces are unchanged.
- Added GlassSurfacePolicyTest coverage for the shared control/sheet/menu radius tokens. All chip selection semantics, 48dp targets, active filter state, sheet dismissal, Reader font-size actions, Reduced Transparency fallback and search/IME behaviour are unchanged.

**Paired cost and coverage**
| Surface | Material / geometry | Effect scope and reuse | Accessibility / fallback | Evidence |
|---|---|---|---|---|
| Explore category chips; Saved/Stats filter pills; Search filter action | Tier 2 static control glass, 16dp continuous corners | No added blur/capture/readback; one shared immutable Shape object | Existing RadioButton/Tab/Button semantics and 48dp targets; Reduced Transparency uses the same stronger static fill/rim | Source diff; exact-head CI pending |
| Search filter sheet | Tier 2 transient Prismal glass, 28dp continuous outer/inner geometry | Reuses existing single PrismalTransientSurface and root background-only source | Existing modal dismissal/drag semantics; static fallback when effects are reduced | Source diff; exact-head CI pending |
| Reader Aa menu | Tier 2 transient Prismal glass, 18dp continuous outer/inner geometry | Reuses existing menu surface; no new capture or render pass | Existing Aa entry point and text-size actions unchanged | Source diff; exact-head CI pending |
| Appearance preview Search field | Tier 2 static preview control, shared 16dp shape | Reuses one preview Prismal surface; no nested blur | Existing 48dp height and local preview-only intensity state | Source diff; exact-head CI pending |

- Render-pass delta: **0**. Added backdrop captures/readbacks, shaders, blur surfaces, graphics layers, image decodes, dependencies, timers, network calls and persistence work: **0**. The cost control is shared immutable geometry and removal of repeated per-composable Shape construction; this is a source-level allocation reduction, not a measured frame-time or memory win.
- OPPO Reno10 appearance, corner rendering, touch/selection behaviour, TalkBack, font scaling, Reduce Motion/Reduce Transparency, frame timing/jank, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. CI success does not establish device launch stability or animation smoothness.

**Next**
- Inspect the new exact-head natural push/PR jobs, complete logs and artifact before another UI batch. Continue only with an evidence-backed whole-app inconsistency; preserve one root background-only capture, the expanded-only tab glyph source, low-cadence adaptive luminance and calm high-contrast article/quiz content. No Release publication, main write, merge, manual dispatch/rerun, retarget or source-branch deletion.
