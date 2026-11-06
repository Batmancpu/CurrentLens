package com.mangoloads.currentlens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.styropyr0.prismal.PrismalBackdrop
import com.styropyr0.prismal.depth.PrismalDepthInset
import com.styropyr0.prismal.depth.PrismalDepthShadow
import com.styropyr0.prismal.drawPrismalGlass
import com.styropyr0.prismal.effects.PrismalAdaptiveTuning
import com.styropyr0.prismal.effects.applyPrismalGlassEffects
import com.styropyr0.prismal.shapes.PrismalCapsule
import com.styropyr0.prismal.shapes.PrismalRoundedRectangle
import com.styropyr0.prismal.sources.prismalGlassLayer
import com.styropyr0.prismal.sources.rememberPrismalGlassLayer
import com.styropyr0.prismal.sources.rememberPrismalMergedSource
import com.styropyr0.prismal.specular.PrismalSpecular

/**
 * Stability-only rendering isolation menu.
 *
 * Tests the same Prismal primitives used by the full app in increasing complexity:
 * 1) one captured GraphicsLayer, 2) one complete glass draw, 3) two captured layers
 * plus a merged source, and finally the complete app. The production entry path and
 * Prismal automatic pipeline are not changed.
 */
@Composable
internal fun StabilityLaunchProbe(onLaunchFullApp: () -> Unit) {
    val context = LocalContext.current
    var selectedStage by remember { mutableIntStateOf(0) }

    LaunchedEffect(selectedStage) {
        val stageName = when (selectedStage) {
            0 -> "Stability probe menu"
            1 -> "Probe stage 1 — root GraphicsLayer capture"
            2 -> "Probe stage 2 — single full Liquid Glass surface"
            3 -> "Probe stage 3 — hidden tab layer and merged backdrop"
            else -> return@LaunchedEffect
        }
        LaunchCrashDiagnostics.recordStage(context, stageName + " composition started")
        repeat(3) { withFrameNanos { } }
        LaunchCrashDiagnostics.markHealthy(context, stageName + " frames completed")
    }

    when (selectedStage) {
        1 -> RootBackdropCaptureProbe {
            LaunchCrashDiagnostics.recordStage(context, "Returning to Stability probe menu after root capture test")
            selectedStage = 0
        }
        2 -> SingleLiquidGlassProbe {
            LaunchCrashDiagnostics.recordStage(context, "Returning to Stability probe menu after single glass test")
            selectedStage = 0
        }
        3 -> MergedTabBackdropProbe {
            LaunchCrashDiagnostics.recordStage(context, "Returning to Stability probe menu after merged backdrop test")
            selectedStage = 0
        }
        else -> StabilityProbeMenu(
            onRootCapture = {
                LaunchCrashDiagnostics.recordStage(context, "Stability probe stage 1 requested — root GraphicsLayer capture")
                selectedStage = 1
            },
            onSingleGlass = {
                LaunchCrashDiagnostics.recordStage(context, "Stability probe stage 2 requested — single full Liquid Glass surface")
                selectedStage = 2
            },
            onMergedBackdrop = {
                LaunchCrashDiagnostics.recordStage(context, "Stability probe stage 3 requested — hidden tab layer and merged backdrop")
                selectedStage = 3
            },
            onLaunchFullApp = {
                LaunchCrashDiagnostics.recordStage(context, "Full brrrNews composition requested from stability probe")
                onLaunchFullApp()
            }
        )
    }
}

@Composable
private fun StabilityProbeMenu(
    onRootCapture: () -> Unit,
    onSingleGlass: () -> Unit,
    onMergedBackdrop: () -> Unit,
    onLaunchFullApp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 28.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "brrrNews",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Startup isolation check",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(12.dp))
        Text(
            text = "The basic Compose screen has launched. Run the rendering checks below in order. Each check is isolated and records its stage locally before rendering.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(18.dp))
        ProbeActionButton("1 · Test backdrop capture only", onRootCapture)
        ProbeActionButton("2 · Test one full Liquid Glass surface", onSingleGlass)
        ProbeActionButton("3 · Test merged tab backdrops", onMergedBackdrop)
        Spacer(Modifier.height(10.dp))
        Text(
            text = "After the three checks, launch the complete app. Its original Liquid Glass pipeline and visual effects remain enabled.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(12.dp))
        ProbeActionButton("Open full brrrNews", onLaunchFullApp)
    }
}

@Composable
private fun ProbeActionButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(label)
    }
}

