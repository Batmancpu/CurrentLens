# CurrentLens — Development and Conversation Ledger

This is the evolving memory of the project across ChatGPT conversations. It records what happened, what was intended, what failed, why it failed, how it was corrected, what not to repeat and what remains uncertain. Append rather than silently rewriting history.

## Why this memory exists

The user clarified that they are not transferring the project to another AI product or external developer. They are continuing CurrentLens in new ChatGPT conversations when an earlier chat reaches its context limit. The next chat should resume without the user manually repeating the vision, visual feedback, prior failures, rejected options, pending work or workflow.

The user explicitly wants:
- Every material change and commit recorded.
- Failed attempts and exact causes/fixes retained.
- Approaches that did not work never repeated.
- Screenshots/photos/recordings and exact visual feedback captured where actually available.
- Build method, decisions, product goals, pending work and rejected ideas.
- README kept basic/public, not turned into the memory store.
- Detail preserved without fabricating unavailable image/log evidence.

## Product requirements and conversation feedback

### Editorial/product requirements
- Strict UPSC CSE product, not general news aggregator.
- Suppress celebrity gossip, film/TV promotion, box office, reality show drama, routine sports scores, generic viral content.
- Require defensible GS paper + specific official syllabus theme + explainable reason.
- Official syllabus plus latest available official Prelims/Mains PYQs as baseline.
- Positive, false-positive and false-negative fixtures.
- Keyword match is not validated score, probability or question prediction.
- Publisher RSS is not primary corroboration. Prefer PIB, Gazette, ministries, Parliament, PRS, RBI, Supreme Court, statutory/regulatory bodies.
- Four tabs only: Today, Explore, Search, You. Saved in You. No Archive.
- Offline WordNet, local-first, Asia/Kolkata, foreground-only reading time, quiz persist on submit.
- No accounts, cloud AI, analytics, telemetry, tracking, paid APIs, runtime dictionary networking; network only RSS/public accessible publisher pages. No paywall bypass/cookies/proxies/fallback JS/DOM storage.

### Reliability/performance/user reports
- Build100 passed CI but app reportedly crashed immediately on OPPO Reno10 5G. User explicitly required crash investigation first.
- PR #7 and #9 added code-level defensive startup/performance work and local crash diagnostics. Neither establishes physical runtime root cause.
- Search field reportedly far above keyboard, leaving awkward gap; Search bar rise/fall/morph animation reported stuttery.
- Avoid expensive per-cell blur, duplicate decode and frequent GPU readbacks. Use trace-backed fixes.
- User wants Apple-quality visual polish but no speculative churn and no smoothness trade-offs.

### User’s design feedback/reference images
- PR #3 body documents supplied OPPO screenshot defects: flat headers; Explore filter chips visually separate; active Today lens blurring selected icon; oversized dark inset image frame; keyboard/system-bar backdrop; empty loading state.
- Later Search/IME complaint: search field too far above keyboard; stuttery movement/morph.
- Launcher icon latest feedback: prior output looked too AI-generated; Liquid Glass design, central newspaper/news-sheet symbol in rounded square/circle-like tile, recognizable and name-independent.
- Exact earlier screenshots/video bytes are not preserved in repo; only text descriptions from PR/user context. Do not invent exact pixel/colour details. Future image attachments need an evidence record; do not place private screenshots in public repo without approval.

## Product and technical phases

### A. Project bootstrap / early build (3 Nov)
- Initial CurrentLens Android MVP and one APK workflow.
- Early workflow repeatedly failed on Android SDK packages and dependency coordinates.
- Implemented four-tab Liquid Glass interface and public article reader.
- Early failed dependency coordinate PrismalAGSL:Prismal was corrected through published Maven/JitPack coordinates. Current requirement is PrismalAGSL v1.0.4 and MIT notice.
- Build config iterated through SDK platform availability; workflow now uses API37 platform/build-tools and Gradle9.5.
- Fixes for reader HTML escaping/CSS string construction, selection JSON, glass-tab API, duplicate settings screen.

