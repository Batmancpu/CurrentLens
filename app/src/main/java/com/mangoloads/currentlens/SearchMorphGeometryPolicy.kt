package com.mangoloads.currentlens

/** Pure geometry and visual handoff policy for the Search-tab glass morph. */
internal object SearchMorphGeometryPolicy {
    fun interpolate(start: Float, end: Float, progress: Float): Float {
        val t = progress.coerceIn(0f, 1f)
        return start + (end - start) * t
    }

    fun collapsedRefractionFactor(progress: Float): Float =
        1f - progress.coerceIn(0f, 1f)

    /** The tab surface fades out while the search capsule grows over its anchor. */
    fun tabBarAlpha(progress: Float): Float =
        1f - (progress.coerceIn(0f, 1f) / 0.18f).coerceIn(0f, 1f)

    /** The capsule becomes visible from the same anchor as the tab droplet. */
    fun fieldAlpha(progress: Float): Float =
        (progress.coerceIn(0f, 1f) / 0.12f).coerceIn(0f, 1f)
}
