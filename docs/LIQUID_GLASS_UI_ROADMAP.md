# Liquid Glass UI Roadmap

**Status:** Consolidated branch; current natural CI build is green  
**Feature branch:** `black-box-bihar-news`  
**Current HEAD:** `ebdfdd7b22c9164ab4ea24a6fa744e706081fc7f`  
**Integration base:** PR #16 stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`  
**Latest successful run:** #187 on this exact HEAD; JVM tests, APK assembly and artifact upload passed.  
**Main:** unchanged at `73b8a0d1ba33503fede02db2c3c0af082d724a12`.  
**Safety:** no source branch has been deleted and no Release has been published. The user retains all source refs until review.

## Product and material principles

Liquid Glass is a functional interaction material, not a universal card background. Use it to clarify navigation, toolbars, search/filter controls and transient actions. Keep article text, article cards, statistics, quiz questions and other dense information surfaces calm, high-contrast and easy to read.

Use one coherent material hierarchy:

1. **Primary floating glass:** navigation and compact Reader chrome; strongest restrained refraction/specular response.
2. **Interactive control glass:** search, category/filter rails, toolbars, contextual controls and popovers.
3. **Supporting surfaces:** quiet translucent tint, continuous corners and restrained edge/depth treatments on dashboard/stat destinations where useful; no per-card live blur.
4. **Editorial/content surfaces:** opaque or near-opaque readable surfaces for article text, cards, statistics and quiz questions.

Preserve the existing PrismalAGSL implementation. Do not create a parallel glass engine, recursively capture descendants, add dependencies, or disable all glass to work around a rendering defect. The root backdrop must remain a background-only sibling; the UI and glass surfaces stay outside the captured source.

## Verified branch selection and dependency register

The complete remote branch inventory contained 19 branches at the start of this iteration. The chosen source is the latest safe UI baseline, not the most recently named or timestamped UI branch.

- `fix/early-startup-crash-diagnostics-2026-11-06` — PR #16 head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`; 57 ahead / 0 behind main. Selected because it contains the latest corrected Prismal capture and launch/privacy work.
- `ui/liquid-glass-performance-2026-11-06` — `2e9c7f62ca65a899e18d9026e1535392835b3724`; diverged from PR #16 by 3 unique commits and 75 commits behind. Not used as a base. Its first material/UI commit was merged through PR #3; the Search source-height adjustment is already present in PR #16; the remaining icon-import change is superseded by current imports.
- `redesign/v0.3-search-reader` — `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8`; 151 commits behind PR #16. Stale and not used.
- PR #13 / `ui/foreground-adaptive-glass-contrast-2026-11-06` — its shared 8×8 bottom-band luminance probe and linear-luminance policy are selectively integrated. The original 1.5 s / 2.5 s cadence was not retained: the consolidated implementation uses 4 s on Android 12+ / 6 s on older Android and suspends sampling during feed motion, Reader, Settings and background operation. It remains one shared root source; device-side jank/battery impact is unverified.
- PR #18 / `ci/read-only-pr-validation-isolated-release-write-2026-11-06` and PR #20 / `ci/stacked-pr-validation-2026-11-07` — their compatible workflow intent is consolidated into the one workflow on `black-box-bihar-news`: read-only build job, separately gated main-push release job, and natural PR validation for main, PR #16 and this integration branch. Source branches are retained.
- PRs #6 and #21 are reconciled into one editorial policy: GS I Geography, Finance Bill / parliamentary bill / heat-wave signals, entertainment and viral/sports false-positive suppression, narrow thin-film science exception, content-aware card metadata and labelled synthetic regression fixtures. PR #8 and #22 are reconciled into off-main local persistence with an ordered 5,000-entry window for new read markers while preserving the legacy unordered set. PR #11, #12 and #17 are selectively ported into the latest redirect-guarded feed code: publication-date precedence/cache/provenance, Atom alternate-link selection, tracking-URL identity and exact bookmark/read-variant preference. These changes were hand-reconciled, not blindly merged.

Recheck the entire branch graph, open PRs, merge state and exact source/base SHAs before every subsequent implementation pass. Never reset, force-push, overwrite or discard another branch's commits.

## Screen coverage matrix

