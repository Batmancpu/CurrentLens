package com.mangoloads.currentlens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/** Identical compact and expanded content for reader selection and Android PROCESS_TEXT. */
@Composable
internal fun DefinitionPopoverContent(
    word: String,
    definition: Definition?,
    loading: Boolean,
    expanded: Boolean,
    scrollAtTop: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onCopy: () -> Unit,
    onClose: () -> Unit,
    closeLabel: String,
    modifier: Modifier = Modifier
) {
    val status = DictionaryOverlayPolicy.status(loading, definition)
    val hasEntry = status == DefinitionOverlayStatus.AVAILABLE
    val hasCopyableContent = definition?.let {
        DictionaryMeaningPolicy.hasCopyableContent(it.meanings, it.synonyms)
    } == true

    Column(modifier, verticalArrangement = Arrangement.spacedBy(if (expanded) 8.dp else 4.dp)) {
        if (hasEntry) {
            DefinitionExpansionHandle(
                expanded = expanded, scrollAtTop = scrollAtTop,
                onExpandedChange = onExpandedChange
            )
        }
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                word,
                modifier = Modifier.weight(1f),
                style = if (expanded) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                softWrap = true,
                overflow = TextOverflow.Clip,
                lineHeight = 20.sp
            )
            if (DictionaryOverlayPolicy.shouldShowCopy(expanded, hasCopyableContent)) {
                IconButton(onClick = onCopy, modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Outlined.ContentCopy, contentDescription = "Copy definition",
                        modifier = Modifier.size(19.dp))
                }
            }
            if (hasEntry) {
                IconButton(onClick = { onExpandedChange(!expanded) }, modifier = Modifier.size(48.dp)) {
                    Icon(
                        if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                        contentDescription = if (expanded) "Show less" else "Show more",
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            IconButton(onClick = onClose, modifier = Modifier.size(48.dp)) {
                Icon(Icons.Outlined.Close, contentDescription = closeLabel, modifier = Modifier.size(20.dp))
            }
        }

        when (status) {
            DefinitionOverlayStatus.LOADING -> Box(
                Modifier.fillMaxWidth().height(26.dp), contentAlignment = Alignment.CenterStart
            ) {
                CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
            }
            DefinitionOverlayStatus.LOOKUP_FAILED -> Text(
                "The offline dictionary lookup could not be completed.",
                style = MaterialTheme.typography.bodyMedium
            )
            DefinitionOverlayStatus.DICTIONARY_MISSING -> Text(
                "The offline dictionary is not bundled in this build.",
                style = MaterialTheme.typography.bodyMedium
            )
            DefinitionOverlayStatus.NO_ENTRY -> Text(
                "No entry found in the offline English dictionary.",
                style = MaterialTheme.typography.bodyMedium
            )
            DefinitionOverlayStatus.AVAILABLE -> {
                val current = requireNotNull(definition)
                current.meanings.take(
                    DictionaryOverlayPolicy.visibleMeanings(current.meanings.size, expanded)
                ).forEach { rawMeaning ->
                    val meaning = DictionaryMeaningPolicy.present(rawMeaning)
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        if (expanded) meaning.partOfSpeech?.let {
                            Text(it.uppercase(java.util.Locale.ROOT),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary)
                        }
                        Text(
                            meaning.definition,
                            style = if (expanded) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium,
                            lineHeight = if (expanded) 24.sp else 18.sp,
                            maxLines = if (expanded) Int.MAX_VALUE else DictionaryOverlayPolicy.COLLAPSED_MEANING_MAX_LINES,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (DictionaryOverlayPolicy.shouldShowRelatedWords(expanded, current.synonyms.size)) {
                    Text("RELATED WORDS", style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary)
                    Text(
                        current.synonyms.take(
                            DictionaryOverlayPolicy.visibleSynonyms(current.synonyms.size, expanded)
                        ).joinToString(" · "),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        if (DictionaryOverlayPolicy.shouldShowAttribution(
                expanded, definition?.dictionaryAvailable == true
            )
        ) {
            Text("Open English WordNet 2025+ · CC BY 4.0",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary)
        }
    }
}
