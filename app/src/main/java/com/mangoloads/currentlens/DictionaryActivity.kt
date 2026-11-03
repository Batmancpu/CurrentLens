package com.mangoloads.currentlens

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

data class Definition(
    val word: String,
    val phonetic: String,
    val meanings: List<String>,
    val synonyms: List<String> = emptyList(),
    val dictionaryAvailable: Boolean = true
)

/**
 * Offline-first lookup against the bundled Open English WordNet 2025+ SQLite database.
 * No dictionary network request is made; definitions remain available in airplane mode.
 */
object DictionaryRepository {
    private const val ASSET_NAME = "wordnet.db"
    private const val DATABASE_NAME = "currentlens_wordnet_2025_plus.db"
    private val databaseLock = Any()
    private val lookupCache = DictionaryDefinitionCache()

    suspend fun lookup(context: Context, word: String): Definition? = withContext(Dispatchers.IO) {
        val normalized = DictionaryWordPolicy.singleSelectedWord(word) ?: return@withContext null
        lookupCache.get(normalized)?.let { return@withContext it }
        val database = openDatabase(context.applicationContext)
            ?: return@withContext Definition(word.trim(), "", emptyList(), dictionaryAvailable = false)
        try {
            val candidates = buildList {
                addAll(DictionaryWordPolicy.candidates(normalized))
                addAll(exceptionForms(database, normalized))
            }.distinct().take(10)

            for (candidate in candidates) {
                val result = queryEntries(database, candidate)
                if (result.meanings.isNotEmpty()) {
                    val definition = Definition(
                        word = normalized,
                        phonetic = "",
                        meanings = result.meanings,
                        synonyms = result.synonyms
                    )
                    lookupCache.put(normalized, definition)
                    return@withContext definition
                }
            }
            // A genuine dictionary miss is distinct from a database/open/query failure.
            // Cache it so repeatedly selecting an unknown token does not reopen SQLite.
            Definition(word = normalized, phonetic = "", meanings = emptyList()).also {
                lookupCache.put(normalized, it)
            }
        } catch (_: Exception) {
            null
        } finally {
            database.close()
        }
    }

    private fun openDatabase(context: Context): SQLiteDatabase? = try {
        val destination = File(context.noBackupFilesDir, DATABASE_NAME)
        if (!destination.isFile || destination.length() < 1_000_000L) {
            synchronized(databaseLock) {
                if (!destination.isFile || destination.length() < 1_000_000L) {
                    val temporary = File(destination.parentFile, "$DATABASE_NAME.tmp")
                    temporary.delete()
                    context.assets.open(ASSET_NAME).use { input ->
                        FileOutputStream(temporary).use { output -> input.copyTo(output) }
                    }
                    if (destination.exists()) destination.delete()
                    check(temporary.renameTo(destination)) { "Could not install offline dictionary" }
                }
            }
        }
        SQLiteDatabase.openDatabase(destination.absolutePath, null, SQLiteDatabase.OPEN_READONLY)
    } catch (_: Exception) {
        null
    }

    private data class LookupRows(val meanings: List<String>, val synonyms: List<String>)

    private fun queryEntries(database: SQLiteDatabase, word: String): LookupRows {
        val meanings = mutableListOf<String>()
        val synonyms = linkedSetOf<String>()
        database.rawQuery(
            "SELECT pos, definition, synonyms FROM entries WHERE word = ? " +
                "ORDER BY CASE pos WHEN 'noun' THEN 0 WHEN 'verb' THEN 1 WHEN 'adjective' THEN 2 ELSE 3 END LIMIT 6",
            arrayOf(word)
        ).use { cursor ->
            while (cursor.moveToNext()) {
                val partOfSpeech = cursor.getString(0).orEmpty()
                val definition = cursor.getString(1).orEmpty()
                val related = cursor.getString(2).orEmpty()
                if (definition.isNotBlank()) {
                    meanings.add(posLabel(partOfSpeech) + " · " + definition)
                }
                related.split(',').map { it.trim() }.filter { it.isNotBlank() }
                    .forEach { synonyms.add(it) }
            }
        }
        return LookupRows(meanings.distinct().take(5), synonyms.take(10))
    }

    private fun exceptionForms(database: SQLiteDatabase, word: String): List<String> {
        val bases = linkedSetOf<String>()
        database.rawQuery(
            "SELECT base_forms FROM exceptions WHERE inflected = ? LIMIT 4",
            arrayOf(word)
        ).use { cursor ->
            while (cursor.moveToNext()) {
                cursor.getString(0).orEmpty().split(' ').filter { it.isNotBlank() }.forEach { bases.add(it) }
            }
        }
        return bases.toList()
    }

    private fun posLabel(value: String): String = when (value.lowercase(Locale.ROOT)) {
        "noun" -> "noun"
        "verb" -> "verb"
        "adjective" -> "adjective"
        "adverb" -> "adverb"
        else -> value
    }
}

class DictionaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val word = DictionaryWordPolicy.singleSelectedWord(
            intent.getCharSequenceExtra("android.intent.extra.PROCESS_TEXT")?.toString().orEmpty()
        ).orEmpty()
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.BOTTOM)
        setContent {
            MaterialTheme {
                DictionaryPopup(word.ifBlank { "Select one word" }) { finish() }
            }
        }
    }
}

@Composable
private fun DictionaryPopup(word: String, onClose: () -> Unit) {
    val context = LocalContext.current
    var result by remember(word) { mutableStateOf<Definition?>(null) }
    var loading by remember(word) { mutableStateOf(true) }
    LaunchedEffect(word) {
        result = DictionaryRepository.lookup(context, word)
        loading = false
    }
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() +
            scaleIn(
                initialScale = 0.92f,
                transformOrigin = TransformOrigin(0.5f, 1f),
                animationSpec = spring(dampingRatio = 0.78f, stiffness = 520f)
            ) +
            slideInVertically(
                initialOffsetY = { it / 7 },
                animationSpec = spring(dampingRatio = 0.78f, stiffness = 520f)
            )
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            shape = RoundedCornerShape(26.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
            tonalElevation = 8.dp,
            shadowElevation = 18.dp
        ) {
            Column(
                Modifier.padding(20.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(word, style = MaterialTheme.typography.headlineSmall)
                        Text(
                            "OFFLINE DICTIONARY",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    TextButton(onClick = onClose) { Text("Close") }
                }
                val definition = result
                when {
                    loading -> CircularProgressIndicator()
                    definition == null ->
                        Text("The offline dictionary lookup could not be completed.")
                    !definition.dictionaryAvailable ->
                        Text("The offline dictionary is not bundled in this build.")
                    definition.meanings.isEmpty() ->
                        Text("No entry found in the offline English dictionary.")
                    else -> {
                        definition.meanings.forEach { meaning ->
                            Text(meaning, style = MaterialTheme.typography.bodyLarge)
                        }
                        if (definition.synonyms.isNotEmpty()) {
                            Spacer(Modifier.height(2.dp))
                            Text("RELATED WORDS", style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary)
                            Text(definition.synonyms.joinToString(" · "), style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
                Text(
                    "Open English WordNet 2025+ · CC BY 4.0 · Princeton WordNet attribution",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
