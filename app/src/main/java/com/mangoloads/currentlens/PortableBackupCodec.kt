package com.mangoloads.currentlens

import org.json.JSONArray
import org.json.JSONObject

internal data class PortableBackup(
    val bookmarks: List<String>,
    val savedArticles: List<JSONObject>,
    val legacyRead: List<String>,
    val recentRead: List<String>,
    val sessions: List<JSONObject>,
    val attempts: List<JSONObject>,
    val positions: List<JSONObject>,
    val notes: Map<String, String>
)

internal data class BackupImportResult(val mainSaved: Boolean, val notesSaved: Boolean, val added: Int, val skipped: Int)

internal object PortableBackupCodec {
    private const val SCHEMA = "currentlens-portable-backup"
    private const val VERSION = 1

    fun encode(data: PortableBackup): String = JSONObject()
        .put("schema", SCHEMA).put("version", VERSION)
        .put("bookmarks", JSONArray(data.bookmarks)).put("savedArticles", JSONArray(data.savedArticles))
        .put("legacyRead", JSONArray(data.legacyRead)).put("recentRead", JSONArray(data.recentRead))
        .put("sessions", JSONArray(data.sessions)).put("attempts", JSONArray(data.attempts))
        .put("positions", JSONArray(data.positions)).put("notes", JSONObject(data.notes))
        .toString().also { require(it.toByteArray(Charsets.UTF_8).size <= PortableBackupPolicy.MAX_BYTES) }

    private fun string(obj: JSONObject, key: String, max: Int, allowBlank: Boolean = false): String {
        val value = obj.get(key) as? String ?: error("Invalid text field")
        require(value.length <= max && (allowBlank || value.isNotBlank()))
        return value
    }

    private fun number(obj: JSONObject, key: String): Long {
        val value = obj.get(key) as? Number ?: error("Invalid numeric field")
        val asLong = value.toLong()
        require(value.toDouble().isFinite() && value.toDouble() == asLong.toDouble())
        return asLong
    }

    private fun url(obj: JSONObject, key: String): String = string(obj, key, 2048).also {
        require(PortableBackupPolicy.safeUrl(it))
    }

    private fun urls(array: JSONArray): List<String> {
        require(array.length() <= PortableBackupPolicy.MAX_RECORDS)
        return (0 until array.length()).map { index ->
            (array.get(index) as? String ?: error("Invalid URL")).also {
                require(PortableBackupPolicy.safeUrl(it))
            }
        }.distinct()
    }

    internal fun objects(array: JSONArray, max: Int = PortableBackupPolicy.MAX_RECORDS): List<JSONObject> {
        require(array.length() <= max)
        return (0 until array.length()).map(array::getJSONObject)
    }

    private fun textMetadata(obj: JSONObject): JSONObject = JSONObject()
        .put("id", if (obj.has("id")) string(obj, "id", 128, true) else "")
        .put("articleUrl", url(obj, "articleUrl"))
        .put("articleTitle", string(obj, "articleTitle", 2048, true))
        .put("subject", string(obj, "subject", 256, true))
        .put("gsPaper", string(obj, "gsPaper", 64, true))

    private fun savedArticle(obj: JSONObject, saved: Set<String>): JSONObject {
        val link = url(obj, "url")
        val source = string(obj, "source", 128)
        require(link in saved && PublisherHostPolicy.allows(source, link))
        val image = obj.opt("imageUrl") as? String
        val feed = obj.opt("feedUrl") as? String
        return JSONObject().put("id", link).put("url", link)
            .put("title", string(obj, "title", 2048))
            .put("description", string(obj, "description", 8192, true).take(420))
            .put("source", source).put("section", string(obj, "section", 256, true))
            .put("published", string(obj, "published", 256, true))
            .put("imageUrl", image?.takeIf { it.length <= 2048 && PublisherAssetHostPolicy.allows(source, it) } ?: JSONObject.NULL)
            .put("feedUrl", feed?.takeIf { it.length <= 2048 && PublisherHostPolicy.allows(source, it) }.orEmpty())
            .put("weekendRead", obj.optBoolean("weekendRead"))
    }

