package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderShortPageStabilityGateTest {
    @Test
    fun requiresThreeIdenticalFullyVisibleMeasurements() {
        val gate = ReaderShortPageStabilityGate()

        assertFalse(gate.record(640, 900))
        assertFalse(gate.record(640, 900))
        assertTrue(gate.record(640, 900))
        assertTrue(gate.record(640, 900))
    }

    @Test
    fun aContentReflowRestartsTheStabilityWindow() {
        val gate = ReaderShortPageStabilityGate()

        assertFalse(gate.record(640, 900))
        assertFalse(gate.record(640, 900))
        assertFalse(gate.record(720, 900))
        assertFalse(gate.record(720, 900))
        assertTrue(gate.record(720, 900))
    }

    @Test
    fun viewportResizeRestartsTheStabilityWindow() {
        val gate = ReaderShortPageStabilityGate()

        assertFalse(gate.record(640, 900))
        assertFalse(gate.record(640, 900))
        assertFalse(gate.record(640, 880))
        assertFalse(gate.record(640, 880))
        assertTrue(gate.record(640, 880))
    }

    @Test
    fun tallOrInvalidGeometryResetsTheStabilityWindow() {
        val gate = ReaderShortPageStabilityGate()

        assertFalse(gate.record(640, 900))
        assertFalse(gate.record(640, 900))
        assertFalse(gate.record(950, 900))
        assertFalse(gate.record(640, 900))
        assertFalse(gate.record(640, 900))
        assertTrue(gate.record(640, 900))
        assertFalse(gate.record(0, 900))
        assertFalse(gate.record(640, 0))
    }

    @Test(expected = IllegalArgumentException::class)
    fun rejectsAnImpossibleStableSampleCount() {
        ReaderShortPageStabilityGate(requiredStableSamples = 1)
    }
}
