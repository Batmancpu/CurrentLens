package com.mangoloads.currentlens

import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.Color as AndroidColor
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.view.ActionMode
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.accessibility.AccessibilityManager
import android.os.SystemClock
import android.webkit.WebResourceRequest
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.TextDecrease
import androidx.compose.material.icons.outlined.TextIncrease
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.layout.positionInRoot
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.compose.foundation.isSystemInDarkTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.styropyr0.prismal.PrismalBackdrop
import com.styropyr0.prismal.PrismalGlassSurface
import com.styropyr0.prismal.drawPrismalGlass
import com.styropyr0.prismal.depth.PrismalDepthInset
import com.styropyr0.prismal.depth.PrismalDepthShadow
import com.styropyr0.prismal.effects.PrismalAdaptiveTuning
import com.styropyr0.prismal.effects.applyPrismalGlassEffects
import com.styropyr0.prismal.specular.PrismalSpecular
import com.styropyr0.prismal.specular.PrismalSpecularStyle
import com.styropyr0.prismal.components.LocalPrismalBottomTabHighlightedIndex
import com.styropyr0.prismal.components.PrismalGlassBottomTab
import com.styropyr0.prismal.interactive.PrismalPressRipple
import com.styropyr0.prismal.interactive.PrismalSpringMotion
import com.styropyr0.prismal.shapes.PrismalCapsule
import com.styropyr0.prismal.shapes.PrismalRoundedRectangle
import com.styropyr0.prismal.sources.prismalGlassLayer
import com.styropyr0.prismal.sources.rememberPrismalGlassLayer
import com.styropyr0.prismal.sources.rememberPrismalMergedSource
import androidx.core.view.WindowCompat
import org.json.JSONTokener
import org.json.JSONObject
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val LocalGlassReduceTransparency = staticCompositionLocalOf { false }
private val LocalGlassReduceMotion = staticCompositionLocalOf { false }
private val LocalGlassIntensity = staticCompositionLocalOf {
    AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA
}

private const val INDIA_ZONE = "Asia/Kolkata"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        LaunchCrashDiagnostics.recordStage(applicationContext, "MainActivity.onCreate entered")
        val previousCrashReport = LaunchCrashDiagnostics.read(this)
        if (previousCrashReport != null) {
            this.showCrashRecoveryScreen(previousCrashReport)
            return
        }
        LaunchCrashDiagnostics.recordStage(applicationContext, "MainActivity preparing Compose content")
        setContent {
            val dark = isSystemInDarkTheme()
            val colors = if (dark) {
                darkColorScheme(
                    primary = Color(0xFFB9CAFF),
                    onPrimary = Color(0xFF15233B),
                    primaryContainer = Color(0xFF293D5C),
                    onPrimaryContainer = Color(0xFFE2EAFF),
                    secondary = Color(0xFFBBC8DA),
                    onSecondary = Color(0xFF253244),
                    background = Color(0xFF0D1118),
                    onBackground = Color(0xFFF1F4F8),
                    surface = Color(0xFF171D27),
                    onSurface = Color(0xFFF1F4F8),
                    surfaceVariant = Color(0xFF252D3A),
                    onSurfaceVariant = Color(0xFFC2CBD7),
                    outline = Color(0xFF4A586B),
                    error = Color(0xFFFFB4AB)
                )
            } else {
                lightColorScheme(
                    primary = Color(0xFF315F9F),
                    onPrimary = Color.White,
                    primaryContainer = Color(0xFFDCE8FF),
                    onPrimaryContainer = Color(0xFF142A49),
                    secondary = Color(0xFF52657D),
                    onSecondary = Color.White,
                    background = Color(0xFFF5F7FA),
                    onBackground = Color(0xFF18212D),
                    surface = Color.White,
                    onSurface = Color(0xFF1A2431),
                    surfaceVariant = Color(0xFFE9EEF5),
                    onSurfaceVariant = Color(0xFF526176),
                    outline = Color(0xFF8291A5),
                    error = Color(0xFFBA1A1A)
                )
            }
            val view = LocalView.current
            SideEffect {
                // OEM window implementations can differ. Bar styling must not abort
                // activity startup or prevent the first Compose frame from drawing.
                runCatching {
                    val window = (view.context as? Activity)?.window
                    if (window != null) {
                        window.statusBarColor = AndroidColor.TRANSPARENT
                        window.navigationBarColor = AndroidColor.TRANSPARENT
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            window.isStatusBarContrastEnforced = false
                            window.isNavigationBarContrastEnforced = false
                        }
                        WindowCompat.getInsetsController(window, view).apply {
                            isAppearanceLightStatusBars = !dark
                            isAppearanceLightNavigationBars = !dark
                        }
                    }
                }
            }
            // Stability packages now enter the complete app directly. The
            // isolated rendering probes remain in source for engineering diagnosis,
            // but are not part of the owner's ordinary launch experience.
            var showFullApp by remember { mutableStateOf(true) }
            MaterialTheme(colorScheme = colors) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background,
                    contentColor = colors.onBackground
                ) {
                    if (showFullApp) {
                        BrrrNewsApp()
                    } else {
                        StabilityLaunchProbe {
                            LaunchCrashDiagnostics.recordStage(
                                applicationContext,
                                "Full brrrNews composition requested from stability probe"
                            )
                            showFullApp = true
                        }
                    }
                }
            }
        }
        LaunchCrashDiagnostics.recordStage(applicationContext, "MainActivity.setContent returned")
    }
}

/**
 * Uses framework Views deliberately: this screen must remain available if Compose or
 * the Prismal graphics pipeline caused the preceding startup failure.
 */
private fun MainActivity.showCrashRecoveryScreen(report: String) {
    val activity = this
    val density = resources.displayMetrics.density
    fun dp(value: Int): Int = (value * density).toInt()

    val root = android.widget.LinearLayout(this).apply {
        orientation = android.widget.LinearLayout.VERTICAL
        setPadding(dp(22), dp(28), dp(22), dp(20))
        setBackgroundColor(android.graphics.Color.rgb(13, 17, 24))
    }
    val title = android.widget.TextView(this).apply {
        text = "brrrNews could not complete its previous launch"
        textSize = 24f
        setTextColor(android.graphics.Color.WHITE)
        typeface = android.graphics.Typeface.DEFAULT_BOLD
    }
    val description = android.widget.TextView(this).apply {
        text = "A privacy-minimised local report was saved on this device. Copy it and share it for diagnosis; it omits device identifiers and raw system traces."
        textSize = 15f
        setTextColor(android.graphics.Color.rgb(205, 215, 228))
        setPadding(0, dp(12), 0, dp(14))
    }
    val reportLabel = android.widget.TextView(this).apply {
        text = "LOCAL STARTUP DIAGNOSTICS"
        textSize = 12f
        setTextColor(android.graphics.Color.rgb(150, 190, 255))
        typeface = android.graphics.Typeface.DEFAULT_BOLD
        setPadding(0, dp(14), 0, dp(8))
    }
    val reportText = android.widget.TextView(this).apply {
        text = report.take(LaunchCrashDiagnostics.MAX_REPORT_CHARS)
        textSize = 11f
        setTextColor(android.graphics.Color.rgb(220, 226, 235))
        typeface = android.graphics.Typeface.MONOSPACE
        setTextIsSelectable(true)
        setPadding(dp(10), dp(10), dp(10), dp(10))
        setBackgroundColor(android.graphics.Color.rgb(25, 33, 45))
    }
    val reportScroll = android.widget.ScrollView(this).apply {
        isFillViewport = true
        addView(reportText)
    }
    val actions = android.widget.LinearLayout(this).apply {
        orientation = android.widget.LinearLayout.VERTICAL
        gravity = android.view.Gravity.CENTER_HORIZONTAL
    }
    val copyButton = android.widget.Button(this).apply {
        text = "Copy crash report"
        setOnClickListener {
            val clipboard = activity.getSystemService(android.content.Context.CLIPBOARD_SERVICE)
                as? android.content.ClipboardManager
            clipboard?.setPrimaryClip(
                android.content.ClipData.newPlainText("brrrNews crash report", report)
            )
            android.widget.Toast.makeText(
                activity,
                "Crash report copied",
                android.widget.Toast.LENGTH_LONG
            ).show()
        }
    }
    val retryButton = android.widget.Button(this).apply {
        text = "Continue to brrrNews"
        setOnClickListener {
            LaunchCrashDiagnostics.clear(activity)
            activity.recreate()
        }
    }
    actions.addView(
        copyButton,
        android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.MATCH_PARENT, dp(48)
        ).apply { bottomMargin = dp(8) }
    )
    actions.addView(
        retryButton,
        android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.MATCH_PARENT, dp(48)
        )
    )
    root.addView(title)
    root.addView(description)
    root.addView(actions)
    root.addView(reportLabel)
    root.addView(
        reportScroll,
        android.widget.LinearLayout.LayoutParams(
            android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            1f
        )
    )
    setContentView(root)
}

private enum class YouPage { DASHBOARD, SAVED, READING_STATS, QUIZ_STATS }

private data class LocalAppSnapshot(
    val appearance: LocalAppearancePreferences,
    val articles: List<Article>,
    val bookmarks: Set<String>,
    val readHistory: Set<String>,
    val readingSessions: List<ReadingSession>,
    val quizAttempts: List<QuizAttempt>
)

