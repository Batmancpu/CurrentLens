package com.mangoloads.currentlens

import org.xmlpull.v1.XmlPullParser

/**
 * Reads RSS/Atom text constructs without assuming they contain text-only XML.
 *
 * Atom XHTML text constructs legally contain a nested XHTML div. XmlPullParser.nextText()
 * throws when it encounters that child element, which previously caused the feed parser
 * to silently discard the title or summary. This reader flattens descendant text and
 * consumes the complete outer element so the caller can safely continue parsing.
 */
internal object FeedXmlTextReader {
    fun readElementText(parser: XmlPullParser, maxChars: Int): String {
        require(maxChars >= 0) { "maxChars must be non-negative" }
        check(parser.eventType == XmlPullParser.START_TAG) {
            "Parser must be positioned on START_TAG"
        }

        val elementDepth = parser.depth
        val result = StringBuilder(minOf(maxChars, 256))
        var limitReached = maxChars == 0
        var event = parser.next()

        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.END_TAG && parser.depth == elementDepth) {
                return result.toString()
            }

            if (!limitReached &&
                (event == XmlPullParser.TEXT ||
                    event == XmlPullParser.CDSECT ||
                    event == XmlPullParser.ENTITY_REF)
            ) {
                val chunk = parser.text.orEmpty()
                var offset = 0
                while (offset < chunk.length) {
                    val current = chunk[offset]
                    val charCount = when {
                        Character.isHighSurrogate(current) &&
                            offset + 1 < chunk.length &&
                            Character.isLowSurrogate(chunk[offset + 1]) -> 2
                        Character.isSurrogate(current) -> {
                            limitReached = true
                            break
                        }
                        else -> 1
                    }
                    if (result.length + charCount > maxChars) {
                        limitReached = true
                        break
                    }
                    result.append(chunk, offset, offset + charCount)
                    offset += charCount
                }
                if (result.length >= maxChars) limitReached = true
            }

            event = parser.next()
        }

        return result.toString()
    }
}