    private fun session(obj: JSONObject): JSONObject {
        val started = number(obj, "startedAtMillis")
        val duration = number(obj, "durationMillis")
        require(started > 0 && duration in ForegroundReadingSessionClock.MIN_SESSION_MILLIS..ForegroundReadingSessionClock.MAX_SESSION_MILLIS)
        return textMetadata(obj).put("source", string(obj, "source", 128, true))
            .put("startedAtMillis", started).put("durationMillis", duration)
    }

    private fun attempt(obj: JSONObject): JSONObject {
        val total = number(obj, "totalQuestions")
        val answered = number(obj, "answeredCount")
        val correct = number(obj, "correctCount")
        val submitted = number(obj, "submittedAtMillis")
        require(total in 1L..10L && answered in 1L..total && correct in 0L..answered && submitted > 0L)
        val answerObjects = objects(if (obj.has("answers")) obj.getJSONArray("answers") else JSONArray(), 10).map { answer ->
            val index = number(answer, "questionIndex")
            val selected = number(answer, "selectedOptionIndex")
            val expected = number(answer, "correctOptionIndex")
            require(index in 0L until total && selected in 0L..7L && expected in 0L..7L)
            JSONObject().put("questionIndex", index)
                .put("prompt", string(answer, "prompt", 500))
                .put("selectedAnswer", string(answer, "selectedAnswer", 300))
                .put("correctAnswer", string(answer, "correctAnswer", 300))
                .put("selectedOptionIndex", selected).put("correctOptionIndex", expected)
                .put("explanation", string(answer, "explanation", 600, true))
                .put("provenance", string(answer, "provenance", 250, true))
        }
        require(answerObjects.isEmpty() || (answerObjects.size.toLong() == answered &&
            answerObjects.map { it.getLong("questionIndex") }.distinct().size == answerObjects.size &&
            answerObjects.count { it.getLong("selectedOptionIndex") == it.getLong("correctOptionIndex") }.toLong() == correct))
        return textMetadata(obj).put("submittedAtMillis", submitted).put("totalQuestions", total)
            .put("answeredCount", answered).put("correctCount", correct).put("answers", JSONArray(answerObjects))
    }

    private fun position(obj: JSONObject): JSONObject {
        val progress = (obj.get("progress") as? Number ?: error("Invalid progress")).toDouble()
        val time = number(obj, "updatedAtMillis")
        require(time > 0 && ReaderPositionPolicy.shouldPersist(progress.toFloat()))
        return JSONObject().put("articleUrl", url(obj, "articleUrl")).put("progress", progress).put("updatedAtMillis", time)
    }

    fun decode(text: String): PortableBackup {
        require(text.length <= PortableBackupPolicy.MAX_BYTES && PortableBackupPolicy.hasBoundedDepth(text))
        val root = JSONObject(text)
        require(root.optString("schema") == SCHEMA && number(root, "version") == VERSION.toLong())
        val bookmarks = urls(root.getJSONArray("bookmarks"))
        val saved = bookmarks.toSet()
        val noteObject = root.getJSONObject("notes")
        require(noteObject.length() <= PortableBackupPolicy.MAX_RECORDS)
        val notes = buildMap {
            val keys = noteObject.keys()
            while (keys.hasNext()) {
                val key = keys.next()
                require(key.matches(Regex("[0-9a-f]{64}")))
                val value = string(noteObject, key, ReviewPolicy.MAX_NOTE_CHARS, true)
                if (value.isNotBlank()) put(key, value)
            }
        }
        return PortableBackup(
            bookmarks,
            objects(root.getJSONArray("savedArticles")).map { savedArticle(it, saved) },
            urls(root.getJSONArray("legacyRead")),
            urls(root.getJSONArray("recentRead")),
            objects(root.getJSONArray("sessions"), 5000).map(::session),
            objects(root.getJSONArray("attempts"), 5000).map(::attempt),
            objects(root.getJSONArray("positions"), ReaderPositionPolicy.MAX_ENTRIES).map(::position),
            notes
        )
    }
}
