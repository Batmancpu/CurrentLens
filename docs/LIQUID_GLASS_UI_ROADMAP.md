# Liquid Glass UI Roadmap

**Status:** Search's filter sheet and the Reader's Aa menu now use one shared, bounded Prismal transient-control material; the modal shell no longer paints an opaque Material slab. This implementation is based on the verified live integration head below; natural CI for the new child commit is pending.  
**Feature branch:** `black-box-bihar-news` — the only active integration branch for this work.  
**Implementation base:** `black-box-bihar-news@16eb549c3235b321ecf7ef1c5c5270817e336489`, verified immediately before editing.  
**Lineage:** 102 commits ahead / 0 behind `main@73b8a0d1ba33503fede02db2c3c0af082d724a12`; 45 ahead / 0 behind PR #16's corrected stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`.  
**Latest active-branch natural validation before this batch:** [Actions run #213](https://github.com/Batmancpu/CurrentLens/actions/runs/42358399254), exact base SHA `16eb549c3235b321ecf7ef1c5c5270817e336489`; four WordNet builder tests passed, the offline database generated 151,456 unique words (50,950,144 bytes), `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload succeeded. Artifact `13568329178`, 39,082,053 bytes, SHA-256 `2a0af7c27ff5aff00b48c12d7659cb5b37b5103f6858ebf090be02b2cc9f0981`; Release job skipped.  
**Latest overall natural run before this batch:** #213 on the active branch; #212 passed on PR #29's separate bookmark-persistence head and #211 passed on PR #27's separate editorial head.  
**Actions history:** 213 runs fully paginated (100 + 100 + 13; page 4 empty): 127 success, 54 failure, 32 cancelled; no queued/in-progress runs.  
**Workflow / Releases:** active workflow blob `6cef6ca19f3babd331c9deb59be9da0067c6fa66` uses read-only build/test permissions, PR triggers for main/PR #16/black-box, and a separate main-push-only Release job. Main's workflow blob `37197497288d9f0a60f4d1cfa8453e3dee52a902` remains push-only with broader write permissions. Latest published Release remains `v0.2.0-build-116`; no Release was published by feature-branch runs.  
**Branch graph:** 33 remote branches; 15 open PRs and 14 closed PRs. The complete live inventory and branch dispositions are recorded in `docs/BRANCH_CONSOLIDATION.md` §12.  
**Safety / device status:** no main change, package/app identity change, workflow change, manual dispatch/rerun, Release publication, PR merge, source-branch write or branch deletion. OPPO Reno10 launch, screenshots, visual motion, TalkBack/Reduce Motion, Perfetto/frame timing, GPU overdraw, memory, thermal and battery remain **UNVERIFIED**.

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
| You dashboard | Supporting glass | Shared non-blurred tinted/rim card, continuous 22dp geometry, single-line metric values | Integrated in code commit 5e966; OPPO contrast check unverified |
| Saved | One shared interactive Prismal filter group + editorial cards | Date/GS/subject rails share one 22dp rounded control surface; story cards remain calm, high-contrast content | `GlassFilterGroup` batch authored; one panel, three 48dp horizontal rails; selected filters and existing saved data semantics unchanged |
| Reading / Quiz Stats | One shared interactive Prismal filter group + supporting glass data cards | Date/GS/subject controls use one small live-glass group; metric/day/attempt values stay high contrast | Grouped rails reuse the root backdrop; supporting cards remain no-blur tint/rim; summaries memoized |
| Reader toolbar | Primary floating glass | Back, source/date/status and compact Aa control; direction-aware auto-hide | Implemented; pinned during selection, definition, IME and accessibility states; device motion unverified |
| Reader progress | Minimal indicator | Reuse existing saved progress; no polling or animated readback | Implemented in this batch |
| Reader article canvas | Editorial content | Clear, high-contrast text; responsive max width and paragraph rhythm | Existing responsive CSS; keep non-glass |
| Reader completion action | Transient contextual glass | Compact, dismissible, only after true completion; hide with reader chrome and avoid definition/action-mode overlap | Implemented; physical safe-area/selection checks unverified |
| Definition popover | Interactive contextual glass | Anchored Prismal surface, full selected word and compact accessible actions | Existing anchored surface; title/actions improved in this batch |
| Quiz questions/options | Editorial content | Calm standard surfaces; persist only on submit | Continuous-corner content cards, >=48dp option targets and selected semantics; no live blur; submit-only persistence unchanged |
| Quiz action controls | Interactive control glass | One compact glass identity/header and one safe-area-aware submission dock; keep Done/Submit discoverable | Implemented in the Quiz action-chrome batch; two small surfaces reuse the root backdrop |
| Appearance settings | Interactive control glass | Clear↔Frosted slider, presets, live header/navigation/article preview and low-effects switch | Preview and reduced-transparency mode integrated; slider changes apply to shared material policy; device validation pending |
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
| You / stats supporting cards | No live blur; one cached gradient tint and rim on visible cards | Shared `GlassSurfacePolicy`; stable shape/brushes; memoized aggregates; existing LazyColumn composition | No capture/readback or image work added; frame/GPU/memory/battery cost not measured |
| Saved / Reading / Quiz Stats filter group | One 5dp-blur / low-refraction Prismal surface per visible subpage, containing three compact 48dp rails | Reuses the one background-only root source; no per-row/per-chip blur, no new capture/readback; option de-duplication is remembered | Reduce Transparency routes blur/refraction through `GlassRenderingPolicy`; FilterChip keeps selected semantics and >=48dp width/height; GPU/frame/memory/battery cost unmeasured |
| Appearance preview | Existing small preview surfaces | Reuse current slider value and existing components; no whole-screen blur or repeated image decode | Reduced-transparency/low-effects preference is integrated; OPPO rendering and slider-response measurements remain unverified |
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

1. Await natural CI on this Quiz action-chrome commit; inspect the exact run, full logs and artifact before another UI batch.
2. Review transient sheets/menus and any remaining quiz contextual actions; keep dense question, option and explanatory content on clear surfaces.
3. Whole-app audit: reconcile header, filters, Reader, navigation, dashboard, Saved, stats and quiz resting/transient states; check light/dark, large text, TalkBack and Reduce Motion.
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

On the next scheduled run, inspect the natural build for the exact transient-surface commit, including the full job log, JVM tests and APK artifact. If it passes, continue the remaining app-wide audit with an Appearance preview cost pass (avoid two simultaneously blurred preview panels) and reconcile the separately validated PR #29 bookmark IO change only as its own scoped performance batch. Do not claim OPPO visual or performance verification from CI.



## Transient Search and Reader controls batch — 7 November 2026

**Implementation base:** `black-box-bihar-news@16eb549c3235b321ecf7ef1c5c5270817e336489`; branch freshness and all 33 remote refs were rechecked before editing. The implementation is one direct child on the same branch; natural CI is pending for that new head.

- Added the shared `PrismalTransientSurface` wrapper and explicit `GlassSurfacePolicy` tokens for a restrained 5dp blur, 2.5dp refraction height, 3.5dp refraction amount and 0.42 tint scale.
- Search's Refine Articles sheet now has a transparent Material shell and one rounded Prismal surface. The drag handle is inside that surface; Topic, GS paper, Subject, Reset and Done retain their original state and callbacks. The three filter rails remain standard 48dp chips; no per-chip blur or duplicated filter panel was added.
- Reader's Aa dropdown now uses the same shared surface while expanded. The existing smaller/larger/current-size actions, font-size bounds, dismiss behaviour and reader text state are unchanged.
- Both transient surfaces consume the existing background-only `PrismalBackdrop`. They are conditionally composed only while their own sheet/menu is open; Search and Reader are mutually exclusive destinations. No new capture, sampler, readback, bitmap decode, graphics layer, shader, timer, dependency, network request or persistence change was added.
- Reduce Transparency routes the shared material through the existing zero-blur/zero-refraction fallback and higher tint reserve. Added policy-bound tests to `GlassSurfacePolicyTest`; the full `:app:testDebugUnitTest` suite and APK assembly must run naturally on this exact commit.
- **Unverified:** OPPO Reno10 screenshots, sheet drag/IME/safe-area interaction, Aa popup placement, TalkBack, Reduce Motion, Perfetto/frame timing, GPU overdraw, allocation/memory, thermal and battery. No device or performance claim is made.
