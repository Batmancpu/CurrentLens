# Latest live branch/PR/CI audit — 8 November 2026, 14:23 IST

**Repository:** `Batmancpu/CurrentLens`  
**Default:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` (unchanged).  
**Active:** `black-box-bihar-news@d5fab2ae9aa097e24180d831d6e0e8d64e441b43`, PR #34 open/draft → main; **138 ahead / 0 behind main** and **81 ahead / 0 behind** PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.  
**Inventory:** 44 remote branches (44 + 0 pagination); 41 PRs (27 open, 14 closed). No source branch was deleted or modified.

## Baseline decision and branch freshness
- The owner-selected black-box branch remains the latest coherent UI integration line: it is a direct descendant of PR #16, retains the corrected background-only root capture and consolidated Reader/Search/feed-navigation implementation, and contains the latest verified UI state.
- PR #36 is a 3-commit/20-behind Today-date branch; its isolated Asia/Kolkata calendar policy was selectively ported into black-box without copying its older MainActivity. Source PR #36 exact head passed natural run #268; the integration commit requires its own exact-head CI.
- PR #37's RowScope repair, PR #30's 48dp target policy, PR #31's off-main appearance restore, PR #13's conservative 8×8 luminance probe and the prior PR #19/#20 work are represented in the active tree.
- PR #39 remains separate: owner-managed secrets and a first-install/local-data migration decision are required. PRs #40/#41 are unvalidated main-based RSS security/field-bound proposals; PR #38 and #32/#33 overlap parser/network code. The divergent brrrNEWS and stale Liquid Glass/v0.3 branches are not baselines.
- No main write, merge, retarget, force update, source-branch mutation, manual workflow dispatch/rerun or Release publication.

## Actions, workflow and Release
- Exact parent PR run [#326](https://github.com/Batmancpu/CurrentLens/actions/runs/42461549072) and push run [#325](https://github.com/Batmancpu/CurrentLens/actions/runs/42461544503) passed on `d5fab2ae9aa097e24180d831d6e0e8d64e441b43`: WordNet 4/4; 151,456 unique words / 50,950,144-byte database; `:app:testDebugUnitTest`; `:app:assembleDebug`; APK artifact upload. Build successful in 1m 9s; Release job skipped.
- PR artifact #13610236262: 39,118,591 bytes; ZIP SHA-256 `db1f34ac9d690e85d398f5b25d5defea6686b5af477b6f70653f3b5fdf464b44`; expires 22 November 2026 08:09 UTC.
- Actions history: 326 runs (195 success, 67 failure, 64 cancelled); active/queued 0. `.github/workflows/build-apk.yml` uses read-only build/test permissions, natural push/PR validation for main, PR #16's base and black-box, and a separate main-push-only Release-write job. Latest published Release remains `v0.2.0-build-116`; no Release was published.

## Complete remote branch inventory (44)

| Branch | Exact head SHA | Latest commit UTC | Latest commit message | PR relationship / disposition |
|---|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02:12Z | test(a11y): cover minimum icon action target | PR #30 open head; policy represented |
| `black-box-bihar-news` | `d5fab2ae9aa097e24180d831d6e0e8d64e441b43` | 2026-11-08 08:07:02Z | docs(reader): scope chrome policy comments correctly | PR #34 open/draft head; active integration |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30Z | ui: consolidate lightweight supporting glass surfaces | PR #24 open base; divergent/superseded material |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53Z | ci: validate pull requests with read-only build permissions | PR #18 open head; compatible workflow intent represented |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27Z | ci: restore Android SDK setup before sdkmanager | PR #20 closed/merged into black-box only |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35Z | Correct icon artwork accessibility name to brrrNews | PR #14 closed/unmerged; head equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35Z | fix: refresh article card metadata when feed content changes | PR #6 open head; editorial work represented selectively |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27Z | fix: select Atom article alternate links deterministically | PR #12 open head; compatible parser work selectively represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32Z | feat: expose publication date and publisher RSS provenance on cards | PR #11 open head; provenance represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20Z | fix: minimise shareable process-exit diagnostics | PR #16 open head; corrected Prismal/stability ancestor |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `2468ed33326ac7310a2ddd5f993495e4051245b8` | 2026-11-08 03:30:19Z | test(editorial): cover reality TV and television promotion | PR #27 open head; independent editorial gate |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40Z | Verify a specific parliamentary bill remains eligible for the GS II feed | PR #21 open head; combined editorial work represented |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47Z | fix(editorial): match policy action and target as separate words | PR #24 open head; based on divergent brrrNEWS |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32Z | test: align economy signal count with repo-rate mapping | PR #2 merged into main |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28Z | fix: tighten UPSC editorial signal matching | PR #1 merged into main |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06Z | fix: use supported calendar navigation glyph | PR #5 merged into main |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44Z | test: preserve Parliament bill headline variants | PR #23 merged into black-box only |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39Z | Validate RSS redirects against publisher allowlists | PR #15 closed/unmerged; safeguards superseded by PR #16 |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10Z | fix(quiz): qualify selected semantics receiver | PR #28 merged into black-box only |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | 2026-11-07 14:59:24Z | test(rss): correct relative xml base fixture | PR #33 open head; NewsRepository overlap |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46Z | test(reader): cover publisher-only image resources | PR #25 open head; security review |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29Z | fix: deduplicate RSS tracking URL variants | PR #17 open head; dedupe represented |
| `fix/search-clear-row-scope-compile-2026-11-08` | `9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | 2026-11-07 21:55:56Z | docs: record verified search clear compile repair | PR #37 open head; RowScope correction already present |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03Z | fix: capture Search glass tint before draw callback | PR #10 merged into main |
| `fix/stable-release-signing-2026-11-08` | `4250de5040a8ca0efbf96a990218ef7d69ef26e8` | 2026-11-08 07:03:31Z | docs: record stable signing validation and migration gate | PR #39 open head; owner secret/data migration gate |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53Z | fix: diagnose launch crashes and move feed indexing off main | PR #9 merged into main |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06Z | fix: harden cold start and reduce feed rendering workload | PR #7 merged into main |
| `fix/today-ist-midnight-rollover-2026-11-08` | `5fbd14e6ef9be33d04a882ba463d4c72473419e2` | 2026-11-07 20:48:43Z | test(today): cover Asia/Kolkata midnight rollover | PR #36 open head; selected and ported in this batch |
| `fix/validate-rss-article-hosts-2026-11-08` | `dcdbce939d0626767163f6133a42b60d23af7e57` | 2026-11-08 06:52:22Z | Test publisher-bound RSS article URLs | PR #40 open head; unvalidated NewsRepository/security proposal |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35Z | Correct icon artwork accessibility name to brrrNews | Default branch; base of open PRs #6, #8, #12, #13, #17, #18, #21, #40 and #41 |
| `perf/bound-rss-entry-parsing-2026-11-08` | `adade1ed8494c66b7e567de87f61f3dac3da7788` | 2026-11-08 03:31:36Z | fix(test): correct RSS budget test identifier | PR #38 open head; parser overlap with #32/#33 |
| `perf/bound-rss-field-values-2026-11-08` | `fb5f7517e8b03d92d72964622a156a68ff1f6397` | 2026-11-08 08:36:27Z | Test Unicode-safe RSS field bounds | PR #41 open head; unvalidated main-based parser proposal |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846224182f16981b15e5c803761e26d` | 2026-11-07 17:34:16Z | Record successful bounded-diagnostic CI | PR #35 open head; diagnostic-only |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04Z | Verify legacy markers survive recent-history retention | PR #22 open head; legacy markers retained |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29:26Z | docs: record bounded RSS fetch review and CI state | PR #32 open head; parser/network review |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11:45Z | fix(startup): keep appearance snapshot API module-internal | PR #31 open head; IO restore represented |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05:25Z | perf(storage): move bookmark mutations off the UI thread | PR #29 open head; persistence path represented |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31Z | perf: move read-marker persistence off main thread | PR #8 open head; history policy reconciled |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27:22Z | privacy: block third-party publisher reader subresources | PR #26 open head; joint security review |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48Z | fix: make selection decoding fallback valid JSON | No PR; stale, 230 behind active |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16Z | ui: restore low-cadence foreground adaptive glass contrast | PR #13 open head; conservative 8x8 policy represented |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39Z | fix: import bottom navigation glyphs | PR #4 closed/unmerged; PR #3 merged earlier code; stale |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36Z | docs(reader): record native selection collision guard | PR #19 closed/merged into black-box only |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34Z | fix: finish syllabus metadata and thin-film feed eligibility | No PR; 52 behind active |

## Complete PR inventory (41)

| PR | State / merge | Head branch @ SHA | Base branch @ SHA | Updated UTC |
|---:|---|---|---|---|
| #1 | closed/merged | `fix/editorial-token-boundaries-2026-11-06@0045c24637f162f1c13dceb1f8878f42f6eb15fd` | `main@682432a6a3122e3ddcd2710e052a30fc2958ed6c` | 2026-11-06T05:14:24Z |
| #2 | closed/merged | `fix/editorial-signal-count-test@4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | `main@f758af2ecc381ebeb6f9bb96aab172967eb973fb` | 2026-11-06T05:16:51Z |
| #3 | closed/merged | `ui/liquid-glass-performance-2026-11-06@b51d5e79ee8d3261c5458712f4e869ada1693697` | `main@6cf84bacd3dce62081cf0efee386e73411357ce0` | 2026-11-06T06:23:55Z |
| #4 | closed/unmerged | `ui/liquid-glass-performance-2026-11-06@2e9c7f62ca65a899e18d9026e1535392835b3724` | `main@df28a43c0beae2e0a6ef74589a56c0e1786d66d3` | 2026-11-06T06:26:39Z |
| #5 | closed/merged | `fix/navigation-icon-imports-2026-11-06@fa8868940c08ed5b4a9def014a850e8a9dcb3490` | `main@df28a43c0beae2e0a6ef74589a56c0e1786d66d3` | 2026-11-06T06:27:23Z |
| #6 | open/draft | `feat/gs1-geography-editorial-coverage@ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | `main@17b47e453ca02ade9d10110614736e904c9a4490` | 2026-11-07T00:14:20Z |
| #7 | closed/merged | `fix/startup-stability-performance-2026-11-06@654e660347de692ef88531dfa49c610269d69671` | `main@17b47e453ca02ade9d10110614736e904c9a4490` | 2026-11-06T07:19:26Z |
| #8 | open/draft | `perf/off-main-session-persistence-2026-11-06@ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | `main@81de60cca96bbbbfb066261b23e3f9843f82b7ee` | 2026-11-06T10:50:48Z |
| #9 | closed/merged | `fix/startup-crash-diagnostics-feed-jank-2026-11-06@5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | `main@81de60cca96bbbbfb066261b23e3f9843f82b7ee` | 2026-11-06T07:42:06Z |
| #10 | closed/merged | `fix/search-glass-theme-capture-2026-11-06@c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | `main@2475970123a2a2fb0e05be25d1d9a45061186c73` | 2026-11-06T07:44:54Z |
| #11 | open/draft | `fix/cache-publication-date-integrity-2026-11-06@be5151e674c2bd4fa9beb166fc38ddf622781f84` | `black-box-bihar-news@c6223c93de84cb25d1e19da42be3bd4992213f6d` | 2026-11-07T02:58:17Z |
| #12 | open/draft | `fix/atom-alternate-link-selection-2026-11-06@cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | `main@ba06a8f3713884b4c4ac524386a6a11576f0bb24` | 2026-11-06T09:14:32Z |
| #13 | open/draft | `ui/foreground-adaptive-glass-contrast-2026-11-06@3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | `main@ba06a8f3713884b4c4ac524386a6a11576f0bb24` | 2026-11-07T00:14:47Z |
| #14 | closed/unmerged | `docs/ai-agent-handoff-2026-11-06@ab2e4815d5077b96142a7aa8217443236739aacb` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T15:16:49Z |
| #15 | closed/unmerged | `fix/publisher-redirect-allowlist-2026-11-06@83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T12:37:37Z |
| #16 | open/draft | `fix/early-startup-crash-diagnostics-2026-11-06@6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T19:36:06Z |
| #17 | open/draft | `fix/rss-tracking-variant-dedup-2026-11-06@2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-07T00:13:16Z |
| #18 | open/draft | `ci/read-only-pr-validation-isolated-release-write-2026-11-06@963118f0abe5f7e38f2aa66a49d4b8e74548660c` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T17:19:11Z |
| #19 | closed/merged | `ui/liquid-glass-system-2026-11-07@1f7cd2e5ddc5d169d1443035a8334538e44efc40` | `black-box-bihar-news@0e01bd5df661de6110ed8e045eb344f3219317fa` | 2026-11-07T02:57:33Z |
| #20 | closed/merged | `ci/stacked-pr-validation-2026-11-07@5dc3179361835b77fb3186a99d9b5791301463fd` | `black-box-bihar-news@6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-07T02:56:15Z |
| #21 | open/draft | `fix/editorial-heatwave-bill-signals-2026-11-07@89464104e300f48da10e3e32e33715cbe2405084` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T22:06:16Z |
| #22 | open/draft | `perf/bounded-read-history-2026-11-07@910178af3f595993557533cb65d03e714bcd7760` | `perf/off-main-session-persistence-2026-11-06@ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06T23:53:25Z |
| #23 | closed/merged | `fix/parliamentary-syllabus-signal-gate-2026-11-07@9d754953a29c0355ce55306ce4fd5463193c9dbb` | `black-box-bihar-news@ebdfdd7b22c9164ab4ea24a6fa744e706081fc7f` | 2026-11-07T03:55:36Z |
| #24 | open/draft | `fix/editorial-policy-exception-guard-2026-11-07@1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07T07:29:39Z |
| #25 | open/draft | `fix/rss-image-host-allowlist-2026-11-07@7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | `black-box-bihar-news@2e2c15f5189ec6c60fa1f07f384bce4aa1734ea0` | 2026-11-07T07:30:59Z |
| #26 | open/draft | `privacy/webview-publisher-subresource-gate-2026-11-07@b8d2ef9ff2413c378347986d00961c401dfa771d` | `black-box-bihar-news@3a08bb95b0f9a0fed8ec11dae055f0ac2817a523` | 2026-11-07T07:30:38Z |
| #27 | open/draft | `fix/editorial-action-gate-blackbox-2026-11-07@2468ed33326ac7310a2ddd5f993495e4051245b8` | `black-box-bihar-news@3a08bb95b0f9a0fed8ec11dae055f0ac2817a523` | 2026-11-08T03:32:29Z |
| #28 | closed/merged | `fix/quiz-option-selected-semantics-compile-2026-11-07@6f9b3c1e1a484233721863d0b736734c0d282cd5` | `black-box-bihar-news@947310a157e03df25dde3856d2686d18ffca68e8` | 2026-11-07T09:01:53Z |
| #29 | open/draft | `perf/off-main-bookmark-persistence-2026-11-07@a64857b5efa62ea053c0c96e5f374fd35924b340` | `black-box-bihar-news@6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07T09:08:11Z |
| #30 | open/draft | `a11y/48dp-icon-targets-2026-11-07@25107ce905b70bde1a41282ffd1d606819ebf58d` | `black-box-bihar-news@f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb` | 2026-11-07T11:12:58Z |
| #31 | open/draft | `perf/off-main-appearance-preferences-2026-11-07@bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | `black-box-bihar-news@ee82dfde140aeb0d1ca80146379a0a2818c695df` | 2026-11-07T11:14:31Z |
| #32 | open/draft | `perf/bounded-rss-fetch-concurrency-2026-11-07@8c56c98c83cc69451f128aa259df86a8ce28f43a` | `black-box-bihar-news@ee82dfde140aeb0d1ca80146379a0a2818c695df` | 2026-11-07T11:30:22Z |
| #33 | open/draft | `fix/resolve-relative-atom-links-2026-11-07@c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | `black-box-bihar-news@48b54808807f5b15e637b6177524fd923741e1f7` | 2026-11-07T14:59:51Z |
| #34 | open/draft | `black-box-bihar-news@d5fab2ae9aa097e24180d831d6e0e8d64e441b43` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-08T08:07:11Z |
| #35 | open/draft | `perf/bound-startup-diagnostic-reads-2026-11-07@c4a977831846224182f16981b15e5c803761e26d` | `black-box-bihar-news@ed6c94190296c8d7af400b80f6fe26f2252fafe1` | 2026-11-07T17:34:25Z |
| #36 | open/draft | `fix/today-ist-midnight-rollover-2026-11-08@5fbd14e6ef9be33d04a882ba463d4c72473419e2` | `black-box-bihar-news@4a7a8f8c1db651d8b06d636836c8221569938ff8` | 2026-11-07T20:48:45Z |
| #37 | open/draft | `fix/search-clear-row-scope-compile-2026-11-08@9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | `black-box-bihar-news@d451d22518b8065e2865dae6b40679c3d7b41dc2` | 2026-11-07T22:51:33Z |
| #38 | open/draft | `perf/bound-rss-entry-parsing-2026-11-08@adade1ed8494c66b7e567de87f61f3dac3da7788` | `black-box-bihar-news@c7d50bb2bafe29a43c106e4fb71b607e9830adea` | 2026-11-08T03:34:06Z |
| #39 | open/draft | `fix/stable-release-signing-2026-11-08@4250de5040a8ca0efbf96a990218ef7d69ef26e8` | `black-box-bihar-news@cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84` | 2026-11-08T07:06:50Z |
| #40 | open/draft | `fix/validate-rss-article-hosts-2026-11-08@dcdbce939d0626767163f6133a42b60d23af7e57` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-08T06:53:49Z |
| #41 | open/draft | `perf/bound-rss-field-values-2026-11-08@fb5f7517e8b03d92d72964622a156a68ff1f6397` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-08T08:36:38Z |

---

# Live branch/PR/CI audit — 8 November 2026, 13:26 IST

**Repository:** `Batmancpu/CurrentLens`  
**Default:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` (unchanged)  
**Active:** `black-box-bihar-news@5f7e8cfe7c536dec2923a67348fb028916ca637c`, PR #34 open/draft → main; 136 ahead / 0 behind main and 79 ahead / 0 behind PR #16's corrected background-only Prismal/stability baseline. This is the exact pre-implementation parent for the Reader completion-reveal batch below.  
**Inventory:** 43 remote branches (43 + 0 pagination); 40 PRs (26 open, 14 closed). All PR refs and the full branch list were re-read. No source branch was deleted or modified.

## Baseline decision / branch freshness

- PR #34 remains the only active consolidation line. It is a direct descendant of PR #16 and contains the corrected background-only Prismal source, the Reader/Search/feed-only navigation work, consolidated CI and the current app architecture.
- PR #39 is open/draft on `fix/stable-release-signing-2026-11-08@4250de5040a8ca0efbf96a990218ef7d69ef26e8`, based on the older `black-box-bihar-news@cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84`. Comparison against active: 6 ahead / 1 behind; its changed files are workflow, Gradle signing and release documentation only. The secret-backed main signing path and first-install migration require owner action; not a UI baseline.
- PR #37's Search clear RowScope repair is already in the active `SearchMorphField` (fixed 48dp trailing Row slot). PR #30's TouchTargetPolicy and PR #31's off-main appearance restore are represented. PR #13's adaptive contrast is represented by the single 8×8 shared probe with 4s/6s cadence and stricter pause conditions; its older 1.5s/2.5s cadence was not copied.
- `brrrNEWS` is a divergent sibling (4 ahead / 42 behind active). Its fixed-intensity supporting card treatment is older than the active intensity-aware `GlassSurfacePolicy` and shared geometry; no port. `ui/liquid-glass-system-continuation` is 52 behind active; `redesign/v0.3-search-reader` is 230 behind; neither is authoritative.
- PR #40 publisher-bound RSS URL validation, #25/#26 publisher asset/WebView security, #35 diagnostic-read bounds, #36 IST rollover, #38 parser budget, #32/#33 RSS concurrency/relative Atom and other independent changes remain in their own review streams. No blind cherry-pick.
- PR #19 and #20 are closed/merged into `black-box-bihar-news` only, not main. PR #23/#28 are also merged into black-box only. PR #15's older redirect proposal is closed unmerged; the active PR #16 lineage supplies the current redirect/privacy guards.

## Actions, workflow and Release

- Actions history was fully paginated: pages 1–4 = 100 + 100 + 100 + 22; page 5 empty. Total 322: 192 success, 67 failure, 63 cancelled; active/queued = 0.
- Exact parent push run [#321](https://github.com/Batmancpu/CurrentLens/actions/runs/42457787922) and PR run [#322](https://github.com/Batmancpu/CurrentLens/actions/runs/42457791091) both passed on `5f7e8cfe7c536dec2923a67348fb028916ca637c`. Full job logs inspected: WordNet builder 4/4; 151,456 words / 50,950,144-byte DB; `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload passed. Publish job skipped.
- PR artifact #13608536799: 39,117,807 bytes, SHA-256 `b65f0f9beb0a0468beaa7e99e33c122aed6f160987e0d0ca898b9019db1aea3f`, expires 22 November 2026 07:14 UTC. Push artifact #13607972960: 39,117,807 bytes, SHA-256 `b9badb72f4838e40a5325bc45be459f16d77811fbcce486855b6fbe32019a57c`.
- Active `.github/workflows/build-apk.yml`: read-only build permissions; push and pull_request triggers cover main, PR #16's base and black-box; separate contents:write publish job is main-push-only. Main's own workflow remains push-only with contents:write.
- Latest published Release: `v0.2.0-build-116`, published 6 November 2026 11:18:59 UTC. No Release created by #321/#322.

## Full remote branch inventory (43)

| Remote branch | Exact head SHA | Latest commit UTC | Latest commit message | PR relationship / disposition |
|---|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02:12Z | test(a11y): cover minimum icon action target | PR #30 open head; represented in active policy |
| `black-box-bihar-news` | `5f7e8cfe7c536dec2923a67348fb028916ca637c` | 2026-11-08 07:11:54Z | ui(today): align weekend surface and loading semantics | PR #34 open/draft head; active integration; base of stacked proposals at earlier SHAs |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30Z | ui: consolidate lightweight supporting glass surfaces | PR #24 base; divergent sibling, not selected |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53Z | ci: validate pull requests with read-only build permissions | PR #18 open head; compatible workflow intent represented in active |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27Z | ci: restore Android SDK setup before sdkmanager | PR #20 closed/merged into black-box only |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35Z | Correct icon artwork accessibility name to brrrNews | PR #14 closed unmerged; current branch equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35Z | fix: refresh article card metadata when feed content changes | PR #6 open head; editorial work represented selectively |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27Z | fix: select Atom article alternate links deterministically | PR #12 open head; compatible parser work selectively represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32Z | feat: expose publication date and publisher RSS provenance on cards | PR #11 open head; compatible date/provenance work represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20Z | fix: minimise shareable process-exit diagnostics | PR #16 open head; corrected Prismal/stability ancestor |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `2468ed33326ac7310a2ddd5f993495e4051245b8` | 2026-11-08 03:30:19Z | test(editorial): cover reality TV and television promotion | PR #27 open head; independent editorial gate |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40Z | Verify a specific parliamentary bill remains eligible for the GS II feed | PR #21 open head; combined editorial work represented |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47Z | fix(editorial): match policy action and target as separate words | PR #24 open head; based on divergent brrrNEWS |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32Z | test: align economy signal count with repo-rate mapping | PR #2 merged into main |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28Z | fix: tighten UPSC editorial signal matching | PR #1 merged into main |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06Z | fix: use supported calendar navigation glyph | PR #5 merged into main |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44Z | test: preserve Parliament bill headline variants | PR #23 merged into black-box only |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39Z | Validate RSS redirects against publisher allowlists | PR #15 closed unmerged; safeguards superseded by PR #16 lineage |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10Z | fix(quiz): qualify selected semantics receiver | PR #28 merged into black-box only |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | 2026-11-07 14:59:24Z | test(rss): correct relative xml base fixture | PR #33 open head; separate RSS parser work |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46Z | test(reader): cover publisher-only image resources | PR #25 open head; separate publisher security review |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29Z | fix: deduplicate RSS tracking URL variants | PR #17 open head; compatible dedupe represented |
| `fix/search-clear-row-scope-compile-2026-11-08` | `9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | 2026-11-07 21:55:56Z | docs: record verified search clear compile repair | PR #37 open head; RowScope correction already in active |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03Z | fix: capture Search glass tint before draw callback | PR #10 merged into main |
| `fix/stable-release-signing-2026-11-08` | `4250de5040a8ca0efbf96a990218ef7d69ef26e8` | 2026-11-08 07:03:31Z | docs: record stable signing validation and migration gate | PR #39 open head; workflow/Gradle/signing only, not UI baseline |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53Z | fix: diagnose launch crashes and move feed indexing off main | PR #9 merged into main |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06Z | fix: harden cold start and reduce feed rendering workload | PR #7 merged into main |
| `fix/today-ist-midnight-rollover-2026-11-08` | `5fbd14e6ef9be33d04a882ba463d4c72473419e2` | 2026-11-07 20:48:43Z | test(today): cover Asia/Kolkata midnight rollover | PR #36 open head; separate date-policy review |
| `fix/validate-rss-article-hosts-2026-11-08` | `dcdbce939d0626767163f6133a42b60d23af7e57` | 2026-11-08 06:52:22Z | Test publisher-bound RSS article URLs | PR #40 open head; independent publisher URL security |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35Z | Correct icon artwork accessibility name to brrrNews | Default branch; unchanged |
| `perf/bound-rss-entry-parsing-2026-11-08` | `adade1ed8494c66b7e567de87f61f3dac3da7788` | 2026-11-08 03:31:36Z | fix(test): correct RSS budget test identifier | PR #38 open head; separate parser budget |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846224182f16981b15e5c803761e26d` | 2026-11-07 17:34:16Z | Record successful bounded-diagnostic CI | PR #35 open head; separate diagnostic performance |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04Z | Verify legacy markers survive recent-history retention | PR #22 open head; based on PR #8 |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29:26Z | docs: record bounded RSS fetch review and CI state | PR #32 open head; separate network performance |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11:45Z | fix(startup): keep appearance snapshot API module-internal | PR #31 open head; equivalent IO restore represented |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05:25Z | perf(storage): move bookmark mutations off the UI thread | PR #29 open head; separate persistence review |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31Z | perf: move read-marker persistence off main thread | PR #8 open head; history policy reconciled on active line |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27:22Z | privacy: block third-party publisher reader subresources | PR #26 open head; separate WebView security review |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48Z | fix: make selection decoding fallback valid JSON | No PR; 230 commits behind active; stale |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16Z | ui: restore low-cadence foreground adaptive glass contrast | PR #13 open head; conservative variant represented |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39Z | fix: import bottom navigation glyphs | PR #4 closed unmerged; PR #3 merged an earlier commit; stale |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36Z | docs(reader): record native selection collision guard | PR #19 closed/merged into black-box only |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34Z | fix: finish syllabus metadata and thin-film feed eligibility | No PR; 52 commits behind active; stale |

## Full PR inventory (40)

| PR | State / merge | Head branch @ SHA | Base branch @ SHA |
|---:|---|---|---|
| #34 | open/draft | `black-box-bihar-news@5f7e8cfe7c536dec2923a67348fb028916ca637c` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| #39 | open/draft | `fix/stable-release-signing-2026-11-08@4250de5040a8ca0efbf96a990218ef7d69ef26e8` | `black-box-bihar-news@cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84` |
| #40 | open/draft | `fix/validate-rss-article-hosts-2026-11-08@dcdbce939d0626767163f6133a42b60d23af7e57` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| #38 | open/draft | `perf/bound-rss-entry-parsing-2026-11-08@adade1ed8494c66b7e567de87f61f3dac3da7788` | `black-box-bihar-news@c7d50bb2bafe29a43c106e4fb71b607e9830adea` |
| #27 | open/draft | `fix/editorial-action-gate-blackbox-2026-11-07@2468ed33326ac7310a2ddd5f993495e4051245b8` | `black-box-bihar-news@3a08bb95b0f9a0fed8ec11dae055f0ac2817a523` |
| #37 | open/draft | `fix/search-clear-row-scope-compile-2026-11-08@9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | `black-box-bihar-news@d451d22518b8065e2865dae6b40679c3d7b41dc2` |
| #36 | open/draft | `fix/today-ist-midnight-rollover-2026-11-08@5fbd14e6ef9be33d04a882ba463d4c72473419e2` | `black-box-bihar-news@4a7a8f8c1db651d8b06d636836c8221569938ff8` |
| #35 | open/draft | `perf/bound-startup-diagnostic-reads-2026-11-07@c4a977831846224182f16981b15e5c803761e26d` | `black-box-bihar-news@ed6c94190296c8d7af400b80f6fe26f2252fafe1` |
| #33 | open/draft | `fix/resolve-relative-atom-links-2026-11-07@c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | `black-box-bihar-news@48b54808807f5b15e637b6177524fd923741e1f7` |
| #32 | open/draft | `perf/bounded-rss-fetch-concurrency-2026-11-07@8c56c98c83cc69451f128aa259df86a8ce28f43a` | `black-box-bihar-news@ee82dfde140aeb0d1ca80146379a0a2818c695df` |
| #31 | open/draft | `perf/off-main-appearance-preferences-2026-11-07@bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | `black-box-bihar-news@ee82dfde140aeb0d1ca80146379a0a2818c695df` |
| #30 | open/draft | `a11y/48dp-icon-targets-2026-11-07@25107ce905b70bde1a41282ffd1d606819ebf58d` | `black-box-bihar-news@f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb` |
| #29 | open/draft | `perf/off-main-bookmark-persistence-2026-11-07@a64857b5efa62ea053c0c96e5f374fd35924b340` | `black-box-bihar-news@6f9b3c1e1a484233721863d0b736734c0d282cd5` |
| #28 | closed/merged | `fix/quiz-option-selected-semantics-compile-2026-11-07@6f9b3c1e1a484233721863d0b736734c0d282cd5` | `black-box-bihar-news@947310a157e03df25dde3856d2686d18ffca68e8` |
| #25 | open/draft | `fix/rss-image-host-allowlist-2026-11-07@7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | `black-box-bihar-news@2e2c15f5189ec6c60fa1f07f384bce4aa1734ea0` |
| #26 | open/draft | `privacy/webview-publisher-subresource-gate-2026-11-07@b8d2ef9ff2413c378347986d00961c401dfa771d` | `black-box-bihar-news@3a08bb95b0f9a0fed8ec11dae055f0ac2817a523` |
| #24 | open/draft | `fix/editorial-policy-exception-guard-2026-11-07@1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` |
| #23 | closed/merged | `fix/parliamentary-syllabus-signal-gate-2026-11-07@9d754953a29c0355ce55306ce4fd5463193c9dbb` | `black-box-bihar-news@ebdfdd7b22c9164ab4ea24a6fa744e706081fc7f` |
| #11 | open/draft | `fix/cache-publication-date-integrity-2026-11-06@be5151e674c2bd4fa9beb166fc38ddf622781f84` | `black-box-bihar-news@c6223c93de84cb25d1e19da42be3bd4992213f6d` |
| #19 | closed/merged | `ui/liquid-glass-system-2026-11-07@1f7cd2e5ddc5d169d1443035a8334538e44efc40` | `black-box-bihar-news@0e01bd5df661de6110ed8e045eb344f3219317fa` |
| #20 | closed/merged | `ci/stacked-pr-validation-2026-11-07@5dc3179361835b77fb3186a99d9b5791301463fd` | `black-box-bihar-news@6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` |
| #13 | open/draft | `ui/foreground-adaptive-glass-contrast-2026-11-06@3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | `main@ba06a8f3713884b4c4ac524386a6a11576f0bb24` |
| #6 | open/draft | `feat/gs1-geography-editorial-coverage@ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | `main@17b47e453ca02ade9d10110614736e904c9a4490` |
| #17 | open/draft | `fix/rss-tracking-variant-dedup-2026-11-06@2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| #22 | open/draft | `perf/bounded-read-history-2026-11-07@910178af3f595993557533cb65d03e714bcd7760` | `perf/off-main-session-persistence-2026-11-06@ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` |
| #21 | open/draft | `fix/editorial-heatwave-bill-signals-2026-11-07@89464104e300f48da10e3e32e33715cbe2405084` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| #16 | open/draft | `fix/early-startup-crash-diagnostics-2026-11-06@6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| #18 | open/draft | `ci/read-only-pr-validation-isolated-release-write-2026-11-06@963118f0abe5f7e38f2aa66a49d4b8e74548660c` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| #14 | closed/unmerged | `docs/ai-agent-handoff-2026-11-06@ab2e4815d5077b96142a7aa8217443236739aacb` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| #15 | closed/unmerged | `fix/publisher-redirect-allowlist-2026-11-06@83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| #8 | open/draft | `perf/off-main-session-persistence-2026-11-06@ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | `main@81de60cca96bbbbfb066261b23e3f9843f82b7ee` |
| #12 | open/draft | `fix/atom-alternate-link-selection-2026-11-06@cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | `main@ba06a8f3713884b4c4ac524386a6a11576f0bb24` |
| #10 | closed/merged | `fix/search-glass-theme-capture-2026-11-06@c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | `main@2475970123a2a2fb0e05be25d1d9a45061186c73` |
| #9 | closed/merged | `fix/startup-crash-diagnostics-feed-jank-2026-11-06@5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | `main@81de60cca96bbbbfb066261b23e3f9843f82b7ee` |
| #7 | closed/merged | `fix/startup-stability-performance-2026-11-06@654e660347de692ef88531dfa49c610269d69671` | `main@17b47e453ca02ade9d10110614736e904c9a4490` |
| #5 | closed/merged | `fix/navigation-icon-imports-2026-11-06@fa8868940c08ed5b4a9def014a850e8a9dcb3490` | `main@df28a43c0beae2e0a6ef74589a56c0e1786d66d3` |
| #4 | closed/unmerged | `ui/liquid-glass-performance-2026-11-06@2e9c7f62ca65a899e18d9026e1535392835b3724` | `main@df28a43c0beae2e0a6ef74589a56c0e1786d66d3` |
| #3 | closed/merged | `ui/liquid-glass-performance-2026-11-06@b51d5e79ee8d3261c5458712f4e869ada1693697` | `main@6cf84bacd3dce62081cf0efee386e73411357ce0` |
| #2 | closed/merged | `fix/editorial-signal-count-test@4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | `main@f758af2ecc381ebeb6f9bb96aab172967eb973fb` |
| #1 | closed/merged | `fix/editorial-token-boundaries-2026-11-06@0045c24637f162f1c13dceb1f8878f42f6eb15fd` | `main@682432a6a3122e3ddcd2710e052a30fc2958ed6c` |

