package com.mangoloads.currentlens

/** Visibility gate for the in-capsule clear action; the surrounding slot stays reserved. */
internal object SearchClearActionPolicy {
    fun shouldShow(query: String, fieldActive: Boolean): Boolean =
        fieldActive && query.isNotBlank()
}
