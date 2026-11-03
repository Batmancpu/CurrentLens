package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DictionarySelectionPolicyTest {
    @Test
    fun doesNotAutoDefineBeforeSelectionGracePeriodEnds() {
        assertNull(
            DictionarySelectionPolicy.autoDefineWord(
                "Commonwealth",
                DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS - 1
            )
        )
    }

    @Test
    fun autoDefinesOneStableWordAfterGracePeriod() {
        assertEquals(
            "commonwealth",
            DictionarySelectionPolicy.autoDefineWord(
                "Commonwealth",
                DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS
            )
        )
    }

    @Test
    fun leavesExtendedMultiWordSelectionToNativeActions() {
        assertNull(
            DictionarySelectionPolicy.autoDefineWord(
                "rule of law",
                DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS + 250
            )
        )
    }

    @Test
    fun ignoresEmptyOrPunctuationOnlySelection() {
        assertNull(
            DictionarySelectionPolicy.autoDefineWord(
                " — ",
                DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS + 250
            )
        )
    }
}
