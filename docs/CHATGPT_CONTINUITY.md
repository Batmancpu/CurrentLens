# Keeping CurrentLens context across ChatGPT conversations

This is specifically for the owner continuing CurrentLens in new ChatGPT chats when an older chat reaches its context limit. It is not an instruction to transfer development to another company, team or AI product.

## Recommended arrangement: one ChatGPT Project

A ChatGPT Project keeps related conversations, project instructions and project files together. New chats started inside that same Project can use its shared files/instructions and, depending on the Project memory setting, refer to other chats in the Project.

Official reference: https://help.openai.com/en/articles/10169521-projects-in-chatgpt

### One-time setup

1. Create a ChatGPT Project named CurrentLens (or CurrentLens — Build Journal).
2. Move this existing CurrentLens conversation into that Project using the conversation menu → Move to project, when the option is available. If the option is not available for this chat, keep it as the source conversation and start the next chat inside the Project.
3. Open the Project instructions and add the short instructions below.
4. Add these repository files to the Project's Sources/files:
   - AGENTS.md
   - docs/PROJECT_HANDOFF.md
   - docs/SESSION_LEDGER.md
   - docs/MAIN_COMMIT_INDEX.md
   - docs/COMMIT_DETAILS.md
   - docs/KNOWN_ISSUES.md
   - docs/DESIGN_EVIDENCE.md
   - docs/BUILD_AND_DEBUG.md
   - docs/DECISION_LOG.md
5. Start every continuation chat from inside the CurrentLens Project, not from a regular chat outside it.
6. Prefer Project-only memory if you want CurrentLens conversations isolated from unrelated chats. Chats in the same Project can still refer to other chats in that Project. Default memory is also possible.

### Suggested Project instructions

Copy into the Project instructions:

> This Project is the ongoing development workspace for my CurrentLens Android app. I am the same owner continuing across ChatGPT conversations; do not treat this as an external developer handover and do not ask me to restate context. Before acting, read AGENTS.md and all linked project records, especially PROJECT_HANDOFF.md, SESSION_LEDGER.md, MAIN_COMMIT_INDEX.md, COMMIT_DETAILS.md, KNOWN_ISSUES.md, DESIGN_EVIDENCE.md, BUILD_AND_DEBUG.md and DECISION_LOG.md. Check live GitHub because the docs are snapshots. Preserve my exact product intent, visual feedback, previous failures, fixes and rejected approaches. After every material change, update the session ledger with exact files, reason, before/after behaviour, commit/PR, tests, failures, corrections, risks and verification status. Keep README a short public overview; do not turn it into project memory. Follow the no-direct-main/no-merge/no-workflow-dispatch/no-release rules in AGENTS.md.

### What the Project does and does not do

- Project chats and files give the next chat shared context, so you do not need to paste the full development history each time.
- GitHub remains the durable source of truth for code, commits, PRs, build logs and release assets.
- Project file attachments are not guaranteed to auto-synchronise every GitHub edit. When the journal changes, refresh corresponding Project source files or have the new chat fetch the live repository using the connected GitHub source.
- A regular ChatGPT chat opened outside the Project is not guaranteed to load these Project files/chats. Start the new chat inside the Project.
- No system can recover exact pixels from a screenshot that was never stored or whose attachment is no longer available. The design register records what was actually visible/said and explicitly flags missing originals instead of fabricating detail.

### Minimal fallback prompt

If a new chat is accidentally opened outside the Project, use:

> Continue my CurrentLens project from its persistent GitHub/Project records. Read AGENTS.md, docs/PROJECT_HANDOFF.md, docs/SESSION_LEDGER.md, docs/MAIN_COMMIT_INDEX.md, docs/COMMIT_DETAILS.md, docs/KNOWN_ISSUES.md, docs/DESIGN_EVIDENCE.md, docs/BUILD_AND_DEBUG.md and docs/DECISION_LOG.md before acting. I am continuing the same project, not handing it to an external developer. Do not make me restate context.
