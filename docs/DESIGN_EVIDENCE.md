# CurrentLens — Design Intent and Visual Evidence Register

This file preserves visual intent and the evidence actually available. It distinguishes user-provided source images, descriptions from conversation history, implementation assets and assistant inference.

## Product-wide visual target

- Android reading app with a restrained, high-quality Liquid Glass design language; quality target described as Apple-level polish, not a generic Material template or obviously AI-generated visual.
- Glass must support legibility and navigation first. Material effects, translucency and motion are subordinate to accessibility, battery, memory and scroll stability.
- Feed should feel like a deliberate editorial reading product: strong hierarchy, controlled spacing, clear typography, restrained image treatment, useful skeleton states and minimal noise.
- Visual system should be coherent across Today, Explore, Search, You, reader, dictionary overlays and navigation.
- Avoid visual novelty that introduces stutter, layout jumps, inaccessible contrast, unnecessary blur or excess vertical space.

## Navigation and glass

- Exactly four destinations: Today / Explore / Search / You. Saved remains within You.
- Bottom bar is icon-led/icon-only where appropriate, with clear active-state contrast and accessible labels.
- Selected glyph must remain crisp and visible above active liquid/glass lens. Do not draw it twice or let the lens blur it.
- Active/inactive states need strong contrast over light and dark content behind transparent material.
- Explore filters should sit inside same material language as navigation, with coherent translucent selected states and at least 48dp interaction targets.
- Continuous/squircle corners are preferred over generic hard rounded rectangles.

## Article cards and images

- User feedback targeted an oversized dark inset image frame and duplicate/blurred imagery.
- Desired direction: image should meet card edge cleanly; card uses continuous corners; subtle ambient colour wash may be derived from same already-loaded image.
- Previous implementation drew a second image copy and applied a 30dp blur behind every visible card. Deliberately removed in PR #3 because expensive during scrolling.
- PR #3 uses one Coil image result and a one-time nine-point sample to drive a light radial palette wash. Do not reintroduce second image decode or continuous per-card blur.
- If article URL's image changes, invalidate prior palette; stale colour must not bleed into replacement article card. Proposed in PR #6, not confirmed on main.

## Search and keyboard / IME

The user reported:
- Search field sits much too far above keyboard instead of naturally just above it.
- Gap looks like a layout bug.
- Search field movement when keyboard opens/closes is stuttery.
- Morph between Search field and selected bottom tab is not fluid enough.
- Keyboard/system-bar backdrop should visually integrate; avoid opaque dark/black regions behind transparent Search/IME.

Historical implementation work includes Search morphing from bottom tab, Compose IME inset observation, geometry-aware anchor/fallback and preserving capsule geometry. These changes do not mean latest visual behaviour is physically verified. Re-check on user's actual device, Android version and active keyboard.

## Reader and dictionary visuals

- Reader prioritizes comfortable reading, clear article dates/provenance and stable geometry.
- Dictionary definition should visually grow from selected word and stay anchored to word, while remaining inside reader viewport on compact screens.
- Definition cards compact-first and expandable rather than a large fixed popover.
- Preserve native WebView text selection and multi-word selections. Avoid auto-defining stale text after user changes selection, interrupts touch, uses multi-touch or scrolls.
- Keep pronunciation data/features out unless user explicitly asks to restore them: offline CMUdict US pronunciation was added and later removed by commit 032cb41f7ca5.

## Loading, motion and accessibility

- Skeleton cards preserve final layout and reduce empty-state jumps.
- Avoid continuously animated shimmer loop on scroll-sensitive feeds unless measured and requested.
- Search morph and keyboard transitions should be fluid but not springy/overshooting in a way that harms position.
- Support TalkBack descriptions/selected state, 48dp touch targets, font scaling and Reduce Motion.
- Respect safe areas, system bars, edge-to-edge and OEM variation.
- Do not use high-frequency GPU readbacks or blur shaders in every list cell. One shared low-cadence sampler is the upper bound proposed in PR #13; even this needs physical-device validation.

