package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchNavigationPolicyTest {
    @Test
    fun enteringSearchFromAnotherTabExpandsOnTheFirstTap() {
        val state = SearchNavigationPolicy.selectTab(targetTab = 2)

        assertEquals(2, state.selectedTab)
        assertTrue(state.searchExpanded)
    }

    @Test
    fun selectingSearchAgainReopensTheFieldAfterSubmission() {
        val state = SearchNavigationPolicy.selectTab(targetTab = SearchNavigationPolicy.SEARCH_TAB)

        assertTrue(state.searchExpanded)
    }

    @Test
    fun selectingAnotherDestinationCollapsesSearchAndKeepsFourTabIndex() {
        val state = SearchNavigationPolicy.selectTab(targetTab = 3)

        assertEquals(3, state.selectedTab)
        assertFalse(state.searchExpanded)
    }
}
