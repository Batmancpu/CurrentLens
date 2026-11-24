package com.mangoloads.currentlens

import org.junit.Assert.*
import org.junit.Test

/** Small human-labelled publisher-page fixtures, not a representative RSS corpus. */
class PublisherEditorialSampleTest {
    private fun article(title: String, url: String, date: String) =
        Article(url, title, "", url, "Indian Express", "Explained", date)

    @Test fun publisherHeadlinesMapToSpecificSyllabusThemes() {
        val fixtures = listOf(
            Triple(article("Cabinet approves fertiliser subsidy of Rs 62,370 crore for rabi season",
                "https://indianexpress.com/article/business/fertiliser-subsidy-rabi-season-2026-27-cabinet-approval-10959144/",
                "2026-11-11T18:18:00+05:30"), "GS III", "Agriculture"),
            Triple(article("Barrier-less system to reduce toll lanes in new highways",
                "https://indianexpress.com/article/business/barrier-less-tolling-system-new-highways-10943979/",
                "2026-10-30"), "GS III", "Infrastructure"),
            Triple(article("COP31: What’s at stake at UN climate summit in Antalya",
                "https://indianexpress.com/article/explained/explained-climate/cop31-antalya-turkey-un-summit-india-agenda-2026-expert-explains-10953189/",
                "2026-11-10"), "GS III", "Environment")
        )
        for ((a, paper, subject) in fixtures) {
            val relevance = UpscFeedPolicy.eligibleRelevance(a)
            assertNotNull(a.title, relevance)
            assertEquals(paper, relevance?.gsPaper)
            assertEquals(subject, relevance?.subject)
        }
    }
}
