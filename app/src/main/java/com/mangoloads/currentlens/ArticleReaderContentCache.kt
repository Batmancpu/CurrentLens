package com.mangoloads.currentlens

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.security.MessageDigest

/**
 * Private on-device cache for successfully extracted and sanitised public article bodies.
 * It never stores publisher fallback pages, login cookies, or paywalled responses.
 */
internal object ArticleReaderContentCache {
    private const val DIRECTORY = "reader_article_cache"
    private const val MAX_ENTRY_BYTES = 2 * 1024 * 1024
    private const val MAX_CACHE_BYTES = 24L * 1024 * 1024
    private const val MAX_ENTRIES = 30
    private const val MAX_AGE_MILLIS = 30L * 24 * 60 * 60 * 1000

    suspend fun read(context: Context, url: String): String? = withContext(Dispatchers.IO) {
        runCatching {
            val file = articleFile(context, url)
            if (!file.isFile) return@runCatching null
            val now = System.currentTimeMillis()
            val age = now - file.lastModified()
            if (age !in 0..MAX_AGE_MILLIS || file.length() !in 1..MAX_ENTRY_BYTES.toLong()) {
                file.delete()
                return@runCatching null
            }
            val content = file.readText(Charsets.UTF_8)
            if (content.isBlank()) {
                file.delete()
                null
            } else {
                file.setLastModified(now)
                prune(context, now)
                content
            }
        }.getOrNull()
    }

    suspend fun write(context: Context, url: String, sanitisedHtml: String): Boolean = withContext(Dispatchers.IO) {
        runCatching {
            val bytes = sanitisedHtml.toByteArray(Charsets.UTF_8)
            if (bytes.isEmpty() || bytes.size > MAX_ENTRY_BYTES) return@runCatching false
            val directory = cacheDirectory(context)
            if (!directory.isDirectory) return@runCatching false
            val name = cacheKey(url)
            val temporary = File(directory, "$name.tmp")
            val destination = File(directory, "$name.html")
            temporary.outputStream().buffered().use { it.write(bytes) }
            if (destination.exists() && !destination.delete()) {
                temporary.delete()
                return@runCatching false
            }
            if (!temporary.renameTo(destination)) {
                temporary.delete()
                return@runCatching false
            }
            destination.setLastModified(System.currentTimeMillis())
            prune(context, System.currentTimeMillis())
            true
        }.getOrDefault(false)
    }

    private fun prune(context: Context, now: Long) {
        val directory = cacheDirectory(context)
        val files = directory.listFiles()?.filter { it.isFile && it.extension == "html" }.orEmpty()
        val entries = files.map { file ->
            ArticleContentCacheEntry(file.nameWithoutExtension, file.length(), file.lastModified())
        }
        val retained = ArticleContentCachePolicy.retain(
            entries = entries,
            nowMillis = now,
            maxAgeMillis = MAX_AGE_MILLIS,
            maxEntries = MAX_ENTRIES,
            maxBytes = MAX_CACHE_BYTES,
            maxEntryBytes = MAX_ENTRY_BYTES.toLong()
        ).toSet()
        files.filterNot { it.nameWithoutExtension in retained }.forEach { it.delete() }
        directory.listFiles()?.filter { it.isFile && it.extension == "tmp" }?.forEach { it.delete() }
    }

    private fun cacheDirectory(context: Context): File =
        File(context.filesDir, DIRECTORY).apply { if (!exists()) mkdirs() }

    private fun articleFile(context: Context, url: String): File =
        File(cacheDirectory(context), cacheKey(url) + ".html")

    private fun cacheKey(url: String): String =
        MessageDigest.getInstance("SHA-256")
            .digest(url.toByteArray(Charsets.UTF_8))
            .joinToString("") { byte -> "%02x".format(byte) }
}

/** Pure retention policy so cache age, count and byte limits are independently testable. */
internal data class ArticleContentCacheEntry(
    val key: String,
    val sizeBytes: Long,
    val lastAccessMillis: Long
)
