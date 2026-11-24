package com.mangoloads.currentlens

internal enum class FeedHealthStatus(val label: String, val isFailure: Boolean) {
    OK("Available", false),
    EMPTY("No usable article entries", false),
    NO_DATED_STORIES("No plausible publication dates", false),
    HTTP_ERROR("Publisher HTTP error", true),
    BLOCKED_REDIRECT("Unsafe redirect blocked", true),
    TOO_LARGE("Response too large", true),
    INVALID_FEED("Invalid RSS / Atom response", true),
    TIMEOUT("Request timed out", true),
    TLS_FAILURE("Secure connection failed", true),
    NETWORK_ERROR("Network error", true)
}
internal class FeedRequestFailure(val status: FeedHealthStatus) : java.io.IOException(status.label)
internal data class FeedHealthReport(
    val publisher: String, val section: String, val status: FeedHealthStatus,
    val parsedCount: Int, val datedCount: Int
)

internal object FeedHealthPolicy {
    fun report(source: FeedSource, outcome: FeedHealthStatus, articles: List<Article>, now: Long): FeedHealthReport {
        val dated = articles.count { ArticleCachePolicy.hasPlausibleFeedDate(it.publishedAtMillis, now) }
        val status = when {
            outcome.isFailure -> outcome
            articles.isEmpty() -> FeedHealthStatus.EMPTY
            dated == 0 -> FeedHealthStatus.NO_DATED_STORIES
            else -> FeedHealthStatus.OK
        }
        return FeedHealthReport(source.name, source.section, status, articles.size, dated)
    }

    /** Only configured publisher/section labels, counts and coarse outcomes, never raw URLs or logs. */
    fun diagnosticText(reports: List<FeedHealthReport>): String = reports.joinToString("\n") {
        "${it.publisher} / ${it.section}: ${it.status.label} (${it.datedCount}/${it.parsedCount} dated)"
    }
}
