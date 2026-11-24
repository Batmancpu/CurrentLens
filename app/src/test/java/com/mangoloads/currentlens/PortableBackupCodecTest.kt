package com.mangoloads.currentlens

import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test

class PortableBackupCodecTest {
    private val link = "https://www.thehindu.com/news/national/example/article12345678.ece"
    private val noteKey = "a".repeat(64)
    private fun backup() = PortableBackup(
        bookmarks = listOf(link), savedArticles = listOf(
            JSONObject().put("url", link).put("title", "Example")
                .put("description", "Excerpt").put("source", "The Hindu")
                .put("section", "India").put("published", "2026-11-24")
        ), legacyRead = listOf(link), recentRead = listOf(link), sessions = emptyList(),
        attempts = emptyList(), positions = emptyList(), notes = mapOf(noteKey to "Private note")
    )
    @Test fun savedMetadataAndNotesRoundTrip() {
        val decoded = PortableBackupCodec.decode(PortableBackupCodec.encode(backup()))
        assertEquals(listOf(link), decoded.bookmarks)
        assertEquals(link, decoded.savedArticles.single().getString("id"))
        assertEquals("Private note", decoded.notes[noteKey])
        assertEquals(listOf(link), decoded.recentRead)
    }
    @Test fun rejectsForeignPublisherAndWrongVersion() {
        val foreign = backup().savedArticles.single().put("source", "BBC")
        assertTrue(runCatching {
            PortableBackupCodec.decode(PortableBackupCodec.encode(backup().copy(savedArticles = listOf(foreign))))
        }.isFailure)
        val future = JSONObject(PortableBackupCodec.encode(backup())).put("version", 99)
        assertTrue(runCatching { PortableBackupCodec.decode(future.toString()) }.isFailure)
    }
    @Test fun supportsLegacySummaryOnlyQuiz() {
        val legacy = JSONObject().put("articleUrl", link).put("articleTitle", "Example")
            .put("subject", "Economy").put("gsPaper", "GS-III")
            .put("totalQuestions", 3).put("answeredCount", 3).put("correctCount", 2)
            .put("submittedAtMillis", 1000L)
        val decoded = PortableBackupCodec.decode(PortableBackupCodec.encode(backup().copy(attempts = listOf(legacy))))
        assertEquals(0, decoded.attempts.single().getJSONArray("answers").length())
        assertEquals(2, decoded.attempts.single().getInt("correctCount"))
    }
}
