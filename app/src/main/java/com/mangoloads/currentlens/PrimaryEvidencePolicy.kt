package com.mangoloads.currentlens

internal data class PrimaryEvidence(
    val authority: String, val documentDate: String, val checkedOn: String,
    val url: String, val supportedClaim: String
)

/** Editorially checked, exact publisher-article links. Never infer evidence from keywords. */
internal object PrimaryEvidencePolicy {
    private val records = mapOf(
        "https://indianexpress.com/article/business/fertiliser-subsidy-rabi-season-2026-27-cabinet-approval-10959144" to PrimaryEvidence(
            "Union Cabinet / PIB", "11 November 2026", "24 November 2026",
            "https://www.pib.gov.in/PressReleseDetailm.aspx?PRID=2334972&lang=1&reg=1",
            "The Cabinet approved Rabi 2026–27 P&K fertilizer subsidy rates; the tentative budget was about Rs 62,370 crore."
        ),
        "https://indianexpress.com/article/business/barrier-less-tolling-system-new-highways-10943979" to PrimaryEvidence(
            "Ministry of Road Transport & Highways / PIB", "6 November 2026", "24 November 2026",
            "https://www.pib.gov.in/PressReleasePage.aspx?PRID=2333654&lang=1&reg=3",
            "The later official release confirms standardized barrier-free toll infrastructure and rationalized toll-lane counts for national highway projects."
        )
    )

    fun forArticle(article: Article): PrimaryEvidence? {
        if (!article.source.equals("Indian Express", ignoreCase = true)) return null
        return records[ArticleUrlIdentityPolicy.key(article.url).trimEnd('/')]
    }
}
