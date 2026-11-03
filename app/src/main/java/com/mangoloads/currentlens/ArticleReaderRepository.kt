package com.mangoloads.currentlens

import android.text.Html
import com.prof18.klead.Klead
import com.prof18.klead.KleadOptions
import com.prof18.klead.KleadOutput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object ArticleReaderRepository {
    suspend fun extractAccessibleArticle(url: String, cookies: String?): String? =
        withContext(Dispatchers.IO) {
            try {
                val connection = (URL(url).openConnection() as HttpURLConnection).apply {
                    connectTimeout = 9000
                    readTimeout = 12000
                    instanceFollowRedirects = true
                    requestMethod = "GET"
                    setRequestProperty(
                        "User-Agent",
                        "Mozilla/5.0 (Linux; Android 15) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Mobile Safari/537.36"
                    )
                    setRequestProperty("Accept", "text/html,application/xhtml+xml")
                    setRequestProperty("Accept-Language", "en-IN,en;q=0.9")
                    if (!cookies.isNullOrBlank()) setRequestProperty("Cookie", cookies)
                }
                try {
                    if (connection.responseCode !in 200..299) return@withContext null
                    if (!connection.contentType.orEmpty().contains("html", ignoreCase = true)) return@withContext null
                    if (connection.contentLengthLong > 5_000_000L) return@withContext null
                    val sourceHtml = connection.inputStream.bufferedReader().use { it.readText() }
                    if (sourceHtml.length < 1200 || sourceHtml.length > 5_000_000) return@withContext null

                    val result = Klead.parseHtml(
                        html = sourceHtml,
                        url = url,
                        options = KleadOptions(outputs = setOf(KleadOutput.HTML))
                    )
                    val cleanHtml = result.content.requireHtml()
                    val plainText = Html.fromHtml(cleanHtml, Html.FROM_HTML_MODE_LEGACY).toString().trim()
                    if (plainText.length < 650) return@withContext null
                    cleanHtml
                } finally {
                    connection.disconnect()
                }
            } catch (_: Exception) {
                null
            }
        }
}
