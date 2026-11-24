package com.mangoloads.currentlens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
internal fun FeedHealthPanel(reports: List<FeedHealthReport>) {
    if (reports.isEmpty()) return
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val available = reports.count { !it.status.isFailure }
    Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("$available of ${reports.size} feeds responded", style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
        TextButton(onClick = { expanded = true }, modifier = Modifier.heightIn(min = 48.dp)) { Text("Feed status") }
    }
    if (expanded) AlertDialog(
        onDismissRequest = { expanded = false },
        title = { Text("Latest refresh") },
        text = {
            Column(Modifier.heightIn(max = 360.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("A successful feed may still contain no syllabus-eligible stories. Counts are before editorial filtering and deduplication.",
                    style = MaterialTheme.typography.bodySmall)
                reports.forEach { report ->
                    Column {
                        Text("${report.publisher} · ${report.section}", style = MaterialTheme.typography.titleSmall)
                        Text("${report.status.label} · ${report.datedCount}/${report.parsedCount} dated",
                            style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = { expanded = false }) { Text("Close") } },
        dismissButton = {
            TextButton(onClick = {
                context.getSystemService(ClipboardManager::class.java)?.setPrimaryClip(
                    ClipData.newPlainText("Feed status", FeedHealthPolicy.diagnosticText(reports))
                )
            }) { Text("Copy status") }
        }
    )
}
