package com.mangoloads.currentlens

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

object LocalStore {
    private const val PREFS = "currentlens_local"
    private const val BOOKMARKS = "bookmarks"
    private const val READ = "read_history"
    // New read markers are stored separately so the unordered legacy set can remain untouched.
    private const val RECENT_READ = "read_history_recent_v1"
    private const val ARTICLE_CACHE = "article_cache"
    private const val GLASS_ALPHA = "glass_alpha"
    private const val MAX_CACHED_ARTICLES = 500
    private const val CACHE_DAYS = 30
    private const val DEFAULT_GLASS_ALPHA = 0.28f
    private const val READING_SESSIONS = "reading_sessions"
    private const val READER_POSITIONS = "reader_positions"
    private const val QUIZ_ATTEMPTS = "quiz_attempts"
    private const val MAX_READING_SESSIONS = 5_000
    private const val MAX_READER_POSITIONS = ReaderPositionPolicy.MAX_ENTRIES
    private const val MAX_QUIZ_ATTEMPTS = 5_000
    private const val MAX_DETAILED_QUIZ_ATTEMPTS = 500
    // Serialize JSON read-modify-write operations for histories and reader positions.
    private val historyWriteMutex = Mutex()
    private val readerPositionWriteMutex = Mutex()

    fun bookmarks(context: Context): Set<String> =
        runCatching {
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getStringSet(BOOKMARKS, emptySet())?.toSet() ?: emptySet()
        }.getOrDefault(emptySet())

    fun isBookmarked(context: Context, url: String): Boolean = bookmarks(context).contains(url)

