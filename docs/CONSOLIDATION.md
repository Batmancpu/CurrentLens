# Main consolidation — 14 November 2026

The baseline is the verified integration line `e14b284b158410ada1b9eaeecd6698d90387610a` from draft #34, containing the prior continuity work, with published main `73b8a0d1ba33503fede02db2c3c0af082d724a12` as an ancestor. Its run #416 passed on the integration source. The new main workflow and combined changes need their own CI and handset acceptance.

The table accounts for the 51 live branch heads reviewed before consolidation. The historical [branch ledger](BRANCH_CONSOLIDATION.md) records full SHAs and PR history. “Represented” includes changes independently implemented with equivalent behavior. Keep source branches until the owner has the accepted APK and reviews any deferred behavior.

| Branches and proposals | Main disposition |
| --- | --- |
| `main`, `black-box-bihar-news`, `docs/ai-agent-handoff-2026-11-06` (#14, #34) | Published history, implementation and continuity carried via integration ancestry. |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06`, `ci/stacked-pr-validation-2026-11-07` (#18, #20) | Main-only validation, serialized publication and isolated write permissions replace the stacked CI workflow. |
| `a11y/48dp-icon-targets-2026-11-07`, `a11y/dictionary-popup-reduce-motion-2026-11-09`, `a11y/dictionary-popup-reduce-transparency-2026-11-09` (#30, #45, #46) | Active policies already represented in baseline. |
| `fix/editorial-signal-count-test`, `fix/editorial-token-boundaries-2026-11-06`, `fix/editorial-heatwave-bill-signals-2026-11-07`, `fix/parliamentary-syllabus-signal-gate-2026-11-07`, `feat/gs1-geography-editorial-coverage` (#1, #2, #6, #21, #23) | Historical main or integrated geographic, heat-wave and legislative policy. Retain live editorial validation as roadmap work. |
| `fix/editorial-action-gate-blackbox-2026-11-07`, `fix/editorial-policy-exception-guard-2026-11-07`, `fix/gst-council-syllabus-disambiguation-2026-11-08` (#24, #27, #42) | Strict institutional action and media-framing rules adapted to the current relevance API. Added selected-rule signals and GST Council disambiguation with existing focused fixtures. #27's broader suite still needs exact-head CI. |
| `fix/publisher-redirect-allowlist-2026-11-06`, `fix/validate-rss-article-hosts-2026-11-08` (#15, #40) | Use existing per-hop feed validation, plus publisher HTTPS article links checked before ranking alternate links. |
| `fix/atom-alternate-link-selection-2026-11-06`, `fix/resolve-relative-atom-links-2026-11-07`, `fix/cache-publication-date-integrity-2026-11-06`, `fix/rss-tracking-variant-dedup-2026-11-06` (#11, #12, #17, #33) | Baseline link order, date/cache and identity policies retained. Relative links use effective feed and XML Base scope. |
| `perf/bounded-rss-fetch-concurrency-2026-11-07`, `perf/bound-rss-entry-parsing-2026-11-08`, `perf/bound-rss-field-values-2026-11-08` (#32, #38, #41) | Adapt bounded fetches and parser entries, typed inline Atom text and individual field limits into one parser. |
| `fix/rss-image-host-allowlist-2026-11-07`, `privacy/webview-publisher-subresource-gate-2026-11-07` (#25, #26) | Shared publisher asset policy validates feed/legacy card and extracted Reader images. WebView checks initial subresource URL and method. Live compatibility and redirect behavior remain device work. |
| `perf/off-main-session-persistence-2026-11-06`, `perf/off-main-bookmark-persistence-2026-11-07`, `perf/off-main-appearance-preferences-2026-11-07`, `perf/bounded-read-history-2026-11-07` (#8, #22, #29, #31) | Integrated off-main persistence and bounded new read history already cover the proposed direction. Retain legacy read markers. |
| `fix/startup-stability-performance-2026-11-06`, `fix/startup-crash-diagnostics-feed-jank-2026-11-06`, `fix/early-startup-crash-diagnostics-2026-11-06`, `perf/bound-startup-diagnostic-reads-2026-11-07` (#7, #9, #16, #35) | Integrated launch and bounded diagnostic I/O. Native crash remains open until normal handset launch is verified. |
| `fix/navigation-icon-imports-2026-11-06`, `fix/search-glass-theme-capture-2026-11-06`, `fix/search-clear-row-scope-compile-2026-11-08`, `fix/feed-card-rim-wiring-2026-11-09`, `fix/quiz-option-selected-semantics-compile-2026-11-07`, `fix/today-ist-midnight-rollover-2026-11-08` (#5, #10, #28, #36, #37, #44) | Baseline includes the corresponding navigation, Search, card, quiz and midnight fixes. |
| `fix/release-reader-webview-resources-2026-11-09`, `ui/definition-popup-transient-optics-2026-11-09`, `ui/foreground-adaptive-glass-contrast-2026-11-06`, `ui/liquid-glass-system-2026-11-07`, `ui/liquid-glass-system-continuation-2026-11-07` (#13, #19, #43, #47) | Baseline carries Reader disposal, shared material, opt-in motion/accessibility and the foreground luminance probe. Preserve device checks. |
| `brrrNEWS`, `redesign/v0.3-search-reader`, `ui/liquid-glass-performance-2026-11-06` (#3, #4) | Superseded UI paths. Keep current Search, Reader and glass implementation. |
| `fix/stable-release-signing-2026-11-08`, `security/pin-wordnet-archive-sha256-2026-11-09` (#39, #48) | Use the pinned WordNet archive, permanent certificate verification and isolated signing adapted to the fresh install and release variant. |

## Branch cleanup checkpoint

The cleanup merge preserves the history of all 50 non-main branches in the ancestry of main. The first parent is the tested main baseline; the additional parents retain the independent branch tips. Its app tree uses the already consolidated implementation plus the version bump. The list below maps each reviewed remote branch to its exact tip for deletion and recovery. A branch that advances after this snapshot must be checked again before deletion.

| Branch | Preserved tip |
| --- | --- |
| `a11y/dictionary-popup-reduce-motion-2026-11-09` | `fbadd56e0e119cf70b865af8a58843190cce249d` |
| `a11y/dictionary-popup-reduce-transparency-2026-11-09` | `55afb9ab662d2926675f0a57737d3f817b1fc2cb` |
| `a11y/48dp-icon-targets-2026-11-07` | `25107ce905b70bde1a41282ffd1d606819ebf58d` |
| `black-box-bihar-news` | `e14b284b158410ada1b9eaeecd6698d90387610a` |
| `brrrNEWS` | `e6fcfc25a4c8d5f363ced58f313552120aa7ff96` |
| `ci/read-only-pr-validation-isolated-release-write-2026-11-06` | `963118f0abe5f7e38f2aa66a49d4b8e74548660c` |
| `ci/stacked-pr-validation-2026-11-07` | `5dc3179361835b77fb3186a99d9b5791301463fd` |
| `docs/ai-agent-handoff-2026-11-06` | `73b8a0d1ba33503fede02db2c3c0af082d724a12` |
| `feat/gs1-geography-editorial-coverage` | `ca66fa1c865514a86c655a4b6c7eaf16b4681f81` |
| `fix/atom-alternate-link-selection-2026-11-06` | `cdf4c3846dde1c05894284e48b8f7d69cb7541ad` |
| `fix/cache-publication-date-integrity-2026-11-06` | `be5151e674c2bd4fa9beb166fc38ddf622781f84` |
| `fix/early-startup-crash-diagnostics-2026-11-06` | `ebea0aac68ce2cf17aad376304f7915fe937e807` |
| `fix/editorial-action-gate-blackbox-2026-11-07` | `2468ed33326ac7310a2ddd5f993495e4051245b8` |
| `fix/editorial-heatwave-bill-signals-2026-11-07` | `89464104e300f48da10e3e32e33715cbe2405084` |
| `fix/editorial-policy-exception-guard-2026-11-07` | `1f7a3c2a7b74cc02cfee6fe451f4576feb291acf` |
| `fix/editorial-signal-count-test` | `4738690d6e7f6c7c02a621742f25d5ebf1d631fb` |
| `fix/editorial-token-boundaries-2026-11-06` | `0045c24637f162f1c13dceb1f8878f42f6eb15fd` |
| `fix/feed-card-rim-wiring-2026-11-09` | `3bcde08a4ba7ee6e9420864d9bb044b8fe1d4895` |
| `fix/gst-council-syllabus-disambiguation-2026-11-08` | `2629cfbe4ee6ece7a41fd13d35dc987abc1e26b8` |
| `fix/navigation-icon-imports-2026-11-06` | `fa8868940c08ed5b4a9def014a850e8a9dcb3490` |
| `fix/parliamentary-syllabus-signal-gate-2026-11-07` | `9d754953a29c0355ce55306ce4fd5463193c9dbb` |
| `fix/publisher-redirect-allowlist-2026-11-06` | `83a94ac9c3fdc4e70f1ef35c65e9cf793aa9b306` |
| `fix/quiz-option-selected-semantics-compile-2026-11-07` | `6f9b3c1e1a484233721863d0b736734c0d282cd5` |
| `fix/release-reader-webview-resources-2026-11-09` | `e5b7b9f8f5d9e33021bd6ccb7b7cb3f827218e3c` |
| `fix/resolve-relative-atom-links-2026-11-07` | `c97d6b960e2cc6b93ac6444ebf9ceb6eabd320a4` |
| `fix/rss-image-host-allowlist-2026-11-07` | `7a51c9c2c40aa89997db55194a6db3d0d65ac95d` |
| `fix/rss-tracking-variant-dedup-2026-11-06` | `2093990be43a8a9b8eed43f8e4ec1e49c0b29f1b` |
| `fix/search-clear-row-scope-compile-2026-11-08` | `9dd4ceac08e88e6ee4ea565025a70539a6e9e331` |
| `fix/search-glass-theme-capture-2026-11-06` | `c514eff3d5fd266ce6b6ecec51a141055e5fa52e` |
| `fix/stable-release-signing-2026-11-08` | `4250de5040a8ca0efbf96a990218ef7d69ef26e8` |
| `fix/startup-crash-diagnostics-feed-jank-2026-11-06` | `5da4e789b0f49c0eefda024410ade34c1e0f2a7c` |
| `fix/startup-stability-performance-2026-11-06` | `654e660347de692ef88531dfa49c610269d69671` |
| `fix/today-ist-midnight-rollover-2026-11-08` | `5fbd14e6ef9be33d04a882ba463d4c72473419e2` |
| `fix/validate-rss-article-hosts-2026-11-08` | `552b96e7948ac5ade821a711ea8dd225984824a9` |
| `perf/bound-rss-entry-parsing-2026-11-08` | `adade1ed8494c66b7e567de87f61f3dac3da7788` |
| `perf/bound-rss-field-values-2026-11-08` | `39acde8373eb3405f655e052ccd3445f22eedebb` |
| `perf/bound-startup-diagnostic-reads-2026-11-07` | `c4a977831846224182f16981b15e5c803761e26d` |
| `perf/bounded-read-history-2026-11-07` | `910178af3f595993557533cb65d03e714bcd7760` |
| `perf/bounded-rss-fetch-concurrency-2026-11-07` | `8c56c98c83cc69451f128aa259df86a8ce28f43a` |
| `perf/off-main-appearance-preferences-2026-11-07` | `bc48daec98ff076c9db3c2c69084d9b08cd0bbce` |
| `perf/off-main-bookmark-persistence-2026-11-07` | `a64857b5efa62ea053c0c96e5f374fd35924b340` |
| `perf/off-main-session-persistence-2026-11-06` | `ed91a3bfbcb3f12f6f6050ce72a120b3aafeae4a` |
| `privacy/webview-publisher-subresource-gate-2026-11-07` | `b8d2ef9ff2413c378347986d00961c401dfa771d` |
| `redesign/v0.3-search-reader` | `654cc89dee4f93fea98bb76df9d8cf88abf2f0d8` |
| `security/pin-wordnet-archive-sha256-2026-11-09` | `e99e6d5b4e36b308e4435dd4d03ec6da549e06d3` |
| `ui/definition-popup-transient-optics-2026-11-09` | `d848a142fd5c61388286379fec34d985771de50d` |
| `ui/foreground-adaptive-glass-contrast-2026-11-06` | `3dc0f3fb0eb4ca69facdfa44eeb26c00e9b55264` |
| `ui/liquid-glass-performance-2026-11-06` | `2e9c7f62ca65a899e18d9026e1535392835b3724` |
| `ui/liquid-glass-system-2026-11-07` | `1f7cd2e5ddc5d169d1443035a8334538e44efc40` |
| `ui/liquid-glass-system-continuation-2026-11-07` | `f031bf21229f43a992384cc23fa0f4da57e547a1` |

The owner performs branch deletion. Keep `main` and release tags. Before deleting a ref, compare its live tip with the recorded SHA or verify that it is an ancestor of current main.
