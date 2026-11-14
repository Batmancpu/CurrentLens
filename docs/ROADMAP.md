# CurrentLens roadmap

CurrentLens is a focused, local-first UPSC current-affairs reader. The product should help a student read a handful of relevant stories, understand the official syllabus connection, and recall them later. The main destinations remain Today, Explore, Search and You. Existing local-first behavior, offline dictionary and Liquid Glass design are part of the baseline.

| Order | Scope | Exit evidence |
| --- | --- | --- |
| 1 | Consolidate the reviewed integration work on main and establish the fresh signed APK line. | One successful main run, one APK asset, verified app ID/version/certificate, and backed-up signing identity. |
| 2 | Reliability pass on the initial APK. Test five ordinary cold launches, warm return, Reader enter/exit, Search/keyboard, saved article and quiz reopen, and offline WordNet. | Record phone results for each path. Verify the next higher-version APK installs over the first with bookmarks and quiz history intact. |
| 3 | Feed and Reader correctness. Exercise live feeds, redirects, Atom bases, cards with missing images, partial content, cache recovery and slow/no network. | Fixture tests plus live publisher checks. Keep one coherent story across publisher/tracking variants only where identity can be defended. |
| 4 | Editorial trust. Build a labelled real-story corpus with feedback on misses, noise, vague mappings and wrong papers. Add a concise “Why it matters” explanation and an official document link when a claim can be corroborated. | Map against the latest syllabus and official previous-year papers. Display original publisher attribution and show the issuing body/date on verified links. Publish measured editorial precision/recall only after the labelled evaluation. |
| 5 | Small study tools after stability. Prototype a topic-based review queue under You, a short optional recall prompt and a local note linking the story to a specific static topic. | Check actual use on a small set of issues. Existing Saved data survives. Original practice is labelled and official PYQs link to the source. |
| 6 | Maintainability and measured accessibility/performance. Use the largest stable subsystem boundaries to reduce MainActivity complexity one change at a time, and compare release frame timings before and after. | Tests around the extracted behavior, device checks for TalkBack, font size and reduced effects, plus startup/frame traces where making performance claims. |

## Ideas and evidence

- **A shorter Today list with an explainable stopping point.** A ranked shortlist and a visible boundary make it easier to finish current affairs and return to static study. First measure false exclusions and retention of consequential stories. Aspirant discussions offer qualitative evidence for source and note overload. [Reddit discussion on source overload](https://www.reddit.com/r/UPSC/comments/1xf9lhn/current_affairs_fomo/)
- **One story, one explicit syllabus link.** Show paper, syllabus phrase, an explanation and official corroboration when available. Pilot on public-document stories, including the difference between GST Council composition and a GST rate decision. [UPSC notification archive](https://cms.upsc.gov.in/exam-notification/archives/) · [Official question papers](https://www.upsc.gov.in/examinations/previous-question-papers) · [Reddit discussion on linking static topics](https://www.reddit.com/r/UPSC/comments/1vvkqrb/whats_your_actual_system_for_tying_current/)
- **Review in a few minutes.** Let the user add one recall prompt to a saved topic. Start with small prompts and direct source links. Defer a full flashcard system until the smaller flow is tested. [Reddit account of a read–recall routine](https://www.reddit.com/r/UPSC/comments/1wrlvrh/heres_the_current_affairs_system_i_actually/)
- **Keep user data portable.** Design a local, versioned export/import for Saved, notes and study history. Test round trip, duplicates, malformed imports and rollback before considering background sync. Keep data local by default.

## Blind spots and operating checks

- The reported native launch crash is not proven fixed. Device behavior and CI are recorded separately. For changes that affect startup, measure cold and warm launch using the ordinary installed APK. [Android Macrobenchmark](https://developer.android.com/topic/performance/benchmarking/macrobenchmark-overview)
- Feed availability, asset hosts, redirected links, and publisher terms can change. Add smoke-check coverage that distinguishes publishers unavailable from a genuinely empty eligible feed. WebView initial subresource interception covers the first request and may miss subsequent redirect hops.
- Data loss can occur when crossing signing identities or changing storage. Require install-over and retained-data evidence for the release line. Use local-only failure evidence and keep all private data out of public history.
- An old branch name or a green parent CI run is weak evidence for current behavior. Compare source at exact SHAs, keep tests for risky paths, and record the exact build and phone result. One workflow at a time uses a shared concurrency group. [GitHub concurrency docs](https://docs.github.com/en/actions/how-tos/write-workflows/choose-when-workflows-run/control-workflow-concurrency)
- Reddit describes individual experiences. Official UPSC notices and question papers define the exam reference. Primary sources and publisher terms define what can be corroborated and displayed.

## Agent checklist

Start each session from live main and this ordered list. Record what changed even when the fix is small. For each chunk, write acceptance criteria, keep the diff focused and record commands, results, exact SHA/run and what needs a handset. Advance one tested batch at a time. For a failure, preserve the original error, investigate, and record the correction before starting new work. Keep the release settings, signing identity and previous release traceable in the runbook.
