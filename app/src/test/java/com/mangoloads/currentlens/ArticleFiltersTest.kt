package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class ArticleFiltersTest {
    private fun article(id: String, published: String) = Article(
        id = id,
        title = id,
        description = "",
        url = "https://example.test/$id",
        source = "Test",
        section = "Test",
        published = published
    )

    @Test
    fun todayUsesAsiaKolkataCalendarDate() {
        val afterMidnightIst = article("today", "2026-11-02T19:00:00Z")
        val beforeMidnightIst = article("yesterday", "2026-11-02T18:29:59Z")

        assertEquals(
            listOf(afterMidnightIst),
            ArticleFilters.today(
                listOf(beforeMidnightIst, afterMidnightIst),
                LocalDate.of(2026, 11, 3)
            )
        )
    }

    @Test
    fun exploreIncludesOlderCachedStoriesAndSortsNewestFirst() {
        val newest = article("newest", "2026-11-03T10:00:00Z")
        val older = article("older", "2026-10-01T10:00:00Z")
        val undated = article("undated", "")

        assertEquals(
            listOf(newest, older, undated),
            ArticleFilters.explore(listOf(older, undated, newest))
        )
    }

}
