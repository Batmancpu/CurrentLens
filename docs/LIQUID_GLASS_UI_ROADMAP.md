# Liquid Glass UI Roadmap

**Status:** Appearance low-effects/reduced-transparency batch authored from the live consolidation head; natural CI for the resulting commit is pending. Reader resume-I/O and supporting-glass code are already present on the branch.  
**Feature branch:** `black-box-bihar-news`  
**Verified source HEAD:** `cfe0b3e8319374df5cb29423f04a79c77d4fe10e`; the new implementation commit is created from this exact head and its SHA is read from the live ref after commit.  
**Integration lineage:** PR #16 stability head `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`; consolidation head is 40 commits ahead / 0 behind PR #16 and 97 ahead / 0 behind main.  
**Last completed successful run:** #200, exact source HEAD `cfe0b3e8319374df5cb29423f04a79c77d4fe10e`, passed WordNet generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload; artifact `13560769755` (39,059,340 bytes). This validates the pre-change head only, not the new low-effects batch.  
**Main:** unchanged at `73b8a0d1ba33503fede02db2c3c0af082d724a12`.  
**Safety:** no source branch has been deleted, no Release has been published, and no workflow has been manually dispatched or rerun.

## Live branch audit and reduced-transparency batch — 7 November 2026, 11:38 IST

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
| Saved | Interactive controls + editorial content | Readable story cards; stable date/GS/subject filters | Saved article/subject/filter derivations memoized in 5e966; grouped filter material remains |
| Reading / Quiz Stats | Supporting glass + editorial data | Shared metric tiles and quiet supporting tint/rim on day/attempt records; data stays high contrast | Integrated in 5e966; summaries memoized; device contrast/performance unverified |
| Reader toolbar | Primary floating glass | Back, source/date/status and compact Aa control; direction-aware auto-hide | Implemented; pinned during selection, definition, IME and accessibility states; device motion unverified |
| Reader progress | Minimal indicator | Reuse existing saved progress; no polling or animated readback | Implemented in this batch |
| Reader article canvas | Editorial content | Clear, high-contrast text; responsive max width and paragraph rhythm | Existing responsive CSS; keep non-glass |
| Reader completion action | Transient contextual glass | Compact, dismissible, only after true completion; hide with reader chrome and avoid definition/action-mode overlap | Implemented; physical safe-area/selection checks unverified |
| Definition popover | Interactive contextual glass | Anchored Prismal surface, full selected word and compact accessible actions | Existing anchored surface; title/actions improved in this batch |
| Quiz questions/options | Editorial content | Calm standard surfaces; persist only on submit | Existing standard surfaces; preserve |
| Quiz action controls | Interactive control glass where useful | Compact, accessible action layer without glassifying questions | Planned |
| Appearance settings | Interactive control glass | Clear↔Frosted slider, presets and small live header/navigation/article preview | Preview integrated; accessible low-effects/reduced-transparency option remains next |
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
| You / stats supporting cards | No live blur; one cached gradient tint and rim on visible cards | Shared `GlassSurfacePolicy`; stable shape/brushes; memoized aggregates; existing LazyColumn composition | No capture/readback or image work added; frame/GPU/memory/battery cost not measured |
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

**Subsequently implemented:** Reader chrome auto-hide/directional pinning, 48dp Search clear, feed-only compact navigation, dashboard/stat supporting surfaces and the Appearance live preview. Low-effects/reduced-transparency preference, grouped Saved/stat filter material and the remaining quiz action-area polish remain future batches.

## Verification status and limitations

- The complete 26-branch graph was re-enumerated on 7 November 2026 at 09:30 IST. The source baseline was `black-box-bihar-news@e411c394e8d48b3f547b6d6fb9039a8935bd05eb`; it was 94 ahead / 0 behind main and 37 ahead / 0 behind PR #16. The current code commit is `5e966a1b2d27bc08e01af3cd40e6573d6e8328be`.
- The last completed natural workflow before the current code commit was run #193 on exact parent SHA `e411c394e8d48b3f547b6d6fb9039a8935bd05eb`. Its build job passed WordNet builder tests/database generation, `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload. Artifact ID `13559372948`, 39,052,734 bytes, SHA-256 `095bb848171516892b33e8e7742d8d46034b7514410179fb002eeb5d274f5c24`. The release job was skipped. This run does **not** validate the newer `5e966a1` commit.
- The current supporting-glass batch adds a focused `GlassSurfacePolicyTest`; its natural CI result is pending. Do not claim this commit compiles or passes until a workflow run reports the exact new SHA.
- No OPPO Reno10 5G install, screenshot, TalkBack session, logcat/tombstone, Perfetto trace, GPU frame-time, memory, thermal or battery measurement is available. Visual transitions, contrast on image backdrops, real-device launch stability and resource cost remain **UNVERIFIED**.
- Source-level cost controls for this batch: no Prismal capture, live blur, refraction, sampler, readback, graphics layer, image decode or dependency was added; only a small gradient tint/rim on already-visible supporting cards. This is a code-scope statement, not a measured performance win.
- The native launch fault remains unproven as fixed until a post-change physical-device launch test is available.

## Next implementation sequence

1. Appearance: add an accessible low-effects/reduced-transparency preference that changes the real shared material parameters; coalesce slider preview updates and avoid whole-app/backdrop rebuilds.
2. Saved and stats controls: group date/GS/subject controls into a restrained shared control material without creating multiple heavy blur plates; preserve 48dp targets and horizontal scroll.
3. Quiz action area and transient menus/sheets: add the same restrained control-glass vocabulary only where it improves hierarchy; keep questions/options and explanatory content on clear surfaces.
4. Whole-app audit: reconcile the header, filters, reader, navigation, dashboard, saved, stats and quiz resting/transient states; run accessibility and visual checks on both light/dark and scaled text.
5. Measure on OPPO Reno10 5G (or explicitly keep device metrics unverified): startup, scroll jank/frame timing, allocation churn, GPU overdraw/layers, thermal and battery against the same base build.

Do not mark the redesign complete until every screen has an intentional material role, paired cost strategy, and verified or explicitly unverified accessibility/performance status.

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
| Supporting glass surfaces | Shared non-blurred tier for You metrics, practice-empty and destinations plus stats day/attempt cards | Code commit 5e966; focused policy tests added; natural CI pending |

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

**Next implementation:** add an accessible low-effects/reduced-transparency preference that changes the real shared Prismal material parameters consistently, with a low-cost fallback and a restrained live preview; then consolidate Saved/stat filters and polish the Quiz action area. Preserve one root backdrop source and do not add a second sampler.
