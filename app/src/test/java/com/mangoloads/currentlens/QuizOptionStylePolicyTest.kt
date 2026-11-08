package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizOptionStylePolicyTest {
    @Test
    fun resolvesSelectionAndSubmittedFeedbackWithoutChangingAnswerData() {
        assertEquals(
            QuizOptionVisualState.IDLE,
            QuizOptionStylePolicy.resolve(selected = false, submitted = false, correct = true)
        )
        assertEquals(
            QuizOptionVisualState.SELECTED,
            QuizOptionStylePolicy.resolve(selected = true, submitted = false, correct = false)
        )
        assertEquals(
            QuizOptionVisualState.CORRECT,
            QuizOptionStylePolicy.resolve(selected = false, submitted = true, correct = true)
        )
        assertEquals(
            QuizOptionVisualState.CORRECT,
            QuizOptionStylePolicy.resolve(selected = true, submitted = true, correct = true)
        )
        assertEquals(
            QuizOptionVisualState.INCORRECT,
            QuizOptionStylePolicy.resolve(selected = true, submitted = true, correct = false)
        )
        assertEquals(
            QuizOptionVisualState.DISABLED,
            QuizOptionStylePolicy.resolve(selected = false, submitted = true, correct = false)
        )
    }

    @Test
    fun selectedAndFeedbackStatesHaveStrongerEdgesThanIdle() {
        val idle = QuizOptionStylePolicy.borderAlpha(QuizOptionVisualState.IDLE, false)
        listOf(
            QuizOptionVisualState.SELECTED,
            QuizOptionVisualState.CORRECT,
            QuizOptionVisualState.INCORRECT
        ).forEach { state ->
            assertTrue(QuizOptionStylePolicy.borderAlpha(state, false) > idle)
            assertTrue(QuizOptionStylePolicy.fillAlpha(state, false) > 0f)
        }
    }

    @Test
    fun reducedTransparencyRaisesStaticFillAndRimForEveryState() {
        QuizOptionVisualState.values().forEach { state ->
            assertTrue(
                QuizOptionStylePolicy.fillAlpha(state, true) >=
                    QuizOptionStylePolicy.fillAlpha(state, false)
            )
            assertTrue(
                QuizOptionStylePolicy.borderAlpha(state, true) >
                    QuizOptionStylePolicy.borderAlpha(state, false)
            )
        }
    }

    @Test
    fun allAlphaValuesStayWithinUnitInterval() {
        QuizOptionVisualState.values().forEach { state ->
            listOf(false, true).forEach { reduced ->
                assertTrue(QuizOptionStylePolicy.fillAlpha(state, reduced) in 0f..1f)
                assertTrue(QuizOptionStylePolicy.borderAlpha(state, reduced) in 0f..1f)
            }
        }
    }
}
