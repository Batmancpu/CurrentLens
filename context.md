# CurrentLens — live context

Updated 15 November 2026 (IST). This is the quick resume note; the full history remains in `docs/SESSION_LEDGER.md`.

## Project and source of truth

- Android app: CurrentLens repository, user-facing app label `brrrNews`.
- GitHub: `Batmancpu/CurrentLens`, `main` only. Current base: `365110f8364e1b1aa88c78d3ca1d88e88e1c96d9` (clean checkout; `origin/main` matched).
- Current signed baseline: 0.3.3, versionCode 14, release tag `android-14`; same pinned signer as 0.3.1/0.3.2. Exact release/run details are in `docs/PROJECT_HANDOFF.md` and `docs/SESSION_LEDGER.md`.
- Read this file, `AGENTS.md`, and the mandatory records listed there. GitHub and live source are authoritative if an old snapshot differs.

## Owner's current direction

> “Start working on it ... keep on updating the context files in this repo ... Look at the whole code, compare the idea and add an Apple style reader as well. And make it closest to the Apple product. You have full authority over it.”

Work autonomously in small batches. After each batch, update this file with the exact resume point, append the full record to `docs/SESSION_LEDGER.md`, and refresh `docs/PROJECT_HANDOFF.md`. Keep product and verification evidence distinct; user wants persistent context when switching AI/conversation.

## Product priorities

1. A polished Apple-inspired in-app Reader: comfortable article typography, saved font choice, clear source/date, save/share controls, reading progress and trustworthy UPSC context.
2. Feed reliability and useful refresh status, including partial/all-source failures while preserving cached stories.
3. Improve UPSC eligibility and mapping using the official syllabus and real labelled examples; do not call keyword matches a validated score or prediction.
4. Fix observed UI defects with device evidence where visual/runtime behavior matters.

The owner reports the app opens and articles load; automatic update to 0.3.2 was confirmed. The 0.3.3 source run passed, but its updated feed/Reader behavior has not been reported as handset-verified.

## Non-negotiables

- Keep exactly Today / Explore / Search / You; Saved stays inside You.
- Preserve the fixed app ID and release signing certificate. Raise versionCode for each published APK and use one workflow at a time.
- Keep the app local-first: no accounts, cloud AI, analytics, telemetry, paid APIs, runtime dictionary requests, paywall bypass, publisher proxy, logged-in cookies, or publisher fallback JavaScript/DOM storage.
- Preserve offline WordNet/licensing, reader selection and progress behavior, Asia/Kolkata date semantics, quiz persistence on submit, and the full Prismal Liquid Glass design.
- CI and unit tests do not prove phone stability, visual correctness, accessibility or frame-time.

## Current batch and next step

- Starting source: clean `main@365110f8364e1b1aa88c78d3ca1d88e88e1c96d9`; release 0.3.3 / code 14.
- Reader implementation is now in the worktree: persist the `Aa` preference across Reader entries, apply Android font scale to extracted and publisher-fallback content, add 48dp save/share controls, refine the title/body/line-width/table/image treatment, keep pinch zoom enabled, and append an explicitly indicative GS/syllabus card when mapping exists. The `Aa` control stays disabled until the saved preference loads. Sharing is limited to publisher URLs allowed by the existing host policy. Network and publisher access rules are unchanged.
- Release identity for this requested update is prepared as 0.3.4 / versionCode 15. Focused font-scale, HTML-escaping and no-mapping tests were added. `git diff --check` passes. This workspace has no Gradle executable or wrapper, so tests/build have not run locally; the serial main workflow must validate them.
- Next: rerun `git diff --check`, stage and inspect the complete diff, check the live Actions queue, then commit/push fast-forward on `main` with `[publish apk]`. Wait for that single run and verify JVM tests, release build, exact app ID/version, pinned signer and signed `android-15` asset. Update this file and the ledger again with the exact run, signer, asset hash and device-verification status. Feed/UPSC work follows after this workflow completes.

## Important project history

- The 0.3.3 batch added feed partial-failure reporting and stale-cache preservation, Reader HTTP recovery and feed-excerpt fallback, Reader text-size reload and bounded Search keyboard dismissal, and headline evidence tie-breaking in UPSC classification.
- The latest history and remaining issue IDs are in `docs/SESSION_LEDGER.md` and `docs/KNOWN_ISSUES.md`. The user has not supplied new CurrentLens screenshots in this batch.
