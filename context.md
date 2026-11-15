# CurrentLens — live context

Updated 15 November 2026 (IST). This is the quick resume note; the full history remains in `docs/SESSION_LEDGER.md`.

## Project and source of truth

- Android app: CurrentLens repository, user-facing app label `brrrNews`.
- GitHub: `Batmancpu/CurrentLens`, `main` only. Latest app source/release commit: `276191f0656261a8f7dfab13847a3e654ca64245`; a documentation-only checkpoint is being added after it. Check live `main` before the next code change.
- Current signed baseline: [0.3.4 (`android-15`)](https://github.com/Batmancpu/CurrentLens/releases/tag/android-15), versionCode 15. Exact run, APK SHA-256 and pinned signer are in `docs/PROJECT_HANDOFF.md` and `docs/SESSION_LEDGER.md`.
- Read this file, `AGENTS.md`, and the mandatory records listed there. GitHub and live source are authoritative if an old snapshot differs.

## Owner's current direction

> “Start working on it ... keep on updating the context files in this repo ... Look at the whole code, compare the idea and add an Apple style reader as well. And make it closest to the Apple product. You have full authority over it.”

Work autonomously in small batches. After each batch, update this file with the exact resume point, append the full record to `docs/SESSION_LEDGER.md`, and refresh `docs/PROJECT_HANDOFF.md`. Keep product and verification evidence distinct; user wants persistent context when switching AI/conversation.

## Product priorities

1. A polished Apple-inspired in-app Reader: comfortable article typography, saved font choice, clear source/date, save/share controls, reading progress and trustworthy UPSC context.
2. Feed reliability and useful refresh status, including partial/all-source failures while preserving cached stories.
3. Improve UPSC eligibility and mapping using the official syllabus and real labelled examples; do not call keyword matches a validated score or prediction.
4. Fix observed UI defects with device evidence where visual/runtime behavior matters.

The owner reports the app opens and articles load; automatic update to 0.3.2 was confirmed. The 0.3.4 source, JVM tests, release assembly, package/version checks and signature passed on Actions run #426. The owner has not yet reported installing 0.3.4 or checking its Reader on a handset.

## Non-negotiables

- Keep exactly Today / Explore / Search / You; Saved stays inside You.
- Preserve the fixed app ID and release signing certificate. Raise versionCode for each published APK and use one workflow at a time.
- Keep the app local-first: no accounts, cloud AI, analytics, telemetry, paid APIs, runtime dictionary requests, paywall bypass, publisher proxy, logged-in cookies, or publisher fallback JavaScript/DOM storage.
- Preserve offline WordNet/licensing, reader selection and progress behavior, Asia/Kolkata date semantics, quiz persistence on submit, and the full Prismal Liquid Glass design.
- CI and unit tests do not prove phone stability, visual correctness, accessibility or frame-time.

## Current batch and next step

- Release source: `276191f0656261a8f7dfab13847a3e654ca64245`; run #426 succeeded and published CurrentLens 0.3.4 / code 15 as `android-15`. APK SHA-256: `8a07dd4ab61e51388bd728375defccfd094de1d9b17cf4415457a0cd924a6d4a`; pinned certificate SHA-256: `af046601fe3a36851e645908e759abbf2caedfe748e2be02817991444884c580`. The sole `CurrentLens.apk` is available from the linked release.
- Reader implementation is shipped: persisted `Aa` sizing, Android font scale for extracted and publisher-fallback content, 48dp save/share controls, refined typography and responsive tables/figures, pinch zoom, source/date, and an explicitly indicative syllabus card when mapping exists. `Aa` stays disabled until the saved preference loads. Sharing is limited to publisher URLs allowed by the existing host policy. No network/publisher-access change was made.
- CI checks passed; this workspace has no Gradle executable or wrapper, so no local test/build ran. Phone appearance, saved text-size update, save/share, selection anchors, progress restore, and publisher fallback remain **unverified on-device**.
- Current handoff batch: reconciling stale issue statuses and generating a complete commit index from the 528 commits reachable at the release SHA. No app source is changed in that documentation audit.
- Next: complete and commit this documentation checkpoint with `[skip ci]`; continue with feed reliability and UPSC real-story evaluation from current main. One workflow has completed; check the queue before any later source push. Do not report live-feed or Reader device validation without evidence.

## Important project history

- The 0.3.3 batch added feed partial-failure reporting and stale-cache preservation, Reader HTTP recovery and feed-excerpt fallback, Reader text-size reload and bounded Search keyboard dismissal, and headline evidence tie-breaking in UPSC classification.
- The latest history and remaining issue IDs are in `docs/SESSION_LEDGER.md` and `docs/KNOWN_ISSUES.md`. The user has not supplied new CurrentLens screenshots in this batch.
