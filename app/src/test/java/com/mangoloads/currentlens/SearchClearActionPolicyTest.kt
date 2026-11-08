package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchClearActionPolicyTest {
    @Test
    fun showsClearOnlyForNonBlankQueryWhileFieldIsActive() {
        assertTrue(SearchClearActionPolicy.shouldShow("RBI", fieldActive = true))
        assertFalse(SearchClearActionPolicy.shouldShow("", fieldActive = true))
        assertFalse(SearchClearActionPolicy.shouldShow("   ", fieldActive = true))
        assertFalse(SearchClearActionPolicy.shouldShow("RBI", fieldActive = false))
    }
}
