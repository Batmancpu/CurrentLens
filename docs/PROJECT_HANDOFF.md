# CurrentLens — Live Project Handoff

> This is a dated state snapshot for the same user continuing the project across ChatGPT conversations. Read AGENTS.md, CHATGPT_CONTINUITY.md and SESSION_LEDGER.md. It is not an external developer handover.

## Snapshot

- Snapshot: 6 November 2026, after the latest observed run #116.
- Repository: https://github.com/Batmancpu/CurrentLens
- Main SHA: 73b8a0d1ba33503fede02db2c3c0af082d724a12
- Current app ID: com.mangoloads.currentlens.
- Display/asset brand in main currently brrrNews; user's earlier requested case was brrrNEWS. Resolve before more branding changes.
- Main commit count inspected: 119.
- Actions runs inspected: 116 total; 73 success, 33 failure, 10 cancelled; no active runs at capture.
- Latest Actions run #116 succeeded and published Build 116 debug APK. Details in BUILD_AND_DEBUG.md.
- No physical-device crash/frame/GPU verification is available.

## Mission and quality bar

CurrentLens is a strict UPSC CSE current-affairs reading app, not a general news aggregator. Quality has two pillars:
1. Editorial trust: noise suppression, defensible GS paper/theme/reason, primary-source provenance, no exam prediction.
2. Android reading quality: stable cold start, smooth mid-range scrolling, clear accessible Liquid Glass surfaces, reliable Search/IME/reader/definitions, low-jank image treatment.

## Editorial contract

Every default-feed item should have:
- A defensible GS paper (I–IV).
- A specific official syllabus theme.
- An explainable relevance reason.
- A clear distinction between publisher RSS provenance and independently checked primary evidence.

Suppress celebrity gossip, film/TV promotion, box-office, reality-show drama, routine sports scores, generic viral trends/memes/influencer drama. Preserve real media/sport law, regulation, judicial/institutional action and policy reforms when headline itself identifies the action.

Use official UPSC syllabus plus latest available official Prelims/Mains PYQs. Maintain labelled positive, false-positive and false-negative examples. Synthetic fixtures are regression examples, not accuracy metrics. Never call keyword matching a validated score or predict future questions.

Main classifier source: UpscEngine.kt / UpscFeedPolicy.kt / EditorialFeedIndexPolicy.kt. It uses whole-token/phrase matching and explicit syllabus signals. Dedicated GS I Geography remains absent on main at this snapshot; proposed in PR #6. Broad “film” suppression may create scientific thin-film false negatives. Publisher RSS reports are not primary-source corroboration; primary evidence is still a product gap.

## Product navigation and data semantics

Exactly Today / Explore / Search / You. Saved nested in You. No Archive.
Today uses current Asia/Kolkata date. Explore is broader eligible retained feed. Search has headline/publisher/topic/subject/GS filters.
Reading time counts only foreground reader sessions; grouped by Asia/Kolkata day. Quiz attempts persist only on submit. Legacy read URLs contribute unique count only, with no inferred historical time. WordNet is local SQLite, generated at build; no runtime dictionary access.

## Commit-level memory

- docs/MAIN_COMMIT_INDEX.md contains all 119 main-branch commit timestamps, SHAs and subjects captured at this snapshot.
- docs/COMMIT_DETAILS.md contains file-level summaries for the high-impact commits whose diffs were actually inspected, including bootstrap/build evolution, Search/navigation, WordNet, You/study stats, reader security/cache, classifier, UI/performance, startup diagnostics and reversals.
- Do not treat a commit subject as a full explanation. Open the linked diff for any unexpanded commit. The ledger explicitly marks any missing detail rather than inventing it.

## Source tree / architecture

- app/src/main/java/com/mangoloads/currentlens/MainActivity.kt — main Compose UI/navigation/cards/reader/search; very large file (~160 KB).
- NewsRepository.kt — 14 configured RSS feeds, concurrent IO fetching, XML/Atom parsing, Article and FeedSource.
- UpscEngine.kt / UpscFeedPolicy.kt — indicative GS mapping and strict eligibility gate.
- EditorialFeedIndexPolicy.kt — shared eligibility + Today/Explore/Search index.
- FeedDateParser.kt / ArticlePublicationDatePolicy.kt / ArticleCachePolicy.kt — feed dates/cache.
- LocalStore.kt — bookmarks/read markers/settings/sessions/quiz/preferences JSON.
- ArticleReaderRepository.kt / ArticleReaderContentCache.kt — public page extraction and bounded article cache.
- PublisherHostPolicy.kt / NetworkLimits.kt — host/network restrictions.
- LaunchCrashDiagnostics.kt — local on-device bounded crash diagnostics; no upload service.
- DictionaryActivity.kt / Dictionary*Policy.kt / DictionaryDatabaseSession.kt — offline WordNet/selection and definition UI.
- StudyStats.kt — reading and quiz summaries.
- GlassLuminanceProbe.kt / GlassLuminancePolicy.kt / GlassOpticsPolicy.kt / GlassTabGeometryPolicy.kt — glass rendering/geometry policies.
- SearchMorphGeometryPolicy.kt / SearchNavigationPolicy.kt — Search morph and navigation geometry.
- scripts/build_wordnet_db.py and tests — builds local SQLite dictionary.

## Main technical configuration

app/build.gradle.kts: minSdk 26, targetSdk 35, compileSdk 37, Java17, versionCode2, versionName0.2.0. Dependencies include Compose, Coil2.7, Coroutines1.9, Jsoup1.23.2, PrismalAGSL v1.0.4, JUnit4.13.2.
Manifest: INTERNET only; cleartext false; backup false.
PrismalAGSL v1.0.4 + MIT notice retained. WordNet CC BY 4.0 attribution retained.