### B. Navigation/Search/Archive evolution (3 Nov)
- Some earlier commit briefly restored Archive and moved Search into Explore.
- Later commit replaced Archive with morphing Search and four-tab navigation.
- Final product rule: exactly Today/Explore/Search/You; no Archive; Saved inside You.
- Native text selection, reader progress/callbacks and Search morphing evolved through iterative fixes.

### C. RSS/reader/security/stats (3 Nov)
- Bounded RSS/dictionary network response sizes.
- Public reader content cache; quiz provenance labels.
- Reader navigation limited to verified publisher hosts.
- Short-article completion and per-article progress reset fixes.
- You dashboard/study stats.
- Reading sessions count only confirmed foreground reading.
- Quiz answers persist only after submit and legacy read URL markers retained.
- RSS feed provenance and India-local date parsing; explicit RFC1123 offsets preserved.
- Reader HTML sanitization and JavaScript scoped to sanitised articles.
- Do not weaken public-host allow-list, sanitization, response limits or paywall restrictions.

### D. Offline dictionary/selection/popovers (3–5 Nov)
- Replaced Android selection menu with in-app dictionary trigger; later preserved/restored native WebView selection and PROCESS_TEXT behaviour.
- Offline WordNet DB and reusable DB connection.
- Definition overlay anchored to selected word and constrained to reader viewport.
- Fixed punctuation/Unicode/multi-word selection, anchor coordinates and touch cancellation.
- Added bounded LRU cache, POS display, compact-first expandable definitions and WordNet exception-lemma lookup.
- Fixed stale auto-define on new touch/interrupted gesture/multi-touch.
- Improved short-reader completion stability and wide mobile article tables.
- CMUdict US pronunciation support was added and later removed entirely (032cb41f7ca5). Do not restore unless asked.
- Preserve WordNet attribution and licences.

### E. Search/IME/adaptive glass (4–5 Nov)
- Search morphs from bottom tab above keyboard.
- Fixed Compose keyboard APIs, synchronized morph with IME, observed IME insets inside Compose, measured Search anchor fallback/capsule geometry.
- Saved reader position/resume.
- Glass materials/motion and toolbar-band sampling refined.
- Despite earlier geometry work, user later reported field gap/stutter still present. Must remeasure actual device.
- Do not add multiple samplers or per-cell GPU readbacks.

### F. Editorial classifier hardening (5–6 Nov)
- PR #1: whole-token/phrase matching to prevent RBI inside arbitrary, IPL inside multiple, actor inside factor; no section-based weak escape; specific syllabus required; narrow policy exceptions must be headline-grounded; public health/disaster themes; matched signals exposed.
- PR #2 corrected economy test expected four signals (RBI, repo rate, inflation, monetary).
- #96 passed; #97 failed stale expected count; #98 passed after test correction.
- PR #6 draft adds GS I Geography, Explore/Search category, rationale cards, accessibility and labelled tests. Not in main at this snapshot.
- Preserve policy exceptions for real institutional/governance action.

### G. Glass/navigation UI and startup reliability (6 Nov)
- PR #3: restrained Prismal toolbar/Explore filters, icon-only bottom bar, crisp selected glyph above lens, Search geometry, removed second 30dp blurred image per card, one Coil image + one-time 9-point palette sampling, radial wash, squircle cards, static skeletons, off-main cache work and transparent Search/IME surfaces.
- Build #99 failed unresolved CalendarMonth and AccountCircle icon imports; #5 fixed; #100 passed.
- User reported Build100 immediate OPPO launch crash. CI success did not mean device runtime works.
- PR #7 moved startup restoration off main, cached Today filters, incrementally updated bookmarks/read state, reduced glass readback cadence, bounded image decode, palette guards, OEM system-bar defensive guard and icon revert; #101 passed.
- PR #9 added local-only crash diagnostics/recovery, off-main editorial index, removed recurring feed readback in favour theme-derived contrast, memoized filters, hardened preferences, Search/nav polish; #102 failed later due draw-scope.
- PR #10 fixed Search draw-scope error; #103 passed.
- No OPPO runtime verification. User-reported crash remains open.

