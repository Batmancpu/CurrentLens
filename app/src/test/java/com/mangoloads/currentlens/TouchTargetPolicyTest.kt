package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class TouchTargetPolicyTest {
    @Test
    fun undersizedIconActionsAreRaisedToFortyEightDp() {
        assertEquals(48, TouchTargetPolicy.iconTargetDp(40))
        assertEquals(48, TouchTargetPolicy.iconTargetDp(44))
        assertEquals(48, TouchTargetPolicy.iconTargetDp(46))
    }

    @Test
    fun compliantOrLargerIconTargetsArePreserved() {
        assertEquals(48, TouchTargetPolicy.iconTargetDp(48))
        assertEquals(56, TouchTargetPolicy.iconTargetDp(56))
    }
}
