# Liquid Glass UI Roadmap

**Status:** Active, incremental implementation  
**Feature branch:** `ui/liquid-glass-system-2026-11-07`  
**Source branch:** `fix/early-startup-crash-diagnostics-2026-11-06`  
**Source SHA at branch creation:** `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`  
**Draft PR base:** `fix/early-startup-crash-diagnostics-2026-11-06` (PR #16)  
**Baseline check:** source was 57 commits ahead of `main` at `73b8a0d1ba33503fede02db2c3c0af082d724a12`, with zero commits behind. PR #16 is open, draft and unmerged. The source contains the background-only Prismal capture correction; this feature PR intentionally depends on PR #16 until that work is integrated.

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
- PR #13 / `ui/foreground-adaptive-glass-contrast-2026-11-06` — one unique commit based on older code. It proposes an 8×8 GPU readback every 1.5 seconds on newer Android versions. Not ported: the current PR #16 shell deliberately uses theme-derived contrast to avoid recurring readback synchronization, and no OPPO trace demonstrates that the probe is a net improvement.
- PR #18 / `ci/read-only-pr-validation-isolated-release-write-2026-11-06` — independent workflow-only change. Left untouched; this feature branch does not overwrite its read-only build/release separation proposal.
- PRs #6, #8, #11, #12 and #17 contain independent editorial, persistence, date/provenance, Atom-link and URL-deduplication work. They are not assumed to be included in PR #16 and are not blindly cherry-picked into this UI branch. Review their current heads/dependencies before any future integration.

Recheck the entire branch graph, open PRs, merge state and exact source/base SHAs before every subsequent implementation pass. Never reset, force-push, overwrite or discard another branch's commits.

## Screen coverage matrix

| Screen / component | Material tier | Intended treatment | Current status |
|---|---|---|---|
| Shared home header | Primary floating glass | Clear translucent header, restrained edge/specular, stable height across destinations | Existing Prismal glass; contrast/hierarchy polish remains |
| Four-tab navigation | Primary floating glass | One droplet bar with selected glyph aligned to its hit region | Existing Prismal droplet, hidden glyph capture and merged source; no new sampler |
| Compact feed navigation | Primary floating glass | Morph the same bar into one active-destination pill on eligible feed scroll only | Planned; not implemented |
| Search morph field | Interactive control glass | Preserve tab-to-IME geometry and focus; trailing clear affordance | Existing morph and clear callback; clear hit target still needs 48dp |
| Explore category rail | Interactive control glass | One shared capsule; selected fill/border and >=48dp targets | Existing glass rail; touch-target/semantics polish remains |
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
- Replaced the large fixed “Finished reading?” card with a compact Prismal completion dock. It appears only after the existing end detector fires, hides while a definition is open, remains dismissible, and retains the five-question CTA with explicit “not official PYQs” provenance.
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
