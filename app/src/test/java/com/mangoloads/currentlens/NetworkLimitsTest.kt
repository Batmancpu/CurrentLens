package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.IOException

class NetworkLimitsTest {
    @Test
    fun readsUtf8ResponseWithinLimit() {
        val result = NetworkLimits.readUtf8Limited(ByteArrayInputStream("CurrentLens • समाचार".toByteArray(Charsets.UTF_8)), 128)
        assertEquals("CurrentLens • समाचार", result)
    }

    @Test
    fun rejectsChunkedResponseThatExceedsLimit() {
        var rejected = false
        try {
            NetworkLimits.readUtf8Limited(ByteArrayInputStream("123456789".toByteArray()), 8)
        } catch (_: IOException) {
            rejected = true
        }
        assertTrue(rejected)
    }
}
