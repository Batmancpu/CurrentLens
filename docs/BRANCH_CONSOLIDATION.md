# brrrNEWS — consolidated branch

**Repository:** `Batmancpu/CurrentLens`  
**Chosen integration branch:** `brrrNEWS`  
**Current HEAD:** follow the live `brrrNEWS` branch ref; this inventory is a maintenance snapshot.  
**Default branch:** `main` — unchanged at `73b8a0d1ba33503fede02db2c3c0af082d724a12`  
**Live branch inventory at this update:** 27 refs, including `main`, `brrrNEWS`, and the superseded `black-box-bihar-news` branch. No remote branch was deleted in this task.  
**Latest completed validation:** Actions run [#190](https://github.com/Batmancpu/CurrentLens/actions/runs/42335940352), success on the documentation commit before the PR #23 merge. Natural post-merge run #192 is validating this exact HEAD.  
**No Release was published.** No manual workflow dispatch or rerun was used.

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

This is the live 27-ref inventory audited against the selected `brrrNEWS` integration branch. “Integrated” means the useful code/policy/test is in this branch, not that the old source commit was merged into its Git ancestry. Source branches were deliberately retained.

| Remote branch | Disposition |
|---|---|
| `brrrNEWS` | **Selected active integration branch.** All future consolidation work goes here. |
| `black-box-bihar-news` | Superseded pre-rename integration branch. Its content is retained as a rollback reference pending owner cleanup. |
| `main` | Default branch; unchanged. Keep. |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | PR #18 workflow intent integrated: read-only build/test permissions and a separately gated main-push-only Release job. Source PR #18 remains open/draft. |
| `ci/stacked-pr-validation-2026-11-07` | PR #20 merged into `brrrNEWS`; natural validation for stacked work. Source PR #20 is closed/merged into this branch, not main. |
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
| `ui/liquid-glass-system-2026-11-07` | Reader UI PR #19 merged into `brrrNEWS`; it was not merged into main. |
| `ui/liquid-glass-system-continuation-2026-11-07` | Earlier validated integration checkpoint (run #182); current branch is ahead of it, with no unique commit remaining. |

## 3. Pull-request state at the audit

- PR #19 (Reader Liquid Glass), PR #20 (stacked read-only CI) and PR #23 (specific parliamentary syllabus gate) are **closed/merged into `brrrNEWS`**, not into main.
- PR #15 (publisher redirect allowlist) is closed/unmerged; its safe redirect behaviour is present in the integration branch.
- PRs #6, #8, #11, #12, #13, #16, #17, #18, #21 and #22 remain open/draft. Their useful code is represented in the consolidated branch; they are source-history records, not independent implementation targets.
- No source branch was deleted. The owner should review the `brrrNEWS` branch before deleting superseded source refs. This review did not delete remote refs. PR #11 is especially redundant because it was retargeted to the integration branch and its changes were separately reconciled there.

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
