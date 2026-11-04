package com.mangoloads.currentlens

/** Navigation state for the Search tab's bottom-bar-to-keyboard morph. */
internal data class SearchDestinationState(
    val selectedTab: Int,
    val searchExpanded: Boolean
)

internal object SearchNavigationPolicy {
    const val SEARCH_TAB = 2

    /**
     * Selecting Search from another destination must open the field on the first tap.
     * Selecting Search again after submit/back reopens it; other destinations collapse it.
     */
    fun selectTab(targetTab: Int): SearchDestinationState = SearchDestinationState(
        selectedTab = targetTab,
        searchExpanded = targetTab == SEARCH_TAB
    )
}
