# CurrentLens — Decision Log

This file stores durable product and engineering decisions, not routine status. The live snapshot is in PROJECT_HANDOFF.md and the chronological memory is in SESSION_LEDGER.md.

## D-001 — UPSC CSE is the editorial product, not a tag on a general news reader
Today, Explore and Search are strict UPSC-first current-affairs surfaces. Suppress broad celebrity/entertainment promotion, box-office coverage, reality-TV drama, routine sports scores and generic viral content. Retain real law, policy, regulatory, judicial and institutional developments even if their subject area is often noisy.

## D-002 — Editorial mapping must be explainable and non-predictive
An eligible story needs a defensible GS paper, specific official syllabus theme and explainable reason. Use official UPSC syllabus and latest available official Prelims/Mains PYQs. Keyword rules are indicative signals, never a validated score/probability/rank or future-question prediction. Label positives, false positives and false negatives. Synthetic fixtures are not an accuracy benchmark.

## D-003 — Publisher provenance is not primary-source corroboration
RSS tells which publisher supplied the report. It does not verify the claim against a primary source. Preferred corroboration: PIB, Gazette of India, ministries/departments, Parliament, PRS, RBI, Supreme Court and statutory/regulatory bodies. Store exact public document and claim supported; otherwise state “not independently corroborated”.

## D-004 — Exactly four primary destinations
Today / Explore / Search / You. Saved stays inside You. No Archive tab.

## D-005 — Local-first privacy and narrow networking
No accounts, cloud AI, analytics, telemetry, tracking, paid APIs or crash-reporting service. Network only for RSS and publicly accessible publisher pages. No paywall bypass, logged-in cookies, subscription tokens, proxying or publisher fallback JavaScript/DOM storage. Local crash diagnostics must remain on-device and bounded.

## D-006 — Offline dictionary
Open English WordNet is bundled as local SQLite generated at build time; definitions do not require runtime network. Preserve attribution and CC BY 4.0 terms. No remote dictionary/AI service.

## D-007 — Time and study statistics have explicit semantics
All current-affairs day boundaries use Asia/Kolkata. Reading duration counts only while reader is foregrounded and flushes on lifecycle pause/exit. Quiz attempts persist only on explicit submit. Do not infer historical dates/durations for legacy read URL markers. Any read-marker retention change must preserve or explicitly redefine lifetime unique-read counts.

## D-008 — Reader respects public publisher access
Use RSS metadata plus best-effort extraction from publicly accessible publisher pages. If unavailable, offer allow-listed original public page or explain limitation. No logged-in sessions/cookies/paywall workarounds/subscription tokens/private proxies/fallback JavaScript/DOM storage.

## D-009 — Liquid Glass is subordinate to readability and frame stability
Use restrained shared surfaces; preserve PrismalAGSL v1.0.4 and MIT notice. Avoid per-cell blur, duplicate decoding and high-frequency GPU readback. Verify contrast, selected glyph clarity, TalkBack, touch size, safe areas, font scale, Reduce Motion and IME. Performance claims require device traces/frame-time.

## D-010 — CI is not device verification
Do not claim a launch crash, visual defect or scroll-jank issue is fixed from CI compile/tests alone. Use local crash report/logcat/tombstone/ANR and physical device; use screenshot/Perfetto/frame-time/memory for UI/performance issues.

## D-011 — Main branch and Release safety
No direct main edits/pushes, no PR merges, no workflow edits, no build dispatch/rerun and no Release publication during ordinary review. Material work goes through isolated branch and focused draft PR after duplicate-PR inspection. The one workflow is push-to-main, contents:write and automatically publishes debug APK prereleases.

## D-012 — Keep README concise; keep project memory separate
README remains a concise public overview. AGENTS.md is the mandatory rulebook; PROJECT_HANDOFF is a live snapshot; SESSION_LEDGER is chronological memory; MAIN_COMMIT_INDEX is the complete commit inventory; KNOWN_ISSUES tracks defects; DESIGN_EVIDENCE tracks visual requirements/assets; BUILD_AND_DEBUG stores CI/build/failure details; DECISION_LOG stores durable rules.

