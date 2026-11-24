package com.mangoloads.currentlens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
internal fun PortableBackupScreen(canRestore: Boolean, onImported: () -> Unit) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var busy by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    var preview by remember { mutableStateOf<PortableBackup?>(null) }
    val exportLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CreateDocument("application/json")) { uri ->
        if (uri != null) {
            busy = true
            message = ""
            scope.launch {
                val result = runCatching {
                    val text = LocalStore.exportPortableBackup(context)
                    withContext(Dispatchers.IO) {
                        val stream = context.contentResolver.openOutputStream(uri, "wt") ?: error("Cannot open document")
                        stream.use { it.write(text.toByteArray(Charsets.UTF_8)) }
                    }
                }
                message = if (result.isSuccess) "Backup written to your selected file."
                    else "Backup could not be written or validated. Existing app data was not changed."
                busy = false
            }
        }
    }
    val importLauncher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            busy = true
            message = ""
            scope.launch {
                val result = runCatching {
                    withContext(Dispatchers.IO) {
                        val stream = context.contentResolver.openInputStream(uri) ?: error("Cannot read document")
                        val text = stream.use { NetworkLimits.readUtf8Limited(it, PortableBackupPolicy.MAX_BYTES) }
                        PortableBackupCodec.decode(text)
                    }
                }
                preview = result.getOrNull()
                if (preview == null) message = "Unsupported, malformed or oversized backup. Nothing was imported."
                busy = false
            }
        }
    }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Your data, your file", style = MaterialTheme.typography.headlineSmall)
        Text("Export Saved article metadata, private review notes, reading and submitted quiz history, and reader resume positions. Article bodies, crash reports and device information are excluded.")
        Text("The JSON file is not encrypted. Choose a private destination and do not share it unless you intend to disclose your study activity.",
            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Button(enabled = !busy && preview == null, onClick = { exportLauncher.launch("CurrentLens-backup.json") },
            modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Export backup") }
        OutlinedButton(enabled = !busy && preview == null && canRestore, onClick = {
            importLauncher.launch(arrayOf("application/json", "text/plain", "application/octet-stream"))
        }, modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp)) { Text("Import backup") }
        if (!canRestore) Text("Import is available after the current feed refresh finishes.", style = MaterialTheme.typography.bodySmall)
        Text("Import merges rather than replaces. Existing records and notes win conflicts. New history is added only within the app's retention limits.",
            style = MaterialTheme.typography.bodySmall)
        if (busy) CircularProgressIndicator()
        if (message.isNotBlank()) Text(message, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(110.dp))
    }
    val selected = preview
    if (selected != null) AlertDialog(
        onDismissRequest = { preview = null },
        title = { Text("Merge this backup?") },
        text = { Text("File contains ${selected.bookmarks.size} bookmarks, ${selected.notes.size} notes, ${selected.sessions.size} reading sessions and ${selected.attempts.size} quiz attempts. Existing local data is preserved; duplicates and excess history may be skipped.") },
        confirmButton = {
            TextButton(enabled = canRestore && !busy, onClick = {
                preview = null
                busy = true
                scope.launch {
                    withContext(NonCancellable) {
                        val result = runCatching { LocalStore.importPortableBackup(context, selected) }.getOrNull()
                        if (result?.mainSaved == true) onImported()
                        message = when {
                            result == null || !result.mainSaved -> "Could not confirm the import. Existing records were not intentionally removed. You can retry."
                            !result.notesSaved -> "Main records merged, but note storage could not be confirmed. Retry the same file to merge missing notes."
                            else -> "Merge complete: ${result.added} records added, ${result.skipped} duplicates or excess records skipped."
                        }
                        busy = false
                    }
                }
            }) { Text("Merge backup") }
        },
        dismissButton = { TextButton(onClick = { preview = null }) { Text("Cancel") } }
    )
}
