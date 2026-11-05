package com.mangoloads.currentlens

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.styropyr0.prismal.sources.PrismalGlassLayer
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/*
 * The 8x8 readback, cadence, luminance weighting and quantisation adapt the
 * PrismalAGSL v1.0.4 adaptive-luminance helper.
 * Copyright (c) 2026 Saurav Sajeev; MIT License.
 * Full notice: app/src/main/assets/licenses/PRISMAL_AGSL_MIT_LICENSE.txt
 */

@Stable
internal class GlassLuminanceState(initialLuminance: Float) {
    var luminance by mutableFloatStateOf(initialLuminance)
        internal set
    var contentColor by mutableStateOf(
        if (initialLuminance > 0.5f) Color.Black else Color.White
    )
        internal set
}

/**
 * Samples a full-width bottom band rather than averaging the entire page.
 *
 * The source is the same root PrismalGlassLayer used by navigation and Search.
 * Because the source layer's measured height changes with imePadding(), the band
 * automatically follows the visible content edge above the keyboard. One 8x8
 * readback is shared by both glass surfaces; it does not create a second sampler.
 */
@Composable
internal fun rememberBottomBandGlassLuminance(
    enabled: Boolean,
    source: PrismalGlassLayer,
    isLightTheme: Boolean,
    bandHeight: Dp = 104.dp,
    initialLuminance: Float = 0.5f
): GlassLuminanceState {
    val initial = initialLuminance.coerceIn(0f, 1f)
    val state = remember {
        GlassLuminanceState(initial).also {
            it.contentColor = if (isLightTheme) Color.Black else Color.White
        }
    }
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val probeLayer = rememberGraphicsLayer()

    LaunchedEffect(enabled, source, density, layoutDirection, isLightTheme, bandHeight, initial) {
        if (!enabled) {
            state.luminance = initial
            state.contentColor = if (isLightTheme) Color.Black else Color.White
            return@LaunchedEffect
        }

        val probeSizePx = 8
        val pixelBuffer = IntArray(probeSizePx * probeSizePx)
        val probeSize = IntSize(probeSizePx, probeSizePx)
        val requestedBandHeightPx = with(density) { bandHeight.toPx().roundToInt() }
        val sampleIntervalMs = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) 600L else 1500L

        while (isActive) {
            try {
                val sourceLayer = source.graphicsLayer
                val sourceSize = sourceLayer.size
                val region = GlassLuminanceRegionPolicy.bottomBand(
                    sourceWidthPx = sourceSize.width,
                    sourceHeightPx = sourceSize.height,
                    requestedHeightPx = requestedBandHeightPx
                )
                if (region != null) {
                    probeLayer.record(density, layoutDirection, probeSize) {
                        scale(
                            probeSizePx.toFloat() / region.widthPx,
                            probeSizePx.toFloat() / region.heightPx,
                            pivot = Offset.Zero
                        ) {
                            translate(
                                left = -region.leftPx.toFloat(),
                                top = -region.topPx.toFloat()
                            ) {
                                drawLayer(sourceLayer)
                            }
                        }
                    }
                    val probeBitmap = probeLayer.toImageBitmap()
                    probeBitmap.readPixels(
                        buffer = pixelBuffer,
                        startX = 0,
                        startY = 0,
                        width = probeSizePx,
                        height = probeSizePx
                    )

                    val averageLuminance = averageLuminanceFromArgb(pixelBuffer)
                    val quantized = (averageLuminance / 0.05f).roundToInt() * 0.05f
                    if (abs(quantized - state.luminance) >= 0.04f) {
                        state.luminance = quantized
                        state.contentColor = if (quantized > 0.5f) Color.Black else Color.White
                    }
                }
            } catch (_: Exception) {
                // A not-yet-recorded layer can be empty during the first layout frame.
            }
            delay(sampleIntervalMs)
        }
    }

    return state
}

private fun averageLuminanceFromArgb(pixels: IntArray): Float {
    if (pixels.isEmpty()) return 0.5f
    val sum = pixels.sumOf { argb ->
        val red = (argb shr 16 and 0xFF) / 255.0
        val green = (argb shr 8 and 0xFF) / 255.0
        val blue = (argb and 0xFF) / 255.0
        0.2126 * red + 0.7152 * green + 0.0722 * blue
    }
    return (sum / pixels.size).toFloat().coerceIn(0f, 1f)
}