    fun toggleBookmark(context: Context, url: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next = runCatching {
            prefs.getStringSet(BOOKMARKS, emptySet())?.toMutableSet() ?: mutableSetOf()
        }.getOrDefault(mutableSetOf())
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
        runCatching {
            val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            // Preserve every pre-existing marker. The old StringSet has no ordering metadata,
            // so it is frozen rather than arbitrarily truncated during migration.
            val legacy = runCatching {
                prefs.getStringSet(READ, emptySet())?.toSet() ?: emptySet()
            }.getOrDefault(emptySet())
            val recentRaw = runCatching { prefs.getString(RECENT_READ, null) }.getOrNull()
            legacy + (parseRecentReadHistory(recentRaw) ?: emptyList())
        }.getOrDefault(emptySet())

    fun markRead(context: Context, url: String) {
        if (url.isBlank() || url.length > ReadHistoryPolicy.MAX_URL_LENGTH) return
        runCatching {
            val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            val legacy = runCatching {
                prefs.getStringSet(READ, emptySet())?.toSet() ?: emptySet()
            }.getOrDefault(emptySet())
            if (url in legacy) return@runCatching

            // Do not overwrite a malformed or wrong-typed recent-history value.
            val rawResult = runCatching { prefs.getString(RECENT_READ, null) }
            if (rawResult.isFailure) return@runCatching
            val current = parseRecentReadHistory(rawResult.getOrNull()) ?: return@runCatching
            val next = ReadHistoryPolicy.add(current, url)
            if (next == current) return@runCatching

            val array = JSONArray()
            next.forEach { array.put(it) }
            prefs.edit().putString(RECENT_READ, array.toString()).apply()
        }
    }

    private fun parseRecentReadHistory(raw: String?): List<String>? {
        if (raw == null) return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val url = array.optString(index)
                    if (url.isNotBlank()) add(url)
                }
            }
        }.getOrNull()?.let { ReadHistoryPolicy.retain(it) }
    }

    /** Read markers are updated and reloaded on IO, never while opening an article on main. */
    internal suspend fun markReadAndRead(context: Context, url: String): Set<String> =
        withContext(Dispatchers.IO) {
            historyWriteMutex.withLock {
                markRead(context, url)
                readHistory(context)
            }
        }

    fun cachedArticles(context: Context): List<Article> {
        val raw = runCatching {
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(ARTICLE_CACHE, null)
        }.getOrNull() ?: return emptyList()
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
                            weekendRead = item.optBoolean("weekendRead", false),
                            feedUrl = item.optString("feedUrl")
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
                    .put("feedUrl", article.feedUrl)
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(ARTICLE_CACHE, array.toString()).apply()
        return merged
    }


    internal fun readerPosition(
        context: Context,
        articleUrl: String,
        nowMillis: Long = System.currentTimeMillis()
    ): ReaderPosition? {
        if (articleUrl.isBlank()) return null
        return savedReaderPositions(context, nowMillis).firstOrNull { it.articleUrl == articleUrl }
    }

    internal fun saveReaderPosition(
        context: Context,
        articleUrl: String,
        progress: Float,
        updatedAtMillis: Long = System.currentTimeMillis()
    ): Boolean {
        if (articleUrl.isBlank() || articleUrl.length > ReaderPositionPolicy.MAX_URL_LENGTH ||
            updatedAtMillis <= 0L
        ) return false

        val current = savedReaderPositions(context, updatedAtMillis)
            .filterNot { it.articleUrl == articleUrl }
        if (!ReaderPositionPolicy.shouldPersist(progress)) {
            writeReaderPositions(context, current, updatedAtMillis)
            return false
        }

        val retained = ReaderPositionPolicy.retain(
            current + ReaderPosition(articleUrl, progress, updatedAtMillis),
            updatedAtMillis,
            MAX_READER_POSITIONS
        )
        writeReaderPositions(context, retained, updatedAtMillis)
        return retained.any { it.articleUrl == articleUrl }
    }

    internal fun clearReaderPosition(context: Context, articleUrl: String) {
        if (articleUrl.isBlank()) return
        val now = System.currentTimeMillis()
        writeReaderPositions(
            context,
            savedReaderPositions(context, now).filterNot { it.articleUrl == articleUrl },
            now
        )
    }

    /** Serialize reader-position JSON writes on IO; scroll callbacks must not parse it on main. */
    internal suspend fun saveReaderPositionAndWrite(
        context: Context,
        articleUrl: String,
        progress: Float,
        updatedAtMillis: Long = System.currentTimeMillis()
    ): Boolean = withContext(Dispatchers.IO) {
        readerPositionWriteMutex.withLock {
            saveReaderPosition(context, articleUrl, progress, updatedAtMillis)
        }
    }

    /** A completed read clears the saved position after any earlier queued save. */
    internal suspend fun clearReaderPositionAndWrite(context: Context, articleUrl: String) =
        withContext(Dispatchers.IO) {
            readerPositionWriteMutex.withLock {
                clearReaderPosition(context, articleUrl)
            }
        }

    private fun savedReaderPositions(context: Context, nowMillis: Long): List<ReaderPosition> {
        val raw = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getString(READER_POSITIONS, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            buildList {
                for (index in 0 until array.length()) {
                    val item = array.optJSONObject(index) ?: continue
                    add(
                        ReaderPosition(
                            articleUrl = item.optString("articleUrl"),
                            progress = item.optDouble("progress", Double.NaN).toFloat(),
                            updatedAtMillis = item.optLong("updatedAtMillis")
                        )
                    )
                }
            }.let { ReaderPositionPolicy.retain(it, nowMillis, MAX_READER_POSITIONS) }
        }.getOrDefault(emptyList())
    }

    private fun writeReaderPositions(
        context: Context,
        positions: List<ReaderPosition>,
        nowMillis: Long
    ) {
        val array = JSONArray()
        ReaderPositionPolicy.retain(positions, nowMillis, MAX_READER_POSITIONS).forEach { position ->
            array.put(
                JSONObject()
                    .put("articleUrl", position.articleUrl)
                    .put("progress", position.progress.toDouble())
                    .put("updatedAtMillis", position.updatedAtMillis)
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(READER_POSITIONS, array.toString()).apply()
    }

    internal fun readingSessions(context: Context): List<ReadingSession> {
        val raw = runCatching {
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(READING_SESSIONS, null)
        }.getOrNull() ?: return emptyList()
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

    /**
     * Persist and reload the bounded reading history on IO. The mutex prevents
     * overlapping lifecycle flushes from overwriting one another's read-modify-write.
     */
    internal suspend fun recordReadingSessionAndRead(
        context: Context,
        article: Article,
        startedAtMillis: Long,
        durationMillis: Long
    ): List<ReadingSession>? = withContext(Dispatchers.IO) {
        historyWriteMutex.withLock {
            if (recordReadingSession(context, article, startedAtMillis, durationMillis)) {
                readingSessions(context)
            } else null
        }
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
        val raw = runCatching {
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(QUIZ_ATTEMPTS, null)
        }.getOrNull() ?: return emptyList()
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
                    val answerArray = item.optJSONArray("answers")
                    val answerRecords = buildList {
                        if (answerArray != null) {
                            for (answerIndex in 0 until answerArray.length()) {
                                val answer = answerArray.optJSONObject(answerIndex) ?: continue
                                val questionIndex = answer.optInt("questionIndex", -1)
                                val prompt = answer.optString("prompt")
                                val selectedAnswer = answer.optString("selectedAnswer")
                                val correctAnswer = answer.optString("correctAnswer")
                                val selectedOptionIndex = answer.optInt("selectedOptionIndex", -1)
                                val correctOptionIndex = answer.optInt("correctOptionIndex", -1)
                                if (questionIndex < 0 || prompt.isBlank() || selectedAnswer.isBlank() ||
                                    correctAnswer.isBlank() || selectedOptionIndex !in 0..7 ||
                                    correctOptionIndex !in 0..7
                                ) continue
                                add(
                                    QuizAnswerRecord(
                                        questionIndex = questionIndex,
                                        prompt = prompt.take(500),
                                        selectedAnswer = selectedAnswer.take(300),
                                        correctAnswer = correctAnswer.take(300),
                                        selectedOptionIndex = selectedOptionIndex,
                                        correctOptionIndex = correctOptionIndex,
                                        explanation = answer.optString("explanation").take(600),
                                        provenance = answer.optString("provenance").take(250)
                                    )
                                )
                            }
                        }
                    }
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
                            correctCount = correct,
                            answers = answerRecords
                        )
                    )
                }
            }.sortedByDescending { it.submittedAtMillis }
        }.getOrDefault(emptyList())
    }

    /** Persist and reload quiz history off the UI thread, serialized with session writes. */
    internal suspend fun recordQuizAttemptAndRead(
        context: Context,
        article: Article,
        answers: Map<Int, Int>,
        questions: List<QuizQuestion>,
        submittedAtMillis: Long = System.currentTimeMillis()
    ): List<QuizAttempt>? = withContext(Dispatchers.IO) {
        historyWriteMutex.withLock {
            if (recordQuizAttempt(context, article, answers, questions, submittedAtMillis) != null) {
                quizAttempts(context)
            } else null
        }
    }

    internal fun recordQuizAttempt(
        context: Context,
        article: Article,
        answers: Map<Int, Int>,
        questions: List<QuizQuestion>,
        submittedAtMillis: Long = System.currentTimeMillis()
    ): QuizAttempt? {
        if (article.url.isBlank() || submittedAtMillis <= 0L || questions.isEmpty()) return null
        val answerRecords = QuizAttemptPolicy.snapshot(answers, questions)
        if (answerRecords.isEmpty()) return null
        val correct = answerRecords.count { it.isCorrect }
        val relevance = UpscEngine.classify(article)
        val attempt = QuizAttempt(
            id = UUID.randomUUID().toString(),
            articleUrl = article.url,
            articleTitle = article.title,
            subject = relevance.subject,
            gsPaper = relevance.gsPaper,
            submittedAtMillis = submittedAtMillis,
            totalQuestions = questions.size,
            answeredCount = answerRecords.size,
            correctCount = correct,
            answers = answerRecords
        )
        val retained = QuizAttemptPolicy.retain(quizAttempts(context) + attempt, MAX_QUIZ_ATTEMPTS)
        val array = JSONArray()
        retained.forEachIndexed { index, record ->
            val answerArray = JSONArray()
            if (index < MAX_DETAILED_QUIZ_ATTEMPTS) {
                record.answers.forEach { answer ->
                    answerArray.put(
                        JSONObject()
                            .put("questionIndex", answer.questionIndex)
                            .put("prompt", answer.prompt)
                            .put("selectedAnswer", answer.selectedAnswer)
                            .put("correctAnswer", answer.correctAnswer)
                            .put("selectedOptionIndex", answer.selectedOptionIndex)
                            .put("correctOptionIndex", answer.correctOptionIndex)
                            .put("explanation", answer.explanation)
                            .put("provenance", answer.provenance)
                    )
                }
            }
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
                    .put("answers", answerArray)
            )
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(QUIZ_ATTEMPTS, array.toString()).apply()
        return attempt
    }

    fun glassAlpha(context: Context): Float =
        runCatching {
            context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getFloat(GLASS_ALPHA, DEFAULT_GLASS_ALPHA).coerceIn(0.12f, 0.55f)
        }.getOrDefault(DEFAULT_GLASS_ALPHA)

    fun saveGlassAlpha(context: Context, value: Float) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putFloat(GLASS_ALPHA, value.coerceIn(0.12f, 0.55f)).apply()
    }
}
