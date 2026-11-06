# CurrentLens — Detailed Commit and Change Notes

This file supplements MAIN_COMMIT_INDEX.md. The index lists every main commit; this file explains the important milestones whose actual GitHub diffs were inspected in this continuity session. Do not infer the contents of an uninspected commit from its subject alone. Open the linked commit for its complete diff.

## Initial project and build evolution

### aa0521719a53 — initialise CurrentLens project
- Initial repository/project bootstrap.
- The first README described a native Kotlin/Compose news reader, publisher RSS discovery/excerpts, UPSC relevance mapping, article-end questions and system-wide word lookup.
- Initial product principles included no accounts, subscriptions, paid APIs, AI runtime, telemetry or tracking.
- The exact first code state is available in the commit, but the diff inspected in this pass showed the README addition only; do not overstate what that first commit included.

### 58d5cc11d0a0 — redesign with Liquid Glass and daily editorial navigation
Files inspected included workflow/build settings, README, third-party notices, app Gradle/manifest, reader and feed repositories, LocalStore, theme resources and settings.
- Shifted the app from a basic reader description toward a local-first editorial UI with Today-oriented daily navigation and Liquid Glass.
- Raised compile SDK from 35 to 37, moved version from 0.1.0/code1 to 0.2.0/code2, increased Gradle JVM heap from 3GB to 4GB and workflow timeout from 25 to 40 minutes.
- Added concurrent feed fetching and date parsing infrastructure to NewsRepository.
- Expanded local article-cache/bookmark/settings persistence.
- Added/expanded public reader extraction infrastructure.
- Added Maven/JitPack dependency repository configuration for Prismal.
- Workflow/build changes from this early period are superseded by the current single workflow; do not restore old coordinates or assumptions.

### 6d2f8576e3c0 — replace Archive with morphing glass Search and four-tab navigation
- Changed the UI/navigation implementation in MainActivity.
- Replaced Archive with Search and established a four-destination bottom navigation.
- Added Compose text field, IME action, focus-requester and related search keyboard/morph APIs.
- This is a key historical transition. The durable product rule is exactly Today, Explore, Search and You; no Archive.

## Offline dictionary and study features

### f05d51c9bdfc — add offline dictionary and refined glass interactions
Files inspected: workflow, third-party notices, WordNet attribution, DictionaryActivity, DictionaryWordPolicy, MainActivity, WordNet builder/tests.
- Added a CI step that tests the dictionary builder, downloads Open English WordNet 2025+, and generates a local SQLite database.
- Added attribution for Open English WordNet and Princeton WordNet 3.1.
- Added Android-side SQLite dictionary integration and a testable token-selection policy that rejects empty/multi-word selection where single-word lookup is required.
- Added a local offline dictionary experience and haptic/glass interaction refinements.
- This is build-time data acquisition; the app's dictionary must stay offline at runtime.
- Preserve WordNet CC BY 4.0 attribution. No remote dictionary API.

### 544aef8a8e9f — add You dashboard and study statistics
Files inspected: manifest, LocalStore, MainActivity, StudyStats and tests.
- Disabled Android backup (allowBackup false) in the manifest, consistent with local privacy.
- Added local persistence for reading sessions and quiz attempts.
- Added StudyStats models/policies and tests for date ranges and study-stat calculations.
- Added You dashboard metrics and reading/quiz statistics.
- Added unique article/session tracking with Asia/Kolkata date logic.
- This laid the groundwork for distinguishing unique read markers from timed reading sessions. Later commits refined these semantics.

### 0b7f6eb5770a — count only confirmed foreground reading sessions
- Changed the minimum accepted foreground reading session from 1 second to 5 seconds.
- Added tests confirming sessions shorter than five seconds are ignored.
- Changed the unique-read count logic to derive from timed reading sessions at that point; this was later corrected by commit 0032873 so legacy read markers remain counted without inventing historical timed sessions.
- Durable rule: only actual foreground reader time is timed; do not count app background time.

### 0032873ebe5f — persist quiz answers and retain legacy read markers
Files inspected: LocalStore, MainActivity, StudyStats and related tests/filters.
- Added a 500-entry detailed-quiz-attempt cap while retaining up to 5,000 aggregate attempts.
- Added QuizAnswerRecord with prompt, selected/correct answers, option indices, explanation and provenance.
- Restored legacy read URLs into the unique-read count by combining read markers and timed sessions, while not inventing timed history for old URLs.
- Removed Archive filter/test from ArticleFilters as the four-tab direction settled.
- Durable semantics: quiz data is persisted as an attempt only after submission; legacy read markers count as read status only, not as a fabricated duration/date.

### 407103c211c8 — resume reading from saved article position
- Added ReaderPosition storage and policy.
- Bounded saved positions to 100 entries, 2,048 URL characters, 30-day age, and only meaningful incomplete progress (minimum save threshold is defined by ReaderPositionPolicy).
- MainActivity stores/restores article scroll progress.
- This is separate from timed reading sessions; do not mix position restoration with reading-time accounting.

## Reader security, extraction and cache integrity

### 6e218cbd8750 — preserve bookmarks and sanitize extracted reader HTML
Files inspected: ArticleCachePolicy, ArticleReaderRepository, LocalStore and regression tests.
- Added an explicit cache policy: preserve bookmarked stories even when older than the rolling cache cutoff/soft limit.
- Added/used Jsoup Safelist sanitization to strip unsafe markup/attributes while preserving readable article content.
- Added tests for pinned bookmarks and unsafe executable markup.
- Durable rule: user-saved items are explicit user choice and should not disappear simply because the feed cache expires.
- Never weaken reader HTML sanitization or public-publisher access limits.