## D-013 — Continuity is for the same user's future ChatGPT chats
The persistent memory exists so the same user can switch to a new ChatGPT conversation when an older chat reaches its context limit. It is not primarily an external-team handover. Use one ChatGPT Project with shared chats/files/instructions and keep GitHub as durable source of truth. Project source-file copies may not auto-sync with GitHub; refresh them after material changes.

## D-014 — Every material change and failure must be journalled
Every material engineering session and code commit must have detailed ledger entry: user request, exact files, before/after, reason, tests, CI/device evidence, failed attempts, fix, risks, verification, rejected approaches and next steps. Do not invent exact details from missing screenshots/logs. GitHub commit is canonical for its own final SHA; record the SHA in the next ledger refresh.

## Initial historical work recorded 6 November 2026

- PR #1 merged: strict whole-token editorial matching and explicit syllabus signals.
- PR #2 merged: corrected four-signal economy test.
- PR #3 merged: Liquid Glass header/Explore filters, crisp selected glyph, one-time image palette sample, squircle cards, skeletons and transparent IME.
- PR #4 closed unmerged (duplicate icon import follow-up).
- PR #5 merged: supported Compose navigation imports.
- PR #6 open draft: Geography mapping/card rationale/accessibility.
- PR #7 merged: startup defensive changes/off-main restoration; no device proof.
- PR #8 open draft: off-main local persistence.
- PR #9 merged: local crash diagnostics, off-main editorial index, theme-derived contrast and hardening.
- PR #10 merged: Search draw-scope compile fix.
- PR #11 open draft: publication date and cache hardening.
- PR #12 open draft: Atom alternate link selection.
- PR #13 open draft: low-cadence adaptive glass contrast restoration.
- PR #14 open draft: expanded ChatGPT context continuity memory; README excluded.


## D-015 — Install launch diagnostics before AndroidX Startup
Decision: The uncaught-exception handler must be installed from custom Application.attachBaseContext(), not MainActivity.onCreate(), because AndroidX Startup ContentProviders (including PrismalAGSL's initializer) execute before any Activity. Keep startup checkpoints in app-private storage and use a framework-View recovery surface so failure details remain visible without re-entering the potentially failing Compose/graphics stack.

Scope: This improves crash evidence and recovery, does not assert that Prismal or any other component is the actual root cause, must not add telemetry/network uploads, and must not let diagnostic setup or checkpoint storage failure block application startup. A startup interruption without a managed stack must be labelled as such, never represented as a Java exception. Physical-device validation remains mandatory.


## D-016 — Crash diagnostics are local and data-minimised
All crash/startup reports remain in app-private storage and are never automatically uploaded. Shareable diagnostics omit device identifiers, build fingerprints, process/thread IDs, exact timestamps, memory addresses, raw tombstones/Base64 traces and exception messages. Internal process markers may be used privately for Android exit-record correlation but must be stripped from displayed/exported reports. Clear older local reports that expose device/build identifiers when upgrading.

## D-017 — Preserve Liquid Glass; isolate startup in a test-only probe
The production app must retain Prismal's normal automatic pipeline and complete Liquid Glass visual intent. If startup needs isolation, use a Stability-only plain Compose/Material first screen without Prismal capture/drawing, then let the owner opt into the full app with all effects active. A Legacy/flat diagnostic configuration is not a permanent product decision. Do not claim stability until repeated physical-device tests pass.

## D-018 — One active integration branch per continuing issue
Reuse the current issue's open integration branch/PR and batch related work there. Avoid branch-per-file/per-session/per-agent. Preserve independent unmerged feature branches until reviewed or explicitly abandoned. Delete merged/stale refs only after confirming no unique work; branch count is repository hygiene, not a runtime cause.
