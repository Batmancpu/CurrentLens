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
- Preserve PrismalAGSL v1.0.6 and its MIT notice; do not downgrade or remove Liquid Glass effects without a measured, reproducible reason and explicit product decision.
- Do not add continuous blur to every scrolling cell, duplicate image decoding or high-frequency GPU readbacks without measured evidence.

## Repository safety

- Never edit, commit or push directly to main.
- Never merge a PR, change workflow configuration, dispatch/rerun builds or publish releases during an ordinary review task.
- Inspect live main, all open PRs/branches, complete Actions history/queue, workflow, latest steps/logs, artifacts and Releases before proposing repository actions.
- Check for equivalent existing PRs before opening another. Update an equivalent PR rather than duplicating it.
- Use one isolated branch and one focused draft PR for material improvements. Keep work batched and evidence-backed.
- The single workflow has `contents:write`; `main` pushes publish a debug APK prerelease, while the temporary active crash-stabilisation branch builds an Actions artifact only. Merging any branch into `main` can trigger a Release; flag this before any merge discussion. Remove temporary branch triggers when the stabilisation work is completed.
- If no material change is justified, do not send a no-change alert or a generic UPSC news digest.

## Verification rules

- CI compilation is not Android runtime verification.
- Never claim the Android test device cold-start crash is fixed without physical-device reproduction and useful local crash/logcat evidence.
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


## Native launch-crash rules — mandatory for every agent

The app has a recurring, user-observed native cold-start failure. Treat this as an unresolved reliability issue until the owner confirms repeated physical-device launches. The exact source-level root cause is **not established**.

### Evidence and interpretations that must stay separate

- Stability 0.2.2 produced `CRASH_NATIVE` / signal 11 with a partial native trace containing Mali GPU, Android HWUI and Skia rendering frames.
- Stability 0.2.3 forced Prismal's Legacy pipeline (AGSL and RenderEffect disabled, sampled GraphicsLayer backdrop retained) and **still crashed**. The subsequent report again showed `CRASH_NATIVE` / signal 11. Its last recorded app checkpoint was that `MainActivity.setContent` had returned; the next Compose composition checkpoint had not been recorded.
- The newer partial tombstone excerpt contains ART finalizer/monitor wait frames, including a `FinalizerDaemon` label. A native tombstone is a protobuf containing process/thread diagnostic data; do not assume that one visible thread excerpt identifies the faulting thread unless the crashing TID is explicitly matched. The system text suggesting a stack overflow is a heuristic, not proof of a recursive source-level call.
- These two reports do not prove Prismal, Compose, the Mali driver, ART finalization, WebView or any individual Kotlin function is the root cause. Do not invent a root cause from CI, a partial trace, or temporal correlation.
- PSS/RSS reported as zero is not proof of out-of-memory termination.
- Build success is not device-launch success. The owner must confirm cold and warm launch on the affected device.

### Required rendering strategy

- Keep the original Liquid Glass visual intent. The normal/production build must start the complete app with Prismal's normal automatic pipeline and its full intended effects. Do not leave a global forced Legacy/flat-rendering override in production.
- Stability-only builds may use the plain Compose/Material `StabilityLaunchProbe` as a diagnostic first screen. It deliberately avoids Prismal backdrop capture, sampled graphics layers and glass drawing. After the owner taps its button, it must enter the complete app with the normal Prismal pipeline enabled.
- This staged build is an isolation instrument, not a permanent product downgrade and not proof that the app is fixed. A probe launch followed by a crash after the full-app button narrows the failure to the full composition/graphics path; a crash before the probe appears points earlier in startup. Preserve that distinction in reports.
- Do not delete expensive effects or change the production pipeline merely to make a stability test appear to pass. Isolate one subsystem at a time and re-enable effects incrementally after a confirmed trigger is found.

### Diagnostics and privacy

- Diagnostics must stay in app-private storage. Never add a crash-reporting endpoint, automatic upload, cloud telemetry, analytics or background transfer.
- Never commit or paste into public issues, PRs, source comments, documentation, logs or CI artifacts: device model/code, build fingerprint, serial/IMEI, precise timestamps, process/thread IDs, memory addresses, full tombstones, raw Base64 traces, personal file paths, exception messages that may contain user data, or screenshots containing personal content.
- New shareable reports should be data-minimised: app/version, Android API level, coarse startup stage, exception type/class-method frames where safe, and the minimum exit reason/status needed to distinguish native crash, Java exception, ANR or system stop. Omit device identifiers, exact time, PIDs/TIDs, raw traces, memory addresses and exception messages.
- Internal PID/time markers may be retained only inside app-private storage when needed to correlate a prior process with Android's historical exit record. Strip these fields before display/export. The report-generation code must remain best-effort and must never abort app startup.
- Clear legacy on-device reports that contain device/build identifiers when upgrading to the privacy-minimised diagnostic implementation.
- Public engineering notes should say only that an anonymised Android test device produced a native crash; do not copy the owner's raw report or unique device/build metadata.

### Repeatable acceptance test

