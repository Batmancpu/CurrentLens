package com.mangoloads.currentlens

import java.net.URI

internal data class BackupMerge<T>(val values: List<T>, val added: Int, val skipped: Int)

internal object PortableBackupPolicy {
    const val MAX_BYTES = 16 * 1024 * 1024
    const val MAX_DEPTH = 16
    const val MAX_RECORDS = 100_000

    /** Retain every existing item; import unique records only within the remaining capacity. */
    fun <T, K> merge(local: List<T>, incoming: List<T>, max: Int, key: (T) -> K): BackupMerge<T> {
        val result = local.toMutableList()
        val seen = local.mapTo(HashSet()) { key(it) }
        var added = 0
        var skipped = 0
        incoming.forEach { value ->
            if (result.size >= max || !seen.add(key(value))) {
                skipped++
            } else {
                result.add(value)
                added++
            }
        }
        return BackupMerge(result, added, skipped)
    }

    fun safeUrl(value: String): Boolean {
        if (value.isBlank() || value.length > 2048) return false
        val uri = runCatching { URI(value) }.getOrNull() ?: return false
        return uri.scheme.equals("https", true) && uri.host != null && uri.rawUserInfo == null &&
            uri.port in listOf(-1, 443)
    }

    /** Reject pathological nesting before handing an untrusted local file to the JSON parser. */
    fun hasBoundedDepth(json: String): Boolean {
        var depth = 0
        var quoted = false
        var escaped = false
        for (char in json) {
            if (quoted) {
                if (escaped) escaped = false
                else if (char == '\\') escaped = true
                else if (char == '"') quoted = false
            } else when (char) {
                '"' -> quoted = true
                '{', '[' -> if (++depth > MAX_DEPTH) return false
                '}', ']' -> if (--depth < 0) return false
            }
        }
        return !quoted && !escaped && depth == 0
    }
}
