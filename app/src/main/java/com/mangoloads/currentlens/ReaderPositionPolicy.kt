package com.mangoloads.currentlens

/** A bounded, local resume point for a single article; separate from timed reading sessions. */
internal data class ReaderPosition(
    val articleUrl: String,
    val progress: Float,
    val updatedAtMillis: Long
)

internal object ReaderPositionPolicy {
    const val MAX_URL_LENGTH = 2_048
    const val MAX_ENTRIES = 100
    const val MAX_AGE_MILLIS = 30L * 24L * 60L * 60L * 1_000L
    const val MIN_SAVED_PROGRESS = 0.025f
    const val COMPLETED_PROGRESS = 0.97f

    fun shouldPersist(progress: Float): Boolean =
        progress.isFinite() &&
            progress >= MIN_SAVED_PROGRESS &&
            progress < COMPLETED_PROGRESS

    fun progressFromScroll(scrollY: Int, contentHeightPx: Int, viewportHeightPx: Int): Float? {
        if (scrollY < 0 || contentHeightPx <= 0 || viewportHeightPx <= 0) return null
        val range = contentHeightPx - viewportHeightPx
        if (range <= 0) return null
        return (scrollY.toFloat() / range.toFloat()).coerceIn(0f, 1f)
    }

    fun scrollYForProgress(progress: Float, contentHeightPx: Int, viewportHeightPx: Int): Int? {
        if (!shouldPersist(progress) || contentHeightPx <= viewportHeightPx || viewportHeightPx <= 0) {
            return null
        }
        val maxScroll = contentHeightPx - viewportHeightPx
        return (maxScroll.toFloat() * progress).toInt().coerceIn(0, maxScroll)
    }

    fun retain(
        positions: List<ReaderPosition>,
        nowMillis: Long,
        maxEntries: Int = MAX_ENTRIES,
        maxAgeMillis: Long = MAX_AGE_MILLIS
    ): List<ReaderPosition> {
        if (nowMillis <= 0L || maxEntries <= 0 || maxAgeMillis <= 0L) return emptyList()
        val oldestAllowed = (nowMillis - maxAgeMillis).coerceAtLeast(0L)
        return positions
            .filter {
                it.articleUrl.isNotBlank() &&
                    it.articleUrl.length <= MAX_URL_LENGTH &&
                    it.updatedAtMillis in oldestAllowed..nowMillis &&
                    shouldPersist(it.progress)
            }
            .groupBy { it.articleUrl }
            .values
            .mapNotNull { entries -> entries.maxByOrNull { it.updatedAtMillis } }
            .sortedByDescending { it.updatedAtMillis }
            .take(maxEntries)
    }
}
