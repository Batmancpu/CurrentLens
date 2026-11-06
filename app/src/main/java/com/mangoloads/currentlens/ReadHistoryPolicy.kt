package com.mangoloads.currentlens

/** Pure read-marker mutation; existing URL identity and history are preserved. */
internal object ReadHistoryPolicy {
    fun add(existing: Set<String>, url: String): Set<String> {
        if (url.isBlank() || url in existing) return existing
        return existing + url
    }
}
