package com.mangoloads.currentlens

/** Keeps automatic word definition subordinate to native text-selection gestures. */
internal object DictionarySelectionPolicy {
    const val AUTO_DEFINE_DELAY_MS = 650L

    /**
     * Cancels automatic definition when the user moves beyond the platform touch slop.
     * Dragging to refine a native selection or beginning a scroll must win over auto-define.
     */
    fun shouldCancelAutoDefineForMovement(
        deltaX: Float,
        deltaY: Float,
        touchSlopPx: Float
    ): Boolean {
        if (!deltaX.isFinite() || !deltaY.isFinite() ||
            !touchSlopPx.isFinite() || touchSlopPx < 0f
        ) return false

        return deltaX * deltaX + deltaY * deltaY > touchSlopPx * touchSlopPx
    }

    /** A system-cancelled touch stream invalidates auto-definition even without movement. */
    fun shouldCancelAutoDefineForGesture(
        gestureCancelled: Boolean,
        deltaX: Float,
        deltaY: Float,
        touchSlopPx: Float
    ): Boolean = gestureCancelled ||
        shouldCancelAutoDefineForMovement(deltaX, deltaY, touchSlopPx)

    /** A new touch must not inherit a delayed definition from the previous gesture. */
    fun shouldCancelAutoDefineForNewTouch(
        hasPendingRunnable: Boolean,
        evaluationPending: Boolean
    ): Boolean = hasPendingRunnable || evaluationPending

    fun autoDefineWord(rawSelection: String, elapsedMillis: Long): String? {
        if (elapsedMillis < AUTO_DEFINE_DELAY_MS) return null
        return DictionaryWordPolicy.singleSelectedWord(rawSelection)
    }

    /** Rejects a delayed JavaScript selection result after its native ActionMode has ended. */
    fun manualDefineWord(
        rawSelection: String,
        requestGeneration: Int,
        currentGeneration: Int
    ): String? {
        if (requestGeneration != currentGeneration) return null
        return DictionaryWordPolicy.singleSelectedWord(rawSelection)
    }
}
