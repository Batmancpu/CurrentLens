package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PublisherHostPolicyTest {
    @Test
    fun allowsHttpsPublisherHostsAndTheirSubdomains() {
        assertTrue(PublisherHostPolicy.allows("The Hindu", "https://www.thehindu.com/news/national/story"))
        assertTrue(PublisherHostPolicy.allows("Indian Express", "https://indianexpress.com/article/india/story"))
        assertTrue(PublisherHostPolicy.allows("BBC", "https://www.bbc.co.uk/news/world"))
        assertTrue(PublisherHostPolicy.allows("DW", "https://www.dw.com/en/story"))
        assertTrue(PublisherHostPolicy.allows("Al Jazeera", "https://www.aljazeera.com/news/story"))
    }

    @Test
    fun rejectsLookalikeHostsWrongPublishersAndUnsafeSchemes() {
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://thehindu.com.attacker.test/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://evilthehindu.com/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://www.bbc.com/news/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "http://www.thehindu.com/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "file:///data/user/0/app/story.html"))
        assertFalse(PublisherHostPolicy.allows("Unknown", "https://news.example.test/story"))
        assertFalse(PublisherHostPolicy.allows("The Hindu", "https://user@www.thehindu.com/story"))
    }
}
