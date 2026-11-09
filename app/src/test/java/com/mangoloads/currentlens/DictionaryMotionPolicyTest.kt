package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DictionaryMotionPolicyTest {
    @Test
    fun keepsSpringTransitionsWhenSystemAnimationsAreEnabled() {
        assertTrue(DictionaryMotionPolicy.shouldUseAnimatedTransitions(true))
    }

    @Test
    fun disablesSpringTransitionsWhenSystemAnimationsAreDisabled() {
        assertFalse(DictionaryMotionPolicy.shouldUseAnimatedTransitions(false))
    }
}