### H. brrrNEWS branding/icon (6 Nov)
- Added SVG, Android vector foreground, adaptive/round icons, background resource, app label/copy/theme changes.
- Initially brrrNEWS; follow-up commits changed visible capitalization and SVG aria-label to brrrNews.
- Builds #104 and #112 passed, intervening #105–#111 cancelled, #113–#115 cancelled, #116 passed.
- User's exact earlier branding preference brrrNEWS versus main brrrNews remains to confirm.
- Icon user intent and vector geometry in DESIGN_EVIDENCE.md.

## PR history and status

| PR | Status | Summary |
|---|---|---|
| #1 | Merged | Whole-token strict editorial matching, explicit syllabus signals, policy exceptions, public health/disaster, matched signals. |
| #2 | Merged | Fixed stale economy test expectation (4 signals). |
| #3 | Merged | Liquid Glass header/Explore, clear nav glyph, image palette wash, no duplicate 30dp blur, squircle cards, skeletons, transparent IME, off-main cache work. |
| #4 | Closed unmerged | Earlier duplicate icon import follow-up. |
| #5 | Merged | Compose navigation imports fixing #99. |
| #6 | Open draft | Geography/editorial/card rationale/accessibility, diverged 13 behind. |
| #7 | Merged | Startup defensive/off-main restoration and reduced rendering workload; not device-proven. |
| #8 | Open draft | Off-main session/quiz/position/read-marker persistence; diverged 13 behind. |
| #9 | Merged | Local crash diagnostics, off-main editorial index, theme-derived contrast, preferences and UI polish. |
| #10 | Merged | Search glass draw-scope compile fix. |
| #11 | Open draft | Date precedence/cache retention; diverged 13 behind. |
| #12 | Open draft | Atom alternate link parsing; diverged 13 behind. |
| #13 | Open draft | Adaptive glass contrast restoration; diverged 13 behind. |
| #14 | Open draft | ChatGPT continuity memory docs; being rebuilt on latest main, README deliberately excluded. |

Open PRs #6/#8/#11/#12/#13 all base on old ba06a8f main snapshot and diverged from latest main (13 commits behind). Do not merge blindly.

## Main commit history

Every commit subject/SHA/timestamp is in docs/MAIN_COMMIT_INDEX.md. File-level summaries for selected high-impact changes whose diffs were inspected are in docs/COMMIT_DETAILS.md; do not infer full intent for other commits from subject alone. (119 items captured from two GitHub API pages). Open diffs for exact file-by-file detail. This ledger groups commit phases; the index is canonical for chronology/SHA.

## CI failure chain and do-not-repeat list

- #1: sdkmanager couldn't find package tools. Do not re-add obsolete tools pkg.
- #5: couldn't find platform android-37. Verify runner SDK packages.
- #8: couldn't resolve PrismalAGSL:Prismal. Do not reuse this coordinate.
- #38: FeedDateParserTest.preservesExplicitRfc1123Offset failed. Preserve explicit RFC1123 offsets.
- #97: test expected 3 economy signals but implementation produced 4; PR #2 fixed.
- #99: unresolved Compose imports CalendarMonth and AccountCircle; PR #5 fixed.
- #102: theme local called inside non-composable draw callback; PR #10 captured it in composable scope.
- #10/#18/#53 compile failure exact diagnostics not preserved in excerpt; root cause not established.
- Other failed run numbers and exact evidence in BUILD_AND_DEBUG.md.
- Don't assume CI success = OPPO crash fixed.
- Don't reintroduce substring match; section-based relevance shortcut; Archive; quiz persistence before submit; background reading-time count; duplicate blurred image draw; per-cell blur; high-frequency GPU readback; CMUdict pronunciation without request; unsafe reader JS or paywall workarounds.

## Continuity update protocol

At end of each material session:
1. Update PROJECT_HANDOFF with current main/PR/CI/issues.
2. Append detailed session entry with request, files, before/after, commit/PR, failed attempts and fixes, tests, CI/device evidence, risks.
3. Update KNOWN_ISSUES and DESIGN_EVIDENCE.
4. Update MAIN_COMMIT_INDEX after new main commit(s).
5. Update BUILD_AND_DEBUG after CI build failure/success.
6. Add durable rules to DECISION_LOG.
7. Keep README out unless specifically requested.
