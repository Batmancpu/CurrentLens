package com.mangoloads.currentlens

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

/** Strict byte ceilings for untrusted RSS and dictionary responses. */
internal object NetworkLimits {
    const val MAX_RSS_BYTES = 2 * 1024 * 1024
    const val MAX_DICTIONARY_BYTES = 512 * 1024

    fun readUtf8Limited(input: InputStream, maxBytes: Int): String {
        require(maxBytes > 0) { "maxBytes must be positive" }
        val output = ByteArrayOutputStream(minOf(maxBytes, 8192))
        val buffer = ByteArray(8192)
        var total = 0
        while (true) {
            val count = input.read(buffer)
            if (count < 0) break
            if (count == 0) continue
            if (count > maxBytes - total) throw IOException("Response exceeds $maxBytes bytes")
            total += count
            output.write(buffer, 0, count)
        }
        return String(output.toByteArray(), Charsets.UTF_8)
    }
}
