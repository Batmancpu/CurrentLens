# Third-party notices

CurrentLens is a new Android application and does not bundle or copy whole third-party applications.

## Open-source projects reviewed

- FeedFlow — Apache License 2.0. https://github.com/prof18/feed-flow. Studied for RSS reading, reader mode and local-first interaction patterns. No source files are bundled in this initial version.
- QuickWord — MIT License. https://github.com/jtrv/quickword. Studied for Android text-selection dictionary integration. No QuickWord source files or dictionary corpus are bundled in this initial version.
- Read You — GNU GPL v3. https://github.com/ReadYouApp/ReadYou. Visual/reference project only; no GPL source is bundled.
- Feeder — GNU GPL v3. https://github.com/spacecowboy/Feeder. Feed reliability/reference project only; no GPL source is bundled.

## Runtime dependencies

Runtime dependencies are declared in app/build.gradle.kts and retain their respective licences:
- AndroidX Jetpack Compose and AndroidX Browser.
- Coil for image loading.
- Kotlin and Kotlin Coroutines.

## Dictionary content

Definitions are requested from https://dictionaryapi.dev/ and cached locally. No dictionary database is redistributed by this project.

## Publisher feeds

Headlines, excerpts and article URLs are supplied by publisher RSS feeds. CurrentLens attributes the source and links to the original article. Publisher terms apply; some RSS feeds are for personal, non-commercial use only.
