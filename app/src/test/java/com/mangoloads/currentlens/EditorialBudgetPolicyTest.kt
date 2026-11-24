package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class EditorialBudgetPolicyTest {
    private fun article(id: String, title: String = id) = Article(
        id, title, "", "https://example.test/$id", "Test", "India", "2026-11-24T08:00:00Z"
    )

    @Test fun relevantStoriesSurviveNoisyShortlist() {
        val noise = List(300) { article("noise-$it", "Movie trailer released") }
        val relevant = article("policy", "RBI revises repo rate")
        val actual = EditorialBudgetPolicy.prioritize(noise + relevant, 250)
        assertEquals(250, actual.size)
        assertEquals("policy", actual.first().id)
        assertEquals("noise-0", actual[1].id)
    }

    @Test fun noExtraClassificationWithoutBudgetPressure() {
        val input = listOf(article("a"), article("b"))
        assertEquals(input, EditorialBudgetPolicy.prioritize(input, 2) { error("unexpected classification") })
        assertEquals(emptyList<Article>(), EditorialBudgetPolicy.prioritize(input, 0))
    }

    @Test fun stableOrderingAndFallback() {
        val input = (1..6).map { article(it.toString()) }
        assertEquals(listOf("2", "4", "1"), EditorialBudgetPolicy.prioritize(input, 3) {
            it.id == "2" || it.id == "4"
        }.map { it.id })
    }
}