## Current implementation batch

- Parent `5f7e8cfe7c536dec2923a67348fb028916ca637c` is the exact source SHA. ReaderWebView's end detector can report true completion after the last downward scroll has already scheduled chrome auto-hide; the completion dock is gated by the same visibility state and could disappear after the 160ms settle delay.
- The new batch adds a pure ReaderChromePolicy reveal/reset transition and calls it when true completion is reported. It cancels only the pending hide; it does not permanently pin the toolbar. Subsequent downward reading can still hide it.
- No new timer, polling, capture/readback, blur/refraction, shader, graphics layer, image decode, dependency, network or persistence path. Added one focused JVM policy test.
- Parent CI above does not validate the new child commit. Await its natural push/PR run; no manual dispatch/rerun.
- OPPO Reno10 visual, launch, TalkBack, native selection, Reduce Motion, frame timing/jank, GPU overdraw, allocation/memory, thermal and battery checks remain UNVERIFIED. No performance improvement is claimed.

---

# Live branch/PR/CI audit — 8 November 2026, 12:31 IST

**Default:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`  
**Active:** `black-box-bihar-news@cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84`, PR #34 open/draft to main; 135 ahead / 0 behind main, 78 ahead / 0 behind PR #16, 65 ahead / 0 behind the incorporated PR #19 UI head.  
**Inventory:** 43 remote branches (page 1 = 43, page 2 = 0); 40 PRs (26 open, 14 closed). The 41 unchanged branch rows remain in the complete inventory table below; the active row was advanced and the two new branches (#39/#40) were added. No source branch was modified.

## Freshness / dependency decision

- PR #39 is 6 ahead / 0 behind the active head but changes only the workflow, Gradle signing and release docs; it is not a UI baseline. Its successful run #320 validates only PR #39's head. It remains unmerged; the proposed stable release key needs owner-managed secrets and an explicit first-install/data-migration decision.
- PR #37 is 6 ahead / 16 behind; its RowScope correction is already represented in active history. PR #30 (48dp targets) and #31 (off-main appearance restore) are represented. PR #36 (IST midnight refresh), #35 (diagnostic-read bounds), #38 (parser budget), #32/#33 (RSS concurrency/Atom links), #25/#26 (publisher resource security) and #27 (editorial gate) remain separate proposals. No blind cherry-picks.
- PR #19/#20/#23/#28 are closed/merged into black-box only. PR #15 is closed unmerged, with its redirect safeguards already in the PR #16/active ancestor. Stale brrrNEWS, ui/liquid-glass-performance and redesign/v0.3 remain excluded.
- The active UI source remains the latest coherent owner-selected line. No other branch was reset, force-pushed, deleted or merged.

## Complete PR ref inventory — 8 November 2026, 12:31 IST (40 total)

| PR | State / merge | Head ref@SHA | Base ref@SHA | Updated UTC |
|---|---|---|---|---|
| #1 closed / merged | `fix/editorial-token-boundaries-2026-11-06@0045c24637f162f1c13dceb1f8878f42f6eb15fd` | `main@682432a6a3122e3ddcd2710e052a30fc2958ed6c` | 2026-11-06T05:14:24Z |
| #2 closed / merged | `fix/editorial-signal-count-test@4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | `main@f758af2ecc381ebeb6f9bb96aab172967eb973fb` | 2026-11-06T05:16:51Z |
| #3 closed / merged | `ui/liquid-glass-performance-2026-11-06@b51d5e79ee8d3261c5458712f4e869ada1693697` | `main@6cf84bacd3dce62081cf0efee386e73411357ce0` | 2026-11-06T06:23:55Z |
| #4 closed / closed unmerged | `ui/liquid-glass-performance-2026-11-06@2e9c7f62ca65a899e18d9026e1535392835b3724` | `main@df28a43c0beae2e0a6ef74589a56c0e1786d66d3` | 2026-11-06T06:26:39Z |
| #5 closed / merged | `fix/navigation-icon-imports-2026-11-06@fa8868940c08ed5b4a9def014a850e8a9dcb3490` | `main@df28a43c0beae2e0a6ef74589a56c0e1786d66d3` | 2026-11-06T06:27:23Z |
| #6 open / open draft | `feat/gs1-geography-editorial-coverage@ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | `main@17b47e453ca02ade9d10110614736e904c9a4490` | 2026-11-07T00:14:20Z |
| #7 closed / merged | `fix/startup-stability-performance-2026-11-06@654e660347de692ef88531dfa49c610269d69671` | `main@17b47e453ca02ade9d10110614736e904c9a4490` | 2026-11-06T07:19:26Z |
| #8 open / open draft | `perf/off-main-session-persistence-2026-11-06@ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | `main@81de60cca96bbbbfb066261b23e3f9843f82b7ee` | 2026-11-06T10:50:48Z |
| #9 closed / merged | `fix/startup-crash-diagnostics-feed-jank-2026-11-06@5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | `main@81de60cca96bbbbfb066261b23e3f9843f82b7ee` | 2026-11-06T07:42:06Z |
| #10 closed / merged | `fix/search-glass-theme-capture-2026-11-06@c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | `main@2475970123a2a2fb0e05be25d1d9a45061186c73` | 2026-11-06T07:44:54Z |
| #11 open / open draft | `fix/cache-publication-date-integrity-2026-11-06@be5151e674c2bd4fa9beb166fc38ddf622781f84` | `black-box-bihar-news@c6223c93de84cb25d1e19da42be3bd4992213f6d` | 2026-11-07T02:58:17Z |
| #12 open / open draft | `fix/atom-alternate-link-selection-2026-11-06@cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | `main@ba06a8f3713884b4c4ac524386a6a11576f0bb24` | 2026-11-06T09:14:32Z |
| #13 open / open draft | `ui/foreground-adaptive-glass-contrast-2026-11-06@3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | `main@ba06a8f3713884b4c4ac524386a6a11576f0bb24` | 2026-11-07T00:14:47Z |
| #14 closed / closed unmerged | `docs/ai-agent-handoff-2026-11-06@ab2e4815d5077b96142a7aa8217443236739aacb` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T15:16:49Z |
| #15 closed / closed unmerged | `fix/publisher-redirect-allowlist-2026-11-06@83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T12:37:37Z |
| #16 open / open draft | `fix/early-startup-crash-diagnostics-2026-11-06@6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T19:36:06Z |
| #17 open / open draft | `fix/rss-tracking-variant-dedup-2026-11-06@2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-07T00:13:16Z |
| #18 open / open draft | `ci/read-only-pr-validation-isolated-release-write-2026-11-06@963118f0abe5f7e38f2aa66a49d4b8e74548660c` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T17:19:11Z |
| #19 closed / merged | `ui/liquid-glass-system-2026-11-07@1f7cd2e5ddc5d169d1443035a8334538e44efc40` | `black-box-bihar-news@0e01bd5df661de6110ed8e045eb344f3219317fa` | 2026-11-07T02:57:33Z |
| #20 closed / merged | `ci/stacked-pr-validation-2026-11-07@5dc3179361835b77fb3186a99d9b5791301463fd` | `black-box-bihar-news@6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-07T02:56:15Z |
| #21 open / open draft | `fix/editorial-heatwave-bill-signals-2026-11-07@89464104e300f48da10e3e32e33715cbe2405084` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06T22:06:16Z |
| #22 open / open draft | `perf/bounded-read-history-2026-11-07@910178af3f595993557533cb65d03e714bcd7760` | `perf/off-main-session-persistence-2026-11-06@ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06T23:53:25Z |
| #23 closed / merged | `fix/parliamentary-syllabus-signal-gate-2026-11-07@9d754953a29c0355ce55306ce4fd5463193c9dbb` | `black-box-bihar-news@ebdfdd7b22c9164ab4ea24a6fa744e706081fc7f` | 2026-11-07T03:55:36Z |
| #24 open / open draft | `fix/editorial-policy-exception-guard-2026-11-07@1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07T07:29:39Z |
| #25 open / open draft | `fix/rss-image-host-allowlist-2026-11-07@7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | `black-box-bihar-news@2e2c15f5189ec6c60fa1f07f384bce4aa1734ea0` | 2026-11-07T07:30:59Z |
| #26 open / open draft | `privacy/webview-publisher-subresource-gate-2026-11-07@b8d2ef9ff2413c378347986d00961c401dfa771d` | `black-box-bihar-news@3a08bb95b0f9a0fed8ec11dae055f0ac2817a523` | 2026-11-07T07:30:38Z |
| #27 open / open draft | `fix/editorial-action-gate-blackbox-2026-11-07@2468ed33326ac7310a2ddd5f993495e4051245b8` | `black-box-bihar-news@3a08bb95b0f9a0fed8ec11dae055f0ac2817a523` | 2026-11-08T03:32:29Z |
| #28 closed / merged | `fix/quiz-option-selected-semantics-compile-2026-11-07@6f9b3c1e1a484233721863d0b736734c0d282cd5` | `black-box-bihar-news@947310a157e03df25dde3856d2686d18ffca68e8` | 2026-11-07T09:01:53Z |
| #29 open / open draft | `perf/off-main-bookmark-persistence-2026-11-07@a64857b5efa62ea053c0c96e5f374fd35924b340` | `black-box-bihar-news@6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07T09:08:11Z |
| #30 open / open draft | `a11y/48dp-icon-targets-2026-11-07@25107ce905b70bde1a41282ffd1d606819ebf58d` | `black-box-bihar-news@f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb` | 2026-11-07T11:12:58Z |
| #31 open / open draft | `perf/off-main-appearance-preferences-2026-11-07@bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | `black-box-bihar-news@ee82dfde140aeb0d1ca80146379a0a2818c695df` | 2026-11-07T11:14:31Z |
| #32 open / open draft | `perf/bounded-rss-fetch-concurrency-2026-11-07@8c56c98c83cc69451f128aa259df86a8ce28f43a` | `black-box-bihar-news@ee82dfde140aeb0d1ca80146379a0a2818c695df` | 2026-11-07T11:30:22Z |
| #33 open / open draft | `fix/resolve-relative-atom-links-2026-11-07@c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | `black-box-bihar-news@48b54808807f5b15e637b6177524fd923741e1f7` | 2026-11-07T14:59:51Z |
| #34 open / open draft | `black-box-bihar-news@cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-08T06:05:44Z |
| #35 open / open draft | `perf/bound-startup-diagnostic-reads-2026-11-07@c4a977831846224182f16981b15e5c803761e26d` | `black-box-bihar-news@ed6c94190296c8d7af400b80f6fe26f2252fafe1` | 2026-11-07T17:34:25Z |
| #36 open / open draft | `fix/today-ist-midnight-rollover-2026-11-08@5fbd14e6ef9be33d04a882ba463d4c72473419e2` | `black-box-bihar-news@4a7a8f8c1db651d8b06d636836c8221569938ff8` | 2026-11-07T20:48:45Z |
| #37 open / open draft | `fix/search-clear-row-scope-compile-2026-11-08@9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | `black-box-bihar-news@d451d22518b8065e2865dae6b40679c3d7b41dc2` | 2026-11-07T22:51:33Z |
| #38 open / open draft | `perf/bound-rss-entry-parsing-2026-11-08@adade1ed8494c66b7e567de87f61f3dac3da7788` | `black-box-bihar-news@c7d50bb2bafe29a43c106e4fb71b607e9830adea` | 2026-11-08T03:34:06Z |
| #39 open / open draft | `fix/stable-release-signing-2026-11-08@4250de5040a8ca0efbf96a990218ef7d69ef26e8` | `black-box-bihar-news@cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84` | 2026-11-08T07:06:50Z |
| #40 open / open draft | `fix/validate-rss-article-hosts-2026-11-08@dcdbce939d0626767163f6133a42b60d23af7e57` | `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-08T06:53:49Z |


## Actions / workflow / Release

- 320 Actions runs fully paginated: 190 success, 67 failure, 63 cancelled; queue 0, in-progress 0.
- Exact active-head parent runs #313 (push) and #314 (pull_request) passed on `cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84`; #314 full log: WordNet 4/4, 151,456 words, `:app:testDebugUnitTest`, `:app:assembleDebug`, artifact upload. Artifact #13606870590, 39,115,995 bytes, expires 22 Nov 2026 06:07 UTC; publish job skipped.
- Latest overall run #320 passed on PR #39 head `4250de5040a8ca0efbf96a990218ef7d69ef26e8`, not the UI branch; artifact #13608018053, 39,115,994 bytes, expires 22 Nov 2026 07:05 UTC; publish job skipped.
- Active workflow is read-only for build/test, with Release write isolated to main push. Main workflow SHA `37197497288d9f0a60f4d1cfa8453e3dee52a902` remains push-only with `contents: write`. Latest Release `v0.2.0-build-116`; no Release published.

## Today UI batch

- Today status row now announces refresh/loading via a polite live region; skeleton placeholders are hidden from accessibility traversal. Weekend Reading adopts the shared continuous 29dp feed-card geometry and zero elevation; content cards remain clear, non-glass surfaces.
- No new render pass, Prismal source, capture/readback, blur, shader, image decode, dependency, timer or persistence. New-head CI is pending; parent #313/#314 do not validate the new commit.
- OPPO Reno10 screenshots, TalkBack runtime, launch, Reduce Motion, IME, frame timing, GPU overdraw, memory, thermal and battery remain **UNVERIFIED**.

---

# Live branch, PR, CI and implementation audit — 8 November 2026, 10:27 IST

**Repository:** `Batmancpu/CurrentLens`  
**Default branch:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`  
**Owner-selected active line:** `black-box-bihar-news@45a3264319dfb16cd43a6912a354c1625054c8dc`; PR #34 remains open/draft and targets main.  
**Ancestry:** 133 ahead / 0 behind main (merge base main); 76 ahead / 0 behind PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. PR #34 currently changes 74 files (9,782 additions / 806 deletions).  
**Inventory:** 41 remote branches (page 1: 41, page 2: 0); 38 PRs (24 open, 14 closed). The full branch/head/timestamp/PR table below is current; the active branch row has been advanced to the new Search UI commit.

## Freshness comparisons and source dispositions

- The selected integration line is still the latest coherent UI baseline. Main and PR #16 heads are unchanged. No newer relevant UI branch supersedes it.
- PR #37 Search clear compile repair is already represented in active `MainActivity.kt`; its six-commit branch also carries continuity documentation. Do not port again.
- PR #38 remains parser-only and diverged (8 ahead / 7 behind); PR #36 is a separate Today date/index refresh (3 ahead / 14 behind); PR #35 is startup diagnostic bounds (6 ahead / 22 behind); PR #27 is editorial-only (46 ahead / 33 behind). These are not UI baselines.
- PR #32/#33 still overlap `NewsRepository.kt`; PR #25/#26 still require combined publisher image/subresource security review. PR #29/#30/#31 and PR #13's conservative shared luminance policy are already represented. Stale `brrrNEWS`, `ui/liquid-glass-performance` and `redesign/v0.3-search-reader` remain excluded.
- No source branch was modified, cherry-picked, deleted or merged.

## Exact-head CI and workflow

- Parent head `748683f156ea05f650a49603f86750e8902551ed` passed natural push run [#307](https://github.com/Batmancpu/CurrentLens/actions/runs/42445240820) and PR run [#308](https://github.com/Batmancpu/CurrentLens/actions/runs/42445244470). Both completed WordNet builder/database generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload; publish job skipped. PR artifact [#13602938183](https://github.com/Batmancpu/CurrentLens/actions/runs/42445244470/artifacts/13602938183): 39,111,930 bytes, SHA-256 `e5767c219b24cff2526bba8e4c94676e0a12cfe8a390a95738a5be31f4078138`.
- Exact Search code head `45a3264319dfb16cd43a6912a354c1625054c8dc` triggered natural push run [#309](https://github.com/Batmancpu/CurrentLens/actions/runs/42449182759) and PR run [#310](https://github.com/Batmancpu/CurrentLens/actions/runs/42449186429). At this audit both were in progress: #309 had completed checkout, Java, signing-key restore, Android SDK/platform, Gradle and WordNet generation; compile/JVM tests were running. #310 had completed setup and was generating WordNet. No exact-head pass/artifact yet; parent evidence is not a substitute.
- Queue: 0 queued. The integration workflow retains read-only build/test permissions and a separate main-push-only Release-write job. Main's workflow remains push-only; no PR-to-main validation is inferred.
- Latest published testing Release remains `v0.2.0-build-116`; no Release was published.

## Search UI change

- Search's Material3 FilterChip action is replaced by `GlassActionPill` inside the existing Search header glass group. The static fill/rim uses Clear↔Frosted and Reduce Transparency policy, 48dp minimum sizing, Button role and active-state semantics. Tapping preserves the existing hide-keyboard/open-filter-sheet behaviour.
- Added `actionPillFillAlpha` / `actionPillBorderAlpha` and JVM tests for intensity endpoints, active hierarchy, reduced-transparency floors and non-finite fallback.
- No new Prismal surface, capture/readback, blur/refraction, sampler, shader, graphics layer, decode, dependency, network, timer or persistence path.

## Safety / device status

No main write, merge, retarget, manual dispatch/rerun, Release publication, package identity change or source-branch mutation/deletion. OPPO Reno10 launch, screenshots, TalkBack, font scaling, Reduce Motion, IME, frame timing/jank, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**.

---

# Live branch-freshness, CI and consolidation audit — 8 November 2026, 09:27 IST

**Repository:** `Batmancpu/CurrentLens`  
**Default branch:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`  
**Owner-selected active line:** `black-box-bihar-news@7ee0976c18c2987854f870b51d5531e9b9185fe6`; PR #34 is open/draft, head on this branch and base `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`.  
**Ancestry:** current active head is **131 ahead / 0 behind main** and **74 ahead / 0 behind** PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. The active branch remains the authoritative consolidated lineage; no newer coherent UI source supersedes it.
**Inventory:** 41 remote branches (page 1: 41; page 2: 0); 38 PRs (24 open, 14 closed). All branch head SHAs, latest commit UTC timestamps/messages and PR relationships are listed below.

## Complete current remote branch inventory

| Branch | Head SHA | Latest commit UTC | Latest commit subject | PR relationship / disposition |
|---|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02Z | test(a11y): cover minimum icon action target | PR #30 open head; based on black-box |
| `black-box-bihar-news` | `cc5a01b4e95b8f1250e5d0dda7535e8a485c7d84` | 2026-11-08 06:05:22Z | perf(search): reuse article relevance index | PR #34 open head; base for open #11, #25–27, #29–33, #35–39; older bases for merged #19/#20/#23/#28 |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13Z | ui: consolidate lightweight supporting glass surfaces | Base of open PR #24; divergent sibling |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18Z | ci: validate pull requests with read-only build permissions | PR #18 open head; based on main |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41Z | ci: restore Android SDK setup before sdkmanager | PR #20 closed/merged into black-box |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16Z | Correct icon artwork accessibility name to brrrNews | PR #14 closed/unmerged; head equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53Z | fix: refresh article card metadata when feed content changes | PR #6 open head; based on main |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14Z | fix: select Atom article alternate links deterministically | PR #12 open head; based on main |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26Z | feat: expose publication date and publisher RSS provenance on cards | PR #11 open head; based on black-box |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33Z | fix: minimise shareable process-exit diagnostics | PR #16 open head; corrected Prismal/stability ancestor |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `2468ed33326ac7310a2ddd5f993495e4051245b8` | 2026-11-08 03:30Z | test(editorial): cover reality TV and television promotion | PR #27 open head; editorial-only |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05Z | Verify a specific parliamentary bill remains eligible for the GS II feed | PR #21 open head; based on main |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46Z | fix(editorial): match policy action and target as separate words | PR #24 open head; based on brrrNEWS |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16Z | test: align economy signal count with repo-rate mapping | PR #2 closed/merged |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34Z | fix: tighten UPSC editorial signal matching | PR #1 closed/merged |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27Z | fix: use supported calendar navigation glyph | PR #5 closed/merged |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49Z | test: preserve Parliament bill headline variants | PR #23 closed/merged into black-box |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15Z | Validate RSS redirects against publisher allowlists | PR #15 closed; superseded by PR #16 guards |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12Z | fix(quiz): qualify selected semantics receiver | PR #28 closed/merged into black-box |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | 2026-11-07 14:59Z | test(rss): correct relative xml base fixture | PR #33 open head; based on black-box |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28Z | test(reader): cover publisher-only image resources | PR #25 open head; based on black-box |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42Z | fix: deduplicate RSS tracking URL variants | PR #17 open head; based on main |
| `fix/search-clear-row-scope-compile-2026-11-08` | `9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | 2026-11-07 21:55Z | docs: record verified search clear compile repair | PR #37 open head; code repair already ported |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44Z | fix: capture Search glass tint before draw callback | PR #10 closed/merged |
| `fix/stable-release-signing-2026-11-08` | `4250de5040a8ca0efbf96a990218ef7d69ef26e8` | 2026-11-08 07:03:31Z | docs: record stable signing validation and migration gate | PR #39 open head; base black-box-bihar-news@cc5a01b4; signing/workflow proposal only |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39Z | fix: diagnose launch crashes and move feed indexing off main | PR #9 closed/merged |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18Z | fix: harden cold start and reduce feed rendering workload | PR #7 closed/merged |
| `fix/today-ist-midnight-rollover-2026-11-08` | `5fbd14e6ef9be33d04a882ba463d4c72473419e2` | 2026-11-07 20:48Z | test(today): cover Asia/Kolkata midnight rollover | PR #36 open head; based on older black-box head |
| `fix/validate-rss-article-hosts-2026-11-08` | `dcdbce939d0626767163f6133a42b60d23af7e57` | 2026-11-08 06:52:22Z | Test publisher-bound RSS article URLs | PR #40 open head; base main@73b8a0d1; independent feed-host validation |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16Z | Correct icon artwork accessibility name to brrrNews | Default branch; base for open #6, #8, #12, #13, #16, #17, #18, #21 |
| `perf/bound-rss-entry-parsing-2026-11-08` | `adade1ed8494c66b7e567de87f61f3dac3da7788` | 2026-11-08 03:31Z | fix(test): correct RSS budget test identifier | PR #38 open head; based on older black-box head |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846224182f16981b15e5c803761e26d` | 2026-11-07 17:34Z | Record successful bounded-diagnostic CI | PR #35 open head; based on older black-box head |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53Z | Verify legacy markers survive recent-history retention | PR #22 open head; base is PR #8 branch |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29Z | docs: record bounded RSS fetch review and CI state | PR #32 open head; based on black-box |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11Z | fix(startup): keep appearance snapshot API module-internal | PR #31 open head; code represented in black-box |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05Z | perf(storage): move bookmark mutations off the UI thread | PR #29 open head; based on black-box |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50Z | perf: move read-marker persistence off main thread | PR #8 open head; base for PR #22 |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27Z | privacy: block third-party publisher reader subresources | PR #26 open head; based on black-box |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31Z | fix: make selection decoding fallback valid JSON | No open PR; stale 225 commits behind active line |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56Z | ui: restore low-cadence foreground adaptive glass contrast | PR #13 open head; code selectively represented |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25Z | fix: import bottom navigation glyphs | PR #4 closed/unmerged; stale divergent UI branch |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11Z | docs(reader): record native selection collision guard | PR #19 closed/merged into black-box |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18Z | fix: finish syllabus metadata and thin-film feed eligibility | No open PR; ancestor/continuation of the consolidated line |

## Freshness comparisons and source dispositions

- **Active integration:** `black-box-bihar-news@7ee0976c18c2987854f870b51d5531e9b9185fe6`; PR #34 remains the single draft consolidation PR. Its current full diff is 74 files (9,567 additions / 813 deletions). No main write or merge.
- **PR #16:** corrected background-only Prismal capture and stability baseline is an ancestor; active branch is 74 commits ahead of it. PR #19 Reader work and PR #20 read-only stacked workflow were consolidated into this branch only; their source branches remain.
- **PR #37 Search clear:** head `9dd4ceac08e88e6ee4ea565025a70539a6e9e331`, 6 ahead / 12 behind active. The direct code change (fixed 48dp trailing slot uses Row for the `AnimatedVisibility` RowScope) is already present in current `MainActivity.kt`; its remaining distinct work is handoff/ledger documentation. Do not port it again.
- **PR #38 bounded RSS entries:** head `adade1ed8494c66b7e567de87f61f3dac3da7788`, 8 ahead / 6 behind active; its diff is limited to `FeedEntryBudgetPolicy.kt`, `NewsRepository.kt` and `NewsRepositoryTest.kt`. Natural PR run #306 passed on that exact head, but this parser change is not UI work and overlaps PR #32/#33's `NewsRepository` concurrency/relative-link changes; leave it separate for joint review.
- **PR #36 IST rollover:** 3 ahead / 13 behind; separate `AsiaKolkataDayPolicy` plus `MainActivity` date refresh. **PR #35 diagnostic reads:** 6 ahead / 21 behind; diagnostics-only. Neither replaces the UI baseline.
- **PR #30 / #31:** their 48dp target policy and off-main appearance preference restore are already represented in active code. PR #13's conservative single 8×8 adaptive-luminance probe is integrated with 4s/6s cadence and paused during feed motion/non-home states.
- **Other independent work:** PR #27 is newer editorial-only work (46 ahead / 32 behind); PRs #25/#26 require joint publisher image/subresource security review; PRs #32/#33 require joint feed parser/concurrency review. PR #11/#12/#17 selected cache/provenance/Atom/URL-identity work is already selectively ported. PR #8/#22 persistence is reconciled with legacy-marker preservation. PR #6/#21 editorial changes are represented. Do not blindly cherry-pick or merge these branches.
- **Superseded/divergent UI candidates:** `brrrNEWS` is 4 ahead / 37 behind and divergent; `ui/liquid-glass-performance` is 3 ahead / 149 behind; `redesign/v0.3-search-reader` is 0 ahead / 225 behind; `ui/liquid-glass-system-continuation` is an ancestor 47 commits behind. None is a safer current UI baseline.
- **Open PRs:** #6, #8, #11, #12, #13, #16, #17, #18, #21, #22, #24, #25, #26, #27, #29, #30, #31, #32, #33, #34, #35, #36, #37, #38. **Closed PRs:** #1–5, #7, #9, #10, #14, #15, #19, #20, #23, #28 (14 total).

## Current natural Actions evidence

