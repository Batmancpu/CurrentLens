package com.mangoloads.currentlens

import java.io.StringReader
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.kxml2.io.KXmlParser
import org.xmlpull.v1.XmlPullParser

class FeedXmlTextReaderTest {
    private fun parser(xml: String): KXmlParser = KXmlParser().apply {
        setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true)
        setInput(StringReader(xml))
    }

    private fun moveToStartTag(parser: KXmlParser, name: String) {
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == name) return
        }
        throw AssertionError("Missing <$name> element")
    }

    @Test
    fun readsAtomXhtmlSummaryAndLeavesParserAtOuterEndTag() {
        val parser = parser(
            """
            <feed xmlns="http://www.w3.org/2005/Atom">
              <entry>
                <summary type="xhtml">
                  <div xmlns="http://www.w3.org/1999/xhtml">
                    RBI <b>retains</b> the repo rate.
                  </div>
                </summary>
                <updated>2026-11-08T10:00:00Z</updated>
              </entry>
            </feed>
            """.trimIndent()
        )
        moveToStartTag(parser, "summary")

        val text = FeedXmlTextReader.readElementText(
            parser,
            FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS
        )

        assertEquals("RBI retains the repo rate.", text.replace(Regex("\\s+"), " ").trim())
        assertEquals(XmlPullParser.END_TAG, parser.eventType)
        assertEquals("summary", parser.name)
        assertEquals(XmlPullParser.START_TAG, parser.nextTag())
        assertEquals("updated", parser.name)
    }

    @Test
    fun insertsWordBoundariesBetweenAdjacentXhtmlBlockElements() {
        val parser = parser(
            """
            <feed xmlns="http://www.w3.org/2005/Atom">
              <entry>
                <summary type="xhtml">
                  <div xmlns="http://www.w3.org/1999/xhtml"><p>GST</p><p>Council<br/>meeting</p></div>
                </summary>
              </entry>
            </feed>
            """.trimIndent()
        )
        moveToStartTag(parser, "summary")

        val text = FeedXmlTextReader.readElementText(
            parser,
            FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS
        )

        assertEquals("GST Council meeting", text.replace(Regex("\\s+"), " ").trim())
    }

    @Test
    fun readsPlainTextAndExpandedEntities() {
        val parser = parser("<feed><entry><summary>RBI &amp; monetary policy</summary></entry></feed>")
        moveToStartTag(parser, "summary")

        assertEquals(
            "RBI & monetary policy",
            FeedXmlTextReader.readElementText(parser, FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS)
        )
    }

    @Test
    fun readsCdataWithoutInterpretingEmbeddedMarkupAsXml() {
        val parser = parser(
            "<feed><entry><summary><![CDATA[<p>RBI reviews monetary policy</p>]]></summary></entry></feed>"
        )
        moveToStartTag(parser, "summary")

        assertEquals(
            "<p>RBI reviews monetary policy</p>",
            FeedXmlTextReader.readElementText(parser, FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS)
        )
    }

    @Test
    fun enforcesLimitWithoutSplittingSupplementaryCharacterAndConsumesElement() {
        val limit = FeedFieldPolicy.MAX_TITLE_SOURCE_CHARS
        val parser = parser(
            "<root><title>" + "a".repeat(limit - 1) + "😀tail</title><next>safe</next></root>"
        )
        moveToStartTag(parser, "title")

        val text = FeedXmlTextReader.readElementText(parser, limit)

        assertEquals(limit - 1, text.length)
        assertTrue(text.none { Character.isSurrogate(it) })
        assertEquals(XmlPullParser.END_TAG, parser.eventType)
        assertEquals("title", parser.name)
        assertEquals(XmlPullParser.START_TAG, parser.nextTag())
        assertEquals("next", parser.name)
    }

    @Test
    fun readsAtomXhtmlContentAndLeavesParserAtOuterEndTag() {
        val parser = parser(
            """
            <feed xmlns="http://www.w3.org/2005/Atom">
              <entry>
                <content type="xhtml">
                  <div xmlns="http://www.w3.org/1999/xhtml">
                    RBI <b>retains</b> the repo rate.
                  </div>
                </content>
                <updated>2026-11-08T10:00:00Z</updated>
              </entry>
            </feed>
            """.trimIndent()
        )
        moveToStartTag(parser, "content")

        val text = FeedXmlTextReader.readElementText(
            parser,
            FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS
        )

        assertEquals("RBI retains the repo rate.", text.replace(Regex("\\s+"), " ").trim())
        assertEquals(XmlPullParser.END_TAG, parser.eventType)
        assertEquals("content", parser.name)
        assertEquals(XmlPullParser.START_TAG, parser.nextTag())
        assertEquals("updated", parser.name)
    }

    @Test
    fun ignoresScriptAndStyleSubtreesWhenExtractingEditorialText() {
        val parser = parser(
            """
            <feed xmlns="http://www.w3.org/2005/Atom">
              <entry>
                <summary type="xhtml">
                  <div xmlns="http://www.w3.org/1999/xhtml">
                    RBI
                    <script>climate change biodiversity policy</script>
                    retains the repo rate
                    <style>.policy { color: red; }</style>
                    after reviewing monetary conditions.
                  </div>
                </summary>
              </entry>
            </feed>
            """.trimIndent()
        )
        moveToStartTag(parser, "summary")

        val text = FeedXmlTextReader.readElementText(
            parser,
            FeedFieldPolicy.MAX_DESCRIPTION_SOURCE_CHARS
        ).replace(Regex("\\s+"), " ").trim()

        assertEquals("RBI retains the repo rate after reviewing monetary conditions.", text)
        assertEquals(XmlPullParser.END_TAG, parser.eventType)
        assertEquals("summary", parser.name)
    }
}
