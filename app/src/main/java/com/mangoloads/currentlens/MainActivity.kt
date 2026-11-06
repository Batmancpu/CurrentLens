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
import android.os.SystemClock
import android.webkit.WebResourceRequest
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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.AccountCircle
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
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
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
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
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.selected
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
import com.styropyr0.prismal.components.PrismalGlassSlider
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private const val INDIA_ZONE = "Asia/Kolkata"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
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
            MaterialTheme(colorScheme = colors) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colors.background,
                    contentColor = colors.onBackground
                ) { CurrentLensApp() }
            }
        }
    }
}

private enum class YouPage { DASHBOARD, SAVED, READING_STATS, QUIZ_STATS }

@Composable
private fun CurrentLensApp() {
    val context = LocalContext.current
    val backdrop = rememberPrismalGlassLayer()
    val dark = isSystemInDarkTheme()
    var articles by remember { mutableStateOf(LocalStore.cachedArticles(context)) }
    var bookmarks by remember { mutableStateOf(LocalStore.bookmarks(context)) }
    var readHistory by remember { mutableStateOf(LocalStore.readHistory(context)) }
    var readingSessions by remember { mutableStateOf(LocalStore.readingSessions(context)) }
    var quizAttempts by remember { mutableStateOf(LocalStore.quizAttempts(context)) }
    var loading by remember { mutableStateOf(articles.isEmpty()) }
    var refreshing by remember { mutableStateOf(false) }
    var loadError by remember { mutableStateOf(false) }
    var refreshKey by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var youPage by remember { mutableStateOf(YouPage.DASHBOARD) }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }
    var quizMode by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var glassAlpha by remember { mutableFloatStateOf(LocalStore.glassAlpha(context)) }
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

    // One shared 8x8 probe samples the full-width bottom 104dp of the live root backdrop.
    // This bounded band follows the IME-resized root, staying behind both navigation
    // glass and the keyboard-docked Search composer instead of averaging unrelated
    // content across the full screen. Cadence remains 600ms on API 31+ and 1500ms
    // on legacy devices; sampling is foreground-only and shared by both surfaces.
    val glassLuminance = rememberBottomBandGlassLuminance(
        enabled = appResumed && selectedArticle == null && !showSettings,
        source = backdrop,
        isLightTheme = !dark,
        bandHeight = 104.dp
    )

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

    LaunchedEffect(refreshKey) {
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

    BoxWithConstraints(
        Modifier.fillMaxSize().background(
            if (searchExpanded) Color.Transparent else MaterialTheme.colorScheme.background
        )
    ) {
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
                .prismalGlassLayer(backdrop)
                .background(if (searchExpanded) Color.Transparent else MaterialTheme.colorScheme.background)
        ) {
            when {
                selectedArticle != null && quizMode -> QuizScreen(
                    article = selectedArticle!!,
                    onBack = { quizMode = false },
                    onSubmit = { answers, questions ->
                        LocalStore.recordQuizAttempt(context, selectedArticle!!, answers, questions)
                        quizAttempts = LocalStore.quizAttempts(context)
                    }
                )
                selectedArticle != null -> ReaderScreen(
                    article = selectedArticle!!,
                    backdrop = backdrop,
                    glassAlpha = glassAlpha,
                    dark = dark,
                    onBack = { selectedArticle = null },
                    onQuiz = { quizMode = true },
                    onSessionRecorded = { readingSessions = LocalStore.readingSessions(context) }
                )
                showSettings -> GlassSettingsScreen(
                    backdrop = backdrop,
                    glassAlpha = glassAlpha,
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
                    loading = loading,
                    loadError = loadError,
                    todaysArticles = ArticleFilters.today(articles, LocalDate.now(ZoneId.of(INDIA_ZONE)))
                        .filter(UpscFeedPolicy::shouldInclude),
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
                    onOpenArticle = {
                        searchExpanded = false
                        LocalStore.markRead(context, it.url)
                        readHistory = LocalStore.readHistory(context)
                        selectedArticle = it
                    },
                    onToggleBookmark = {
                        LocalStore.toggleBookmark(context, it.url)
                        bookmarks = LocalStore.bookmarks(context)
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
                    morphProgress = if (selectedTab == SearchNavigationPolicy.SEARCH_TAB && searchMorphActive) {
                        searchMorphProgress
                    } else 0f,
                    onSearchAnchor = { searchTabAnchorState.value = it },
                    onSelect = { tab ->
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
                    onSubmit = requestSearchCollapse,
                    onClear = { searchQuery = "" }
                )
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
    val dateLabel = remember(today) { today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)) }
    var exploreCategory by remember { mutableStateOf("All") }
    val exploreArticles = remember(allCachedArticles) {
        ArticleFilters.explore(allCachedArticles).filter(UpscFeedPolicy::shouldInclude)
    }

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
            transitionSpec = { fadeIn(tween(190)) togetherWith fadeOut(tween(120)) },
            label = "CurrentLens destination transition"
        ) { target ->
            val (tab, page) = target
            when (tab) {
                0 -> TodayFeed(
                    todaysArticles, allCachedArticles, loading, loadError, refreshing,
                    bookmarks, onOpenArticle, onToggleBookmark
                )
                1 -> ExploreFeed(
                    exploreArticles, exploreCategory,
                    { exploreCategory = it }, bookmarks, onOpenArticle, onToggleBookmark,
                    loading, loadError, backdrop, glassAlpha, isSystemInDarkTheme()
                )
                2 -> SearchFeed(
                    allCachedArticles, searchQuery, searchExpanded,
                    bookmarks, onOpenArticle, onToggleBookmark, loading, loadError
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
                        allCachedArticles, bookmarks, readHistory, onOpenArticle, onToggleBookmark
                    )
                    YouPage.READING_STATS -> ReadingStatsScreen(readingSessions)
                    YouPage.QUIZ_STATS -> QuizStatsScreen(quizAttempts)
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
    val surfaceTint = MaterialTheme.colorScheme.surface
    val luminance = if (dark) 0.12f else 0.82f
    Box(
        Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 5.dp)
            .drawPrismalGlass(
                backdrop = backdrop,
                shape = { PrismalRoundedRectangle(24.dp) },
                effects = {
                    applyPrismalGlassEffects(
                        density = density, adaptiveLuminance = true, luminance = luminance,
                        blurRadiusPx = with(density) { 13.dp.toPx() },
                        refractionHeightPx = with(density) { 4.dp.toPx() },
                        refractionAmountPx = with(density) { 6.dp.toPx() },
                        depthEffect = true, chromaticAberration = 0.003f,
                        adaptiveTuning = PrismalAdaptiveTuning.Subtle
                    )
                },
                specular = {
                    PrismalSpecular.Default.copy(width = 0.55.dp, blurRadius = 0.35.dp, alpha = 0.28f)
                },
                depthShadow = {
                    PrismalDepthShadow(radius = 12.dp, offset = DpOffset(0.dp, 2.dp),
                        color = Color.Black.copy(alpha = 0.09f), alpha = 0.20f)
                },
                depthInset = {
                    PrismalDepthInset(radius = 4.dp, offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.05f), alpha = 0.05f)
                },
                onDrawSurface = { drawRect(surfaceTint.copy(alpha = glassAlpha * 0.13f)) }
            )
    ) {
        Row(Modifier.fillMaxWidth().padding(start = 14.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            if (showBack) IconButton(onClick = onBack, modifier = Modifier.size(44.dp)) {
                Icon(Icons.Outlined.ArrowBack, contentDescription = "Back to You", modifier = Modifier.size(23.dp))
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(1.dp)) {
                Text(pageTitle, style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Text(subtitle, style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (showRefresh) IconButton(onClick = onRefresh, enabled = !refreshing, modifier = Modifier.size(46.dp)) {
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
    val surfaceTint = MaterialTheme.colorScheme.surface
    val luminance = if (dark) 0.14f else 0.80f
    val chipShape = ContinuousCornerShape(18.dp)
    Box(
        Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 5.dp)
            .drawPrismalGlass(
                backdrop = backdrop,
                shape = { PrismalRoundedRectangle(22.dp) },
                effects = {
                    applyPrismalGlassEffects(
                        density = density, adaptiveLuminance = true, luminance = luminance,
                        blurRadiusPx = with(density) { 8.dp.toPx() },
                        refractionHeightPx = with(density) { 3.dp.toPx() },
                        refractionAmountPx = with(density) { 4.dp.toPx() },
                        depthEffect = true, chromaticAberration = 0.0015f,
                        adaptiveTuning = PrismalAdaptiveTuning.Subtle
                    )
                },
                specular = {
                    PrismalSpecular.Default.copy(width = 0.45.dp, blurRadius = 0.30.dp, alpha = 0.20f)
                },
                depthShadow = {
                    PrismalDepthShadow(radius = 9.dp, offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.07f), alpha = 0.12f)
                },
                depthInset = {
                    PrismalDepthInset(radius = 3.dp, offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.04f), alpha = 0.04f)
                },
                onDrawSurface = { drawRect(surfaceTint.copy(alpha = glassAlpha * 0.09f)) }
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
                        .clickable { onCategory(category) }
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
    val weekendStories = if (weekend) {
        weekendArticles.filter { article ->
            article.weekendRead && UpscFeedPolicy.shouldInclude(article) &&
                ArticleFilters.publishedDate(article)?.let { it >= weekendStart && it <= today } == true
        }.sortedByDescending { it.publishedAtMillis ?: 0L }.take(5)
    } else emptyList()

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
                Text(if (refreshing || loading) "Updating" else articles.size.toString() + " relevant stories",
                    color = MaterialTheme.colorScheme.secondary, fontSize = 11.sp)
            }
        }
        when {
            loading && articles.isEmpty() -> items(3) { FeedSkeletonCard() }
            loadError && articles.isEmpty() -> item { EmptyPanel("News is temporarily unavailable", "Check your connection and refresh.") }
            articles.isEmpty() -> item { EmptyPanel("No UPSC-relevant stories today", "Broad entertainment and general-interest updates are filtered out. Refresh later for syllabus-linked developments.") }
            else -> items(articles.take(45), key = { it.id }) { article ->
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
        Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                Text("The best of the weekend", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Express Sunday Eye stories published in the latest weekend window.",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                articles.forEach { article ->
                    Text(article.title,
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).clickable { onOpenArticle(article) }.padding(vertical = 7.dp),
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
    val categories = listOf("All", "India", "World", "Politics", "Economy", "Environment", "Science", "Explained", "UPSC", "Opinion")
    val filtered = articles.filter { article -> ExploreFilters.matches(article, selectedCategory, "") }
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
                items(3) { FeedSkeletonCard() }
            } else if (filtered.isEmpty() && loadError) {
                item { EmptyPanel("News is temporarily unavailable", "Check your connection and refresh.") }
            } else if (filtered.isEmpty()) item {
                EmptyPanel("No stories in this topic", "Try another topic or refresh your feeds.")
            } else items(filtered, key = { it.id }) { article ->
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
    loadError: Boolean
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    var showFilters by remember { mutableStateOf(false) }
    val filterSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val keyboardController = LocalSoftwareKeyboardController.current
    val eligibleArticles = remember(articles) { articles.filter(UpscFeedPolicy::shouldInclude) }
    val categories = listOf("All", "India", "World", "Politics", "Economy", "Environment", "Science", "Explained", "UPSC", "Opinion")
    val subjects = remember(eligibleArticles) {
        listOf("All") + eligibleArticles.map { UpscEngine.classify(it).subject }.distinct().sorted()
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
    val filtered = eligibleArticles.filter { article ->
        val relevance = UpscEngine.classify(article)
        ExploreFilters.matches(article, selectedCategory, query) &&
            (selectedSubject == "All" || relevance.subject == selectedSubject) &&
            (selectedGsPaper == "All" || relevance.gsPaper == selectedGsPaper)
    }
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 2.dp),
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
            FilterChip(
                selected = activeFilterCount > 0,
                onClick = {
                    keyboardController?.hide()
                    showFilters = true
                },
                label = { Text(if (activeFilterCount == 0) "Filters" else "Filters · $activeFilterCount") },
                leadingIcon = {
                    Icon(Icons.Outlined.Tune, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            )
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
                items(3) { FeedSkeletonCard() }
            } else if (filtered.isEmpty() && loadError) {
                item { EmptyPanel("News is temporarily unavailable", "Check your connection and refresh.") }
            } else if (filtered.isEmpty()) item {
                EmptyPanel("No matching articles", "Change your search or remove one of the filters.")
            } else items(filtered, key = { it.id }) { article ->
                NewsCard(article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark)
            }
        }
    }
    if (showFilters) {
        ModalBottomSheet(
            onDismissRequest = { showFilters = false },
            sheetState = filterSheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ) {
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
    onSubmit: () -> Unit,
    onClear: () -> Unit
) {
    val density = LocalDensity.current
    val progress = morphProgress.coerceIn(0f, 1f)
    val optics = GlassOpticsPolicy.search(progress)
    val glassTint = if (adaptiveContentColor == Color.Black) Color(0xFFF5F8FC) else Color(0xFF202B3A)
    val tintAlpha = GlassOpticsPolicy.adaptiveTintAlpha(
        baseAlpha = glassAlpha * optics.tintAlpha,
        luminance = luminance,
        minAlpha = 0.04f,
        maxAlpha = 0.12f
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
                        blurRadiusPx = with(density) { optics.blurDp.dp.toPx() },
                        refractionHeightPx = with(density) { optics.refractionHeightDp.dp.toPx() },
                        refractionAmountPx = with(density) { optics.refractionAmountDp.dp.toPx() },
                        depthEffect = true,
                        chromaticAberration = optics.chromaticAberration,
                        adaptiveTuning = PrismalAdaptiveTuning.Subtle
                    )
                },
                specular = {
                    PrismalSpecular.Default.copy(
                        width = (0.55f + 0.20f * progress).dp,
                        blurRadius = (0.30f + 0.15f * progress).dp,
                        alpha = optics.specularAlpha
                    )
                },
                depthShadow = {
                    PrismalDepthShadow(
                        radius = (12f + 6f * progress).dp,
                        offset = DpOffset(0.dp, (2f + progress).dp),
                        color = Color.Black.copy(alpha = 0.12f),
                        alpha = optics.shadowAlpha
                    )
                },
                depthInset = {
                    PrismalDepthInset(
                        radius = (4f + progress).dp,
                        offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.07f),
                        alpha = optics.insetAlpha
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
            if (query.isNotBlank() && progress > 0.34f) {
                IconButton(
                    onClick = onClear,
                    enabled = enabled,
                    modifier = Modifier.size(38.dp).graphicsLayer { alpha = progress }
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


@Composable
private fun SavedFeed(
    articles: List<Article>,
    bookmarks: Set<String>,
    readHistory: Set<String>,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit
) {
    var dateRange by remember { mutableStateOf(StatsDateRange.ALL_TIME) }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val savedArticles = articles.filter { bookmarks.contains(it.url) }
    val subjects = remember(savedArticles) {
        listOf("All") + savedArticles.map { UpscEngine.classify(it).subject }.distinct().sorted()
    }
    val filtered = StudyStatsPolicy.filterArticles(
        savedArticles, dateRange, selectedSubject, selectedGsPaper, today
    )
    val previouslyOpened = articles.filter {
        readHistory.contains(it.url) && !bookmarks.contains(it.url) && UpscFeedPolicy.shouldInclude(it)
    }
        .sortedByDescending { it.publishedAtMillis ?: 0L }.take(15)

    Column(Modifier.fillMaxSize()) {
        FilterChipRow("Published", StatsDateRange.values().map { it.label }, dateRange.label) { label ->
            dateRange = StatsDateRange.values().first { it.label == label }
        }
        FilterChipRow("GS paper", listOf("All", "GS I", "GS II", "GS III", "GS IV", "Unmapped"),
            selectedGsPaper, { selectedGsPaper = it })
        FilterChipRow("Subject", subjects, selectedSubject, { selectedSubject = it })
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
                EmptyPanel("Nothing saved yet", "Tap the bookmark on any story to keep it here.")
            } else if (filtered.isEmpty()) item {
                EmptyPanel("No saved articles match", "Change the publication-date, subject or GS-paper filter.")
            } else items(filtered, key = { it.id }) { article ->
                NewsCard(article, true, onOpenArticle, onToggleBookmark)
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
    val answersAttempted = attempts.sumOf { it.answeredCount }
    val correctAnswers = attempts.sumOf { it.correctCount }
    val accuracy = if (answersAttempted == 0) "—"
        else ((correctAnswers * 100f / answersAttempted).toInt().toString() + "%")
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 112.dp),
        verticalArrangement = Arrangement.spacedBy(13.dp)
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
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                MetricTile("Articles read", totalUniqueRead.toString(), Modifier.weight(1f))
                MetricTile("Read today", articlesReadToday.toString(), Modifier.weight(1f))
            }
        }
        if (attempts.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(21.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(Modifier.fillMaxWidth().padding(17.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)) {
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
                Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                    MetricTile("Quiz attempts", attempts.size.toString(), Modifier.weight(1f))
                    MetricTile("Accuracy", accuracy, Modifier.weight(1f))
                }
            }
        }
        item {
            Text("YOUR LIBRARY", color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp,
                modifier = Modifier.padding(top = 5.dp))
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
private fun MetricTile(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(Modifier.padding(horizontal = 14.dp, vertical = 13.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary)
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        }
    }
}

@Composable
private fun DestinationRow(title: String, description: String, trailing: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
            Text(trailing, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun FilterChipRow(
    title: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(top = 3.dp)) {
        Text(title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 16.dp, bottom = 2.dp))
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 13.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            options.distinct().forEach { option ->
                FilterChip(selected = selected == option, onClick = { onSelected(option) },
                    label = { Text(option, fontSize = 11.sp) })
            }
        }
    }
}

@Composable
private fun ReadingStatsScreen(sessions: List<ReadingSession>) {
    var dateRange by remember { mutableStateOf(StatsDateRange.LAST_7_DAYS) }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val subjects = remember(sessions) {
        listOf("All") + sessions.map { it.subject }.distinct().sorted()
    }
    val summary = StudyStatsPolicy.readingSummary(
        sessions, dateRange, selectedSubject, selectedGsPaper, today
    )
    Column(Modifier.fillMaxSize()) {
        FilterChipRow("Date", StatsDateRange.values().map { it.label }, dateRange.label) { label ->
            dateRange = StatsDateRange.values().first { it.label == label }
        }
        FilterChipRow("GS paper", listOf("All", "GS I", "GS II", "GS III", "GS IV", "Unmapped"),
            selectedGsPaper, { selectedGsPaper = it })
        FilterChipRow("Subject", subjects, selectedSubject, { selectedSubject = it })
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MetricTile("Reading time", formatDuration(summary.totalDurationMillis), Modifier.weight(1f))
            MetricTile("Unique articles", summary.uniqueArticleCount.toString(), Modifier.weight(1f))
            MetricTile("Sessions", summary.sessionCount.toString(), Modifier.weight(1f))
        }
        if (summary.days.isEmpty()) {
            EmptyPanel("No timed reading sessions in this range",
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
                    Card(shape = RoundedCornerShape(17.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
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
private fun QuizStatsScreen(attempts: List<QuizAttempt>) {
    var dateRange by remember { mutableStateOf(StatsDateRange.ALL_TIME) }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val subjects = remember(attempts) {
        listOf("All") + attempts.map { it.subject }.distinct().sorted()
    }
    val summary = StudyStatsPolicy.quizSummary(
        attempts, dateRange, selectedSubject, selectedGsPaper, today
    )
    val accuracy = if (summary.answersAttempted == 0) "—" else
        ((summary.correctAnswers * 100f / summary.answersAttempted).toInt().toString() + "%")
    Column(Modifier.fillMaxSize()) {
        FilterChipRow("Date", StatsDateRange.values().map { it.label }, dateRange.label) { label ->
            dateRange = StatsDateRange.values().first { it.label == label }
        }
        FilterChipRow("GS paper", listOf("All", "GS I", "GS II", "GS III", "GS IV", "Unmapped"),
            selectedGsPaper, { selectedGsPaper = it })
        FilterChipRow("Subject", subjects, selectedSubject, { selectedSubject = it })
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MetricTile("Attempts", summary.attemptCount.toString(), Modifier.weight(1f))
            MetricTile("Answered", summary.answersAttempted.toString(), Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            MetricTile("Correct", summary.correctAnswers.toString(), Modifier.weight(1f))
            MetricTile("Incorrect", summary.wrongAnswers.toString(), Modifier.weight(1f))
            MetricTile("Accuracy", accuracy, Modifier.weight(1f))
        }
        if (summary.attempts.isEmpty()) {
            EmptyPanel("No submitted quizzes in this range",
                "Quiz statistics are saved only after you submit answers. Unsubmitted selections are not counted.")
        } else {
            LazyColumn(
                Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                items(summary.attempts, key = { it.id }) { attempt ->
                    Card(shape = RoundedCornerShape(17.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Column(Modifier.fillMaxWidth().padding(14.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
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
    onToggleBookmark: (Article) -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val relevance = remember(article.id) { UpscEngine.classify(article) }
    val syllabusHint = remember(article.id, relevance.gsPaper, relevance.subject, relevance.matchedTerms) {
        ArticleCardMetaPolicy.syllabusHint(
            gsPaper = relevance.gsPaper,
            subject = relevance.subject,
            matchedTerms = relevance.matchedTerms
        )
    }
    val hasImage = !article.imageUrl.isNullOrBlank()
    val imageRequest = remember(article.imageUrl) {
        article.imageUrl?.let { url ->
            ImageRequest.Builder(context).data(url).allowHardware(false).crossfade(false).build()
        }
    }
    var imagePalette by remember(article.id) { mutableStateOf<Color?>(null) }
    val palette = imagePalette

    Box(
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
            .graphicsLayer { clip = false }
            .drawBehind {
                val sampled = palette ?: return@drawBehind
                val overscan = with(density) { 24.dp.toPx() }
                drawRoundRect(
                    brush = Brush.radialGradient(
                        colors = listOf(sampled.copy(alpha = 0.30f), sampled.copy(alpha = 0.16f), Color.Transparent),
                        center = Offset(size.width * 0.5f, size.height * 0.40f),
                        radius = maxOf(size.width, size.height) * 0.82f
                    ),
                    topLeft = Offset(-overscan, -overscan),
                    size = Size(size.width + overscan * 2f, size.height + overscan * 2f),
                    cornerRadius = CornerRadius(with(density) { 34.dp.toPx() })
                )
            }
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onOpenArticle(article) },
            shape = ContinuousCornerShape(29.dp),
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
                            val bitmap = (state.result.drawable as? BitmapDrawable)?.bitmap
                            val sampled = bitmap?.let(::sampleImagePalette)
                            if (sampled != null && imagePalette != sampled) imagePalette = sampled
                        },
                        modifier = Modifier.fillMaxWidth().height(190.dp)
                            .clip(ContinuousCornerShape(23.dp))
                    )
                }
                Column(Modifier.padding(horizontal = 15.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(article.source.uppercase(), color = MaterialTheme.colorScheme.primary,
                            fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.65.sp)
                        Text("  ·  " + article.section, color = MaterialTheme.colorScheme.secondary,
                            fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Spacer(Modifier.weight(1f))
                        IconButton(onClick = { onToggleBookmark(article) }, modifier = Modifier.size(40.dp)) {
                            Icon(if (saved) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                                contentDescription = if (saved) "Remove bookmark" else "Save article",
                                tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(21.dp))
                        }
                    }
                    SelectionContainer {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(article.title, style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold, lineHeight = 28.sp)
                            if (article.description.isNotBlank()) {
                                Text(article.description, style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 4,
                                    overflow = TextOverflow.Ellipsis, lineHeight = 23.sp)
                            }
                        }
                    }
                    syllabusHint?.let { hint ->
                        Text(hint, style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold,
                            maxLines = 2, overflow = TextOverflow.Ellipsis)
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
private fun FeedSkeletonCard() {
    val base = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.82f)
    val line = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.075f)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = ContinuousCornerShape(29.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Box(Modifier.fillMaxWidth().height(176.dp).background(base))
            Column(Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.width(86.dp).height(10.dp).clip(CircleShape).background(line))
                    Spacer(Modifier.width(9.dp))
                    Box(Modifier.width(64.dp).height(10.dp).clip(CircleShape).background(line))
                    Spacer(Modifier.weight(1f))
                    Box(Modifier.size(22.dp).clip(CircleShape).background(line))
                }
                Box(Modifier.fillMaxWidth(0.88f).height(25.dp).clip(CircleShape).background(line))
                Box(Modifier.fillMaxWidth(0.71f).height(25.dp).clip(CircleShape).background(line))
                Box(Modifier.fillMaxWidth().height(13.dp).clip(CircleShape).background(line))
                Box(Modifier.fillMaxWidth(0.82f).height(13.dp).clip(CircleShape).background(line))
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
    morphProgress: Float,
    onSearchAnchor: (Offset) -> Unit,
    onSelect: (Int) -> Unit
) {
    val density = LocalDensity.current
    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr
    val animationScope = rememberCoroutineScope()
    val latestOnSelect = rememberUpdatedState(onSelect)
    val tabsBackdrop = rememberPrismalGlassLayer()
    val dropletBackdrop = rememberPrismalMergedSource(backdrop, tabsBackdrop)
    tabsBackdrop.readSamplingState()
    val barAlpha = SearchMorphGeometryPolicy.tabBarAlpha(morphProgress)
    val glassTint = if (adaptiveContentColor == Color.Black) Color(0xFFEAF0FA) else Color(0xFF1C2E49)
    val baseTintAlpha = GlassOpticsPolicy.adaptiveTintAlpha(
        baseAlpha = glassAlpha * 0.12f,
        luminance = luminance,
        minAlpha = 0.035f,
        maxAlpha = 0.13f
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth().height(70.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val tabWidthPx = (
            constraints.maxWidth.toFloat() - with(density) { 8.dp.toPx() }
        ).div(4f).coerceAtLeast(1f)
        val tabWidth = with(density) { tabWidthPx.toDp() }
        val contentStartPx = with(density) { 4.dp.toPx() }
        val contentHeight = 64.dp
        val contentHeightPx = with(density) { contentHeight.toPx() }
        val barHeightPx = with(density) { 70.dp.toPx() }
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
                    val next = (targetValue + delta.x / tabWidthPx * direction)
                        .coerceIn(0f, 3f)
                    if (reduceMotion) snapToValue(next) else updateValue(next)
                    dragHighlightIndex = next.roundToInt().coerceIn(0, 3)
                }
            )
        }

        LaunchedEffect(selectedTab, reduceMotion) {
            dragHighlightIndex = null
            if (reduceMotion) motion.snapToValue(selectedTab.toFloat())
            else motion.animateToValue(selectedTab.toFloat())
        }

        val highlightedIndex = dragHighlightIndex ?: selectedTab
        val pillPress = GlassOpticsPolicy.interactionProgress(motion.pressProgress, reduceMotion)
        val pillVelocity = if (reduceMotion) 0f else motion.velocity.coerceIn(-2f, 2f)
        val contentTopPx = (barHeightPx - contentHeightPx) / 2f
        val dropletOptics = GlassOpticsPolicy.droplet(pillPress, reduceMotion)
        val dropletTint = if (adaptiveContentColor == Color.Black) Color(0xFFE8F1FF) else Color(0xFF1C2E49)
        val dropletTintAlpha = GlassOpticsPolicy.adaptiveTintAlpha(
            baseAlpha = glassAlpha * dropletOptics.tintAlpha,
            luminance = luminance,
            minAlpha = 0.045f,
            maxAlpha = 0.14f
        )

        // Reuse Prismal's touch ripple and keep it on the expanded optical droplet.
        // The ripple is drawn over the base material; its centre follows the selected tab.
        val tabRipple = remember(animationScope, tabWidthPx, contentStartPx) {
            PrismalPressRipple(animationScope) { size, _ ->
                val center = GlassTabGeometryPolicy.tabCenterPx(
                    contentStartPx, motion.value, tabWidthPx
                )
                Offset(center, size.height / 2f)
            }
        }
        // Prismal's pre-33 fallback is a flat white flash, not a shader ripple.
        // Keep spring/lens feedback on those devices without that visual regression.
        val rippleEnabled = GlassOpticsPolicy.rippleEnabled(Build.VERSION.SDK_INT, reduceMotion)
        val rippleDrawModifier = if (rippleEnabled) tabRipple.modifier else Modifier
        val rippleGestureModifier = if (rippleEnabled) tabRipple.gestureModifier else Modifier

        // The sampled root includes the page background and content, but excludes this
        // overlay bar; the tab-content layer is merged only for the selected droplet.
        Box(
            Modifier.fillMaxSize().graphicsLayer { alpha = barAlpha }.drawPrismalGlass(
                backdrop = backdrop,
                shape = { PrismalCapsule() },
                effects = {
                    applyPrismalGlassEffects(
                        density = density,
                        adaptiveLuminance = true,
                        luminance = luminance,
                        blurRadiusPx = with(density) { 6.dp.toPx() },
                        refractionHeightPx = with(density) { 14.dp.toPx() },
                        refractionAmountPx = with(density) { 20.dp.toPx() },
                        depthEffect = true,
                        chromaticAberration = 0.015f,
                        adaptiveTuning = PrismalAdaptiveTuning.Subtle
                    )
                },
                specular = {
                    PrismalSpecular.Default.copy(
                        width = 0.6.dp,
                        blurRadius = 0.4.dp,
                        alpha = 0.48f
                    )
                },
                depthShadow = {
                    PrismalDepthShadow(
                        radius = 18.dp,
                        offset = DpOffset(0.dp, 3.dp),
                        color = Color.Black.copy(alpha = 0.10f),
                        alpha = 0.28f
                    )
                },
                depthInset = {
                    PrismalDepthInset(
                        radius = 4.dp,
                        offset = DpOffset(0.dp, 1.dp),
                        color = Color.Black.copy(alpha = 0.06f),
                        alpha = 0.06f
                    )
                },
                onDrawSurface = {
                    drawRect(glassTint.copy(alpha = baseTintAlpha))
                }
            )
        )
        Box(Modifier.fillMaxSize().clip(PrismalCapsule()).then(rippleDrawModifier)) {}

        CompositionLocalProvider(
            LocalPrismalBottomTabHighlightedIndex provides { highlightedIndex }
        ) {
            // Capture the tab glyphs once as a second source; keep this hidden layer
            // out of the accessibility tree so each destination is announced only once.
            Row(
                Modifier.graphicsLayer { alpha = 0f }
                    .clearAndSetSemantics {}
                    .prismalGlassLayer(tabsBackdrop)
                    .fillMaxSize()
                    .padding(horizontal = 4.dp, vertical = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassTab(0, "Today", Icons.Outlined.CalendarMonth, onSelect, adaptiveContentColor)
                GlassTab(1, "Explore", Icons.Outlined.Explore, onSelect, adaptiveContentColor)
                GlassTab(2, "Search", Icons.Outlined.Search, onSelect, adaptiveContentColor)
                GlassTab(3, "You", Icons.Outlined.AccountCircle, onSelect, adaptiveContentColor)
            }

            Row(
                Modifier.fillMaxSize()
                    .padding(horizontal = 4.dp, vertical = 3.dp)
                    .graphicsLayer { alpha = barAlpha },
                verticalAlignment = Alignment.CenterVertically
            ) {
                GlassTab(0, "Today", Icons.Outlined.CalendarMonth, onSelect, adaptiveContentColor)
                GlassTab(1, "Explore", Icons.Outlined.Explore, onSelect, adaptiveContentColor)
                GlassTab(2, "Search", Icons.Outlined.Search, onSelect, adaptiveContentColor, onSearchAnchor)
                GlassTab(3, "You", Icons.Outlined.AccountCircle, onSelect, adaptiveContentColor)
            }
        }

        // The expanded droplet itself is the drag target. Its touch bounds now match
        // the refracted rim rather than a narrower, visually detached tab-cell target.

        Box(
            Modifier.offset {
                val center = GlassTabGeometryPolicy.tabCenterPx(
                    contentStartPx, motion.value, tabWidthPx
                )
                IntOffset(
                    GlassTabGeometryPolicy.leftPx(center, dropletWidthPx).roundToInt(),
                    GlassTabGeometryPolicy.topPx(barHeightPx, dropletHeightPx).roundToInt()
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
                            blurRadiusPx = with(density) { dropletOptics.blurDp.dp.toPx() },
                            refractionHeightPx = with(density) { dropletOptics.refractionHeightDp.dp.toPx() },
                            refractionAmountPx = with(density) { dropletOptics.refractionAmountDp.dp.toPx() },
                            depthEffect = true,
                            chromaticAberration = dropletOptics.chromaticAberration,
                            adaptiveTuning = PrismalAdaptiveTuning.Subtle
                        )
                    },
                    specular = {
                        PrismalSpecular.Default.copy(
                            width = 0.7.dp,
                            blurRadius = 0.4.dp,
                            alpha = dropletOptics.specularAlpha,
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
                            alpha = dropletOptics.shadowAlpha
                        )
                    },
                    depthInset = {
                        PrismalDepthInset(
                            radius = 5.dp,
                            offset = DpOffset(0.dp, 1.dp),
                            color = Color.Black.copy(alpha = 0.08f),
                            alpha = dropletOptics.insetAlpha
                        )
                    },
                    onDrawSurface = {
                        drawRect(dropletTint.copy(alpha = dropletTintAlpha))
                    }
                )
            )
        }

        val activeGlyph = when (highlightedIndex) {
            0 -> Icons.Outlined.CalendarMonth
            1 -> Icons.Outlined.Explore
            2 -> Icons.Outlined.Search
            else -> Icons.Outlined.AccountCircle
        }
        Box(
            Modifier.offset {
                val center = GlassTabGeometryPolicy.tabCenterPx(contentStartPx, motion.value, tabWidthPx)
                val iconSizePx = with(density) { 27.dp.toPx() }
                IntOffset((center - iconSizePx / 2f).roundToInt(),
                    ((barHeightPx - iconSizePx) / 2f).roundToInt())
            }
                .size(27.dp)
                .clearAndSetSemantics {},
            contentAlignment = Alignment.Center
        ) {
            Icon(activeGlyph, contentDescription = null, tint = adaptiveContentColor,
                modifier = Modifier.size(27.dp))
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
    val tabModifier = positionModifier.semantics(mergeDescendants = true) { selected = isSelected }
    PrismalGlassBottomTab(onClick = { onSelect(index) }, modifier = tabModifier) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(27.dp))
    }
}

private fun matchesSearchTerms(article: Article, query: String): Boolean =
    ExploreFilters.matchesSearchTerms(article, query)

@Composable
private fun ReaderScreen(article: Article, backdrop: PrismalBackdrop, glassAlpha: Float, dark: Boolean, onBack: () -> Unit, onQuiz: () -> Unit, onSessionRecorded: () -> Unit) {
    val context = LocalContext.current
    val readerWebViewHolder = remember { mutableStateOf<ReaderWebView?>(null) }
    var savedReadingProgress by remember(article.url) {
        mutableFloatStateOf(LocalStore.readerPosition(context, article.url)?.progress ?: 0f)
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
                clock.pause(SystemClock.elapsedRealtime())?.let { window ->
                    if (LocalStore.recordReadingSession(context, article, window.startedAtMillis, window.durationMillis)) {
                        onSessionRecorded()
                    }
                }
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

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                readerWebViewHolder.value?.flushReadingPosition()
                onBack()
            }) { Icon(Icons.Outlined.ArrowBack, contentDescription = "Back") }
            Column(Modifier.weight(1f)) {
                Text(article.source, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                val readerSourceStatus = when {
                    extractedHtml == null -> "In-app publisher view"
                    loadedFromCache -> "Saved public content · on this device"
                    else -> "Public publisher content · attributed"
                }
                Text(
                    listOf(
                        ArticlePublicationDatePolicy.label(article.publishedAtMillis),
                        readerSourceStatus
                    ).joinToString(" · "),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(onClick = { fontSize = (fontSize - 1).coerceIn(16, 28) }) { Icon(Icons.Outlined.TextDecrease, contentDescription = "Smaller text") }
            IconButton(onClick = { fontSize = (fontSize + 1).coerceIn(16, 28) }) { Icon(Icons.Outlined.TextIncrease, contentDescription = "Larger text") }
        }
        if (extracting || (!pageLoaded && (extractedHtml != null || fallbackToPublisher))) androidx.compose.material3.LinearProgressIndicator(Modifier.fillMaxWidth())
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
                        val persisted = LocalStore.saveReaderPosition(context, article.url, progress)
                        savedReadingProgress = if (persisted) progress else 0f
                    }
                    webView.onReachedBottom = {
                        webView.markReadingPositionCompleted()
                        LocalStore.clearReaderPosition(context, article.url)
                        savedReadingProgress = 0f
                        reachedBottom = true
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
            )

            if (!extracting && extractedHtml == null && !fallbackToPublisher) {
                Column(
                    Modifier.align(Alignment.Center).padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text("A readable public article is unavailable", style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
                    Text("CurrentLens could not verify a publisher page for this link. The RSS headline and excerpt remain available in the feed; no paywall or access restriction is bypassed.",
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
        if (fallbackToPublisher && !extracting) {
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
        if (reachedBottom) {
            Card(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Finished reading?", fontWeight = FontWeight.Bold)
                        Text("Five practice questions based on the RSS headline/excerpt and indicative syllabus mapping.", fontSize = 12.sp)
                    }
                    Button(onClick = onQuiz) { Text("Test") }
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
        "</style></head><body><div class=\"cl-source\">" + source + " · CurrentLens Reader</div><h1>" + title + "</h1><main>" + content + "</main></body></html>"
}

private class ReaderWebView(context: android.content.Context) : WebView(context) {
    var loadedKey: String? = null
    var onReachedBottom: () -> Unit = {}
    var onDefineWord: (String, RectF?) -> Unit = { _, _ -> }
    var onReaderPageFinished: () -> Unit = {}
    var onReaderMainFrameError: () -> Unit = {}
    var allowReaderNavigation: (String) -> Boolean = { false }
    var onOpenPublisherLink: (String) -> Unit = {}
    var onReadingProgressChanged: (Float) -> Unit = {}
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

    fun beginArticle(url: String): Boolean {
        val changed = progressGate.beginArticle(url)
        if (changed) {
            flushReadingPosition()
            readingPositionSave?.let { removeCallbacks(it) }
            readingPositionSave = null
            cancelShortPageEndCheck(resetStability = true)
            cancelPendingAutoDefine()
            cancelReadingPositionRestore()
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
        if (scrollY != oldScrollY) scheduleReadingPositionSave()
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
        transitionSpec = { spring(dampingRatio = 0.76f, stiffness = 480f) },
        label = "Definition morph progress"
    ) { visible -> if (visible) 1f else 0f }
    val opacity by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 115) },
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
                    .animateContentSize(spring(dampingRatio = 0.82f, stiffness = 480f)),
                shape = { PrismalRoundedRectangle(25.dp) },
                blurRadius = 12.dp,
                refractionHeightPx = 22f,
                refractionAmountPx = 24f,
                depthEffect = true,
                chromaticAberration = 0.015f,
                tint = if (isSystemInDarkTheme()) Color(0xFF263247) else Color(0xFFEAF0FA),
                tintAlpha = (glassAlpha * 0.62f).coerceIn(0.08f, 0.34f)
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
private fun GlassSettingsScreen(backdrop: PrismalBackdrop, glassAlpha: Float, onGlassAlphaChange: (Float) -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(horizontal = 18.dp)) {
        Row(Modifier.fillMaxWidth().padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Outlined.ArrowBack, contentDescription = "Back") }
            Text("Appearance", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        PrismalGlassSurface(backdrop = backdrop, modifier = Modifier.fillMaxWidth(),
            shape = { PrismalRoundedRectangle(26.dp) }, blurRadius = 11.dp,
            tint = if (isSystemInDarkTheme()) Color(0xFF30394B) else Color(0xFFEAF0FA), tintAlpha = glassAlpha) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(13.dp)) {
                Text("Liquid Glass", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Adjust the transparency of CurrentLens glass surfaces.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Clear", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                    Spacer(Modifier.weight(1f))
                    Text((glassAlpha * 100).toInt().toString() + "%", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.weight(1f))
                    Text("Frosted", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary)
                }
                PrismalGlassSlider(value = { glassAlpha }, onValueChange = onGlassAlphaChange,
                    valueRange = 0.12f..0.55f, visibilityThreshold = 0.01f, backdrop = backdrop,
                    modifier = Modifier.fillMaxWidth())
            }
        }
        Text("Four-tab navigation, editorial cards and adaptive glass effects. Heavy shader effects fall back on unsupported devices.",
            style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(vertical = 16.dp))
    }
}

@Composable
private fun QuizScreen(
    article: Article,
    onBack: () -> Unit,
    onSubmit: (Map<Int, Int>, List<QuizQuestion>) -> Unit
) {
    val relevance = remember(article.id) { UpscEngine.classify(article) }
    val questions = remember(article.id) { UpscEngine.questions(article, relevance) }
    var selected by remember(article.id) { mutableStateOf<Map<Int, Int>>(emptyMap()) }
    var submitted by remember(article.id) { mutableStateOf(false) }
    val score = questions.indices.count { selected[it] == questions[it].correctIndex }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Outlined.ArrowBack, contentDescription = "Back to article") }
            Text("Your reading check", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        Text("Five questions · Basic to application", color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 20.dp))
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text("Question-source note", fontWeight = FontWeight.Bold)
                Text(
                    "This quiz uses the publisher's RSS headline and excerpt only. Extracted full-text reader content is not yet used to generate questions.",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "Q1: headline comprehension · Q2: indicative syllabus keyword mapping · Q3–Q5: original general UPSC skill practice. No official PYQ is included.",
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
            items(questions.size) { index ->
                val question = questions[index]
                Card(shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        Text(question.level + " / " + question.type.uppercase(),
                            color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        Text(question.prompt, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        question.options.forEachIndexed { optionIndex, option ->
                            val chosen = selected[index] == optionIndex
                            val correct = submitted && optionIndex == question.correctIndex
                            OutlinedButton(
                                onClick = { if (!submitted) selected = selected + (index to optionIndex) },
                                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(13.dp),
                                enabled = !submitted
                            ) {
                                Text((if (chosen) "●  " else "○  ") + option,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = if (correct) Color(0xFF168653) else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                        Text(question.provenance, style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary)
                        if (submitted) {
                            Text(if (selected[index] == question.correctIndex) "Correct" else
                                if (selected.containsKey(index)) "Incorrect" else "Not answered",
                                fontWeight = FontWeight.Bold,
                                color = if (selected[index] == question.correctIndex) Color(0xFF168653)
                                else MaterialTheme.colorScheme.error)
                            Text(question.explanation, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
            if (submitted) item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
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
        Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick = onBack, modifier = Modifier.weight(1f)) { Text("Done") }
            Button(
                onClick = {
                    if (!submitted) {
                        submitted = true
                        onSubmit(selected.toMap(), questions)
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = selected.isNotEmpty() && !submitted
            ) { Text("Submit answers") }
        }
    }
}
