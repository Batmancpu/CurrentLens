# CurrentLens — live context

Updated 24 November 2026 (IST). Full history: `docs/SESSION_LEDGER.md`.

## Verified release checkpoint — 24 November 2026 (IST)

- Owner request (verbatim): “Finish whtas  left”. Scope: close the interrupted 0.3.7 release-verification and documentation checkpoint. The bounded-release rule remains in force; no new APK is needed just to correct documentation.
- Main at inspection: `3fe98e8ebdf28c5ab11f37e3de727dee691a704e` (0.3.7 / versionCode 18). [Actions run #429](https://github.com/Batmancpu/CurrentLens/actions/runs/44581595658) completed successfully on this exact source. Build and publish jobs passed, including offline WordNet generation, JVM tests, release assembly, package/version validation, signing verification and publication. [Release android-18](https://github.com/Batmancpu/CurrentLens/releases/tag/android-18) is Latest; its tag points to the exact source SHA. GitHub returned one `CurrentLens.apk` asset (30,830,942 bytes), SHA-256 `51e01593ce4ee2b38bef2a463b66548c364ee12ef507dc729b6268bdee44ff43`, matching the release notes. Signing certificate SHA-256 `af046601fe3a36851e645908e759abbf2caedfe748e2be02817991444884c580` matches the pinned certificate from the prior release. [Direct APK](https://github.com/Batmancpu/CurrentLens/releases/download/android-18/CurrentLens.apk).
- 0.3.7 changes shipped: incoming RSS undated/future-date rejection before the 250-story cap; saved-cache behavior retained; narrowly scoped GS III Infrastructure mapping and positive/negative regression tests. This is CI evidence, not a measured publisher-corpus evaluation.
- No queued or in-progress runs and no open PRs were returned by GitHub during the audit. No new app-source, package, version, signing, dependency or workflow changes in this checkpoint.
- Remaining: handset install-over and retained data, repeated startup, Reader and Search/IME visuals, live RSS source/refresh behavior, accessibility and performance, representative labelled UPSC corpus and independent primary-source corroboration. Study-tool and maintainability roadmap items remain open; do not present them as shipped.
- Resume: stop after recording this already-published release. On the next development batch, read current main and this record, prioritize reproducible live-feed defects and labelled editorial cases, and preserve one-workflow/one-release sequencing. No device acceptance has been received for 0.3.7.

## Product and operating constraints

- Android app CurrentLens, user-facing label brrrNews; repository `Batmancpu/CurrentLens`, main only. Exactly Today / Explore / Search / You; Saved stays inside You.
- Apple-inspired Reader, reliable cache-preserving feeds, defensible UPSC syllabus mapping and UI polish remain the priorities. User previously confirmed app opens, articles load and an automatic update to 0.3.2 worked; this is not acceptance of 0.3.7.
- Keep the fixed package and signing identity, local-first storage, offline WordNet, Asia/Kolkata dates, reader selection/progress, quiz persistence on submit and full Prismal Liquid Glass. No analytics, cloud AI, accounts, paywall bypass, publisher proxy, logged-in cookies, or publisher fallback JavaScript/DOM storage.
- Read `AGENTS.md`, `docs/PROJECT_HANDOFF.md`, `docs/ROADMAP.md` and the mandatory records. Continue in small batches; update context and ledger, and stop after one acceptable signed release. CI is not device, accessibility, visual or editorial-population evidence.
