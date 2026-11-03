# CurrentLens

A free, local-first Android news reader for Indian and international journalism, with UPSC-oriented study tools.

## Navigation

The bottom bar has **exactly four destinations**:

1. **Today** — stories published on the current Asia/Kolkata calendar date.
2. **Explore** — the broader retained news feed, organised by topic.
3. **Search** — a dedicated destination with headline, publisher, topic, subject and GS-paper filters.
4. **You** — the personal dashboard and entry point to Saved, Reading Stats, Quiz & Practice Stats, and Settings.

There is no Archive destination and no Saved bottom tab. **Saved** is a section inside **You**. Existing appearance settings remain available from You.

## Personal data and study statistics

- The You dashboard shows unique read markers, articles read today, saved articles, submitted quiz attempts, and correct/incorrect answers.
- Legacy read URLs remain read-history markers. They contribute to the unique-article count only; no historical date or reading duration is inferred.
- Reading statistics use persisted foreground reader sessions. Time is counted only while the reader is foregrounded, flushed on lifecycle pause/exit, and grouped by Asia/Kolkata calendar day. Filters cover date range, subject and GS paper.
- Quiz statistics use submitted attempts only. Submitted answer selections, correct answers and provenance are persisted for the latest 500 detailed attempts; aggregate attempt summaries are retained for up to 5,000 attempts. Unsubmitted selections are never stored as attempts.
- RSS metadata classification is an indicative keyword mapping, not a validated UPSC relevance score. It should be checked against the official syllabus and relevant previous-year papers before being used for study decisions.

## News sources and reader behaviour

- RSS supplies attributed headlines, publication dates, images and short excerpts; it does not guarantee full article text.
- CurrentLens attempts best-effort extraction only from publicly accessible publisher pages, without logged-in cookies, subscription tokens, proxying or paywall circumvention. If extraction is blocked, paywalled or incomplete, the original publisher page is offered in the in-app WebView when its HTTPS host matches the publisher allow-list.
- If neither a public article body nor a verified publisher page can be shown, the reader explains that only the RSS headline/excerpt remains available in the feed.
- RSS metadata is cached locally with a 30-day retention window and a 500-article soft limit; saved stories are retained. Extracted public article bodies use a separate cache capped at 30 entries and 24 MB, with a 2 MB per-entry limit and 30-day expiry.
- Newly fetched stories retain the configured originating RSS feed URL in the local metadata cache. Duplicate article URLs keep the first feed match; section-specific feeds are intentionally listed before broad feeds.
- Explicit RSS timezone offsets are preserved. Timezone-less ISO date/time values and date-only values use Asia/Kolkata; unparseable dates remain undated rather than being silently interpreted as UTC.
- Feed URLs and availability can change. The Indian Express publishes an official RSS directory and limits feed consumption to personal, non-commercial use. Publisher terms apply.

## UPSC practice

- The five-question flow distinguishes headline comprehension, indicative syllabus mapping and original practice prompts.
- Authored practice is not an official UPSC PYQ. A question is labelled as an official PYQ only when its year, paper and official source have been verified.
- The current mapping uses transparent keyword matches over RSS headline/excerpt text. It is preliminary and not an exam prediction.

## Privacy and dependencies

- No accounts, analytics, telemetry, cloud AI runtime or paid APIs are included.
- Network access is used for RSS feeds, publicly accessible publisher pages and the free dictionary endpoint. Bookmarks, read markers, study sessions, quiz attempts, settings and caches are stored locally.
- The only declared Android permission is INTERNET; cleartext traffic is disabled and Android backup is disabled.
- Runtime dependencies retain their own licences. See [THIRD_PARTY_NOTICES.md](THIRD_PARTY_NOTICES.md).

## Build and download

The single concurrency-protected **Build Android APK** workflow runs on pushes to main, runs JVM unit tests before packaging, and publishes a debug-signed APK as a GitHub Release asset. Actions artifacts are retained for 14 days.

[Download the latest APK from Releases](https://github.com/Batmancpu/CurrentLens/releases).

These are testing builds, not production or Play Store releases. Android may ask you to allow installation from your browser or file manager.

## Licence

Application source: MIT. Third-party libraries and data retain their original licences; see THIRD_PARTY_NOTICES.md.
