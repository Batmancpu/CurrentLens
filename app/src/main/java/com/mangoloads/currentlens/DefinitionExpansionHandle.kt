package com.mangoloads.currentlens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

/** Small grab handle; the adjacent More/Less button remains the accessible alternative. */
@Composable
internal fun DefinitionExpansionHandle(
    expanded: Boolean,
    scrollAtTop: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    val density = LocalDensity.current
    Box(
        modifier = Modifier.fillMaxWidth().height(12.dp).pointerInput(expanded, scrollAtTop) {
            var dragDistance = 0f
            val thresholdPx = with(density) { 32.dp.toPx() }
            detectVerticalDragGestures(
                onDragStart = { _ -> dragDistance = 0f },
                onVerticalDrag = { change, amount ->
                    dragDistance += amount
                    change.consume()
                },
                onDragEnd = {
                    when {
                        !expanded && DictionaryOverlayPolicy.shouldExpand(dragDistance, thresholdPx) ->
                            onExpandedChange(true)
                        expanded && DictionaryOverlayPolicy.shouldCollapse(
                            dragDistance, thresholdPx, scrollAtTop
                        ) -> onExpandedChange(false)
                    }
                }
            )
        },
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier.width(36.dp).height(4.dp)
                .clip(RoundedCornerShape(percent = 50))
                .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.38f))
        ) {}
    }
}
