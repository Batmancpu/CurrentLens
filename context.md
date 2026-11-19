# CurrentLens — live context

Updated 19 November 2026 (IST). This is the quick resume note; the full history remains in `docs/SESSION_LEDGER.md`.

## Active 19 November batch — 0.3.7 candidate, not yet published

- Live starting `main`: `c58ab1cf1375741cb88020384aae32a8c5663671`. Last successful release remains 0.3.6 / code 17 / `android-17`, run #428, and the pinned signing certificate below. At the start of this batch the Actions queue was empty (428 completed runs) and GitHub listed zero open PRs.
- New bounded scope: add precise GS-III Infrastructure signals using the official 6 November NHAI multi-lane tolling notice; filter invalid/undated and materially future-dated RSS articles **before** they consume the 250-story fetch budget (previously only the later cache layer rejected them). Add focused JVM tests and a source-labelled editorial evidence register; preserve saved cache entries. Proposed APK identity: 0.3.7 / code 18, same package and pinned certificate.
- Code/test modifications currently local, **tests and CI not yet run**. Do not claim 0.3.7 is installable or signed until the exact-head workflow passes, produces `android-18`, and its APK/certificate are checked. The workflow is expected to publish only when the reviewed main commit message has `[publish apk]`.
- Next: review local diff and docs ledger, verify remote head still equals starting SHA and no Actions run overlaps; advance `main` by guarded fast-forward, poll the exact run until terminal. On the first failure inspect the job step/log, fix and rerun serially. After success, record source SHA, run, tag, APK SHA-256/signature and stop after this single release. Phone acceptance and broader roadmap remain unverified/outstanding.

## Project and source of truth