@Composable
private fun RootBackdropCaptureProbe(onBack: () -> Unit) {
    val backdrop = rememberPrismalGlassLayer()
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .prismalGlassLayer(backdrop)
    ) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Stage 1 · Backdrop capture", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text(
                "This screen records one Compose subtree into a single GraphicsLayer. No glass surface, shader, blur or merged source is drawn.",
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(24.dp))
            ProbeActionButton("Back to test menu", onBack)
        }
    }
}

@Composable
private fun SingleLiquidGlassProbe(onBack: () -> Unit) {
    val backdrop = rememberPrismalGlassLayer()
    val density = LocalDensity.current
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .prismalGlassLayer(backdrop)
    ) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Stage 2 · Single Liquid Glass surface", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(14.dp))
            Text(
                "One root capture and one complete Prismal glass draw, using the normal automatic pipeline.",
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.height(24.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .probeLiquidGlass(backdrop, density)
                    .background(Color(0x223D72C7), RoundedCornerShape(24.dp))
                    .padding(22.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Liquid Glass rendering test",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(Modifier.height(24.dp))
            ProbeActionButton("Back to test menu", onBack)
        }
    }
}

@Composable
private fun MergedTabBackdropProbe(onBack: () -> Unit) {
    val pageBackdrop = rememberPrismalGlassLayer()
    val tabBackdrop = rememberPrismalGlassLayer()
    val mergedBackdrop = rememberPrismalMergedSource(pageBackdrop, tabBackdrop)
    val density = LocalDensity.current

    // Match the production bottom bar's second, hidden tab-glyph capture.
    tabBackdrop.readSamplingState()

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = 94.dp)
                .prismalGlassLayer(pageBackdrop)
                .padding(horizontal = 24.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Stage 3 · Merged tab backdrops", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(12.dp))
            Text(
                "This reproduces the production structure: a page GraphicsLayer, a second invisible tab-content GraphicsLayer, a merged source and glass surfaces sampling both.",
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Row(
            Modifier
                .graphicsLayer { alpha = 0f }
                .clearAndSetSemantics {}
                .prismalGlassLayer(tabBackdrop)
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Today")
            Text("Explore")
            Text("Search")
            Text("You")
        }

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 12.dp)
                .height(70.dp)
                .probeLiquidGlass(pageBackdrop, density),
            contentAlignment = Alignment.Center
        ) {
            Text("Base tab bar · page source")
        }

        Box(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 18.dp)
                .fillMaxWidth(0.24f)
                .height(58.dp)
                .probeLiquidGlass(mergedBackdrop, density, capsule = true),
            contentAlignment = Alignment.Center
        ) {
            Text("You", fontWeight = FontWeight.Bold)
        }

        Box(Modifier.align(Alignment.TopCenter).padding(top = 12.dp)) {
            ProbeActionButton("Back to test menu", onBack)
        }
    }
}

private fun Modifier.probeLiquidGlass(
    backdrop: PrismalBackdrop,
    density: Density,
    capsule: Boolean = false
): Modifier = drawPrismalGlass(
    backdrop = backdrop,
    shape = {
        if (capsule) PrismalCapsule() else PrismalRoundedRectangle(24.dp)
    },
    effects = {
        applyPrismalGlassEffects(
            density = density,
            adaptiveLuminance = true,
            luminance = 0.18f,
            blurRadiusPx = with(density) { 13.dp.toPx() },
            refractionHeightPx = with(density) { 16.dp.toPx() },
            refractionAmountPx = with(density) { 20.dp.toPx() },
            depthEffect = true,
            chromaticAberration = 0.012f,
            adaptiveTuning = PrismalAdaptiveTuning.Subtle
        )
    },
    specular = {
        PrismalSpecular.Default.copy(
            width = 0.55.dp,
            blurRadius = 0.35.dp,
            alpha = 0.28f
        )
    },
    depthShadow = {
        PrismalDepthShadow(
            radius = 12.dp,
            offset = DpOffset(0.dp, 2.dp),
            color = Color.Black.copy(alpha = 0.09f),
            alpha = 0.20f
        )
    },
    depthInset = {
        PrismalDepthInset(
            radius = 4.dp,
            offset = DpOffset(0.dp, 1.dp),
            color = Color.Black.copy(alpha = 0.05f),
            alpha = 0.05f
        )
    }
)