| Screen / component | Material tier | Intended treatment | Current status |
|---|---|---|---|
| Shared home header | Primary floating glass | Clear translucent header, restrained edge/specular, stable height across destinations | Existing Prismal glass; contrast/hierarchy polish remains |
| Four-tab navigation | Primary floating glass | One droplet bar with selected glyph aligned to its hit region | Existing Prismal droplet, hidden glyph capture and merged source; no new sampler |
| Compact feed navigation | Primary floating glass | Morph the same bar into one active-destination pill on eligible feed scroll only | Implemented; state policy validated in runs #186 and #187; physical animation still needs device validation |
| Search morph field | Interactive control glass | Preserve tab-to-IME geometry and focus; trailing clear affordance | Existing morph/focus callback preserved; clear target is 48dp (run #183 and #187 passed) |
| Explore category rail | Interactive control glass | One shared capsule; selected fill/border and >=48dp targets | 48dp target, selected semantics and Geography filter integrated |
| Today / Explore / Search article cards | Editorial content | Continuous corners, clear typography, image-derived ambient colour; no live blur per card | Existing standard cards, single Coil request and same-bitmap palette |
| You dashboard | Supporting surfaces | Consistent metric/destination geometry and restrained tint/edge hierarchy | Standard cards; geometry pass remains |
| Saved | Interactive controls + editorial content | Clear filters; standard readable saved-story cards | Existing filters/cards; consistency pass remains |
| Reading / Quiz Stats | Supporting surfaces | Consistent metric tiles, filter states and readable charts/data | Standard content surfaces; hierarchy pass remains |
| Reader toolbar | Primary floating glass | Back, source/date/status and compact Aa control | Implemented in this batch |
| Reader progress | Minimal indicator | Reuse existing saved progress; no polling or animated readback | Implemented in this batch |
| Reader article canvas | Editorial content | Clear, high-contrast text; responsive max width and paragraph rhythm | Existing responsive CSS; keep non-glass |
| Reader completion action | Transient contextual glass | Compact, dismissible, only after true completion; no overlap with definition | Implemented in this batch |
| Definition popover | Interactive contextual glass | Anchored Prismal surface, full selected word and compact accessible actions | Existing anchored surface; title/actions improved in this batch |
| Quiz questions/options | Editorial content | Calm standard surfaces; persist only on submit | Existing standard surfaces; preserve |
| Quiz action controls | Interactive control glass where useful | Compact, accessible action layer without glassifying questions | Planned |
| Appearance settings | Interactive control glass | Clear↔Frosted control and live preview using existing components | Existing slider, presets and preview; low-effects option/preview polish remains |
| Dialogs, menus and sheets | Contextual controls | Use glass only when transient context benefits; preserve native OS selection/share UI | Review individually; no global glass overlay |

## Paired performance matrix

| Component | Live effect / scope | Cost-control strategy | Fallback / verification |
|---|---|---|---|
| Root backdrop | One full-window background-only Prismal capture source | Capture background pixels only; no descendants; reuse same source | Do not reintroduce self-capture; code reviewed, device launch still unverified |
| Navigation | One floating capsule plus existing tab-glyph source/merged droplet | Preserve existing two-source architecture; no new sampler/readback | Static/low-effects presentation under Reduce Motion; OPPO GPU cost unmeasured |
| Home header | One small header glass surface | Existing shared backdrop; no duplicate captures | Theme-derived contrast; no adaptive readback added |
| Search | One morphing capsule | Reuse existing anchor/dock geometry and single backdrop | Preserve IME; clear affordance must not alter layout or focus |
| Explore filters | One shared capsule, not one glass surface per chip | Reuse same backdrop; keep chip fills/borders as simple drawing | >=48dp target and selected semantics still require device/TalkBack checks |
| Feed cards | No live blur | One Coil request per image; palette sampled from that decoded bitmap; cached per article | Standard content card if palette unavailable; no duplicate decode |
| Reader toolbar | One 8dp-blur glass toolbar, 60dp minimum height | Reuse root backdrop; one small bounded surface; Aa menu exists only when opened | Static material/Reduce Motion fallback; device GPU cost unmeasured |
| Reader progress | 2dp content indicator | Reuse existing debounced `savedReadingProgress`; no polling, WebView injection or readback | Hidden at zero/completion; visual timing needs device validation |
| Completion dock | One 7dp-blur surface, only after end detection | Render only when complete, not dismissed and no definition open | No large persistent panel; safe-area and collision checks still need device validation |
| Definition popover | Existing anchored Prismal surface | Keep one existing popover; do not add another capture; action buttons use native Compose controls | Compact scroll bounds retained; selection/ActionMode requires device validation |
| Dashboard / stats / quiz | No new live blur in this batch | Standard content surfaces and shared shape/spacing tokens in later pass | Avoid glass on dense data/questions |
| Appearance preview | Existing small preview surfaces | Reuse current slider value and existing components; no whole-screen blur or repeated image decode | Low-effects option and slider coalescing remain future work |

## Implemented batch — Reader toolbar and completion

**Changes on this feature branch:**

- Replaced the plain cramped Reader row with a compact Prismal glass toolbar containing Back, publisher identity, the publisher-supplied date in IST, source-status wording and an Aa entry point.
- Moved the existing font-size range (16–28sp) behind an accessible dropdown. The menu uses deterministic clamping and is only composed when opened.
- Added a subtle 2dp progress line that reuses the existing debounced `savedReadingProgress` state. No new WebView polling, scroll observer, GPU readback or JavaScript was added.
- Replaced the large fixed “Finished reading?” card with a compact Prismal completion dock. It appears only after the existing end detector fires, hides while a definition or native selection ActionMode is open, remains dismissible, and retains the five-question CTA with explicit “not official PYQs” provenance.
- Reworked the definition title/actions so the selected word can use two lines and Copy/More/Done are compact icon actions with accessible descriptions and 48dp targets.
- Added `ReaderChromePolicy` and focused JVM tests for font-size bounds and completion visibility.

**Rendering delta:** one small Reader toolbar surface is now active while reading; one small completion surface is active only after completion. Both reuse the existing root backdrop. The definition popover remains the existing single anchored surface. No per-card blur, extra backdrop capture, sampler, image decode, GPU readback, dependency, network request or persistent data format was added.

**Not included yet:** Reader chrome auto-hide/directional pinning, feed-only compact navigation, Search clear target increase, dashboard/stat geometry and the full appearance-preview refinement remain separate future batches.

## Verification status and limitations

- Branch creation source was rechecked against PR #16 head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.
- PR #16's latest natural run before this branch, Build #167, passed WordNet builder tests (4/4), offline database generation (151,456 unique words), `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload. This result does **not** validate this feature branch.
- The current PR #16 workflow only triggers pushes to `main` and the PR #16 branch; it has no pull-request trigger. The PR #18 read-only PR workflow is a separate, unmerged branch and targets `main`, not this feature PR's base. No workflow was manually dispatched or rerun. Therefore this feature batch has **no CI result yet**; do not claim compilation or tests passed until a natural run validates this exact head.
- No OPPO Reno10 5G install, screenshot, TalkBack session, logcat/tombstone, Perfetto trace, GPU frame-time, memory, thermal or battery measurement was available. Those checks remain **UNVERIFIED**. CI/JVM success cannot establish native launch stability or real-device smoothness.
- The native launch fault remains unproven as fixed until a post-change physical-device launch test is available.

## Next implementation sequence

1. Reader chrome auto-hide: directional thresholds/hysteresis, reveal on upward scroll/tap, pin while selection/ActionMode/definition/IME/accessibility needs controls; no per-pixel Compose updates.
2. Search pass: raise the existing clear affordance to >=48dp while preserving focus, keyboard and filters; keep the current IME morph geometry.
3. Feed-only navigation morph: Today/Explore/Search only; compact active-name pill on sustained downward scroll, expand on upward scroll/top/tab action; prove You/nested screens never collapse.
4. Explore filter touch targets/semantics and selected contrast; You/Saved/stats card geometry; Appearance preview and low-effects option.
5. Whole-app consistency and measured performance/accessibility pass on representative screens.

Do not mark the redesign complete until every screen in the matrix has an intentional material role, paired cost strategy, and verified or explicitly unverified accessibility/performance status.


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
| Feed-only compact navigation | Same bar morphs to active-label pill only on Today/Explore/collapsed Search; You/nested surfaces remain expanded | Run #186 policy tests and #187 integration build passed; visual motion needs device validation |

### Deliberately excluded / superseded

- Older `ui/liquid-glass-performance-2026-11-06` and `redesign/v0.3-search-reader` are stale; surviving useful changes are already present in merged ancestry or superseded.
- PR #15's per-hop RSS and article-page redirect validation is present in the integration code together with the publisher-host policy and focused tests. The branch was not copied wholesale; the equivalent safeguards are retained in the newer feed and Reader repositories.
- No old `MainActivity.kt` was copied wholesale from PR #6/#11/#13/#17/#19/#22. Their independent policies and tests were reconciled while preserving the current Reader, redirect and backdrop code.
- PR #13's original 1.5 s / 2.5 s readback cadence was rejected. The integrated probe is less frequent and idle-only; it still needs OPPO profiling.
- A “bug-free” claim is not supported by CI. Physical-device launch, TalkBack, Reduce Motion, visual transitions, frame timing, memory, thermal and battery remain outstanding.

## Current validation record

- Run #182, exact SHA `f031bf21229f43a992384cc23fa0f4da57e547a1`: passed JVM tests, APK assembly and artifact upload.
- Run #183, exact SHA `d80916691eb5e72ee5448bd543433cdabe2a1e5a`: passed Reader auto-hide and Search clear-target work.
- Runs #184/#185 exposed one threshold-boundary assertion in the new feed-nav policy test; the test was corrected to 43px + 1px without reducing the 44px threshold.
- Run #186, exact SHA `6f15200325d3d333b3abff16c6f290fc81287b9c`: passed the corrected compact-navigation policy test suite and APK build.
- **Run #187, exact SHA `ebdfdd7b22c9164ab4ea24a6fa744e706081fc7f`: passed** `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload. Artifact ID `13559292205`, 39,052,427 bytes, expires 21 November 2026. [Open run #187](https://github.com/Batmancpu/CurrentLens/actions/runs/42334981467).
- The Release job is skipped on the integration branch. No manual dispatch/rerun or main edit was made.
- CI verifies build and JVM tests; physical-device launch, accessibility, animation smoothness and performance remain unverified.
