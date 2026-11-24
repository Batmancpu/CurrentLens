package com.mangoloads.currentlens

import org.junit.Assert.*
import org.junit.Test

class PrimaryEvidencePolicyTest {
    private val article = Article("a", "Fertilizer subsidy", "",
        "https://indianexpress.com/article/business/fertiliser-subsidy-rabi-season-2026-27-cabinet-approval-10959144/",
        "Indian Express", "Economy", "2026-11-11T18:18:00+05:30")

    @Test fun exactArticleOnlyWithSafeTrackingVariant() {
        assertNotNull(PrimaryEvidencePolicy.forArticle(article))
        assertNotNull(PrimaryEvidencePolicy.forArticle(article.copy(url = article.url + "?utm_source=test")))
        assertNull(PrimaryEvidencePolicy.forArticle(article.copy(url = article.url + "other")))
        assertNull(PrimaryEvidencePolicy.forArticle(article.copy(source = "BBC")))
        assertNull(PrimaryEvidencePolicy.forArticle(article.copy(url = article.url + "?edition=other")))
    }
}
