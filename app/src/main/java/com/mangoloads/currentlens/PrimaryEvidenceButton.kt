package com.mangoloads.currentlens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
internal fun PrimaryEvidenceButton(article: Article) {
    val evidence = remember(article.url, article.source) { PrimaryEvidencePolicy.forArticle(article) } ?: return
    var show by remember(article.url) { mutableStateOf(false) }
    val context = LocalContext.current
    TextButton(onClick = { show = true }, modifier = Modifier.heightIn(min = 48.dp)) {
        Text("Primary-source evidence")
    }
    if (show) AlertDialog(
        onDismissRequest = { show = false },
        title = { Text("Official document") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(evidence.authority, style = MaterialTheme.typography.titleSmall)
                Text("Published ${evidence.documentDate} · checked ${evidence.checkedOn}", style = MaterialTheme.typography.labelSmall)
                Text(evidence.supportedClaim)
                Text("Only this claim was checked against the official source; the rest of the article is not independently verified.",
                    style = MaterialTheme.typography.bodySmall)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                runCatching { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(evidence.url))) }
            }) { Text("Open official document") }
        },
        dismissButton = { TextButton(onClick = { show = false }) { Text("Close") } }
    )
}
