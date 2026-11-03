package com.mangoloads.currentlens

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID

object LocalStore {
    private const val PREFS = "currentlens_local"
    private const val BOOKMARKS = "bookmarks"
    private const val READ = "read_history"
    private const val ARTICLE_CACHE = "article_cache"
    private const val GLASS_ALPHA = "glass_alpha"
    private const val MAX_CACHED_ARTICLES = 500
    private const val CACHE_DAYS = 30
    private const val DEFAULT_GLASS_ALPHA = 0.28f
    private const val READING_SESSIONS = "reading_sessions"
    private const val QUIZ_ATTEMPTS = "quiz_attempts"
    private const val MAX_READING_SESSIONS = 5_000
    private const val MAX_QUIZ_ATTEMPTS = 5_000

    fun bookmarks(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(BOOKMARKS, emptySet())?.toSet() ?: emptySet()

    fun isBookmarked(context: Context, url: String): Boolean = bookmarks(context).contains(url)

    fun toggleBookmark(context: Context, url: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next = prefs.getStringSet(BOOKMARKS, emptySet())?.toMutableSet() ?: mutableSetOf()
        val added = if (next.contains(url)) {
            next.remove(url)
            false
        } else {
            next.add(url)
            true
        }
        prefs.edit().putStringSet(BOOKMARKS, next.toSet()).apply()
        return added
    }

    fun readHistory(context: Context): Set<String> =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getStringSet(READ, emptySet())?.toSet() ?: emptySet()

    fun markRead(context: Context, url: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next = prefs.getStringSet(READ, emptySet())?.toMutableSet() ?: mutableSetOf()
        next.add(url)
        prefs.edit().putStringSet(READ, next.toSet()).apply()
    }

    fun cachedArticles(context: Context): List<Article> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(ARTICLE_CACHE, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    val url = item.optString("url")
                    val title = item.optString("title")
                    if (url.isBlank() || title.isBlank()) continue
                    add(
                        Article(
                            id = item.optString("id").ifBlank { url },
                            title = title,
                            description = item.optString("description"),
                            url = url,
                            source = item.optString("source"),
                            section = item.optString("section"),
                            published = item.optString("published"),
                            imageUrl = item.optString("imageUrl").takeIf { it.isNotBlank() },
                            weekendRead = item.optBoolean("weekendRead", false)
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    fun mergeAndSaveArticles(context: Context, incoming: List<Article>): List<Article> {
        val savedUrls = bookmarks(context)
        val zone = ZoneId.of("Asia/Kolkata")
        val cutoff = LocalDate.now(zone).minusDays(CACHE_DAYS.toLong())
            .atStartOfDay(zone).toInstant().toEpochMilli()
        val merged = ArticleCachePolicy.retain(
            incoming + cachedArticles(context),
            bookmarkedUrls = savedUrls,
            cutoffMillis = cutoff,
            maxArticles = MAX_CACHED_ARTICLES
        )

        val array = JSONArray()
        merged.forEach { article ->
            array.put(
                JSONObject()
                    .put("id", article.id)
                    .put("title", article.title)
                    .put("description", article.description)
                    .put("url", article.url)
                    .put("source", article.source)
                    .put("section", article.section)
                    .put("published", article.published)
                    .put("imageUrl", article.imageUrl ?: JSONObject.NULL)
                    .put("weekendRead", article.weekendRead)
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(ARTICLE_CACHE, array.toString()).apply()
        return merged
    }


    internal fun readingSessions(context: Context): List<ReadingSession> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(READING_SESSIONS, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    val url = item.optString("articleUrl")
                    val started = item.optLong("startedAtMillis")
                    val duration = item.optLong("durationMillis")
                    if (url.isBlank() || started <= 0L ||
                        duration !in ForegroundReadingSessionClock.MIN_SESSION_MILLIS..ForegroundReadingSessionClock.MAX_SESSION_MILLIS
                    ) continue
                    add(
                        ReadingSession(
                            id = item.optString("id"),
                            articleUrl = url,
                            articleTitle = item.optString("articleTitle"),
                            source = item.optString("source"),
                            subject = item.optString("subject").ifBlank { "No clear syllabus match" },
                            gsPaper = item.optString("gsPaper").ifBlank { "Unmapped" },
                            startedAtMillis = started,
                            durationMillis = duration
                        )
                    )
                }
            }.sortedBy { it.startedAtMillis }
        }.getOrDefault(emptyList())
    }

    internal fun recordReadingSession(
        context: Context,
        article: Article,
        startedAtMillis: Long,
        durationMillis: Long
    ): Boolean {
        if (article.url.isBlank() || startedAtMillis <= 0L ||
            durationMillis !in ForegroundReadingSessionClock.MIN_SESSION_MILLIS..ForegroundReadingSessionClock.MAX_SESSION_MILLIS
        ) return false
        val relevance = UpscEngine.classify(article)
        val record = ReadingSession(
            id = UUID.randomUUID().toString(),
            articleUrl = article.url,
            articleTitle = article.title,
            source = article.source,
            subject = relevance.subject,
            gsPaper = relevance.gsPaper,
            startedAtMillis = startedAtMillis,
            durationMillis = durationMillis
        )
        val retained = (readingSessions(context) + record)
            .sortedByDescending { it.startedAtMillis }
            .take(MAX_READING_SESSIONS)
        val array = JSONArray()
        retained.forEach { session ->
            array.put(
                JSONObject()
                    .put("id", session.id)
                    .put("articleUrl", session.articleUrl)
                    .put("articleTitle", session.articleTitle)
                    .put("source", session.source)
                    .put("subject", session.subject)
                    .put("gsPaper", session.gsPaper)
                    .put("startedAtMillis", session.startedAtMillis)
                    .put("durationMillis", session.durationMillis)
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(READING_SESSIONS, array.toString()).apply()
        return true
    }

    internal fun quizAttempts(context: Context): List<QuizAttempt> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(QUIZ_ATTEMPTS, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    val url = item.optString("articleUrl")
                    val total = item.optInt("totalQuestions")
                    val answered = item.optInt("answeredCount")
                    val correct = item.optInt("correctCount")
                    val submitted = item.optLong("submittedAtMillis")
                    if (url.isBlank() || submitted <= 0L || total <= 0 ||
                        answered !in 1..total || correct !in 0..answered
                    ) continue
                    add(
                        QuizAttempt(
                            id = item.optString("id"),
                            articleUrl = url,
                            articleTitle = item.optString("articleTitle"),
                            subject = item.optString("subject").ifBlank { "No clear syllabus match" },
                            gsPaper = item.optString("gsPaper").ifBlank { "Unmapped" },
                            submittedAtMillis = submitted,
                            totalQuestions = total,
                            answeredCount = answered,
                            correctCount = correct
                        )
                    )
                }
            }.sortedByDescending { it.submittedAtMillis }
        }.getOrDefault(emptyList())
    }

    internal fun recordQuizAttempt(
        context: Context,
        article: Article,
        answers: Map<Int, Int>,
        questions: List<QuizQuestion>,
        submittedAtMillis: Long = System.currentTimeMillis()
    ): QuizAttempt? {
        if (article.url.isBlank() || submittedAtMillis <= 0L || questions.isEmpty()) return null
        val validAnswers = answers.filter { (questionIndex, optionIndex) ->
            questionIndex in questions.indices && optionIndex in questions[questionIndex].options.indices
        }
        if (validAnswers.isEmpty()) return null
        val correct = validAnswers.count { (questionIndex, optionIndex) ->
            questions[questionIndex].correctIndex == optionIndex
        }
        val relevance = UpscEngine.classify(article)
        val attempt = QuizAttempt(
            id = UUID.randomUUID().toString(),
            articleUrl = article.url,
            articleTitle = article.title,
            subject = relevance.subject,
            gsPaper = relevance.gsPaper,
            submittedAtMillis = submittedAtMillis,
            totalQuestions = questions.size,
            answeredCount = validAnswers.size,
            correctCount = correct
        )
        val retained = (quizAttempts(context) + attempt)
            .sortedByDescending { it.submittedAtMillis }
            .take(MAX_QUIZ_ATTEMPTS)
        val array = JSONArray()
        retained.forEach { record ->
            array.put(
                JSONObject()
                    .put("id", record.id)
                    .put("articleUrl", record.articleUrl)
                    .put("articleTitle", record.articleTitle)
                    .put("subject", record.subject)
                    .put("gsPaper", record.gsPaper)
                    .put("submittedAtMillis", record.submittedAtMillis)
                    .put("totalQuestions", record.totalQuestions)
                    .put("answeredCount", record.answeredCount)
                    .put("correctCount", record.correctCount)
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(QUIZ_ATTEMPTS, array.toString()).apply()
        return attempt
    }

    fun glassAlpha(context: Context): Float =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getFloat(GLASS_ALPHA, DEFAULT_GLASS_ALPHA).coerceIn(0.12f, 0.55f)

    fun saveGlassAlpha(context: Context, value: Float) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putFloat(GLASS_ALPHA, value.coerceIn(0.12f, 0.55f)).apply()
    }
}
