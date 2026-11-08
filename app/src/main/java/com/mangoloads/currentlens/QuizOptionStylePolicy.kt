package com.mangoloads.currentlens

/** Visual state for answer controls; question and explanation surfaces remain content-first. */
internal enum class QuizOptionVisualState {
    IDLE,
    SELECTED,
    CORRECT,
    INCORRECT,
    DISABLED
}

/**
 * Static tint/rim values for the existing answer buttons. This adds no live blur,
 * backdrop sampling, shader or per-option graphics layer.
 */
internal object QuizOptionStylePolicy {
    fun resolve(
        selected: Boolean,
        submitted: Boolean,
        correct: Boolean
    ): QuizOptionVisualState = when {
        submitted && correct -> QuizOptionVisualState.CORRECT
        submitted && selected -> QuizOptionVisualState.INCORRECT
        submitted -> QuizOptionVisualState.DISABLED
        selected -> QuizOptionVisualState.SELECTED
        else -> QuizOptionVisualState.IDLE
    }

    fun fillAlpha(state: QuizOptionVisualState, reduceTransparency: Boolean): Float {
        val normal = when (state) {
            QuizOptionVisualState.IDLE -> 0f
            QuizOptionVisualState.SELECTED -> 0.15f
            QuizOptionVisualState.CORRECT -> 0.16f
            QuizOptionVisualState.INCORRECT -> 0.14f
            QuizOptionVisualState.DISABLED -> 0.02f
        }
        return if (reduceTransparency) {
            when (state) {
                QuizOptionVisualState.IDLE -> 0.06f
                QuizOptionVisualState.SELECTED -> 0.23f
                QuizOptionVisualState.CORRECT -> 0.24f
                QuizOptionVisualState.INCORRECT -> 0.22f
                QuizOptionVisualState.DISABLED -> 0.08f
            }
        } else normal
    }

    fun borderAlpha(state: QuizOptionVisualState, reduceTransparency: Boolean): Float {
        val normal = when (state) {
            QuizOptionVisualState.IDLE -> 0.30f
            QuizOptionVisualState.SELECTED -> 0.66f
            QuizOptionVisualState.CORRECT -> 0.72f
            QuizOptionVisualState.INCORRECT -> 0.70f
            QuizOptionVisualState.DISABLED -> 0.24f
        }
        return if (reduceTransparency) {
            when (state) {
                QuizOptionVisualState.IDLE -> 0.42f
                QuizOptionVisualState.SELECTED -> 0.78f
                QuizOptionVisualState.CORRECT -> 0.82f
                QuizOptionVisualState.INCORRECT -> 0.80f
                QuizOptionVisualState.DISABLED -> 0.36f
            }
        } else normal
    }
}
