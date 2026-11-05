package com.mangoloads.currentlens

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.HapticFeedbackConstants
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.Locale

data class Definition(
    val word: String,
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
    private const val DATABASE_VERSION = 2027
    private val databaseLock = Any()
    private val lookupCache = DictionaryDefinitionCache()
    private val databaseSession = DictionaryDatabaseSession<SQLiteDatabase> { it.isOpen }

    suspend fun lookup(context: Context, word: String): Definition? = withContext(Dispatchers.IO) {
        val normalized = DictionaryWordPolicy.singleSelectedWord(word) ?: return@withContext null
        lookupCache.get(normalized)?.let { return@withContext it }
        // Reuse the read-only SQLite connection across word selections. Opening a 50 MB
        // database for every uncached word adds avoidable latency to the selection experience.
        val database = databaseSession.getOrOpen { openDatabase(context.applicationContext) }
            ?: return@withContext Definition(word.trim(), emptyList(), dictionaryAvailable = false)
        try {
            val candidates = DictionaryWordPolicy.lookupCandidates(
                normalized,
                exceptionForms(database, normalized)
            )

            for (candidate in candidates) {
                val result = queryEntries(database, candidate)
                if (result.meanings.isNotEmpty()) {
                    val definition = Definition(
                        word = normalized,
                        meanings = result.meanings,
                        synonyms = result.synonyms
                    )
                    lookupCache.put(normalized, definition)
                    return@withContext definition
                }
            }
            Definition(word = normalized, meanings = emptyList()).also {
                lookupCache.put(normalized, it)
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun openDatabase(context: Context): SQLiteDatabase? = try {
        val destination = File(context.noBackupFilesDir, DATABASE_NAME)
        fun needsInstall(): Boolean {
            val installedVersion = if (destination.isFile && destination.length() >= 1_000_000L) {
                readDatabaseVersion(destination)
            } else {
                null
            }
            return DictionaryDatabaseInstallPolicy.shouldInstall(
                fileExists = destination.isFile,
                fileLength = destination.length(),
                installedVersion = installedVersion,
                expectedVersion = DATABASE_VERSION
            )
        }

        if (needsInstall()) {
            synchronized(databaseLock) {
                if (needsInstall()) {
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
        val database = SQLiteDatabase.openDatabase(
            destination.absolutePath,
            null,
            SQLiteDatabase.OPEN_READONLY
        )
        if (readDatabaseVersion(database) != DATABASE_VERSION) {
            database.close()
            throw IllegalStateException("Installed offline dictionary has an unsupported schema")
        }
        database
    } catch (_: Exception) {
        null
    }

    private fun readDatabaseVersion(file: File): Int? {
        val database = try {
            SQLiteDatabase.openDatabase(file.absolutePath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (_: Exception) {
            return null
        }
        return try {
            readDatabaseVersion(database)
        } finally {
            database.close()
        }
    }

    private fun readDatabaseVersion(database: SQLiteDatabase): Int? =
        database.rawQuery("PRAGMA user_version", null).use { cursor ->
            if (cursor.moveToFirst()) cursor.getInt(0) else null
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
            "SELECT base_forms FROM exceptions WHERE inflected = ? " +
                "ORDER BY CASE pos WHEN 'noun' THEN 0 WHEN 'verb' THEN 1 WHEN 'adjective' THEN 2 ELSE 3 END LIMIT 4",
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
        window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.attributes = window?.attributes?.apply { dimAmount = 0.08f }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Blur-behind samples the whole app window, unlike a blur confined to this popup.
            window?.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            window?.attributes = window?.attributes?.apply {
                blurBehindRadius = (resources.displayMetrics.density * 24f).toInt()
            }
        }
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.decorView?.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
        window?.setGravity(Gravity.BOTTOM)
        setContent {
            val dark = androidx.compose.foundation.isSystemInDarkTheme()
            MaterialTheme(
                colorScheme = if (dark) {
                    androidx.compose.material3.darkColorScheme(
                        primary = androidx.compose.ui.graphics.Color(0xFFB9CAFF),
                        onPrimary = androidx.compose.ui.graphics.Color(0xFF15233B),
                        background = androidx.compose.ui.graphics.Color(0xFF0D1118),
                        onBackground = androidx.compose.ui.graphics.Color(0xFFF1F4F8),
                        surface = androidx.compose.ui.graphics.Color(0xFF1B2636),
                        onSurface = androidx.compose.ui.graphics.Color(0xFFF1F4F8),
                        surfaceVariant = androidx.compose.ui.graphics.Color(0xFF252D3A),
                        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFFC2CBD7)
                    )
                } else {
                    androidx.compose.material3.lightColorScheme(
                        primary = androidx.compose.ui.graphics.Color(0xFF315F9F),
                        onPrimary = androidx.compose.ui.graphics.Color.White,
                        background = androidx.compose.ui.graphics.Color(0xFFF5F7FA),
                        onBackground = androidx.compose.ui.graphics.Color(0xFF18212D),
                        surface = androidx.compose.ui.graphics.Color(0xFFF7F9FC),
                        onSurface = androidx.compose.ui.graphics.Color(0xFF1A2431),
                        surfaceVariant = androidx.compose.ui.graphics.Color(0xFFE9EEF5),
                        onSurfaceVariant = androidx.compose.ui.graphics.Color(0xFF526176)
                    )
                }
            ) {
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
    var expanded by remember(word) { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    LaunchedEffect(word) {
        scrollState.scrollTo(0)
        result = DictionaryRepository.lookup(context, word)
        loading = false
    }
    val definition = result
    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + scaleIn(
            initialScale = 0.92f,
            transformOrigin = TransformOrigin(0.5f, 1f),
            animationSpec = spring(dampingRatio = 0.78f, stiffness = 520f)
        ) + slideInVertically(
            initialOffsetY = { it / 7 },
            animationSpec = spring(dampingRatio = 0.78f, stiffness = 520f)
        )
    ) {
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.widthIn(max = DictionaryOverlayPolicy.MAX_WIDTH_DP.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .animateContentSize(spring(dampingRatio = 0.82f, stiffness = 480f)),
                shape = RoundedCornerShape(26.dp),
                color = MaterialTheme.colorScheme.surface.copy(
                    alpha = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) 0.86f else 0.94f
                ),
                contentColor = MaterialTheme.colorScheme.onSurface,
                tonalElevation = 0.dp,
                shadowElevation = 18.dp
            ) {
                DefinitionPopoverContent(
                    word = word,
                    definition = definition,
                    loading = loading,
                    expanded = expanded,
                    scrollAtTop = scrollState.value == 0,
                    onExpandedChange = { expanded = it },
                    onCopy = {
                        definition?.let { current ->
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE)
                                as? android.content.ClipboardManager
                            clipboard?.setPrimaryClip(
                                android.content.ClipData.newPlainText(
                                    "Definition of $word",
                                    DictionaryMeaningPolicy.copyText(word, current.meanings, current.synonyms)
                                )
                            )
                        }
                    },
                    onClose = onClose,
                    closeLabel = "Close",
                    modifier = Modifier
                        .heightIn(max = if (expanded) DictionaryOverlayPolicy.EXPANDED_MAX_HEIGHT_DP.dp else DictionaryOverlayPolicy.COMPACT_MAX_HEIGHT_DP.dp)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .verticalScroll(scrollState)
                )
            }
        }
    }
}
