package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertSame
import org.junit.Assert.assertNotSame
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DictionaryDatabaseSessionTest {
    private class FakeConnection(var open: Boolean = true)

    private fun session() = DictionaryDatabaseSession<FakeConnection> { it.open }

    @Test
    fun reusesAnOpenConnectionForSubsequentLookups() {
        val session = session()
        var openCount = 0
        val first = session.getOrOpen {
            openCount += 1
            FakeConnection()
        }
        val second = session.getOrOpen {
            openCount += 1
            FakeConnection()
        }

        assertNotNull(first)
        assertSame(first, second)
        assertEquals(1, openCount)
    }

    @Test
    fun reopensConnectionAfterItHasBeenClosed() {
        val session = session()
        var openCount = 0
        val first = session.getOrOpen {
            openCount += 1
            FakeConnection()
        }!!

        first.open = false
        val second = session.getOrOpen {
            openCount += 1
            FakeConnection()
        }

        assertNotNull(second)
        assertNotSame(first, second)
        assertEquals(2, openCount)
    }

    @Test
    fun failedOpenIsNotCachedAndCanRecoverOnNextLookup() {
        val session = session()
        assertNull(session.getOrOpen { null })

        val recovered = session.getOrOpen { FakeConnection() }
        assertNotNull(recovered)
    }

    @Test
    fun currentSchemaIsReusedWithoutCopyingTheBundledDatabase() {
        assertFalse(
            DictionaryDatabaseInstallPolicy.shouldInstall(
                fileExists = true,
                fileLength = 50_000_000L,
                installedVersion = 2027,
                expectedVersion = 2027
            )
        )
    }

    @Test
    fun outdatedOrUnreadableSchemaIsReplacedOnUpgrade() {
        assertTrue(
            DictionaryDatabaseInstallPolicy.shouldInstall(
                fileExists = true,
                fileLength = 50_000_000L,
                installedVersion = 2026,
                expectedVersion = 2027
            )
        )
        assertTrue(
            DictionaryDatabaseInstallPolicy.shouldInstall(
                fileExists = true,
                fileLength = 50_000_000L,
                installedVersion = null,
                expectedVersion = 2027
            )
        )
    }

    @Test
    fun missingOrTruncatedDatabaseIsReinstalled() {
        assertTrue(
            DictionaryDatabaseInstallPolicy.shouldInstall(
                fileExists = false,
                fileLength = 0L,
                installedVersion = null,
                expectedVersion = 2027
            )
        )
        assertTrue(
            DictionaryDatabaseInstallPolicy.shouldInstall(
                fileExists = true,
                fileLength = 100L,
                installedVersion = null,
                expectedVersion = 2027
            )
        )
    }

    @Test
    fun closedConnectionReturnedByOpenerIsNotRetained() {
        val session = session()
        assertNull(session.getOrOpen { FakeConnection(open = false) })

        val recovered = session.getOrOpen { FakeConnection() }
        assertNotNull(recovered)
    }
}
