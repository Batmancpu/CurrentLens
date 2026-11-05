# Third-party notices

CurrentLens is an original Android application. It does not bundle or copy whole third-party applications.

## Open-source projects reviewed

- PrismalAGSL — MIT License. https://github.com/styropyr0/PrismalAGSL. Used for Compose glass surfaces, four-tab glass bar and adaptive AGSL effects.
- Jsoup — MIT License. https://github.com/jhy/jsoup. Used to parse publicly accessible publisher HTML; it does not bypass paywalls or use logged-in cookies.
- FeedFlow — Apache License 2.0. https://github.com/prof18/feed-flow. Studied for RSS reading, reader mode and local-first interaction patterns; no source files are bundled.
- QuickWord — MIT License. https://github.com/jtrv/quickword. Studied for Android text-selection dictionary integration; no QuickWord source files or dictionary corpus are bundled.
- Read You — GNU GPL v3. https://github.com/ReadYouApp/ReadYou. Visual/reference project only; no GPL source is bundled.
- Feeder — GNU GPL v3. https://github.com/spacecowboy/Feeder. Feed reliability/reference project only; no GPL source is bundled.

## Runtime dependencies

Runtime dependencies are declared in app/build.gradle.kts and retain their respective licences:
- AndroidX Jetpack Compose and AndroidX Activity — Apache License 2.0.
- PrismalAGSL — MIT License.
- Jsoup — MIT License.
- Coil — Apache License 2.0.
- Kotlin and Kotlin Coroutines — Apache License 2.0.

No database library was added for the dictionary; the Android platform SQLite engine is used.

## Bundled dictionary data

- Open English WordNet 2025+ WNDB, released 31 December 2025, is converted into a local SQLite lexicon during the Android build. It is licensed under CC BY 4.0 and derives from Princeton WordNet 3.1. Attribution to both the Open English WordNet team and Princeton WordNet 3.1 is preserved in the app's dictionary surface and in the bundled attribution text under app/src/main/assets/licenses/.
- The dataset is downloaded from the official Open English WordNet downloads page during CI. It is not fetched by the app at runtime. The app performs dictionary lookup against its local SQLite copy.
- Dataset source: https://en-word.net/downloads
- Dataset project and licence: https://github.com/globalwordnet/english-wordnet

## Publisher feeds

Headlines, excerpts and article URLs are supplied by publisher RSS feeds. CurrentLens attributes the source and links to the original article. Publisher terms apply; the Indian Express states that its RSS feeds are for personal, non-commercial use.
