# Liquid Glass UI Roadmap

**Status:** Shared Prismal coverage spans the primary shell, Reader auto-hide, Search clear, feed-only compact navigation, Appearance preview, supporting dashboard/stat materials and the unified Reader/Quiz transient tier. The Reader Aa menu now pins the toolbar while open and suppresses the completion dock until dismissal; no new render pass or backdrop capture is added.
**Feature branch:** black-box-bihar-news — the sole active integration/UI branch by explicit owner direction.
**Source baseline for this pass:** black-box-bihar-news@818afb47eaadc685f133eaffc3efd199392627e4 (2026-11-07T16:02:13Z); 109 commits ahead / 0 behind main@73b8a0d1ba33503fede02db2c3c0af082d724a12 and 52 ahead / 0 behind PR #16's corrected background-only Prismal/stability head 6ab1e6a02ac8e91027f21900d64d63b9f2e6a890.
**Previous exact active-branch validation:** [PR run #243](https://github.com/Batmancpu/CurrentLens/actions/runs/42389283591) and [push run #242](https://github.com/Batmancpu/CurrentLens/actions/runs/42389254191) both succeeded on exact parent SHA 818afb47eaadc685f133eaffc3efd199392627e4. Job-step summaries confirm WordNet generation, :app:testDebugUnitTest, :app:assembleDebug and artifact upload passed. PR artifact 13580718014 (39,094,949 bytes; expires 21 Nov 2026 16:04:54Z); push artifact 13580578348 (39,094,947 bytes).
**Latest overall natural run:** [#243](https://github.com/Batmancpu/CurrentLens/actions/runs/42389283591), successful PR #34 validation on the exact active-branch parent head; #242 is the matching successful push run. No queued or in-progress Actions jobs remain.
**Actions history:** 243 runs fully paginated (pages 1–3; page 4 empty): 144 success, 58 failure, 41 cancelled; queue empty.
**Workflow / Releases:** active workflow blob 6cef6ca19f3babd331c9deb59be9da0067c6fa66 uses read-only build/test permissions and a separate main-push-only Release-write job. Main workflow blob 37197497288d9f0a60f4d1cfa8453e3dee52a902 remains push-only; nevertheless the exact PR #34 event naturally ran as #243 and passed on the PR head. Latest Release remains v0.2.0-build-116; neither #242 nor #243 published a Release.
**Branch graph:** 37 remote branches (page 1 complete, page 2 empty), 20 open PRs and 14 closed PRs. black-box-bihar-news is 109 ahead / 0 behind main and 52 ahead / 0 behind PR #16. brrrNEWS is divergent (4 ahead / 15 behind); its useful supporting-material implementation is already represented. PR #29/#30/#31 changes are also already represented; PR #27/#32/#33 remain independent editorial/feed candidates, and #25/#26 remain pending a combined publisher-resource security review. See the latest complete table in docs/BRANCH_CONSOLIDATION.md.
**Safety / device status:** no main change, app/package identity change, workflow edit, manual dispatch/rerun, Release publication, PR merge, source-branch mutation or branch deletion. OPPO Reno10 launch, screenshots, transitions, TalkBack/Reduce Motion, Perfetto/frame timing, GPU overdraw, memory, thermal and battery remain UNVERIFIED.

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
| Shared home header | Primary floating glass | Clear translucent header, restrained edge/specular, stable height across destinations | Existing Prismal glass; contrast/hierarchy polish remains |
| Four-tab navigation | Primary floating glass | One droplet bar with selected glyph aligned to its hit region | Existing Prismal droplet, hidden glyph capture and merged source; no new sampler |
| Compact feed navigation | Primary floating glass | Morph the same bar into one active-destination pill on eligible feed scroll only | Implemented; state policy validated in runs #186 and #187; physical animation still needs device validation |
| Search morph field | Interactive control glass | Preserve tab-to-IME geometry/focus; trailing clear affordance only for non-empty expanded query | 48dp clear target and existing onClear/focus path integrated; run #183 passed |
| Explore category rail | Interactive control glass | One shared capsule; selected fill/border and >=48dp targets | 48dp target, selected semantics and Geography filter integrated |
| Today / Explore / Search article cards | Editorial content | Continuous corners, clear typography, image-derived ambient colour; no live blur per card | Existing standard cards, single Coil request and same-bitmap palette |
| You dashboard | Supporting glass | Theme-aware translucent tint/rim, continuous 22dp geometry, single-line metric values | Shared policy tuned: dark surface/tint 0.93/0.11, light 0.965/0.07; no live blur; OPPO contrast unverified |
| Saved | One shared interactive Prismal filter group + editorial cards | Date/GS/subject rails share one 22dp rounded control surface; story cards remain calm, high-contrast content | `GlassFilterGroup` batch authored; one panel, three 48dp horizontal rails; selected filters and existing saved data semantics unchanged |
| Reading / Quiz Stats | One shared interactive Prismal filter group + supporting glass data cards | Date/GS/subject controls use one small live-glass group; metric/day/attempt values stay high contrast | Supporting tint/rim tuned through shared policy; cards remain no-blur; summaries memoized |
| Reader toolbar | Primary floating glass | Back, source/date/status and compact Aa control; direction-aware auto-hide | Implemented; pinned during selection, definition, IME and accessibility states; device motion unverified |
| Reader progress | Minimal indicator | Reuse existing saved progress; no polling or animated readback | Implemented in this batch |
| Reader article canvas | Editorial content | Clear, high-contrast text; responsive max width and paragraph rhythm | Existing responsive CSS; keep non-glass |
| Reader completion action | Transient contextual glass | Compact, dismissible, only after true completion; hide with reader chrome and avoid definition/action-mode overlap | Implemented; physical safe-area/selection checks unverified |
| Definition popover | Interactive contextual glass | Anchored Prismal surface, full selected word and compact accessible actions | Existing anchored surface; title/actions improved in this batch |
| Quiz questions/options | Editorial content | Calm standard surfaces; persist only on submit | Continuous-corner content cards, >=48dp option targets and selected semantics; no live blur; submit-only persistence unchanged |
| Quiz action controls | Interactive control glass | One compact glass identity/header and one safe-area-aware submission dock; keep Done/Submit discoverable | Implemented in the Quiz action-chrome batch; two small surfaces reuse the root backdrop |
| Appearance settings | Standard settings surface + one interactive control-glass preview | Clear↔Frosted slider/presets, four-destination preview, readable article sample and low-effects switch | Controls stay on a calm standard card; one shared 5dp Prismal preview shows header/search/four-tab navigation; slider state is child-scoped and commits app-wide only on release; device validation pending |
| Search filter sheet | Interactive transient glass | One rounded Prismal surface behind the active topic/GS/subject controls; filter state and reset semantics remain unchanged | Shared 5dp transient material; existing root backdrop only; device safe-area and drag behaviour unverified |
| Reader Aa menu | Interactive transient glass | Compact anchored text-size menu; preserve the existing 16–28sp actions and clamping | Same shared transient material, mounted only while expanded; font-size behaviour unchanged |
| Other dialogs, menus and sheets | Contextual controls | Use glass only when transient context benefits; preserve native OS selection/share UI | Review individually; no global glass overlay |

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
| Search filter sheet / Reader Aa menu | One 5dp-blur, low-refraction transient surface while open | Shared `PrismalTransientSurface` consumes the existing background-only root backdrop; no extra capture/readback, no per-chip/item blur, and the two screens cannot show these surfaces together | `GlassRenderingPolicy` removes live optics for Reduce Transparency; policy constants covered by `GlassSurfacePolicyTest`; frame/GPU/memory/battery cost unmeasured |
| You / Reading / Quiz Stats supporting cards | No live blur; theme-aware translucent surface (0.93 dark / 0.965 light), tint (0.11 / 0.07) and restrained rim | Existing Card + remembered gradients/shapes; memoized aggregates; no per-card capture | No capture/readback/image work added; CI policy tests; device/frame/GPU/memory/battery cost unmeasured |
| Saved / Reading / Quiz Stats filter group | One 5dp-blur / low-refraction Prismal surface per visible subpage, containing three compact 48dp rails | Reuses the one background-only root source; no per-row/per-chip blur, no new capture/readback; option de-duplication is remembered | Reduce Transparency routes blur/refraction through `GlassRenderingPolicy`; FilterChip keeps selected semantics and >=48dp width/height; GPU/frame/memory/battery cost unmeasured |
| Appearance preview | One 5dp-blur / low-refraction `PrismalTransientSurface`; controls card and article sample are non-blurred | Reuse the root background-only backdrop and existing transient material tokens; remove the former second live-glass preview panel; slider state remains local until release/preset selection; no image decode or root-state update per drag tick | `GlassSurfacePolicyTest` bounds the transient tier and `GlassRenderingPolicyTest` covers zero-optics fallback; OPPO rendering, frame timing, memory, thermal and battery remain unverified |
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
