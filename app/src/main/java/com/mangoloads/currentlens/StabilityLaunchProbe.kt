package com.mangoloads.currentlens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.runtime.withFrameNanos

/**
 * A temporary first-launch isolation screen used only in Stability APKs.
 *
 * It deliberately does not create Prismal backdrops, graphics-layer sampling,
 * glass drawing modifiers, blur or shader effects. The button then loads the
 * exact full app UI with Prismal's normal automatic pipeline enabled.
 */
@Composable
internal fun StabilityLaunchProbe(onLaunchFullApp: () -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        LaunchCrashDiagnostics.recordStage(context, "Stability probe composition started")
        repeat(3) { withFrameNanos { } }
        LaunchCrashDiagnostics.markHealthy(
            context,
            "First Compose frames completed — stability probe"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 26.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "brrrNews",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "Startup isolation check",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(14.dp))
        Text(
            text = "This first screen uses standard Compose and Material only. It avoids Prismal backdrop capture and glass rendering so the initial launch can be checked separately.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(14.dp))
        Text(
            text = "Next, open the complete app with the original Liquid Glass effects enabled. If that step crashes, the report will identify that the failure occurred after the full UI was requested.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(Modifier.height(26.dp))
        Button(
            onClick = onLaunchFullApp,
            modifier = Modifier.fillMaxSize(fraction = 0.0f).then(Modifier),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text("Open full brrrNews — Liquid Glass enabled")
        }
    }
}