@Composable
private fun BrrrNewsApp() {
    val context = LocalContext.current
    val persistenceScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        LaunchCrashDiagnostics.recordStage(context, "BrrrNewsApp composition started")
        repeat(3) { withFrameNanos { } }
        LaunchCrashDiagnostics.markHealthy(context)
    }
    val backdrop = rememberPrismalGlassLayer()
    val dark = isSystemInDarkTheme()
    // Restore larger persisted collections after the first frame; parsing 5,000
    // sessions/quiz attempts on the UI thread can stall cold launch.
    var articles by remember { mutableStateOf(emptyList<Article>()) }
    var bookmarks by remember { mutableStateOf(emptySet<String>()) }
    var readHistory by remember { mutableStateOf(emptySet<String>()) }
    var readingSessions by remember { mutableStateOf(emptyList<ReadingSession>()) }
    var quizAttempts by remember { mutableStateOf(emptyList<QuizAttempt>()) }
    var localStateLoaded by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }
    var refreshing by remember { mutableStateOf(false) }
    var loadError by remember { mutableStateOf(false) }
    var refreshKey by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var youPage by remember { mutableStateOf(YouPage.DASHBOARD) }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }
    var quizMode by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    // Do not make SharedPreferences' first (whole-file) load on the Compose/UI thread.
    // The persisted values are restored with the rest of LocalAppSnapshot on Dispatchers.IO.
    var glassAlpha by remember { mutableFloatStateOf(AppearancePreferencesPolicy.DEFAULT_GLASS_ALPHA) }
    var reduceTransparency by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var searchExpanded by remember { mutableStateOf(false) }
    var searchCollapseRequested by remember { mutableStateOf(false) }
    val searchTabAnchorState = remember { mutableStateOf(Offset.Zero) }
    val searchDockPositionState = remember { mutableStateOf(Offset.Zero) }
    val searchFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    // WindowInsets.ime is a composable local; capture it here before entering an effect.
    val imeInsets = WindowInsets.ime
    var compactNavigation by remember { mutableStateOf(false) }
    var feedIsScrolling by remember { mutableStateOf(false) }
    val feedScrollSettleJob = remember { arrayOfNulls<Job>(1) }
    DisposableEffect(Unit) {
        onDispose { feedScrollSettleJob[0]?.cancel() }
    }
    val feedNavigationTracker = remember { FeedNavigationScrollTracker() }
    val feedNavigationEnabled = selectedArticle == null && !quizMode && !showSettings &&
        selectedTab in 0..2 && !searchExpanded
    val feedNavigationConnection = remember(feedNavigationEnabled) {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (!feedNavigationEnabled) return Offset.Zero
                if (consumed.y != 0f) {
                    if (!feedIsScrolling) feedIsScrolling = true
                    feedScrollSettleJob[0]?.cancel()
                    feedScrollSettleJob[0] = persistenceScope.launch {
                        delay(550L)
                        feedIsScrolling = false
                    }
                }
                // LazyColumn scrolls forward with positive consumed Y; pulling past its
                // top edge leaves a negative unconsumed delta.
                val atTopEdge = consumed.y == 0f && available.y < 0f
                val nextCompact = when {
                    atTopEdge -> feedNavigationTracker.reset()
                    consumed.y != 0f -> feedNavigationTracker.onScroll(
                        deltaPx = consumed.y.roundToInt(),
                        hideThresholdPx = with(density) { FeedNavigationPolicy.HIDE_THRESHOLD_DP.dp.roundToPx() },
                        showThresholdPx = with(density) { FeedNavigationPolicy.SHOW_THRESHOLD_DP.dp.roundToPx() },
                        eligible = true
                    )
                    else -> compactNavigation
                }
                if (nextCompact != compactNavigation) compactNavigation = nextCompact
                return Offset.Zero
            }
        }
    }
    LaunchedEffect(selectedTab, searchExpanded, selectedArticle?.url, showSettings, quizMode) {
        feedNavigationTracker.reset()
        compactNavigation = false
        feedIsScrolling = false
        feedScrollSettleJob[0]?.cancel()
    }
    val lifecycleOwner = context as? LifecycleOwner
    var appResumed by remember {
        mutableStateOf(lifecycleOwner?.lifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED) == true)
    }
    var reduceMotion by remember { mutableStateOf(!ValueAnimator.areAnimatorsEnabled()) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    appResumed = true
                    reduceMotion = !ValueAnimator.areAnimatorsEnabled()
                }
                Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP -> appResumed = false
                else -> Unit
            }
        }
        lifecycleOwner?.lifecycle?.addObserver(observer)
        onDispose { lifecycleOwner?.lifecycle?.removeObserver(observer) }
    }

    // One shared 8x8 bottom-band probe supplies Navigation and Search contrast.
    // It pauses outside foreground home surfaces and during feed motion; the readback
    // cadence is deliberately conservative to avoid synchronising with active flings.
    val glassLuminance = rememberBottomBandGlassLuminance(
        enabled = GlassLuminanceActivationPolicy.shouldSample(
            isResumed = appResumed,
            readerOpen = selectedArticle != null || quizMode,
            settingsOpen = showSettings,
            feedScrolling = feedIsScrolling,
            reduceTransparency = reduceTransparency
        ),
        source = backdrop,
        isLightTheme = !dark,
        bandHeight = 104.dp
    )
    var todayDate by remember { mutableStateOf(AsiaKolkataDayPolicy.today()) }
    // Today is a calendar-day view, not a fetch-time snapshot. Keep its index current
    // across IST midnight while foregrounded, and refresh immediately after backgrounding.
    LaunchedEffect(appResumed) {
        if (!appResumed) return@LaunchedEffect
        todayDate = AsiaKolkataDayPolicy.today()
        while (true) {
            delay(AsiaKolkataDayPolicy.millisUntilNextMidnight(Instant.now()))
            todayDate = AsiaKolkataDayPolicy.today()
        }
    }
    var feedIndex by remember {
        mutableStateOf(EditorialFeedIndex(emptyList(), emptyList(), emptyList()))
    }
    var feedIndexReady by remember { mutableStateOf(false) }
    LaunchedEffect(articles, todayDate, localStateLoaded) {
        if (!localStateLoaded) return@LaunchedEffect
        val nextIndex = withContext(Dispatchers.Default) {
            EditorialFeedIndexPolicy.build(articles, todayDate)
        }
        feedIndex = nextIndex
        feedIndexReady = true
    }

    // Keep the composer IME-docked while the keyboard dismisses. Only after the
    // keyboard reaches zero inset does the capsule travel back into its tab anchor.
    val requestSearchCollapse: () -> Unit = {
        focusManager.clearFocus()
        keyboardController?.hide()
        searchCollapseRequested = true
    }

    LaunchedEffect(searchCollapseRequested) {
        if (searchCollapseRequested) {
            snapshotFlow { imeInsets.getBottom(density) }.first { it == 0 }
            searchExpanded = false
            searchCollapseRequested = false
        }
    }

    LaunchedEffect(searchExpanded, selectedTab) {
        if (searchExpanded && selectedTab == SearchNavigationPolicy.SEARCH_TAB) {
            withFrameNanos { }
            searchFocusRequester.requestFocus()
            keyboardController?.show()
        } else if (!searchCollapseRequested) {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    LaunchedEffect(context) {
        val restored = withContext(Dispatchers.IO) {
            LocalAppSnapshot(
                appearance = LocalStore.appearancePreferences(context),
                articles = LocalStore.cachedArticles(context),
                bookmarks = LocalStore.bookmarks(context),
                readHistory = LocalStore.readHistory(context),
                readingSessions = LocalStore.readingSessions(context),
                quizAttempts = LocalStore.quizAttempts(context)
            )
        }
        glassAlpha = restored.appearance.glassAlpha
        reduceTransparency = restored.appearance.reduceTransparency
        articles = restored.articles
        bookmarks = restored.bookmarks
        readHistory = restored.readHistory
        readingSessions = restored.readingSessions
        quizAttempts = restored.quizAttempts
        localStateLoaded = true
    }

    LaunchedEffect(refreshKey, localStateLoaded) {
        if (!localStateLoaded) return@LaunchedEffect
        loading = articles.isEmpty()
        refreshing = articles.isNotEmpty()
        loadError = false
        val fetched = NewsRepository.fetchAll()
        if (fetched.isNotEmpty()) {
            val merged = withContext(Dispatchers.IO) { LocalStore.mergeAndSaveArticles(context, fetched) }
            articles = merged
        }
        else if (articles.isEmpty()) loadError = true
        loading = false
        refreshing = false
    }

    BackHandler(enabled = searchExpanded || selectedArticle != null || showSettings || selectedTab != 0) {
        when {
            searchExpanded -> requestSearchCollapse()
            quizMode -> quizMode = false
            selectedArticle != null -> selectedArticle = null
            showSettings -> showSettings = false
            selectedTab == 3 && youPage != YouPage.DASHBOARD -> youPage = YouPage.DASHBOARD
            selectedTab != 0 -> selectedTab = 0
        }
    }

    CompositionLocalProvider(
        LocalGlassReduceTransparency provides reduceTransparency,
        LocalGlassReduceMotion provides reduceMotion,
        LocalGlassIntensity provides glassAlpha
    ) {
        BoxWithConstraints(
        Modifier.fillMaxSize().background(
            if (searchExpanded) Color.Transparent else MaterialTheme.colorScheme.background
        )
    ) {
        // Prismal's capture source must contain background pixels only. Capturing the
        // complete UI subtree here would also record glass surfaces that sample this
        // same layer, creating a self-referential GraphicsLayer render dependency.
        // Keep the live glass surfaces as siblings above this isolated ambient source.
        Box(
            Modifier.fillMaxSize()
                .prismalGlassLayer(backdrop)
                .background(MaterialTheme.colorScheme.background)
                .drawBehind {
                    val topCenter = Offset(size.width * 0.82f, size.height * 0.12f)
                    val lowerCenter = Offset(size.width * 0.14f, size.height * 0.78f)
                    val glowRadius = size.width * 0.92f
                    val topGlow = if (dark) {
                        Color(0xFF5279B6).copy(alpha = 0.24f)
                    } else {
                        Color(0xFF8CB5F0).copy(alpha = 0.20f)
                    }
                    val lowerGlow = if (dark) {
                        Color(0xFF315B79).copy(alpha = 0.16f)
                    } else {
                        Color(0xFFB7C9E8).copy(alpha = 0.18f)
                    }
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(topGlow, Color.Transparent),
                            center = topCenter,
                            radius = glowRadius
                        ),
                        radius = glowRadius,
                        center = topCenter
                    )
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(lowerGlow, Color.Transparent),
                            center = lowerCenter,
                            radius = glowRadius * 0.82f
                        ),
                        radius = glowRadius * 0.82f,
                        center = lowerCenter
                    )
                }
        )

        val searchTransition = updateTransition(targetState = searchExpanded, label = "Search tab morph")
        val animatedSearchMorphProgress = searchTransition.animateFloat(
            transitionSpec = {
                if (reduceMotion) snap() else spring(dampingRatio = 0.84f, stiffness = 320f)
            },
            label = "Liquid search morph progress"
        ) { expanded -> if (expanded) 1f else 0f }.value
        val searchMorphProgress = SearchMorphGeometryPolicy.effectiveProgress(
            animatedProgress = animatedSearchMorphProgress,
            expanded = searchExpanded,
            reduceMotion = reduceMotion
        )
        val searchMorphActive = searchTransition.currentState || searchTransition.targetState
        // Match the collapsed Search capsule to the selected tab's resting optical footprint.
        // The tab row is inset by 10dp outside and 4dp inside; its content height is 56dp.
        val sourceWidth = ((maxWidth - 28.dp) / 4f).coerceAtLeast(1.dp)
        val sourceHeight = 64.dp
        val searchHeight = 52.dp
        val targetWidth = (maxWidth - 32.dp).coerceAtLeast(180.dp)
        val sourceWidthPx = with(density) { sourceWidth.toPx() }
        val sourceHeightPx = with(density) { sourceHeight.toPx() }
        val targetWidthPx = with(density) { targetWidth.toPx() }
        val collapsedIconOffsetPx = SearchMorphGeometryPolicy.collapsedIconOffsetPx(
            sourceWidthPx = sourceWidthPx,
            iconWidthPx = with(density) { 20.dp.toPx() },
            leadingInsetPx = with(density) { 13.dp.toPx() }
        )
        val fallbackAnchor = Offset(
            with(density) { (maxWidth * 0.625f - 3.5.dp).toPx() },
            with(density) { maxHeight.toPx() - 38.dp.toPx() }
        )
        val fallbackDock = Offset(
            (with(density) { maxWidth.toPx() } - targetWidthPx) / 2f,
            with(density) { maxHeight.toPx() - searchHeight.toPx() - 2.dp.toPx() }
        )

        Column(
            Modifier.fillMaxSize()
                .then(if (searchExpanded) Modifier.imePadding() else Modifier)
                .then(if (feedNavigationEnabled) Modifier.nestedScroll(feedNavigationConnection) else Modifier)
                .background(if (searchExpanded) Color.Transparent else MaterialTheme.colorScheme.background)
        ) {
            when {
                selectedArticle != null && quizMode -> QuizScreen(
                    article = selectedArticle!!,
                    backdrop = backdrop,
                    glassAlpha = glassAlpha,
                    dark = dark,
                    onBack = { quizMode = false },
                    onSubmit = { answers, questions ->
                        val article = selectedArticle!!
                        persistenceScope.launch {
                            val updated = withContext(NonCancellable) {
                                LocalStore.recordQuizAttemptAndRead(context, article, answers, questions)
                            }
                            if (updated != null) quizAttempts = updated
                        }
                    }
                )
                selectedArticle != null -> {
                    val article = selectedArticle!!
                    ReaderScreen(
                        article = article,
                        backdrop = backdrop,
                        glassAlpha = glassAlpha,
                        dark = dark,
                        onBack = { selectedArticle = null },
                        onQuiz = { quizMode = true },
                        onSessionWindow = { window ->
                            persistenceScope.launch {
                                val updated = withContext(NonCancellable) {
                                    LocalStore.recordReadingSessionAndRead(
                                        context, article, window.startedAtMillis, window.durationMillis
                                    )
                                }
                                if (updated != null) readingSessions = updated
                            }
                        },
                        onSaveReaderPosition = { url, progress ->
                            persistenceScope.launch {
                                withContext(NonCancellable) {
                                    LocalStore.saveReaderPositionAndWrite(context, url, progress)
                                }
                            }
                        },
                        onClearReaderPosition = { url ->
                            persistenceScope.launch {
                                withContext(NonCancellable) {
                                    LocalStore.clearReaderPositionAndWrite(context, url)
                                }
                            }
                        }
                    )
                }
                showSettings -> GlassSettingsScreen(
                    backdrop = backdrop,
                    glassAlpha = glassAlpha,
                    reduceTransparency = reduceTransparency,
                    onReduceTransparencyChange = { enabled ->
                        reduceTransparency = enabled
                        LocalStore.saveReduceTransparency(context, enabled)
                    },
                    onGlassAlphaChange = {
                        glassAlpha = it
                        LocalStore.saveGlassAlpha(context, it)
                    },
                    onBack = { showSettings = false }
                )
                else -> HomeScreen(
                    backdrop = backdrop,
                    glassAlpha = glassAlpha,
                    selectedTab = selectedTab,
                    youPage = youPage,
                    onYouPageChange = { youPage = it },
                    onRefresh = { if (!refreshing) refreshKey++ },
                    refreshing = refreshing,
                    loading = loading || !feedIndexReady,
                    loadError = loadError,
                    todaysArticles = feedIndex.todayArticles,
                    eligibleArticles = feedIndex.eligibleArticles,
                    exploreArticles = feedIndex.exploreArticles,
                    allCachedArticles = articles,
                    bookmarks = bookmarks,
                    readHistory = readHistory,
                    readingSessions = readingSessions,
                    quizAttempts = quizAttempts,
                    totalUniqueRead = StudyStatsPolicy.totalUniqueReadCount(readHistory, readingSessions),
                    articlesReadToday = StudyStatsPolicy.articlesReadOn(
                        readingSessions, LocalDate.now(ZoneId.of(INDIA_ZONE))
                    ),
                    searchQuery = searchQuery,
                    searchExpanded = searchExpanded,
                    onSettings = { showSettings = true },
                    onOpenArticle = { article ->
                        searchExpanded = false
                        selectedArticle = article
                        persistenceScope.launch {
                            val updated = withContext(NonCancellable) {
                                LocalStore.markReadAndRead(context, article.url)
                            }
                            readHistory = updated
                        }
                    },
                    onToggleBookmark = { article ->
                        persistenceScope.launch {
                            val added = withContext(NonCancellable) {
                                LocalStore.toggleBookmark(context, article.url)
                            }
                            bookmarks = if (added) bookmarks + article.url else bookmarks - article.url
                        }
                    }
                )
            }
        }

        if (selectedArticle == null && !showSettings) {
            Box(
                Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                    .navigationBarsPadding().padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                GlassTabBar(
                    backdrop = backdrop,
                    selectedTab = selectedTab,
                    glassAlpha = glassAlpha,
                    luminance = glassLuminance.luminance,
                    adaptiveContentColor = glassLuminance.contentColor,
                    reduceMotion = reduceMotion,
                    compact = compactNavigation && feedNavigationEnabled,
                    onExpandCompact = {
                        compactNavigation = false
                        feedNavigationTracker.reset()
                    },
                    morphProgress = if (selectedTab == SearchNavigationPolicy.SEARCH_TAB && searchMorphActive) {
                        searchMorphProgress
                    } else 0f,
                    onSearchAnchor = { searchTabAnchorState.value = it },
                    onSelect = { tab ->
                        if (tab != selectedTab) {
                            compactNavigation = false
                            feedNavigationTracker.reset()
                        }
                        val destination = SearchNavigationPolicy.selectTab(tab)
                        selectedTab = destination.selectedTab
                        searchExpanded = destination.searchExpanded
                        searchCollapseRequested = false
                        if (tab == 3) youPage = YouPage.DASHBOARD
                    }
                )
            }
        }

        if (selectedArticle == null && !showSettings &&
            selectedTab == SearchNavigationPolicy.SEARCH_TAB && searchMorphActive
        ) {
            // Compose's animated IME insets position this dock at the actual keyboard edge.
            // No manual maxHeight - imeBottom subtraction, which double-counts on resized windows.
            Box(
                Modifier.align(Alignment.BottomCenter)
                    .imePadding()
                    .padding(bottom = 2.dp)
                    .width(targetWidth)
                    .height(searchHeight)
                    .onGloballyPositioned { coordinates ->
                        searchDockPositionState.value = coordinates.positionInRoot()
                    }
            ) {
                val anchor = searchTabAnchorState.value.takeIf { it != Offset.Zero } ?: fallbackAnchor
                val dock = searchDockPositionState.value.takeIf { it != Offset.Zero } ?: fallbackDock
                // Interpolate real layout bounds instead of non-uniformly scaling a
                // full-width capsule. PrismalCapsule derives its radius from the live
                // dimensions, so its end caps stay circular throughout the morph.
                val morphFrame = SearchMorphGeometryPolicy.frame(
                    sourceLeftPx = anchor.x - sourceWidthPx / 2f,
                    sourceTopPx = anchor.y - sourceHeightPx / 2f,
                    sourceWidthPx = sourceWidthPx,
                    sourceHeightPx = sourceHeightPx,
                    dockLeftPx = dock.x,
                    dockTopPx = dock.y,
                    dockWidthPx = targetWidthPx,
                    dockHeightPx = with(density) { searchHeight.toPx() },
                    progress = searchMorphProgress
                )
                SearchMorphField(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                morphFrame.offsetXPx.roundToInt(),
                                morphFrame.offsetYPx.roundToInt()
                            )
                        }
                        .width(with(density) { morphFrame.widthPx.toDp() })
                        .requiredHeight(with(density) { morphFrame.heightPx.toDp() }),
                    backdrop = backdrop,
                    glassAlpha = glassAlpha,
                    luminance = glassLuminance.luminance,
                    adaptiveContentColor = glassLuminance.contentColor,
                    collapsedIconOffsetPx = collapsedIconOffsetPx,
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    focusRequester = searchFocusRequester,
                    enabled = searchExpanded && !searchCollapseRequested,
                    morphProgress = searchMorphProgress,
                    reduceMotion = reduceMotion,
                    onSubmit = requestSearchCollapse,
                    onClear = {
                        searchQuery = ""
                        searchFocusRequester.requestFocus()
                        keyboardController?.show()
                    }
                )
            }
        }
    }
    }
}
@Composable
private fun HomeScreen(
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    selectedTab: Int,
    youPage: YouPage,
    onYouPageChange: (YouPage) -> Unit,
    onRefresh: () -> Unit,
    refreshing: Boolean,
    loading: Boolean,
    loadError: Boolean,
    todaysArticles: List<Article>,
    eligibleArticles: List<Article>,
    exploreArticles: List<Article>,
    allCachedArticles: List<Article>,
    bookmarks: Set<String>,
    readHistory: Set<String>,
    readingSessions: List<ReadingSession>,
    quizAttempts: List<QuizAttempt>,
    totalUniqueRead: Int,
    articlesReadToday: Int,
    searchQuery: String,
    searchExpanded: Boolean,
    onSettings: () -> Unit,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit
) {
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val reduceMotion = LocalGlassReduceMotion.current
    val dateLabel = remember(today) { today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)) }
    var exploreCategory by remember { mutableStateOf("All") }

    val pageTitle = when (selectedTab) {
        0 -> "Today"
        1 -> "Explore"
        2 -> "Search"
        else -> when (youPage) {
            YouPage.DASHBOARD -> "You"
            YouPage.SAVED -> "Saved"
            YouPage.READING_STATS -> "Reading Stats"
            YouPage.QUIZ_STATS -> "Quiz & Practice Stats"
        }
    }
    val subtitle = when (selectedTab) {
        0 -> dateLabel
        1 -> "UPSC-relevant current affairs across GS papers"
        2 -> "Search syllabus-aligned current affairs by topic or GS paper"
        else -> when (youPage) {
            YouPage.DASHBOARD -> "Your reading and practice at a glance"
            YouPage.SAVED -> "Your saved articles and previously opened stories"
            YouPage.READING_STATS -> "Time and reading activity recorded on this device"
            YouPage.QUIZ_STATS -> "Submitted practice results and answer accuracy"
        }
    }

    Column(Modifier.fillMaxSize()) {
        HomeGlassHeader(
            backdrop = backdrop,
            glassAlpha = glassAlpha,
            dark = isSystemInDarkTheme(),
            pageTitle = pageTitle,
            subtitle = subtitle,
            showRefresh = selectedTab != 3,
            onRefresh = onRefresh,
            refreshing = refreshing,
            showBack = selectedTab == 3 && youPage != YouPage.DASHBOARD,
            onBack = { onYouPageChange(YouPage.DASHBOARD) }
        )

        AnimatedContent(
            targetState = selectedTab to youPage,
            transitionSpec = {
                if (reduceMotion) fadeIn(snap()) togetherWith fadeOut(snap())
                else fadeIn(tween(190)) togetherWith fadeOut(tween(120))
            },
            label = "brrrNews destination transition"
        ) { target ->
            val (tab, page) = target
            when (tab) {
                0 -> TodayFeed(
                    todaysArticles, eligibleArticles, loading, loadError, refreshing,
                    bookmarks, onOpenArticle, onToggleBookmark
                )
                1 -> ExploreFeed(
                    exploreArticles, exploreCategory,
                    { exploreCategory = it }, bookmarks, onOpenArticle, onToggleBookmark,
                    loading, loadError, backdrop, glassAlpha, isSystemInDarkTheme()
                )
                2 -> SearchFeed(
                    eligibleArticles, searchQuery, searchExpanded,
                    bookmarks, onOpenArticle, onToggleBookmark, loading, loadError,
                    backdrop, glassAlpha, isSystemInDarkTheme()
                )
                else -> when (page) {
                    YouPage.DASHBOARD -> YouDashboard(
                        totalUniqueRead = totalUniqueRead,
                        articlesReadToday = articlesReadToday,
                        savedCount = bookmarks.size,
                        attempts = quizAttempts,
                        onSaved = { onYouPageChange(YouPage.SAVED) },
                        onReadingStats = { onYouPageChange(YouPage.READING_STATS) },
                        onQuizStats = { onYouPageChange(YouPage.QUIZ_STATS) },
                        onSettings = onSettings
                    )
                    YouPage.SAVED -> SavedFeed(
                        articles = allCachedArticles,
                        bookmarks = bookmarks,
                        readHistory = readHistory,
                        onOpenArticle = onOpenArticle,
                        onToggleBookmark = onToggleBookmark,
                        backdrop = backdrop,
                        glassAlpha = glassAlpha
                    )
                    YouPage.READING_STATS -> ReadingStatsScreen(
                        sessions = readingSessions,
                        backdrop = backdrop,
                        glassAlpha = glassAlpha
                    )
                    YouPage.QUIZ_STATS -> QuizStatsScreen(
                        attempts = quizAttempts,
                        backdrop = backdrop,
                        glassAlpha = glassAlpha
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeGlassHeader(
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    dark: Boolean,
    pageTitle: String,
    subtitle: String,
    showRefresh: Boolean,
    onRefresh: () -> Unit,
    refreshing: Boolean,
    showBack: Boolean,
    onBack: () -> Unit
) {
    val density = LocalDensity.current
    val reduceTransparency = LocalGlassReduceTransparency.current
    val surfaceTint = MaterialTheme.colorScheme.surface
    val luminance = if (dark) 0.12f else 0.82f
    val optics = remember { GlassOpticsPolicy.header() }
    Box(
        Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 5.dp)
            .drawPrismalGlass(
                backdrop = backdrop,
                shape = { PrismalRoundedRectangle(24.dp) },
                effects = {
                    applyPrismalGlassEffects(
                        density = density, adaptiveLuminance = true, luminance = luminance,
                        blurRadiusPx = with(density) { GlassRenderingPolicy.blurDp(optics.blurDp, reduceTransparency).dp.toPx() },
                        refractionHeightPx = with(density) { GlassRenderingPolicy.refractionDp(optics.refractionHeightDp, reduceTransparency).dp.toPx() },
                        refractionAmountPx = with(density) { GlassRenderingPolicy.refractionDp(optics.refractionAmountDp, reduceTransparency).dp.toPx() },
                        depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
                        chromaticAberration = GlassRenderingPolicy.chromaticAberration(optics.chromaticAberration, reduceTransparency),
                        adaptiveTuning = PrismalAdaptiveTuning.Subtle
                    )
                },
                specular = {
                    PrismalSpecular.Default.copy(width = 0.55.dp, blurRadius = 0.35.dp, alpha = GlassRenderingPolicy.specularAlpha(optics.specularAlpha, reduceTransparency))
                },
                depthShadow = {
                    PrismalDepthShadow(radius = 12.dp, offset = DpOffset(0.dp, 2.dp),
                        color = Color.Black.copy(alpha = 0.09f), alpha = GlassRenderingPolicy.shadowAlpha(optics.shadowAlpha, reduceTransparency))
                },
                depthInset = {
                    PrismalDepthInset(radius = 4.dp, offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.05f), alpha = GlassRenderingPolicy.shadowAlpha(optics.insetAlpha, reduceTransparency))
                },
                onDrawSurface = { drawRect(surfaceTint.copy(alpha = GlassRenderingPolicy.tintAlpha(glassAlpha * optics.tintAlpha, reduceTransparency))) }
            )
    ) {
        Row(Modifier.fillMaxWidth().padding(start = 14.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            if (showBack) IconButton(onClick = onBack, modifier = Modifier.size(TouchTargetPolicy.iconTargetDp(44).dp)) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back to You", modifier = Modifier.size(23.dp))
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(pageTitle, style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(subtitle, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (showRefresh) IconButton(onClick = onRefresh, enabled = !refreshing, modifier = Modifier.size(TouchTargetPolicy.iconTargetDp(46).dp)) {
                Icon(Icons.Outlined.Refresh, contentDescription = "Refresh news", modifier = Modifier.size(25.dp))
            }
        }
    }
}

@Composable
private fun ExploreGlassCategories(
    categories: List<String>,
    selectedCategory: String,
    onCategory: (String) -> Unit,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    dark: Boolean
) {
    val density = LocalDensity.current
    val reduceTransparency = LocalGlassReduceTransparency.current
    val surfaceTint = MaterialTheme.colorScheme.surface
    val luminance = if (dark) 0.14f else 0.80f
    val chipShape = glassControlShape
    Box(
        Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 5.dp)
            .drawPrismalGlass(
                backdrop = backdrop,
                shape = { PrismalRoundedRectangle(22.dp) },
                effects = {
                    applyPrismalGlassEffects(
                        density = density, adaptiveLuminance = true, luminance = luminance,
                        blurRadiusPx = with(density) { GlassRenderingPolicy.blurDp(8f, reduceTransparency).dp.toPx() },
                        refractionHeightPx = with(density) { GlassRenderingPolicy.refractionDp(3f, reduceTransparency).dp.toPx() },
                        refractionAmountPx = with(density) { GlassRenderingPolicy.refractionDp(4f, reduceTransparency).dp.toPx() },
                        depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
                        chromaticAberration = GlassRenderingPolicy.chromaticAberration(0.0015f, reduceTransparency),
                        adaptiveTuning = PrismalAdaptiveTuning.Subtle
                    )
                },
                specular = {
                    PrismalSpecular.Default.copy(width = 0.45.dp, blurRadius = 0.30.dp, alpha = GlassRenderingPolicy.specularAlpha(0.20f, reduceTransparency))
                },
                depthShadow = {
                    PrismalDepthShadow(radius = 9.dp, offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.07f), alpha = GlassRenderingPolicy.shadowAlpha(0.12f, reduceTransparency))
                },
                depthInset = {
                    PrismalDepthInset(radius = 3.dp, offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.04f), alpha = GlassRenderingPolicy.shadowAlpha(0.04f, reduceTransparency))
                },
                onDrawSurface = { drawRect(surfaceTint.copy(alpha = GlassRenderingPolicy.tintAlpha(glassAlpha * 0.09f, reduceTransparency))) }
            )
    ) {
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
            .padding(horizontal = 5.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp), verticalAlignment = Alignment.CenterVertically) {
            categories.forEach { category ->
                val selected = selectedCategory == category
                val borderColor = if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.48f)
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.34f)
                Box(
                    Modifier.clip(chipShape)
                        .background(if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.20f)
                            else surfaceTint.copy(alpha = 0.12f))
                        .border(1.dp, borderColor, chipShape)
                        .selectable(selected = selected, role = Role.Tab) { onCategory(category) }
                        .heightIn(min = 48.dp)
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Text(category, fontSize = 13.sp,
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface, maxLines = 1)
                }
            }
        }
    }
}
@Composable
private fun TodayFeed(
    articles: List<Article>,
    weekendArticles: List<Article>,
    loading: Boolean,
    loadError: Boolean,
    refreshing: Boolean,
    bookmarks: Set<String>,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit
) {
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val weekend = today.dayOfWeek == DayOfWeek.SATURDAY || today.dayOfWeek == DayOfWeek.SUNDAY
    val weekendStart = today.minusDays(if (today.dayOfWeek == DayOfWeek.SATURDAY) 1 else 2)
    val weekendStories = remember(weekend, weekendArticles, today) {
        if (weekend) {
            weekendArticles.filter { article ->
                article.weekendRead &&
                    ArticleFilters.publishedDate(article)?.let { it >= weekendStart && it <= today } == true
            }.sortedByDescending { it.publishedAtMillis ?: 0L }.take(5)
        } else emptyList()
    }

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 110.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        if (weekendStories.isNotEmpty()) item { WeekendPanel(weekendStories, onOpenArticle) }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("UPSC CURRENT AFFAIRS", color = MaterialTheme.colorScheme.primary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
                Spacer(Modifier.weight(1f))
                Text(
                    if (refreshing || loading) "Updating" else articles.size.toString() + " relevant stories",
                    modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 11.sp
                )
            }
        }
        when {
            loading -> items(
                count = 3,
                key = { index -> "feed-skeleton-$index" },
                contentType = { "feed-skeleton" }
            ) { FeedSkeletonCard() }
            loadError && articles.isEmpty() -> item { EmptyPanel("News is temporarily unavailable", "Check your connection and refresh.") }
            articles.isEmpty() -> item { EmptyPanel("No UPSC-relevant stories today", "Broad entertainment and general-interest updates are filtered out. Refresh later for syllabus-linked developments.") }
            else -> items(articles.take(45), key = { it.id }, contentType = { "article" }) { article ->
                NewsCard(article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark)
            }
        }
        if (articles.size > 45) item {
            Text("Showing today's 45 most recent stories. Explore has the wider recent feed.",
                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth().padding(8.dp), textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun WeekendPanel(articles: List<Article>, onOpenArticle: (Article) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("WEEKEND READING", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = ContinuousCornerShape(FeedCardGeometryPolicy.CARD_CORNER_RADIUS_DP.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                Text("The best of the weekend", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Express Sunday Eye stories published in the latest weekend window.",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                articles.forEach { article ->
                    Text(article.title,
                        modifier = Modifier.fillMaxWidth().clip(ContinuousCornerShape(12.dp)).clickable { onOpenArticle(article) }.padding(vertical = 7.dp),
                        style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun ExploreFeed(
    articles: List<Article>,
    selectedCategory: String,
    onCategory: (String) -> Unit,
    bookmarks: Set<String>,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit,
    loading: Boolean,
    loadError: Boolean,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    dark: Boolean
) {
    val categories = listOf("All", "India", "World", "Politics", "Geography", "Economy", "Environment", "Science", "Explained", "UPSC", "Opinion")
    val filtered = remember(articles, selectedCategory) {
        articles.filter { article -> ExploreFilters.matches(article, selectedCategory, "") }
    }
    Column(Modifier.fillMaxSize()) {
        ExploreGlassCategories(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategory = onCategory,
            backdrop = backdrop,
            glassAlpha = glassAlpha,
            dark = dark
        )
        LazyColumn(
            Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            if (filtered.isEmpty() && loading) {
                items(
                count = 3,
                key = { index -> "feed-skeleton-$index" },
                contentType = { "feed-skeleton" }
            ) { FeedSkeletonCard() }
            } else if (filtered.isEmpty() && loadError) {
                item { EmptyPanel("News is temporarily unavailable", "Check your connection and refresh.") }
            } else if (filtered.isEmpty()) item {
                EmptyPanel("No stories in this topic", "Try another topic or refresh your feeds.")
            } else items(filtered, key = { it.id }, contentType = { "article" }) { article ->
                NewsCard(article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchFeed(
    articles: List<Article>,
    query: String,
    searchExpanded: Boolean,
    bookmarks: Set<String>,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit,
    loading: Boolean,
    loadError: Boolean,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    dark: Boolean
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    var showFilters by remember { mutableStateOf(false) }
    val filterSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val keyboardController = LocalSoftwareKeyboardController.current
    val eligibleArticles = articles
    val density = LocalDensity.current
    val reduceTransparency = LocalGlassReduceTransparency.current
    val glassSurfaceColor = MaterialTheme.colorScheme.surface
    val categories = listOf("All", "India", "World", "Politics", "Geography", "Economy", "Environment", "Science", "Explained", "UPSC", "Opinion")
    val classificationInputs = remember(eligibleArticles) {
        ArticleRelevanceIndexPolicy.keys(eligibleArticles)
    }
    // Classification depends only on id/title/description. Reuse the same index for
    // subject choices, query filters and visible Search cards instead of rerunning the
    // keyword engine for every query change.
    val relevanceById = remember(classificationInputs) {
        ArticleRelevanceIndexPolicy.build(eligibleArticles)
    }
    val subjects = remember(relevanceById) {
        listOf("All") + relevanceById.values.map { it.subject }.distinct().sorted()
    }
    val gsPapers = listOf("All", "GS I", "GS II", "GS III", "GS IV")
    val activeFilterCount = listOf(
        selectedCategory != "All",
        selectedGsPaper != "All",
        selectedSubject != "All"
    ).count { it }
    val activeSummary = listOfNotNull(
        selectedCategory.takeIf { it != "All" },
        selectedGsPaper.takeIf { it != "All" },
        selectedSubject.takeIf { it != "All" }
    ).joinToString(" · ")
    val filtered = remember(eligibleArticles, relevanceById, query, selectedCategory, selectedSubject, selectedGsPaper) {
        eligibleArticles.filter { article ->
            val relevance = relevanceById[article.id] ?: return@filter false
            ExploreFilters.matches(article, selectedCategory, query, relevance) &&
                (selectedSubject == "All" || relevance.subject == selectedSubject) &&
                (selectedGsPaper == "All" || relevance.gsPaper == selectedGsPaper)
        }
    }
    Column(Modifier.fillMaxSize()) {
        Box(
            Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 5.dp)
                .drawPrismalGlass(
                    backdrop = backdrop,
                    shape = { PrismalRoundedRectangle(22.dp) },
                    effects = {
                        applyPrismalGlassEffects(
                            density = density,
                            adaptiveLuminance = true,
                            luminance = if (dark) 0.10f else 0.90f,
                            blurRadiusPx = with(density) { GlassRenderingPolicy.blurDp(7f, reduceTransparency).dp.toPx() },
                            refractionHeightPx = with(density) { GlassRenderingPolicy.refractionDp(3f, reduceTransparency).dp.toPx() },
                            refractionAmountPx = with(density) { GlassRenderingPolicy.refractionDp(4f, reduceTransparency).dp.toPx() },
                            depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
                            chromaticAberration = GlassRenderingPolicy.chromaticAberration(0.001f, reduceTransparency),
                            adaptiveTuning = PrismalAdaptiveTuning.Subtle
                        )
                    },
                    specular = {
                        PrismalSpecular.Default.copy(width = 0.42.dp, blurRadius = 0.28.dp, alpha = GlassRenderingPolicy.specularAlpha(0.19f, reduceTransparency))
                    },
                    depthShadow = {
                        PrismalDepthShadow(radius = 8.dp, offset = DpOffset(0.dp, 1.dp),
                            color = Color.Black.copy(alpha = 0.06f), alpha = GlassRenderingPolicy.shadowAlpha(0.12f, reduceTransparency))
                    },
                    depthInset = {
                        PrismalDepthInset(radius = 3.dp, offset = DpOffset(0.dp, 1.dp),
                            color = Color.Black.copy(alpha = 0.04f), alpha = GlassRenderingPolicy.shadowAlpha(0.04f, reduceTransparency))
                    },
                    onDrawSurface = {
                        drawRect(glassSurfaceColor.copy(alpha = GlassRenderingPolicy.tintAlpha(glassAlpha * 0.10f, reduceTransparency)))
                    }
                )
        ) {
            Row(
                Modifier.fillMaxWidth().padding(start = 12.dp, end = 8.dp, top = 7.dp, bottom = 7.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    if (query.isBlank()) "UPSC-RELEVANT ARTICLES" else "UPSC SEARCH RESULTS",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    letterSpacing = 1.0.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    if (activeFilterCount == 0) "Syllabus-linked topics and subjects" else activeSummary,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            GlassActionPill(
                label = if (activeFilterCount == 0) "Filters" else "Filters · $activeFilterCount",
                selected = activeFilterCount > 0,
                icon = Icons.Outlined.Tune,
                onClick = {
                    keyboardController?.hide()
                    showFilters = true
                }
            )
            }
        }
        LazyColumn(
            Modifier.weight(1f),
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, top = 7.dp,
                bottom = if (searchExpanded) 76.dp else 110.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (filtered.isEmpty() && loading) {
                items(
                count = 3,
                key = { index -> "feed-skeleton-$index" },
                contentType = { "feed-skeleton" }
            ) { FeedSkeletonCard() }
            } else if (filtered.isEmpty() && loadError) {
                item { EmptyPanel("News is temporarily unavailable", "Check your connection and refresh.") }
            } else if (filtered.isEmpty()) item {
                EmptyPanel("No matching articles", "Change your search or remove one of the filters.")
            } else items(filtered, key = { it.id }, contentType = { "article" }) { article ->
                NewsCard(
                    article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark,
                    relevanceOverride = relevanceById[article.id]
                )
            }
        }
    }
    if (showFilters) {
        ModalBottomSheet(
            onDismissRequest = { showFilters = false },
            sheetState = filterSheetState,
            shape = glassFilterSheetShape,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            tonalElevation = 0.dp,
            dragHandle = null
        ) {
            PrismalTransientSurface(
                backdrop = backdrop,
                modifier = Modifier.fillMaxWidth(),
                glassAlpha = glassAlpha,
                dark = dark,
                cornerRadiusDp = 28f
            ) {
                Column(Modifier.fillMaxWidth()) {
                    Box(
                        Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            Modifier.size(width = 32.dp, height = 4.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.30f))
                        )
                    }
                    Column(
                        Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(9.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("Refine articles", style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold)
                                Text("Filters update the results immediately.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary)
                            }
                            TextButton(onClick = {
                                selectedCategory = "All"
                                selectedGsPaper = "All"
                                selectedSubject = "All"
                            }) { Text("Reset") }
                        }
                        FilterChipRow("Topic", categories, selectedCategory, { selectedCategory = it })
                        FilterChipRow("GS paper", gsPapers, selectedGsPaper, { selectedGsPaper = it })
                        FilterChipRow("Subject", subjects, selectedSubject, { selectedSubject = it })
                        Button(onClick = { showFilters = false }, modifier = Modifier.fillMaxWidth()) {
                            Text("Done")
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SearchMorphField(
    modifier: Modifier,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    luminance: Float,
    adaptiveContentColor: Color,
    collapsedIconOffsetPx: Float,
    query: String,
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester,
    enabled: Boolean,
    morphProgress: Float,
    reduceMotion: Boolean,
    onSubmit: () -> Unit,
    onClear: () -> Unit
) {
    val density = LocalDensity.current
    val reduceTransparency = LocalGlassReduceTransparency.current
    val progress = morphProgress.coerceIn(0f, 1f)
    val optics = GlassOpticsPolicy.search(progress)
    val glassTint = if (adaptiveContentColor == Color.Black) Color(0xFFF5F8FC) else Color(0xFF202B3A)
    val tintAlpha = GlassRenderingPolicy.tintAlpha(
        GlassOpticsPolicy.adaptiveTintAlpha(
            baseAlpha = glassAlpha * optics.tintAlpha,
            luminance = luminance,
            minAlpha = 0.04f,
            maxAlpha = 0.12f
        ),
        reduceTransparency
    )
    val iconOffset = with(density) {
        SearchMorphGeometryPolicy.iconOffset(collapsedIconOffsetPx, progress).toDp()
    }

    Box(modifier) {
        Box(
            Modifier.fillMaxSize()
                .graphicsLayer { alpha = SearchMorphGeometryPolicy.fieldAlpha(progress) }
                .drawPrismalGlass(
                backdrop = backdrop,
                shape = { PrismalCapsule() },
                effects = {
                    applyPrismalGlassEffects(
                        density = density,
                        adaptiveLuminance = true,
                        luminance = luminance,
                        blurRadiusPx = with(density) { GlassRenderingPolicy.blurDp(optics.blurDp, reduceTransparency).dp.toPx() },
                        refractionHeightPx = with(density) { GlassRenderingPolicy.refractionDp(optics.refractionHeightDp, reduceTransparency).dp.toPx() },
                        refractionAmountPx = with(density) { GlassRenderingPolicy.refractionDp(optics.refractionAmountDp, reduceTransparency).dp.toPx() },
                        depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
                        chromaticAberration = GlassRenderingPolicy.chromaticAberration(optics.chromaticAberration, reduceTransparency),
                        adaptiveTuning = PrismalAdaptiveTuning.Subtle
                    )
                },
                specular = {
                    PrismalSpecular.Default.copy(
                        width = (0.55f + 0.20f * progress).dp,
                        blurRadius = (0.30f + 0.15f * progress).dp,
                        alpha = GlassRenderingPolicy.specularAlpha(optics.specularAlpha, reduceTransparency)
                    )
                },
                depthShadow = {
                    PrismalDepthShadow(
                        radius = (12f + 6f * progress).dp,
                        offset = DpOffset(0.dp, (2f + progress).dp),
                        color = Color.Black.copy(alpha = 0.12f),
                        alpha = GlassRenderingPolicy.shadowAlpha(optics.shadowAlpha, reduceTransparency)
                    )
                },
                depthInset = {
                    PrismalDepthInset(
                        radius = (4f + progress).dp,
                        offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.07f),
                        alpha = GlassRenderingPolicy.shadowAlpha(optics.insetAlpha, reduceTransparency)
                    )
                },
                onDrawSurface = {
                    drawRect(glassTint.copy(alpha = tintAlpha))
                }
            )
        )

        Row(
            Modifier.fillMaxSize()
                .padding(start = 13.dp, end = 6.dp)
                .graphicsLayer { alpha = SearchMorphGeometryPolicy.fieldAlpha(progress) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                Icons.Outlined.Search,
                contentDescription = "Search",
                tint = adaptiveContentColor,
                modifier = Modifier.offset(x = iconOffset).size(20.dp)
            )
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f)
                    .focusRequester(focusRequester)
                    .graphicsLayer { alpha = progress },
                enabled = enabled,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = adaptiveContentColor),
                cursorBrush = SolidColor(adaptiveContentColor),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSubmit() }),
                decorationBox = { inner ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (query.isBlank()) {
                            Text(
                                "Search articles",
                                color = adaptiveContentColor.copy(alpha = 0.64f),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        inner()
                    }
                }
            )
            if (progress > 0.34f) {
                // Reserve the trailing hit-target slot while expanded so clearing the
                // query does not move the text field or cursor horizontally.
                Row(
                    modifier = Modifier.size(48.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // AnimatedVisibility's RowScope overload requires a Row receiver.
                    // The fixed outer slot preserves the text field's width while the
                    // clear affordance fades in/out.
                    AnimatedVisibility(
                        visible = query.isNotBlank(),
                        enter = fadeIn(if (reduceMotion) snap() else tween(120)),
                        exit = fadeOut(if (reduceMotion) snap() else tween(90))
                    ) {
                        IconButton(
                            onClick = onClear,
                            enabled = enabled,
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = "Clear search",
                                tint = adaptiveContentColor
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SavedFeed(
    articles: List<Article>,
    bookmarks: Set<String>,
    readHistory: Set<String>,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit,
    backdrop: PrismalBackdrop,
    glassAlpha: Float
) {
    var dateRange by remember { mutableStateOf(StatsDateRange.ALL_TIME) }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val savedArticles = remember(articles, bookmarks) {
        articles.filter { bookmarks.contains(it.url) }
    }
    // Relevance depends only on ID/title/description. Reuse one index for filter choices,
    // filtering and visible cards; date/source/image updates do not rerun the keyword engine.
    val classificationInputs = remember(savedArticles) {
        ArticleRelevanceIndexPolicy.keys(savedArticles)
    }
    val relevanceById = remember(classificationInputs) {
        ArticleRelevanceIndexPolicy.build(savedArticles)
    }
    val subjects = remember(relevanceById) {
        listOf("All") + relevanceById.values.map { it.subject }.distinct().sorted()
    }
    val filtered = remember(savedArticles, relevanceById, dateRange, selectedSubject, selectedGsPaper, today) {
        StudyStatsPolicy.filterArticles(
            savedArticles, dateRange, selectedSubject, selectedGsPaper, today,
            relevanceByArticleId = relevanceById
        )
    }
    val previouslyOpened = remember(articles, readHistory, bookmarks) {
        articles.filter {
            readHistory.contains(it.url) && !bookmarks.contains(it.url) && UpscFeedPolicy.shouldInclude(it)
        }
            .sortedByDescending { it.publishedAtMillis ?: 0L }.take(15)
    }

    Column(Modifier.fillMaxSize()) {
        GlassFilterGroup(backdrop = backdrop, glassAlpha = glassAlpha) {
            CompactFilterRow("Published", StatsDateRange.values().map { it.label }, dateRange.label) { label ->
                dateRange = StatsDateRange.values().first { it.label == label }
            }
            CompactFilterRow(
                "GS paper",
                listOf("All", "GS I", "GS II", "GS III", "GS IV", "Unmapped"),
                selectedGsPaper,
                { selectedGsPaper = it }
            )
            CompactFilterRow("Subject", subjects, selectedSubject) { selectedSubject = it }
        }
        LazyColumn(
            Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("SAVED ARTICLES", color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp)
            }
            if (savedArticles.isEmpty()) item {
                SupportingEmptyPanel("Nothing saved yet", "Tap the bookmark on any story to keep it here.")
            } else if (filtered.isEmpty()) item {
                SupportingEmptyPanel("No saved articles match", "Change the publication-date, subject or GS-paper filter.")
            } else items(filtered, key = { it.id }, contentType = { "article" }) { article ->
                NewsCard(
                    article, true, onOpenArticle, onToggleBookmark,
                    relevanceOverride = relevanceById[article.id]
                )
            }
            if (previouslyOpened.isNotEmpty()) {
                item {
                    Text("PREVIOUSLY OPENED", color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp,
                        modifier = Modifier.padding(top = 12.dp))
                    Text("Older read markers are preserved, but their original read dates and durations were not recorded.",
                        style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                }
                items(previouslyOpened, key = { "opened-" + it.id }) { article ->
                    NewsCard(article, false, onOpenArticle, onToggleBookmark)
                }
            }
        }
    }
}

@Composable
private fun YouDashboard(
    totalUniqueRead: Int,
    articlesReadToday: Int,
    savedCount: Int,
    attempts: List<QuizAttempt>,
    onSaved: () -> Unit,
    onReadingStats: () -> Unit,
    onQuizStats: () -> Unit,
    onSettings: () -> Unit
) {
    val answersAttempted = remember(attempts) { attempts.sumOf { it.answeredCount } }
    val correctAnswers = remember(attempts) { attempts.sumOf { it.correctCount } }
    val accuracy = if (answersAttempted == 0) "—"
        else ((correctAnswers * 100f / answersAttempted).toInt().toString() + "%")
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 112.dp),
        verticalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.SECTION_GAP_DP.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text("YOUR ACTIVITY", color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp)
                Text("A private snapshot of your reading and practice.",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.METRIC_GAP_DP.dp)) {
                MetricTile("Articles read", totalUniqueRead.toString(), Modifier.weight(1f))
                MetricTile("Read today", articlesReadToday.toString(), Modifier.weight(1f))
            }
        }
        if (attempts.isEmpty()) {
            item {
                SupportingGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.fillMaxWidth().padding(SupportingCardGeometryPolicy.CONTENT_PADDING_DP.dp),
                        verticalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.CONTENT_GAP_DP.dp)) {
                        Text("Practice when you're ready",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface)
                        Text(
                            "Reading checks you submit from articles will appear here. Unsubmitted answers are never counted.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.METRIC_GAP_DP.dp)) {
                    MetricTile("Quiz attempts", attempts.size.toString(), Modifier.weight(1f))
                    MetricTile("Accuracy", accuracy, Modifier.weight(1f))
                }
            }
        }
        item {
            Text("YOUR LIBRARY", color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp,
                modifier = Modifier.padding(top = 4.dp))
        }
        item { DestinationRow("Saved", "Your bookmarked articles", savedCount.toString(), onSaved) }
        item { DestinationRow("Reading Stats", "Daily reading time and article activity", "›", onReadingStats) }
        item {
            DestinationRow(
                "Quiz & Practice Stats",
                if (attempts.isEmpty()) "Submitted practice results will appear here"
                else "$answersAttempted answers · $correctAnswers correct",
                "›", onQuizStats
            )
        }
        item { DestinationRow("Settings", "Liquid Glass appearance", "›", onSettings) }
    }
}

@Composable
private fun SupportingGlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val dark = isSystemInDarkTheme()
    val glassAlpha = LocalGlassIntensity.current
    val shape = remember {
        PrismalRoundedRectangle(GlassSurfacePolicy.SUPPORTING_CORNER_RADIUS_DP.dp)
    }
    val tintAlpha = GlassSurfacePolicy.supportingTintAlpha(dark, glassAlpha)
    val rimAlpha = GlassSurfacePolicy.supportingRimAlpha(dark, glassAlpha)
    val rimBrush = remember(colors.primary, colors.outline, dark, rimAlpha) {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = if (dark) 0.24f else 0.64f),
                colors.primary.copy(alpha = rimAlpha),
                colors.outline.copy(alpha = if (dark) 0.30f else 0.18f)
            )
        )
    }
    val tintBrush = remember(colors.primary, colors.surface, dark, tintAlpha) {
        Brush.verticalGradient(
            colors = listOf(
                colors.primary.copy(alpha = tintAlpha),
                Color.Transparent
            )
        )
    }
    Card(
        modifier = modifier.border(1.dp, rimBrush, shape),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = colors.surface.copy(
                alpha = GlassSurfacePolicy.supportingSurfaceAlpha(dark)
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(Modifier.fillMaxWidth().background(tintBrush)) {
            content()
        }
    }
}

@Composable
private fun MetricTile(label: String, value: String, modifier: Modifier = Modifier) {
    SupportingGlassCard(modifier = modifier.heightIn(min = SupportingCardGeometryPolicy.METRIC_MIN_HEIGHT_DP.dp)) {
        Column(
            Modifier.fillMaxWidth().padding(SupportingCardGeometryPolicy.CONTENT_PADDING_DP.dp),
            verticalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.CONTENT_GAP_DP.dp)
        ) {
            Text(
                value,
                style = MaterialTheme.typography.titleLarge,
                fontSize = GlassSurfacePolicy.SUPPORTING_VALUE_SIZE_SP.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun DestinationRow(title: String, description: String, trailing: String, onClick: () -> Unit) {
    SupportingGlassCard(modifier = Modifier.fillMaxWidth().heightIn(min = SupportingCardGeometryPolicy.DESTINATION_MIN_HEIGHT_DP.dp)) {
        Row(
            Modifier.fillMaxWidth()
                .clickable(role = Role.Button, onClick = onClick)
                .padding(SupportingCardGeometryPolicy.CONTENT_PADDING_DP.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(description, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary)
            }
            Text(trailing, style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary, maxLines = 1, softWrap = false)
        }
    }
}

@Composable
private fun SupportingEmptyPanel(title: String, description: String) {
    SupportingGlassCard(
        modifier = Modifier.fillMaxWidth()
            .heightIn(min = SupportingCardGeometryPolicy.EMPTY_MIN_HEIGHT_DP.dp)
    ) {
        Column(
            Modifier.fillMaxWidth()
                .padding(SupportingCardGeometryPolicy.CONTENT_PADDING_DP.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.CONTENT_GAP_DP.dp)
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun GlassFilterPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    glassAlpha: Float? = null,
    reduceTransparency: Boolean? = null
) {
    val colors = MaterialTheme.colorScheme
    val intensity = glassAlpha ?: LocalGlassIntensity.current
    val lowEffects = reduceTransparency ?: LocalGlassReduceTransparency.current
    val fillAlpha = GlassSurfacePolicy.filterPillFillAlpha(intensity, selected, lowEffects)
    val borderAlpha = GlassSurfacePolicy.filterPillBorderAlpha(intensity, selected, lowEffects)
    val shape = glassControlShape
    val fill = (if (selected) colors.primary else colors.surface).copy(alpha = fillAlpha)
    val rim = (if (selected) colors.primary else colors.outline).copy(alpha = borderAlpha)

    Box(
        Modifier.clip(shape)
            .background(fill)
            .border(1.dp, rim, shape)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .widthIn(min = TouchTargetPolicy.MIN_ICON_TARGET_DP.dp)
            .heightIn(min = TouchTargetPolicy.MIN_ICON_TARGET_DP.dp)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            label,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (selected) colors.onSurface else colors.onSurfaceVariant,
            maxLines = 1,
            softWrap = false
        )
    }
}

@Composable
private fun GlassActionPill(
    label: String,
    selected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val intensity = LocalGlassIntensity.current
    val reduceTransparency = LocalGlassReduceTransparency.current
    val fillAlpha = GlassSurfacePolicy.actionPillFillAlpha(intensity, selected, reduceTransparency)
    val borderAlpha = GlassSurfacePolicy.actionPillBorderAlpha(intensity, selected, reduceTransparency)
    val shape = remember { RoundedCornerShape(GlassSurfacePolicy.FILTER_PILL_CORNER_RADIUS_DP.dp) }
    val fill = (if (selected) colors.primary else colors.surface).copy(alpha = fillAlpha)
    val rim = (if (selected) colors.primary else colors.outline).copy(alpha = borderAlpha)

    Row(
        Modifier.clip(shape)
            .background(fill)
            .border(1.dp, rim, shape)
            .clickable(role = Role.Button, onClick = onClick)
            .heightIn(min = TouchTargetPolicy.MIN_ICON_TARGET_DP.dp)
            .semantics {
                stateDescription = if (selected) "Filters active" else "No filters active"
            }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = if (selected) colors.primary else colors.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
        )
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            color = if (selected) colors.onSurface else colors.onSurfaceVariant,
            maxLines = 1,
            softWrap = false
        )
    }
}

@Composable
private fun FilterChipRow(
    title: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    val distinctOptions = remember(options) { options.distinct() }
    Column(Modifier.fillMaxWidth().padding(top = 3.dp)) {
        Text(title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 16.dp, bottom = 2.dp))
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 13.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            distinctOptions.forEach { option ->
                val isSelected = selected == option
                GlassFilterPill(
                    label = option,
                    selected = isSelected,
                    onClick = { onSelected(option) }
                )
            }
        }
    }
}

@Composable
private fun PrismalTransientSurface(
    backdrop: PrismalBackdrop,
    modifier: Modifier = Modifier,
    glassAlpha: Float,
    dark: Boolean,
    cornerRadiusDp: Float,
    content: @Composable () -> Unit
) {
    val reduceTransparency = LocalGlassReduceTransparency.current
    PrismalGlassSurface(
        backdrop = backdrop,
        modifier = modifier,
        shape = { PrismalRoundedRectangle(cornerRadiusDp.dp) },
        blurRadius = GlassRenderingPolicy.blurDp(
            GlassSurfacePolicy.TRANSIENT_BLUR_DP, reduceTransparency
        ).dp,
        refractionHeightPx = GlassRenderingPolicy.refractionPx(
            GlassSurfacePolicy.TRANSIENT_REFRACTION_HEIGHT_DP, reduceTransparency
        ),
        refractionAmountPx = GlassRenderingPolicy.refractionPx(
            GlassSurfacePolicy.TRANSIENT_REFRACTION_AMOUNT_DP, reduceTransparency
        ),
        depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
        chromaticAberration = GlassRenderingPolicy.chromaticAberration(0.001f, reduceTransparency),
        tint = if (dark) Color(0xFF26364B) else Color(0xFFEAF0FA),
        tintAlpha = GlassRenderingPolicy.tintAlpha(
            (glassAlpha * GlassSurfacePolicy.TRANSIENT_TINT_SCALE).coerceIn(0.05f, 0.22f),
            reduceTransparency
        )
    ) {
        content()
    }
}

@Composable
private fun GlassFilterGroup(
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val dark = isSystemInDarkTheme()
    val reduceTransparency = LocalGlassReduceTransparency.current
    val surfaceColor = MaterialTheme.colorScheme.surface
    Box(
        Modifier.fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .drawPrismalGlass(
                backdrop = backdrop,
                shape = { PrismalRoundedRectangle(GlassSurfacePolicy.SUPPORTING_CORNER_RADIUS_DP.dp) },
                effects = {
                    applyPrismalGlassEffects(
                        density = density,
                        adaptiveLuminance = true,
                        luminance = if (dark) 0.10f else 0.90f,
                        blurRadiusPx = with(density) {
                            GlassRenderingPolicy.blurDp(5f, reduceTransparency).dp.toPx()
                        },
                        refractionHeightPx = with(density) {
                            GlassRenderingPolicy.refractionDp(2.5f, reduceTransparency).dp.toPx()
                        },
                        refractionAmountPx = with(density) {
                            GlassRenderingPolicy.refractionDp(3.5f, reduceTransparency).dp.toPx()
                        },
                        depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
                        chromaticAberration = GlassRenderingPolicy.chromaticAberration(0.001f, reduceTransparency),
                        adaptiveTuning = PrismalAdaptiveTuning.Subtle
                    )
                },
                specular = {
                    PrismalSpecular.Default.copy(
                        width = 0.42.dp,
                        blurRadius = 0.26.dp,
                        alpha = GlassRenderingPolicy.specularAlpha(0.15f, reduceTransparency)
                    )
                },
                depthShadow = {
                    PrismalDepthShadow(
                        radius = 8.dp,
                        offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.06f),
                        alpha = GlassRenderingPolicy.shadowAlpha(0.10f, reduceTransparency)
                    )
                },
                depthInset = {
                    PrismalDepthInset(
                        radius = 3.dp,
                        offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.04f),
                        alpha = GlassRenderingPolicy.shadowAlpha(0.04f, reduceTransparency)
                    )
                },
                onDrawSurface = {
                    drawRect(
                        surfaceColor.copy(
                            alpha = GlassRenderingPolicy.tintAlpha(glassAlpha * 0.10f, reduceTransparency)
                        )
                    )
                }
            )
    ) {
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            content()
        }
    }
}

@Composable
private fun CompactFilterRow(
    title: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    val distinctOptions = remember(options) { options.distinct() }
    Row(
        Modifier.fillMaxWidth().heightIn(min = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            title,
            modifier = Modifier.width(72.dp).padding(start = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            Modifier.weight(1f).horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            distinctOptions.forEach { option ->
                val isSelected = selected == option
                GlassFilterPill(
                    label = option,
                    selected = isSelected,
                    onClick = { onSelected(option) }
                )
            }
        }
    }
}

@Composable
private fun ReadingStatsScreen(
    sessions: List<ReadingSession>,
    backdrop: PrismalBackdrop,
    glassAlpha: Float
) {
    var dateRange by remember { mutableStateOf(StatsDateRange.LAST_7_DAYS) }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val subjects = remember(sessions) {
        listOf("All") + sessions.map { it.subject }.distinct().sorted()
    }
    val summary = remember(sessions, dateRange, selectedSubject, selectedGsPaper, today) {
        StudyStatsPolicy.readingSummary(
            sessions, dateRange, selectedSubject, selectedGsPaper, today
        )
    }
    Column(Modifier.fillMaxSize()) {
        GlassFilterGroup(backdrop = backdrop, glassAlpha = glassAlpha) {
            CompactFilterRow("Date", StatsDateRange.values().map { it.label }, dateRange.label) { label ->
                dateRange = StatsDateRange.values().first { it.label == label }
            }
            CompactFilterRow(
                "GS paper",
                listOf("All", "GS I", "GS II", "GS III", "GS IV", "Unmapped"),
                selectedGsPaper,
                { selectedGsPaper = it }
            )
            CompactFilterRow("Subject", subjects, selectedSubject) { selectedSubject = it }
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.METRIC_GAP_DP.dp)) {
            MetricTile("Reading time", formatDuration(summary.totalDurationMillis), Modifier.weight(1f))
            MetricTile("Unique articles", summary.uniqueArticleCount.toString(), Modifier.weight(1f))
            MetricTile("Sessions", summary.sessionCount.toString(), Modifier.weight(1f))
        }
        if (summary.days.isEmpty()) {
            SupportingEmptyPanel("No timed reading sessions in this range",
                "Open an article and keep it in the foreground. Older read markers have no historical date or duration.")
        } else {
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                item {
                    Text("TIME SPENT PER DAY", color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.3.sp)
                }
                items(summary.days, key = { it.date.toString() }) { day ->
                    SupportingGlassCard(modifier = Modifier.fillMaxWidth().heightIn(min = SupportingCardGeometryPolicy.STAT_ROW_MIN_HEIGHT_DP.dp)) {
                        Row(Modifier.fillMaxWidth().padding(SupportingCardGeometryPolicy.CONTENT_PADDING_DP.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                Text(day.date.format(DateTimeFormatter.ofPattern("EEE, d MMM", Locale.ENGLISH)),
                                    fontWeight = FontWeight.SemiBold)
                                Text(day.articleCount.toString() + " unique articles",
                                    style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                            }
                            Text(formatDuration(day.durationMillis), fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuizStatsScreen(
    attempts: List<QuizAttempt>,
    backdrop: PrismalBackdrop,
    glassAlpha: Float
) {
    var dateRange by remember { mutableStateOf(StatsDateRange.ALL_TIME) }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val subjects = remember(attempts) {
        listOf("All") + attempts.map { it.subject }.distinct().sorted()
    }
    val summary = remember(attempts, dateRange, selectedSubject, selectedGsPaper, today) {
        StudyStatsPolicy.quizSummary(
            attempts, dateRange, selectedSubject, selectedGsPaper, today
        )
    }
    val accuracy = if (summary.answersAttempted == 0) "—" else
        ((summary.correctAnswers * 100f / summary.answersAttempted).toInt().toString() + "%")
    Column(Modifier.fillMaxSize()) {
        GlassFilterGroup(backdrop = backdrop, glassAlpha = glassAlpha) {
            CompactFilterRow("Date", StatsDateRange.values().map { it.label }, dateRange.label) { label ->
                dateRange = StatsDateRange.values().first { it.label == label }
            }
            CompactFilterRow(
                "GS paper",
                listOf("All", "GS I", "GS II", "GS III", "GS IV", "Unmapped"),
                selectedGsPaper,
                { selectedGsPaper = it }
            )
            CompactFilterRow("Subject", subjects, selectedSubject) { selectedSubject = it }
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.METRIC_GAP_DP.dp)) {
            MetricTile("Attempts", summary.attemptCount.toString(), Modifier.weight(1f))
            MetricTile("Answered", summary.answersAttempted.toString(), Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.METRIC_GAP_DP.dp)) {
            MetricTile("Correct", summary.correctAnswers.toString(), Modifier.weight(1f))
            MetricTile("Incorrect", summary.wrongAnswers.toString(), Modifier.weight(1f))
            MetricTile("Accuracy", accuracy, Modifier.weight(1f))
        }
        if (summary.attempts.isEmpty()) {
            SupportingEmptyPanel("No submitted quizzes in this range",
                "Quiz statistics are saved only after you submit answers. Unsubmitted selections are not counted.")
        } else {
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                items(summary.attempts, key = { it.id }) { attempt ->
                    SupportingGlassCard(modifier = Modifier.fillMaxWidth().heightIn(min = SupportingCardGeometryPolicy.STAT_ROW_MIN_HEIGHT_DP.dp)) {
                        Column(Modifier.fillMaxWidth().padding(SupportingCardGeometryPolicy.CONTENT_PADDING_DP.dp), verticalArrangement = Arrangement.spacedBy(SupportingCardGeometryPolicy.CONTENT_GAP_DP.dp)) {
                            Text(attempt.articleTitle, fontWeight = FontWeight.SemiBold,
                                maxLines = 2, overflow = TextOverflow.Ellipsis)
                            Text(
                                Instant.ofEpochMilli(attempt.submittedAtMillis).atZone(ZoneId.of(INDIA_ZONE))
                                    .format(DateTimeFormatter.ofPattern("d MMM yyyy · h:mm a", Locale.ENGLISH)),
                                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary
                            )
                            Text(attempt.gsPaper + " · " + attempt.subject,
                                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                            Text(
                                attempt.answeredCount.toString() + "/" + attempt.totalQuestions + " answered  ·  " +
                                    attempt.correctCount + " correct  ·  " + attempt.wrongCount + " incorrect",
                                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatDuration(durationMillis: Long): String {
    val totalMinutes = durationMillis.coerceAtLeast(0L) / 60_000L
    val hours = totalMinutes / 60L
    val minutes = totalMinutes % 60L
    return when {
        hours > 0L -> hours.toString() + "h " + minutes.toString() + "m"
        totalMinutes > 0L -> totalMinutes.toString() + "m"
        durationMillis > 0L -> "<1m"
        else -> "0m"
    }
}

@Composable
private fun NewsCard(
    article: Article,
    saved: Boolean,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit,
    relevanceOverride: Relevance? = null
) {
    val context = LocalContext.current
    val relevance = remember(article.id, article.title, article.description, relevanceOverride) {
        relevanceOverride ?: UpscEngine.classify(article)
    }
    val syllabusHint = remember(article.id, article.title, article.description, relevance.gsPaper, relevance.subject, relevance.matchedTerms, relevance.syllabusLink) {
        ArticleCardMetaPolicy.syllabusHint(
            gsPaper = relevance.gsPaper,
            subject = relevance.subject,
            matchedTerms = relevance.matchedTerms,
            syllabusLink = relevance.syllabusLink
        )
    }
    val hasImage = !article.imageUrl.isNullOrBlank()
    val imageRequest = remember(article.imageUrl) {
        article.imageUrl?.let { url ->
            ImageRequest.Builder(context).data(url)
                .size(960, 640)
                .allowHardware(false)
                .crossfade(false)
                .build()
        }
    }
    var imagePalette by remember(article.id, article.imageUrl) { mutableStateOf<Color?>(null) }
    val palette = imagePalette

    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = FeedCardGeometryPolicy.CARD_VERTICAL_INSET_DP.dp)
            .graphicsLayer { clip = false }
            // Cache the palette glow's brush and geometry by card size/palette rather
            // than allocating a new radial gradient on every draw invalidation.
            .drawWithCache {
                val sampled = palette
                val overscan = 24.dp.toPx()
                val glowTopLeft = Offset(-overscan, -overscan)
                val glowSize = Size(size.width + overscan * 2f, size.height + overscan * 2f)
                val glowCornerRadius = CornerRadius(34.dp.toPx())
                val glowBrush = sampled?.let {
                    Brush.radialGradient(
                        colors = listOf(
                            it.copy(alpha = 0.30f),
                            it.copy(alpha = 0.16f),
                            Color.Transparent
                        ),
                        center = Offset(size.width * 0.5f, size.height * 0.40f),
                        radius = maxOf(size.width, size.height) * 0.82f
                    )
                }
                onDrawBehind {
                    if (glowBrush != null) {
                        drawRoundRect(
                            brush = glowBrush,
                            topLeft = glowTopLeft,
                            size = glowSize,
                            cornerRadius = glowCornerRadius
                        )
                    }
                }
            }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onOpenArticle(article) },
            shape = ContinuousCornerShape(FeedCardGeometryPolicy.CARD_CORNER_RADIUS_DP.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = if (palette != null) 0.94f else 1f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column {
                if (hasImage && imageRequest != null) {
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = article.title,
                        contentScale = ContentScale.Crop,
                        onSuccess = { state ->
                            val sampled = runCatching {
                                val bitmap = (state.result.drawable as? BitmapDrawable)?.bitmap
                                if (bitmap != null && !bitmap.isRecycled) sampleImagePalette(bitmap) else null
                            }.getOrNull()
                            if (sampled != null && imagePalette != sampled) imagePalette = sampled
                        },
                        modifier = Modifier.fillMaxWidth()
                            .aspectRatio(FeedCardGeometryPolicy.IMAGE_ASPECT_RATIO)
                            .clip(ContinuousCornerShape(FeedCardGeometryPolicy.IMAGE_CORNER_RADIUS_DP.dp))
                    )
                }
                Column(
                    Modifier.padding(
                        horizontal = FeedCardGeometryPolicy.CONTENT_HORIZONTAL_PADDING_DP.dp,
                        vertical = FeedCardGeometryPolicy.CONTENT_VERTICAL_PADDING_DP.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(FeedCardGeometryPolicy.CONTENT_VERTICAL_SPACING_DP.dp)
                ) {
                    Row(
                        Modifier.heightIn(min = FeedCardGeometryPolicy.METADATA_ACTION_TARGET_DP.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(article.source.uppercase(), color = MaterialTheme.colorScheme.primary,
                            fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.65.sp)
                        Text("  ·  " + article.section, color = MaterialTheme.colorScheme.secondary,
                            fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { onToggleBookmark(article) }, modifier = Modifier.size(TouchTargetPolicy.iconTargetDp(40).dp)) {
                            Icon(if (saved) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = if (saved) "Remove bookmark" else "Save article",
                                tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(21.dp))
                        }
                    }
                    Row(
                        Modifier.fillMaxWidth().heightIn(min = FeedCardGeometryPolicy.METADATA_TEXT_ROW_MIN_HEIGHT_DP.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(ArticlePublicationDatePolicy.compactLabel(article.publishedAtMillis),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Spacer(Modifier.weight(1f))
                        Text(
                            ArticleProvenancePolicy.label(article.source, article.feedUrl),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1, overflow = TextOverflow.Ellipsis
                        )
                    }
                    SelectionContainer {
                        Column(verticalArrangement = Arrangement.spacedBy(FeedCardGeometryPolicy.TITLE_DESCRIPTION_SPACING_DP.dp)) {
                            Text(article.title, style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold, lineHeight = FeedCardGeometryPolicy.TITLE_LINE_HEIGHT_SP.sp)
                            if (article.description.isNotBlank()) {
                                Text(article.description, style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 4,
                                    overflow = TextOverflow.Ellipsis, lineHeight = FeedCardGeometryPolicy.DESCRIPTION_LINE_HEIGHT_SP.sp)
                            }
                        }
                    }
                    syllabusHint?.let { hint ->
                        Text(hint, style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold,
                            maxLines = 4, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
        }
    }
}

private data class ContinuousCornerShape(private val radius: androidx.compose.ui.unit.Dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): Outline {
        val r = with(density) { radius.toPx() }.coerceIn(0f, minOf(size.width, size.height) / 2f)
        val handle = r * 0.43f
        val path = Path().apply {
            moveTo(0f, r)
            cubicTo(0f, r - handle, r - handle, 0f, r, 0f)
            lineTo(size.width - r, 0f)
            cubicTo(size.width - r + handle, 0f, size.width, r - handle, size.width, r)
            lineTo(size.width, size.height - r)
            cubicTo(size.width, size.height - r + handle, size.width - r + handle, size.height, size.width - r, size.height)
            lineTo(r, size.height)
            cubicTo(r - handle, size.height, 0f, size.height - r + handle, 0f, size.height - r)
            close()
        }
        return Outline.Generic(path)
    }
}

private val glassControlShape =
    ContinuousCornerShape(GlassSurfacePolicy.CONTROL_CORNER_RADIUS_DP.dp)
private val glassFilterSheetShape =
    ContinuousCornerShape(GlassSurfacePolicy.FILTER_SHEET_CORNER_RADIUS_DP.dp)
private val readerTextSizeMenuShape =
    ContinuousCornerShape(GlassSurfacePolicy.READER_MENU_CORNER_RADIUS_DP.dp)

private fun sampleImagePalette(bitmap: Bitmap): Color {
    if (bitmap.width <= 0 || bitmap.height <= 0) return Color(0xFF527AA8)
    val positions = arrayOf(
        0.12f to 0.16f, 0.50f to 0.16f, 0.88f to 0.16f,
        0.12f to 0.50f, 0.50f to 0.50f, 0.88f to 0.50f,
        0.12f to 0.84f, 0.50f to 0.84f, 0.88f to 0.84f
    )
    var red = 0f
    var green = 0f
    var blue = 0f
    var totalWeight = 0f
    positions.forEach { (xRatio, yRatio) ->
        val pixel = bitmap.getPixel(
            (bitmap.width * xRatio).toInt().coerceIn(0, bitmap.width - 1),
            (bitmap.height * yRatio).toInt().coerceIn(0, bitmap.height - 1)
        )
        val r = AndroidColor.red(pixel).toFloat()
        val g = AndroidColor.green(pixel).toFloat()
        val b = AndroidColor.blue(pixel).toFloat()
        val saturation = maxOf(r, g, b) - minOf(r, g, b)
        val weight = 1f + saturation / 220f
        red += r * weight
        green += g * weight
        blue += b * weight
        totalWeight += weight
    }
    return Color(AndroidColor.rgb(
        (red / totalWeight).toInt().coerceIn(0, 255),
        (green / totalWeight).toInt().coerceIn(0, 255),
        (blue / totalWeight).toInt().coerceIn(0, 255)
    ))
}

@Composable
private fun SkeletonTextLine(
    widthFraction: Float,
    lineHeightDp: Float,
    barHeightDp: Float,
    color: Color
) {
    Box(
        Modifier.fillMaxWidth(widthFraction).height(lineHeightDp.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            Modifier.fillMaxWidth().height(barHeightDp.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(color)
        )
    }
}

@Composable
private fun FeedSkeletonCard() {
    val base = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.82f)
    val line = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.075f)
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = FeedCardGeometryPolicy.CARD_VERTICAL_INSET_DP.dp)
            .clearAndSetSemantics { },
        shape = ContinuousCornerShape(FeedCardGeometryPolicy.CARD_CORNER_RADIUS_DP.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(
                Modifier.fillMaxWidth()
                    .aspectRatio(FeedCardGeometryPolicy.IMAGE_ASPECT_RATIO)
                    .clip(ContinuousCornerShape(FeedCardGeometryPolicy.IMAGE_CORNER_RADIUS_DP.dp))
                    .background(base)
            )
            Column(
                Modifier.padding(
                    horizontal = FeedCardGeometryPolicy.CONTENT_HORIZONTAL_PADDING_DP.dp,
                    vertical = FeedCardGeometryPolicy.CONTENT_VERTICAL_PADDING_DP.dp
                ),
                verticalArrangement = Arrangement.spacedBy(FeedCardGeometryPolicy.CONTENT_VERTICAL_SPACING_DP.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth()
                        .heightIn(min = FeedCardGeometryPolicy.METADATA_ACTION_TARGET_DP.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.width(86.dp).height(10.dp).clip(CircleShape).background(line))
                    Spacer(Modifier.width(9.dp))
                    Box(Modifier.width(64.dp).height(10.dp).clip(CircleShape).background(line))
                    Spacer(Modifier.weight(1f))
                    Box(
                        Modifier.size(FeedCardGeometryPolicy.METADATA_ACTION_TARGET_DP.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(Modifier.size(22.dp).clip(CircleShape).background(line))
                    }
                }
                Row(
                    Modifier.fillMaxWidth()
                        .heightIn(min = FeedCardGeometryPolicy.METADATA_TEXT_ROW_MIN_HEIGHT_DP.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.width(84.dp).height(8.dp).clip(CircleShape).background(line))
                    Spacer(Modifier.weight(1f))
                    Box(Modifier.width(106.dp).height(8.dp).clip(CircleShape).background(line))
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(FeedCardGeometryPolicy.TITLE_DESCRIPTION_SPACING_DP.dp)
                ) {
                    SkeletonTextLine(0.88f, FeedCardGeometryPolicy.TITLE_LINE_HEIGHT_SP, 15f, line)
                    SkeletonTextLine(0.71f, FeedCardGeometryPolicy.TITLE_LINE_HEIGHT_SP, 15f, line)
                    SkeletonTextLine(1f, FeedCardGeometryPolicy.DESCRIPTION_LINE_HEIGHT_SP, 10f, line)
                    SkeletonTextLine(0.82f, FeedCardGeometryPolicy.DESCRIPTION_LINE_HEIGHT_SP, 10f, line)
                }
            }
        }
    }
}
@Composable
private fun LoadingPanel() {
    Column(Modifier.fillMaxWidth().padding(38.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CircularProgressIndicator()
        Text("Gathering today's stories", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun EmptyPanel(title: String, description: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 34.dp, horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
        Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, textAlign = TextAlign.Center)
    }
}

@Composable
private fun GlassTabBar(
    backdrop: PrismalBackdrop,
    selectedTab: Int,
    glassAlpha: Float,
    luminance: Float,
    adaptiveContentColor: Color,
    reduceMotion: Boolean,
    compact: Boolean,
    onExpandCompact: () -> Unit,
    morphProgress: Float,
    onSearchAnchor: (Offset) -> Unit,
    onSelect: (Int) -> Unit
) {
    val density = LocalDensity.current
    val reduceTransparency = LocalGlassReduceTransparency.current
    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr
    val animationScope = rememberCoroutineScope()
    val latestOnSelect = rememberUpdatedState(onSelect)
    // The expanded branch owns the hidden glyph capture and merged droplet source.
    // Keeping them inside that branch disposes both while the compact pill is active.
    val glassTint = if (adaptiveContentColor == Color.Black) Color(0xFFEAF0FA) else Color(0xFF1C2E49)
    val baseTintAlpha = GlassRenderingPolicy.tintAlpha(
        GlassOpticsPolicy.adaptiveTintAlpha(
            baseAlpha = glassAlpha * 0.12f,
            luminance = luminance,
            minAlpha = 0.035f,
            maxAlpha = 0.13f
        ),
        reduceTransparency
    )
    val compactTransition = updateTransition(targetState = compact, label = "Feed navigation compact morph")
    val compactProgress = compactTransition.animateFloat(
        transitionSpec = { if (reduceMotion) snap() else tween(180) },
        label = "Feed navigation compact progress"
    ) { if (it) 1f else 0f }.value.coerceIn(0f, 1f)

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
            .height(70.dp + (54.dp - 70.dp) * compactProgress),
        contentAlignment = Alignment.Center
    ) {
        val fullWidth = maxWidth
        val compactWidth = 148.dp.coerceAtMost(maxWidth)
        val barWidth = fullWidth + (compactWidth - fullWidth) * compactProgress
        val barHeight = 70.dp + (54.dp - 70.dp) * compactProgress
        val barWidthPx = with(density) { barWidth.toPx() }
        val barHeightPx = with(density) { barHeight.toPx() }
        val barOffsetXPx = (constraints.maxWidth.toFloat() - barWidthPx) / 2f
        val tabWidthPx = (barWidthPx - with(density) { 8.dp.toPx() }).div(4f).coerceAtLeast(1f)
        val contentStartPx = barOffsetXPx + with(density) { 4.dp.toPx() }
        val contentHeight = 64.dp + (48.dp - 64.dp) * compactProgress
        val contentHeightPx = with(density) { contentHeight.toPx() }
        val dropletWidth = with(density) {
            GlassTabGeometryPolicy.expandedDimension(tabWidthPx, 12.dp.toPx()).toDp()
        }
        val dropletHeight = with(density) {
            GlassTabGeometryPolicy.expandedDimension(contentHeightPx, 8.dp.toPx()).toDp()
        }
        val dropletWidthPx = with(density) { dropletWidth.toPx() }
        val dropletHeightPx = with(density) { dropletHeight.toPx() }
        val restingScaleX = GlassTabGeometryPolicy.restingScale(tabWidthPx, dropletWidthPx)
        val restingScaleY = GlassTabGeometryPolicy.restingScale(contentHeightPx, dropletHeightPx)
        var dragHighlightIndex by remember { mutableStateOf<Int?>(null) }
        val motion = remember(animationScope, tabWidthPx, isLtr, reduceMotion) {
            PrismalSpringMotion(
                animationScope = animationScope,
                initialValue = selectedTab.toFloat(),
                valueRange = 0f..3f,
                visibilityThreshold = 0.001f,
                initialScale = 1f,
                pressedScale = GlassOpticsPolicy.pressedScale(reduceMotion),
                onDragStarted = {
                    dragHighlightIndex = value.roundToInt().coerceIn(0, 3)
                },
                onDragStopped = {
                    val target = targetValue.roundToInt().coerceIn(0, 3)
                    dragHighlightIndex = target
                    latestOnSelect.value(target)
                },
                onDrag = { _, delta ->
                    val direction = if (isLtr) 1f else -1f
                    val next = (targetValue + delta.x / tabWidthPx * direction).coerceIn(0f, 3f)
                    if (reduceMotion) snapToValue(next) else updateValue(next)
                    dragHighlightIndex = next.roundToInt().coerceIn(0, 3)
                }
            )
        }
        LaunchedEffect(selectedTab, reduceMotion, compact) {
            dragHighlightIndex = null
            if (reduceMotion) motion.snapToValue(selectedTab.toFloat())
            else motion.animateToValue(selectedTab.toFloat())
        }

        val highlightedIndex = dragHighlightIndex ?: selectedTab
        val pillPress = GlassOpticsPolicy.interactionProgress(motion.pressProgress, reduceMotion)
        val pillVelocity = if (reduceMotion) 0f else motion.velocity.coerceIn(-2f, 2f)
        val dropletOptics = GlassOpticsPolicy.droplet(pillPress, reduceMotion)
        val dropletTint = if (adaptiveContentColor == Color.Black) Color(0xFFE8F1FF) else Color(0xFF1C2E49)
        val dropletTintAlpha = GlassRenderingPolicy.tintAlpha(
            GlassOpticsPolicy.adaptiveTintAlpha(
                baseAlpha = glassAlpha * dropletOptics.tintAlpha,
                luminance = luminance,
                minAlpha = 0.045f,
                maxAlpha = 0.14f
            ),
            reduceTransparency
        )

        val tabRipple = remember(animationScope, tabWidthPx, contentStartPx) {
            PrismalPressRipple(animationScope) { size, _ ->
                val center = GlassTabGeometryPolicy.tabCenterPx(contentStartPx, motion.value, tabWidthPx)
                Offset(center, size.height / 2f)
            }
        }
        val rippleEnabled = GlassOpticsPolicy.rippleEnabled(Build.VERSION.SDK_INT, reduceMotion)
        val rippleDrawModifier = if (rippleEnabled && !compact) tabRipple.modifier else Modifier
        val rippleGestureModifier = if (rippleEnabled && !compact) tabRipple.gestureModifier else Modifier
        val barAlpha = SearchMorphGeometryPolicy.tabBarAlpha(morphProgress)

        // One shared Prismal surface changes bounds with the existing bar. No duplicate
        // expanded/compact layer is composed; live blur remains restricted to this small control.
        Box(
            Modifier.align(Alignment.Center).width(barWidth).height(barHeight)
                .graphicsLayer { alpha = barAlpha }
                .drawPrismalGlass(
                    backdrop = backdrop,
                    shape = { PrismalCapsule() },
                    effects = {
                        applyPrismalGlassEffects(
                            density = density,
                            adaptiveLuminance = true,
                            luminance = luminance,
                            blurRadiusPx = with(density) { GlassRenderingPolicy.blurDp(6f - 2f * compactProgress, reduceTransparency).dp.toPx() },
                            refractionHeightPx = with(density) { GlassRenderingPolicy.refractionDp(14f - 6f * compactProgress, reduceTransparency).dp.toPx() },
                            refractionAmountPx = with(density) { GlassRenderingPolicy.refractionDp(20f - 10f * compactProgress, reduceTransparency).dp.toPx() },
                            depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
                            chromaticAberration = GlassRenderingPolicy.chromaticAberration(0.015f * (1f - compactProgress), reduceTransparency),
                            adaptiveTuning = PrismalAdaptiveTuning.Subtle
                        )
                    },
                    specular = {
                        PrismalSpecular.Default.copy(
                            width = 0.6.dp,
                            blurRadius = 0.4.dp,
                            alpha = GlassRenderingPolicy.specularAlpha(0.48f * (1f - 0.18f * compactProgress), reduceTransparency)
                        )
                    },
                    depthShadow = {
                        PrismalDepthShadow(
                            radius = 18.dp - 4.dp * compactProgress,
                            offset = DpOffset(0.dp, 3.dp),
                            color = Color.Black.copy(alpha = 0.10f),
                            alpha = GlassRenderingPolicy.shadowAlpha(0.28f, reduceTransparency)
                        )
                    },
                    depthInset = {
                        PrismalDepthInset(
                            radius = 4.dp,
                            offset = DpOffset(0.dp, 1.dp),
                            color = Color.Black.copy(alpha = 0.06f),
                            alpha = GlassRenderingPolicy.shadowAlpha(0.06f, reduceTransparency)
                        )
                    },
                    onDrawSurface = { drawRect(glassTint.copy(alpha = baseTintAlpha)) }
                )
        )

        if (!compact) {
            val tabsBackdrop = rememberPrismalGlassLayer()
            val dropletBackdrop = rememberPrismalMergedSource(backdrop, tabsBackdrop)
            Box(
                Modifier.align(Alignment.Center).width(barWidth).height(barHeight)
                    .clip(PrismalCapsule()).then(rippleDrawModifier)
            )
            tabsBackdrop.readSamplingState()
            CompositionLocalProvider(
                LocalPrismalBottomTabHighlightedIndex provides { highlightedIndex }
            ) {
                // Hidden glyph capture is active only in expanded navigation. Compact mode
                // draws no glyph source, droplet, or merged-source sampling layer.
                Row(
                    Modifier.align(Alignment.Center).width(barWidth).height(barHeight)
                        .graphicsLayer { alpha = 0f }
                        .clearAndSetSemantics {}
                        .prismalGlassLayer(tabsBackdrop)
                        .padding(horizontal = 4.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlassTab(0, "Today", Icons.Outlined.Today, onSelect, adaptiveContentColor)
                    GlassTab(1, "Explore", Icons.Outlined.Explore, onSelect, adaptiveContentColor)
                    GlassTab(2, "Search", Icons.Outlined.Search, onSelect, adaptiveContentColor)
                    GlassTab(3, "You", Icons.Outlined.Person, onSelect, adaptiveContentColor)
                }

                Row(
                    Modifier.align(Alignment.Center).width(barWidth).height(barHeight)
                        .padding(horizontal = 4.dp, vertical = 3.dp)
                        .graphicsLayer { alpha = barAlpha },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlassTab(0, "Today", Icons.Outlined.Today, onSelect, adaptiveContentColor)
                    GlassTab(1, "Explore", Icons.Outlined.Explore, onSelect, adaptiveContentColor)
                    GlassTab(2, "Search", Icons.Outlined.Search, onSelect, adaptiveContentColor, onSearchAnchor)
                    GlassTab(3, "You", Icons.Outlined.Person, onSelect, adaptiveContentColor)
                }
            }

            val dropletTopPx = (barHeightPx - dropletHeightPx) / 2f
            Box(
                Modifier.offset {
                    val center = GlassTabGeometryPolicy.tabCenterPx(contentStartPx, motion.value, tabWidthPx)
                    IntOffset(
                        GlassTabGeometryPolicy.leftPx(center, dropletWidthPx).roundToInt(),
                        dropletTopPx.roundToInt()
                    )
                }
                    .size(dropletWidth, dropletHeight)
                    .graphicsLayer {
                        alpha = barAlpha
                        val velocity = (pillVelocity / 10f).coerceIn(-0.2f, 0.2f)
                        scaleX = GlassTabGeometryPolicy.horizontalScale(restingScaleX, motion.scaleX, velocity)
                        scaleY = GlassTabGeometryPolicy.verticalScale(restingScaleY, motion.scaleY, velocity)
                        clip = false
                    }
                    .then(rippleGestureModifier)
                    .then(motion.modifier)
            ) {
                Box(
                    Modifier.fillMaxSize().drawPrismalGlass(
                        backdrop = dropletBackdrop,
                        shape = { PrismalCapsule() },
                        effects = {
                            applyPrismalGlassEffects(
                                density = density,
                                adaptiveLuminance = true,
                                luminance = luminance,
                                blurRadiusPx = with(density) { GlassRenderingPolicy.blurDp(dropletOptics.blurDp, reduceTransparency).dp.toPx() },
                                refractionHeightPx = with(density) { GlassRenderingPolicy.refractionDp(dropletOptics.refractionHeightDp, reduceTransparency).dp.toPx() },
                                refractionAmountPx = with(density) { GlassRenderingPolicy.refractionDp(dropletOptics.refractionAmountDp, reduceTransparency).dp.toPx() },
                                depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
                                chromaticAberration = GlassRenderingPolicy.chromaticAberration(dropletOptics.chromaticAberration, reduceTransparency),
                                adaptiveTuning = PrismalAdaptiveTuning.Subtle
                            )
                        },
                        specular = {
                            PrismalSpecular.Default.copy(
                                width = 0.7.dp,
                                blurRadius = 0.4.dp,
                                alpha = GlassRenderingPolicy.specularAlpha(dropletOptics.specularAlpha, reduceTransparency),
                                style = PrismalSpecularStyle.Default(
                                    angle = GlassOpticsPolicy.specularAngle(pillVelocity, reduceMotion)
                                )
                            )
                        },
                        depthShadow = {
                            PrismalDepthShadow(
                                radius = 12.dp,
                                offset = DpOffset(0.dp, 2.dp),
                                color = Color.Black.copy(alpha = 0.12f),
                                alpha = GlassRenderingPolicy.shadowAlpha(dropletOptics.shadowAlpha, reduceTransparency)
                            )
                        },
                        depthInset = {
                            PrismalDepthInset(
                                radius = 5.dp,
                                offset = DpOffset(0.dp, 1.dp),
                                color = Color.Black.copy(alpha = 0.08f),
                                alpha = GlassRenderingPolicy.shadowAlpha(dropletOptics.insetAlpha, reduceTransparency)
                            )
                        },
                        onDrawSurface = { drawRect(dropletTint.copy(alpha = dropletTintAlpha)) }
                    )
                )
            }

            val activeGlyph = when (highlightedIndex) {
                0 -> Icons.Outlined.Today
                1 -> Icons.Outlined.Explore
                2 -> Icons.Outlined.Search
                else -> Icons.Outlined.Person
            }
            Box(
                Modifier.offset {
                    val center = GlassTabGeometryPolicy.tabCenterPx(contentStartPx, motion.value, tabWidthPx)
                    val iconSizePx = with(density) { 29.dp.toPx() }
                    IntOffset((center - iconSizePx / 2f).roundToInt(),
                        ((barHeightPx - iconSizePx) / 2f).roundToInt())
                }
                    .size(29.dp)
                    .clearAndSetSemantics {},
                contentAlignment = Alignment.Center
            ) {
                Icon(activeGlyph, contentDescription = null, tint = adaptiveContentColor,
                    modifier = Modifier.size(29.dp))
            }
        } else {
            val activeLabel = when (selectedTab) {
                0 -> "Today"
                1 -> "Explore"
                2 -> "Search"
                else -> "You"
            }
            val activeIcon = when (selectedTab) {
                0 -> Icons.Outlined.Today
                1 -> Icons.Outlined.Explore
                2 -> Icons.Outlined.Search
                else -> Icons.Outlined.Person
            }
            Row(
                Modifier.align(Alignment.Center).width(barWidth).height(barHeight)
                    .clip(PrismalCapsule())
                    .clickable(onClick = onExpandCompact, role = Role.Button)
                    .semantics(mergeDescendants = true) {
                        contentDescription = "$activeLabel navigation, compact. Expand navigation."
                    }
                    .padding(horizontal = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(activeIcon, contentDescription = null, tint = adaptiveContentColor, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(9.dp))
                Text(activeLabel, color = adaptiveContentColor, style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold, maxLines = 1)
            }
        }
    }
}

@Composable
private fun RowScope.GlassTab(
    index: Int,
    label: String,
    icon: ImageVector,
    onSelect: (Int) -> Unit,
    adaptiveContentColor: Color,
    onPositioned: ((Offset) -> Unit)? = null
) {
    val highlighted = LocalPrismalBottomTabHighlightedIndex.current()
    val isSelected = highlighted == index
    val tint = if (isSelected) adaptiveContentColor else adaptiveContentColor.copy(alpha = 0.72f)
    val positionModifier = if (onPositioned != null) {
        Modifier.onGloballyPositioned { coordinates ->
            onPositioned(
                coordinates.positionInRoot() + Offset(
                    coordinates.size.width / 2f,
                    coordinates.size.height / 2f
                )
            )
        }
    } else Modifier
    val tabModifier = positionModifier.semantics(mergeDescendants = true) {
        selected = isSelected
        contentDescription = label
    }
    PrismalGlassBottomTab(onClick = { onSelect(index) }, modifier = tabModifier) {
        if (isSelected) Spacer(Modifier.size(29.dp))
        else Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(29.dp))
    }
}

private fun matchesSearchTerms(article: Article, query: String): Boolean =
    ExploreFilters.matchesSearchTerms(article, query)

@Composable
private fun ReaderScreen(
    article: Article,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    dark: Boolean,
    onBack: () -> Unit,
    onQuiz: () -> Unit,
    onSessionWindow: (ReadingSessionWindow) -> Unit,
    onSaveReaderPosition: (String, Float) -> Unit,
    onClearReaderPosition: (String) -> Unit
) {
    val context = LocalContext.current
    val reduceMotion = LocalGlassReduceMotion.current
    val readerWebViewHolder = remember { mutableStateOf<ReaderWebView?>(null) }
    var savedReadingProgress by remember(article.url) { mutableFloatStateOf(0f) }
    var readerPositionLoaded by remember(article.url) { mutableStateOf(false) }
    LaunchedEffect(article.url) {
        readerPositionLoaded = false
        savedReadingProgress = 0f
        val restoredProgress = withContext(Dispatchers.IO) {
            runCatching {
                LocalStore.readerPosition(context, article.url)?.progress ?: 0f
            }.getOrDefault(0f)
        }
        savedReadingProgress = restoredProgress
        readerPositionLoaded = true
    }

    var contentLoaded by remember(article.url) { mutableStateOf(false) }
    val lifecycleOwner = context as? LifecycleOwner
    DisposableEffect(lifecycleOwner, article.url, contentLoaded) {
        if (!contentLoaded) {
            onDispose { readerWebViewHolder.value?.flushReadingPosition() }
        } else {
            val clock = ForegroundReadingSessionClock()
            fun resumeSession() {
                clock.resume(System.currentTimeMillis(), SystemClock.elapsedRealtime())
            }
            fun flushSession() {
                readerWebViewHolder.value?.flushReadingPosition()
                clock.pause(SystemClock.elapsedRealtime())?.let(onSessionWindow)
            }
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> resumeSession()
                    Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY -> flushSession()
                    else -> Unit
                }
            }
            lifecycleOwner?.lifecycle?.addObserver(observer)
            if (lifecycleOwner == null ||
                lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
            ) resumeSession()
            onDispose {
                lifecycleOwner?.lifecycle?.removeObserver(observer)
                readerWebViewHolder.value?.flushReadingPosition()
                flushSession()
            }
        }
    }
    var extractedHtml by remember(article.url) { mutableStateOf<String?>(null) }
    var loadedFromCache by remember(article.url) { mutableStateOf(false) }
    var extracting by remember(article.url) { mutableStateOf(true) }
    var fallbackToPublisher by remember(article.url) { mutableStateOf(false) }
    var pageLoaded by remember(article.url) { mutableStateOf(false) }
    var publisherLoadFailed by remember(article.url) { mutableStateOf(false) }
    var reachedBottom by remember(article.url) { mutableStateOf(false) }
    var activeWord by remember(article.url) { mutableStateOf<String?>(null) }
    var activeWordAnchor by remember(article.url) { mutableStateOf<DefinitionSelectionAnchor?>(null) }
    var activeWordPreferAbove by remember(article.url) { mutableStateOf(false) }
    var readerViewportWindowOrigin by remember(article.url) { mutableStateOf(Offset.Zero) }
    var readerViewportHeightPx by remember(article.url) { mutableIntStateOf(0) }
    var fontSize by remember(article.url) { mutableIntStateOf(20) }
    var textSizeMenuExpanded by remember(article.url) { mutableStateOf(false) }
    var completionDismissed by remember(article.url) { mutableStateOf(false) }
    var selectionActionModeActive by remember(article.url) { mutableStateOf(false) }
    var readerChromeVisible by remember(article.url) { mutableStateOf(true) }
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val accessibilityEnabled = context.getSystemService(AccessibilityManager::class.java)?.isEnabled == true
    val readerChromePinned = ReaderChromePolicy.shouldPinChrome(
        definitionOpen = activeWord != null,
        selectionActive = selectionActionModeActive,
        imeVisible = imeVisible,
        accessibilityEnabled = accessibilityEnabled,
        textSizeMenuExpanded = textSizeMenuExpanded
    )
    val readerChromeMotionMs = if (reduceMotion) 0 else 160

    LaunchedEffect(article.url) {
        extracting = true
        loadedFromCache = false
        try {
            val cached = ArticleReaderContentCache.read(context, article.url)
            if (cached != null) {
                extractedHtml = cached
                loadedFromCache = true
            } else {
                val publicContent = ArticleReaderRepository.extractAccessibleArticle(article.url, article.source)
                extractedHtml = publicContent
                if (publicContent != null) {
                    ArticleReaderContentCache.write(context, article.url, publicContent)
                }
            }
            fallbackToPublisher = extractedHtml == null && PublisherHostPolicy.allows(article.source, article.url)
            if (extractedHtml == null && !fallbackToPublisher) pageLoaded = true
        } finally {
            extracting = false
        }
    }

    val readerSourceStatus = when {
        extractedHtml != null && loadedFromCache -> "Saved public content · on this device"
        extractedHtml != null -> "Public publisher content · attributed"
        fallbackToPublisher -> "Publisher fallback · scripts disabled"
        else -> "Public article unavailable"
    }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AnimatedVisibility(
            visible = readerChromeVisible,
            enter = fadeIn(animationSpec = tween(readerChromeMotionMs)) +
                slideInVertically(animationSpec = tween(readerChromeMotionMs)) { -it / 4 },
            exit = fadeOut(animationSpec = tween(readerChromeMotionMs)) +
                slideOutVertically(animationSpec = tween(readerChromeMotionMs)) { -it / 4 }
        ) {
            Column {
                ReaderGlassToolbar(
                    article = article,
                    sourceStatus = readerSourceStatus,
                    backdrop = backdrop,
                    glassAlpha = glassAlpha,
                    dark = dark,
                    fontSize = fontSize,
                    textSizeMenuExpanded = textSizeMenuExpanded,
                    onTextSizeMenuExpandedChange = { textSizeMenuExpanded = it },
                    onBack = {
                        readerWebViewHolder.value?.flushReadingPosition()
                        onBack()
                    },
                    onFontSizeChange = { fontSize = it }
                )
                if (savedReadingProgress > 0.005f && !reachedBottom) {
                    Box(
                        Modifier.fillMaxWidth().padding(horizontal = 18.dp, vertical = 2.dp)
                            .height(2.dp).clip(CircleShape)
                            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.18f))
                    ) {
                        Box(
                            Modifier.fillMaxWidth(savedReadingProgress.coerceIn(0f, 1f))
                                .fillMaxSize().background(MaterialTheme.colorScheme.primary)
                        )
                    }
                }
            }
        }
        if (extracting || !readerPositionLoaded ||
            (!pageLoaded && (extractedHtml != null || fallbackToPublisher))
        ) androidx.compose.material3.LinearProgressIndicator(Modifier.fillMaxWidth())
        Box(Modifier.weight(1f).fillMaxWidth().onGloballyPositioned { coordinates ->
            // Both this viewport and the WebView selection rectangle are converted
            // in window coordinates; mixing screen and window origins double-counts
            // edge-to-edge/status-bar offsets on some Android window configurations.
            readerViewportWindowOrigin = coordinates.positionInWindow()
            readerViewportHeightPx = coordinates.size.height
        }) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    ReaderWebView(ctx).apply {
                        readerWebViewHolder.value = this
                        // Public-page reading is intentionally stateless: never reuse
                        // publisher login/tracking cookies from an earlier WebView session.
                        val cookieManager = CookieManager.getInstance()
                        ReaderCookiePolicy.enforce(
                            setAcceptCookie = cookieManager::setAcceptCookie,
                            setAcceptThirdPartyCookies = { accept ->
                                cookieManager.setAcceptThirdPartyCookies(this, accept)
                            },
                            clearCookies = { cookieManager.removeAllCookies(null) }
                        )
                        settings.javaScriptEnabled = false
                        settings.domStorageEnabled = false
                        settings.loadsImagesAutomatically = true
                        settings.javaScriptCanOpenWindowsAutomatically = false
                        settings.allowFileAccess = false
                        settings.allowContentAccess = false
                        settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_NEVER_ALLOW
                        settings.safeBrowsingEnabled = true
                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: android.graphics.Bitmap?
                            ) {
                                (view as? ReaderWebView)?.cancelShortPageEndCheck(resetStability = true)
                            }
                            override fun onPageFinished(view: WebView?, url: String?) {
                                onReaderPageFinished()
                                (view as? ReaderWebView)?.schedulePendingReadingPositionRestore()
                                // A page that fits entirely in the viewport never scrolls, so
                                // onScrollChanged cannot expose its end-of-reading state.
                                (view as? ReaderWebView)?.scheduleShortPageEndCheck()
                            }
                            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: android.webkit.WebResourceError?) {
                                if (request?.isForMainFrame == true) onReaderMainFrameError()
                            }
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                                if (!request.isForMainFrame) return false
                                val target = request.url.toString()
                                if (!allowReaderNavigation(target)) return true
                                // Preserve server redirects during initial loading, but send
                                // user-initiated publisher links to the system browser.
                                if (!request.hasGesture()) return false
                                (view as? ReaderWebView)?.onOpenPublisherLink?.invoke(target)
                                return true
                            }
                        }
                    }
                },
                update = { webView ->
                    val articleChanged = webView.beginArticle(article.url)
                    webView.onReadingProgressChanged = { progress ->
                        savedReadingProgress = progress
                        onSaveReaderPosition(article.url, progress)
                    }
                    webView.onReaderChromeVisibilityChanged = { visible ->
                        readerChromeVisible = visible
                    }
                    webView.setReaderChromePinned(readerChromePinned)
                    webView.onReachedBottom = {
                        // The final downward scroll may already have scheduled chrome auto-hide.
                        // Reveal it again so the completion action is actually discoverable.
                        webView.revealReaderChrome()
                        webView.markReadingPositionCompleted()
                        onClearReaderPosition(article.url)
                        savedReadingProgress = 0f
                        reachedBottom = true
                    }
                    webView.onSelectionActionModeChanged = { active ->
                        selectionActionModeActive = active
                    }
                    webView.onDefineWord = { word, selectionRect ->
                        val webViewLocation = IntArray(2)
                        webView.getLocationInWindow(webViewLocation)
                        val displayDensity = webView.resources.displayMetrics.density
                        val cssToWindowScale = displayDensity * webView.scale
                        val selectionAnchor = selectionRect?.let { rect ->
                            DefinitionSelectionAnchorPolicy.fromCssRect(
                                leftCssPx = rect.left,
                                topCssPx = rect.top,
                                widthCssPx = rect.width(),
                                heightCssPx = rect.height(),
                                webViewWindowOrigin = Offset(
                                    webViewLocation[0].toFloat(), webViewLocation[1].toFloat()
                                ),
                                viewportWindowOrigin = readerViewportWindowOrigin,
                                cssToWindowScale = cssToWindowScale
                            )
                        }
                        activeWordAnchor = selectionAnchor
                        // Resolve the side from the compact footprint once per word.
                        // Expansion scrolls in place instead of jumping across selection.
                        activeWordPreferAbove = DefinitionPopupPlacementPolicy.preferAbove(
                            parentHeightPx = readerViewportHeightPx,
                            anchorY = selectionAnchor?.center?.y ?: readerViewportHeightPx * 0.72f,
                            compactHeightPx = (DictionaryOverlayPolicy.COMPACT_MAX_HEIGHT_DP * displayDensity).roundToInt(),
                            marginPx = (12f * displayDensity).roundToInt(),
                            gapPx = (14f * displayDensity).roundToInt()
                        )
                        activeWord = word
                    }
                    webView.onReaderPageFinished = {
                        pageLoaded = true
                        contentLoaded = true
                        publisherLoadFailed = false
                        // Publisher fallback uses WebSettings.textZoom; no publisher script is injected.
                    }
                    webView.onReaderMainFrameError = {
                        pageLoaded = true
                        contentLoaded = false
                        publisherLoadFailed = true
                    }
                    webView.allowReaderNavigation = { target -> PublisherHostPolicy.allows(article.source, target) }
                    webView.onOpenPublisherLink = { target -> openPublisherPage(context, article.source, target) }
                    val selectionBridgeEnabled = ReaderExecutionPolicy.javascriptEnabled(
                        hasSanitizedArticle = extractedHtml != null,
                        hasAllowlistedPublisherFallback = fallbackToPublisher
                    )
                    webView.selectionBridgeEnabled = selectionBridgeEnabled
                    webView.settings.javaScriptEnabled = selectionBridgeEnabled
                    // Neither the app-owned document nor publisher fallback needs DOM storage.
                    webView.settings.domStorageEnabled = false
                    webView.settings.textZoom = if (fallbackToPublisher) (fontSize * 100 / 16) else 100
                    if (articleChanged) {
                        reachedBottom = false
                        activeWord = null
                        activeWordAnchor = null
                        activeWordPreferAbove = false
                    }
                    if (readerPositionLoaded) {
                        val cleanHtml = extractedHtml
                        if (cleanHtml != null) {
                            val key = "reader:" + article.url + ":" + fontSize + ":" + dark
                            if (webView.loadedKey != key) {
                                if (!articleChanged) webView.flushReadingPosition()
                                webView.queueReadingPositionRestore(savedReadingProgress)
                                webView.loadedKey = key
                                pageLoaded = false
                                contentLoaded = false
                                webView.loadDataWithBaseURL(article.url, readerDocument(article, cleanHtml, fontSize, dark), "text/html", "UTF-8", null)
                            }
                        } else if (fallbackToPublisher) {
                            val key = "publisher:" + article.url + ":" + fontSize
                            if (webView.loadedKey != key) {
                                if (!articleChanged) webView.flushReadingPosition()
                                webView.queueReadingPositionRestore(savedReadingProgress)
                                webView.loadedKey = key
                                pageLoaded = false
                                contentLoaded = false
                                webView.loadUrl(article.url)
                            }
                        }
                    }
                }
            )

            if (!extracting && extractedHtml == null && !fallbackToPublisher) {
                Column(
                    Modifier.align(Alignment.Center).padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("A readable public article is unavailable", style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                    Text("brrrNews could not verify a publisher page for this link. The RSS headline and excerpt remain available in the feed; no paywall or access restriction is bypassed.",
                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center)
                }
            }
            if (!extracting && fallbackToPublisher && publisherLoadFailed) {
                Column(
                    Modifier.align(Alignment.Center).padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("Publisher page could not be loaded", style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                    Text("Check your connection or return to the feed. Subscription restrictions remain unchanged.",
                        style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center)
                }
            }

            if (extracting) {
                Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background.copy(alpha = 0.90f)), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        CircularProgressIndicator()
                        Text("Checking for publicly accessible article content", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
            // The selection anchor is relative to this reader viewport. Keep the glass
            // surface in the same Box so it overlays the WebView without consuming layout height.
            if (activeWord != null) {
                DefinitionGlassOverlay(
                    word = activeWord!!,
                    anchor = activeWordAnchor,
                    preferAbove = activeWordPreferAbove,
                    backdrop = backdrop,
                    glassAlpha = glassAlpha
                ) {
                    activeWord = null
                    activeWordAnchor = null
                }
            }
        }
        if (readerChromeVisible && fallbackToPublisher && !extracting) {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Publisher fallback · scripts disabled; subscription limits unchanged.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = { openPublisherPage(context, article.source, article.url) }) {
                    Text("Browser")
                }
            }
        }
        AnimatedVisibility(
            visible = readerChromeVisible && ReaderChromePolicy.shouldShowCompletion(
                reachedBottom = reachedBottom,
                definitionOpen = activeWord != null,
                dismissed = completionDismissed,
                selectionActive = selectionActionModeActive,
                transientControlOpen = textSizeMenuExpanded
            ),
            enter = fadeIn(animationSpec = tween(readerChromeMotionMs)) +
                slideInVertically(animationSpec = tween(readerChromeMotionMs)) { it / 4 },
            exit = fadeOut(animationSpec = tween(readerChromeMotionMs)) +
                slideOutVertically(animationSpec = tween(readerChromeMotionMs)) { it / 4 }
        ) {
            PrismalTransientSurface(
                backdrop = backdrop,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .navigationBarsPadding(),
                glassAlpha = glassAlpha,
                dark = dark,
                cornerRadiusDp = 20f
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(start = 14.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                        Text("Reading complete", style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold)
                        Text("Five original prompts · not official PYQs",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                    TextButton(
                        onClick = onQuiz,
                        modifier = Modifier.heightIn(min = 48.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                    ) { Text("Try 5 questions") }
                    IconButton(
                        onClick = { completionDismissed = true },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Outlined.Close, contentDescription = "Dismiss completion")
                    }
                }
            }
        }
    }
}


@Composable
private fun ReaderGlassToolbar(
    article: Article,
    sourceStatus: String,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    dark: Boolean,
    fontSize: Int,
    textSizeMenuExpanded: Boolean,
    onTextSizeMenuExpandedChange: (Boolean) -> Unit,
    onBack: () -> Unit,
    onFontSizeChange: (Int) -> Unit
) {
    PrismalTransientSurface(
        backdrop = backdrop,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
        glassAlpha = glassAlpha,
        dark = dark,
        cornerRadiusDp = 22f
    ) {
        Row(
            Modifier.fillMaxWidth().heightIn(min = 60.dp)
                .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back to article list",
                    modifier = Modifier.size(22.dp))
            }
            Column(
                Modifier.weight(1f).padding(vertical = 3.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    article.source,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${ArticlePublicationDatePolicy.label(article.publishedAtMillis)} · $sourceStatus",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 14.sp
                )
            }
            Box {
                IconButton(
                    onClick = { onTextSizeMenuExpandedChange(true) },
                    modifier = Modifier.size(48.dp).semantics {
                        contentDescription = "Text size, $fontSize sp"
                    }
                ) {
                    Text("Aa", style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                DropdownMenu(
                    expanded = textSizeMenuExpanded,
                    onDismissRequest = { onTextSizeMenuExpandedChange(false) },
                    modifier = Modifier.widthIn(min = 196.dp),
                    shape = readerTextSizeMenuShape,
                    containerColor = Color.Transparent,
                    tonalElevation = 0.dp,
                    shadowElevation = 0.dp
                ) {
                    PrismalTransientSurface(
                        backdrop = backdrop,
                        modifier = Modifier.fillMaxWidth(),
                        glassAlpha = glassAlpha,
                        dark = dark,
                        cornerRadiusDp = 18f
                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            DropdownMenuItem(
                                text = { Text("Smaller text") },
                                leadingIcon = { Icon(Icons.Outlined.TextDecrease, contentDescription = null) },
                                enabled = fontSize > ReaderChromePolicy.MIN_FONT_SIZE_SP,
                                onClick = {
                                    onFontSizeChange(ReaderChromePolicy.adjustFontSize(fontSize, -1))
                                    onTextSizeMenuExpandedChange(false)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Larger text") },
                                leadingIcon = { Icon(Icons.Outlined.TextIncrease, contentDescription = null) },
                                enabled = fontSize < ReaderChromePolicy.MAX_FONT_SIZE_SP,
                                onClick = {
                                    onFontSizeChange(ReaderChromePolicy.adjustFontSize(fontSize, 1))
                                    onTextSizeMenuExpandedChange(false)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Current size · $fontSize sp") },
                                enabled = false,
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun openPublisherPage(context: android.content.Context, source: String, url: String) {
    if (!PublisherHostPolicy.allows(source, url)) return
    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
    runCatching { context.startActivity(intent) }.onFailure {
        android.widget.Toast.makeText(context, "No browser available", android.widget.Toast.LENGTH_SHORT).show()
    }
}

private fun readerDocument(article: Article, content: String, fontSize: Int, dark: Boolean): String {
    val background = if (dark) "#10141B" else "#FFFFFF"
    val foreground = if (dark) "#E9EDF5" else "#20242B"
    val muted = if (dark) "#AAB4C5" else "#717985"
    val title = article.title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    val source = article.source.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    return "<!doctype html><html><head><meta charset=\"utf-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\"><style>" +
        "html{background:" + background + ";color:" + foreground + ";}body{margin:0;padding:22px 20px 44px;background:" + background + ";color:" + foreground + ";font-family:-apple-system,BlinkMacSystemFont,'SF Pro Text','Helvetica Neue',system-ui,sans-serif;font-size:" + fontSize + "px;line-height:1.78;-webkit-text-size-adjust:100%;}" +
        ".cl-source{font:700 11px/1.4 system-ui,sans-serif;letter-spacing:1.2px;text-transform:uppercase;color:" + muted + ";margin:4px 0 14px;}" +
        "h1{font:700 1.72em/1.12 -apple-system,BlinkMacSystemFont,'SF Pro Display',system-ui,sans-serif;letter-spacing:-.045em;margin:0 0 18px;color:" + foreground + ";}" +
        "h2,h3{font-family:system-ui,sans-serif;line-height:1.25;margin:1.55em 0 .55em;}p,li{margin:0 0 1.1em;}img,figure,video{max-width:100%;height:auto;border-radius:14px;}figure{margin:1.4em 0;}figcaption{font:12px/1.5 system-ui,sans-serif;color:" + muted + ";}" +
        "a{color:" + (if (dark) "#9CB9FF" else "#2859A8") + ";text-decoration:none;}blockquote{margin:1.4em 0;padding:14px 16px;border-left:3px solid " + (if (dark) "#52658D" else "#A9BCE0") + ";border-radius:0 12px 12px 0;background:" + (if (dark) "#171D27" else "#F2F5F9") + ";color:" + muted + ";}" +
        ReaderPresentationPolicy.responsiveTableCss(dark) +
        "iframe{max-width:100%;}pre{white-space:pre-wrap;overflow-wrap:anywhere;border-radius:12px;padding:12px;background:" + (if (dark) "#171D27" else "#F2F5F9") + ";}code{overflow-wrap:anywhere;}" +
        "</style></head><body><div class=\"cl-source\">" + source + " · brrrNews Reader</div><h1>" + title + "</h1><main>" + content + "</main></body></html>"
}

private class ReaderWebView(context: android.content.Context) : WebView(context) {
    var loadedKey: String? = null
    var onReachedBottom: () -> Unit = {}
    var onSelectionActionModeChanged: (Boolean) -> Unit = {}
    var onDefineWord: (String, RectF?) -> Unit = { _, _ -> }
    var onReaderPageFinished: () -> Unit = {}
    var onReaderMainFrameError: () -> Unit = {}
    var allowReaderNavigation: (String) -> Boolean = { false }
    var onOpenPublisherLink: (String) -> Unit = {}
    var onReadingProgressChanged: (Float) -> Unit = {}
    var onReaderChromeVisibilityChanged: (Boolean) -> Unit = {}
    var selectionBridgeEnabled: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                if (!value) cancelPendingAutoDefine()
            }
        }
    private val progressGate = ReaderProgressGate()
    private var selectionModeGeneration = 0
    private var pendingAutoDefine: Runnable? = null
    private var autoDefineEvaluationPending = false
    private var pendingReadingPositionRestore: Float? = null
    private var readingPositionRestore: Runnable? = null
    private var readingPositionSave: Runnable? = null
    private var lastRestoreContentHeight = -1
    private var stableRestoreSamples = 0
    private var restoringReadingPosition = false
    private var readingPositionCompleted = false
    private var readerChromeVisible = true
    private var readerChromePinned = false
    private var readerChromeScrollState = ReaderChromeScrollState()
    private var pendingReaderChromeHide: Runnable? = null

    fun setReaderChromePinned(pinned: Boolean) {
        if (readerChromePinned == pinned) return
        readerChromePinned = pinned
        readerChromeScrollState = ReaderChromeScrollState()
        if (pinned) {
            cancelPendingReaderChromeHide()
            setReaderChromeVisible(true)
        }
    }

    /** Reveal controls after true completion without permanently pinning them. */
    fun revealReaderChrome() {
        cancelPendingReaderChromeHide()
        readerChromeScrollState = ReaderChromePolicy.reveal(readerChromeScrollState)
        setReaderChromeVisible(readerChromeScrollState.visible)
    }

    private fun setReaderChromeVisible(visible: Boolean) {
        if (readerChromeVisible == visible) return
        readerChromeVisible = visible
        onReaderChromeVisibilityChanged(visible)
    }

    private fun cancelPendingReaderChromeHide() {
        pendingReaderChromeHide?.let { removeCallbacks(it) }
        pendingReaderChromeHide = null
    }

    private fun scheduleReaderChromeHide() {
        cancelPendingReaderChromeHide()
        val hide = Runnable {
            pendingReaderChromeHide = null
            if (!readerChromePinned && scrollY > 0 && !restoringReadingPosition) {
                setReaderChromeVisible(false)
            }
        }
        pendingReaderChromeHide = hide
        postDelayed(hide, ReaderChromePolicy.HIDE_SETTLE_DELAY_MS)
    }

    fun beginArticle(url: String): Boolean {
        val changed = progressGate.beginArticle(url)
        if (changed) {
            flushReadingPosition()
            readingPositionSave?.let { removeCallbacks(it) }
            readingPositionSave = null
            cancelShortPageEndCheck(resetStability = true)
            cancelPendingAutoDefine()
            cancelReadingPositionRestore()
            cancelPendingReaderChromeHide()
            readerChromeScrollState = ReaderChromeScrollState()
            readerChromePinned = false
            setReaderChromeVisible(true)
            readingPositionCompleted = false
        }
        return changed
    }

    fun queueReadingPositionRestore(progress: Float) {
        cancelReadingPositionRestore()
        pendingReadingPositionRestore = progress.takeIf { ReaderPositionPolicy.shouldPersist(it) }
        lastRestoreContentHeight = -1
        stableRestoreSamples = 0
        readingPositionCompleted = false
    }

    fun schedulePendingReadingPositionRestore() {
        if (pendingReadingPositionRestore == null || readingPositionRestore != null) return
        scheduleReadingPositionRestoreAttempt(0)
    }

    private fun scheduleReadingPositionRestoreAttempt(attempt: Int) {
        val check = Runnable {
            readingPositionRestore = null
            val progress = pendingReadingPositionRestore ?: return@Runnable
            val fullHeight = (contentHeight * scale).toInt()
            if (width <= 0 || height <= 0 || fullHeight <= height) {
                if (attempt < MAX_READING_POSITION_RESTORE_ATTEMPTS) {
                    scheduleReadingPositionRestoreAttempt(attempt + 1)
                } else {
                    pendingReadingPositionRestore = null
                }
                return@Runnable
            }

            if (fullHeight == lastRestoreContentHeight) {
                stableRestoreSamples += 1
            } else {
                lastRestoreContentHeight = fullHeight
                stableRestoreSamples = 0
            }

            if (stableRestoreSamples >= REQUIRED_STABLE_RESTORE_SAMPLES ||
                attempt >= MAX_READING_POSITION_RESTORE_ATTEMPTS
            ) {
                val targetY = ReaderPositionPolicy.scrollYForProgress(progress, fullHeight, height)
                pendingReadingPositionRestore = null
                if (targetY != null && targetY > 0) {
                    restoringReadingPosition = true
                    scrollTo(0, targetY)
                    postDelayed({
                        restoringReadingPosition = false
                        if (!readingPositionCompleted) scheduleReadingPositionSave()
                    }, READING_POSITION_RESTORE_SETTLE_MS)
                }
            } else {
                scheduleReadingPositionRestoreAttempt(attempt + 1)
            }
        }
        readingPositionRestore = check
        postDelayed(check, READING_POSITION_RESTORE_SAMPLE_MS)
    }

    private fun cancelReadingPositionRestore() {
        readingPositionRestore?.let { removeCallbacks(it) }
        readingPositionRestore = null
        pendingReadingPositionRestore = null
        stableRestoreSamples = 0
        lastRestoreContentHeight = -1
    }

    fun markReadingPositionCompleted() {
        readingPositionCompleted = true
        pendingReadingPositionRestore = null
        cancelReadingPositionRestore()
        readingPositionSave?.let { removeCallbacks(it) }
        readingPositionSave = null
    }

    private fun scheduleReadingPositionSave() {
        if (restoringReadingPosition || readingPositionCompleted ||
            pendingReadingPositionRestore != null
        ) return
        readingPositionSave?.let { removeCallbacks(it) }
        val save = Runnable {
            readingPositionSave = null
            publishReadingPosition()
        }
        readingPositionSave = save
        postDelayed(save, READING_POSITION_SAVE_DEBOUNCE_MS)
    }

    private fun publishReadingPosition() {
        if (restoringReadingPosition || readingPositionCompleted ||
            pendingReadingPositionRestore != null
        ) return
        val fullHeight = (contentHeight * scale).toInt()
        val progress = ReaderPositionPolicy.progressFromScroll(scrollY, fullHeight, height) ?: return
        onReadingProgressChanged(progress)
    }

    fun flushReadingPosition() {
        val pending = readingPositionSave ?: return
        removeCallbacks(pending)
        readingPositionSave = null
        publishReadingPosition()
    }

    private val shortPageStabilityGate = ReaderShortPageStabilityGate()
    private var shortPageEndCheck: Runnable? = null

    fun cancelShortPageEndCheck(resetStability: Boolean = false) {
        shortPageEndCheck?.let { removeCallbacks(it) }
        shortPageEndCheck = null
        if (resetStability) shortPageStabilityGate.reset()
    }

    fun scheduleShortPageEndCheck(attempt: Int = 0) {
        cancelShortPageEndCheck()
        val check = Runnable {
            shortPageEndCheck = null
            val fullHeight = (contentHeight * scale).toInt()
            val viewportHeight = height
            when {
                fullHeight <= 0 || viewportHeight <= 0 -> {
                    shortPageStabilityGate.reset()
                    if (attempt < MAX_SHORT_PAGE_END_CHECK_ATTEMPTS) {
                        scheduleShortPageEndCheck(attempt + 1)
                    }
                }
                fullHeight > viewportHeight -> shortPageStabilityGate.reset()
                shortPageStabilityGate.record(fullHeight, viewportHeight) -> {
                    if (progressGate.markEndReached()) onReachedBottom()
                }
                attempt < MAX_SHORT_PAGE_END_CHECK_ATTEMPTS ->
                    scheduleShortPageEndCheck(attempt + 1)
                else -> shortPageStabilityGate.reset()
            }
        }
        shortPageEndCheck = check
        postDelayed(check, SHORT_PAGE_END_CHECK_DELAY_MS)
    }

    override fun onDetachedFromWindow() {
        flushReadingPosition()
        readingPositionSave?.let { removeCallbacks(it) }
        readingPositionSave = null
        cancelReadingPositionRestore()
        cancelShortPageEndCheck(resetStability = true)
        cancelPendingAutoDefine()
        cancelPendingReaderChromeHide()
        onSelectionActionModeChanged(false)
        super.onDetachedFromWindow()
    }

    @Suppress("DEPRECATION")
    override fun startActionMode(callback: ActionMode.Callback, type: Int): ActionMode? =
        super.startActionMode(wrapSelectionCallback(callback), type)

    @Suppress("DEPRECATION")
    override fun startActionMode(callback: ActionMode.Callback): ActionMode? =
        super.startActionMode(wrapSelectionCallback(callback))

    private fun evaluateSelection(onResult: (String, RectF?) -> Unit) {
        val script = "(function(){" +
            "var s=window.getSelection();" +
            "if(!s||!s.rangeCount)return JSON.stringify({text:''});" +
            "var r=s.getRangeAt(0).getBoundingClientRect();" +
            "return JSON.stringify({text:s.toString(),left:r.left,top:r.top,width:r.width,height:r.height});" +
            "})()"
        evaluateJavascript(script) { encoded ->
            val raw = runCatching {
                JSONTokener(encoded ?: "null").nextValue() as? String
            }.getOrNull()
            val payload = raw?.let { runCatching { JSONObject(it) }.getOrNull() }
            val selected = payload?.optString("text").orEmpty()
            val rect = payload?.let {
                val left = it.optDouble("left", Double.NaN)
                val top = it.optDouble("top", Double.NaN)
                val width = it.optDouble("width", Double.NaN)
                val height = it.optDouble("height", Double.NaN)
                if (listOf(left, top, width, height).all { value -> value.isFinite() } &&
                    width > 0.0 && height > 0.0
                ) RectF(left.toFloat(), top.toFloat(), (left + width).toFloat(), (top + height).toFloat())
                else null
            }
            onResult(selected, rect)
        }
    }

    private fun cancelPendingAutoDefine() {
        selectionModeGeneration += 1
        pendingAutoDefine?.let { removeCallbacks(it) }
        pendingAutoDefine = null
        autoDefineEvaluationPending = false
    }

    private fun wrapSelectionCallback(original: ActionMode.Callback): ActionMode.Callback2 =
        object : ActionMode.Callback2() {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val created = original.onCreateActionMode(mode, menu)
                if (created) onSelectionActionModeChanged(true)
                if (created && selectionBridgeEnabled) {
                    menu.add(0, MENU_DEFINE_WORD, menu.size(), "Define")
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

                    // Give the user time to extend the native selection before auto-defining.
                    // Copy/Select and other native actions remain available during this grace period.
                    cancelPendingAutoDefine()
                    val generation = selectionModeGeneration
                    val startedAt = SystemClock.uptimeMillis()
                    val autoDefine = Runnable {
                        if (generation == selectionModeGeneration) {
                            pendingAutoDefine = null
                            autoDefineEvaluationPending = true
                            evaluateSelection { selected, selectionRect ->
                                if (generation == selectionModeGeneration) {
                                    autoDefineEvaluationPending = false
                                    val elapsed = SystemClock.uptimeMillis() - startedAt
                                    val word = DictionarySelectionPolicy.autoDefineWord(selected, elapsed)
                                    if (word != null) {
                                        performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                                        onDefineWord(word, selectionRect)
                                        mode.finish()
                                    }
                                }
                            }
                        }
                    }
                    pendingAutoDefine = autoDefine
                    postDelayed(autoDefine, DictionarySelectionPolicy.AUTO_DEFINE_DELAY_MS)
                }
                return created
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean =
                original.onPrepareActionMode(mode, menu)

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                if (item.itemId == MENU_DEFINE_WORD && selectionBridgeEnabled) {
                    cancelPendingAutoDefine()
                    val requestGeneration = selectionModeGeneration
                    evaluateSelection { selected, selectionRect ->
                        if (requestGeneration == selectionModeGeneration) {
                            val word = DictionarySelectionPolicy.manualDefineWord(
                                selected,
                                requestGeneration,
                                selectionModeGeneration
                            )
                            if (word != null) {
                                performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                                onDefineWord(word, selectionRect)
                            }
                            // WebView selection can be cleared when ActionMode finishes.
                            // Keep native selection alive until evaluateJavascript returns.
                            mode.finish()
                        }
                    }
                    return true
                }
                val handled = original.onActionItemClicked(mode, item)
                if (handled) cancelPendingAutoDefine()
                return handled
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                cancelPendingAutoDefine()
                onSelectionActionModeChanged(false)
                original.onDestroyActionMode(mode)
            }

            override fun onGetContentRect(mode: ActionMode, view: View, outRect: Rect) {
                if (original is ActionMode.Callback2) original.onGetContentRect(mode, view, outRect)
                else super.onGetContentRect(mode, view, outRect)
            }
        }
    private var touchDownX: Float? = null
    private var touchDownY: Float? = null
    private var upwardSwipeDetected = false

    private fun cancelPendingAutoDefineIfGestureInvalidated(
        event: MotionEvent,
        gestureCancelled: Boolean = false
    ) {
        if (pendingAutoDefine == null && !autoDefineEvaluationPending) return
        val startX = touchDownX
        val startY = touchDownY
        val touchSlop = ViewConfiguration.get(context).scaledTouchSlop.toFloat()
        if (DictionarySelectionPolicy.shouldCancelAutoDefineForGesture(
                gestureCancelled = gestureCancelled,
                deltaX = startX?.let { event.x - it } ?: 0f,
                deltaY = startY?.let { event.y - it } ?: 0f,
                touchSlopPx = touchSlop
            )
        ) {
            cancelPendingAutoDefine()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                // A fresh touch is a new interaction. Do not let an earlier long-press
                // open its definition while the user is tapping or refining the selection.
                if (DictionarySelectionPolicy.shouldCancelAutoDefineForNewTouch(
                        hasPendingRunnable = pendingAutoDefine != null,
                        evaluationPending = autoDefineEvaluationPending
                    )
                ) {
                    cancelPendingAutoDefine()
                }
                touchDownX = event.x
                touchDownY = event.y
                upwardSwipeDetected = false
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                if (DictionarySelectionPolicy.shouldCancelAutoDefineForMultiTouch(event.pointerCount)) {
                    // A second pointer changes selection into a multi-touch gesture (for example,
                    // pinch zoom). Keep WebView's native gesture, but stop definition/end tracking.
                    cancelPendingAutoDefine()
                    touchDownX = null
                    touchDownY = null
                    upwardSwipeDetected = false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val startY = touchDownY
                cancelPendingAutoDefineIfGestureInvalidated(event)
                if (startY != null && startY - event.y > 24f) upwardSwipeDetected = true
            }
            MotionEvent.ACTION_UP -> {
                // ACTION_UP is the final safety net if the platform omitted a MOVE sample.
                cancelPendingAutoDefineIfGestureInvalidated(event)
                val attemptedToReadDown = upwardSwipeDetected
                if (attemptedToReadDown) {
                    post {
                        val fullHeight = (contentHeight * scale).toInt()
                        if (ReaderEndDetector.isAtEnd(fullHeight, height, scrollY) &&
                            progressGate.markEndReached()
                        ) {
                            onReachedBottom()
                        }
                    }
                }
                touchDownX = null
                touchDownY = null
                upwardSwipeDetected = false
            }
            MotionEvent.ACTION_CANCEL -> {
                // A cancelled stream must never leave a delayed or in-flight definition behind.
                cancelPendingAutoDefineIfGestureInvalidated(event, gestureCancelled = true)
                touchDownX = null
                touchDownY = null
                upwardSwipeDetected = false
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY)
        val deltaY = scrollY - oldScrollY
        if (deltaY != 0) {
            scheduleReadingPositionSave()
            if (deltaY < 0) cancelPendingReaderChromeHide()
            readerChromeScrollState = ReaderChromePolicy.onScroll(
                state = readerChromeScrollState,
                deltaY = deltaY,
                hideThresholdPx = (ReaderChromePolicy.HIDE_THRESHOLD_DP * resources.displayMetrics.density).roundToInt(),
                showThresholdPx = (ReaderChromePolicy.SHOW_THRESHOLD_DP * resources.displayMetrics.density).roundToInt(),
                pinned = readerChromePinned || restoringReadingPosition,
                atTop = scrollY <= 0
            )
            if (readerChromeScrollState.visible) {
                cancelPendingReaderChromeHide()
                setReaderChromeVisible(true)
            } else if (deltaY > 0 && !readerChromePinned && !restoringReadingPosition) {
                // Only downward movement can schedule a hide; upward movement cancels it
                // immediately and reveals chrome once the smaller threshold is crossed.
                scheduleReaderChromeHide()
            }
        }
        val fullHeight = (contentHeight * scale).toInt()
        if (fullHeight > height + 120 &&
            ReaderEndDetector.isAtEnd(fullHeight, height, scrollY) &&
            progressGate.markEndReached()
        ) {
            onReachedBottom()
        }
    }

    companion object {
        private const val MENU_DEFINE_WORD = 0x43555252
        private const val SHORT_PAGE_END_CHECK_DELAY_MS = 180L
        private const val MAX_SHORT_PAGE_END_CHECK_ATTEMPTS = 5
        private const val READING_POSITION_SAVE_DEBOUNCE_MS = 600L
        private const val READING_POSITION_RESTORE_SAMPLE_MS = 180L
        private const val READING_POSITION_RESTORE_SETTLE_MS = 260L
        private const val MAX_READING_POSITION_RESTORE_ATTEMPTS = 12
        private const val REQUIRED_STABLE_RESTORE_SAMPLES = 2
    }
}

@Composable
private fun DefinitionGlassOverlay(
    word: String,
    anchor: DefinitionSelectionAnchor?,
    preferAbove: Boolean,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val reduceTransparency = LocalGlassReduceTransparency.current
    val reduceMotion = LocalGlassReduceMotion.current
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
    val visibilityState = remember(word) {
        androidx.compose.animation.core.MutableTransitionState(false).apply { targetState = true }
    }
    val transition = updateTransition(visibilityState, label = "Definition glass morph")
    val expansion by transition.animateFloat(
        transitionSpec = { if (reduceMotion) snap() else spring(dampingRatio = 0.76f, stiffness = 480f) },
        label = "Definition morph progress"
    ) { visible -> if (visible) 1f else 0f }
    val opacity by transition.animateFloat(
        transitionSpec = { tween(durationMillis = if (reduceMotion) 90 else 115) },
        label = "Definition opacity"
    ) { visible -> if (visible) 1f else 0f }

    BackHandler(enabled = visibilityState.targetState) { visibilityState.targetState = false }
    LaunchedEffect(visibilityState.isIdle, visibilityState.currentState, visibilityState.targetState) {
        if (visibilityState.isIdle && !visibilityState.currentState && !visibilityState.targetState) onClose()
    }

    val density = LocalDensity.current
    val marginPx = with(density) { 12.dp.roundToPx() }
    val gapPx = with(density) { 14.dp.roundToPx() }
    val maxPopupWidthPx = with(density) { DictionaryOverlayPolicy.MAX_WIDTH_DP.dp.roundToPx() }
    Layout(
        modifier = Modifier.fillMaxSize(),
        content = {
            PrismalGlassSurface(
                backdrop = backdrop,
                modifier = Modifier.fillMaxWidth()
                    .animateContentSize(if (reduceMotion) snap() else spring(dampingRatio = 0.82f, stiffness = 480f)),
                shape = { PrismalRoundedRectangle(25.dp) },
                blurRadius = GlassRenderingPolicy.blurDp(12f, reduceTransparency).dp,
                refractionHeightPx = GlassRenderingPolicy.refractionPx(22f, reduceTransparency),
                refractionAmountPx = GlassRenderingPolicy.refractionPx(24f, reduceTransparency),
                depthEffect = GlassRenderingPolicy.depthEnabled(true, reduceTransparency),
                chromaticAberration = GlassRenderingPolicy.chromaticAberration(0.015f, reduceTransparency),
                tint = if (isSystemInDarkTheme()) Color(0xFF263247) else Color(0xFFEAF0FA),
                tintAlpha = GlassRenderingPolicy.tintAlpha((glassAlpha * 0.62f).coerceIn(0.08f, 0.34f), reduceTransparency)
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
                            val copiedText = DictionaryMeaningPolicy.copyText(word, current.meanings, current.synonyms)
                            val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)
                                as? android.content.ClipboardManager
                            clipboard?.setPrimaryClip(android.content.ClipData.newPlainText("Definition of $word", copiedText))
                            android.widget.Toast.makeText(context, "Definition copied", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    },
                    onClose = { visibilityState.targetState = false },
                    closeLabel = "Done",
                    modifier = Modifier
                        .heightIn(max = if (expanded) DictionaryOverlayPolicy.EXPANDED_MAX_HEIGHT_DP.dp else DictionaryOverlayPolicy.COMPACT_MAX_HEIGHT_DP.dp)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .verticalScroll(scrollState)
                )
            }
        }
    ) { measurables, constraints ->
        val parentWidthPx = constraints.maxWidth
        val parentHeightPx = constraints.maxHeight
        val widthLimitPx = (parentWidthPx - marginPx * 2).coerceAtLeast(1).coerceAtMost(maxPopupWidthPx)
        val fallbackAnchor = Offset(parentWidthPx / 2f, parentHeightPx * 0.72f)
        val resolvedAnchor = anchor?.center ?: fallbackAnchor
        val availableHeightPx = DefinitionPopupPlacementPolicy.availableHeightOnSide(
            parentHeightPx, resolvedAnchor.y, marginPx, gapPx, preferAbove
        )
        val popup = measurables.single().measure(
            Constraints(maxWidth = widthLimitPx, maxHeight = availableHeightPx)
        )
        val placement = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = parentWidthPx,
            parentHeightPx = parentHeightPx,
            anchor = resolvedAnchor,
            popupWidthPx = popup.width,
            estimatedHeightPx = popup.height,
            marginPx = marginPx,
            gapPx = gapPx,
            maxPopupWidthPx = maxPopupWidthPx,
            anchorWidthPx = anchor?.widthPx ?: 0f,
            anchorHeightPx = anchor?.heightPx ?: 0f,
            preferAbove = preferAbove
        )
        layout(parentWidthPx, parentHeightPx) {
            popup.placeWithLayer(placement.leftPx, placement.topPx) {
                val progress = expansion.coerceIn(0f, 1f)
                alpha = opacity.coerceIn(0f, 1f)
                scaleX = placement.initialScaleX + (1f - placement.initialScaleX) * progress
                scaleY = placement.initialScaleY + (1f - placement.initialScaleY) * progress
                transformOrigin = TransformOrigin(placement.originX, placement.originY)
            }
        }
    }
}

@Composable
private fun GlassSettingsScreen(
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    reduceTransparency: Boolean,
    onReduceTransparencyChange: (Boolean) -> Unit,
    onGlassAlphaChange: (Float) -> Unit,
    onBack: () -> Unit
) {
    val dark = isSystemInDarkTheme()
    Column(Modifier.fillMaxSize()) {
        HomeGlassHeader(
            backdrop = backdrop,
            glassAlpha = glassAlpha,
            dark = dark,
            pageTitle = "Appearance",
            subtitle = "Personalise controls and reading surfaces",
            showRefresh = false,
            onRefresh = {},
            refreshing = false,
            showBack = true,
            onBack = onBack
        )

        Column(
            Modifier.weight(1f).fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(top = 8.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AppearanceGlassEditor(
                backdrop = backdrop,
                glassAlpha = glassAlpha,
                reduceTransparency = reduceTransparency,
                dark = dark,
                onReduceTransparencyChange = onReduceTransparencyChange,
                onGlassAlphaChange = onGlassAlphaChange
            )

            SupportingGlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text("Designed for reading first", fontWeight = FontWeight.SemiBold)
                    Text(
                        "Navigation and controls borrow the glass material; article text, imagery and study data stay clear and high-contrast. Your appearance choice is saved locally on this device.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Text(
                "Unsupported shader effects fall back automatically. Reduce transparency removes live blur and refraction; appearance settings use no cloud service.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun AppearanceGlassEditor(
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    reduceTransparency: Boolean,
    dark: Boolean,
    onReduceTransparencyChange: (Boolean) -> Unit,
    onGlassAlphaChange: (Float) -> Unit
) {
    // Preview state is deliberately scoped to this child. Dragging the slider updates
    // only this editor; the app-wide material preference is committed on release.
    var previewGlassAlpha by remember { mutableFloatStateOf(glassAlpha) }
    LaunchedEffect(glassAlpha) { previewGlassAlpha = glassAlpha }
    val selectedPreset = when {
        kotlin.math.abs(previewGlassAlpha - 0.12f) < 0.015f -> "Clear"
        kotlin.math.abs(previewGlassAlpha - 0.32f) < 0.015f -> "Balanced"
        kotlin.math.abs(previewGlassAlpha - 0.55f) < 0.015f -> "Frosted"
        else -> "Custom"
    }
    // The miniature controls use the same Clear↔Frosted intensity and reduced-
    // transparency response as the app, without adding another live glass surface.
    val previewControlSurfaceAlpha = GlassSurfacePolicy.previewControlSurfaceAlpha(
        previewGlassAlpha, reduceTransparency
    )
    val previewControlRimAlpha = GlassSurfacePolicy.previewControlRimAlpha(
        previewGlassAlpha, reduceTransparency
    )
    val previewSelectedFillAlpha = GlassSurfacePolicy.previewSelectedFillAlpha(
        previewGlassAlpha, reduceTransparency
    )
    val previewTabs = remember {
        listOf(
            "Today" to Icons.Outlined.Today,
            "Explore" to Icons.Outlined.Explore,
            "Search" to Icons.Outlined.Search,
            "You" to Icons.Outlined.Person
        )
    }
    val applyPreset: (Float) -> Unit = { value ->
        previewGlassAlpha = value
        onGlassAlphaChange(value)
    }

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // This interactive panel uses the shared static supporting-glass tier.
        // The compact preview below remains the only live Prismal surface in this editor.
        SupportingGlassCard(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                    Text("Liquid Glass", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Text(
                        "Tune the shared tint used by navigation, search, filters and contextual controls.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Clear", style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.weight(1f))
                    Text(
                        "${(previewGlassAlpha * 100).roundToInt()}%",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.weight(1f))
                    Text("Frosted", style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary)
                }
                Slider(
                    value = previewGlassAlpha,
                    onValueChange = { previewGlassAlpha = it },
                    onValueChangeFinished = { onGlassAlphaChange(previewGlassAlpha) },
                    valueRange = 0.12f..0.55f,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp).semantics {
                        contentDescription = "Glass intensity from clear to frosted"
                    }
                )
                Row(
                    Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    GlassFilterPill(
                        label = "Clear",
                        selected = selectedPreset == "Clear",
                        onClick = { applyPreset(0.12f) },
                        glassAlpha = previewGlassAlpha,
                        reduceTransparency = reduceTransparency
                    )
                    GlassFilterPill(
                        label = "Balanced",
                        selected = selectedPreset == "Balanced",
                        onClick = { applyPreset(0.32f) },
                        glassAlpha = previewGlassAlpha,
                        reduceTransparency = reduceTransparency
                    )
                    GlassFilterPill(
                        label = "Frosted",
                        selected = selectedPreset == "Frosted",
                        onClick = { applyPreset(0.55f) },
                        glassAlpha = previewGlassAlpha,
                        reduceTransparency = reduceTransparency
                    )
                }
                Row(
                    Modifier.fillMaxWidth().heightIn(min = 56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            "Reduce transparency & effects",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Raises control opacity and removes blur, refraction and adaptive sampling.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = reduceTransparency,
                        onCheckedChange = onReduceTransparencyChange,
                        modifier = Modifier.semantics {
                            contentDescription = "Reduce transparency and effects"
                        }
                    )
                }
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            Text("Live preview", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(
                "One shared control material responds to the slider; the article surface stays readable.",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary
            )
            // A single small Prismal surface replaces the previous two independently
            // blurred panels. Search and navigation are simple tinted children, not
            // nested live-glass surfaces or additional backdrop consumers.
            CompositionLocalProvider(LocalGlassIntensity provides previewGlassAlpha) {
                PrismalTransientSurface(
                    backdrop = backdrop,
                    modifier = Modifier.fillMaxWidth(),
                    glassAlpha = previewGlassAlpha,
                    dark = dark,
                    cornerRadiusDp = 26f
                ) {
                Column(
                    Modifier.fillMaxWidth().padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth().heightIn(min = 42.dp).padding(horizontal = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                            Text(
                                "brrrNEWS",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "CURRENT AFFAIRS",
                                style = MaterialTheme.typography.labelSmall,
                                letterSpacing = 0.8.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Text(
                            "TODAY",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    val searchShape = glassControlShape
                    Row(
                        Modifier.fillMaxWidth().heightIn(min = 48.dp)
                            .clip(searchShape)
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = previewControlSurfaceAlpha))
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = previewControlRimAlpha), searchShape)
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(9.dp))
                        Text(
                            "Search articles",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            "Filters",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    SupportingGlassCard(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "THE HINDU",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.weight(1f))
                                Text(
                                    "GS II",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            Text(
                                "How a policy decision shapes India's federal balance",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                "Publisher report · GS II connection",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        Modifier.fillMaxWidth().heightIn(min = 58.dp),
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        previewTabs.forEachIndexed { index, (label, icon) ->
                            val selected = index == 0
                            Column(
                                Modifier.weight(1f).heightIn(min = 48.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = previewSelectedFillAlpha)
                                        else Color.Transparent
                                    )
                                    .padding(vertical = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = null,
                                    tint = if (selected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    label,
                                    fontSize = 9.sp,
                                    color = if (selected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                }
            }
        }
    }
}
@Composable
private fun QuizScreen(
    article: Article,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    dark: Boolean,
    onBack: () -> Unit,
    onSubmit: (Map<Int, Int>, List<QuizQuestion>) -> Unit
) {
    val relevance = remember(article.id, article.title, article.description) {
        UpscEngine.classify(article)
    }
    val questions = remember(article.id, article.title, article.description, relevance) {
        UpscEngine.questions(article, relevance)
    }
    var selected by remember(article.id, article.title, article.description) {
        mutableStateOf<Map<Int, Int>>(emptyMap())
    }
    var submitted by remember(article.id, article.title, article.description) {
        mutableStateOf(false)
    }
    val score = questions.indices.count { selected[it] == questions[it].correctIndex }
    val reduceTransparency = LocalGlassReduceTransparency.current
    val optionShape = remember { ContinuousCornerShape(16.dp) }
    val successColor = if (dark) Color(0xFF75D9A4) else Color(0xFF168653)

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        PrismalTransientSurface(
            backdrop = backdrop,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
            glassAlpha = glassAlpha,
            dark = dark,
            cornerRadiusDp = 22f
        ) {
            Row(
                Modifier.fillMaxWidth().heightIn(min = 60.dp)
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack, modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Outlined.ArrowBack, contentDescription = "Back to article")
                }
                Column(
                    Modifier.weight(1f).padding(vertical = 3.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        "Your reading check",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        "Five questions · Basic to application",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    selected.size.toString() + "/" + questions.size,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 10.dp).semantics {
                        contentDescription = "Answered " + selected.size + " of " +
                            questions.size + " questions"
                    }
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            shape = ContinuousCornerShape(22.dp)
        ) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text("Question-source note", fontWeight = FontWeight.Bold)
                Text(
                    "This review uses the publisher's RSS headline and excerpt for story context. It does not test the full article body.",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "Q1: headline comprehension · Q2: indicative syllabus keyword mapping · Q3–Q5: original source-literacy and GS answer-framing practice. None is an official PYQ.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item {
                Text(article.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(relevance.gsPaper + " · " + relevance.subject + " · " + relevance.signalLabel,
                    color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
                Text(
                    if (relevance.matchedTerms.isEmpty()) "No mapped keywords in RSS headline/excerpt"
                    else "Matched terms: " + relevance.matchedTerms.joinToString(", "),
                    color = MaterialTheme.colorScheme.secondary, fontSize = 11.sp
                )
                Text("Answered: " + selected.size + "/" + questions.size,
                    style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            }
            items(
                count = questions.size,
                key = { index -> "quiz-question-$index" },
                contentType = { "quiz-question" }
            ) { index ->
                val question = questions[index]
                Card(shape = ContinuousCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        Text(question.level + " / " + question.type.uppercase(),
                            color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        Text(question.prompt, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        question.options.forEachIndexed { optionIndex, option ->
                            val chosen = selected[index] == optionIndex
                            val optionState = QuizOptionStylePolicy.resolve(
                                selected = chosen,
                                submitted = submitted,
                                correct = optionIndex == question.correctIndex
                            )
                            val optionColor = when (optionState) {
                                QuizOptionVisualState.SELECTED -> MaterialTheme.colorScheme.primary
                                QuizOptionVisualState.CORRECT -> successColor
                                QuizOptionVisualState.INCORRECT -> MaterialTheme.colorScheme.error
                                QuizOptionVisualState.IDLE,
                                QuizOptionVisualState.DISABLED -> MaterialTheme.colorScheme.outlineVariant
                            }
                            val optionTextColor = when (optionState) {
                                QuizOptionVisualState.CORRECT -> successColor
                                QuizOptionVisualState.INCORRECT -> MaterialTheme.colorScheme.error
                                QuizOptionVisualState.DISABLED -> MaterialTheme.colorScheme.onSurfaceVariant
                                QuizOptionVisualState.IDLE,
                                QuizOptionVisualState.SELECTED -> MaterialTheme.colorScheme.onSurface
                            }
                            val optionFill = optionColor.copy(
                                alpha = QuizOptionStylePolicy.fillAlpha(optionState, reduceTransparency)
                            )
                            val optionBorder = optionColor.copy(
                                alpha = QuizOptionStylePolicy.borderAlpha(optionState, reduceTransparency)
                            )
                            val optionStateDescription = when (optionState) {
                                QuizOptionVisualState.IDLE -> "Not selected"
                                QuizOptionVisualState.SELECTED -> "Selected"
                                QuizOptionVisualState.CORRECT -> "Correct answer"
                                QuizOptionVisualState.INCORRECT -> "Incorrect selected answer"
                                QuizOptionVisualState.DISABLED -> "Not selected"
                            }
                            OutlinedButton(
                                onClick = { if (!submitted) selected = selected + (index to optionIndex) },
                                modifier = Modifier.fillMaxWidth().heightIn(min = 52.dp)
                                    .semantics {
                                        this.selected = chosen
                                        stateDescription = optionStateDescription
                                    },
                                shape = optionShape,
                                border = BorderStroke(1.dp, optionBorder),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = optionFill,
                                    contentColor = optionTextColor,
                                    disabledContainerColor = optionFill,
                                    disabledContentColor = optionTextColor
                                ),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                                enabled = !submitted
                            ) {
                                Text(
                                    (if (chosen) "●  " else "○  ") + option,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = optionTextColor
                                )
                            }
                        }
                        Text(question.provenance, style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary)
                        if (submitted) {
                            Text(if (selected[index] == question.correctIndex) "Correct" else
                                if (selected.containsKey(index)) "Incorrect" else "Not answered",
                                fontWeight = FontWeight.Bold,
                                color = if (selected[index] == question.correctIndex) successColor
                                else MaterialTheme.colorScheme.error)
                            Text(question.explanation, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            if (submitted) item {
                Card(
                    shape = ContinuousCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(Modifier.padding(17.dp)) {
                        Text("Your result: " + score + "/" + questions.size,
                            style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(selected.size.toString() + " answered · " + score + " correct · " +
                            (selected.size - score).coerceAtLeast(0) + " incorrect")
                        Text("Only this submitted attempt is included in your practice statistics. These are practice questions, not predictions of future UPSC papers.")
                    }
                }
            }
        }
        Box(Modifier.fillMaxWidth().navigationBarsPadding()) {
            PrismalTransientSurface(
                backdrop = backdrop,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp),
                glassAlpha = glassAlpha,
                dark = dark,
                cornerRadiusDp = 22f
            ) {
                Column(
                    Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 9.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    LinearProgressIndicator(
                        progress = selected.size.toFloat() / questions.size.coerceAtLeast(1),
                        modifier = Modifier.fillMaxWidth().height(3.dp).clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.42f)
                    )
                    Text(
                        selected.size.toString() + " of " + questions.size + " answered",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = onBack,
                            modifier = Modifier.weight(1f).heightIn(min = 48.dp),
                            shape = ContinuousCornerShape(16.dp)
                        ) { Text("Done") }
                        Button(
                            onClick = {
                                if (!submitted) {
                                    submitted = true
                                    onSubmit(selected.toMap(), questions)
                                }
                            },
                            modifier = Modifier.weight(1f).heightIn(min = 48.dp),
                            shape = ContinuousCornerShape(16.dp),
                            enabled = selected.isNotEmpty() && !submitted
                        ) { Text(if (submitted) "Submitted" else "Submit answers") }
                    }
                }
            }
        }
    }
}
