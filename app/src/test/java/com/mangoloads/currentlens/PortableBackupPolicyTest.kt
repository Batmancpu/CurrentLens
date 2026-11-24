package com.mangoloads.currentlens

import org.junit.Assert.*
import org.junit.Test

class PortableBackupPolicyTest {
    @Test fun existingRecordsWinAndCapacityNeverEvictsLocalData() {
        data class Record(val id: String, val text: String)
        val local = listOf(Record("a", "local"), Record("b", "existing"))
        val incoming = listOf(Record("a", "different"), Record("c", "new"), Record("d", "extra"))
        val result = PortableBackupPolicy.merge(local, incoming, 3) { it.id }
        assertEquals(local + incoming[1], result.values)
        assertEquals(1, result.added)
        assertEquals(2, result.skipped)
        assertEquals(local, PortableBackupPolicy.merge(local, incoming, 1) { it.id }.values)
    }

    @Test fun depthCheckHandlesStringsAndRejectsExcessNesting() {
        assertTrue(PortableBackupPolicy.hasBoundedDepth("""{"note":"{[ }", "list":[1,2]}"""))
        assertFalse(PortableBackupPolicy.hasBoundedDepth("[".repeat(17) + "]".repeat(17)))
        assertFalse(PortableBackupPolicy.hasBoundedDepth("""{"note":"unfinished}"""))
        assertFalse(PortableBackupPolicy.hasBoundedDepth("]"))
    }

    @Test fun backupLinksMustBeHttpsWithoutCredentialsOrCustomPorts() {
        assertTrue(PortableBackupPolicy.safeUrl("https://indianexpress.com/article/test"))
        assertFalse(PortableBackupPolicy.safeUrl("file:///data/private"))
        assertFalse(PortableBackupPolicy.safeUrl("https://user@indianexpress.com/test"))
        assertFalse(PortableBackupPolicy.safeUrl("https://indianexpress.com:444/test"))
    }
}