1. Install the new Stability APK as an update to the prior Stability test APK (same dedicated test signing key); keep the normal app package separate.
2. Cold-launch repeatedly and verify that the plain probe appears and remains usable.
3. Tap the full-app button; verify the original Liquid Glass UI, all four tabs, Search/IME, reader, settings and core offline dictionary.
4. Repeat cold launch and full-app entry at least five times; test a warm relaunch and restored local state.
5. If a crash occurs, preserve only a local raw trace for private diagnosis; share the sanitised report. Do not claim resolved from CI or a single successful launch.

## Branch hygiene

- Branch count does not affect an installed APK or cause runtime crashes; only the checked-out/build commit determines the code in a build. Branch clutter does increase review, CI and continuity risk.
- Use one active integration branch and one focused draft PR for a continuing issue. Make multiple related commits on that branch rather than creating a new branch per file, session or AI agent.
- Reuse/update the existing open PR when work is related. Do not create duplicate draft PRs for the same issue.
- Preserve independent open feature branches until their unique commits are reviewed, integrated or explicitly abandoned. Never delete a branch with unmerged unique work merely to reduce the branch count.
- After merge/closure, delete truly stale refs when safe; do not force-move a branch to simulate deletion. If branch deletion is not available to the acting tool, say so and give the owner the GitHub Branches page rather than claiming deletion.
- Enable repository-side automatic deletion of merged branches only after the owner approves the setting. Never merge PRs, close active feature PRs or delete unique work without an explicit user decision.


### Stability 0.2.5 device result and the next isolation protocol

- The owner tested the 0.2.5 Stability build. Its privacy-minimised report recorded `CRASH_NATIVE (5)` / signal 11 after the checkpoint `Full brrrNews composition requested from stability probe`. No Java/Kotlin exception was captured.
- A user-supplied 21.36-second screen recording shows the plain Compose/Material probe rendering, followed by the app returning to the launcher/recovery flow after the full-app transition. Do not upload the recording or its device/launcher contents to this public repository.
- This is stronger evidence that the basic Activity and plain Compose probe can draw, while the transition into the full app fails. It does not prove which full-app component is responsible.
- The 0.2.3 Legacy build still crashed. PrismalAGSL v1.0.6 Legacy disables AGSL and RenderEffect, but its `rememberPrismalGlassLayer` / `prismalGlassLayer` path still records and draws Compose `GraphicsLayer` objects; `drawPrismalGlass` also creates and records graphics layers. Therefore the earlier Legacy test did not eliminate backdrop capture or layer composition as a cause. Source: https://github.com/styropyr0/PrismalAGSL/tree/v1.0.6/Prismal/src/main/java/com/styropyr0/prismal.
- Keep production and normal Stability full-app mode on Prismal's automatic pipeline with all current effects. Do not re-add the forced Legacy override. Do not permanently remove blur, refraction, specular, depth or chromatic effects on the basis of these reports.
- Stability 0.2.6 must isolate, in this order, (1) one root backdrop capture only, (2) one full Prismal glass surface, (3) two captured layers plus the merged hidden tab source, then (4) the full application. Each button must persist a coarse stage marker before changing Compose state; after three stable frames, mark that test stage complete. If it fails, the next local report identifies the last requested stage.
- If stages 1–3 all pass but the full app crashes, the issue lies beyond the tested primitive path (e.g. another composable, image decode, data-driven screen or interaction); isolate the app tree next. Do not infer from this short test alone.
- The shareable report remains minimised. Raw tombstones may contain device fingerprints, PIDs/TIDs, addresses and all-thread memory maps; never commit or include them in public GitHub text, screenshots or CI artifacts. Keep any raw trace app-private and only share a deliberately sanitised symbol-level summary.


## 6 November 2026 — Prismal backdrop self-capture correction

- Source inspection found a concrete rendering-architecture defect in the prior PR #16 implementation: `BrrrNewsApp` attached `.prismalGlassLayer(backdrop)` to the full content `Column`, while glass surfaces inside that same subtree sampled the same `backdrop`.
- PrismalAGSL v1.0.6 documents this modifier for background content with glass surfaces drawn above it. Its layer modifier draws the subtree and then records that subtree to a GraphicsLayer; descendant glass draws referencing the same layer can therefore create a self-referential capture/sampling dependency. This is the strongest source-level diagnosis consistent with the full-app first-render failure and earlier GPU/HWUI trace. It is not proof that a specific native instruction caused the reported SIGSEGV.
- The code correction moves the Prismal capture to a separate background-only sibling (theme background plus two subtle ambient radial glows); the app content and all glass surfaces are drawn above/outside that capture. The full automatic Prismal pipeline and blur, refraction, chromatic aberration, specular, depth and spring effects remain enabled. No telemetry, upload or extra permissions are introduced.
- Do not reintroduce a capture modifier on a parent whose descendants sample that same backdrop. Keep every captured source tree free of glass surfaces that read from that source.
- The owner declined another staged diagnostic cycle. Do not keep asking for probe-stage tests. Rely on source inspection and CI first; report that device-level causality remains unverified unless the owner later volunteers a single runtime check.
- The previous KL-017 0.2.5 evidence remains valid. The old 0.2.6 staged probe is diagnostic history, not a required owner workflow. CI success must not be described as proof of device stability.
