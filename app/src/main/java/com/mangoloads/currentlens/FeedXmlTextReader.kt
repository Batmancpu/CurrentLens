package com.mangoloads.currentlens

import java.util.Locale
import org.xmlpull.v1.XmlPullParser

/**
 * Reads RSS/Atom text constructs without assuming they contain text-only XML.
 *
 * Atom XHTML text constructs legally contain nested XHTML elements. XmlPullParser.nextText()
 * throws when it encounters a child element, which previously caused the feed parser to
 * silently discard the title or summary. This reader flattens descendant text, preserves
 * word boundaries across block elements, and consumes the complete outer element so the
 * caller can safely continue parsing.
 */
internal object FeedXmlTextReader {
    private val blockElements = setOf(
        "address", "article", "aside", "blockquote", "br", "dd", "div", "dl", "dt",
        "fieldset", "figcaption", "figure", "footer", "form",
        "h1", "h2", "h3", "h4", "h5", "h6", "header", "hr",
        "li", "main", "nav", "ol", "p", "pre", "section",
        "table", "tbody", "td", "tfoot", "th", "thead", "tr", "ul"
    )
    // Executable/presentation-only XHTML is not article evidence and must not
    // contribute syllabus keywords to the editorial classifier.
    private val ignoredSubtreeElements = setOf("script", "style")

    fun readElementText(parser: XmlPullParser, maxChars: Int): String {
        require(maxChars >= 0) { "maxChars must be non-negative" }
        check(parser.eventType == XmlPullParser.START_TAG) {
            "Parser must be positioned on START_TAG"
        }

        val elementDepth = parser.depth
        val result = StringBuilder(minOf(maxChars, 256))
        var limitReached = maxChars == 0
        val ignoredElementDepths = mutableListOf<Int>()

        fun appendBoundarySpace() {
            if (result.isNotEmpty() && !result.last().isWhitespace()) {
                if (result.length < maxChars) {
                    result.append(' ')
                } else {
                    limitReached = true
                }
            }
        }

        var event = parser.next()
        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.END_TAG && parser.depth == elementDepth) {
                return result.toString()
            }

            val blockTag = parser.name?.lowercase(Locale.ROOT)
            if (event == XmlPullParser.START_TAG && parser.depth > elementDepth &&
                blockTag != null && blockTag in ignoredSubtreeElements
            ) {
                ignoredElementDepths.add(parser.depth)
            }
            val insideIgnoredSubtree = ignoredElementDepths.isNotEmpty()

            if (!insideIgnoredSubtree && parser.depth > elementDepth &&
                (event == XmlPullParser.START_TAG || event == XmlPullParser.END_TAG) &&
                blockTag != null && blockTag in blockElements
            ) {
                appendBoundarySpace()
            }

            if (!insideIgnoredSubtree && !limitReached &&
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
                        Character.isSurrogate(current) -> 0
                        else -> 1
                    }
                    if (charCount == 0 || result.length + charCount > maxChars) {
                        limitReached = true
                        break
                    }
                    result.append(chunk, offset, offset + charCount)
                    offset += charCount
                }
                if (result.length >= maxChars) limitReached = true
            }

            if (event == XmlPullParser.END_TAG &&
                ignoredElementDepths.lastOrNull() == parser.depth
            ) {
                ignoredElementDepths.removeAt(ignoredElementDepths.lastIndex)
                if (ignoredElementDepths.isEmpty()) appendBoundarySpace()
            }

            event = parser.next()
        }

        return result.toString()
    }
}
