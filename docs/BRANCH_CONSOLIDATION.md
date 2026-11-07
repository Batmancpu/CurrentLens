# Black Box Bihar News — branch consolidation record

**Integration branch:** `black-box-bihar-news`  
**Repository:** `Batmancpu/CurrentLens`  
**Main SHA at integration start:** `73b8a0d1ba33503fede02db2c3c0af082d724a12`  
**Integration source:** PR #16 startup/stability head, which carries the corrected background-only Prismal capture, diagnostics and publisher/network guards.  
**Safety:** main remains unchanged. No source branch was deleted. Keep all source branches until the owner has reviewed the integrated branch and its natural CI status.

## Consolidated work

| Existing source | Treatment on this branch |
|---|---|
| PR #16 — startup/network/Prismal | Integration ancestor; retains background-only capture, redirect checks, local diagnostics and privacy guards |
| PR #19 — Reader Liquid Glass | Integrated: compact toolbar, Aa controls, progress, completion dock, definition improvements |
| PR #20 — stacked read-only CI | Integrated into the one workflow; source branch retained |
| PR #18 — main read-only CI | Compatible read-only build / isolated main-push release workflow intent ported into the single workflow |
| PR #11 — date/cache/provenance | Selectively ported: original publication timestamp precedence, stale/future/undated cache policy, publisher RSS explicitly labelled secondary |
| PR #12 — Atom alternate links | Selectively ported: HTML alternate destinations, RSS text links, excludes self/enclosure/feed URLs and unsafe schemes |
| PR #17 — RSS tracking dedupe | Selectively ported and improved: canonical comparison while preserving original destination; exact bookmarked/read variant preferred before date |
| PR #8 — off-main persistence | Selectively ported: serialized IO persistence for reading sessions, quiz submissions, read markers and reader-position writes |
| PR #22 — bounded recent read markers | Selectively ported: ordered cap for new markers; legacy unordered set preserved |
| PR #6 — GS I Geography/editorial | Selectively ported and reconciled with #21: Geography mapping/filter, syllabus metadata, content-aware cache invalidation, accessible filters |
| PR #21 — bill/heat-wave editorial | Selectively ported: precise parliamentary bill phrases, Finance Bill GS III, heat-wave variants GS III, NDMA-alone rejection |
| PR #13 — adaptive glass contrast | Selectively integrated with safeguards: shared 8×8 bottom-band probe, idle foreground-home only, 4s/6s cadence, paused during feed motion, Reader, Settings and background |
| Feed-only compact navigation | Added to the same existing GlassTabBar; no second bar or duplicate backdrop. Today/Explore/Search only; You/nested surfaces stay expanded |
| PR #15 — older publisher redirect allowlist | Not copied wholesale: superseded by newer PR #16 per-hop/final host checks and cookie/privacy restrictions; verify current source diff before deletion |
| Old Liquid Glass / v0.3 redesign branches | Not used as baseline; stale. Relevant surviving changes are present in the PR #16/#19 lineage or superseded |

## Known limits — no “perfect/bug-free” claim

- The legacy `read_history` SharedPreferences StringSet is unordered and cannot be safely trimmed without a migration. Only new markers are capped to a 5,000-entry ordered supplemental window; total legacy storage is not bounded.
- Article-level primary-source corroboration is not implemented. Publisher RSS remains secondary reporting. The UI must not imply verification against PIB, Gazette, ministries, Parliament, PRS, RBI, Supreme Court or regulators without a linked source document.
- No physical OPPO Reno10 runtime/logcat/tombstone/Perfetto/screenshot/TalkBack validation is available. Native launch stability, GPU cost, frame jank, memory, thermal and battery effects are not certified.
- Adaptive contrast has a conservative cadence and pauses during motion, but still needs device profiling.
- CI validates Kotlin compilation, JVM tests, APK assembly and artifact upload; it does not prove every app flow or physical-device behaviour.
- No source branch has been deleted or reset. The owner should wait for final branch inventory, PR comparison and exact tested head before deleting source branches.

## Validation

The consolidated branch uses the single existing workflow. Build job has read-only permissions; Release write access is isolated to successful main pushes. Integration-branch pushes and pull requests trigger build/test only. No manual dispatch or rerun is used.
