package com.mangoloads.currentlens

/**
 * Keeps the selection-definition popup aligned with Android's system animation preference.
 * The caller supplies ValueAnimator.areAnimatorsEnabled() so this policy stays JVM-testable.
 */
object DictionaryMotionPolicy {
    fun shouldUseAnimatedTransitions(systemAnimationsEnabled: Boolean): Boolean =
        systemAnimationsEnabled
}
