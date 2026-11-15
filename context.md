# CurrentLens — live context

Updated 15 November 2026 (IST). This is the quick resume note; the full history remains in `docs/SESSION_LEDGER.md`.

## Project and source of truth

- Android app: CurrentLens repository, user-facing app label `brrrNews`.
- GitHub: `Batmancpu/CurrentLens`, `main` only. Latest app source/release commit: `307a209bc4f871871c08fe7fe0bebfb74764c01f`; main has only a post-release docs/index continuation after this source. Read the live main tip before the next change.
- Current signed baseline: [0.3.5 (`android-16`)](https://github.com/Batmancpu/CurrentLens/releases/tag/android-16), versionCode 16, published from exact source `307a209...` by successful run #427. APK SHA-256 `3570a3066cfc21a05bce83983cedd609722dfd07b198dff70a32b8c85f6fb4c9`; pinned certificate SHA-256 remains `af046601fe3a36851e645908e759abbf2caedfe748e2be02817991444884c580`.
- Read this file, `AGENTS.md`, and the mandatory records listed there. GitHub and live source are authoritative if an old snapshot differs.

## Owner's current direction

> “Start working on it ... keep on updating the context files in this repo ... Look at the whole code, compare the idea and add an Apple style reader as well. And make it closest to the Apple product. You have full authority over it.”

Work autonomously in small batches. After each batch, update this file with the exact resume point, append the full record to `docs/SESSION_LEDGER.md`, and refresh `docs/PROJECT_HANDOFF.md`. Keep product and verification evidence distinct; user wants persistent context when switching AI/conversation.

## Product priorities

1. A polished Apple-inspired in-app Reader: comfortable article typography, saved font choice, clear source/date, save/share controls, reading progress and trustworthy UPSC context.
2. Feed reliability and useful refresh status, including partial/all-source failures while preserving cached stories.
3. Improve UPSC eligibility and mapping using the official syllabus and real labelled examples; do not call keyword matches a validated score or prediction.
4. Fix observed UI defects with device evidence where visual/runtime behavior matters.

The owner reports the app opens and articles load; automatic update to 0.3.2 was confirmed. The 0.3.5 source, JVM tests, release assembly, package/version checks and signature passed on Actions run #427. The owner has not yet reported installing 0.3.5 or checking the Reader or DPDP card on a handset.

## Non-negotiables

- Keep exactly Today / Explore / Search / You; Saved stays inside You.
- Preserve the fixed app ID and release signing certificate. Raise versionCode for each published APK and use one workflow at a time.
- Keep the app local-first: no accounts, cloud AI, analytics, telemetry, paid APIs, runtime dictionary requests, paywall bypass, publisher proxy, logged-in cookies, or publisher fallback JavaScript/DOM storage.
- Preserve offline WordNet/licensing, reader selection and progress behavior, Asia/Kolkata date semantics, quiz persistence on submit, and the full Prismal Liquid Glass design.
- CI and unit tests do not prove phone stability, visual correctness, accessibility or frame-time.

## Current batch and next step

- Release source: `307a209bc4f871871c08fe7fe0bebfb74764c01f`; run [#427](https://github.com/Batmancpu/CurrentLens/actions/runs/43730681996) succeeded and published CurrentLens 0.3.5 / code 16 as `android-16`. APK SHA-256: `3570a3066cfc21a05bce83983cedd609722dfd07b198dff70a32b8c85f6fb4c9`; pinned certificate SHA-256: `af046601fe3a36851e645908e759abbf2caedfe748e2be02817991444884c580`. The sole `CurrentLens.apk` is available from the linked release.
- Reader implementation is shipped: persisted `Aa` sizing, Android font scale for extracted and publisher-fallback content, 48dp save/share controls, refined typography and responsive tables/figures, pinch zoom, source/date, and an explicitly indicative syllabus card when mapping exists. `Aa` stays disabled until the saved preference loads. Sharing is limited to publisher URLs allowed by the existing host policy. No network/publisher-access change was made.
- Shipped DPDP mapping: `UpscEngine.kt` and `UpscFeedPolicy.kt` recognize exact DPDP regulatory concepts; `UpscFeedPolicyTest.kt` covers the official PIB title/body and a generic digital-portal negative. Run #427 passed the JVM suite and release assembly. This is one source-based regression fixture, not a precision/recall evaluation.
- Current GitHub check: run #427 is completed/successful on `307a209...`; release `android-16` is Latest with one APK asset. The latest check returned zero open PRs. The docs-only checkpoint is the only current local work. Direct `git push` previously failed for missing CLI credentials; if needed, use the connected GitHub Git Data API with an expected-parent fast-forward guard.
- Phone install, saved text-size update, save/share, selection anchors, progress restore, publisher fallback, native launch stability and live-feed behavior remain **unverified on-device**. Do not claim editorial precision/recall from this one fixture or keyword rules.

## Important project history

- The 0.3.3 batch added feed partial-failure reporting and stale-cache preservation, Reader HTTP recovery and feed-excerpt fallback, Reader text-size reload and bounded Search keyboard dismissal, and headline evidence tie-breaking in UPSC classification.
- The latest history and remaining issue IDs are in `docs/SESSION_LEDGER.md` and `docs/KNOWN_ISSUES.md`. The user has not supplied new CurrentLens screenshots in this batch.