## Launcher icon: user intent and current vector output

### User's explicit feedback

- Earlier icon looked too AI-generated.
- User wanted the Liquid Glass design language.
- Central symbol inside rounded square/circle-like icon container should read as a newspaper/news sheet.
- Icon should look intentionally designed and recognizable at launcher size, not like a generic AI illustration.
- Visual identity should be name-independent; no wordmark printed in icon.

### Current source asset inspected on main

File: artwork/brrrnews-icon.svg. Introduced by commit c5e7746b2487 (6 Nov 2026).

Source facts:
- SVG viewBox 108 × 108.
- Outer dark surround #0C1523 with 30-unit rounded rectangle.
- Inner tile 96 × 96, x/y 6, radius 27.
- Tile gradient #334F70 → #20364F → #111E30.
- Glass-edge treatment uses white/blue translucent strokes.
- Central newspaper sheet is a pale translucent paper panel, approx x=14–93, y=11–97, rounded with glass highlight.
- Content: dark headline bar, small blue landscape/photo block with a sun and mountain-like shapes, several article text lines and divider rules.
- Cool navy/blue/white palette; no visible brand wordmark.
- Android vector/adaptive launcher resources include ic_brrrnews_foreground.xml, ic_launcher.xml, ic_launcher_round.xml and values/colors.xml.
- This artwork is implementation output, not the user's original reference.

### Branding case discrepancy

- User previously specified brrrNEWS (lowercase b + three lowercase r's + uppercase NEWS).
- Later main commits initially set brrrNEWS, then commits 36fa410f8af9, cae53afc7eb1, a277d9bf1737 and 73b8a0d1ba33 changed display/copy/theme/README/SVG accessibility text to brrrNews.
- Current SVG aria-label: “brrrNews glass newspaper app icon”.
- Confirm canonical user-facing case before any future branding edits; do not silently oscillate.
- Repository remains CurrentLens; app display identity is separate.

## Historical screenshot/video evidence referenced in PR descriptions

PR #3 records supplied Android test device screenshots showing:
1. Flat Today/Explore/Search/You header bands.
2. Explore topic chips visually unrelated to rest of UI.
3. Selected Today lens blurring its own navigation icon.
4. Large rounded-rectangle/dark inset framing around article images.
5. Search keyboard/system-bar backdrop visually disconnected.
6. Empty/loading state needed more intentional skeleton presentation.

PR #3 records the design/performance response:
- restrained Prismal glass toolbar,
- Explore chips inside shared frosted band,
- crisp selected glyph above active lens,
- one image decode with one-time nine-point palette sample instead of second 30dp blur,
- squircle cards and image-to-card-edge geometry,
- static card skeletons,
- transparent Search/IME surfaces.

Earlier/later chat summaries preserve the Search keyboard gap and morph stutter. Original screenshot/video bytes are not currently stored in repository; pixel-level measurements and exact colour/spacing cannot be reconstructed from text.

## Evidence handling for every future image/video

When user supplies image/screenshot/recording/reference, add an entry containing:
- Stable evidence ID and original filename.
- Date/time received and app build/commit if visible or supplied.
- Device/model, Android version, display size/density, theme and keyboard if known.
- Screen and interaction state (scroll position, tab, keyboard open/closed, loading/loaded, selected item).
- What is actually visible; no inference of hidden implementation.
- User's exact criticism/request in their wording where possible.
- Required change and acceptance criteria.
- Related source file, issue ID, PR and resulting commit once implemented.
- Whether original is stored, redacted, private-only or unavailable.

Do not upload private screenshots to this public repo without explicit approval. Do not invent dimensions, colour values, spacing or timing from memory. If original unavailable, state that only textual observation is retained.


## Liquid Glass and startup-isolation boundary (6 November 2026)
The owner explicitly wants the original expensive Liquid Glass effects preserved. Stability-only builds may use a plain first-screen probe before the full composition; this is not a replacement design. Normal app builds must use the complete Prismal automatic pipeline. Do not use the flat diagnostic screen as the default production appearance or call it a visual redesign.
