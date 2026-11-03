# CurrentLens

A free, open-source Android news reader built around Indian and international journalism, UPSC relevance mapping, article-end study questions, and system-wide word lookup.

## Product principles

- Native Android app (Kotlin + Jetpack Compose).
- No accounts, subscriptions, paid APIs, AI runtime, telemetry, or tracking.
- Publisher RSS feeds are used for discovery and excerpts; full stories are opened from the original publisher inside the app.
- Local-first bookmarks, reading history, dictionary cache, and UPSC metadata.
- Five-question learning flow appears when the user reaches the end of an article.
- A rule-based UPSC relevance estimate is shown with its matching syllabus themes and PYQ references. It is a study-priority aid, never an exam prediction.

## Initial sources

- The Hindu RSS: https://www.thehindu.com/feeder/default.rss
- The Indian Express RSS: https://indianexpress.com/section/india/feed/
- The Indian Express Explained: https://indianexpress.com/section/explained/feed/
- The Indian Express UPSC Current Affairs: https://indianexpress.com/section/upsc-current-affairs/feed/
- BBC World RSS: https://feeds.bbci.co.uk/news/world/rss.xml
- DW English RSS: https://rss.dw.com/rdf/rss-en-all

Feed availability and publisher terms can change. CurrentLens links to publisher pages and does not republish full articles.

## Build and download

The single concurrency-protected **Build Android APK** workflow runs on pushes to `main` and publishes a debug-signed installable APK as a GitHub Release asset. Download the APK from [Releases](https://github.com/Batmancpu/CurrentLens/releases). The Actions artifact is also retained for 14 days.

These are testing builds, not production or Play Store releases. Android may ask you to allow installation from your browser or file manager.

## Privacy

No account or analytics SDK is included. Network access is used only for RSS feeds, publisher pages, and dictionary definitions. Saved content stays on the device.

## Licence

Application source: MIT. Third-party libraries and data retain their original licences; see `THIRD_PARTY_NOTICES.md`.
