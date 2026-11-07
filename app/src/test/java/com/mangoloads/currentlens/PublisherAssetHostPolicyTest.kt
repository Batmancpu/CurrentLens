package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PublisherAssetHostPolicyTest {
    @Test
    fun allowsPublisherOwnedAndObservedImageCdnHosts() {
        assertTrue(PublisherAssetHostPolicy.allows(
            "The Hindu",
            "https://th-i.thgim.com/public/incoming/story/alternates/LANDSCAPE_1200/image.JPG"
        ))
        assertTrue(PublisherAssetHostPolicy.allows(
            "Indian Express",
            "https://images.indianexpress.com/2026/11/story.jpg?w=640"
        ))
        assertTrue(PublisherAssetHostPolicy.allows(
            "BBC",
            "https://ichef.bbci.co.uk/ace/standard/240/cpsprodpb/story.jpg"
        ))
        assertTrue(PublisherAssetHostPolicy.allows("DW", "https://static.dw.com/image/story_303.jpg"))
        assertTrue(PublisherAssetHostPolicy.allows("Al Jazeera", "https://www.aljazeera.com/images/story.jpg"))
    }

    @Test
    fun rejectsUntrustedLookalikeAndCrossPublisherHosts() {
        assertFalse(PublisherAssetHostPolicy.allows("The Hindu", "https://images.indianexpress.com/story.jpg"))
        assertFalse(PublisherAssetHostPolicy.allows("The Hindu", "https://thehindu.com.attacker.test/story.jpg"))
        assertFalse(PublisherAssetHostPolicy.allows("The Hindu", "https://evilthehindu.com/story.jpg"))
        assertFalse(PublisherAssetHostPolicy.allows("Unknown", "https://news.example.test/story.jpg"))
    }

    @Test
    fun rejectsCleartextCredentialsAndNonDefaultPorts() {
        assertFalse(PublisherAssetHostPolicy.allows("BBC", "http://ichef.bbci.co.uk/story.jpg"))
        assertFalse(PublisherAssetHostPolicy.allows("BBC", "https://user@ichef.bbci.co.uk/story.jpg"))
        assertFalse(PublisherAssetHostPolicy.allows("BBC", "https://ichef.bbci.co.uk:8443/story.jpg"))
    }

    @Test
    fun acceptsDeclaredMediaRssImageAndRejectsVideoEvenOnPublisherHost() {
        val image = FeedImageCandidate(
            url = "https://th-i.thgim.com/public/story/no-extension",
            element = FeedImageElement.CONTENT,
            type = "image/jpeg",
            medium = "image"
        )
        val video = FeedImageCandidate(
            url = "https://th-i.thgim.com/public/story/clip.mp4",
            element = FeedImageElement.CONTENT,
            type = "video/mp4",
            medium = "video"
        )

        assertEquals(image.url, FeedImagePolicy.select("The Hindu", listOf(video, image)))
    }

    @Test
    fun acceptsThumbnailAndImageEnclosureButRejectsUndeclaredNonImageEnclosure() {
        val thumbnail = FeedImageCandidate(
            url = "https://ichef.bbci.co.uk/ace/standard/240/story.jpg",
            element = FeedImageElement.THUMBNAIL
        )
        val imageEnclosure = FeedImageCandidate(
            url = "https://images.indianexpress.com/2026/11/story.jpg?w=640",
            element = FeedImageElement.ENCLOSURE
        )
        val unknownEnclosure = FeedImageCandidate(
            url = "https://images.indianexpress.com/2026/11/file",
            element = FeedImageElement.ENCLOSURE
        )

        assertEquals(thumbnail.url, FeedImagePolicy.select("BBC", listOf(thumbnail)))
        assertEquals(imageEnclosure.url, FeedImagePolicy.select("Indian Express", listOf(unknownEnclosure, imageEnclosure)))
        assertNull(FeedImagePolicy.select("Indian Express", listOf(unknownEnclosure)))
    }

    @Test
    fun rejectsUntrustedHostEvenWhenMediaTypeDeclaresImage() {
        val candidate = FeedImageCandidate(
            url = "https://tracker.example.test/pixel.jpg",
            element = FeedImageElement.CONTENT,
            type = "image/jpeg",
            medium = "image"
        )
        assertNull(FeedImagePolicy.select("The Hindu", listOf(candidate)))
    }

    @Test
    fun laterValidImageCandidateWinsWithoutAllowingLaterVideoToReplaceIt() {
        val image = FeedImageCandidate(
            url = "https://th-i.thgim.com/public/story/image.jpg",
            element = FeedImageElement.CONTENT,
            medium = "image"
        )
        val video = FeedImageCandidate(
            url = "https://th-i.thgim.com/public/story/video.mp4",
            element = FeedImageElement.CONTENT,
            medium = "video",
            type = "video/mp4"
        )
        assertEquals(image.url, FeedImagePolicy.select("The Hindu", listOf(image, video)))
    }
}
