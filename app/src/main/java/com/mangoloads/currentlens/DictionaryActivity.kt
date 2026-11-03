package com.mangoloads.currentlens

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

data class Definition(val word: String, val phonetic: String, val meanings: List<String>)

object DictionaryRepository {
    suspend fun lookup(context: Context, word: String): Definition? = withContext(Dispatchers.IO) {
        val key = word.trim().lowercase()
        if (key.isBlank() || key.length > 60) return@withContext null
        val prefs = context.getSharedPreferences("currentlens_dictionary", Context.MODE_PRIVATE)
        val cached = prefs.getString(key, null)
        if (cached != null) return@withContext parse(cached, word)
        try {
            val encoded = URLEncoder.encode(key, "UTF-8")
            val connection = (URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + encoded).openConnection() as HttpURLConnection).apply {
                connectTimeout = 5000
                readTimeout = 7000
                setRequestProperty("User-Agent", "CurrentLens/0.1")
            }
            val json = connection.inputStream.bufferedReader().use { it.readText() }
            connection.disconnect()
            prefs.edit().putString(key, json).apply()
            parse(json, word)
        } catch (_: Exception) { null }
    }

    private fun parse(raw: String, displayWord: String): Definition? {
        return try {
        val entry = JSONArray(raw).optJSONObject(0) ?: return null
        val phonetics = entry.optJSONArray("phonetics")
        var phonetic = ""
        if (phonetics != null) {
            for (i in 0 until phonetics.length()) {
                val candidate = phonetics.optJSONObject(i)?.optString("text").orEmpty()
                if (candidate.isNotBlank()) { phonetic = candidate; break }
            }
        }
        val meanings = mutableListOf<String>()
        val groups = entry.optJSONArray("meanings")
        if (groups != null) {
            for (i in 0 until groups.length()) {
                val group = groups.optJSONObject(i) ?: continue
                val part = group.optString("partOfSpeech")
                val definitions = group.optJSONArray("definitions") ?: continue
                for (j in 0 until minOf(definitions.length(), 3)) {
                    val text = definitions.optJSONObject(j)?.optString("definition").orEmpty()
                    if (text.isNotBlank()) meanings.add((if (part.isNotBlank()) part + " · " else "") + text)
                }
            }
        }
        Definition(displayWord, phonetic, meanings.take(5))
        } catch (_: Exception) { null }
    }
}

class DictionaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val word = intent.getCharSequenceExtra("android.intent.extra.PROCESS_TEXT")
            ?.toString().orEmpty().trim().split(Regex(" +")).firstOrNull().orEmpty().trim { !it.isLetter() }
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.BOTTOM)
        setContent { MaterialTheme { DictionaryPopup(word.ifBlank { "Word" }) { finish() } } }
    }
}

@Composable
private fun DictionaryPopup(word: String, onClose: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    var result by remember(word) { mutableStateOf<Definition?>(null) }
    var loading by remember(word) { mutableStateOf(true) }
    LaunchedEffect(word) {
        result = DictionaryRepository.lookup(context, word)
        loading = false
    }
    Surface(
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        shape = RoundedCornerShape(26.dp),
        tonalElevation = 8.dp,
        shadowElevation = 18.dp
    ) {
        Column(Modifier.padding(22.dp).verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row {
                Column(Modifier.weight(1f)) {
                    Text(word, style = MaterialTheme.typography.headlineMedium)
                    if (result?.phonetic?.isNotBlank() == true) Text(result?.phonetic.orEmpty(), color = MaterialTheme.colorScheme.secondary)
                }
                TextButton(onClick = onClose) { Text("Close") }
            }
            when {
                loading -> CircularProgressIndicator()
                result == null || result?.meanings.isNullOrEmpty() -> Text("No definition available. Check your connection or try the base form of the word.")
                else -> result!!.meanings.forEachIndexed { index, meaning -> Text((index + 1).toString() + ". " + meaning, style = MaterialTheme.typography.bodyLarge) }
            }
            Spacer(Modifier.height(4.dp))
            Text("Free Dictionary API · Definition cached on this device", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        }
    }
}