### 8f9258b4da65 — scope reader JavaScript to sanitised articles
- Tightened ReaderExecutionPolicy so JavaScript is enabled only for the app-owned, sanitised article document needed for the text-selection bridge.
- Removed the earlier possibility of enabling app JavaScript in a publisher fallback page.
- Updated tests to enforce the narrower rule.
- This is a security boundary. Do not enable JavaScript or DOM storage for publisher fallback pages.

## Editorial classifier and feed policy

### 682432a6a312 — gate default feeds for UPSC relevance and add image palette bleed
Files inspected: MainActivity, UpscFeedPolicy and test.
- Introduced UpscFeedPolicy as a strict editorial eligibility gate for Today/Explore/Search.
- Initial policy suppresses entertainment noise and requires mapped GS subject plus concrete syllabus signals; the initial rule implementation was later hardened.
- Initial card image palette treatment introduced a blurred background image effect. Later PR #3 deliberately removed the second 30dp blurred image per card due scroll overhead and moved to one decode + one-time palette sampling. Do not restore the expensive first approach.
- The main classifier still needs explicit false-positive and false-negative coverage; a general keyword count is not validation.

### f758af2ecc38 — harden UPSC signal matching and feed eligibility
Files inspected: ArticleCardMetaPolicy, MainActivity, UpscEngine, UpscFeedPolicy and tests.
- Added whole-token/whole-phrase matching with Unicode-aware punctuation normalization. Substrings inside unrelated words (e.g. “rbi” within “arbitrary”) are not signals.
- Tightened policy gate: broad category/publisher section labels alone cannot qualify. A story needs an explicit GS map and a specific syllabus signal.
- Exposed concrete matched terms in card metadata, rather than a vague classifier hint.
- Added regression tests for false-positive substring matching and weak section/category signals.
- This remains a keyword heuristic, not a validated relevance score or exam prediction.

### 6cf84bacd3dc — align economy signal-count expectation
- Corrected one test fixture to expect four signals rather than three: RBI, repo rate, inflation and monetary.
- This was a stale test expectation, not evidence that a user-facing classifier bug was fixed.
- Build #97 failed with the stale assertion; subsequent Build #98 succeeded.

## Liquid Glass and scrolling performance

### df28a43c0bea — polish glass surfaces and remove feed scroll overhead
- MainActivity and theme resources were changed for a more cohesive glass UI and transparent system bars.
- Commit description says it removed feed scroll overhead; this phase is associated with screenshot-driven header/filter/navigation/card/loading improvements recorded in PR #3.
- PR #3's recorded intended result: restrained glass toolbar, Explore chip band, selected icon drawn crisp above active lens, squircle image cards, skeleton loading, transparent Search/IME.
- Important performance correction from PR #3: avoid drawing a second blurred image copy behind every card. Use the same Coil-loaded image, a one-time nine-point palette sample and light radial ambient tint.

### c403d7f2597d — PR #3 design/performance refinement (PR branch commit, not a main commit index entry)
- The inspected PR-branch diff included MainActivity and theme changes for Liquid Glass surfaces, system bars and image palette treatment.
- This SHA does not appear in the current main commit list; it is recorded as a PR-branch implementation reference, not as a main commit.
- Use PR #3's merged diff and supplied screenshot observations as the definitive evidence for all of its changes.

### 81de60cca96b — harden cold start and reduce rendering workload
- Reduced the existing 8×8 adaptive glass readback cadence: API 31+ changed from 600ms to 1,500ms; older Android from 1,500ms to 2,500ms.
- Changed startup restoration/feed filtering and moved hot-path work off main, per commit description and PR #7 body.
- Bounded image decode and guarded image-palette sampling.
- Added OEM-specific defensive handling around system-bar operations.
- These are code-level defensive changes. They do not prove the OPPO cold-start crash was fixed.

### 2475970123a2 — harden launch, reduce feed jank and finish glass polish
Files inspected: EditorialFeedIndexPolicy, LaunchCrashDiagnostics, LocalStore, MainActivity and tests.
- Added shared EditorialFeedIndexPolicy to compute one eligible/today/explore index off the main thread, reused by Today/Explore/Search.
- Added local-only crash diagnostics: stores a bounded on-device crash report; nothing is uploaded. Formatter test checks device/thread/exception content and bounded size.
- Hardened SharedPreferences restore with runCatching fallback for malformed/wrong-typed values.
- Added selected-state/accessibility semantics and nav/Search visual refinements.
- Removed recurring 8×8 graphics-layer readback from the feed path at this stage; theme-derived glass contrast replaced it. A later draft PR #13 proposes restoring one shared low-cadence adaptive probe.
- The crash diagnostic was intended to capture future failures; it does not prove the crash root cause or fix the user's OPPO device.

## Other important reversals and feature boundaries

### 032cb41f7ca5 — remove bundled pronunciation data and UI
- Removed previously added CMUdict download/build integration, CMUdict licence file and pronunciation UI/data structures.
- Simplified dictionary data builder back to Open English WordNet only.
- User-facing pronunciation should not be reintroduced unless explicitly requested; do not quietly add external data/permissions/dependencies.

## How to extend these notes

This file currently explains selected high-impact commits whose diffs were inspected in this session. MAIN_COMMIT_INDEX.md has every main commit SHA/date/subject. Future material commits must receive a full entry here or in SESSION_LEDGER.md, including changed files, exact behavioural changes, reason, tests, failure/fix chain, risks and verification. If an old commit has not been inspected, say so and link to its diff rather than inventing detail.
