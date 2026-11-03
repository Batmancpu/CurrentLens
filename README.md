# CurrentLens

A free, open-source Android editorial news reader for Indian and international journalism, with a four-tab native feed and UPSC-oriented study tools.

## Product principles

- Exactly four bottom tabs: Today, Explore, Archive and Saved. Settings stay in the top toolbar.
- Today is filtered by the calendar date in Asia/Kolkata. The local article metadata cache is capped at 30 days (plus older bookmarked stories), with a bounded archive rather than an endless feed.
- RSS supplies attributed headlines, publication dates, images and short excerpts; it does not supply full articles. CurrentLens attempts best-effort extraction only from publicly accessible publisher pages, without WebView cookies, subscription tokens or paywall circumvention. If extraction is blocked, paywalled or incomplete, the original publisher page remains available in the in-app WebView.
- Article extraction is not yet an offline full-text archive. Publisher pages are fetched when opened; RSS metadata and excerpts are cached locally.
- The five-question flow distinguishes the headline/excerpt comprehension item from authored UPSC practice prompts. Generic prompts are never described as article-specific, and no item is labelled an official PYQ without a verified UPSC paper reference.
- The current syllabus mapping is a preliminary keyword match over the headline and RSS excerpt. The app shows the matched terms and GS mapping, not a numeric relevance score. It is not yet validated against a structured official UPSC syllabus/PYQ corpus and is not an exam prediction.
- No accounts, subscriptions, paid APIs, AI runtime, telemetry or analytics. The dictionary uses a free public API and caches successful lookups locally.

## Initial sources

- The Hindu home RSS: https://www.thehindu.com/feeder/default.rss
- The Hindu international RSS: https://www.thehindu.com/news/international/feeder/default.rss
- The Indian Express India: https://indianexpress.com/section/india/feed/
- The Indian Express Explained: https://indianexpress.com/section/explained/feed/
- The Indian Express UPSC Current Affairs: https://indianexpress.com/section/upsc-current-affairs/feed/
- The Indian Express Express Sunday Eye: https://indianexpress.com/section/express-sunday-eye/feed/
- BBC World RSS: https://feeds.bbci.co.uk/news/world/rss.xml
- DW English RSS: https://rss.dw.com/rdf/rss-en-all
- Al Jazeera English RSS: https://www.aljazeera.com/xml/rss/all.xml

The Indian Express lists Express Sunday Eye on its official RSS directory and states that RSS-feed consumption is for personal, non-commercial use. The Hindu's currently verified public feed catalogue does not identify an active Sunday Magazine/Weekend RSS endpoint; CurrentLens does not guess one. This can be revisited when a publisher-verified feed is available.

## Reader and publisher rights

CurrentLens preserves publisher attribution and original article links. It does not remove subscription overlays, reuse logged-in cookies, proxy restricted pages or attempt to defeat access controls. RSS headlines and excerpts remain clearly identified as publisher feed metadata. Publisher terms apply; feed availability and terms can change.

## Build and download

The single concurrency-protected **Build Android APK** workflow runs on pushes to `main` and publishes a debug-signed installable APK as a GitHub Release asset. Download from [Releases](https://github.com/Batmancpu/CurrentLens/releases). Actions artifacts are retained for 14 days.

These are testing builds, not production or Play Store releases. Android may ask you to allow installation from your browser or file manager.

## Privacy

Network access is used only for RSS feeds, publicly accessible publisher pages and dictionary definitions. Saved bookmarks, read history, display settings and RSS metadata stay on the device. No analytics or account SDK is included.

## Licence

Application source: MIT. Third-party libraries and data retain their original licences; see `THIRD_PARTY_NOTICES.md`.
