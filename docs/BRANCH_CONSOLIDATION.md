# Black Box Bihar News — complete branch consolidation

**Repository:** Batmancpu/CurrentLens  
**Chosen integration branch:** `black-box-bihar-news` — owner-selected single active integration branch.  
**Verified source HEAD before the transient-controls batch:** `16eb549c3235b321ecf7ef1c5c5270817e336489`; the new UI commit is a direct child, created with a fast-forward-only expected-head update.  
**Default branch:** `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`, unchanged. The active branch is 102 commits ahead / 0 behind main and 45 ahead / 0 behind PR #16's corrected stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.  
**Live branch inventory before this batch:** 33 remote refs (all returned on page 1; page 2 empty), 15 open PRs and 14 closed PRs. No branch was deleted.  
**Latest active-branch validation before this batch:** Actions run #213, success on exact SHA `16eb549c3235b321ecf7ef1c5c5270817e336489`; WordNet builder tests 4/4, database 151,456 unique words / 50,950,144 bytes, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload succeeded. Artifact `13568329178`, 39,082,053 bytes, SHA-256 `2a0af7c27ff5aff00b48c12d7659cb5b37b5103f6858ebf090be02b2cc9f0981`. Release job skipped.  
**Actions history:** 213 runs fully paginated (100 + 100 + 13; page 4 empty): 127 success, 54 failure, 32 cancelled; no queued/in-progress runs.  
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

