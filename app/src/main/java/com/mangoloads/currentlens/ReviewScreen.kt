package com.mangoloads.currentlens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest

/** Independent local-only note store; no change to bookmarks or quiz history schema. */
internal object ReviewNoteStore {
    private fun key(url: String): String = MessageDigest.getInstance("SHA-256")
        .digest(url.toByteArray(Charsets.UTF_8)).joinToString("") { "%02x".format(it.toInt() and 255) }

    suspend fun read(context: Context, url: String): String = withContext(Dispatchers.IO) {
        context.getSharedPreferences("currentlens_review_notes_v1", Context.MODE_PRIVATE)
            .getString(key(url), "").orEmpty()
    }

    suspend fun save(context: Context, url: String, text: String): Boolean = withContext(Dispatchers.IO) {
        val normalized = ReviewPolicy.normalizeNote(text)
        val edit = context.getSharedPreferences("currentlens_review_notes_v1", Context.MODE_PRIVATE).edit()
        if (normalized.isEmpty()) edit.remove(key(url)) else edit.putString(key(url), normalized)
        edit.commit()
    }
}

@Composable
internal fun ReviewScreen(
    articles: List<Article>, bookmarks: Set<String>, attempts: List<QuizAttempt>,
    onOpenArticle: (Article) -> Unit
) {
    var queue by remember { mutableStateOf<List<ReviewItem>?>(null) }
    var topic by remember { mutableStateOf("All") }
    LaunchedEffect(articles, bookmarks, attempts) {
        queue = withContext(Dispatchers.Default) { ReviewPolicy.queue(articles, bookmarks, attempts) }
    }
    val topics = listOf("All") + queue.orEmpty().map { it.relevance.subject }.distinct().sorted()
    val selected = topic.takeIf { it in topics } ?: "All"
    val visible = queue.orEmpty().filter { selected == "All" || it.relevance.subject == selected }
    LazyColumn(
        Modifier.fillMaxSize().imePadding(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 112.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Review saved topics", style = MaterialTheme.typography.titleLarge)
            Text("Never-practised topics first, then oldest practice. Notes stay on this device; recall prompts are original, not official PYQs.",
                style = MaterialTheme.typography.bodySmall)
        }
        if (queue == null) item { CircularProgressIndicator() }
        else if (visible.isEmpty()) item { Text("Save an article to start reviewing.") }
        if (topics.size > 1) item {
            var expanded by remember { mutableStateOf(false) }
            Box {
                OutlinedButton(onClick = { expanded = true }) { Text("Topic: " + selected) }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    topics.forEach { label ->
                        DropdownMenuItem(text = { Text(label) }, onClick = { topic = label; expanded = false })
                    }
                }
            }
        }
        items(visible, key = { it.article.url }) { item ->
            ReviewCard(item, onOpenArticle)
        }
    }
}

@Composable
private fun ReviewCard(item: ReviewItem, onOpenArticle: (Article) -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val url = item.article.url
    var expanded by remember(url) { mutableStateOf(false) }
    var note by remember(url) { mutableStateOf("") }
    var loaded by remember(url) { mutableStateOf(false) }
    var saving by remember(url) { mutableStateOf(false) }
    var status by remember(url) { mutableStateOf("") }
    LaunchedEffect(url, expanded) {
        if (expanded && !loaded) {
            val result = runCatching { ReviewNoteStore.read(context, url) }
            if (result.isSuccess) {
                note = result.getOrThrow()
                loaded = true
            } else status = "Could not read the saved note."
        }
    }
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(item.relevance.gsPaper + " · " + item.relevance.subject, style = MaterialTheme.typography.labelMedium)
            Text(item.article.title, style = MaterialTheme.typography.titleMedium)
            Text(item.relevance.syllabusLink, style = MaterialTheme.typography.bodySmall)
            Text(if (item.lastPracticeMillis == null) "No submitted practice yet" else "Previously practised",
                style = MaterialTheme.typography.labelSmall)
            TextButton(onClick = { expanded = !expanded }, modifier = Modifier.heightIn(min = 48.dp)) {
                Text(if (expanded) "Close recall" else "Recall and make a note")
            }
            if (expanded) {
                Text("Without looking at the article, explain the issue, its connection to the syllabus and one implication. Check the original source before relying on any fact.")
                OutlinedTextField(value = note, onValueChange = { note = it.take(ReviewPolicy.MAX_NOTE_CHARS); status = "" },
                    enabled = loaded && !saving, label = { Text("Private study note") },
                    modifier = Modifier.fillMaxWidth(), minLines = 3, maxLines = 6)
                TextButton(enabled = loaded && !saving, onClick = {
                    saving = true
                    val snapshot = note
                    scope.launch {
                        val result = runCatching { ReviewNoteStore.save(context, url, snapshot) }.getOrDefault(false)
                        if (result) note = ReviewPolicy.normalizeNote(snapshot)
                        status = if (result) "Note saved on this device." else "Could not save note."
                        saving = false
                    }
                }, modifier = Modifier.heightIn(min = 48.dp)) { Text("Save note") }
                if (status.isNotEmpty()) Text(status, style = MaterialTheme.typography.bodySmall)
            }
            TextButton(onClick = { onOpenArticle(item.article) }, modifier = Modifier.heightIn(min = 48.dp)) { Text("Open article") }
        }
    }
}
