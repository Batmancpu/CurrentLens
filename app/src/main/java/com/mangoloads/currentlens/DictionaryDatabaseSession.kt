package com.mangoloads.currentlens

/**
 * Reuses a process-scoped read-only database handle and reopens it only after it is closed.
 *
 * The opener is supplied at lookup time so this policy stays independent of Android Context
 * and can be exercised with a small JVM fake in unit tests.
 */
internal class DictionaryDatabaseSession<T>(
    private val isOpen: (T) -> Boolean
) {
    private var current: T? = null

    @Synchronized
    fun getOrOpen(open: () -> T?): T? {
        current?.let { existing ->
            if (isOpen(existing)) return existing
        }
        current = null

        val opened = open() ?: return null
        if (!isOpen(opened)) return null

        current = opened
        return opened
    }
}


/** Decides whether a persisted dictionary file must be replaced by the bundled schema. */
internal object DictionaryDatabaseInstallPolicy {
    fun shouldInstall(
        fileExists: Boolean,
        fileLength: Long,
        installedVersion: Int?,
        expectedVersion: Int,
        minimumBytes: Long = 1_000_000L
    ): Boolean =
        !fileExists || fileLength < minimumBytes || installedVersion != expectedVersion
}
