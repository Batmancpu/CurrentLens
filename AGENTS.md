# CurrentLens — Chat Continuity Rules

This repository documents an ongoing product built by one user working with ChatGPT across multiple conversations. It is not an external developer handover. Its purpose is to preserve context when a ChatGPT conversation reaches its context limit and the user opens another chat.

## Mandatory reading at the start of every new CurrentLens chat

Read these files in this order before proposing or making changes:

1. docs/CHATGPT_CONTINUITY.md — how to keep this project inside one ChatGPT Project across conversations.
2. docs/PROJECT_HANDOFF.md — latest known product/repository state and current priorities.
3. docs/SESSION_LEDGER.md — chronological history, feature phases, PR history, failed attempts and corrections.
4. docs/MAIN_COMMIT_INDEX.md — every commit on main, with SHA, timestamp and subject.
5. docs/KNOWN_ISSUES.md — unresolved, proposed, reported and verified issues.
6. docs/DESIGN_EVIDENCE.md — exact design intent, supplied-image register, user feedback and screenshot/video handling.
7. docs/BUILD_AND_DEBUG.md — build configuration, workflow, test/release process and failure patterns.
8. docs/DECISION_LOG.md — durable product/engineering decisions.
9. THIRD_PARTY_NOTICES.md and relevant licence files before changing dependencies, assets or bundled data.

Do not treat README as the engineering memory or full product specification. It is only a short public overview. Do not edit README during continuity-document work unless the user specifically asks.

## What must be preserved

- The same user is continuing the same project across ChatGPT chats. Do not frame the work as an external-team handover or ask the user to re-explain documented requirements.
- Preserve the user's original intent, exact wording where important, rejected approaches, reasons for rejection, what failed, why it failed, how it was corrected, and what remains unverified.
- Separate main implementation, draft-PR proposal, user-reported behaviour, synthetic test, measured result and hypothesis. Never promote one category into another.
- If the repository contradicts an older chat summary, inspect source/commit history and record the discrepancy. Live repository state is authoritative for implementation; user-authored requirements are authoritative for desired behaviour.
- Do not silently delete or rewrite historical entries. Append corrections with date, evidence and a link to the superseding entry.
- Preserve screenshots/recordings as evidence metadata: exact filename or asset path, capture/build/device if known, screen/state, user's observation, desired outcome and acceptance conditions. Do not invent details not visible in the image or supplied with it.
- Do not place private device screenshots or personal content into this public repository without explicit user approval. Prefer a precise written description and a local/private evidence reference. If the user explicitly approves a public asset, redact unrelated personal data first.

## Mandatory logging for every material change

Before finishing a material engineering session, update docs/SESSION_LEDGER.md and the relevant specialised register. For each code change/commit, record:
- Session date/time (IST and UTC when known), PR, branch, commit SHA and parent/base SHA.
- Exact files changed and the purpose of each file.
- User request or defect that motivated the work.
- Previous behaviour, new behaviour and why the change is correct.
- Other approaches considered or rejected, and why.
- Tests added/changed, exact command/task, result, and whether it actually ran.
- CI run number and failing/passing step/log evidence, if any.
- Device/runtime/screenshot/Perfetto evidence, separately from CI.
- Known risks, compatibility/data-migration implications and rollback path.
- What must not be repeated; whether an earlier failed approach is permanently ruled out or merely unverified.
- Remaining follow-up work and its priority.

A commit cannot contain its own final SHA before Git creates it. Record the change in the same PR/session entry, then add the resulting SHA in the next ledger refresh. GitHub's commit/PR history is the source of truth for the commit that updates the ledger itself.

For failed attempts, log the exact compiler/test/runtime message when available, the first failing step, confirmed root cause, corrective commit/PR, and final verification. If the log does not establish the root cause, write “root cause not established”; do not guess from the next commit message.

For conversation-only decisions that do not produce a commit, append a dated session entry when they materially change scope, priorities, visual intent or acceptance criteria.

## CurrentLens non-negotiables

- Strict UPSC CSE editorial product, not a generic news aggregator. Require a defensible GS paper, a specific official syllabus theme and an explainable relevance reason. Keyword matching is an indicative heuristic—not a validated score, rank or future-question predictor.
- Suppress celebrity gossip, film/TV promotion, box-office stories, reality-show drama, routine sports scores and generic viral content. Retain genuine law, governance, regulatory, institutional and policy stories even when they concern a noisy subject area.
- Use official UPSC syllabus and latest available official Prelims/Mains PYQs. Label positive, false-positive and false-negative examples. Synthetic fixtures do not prove real-world precision/recall.
- Keep publisher RSS provenance separate from independently verified primary-source corroboration. Prefer PIB, Gazette, ministries, Parliament, PRS, RBI, Supreme Court and statutory/regulatory bodies. Never present a publisher RSS feed as a primary source.
- Exactly four primary destinations: Today / Explore / Search / You. Saved remains inside You. No Archive tab.
- Preserve offline WordNet, local-first storage, Asia/Kolkata dates, foreground-only reading-session accounting and quiz persistence only on explicit submit.
- No accounts, cloud AI, analytics, telemetry, tracking, paid APIs, runtime dictionary network, paywall bypass, logged-in cookies, publisher proxies, publisher fallback JavaScript or DOM storage.
- Preserve PrismalAGSL v1.0.4 and its MIT notice.
- Do not add continuous blur to every scrolling cell, duplicate image decoding or high-frequency GPU readbacks without measured evidence.

## Repository safety

- Never edit, commit or push directly to main.
- Never merge a PR, change workflow configuration, dispatch/rerun builds or publish releases during an ordinary review task.
- Inspect live main, all open PRs/branches, complete Actions history/queue, workflow, latest steps/logs, artifacts and Releases before proposing repository actions.
- Check for equivalent existing PRs before opening another. Update an equivalent PR rather than duplicating it.
- Use one isolated branch and one focused draft PR for material improvements. Keep work batched and evidence-backed.
- The current single workflow triggers on pushes to main, has contents:write and publishes a debug APK prerelease. Merging can publish a Release automatically; flag this before any merge discussion.
- If no material change is justified, do not send a no-change alert or a generic UPSC news digest.

## Verification rules

- CI compilation is not Android runtime verification.
- Never claim the OPPO cold-start crash is fixed without physical-device reproduction and useful local crash/logcat evidence.
- Never claim scroll performance is improved without frame-time/Perfetto evidence on a relevant device.
- Never claim visual, IME, TalkBack, font-scale or Reduce Motion correctness from code inspection alone.
- Mark tests as “added, not run” unless an actual test result exists.
- Preserve exact uncertainty. A plausible defensive fix is not a verified root-cause fix.

## End-of-session continuity update

At the end of each material session:
1. Refresh docs/PROJECT_HANDOFF.md with current main SHA, open PRs, builds, priorities and evidence.
2. Append the detailed event to docs/SESSION_LEDGER.md.
3. Update docs/KNOWN_ISSUES.md and/or docs/DESIGN_EVIDENCE.md when status or evidence changes.
4. Add only durable decisions to docs/DECISION_LOG.md.
5. Keep Project instructions and uploaded copies of these docs aligned with GitHub when they change.

Do not use this file as the entire history. It is the rulebook that forces every continuation chat to consult and maintain the complete history in the linked records.
