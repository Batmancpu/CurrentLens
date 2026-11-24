package com.mangoloads.currentlens

import org.junit.Assert.*
import org.junit.Test

/** Targeted public publisher-page examples, not a random or representative evaluation corpus.
 * Title-only metadata deliberately omits unverified RSS timestamps. */
class ExpandedPublisherEditorialSampleTest {
    private fun article(title: String, url: String, source: String, section: String) =
        Article(url, title, "", url, source, section, "")

    @Test fun narrowSyllabusSignalsOnPublicHeadlines() {
        val examples = listOf(
            Triple(article("NavIC system to be operational again after NVS-03's successful launch",
                "https://indianexpress.com/article/india/isro-gslv-f18-nvs-03-launch-navic-satellite-constellation-10945164/",
                "Indian Express", "India"), "GS III", "Science & Technology"),
            Triple(article("Strongest El Niño in decades possible by year-end: What it means for India",
                "https://indianexpress.com/article/india/el-nino-2026-noaa-forecast-india-impact-10832538/",
                "Indian Express", "India"), "GS I", "Geography"),
            Triple(article("The challenges of getting rid of antibiotic resistance",
                "https://amp.dw.com/en/getting-rid-of-antibiotic-resistance-challenges-and-possible-solutions/a-80010693",
                "DW", "World"), "GS II", "Social Sector & Public Health"),
            Triple(article("After COP31: A reset for climate diplomacy?",
                "https://amp.dw.com/en/cop-deadlock-prompts-calls-for-a-reset-of-climate-diplomacy/a-80020494",
                "DW", "World"), "GS III", "Environment"),
            Triple(article("Fossil fuel talks deadlock pushes COP31 climate summit into overtime",
                "https://www.aljazeera.com/amp/news/2026/11/20/fossil-fuel-talks-deadlock-pushes-cop31-climate-summit-into-overtime",
                "Al Jazeera", "World"), "GS III", "Environment"),
            Triple(article("Money Bill passed to increase judges’ number: Why this route faces legal challenge",
                "https://indianexpress.com/article/explained/explained-law/money-bill-route-pending-before-supreme-court-government-chooses-it-to-raise-strength-of-judges-10819849/lite/",
                "Indian Express", "Explained"), "GS II", "Constitution"),
            Triple(article("Is inflation becoming more widespread? RBI’s rate-setters are divided, and that’s a worry",
                "https://indianexpress.com/article/explained/explained-economics/rbi-mpc-inflation-divide-rate-hikes-rupee-concerns-explained-10932047/",
                "Indian Express", "Explained"), "GS III", "Indian Economy"),
            Triple(article("Monsoon ends with 13% rain deficit, driest year in a decade",
                "https://indianexpress.com/article/india/india-driest-monsoon-decade-el-nino-rainfall-deficit-10900646/",
                "Indian Express", "India"), "GS I", "Geography")
        )
        for ((a, paper, subject) in examples) {
            val result = UpscFeedPolicy.eligibleRelevance(a)
            assertNotNull(a.title, result)
            assertEquals(a.title, paper, result?.gsPaper)
            assertEquals(a.title, subject, result?.subject)
        }
    }

    @Test fun routineEntertainmentAndSportsRemainExcluded() {
        val examples = listOf(
            article("Ramayana is the fastest Indian film to cross Rs 1,500 cr: All box office records it has broken",
                "https://indianexpress.com/article/entertainment/bollywood/all-box-office-records-ranbir-kapoor-yash-ramayana-part-1-has-broken-10969618/",
                "Indian Express", "Entertainment"),
            article("Hrithik Roshan pens heartfelt note for Saba Azad on her birthday: 'A dance of adventures'",
                "https://indianexpress.com/article/entertainment/bollywood/hrithik-roshan-pens-heartfelt-note-for-saba-azad-on-her-birthday-a-dance-of-adventures-10945994/",
                "Indian Express", "Entertainment"),
            article("Tilak's 100 in vain as Pakistan A beat India A in Rising Stars Asia Cup",
                "https://indianexpress.com/article/sports/cricket/tilak-varma-100-pakistan-a-defeat-india-a-rising-stars-asia-cup-10974124/",
                "Indian Express", "Cricket"),
            article("Arsenal held to 2-2 draw by Newcastle United – updates",
                "https://www.aljazeera.com/sports/liveblog/2026/11/21/newcastle-united-vs-arsenal-live-premier-league",
                "Al Jazeera", "Sports")
        )
        for (a in examples) assertNull(a.title, UpscFeedPolicy.eligibleRelevance(a))
    }
}