- Android app: CurrentLens repository, user-facing app label `brrrNews`.
- GitHub: `Batmancpu/CurrentLens`, `main` only. Latest APK source SHA: `779c6423e91b77620bec2f5afca2bae6c4b1df3c` (0.3.6); docs-only handoff checkpoint `f9c8baf7ad5a219c221549e017181e3b818ddc53` follows it. Read live `main` for the current working base.
- Current signed release: [0.3.6 (`android-17`)](https://github.com/Batmancpu/CurrentLens/releases/tag/android-17), versionCode 17, published from exact source `779c6423e91b77620bec2f5afca2bae6c4b1df3c` by successful run #428. `CurrentLens.apk` is 29.4 MB per GitHub, SHA-256 `3bd5cbe0b43d0ac09ef782968c4c93748d78afeff29e3eebe648e11cf2e925dc`; pinned certificate SHA-256 `af046601fe3a36851e645908e759abbf2caedfe748e2be02817991444884c580`.
- Read this file, `AGENTS.md`, and the mandatory records listed there. GitHub and live source are authoritative if an old snapshot differs.

## Owner's current direction

> “Start working on it ... keep on updating the context files in this repo ... Look at the whole code, compare the idea and add an Apple style reader as well. And make it closest to the Apple product. You have full authority over it.”

Latest steering: “remember to stop after releasing a good acceptable APK. Don't run endlessly. Okay? So I am hoping you stop maximum in an hour or half an hour if possible.” This supersedes the earlier request for unbounded continuous work. Stop after the current release handoff is recorded; do not start another code or APK batch in this session.

Work autonomously in small batches within that bound. After each batch, update this file with the exact resume point, append the full record to `docs/SESSION_LEDGER.md`, and refresh `docs/PROJECT_HANDOFF.md`. Keep product and verification evidence distinct; user wants persistent context when switching AI/conversation.

## Product priorities

1. A polished Apple-inspired in-app Reader: comfortable article typography, saved font choice, clear source/date, save/share controls, reading progress and trustworthy UPSC context.
2. Feed reliability and useful refresh status, including partial/all-source failures while preserving cached stories.
3. Improve UPSC eligibility and mapping using the official syllabus and real labelled examples; do not call keyword matches a validated score or prediction.
4. Fix observed UI defects with device evidence where visual/runtime behavior matters.

The owner reports the app opens and articles load; automatic update to 0.3.2 was confirmed. The 0.3.6 source, JVM tests, release assembly, package/version checks and signature passed on Actions run #428. The owner has not yet reported installing 0.3.6 or checking the Reader, DPDP card or NBS mapping on a handset.

## Non-negotiables

- Keep exactly Today / Explore / Search / You; Saved stays inside You.
- Preserve the fixed app ID and release signing certificate. Raise versionCode for each published APK and use one workflow at a time.
- Keep the app local-first: no accounts, cloud AI, analytics, telemetry, paid APIs, runtime dictionary requests, paywall bypass, publisher proxy, logged-in cookies, or publisher fallback JavaScript/DOM storage.
- Preserve offline WordNet/licensing, reader selection and progress behavior, Asia/Kolkata date semantics, quiz persistence on submit, and the full Prismal Liquid Glass design.
- CI and unit tests do not prove phone stability, visual correctness, accessibility or frame-time.

## Current batch and next step

- Latest release: source `779c6423e91b77620bec2f5afca2bae6c4b1df3c`; run [#428](https://github.com/Batmancpu/CurrentLens/actions/runs/43733493845) succeeded and published CurrentLens 0.3.6 / code 17 as `android-17`. APK SHA-256: `3bd5cbe0b43d0ac09ef782968c4c93748d78afeff29e3eebe648e11cf2e925dc`; pinned certificate SHA-256: `af046601fe3a36851e645908e759abbf2caedfe748e2be02817991444884c580`. The release contains one `CurrentLens.apk` asset.
- Reader implementation is shipped: persisted `Aa` sizing, Android font scale for extracted and publisher-fallback content, 48dp save/share controls, refined typography and responsive tables/figures, pinch zoom, source/date, and an explicitly indicative syllabus card when mapping exists. `Aa` stays disabled until the saved preference loads. Sharing is limited to publisher URLs allowed by the existing host policy. No network/publisher-access change was made.
- Shipped DPDP mapping: `UpscEngine.kt` and `UpscFeedPolicy.kt` recognize exact DPDP regulatory concepts; `UpscFeedPolicyTest.kt` covers the official PIB title/body and a generic digital-portal negative. Run #427 passed the JVM suite and release assembly. This is one source-based regression fixture, not a precision/recall evaluation.
- 0.3.6 fix: `UpscEngine.kt` and `UpscFeedPolicy.kt` add the exact `nutrient based subsidy` signal. `UpscFeedPolicyTest.kt` contains a title-only fixture for PIB PRID 2334972, asserts GS III / Agriculture, matched phrase and eligibility, and includes a generic consumer-internet subsidy negative. The fixture and release passed run #428. This is one source-based regression fixture, not a precision/recall evaluation.
- Current GitHub check: run [#428](https://github.com/Batmancpu/CurrentLens/actions/runs/43733493845) completed successfully on exact source `779c6423e91b77620bec2f5afca2bae6c4b1df3c`; dictionary verification/bundling, `:app:testDebugUnitTest`, `:app:assembleRelease`, package/version validation, pinned signer verification and publication all passed. Release [`android-17`](https://github.com/Batmancpu/CurrentLens/releases/tag/android-17) is Latest and its tag points to that source. GitHub reports one APK asset, `CurrentLens.apk`, displayed as 29.4 MB. GitHub reported zero open PRs in the latest check. Direct `git push` lacked CLI credentials, so the connected GitHub Git Data API advanced `main` with an expected-parent guard.
- Phone install, saved text-size update, save/share, selection anchors, progress restore, publisher fallback, native launch stability and live-feed behavior remain **unverified on-device**. Do not claim editorial precision/recall from these individual source fixtures or keyword rules.
- Resume action: stop here. The 0.3.6 / `android-17` APK passed run #428 and is published; the release handoff is current. Do not begin another code or APK batch in this session. When the owner resumes, the next planned work is a small labelled real-story sample, followed by the RSS/refresh/cache audit. Reader visuals, phone behavior, live feeds and corpus-level precision/recall remain unverified.

## Important project history

- The 0.3.3 batch added feed partial-failure reporting and stale-cache preservation, Reader HTTP recovery and feed-excerpt fallback, Reader text-size reload and bounded Search keyboard dismissal, and headline evidence tie-breaking in UPSC classification.
- The latest history and remaining issue IDs are in `docs/SESSION_LEDGER.md` and `docs/KNOWN_ISSUES.md`. The user has not supplied new CurrentLens screenshots in this batch.
