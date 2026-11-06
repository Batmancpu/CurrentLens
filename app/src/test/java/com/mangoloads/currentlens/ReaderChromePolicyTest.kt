package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderChromePolicyTest {
    @Test
    fun fontSizeChangesInSingleStepsWithinAccessibleBounds() {
        assertEquals(21, ReaderChromePolicy.adjustFontSize(20, 1))
        assertEquals(19, ReaderChromePolicy.adjustFontSize(20, -1))
        assertEquals(16, ReaderChromePolicy.adjustFontSize(16, -1))
        assertEquals(28, ReaderChromePolicy.adjustFontSize(28, 1))
    }

    @Test
    fun invalidStoredFontSizeIsClampedBeforeApplyingChange() {
        assertEquals(28, ReaderChromePolicy.adjustFontSize(40, 1))
        assertEquals(16, ReaderChromePolicy.adjustFontSize(10, -1))
    }

    @Test
    fun completionAppearsOnlyAtEndWithoutOpenDefinitionOrDismissal() {
        assertTrue(ReaderChromePolicy.shouldShowCompletion(true, false, false))
        assertFalse(ReaderChromePolicy.shouldShowCompletion(false, false, false))
        assertFalse(ReaderChromePolicy.shouldShowCompletion(true, true, false))
        assertFalse(ReaderChromePolicy.shouldShowCompletion(true, false, true))
    }
}