## RSS and reader

Main has 14 publisher feeds: The Hindu7, Indian Express4, BBC1, DW1, Al Jazeera1. HTTP connection timeout8s/read timeout10s, bounded RSS response. First-seen exact-URL duplicates keep first feed (section feeds ordered before broad feeds), sorted by publication time and first 250. Parser uses XmlPullParser, but PR #12 proposes robust Atom rel=alternate selection. PR #11 proposes pubDate/published precedence over updated, reject undated/stale/far-future unbookmarked cache items. Both PRs diverged 13 commits behind.
Metadata cache: README snapshot describes 30 days/500 soft limit, retain saved. Reader body cache separate: 30 entries/24MB/2MB each/30-day expiry. Recheck actual code before altering.
Reader extracts best-effort only from public publisher pages. HTTPS allow-list, no logged-in cookies, subscription token, paywall bypass, proxy or publisher fallback JavaScript/DOM storage. If body unavailable, explain RSS-only/original public page.

## Privacy constraints

No accounts, cloud AI, analytics, telemetry, tracking, paid APIs or crash reporting service. Only RSS/public page network. No runtime dictionary network. Local crash report may remain on-device. Preserve local-first.

## Visual intent / device defects

See DESIGN_EVIDENCE.md. Target restrained Apple-quality Liquid Glass but readability/performance first. Main has theme-derived glass contrast; older README wording was inaccurate but user explicitly asks README not be changed for this continuity work. The 8×8 luminance probe remains in code but is not active in MainActivity path described by PR #13. PR #13 proposes gated shared sampler; no GPU/perfetto proof.
User reported Build100 immediate crash on OPPO Reno10. Not verified fixed. No logcat/tombstone/ANR currently attached.
User reported Search field too far above keyboard and stuttery morph. Requires actual OPPO screenshot/recording + IME geometry before changing.
Previous screenshot observations from PR #3: flat header, disconnected Explore chips, active glyph blurred by lens, dark inset article image framing, keyboard backdrop, missing skeleton loading. Original image bytes not in repo; do not invent exact px/colour.

## Current open PRs at snapshot

These are stale branches based on ba06a8f and now diverged from latest main by 13 commits due brrrNews branding sequence. Recheck live PR state and diffs before any action.

- #6 Geography editorial improvements: 10 commits ahead/13 behind.
- #8 persistence off-main: 3 ahead/13 behind.
- #11 date/cache integrity: 2 ahead/13 behind.
- #12 Atom alternate link selection: 1 ahead/13 behind.
- #13 adaptive glass contrast restoration: 1 ahead/13 behind.
- #14 expanded ChatGPT continuation documentation: draft, branch docs/ai-agent-handoff-2026-11-06, head f819b3d839feb32c1d96fe16619835645e8e8602; based on main 73b8a0d1ba33503fede02db2c3c0af082d724a12; 3 commits ahead / 0 behind, 10 documentation files changed, mergeable at snapshot. README is not changed.

Do not blindly merge. No PR branch CI result follows from Build #116.

## Ordered next priorities

1. Verify Build 116 cold-launch on OPPO Reno10 and retrieve local crash report/logcat if needed. No crash fix certified.
2. Resolve brrrNEWS vs brrrNews case before future brand updates.
3. Rebase/review PRs #6/#8/#11/#12/#13 separately against latest main; all are diverged.
4. Improve primary-source corroboration with explicit claim/document/authority records.
5. Validate classifier using real labelled positives/false positives/false negatives.
6. Measure Search keyboard gap/morph on device; fix only with geometry evidence.
7. Validate Explore chip TalkBack/touch targets, font scaling, Reduce Motion, safe areas and adaptive glass contrast.
8. Keep this memory current after each material session and refresh files uploaded to ChatGPT Project.

## Continuity documentation PR #14 — exact branch state

- PR: https://github.com/Batmancpu/CurrentLens/pull/14
- State at snapshot: open draft, mergeable; base main SHA 73b8a0d1ba33503fede02db2c3c0af082d724a12; branch docs/ai-agent-handoff-2026-11-06; head f819b3d839feb32c1d96fe16619835645e8e8602.
- Comparison: 3 commits ahead, 0 behind. Ten docs-only files, 1,080 additions, 0 deletions. README is absent from the diff.
- Branch commits: c2603bfa34ba00874c64c863e47710273120bb97 (nine initial continuity docs); b30b1f75f87f0f7e779f4d1feaf8d5f1e8c0af6c (COMMIT_DETAILS.md); f819b3d839feb32c1d96fe16619835645e8e8602 (wire commit detail file into mandatory reading order and continuity docs).
- The branch was rebuilt on latest main; the earlier two-commit docs branch based on ba06a8f was replaced and its README modification is not in this PR.
- Verification: all ten files were fetched from the final branch; commit index contains 119 rows; PR diff paths were checked and README is absent. No CI run is available/expected because the workflow triggers only on pushes to main. No application/workflow source, permissions, assets or release files changed.
- The only remaining human step is to review/merge PR #14 if desired. Merging will trigger the existing main-push workflow and publish a debug APK prerelease; see BUILD_AND_DEBUG.md.

## Session continuity

The user continues in ChatGPT and does not want to repeat project context. Start the next chat inside the CurrentLens ChatGPT Project. Read CHATGPT_CONTINUITY.md for setup. GitHub is canonical for code/history, but Project sources are copies and need refresh after updates. Upload COMMIT_DETAILS.md to the Project as well.