- Actions history is fully paginated: 306 runs, 181 success, 65 failure, 60 cancelled; no queued or in-progress runs.
- Active branch exact parent head `7ee0976c18c2987854f870b51d5531e9b9185fe6`: push run [#299](https://github.com/Batmancpu/CurrentLens/actions/runs/42441329214) and PR run [#300](https://github.com/Batmancpu/CurrentLens/actions/runs/42441332518) both passed. WordNet generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and APK upload all succeeded. Artifacts #13601299645 and #13602145334: 39,112,177 bytes each; Release jobs skipped.
- Latest global run [#306](https://github.com/Batmancpu/CurrentLens/actions/runs/42443089324) passed on PR #38 head `adade1ed8494c66b7e567de87f61f3dac3da7788`; artifact #13602895434, 39,112,397 bytes. It does not validate the active UI branch.
- The integration workflow `.github/workflows/build-apk.yml` has read-only build/test permissions and a separate main-push-only Release-write job; main's own workflow remains push-only. No manual dispatch/rerun or Release publication.
- Latest published testing prerelease is `v0.2.0-build-116` (6 November 2026). No new Release.

## Current UI iteration

- On verified source head `7ee0976c18c2987854f870b51d5531e9b9185fe6`, the Appearance editor's plain `surfaceVariant` Material card was replaced by the shared `SupportingGlassCard`. It now uses the same 22dp continuous geometry, static intensity-driven tint/rim and near-opaque content surface as the other supporting panels.
- The single compact Appearance preview remains the only live Prismal surface in that editor. No extra backdrop source, blur/refraction, shader, sampler/readback, graphics layer, image decode, dependency, network or persistence path was added.
- Exact new-head CI is pending; parent runs #299/#300 are not substitutes. OPPO Reno10 launch/visual/accessibility/performance checks remain UNVERIFIED.

## Safety and next step

No main edit, merge, retarget, manual dispatch/rerun, Release publication, source-branch mutation/deletion, package identity change or privacy/network expansion. Continue only on `black-box-bihar-news`, after the next exact-head natural CI result is inspected. Preserve four destinations, Saved inside You, offline WordNet, local-first privacy, Asia/Kolkata dates, foreground-only reading tracking and submit-only quiz persistence.

---

# Latest live freshness and integration audit — 8 November 2026

**Repository:** `Batmancpu/CurrentLens`  
**Default branch:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`  
**Owner-selected active line:** `black-box-bihar-news@a15984673f0b2c8b3cc032a38cfc7d08cdddc87b`; parent/source baseline `df5a75cff6fe9e75570d5daec64cf3d1db8ae726`.  
**PR:** #34 remains open/draft, base `main`; no merge, retarget or main write. The active line is 130 ahead / 0 behind main and 73 ahead / 0 behind PR #16's corrected Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.  
**Inventory:** 41 remote branches; 38 PRs (24 open, 14 closed). Page 1 contained all branches/PRs; page 2 was empty for branches, open PRs and closed PRs. The current owner-selected line remains the newest coherent integration baseline, not a timestamp-only choice.

## Relevant branch and PR dispositions

- `black-box-bihar-news` is the only branch modified by this task. Its current code commit is `a15984673f0b2c8b3cc032a38cfc7d08cdddc87b`.
- `main` remains unchanged. PR #16 remains the corrected background-only Prismal/stability ancestor; PR #34 is the single draft consolidation PR.
- `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` is divergent (4 ahead / 35 behind); its earlier supporting-glass and Saved/You implementation is superseded by the active branch's evolved policy and screen coverage.
- PR #37 / `fix/search-clear-row-scope-compile-2026-11-08` is 6 ahead / 10 behind from its merge base; the RowScope repair and 48dp Search clear control are already present in active `MainActivity.kt`. Its remaining delta is handoff/ledger documentation, not a newer UI baseline.
- PR #36's Asia/Kolkata midnight feed refresh remains a separate MainActivity/date-policy change; PR #35's bounded startup diagnostic reads remain a separate diagnostics change. Neither is needed for this filter-control batch.
- PR #38's bounded RSS parser proposal remains unported: natural run #288 failed two `NewsRepositoryTest` cases and no APK artifact was uploaded. The failure is outside the UI branch.
- PRs #30 (48dp icon targets) and #31 (off-main appearance preference restore) are represented in the active code. PR #13's conservative shared adaptive-contrast probe remains one 8×8 source at 4s/6s cadence, paused during feed motion and non-home/background states; no second sampler was added.
- PRs #32/#33 still need joint `NewsRepository` parsing/concurrency review; PRs #25/#26 need a joint publisher asset/subresource host-security review. PR #27 remains newer editorial-only work. These are not UI baselines and were not blindly ported.

## Natural CI, workflow and Release state

- Actions history before the new code commit: 296 runs fully paginated (100 + 100 + 96; page 4 empty): 177 success, 64 failure, 55 cancelled. No older queued/in-progress runs remained.
- Exact parent-head runs #295 (push) and #296 (pull_request) passed on `df5a75cff6fe9e75570d5daec64cf3d1db8ae726`. Both completed WordNet builder 4/4, 151,456-word database generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and APK artifact upload. Artifacts: #295 `13600453110` (39,111,082 bytes; SHA-256 `56a1e963541407d28c87dac55cf77761e4dee58fbe2805ad5d8ee29059842c80`); #296 `13600826632` (39,111,083 bytes; SHA-256 `47de6ba07042d58f1539f28689b567f0c72f9cc610faed96dc759b6f3165e3cb`).
- New code head `a15984673f0b2c8b3cc032a38cfc7d08cdddc87b` triggered natural push run #297 and PR run #298; both were in progress at this audit. The parent runs do **not** validate this new commit.
- Active integration workflow `.github/workflows/build-apk.yml` uses read-only build/test permissions, natural push/PR triggers for the integration line and a separate main-push-only Release-write job. No manual dispatch/rerun was used. Main's workflow remains push-only; do not overstate PR-to-main validation.
- Latest published Release remains `v0.2.0-build-116`. No Release was created.

## Latest implementation batch — filter-control material consistency

- `GlassFilterPill` now unifies selected/unselected control styling across Search's filter sheet, Saved, Reading Stats, Quiz & Practice Stats and Appearance presets. Choice pills use RadioButton selected semantics and minimum 48dp width/height; Search's Filters action now has a 48dp minimum height.
- Fill/rim alpha follows Clear↔Frosted and uses higher static opacity floors when Reduce Transparency is enabled. Full option labels remain horizontally scrollable.
- The change adds only static fills/borders inside existing parent glass surfaces. No new Prismal surface, backdrop capture/readback, blur/refraction, shader, graphics layer, image decode, dependency, network request or persistence path was added.
- Added pure JVM coverage for intensity endpoints, selected-state hierarchy, reduced-transparency fallback and non-finite preference fallback. Exact-head CI is pending on #297/#298.

## Device and safety status

OPPO Reno10 launch, screenshots, visual fidelity, TalkBack, font scaling, Reduce Motion, IME/safe areas, scroll smoothness, Perfetto/frame timing, GPU overdraw, allocations/memory, thermal and battery remain **UNVERIFIED**. CI does not establish device launch stability or performance. No main write, PR merge/retarget, Release publication, package/app identity change, source-branch mutation or branch deletion occurred.

---

# Live branch inventory and freshness decision — 8 November 2026, 07:41 IST

**Repository:** `Batmancpu/CurrentLens`  
**Default branch:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` (unchanged)  
**Owner-selected active line:** `black-box-bihar-news@400496d3a24e7ed9e1f2d4b62c964aa07ebb7348`; code commit `c6deffb96a89d56642374456e92477fbabb8e4b0`, docs sync `12f5831122067cf7996b14d8b8aed4d8dc3bbd4d`, test correction at current head.  
**Branch inventory:** 41 remote branches; **38 PRs** (24 open, 14 closed). Full pagination completed; no additional branch or PR page. PR #34 is the only active consolidation draft targeting main.

The active source is not selected by timestamp alone: it is the owner-directed consolidation line, 128 ahead / 0 behind main, 71 ahead / 0 behind PR #16's corrected background-only Prismal/stability baseline. Its lineage includes the PR #19 Reader work, the read-only stacked workflow, the selected network/cache/persistence/editorial ports, conservative adaptive contrast, Reader/Search/feed-navigation policies, shared supporting materials, Appearance preview and system Reduce Motion handling. PR #34 remains draft; no merge, retarget, main write or branch deletion.

## Complete remote branch inventory

| Branch | Head SHA | Latest commit (UTC) | Latest commit subject | PR relationship / disposition |
|---|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b7` | 2026-11-07 11:02Z | test(a11y): cover minimum icon action target | PR #30 open; policy represented |
| `black-box-bihar-news` | `400496d3a24e` | 2026-11-08 02:20Z | test(glass): compare header blur against prior profile | PR #34 open; active consolidation |
| `brrrNEWS` | `e6fcfc25a4c8` | 2026-11-07 04:13Z | ui: consolidate lightweight supporting glass surfaces | Base of PR #24; divergent sibling |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5` | 2026-11-06 17:18Z | ci: validate pull requests with read-only build permissions | PR #18 open; compatible workflow intent represented |
| `ci/stacked-pr-validation-2026-11-07` | `5dc317936183` | 2026-11-06 21:41Z | ci: restore Android SDK setup before sdkmanager | PR #20 closed; integrated into consolidation |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33` | 2026-11-06 11:16Z | Correct icon artwork accessibility name to brrrNews | PR #14 closed; ref currently equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c8655` | 2026-11-06 10:53Z | fix: refresh article card metadata when feed content changes | PR #6 open; selective editorial work represented |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde` | 2026-11-06 09:14Z | fix: select Atom article alternate links deterministically | PR #12 open; parser work selectively represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2` | 2026-11-06 14:26Z | feat: expose publication date and publisher RSS provenance on cards | PR #11 open; cache/provenance work represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8` | 2026-11-06 19:33Z | fix: minimise shareable process-exit diagnostics | PR #16 open; ancestor/stability baseline |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `405e729d3d0d` | 2026-11-08 01:04Z | docs(editorial): record reality-show exception regressions | PR #27 open; newer editorial-only work, divergent |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300` | 2026-11-06 22:05Z | Verify a specific parliamentary bill remains eligible for the GS II feed | PR #21 open; editorial work represented |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74` | 2026-11-07 04:46Z | fix(editorial): match policy action and target as separate words | PR #24 open; targets brrrNEWS, not active branch |
| `fix/editorial-signal-count-test` | `4738690d6e7f` | 2026-11-06 05:16Z | test: align economy signal count with repo-rate mapping | PR #2 closed |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f1` | 2026-11-06 02:34Z | fix: tighten UPSC editorial signal matching | PR #1 closed |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08` | 2026-11-06 06:27Z | fix: use supported calendar navigation glyph | PR #5 closed |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c` | 2026-11-07 03:49Z | test: preserve Parliament bill headline variants | PR #23 closed; integrated |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fd` | 2026-11-06 12:15Z | Validate RSS redirects against publisher allowlists | PR #15 closed; superseded by current publisher policy |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a48` | 2026-11-07 08:12Z | fix(quiz): qualify selected semantics receiver | PR #28 closed; compile fix integrated |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2c` | 2026-11-07 14:59Z | test(rss): correct relative xml base fixture | PR #33 open; joint NewsRepository review with #32 |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40a` | 2026-11-07 06:28Z | test(reader): cover publisher-only image resources | PR #25 open; joint publisher-resource security review with #26 |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a` | 2026-11-06 16:42Z | fix: deduplicate RSS tracking URL variants | PR #17 open; URL identity work selectively represented |
| `fix/search-clear-row-scope-compile-2026-11-08` | `9dd4ceac08e8` | 2026-11-07 21:55Z | docs: record verified search clear compile repair | PR #37 open; code fix already in active branch commit 6f777f87 |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd` | 2026-11-06 07:44Z | fix: capture Search glass tint before draw callback | PR #10 closed |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f4` | 2026-11-06 07:39Z | fix: diagnose launch crashes and move feed indexing off main | PR #9 closed; stability work superseded/represented |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de` | 2026-11-06 07:18Z | fix: harden cold start and reduce feed rendering workload | PR #7 closed; stability baseline superseded/represented |
| `fix/today-ist-midnight-rollover-2026-11-08` | `5fbd14e6ef9b` | 2026-11-07 20:48Z | test(today): cover Asia/Kolkata midnight rollover | PR #36 open; separate date/index candidate |
| `main` | `73b8a0d1ba33` | 2026-11-06 11:16Z | Correct icon artwork accessibility name to brrrNews | Default branch; unchanged |
| `perf/bound-rss-entry-parsing-2026-11-08` | `9daad62c852a` | 2026-11-08 02:15Z | test(rss): cover per-feed parser entry limit | PR #38 open; direct child performance proposal, run #288 in progress |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846` | 2026-11-07 17:34Z | Record successful bounded-diagnostic CI | PR #35 open; diagnostics-only candidate |
| `perf/bounded-read-history-2026-11-07` | `910178af3f59` | 2026-11-06 23:53Z | Verify legacy markers survive recent-history retention | PR #22 open; new markers bounded, legacy set retained |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc` | 2026-11-07 11:29Z | docs: record bounded RSS fetch review and CI state | PR #32 open; joint review with #33 |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff` | 2026-11-07 11:11Z | fix(startup): keep appearance snapshot API module-internal | PR #31 open; represented in active branch |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa6` | 2026-11-07 09:05Z | perf(storage): move bookmark mutations off the UI thread | PR #29 open; represented in active branch |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3` | 2026-11-06 10:50Z | perf: move read-marker persistence off main thread | PR #8 open; reconciled with #22 |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff241` | 2026-11-07 07:27Z | privacy: block third-party publisher reader subresources | PR #26 open; joint publisher-resource security review with #25 |
| `redesign/v0.3-search-reader` | `654cc89dee4f` | 2026-11-03 15:31Z | fix: make selection decoding fallback valid JSON | No open PR; stale |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4` | 2026-11-06 10:56Z | ui: restore low-cadence foreground adaptive glass contrast | PR #13 open; conservative policy integrated |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65` | 2026-11-06 06:25Z | fix: import bottom navigation glyphs | PR #3/#4 closed; stale sibling |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5` | 2026-11-06 20:11Z | docs(reader): record native selection collision guard | PR #19 closed; integrated into consolidation |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f` | 2026-11-07 03:18Z | fix: finish syllabus metadata and thin-film feed eligibility | No open PR; superseded by consolidation |

## Relevant ancestry and diff decisions

| Candidate | Comparison against active branch | Changed files / relationship | Decision |
|---|---|---|---|
| PR #16 stability head | Active branch is 71 commits ahead; PR #16 is an ancestor | Corrected background-only Prismal capture and startup stability prerequisite | Keep as ancestor; do not switch back |
| PR #38 bounded RSS parser entries | 2 ahead / 3 behind from merge base `c7d50bb2` | `NewsRepository.kt` and `NewsRepositoryTest.kt`; caps each feed at 100 entries, including malformed records | Separate parser/performance proposal; run #288 failed two parser tests; no MainActivity/Prismal overlap and no blind port |
| PR #37 Search compile repair | Diverged: PR head 6 ahead / active 9 ahead from `d451d225`; its code fix is already in active commit `6f777f87` | `MainActivity.kt` RowScope receiver repair plus handoff docs | No cherry-pick; preserve source PR untouched |
| PR #36 IST midnight rollover | 3 ahead / 10 behind from `4a7a8f8c` | `AsiaKolkataDayPolicy.kt`, 11-line `MainActivity.kt` change, JVM tests | Defer to a separate data/date pass; it is not a UI baseline |
| PR #35 bounded diagnostic reads | 6 ahead / 18 behind from `ed6c9419` | `LaunchCrashDiagnostics.kt`, tests and handoff docs | Diagnostics-only; do not mix into Quiz/UI pass |
| PR #27 strict editorial action gate | 44 ahead / 29 behind from `3a08bb95`; latest branch commit is newer than active head | `UpscEngine.kt`, `UpscFeedPolicy.kt`, tests/docs | Newer but editorial-only; keep separate until full editorial regression review |
| PRs #32 and #33 | #32: 2 ahead / 23 behind; #33: 4 ahead / 21 behind | Both modify `NewsRepository.kt`; concurrency policy and relative Atom-link resolution | Jointly review before any port; no blind merge |
| PRs #25 and #26 | #25: 7 ahead / 30 behind; #26: 1 ahead / 29 behind | Reader repository, `MainActivity.kt`, publisher asset/subresource host policies and tests | Joint security/redirect review required; no port in this UI task |
| PRs #29, #30 and #31 | Diverged from older bases; their current useful policies are already present | Bookmark persistence, 48dp target policy, off-main appearance preferences | Treat as represented; source branches remain untouched |
| PRs #22 and #8 | Separate persistence lineage | Ordered 5,000-entry supplemental new-marker window; legacy unordered set retained | Reconciled; do not claim all historical read storage is bounded |
| PRs #11, #12 and #17 | Independent cache/parser/URL identity lineages | Provenance/date, Atom alternate selection and tracking-variant identity | Selectively represented in current code; no wholesale merge |
| PRs #6 and #21 | Editorial branches based on older main | Geography, heat-wave and bill signals | Selectively represented; preserve synthetic fixture labels |
| PR #13 | Diverged older adaptive-contrast branch | Its faster 1.5s/2.5s probe cadence is not used | Conservative shared 8×8 probe remains 4s/6s and paused during scrolling/non-home |
| PRs #18 and #20 | Workflow-only branches | Read-only PR build and separate main-push Release-write job | Compatible workflow intent already in active branch; no source workflow overwritten |
| PR #24 / `brrrNEWS` | Divergent sibling base; PR #24 changes `MainActivity.kt`, `GlassSurfacePolicy.kt` and editorial policy | Separate app/editorial line | Do not switch to sibling or merge its full MainActivity |
| `ui/liquid-glass-performance`, `redesign/v0.3-search-reader` | Stale/diverged older lines | Old navigation/Reader work superseded by active consolidation | Excluded |
| PR #19 | Closed; source branch retained | Reader toolbar, completion and definition UI integrated into active consolidation | Do not continue on old branch |

The exact full patch review was scoped by subsystem: Search/Reader/Prismal and Compose receiver fixes were checked against current `MainActivity.kt`; persistence and network candidates were reviewed by their actual touched files and ancestry, not by branch names. No unrelated source branch was modified.

## 8 November 2026, 07:41 IST — Shared shell optical clarity and compact capture lifecycle (CI follow-up 07:54 IST)

- **Source baseline:** `black-box-bihar-news@c7d50bb2bafe29a43c106e4fb71b607e9830adea`; code commit `c6deffb96a89d56642374456e92477fbabb8e4b0`; docs sync `12f5831122067cf7996b14d8b8aed4d8dc3bbd4d`; test correction `400496d3a24e7ed9e1f2d4b62c964aa07ebb7348` (current head). PR #34 remains open/draft to main.
- **Current comparison:** 128 ahead / 0 behind main; 71 ahead / 0 behind PR #16. Branch inventory 41; PR inventory 38 (24 open, 14 closed). PR #38 is now 2 ahead / 3 behind the active branch from merge base `c7d50bb2`; it only touches RSS parsing/tests and remains separate.
- **Natural CI:** PR run #294 and push run #293 both passed exact head `400496d3`; WordNet 4/4, 151,456 unique words, JVM tests, APK assembly and artifact upload passed. Artifacts #13600736327 (39,111,083 bytes) and #13599529948 (39,111,082 bytes).
- **Failure/fix:** #289 and #291 each failed one test because the assertion compared the 9dp header profile against the 5.5dp droplet profile. The test now compares against the previous 13dp header value; #294/#293 pass. No production value was softened to hide a failure.
- **Separate RSS branch:** PR #38 run #288 failed two parser tests with RuntimeException at the test parse call sites; no artifact. Not ported.
- **Device / cost:** one existing root capture; expanded-only hidden glyph/merged droplet source; no extra readback. Header optical parameters are lower, but GPU/frame/memory/battery impact is unmeasured. OPPO Reno10, accessibility, motion and safe-area checks remain UNVERIFIED.
- **Next:** continue the shared-shell/transient-control audit. No dispatch/rerun, merge, main write, Release publication or source-branch mutation.

## Current Quiz batch

- Code commit: `b06557acb2923d62c8c4f18e666b584f89243242`, parent `94a1e9a3a6c0cc92abc41cfe332ebf821ed1e351`.
- Adds static Quiz option fill/rim states and state descriptions; no live blur/capture per option. Adds stable LazyColumn keys/content type and a 3dp answered-count progress track inside the existing footer.
- New policy/tests: `QuizOptionStylePolicy.kt` and `QuizOptionStylePolicyTest.kt`; submit-only persistence unchanged.
- Parent exact-head CI: natural runs #282/#283 passed on `94a1e9a3`; these do not validate the Quiz commit. New exact-head natural runs are required. No manual dispatch/rerun.

## Safety and device status

No main write, merge, Release publication, manual workflow dispatch/rerun, package/app identity change, source-branch mutation or deletion. No new network, telemetry, analytics, account, cloud AI, dependency or storage schema. OPPO Reno10 launch, screenshot, TalkBack, font scale, keyboard/IME, Reduce Motion runtime, Perfetto/frame timing, GPU overdraw, memory, thermal and battery remain **UNVERIFIED**.

---

## Live branch-graph audit — 8 November 2026, 06:23 IST

- **Repository/default:** `Batmancpu/CurrentLens`, default `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`.
- **Authoritative integration/UI branch:** `black-box-bihar-news@31e3bf753f91002b26b1e02e281899d10d0e590f`; **122 ahead / 0 behind main**, merge base main; **65 ahead / 0 behind** PR #16's corrected stability/Prismal head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. Its latest commit is `a11y(ui): honor system Reduce Motion across app transitions` (2026-11-07 23:59:46 UTC), parent `d9a4d1eac035172da0f8d7c40a984eb6c40c1b8b`. PR #34 is open/draft, head equals the selected branch, base main.
- **Complete inventory:** 40 remote branches (page 1: 40, page 2: 0); 37 PRs (23 open, 14 closed). The complete 40-name/head table follows in this document; this audit supersedes older rows where the same branch head has advanced. No source branch was changed, merged, retargeted or deleted.
- **Relevant branch comparisons against current black-box head:** PR #37 Search clear is 6 ahead / 3 behind; its six-line RowScope receiver fix is already present in current SearchMorphField, so no duplicate port. PR #36 IST rollover is 3 ahead / 4 behind from its older base and changes MainActivity/date-index logic; defer for a separate correctness review. PR #35 diagnostic-read bounds are 6 ahead / 12 behind and startup-only. PR #30 48dp policy (3/18), PR #31 appearance restore (5/17) and PR #29 bookmark IO (1/21) are already represented. PR #27 (41/23) is editorial-only. PR #25 asset-host and #26 WebView subresource controls remain a joint security review; PR #32 RSS concurrency and #33 relative Atom resolution both touch NewsRepository and remain a joint review. PR #24 is based on divergent brrrNEWS.
- **Sibling/stale lines:** brrrNEWS is 4 ahead / 28 behind (divergent); `ui/liquid-glass-performance-2026-11-06` is 3 ahead / 140 behind; `redesign/v0.3-search-reader` is 0 ahead / 215 behind; `ui/liquid-glass-system-continuation-2026-11-07` is 0 ahead / 38 behind. PR #19's source branch is 0 ahead / 52 behind; its approved Reader changes are already in black-box. PR #16 remains an ancestor, not the selected work branch.
- **CI:** all 281 runs paginated (100 + 100 + 81), 168 success / 61 failure / 52 cancelled, no queued/in-progress. Natural PR #281 and push #280 both passed on exact head `31e3bf7`; WordNet, JVM tests, APK assembly and artifact upload succeeded. PR artifact `13596471410`, push artifact `13595943420`; publish jobs skipped.
- **Workflow / release:** black-box workflow has read-only build/test and separate main-push-only Release write; main workflow is still push-only. Latest Release remains `v0.2.0-build-116`; no Release was created by #280/#281.
- **Safety:** only `black-box-bihar-news` may receive this task's commits. No main write, merge, retarget, workflow edit, manual dispatch/rerun, Release publication or source-branch deletion.

---

## Live branch-graph audit — 8 November 2026, 05:24 IST

- **Repository/default:** `Batmancpu/CurrentLens`, default `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`.
- **Authoritative integration:** `black-box-bihar-news@d9a4d1eac035172da0f8d7c40a984eb6c40c1b8b`, 121 ahead / 0 behind main (merge base main) and 64 ahead / 0 behind PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. PR #34 remains open/draft, head equal to the active branch, base main.
- **Complete inventory:** 40 remote refs (page 1: 40, page 2: 0); 37 PRs (23 open, 14 closed). The branch-name set is unchanged from the complete 40-row inventory immediately below. Two heads advanced since that table: `black-box-bihar-news` and `fix/editorial-action-gate-blackbox-2026-11-07`; their exact current refs are recorded here. No source branch was modified or deleted.
- **Head deltas since the 04:29 IST inventory:**

| Branch | Current head | Latest commit UTC / subject | PR relationship |
|---|---|---|---|
| `black-box-bihar-news` | `d9a4d1eac035172da0f8d7c40a984eb6c40c1b8b` | 2026-11-07 23:05:34 — ui(glass): bind supporting surfaces to appearance intensity | #34 open/draft; active source; base main |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `0248121c6e19c556580c17ac72ce2aafe37169c2` | 2026-11-07 23:13:33 — docs: record editorial promotion-gate regression | #27 open/draft; editorial-only, diverged; not a UI baseline |

- **Open-PR ancestry and treatment (all 23 checked against the active head):**
  - #34 is the active integration PR; current source equals its head.
  - #27 is 39 ahead / 22 behind (merge base `3a08bb95b0f9a0fed8ec11dae055f0ac2817a523`); its delta is `UpscEngine`, `UpscFeedPolicy`, tests and editorial docs only. Its later timestamp does not supersede the UI baseline.
  - #37 is 6 ahead / 0 behind its own older base `d451d22518b8065e2865dae6b40679c3d7b41dc2`; compared with current integration it is 6 ahead / 2 behind. Its Box→Row RowScope compile repair is already present in current `SearchMorphField`; the separate source branch/PR remains untouched.
  - #36 is 3 ahead / 3 behind current integration (merge base `4a7a8f8c1db651d8b06d636836c8221569938ff8`). It adds `AsiaKolkataDayPolicy`, a foreground IST-midnight Today-index refresh and tests; it changes `MainActivity.kt` on a diverged base. Its exact source passed run #268, but it is not integrated or claimed here.
  - #35 is 6 ahead / 11 behind (merge base `ed6c94190296c8d7af400b80f6fe26f2252fafe1`); startup diagnostic read bounds only.
  - #32 is 2 ahead / 16 behind (merge base `ee82dfde140aeb0d1ca80146379a0a2818c695df`) and #33 is 4 ahead / 14 behind (merge base `48b54808807f5b15e637b6177524fd923741e1f7`); both touch `NewsRepository.kt`, so retain as a joint parser/concurrency review.
  - #25 is 7 ahead / 23 behind and #26 is 1 ahead / 22 behind; both affect publisher image/subresource security and remain a combined host-policy review.
  - #31 (5 ahead / 16 behind), #30 (3 ahead / 17 behind) and #29 (1 ahead / 20 behind) contain appearance restore, 48dp target and bookmark IO work already represented in the active source; no wholesale replay.
  - #24 is based on divergent `brrrNEWS` (9 ahead / 27 behind) and is editorial-only. #11 (3 ahead / 121 behind), #13 (1 ahead / 134 behind), #6 (10 ahead / 134 behind), #17 (1 ahead / 121 behind), #22 (9 ahead / 134 behind), #21 (11 ahead / 121 behind), #18 (1 ahead / 121 behind), #8 (3 ahead / 134 behind) and #12 (1 ahead / 134 behind) are independent older-line proposals whose compatible policies are already represented selectively or remain separately reviewed; none supersedes the active UI line.
  - #16 is the ancestor (0 ahead / 64 behind). #19/#20/#23/#28 are closed and their approved work is already represented in black-box; #14/#15/#4 were closed without merge; #1/#2/#3/#5/#7/#9/#10 are merged into main.
- **Other branches:** `brrrNEWS` is 4 ahead / 27 behind from merge base `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`; its supporting-glass implementation is already represented, while its workflow/branding/formatting differences are not adopted. `ui/liquid-glass-performance-2026-11-06` is 3 ahead / 139 behind; `redesign/v0.3-search-reader` is 0 ahead / 215 behind; both remain stale. `ui/liquid-glass-system-2026-11-07` is 51 behind and its PR #19 work is represented; continuation is 37 behind. No branch is selected by timestamp alone.
- **Actions / workflow:** 279 runs fully paginated (100 + 100 + 79): 166 success, 61 failure, 52 cancelled; no queued/in-progress run. Exact active head `d9a4d1e` passed natural PR #279 and push #278. Both build jobs completed WordNet 4/4, 151,456-word database generation, JVM tests, APK assembly and artifact upload; full logs inspected; release jobs skipped. The new reduced-motion commit must receive its own natural run.
- **Artifacts:** PR #279 artifact `13594284223`, 39,107,398 bytes, SHA-256 `1959a089d31d4a96ee851749d5a3753b9b42e1a2283b381b50dd2462d2762803`; push #278 artifact `13594681752`, 39,107,393 bytes, SHA-256 `5fb4503525d5fa4f8a9b77213cc07f196345ae41cd7ae243ce3e746f4ad17000`. Latest Release remains `v0.2.0-build-116`; no Release published.
- **Workflow:** active `.github/workflows/build-apk.yml` has read-only build/test permissions and a separate main-push-only Release-write job. Main's workflow remains push-only; do not claim a main-targeted PR check.
- **Safety:** no main write, merge, retarget, workflow change, manual dispatch/rerun, Release publication, source-branch mutation or deletion.

---

## Live branch-graph audit — 8 November 2026, 04:29 IST

- **Repository/default:** `Batmancpu/CurrentLens`, default `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`.
- **Authoritative integration:** `black-box-bihar-news@6f777f8777b3168383361dc79109b3d1d75b612b`, 120 ahead / 0 behind main, merge base main. PR #34 is open/draft, head equal to this SHA, base main; this remains the only branch this task may modify.
- **Complete inventory:** 40 remote branches (page 1 returned 40, page 2 empty), 23 open PRs and 14 closed PRs. All branch head timestamps/messages and PR relationships are recorded below. No source branch was deleted or modified.
- **Search repair:** PR #37 remains open/draft on `fix/search-clear-row-scope-compile-2026-11-08@9dd4ceac08e88e6ee4ea565025a70539a6e9e331`, based on older black-box SHA `d451d22518b8065e2865dae6b40679c3d7b41dc2`. Its six-line RowScope correction is already functionally present in the current branch's SearchMorphField; the current head passed natural #276/#277. Do not merge/retarget or duplicate PR #37.
- **Other relevant open proposals:** PR #36 (IST midnight date/index refresh) is 3 ahead / 2 behind current black-box from merge base `4a7a8f8c`; not integrated because it overlaps MainActivity and remains a separate Today correctness change. PR #35 (bounded startup diagnostic reads) is 6 ahead / 10 behind from `ed6c9419`; startup-only. PR #32 (bounded RSS concurrency) is 2 ahead / 15 behind from `ee82dfde`; PR #33 (relative Atom links) is 4 ahead / 13 behind from `48b54808`; both modify NewsRepository and require joint review. PR #31 appearance IO restore, #30 48dp targets and #29 bookmark IO are already represented; their branches remain untouched. PR #27 remains separate editorial work. PR #25/#26 remain a joint publisher-resource security review. PR #24 is based on divergent brrrNEWS and is not a baseline.
- **Workflow:** `.github/workflows/build-apk.yml` on black-box uses read-only build/test permissions, push and pull-request triggers for main/PR #16/black-box, and a separate main-push-only release-write job. Main's workflow remains push-only with workflow-wide `contents: write`; the main-targeted draft PR is not claimed as having a guaranteed pull_request run from main's current workflow.
- **Actions:** 277 total runs fully paginated (100 + 100 + 77; page 4 empty), no queued or in-progress runs. Natural run #276 (push) and #277 (pull_request) both passed on exact SHA `6f777f8777b3168383361dc79109b3d1d75b612b`: WordNet builder/database, `:app:testDebugUnitTest`, `:app:assembleDebug`, artifact upload. Full job logs inspected; `BUILD SUCCESSFUL`. Publish job skipped. Run #277 artifact #13593000344 is 39,106,297 bytes, SHA-256 `1ae88bf11d4a2e4c8cefabea0f9abd59af6939e52265558922c7177a22aa4235`, expires 21 November 2026.
- **Release:** latest published remains `v0.2.0-build-116`, published 6 November 2026; no new Release.
- **Safety:** no main write, PR merge/retarget/close, workflow edit, manual dispatch/rerun, Release publication, source-branch mutation or deletion.

### Complete remote branch-head inventory (8 November 2026)

| Branch | Head SHA | Latest commit UTC / message | PR relationship |
|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02:12 — test(a11y): cover minimum icon action target | #30 open; base black-box |
| `black-box-bihar-news` | `31e3bf753f91002b26b1e02e281899d10d0e590f` | 2026-11-07 23:59:46 — a11y(ui): honor system Reduce Motion across app transitions | #34 open head/base main; selected; base/ancestor for the active stacked proposals |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30 — ui: consolidate lightweight supporting glass surfaces | Base of #24; divergent, not selected |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 — ci: validate pull requests with read-only build permissions | #18 open; workflow intent represented in black-box |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 — ci: restore Android SDK setup before sdkmanager | #20 closed/merged into black-box, not main |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — Correct icon artwork accessibility name to brrrNews | #14 closed/unmerged; equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 — fix: refresh article card metadata when feed content changes | #6 open; policy represented selectively |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 — fix: select Atom article alternate links deterministically | #12 open; core policy represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 — feat: expose publication date and publisher RSS provenance on cards | #11 open; equivalent code represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 — fix: minimise shareable process-exit diagnostics | #16 open; ancestor of integration |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `bd0bfce055e4d91737ff0d43e9eaa96c70a28519` | 2026-11-08 00:11:33 — fix(editorial): correct promotional framing regex escaping | #27 open; separate editorial review |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 — Verify a specific parliamentary bill remains eligible for the GS II feed | #21 open; combined policy represented |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47 — fix(editorial): match policy action and target as separate words | #24 open on brrrNEWS; divergent |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 — test: align economy signal count with repo-rate mapping | #2 closed/merged into main |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 — fix: tighten UPSC editorial signal matching | #1 closed/merged into main |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 — fix: use supported calendar navigation glyph | #5 closed/merged into main |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 — test: preserve Parliament bill headline variants | #23 closed/merged into black-box |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 — Validate RSS redirects against publisher allowlists | #15 closed/unmerged; equivalent guards retained |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10 — fix(quiz): qualify selected semantics receiver | #28 closed/merged into black-box |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | 2026-11-07 14:59:24 — test(rss): correct relative xml base fixture | #33 open; NewsRepository overlap with #32 |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46 — test(reader): cover publisher-only image resources | #25 open; joint security review with #26 |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 — fix: deduplicate RSS tracking URL variants | #17 open; URL identity policy represented |
| `fix/search-clear-row-scope-compile-2026-11-08` | `9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | 2026-11-07 21:55:56 — docs: record verified search clear compile repair | #37 open; code hunk already represented in current integration |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 — fix: capture Search glass tint before draw callback | #10 closed/merged into main |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 — fix: diagnose launch crashes and move feed indexing off main | #9 closed/merged into main |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 — fix: harden cold start and reduce feed rendering workload | #7 closed/merged into main |
| `fix/today-ist-midnight-rollover-2026-11-08` | `5fbd14e6ef9be33d04a882ba463d4c72473419e2` | 2026-11-07 20:48:43 — test(today): cover Asia/Kolkata midnight rollover | #36 open; separate Today-date candidate |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — Correct icon artwork accessibility name to brrrNews | default branch |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846224182f16981b15e5c803761e26d` | 2026-11-07 17:34:16 — Record successful bounded-diagnostic CI | #35 open; startup-only candidate |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 — Verify legacy markers survive recent-history retention | #22 open on PR #8; supplemental policy represented |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29:26 — docs: record bounded RSS fetch review and CI state | #32 open; joint NewsRepository review with #33 |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11:45 — fix(startup): keep appearance snapshot API module-internal | #31 open; IO restore represented |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05:25 — perf(storage): move bookmark mutations off the UI thread | #29 open; serialized IO policy represented |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 — perf: move read-marker persistence off main thread | #8 open; IO/mutex policy reconciled |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27:22 — privacy: block third-party publisher reader subresources | #26 open; joint security review with #25 |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 — fix: make selection decoding fallback valid JSON | no PR; stale, not a baseline |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 — ui: restore low-cadence foreground adaptive glass contrast | #13 open; conservative shared probe represented |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 — fix: import bottom navigation glyphs | #3 merged to main, #4 closed/unmerged; stale divergent sibling |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 — docs(reader): record native selection collision guard | #19 closed/merged into black-box, not main |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 — fix: finish syllabus metadata and thin-film feed eligibility | checkpoint ancestor; no PR |

### Open pull requests (all draft)

| PR | Head branch / SHA | Base branch / SHA | Treatment |
|---|---|---|---|
| #37 | `fix/search-clear-row-scope-compile-2026-11-08@9dd4ceac` | `black-box-bihar-news@d451d225` | RowScope fix already present on current integration; leave source PR untouched |
| #36 | `fix/today-ist-midnight-rollover-2026-11-08@5fbd14e6` | `black-box-bihar-news@4a7a8f8c` | Separate IST rollover candidate; reconcile MainActivity against current head before port |
| #35 | `perf/bound-startup-diagnostic-reads-2026-11-07@c4a97783` | `black-box-bihar-news@ed6c9419` | Startup diagnostics only; defer |
| #34 | `black-box-bihar-news@6f777f87` | `main@73b8a0d1` | Single consolidation draft; only PR this task updates |
| #33 | `fix/resolve-relative-atom-links-2026-11-07@c97d6b96` | `black-box-bihar-news@48b54808` | Joint parser/concurrency review with #32 |
| #32 | `perf/bounded-rss-fetch-concurrency-2026-11-07@8c56c98c` | `black-box-bihar-news@ee82dfde` | Joint parser/concurrency review with #33 |
| #31 | `perf/off-main-appearance-preferences-2026-11-07@bc48daec` | `black-box-bihar-news@ee82dfde` | Off-main restore policy represented; no wholesale replay |
| #30 | `a11y/48dp-icon-targets-2026-11-07@25107ce9` | `black-box-bihar-news@f7e10ab1` | 48dp policy represented; no wholesale replay |
| #29 | `perf/off-main-bookmark-persistence-2026-11-07@a64857b5` | `black-box-bihar-news@6f9b3c1e` | Serialized IO policy represented; no wholesale replay |
| #27 | `fix/editorial-action-gate-blackbox-2026-11-07@cef7f126` | `black-box-bihar-news@3a08bb95` | Separate editorial-policy review |
| #26 | `privacy/webview-publisher-subresource-gate-2026-11-07@b8d2ef9f` | `black-box-bihar-news@3a08bb95` | Joint publisher-resource security review with #25 |
| #25 | `fix/rss-image-host-allowlist-2026-11-07@7a51c9c2` | `black-box-bihar-news@2e2c15f5` | Joint publisher-resource security review with #26 |
| #24 | `fix/editorial-policy-exception-guard-2026-11-07@1f7a3c2a` | `brrrNEWS@e6fcfc25` | Divergent parent; do not use as UI baseline |
| #22 | `perf/bounded-read-history-2026-11-07@910178af` | `perf/off-main-session-persistence-2026-11-06@ed91a3bf` | Supplemental policy already reconciled; legacy unordered markers retained |
| #21 | `fix/editorial-heatwave-bill-signals-2026-11-07@89464104` | `main@73b8a0d1` | Editorial policy represented in consolidation |
| #18 | `ci/read-only-pr-validation-isolated-release-write-2026-11-06@963118f0` | `main@73b8a0d1` | Workflow intent reconciled into black-box; main untouched |
| #17 | `fix/rss-tracking-variant-dedup-2026-11-06@2093990b` | `main@73b8a0d1` | URL identity policy represented |
| #16 | `fix/early-startup-crash-diagnostics-2026-11-06@6ab1e6a0` | `main@73b8a0d1` | Corrected stability/Prismal ancestor; do not modify |
| #13 | `ui/foreground-adaptive-glass-contrast-2026-11-06@3dc0f3fb` | `main@ba06a8f3` | Conservative shared probe represented |
| #12 | `fix/atom-alternate-link-selection-2026-11-06@cdf4c384` | `main@ba06a8f3` | Parser policy represented |
| #11 | `fix/cache-publication-date-integrity-2026-11-06@be5151e6` | `black-box-bihar-news@c6223c93` | Cache/provenance policy represented |
| #8 | `perf/off-main-session-persistence-2026-11-06@ed91a3bf` | `main@81de60cc` | IO/mutex policy reconciled |
| #6 | `feat/gs1-geography-editorial-coverage@ca66fa1c` | `main@17b47e45` | Editorial policy represented selectively |

### Closed pull requests

- Merged into black-box (not main): #28, #23, #20, #19.
- Merged into main: #10, #9, #7, #5, #3, #2, #1.
- Closed without merge: #15, #14, #4.

---

## Live branch-graph audit — 8 November 2026, 03:24 IST

- **Repository/default:** `Batmancpu/CurrentLens`, default `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`.
- **Selected integration:** `black-box-bihar-news@d451d22518b8065e2865dae6b40679c3d7b41dc2`, 119 ahead / 0 behind main, merge base main; PR #34 is open/draft with base main. This remains the only branch this task may modify.
- **Complete refs:** 40 remote branches (page 1: 40, page 2: 0); 23 open PRs and 14 closed PRs. Latest branch commit date/message and current PR relationship are recorded below. No branch was deleted.
- **PR #37 repair lineage:** `fix/search-clear-row-scope-compile-2026-11-08@9dd4ceac08e88e6ee4ea565025a70539a6e9e331` is 6 ahead / 0 behind black-box, merge base d451d225. Its code fix commit `84cb91b8a1ecfcd6790a086743215031d7f8d671` is a direct child of black-box; only the six-line Box→Row receiver correction is selectively ported. The source branch and PR remain untouched. Natural #274 passed on the code-bearing PR head; #275's build/test/artifact steps passed on the docs-only successor but its overall run was still completing at the last poll.
- **Workflow:** active integration `.github/workflows/build-apk.yml` has read-only build/test permissions and a separate main-push-only release-write job; its push and PR triggers include main, PR #16's branch and black-box. Main's current copy remains push-only with workflow-wide `contents: write`; do not claim the main-targeted draft PR has a guaranteed base-workflow validation path.
- **Actions:** 275 total runs fully paginated (100 + 100 + 75): 161 success, 61 failure, 52 cancelled, 1 in progress (#275); queued 0. #269/#270 failed on black-box d451; #274 passed on PR #37 code head; #275 build/test/upload steps passed on PR #37 docs-only head. Latest published Release remains `v0.2.0-build-116`, published 6 November 2026; no new Release.
- **Safety:** no main edit, PR merge/retarget, workflow edit, manual dispatch/rerun, Release publication, source-branch mutation or deletion.

### Complete remote branch-head inventory

| Branch | Head SHA | Latest commit (UTC) | Open PR / state |
|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02:12 — test(a11y): cover minimum icon action target | #30 open, base black-box |
| `black-box-bihar-news` | `d451d22518b8065e2865dae6b40679c3d7b41dc2` | 2026-11-07 21:02:41 — ui(search): retain focus and animate clear affordance | #34 open, base main; selected |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30 — ui: consolidate lightweight supporting glass surfaces | Base of #24; divergent, not selected |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 — ci: validate pull requests with read-only build permissions | #18 open, base main; workflow intent represented in black-box |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 — ci: restore Android SDK setup before sdkmanager | #20 closed/merged into black-box, not main |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — Correct icon artwork accessibility name to brrrNews | #14 closed/unmerged; equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 — fix: refresh article card metadata when feed content changes | #6 open, base main; policy represented selectively |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 — fix: select Atom article alternate links deterministically | #12 open, base main; core policy represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 — feat: expose publication date and publisher RSS provenance on cards | #11 open, base black-box; equivalent code represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 — fix: minimise shareable process-exit diagnostics | #16 open, base main; ancestor of integration |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `cef7f1267cf2ca67ceacaf2acd9cfcbfa7dbf6fc` | 2026-11-07 19:06:26 — test(editorial): reject institutional film-promotion false positives | #27 open, base black-box ancestor; separate editorial review |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 — Verify a specific parliamentary bill remains eligible for the GS II feed | #21 open, base main; combined policy represented |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47 — fix(editorial): match policy action and target as separate words | #24 open, base brrrNEWS; divergent, not selected |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 — test: align economy signal count with repo-rate mapping | #2 closed/merged into main |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 — fix: tighten UPSC editorial signal matching | #1 closed/merged into main |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 — fix: use supported calendar navigation glyph | #5 closed/merged into main |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 — test: preserve Parliament bill headline variants | #23 closed/merged into black-box |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 — Validate RSS redirects against publisher allowlists | #15 closed/unmerged; equivalent guards retained |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10 — fix(quiz): qualify selected semantics receiver | #28 closed/merged into black-box |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | 2026-11-07 14:59:24 — test(rss): correct relative xml base fixture | #33 open, base black-box ancestor; NewsRepository overlap with #32 |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46 — test(reader): cover publisher-only image resources | #25 open, base black-box ancestor; security review with #26 |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 — fix: deduplicate RSS tracking URL variants | #17 open, base main; URL identity policy represented |
| `fix/search-clear-row-scope-compile-2026-11-08` | `9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | 2026-11-07 21:55:56 — docs: record verified search clear compile repair | #37 open, base black-box; 6 ahead / 0 behind |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 — fix: capture Search glass tint before draw callback | #10 closed/merged into main |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 — fix: diagnose launch crashes and move feed indexing off main | #9 closed/merged into main |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 — fix: harden cold start and reduce feed rendering workload | #7 closed/merged into main |
| `fix/today-ist-midnight-rollover-2026-11-08` | `5fbd14e6ef9be33d04a882ba463d4c72473419e2` | 2026-11-07 20:48:43 — test(today): cover Asia/Kolkata midnight rollover | #36 open, base black-box ancestor; 3 ahead / 1 behind |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — Correct icon artwork accessibility name to brrrNews | Default branch |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846224182f16981b15e5c803761e26d` | 2026-11-07 17:34:16 — Record successful bounded-diagnostic CI | #35 open, base black-box ancestor; 6 ahead / 9 behind |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 — Verify legacy markers survive recent-history retention | #22 open, base PR #8; equivalent supplemental policy represented |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29:26 — docs: record bounded RSS fetch review and CI state | #32 open, base black-box ancestor; NewsRepository overlap with #33 |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11:45 — fix(startup): keep appearance snapshot API module-internal | #31 open, base black-box ancestor; equivalent IO restore represented |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05:25 — perf(storage): move bookmark mutations off the UI thread | #29 open, base black-box ancestor; equivalent policy represented |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 — perf: move read-marker persistence off main thread | #8 open, base main; IO/mutex policy selectively reconciled |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27:22 — privacy: block third-party publisher reader subresources | #26 open, base black-box ancestor; joint review with #25 |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 — fix: make selection decoding fallback valid JSON | No active PR; stale, not a baseline |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 — ui: restore low-cadence foreground adaptive glass contrast | #13 open, base main; conservative shared probe represented |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 — fix: import bottom navigation glyphs | #3 merged to main; #4 closed/unmerged; stale |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 — docs(reader): record native selection collision guard | #19 closed/merged into black-box |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 — fix: finish syllabus metadata and thin-film feed eligibility | No PR; ancestor/checkpoint of black-box |

### Relevant comparison and dependency decisions

- `main...black-box-bihar-news`: 119 ahead / 0 behind; black-box remains the authoritative selected integration line and retains PR #16's corrected background-only Prismal capture.
- `black-box-bihar-news...fix/search-clear-row-scope-compile`: 6 ahead / 0 behind; direct merge base d451d225. Port only the code hunk from direct-child commit `84cb91b8`; do not copy its branch-wide documentation snapshot.
- `black-box-bihar-news...fix/today-ist-midnight-rollover`: 3 ahead / 1 behind, merge base `4a7a8f8c`; safe candidate but its MainActivity hunk must be reconciled with Search and current feed-index state, then retested on the current head.
- `black-box-bihar-news...perf/bound-startup-diagnostic-reads`: 6 ahead / 9 behind, merge base `ed6c9419`; only LaunchCrashDiagnostics and its tests change in source code; exact code/test tree `80cf6dbd` passed run #248. Review current diagnostic implementation before selective port.
- PRs #32 and #33 both change `NewsRepository.kt`; do not apply either in isolation. PR #25 and #26 both constrain publisher resources; review as one host/resource policy and preserve WebView JavaScript/DOM-storage restrictions.
- PR #31, #30, #29, #22, #21, #18, #17, #13, #12, #11, #8 and #6 have useful code/policies already represented or selectively reconciled in the integration branch; do not wholesale replay their divergent commits. PR #27 remains a separate editorial-policy review. PR #24 is based on divergent brrrNEWS and is not a safe UI baseline.
- `ui/liquid-glass-performance` and `redesign/v0.3-search-reader` are stale; do not use. PR #16 remains open on main and is an ancestor, not the active base.

### Current Search clear integration result

- On the active branch's last tested code head d451d225, natural runs #269/#270 failed at the RowScope-specific AnimatedVisibility call inside the fixed 48dp Box slot.
- PR #37's isolated code commit `84cb91b8` replaces that slot with a fixed 48dp Row; exact code-bearing PR head `8eb7aae3` passed natural run #274. Docs-only successor `9dd4ceac` has natural run #275 with WordNet, JVM tests, APK build and artifact upload steps successful; overall cleanup was still in progress at audit time.
- This audit's only source-code port is the same RowScope correction. Await a fresh natural build on the updated black-box head before further UI work. No device-performance result is inferred.

---

# Black Box Bihar News — complete branch consolidation

**Repository:** Batmancpu/CurrentLens  
**Chosen integration branch:** `black-box-bihar-news` — owner-selected single active integration branch.  
**Verified source HEAD before the supporting-card material pass:** `ec86b2fef2f5e86c3b95f2a2a2269bfef6158053`; the next UI commit is a direct child, created with a fast-forward-only expected-head update.  
**Default branch:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`, unchanged. The active branch is 108 commits ahead / 0 behind main and 51 ahead / 0 behind PR #16's corrected stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.  
**Live branch inventory before this batch:** 37 remote refs (all returned on page 1; page 2 empty), 19 open PRs and 14 closed PRs. No branch was deleted.  
**Latest active-branch validation before this batch:** Actions run #241, success on exact SHA `ec86b2fef2f5e86c3b95f2a2a2269bfef6158053`; WordNet builder tests 4/4, database 151,456 unique words / 50,950,144 bytes, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload succeeded. Artifact `13579222899`, 39,094,929 bytes, SHA-256 `d60b7e0d261c0ccac6bb5b407a642bd54183cca7abf1601b9fae541645bc838f`. Release job skipped.  
**Actions history:** 241 runs fully paginated (100 + 100 + 41; page 4 empty): 142 success, 58 failure, 41 cancelled; no queued/in-progress runs.  
**Workflow:** active branch blob `6cef6ca19f3babd331c9deb59be9da0067c6fa66` has read-only build/test permissions, PR validation for main/PR #16/black-box, and a separate main-push-only Release job. Main workflow blob `37197497288d9f0a60f4d1cfa8453e3dee52a902` remains push-only with broader write permissions. A future draft PR to main will not have a natural PR-triggered validation until main's workflow is reconciled; branch push CI is not PR-to-main validation.  
**Release:** latest published remains `v0.2.0-build-116`; no feature-branch Release was created. No manual dispatch/rerun, main edit/merge, source-branch write or branch deletion occurred.

The integration was started from PR #16's corrected stability/network baseline, then useful changes were merged or selectively reconciled. No older `MainActivity.kt`, `NewsRepository.kt`, `LocalStore.kt` or workflow was copied wholesale over the newer code.

## 1. Feature consolidation by area

### Startup, performance and reliability
- Retained PR #16's background-only Prismal capture, startup-stage diagnostics, local crash recovery and publisher/network restrictions.
- Integrated the shared `EditorialFeedIndexPolicy`: editorial eligibility and Today/Explore indexing are computed off the UI thread and reused instead of re-filtered repeatedly.
- Local app state is restored on `Dispatchers.IO` after first composition; OEM window styling is guarded; lazy feed items have content types; image requests are bounded and palette sampling checks recycled bitmaps.
- Startup diagnostics are more privacy-restricted than the early diagnostic branch: no raw device identifiers, process IDs, opaque Android exit descriptions or complete stack traces are added to the user-shareable report.
- Local persistence writes for article-read markers, Reader positions, reading sessions and submitted quizzes run on IO and are serialized where read-modify-write races matter.

### Editorial and UPSC engine
- GS I Geography was added to the classifier and Explore topic filter.
- Finance Bill / specific parliamentary bill phrases and heat-wave / heat-action-plan signals map to their intended GS themes.
- Whole-token / whole-phrase matching avoids substring errors such as treating `rbi` inside `arbitrary` as a Reserve Bank signal.
- Celebrity, film promotion, box-office, reality-show, routine sports and generic viral noise are suppressed; policy exceptions require an institutional action in the headline. A narrow thin-film photovoltaic/solar-cell exception avoids blocking a legitimate Science & Technology topic.
- Cards expose a concise indicative GS mapping, matched signal and syllabus rationale. The UI and tests do not describe this heuristic as a validated UPSC score or question predictor.
- Synthetic regression fixtures explicitly cover both eligible cases and false positives; they are not represented as actual PYQs or real news articles.
- Parliamentary classification now requires concrete function/process signals—bill introduction/passage/debate, committee scrutiny, Question Hour/Zero Hour, privileges, motions, sessions or legislative oversight. A bare institution mention (for example, a visitor-centre story) is rejected. The full “Representation of the People Act” phrase is recognised.

### Feeds, dates, sources and URLs
- RSS/Atom parsing prefers safe HTML alternate destinations over feed self-links/enclosures, while supporting RSS text links.
- Original publication time is preferred over a later update time; date/cache policy rejects inappropriate stale/future/undated entries according to its tests.
- Publisher RSS provenance is identified as secondary reporting, not primary-source verification.
- RSS-feed and article-page redirects are followed manually: every next URL is checked against the same publisher allowlist, HTTPS, normal ports and a bounded hop count. Automatic redirect following is disabled.
- Tracking parameters are removed for story identity, while the original destination URL is preserved. Duplicate selection prioritises an exact bookmarked variant, then an exact previously-read variant, then publication recency.
- No cookies, proxies, paywall circumvention or publisher subscription bypass is used.

### Personal data and study features
- Reading-session accounting remains foreground-only; quiz records are persisted only on submission.
- New read markers use an insertion-ordered 5,000-entry supplemental window. Legacy `read_history` is an unordered SharedPreferences set; it is preserved rather than destructively trimmed. **Total lifetime legacy read-marker storage is therefore not bounded.**
- Reader-position and quiz storage retain their existing policies and limits; old schemas are not replaced by a wholesale branch copy.

### Liquid Glass and screen behaviour
- Reader toolbar, Aa/font controls, progress indicator, compact/dismissible completion dock and definition overlay refinements are consolidated from the Reader UI work.
- Reader chrome hides after sustained downward scroll and reappears on upward movement; definition popover, native selection/Copy/Share ActionMode, IME and accessibility keep the controls pinned.
- Search's trailing clear action reuses its original callback and now has a 48dp target; clearing retains the expanded Search and existing filter/focus model.
- On Today, Explore and collapsed Search only, the existing four-tab GlassTabBar compacts to a named active-destination pill on downward feed scroll and expands on upward scroll/top edge. You, Saved, stats, Reader, Quiz, Settings and the keyboard Search overlay are excluded.
- Adaptive contrast uses one shared 8×8 bottom-band probe. The consolidated cadence is 4 seconds on Android 12+ and 6 seconds on older Android, and sampling pauses during feed motion, Reader, Settings and background operation.
- You and stats cards use consistent geometry; stats and appearance filter targets are at least 48dp. Appearance's existing preview now shows a compact header, readable article surface and control rail responding to the slider.
- Article text, story cards, statistics and quiz questions remain readable non-glass surfaces; no second root capture or per-card blur sampler was added.

## 2. Remote branch-by-branch disposition

This is the live 25-ref inventory audited against the selected integration branch. “Integrated” means the useful code/policy/test is in this branch, not that the old source commit was merged into its Git ancestry. Source branches were deliberately retained.

| Remote branch | Disposition |
|---|---|
| `black-box-bihar-news` | **Selected active integration branch.** All future consolidation work goes here. |
| `main` | Default branch; unchanged. Keep. |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | PR #18 workflow intent integrated: read-only build/test permissions and a separately gated main-push-only Release job. Source PR #18 remains open/draft. |
| `ci/stacked-pr-validation-2026-11-07` | PR #20 merged into `black-box-bihar-news`; natural validation for stacked work. Source PR #20 is closed/merged into this branch, not main. |
| `docs/ai-agent-handoff-2026-11-06` | Identical to the audited `main` SHA; no unique implementation. Redundant. |
| `feat/gs1-geography-editorial-coverage` | Geography classifier/filter, syllabus metadata, feed eligibility and baseline docs/tests selectively integrated. PR #6 remains open/draft. |
| `fix/atom-alternate-link-selection-2026-11-06` | Atom/RSS link candidate policy and parser regression tests integrated. PR #12 remains open/draft. |
| `fix/cache-publication-date-integrity-2026-11-06` | Publication-date precedence, cache-retention policy, publisher provenance and tests integrated. PR #11 remains open/draft and is based on this integration branch; its remaining diff should be reconciled/closed before source deletion. |
| `fix/early-startup-crash-diagnostics-2026-11-06` | Integration ancestor from PR #16; corrected Prismal capture, startup safeguards and network policy retained. PR #16 remains open/draft against main. |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | Bare Parliament mention removed as a sufficient GS-II signal; concrete parliamentary functions and bill actions retained; institution-only false-positive and bill/committee positive fixtures added. PR #23 merged into this branch, not main. |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | Bill/Finance Bill/heat-wave classifier and feed tests integrated with the Geography and entertainment-noise rules. PR #21 remains open/draft. |
| `fix/editorial-signal-count-test` | Updated repo-rate signal-count expectation integrated; current classifier test expects four matched economy signals. |
| `fix/editorial-token-boundaries-2026-11-06` | Whole-token matcher and negative substring fixtures are present; current editorial policy is a later, expanded version with more specific syllabus and noise rules. |
| `fix/navigation-icon-imports-2026-11-06` | Supported Today/Person glyphs are present in the current four-tab bar. Superseded. |
| `fix/publisher-redirect-allowlist-2026-11-06` | Per-hop RSS and article-page redirect checks, host allowlist and rejection tests are present in the current branch. PR #15 is closed unmerged; equivalent policy was retained through the newer stability/network implementation. |
| `fix/rss-tracking-variant-dedup-2026-11-06` | Tracking-variant story identity and exact saved/read variant preference integrated, with regression tests. PR #17 remains open/draft. |
| `fix/search-glass-theme-capture-2026-11-06` | Search material color is captured before the draw callback; integrated. |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | Shared editorial feed index and local startup diagnostics are present. The integration uses the later privacy-minimised diagnostic implementation, not the older raw device/stack report. |
| `fix/startup-stability-performance-2026-11-06` | Off-main local-state restore, OEM-safe window styling, lazy content types, bounded image requests and palette safeguards are present. Its original 1.5s/2.5s luminance cadence was made more conservative. |
| `perf/bounded-read-history-2026-11-07` | Bounded new-marker window integrated with malformed/wrong-type preference preservation; PR #22 remains open/draft. |
| `perf/off-main-session-persistence-2026-11-06` | IO/mutex persistence and Reader/quiz call-site changes selectively reconciled; PR #8 remains open/draft. |
| `redesign/v0.3-search-reader` | Very stale relative to PR #16/current Reader; no unique change justified replaying. Do not use as a base. |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | Shared probe and activation policy integrated with a slower cadence and feed-scroll gating. PR #13 remains open/draft. |
| `ui/liquid-glass-performance-2026-11-06` | Three older commits' useful header/category/skeleton/card/search/nav work is present in the newer PR #16/#19-derived UI; old version and icon treatment are superseded. |
| `ui/liquid-glass-system-2026-11-07` | Reader UI PR #19 merged into `black-box-bihar-news`; it was not merged into main. |
| `ui/liquid-glass-system-continuation-2026-11-07` | Earlier validated integration checkpoint (run #182); current branch is ahead of it, with no unique commit remaining. |

## 3. Pull-request state at the audit

- PR #19 (Reader Liquid Glass), PR #20 (stacked read-only CI) and PR #23 (specific parliamentary syllabus gate) are **closed/merged into `black-box-bihar-news`**, not into main.
- PR #15 (publisher redirect allowlist) is closed/unmerged; its safe redirect behaviour is present in the integration branch.
- PRs #6, #8, #11, #12, #13, #16, #17, #18, #21 and #22 remain open/draft. Their useful code is represented in the consolidated branch; they are source-history records, not independent implementation targets.
- No source branch was deleted. The owner should review the consolidated PR/branch before deleting source refs. PR #11 is especially redundant because it was retargeted to the integration branch and its changes were separately reconciled there.

## 4. Validation record

- Run #182, SHA `f031bf21229f43a992384cc23fa0f4da57e547a1`: passed JVM tests, APK assembly and artifact upload.
- Run #183, SHA `d80916691eb5e72ee5448bd543433cdabe2a1e5a`: passed Reader auto-hide and Search clear-target changes.
- Runs #184 and #185 failed one newly added navigation threshold-boundary test; that assertion was corrected without weakening the threshold.
- Run #186, SHA `6f15200325d3d333b3abff16c6f290fc81287b9c`: passed after that correction.
- **Run #187, SHA `ebdfdd7b22c9164ab4ea24a6fa744e706081fc7f`: passed** Android setup, WordNet generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and APK artifact upload. Artifact `CurrentLens-v0.2.8-stability-debug-apk`, ID `13559292205`, 39,052,427 bytes; expires 21 November 2026. [Open run #187](https://github.com/Batmancpu/CurrentLens/actions/runs/42334981467).
- No Release job ran on the feature branch. No main edit, merge, manual dispatch or rerun occurred.

## 5. Remaining limits

- CI proves JVM tests, Kotlin/Android compilation, APK assembly and artifact upload for the tested commit. It does not prove “bug-free” runtime behaviour on a physical device.
- No OPPO Reno10 logcat/tombstone/Perfetto capture, TalkBack session, Reduce Motion hardware check, frame-time, memory, thermal or battery profile has been performed for this exact build.
- The adaptive GPU readback is slower and paused during motion/non-home states, but its real device cost remains unmeasured.
- The legacy unordered read-marker set remains unbounded; only new markers are capped.
- Publisher RSS is secondary reporting. Automated primary-source corroboration against PIB, Gazette, ministries, Parliament, PRS, RBI, Supreme Court or regulators is not yet implemented.
- Do not call the product perfectly working or bug-free until device and end-to-end checks are completed.


## Fresh branch-graph audit — 7 November 2026, 09:30 IST

The repository API returned 26 remote branches on page 1 and none on page 2. The default branch is `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. Before the current UI batch, `black-box-bihar-news@e411c394e8d48b3f547b6d6fb9039a8935bd05eb` compared as 94 ahead / 0 behind main and 37 ahead / 0 behind PR #16. It descends from PR #16's corrected stability/backdrop baseline. PR #19, #20 and #23 are ancestors of the integration branch. The following table records every branch head, last commit timestamp/message and its PR relationship at audit time.

| Branch | Head SHA | Latest commit (UTC) | PR / state and treatment |
|---|---|---|---|
| `black-box-bihar-news` | `e411c394e8d48b3f547b6d6fb9039a8935bd05eb` | 2026-11-07 03:57:19 — docs: include parliamentary gate and final branch inventory | Active integration line; source for this batch |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 — ci: validate pull requests with read-only build permissions | PR #18 open; compatible workflow design integrated, source retained |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 — ci: restore Android SDK setup before sdkmanager | PR #20 merged into black-box; 35 commits behind current integration |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — Correct icon artwork accessibility name to brrrNews | PR #14 closed unmerged; head equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 — fix: refresh article card metadata when feed content changes | PR #6 open; editorial policy selectively ported; no wholesale merge |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 — fix: select Atom article alternate links deterministically | PR #12 open; FeedLinkPolicy equivalent integrated |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 — feat: expose publication date and publisher RSS provenance on cards | PR #11 open against black-box but head diverges (3 ahead / 94 behind); equivalent cache/date/provenance policies present; do not merge blindly |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 — fix: minimise shareable process-exit diagnostics | PR #16 open; integration ancestor (37 commits behind current) |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 — Verify a specific parliamentary bill remains eligible for the GS II feed | PR #21 open; combined editorial policy/tests ported |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 — test: align economy signal count with repo-rate mapping | PR #2 merged to main; retained |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 — fix: tighten UPSC editorial signal matching | PR #1 merged to main; retained |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 — fix: use supported calendar navigation glyph | PR #5 merged to main; retained |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 — test: preserve Parliament bill headline variants | PR #23 merged into black-box; current integration is 3 commits ahead |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 — Validate RSS redirects against publisher allowlists | PR #15 closed unmerged; equivalent host/redirect guards are in current code |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 — fix: deduplicate RSS tracking URL variants | PR #17 open; equivalent URL identity policy ported |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 — fix: capture Search glass tint before draw callback | PR #10 merged to main; current Prismal draw-scope fix retained |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 — fix: diagnose launch crashes and move feed indexing off main | PR #9 merged to main; current startup/indexing work retained |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 — fix: harden cold start and reduce feed rendering workload | PR #7 merged to main; current startup path retained |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — Correct icon artwork accessibility name to brrrNews | Default branch; current integration is 94 ahead / 0 behind |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 — Verify legacy markers survive recent-history retention | PR #22 open on PR #8 lineage; 9 ahead / 107 behind; current policy reconciled, legacy markers retained |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 — perf: move read-marker persistence off main thread | PR #8 open; IO/mutex persistence represented in current code; source retained |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 — fix: make selection decoding fallback valid JSON | Stale; 188 commits behind current integration; not used |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 — ui: restore low-cadence foreground adaptive glass contrast | PR #13 open; one shared 8×8 probe selectively ported with 4s/6s idle cadence; original faster cadence rejected |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 — fix: import bottom navigation glyphs | PR #3 merged; PR #4 closed unmerged; 3 unique commits / 112 behind current; stale, useful changes superseded |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 — docs(reader): record native selection collision guard | PR #19 merged into black-box; 24 commits behind current |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 — fix: finish syllabus metadata and thin-film feed eligibility | No separate PR; ancestor of black-box, 10 commits behind current |

### Comparison and safety rationale

- `main...black-box-bihar-news`: 94 ahead / 0 behind; merge base is main.
- `fix/early-startup-crash-diagnostics...black-box-bihar-news`: 37 ahead / 0 behind; PR #16 is the safe stability/Prismal ancestor.
- `ui/liquid-glass-system`, `ci/stacked-pr-validation`, `fix/parliamentary-syllabus-signal-gate` and `ui/liquid-glass-system-continuation` are ancestors of the integration branch; their deltas are already included.
- PR #11, #13, #12, #8, #22, #6, #21, #17, #18 and #15 have independent commits on divergent older lineages. Their useful policies were compared and selectively represented in current code; their branches were not merged or rewritten.
- The current workflow on black-box has read-only build permissions, pull-request validation for main/PR #16/black-box, and an isolated main-push release job. No workflow was dispatched manually. The current code commit awaits its natural workflow.
- No source branch was deleted, no main commit was made, and no Release was published.



## Live branch-graph audit — 7 November 2026 (pre-implementation source state)

The complete GitHub branch endpoint returned **28 remote refs** on page 1 and **0** on page 2. Default branch: `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. The table records the exact source graph before this iteration's commit; branch refs must be refreshed on the next run.

| Branch | Head SHA | Latest commit (UTC) | PR relationship / disposition |
|---|---|---|---|
| `black-box-bihar-news` | `c8afed0a78c536a36567c286f19fdafcada1ac70` | 2026-11-07 04:07:04 — docs: refresh liquid glass roadmap and branch graph audit | Owner-selected active consolidation branch; PRs #19, #20, #23 merged into this branch; no open PR from this head |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30 — ui: consolidate lightweight supporting glass surfaces | Divergent rename/validation line; PR #24 targets it; not selected under the single-branch instruction |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 — ci: validate pull requests with read-only build permissions | PR #18 open/draft, base main; compatible workflow design already integrated |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 — ci: restore Android SDK setup before sdkmanager | PR #20 merged into black-box |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — Correct icon artwork accessibility name to brrrNews | PR #14 closed unmerged; head equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 — fix: refresh article card metadata when feed content changes | PR #6 open/draft, base main; policy selectively represented |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 — fix: select Atom article alternate links deterministically | PR #12 open/draft, base main; FeedLinkPolicy equivalent integrated |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 — feat: expose publication date and publisher RSS provenance on cards | PR #11 open/draft, base black-box; divergent older base, do not merge wholesale |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 — fix: minimise shareable process-exit diagnostics | PR #16 open/draft, base main; required stability/Prismal ancestor |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 — Verify a specific parliamentary bill remains eligible for the GS II feed | PR #21 open/draft, base main; useful policy already reconciled |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47 — fix(editorial): match policy action and target as separate words | PR #24 open/draft, base brrrNEWS; 3-file editorial-only diff, not ported |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 — test: align economy signal count with repo-rate mapping | PR #2 merged into main |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 — fix: tighten UPSC editorial signal matching | PR #1 merged into main |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 — fix: use supported calendar navigation glyph | PR #5 merged into main |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 — test: preserve Parliament bill headline variants | PR #23 merged into black-box |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 — Validate RSS redirects against publisher allowlists | PR #15 closed unmerged; equivalent guards retained |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 — fix: deduplicate RSS tracking URL variants | PR #17 open/draft, base main; URL identity policy ported |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 — fix: capture Search glass tint before draw callback | PR #10 merged into main |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 — fix: diagnose launch crashes and move feed indexing off main | PR #9 merged into main |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 — fix: harden cold start and reduce feed rendering workload | PR #7 merged into main |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — Correct icon artwork accessibility name to brrrNews | Default branch; unchanged |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 — Verify legacy markers survive recent-history retention | PR #22 open/draft, base perf/off-main; reconciled policy represented |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 — perf: move read-marker persistence off main thread | PR #8 open/draft, base main; persistence policy represented |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 — fix: make selection decoding fallback valid JSON | Stale; not used |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 — ui: restore low-cadence foreground adaptive glass contrast | PR #13 open/draft, base main; safeguarded probe integrated |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 — fix: import bottom navigation glyphs | PR #3 merged into main; PR #4 closed unmerged; stale |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 — docs(reader): record native selection collision guard | PR #19 merged into black-box |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 — fix: finish syllabus metadata and thin-film feed eligibility | No separate PR; ancestor of black-box |

### Comparison and CI state at this audit

- `main...black-box-bihar-news`: 96 ahead / 0 behind; merge base main.
- `fix/early-startup-crash-diagnostics...black-box-bihar-news`: 39 ahead / 0 behind; corrected PR #16 baseline retained.
- `black-box-bihar-news...brrrNEWS`: 4 ahead / 2 behind; merge base `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`. Only the Reader resume-I/O commit was useful to this task and is selectively ported in the current implementation. The visible-label/workflow rename is deliberately excluded.
- `ui/liquid-glass-system-continuation...black-box-bihar-news`: 12 ahead / 0 behind; latest relevant UI continuation is included.
- Open PRs: #24 (base brrrNEWS), #11 (base black-box), #13, #6, #17, #22, #21, #16, #18, #8 and #12. Closed/merged PRs: #23, #19 and #20 into black-box; #10, #9, #7, #5, #3, #2 and #1 into main. #14, #15 and #4 are closed unmerged.
- Actions history: 199 runs, fully paginated (100 + 99 + 0); no queued or in-progress runs. Latest overall run #199 is PR #24 on its exact head and is not validation of black-box.
- Latest black-box validation before this iteration: run #196 on exact SHA `c8afed0`, success; artifact `13560390670` (39,057,437 bytes, SHA-256 `ced7d2283572550e3e74a9d159ba485ace818aa02d0c358f9fba0296b48c7254`). Run #195 on exact supporting-glass code SHA `5e966a1` also succeeded; artifact `13559698985`.
- Run #196 build steps: WordNet builder tests passed; offline WordNet generated 151,456 unique words; `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload succeeded. The isolated publish job was skipped.
- The single workflow on black-box has read-only build permissions and a separately gated `contents: write` job only for a main-branch push. Push and PR triggers include main, PR #16 and black-box. No workflow was manually dispatched or rerun.
- Latest published Release remains `v0.2.0-build-116` (6 November 2026 11:18:59 UTC). No feature-branch Release was published.
- No branch other than `black-box-bihar-news` was modified; no source ref was deleted; main remains unchanged.

## Reader resume-I/O port from brrrNEWS

Commit `e161a9a53a3d85d06b4908eb690aecb02a716015` was reviewed at full diff and ported selectively: the existing saved Reader progress is now restored on `Dispatchers.IO` before the WebView queues its content restore. The rest of the `brrrNEWS` branch's branch-specific workflow and visible app-label changes were not ported. No source branch was modified or deleted.


## 8. Live branch graph audit — 7 November 2026, 11:38 IST

The complete remote branch list was re-enumerated (28 refs; page 2 empty). Commit dates below are GitHub committer timestamps in UTC. Open PR heads/bases are identified; merged and closed refs remain untouched.

| Branch | Head SHA | Latest commit (UTC) | PR / status |
|---|---|---|---|
| black-box-bihar-news | cfe0b3e8319374df5cb29423f04a79c77d4fe10e | 2026-11-07 05:05:40 — restore Reader position off UI thread | Active consolidation branch; no PR to main |
| brrrNEWS | e6fcfc25a4c8d5f363ced58f313552120aa7ff96 | 2026-11-07 04:13:30 — consolidate supporting glass surfaces | Base of open PR #24 |
| ci/read-only-pr-validation-isolated-release-write-2026-11-06 | 963118f0abe5f7e38f2aa66a49d4b8e74548660c | 2026-11-06 17:18:53 — read-only PR validation | Open PR #18 head |
| ci/stacked-pr-validation-2026-11-07 | 5dc3179361835b77fb3186a99d9b5791301463fd | 2026-11-06 21:41:27 — Android SDK setup correction | PR #20 merged into consolidation |
| docs/ai-agent-handoff-2026-11-06 | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 2026-11-06 11:16:35 — icon accessibility name | Now points at main; PR #14 was closed unmerged at an older head |
| feat/gs1-geography-editorial-coverage | ca66fa1c865514a86c655a4b6c7eaf16b4681f81 | 2026-11-06 10:53:35 — refresh card metadata | Open PR #6 head |
| fix/atom-alternate-link-selection-2026-11-06 | cdf4c3846dde1c05894284e48b8f7d69cb7541ad | 2026-11-06 09:14:27 — deterministic Atom alternate links | Open PR #12 head |
| fix/cache-publication-date-integrity-2026-11-06 | be5151e674c2bd4fa9beb166fc38ddf622781f84 | 2026-11-06 14:26:32 — publication date/provenance | Open PR #11 head; base is older black-box SHA c6223c93de84cb25d1e19da42be3bd4992213f6d |
| fix/early-startup-crash-diagnostics-2026-11-06 | 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890 | 2026-11-06 19:33:20 — minimise shareable diagnostics | Open PR #16 head; 40 commits behind black-box |
| fix/editorial-heatwave-bill-signals-2026-11-07 | 89464104e300f48da10e3e32e33715cbe2405084 | 2026-11-06 22:05:40 — specific parliamentary bill eligibility | Open PR #21 head |
| fix/editorial-policy-exception-guard-2026-11-07 | 1f7a3c2a7b74cc02cfee6fe451f4576feb291acf | 2026-11-07 04:46:47 — separate policy action/target words | Open PR #24 head, based on brrrNEWS |
| fix/editorial-signal-count-test | 4738690d6e7f6c7c02a621742f25d5ebf1d631fb | 2026-11-06 05:16:32 — repo-rate signal count test | PR #2 merged to main |
| fix/editorial-token-boundaries-2026-11-06 | 0045c24637f162f1c13dceb1f8878f42f6eb15fd | 2026-11-06 02:34:28 — editorial signal boundaries | PR #1 merged to main |
| fix/navigation-icon-imports-2026-11-06 | fa8868940c08ed5b4a9def014a850e8a9dcb3490 | 2026-11-06 06:27:06 — supported calendar glyph | PR #5 merged to main |
| fix/parliamentary-syllabus-signal-gate-2026-11-07 | 9d754953a29c0355ce55306ce4fd5463193c9dbb | 2026-11-07 03:49:44 — Parliament headline variants | PR #23 merged into consolidation |
| fix/publisher-redirect-allowlist-2026-11-06 | 83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306 | 2026-11-06 12:15:39 — validate RSS redirects | PR #15 closed unmerged; safety guard retained from PR #16 |
| fix/rss-tracking-variant-dedup-2026-11-06 | 2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b | 2026-11-06 16:42:29 — deduplicate tracking URL variants | Open PR #17 head |
| fix/search-glass-theme-capture-2026-11-06 | c514eff3d5fd266ce6b6ecec51a141055e5fa52e | 2026-11-06 07:44:03 — Search tint capture order | PR #10 merged to main |
| fix/startup-crash-diagnostics-feed-jank-2026-11-06 | 5da4e789b0f49c0eefda024410ade34c1e0f2a7c | 2026-11-06 07:39:53 — startup diagnostics/feed indexing | PR #9 merged to main |
| fix/startup-stability-performance-2026-11-06 | 654e660347de692ef88531dfa49c610269d69671 | 2026-11-06 07:18:06 — cold-start/feed rendering | PR #7 merged to main |
| main | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 2026-11-06 11:16:35 — icon accessibility name | Default branch; unchanged |
| perf/bounded-read-history-2026-11-07 | 910178af3f595993557533cb65d03e714bcd7760 | 2026-11-06 23:53:04 — legacy marker retention test | Open PR #22 head; based on PR #8 |
| perf/off-main-session-persistence-2026-11-06 | ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a | 2026-11-06 10:50:31 — off-main read-marker persistence | Open PR #8 head |
| redesign/v0.3-search-reader | 654cc89dee4f93fea98bb76df9d8cf88abf2f0d8 | 2026-11-03 15:31:48 — selection fallback JSON | Stale; no active PR |
| ui/foreground-adaptive-glass-contrast-2026-11-06 | 3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264 | 2026-11-06 10:56:16 — foreground adaptive contrast | Open PR #13 head |
| ui/liquid-glass-performance-2026-11-06 | 2e9c7f62ca65a899e18d9026e1535392835b3724 | 2026-11-06 06:25:39 — bottom-nav glyph imports | Stale/diverged; PR #4 closed unmerged; earlier PR #3 merged |
| ui/liquid-glass-system-2026-11-07 | 1f7cd2e5ddc5d169d1443035a8334538e44efc40 | 2026-11-06 20:11:36 — Reader selection guard docs | PR #19 merged into consolidation |
| ui/liquid-glass-system-continuation-2026-11-07 | f031bf21229f43a992384cc23fa0f4da57e547a1 | 2026-11-07 03:18:34 — syllabus/thin-film eligibility | 13 commits behind consolidation; no new work to port |

### Freshness and safe-port rationale

- main...black-box-bihar-news: 97 ahead / 0 behind. PR16...black-box-bihar-news: 40 ahead / 0 behind. This is the latest coherent branch containing the corrected background-only Prismal root capture and the consolidated Reader/Search/feed-nav work.
- black-box-bihar-news...brrrNEWS: diverged 4 ahead / 3 behind from common ancestor e411c394e8d48b3f547b6d6fb9039a8935bd05eb. GlassSurfacePolicy.kt and its test are byte-identical; MainActivity's only content difference is formatting around Reader saved-position state. The sibling's workflow/README/app-build metadata was not copied.
- PR #24 is 9 ahead / 3 behind black-box with the same merge base. Its policy action/target matching change is separate editorial work; keep it on its draft branch until reviewed/ported as a dedicated change. Its successful CI, if any, validates only PR #24's head.
- PR #22's ReadHistoryPolicy.kt and test, PR #11's ArticleCachePolicy.kt and ArticleProvenancePolicy.kt, and PR #12's FeedLinkPolicy.kt and test are byte-identical to black-box. PR #17's URL-identity files and PR #21's UPSC engine/policy files differ; do not replace current code with their older divergent versions.
- PR #13's source uses a 1.5s/2.5s adaptive luminance cadence; black-box retains the more conservative 4s/6s foreground-only cadence and pauses during feed scroll, Reader, Settings and background.
- The workflow on black-box has push and pull_request triggers for main, PR #16 and black-box; build permissions are contents:read; the write-enabled publish job is gated to push on main only. No workflow_dispatch was used.

### Latest natural validation at audit time

- Actions history: 200 runs, fully paginated (100 + 100 + 0); no queued or in-progress runs.
- Latest overall run #200: success on exact black-box SHA cfe0b3e8319374df5cb29423f04a79c77d4fe10e; build/test job completed checkout, Java, signing cache, Android SDK, platform install, Gradle, WordNet builder tests/database generation, :app:testDebugUnitTest, :app:assembleDebug and artifact upload. Publish job skipped.
- Artifact 13560769755, CurrentLens-v0.2.8-stability-debug-apk, 39,059,340 bytes, 14-day retention. This is the non-main stability variant: application ID com.mangoloads.currentlens.stability, label brrrNews Stability; it installs separately from the normal app.
- Latest published Release remains v0.2.0-build-116; no Release was created by run #200.


## 9. Live branch graph and CI audit — 7 November 2026, 12:27 IST

The full remote branch list was refreshed (29 branches; page 2 empty). Commit timestamps are GitHub committer timestamps in UTC. This table supersedes earlier inventory snapshots above; all source refs remain untouched.

| Branch | Head SHA | Latest commit (UTC) | PR / status |
|---|---|---|---|
| `black-box-bihar-news` | `2e2c15f5189ec6c60fa1f07f384bce4aa1734ea0` | 2026-11-07 06:13:13 — app-wide reduced-transparency mode | Owner-selected active consolidation; no PR to main; source for this filter batch |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30 — consolidate lightweight supporting glass surfaces | Divergent sibling; base of open PR #24; not selected |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 — read-only PR validation | Open PR #18, base main; compatible design is represented in black-box workflow |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 — restore Android SDK setup before sdkmanager | PR #20 merged into black-box; source ref retained |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — icon accessibility name | Now points at main; PR #14 closed unmerged at an older head |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 — refresh article card metadata | Open PR #6, base main; compatible editorial policy selectively represented |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 — deterministic Atom alternate links | Open PR #12, base main; equivalent FeedLinkPolicy is byte-identical in black-box |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 — publication date and provenance | Open PR #11, older black-box base; current policy equivalents already integrated |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 — minimise shareable process-exit diagnostics | Open PR #16, base main; corrected stability/Prismal ancestor, 41 commits behind black-box |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 — preserve a specific parliamentary bill signal | Open PR #21, base main; useful editorial rules reconciled, no wholesale port |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47 — match policy action and target separately | Open PR #24, base brrrNEWS; three-file editorial-only PR diff, not ported |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 — align economy signal count | PR #2 merged into main |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 — tighten UPSC signal boundaries | PR #1 merged into main; old divergent branch not a baseline |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 — supported calendar glyph | PR #5 merged into main; old divergent branch not a baseline |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 — preserve Parliament headline variants | PR #23 merged into black-box; source ref retained |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 — validate RSS redirect hosts | PR #15 closed unmerged; equivalent safety guards retained in newer code |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46 — cover publisher-only image resources | Open PR #25, base black-box at the pre-filter head; seven-commit privacy/network child, untouched |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 — deduplicate RSS tracking variants | Open PR #17, base main; URL identity policy ported selectively |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 — capture Search tint before draw callback | PR #10 merged into main; older divergent branch |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 — startup diagnostics and feed indexing | PR #9 merged into main; older divergent branch |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 — harden cold start and feed workload | PR #7 merged into main; older divergent branch |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 — icon accessibility name | Default branch; unchanged |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 — verify legacy markers survive retention | Open PR #22, base PR #8; reconciled supplemental policy represented |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 — move read-marker persistence off main | Open PR #8, base main; persistence changes reconciled into black-box |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 — selection fallback JSON | Stale; 192 commits behind black-box |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 — restore foreground adaptive contrast | Open PR #13, base main; original 1.5s/2.5s cadence deliberately not copied |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 — bottom-nav glyph imports | Stale/diverged; PR #4 closed unmerged; earlier PR #3 merged into main |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 — record Reader selection guard | PR #19 merged into black-box; source ref retained |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 — syllabus and thin-film eligibility | Ancestor of black-box; no missing UI delta |

### Current branch comparisons and dependency decisions

- `main...black-box-bihar-news`: 98 ahead / 0 behind; merge base main. `fix/early-startup-crash-diagnostics...black-box-bihar-news`: 41 ahead / 0 behind. The selected branch retains the corrected background-only Prismal source and all owner-consolidated Reader/Search/feed-navigation changes.
- `black-box-bihar-news...brrrNEWS`: diverged 4 ahead / 4 behind. `GlassSurfacePolicy.kt` and its JVM test are byte-identical; the supporting-glass implementation is already present. PR #24's own diff is editorial-only; its branch's remaining workflow/app-label metadata is not adopted.
- PR #25 head is 7 ahead / 0 behind the pre-batch black-box head. Its asset allowlist/cached HTML changes are separate from this batch's Saved/stat filter call sites; do not merge or rewrite its branch. The two MainActivity diff regions are in NewsCard and Reader cache loading, not the filter screens.
- PR #13's 1.5s/2.5s probe cadence is superseded by the conservative one-source 4s/6s policy, paused during feed scroll, Reader, Settings and background. PR #18's read-only workflow proposal is not copied blindly; the compatible PR #20 workflow is already in black-box. The workflow on main remains push-only with broader write permissions; this batch changes no workflow.
- PR #11/12/17, PR #6/21 and PR #8/22 remain independent open source refs. Their useful cache/provenance, Atom link, URL identity, editorial and supplemental read-history policies were selectively reconciled; their divergent MainActivity/network/persistence files are not authoritative replacements.
- Stale `ui/liquid-glass-performance` and `redesign/v0.3-search-reader` are not baselines. `ui/liquid-glass-system` and `ui/liquid-glass-system-continuation` are ancestors of the selected branch.

### Natural CI and release state

- Actions history: 202 runs (pages 100 + 100 + 2; next page empty); queue empty. Latest overall #202 is PR #25 on its exact child head, not this branch.
- Run #201 on exact black-box SHA `2e2c15f` completed the build job successfully: Python WordNet builder ran 4 tests (OK), generated 151,456 unique words / 50,950,144-byte database, then `:app:testDebugUnitTest` and `:app:assembleDebug` succeeded (44 tasks; 32 executed, 12 cached). Artifact upload succeeded; publish job skipped.
- Run #201 artifact: `13563139070`, `CurrentLens-v0.2.8-stability-debug-apk`, 39,067,369 bytes, SHA-256 `236b9611c925a8a3f9aae4f7a28b57ef76c7c488161ada7d2d84fd54bcce27fb`.
- Run #202 on PR #25 exact SHA `7a51c9c` also passed WordNet generation, JVM tests, APK assembly and upload; artifact `13563912571`, 39,072,517 bytes, SHA-256 `ef86f304323ddbcac17db3bce426e18853fa8affd364d09dcfe870ba00b31f44`. It does not validate black-box.
- No Release was created by #201/#202. Latest published Release remains `v0.2.0-build-116`. No main edit/merge, manual dispatch/rerun, other-branch write or ref deletion occurred.

### Device/performance limitations

No post-change OPPO Reno10 runtime launch, screenshot, TalkBack session, Reduce Motion verification, logcat/tombstone, Perfetto trace, GPU overdraw/frame-time, allocation/memory, thermal or battery evidence is available. Keep those checks **UNVERIFIED**; successful CI only establishes the tested source compiles, passes JVM tests and packages an APK.


## 10. Historical branch-graph freshness audit — 7 November 2026, 13:00 IST (superseded by §11)

The repository default remains main@73b8a0d1ba33503fede02db2c3c0af082d724a12. The complete remote inventory returned 31 refs on page 1 and zero on page 2. All 13 open PRs and 13 closed PRs were re-read; branch heads were compared against the active integration branch, main and the relevant PR #16 baseline. Commit timestamps below are UTC; PR numbers refer to current live relationships.

| Remote branch | Head SHA | Latest commit (UTC) | Latest commit message | PR / disposition |
|---|---|---|---|---|
| black-box-bihar-news | 3a08bb95b0f9a0fed8ec11dae055f0ac2817a523 | 2026-11-07 07:04:04 | ui: unify Saved and stats filter glass controls | Owner-selected active integration branch; base for PR #25/#26; only branch modified by this task |
| brrrNEWS | e6fcfc25a4c8d5f363ced58f313552120aa7ff96 | 2026-11-07 04:13:30 | ui: consolidate lightweight supporting glass surfaces | Divergent sibling; PR #24 base; not active |
| ci/read-only-pr-validation-isolated-release-write-2026-11-06 | 963118f0abe5f7e38f2aa66a49d4b8e74548660c | 2026-11-06 17:18:53 | ci: validate pull requests with read-only build permissions | PR #18 head; compatible intent already represented in active workflow |
| ci/stacked-pr-validation-2026-11-07 | 5dc3179361835b77fb3186a99d9b5791301463fd | 2026-11-06 21:41:27 | ci: restore Android SDK setup before sdkmanager | PR #20 closed/merged into black-box only |
| docs/ai-agent-handoff-2026-11-06 | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | Identical to main; no unique implementation |
| feat/gs1-geography-editorial-coverage | ca66fa1c865514a86c655a4b6c7eaf16b4681f81 | 2026-11-06 10:53:35 | fix: refresh article card metadata when feed content changes | PR #6 head; relevant editorial policy already selectively reconciled |
| fix/atom-alternate-link-selection-2026-11-06 | cdf4c3846dde1c05894284e48b8f7d69cb7541ad | 2026-11-06 09:14:27 | fix: select Atom article alternate links deterministically | PR #12 head; parser policy/tests selectively reconciled |
| fix/cache-publication-date-integrity-2026-11-06 | be5151e674c2bd4fa9beb166fc38ddf622781f84 | 2026-11-06 14:26:32 | feat: expose publication date and publisher RSS provenance on cards | PR #11 head; current date/cache/provenance policy selectively reconciled |
| fix/early-startup-crash-diagnostics-2026-11-06 | 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890 | 2026-11-06 19:33:20 | fix: minimise shareable process-exit diagnostics | PR #16 head; active integration descends from it |
| fix/editorial-action-gate-blackbox-2026-11-07 | 3a08bb95b0f9a0fed8ec11dae055f0ac2817a523 | 2026-11-07 07:04:04 | ui: unify Saved and stats filter glass controls | Alias of active integration head; no separate work |
| fix/editorial-heatwave-bill-signals-2026-11-07 | 89464104e300f48da10e3e32e33715cbe2405084 | 2026-11-06 22:05:40 | Verify a specific parliamentary bill remains eligible for the GS II feed | PR #21 head; editorial changes represented in active branch |
| fix/editorial-policy-exception-guard-2026-11-07 | 1f7a3c2a7b74cc02cfee6fe451f4576feb291acf | 2026-11-07 04:46:47 | fix(editorial): match policy action and target as separate words | PR #24 head; based on brrrNEWS; editorial-only, not ported in this UI pass |
| fix/editorial-signal-count-test | 4738690d6e7f6c7c02a621742f25d5ebf1d631fb | 2026-11-06 05:16:32 | test: align economy signal count with repo-rate mapping | PR #2 merged into main; later test state is integrated |
| fix/editorial-token-boundaries-2026-11-06 | 0045c24637f162f1c13dceb1f8878f42f6eb15fd | 2026-11-06 02:34:28 | fix: tighten UPSC editorial signal matching | PR #1 merged into main; later matcher is integrated |
| fix/navigation-icon-imports-2026-11-06 | fa8868940c08ed5b4a9def014a850e8a9dcb3490 | 2026-11-06 06:27:06 | fix: use supported calendar navigation glyph | PR #5 merged into main; current glyphs are retained |
| fix/parliamentary-syllabus-signal-gate-2026-11-07 | 9d754953a29c0355ce55306ce4fd5463193c9dbb | 2026-11-07 03:49:44 | test: preserve Parliament bill headline variants | PR #23 closed/merged into black-box only |
| fix/publisher-redirect-allowlist-2026-11-06 | 83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306 | 2026-11-06 12:15:39 | Validate RSS redirects against publisher allowlists | PR #15 closed unmerged; equivalent safeguards retained in current network code |
| fix/rss-image-host-allowlist-2026-11-07 | 7a51c9c2c40aa89997db55194a6db3d0d65ac95d | 2026-11-07 06:28:46 | test(reader): cover publisher-only image resources | PR #25 head; open, separate publisher-asset security scope |
| fix/rss-tracking-variant-dedup-2026-11-06 | 2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b | 2026-11-06 16:42:29 | fix: deduplicate RSS tracking URL variants | PR #17 head; URL identity policy selectively reconciled |
| fix/search-glass-theme-capture-2026-11-06 | c514eff3d5fd266ce6b6ecec51a141055e5fa52e | 2026-11-06 07:44:03 | fix: capture Search glass tint before draw callback | PR #10 merged into main; fix retained |
| fix/startup-crash-diagnostics-feed-jank-2026-11-06 | 5da4e789b0f49c0eefda024410ade34c1e0f2a7c | 2026-11-06 07:39:53 | fix: diagnose launch crashes and move feed indexing off main | PR #9 merged into main; later diagnostics/indexing retained |
| fix/startup-stability-performance-2026-11-06 | 654e660347de692ef88531dfa49c610269d69671 | 2026-11-06 07:18:06 | fix: harden cold start and reduce feed rendering workload | PR #7 merged into main; later safeguards retained |
| main | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | Default branch; unchanged |
| perf/bounded-read-history-2026-11-07 | 910178af3f595993557533cb65d03e714bcd7760 | 2026-11-06 23:53:04 | Verify legacy markers survive recent-history retention | PR #22 head; new markers bounded, legacy unordered markers retained |
| perf/off-main-session-persistence-2026-11-06 | ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a | 2026-11-06 10:50:31 | perf: move read-marker persistence off main thread | PR #8 head; IO/mutex work selectively reconciled |
| privacy/webview-publisher-subresource-gate-2026-11-07 | b8d2ef9ff2413c378347986d00961c401dfa771d | 2026-11-07 07:27:22 | privacy: block third-party publisher reader subresources | PR #26 head; open, direct child of current pre-batch integration head |
| redesign/v0.3-search-reader | 654cc89dee4f93fea98bb76df9d8cf88abf2f0d8 | 2026-11-03 15:31:48 | fix: make selection decoding fallback valid JSON | Stale; 193 commits behind active integration |
| ui/foreground-adaptive-glass-contrast-2026-11-06 | 3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264 | 2026-11-06 10:56:16 | ui: restore low-cadence foreground adaptive glass contrast | PR #13 head; shared 8×8 probe policy selectively integrated at 4s/6s |
| ui/liquid-glass-performance-2026-11-06 | 2e9c7f62ca65a899e18d9026e1535392835b3724 | 2026-11-06 06:25:39 | fix: import bottom navigation glyphs | PR #4 closed unmerged; PR #3's earlier material changes are superseded by the active UI |
| ui/liquid-glass-system-2026-11-07 | 1f7cd2e5ddc5d169d1443035a8334538e44efc40 | 2026-11-06 20:11:36 | docs(reader): record native selection collision guard | PR #19 closed/merged into black-box only |
| ui/liquid-glass-system-continuation-2026-11-07 | f031bf21229f43a992384cc23fa0f4da57e547a1 | 2026-11-07 03:18:34 | fix: finish syllabus metadata and thin-film feed eligibility | Earlier validated checkpoint; active branch is ahead |

### Exact ancestry and dependency decisions

- main...black-box-bihar-news: 99 ahead / 0 behind; merge base is main. fix/early-startup-crash-diagnostics...black-box-bihar-news: 42 ahead / 0 behind; merge base is PR #16 head. The selected branch therefore contains the corrected background-only Prismal capture and the newer approved UI/data work without starting from stale main.
- black-box-bihar-news...brrrNEWS: diverged, with brrrNEWS 4 commits ahead and black-box 5 commits ahead of merge base e411c394e8d48b3f547b6d6fb9039a8935bd05eb. The GlassSurfacePolicy source and test are byte-identical. The Reader saved-position IO restore is already present in black-box; the brrrNEWS-specific workflow branch filter, artifact rename and app-label/docs changes are not adopted. PR #24 is editorial-only and remains on brrrNEWS.
- PR #25 is based on black-box's older 2e2c15f head and its full diff changes ArticleReaderRepository, NewsRepository, PublisherAssetHostPolicy, sanitizer tests and MainActivity's NewsCard/cached-Reader image paths. PR #26 is a one-commit child of 3a08bb95 and adds PublisherWebResourcePolicy plus ReaderWebView subresource interception. Both exact PR heads passed natural CI (#202 and #204 respectively); neither changes QuizScreen. They remain open and unmodified pending a separate shared publisher-host policy review.
- PR #13's original 1.5s/2.5s adaptive readback branch is not used; current black-box code retains one 8×8 shared source at 4s/6s, paused during scrolling and outside foreground home surfaces. PR #18/20 workflow intent is integrated only in the active branch; neither source branch was modified.
- PR #24's full diff is limited to UpscFeedPolicy.kt, its tests and docs/UPSC_EDITORIAL_BASELINE.md; it requires an institutional actor plus a concrete action on the policy target. It is not ported here because this run is scoped to Quiz UI and the PR is based on a divergent sibling. Its natural run #199 passed on its exact head.
- No new PR from black-box to main was created. Main's workflow 37197497288d9f0a60f4d1cfa8453e3dee52a902 still triggers only on push to main with workflow-wide contents: write; do not claim a future PR-to-main run until the base workflow is safely reconciled.

### Open and closed pull-request register

**Open (13):** #26 (publisher subresource guard, base black-box), #25 (publisher asset allowlist, older black-box base), #24 (editorial policy exception, base brrrNEWS), #22 (bounded new read history, base PR #8), #21 (bill/heatwave editorial, base main), #18 (read-only workflow proposal, base main), #17 (URL-variant dedupe, base main), #16 (startup/network stability, base main), #13 (adaptive contrast, base main), #12 (Atom alternate links, base main), #11 (date/cache/provenance, older black-box base), #8 (off-main persistence, base main), #6 (GS I Geography/editorial coverage, base main).

**Closed (13):** #23, #20 and #19 are merged into black-box only; #15 and #14 are closed unmerged; #10, #9, #7, #5, #3, #2 and #1 are merged into main; #4 is closed unmerged. No branch was deleted.

### Natural CI and release state

- Actions history: 204 runs, fully paginated (100 + 100 + 4; next page empty). No queued or in-progress runs.
- Run #203, exact black-box SHA 3a08bb95b0f9a0fed8ec11dae055f0ac2817a523, completed the build job successfully: four WordNet builder tests passed; database generation produced 151,456 unique words / 50,950,144 bytes; :app:testDebugUnitTest and :app:assembleDebug succeeded; artifact upload succeeded. Publish job skipped.
- Run #203 artifact: 13565296151, CurrentLens-v0.2.8-stability-debug-apk, 39,078,047 bytes, created 7 Nov 2026 07:06:19Z, expires 21 Nov 2026 07:06:17Z.
- Run #204 on PR #26 exact SHA b8d2ef9ff2413c378347986d00961c401dfa771d passed the same build/test/assemble/upload sequence; artifact 13565228424, 39,079,206 bytes. Run #202 on PR #25 exact SHA 7a51c9c2c40aa89997db55194a6db3d0d65ac95d also passed; artifact 13563912571, 39,072,517 bytes. These are separate PR-head validations, not black-box validation.
- Run #203 logs contain non-blocking Node.js 20 action-runtime deprecation warnings plus punycode/url.parse() warnings; all build/test/assemble/upload steps succeeded.
- Latest published Release is v0.2.0-build-116; no Release was created by #202/#203/#204. No manual dispatch/rerun, main edit/merge, other-branch write or ref deletion occurred.

### Device/performance limitations

No post-change OPPO Reno10 runtime launch, screenshot, TalkBack session, Reduce Motion verification, logcat/tombstone, Perfetto trace, GPU overdraw/frame-time, allocation/memory, thermal or battery evidence is available. Keep those checks **UNVERIFIED**; successful CI only establishes the tested source compiles, passes JVM tests and packages an APK.


## 11. Live branch-graph freshness audit — 7 November 2026 (after natural runs #210–#211)

The repository default remains `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. The complete remote inventory returned **32 refs** on page 1 and zero on page 2. There are **14 open PRs** and **14 closed PRs**; no branch was deleted. All branch heads, open/closed PR relationships, current workflow and Actions history were re-read. The exact current branch-head table is below.

- `main...black-box-bihar-news`: 101 ahead / 0 behind; merge base is main.
- `fix/early-startup-crash-diagnostics...black-box-bihar-news`: black-box is 44 ahead / 0 behind; PR #16's corrected background-only Prismal capture/stability code remains in the active lineage.
- `black-box-bihar-news...brrrNEWS`: diverged; brrrNEWS is 4 ahead / 7 behind, common ancestor `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`. `GlassSurfacePolicy.kt` is byte-identical on both branches (blob `96fc60adf8ef7b1f39a1ae7bb75a87c0876b2aa4`); no supporting-glass port is missing. Do not switch to or merge the sibling's workflow/branding changes.
- `ui/liquid-glass-system-continuation` is an earlier validated ancestor 17 commits behind the active branch. `ui/liquid-glass-performance` is 3 ahead / 119 behind and remains stale. `redesign/v0.3-search-reader` is stale.
- PR #28's single-line Quiz semantics correction was fast-forwarded into black-box at exact head `6f9b3c1e1a484233721863d0b736734c0d282cd5`; GitHub closed PR #28 as merged into this integration branch. Its source branch remains. No main or other branch was changed.
- PR #27 is now at `9b70ed1c7c39bb511399c6284c7e8833f4672618`, 5 ahead / 2 behind active, with a four-file strict institutional-action editorial gate. Its natural run #211 passed on its own head. It is useful but separate editorial scope; it was not ported in this Liquid Glass/build-repair iteration.
- PR #25's publisher asset allowlist and cached-HTML image filtering remain separate (7 ahead / 3 behind active). PR #26's publisher WebView subresource guard remains separate (1 ahead / 2 behind active); its own source comments correctly limit the guarantee to the initial request because WebView may not call interception for every redirect hop. Both PR heads passed their own natural CI (#202/#204), but neither validates active black-box. Review/port them only as a separate publisher-security batch after inspecting current Reader and image call sites.
- PR #24 remains based on divergent `brrrNEWS`; its editorial policy change is not adopted. PR #13's faster 1.5s/2.5s sampler cadence remains rejected; active code retains one 8×8 source at 4s/6s and pauses it while scrolling, outside foreground home, in Reader/Quiz/Settings or under Reduce Transparency.
- PR #18's compatible read-only workflow design is represented in the active branch; no workflow was changed in this run. Main still has a push-only workflow with workflow-wide `contents: write`, so do not describe a future black-box-to-main PR as naturally CI-validated until main's workflow is safely reconciled.
- No PR from black-box to main was created: the consolidated diff is not yet a focused reviewable PR and main has no pull_request trigger. No branch was deleted or force-moved.

### Current remote branch heads

| Remote branch | Head SHA | Latest commit (UTC) | Latest commit message | PR relationship / disposition |
|---|---|---|---|---|
| `black-box-bihar-news` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10 | fix(quiz): qualify selected semantics receiver | Active integration branch; PR #28 correction fast-forwarded here |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30 | ui: consolidate lightweight supporting glass surfaces | Divergent sibling; PR #24 base; 4 ahead / 7 behind active |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 | ci: validate pull requests with read-only build permissions | PR #18 head; compatible workflow intent represented in active branch |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 | ci: restore Android SDK setup before sdkmanager | PR #20 closed/merged into black-box only; source branch retained |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | Identical to main; no unique implementation |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 | fix: refresh article card metadata when feed content changes | PR #6 head; editorial coverage selectively reconciled |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 | fix: select Atom article alternate links deterministically | PR #12 head; parser policy/tests selectively reconciled |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 | feat: expose publication date and publisher RSS provenance on cards | PR #11 head; date/cache/provenance policy selectively reconciled |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 | fix: minimise shareable process-exit diagnostics | PR #16 head; corrected background-only capture ancestor |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `9b70ed1c7c39bb511399c6284c7e8833f4672618` | 2026-11-07 09:02:50 | fix(editorial): preserve regulatory coverage amid entertainment filtering | PR #27 head; open/draft, 5 ahead / 2 behind active; independent editorial scope |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 | Verify a specific parliamentary bill remains eligible for the GS II feed | PR #21 head; relevant editorial rules selectively reconciled |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47 | fix(editorial): match policy action and target as separate words | PR #24 head; open/draft on divergent brrrNEWS base |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 | test: align economy signal count with repo-rate mapping | PR #2 merged into main; later test state integrated |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 | fix: tighten UPSC editorial signal matching | PR #1 merged into main; later matcher integrated |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 | fix: use supported calendar navigation glyph | PR #5 merged into main; current glyphs retained |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 | test: preserve Parliament bill headline variants | PR #23 closed/merged into black-box only |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 | Validate RSS redirects against publisher allowlists | PR #15 closed unmerged; equivalent safeguards retained |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10 | fix(quiz): qualify selected semantics receiver | PR #28 merged into black-box; branch now equals active HEAD |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46 | test(reader): cover publisher-only image resources | PR #25 head; open/draft, 7 ahead / 3 behind active |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 | fix: deduplicate RSS tracking URL variants | PR #17 head; URL identity policy selectively reconciled |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 | fix: capture Search glass tint before draw callback | PR #10 merged into main; fix retained |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 | fix: diagnose launch crashes and move feed indexing off main | PR #9 merged into main; later safeguards retained |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 | fix: harden cold start and reduce feed rendering workload | PR #7 merged into main; later safeguards retained |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | Default branch; unchanged |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 | Verify legacy markers survive recent-history retention | PR #22 head; open/draft, based on PR #8 |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 | perf: move read-marker persistence off main thread | PR #8 head; IO/mutex changes selectively reconciled |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27:22 | privacy: block third-party publisher reader subresources | PR #26 head; open/draft, 1 ahead / 2 behind active |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 | fix: make selection decoding fallback valid JSON | Stale; no longer a valid baseline |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 | ui: restore low-cadence foreground adaptive glass contrast | PR #13 head; 1.5s/2.5s cadence rejected; active branch uses 4s/6s |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 | fix: import bottom navigation glyphs | PR #4 closed unmerged; stale, 3 ahead / 119 behind active |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 | docs(reader): record native selection collision guard | PR #19 closed/merged into black-box only |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 | fix: finish syllabus metadata and thin-film feed eligibility | Earlier validated checkpoint; 17 commits behind active |

### Current PR register

**Open/draft (14):** #27 (editorial action gate, base black-box at older `3a08bb95`); #26 (publisher subresource guard, base black-box at `3a08bb95`); #25 (publisher asset allowlist, base black-box at `2e2c15f5`); #24 (editorial policy exception, base brrrNEWS); #22 (bounded new read-history window, base PR #8); #21 (heatwave/bill editorial, base main); #18 (read-only workflow proposal, base main); #17 (URL-variant dedupe, base main); #16 (startup/network stability, base main); #13 (adaptive contrast, base main); #12 (Atom alternate links, base main); #11 (date/cache/provenance, older black-box base); #8 (off-main persistence, base main); #6 (GS I Geography/editorial coverage, base main).

**Closed (14):** #28, #23, #20 and #19 merged into black-box only; #15, #14 and #4 closed unmerged; #10, #9, #7, #5, #3, #2 and #1 merged into main. Source branches remain; none was deleted.

### Natural CI, artifacts and releases

- Actions history: **211 runs**, fully paginated (100 + 100 + 11; page 4 empty): 125 success, 54 failure, 32 cancelled; queue empty.
- Run #208 failed on black-box pre-fix SHA `947310a157e03df25dde3856d2686d18ffca68e8` at `MainActivity.kt:4238:61` with Boolean-vs-`Map<Int, Int>` assignment mismatch in Quiz semantics. The exact receiver-qualified fix is in #28.
- Run #209 passed on PR #28 head `6f9b3c1e1a484233721863d0b736734c0d282cd5`; artifact `13566663804`, 39,082,053 bytes.
- Run #210, natural push on active black-box exact SHA `6f9b3c1e1a484233721863d0b736734c0d282cd5`, passed WordNet builder tests (4/4), generated 151,456 unique words / 50,950,144-byte database, passed `:app:testDebugUnitTest` and `:app:assembleDebug`, and uploaded artifact `13568511654` (39,082,053 bytes; SHA-256 `9eeb4bbdbfd08f9f8f5e94a5eda0251b41d1c441933653c0ef326246669458de`). Release job skipped.
- Run #211 passed on PR #27's exact head `9b70ed1c7c39bb511399c6284c7e8833f4672618`, not black-box; artifact `13567364771` (39,084,359 bytes; SHA-256 `95d591d857eaa7e354be8d6a06fb80d3148e9ba46491fa27354d9397f573f4be`). Release job skipped.
- Active workflow blob: `6cef6ca19f3babd331c9deb59be9da0067c6fa66`; main workflow blob: `37197497288d9f0a60f4d1cfa8453e3dee52a902`; PR #16 workflow blob: `d7dfe25301cd877a27b5dc603277c76bde530198`. Build/test permissions on active branch are read-only; only a main push can reach the separate Release-write job.
- Latest published Release remains `v0.2.0-build-116`; no Release was created by #208–#211.

### Warnings and device limitations

The passing builds still report non-blocking deprecated WebView `scale`, Compose `updateTransition`, and non-auto-mirrored ArrowBack API warnings, plus Node.js 20 action-runtime and Node `punycode` / `url.parse()` deprecation warnings. They were not changed in this scoped repair. No post-change OPPO Reno10 launch, screenshot, TalkBack/Reduce Motion session, logcat/tombstone, Perfetto/frame-time, GPU-overdraw, allocation/memory, thermal or battery evidence exists. Keep those checks **UNVERIFIED**; CI only establishes compilation, JVM tests and APK packaging on the tested source.

## 12. Live branch freshness and CI audit — 7 November 2026 (after run #213)

The owner-selected integration baseline was re-enumerated before this implementation. The complete 33-branch inventory is below; commit dates are UTC and messages are the exact latest branch-head subjects. “PR relation” identifies a current head/base or disposition, not a claim that the branch is merged into main.

| Remote branch | Head SHA | Latest commit (UTC) | Latest commit message | PR relation / disposition |
|---|---|---|---|---|
| `black-box-bihar-news` | `16eb549c3235b321ecf7ef1c5c5270817e336489` | 2026-11-07 09:07:46 | docs: record quiz build repair and live branch audit | Owner-selected active branch; base of open #25/#26/#27/#29 |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30 | ui: consolidate lightweight supporting glass surfaces | Base of open #24; sibling diverged 4 ahead / 8 behind active |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 | ci: validate pull requests with read-only build permissions | Open #18 head; compatible intent already represented in active workflow |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 | ci: restore Android SDK setup before sdkmanager | Closed/merged #20 into black-box only |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | Closed #14 unmerged; head equals main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 | fix: refresh article card metadata when feed content changes | Open #6 head; relevant classifier changes selectively represented |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 | fix: select Atom article alternate links deterministically | Open #12 head; current parser policy already represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 | feat: expose publication date and publisher RSS provenance on cards | Open #11 head on older black-box base; selected policies already represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 | fix: minimise shareable process-exit diagnostics | Open #16 head; direct ancestor of active branch |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `9b70ed1c7c39bb511399c6284c7e8833f4672618` | 2026-11-07 09:02:50 | fix(editorial): preserve regulatory coverage amid entertainment filtering | Open #27 head; 5 ahead / 3 behind active; separate editorial scope |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 | Verify a specific parliamentary bill remains eligible for the GS II feed | Open #21 head; selected bill/heat-wave rules already reconciled, not merged wholesale |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47 | fix(editorial): match policy action and target as separate words | Open #24 head on brrrNEWS; separate editorial policy |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 | test: align economy signal count with repo-rate mapping | #2 merged into main |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 | fix: tighten UPSC editorial signal matching | #1 merged into main |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 | fix: use supported calendar navigation glyph | #5 merged into main |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 | test: preserve Parliament bill headline variants | #23 merged into black-box only |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 | Validate RSS redirects against publisher allowlists | #15 closed unmerged; equivalent guards are retained in the newer PR #16 lineage |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10 | fix(quiz): qualify selected semantics receiver | #28 merged into black-box only |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46 | test(reader): cover publisher-only image resources | Open #25 head; 7 ahead / 4 behind active; separate publisher-asset guard |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 | fix: deduplicate RSS tracking URL variants | Open #17 head; URL identity policy already represented |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 | fix: capture Search glass tint before draw callback | #10 merged into main |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 | fix: diagnose launch crashes and move feed indexing off main | #9 merged into main; superseded by newer PR #16 startup safeguards |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 | fix: harden cold start and reduce feed rendering workload | #7 merged into main |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | Default branch; unchanged |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 | Verify legacy markers survive recent-history retention | Open #22 head, stacked on #8; preserves old unordered markers, so total legacy storage is not bounded |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05:25 | perf(storage): move bookmark mutations off the UI thread | Open #29 head; 1 ahead / 1 behind active; natural run #212 passed on its exact head; not part of this UI batch |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 | perf: move read-marker persistence off main thread | Open #8 head; read/session/quiz persistence policy selectively represented |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27:22 | privacy: block third-party publisher reader subresources | Open #26 head; 1 ahead / 3 behind active; separate WebView security reconciliation |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 | fix: make selection decoding fallback valid JSON | Stale checkpoint; 196 commits behind active; not a baseline |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 | ui: restore low-cadence foreground adaptive glass contrast | Open #13 head; its 1.5s/2.5s cadence is not copied; active policy remains one 8×8 source at 4s/6s |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 | fix: import bottom navigation glyphs | #4 closed unmerged; 3 ahead / 120 behind active; stale |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 | docs(reader): record native selection collision guard | #19 closed/merged into black-box only; source branch retained |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 | fix: finish syllabus metadata and thin-film feed eligibility | Earlier validated checkpoint; 18 commits behind active |

### Freshness rationale and outstanding work

- `black-box-bihar-news@16eb549c3235b321ecf7ef1c5c5270817e336489` is the newest coherent owner-selected line: it descends from PR #16's corrected background-only Prismal/stability head and is 102 ahead / 0 behind main. The current head is later than all other branch-head timestamps except the PR #29 and PR #27 independent commits, which are not complete descendant lineages and have overlapping non-UI scopes.
- `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` is divergent (4 ahead / 8 behind active). Its `GlassSurfacePolicy.kt` and `GlassSurfacePolicyTest.kt` blob SHAs exactly match the files already present in black-box; its workflow/app-label/documentation changes are not adopted. PR #24 changes the separate editorial policy and is not copied into the UI branch.
- PR #29 is a safe, useful bookmark read/modify/write move to IO with a mutex and a pure preservation policy; its own exact-head run #212 passed. It remains a separate pending storage batch so this transient-control UI change does not bundle unrelated persistence work. The complete bookmark set is intentionally preserved; no retention cap is applied.
- PR #25/#26 are publisher asset/subresource restrictions; their MainActivity changes overlap Reader resource handling and require a separate shared-host/privacy review. Do not blindly copy either.
- PR #27 is an editorial-action gate; its natural run #211 validates only its own head. PR #21/#24 and other editorial branches remain separate. Existing integrated editorial policies are documented above.
- PR #22's new-marker window does not bound old unordered legacy markers; the active implementation preserves those legacy values. PR #13's faster 1.5s/2.5s sampler is rejected in favour of the active single 8×8 probe at 4s/6s.
- The active workflow remains read-only for build/test and isolates Release write permission to a main push. Main's current workflow is still push-only; no PR-to-main CI claim is made. No draft PR to main was opened in this batch because the consolidated diff remains broad and the base workflow does not naturally validate pull requests.

### Latest natural CI and Release check

- Exact active head before this batch: `16eb549c3235b321ecf7ef1c5c5270817e336489`; run #213 (push) completed successfully.
- Build job steps: checkout, Java, cached stability signing key, Android SDK/platform, Gradle, WordNet builder, offline DB generation, `:app:testDebugUnitTest`, `:app:assembleDebug`, APK artifact upload all succeeded; stability key generation skipped due to cache hit.
- WordNet builder: 4/4 Python tests passed; 151,456 unique words generated to a 50,950,144-byte database.
- Artifact: `13568329178`, `CurrentLens-v0.2.8-stability-debug-apk`, 39,082,053 bytes, SHA-256 `2a0af7c27ff5aff00b48c12d7659cb5b37b5103f6858ebf090be02b2cc9f0981`; expires 21 Nov 2026 09:09:56Z. Publish job skipped.
- Full job log inspected (1,521 lines). Remaining non-blocking warnings include deprecated WebView `scale`, Compose `updateTransition`, non-auto-mirrored ArrowBack, Node.js 20 action runtime and Node `punycode`/`url.parse()`.
- Latest published Release remains `v0.2.0-build-116`; no Release created by runs #211–#213.
- This is the last verified CI on the base SHA, not on the transient-control child commit. The new child commit must receive its own natural run.

## Live branch-freshness and CI audit — 7 November 2026 (before bookmark-I/O port)

Repository/default: Batmancpu/CurrentLens; default main at 73b8a0d1ba33503fede02db2c3c0af082d724a12. Complete remote inventory: 33 branches on page 1; page 2 empty. Complete pull inventory: 29 PRs on page 1; page 2 empty; 15 open, 14 closed. No branches were deleted. Current integration head is black-box-bihar-news at 59022ed4dd20513ccd2a00b38c6e8b822057e938 (2026-11-07 10:07:10Z, “ui: unify transient search and reader glass controls”), parent 16eb549c3235b321ecf7ef1c5c5270817e336489; compare against main: 103 ahead / 0 behind. It remains the freshest coherent owner-selected consolidation line and contains the corrected background-only Prismal capture, Reader/Search controls, feed-only compact navigation, adaptive contrast safeguards, Saved/Stats glass controls, Quiz chrome and read-only stacked CI. No PR from this branch to main exists; no merge or Release publication was performed.

### Complete branch-head inventory

| Branch | Head SHA | Commit time (UTC) | Latest commit message | PR / disposition |
|---|---|---|---|---|
| black-box-bihar-news | 59022ed4dd20513ccd2a00b38c6e8b822057e938 | 2026-11-07 10:07:10 | ui: unify transient search and reader glass controls | Active integration branch; base of open #11, #25–#27, #29 |
| brrrNEWS | e6fcfc25a4c8d5f363ced58f313552120aa7ff96 | 2026-11-07 04:13:30 | ui: consolidate lightweight supporting glass surfaces | Divergent sibling; open #24 targets it; not baseline |
| ci/read-only-pr-validation-isolated-release-write-2026-11-06 | 963118f0abe5f7e38f2aa66a49d4b8e74548660c | 2026-11-06 17:18:53 | ci: validate pull requests with read-only build permissions | Open #18; workflow intent reconciled into active branch |
| ci/stacked-pr-validation-2026-11-07 | 5dc3179361835b77fb3186a99d9b5791301463fd | 2026-11-06 21:41:27 | ci: restore Android SDK setup before sdkmanager | #20 merged into active branch only; source retained |
| docs/ai-agent-handoff-2026-11-06 | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | Same head as main; no active PR |
| feat/gs1-geography-editorial-coverage | ca66fa1c865514a86c655a4b6c7eaf16b4681f81 | 2026-11-06 10:53:35 | fix: refresh article card metadata when feed content changes | Open #6; useful editorial pieces already selectively represented |
| fix/atom-alternate-link-selection-2026-11-06 | cdf4c3846dde1c05894284e48b8f7d69cb7541ad | 2026-11-06 09:14:27 | fix: select Atom article alternate links deterministically | Open #12; policy/test already represented; do not merge whole branch |
| fix/cache-publication-date-integrity-2026-11-06 | be5151e674c2bd4fa9beb166fc38ddf622781f84 | 2026-11-06 14:26:32 | feat: expose publication date and publisher RSS provenance on cards | Open #11; current policies selectively represented; old base requires review |
| fix/early-startup-crash-diagnostics-2026-11-06 | 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890 | 2026-11-06 19:33:20 | fix: minimise shareable process-exit diagnostics | Open #16; active integration descends from this corrected baseline |
| fix/editorial-action-gate-blackbox-2026-11-07 | 9b70ed1c7c39bb511399c6284c7e8833f4672618 | 2026-11-07 09:02:50 | fix(editorial): preserve regulatory coverage amid entertainment filtering | Open #27; separate editorial change, not UI scope |
| fix/editorial-heatwave-bill-signals-2026-11-07 | 89464104e300f48da10e3e32e33715cbe2405084 | 2026-11-06 22:05:40 | Verify a specific parliamentary bill remains eligible for the GS II feed | Open #21; prior editorial work selectively represented; review separately |
| fix/editorial-policy-exception-guard-2026-11-07 | 1f7a3c2a7b74cc02cfee6fe451f4576feb291acf | 2026-11-07 04:46:47 | fix(editorial): match policy action and target as separate words | Open #24; based on brrrNEWS; not copied into UI branch |
| fix/editorial-signal-count-test | 4738690d6e7f6c7c02a621742f25d5ebf1d631fb | 2026-11-06 05:16:32 | test: align economy signal count with repo-rate mapping | #2 merged into main |
| fix/editorial-token-boundaries-2026-11-06 | 0045c24637f162f1c13dceb1f8878f42f6eb15fd | 2026-11-06 02:34:28 | fix: tighten UPSC editorial signal matching | #1 merged into main |
| fix/navigation-icon-imports-2026-11-06 | fa8868940c08ed5b4a9def014a850e8a9dcb3490 | 2026-11-06 06:27:06 | fix: use supported calendar navigation glyph | #5 merged into main |
| fix/parliamentary-syllabus-signal-gate-2026-11-07 | 9d754953a29c0355ce55306ce4fd5463193c9dbb | 2026-11-07 03:49:44 | test: preserve Parliament bill headline variants | #23 merged into active branch only |
| fix/publisher-redirect-allowlist-2026-11-06 | 83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306 | 2026-11-06 12:15:39 | Validate RSS redirects against publisher allowlists | #15 closed unmerged; superseded by newer active redirect guards |
| fix/quiz-option-selected-semantics-compile-2026-11-07 | 6f9b3c1e1a484233721863d0b736734c0d282cd5 | 2026-11-07 08:12:10 | fix(quiz): qualify selected semantics receiver | #28 merged into active branch only |
| fix/rss-image-host-allowlist-2026-11-07 | 7a51c9c2c40aa89997db55194a6db3d0d65ac95d | 2026-11-07 06:28:46 | test(reader): cover publisher-only image resources | Open #25; publisher asset/privacy reconciliation pending |
| fix/rss-tracking-variant-dedup-2026-11-06 | 2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b | 2026-11-06 16:42:29 | fix: deduplicate RSS tracking URL variants | Open #17; URL identity policy already represented |
| fix/search-glass-theme-capture-2026-11-06 | c514eff3d5fd266ce6b6ecec51a141055e5fa52e | 2026-11-06 07:44:03 | fix: capture Search glass tint before draw callback | #10 merged into main |
| fix/startup-crash-diagnostics-feed-jank-2026-11-06 | 5da4e789b0f49c0eefda024410ade34c1e0f2a7c | 2026-11-06 07:39:53 | fix: diagnose launch crashes and move feed indexing off main | #9 merged into main; superseded by newer startup safeguards |
| fix/startup-stability-performance-2026-11-06 | 654e660347de692ef88531dfa49c610269d69671 | 2026-11-06 07:18:06 | fix: harden cold start and reduce feed rendering workload | #7 merged into main |
| main | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | Default branch; unchanged |
| perf/bounded-read-history-2026-11-07 | 910178af3f595993557533cb65d03e714bcd7760 | 2026-11-06 23:53:04 | Verify legacy markers survive recent-history retention | Open #22 stacked on #8; only new markers bounded; legacy unordered markers preserved |
| perf/off-main-bookmark-persistence-2026-11-07 | a64857b5efa62ea053c0c96e5f374fd35924b340 | 2026-11-07 09:05:25 | perf(storage): move bookmark mutations off the UI thread | Open #29; exact one-commit change selectively ported below |
| perf/off-main-session-persistence-2026-11-06 | ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a | 2026-11-06 10:50:31 | perf: move read-marker persistence off main thread | Open #8; policy selectively represented |
| privacy/webview-publisher-subresource-gate-2026-11-07 | b8d2ef9ff2413c378347986d00961c401dfa771d | 2026-11-07 07:27:22 | privacy: block third-party publisher reader subresources | Open #26; one MainActivity hunk plus policy/tests; separate security review |
| redesign/v0.3-search-reader | 654cc89dee4f93fea98bb76df9d8cf88abf2f0d8 | 2026-11-03 15:31:48 | fix: make selection decoding fallback valid JSON | Stale; 197 commits behind active; not baseline |
| ui/foreground-adaptive-glass-contrast-2026-11-06 | 3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264 | 2026-11-06 10:56:16 | ui: restore low-cadence foreground adaptive glass contrast | Open #13; active implementation uses one 8×8 probe at 4s/6s, not its faster cadence |
| ui/liquid-glass-performance-2026-11-06 | 2e9c7f62ca65a899e18d9026e1535392835b3724 | 2026-11-06 06:25:39 | fix: import bottom navigation glyphs | #4 closed unmerged; stale divergent checkpoint |
| ui/liquid-glass-system-2026-11-07 | 1f7cd2e5ddc5d169d1443035a8334538e44efc40 | 2026-11-06 20:11:36 | docs(reader): record native selection collision guard | #19 merged into active branch only; source retained |
| ui/liquid-glass-system-continuation-2026-11-07 | f031bf21229f43a992384cc23fa0f4da57e547a1 | 2026-11-07 03:18:34 | fix: finish syllabus metadata and thin-film feed eligibility | Earlier checkpoint; 18 commits behind active |

### Branch comparison and dependency decisions

- black-box-bihar-news is 103 ahead / 0 behind main; PR #16 is an ancestor (active is 46 commits ahead of its head), PR #20 is an ancestor (active is 44 commits ahead), and PR #19/#23/#28 are ancestors. Corrected background-only Prismal capture and read-only workflow are already present.
- brrrNEWS is divergent from active: 4 ahead / 9 behind, merge base e411c394e8d48b3f547b6d6fb9039a8935bd05eb. Its supporting-glass files match the already-integrated implementation; its app-label/workflow/build metadata changes are not adopted. PR #24 remains separate editorial work.
- PR #29 bookmark persistence is 1 ahead / 2 behind active, merge base 6f9b3c1e1a484233721863d0b736734c0d282cd5. Only its four-file bookmark delta was ported after checking that current MainActivity still has the same synchronous callback and LocalStore still has the same synchronous read/modify/write. No branch merge or reset was used.
- PR #27 editorial gate is 5 ahead / 4 behind, merge base 3a08bb95b0f9a0fed8ec11dae055f0ac2817a523; remains separate.
- PR #26 WebView subresource gate is 1 ahead / 4 behind, merge base 3a08bb95b0f9a0fed8ec11dae055f0ac2817a523; its shouldInterceptRequest change is an initial-request boundary and does not cover redirect hops, so retain that caveat for a separate security review.
- PR #25 publisher asset gate is 7 ahead / 5 behind, merge base 2e2c15f5189ec6c60fa1f07f384bce4aa1734ea0; it changes ArticleReaderRepository, NewsRepository and MainActivity image handling. Do not combine with PR #26 or copy old MainActivity wholesale.
- PR #24 is 9 ahead / 9 behind active, merge base e411c394e8d48b3f547b6d6fb9039a8935bd05eb; its policy regex/test changes are separate from the UI branch.
- PR #18 workflow proposal is 1 ahead / 103 behind active; its read-only PR-validation and isolated publish intent is already reconciled in the active workflow, so do not overwrite the current workflow.
- PR #11 is 3 ahead / 103 behind; current cache/date/provenance policy files already contain its selectively ported behaviour.
- PR #21 is 11 ahead / 103 behind; current editorial coverage already contains selected heat-wave/bill signals, but its head remains separate.
- PR #22 is 9 ahead / 116 behind; only the new ordered 5,000-marker window is bounded, while old unordered legacy markers remain preserved.
- PR #6 is 10 ahead / 116 behind; selected GS I Geography and editorial tests are already represented; do not merge its older MainActivity.
- PR #8 is 3 ahead / 116 behind; read/session/quiz persistence policies are already represented.
- PR #12 is 1 ahead / 116 behind; Atom alternate-link selection is already represented.
- PR #13 is 1 ahead / 116 behind; its 1.5s/2.5s probe cadence is not copied. Active probe remains one 8×8 source at 4s Android 12+ / 6s older, paused during feed motion and non-home/background.
- PR #17 is 1 ahead / 103 behind; URL identity/dedupe policy is already represented.
- Old ui/liquid-glass-performance is 3 ahead / 121 behind active; redesign/v0.3-search-reader is 197 behind active. Neither is a baseline.

### Actions queue, latest run and Release state

- Actions inventory is fully paginated: 214 runs (100 + 100 + 14; page 4 empty). No queued or in-progress runs.
- Latest natural run #214, push on exact active SHA 59022ed4dd20513ccd2a00b38c6e8b822057e938, completed successfully. Build job #128373870121 passed checkout, Java, cached signing key, Android SDK/platform, Gradle, four WordNet-builder Python tests, offline database generation (151,456 unique words), :app:testDebugUnitTest, :app:assembleDebug and artifact upload. Release/publish job skipped. Full 202,824-character job log inspected; no build/test failure. Compiler warnings are non-blocking deprecations.
- Artifact 13570367489, CurrentLens-v0.2.8-stability-debug-apk, 39,090,275 bytes, SHA-256 1b3635ef1a23d51054550bd4fe397eb6fbff640e2ce9f081f596d4559accc108, expires 21 Nov 2026 10:09:04Z.
- Latest published Release remains v0.2.0-build-116; no Release was created by #214.
- Current .github/workflows/build-apk.yml is read-only for build/test, has natural push and pull_request triggers for main/PR #16/black-box, and isolates contents: write to the main-push publish job. No PR-to-main validation claim is made because main's base workflow is not this branch's workflow.

### PR #29 selective bookmark-I/O port

The exact PR #29 source commit a64857b5efa62ea053c0c96e5f374fd35924b340 was reviewed as a one-commit delta, not merged. Its natural PR run #212 passed the WordNet builder, :app:testDebugUnitTest, :app:assembleDebug and artifact upload; source artifact 13569115360 was 39,084,383 bytes. The integration below keeps the complete bookmark set (no cap/eviction), moves the SharedPreferences read/copy/mutation/write to Dispatchers.IO, serializes mutations with a dedicated Mutex, updates Compose bookmark state after the serialized operation, and adds pure policy tests for add/remove/blank/reversible toggles. The source branch and PR remain untouched. This change adds no glass surface, capture, sampler/readback, image decode, shader, dependency or network request; actual interaction/frame-time/battery gains are not measured.



## Live consolidation and CI audit — 7 November 2026, before Appearance preview batch

- Complete remote branch enumeration: 33 branches on page 1, page 2 empty. Complete PR enumeration: 15 open and 14 closed. No branch was created or deleted during this audit.
- Owner-selected active branch remains `black-box-bihar-news` at `f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb` (2026-11-07 10:14:38Z, “perf(storage): move bookmark mutations off the UI thread”). Comparison: 104 ahead / 0 behind `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; 47 ahead / 0 behind PR #16's corrected stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. The active head is the direct descendant of the current consolidation history; no newer relevant UI branch was found.
- The 33-branch inventory remains the table above; the only updated remote head since that table was written is PR #27's `fix/editorial-action-gate-blackbox-2026-11-07@33ee2194b51c07b7f00ef9f58b1d82444a050678` (commit “docs(editorial): document cross-topic signal false positive”, 11:00:46Z). Its compare against active is 9 ahead / 5 behind, merge base `3a08bb95b0f9a0fed8ec11dae055f0ac2817a523`; its full diff touches only UpscEngine/UpscFeedPolicy, their tests and editorial documentation. It is not a UI baseline.
- `brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96` remains divergent at 4 ahead / 10 behind (merge base `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`). The shared supporting-glass implementation is already present on the active line; do not port its app-label, Gradle metadata or workflow changes. PR #24 remains based on that sibling.
- PR #29 bookmark persistence remains open on its source branch; its reviewed one-commit delta is present on active as a selective port, not a branch merge. PR #25 and #26 remain separate publisher-resource/security changes; PR #27 remains editorial-only. PR #16 remains open on main but is an ancestor; PR #19/#20/#23/#28 are closed/merged into this integration branch only. PR #18's read-only workflow design is reconciled in active. All source branches remain intact.
- Actions history: 217 runs fully paginated (100 + 100 + 17): 129 success, 54 failure, 33 cancelled and one in progress (#217 on PR #27's editorial head); no queued runs. Active-branch run #215 passed on exact SHA `f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb`. The build job completed WordNet generation (151,456 unique words), `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload. Artifact `13570219824`, 39,092,836 bytes, SHA-256 `80f0a63e68782348368a0455b6b51e0a5edccb4e4abf3cbc42b7ef5a363f5585`; publish job skipped. The full 202,422-character job log was inspected; only non-blocking deprecation warnings remain.
- Latest published Release remains `v0.2.0-build-116`. Active workflow blob `6cef6ca19f3babd331c9deb59be9da0067c6fa66` keeps build/test read-only and isolates Release write permission to a main-push-only job. Main's workflow blob `37197497288d9f0a60f4d1cfa8453e3dee52a902` is still push-only; no PR-to-main validation claim is made.
- No main update, merge, Release publication, workflow edit, manual dispatch/rerun, source-branch mutation or branch deletion occurred in this audit.


## Live branch, PR and CI audit — 7 November 2026, 17:27 IST

Repository/default: `Batmancpu/CurrentLens`, default `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`. The full remote branch endpoint returned **36 branches** on page 1 and 0 on page 2. Pull requests: **18 open** and **14 closed**. The complete branch-head inventory is recorded below; no branch was created or deleted in this audit.

### Authoritative integration baseline

- Owner-selected integration remains `black-box-bihar-news@ee82dfde140aeb0d1ca80146379a0a2818c695df`, latest commit 2026-11-07 11:03:39Z, “ui: consolidate Appearance preview into one Prismal surface”. GitHub compare: **105 ahead / 0 behind main**, merge base exactly main; **48 ahead / 0 behind PR #16** at `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. PR #16's corrected background-only Prismal/stability work is an ancestor. The active branch remains the only coherent owner-selected integration baseline; newer child branches are selective-port candidates, not replacement baselines.
- PR #31 `perf/off-main-appearance-preferences-2026-11-07@bc48daec98ff076c9db3c2c69084d9b08cd0bbce` is **5 ahead / 0 behind** active, merge base exactly active. Its 5-commit diff touches only `AppearancePreferencesPolicy.kt`, `LocalStore.kt`, `MainActivity.kt` and the policy test; this exact delta is selectively ported by the current commit. PR #31 remains open and its source branch is untouched.
- PR #32 `perf/bounded-rss-fetch-concurrency-2026-11-07@8c56c98c83cc69451f128aa259df86a8ce28f43a` is **2 ahead / 0 behind** active. It bounds simultaneous RSS fetch/parse work to four; separate non-UI performance batch, not included in this commit.
- PR #30 `a11y/48dp-icon-targets-2026-11-07@25107ce905b70bde1a41282ffd1d606819ebf58d` is **3 ahead / 1 behind** active, merge base `f7e10ab13e5098bd51f8c3ff3c03189ef0b72fbb`. Its three-file delta changes two icon actions plus a pure 48dp policy/test; it must be reapplied against current MainActivity rather than fast-forwarding its older branch. Natural run #218 passed its exact source head.
- PR #27 is **23 ahead / 6 behind** active (merge base `3a08bb95b0f9a0fed8ec11dae055f0ac2817a523`), editorial-only; run #233 passed its own head, not the current base. Reconcile and retest before any port.
- PR #26 is **1 ahead / 6 behind** active (same merge base `3a08bb95...`); its WebView `shouldInterceptRequest` guard covers initial subresource URLs but explicitly does not cover redirect hops. Do not claim complete subresource isolation or port without a redirect-aware security review.
- PR #25 is **7 ahead / 7 behind** active (merge base `2e2c15f5189ec6c60fa1f07f384bce4aa1734ea0`); publisher image host restrictions touch `ArticleReaderRepository`, `NewsRepository` and `MainActivity`. Keep separate until source-host coverage, redirect and cache compatibility are reviewed together.
- PR #29 is **1 ahead / 4 behind** active (merge base `6f9b3c1e1a484233721863d0b736734c0d282cd5`); its bookmark IO/mutex/preservation behaviour is already represented by a reviewed selective port on active.
- PR #24 is **9 ahead / 11 behind** active (merge base `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`) and targets divergent `brrrNEWS`; separate editorial-policy work.
- PR #22 is **9 ahead / 118 behind** active and is based on PR #8; only new ordered read markers are bounded, while old unordered legacy markers remain preserved. PR #21 (#11 ahead / 105 behind) is editorial-only and selected bill/heat-wave signals are already represented. PR #18 workflow intent, PR #17 URL-identity/deduplication, PR #11 cache/date/provenance, PR #12 Atom alternate links, PR #8 off-main session persistence and PR #6 selected GS I Geography work are already selectively represented on active.
- PR #13's faster 1.5s/2.5s luminance cadence is not copied; active retains one shared 8×8 probe at 4s Android 12+ / 6s older, paused during feed scrolling and outside foreground home. `brrrNEWS` is divergent (**4 ahead / 11 behind**, merge base `e411c394...`); its app/build/workflow differences make it unsuitable as baseline. Old `ui/liquid-glass-performance` is **3 ahead / 123 behind**, `ui/liquid-glass-system-continuation` is **21 behind**, and `redesign/v0.3-search-reader` is **199 behind**; none supersedes active. PR #15's redirect policy is superseded by newer active redirect guards.

### Complete remote branch-head inventory

| Branch | Head SHA | Latest commit (UTC) | Latest message | Open PR relationship / disposition |
|---|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02:12 | test(a11y): cover minimum icon action target | open PR #30 head |
| `black-box-bihar-news` | `ee82dfde140aeb0d1ca80146379a0a2818c695df` | 2026-11-07 11:03:39 | ui: consolidate Appearance preview into one Prismal surface | active; base of open #11, #25–#27, #29–#32 |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30 | ui: consolidate lightweight supporting glass surfaces | open PR #24 base; divergent sibling |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 | ci: validate pull requests with read-only build permissions | open PR #18 head |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 | ci: restore Android SDK setup before sdkmanager | PR #20 merged into active only; source retained |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | PR #14 closed unmerged; same head as main |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 | fix: refresh article card metadata when feed content changes | open PR #6 head |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 | fix: select Atom article alternate links deterministically | open PR #12 head; policy selectively represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 | feat: expose publication date and publisher RSS provenance on cards | open PR #11 head; selected policies represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 | fix: minimise shareable process-exit diagnostics | open PR #16 head; active is 48 commits ahead |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `4ee9233e6a18c26163b69a6b40ac8d8b99b1fae9` | 2026-11-07 11:19:28 | docs(editorial): format specific-signal recovery fixtures as a table | open PR #27 head |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 | Verify a specific parliamentary bill remains eligible for the GS II feed | open PR #21 head; selected signals already represented |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47 | fix(editorial): match policy action and target as separate words | open PR #24 head; based on brrrNEWS |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 | test: align economy signal count with repo-rate mapping | PR #2 merged |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 | fix: tighten UPSC editorial signal matching | PR #1 merged |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 | fix: use supported calendar navigation glyph | PR #5 merged |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 | test: preserve Parliament bill headline variants | PR #23 merged into active only; source retained |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 | Validate RSS redirects against publisher allowlists | PR #15 closed unmerged; superseded by newer guards |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10 | fix(quiz): qualify selected semantics receiver | PR #28 merged into active only; source retained |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46 | test(reader): cover publisher-only image resources | open PR #25 head |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 | fix: deduplicate RSS tracking URL variants | open PR #17 head; policy represented |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 | fix: capture Search glass tint before draw callback | PR #10 merged |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 | fix: diagnose launch crashes and move feed indexing off main | PR #9 merged |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 | fix: harden cold start and reduce feed rendering workload | PR #7 merged |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | default branch |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 | Verify legacy markers survive recent-history retention | open PR #22 head; based on PR #8 |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29:26 | docs: record bounded RSS fetch review and CI state | open PR #32 head; 2 commits ahead of active |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11:45 | fix(startup): keep appearance snapshot API module-internal | open PR #31 head; 5 commits ahead of active |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05:25 | perf(storage): move bookmark mutations off the UI thread | open PR #29 head; selective port already present |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 | perf: move read-marker persistence off main thread | open PR #8 head; policy selectively represented |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27:22 | privacy: block third-party publisher reader subresources | open PR #26 head |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 | fix: make selection decoding fallback valid JSON | stale; 199 commits behind active |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 | ui: restore low-cadence foreground adaptive glass contrast | open PR #13 head; faster cadence rejected |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 | fix: import bottom navigation glyphs | PR #4 closed unmerged; 3 ahead / 123 behind active |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 | docs(reader): record native selection collision guard | PR #19 merged into active only; source retained |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 | fix: finish syllabus metadata and thin-film feed eligibility | stale checkpoint; 21 commits behind active |

### Natural Actions verification and workflow state

- Actions history was fully paginated: **235 runs** (100 + 100 + 35): 136 success, 58 failure, 41 cancelled; no queued, requested, waiting or in-progress runs.
- Latest overall run #235 / ID `42368389290` passed on PR #32's exact head `8c56c98c83cc69451f128aa259df86a8ce28f43a`. Build job `128391285625`: checkout, Java, signing-key cache, Android SDK/platform, Gradle, WordNet builder, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload all succeeded; Release job skipped. Artifact `13572319837`, 39,096,771 bytes, SHA-256 `d8e328e3e31a8160eb8d0949d4abcfb8efb79de162a13b04bf027f6ec8fd8888`, expires 21 Nov 2026 11:31:55Z.
- Exact active predecessor run #219 / ID `42366560250` passed on `ee82dfde140aeb0d1ca80146379a0a2818c695df`; build job `128385741444` completed all steps, generated 151,456 WordNet words (50,950,144 bytes), passed JVM tests and assembled/uploaded the APK. Artifact `13572515005`, 39,095,662 bytes, SHA-256 `4982f5c20478479e41fdb957571c9d7cea6a6edac25e3e1bd4702aedbbe9db05`. Release job skipped.
- PR #31 exact source run #227 / ID `42367156421` passed on `bc48daec98ff076c9db3c2c69084d9b08cd0bbce`; build job `128387512638` passed all steps. Artifact `13571813251`, 39,096,319 bytes, SHA-256 `4ef3cb151f40045128b16b7a47a19dcdb1decc9674eabbf9a66929d37dd15497`.
- PR #30 exact source run #218 / ID `42366460318` passed on `25107ce905b70bde1a41282ffd1d606819ebf58d`; artifact `13572141319`, 39,093,083 bytes, SHA-256 `2f4347920f6395c829c58fd9acb263fc990ec7b8162e6917593f24f0f1ca1fdd`.
- Full decoded logs were inspected for #219, #227, #218 and #235 (1515, 1531, 1535 and 1534 lines respectively). Each build log ends with `BUILD SUCCESSFUL`; only non-blocking Node.js 20/action-runtime, `punycode` and `url.parse()` deprecation warnings were found. The source runs validate their exact source heads only; the new active commit must receive its own natural run.
- Active workflow blob `6cef6ca19f3babd331c9deb59be9da0067c6fa66`: read-only build/test, push and pull_request triggers for main/PR #16/black-box, isolated main-push-only Release write. Main workflow blob `37197497288d9f0a60f4d1cfa8453e3dee52a902`: push-only and workflow-wide `contents: write`; no PR-to-main validation claim. Latest published Release remains `v0.2.0-build-116`.
- No main write, workflow edit, manual dispatch/rerun, Release publication, PR merge, source-branch mutation or branch deletion occurred. OPPO device launch/visual/accessibility/performance, Perfetto, GPU, memory, thermal and battery remain **UNVERIFIED**.


## Live branch graph audit — 7 November 2026, 20:30 IST

**Repository:** Batmancpu/CurrentLens  
**Default branch:** main@73b8a0d1ba33503fede02db2c3c0af082d724a12  
**Owner-selected integration branch:** black-box-bihar-news@48b54808807f5b15e637b6177524fd923741e1f7 — 107 ahead / 0 behind main; 50 ahead / 0 behind PR #16 at 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890. This is the only branch to receive further consolidation/UI commits.  
**Inventory:** 37 remote branches (page 1 complete, page 2 empty), 19 open PRs and 14 closed PRs. The table records every live ref's head, latest commit timestamp/message and PR role.

| Remote branch | Head SHA | Latest commit UTC | Latest message | PR role |
|---|---|---|---|---|
| a11y/48dp-icon-targets-2026-11-07 | 25107ce905b70bde1a41282ffd1d606819ebf58d | 2026-11-07T11:02:12Z | test(a11y): cover minimum icon action target | head #30 |
| black-box-bihar-news | ec86b2fef2f5e86c3b95f2a2a2269bfef6158053 | 2026-11-07T15:09:39Z | perf(ui): unify Reader and Quiz transient glass chrome | active; base #11,#25,#26,#27,#29,#30,#31,#32,#33 |
| brrrNEWS | e6fcfc25a4c8d5f363ced58f313552120aa7ff96 | 2026-11-07T04:13:30Z | ui: consolidate lightweight supporting glass surfaces | base #24; divergent sibling |
| ci/read-only-pr-validation-isolated-release-write-2026-11-06 | 963118f0abe5f7e38f2aa66a49d4b8e74548660c | 2026-11-06T17:18:53Z | ci: validate pull requests with read-only build permissions | head #18 |
| ci/stacked-pr-validation-2026-11-07 | 5dc3179361835b77fb3186a99d9b5791301463fd | 2026-11-06T21:41:27Z | ci: restore Android SDK setup before sdkmanager | head #20; closed/merged into active branch |
| docs/ai-agent-handoff-2026-11-06 | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 2026-11-06T11:16:35Z | Correct icon artwork accessibility name to brrrNews | same as main; no PR |
| feat/gs1-geography-editorial-coverage | ca66fa1c865514a86c655a4b6c7eaf16b4681f81 | 2026-11-06T10:53:35Z | fix: refresh article card metadata when feed content changes | head #6 |
| fix/atom-alternate-link-selection-2026-11-06 | cdf4c3846dde1c05894284e48b8f7d69cb7541ad | 2026-11-06T09:14:27Z | fix: select Atom article alternate links deterministically | head #12 |
| fix/cache-publication-date-integrity-2026-11-06 | be5151e674c2bd4fa9beb166fc38ddf622781f84 | 2026-11-06T14:26:32Z | feat: expose publication date and publisher RSS provenance on cards | head #11 |
| fix/early-startup-crash-diagnostics-2026-11-06 | 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890 | 2026-11-06T19:33:20Z | fix: minimise shareable process-exit diagnostics | head #16; corrected Prismal/stability parent |
| fix/editorial-action-gate-blackbox-2026-11-07 | 627014500a7a983377b7c2fc82ca0cd238f776b9 | 2026-11-07T13:50:45Z | test(editorial): require a syllabus issue beyond institution names | head #27 |
| fix/editorial-heatwave-bill-signals-2026-11-07 | 89464104e300f48da10e3e32e33715cbe2405084 | 2026-11-06T22:05:40Z | Verify a specific parliamentary bill remains eligible for the GS II feed | head #21 |
| fix/editorial-policy-exception-guard-2026-11-07 | 1f7a3c2a7b74cc02cfee6fe451f4576feb291acf | 2026-11-07T04:46:47Z | fix(editorial): match policy action and target as separate words | head #24 |
| fix/editorial-signal-count-test | 4738690d6e7f6c7c02a621742f25d5ebf1d631fb | 2026-11-06T05:16:32Z | test: align economy signal count with repo-rate mapping | closed/merged #2 |
| fix/editorial-token-boundaries-2026-11-06 | 0045c24637f162f1c13dceb1f8878f42f6eb15fd | 2026-11-06T02:34:28Z | fix: tighten UPSC editorial signal matching | closed/merged #1 |
| fix/navigation-icon-imports-2026-11-06 | fa8868940c08ed5b4a9def014a850e8a9dcb3490 | 2026-11-06T06:27:06Z | fix: use supported calendar navigation glyph | closed/merged #5 |
| fix/parliamentary-syllabus-signal-gate-2026-11-07 | 9d754953a29c0355ce55306ce4fd5463193c9dbb | 2026-11-07T03:49:44Z | test: preserve Parliament bill headline variants | closed/merged #23 |
| fix/publisher-redirect-allowlist-2026-11-06 | 83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306 | 2026-11-06T12:15:39Z | Validate RSS redirects against publisher allowlists | closed #15; safe policy present in active line |
| fix/quiz-option-selected-semantics-compile-2026-11-07 | 6f9b3c1e1a484233721863d0b736734c0d282cd5 | 2026-11-07T08:12:10Z | fix(quiz): qualify selected semantics receiver | closed/merged #28 |
| fix/resolve-relative-atom-links-2026-11-07 | c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4 | 2026-11-07T14:59:24Z | test(rss): correct relative xml base fixture | head #33 |
| fix/rss-image-host-allowlist-2026-11-07 | 7a51c9c2c40aa89997db55194a6db3d0d65ac95d | 2026-11-07T06:28:46Z | test(reader): cover publisher-only image resources | head #25 |
| fix/rss-tracking-variant-dedup-2026-11-06 | 2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b | 2026-11-06T16:42:29Z | fix: deduplicate RSS tracking URL variants | head #17 |
| fix/search-glass-theme-capture-2026-11-06 | c514eff3d5fd266ce6b6ecec51a141055e5fa52e | 2026-11-06T07:44:03Z | fix: capture Search glass tint before draw callback | closed/merged #10 |
| fix/startup-crash-diagnostics-feed-jank-2026-11-06 | 5da4e789b0f49c0eefda024410ade34c1e0f2a7c | 2026-11-06T07:39:53Z | fix: diagnose launch crashes and move feed indexing off main | closed/merged #9 |
| fix/startup-stability-performance-2026-11-06 | 654e660347de692ef88531dfa49c610269d69671 | 2026-11-06T07:18:06Z | fix: harden cold start and reduce feed rendering workload | closed/merged #7 |
| main | 73b8a0d1ba33503fede02db2c3c0af082d724a12 | 2026-11-06T11:16:35Z | Correct icon artwork accessibility name to brrrNews | default; base #6,#8,#12,#13,#16,#17,#18,#21 |
| perf/bounded-read-history-2026-11-07 | 910178af3f595993557533cb65d03e714bcd7760 | 2026-11-06T23:53:04Z | Verify legacy markers survive recent-history retention | head #22 |
| perf/bounded-rss-fetch-concurrency-2026-11-07 | 8c56c98c83cc69451f128aa259df86a8ce28f43a | 2026-11-07T11:29:26Z | docs: record bounded RSS fetch review and CI state | head #32 |
| perf/off-main-appearance-preferences-2026-11-07 | bc48daec98ff076c9db3c2c69084d9b08cd0bbce | 2026-11-07T11:11:45Z | fix(startup): keep appearance snapshot API module-internal | head #31 |
| perf/off-main-bookmark-persistence-2026-11-07 | a64857b5efa62ea053c0c96e5f374fd35924b340 | 2026-11-07T09:05:25Z | perf(storage): move bookmark mutations off the UI thread | head #29 |
| perf/off-main-session-persistence-2026-11-06 | ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a | 2026-11-06T10:50:31Z | perf: move read-marker persistence off main thread | head #8; base #22 |
| privacy/webview-publisher-subresource-gate-2026-11-07 | b8d2ef9ff2413c378347986d00961c401dfa771d | 2026-11-07T07:27:22Z | privacy: block third-party publisher reader subresources | head #26 |
| redesign/v0.3-search-reader | 654cc89dee4f93fea98bb76df9d8cf88abf2f0d8 | 2026-11-03T15:31:48Z | fix: make selection decoding fallback valid JSON | no PR; stale |
| ui/foreground-adaptive-glass-contrast-2026-11-06 | 3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264 | 2026-11-06T10:56:16Z | ui: restore low-cadence foreground adaptive glass contrast | head #13 |
| ui/liquid-glass-performance-2026-11-06 | 2e9c7f62ca65a899e18d9026e1535392835b3724 | 2026-11-06T06:25:39Z | fix: import bottom navigation glyphs | closed #4; earlier #3 merged from a prior head |
| ui/liquid-glass-system-2026-11-07 | 1f7cd2e5ddc5d169d1443035a8334538e44efc40 | 2026-11-06T20:11:36Z | docs(reader): record native selection collision guard | closed/merged #19 |
| ui/liquid-glass-system-continuation-2026-11-07 | f031bf21229f43a992384cc23fa0f4da57e547a1 | 2026-11-07T03:18:34Z | fix: finish syllabus metadata and thin-film feed eligibility | no PR; superseded checkpoint |

### Pull-request state and source treatment

**Open PRs (19):** #33, #32, #31, #30, #29, #27, #26, #25, #24, #22, #21, #18, #17, #16, #13, #12, #11, #8 and #6. All remain open/draft; none was merged or retargeted in this audit.

**Closed PRs (14):** #28, #23, #20, #19, #10, #9, #7, #5, #3, #2 and #1 were merged into their stated targets; #15, #14 and #4 were closed without merge. PRs #19/#20/#23/#28 are represented in black-box-bihar-news only, not main.

### Freshness and comparison decisions

- black-box-bihar-news is a direct descendant of PR #16's corrected stability/Prismal baseline; no older UI branch supersedes it. Its latest exact natural push validation before this implementation was run #239 on 48b54808807f5b15e637b6177524fd923741e1f7.
- brrrNEWS is a divergent sibling (4 ahead / 13 behind black-box-bihar-news). Its useful SupportingGlassCard/GlassSurfacePolicy, SavedFeed memoization and Reader-position IO changes are already represented in the selected branch. Its app-label, workflow and branch-documentation changes are not adopted. PR #24 remains based on brrrNEWS and is not part of this UI branch.
- ui/liquid-glass-system-continuation is an older checkpoint (0 ahead / 23 behind selected branch). ui/liquid-glass-performance and redesign/v0.3-search-reader remain stale and are not used as baselines.
- PR #30's 48dp target policy, PR #31's off-main appearance snapshot and PR #29's serialized off-main bookmark persistence have been selectively represented in black-box-bihar-news; source branches and PRs remain untouched.
- PR #32's bounded RSS concurrency and PR #33's relative Atom-link resolution are independent non-UI changes. PR #33's exact head passed run #240, but that run does not validate the selected branch. Neither change is copied in this UI pass.
- PRs #25/#26 remain separate publisher-resource/security proposals; do not port until redirect, asset-host and third-party subresource boundaries are reviewed together. PR #27 and other editorial/parser branches are not UI baselines.
- PR #18/#20's compatible read-only build and isolated main-push Release-write workflow is present on the selected branch. The main workflow remains push-only; a future PR to main must not be described as PR-to-main CI validated until main's workflow changes.

### Current Actions and Release evidence

- Actions history: 240 runs, fully paginated; 141 success, 58 failure, 41 cancelled; queue empty.
- Run #239: success on exact selected-branch head 48b54808807f5b15e637b6177524fd923741e1f7. WordNet builder: 4 tests passed, 151,456 unique words generated; :app:testDebugUnitTest and :app:assembleDebug succeeded; artifact 13577371341, 39,096,477 bytes, SHA-256 e37f05a313fb96b959970579b86e6101b30256f202cec3db49d7a492521cd8d5.
- Run #240: success on PR #33 head c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4; WordNet generation, JVM tests, APK assembly and artifact upload succeeded; artifact 13579255941, 39,097,729 bytes. It is not a selected-branch run.
- Active workflow blob 6cef6ca19f3babd331c9deb59be9da0067c6fa66 has read-only build/test permissions and isolated main-push Release write. Main workflow blob 37197497288d9f0a60f4d1cfa8453e3dee52a902 remains push-only. Latest published Release is v0.2.0-build-116.
- No workflow was edited, no manual dispatch/rerun was used, no Release was published, no PR was merged/retargeted, and no remote branch was modified/deleted by this audit.


## 7 November 2026, 21:26 IST — supporting-material freshness and CI audit

- Re-enumerated all 37 remote branches (page 1 complete; page 2 empty), all 19 open and 14 closed PRs, current branch/default/PR16 refs, workflow, all Actions pages and Releases. No branch was deleted.
- `black-box-bihar-news@ec86b2fef2f5e86c3b95f2a2a2269bfef6158053` is 108 ahead / 0 behind `main@73b8a0d1ba33503fede02db2c3c0af082d724a12` and 51 ahead / 0 behind PR #16's corrected stability/Prismal head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. It remains the newest coherent owner-selected integration line; the current source is a direct descendant of PR #16 and includes the previously consolidated Reader, workflow and UI work.
- Comparison checks against the current head: `brrrNEWS` 4 ahead / 14 behind (diverged); `ui/liquid-glass-system-continuation` 0 ahead / 24 behind; `ui/liquid-glass-performance` 3 ahead / 126 behind; PR #13 adaptive contrast 1 ahead / 121 behind; PR #32 RSS concurrency 2 ahead / 3 behind; PR #33 relative Atom parsing 4 ahead / 1 behind; PR #25 image-host 7 ahead / 10 behind; PR #26 WebView subresource 1 ahead / 9 behind; PR #27 editorial gate 26 ahead / 9 behind; PR #24 policy guard 9 ahead / 14 behind; PR #29 bookmark IO 1 ahead / 7 behind; PR #30 48dp 3 ahead / 4 behind; PR #31 appearance IO 5 ahead / 3 behind. All are non-ancestor/diverged candidates, not automatic baselines.
- Useful PR #29/#30/#31 changes and PR #13's conservative adaptive-contrast policy are already represented by focused ports; brrrNEWS's supporting-card material is already in the integration line. PR #25/#26 remain unported pending a combined publisher asset-host/subresource review. PR #32/#33 are independent RSS performance/parser changes and were not mixed into this UI batch. PR #27/#24 are editorial and not UI baselines.
- PR #16 and #18 remain open/draft; #19/#20/#23/#28 are closed/merged into black-box-bihar-news only. No PR was merged or retargeted in this audit.
- Exact active-head run #241 passed on ec86b2fef2f5e86c3b95f2a2a2269bfef6158053; WordNet builder 4/4, 151,456 unique words, JVM tests, debug APK assembly and artifact upload succeeded. Artifact 13579222899, 39,094,929 bytes, SHA-256 d60b7e0d261c0ccac6bb5b407a642bd54183cca7abf1601b9fae541645bc838f. The next UI commit needs its own natural push run.
- Active branch workflow `6cef6ca19f3babd331c9deb59be9da0067c6fa66` has read-only build/test permissions and a separate main-push-only Release-write job. Main workflow `37197497288d9f0a60f4d1cfa8453e3dee52a902` remains push-only. Latest Release is `v0.2.0-build-116`; no Release publication, manual dispatch/rerun, main write, PR merge or source-branch mutation occurred.


## Live branch and PR freshness audit — 7 November 2026, 22:24 IST

- Default branch: `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`.
- Complete remote refs: **37** (page 1 returned all 37; page 2 empty). Open PRs: **20** — #34, #33, #32, #31, #30, #29, #27, #26, #25, #24, #22, #21, #18, #17, #16, #13, #12, #11, #8 and #6. Closed PRs: **14** — #28, #23, #20, #19, #15, #14, #10, #9, #7, #5, #4, #3, #2 and #1; page 2 empty.
- Selected line remains `black-box-bihar-news@818afb47eaadc685f133eaffc3efd199392627e4`, direct child of `ec86b2fef2f5e86c3b95f2a2a2269bfef6158053`; comparison: **109 ahead / 0 behind main**, **52 ahead / 0 behind PR #16**. It is also the exact head of open draft PR #34 (base `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`, 109 ahead / 0 behind). No other branch is a newer coherent UI baseline.
- Actions: all **243** runs paginated (100 + 100 + 43; page 4 empty): 144 success, 58 failure, 41 cancelled; queued 0, in-progress 0. Exact PR #34 run #243 and push run #242 passed on head 818afb47; artifacts #13580718014 (39,094,949 bytes) and #13580578348 (39,094,947 bytes). Build job steps passed WordNet generation, JVM tests, APK assembly and upload; publish jobs skipped. The log-archive endpoint returned a transient BlobNotFound response, so step summaries—not raw log text—are the available evidence.
- Active workflow blob `6cef6ca19f3babd331c9deb59be9da0067c6fa66`: read-only build/test and separate main-push-only Release write. Main workflow blob `37197497288d9f0a60f4d1cfa8453e3dee52a902` remains push-only. The exact PR #34 pull_request run #243 nevertheless occurred and passed using the branch workflow. Latest Release remains `v0.2.0-build-116`; no Release was published by #242/#243.

### Complete remote branch inventory

| Branch | Head SHA | Latest commit (UTC) | Latest subject | PR / disposition |
|---|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02Z | test(a11y): cover minimum icon action target | #30 open; represented |
| `black-box-bihar-news` | `818afb47eaadc685f133eaffc3efd199392627e4` | 2026-11-07 16:02Z | ui: refine supporting glass tone across dashboard and stats | #34 open/draft; active |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13Z | ui: consolidate lightweight supporting glass surfaces | #24 open; divergent sibling |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18Z | ci: validate pull requests with read-only build permissions | #18 open; workflow intent represented |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41Z | ci: restore Android SDK setup before sdkmanager | #20 merged into black-box only |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16Z | Correct icon artwork accessibility name to brrrNews | same head as main; no open PR |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53Z | fix: refresh article card metadata when feed content changes | #6 open |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14Z | fix: select Atom article alternate links deterministically | #12 open; policy represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26Z | feat: expose publication date and publisher RSS provenance on cards | #11 open; selective policies represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33Z | fix: minimise shareable process-exit diagnostics | #16 open; stability ancestor |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `627014500a7a983377b7c2fc82ca0cd238f776b9` | 2026-11-07 13:50Z | test(editorial): require a syllabus issue beyond institution names | #27 open; independent |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05Z | Verify a specific parliamentary bill remains eligible for the GS II feed | #21 open |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46Z | fix(editorial): match policy action and target as separate words | #24 open; based on brrrNEWS |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16Z | test: align economy signal count with repo-rate mapping | #2 merged |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34Z | fix: tighten UPSC editorial signal matching | #1 merged |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27Z | fix: use supported calendar navigation glyph | #5 merged |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49Z | test: preserve Parliament bill headline variants | #23 merged into black-box only |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15Z | Validate RSS redirects against publisher allowlists | #15 closed unmerged; policy present in active line |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12Z | fix(quiz): qualify selected semantics receiver | #28 merged into black-box only |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | 2026-11-07 14:59Z | test(rss): correct relative xml base fixture | #33 open |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28Z | test(reader): cover publisher-only image resources | #25 open; security review pending |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42Z | fix: deduplicate RSS tracking URL variants | #17 open; policy represented |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44Z | fix: capture Search glass tint before draw callback | #10 merged |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39Z | fix: diagnose launch crashes and move feed indexing off main | #9 merged |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18Z | fix: harden cold start and reduce feed rendering workload | #7 merged |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16Z | Correct icon artwork accessibility name to brrrNews | default branch |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53Z | Verify legacy markers survive recent-history retention | #22 open; policy represented with legacy caveat |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29Z | docs: record bounded RSS fetch review and CI state | #32 open; independent |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11Z | fix(startup): keep appearance snapshot API module-internal | #31 open; represented |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05Z | perf(storage): move bookmark mutations off the UI thread | #29 open; represented |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50Z | perf: move read-marker persistence off main thread | #8 open; reconciled policy |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27Z | privacy: block third-party publisher reader subresources | #26 open; security review pending |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31Z | fix: make selection decoding fallback valid JSON | no PR; stale |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56Z | ui: restore low-cadence foreground adaptive glass contrast | #13 open; conservative policy represented |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25Z | fix: import bottom navigation glyphs | #4 closed unmerged; stale sibling |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11Z | docs(reader): record native selection collision guard | #19 merged into black-box only |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18Z | fix: finish syllabus metadata and thin-film feed eligibility | no PR; 25 behind active branch |

### Dependency and port decisions

- **Already represented in black-box:** PR #19 Reader UI, #20 workflow, #23 parliamentary gate, #28 Quiz semantics; selective PR #11/#12/#17 feed policies; PR #8/#22 read-history reconciliation; PR #13 conservative 4s/6s adaptive contrast; PR #29 bookmark IO; PR #30 48dp target policy; PR #31 off-main appearance snapshot. Their source branches remain untouched.
- **Not adopted as baselines:** `brrrNEWS` is divergent (4 ahead / 15 behind; merge base `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`); `ui/liquid-glass-system-continuation` is 25 behind; `ui/liquid-glass-performance` and `redesign/v0.3-search-reader` are stale.
- **Independent remaining work:** PR #27 editorial action gate (26 ahead / 10 behind), PR #32 bounded RSS concurrency (2 ahead / 4 behind), and PR #33 relative Atom resolution (4 ahead / 2 behind) are not included in this Reader UI batch; the PR #34 owner comment asks for reconciliation before completion. PR #25 asset-host and #26 publisher subresource security branches remain held for one combined host/security review. PR #24 is based on divergent `brrrNEWS`.
- **No source branch was modified, merged, retargeted or deleted.** Only the selected `black-box-bihar-news` branch may receive this UI batch; no main write, workflow edit, manual dispatch/rerun or Release publication is allowed.


## Live branch freshness audit — 7 November 2026, 23:26 IST

**Repository and authoritative line**
- Default branch is `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; it is unchanged. The owner-selected active integration/UI branch is `black-box-bihar-news@ed6c94190296c8d7af400b80f6fe26f2252fafe1`, 110 commits ahead / 0 behind main and 53 ahead / 0 behind PR #16's corrected background-only Prismal/stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`. PR #34 is open/draft, base main, and remains the single consolidation PR.
- The branch is the freshest coherent UI/integration lineage: it contains the corrected background-only root capture, PR #19 Reader/Search/navigation work, the single read-only validation workflow, and the previously reconciled feed, persistence, editorial and accessibility changes. No sibling UI branch supersedes it.
- Complete inventory: 38 remote branches (one page, no further page), 21 open PRs and 14 closed PRs. The full head/commit/PR/comparison table below was captured against the pre-change active head `ed6c94190296c8d7af400b80f6fe26f2252fafe1`; the filter-control implementation is a direct child of that source. No branch was deleted or modified outside the selected integration branch.
- Open PRs: #34, #27, #35, #33, #32, #31, #30, #29, #25, #26, #24, #11, #13, #6, #17, #22, #21, #16, #18, #8 and #12. Closed PRs: #28, #23, #19, #20, #14, #15, #10, #9, #7, #5, #4, #3, #2 and #1.
- PR #35 is a separate direct child of the active head (6 ahead / 0 behind) containing bounded diagnostic-file reads; its exact-head natural run #251 passed, but it is not a UI baseline and was not integrated in this screen-sized pass. PR #27 is editorial-only and diverged 28 ahead / 11 behind. PR #32 and #33 both touch `NewsRepository.kt` and must be reconciled together. PR #25/#26 remain behind a combined publisher asset/subresource security review.
- PR #29/#30/#31 source branches are divergent but their bookmark IO, 48dp target and off-main appearance snapshot implementations are already represented in current code. PR #19/#20/#23/#28 changes are integrated into black-box only. PR #13's original faster adaptive sampler is not copied; the current shared 8×8 policy remains 4s/6s and pauses outside eligible foreground-home idle state.
- `brrrNEWS` is a divergent sibling (4 ahead / 16 behind); `ui/liquid-glass-performance` is 3 ahead / 128 behind; `ui/liquid-glass-system` is 40 behind; `ui/liquid-glass-system-continuation` is 26 behind; `redesign/v0.3-search-reader` is 204 behind. None is used as a baseline.

| Remote branch | Head SHA | Last commit (UTC) | Last commit message | PR relationship | Compare to active head |
|---|---|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02Z | test(a11y): cover minimum icon action target | #30 open | 3 ahead / 6 behind |
| `black-box-bihar-news` | `ed6c94190296c8d7af400b80f6fe26f2252fafe1` | 2026-11-07 17:00Z | fix(reader): pin chrome while Aa menu is open | #34 open; base main | current selected head |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13Z | ui: consolidate lightweight supporting glass surfaces | #24 base; divergent sibling | 4 ahead / 16 behind |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18Z | ci: validate pull requests with read-only build permissions | #18 open | 1 ahead / 110 behind |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41Z | ci: restore Android SDK setup before sdkmanager | #20 closed; represented | 0 ahead / 51 behind |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16Z | Correct icon artwork accessibility name to brrrNews | ref currently equals main; PR #14 closed | 0 ahead / 110 behind |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53Z | fix: refresh article card metadata when feed content changes | #6 open; editorial | 10 ahead / 123 behind |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14Z | fix: select Atom article alternate links deterministically | #12 open; parser delta represented selectively | 1 ahead / 123 behind |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26Z | feat: expose publication date and publisher RSS provenance on cards | #11 open; selective policies represented | 3 ahead / 110 behind |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33Z | fix: minimise shareable process-exit diagnostics | #16 open; ancestor | 0 ahead / 53 behind |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `a5dddb3697b18167b195f79a60b4f14468f789c3` | 2026-11-07 17:32Z | Test active-voice historical policy false positive | #27 open; editorial | 28 ahead / 11 behind |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05Z | Verify a specific parliamentary bill remains eligible for the GS II feed | #21 open; editorial | not compared; non-UI |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46Z | fix(editorial): match policy action and target as separate words | #24 open; based on brrrNEWS | 9 ahead / 16 behind |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16Z | test: align economy signal count with repo-rate mapping | #2 closed | 1 ahead / 129 behind |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34Z | fix: tighten UPSC editorial signal matching | #1 closed | 1 ahead / 130 behind |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27Z | fix: use supported calendar navigation glyph | #5 closed | 2 ahead / 127 behind |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49Z | test: preserve Parliament bill headline variants | #23 closed; merged into black-box only | 0 ahead / 19 behind |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15Z | Validate RSS redirects against publisher allowlists | #15 closed; policy present in active line | 2 ahead / 110 behind |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12Z | fix(quiz): qualify selected semantics receiver | #28 closed; merged into black-box only | 0 ahead / 9 behind |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | 2026-11-07 14:59Z | test(rss): correct relative xml base fixture | #33 open; parser delta | 4 ahead / 3 behind |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28Z | test(reader): cover publisher-only image resources | #25 open; security review pending | 7 ahead / 12 behind |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42Z | fix: deduplicate RSS tracking URL variants | #17 open; policy represented selectively | 1 ahead / 110 behind |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44Z | fix: capture Search glass tint before draw callback | #10 closed | 1 ahead / 124 behind |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39Z | fix: diagnose launch crashes and move feed indexing off main | #9 closed | 1 ahead / 125 behind |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18Z | fix: harden cold start and reduce feed rendering workload | #7 closed | 1 ahead / 126 behind |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16Z | Correct icon artwork accessibility name to brrrNews | default branch | 0 ahead / 110 behind |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846224182f16981b15e5c803761e26d` | 2026-11-07 17:34Z | Record successful bounded-diagnostic CI | #35 open; direct child | 6 ahead / 0 behind |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53Z | Verify legacy markers survive recent-history retention | #22 open; legacy caveat | 9 ahead / 123 behind |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29Z | docs: record bounded RSS fetch review and CI state | #32 open; NewsRepository delta | 2 ahead / 5 behind |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11Z | fix(startup): keep appearance snapshot API module-internal | #31 open; represented | 5 ahead / 5 behind |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05Z | perf(storage): move bookmark mutations off the UI thread | #29 open; represented | 1 ahead / 9 behind |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50Z | perf: move read-marker persistence off main thread | #8 open; reconciled policy | 3 ahead / 123 behind |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27Z | privacy: block third-party publisher reader subresources | #26 open; security review pending | 1 ahead / 11 behind |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31Z | fix: make selection decoding fallback valid JSON | no PR; stale | 0 ahead / 204 behind |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56Z | ui: restore low-cadence foreground adaptive glass contrast | #13 open; conservative policy represented | 1 ahead / 123 behind |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25Z | fix: import bottom navigation glyphs | #3/#4 closed; stale sibling | 3 ahead / 128 behind |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11Z | docs(reader): record native selection collision guard | #19 closed; merged into black-box only | 0 ahead / 40 behind |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18Z | fix: finish syllabus metadata and thin-film feed eligibility | no PR; superseded | 0 ahead / 26 behind |

**Actions, workflow and Releases**
- All 251 Actions runs were paginated (100 + 100 + 51): 149 success, 58 failure, 44 cancelled; no queued or in-progress run remains.
- Latest active-branch validation before this pass is PR run #245 and push run #244, both successful on `ed6c94190296c8d7af400b80f6fe26f2252fafe1`. PR artifact `13582611557` is 39,095,549 bytes; push artifact `13581944398` is the same size. Both jobs completed WordNet builder tests/database generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload; release jobs skipped.
- Latest overall run #251 is PR #35's exact head `c4a977831846224182f16981b15e5c803761e26d`; its build/test/artifact steps passed and artifact `13584216067` is 39,095,814 bytes. It does not validate black-box. Run #250 passed on PR #27's separate editorial head.
- Active `.github/workflows/build-apk.yml` keeps build/test read-only and isolates Release write access to main pushes. Main's workflow remains push-only; PR #34's exact natural PR run #245 is the available PR validation evidence. Latest Release remains `v0.2.0-build-116`; no Release was created.
- No manual dispatch/rerun, main write, PR merge/retarget, workflow change, package identity change or source-branch deletion occurred. OPPO device launch/visual/performance/accessibility evidence remains unverified.

**Disposition**
- Continue only on `black-box-bihar-news`; preserve every other branch and its PR state. Re-read all refs and comparisons before the next commit. The next UI pass is the Search/Saved/Stats grouped-filter readability improvement recorded in `docs/LIQUID_GLASS_UI_ROADMAP.md`; its CI is pending on the new child head.


## Live branch, PR, Actions and source-freshness audit — 8 November 2026, 00:24 IST

- Complete branch endpoint: 38 remote refs (page 1 = 38, page 2 = 0). All PR pages: 35 PRs (21 open, 14 closed). Default branch remains main@73b8a0d1ba33503fede02db2c3c0af082d724a12.
- Owner-selected integration line before this screen-sized commit: black-box-bihar-news@ad16cecf06add374b3e19989d0829919d0055114; it is 111 ahead / 0 behind main and 54 ahead / 0 behind PR #16's corrected stability/Prismal head 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890. It is a direct child of ed6c94190296c8d7af400b80f6fe26f2252fafe1. PR #34 is the sole consolidation draft, open, based on main; its exact head matched this source before the implementation commit.
- The branch graph has not produced a newer coherent UI baseline. brrrNEWS@e6fcfc25a4c8d5f363ced58f313552120aa7ff96 is 4 ahead / 17 behind the selected line; its supporting-card glass and memoization are already represented. PR #30 (48dp icon targets), #31 (off-main appearance restore) and #29 (bookmark IO) are also already represented. No cherry-pick or source-branch modification was required.
- The 38-ref inventory is unchanged by name from the preceding full table; the updated/newer heads and their PR relationships are:

| Branch | Current head | Last commit UTC / subject | PR state / treatment |
|---|---|---|---|
| black-box-bihar-news | ad16cecf06add374b3e19989d0829919d0055114 | 2026-11-07 18:02:35 — ui: improve grouped filter legibility and reuse | #34 open/draft; active source |
| a11y/48dp-icon-targets-2026-11-07 | 25107ce905b70bde1a41282ffd1d606819ebf58d | 2026-11-07 11:02:12 — test(a11y): cover minimum icon action target | #30 open; policy already represented |
| perf/off-main-appearance-preferences-2026-11-07 | bc48daec98ff076c9db3c2c69084d9b08cd0bbce | 2026-11-07 11:11:45 — fix(startup): keep appearance snapshot API module-internal | #31 open; policy already represented |
| perf/off-main-bookmark-persistence-2026-11-07 | a64857b5efa62ea053c0c96e5f374fd35924b340 | 2026-11-07 09:05:25 — perf(storage): move bookmark mutations off the UI thread | #29 open; policy already represented |
| perf/bound-startup-diagnostic-reads-2026-11-07 | c4a977831846224182f16981b15e5c803761e26d | 2026-11-07 17:34:16 — Record successful bounded-diagnostic CI | #35 open; diagnostic-only, not a UI baseline |
| perf/bounded-read-history-2026-11-07 | 910178af3f595993557533cb65d03e714bcd7760 | 2026-11-06 23:53:04 — Verify legacy markers survive recent-history retention | #22 open; new-marker policy represented with legacy caveat |
| perf/bounded-rss-fetch-concurrency-2026-11-07 | 8c56c98c83cc69451f128aa259df86a8ce28f43a | 2026-11-07 11:29:26 — docs: record bounded RSS fetch review and CI state | #32 open; independent NewsRepository change |
| fix/resolve-relative-atom-links-2026-11-07 | c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4 | 2026-11-07 14:59:24 — test(rss): correct relative xml base fixture | #33 open; independent NewsRepository change |
| fix/editorial-action-gate-blackbox-2026-11-07 | ac0f91097fc2ed524682ea085d87d0770d830e3d | 2026-11-07 18:38:09 — Record editorial regression CI failure and targeted correction | #27 open; editorial-only, natural run #254 failed one fixture |
| fix/editorial-policy-exception-guard-2026-11-07 | 1f7a3c2a7b74cc02cfee6fe451f4576feb291acf | 2026-11-07 04:46:47 — fix(editorial): match policy action and target as separate words | #24 open; based on divergent brrrNEWS |
| fix/rss-image-host-allowlist-2026-11-07 | 7a51c9c2c40aa89997db55194a6db3d0d65ac95d | 2026-11-07 06:28:46 — test(reader): cover publisher-only image resources | #25 open; security review pending |
| privacy/webview-publisher-subresource-gate-2026-11-07 | b8d2ef9ff2413c378347986d00961c401dfa771d | 2026-11-07 07:27:22 — privacy: block third-party publisher reader subresources | #26 open; security review pending |
| fix/editorial-heatwave-bill-signals-2026-11-07 | 89464104e300f48da10e3e32e33715cbe2405084 | 2026-11-06 22:05:40 — Verify a specific parliamentary bill remains eligible for the GS II feed | #21 open; editorial policy represented |
| fix/rss-tracking-variant-dedup-2026-11-06 | 2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b | 2026-11-06 16:42:29 — fix: deduplicate RSS tracking URL variants | #17 open; URL identity policy represented |
| fix/early-startup-crash-diagnostics-2026-11-06 | 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890 | 2026-11-07 03:33:20 — fix: minimise shareable process-exit diagnostics | #16 open; corrected stability ancestor |
| ui/foreground-adaptive-glass-contrast-2026-11-06 | 3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264 | 2026-11-06 10:56:16 — ui: restore low-cadence foreground adaptive glass contrast | #13 open; conservative shared policy represented |
| ui/liquid-glass-system-2026-11-07 | 1f7cd2e5ddc5d169d1443035a8334538e44efc40 | 2026-11-06 20:11:36 — docs(reader): record native selection collision guard | #19 closed; integrated into black-box only |
| ui/liquid-glass-system-continuation-2026-11-07 | f031bf21229f43a992384cc23fa0f4da57e547a1 | 2026-11-07 03:18:34 — fix: finish syllabus metadata and thin-film feed eligibility | no PR; 27 commits behind active |
| ui/liquid-glass-performance-2026-11-06 | 2e9c7f62ca65a899e18d9026e1535392835b3724 | 2026-11-06 06:25:39 — fix: import bottom navigation glyphs | #3/#4 closed; stale sibling |
| redesign/v0.3-search-reader | 654cc89dee4f93fea98bb76df9d8cf88abf2f0d8 | 2026-11-03 15:31:48 — fix: make selection decoding fallback valid JSON | no PR; stale |

- PR #35's bounded diagnostic reads are separate from the UI branch; its natural run #251 passed. PR #32/#33 both touch NewsRepository.kt and remain a joint review item. PR #25/#26 remain a combined publisher-host/subresource security review. PR #27's #254 failure is on its own merge-ref/test tree and is not a failure of black-box-bihar-news. PR #24 is based on brrrNEWS and is not a UI baseline.
- Actions history: 254 runs (100 + 100 + 54), no queued/in-progress runs. Run #253 passed on exact pre-commit integration head ad16cecf06add374b3e19989d0829919d0055114; build/test/artifact steps passed and artifact 13584638074 is 39,095,814 bytes. Run #254 failed only on PR #27's editorial test; it is not an active-branch check. Latest Release is v0.2.0-build-116; no Release was published.
- Active workflow .github/workflows/build-apk.yml on this branch has read-only build/test permissions and an isolated main-push Release-write job. Main's workflow remains push-only. No manual dispatch/rerun, merge, main write, retarget, Release publication or source-branch deletion occurred.
- Current implementation is a screen-sized feed-placeholder geometry pass on the owner-selected branch; its exact new head awaits natural CI. OPPO Reno10 launch, visual, accessibility, animation, Perfetto, memory, GPU, battery and thermal evidence remains unverified.

## 8 November 2026, 01:29 IST — You and statistics supporting-surface consistency

- Active line: black-box-bihar-news, starting head f2ad2b57e06863f04ee22c2b0d89ae3b0775fb32; PR #34 remains open/draft to main@73b8a0d1ba33503fede02db2c3c0af082d724a12. The branch was 112 ahead / 0 behind main before this pass. No branch creation, retarget, merge or source-branch mutation.
- Live graph: 38 remote branches (38 on page 1, none on page 2), 35 PRs (21 open, 14 closed). No newer single branch supersedes the owner-selected integration line. PR #16's corrected stability/Prismal baseline is an ancestor; PR #19/#20 work and selected feed/editorial policies are represented in the active tree.
- PR #30 (48dp icon target), #31 (IO appearance preference snapshot) and #29 (serialized off-main bookmark persistence) were checked in the active source and are already represented; their source branches remain untouched. PR #35 bounded startup-diagnostic reads are still a separate six-commit branch and are not mixed into this UI pass. PR #32/#33 passed separate natural CI but both modify NewsRepository.kt; joint reconciliation remains pending. PR #27 latest head cef7f1267cf2ca67ceacaf2acd9cfcbfa7dbf6fc has no natural PR run. PR #25/#26 remain a combined security review. Old UI/search branches remain stale or behind.
- Exact pre-batch active head f2ad2b57e06863f04ee22c2b0d89ae3b0775fb32 passed natural PR run #256 (42404197533). Build/test job steps: checkout, Java, cached signing key, Android SDK/platform, Gradle, WordNet builder/database, :app:testDebugUnitTest, :app:assembleDebug and artifact upload all succeeded; publish job skipped. Artifact 13586850838, 39,102,734 bytes, SHA-256 6be6840c650537a5d29e95397576bcb4f71cc92635f0be8890ac6f89adf8a703. Full job log was inspected. This is parent evidence only, not validation of the new UI commits.
- This pass adds shared SupportingCardGeometryPolicy tokens and standardises You/Saved/Reading Stats/Quiz Stats supporting surfaces and empty states. Existing static SupportingGlassCard is reused; no additional blur, backdrop capture, sampler, shader, graphics layer, decode, dependency or persistence path. The exact implementation commits are d8b7146e3546e0e248bb5db766ccb0ec8ac09184, 41b572e77f1a7eca48abb11729ac5dd175b3ee69 and dfcc792887cd56d7e5272ca4087c6517c88b3a44.
- Actions limitation: the connected GitHub actions expose exact-commit PR runs, job steps, logs and artifacts, but no repository-wide list-runs/queue action. Therefore run #256 is the latest verified exact active-branch PR run; the full queue/history could not be independently enumerated in this pass. The connector also exposes no Releases listing action; the last recorded Release v0.2.0-build-116 remains historical, not freshly verified here.
- New exact-head CI is pending after the code and documentation commits; do not dispatch/rerun. No main change, Release publication, PR merge, source-branch deletion or device-backed performance claim.


## Live repository/branch/PR/Actions audit — 8 November 2026, 11:06 UTC

- **Default branch:** \`main@73b8a0d1ba33503fede02db2c3c0af082d724a12\`, unchanged.
- **Active integration:** \`black-box-bihar-news@f73ea24fe71489813df3f576ee843d564b51de56\`; parent \`8c1239a6762cacfce281233b96298096bf28a3a6\`. PR #34 is open/draft, head matches the branch, base is main, mergeable; comparison is **143 ahead / 0 behind main**, merge base main. PR #16's corrected background-only Prismal/stability head is an ancestor; active integration is **86 commits ahead** of it.
- **Complete graph:** 44 remote branches (page 1 = 44, page 2 = 0); 41 PRs (27 open, 14 closed). The full branch metadata table follows. This table supersedes earlier inventory tables for current refs; older historical entries remain for chronology.
- **Relevant ancestry/diff review:** PR #37 is 6 ahead / 24 behind active at this snapshot; its RowScope Search clear fix is already present in current \`MainActivity.kt\`. PR #30 is 3 ahead / 39 behind; its \`TouchTargetPolicy.kt\` blob is identical. PR #36 is 3 ahead / 25 behind; its AsiaKolkataDayPolicy and test blobs are identical. PR #31 is 5 ahead / 38 behind; AppearancePreferencesPolicy is identical. PR #29 is 1 ahead / 42 behind; BookmarkSetPolicy is identical. These source branches remain untouched.
- PR #39 is 6 ahead / 8 behind and changes the build/signing workflow, Gradle signing configuration and migration documentation; it is not a UI baseline and remains blocked on owner-configured secrets, pinned certificate fingerprint and explicit local-data migration acceptance. PR #40/#41 are main-based RSS changes (6/143 and 8/143 ahead/behind relative to active) and both alter NewsRepository; do not port independently. PR #32/#33/#38 also overlap parser/concurrency paths. PR #35 is diagnostic-only. PR #25/#26 require joint resource-security review. PR #27/#21/#24/#6 are editorial proposals. PR #13's source differs from the current conservative shared luminance implementation. No unrelated branch was changed.
- **Current source commit:** \`f73ea24fe71489813df3f576ee843d564b51de56\` (\`ui(appearance): reuse shared glass page header\`, 11:06:43 UTC). It replaces only the Appearance page's plain title/back row with the shared glass header; the detailed design/cost rationale is in \`LIQUID_GLASS_UI_ROADMAP.md\`.
- **Actions:** fully paginated 334 runs (100 + 100 + 100 + 34): 203 success, 67 failure, 64 cancelled; queued/in-progress 0. Latest natural push run #333 and PR run #334 passed on parent \`8c1239a6\`; WordNet builder 4/4, 151,456-word database, \`:app:testDebugUnitTest\`, \`:app:assembleDebug\`, artifact upload; publish jobs skipped. PR artifact #13614265719 is 39,121,080 bytes (SHA-256 \`95dd164522c666932f4dccb52b8c918c164a4879f0e42474b3cb161aa8750c11\`); push artifact #13614275602 is 39,121,079 bytes (SHA-256 \`507da7fd7fcddd2c23c485f73ae194473cc546cce9f4911ab5dc7a41537b2942\`). These runs do **not** validate new head f73ea24; its natural push/PR runs are pending.
- **Workflow / Release:** integration \`.github/workflows/build-apk.yml\` has read-only build/test permissions and a separate main-push-only Release-write job. Main's workflow remains push-only with workflow-wide contents:write. Latest Release remains \`v0.2.0-build-116\`, published 6 November 2026 11:18:59 UTC; no Release was created.
- **Safety:** no main write, merge, retarget, force update, workflow dispatch/rerun, Release publication or branch deletion. No OPPO Reno10 logcat/tombstone/Perfetto or screenshot evidence was available; native launch, visuals, accessibility, frame timing, GPU, memory, thermal and battery remain UNVERIFIED.

### Complete remote branch inventory

| Remote branch | Head SHA | Last commit (UTC) | Last commit message | PR relationship / disposition |
|---|---|---|---|---|
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` | 2026-11-07 11:02:12 | test(a11y): cover minimum icon action target | #30 open; policy already represented |
| `black-box-bihar-news` | `f73ea24fe71489813df3f576ee843d564b51de56` | 2026-11-08 11:06:43 | ui(appearance): reuse shared glass page header | #34 open/draft; active; base main |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` | 2026-11-07 04:13:30 | ui: consolidate lightweight supporting glass surfaces | base of #24; divergent sibling, not baseline |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` | 2026-11-06 17:18:53 | ci: validate pull requests with read-only build permissions | #18 open; separate main workflow proposal |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` | 2026-11-06 21:41:27 | ci: restore Android SDK setup before sdkmanager | #20 closed; CI design represented in active branch |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | current ref equals main; #14 closed |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` | 2026-11-06 10:53:35 | fix: refresh article card metadata when feed content changes | #6 open; editorial proposal |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` | 2026-11-06 09:14:27 | fix: select Atom article alternate links deterministically | #12 open; parser delta selectively represented |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` | 2026-11-06 14:26:32 | feat: expose publication date and publisher RSS provenance on cards | #11 open; selected policies represented |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890` | 2026-11-06 19:33:20 | fix: minimise shareable process-exit diagnostics | #16 open; corrected stability ancestor |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `2468ed33326ac7310a2ddd5f993495e4051245b8` | 2026-11-08 03:30:19 | test(editorial): cover reality TV and television promotion | #27 open; editorial-only |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` | 2026-11-06 22:05:40 | Verify a specific parliamentary bill remains eligible for the GS II feed | #21 open; editorial proposal |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` | 2026-11-07 04:46:47 | fix(editorial): match policy action and target as separate words | #24 open; based on divergent brrrNEWS |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` | 2026-11-06 05:16:32 | test: align economy signal count with repo-rate mapping | #2 closed |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` | 2026-11-06 02:34:28 | fix: tighten UPSC editorial signal matching | #1 closed |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` | 2026-11-06 06:27:06 | fix: use supported calendar navigation glyph | #5 closed |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` | 2026-11-07 03:49:44 | test: preserve Parliament bill headline variants | #23 closed; integrated into black-box only |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` | 2026-11-06 12:15:39 | Validate RSS redirects against publisher allowlists | #15 closed; redirect policy lineage superseded by later host review |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` | 2026-11-07 08:12:10 | fix(quiz): qualify selected semantics receiver | #28 closed; integrated into black-box only |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` | 2026-11-07 14:59:24 | test(rss): correct relative xml base fixture | #33 open; NewsRepository/parser delta |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` | 2026-11-07 06:28:46 | test(reader): cover publisher-only image resources | #25 open; security review pending |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` | 2026-11-06 16:42:29 | fix: deduplicate RSS tracking URL variants | #17 open; URL identity policy selectively represented |
| `fix/search-clear-row-scope-compile-2026-11-08` | `9dd4ceac08e88e6ee4ea565025a70539a6e9e331` | 2026-11-07 21:55:56 | docs: record verified search clear compile repair | #37 open; RowScope fix already present in active MainActivity |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` | 2026-11-06 07:44:03 | fix: capture Search glass tint before draw callback | #10 closed |
| `fix/stable-release-signing-2026-11-08` | `4250de5040a8ca0efbf96a990218ef7d69ef26e8` | 2026-11-08 07:03:31 | docs: record stable signing validation and migration gate | #39 open; secret-backed signing/migration proposal |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` | 2026-11-06 07:39:53 | fix: diagnose launch crashes and move feed indexing off main | #9 closed |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` | 2026-11-06 07:18:06 | fix: harden cold start and reduce feed rendering workload | #7 closed |
| `fix/today-ist-midnight-rollover-2026-11-08` | `5fbd14e6ef9be33d04a882ba463d4c72473419e2` | 2026-11-07 20:48:43 | test(today): cover Asia/Kolkata midnight rollover | #36 open; policy/test blobs identical in active tree |
| `fix/validate-rss-article-hosts-2026-11-08` | `552b96e7948ac5ade821a711ea8dd225984824a9` | 2026-11-08 10:34:28 | fix(rss): keep redirect result non-null | #40 open; main-based RSS/network delta |
| `main` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` | 2026-11-06 11:16:35 | Correct icon artwork accessibility name to brrrNews | default branch |
| `perf/bound-rss-entry-parsing-2026-11-08` | `adade1ed8494c66b7e567de87f61f3dac3da7788` | 2026-11-08 03:31:36 | fix(test): correct RSS budget test identifier | #38 open; parser-only |
| `perf/bound-rss-field-values-2026-11-08` | `e4ca4a522dd236052142aa31dc518979bfafe891` | 2026-11-08 10:30:00 | fix(rss): retain inline Atom content text | #41 open; main-based parser/field delta |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846224182f16981b15e5c803761e26d` | 2026-11-07 17:34:16 | Record successful bounded-diagnostic CI | #35 open; diagnostic-only |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` | 2026-11-06 23:53:04 | Verify legacy markers survive recent-history retention | #22 open; based on #8; legacy markers retained |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` | 2026-11-07 11:29:26 | docs: record bounded RSS fetch review and CI state | #32 open; NewsRepository delta |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` | 2026-11-07 11:11:45 | fix(startup): keep appearance snapshot API module-internal | #31 open; AppearancePreferencesPolicy blob identical |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` | 2026-11-07 09:05:25 | perf(storage): move bookmark mutations off the UI thread | #29 open; BookmarkSetPolicy blob identical |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` | 2026-11-06 10:50:31 | perf: move read-marker persistence off main thread | #8 open; reconciled persistence lineage |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` | 2026-11-07 07:27:22 | privacy: block third-party publisher reader subresources | #26 open; security review pending |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` | 2026-11-03 15:31:48 | fix: make selection decoding fallback valid JSON | no PR; stale, 236 behind active |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` | 2026-11-06 10:56:16 | ui: restore low-cadence foreground adaptive glass contrast | #13 open; older policy differs from active conservative implementation |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` | 2026-11-06 06:25:39 | fix: import bottom navigation glyphs | #3/#4 closed; divergent and 160 behind active |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` | 2026-11-06 20:11:36 | docs(reader): record native selection collision guard | #19 closed; integrated into black-box only |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` | 2026-11-07 03:18:34 | fix: finish syllabus metadata and thin-film feed eligibility | no PR; 59 behind active, superseded |
