# CurrentLens

A free, local-first Android news reader with a premium editorial interface, UPSC study context and a system-wide word dictionary.

## Product

- Exactly four bottom tabs: Today, Explore, Archive and Saved.
- Today displays stories published on the current India Standard Time date.
- Weekend reading highlights the Indian Express Express Sunday Eye and selected The Hindu books, opinion and life-style feeds.
- Explore filters today's stories by topic.
- Archive groups locally cached recent stories by date for up to 30 days.
- Saved contains bookmarks and reading history.
- Full article reading stays inside CurrentLens. The open-source Klead parser creates a clean reader from HTML normally returned by a publisher. If extraction is blocked, returns a short preview or requires a subscription, the original page opens inside the app. No paywall is bypassed.
- Select a word in the reader and choose Define from the text-selection toolbar to show a frosted definition card.
- Liquid-glass controls use PrismalAGSL (MIT), including a draggable four-tab bar and adjustable glass intensity.

## Build and download

One concurrency-protected GitHub Actions workflow builds a debug APK and publishes the APK itself as a GitHub Release asset. Download from https://github.com/Batmancpu/CurrentLens/releases.

These are testing builds. A persistent signing key should be configured before expecting seamless upgrades across separately built APKs.

## Privacy

No account, AI runtime, paid API, analytics SDK or tracking service is included. Network access is used for publisher RSS feeds, publisher pages and word definitions. RSS metadata, bookmarks and reading history are cached locally.

## Licence

CurrentLens application source is MIT licensed. Third-party dependencies and data retain their upstream licences; see THIRD_PARTY_NOTICES.md.
