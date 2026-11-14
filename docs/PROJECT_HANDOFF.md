## Current starting point — fresh signing line and main-only development

The owner chose a fresh install and asked for all active work on main, one workflow at a time, one installable APK, and a durable record of every batch. Begin with [AGENTS.md](../AGENTS.md), the [roadmap](ROADMAP.md), [branch disposition](CONSOLIDATION.md) and [release signing](RELEASE_SIGNING.md). Main source `76816f7` passed [run #420](https://github.com/Batmancpu/CurrentLens/actions/runs/43582542056) with JVM tests and release assembly. The first signed APK, CurrentLens 0.3.0 (versionCode 11), was published in [release android-11](https://github.com/Batmancpu/CurrentLens/releases/tag/android-11) after successful [run #422](https://github.com/Batmancpu/CurrentLens/actions/runs/43585390946). The next step is the owner's fresh install and launch check. After that, prepare a higher-version APK using the same signing identity for the update test. Use the live main run and the session ledger for new results. Earlier snapshots below retain historical decisions and evidence.

---

## Current starting point — 14 November 2026 (IST)

See [PROJECT_AUDIT.md](PROJECT_AUDIT.md) for the live-baseline review, implementation matrix, signing and data preservation plan, and next steps. The source audited was `black-box-bihar-news@8daadee450ee59a8cffb3227b8460c2bbf6de794` (draft PR #34), with successful exact-head PR run #414. Main was `73b8a0d1ba33503fede02db2c3c0af082d724a12` with release tag `v0.2.0-build-116`. The immediate dependency is the package/build/signer currently installed on the owner's phone. Keep earlier snapshots below as historical evidence.

---

## Live snapshot — 9 November 2026, 01:27 IST

- **Main:** `73b8a0d1ba33503fede02db2c3c0af082d724a12`, unchanged.
- **Active branch:** `black-box-bihar-news`, source parent `3173bd9a4af5483d134fda1d5b756a2f5657d03d`; PR #34 remains the single draft/open PR to main, 155 ahead / 0 behind.
- **Freshness:** 46 branches, 43 PRs (29 open, 14 closed). PR #16 head `ebea0aac68ce2cf17aad376304f7915fe937e807` is 7 commits ahead / 98 behind active from common base `6ab1e6a02ac8e91027f21900d64d63b9f2e6a890`; four Appearance commits are superseded by active code, three diagnostic commits are adapted here. PR #43's remaining Reader settle-callback/guard delta is deferred. No other branch changed.
- **Parent CI:** natural push #367 and PR #368 passed on `3173bd9a4af5483d134fda1d5b756a2f5657d03d`; WordNet 151,456 unique words, `:app:testDebugUnitTest`, `:app:assembleDebug`, artifact upload. Artifact #13630179831, 39,122,992 bytes, SHA-256 `f10f20cdd0d078648c5bf0fb069bbafafb0105b54133f247f01a083e0f482a92`. These runs do not validate the new commit.
- **Actions:** 368 total, 230 success, 68 failure, 70 cancelled; queue/in-progress 0.
- **Workflow:** active branch read-only build/test plus isolated main-push Release-write; main push-only with workflow-wide `contents: write`.
- **Latest Release:** v0.2.0-build-116, published 6 November 2026 11:18:59 UTC; no new Release.
- **Current change:** startup diagnostic inspection, bounded report reads, stage writes and recovery deletion move to IO; immediate uncaught-handler install and synchronous final crash-report flush remain.
- **Device:** OPPO cold/warm launch, native crash causality, screenshots, Reader/IME/selection, TalkBack, font scaling, Reduce Motion, Perfetto, GPU, memory, thermal and battery remain **UNVERIFIED**.
- **Safety:** no main write, merge, retarget, dispatch/rerun, Release publication, package identity change or branch deletion.

---

## Live snapshot — 8 November 2026, 19:28 IST

- **Canonical main:** `73b8a0d1ba33503fede02db2c3c0af082d724a12`, unchanged.
- **Active integration:** `black-box-bihar-news`, owner-selected branch; this pass advances it from pre-change parent `261c387bb196e1bb74d5a20df9b3d1bc4f9ebc18` with one Saved-feed eligibility-reuse implementation/audit commit. PR [#34](https://github.com/Batmancpu/CurrentLens/pull/34) remains open/draft and targets main; no merge or retarget. The exact new head is available from the branch/PR and will be copied into the next handoff refresh.
- **Branch/PR inventory:** 44 remote branches and 41 PRs (27 open, 14 closed), fully paginated. Before this pass the active line was 148 ahead / 0 behind main, 91 ahead / 0 behind PR #16's corrected Prismal/stability baseline, and 78 ahead / 0 behind the former PR #19 UI line.
- **Actions:** 346 runs, fully paginated: 213 success, 67 failure, 66 cancelled; queued/in-progress 0. Exact parent natural push [#345](https://github.com/Batmancpu/CurrentLens/actions/runs/42483678631) and PR [#346](https://github.com/Batmancpu/CurrentLens/actions/runs/42483682905) passed. WordNet builder 4/4; 151,456 unique words / 50,950,144-byte database; `:app:testDebugUnitTest`, `:app:assembleDebug` and artifact upload succeeded. PR artifact [#13618970693](https://github.com/Batmancpu/CurrentLens/actions/runs/42483682905/artifacts/13618970693) is 39,120,818 bytes (SHA-256 `26b04cf919118ba3c0fec84b8efb95affbd4e19763477454e9bfaf9f68b1e32a`), expires 22 November 2026 13:12:57 UTC. Release jobs skipped. New implementation head awaits its own natural CI.
- **Workflow:** active integration workflow retains read-only build/test permissions and a separate main-push-only Release-write job. Main's workflow remains push-only with workflow-wide `contents: write`; no workflow changes.
- **Latest Release:** [v0.2.0-build-116](https://github.com/Batmancpu/CurrentLens/releases/tag/v0.2.0-build-116), published 6 November 2026 11:18:59 UTC; no Release created.
- **Current work:** Saved → Previously Opened now reuses one editorial relevance result for eligibility and the visible NewsCard's syllabus metadata. Existing noise rejection, ordering, top-15 limit, bookmarks/read markers and card/image semantics remain unchanged. Two focused JVM regression tests were added; exact-head CI is pending.
- **Open branch decisions:** PR #37 Search RowScope fix, #36 IST rollover, #30 touch-target policy and #31 Appearance restore are represented. PR #39 stable signing remains owner-gated; #40/#41 and #32/#33/#38 remain separate RSS/parser reviews; #25/#26 need joint publisher-resource security review; #27 editorial-only; #35 diagnostic-only.
- **Device evidence:** no fresh OPPO Reno10 logcat/tombstone/Perfetto or screenshot. Launch stability, visual/IME/TalkBack/font-scale/Reduce Motion and frame-time/GPU/memory/thermal/battery remain **UNVERIFIED**.
- **Safety:** no main write, merge, retarget, manual dispatch/rerun, workflow edit, Release publication, package identity change or branch deletion.

---

## Live snapshot — 8 November 2026, 16:05 IST

- **Canonical main:** `73b8a0d1ba33503fede02db2c3c0af082d724a12`, unchanged.
- **Active integration:** `black-box-bihar-news@9efd103a0cd8a272bbce3c61140e40bfa6a8b707`; PR [#34](https://github.com/Batmancpu/CurrentLens/pull/34) is draft/open, 141 commits ahead and 0 behind main. The current snapshot is the code/documentation head before this handoff refresh.
- **Repository inventory:** 44 branches, 27 open PRs, 14 closed PRs. No branch deletion or PR merge.
- **Actions:** 332 runs across four pages; 201 success, 67 failure, 64 cancelled; queue and in-progress 0. Latest natural push run [#331](https://github.com/Batmancpu/CurrentLens/actions/runs/42471818414) and PR run [#332](https://github.com/Batmancpu/CurrentLens/actions/runs/42471821932) both passed on exact integration head `9efd103a0cd8a272bbce3c61140e40bfa6a8b707`. WordNet builder 4/4; 151,456 unique words; `:app:testDebugUnitTest`; `:app:assembleDebug`; artifact upload. Release jobs skipped. Artifacts: push [13613817606](https://github.com/Batmancpu/CurrentLens/actions/runs/42471818414/artifacts/13613817606), 39,121,079 bytes, SHA-256 `c7b46466803b5da8e78c58410f51c0a2db5110c11f7bae2d0983c336934daa00`; PR [13613339920](https://github.com/Batmancpu/CurrentLens/actions/runs/42471821932/artifacts/13613339920), 39,121,080 bytes, SHA-256 `4b1c580f4224a731a1523e2df5e477ab64efb6543e6f3bea3b5ba63b059e4245`. Both expire 22 November 2026 around 10:34 UTC. These runs validate PR #34's integration/docs head, not the separate PR #40/#41 heads.
- **Workflow:** main has one push-only `.github/workflows/build-apk.yml` with workflow-wide `contents: write`; it does not validate PR #40/#41. The integration branch's workflow is a separate unmerged version with read-only build/test and isolated main-push release-write. PR #18 remains the main-workflow proposal.
- **Latest Release:** [v0.2.0-build-116](https://github.com/Batmancpu/CurrentLens/releases/tag/v0.2.0-build-116), published 6 November 2026 11:18:59 UTC. No Release created.
- **RSS PRs:** [#40](https://github.com/Batmancpu/CurrentLens/pull/40) now validates both RSS article destinations and every publisher-feed redirect. Head `552b96e7948ac5ade821a711ea8dd225984824a9`; 6 commits, 7 files, draft/open/mergeable; tests added, not run. [#41](https://github.com/Batmancpu/CurrentLens/pull/41) bounds untrusted RSS fields and preserves Atom XHTML summaries plus inline Atom content; head `e4ca4a522dd236052142aa31dc518979bfafe891`; 8 commits, 6 files, draft/open/mergeable; tests added, not run. Both are main-based and both modify `NewsRepository.kt`; reconcile their parser/redirect changes before integration rather than merging independently.
- **Editorial:** current integration `UpscFeedPolicy.kt` still has a generic action-term exception and global syllabus-signal gate. PR #27 remains the existing strict-gate proposal without verified exact-head CI. Entertainment false positives and unrelated-excerpt false positives are not certified resolved.
- **Remaining RSS/security work:** #32 concurrency limit, #38 per-feed parser-entry budget, #33 relative Atom links, #25 publisher image hosts, #26 WebView subresource restrictions, #35 bounded startup diagnostic reads. These require coordinated review.
- **Primary-source evidence:** configured live feeds remain publisher RSS; no per-story independently verified PIB/Gazette/ministry/Parliament/PRS/RBI/Supreme Court/regulator corroboration is yet implemented. Publisher RSS remains secondary provenance.
- **UPSC baseline:** official 2026 Prelims GS I–II and Mains GS I–IV papers are available from UPSC; the 2017–2026 ten-year reference window is current. [Official UPSC previous papers](https://www.upsc.gov.in/examinations/previous-question-papers).
- **Device evidence:** no fresh OPPO Reno10 logcat/tombstone or Perfetto trace. Cold-start causality, visual/IME/TalkBack/font-scale/Reduce Motion and GPU/frame-time/memory/battery/thermal remain **UNVERIFIED**.
- **Safety:** no main edit, merge, workflow dispatch/rerun, Release publication or branch deletion.

---

## Live snapshot — 8 November 2026, 15:55 IST

- **Canonical main:** `73b8a0d1ba33503fede02db2c3c0af082d724a12`, unchanged.
- **Active integration:** `black-box-bihar-news@90508dad6d8ce1327051f78eedf8273a1c5010cc`; PR [#34](https://github.com/Batmancpu/CurrentLens/pull/34) remains draft/open, 140 commits ahead and 0 behind main. The branch-specific workflow is not the workflow currently on main.
- **Repository inventory:** 44 branches, 27 open PRs, 14 closed PRs. No branch deletion or PR merge occurred.
- **Actions:** all four history pages were inspected (330 runs): 199 success, 67 failure, 64 cancelled; 0 queued and 0 in progress. Latest run [#330](https://github.com/Batmancpu/CurrentLens/actions/runs/42469858410) passed on the exact integration head. WordNet builder 4/4; 151,456 unique words; `:app:testDebugUnitTest`; `:app:assembleDebug`; artifact upload. Release job skipped. Artifact [13612618168](https://github.com/Batmancpu/CurrentLens/actions/runs/42469858410/artifacts/13612618168), 39,121,080 bytes, SHA-256 `e5556d4be8c6e760329f9919caad83af93f822611fa6b6be9762355f868e1f9a`, expires 22 November 2026 10:06:44 UTC. This validates the pre-documentation integration head, not PR #41.
- **Workflow:** main still has one push-only `.github/workflows/build-apk.yml` with workflow-wide `contents: write`; it does not validate PR #41. The integration branch's one workflow uses read-only build/test permissions and a separate main-push release-write job. PR #18's main workflow proposal remains open; no workflow was changed.
- **Latest Release:** [v0.2.0-build-116](https://github.com/Batmancpu/CurrentLens/releases/tag/v0.2.0-build-116), published 6 November 2026 11:18:59 UTC. Release list was sorted by publication time; API's default first item was not the newest by timestamp. No Release was created.
- **RSS parser follow-up:** existing draft [PR #41](https://github.com/Batmancpu/CurrentLens/pull/41), based on main, was extended rather than duplicated. New head `e4ca4a522dd236052142aa31dc518979bfafe891`; 8 commits, 6 files, draft/open/mergeable. It now bounds individual RSS fields, preserves nested Atom XHTML summaries, and reads inline Atom `content` text/html/xhtml when no external `src` is present. URL-backed Media RSS content remains an image; external Atom `src` is not fetched. Three additional focused tests were added across field-policy and XML-reader tests. Exact-head statuses/check-runs are empty; tests are **added, not run**.
- **Editorial:** integration `UpscFeedPolicy.kt` still has the generic action-term exception and global syllabus-signal gate. PR #27 is the existing strict-gate proposal, but its head remains unvalidated; do not claim the entertainment false-positive class is resolved.
- **Other integration blockers:** PRs #32/#38 RSS concurrency/entry bounds; #33 relative Atom links; #25/#26 image/WebView resource restrictions; #35 bounded startup diagnostic reads; #40 publisher article-host validation still need reconciliation with shared parser/security files.
- **Device evidence:** no fresh OPPO Reno10 logcat/tombstone or Perfetto trace was available. Native cold-start causality, visual/IME/TalkBack/font-scale/Reduce Motion and GPU/frame-time/memory/battery/thermal behaviour remain **UNVERIFIED**. CI is not device verification.
- **Safety:** no main edit, merge, workflow dispatch/rerun, Release publication or branch deletion.

---

## Live snapshot — 8 November 2026, 05:24 IST

- **Main:** `73b8a0d1ba33503fede02db2c3c0af082d724a12`, unchanged.
- **Active integration/UI:** `black-box-bihar-news@d9a4d1eac035172da0f8d7c40a984eb6c40c1b8b`; 121 ahead / 0 behind main and 64 ahead / 0 behind PR #16's corrected Prismal/stability head. PR #34 remains open/draft to main.
- **Latest exact-head CI:** natural PR run #279 and push run #278 both passed on the exact active SHA. WordNet builder 4/4; 151,456-word offline dictionary; `:app:testDebugUnitTest`; `:app:assembleDebug`; APK artifact upload. Full logs inspected. PR artifact #13594284223 (39,107,398 bytes, SHA-256 `1959a089d31d4a96ee851749d5a3753b9b42e1a2283b381b50dd2462d2762803`); push artifact #13594681752 (39,107,393 bytes). Release jobs skipped.
- **Freshness:** 40 branches and all 37 PRs re-enumerated. PR #27's newer timestamp is editorial-only and divergent; PR #37's Search RowScope fix is already in active code; PR #36's IST-midnight refresh remains a separate diverged MainActivity change; PR #35 diagnostics, #32/#33 feed changes and #25/#26 publisher security work remain separately reviewed. See `docs/BRANCH_CONSOLIDATION.md`.
- **This pass:** system Reduce Motion is now shared through `LocalGlassReduceMotion`; Home destination transitions snap, Reader chrome uses zero-duration motion, and definition popover scale/content-size snap with a short fade. No additional Prismal capture, blur, readback, shader, graphics layer, decode or dependency.
- **Next:** wait for natural CI on the new head, then continue the shared-header/transient-control consistency audit. Do not dispatch/rerun or publish a Release.
- **Remaining limits:** no fresh OPPO Reno10 launch logcat/tombstone, screenshot, TalkBack/Reduce Motion runtime check, Perfetto trace or frame/GPU/memory/battery/thermal measurement. CI success does not establish device launch stability or smooth animation.

---

# CurrentLens — Latest project handoff (7 November 2026, 22:24 IST)

## Live snapshot — 7 November 2026, 22:24 IST

- **Active integration/UI branch:** `black-box-bihar-news@818afb47eaadc685f133eaffc3efd199392627e4` before the Reader Aa pinning change; 109 ahead / 0 behind main and 52 ahead / 0 behind PR #16. It remains the owner-selected single integration line and direct descendant of PR #16's corrected background-only Prismal/stability baseline.
- **Draft consolidation PR:** [#34](https://github.com/Batmancpu/CurrentLens/pull/34), base main, open/draft, 109 ahead / 0 behind. Natural push run [#242](https://github.com/Batmancpu/CurrentLens/actions/runs/42389254191) and exact PR run [#243](https://github.com/Batmancpu/CurrentLens/actions/runs/42389283591) both passed on parent SHA 818afb47. PR artifact 13580718014 (39,094,949 bytes); push artifact 13580578348 (39,094,947 bytes); publish jobs skipped.
- **Repository inventory:** 37 remote branches, 20 open PRs and 14 closed PRs. Full current branch table and exact head metadata are in docs/BRANCH_CONSOLIDATION.md. No source branch was deleted or changed.
- **Liquid Glass state:** primary shell, Search clear, feed-only compact navigation, Reader auto-hide, definition selection pinning, completion dock, Appearance preview/reduced-transparency fallback, Saved/stats filters, supporting dashboard/stat materials and Reader/Quiz transient material are represented. This pass hoists the Reader Aa menu state so the menu pins chrome and suppresses the completion dock while open; its new JVM tests await natural CI.
- **Outstanding non-UI candidates:** PR #27 editorial action gate, #32 bounded RSS concurrency and #33 relative Atom links remain separate; PR #25/#26 remain held for a combined publisher-resource security review. PR #29/#30/#31 and #13's conservative adaptive-contrast policy are already represented.
- **Actions:** 243 total runs fully paginated; 144 success, 58 failure, 41 cancelled; queue empty. Active branch workflow uses read-only build/test permissions and separate main-push Release write. Main workflow remains push-only; PR #34 nevertheless received its exact natural pull_request run #243. Latest Release remains v0.2.0-build-116.
- **No main edit, merge, retarget, workflow change, manual dispatch/rerun or Release publication.**
- **Device evidence:** OPPO Reno10 cold/warm launch, visual screenshots, menu/definition collision, TalkBack, Reduce Motion, animation smoothness, Perfetto/frame timing, GPU overdraw, memory/allocation, thermal and battery remain **UNVERIFIED**. CI success is not device verification.
- **Next:** wait for the natural run on the new Reader Aa pinning head; inspect its exact jobs/artifact before another UI batch. Keep branch-freshness checks mandatory.

> This snapshot supersedes earlier live snapshots below where they conflict. Historical context remains in SESSION_LEDGER.md.
## Live snapshot — 7 November 2026, 11:38 IST

- Current integration branch: black-box-bihar-news, verified pre-batch head cfe0b3e8319374df5cb29423f04a79c77d4fe10e; 97 ahead / 0 behind main and 40 ahead / 0 behind PR #16. The low-effects Appearance batch is authored from this exact head; its resulting SHA and natural CI are pending.
- Full remote inventory: 28 branches, 11 open draft PRs (#24, #22, #21, #18, #17, #16, #13, #12, #11, #8, #6). No branch deleted; no PR merged during this run.
- Latest natural Actions run #200 passed on parent SHA cfe0b3e; WordNet builder tests/database, :app:testDebugUnitTest, :app:assembleDebug and artifact upload succeeded; publish job skipped. Artifact 13560769755 (39,059,340 bytes), CurrentLens-v0.2.8-stability-debug-apk, installs as com.mangoloads.currentlens.stability / brrrNews Stability, not the normal package.
- Latest Release remains v0.2.0-build-116. No main write, Release publication, workflow dispatch or rerun.
- Branch audit: black-box remains the latest coherent consolidation line; brrrNEWS is 4 ahead / 3 behind with equivalent GlassSurfacePolicy/test and only a formatting-only MainActivity difference. PR #24 is a separate editorial-policy draft based on brrrNEWS; it was not ported in this UI batch.
- New Appearance setting: persisted Reduce transparency & effects; low-effects policy disables blur/refraction/chromatic/depth/shadows, raises tint opacity and pauses adaptive luminance sampling. Slider preview is local and applies on release/preset. New JVM tests await the natural build on the new commit.
- Device-backed OPPO launch, visual, accessibility and performance checks remain UNVERIFIED. No device measurement is inferred from CI.

> This is the newest status snapshot and supersedes older dated snapshots below where they conflict. Full chronological context remains in SESSION_LEDGER.md.


## Live snapshot — 7 November 2026
- Main remains 73b8a0d1ba33503fede02db2c3c0af082d724a12; no main write, PR merge or Release was performed in this review.
- The repository has 19 branch refs and eight open draft PRs: #6, #8, #11, #12, #13, #16, #17 and #18. No open issue records were returned in the live issue search. Preserve unique work on the independent PR branches; do not blindly merge diverged branches.
- Actions history was paginated across all 166 runs: 91 success, 44 failure and 31 cancelled; queue and in-progress lists were empty. Latest overall run #166 passed on PR #16; latest main run #116 passed on main; PR #18 run #165 passed.
- Main's latest release is v0.2.0-build-116 from main SHA 73b8a0d; it is older than the unmerged Stability branch. PR #16's Stability 0.2.8 / versionCode 10 APK is an Actions artifact, not a Release: artifact 13533102905, 38,996,079 bytes, 14-day retention.
- PR #16 remains draft/open on fix/early-startup-crash-diagnostics-2026-11-06; head before the current privacy-minimisation commit was d63c22d621a5e8c4c293d1c1b856e8afca478ead. The background-only Prismal capture correction is present. Device-level native-crash causality remains unverified; CI is not a physical-device launch test.
- Main still has one push-only Actions workflow with workflow-wide contents: write. PR #18 proposes normal pull-request CI with read-only build permissions and a separately gated main-release job; its own PR run #165 passed. PR #16 has a branch-specific workflow; reconcile the two workflow versions before any merge.
- The current quality-watch change in PR #16 removes the opaque Android process-exit description, importance and PSS/RSS from the selectable/copyable crash report, retaining only reason/status. Focused tests are added; check the natural CI result before marking them passed.
- Product gaps remain tracked: primary-source corroboration per story, labelled real-world false-positive/false-negative validation, GS I Geography integration, Atom alternate-link handling, date/cache precedence, tracking-URL deduplication, off-main persistence and adaptive glass contrast. Existing PRs #6/#8/#11/#12/#13/#17 cover portions; review their live diffs/ancestry before integration.

## Current repository state
- Main remains at `73b8a0d1ba33503fede02db2c3c0af082d724a12`; no direct-main edit, merge or production release was performed in this stabilisation session.
- Active draft PR: [#16](https://github.com/Batmancpu/CurrentLens/pull/16), branch `fix/early-startup-crash-diagnostics-2026-11-06`.
- The branch contains the launch diagnostics, privacy-minimised reports, network redirect/cookie guards and a Stability-only launch probe. Reuse this branch for this ongoing crash investigation; do not open another branch/PR for the same work.
- Current Stability code is v0.2.8, versionCode 10. CI Build #166 / run `42275323743` passed WordNet generation, JVM tests, APK assembly and artifact upload. Artifact 13533102905 is a 14-day Actions artifact; the workflow did not publish a Release.
- CI is not physical-device verification. The native launch crash is not yet marked fixed.

## Crash evidence and next action
- Stability 0.2.2 reported a native crash with a partial Mali GPU / Android HWUI / Skia rendering trace.
- Stability 0.2.3 forced the Prismal Legacy pipeline but still crashed with `CRASH_NATIVE` / signal 11. The last stored application checkpoint was `MainActivity.setContent returned`; the report did not record the next app-composition checkpoint.
- The 0.2.3 partial tombstone excerpt contains ART finalizer/monitor frames and a system stack-pointer heuristic. This does not prove a source-level stack overflow or identify a specific faulty app function/thread. No Java/Kotlin exception was captured.
- Stability 0.2.5 provides a plain Compose/Material probe on cold launch and a button to enter the full app with the original Liquid Glass rendering restored. Test the probe first, then tap into the full app. Repeat cold launch and full-app entry at least five times.
- Normal/production builds start the full app directly with Prismal's normal automatic pipeline; no global Legacy override remains.
- Never place exact device/build identifiers, process/thread IDs, precise timestamps, memory addresses or raw traces in public records. Diagnostic reports remain local.

## Branch cleanup
At the latest audit there were 17 refs including main. Several are old branches for already-merged PRs; others contain independent unmerged feature work. Branch count itself does not cause the native crash. PR #14's continuity documents are now being copied into this active PR so the duplicate docs PR can be closed after verification. The current GitHub connection cannot delete branch refs; do not claim that stale refs have been deleted. The owner can remove safe merged/stale refs at https://github.com/Batmancpu/CurrentLens/branches after verifying no unique commits.

## Current open feature work (separate from crash fix)
- #6 Geography/editorial mapping
- #8 off-main persistence
- #11 feed publication-date/cache integrity
- #12 Atom alternate-link selection
- #13 adaptive glass contrast
- #14 docs continuity (to be superseded by importing these docs into #16)
- #16 launch stability + privacy (active)

Do not blindly merge diverged feature branches. Check their current diff/ancestry against live main first.

---

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

## Active launcher-crash investigation — PR #16

- User's current symptom: tap launcher icon, Android shows opening animation, no app screen appears, and Android returns to the launcher.
- PR: https://github.com/Batmancpu/CurrentLens/pull/16 (draft; not merged).
- Branch: fix/early-startup-crash-diagnostics-2026-11-06.
- Head: b66315c2f82f7785e7dfd96577d83d203770faad; base main: 73b8a0d1ba33503fede02db2c3c0af082d724a12; 7 commits ahead / 0 behind.
- Root cause is not established: no Android test device logcat, tombstone, ANR or current on-device stack trace was available. Do not claim the crash is fixed.
- Audit found that the old uncaught-exception handler was installed inside MainActivity.onCreate(), but PrismalAGSL v1.0.4 contributes an AndroidX Startup InitializationProvider that runs before the Activity. A managed exception in that earlier startup phase could occur before the old handler existed. Prismal's v1.0.4 initializer itself only binds the asset manager; this is an instrumentation gap, not evidence that Prismal causes the crash. Android references: https://developer.android.com/topic/libraries/app-startup and https://developer.android.com/reference/androidx/startup/InitializationProvider.
- PR #16 installs a custom Application handler from attachBaseContext(), records startup stages, writes reports atomically, detects a prior interrupted startup without inventing a stack trace, replaces the Compose recovery screen with native Views, and makes diagnostic setup best-effort so storage failures cannot block app launch.
- Unit tests were updated/added but not executed. The current workflow only runs on pushes to main; no build was dispatched and no APK exists for this branch.
- After review/merge and installation of the next build, relaunch once if it still exits; the native recovery screen should expose a local report for copying. If a native/system kill still produces no report, collect Android bugreport/logcat/tombstone evidence.
- Merging PR #16 triggers the main-push workflow and publishes a debug APK prerelease; account for this before merging.

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
User reported Build100 immediate crash on the Android test device. Not verified fixed. No logcat/tombstone/ANR currently attached.
User reported Search field too far above keyboard and stuttery morph. Requires actual Android test device screenshot/recording + IME geometry before changing.
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

1. Review PR #16 early-startup crash diagnostics. It is diagnostic hardening, not a proven root-cause fix. After merge and install, reproduce on the Android test device and return the local report or logcat/tombstone.
2. Resolve brrrNEWS vs brrrNews case before future brand updates.
3. Rebase/review PRs #6/#8/#11/#12/#13 separately against latest main; all are diverged.
4. Improve primary-source corroboration with explicit claim/document/authority records.
5. Validate classifier using real labelled positives/false positives/false negatives.
6. Measure Search keyboard gap/morph on device; fix only with geometry evidence.
7. Validate Explore chip TalkBack/touch targets, font scaling, Reduce Motion, safe areas and adaptive glass contrast.
8. Keep this memory current after each material session and refresh files uploaded to ChatGPT Project.

## Continuity documentation PR #14 — exact branch state

- PR: https://github.com/Batmancpu/CurrentLens/pull/14
- State: open draft; base main SHA 73b8a0d1ba33503fede02db2c3c0af082d724a12; branch docs/ai-agent-handoff-2026-11-06.
- It contains ten docs-only files; README is absent from the diff. Re-fetch PR #14 for the exact live head/count; this handoff is deliberately not self-referential about its own last commit.
- The branch includes the initial continuity-doc commit, commit-details addition, mandatory-reading-order update, PR #14 state bookkeeping, and the 6 November launch-crash journal update. The GitHub branch history is canonical; do not rely on this file's prior head if the branch has advanced.
- The branch was rebuilt on latest main; the earlier two-commit docs branch based on ba06a8f was replaced and its README modification is not in this PR.
- Verification: all ten files were fetched from the final branch; commit index contains 119 rows; PR diff paths were checked and README is absent. No CI run is available/expected because the workflow triggers only on pushes to main. No application/workflow source, permissions, assets or release files changed.
- The only remaining human step is to review/merge PR #14 if desired. Merging will trigger the existing main-push workflow and publish a debug APK prerelease; see BUILD_AND_DEBUG.md.

## Crash investigation PR #16

- URL: https://github.com/Batmancpu/CurrentLens/pull/16
- State: open draft; branch fix/early-startup-crash-diagnostics-2026-11-06.
- Branch head: b66315c2f82f7785e7dfd96577d83d203770faad; base main 73b8a0d1ba33503fede02db2c3c0af082d724a12; 7 ahead, 0 behind.
- Files: AndroidManifest.xml, new CurrentLensApplication.kt, LaunchCrashDiagnostics.kt, MainActivity.kt, LaunchCrashDiagnosticsTest.kt.
- Scope: early Java/Kotlin crash instrumentation and non-Compose recovery surface only; no Prismal version, feed logic, app navigation, permissions or workflow changes.
- Tests added but not run; no CI run because the existing workflow triggers only on push to main. Diagnostic I/O/setup is wrapped best-effort to avoid adding a new launch blocker.
- Exact code root cause remains unknown until a real device report is obtained.

## Session continuity

The user continues in ChatGPT and does not want to repeat project context. Start the next chat inside the CurrentLens ChatGPT Project. Read CHATGPT_CONTINUITY.md for setup. GitHub is canonical for code/history, but Project sources are copies and need refresh after updates. Upload COMMIT_DETAILS.md to the Project as well.


## Live repository snapshot — 6 November 2026, supersedes older PR/branch counts above

- `main`: `73b8a0d1ba33503fede02db2c3c0af082d724a12` (unchanged in this session).
- Active crash work: PR #16, branch `fix/early-startup-crash-diagnostics-2026-11-06`; keep this as the single active integration branch for launch stabilisation.
- PR #16 remains draft/open and unmerged. Its 0.2.5 full-app test still crashed after the plain probe; current root cause is not established.
- Other open draft PRs with unique pending work: #6 Geography/editorial mapping, #8 off-main persistence, #11 publication/cache integrity, #12 Atom link selection, #13 adaptive glass contrast. Do not blindly merge or delete their branches.
- Closed historical PRs #1/#2/#3/#5/#7/#9/#10 were merged; their remote branch refs are historical clutter. PR #14 is closed and its continuity docs were imported into PR #16. PR #15 is closed unmerged; its redirect work was consolidated into PR #16. PR #4 is closed unmerged and shares a branch with earlier UI work; inspect its unique commits before considering deletion.
- Repository currently has 17 branch refs and automatic deletion of merged branches is disabled. Branch count does not affect installed APK runtime; it increases review/CI/continuity overhead. The available GitHub connection in this session can inspect and update branches/PRs but does not expose branch-ref deletion or repository-setting mutation. Do not claim refs were deleted. After reviewing unique commits, manually delete only stale refs in GitHub's Branches page; enable automatic deletion of merged branches in repository Settings → General → Pull Requests.
- New engineering work should stay on PR #16; do not create a branch per chat/session/agent. Batch related files into one commit to avoid redundant CI runs.

### Latest device evidence
- Stability 0.2.5 report: native signal-11 crash after the user requested the full app from the stability probe. The short screen recording shows the plain Compose/Material probe renders, then the full-app transition returns to launcher/recovery.
- Prismal Legacy still uses GraphicsLayer capture/draw. The failed Legacy attempt did not exonerate the shared layer path. Normal rendering remains full Liquid Glass.
- Stability 0.2.6 is an incremental diagnostic APK, not a claimed fix. It tests backdrop capture, one Liquid Glass surface, merged tab capture, and full app separately. See root AGENTS.md and KL-017.



## Latest crash diagnosis and correction — 6 November 2026

- Main remains `73b8a0d1ba33503fede02db2c3c0af082d724a12`; no direct main change, PR merge or Release.
- Work remains on existing draft PR #16 / `fix/early-startup-crash-diagnostics-2026-11-06`.
- Strongest code-level diagnosis: the full app had a Prismal backdrop capture on its root content `Column`, while glass descendants inside that captured subtree sampled the same layer. Prismal v1.0.6 documents the capture modifier for background-only content; the prior structure created a self-referential layer dependency.
- Correction commit: `8c5f29d3a53669b3d810e4318d949caef4922f1a`. The captured source is now a separate background-only sibling with subtle ambient gradients; app content and glass surfaces are outside it.
- All original Prismal effects and automatic pipeline remain enabled. No telemetry, upload, extra permissions or raw diagnostic material was introduced.
- Stability version is advanced to 0.2.7 / versionCode 9, preserving the same signing key and separate package so it can replace the prior Stability APK.
- The user declined another staged diagnostic round. Do not ask for one. CI and source review are the available verification in this session; device-level causality remains unverified.
- Fetch live PR #16 metadata for its exact current head and CI run; do not rely on a self-referential branch SHA inside this handoff.
