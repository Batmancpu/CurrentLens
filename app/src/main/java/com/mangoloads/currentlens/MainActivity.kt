package com.mangoloads.currentlens

import android.graphics.Rect
import android.graphics.RectF
import android.view.ActionMode
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.os.SystemClock
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material.icons.outlined.TextDecrease
import androidx.compose.material.icons.outlined.TextIncrease
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.layout.positionInRoot
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.compose.foundation.isSystemInDarkTheme
import coil.compose.AsyncImage
import com.styropyr0.prismal.PrismalBackdrop
import com.styropyr0.prismal.PrismalGlassSurface
import com.styropyr0.prismal.components.LocalPrismalBottomTabHighlightedIndex
import com.styropyr0.prismal.components.PrismalGlassBottomTab
import com.styropyr0.prismal.components.PrismalGlassBottomTabs
import com.styropyr0.prismal.components.PrismalGlassSlider
import com.styropyr0.prismal.shapes.PrismalRoundedRectangle
import com.styropyr0.prismal.sources.prismalGlassLayer
import com.styropyr0.prismal.sources.rememberPrismalGlassLayer
import com.styropyr0.prismal.specular.PrismalSpecular
import org.json.JSONTokener
import org.json.JSONObject
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

private const val INDIA_ZONE = "Asia/Kolkata"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dark = isSystemInDarkTheme()
            MaterialTheme(
                colorScheme = if (dark) {
                    darkColorScheme(
                        primary = Color(0xFFB9CAFF),
                        background = Color(0xFF0D1118),
                        surface = Color(0xFF171D27),
                        surfaceVariant = Color(0xFF252D3A)
                    )
                } else {
                    lightColorScheme(
                        primary = Color(0xFF2859A8),
                        background = Color(0xFFF5F6F8),
                        surface = Color.White,
                        surfaceVariant = Color(0xFFE9EDF3)
                    )
                }
            ) { CurrentLensApp() }
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
    var searchTabAnchor by remember { mutableStateOf(Offset.Zero) }
    val searchFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(searchExpanded, selectedTab) {
        if (searchExpanded && selectedTab == 2) {
            withFrameNanos { }
            searchFocusRequester.requestFocus()
            keyboardController?.show()
        } else {
            focusManager.clearFocus()
            keyboardController?.hide()
        }
    }

    LaunchedEffect(refreshKey) {
        loading = articles.isEmpty()
        refreshing = articles.isNotEmpty()
        loadError = false
        val fetched = NewsRepository.fetchAll()
        if (fetched.isNotEmpty()) articles = LocalStore.mergeAndSaveArticles(context, fetched)
        else if (articles.isEmpty()) loadError = true
        loading = false
        refreshing = false
    }

    BackHandler(enabled = searchExpanded || selectedArticle != null || showSettings || selectedTab != 0) {
        when {
            searchExpanded -> searchExpanded = false
            quizMode -> quizMode = false
            selectedArticle != null -> selectedArticle = null
            showSettings -> showSettings = false
            selectedTab == 3 && youPage != YouPage.DASHBOARD -> youPage = YouPage.DASHBOARD
            selectedTab != 0 -> selectedTab = 0
        }
    }

    BoxWithConstraints(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        val density = LocalDensity.current
        val imeBottom = with(density) { WindowInsets.ime.getBottom(this).toDp() }
        val searchTransition = updateTransition(targetState = searchExpanded, label = "Search tab morph")
        val sourceWidth = 48.dp
        val searchHeight = 52.dp
        val targetWidth = (maxWidth - 32.dp).coerceAtLeast(180.dp)
        val sourceCenterX = if (searchTabAnchor != Offset.Zero) {
            with(density) { searchTabAnchor.x.toDp() }
        } else maxWidth * 0.625f
        val sourceCenterY = if (searchTabAnchor != Offset.Zero) {
            with(density) { searchTabAnchor.y.toDp() }
        } else maxHeight - 35.dp
        val sourceX = sourceCenterX - sourceWidth / 2
        val sourceY = sourceCenterY - searchHeight / 2
        val targetY = (maxHeight - imeBottom - searchHeight - 8.dp).coerceAtLeast(0.dp)
        val morphX by searchTransition.animateDp(label = "Search bar horizontal morph") {
            if (it) 16.dp else sourceX
        }
        val morphY by searchTransition.animateDp(label = "Search bar vertical morph") {
            if (it) targetY else sourceY
        }
        val morphWidth by searchTransition.animateDp(label = "Search bar width morph") {
            if (it) targetWidth else sourceWidth
        }
        val morphAlpha by searchTransition.animateFloat(label = "Search bar opacity") {
            if (it) 1f else 0f
        }
        val searchMorphActive = searchTransition.currentState || searchTransition.targetState

        Column(
            Modifier.fillMaxSize()
                .then(if (searchExpanded) Modifier.imePadding() else Modifier)
                .prismalGlassLayer(backdrop)
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
                    selectedTab = selectedTab,
                    youPage = youPage,
                    onYouPageChange = { youPage = it },
                    onRefresh = { if (!refreshing) refreshKey++ },
                    refreshing = refreshing,
                    loading = loading,
                    loadError = loadError,
                    todaysArticles = ArticleFilters.today(articles, LocalDate.now(ZoneId.of(INDIA_ZONE))),
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

        if (selectedArticle == null && !showSettings &&
            !(selectedTab == 2 && searchMorphActive)
        ) {
            Box(
                Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                    .navigationBarsPadding().padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                GlassTabBar(
                    backdrop = backdrop,
                    selectedTab = selectedTab,
                    glassAlpha = glassAlpha,
                    onSearchAnchor = { searchTabAnchor = it },
                    onSelect = { tab ->
                        val destination = SearchNavigationPolicy.selectTab(tab)
                        selectedTab = destination.selectedTab
                        searchExpanded = destination.searchExpanded
                        if (tab == 3) youPage = YouPage.DASHBOARD
                    }
                )
            }
        }

        if (selectedArticle == null && !showSettings && selectedTab == 2 && searchMorphActive) {
            SearchMorphField(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            with(density) { morphX.roundToPx() },
                            with(density) { morphY.roundToPx() }
                        )
                    }
                    .width(morphWidth)
                    .height(searchHeight)
                    .graphicsLayer { alpha = morphAlpha },
                backdrop = backdrop,
                glassAlpha = glassAlpha,
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                focusRequester = searchFocusRequester,
                enabled = searchExpanded,
                onSubmit = { searchExpanded = false },
                onClear = { searchQuery = "" }
            )
        }
    }
}

@Composable
private fun HomeScreen(
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
        1 -> "Recent stories across your sources"
        2 -> "Find articles by topic, subject or GS paper"
        else -> when (youPage) {
            YouPage.DASHBOARD -> "Your reading and practice at a glance"
            YouPage.SAVED -> "Your saved articles and previously opened stories"
            YouPage.READING_STATS -> "Time and reading activity recorded on this device"
            YouPage.QUIZ_STATS -> "Submitted practice results and answer accuracy"
        }
    }

    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedTab == 3 && youPage != YouPage.DASHBOARD) {
                IconButton(onClick = { onYouPageChange(YouPage.DASHBOARD) }) {
                    Icon(Icons.Outlined.ArrowBack, contentDescription = "Back to You")
                }
            }
            Column(Modifier.weight(1f)) {
                Text(pageTitle, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
            if (selectedTab != 3) {
                IconButton(onClick = onRefresh, enabled = !refreshing) {
                    Icon(Icons.Outlined.Refresh, contentDescription = "Refresh news")
                }
            }
        }

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
                    ArticleFilters.explore(allCachedArticles), exploreCategory,
                    { exploreCategory = it }, bookmarks, onOpenArticle, onToggleBookmark
                )
                2 -> SearchFeed(
                    allCachedArticles, searchQuery, searchExpanded,
                    bookmarks, onOpenArticle, onToggleBookmark
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
            article.weekendRead && ArticleFilters.publishedDate(article)?.let { it >= weekendStart && it <= today } == true
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
                Text("THE DAILY EDITION", color = MaterialTheme.colorScheme.primary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.4.sp)
                Spacer(Modifier.weight(1f))
                Text(if (refreshing || loading) "Updating" else articles.size.toString() + " stories",
                    color = MaterialTheme.colorScheme.secondary, fontSize = 11.sp)
            }
        }
        when {
            loading && articles.isEmpty() -> item { LoadingPanel() }
            loadError && articles.isEmpty() -> item { EmptyPanel("News is temporarily unavailable", "Check your connection and refresh.") }
            articles.isEmpty() -> item { EmptyPanel("No articles dated today yet", "Explore keeps recent stories from all your feeds.") }
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
    onToggleBookmark: (Article) -> Unit
) {
    val categories = listOf("All", "India", "World", "Politics", "Economy", "Environment", "Science", "Explained", "UPSC", "Opinion")
    val filtered = articles.filter { article -> ExploreFilters.matches(article, selectedCategory, "") }
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 14.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            categories.forEach { category ->
                FilterChip(selected = selectedCategory == category, onClick = { onCategory(category) },
                    label = { Text(category, fontSize = 12.sp) })
            }
        }
        LazyColumn(
            Modifier.weight(1f),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            if (filtered.isEmpty()) item {
                EmptyPanel("No stories in this topic", "Try another topic or refresh your feeds.")
            } else items(filtered, key = { it.id }) { article ->
                NewsCard(article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark)
            }
        }
    }
}

@Composable
private fun SearchFeed(
    articles: List<Article>,
    query: String,
    searchExpanded: Boolean,
    bookmarks: Set<String>,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var selectedSubject by remember { mutableStateOf("All") }
    var selectedGsPaper by remember { mutableStateOf("All") }
    val categories = listOf("All", "India", "World", "Politics", "Economy", "Environment", "Science", "Explained", "UPSC", "Opinion")
    val subjects = remember(articles) {
        listOf("All") + articles.map { UpscEngine.classify(it).subject }.distinct().sorted()
    }
    val gsPapers = listOf("All", "GS I", "GS II", "GS III", "GS IV", "Unmapped")
    val filtered = articles.filter { article ->
        val relevance = UpscEngine.classify(article)
        ExploreFilters.matches(article, selectedCategory, query) &&
            (selectedSubject == "All" || relevance.subject == selectedSubject) &&
            (selectedGsPaper == "All" || relevance.gsPaper == selectedGsPaper)
    }
    Column(Modifier.fillMaxSize()) {
        FilterChipRow("Topic", categories, selectedCategory, { selectedCategory = it })
        FilterChipRow("GS paper", gsPapers, selectedGsPaper, { selectedGsPaper = it })
        FilterChipRow("Subject", subjects, selectedSubject, { selectedSubject = it })
        LazyColumn(
            Modifier.weight(1f),
            contentPadding = PaddingValues(
                start = 16.dp, end = 16.dp, top = 7.dp,
                bottom = if (searchExpanded) 76.dp else 110.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (filtered.isEmpty()) item {
                EmptyPanel("No matching articles", "Change your search or remove one of the filters.")
            } else items(filtered, key = { it.id }) { article ->
                NewsCard(article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark)
            }
        }
    }
}


@Composable
private fun SearchMorphField(
    modifier: Modifier,
    backdrop: PrismalBackdrop,
    glassAlpha: Float,
    query: String,
    onQueryChange: (String) -> Unit,
    focusRequester: FocusRequester,
    enabled: Boolean,
    onSubmit: () -> Unit,
    onClear: () -> Unit
) {
    val dark = isSystemInDarkTheme()
    val textColor = MaterialTheme.colorScheme.onSurface
    val glowAlpha by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (query.isBlank()) 0.20f else 0.82f,
        animationSpec = tween(420),
        label = "Search edge glow"
    )
    val glow = Brush.sweepGradient(
        listOf(
            Color(0xFF7CE5FF),
            Color(0xFF8D91FF),
            Color(0xFFE28BFF),
            Color(0xFF83C8FF),
            Color(0xFF7CE5FF)
        )
    )

    Box(
        modifier.drawWithContent {
            drawContent()
            drawRoundRect(
                brush = glow,
                alpha = glowAlpha,
                cornerRadius = CornerRadius(size.height / 2f),
                style = Stroke(width = 1.15.dp.toPx())
            )
        }
    ) {
        PrismalGlassSurface(
            backdrop = backdrop,
            modifier = Modifier.fillMaxSize(),
            shape = { PrismalRoundedRectangle(30.dp) },
            blurRadius = 15.dp,
            refractionHeightPx = 18f,
            refractionAmountPx = 14f,
            depthEffect = true,
            chromaticAberration = 0.16f,
            tint = if (dark) Color(0xFF202B3A) else Color(0xFFF5F8FC),
            tintAlpha = (glassAlpha * 0.34f).coerceIn(0.07f, 0.20f)
        ) {
            Row(
                Modifier.fillMaxSize().padding(start = 15.dp, end = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(9.dp)
            ) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(21.dp)
                )
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    modifier = Modifier.weight(1f).focusRequester(focusRequester),
                    enabled = enabled,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                    cursorBrush = Brush.linearGradient(
                        listOf(Color(0xFF7CE5FF), Color(0xFFAE8BFF), Color(0xFFE28BFF))
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { onSubmit() }),
                    decorationBox = { inner ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (query.isBlank()) {
                                Text(
                                    "Search headlines, topics…",
                                    color = MaterialTheme.colorScheme.secondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            inner()
                        }
                    }
                )
                if (query.isNotBlank()) {
                    IconButton(
                        onClick = onClear,
                        enabled = enabled,
                        modifier = Modifier.size(38.dp)
                    ) {
                        Icon(Icons.Outlined.Close, contentDescription = "Clear search")
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
    val previouslyOpened = articles.filter { readHistory.contains(it.url) && !bookmarks.contains(it.url) }
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
    val wrongAnswers = attempts.sumOf { it.wrongCount }
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 112.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("YOUR ACTIVITY", color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp)
            Text("A private snapshot of your reading and UPSC practice.",
                style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                MetricTile("Articles read", totalUniqueRead.toString(), Modifier.weight(1f))
                MetricTile("Read today", articlesReadToday.toString(), Modifier.weight(1f))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                MetricTile("Saved", savedCount.toString(), Modifier.weight(1f))
                MetricTile("Quiz attempts", attempts.size.toString(), Modifier.weight(1f))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                MetricTile("Answers attempted", answersAttempted.toString(), Modifier.weight(1f))
                MetricTile("Correct", correctAnswers.toString(), Modifier.weight(1f))
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                MetricTile("Incorrect", wrongAnswers.toString(), Modifier.weight(1f))
                MetricTile("Accuracy", if (answersAttempted == 0) "—" else
                    ((correctAnswers * 100f / answersAttempted).toInt().toString() + "%"),
                    Modifier.weight(1f))
            }
        }
        item {
            Text("YOUR LIBRARY & STATS", color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp,
                modifier = Modifier.padding(top = 8.dp))
        }
        item { DestinationRow("Saved", "Your bookmarked articles", savedCount.toString(), onSaved) }
        item { DestinationRow("Reading Stats", "Daily reading time, articles and filters", "›", onReadingStats) }
        item { DestinationRow("Quiz & Practice Stats", "Submitted answers, correct and incorrect", "›", onQuizStats) }
        item { DestinationRow("Settings", "Liquid Glass appearance", "›", onSettings) }
        item {
            Text("Legacy read markers remain available as history and contribute only to the unique-article count; their original dates and durations are unknown. Timed reading begins when an article loads and stays in the foreground for at least five seconds.",
                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 5.dp))
        }
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
    val relevance = remember(article.id) { UpscEngine.classify(article) }
    Card(modifier = Modifier.fillMaxWidth().clickable { onOpenArticle(article) },
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (!article.imageUrl.isNullOrBlank()) {
                AsyncImage(model = article.imageUrl, contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(186.dp).clip(RoundedCornerShape(17.dp)))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(article.source.uppercase(), color = MaterialTheme.colorScheme.primary, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.7.sp)
                Text("  ·  " + article.section, color = MaterialTheme.colorScheme.secondary, fontSize = 10.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { onToggleBookmark(article) }, modifier = Modifier.size(34.dp)) {
                    Icon(if (saved) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = if (saved) "Remove bookmark" else "Save article",
                        tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(19.dp))
                }
            }
            SelectionContainer {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(article.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp)
                    if (article.description.isNotBlank()) {
                        Text(article.description, style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 4, overflow = TextOverflow.Ellipsis, lineHeight = 23.sp)
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("INDICATIVE SYLLABUS MATCH", fontSize = 10.sp, fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary, letterSpacing = 0.5.sp)
                    Spacer(Modifier.weight(1f))
                    Text(relevance.signalLabel, fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
                }
                Text(relevance.gsPaper + " · " + relevance.subject, fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Text(
                    if (relevance.matchedTerms.isEmpty()) "No mapped keywords in RSS headline/excerpt"
                    else "Matched in RSS headline/excerpt: " + relevance.matchedTerms.joinToString(", "),
                    fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary
                )
            }
            Text("Read in CurrentLens  →", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
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
    onSearchAnchor: (Offset) -> Unit,
    onSelect: (Int) -> Unit
) {
    // Keep the callback identity stable. Prismal observes this callback with snapshotFlow;
    // replacing the closure on each recomposition can reset its internal tab index instead
    // of animating the droplet from the previous tab to the new one.
    val selectedTabState = rememberUpdatedState(selectedTab)
    val selectedTabIndex = remember { { selectedTabState.value } }
    PrismalGlassBottomTabs(
        selectedTabIndex = selectedTabIndex,
        onTabSelected = onSelect,
        backdrop = backdrop,
        tabsCount = 4,
        tintDropletContent = false,
        adaptiveLuminance = true,
        specular = { PrismalSpecular.Default.copy(alpha = glassAlpha) },
        modifier = Modifier.fillMaxWidth().height(62.dp)
            .graphicsLayer { alpha = (0.78f + glassAlpha * 0.36f).coerceIn(0.82f, 0.98f) }
    ) {
        GlassTab(0, "Today", Icons.Outlined.Today, onSelect)
        GlassTab(1, "Explore", Icons.Outlined.Explore, onSelect)
        GlassTab(2, "Search", Icons.Outlined.Search, onSelect, onSearchAnchor)
        GlassTab(3, "You", Icons.Outlined.Person, onSelect)
    }
}

@Composable
private fun RowScope.GlassTab(
    index: Int,
    label: String,
    icon: ImageVector,
    onSelect: (Int) -> Unit,
    onPositioned: ((Offset) -> Unit)? = null
) {
    val highlighted = LocalPrismalBottomTabHighlightedIndex.current()
    val selected = highlighted == index
    val tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    val tabModifier = if (onPositioned != null) {
        Modifier.onGloballyPositioned { coordinates ->
            onPositioned(
                coordinates.positionInRoot() + Offset(
                    coordinates.size.width / 2f,
                    coordinates.size.height / 2f
                )
            )
        }
    } else Modifier
    PrismalGlassBottomTab(onClick = { onSelect(index) }, modifier = tabModifier) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(20.dp))
        Text(
            label,
            fontSize = 9.sp,
            lineHeight = 11.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color = tint
        )
    }
}

private fun matchesSearchTerms(article: Article, query: String): Boolean =
    ExploreFilters.matchesSearchTerms(article, query)

@Composable
private fun ReaderScreen(article: Article, backdrop: PrismalBackdrop, glassAlpha: Float, dark: Boolean, onBack: () -> Unit, onQuiz: () -> Unit, onSessionRecorded: () -> Unit) {
    val context = LocalContext.current
    val composeRootView = LocalView.current

    var contentLoaded by remember(article.url) { mutableStateOf(false) }
    val lifecycleOwner = context as? LifecycleOwner
    DisposableEffect(lifecycleOwner, article.url, contentLoaded) {
        if (!contentLoaded) {
            onDispose { }
        } else {
            val clock = ForegroundReadingSessionClock()
            fun resumeSession() {
                clock.resume(System.currentTimeMillis(), SystemClock.elapsedRealtime())
            }
            fun flushSession() {
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
    var activeWordAnchor by remember(article.url) { mutableStateOf<Offset?>(null) }
    var readerViewportScreenOrigin by remember(article.url) { mutableStateOf(Offset.Zero) }
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
            IconButton(onClick = onBack) { Icon(Icons.Outlined.ArrowBack, contentDescription = "Back") }
            Column(Modifier.weight(1f)) {
                Text(article.source, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(
                    when {
                        extractedHtml == null -> "In-app publisher view"
                        loadedFromCache -> "Saved public content · on this device"
                        else -> "Public publisher content · attributed"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(onClick = { fontSize = (fontSize - 1).coerceIn(16, 28) }) { Icon(Icons.Outlined.TextDecrease, contentDescription = "Smaller text") }
            IconButton(onClick = { fontSize = (fontSize + 1).coerceIn(16, 28) }) { Icon(Icons.Outlined.TextIncrease, contentDescription = "Larger text") }
        }
        if (extracting || (!pageLoaded && (extractedHtml != null || fallbackToPublisher))) androidx.compose.material3.LinearProgressIndicator(Modifier.fillMaxWidth())
        Box(Modifier.weight(1f).fillMaxWidth().onGloballyPositioned { coordinates ->
            val rootLocation = IntArray(2)
            composeRootView.getLocationOnScreen(rootLocation)
            val positionInWindow = coordinates.positionInWindow()
            readerViewportScreenOrigin = Offset(
                rootLocation[0] + positionInWindow.x,
                rootLocation[1] + positionInWindow.y
            )
        }) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    ReaderWebView(ctx).apply {
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
                                (view as? ReaderWebView)?.cancelShortPageEndCheck()
                            }
                            override fun onPageFinished(view: WebView?, url: String?) {
                                onReaderPageFinished()
                                // A page that fits entirely in the viewport never scrolls, so
                                // onScrollChanged cannot expose its end-of-reading state.
                                (view as? ReaderWebView)?.scheduleShortPageEndCheck()
                            }
                            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: android.webkit.WebResourceError?) {
                                if (request?.isForMainFrame == true) onReaderMainFrameError()
                            }
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest): Boolean {
                                return request.isForMainFrame && !allowReaderNavigation(request.url.toString())
                            }
                        }
                    }
                },
                update = { webView ->
                    webView.onReachedBottom = { reachedBottom = true }
                    webView.onDefineWord = { word, selectionRect ->
                        val webViewLocation = IntArray(2)
                        webView.getLocationOnScreen(webViewLocation)
                        val cssToScreenScale = webView.resources.displayMetrics.density * webView.scale
                        activeWordAnchor = selectionRect?.let { rect ->
                            Offset(
                                webViewLocation[0] + rect.centerX() * cssToScreenScale - readerViewportScreenOrigin.x,
                                webViewLocation[1] + rect.centerY() * cssToScreenScale - readerViewportScreenOrigin.y
                            )
                        }
                        activeWord = word
                    }
                    webView.onReaderPageFinished = {
                        pageLoaded = true
                        contentLoaded = true
                        publisherLoadFailed = false
                        if (extractedHtml == null) {
                            val size = fontSize
                            webView.evaluateJavascript("(function(){document.body.style.fontSize='" + size + "px';document.body.style.lineHeight='1.7';})();", null)
                        }
                    }
                    webView.onReaderMainFrameError = {
                        pageLoaded = true
                        contentLoaded = false
                        publisherLoadFailed = true
                    }
                    webView.allowReaderNavigation = { target -> PublisherHostPolicy.allows(article.source, target) }
                    // evaluateJavascript is used only for native text selection and its anchor.
                    // Extracted HTML has already passed Jsoup's allow-list sanitizer (scripts,
                    // event handlers, unsafe URL schemes and embedded documents are removed).
                    // Keep JS off for non-readable/unverified pages; publisher fallback remains
                    // gated by PublisherHostPolicy.
                    webView.settings.javaScriptEnabled = ReaderExecutionPolicy.javascriptEnabled(
                        hasSanitizedArticle = extractedHtml != null,
                        hasAllowlistedPublisherFallback = fallbackToPublisher
                    )
                    webView.settings.domStorageEnabled = extractedHtml == null && fallbackToPublisher
                    if (webView.beginArticle(article.url)) {
                        reachedBottom = false
                        activeWord = null
                        activeWordAnchor = null
                    }
                    val cleanHtml = extractedHtml
                    if (cleanHtml != null) {
                        val key = "reader:" + article.url + ":" + fontSize + ":" + dark
                        if (webView.loadedKey != key) {
                            webView.loadedKey = key
                            pageLoaded = false
                            contentLoaded = false
                            webView.loadDataWithBaseURL(article.url, readerDocument(article, cleanHtml, fontSize, dark), "text/html", "UTF-8", null)
                        }
                    } else if (fallbackToPublisher) {
                        val key = "publisher:" + article.url + ":" + fontSize
                        if (webView.loadedKey != key) {
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
                DefinitionGlassOverlay(activeWord!!, activeWordAnchor, backdrop, glassAlpha) {
                    activeWord = null
                    activeWordAnchor = null
                }
            }
        }
        if (fallbackToPublisher && !extracting) {
            Text("A readable public version was unavailable. The verified publisher page remains inside CurrentLens; subscription limits are unchanged.",
                style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 5.dp))
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
    private val progressGate = ReaderProgressGate()
    private var selectionModeGeneration = 0
    private var pendingAutoDefine: Runnable? = null

    fun beginArticle(url: String): Boolean {
        val changed = progressGate.beginArticle(url)
        if (changed) cancelShortPageEndCheck()
        return changed
    }

    private var shortPageEndCheck: Runnable? = null

    fun cancelShortPageEndCheck() {
        shortPageEndCheck?.let { removeCallbacks(it) }
        shortPageEndCheck = null
    }

    fun scheduleShortPageEndCheck() {
        cancelShortPageEndCheck()
        val check = Runnable {
            shortPageEndCheck = null
            val fullHeight = (contentHeight * scale).toInt()
            if (ReaderEndDetector.isShortPageFullyVisible(fullHeight, height) &&
                progressGate.markEndReached()
            ) {
                onReachedBottom()
            }
        }
        shortPageEndCheck = check
        postDelayed(check, 180L)
    }

    override fun onDetachedFromWindow() {
        cancelShortPageEndCheck()
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
    }

    private fun wrapSelectionCallback(original: ActionMode.Callback): ActionMode.Callback2 =
        object : ActionMode.Callback2() {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val created = original.onCreateActionMode(mode, menu)
                if (created) {
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
                            evaluateSelection { selected, selectionRect ->
                                if (generation == selectionModeGeneration) {
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
                if (item.itemId == MENU_DEFINE_WORD) {
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
    private var touchDownY: Float? = null
    private var upwardSwipeDetected = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchDownY = event.y
                upwardSwipeDetected = false
            }
            MotionEvent.ACTION_MOVE -> {
                val startY = touchDownY
                if (startY != null && startY - event.y > 24f) upwardSwipeDetected = true
            }
            MotionEvent.ACTION_UP -> {
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
                touchDownY = null
                upwardSwipeDetected = false
            }
            MotionEvent.ACTION_CANCEL -> {
                touchDownY = null
                upwardSwipeDetected = false
            }
        }
        return super.onTouchEvent(event)
    }

    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY)
        val fullHeight = (contentHeight * scale).toInt()
        if (fullHeight > height + 120 &&
            ReaderEndDetector.isAtEnd(fullHeight, height, scrollY) &&
            progressGate.markEndReached()
        ) {
            onReachedBottom()
        }
    }

    companion object { private const val MENU_DEFINE_WORD = 0x43555252 }
}

@Composable
private fun DefinitionGlassOverlay(word: String, anchor: Offset?, backdrop: PrismalBackdrop, glassAlpha: Float, onClose: () -> Unit) {
    val context = LocalContext.current
    var result by remember(word) { mutableStateOf<Definition?>(null) }
    var loading by remember(word) { mutableStateOf(true) }
    LaunchedEffect(word) {
        result = DictionaryRepository.lookup(context, word)
        loading = false
    }
    val definition = result
    val visibilityState = remember(word) {
        androidx.compose.animation.core.MutableTransitionState(true)
    }
    BackHandler(enabled = visibilityState.targetState) {
        visibilityState.targetState = false
    }
    LaunchedEffect(visibilityState.isIdle, visibilityState.currentState, visibilityState.targetState) {
        if (visibilityState.isIdle && !visibilityState.currentState && !visibilityState.targetState) {
            onClose()
        }
    }
    BoxWithConstraints(Modifier.fillMaxSize()) {
        val density = LocalDensity.current
        val marginPx = with(density) { 12.dp.roundToPx() }
        val maxWidthPx = constraints.maxWidth
        val maxHeightPx = constraints.maxHeight
        val popupWidthPx = (maxWidthPx - marginPx * 2).coerceAtLeast(1)
        // Keep the definition surface compact on tablets and wide split-screen layouts;
        // narrow phone windows still use their full safe width.
        val maxPopupWidthPx = with(density) { 420.dp.roundToPx() }
        val estimatedHeightPx = with(density) { 300.dp.roundToPx() }
        val fallbackAnchor = Offset(maxWidthPx / 2f, maxHeightPx * 0.72f)
        val placement = DefinitionPopupPlacementPolicy.calculate(
            parentWidthPx = maxWidthPx,
            parentHeightPx = maxHeightPx,
            anchor = anchor ?: fallbackAnchor,
            popupWidthPx = popupWidthPx,
            estimatedHeightPx = estimatedHeightPx,
            marginPx = marginPx,
            gapPx = with(density) { 14.dp.roundToPx() },
            maxPopupWidthPx = maxPopupWidthPx
        )
        AnimatedVisibility(
            visibleState = visibilityState,
            modifier = Modifier
                .offset { IntOffset(placement.leftPx, placement.topPx) }
                .width(with(density) { placement.widthPx.toDp() })
                .heightIn(max = with(density) { placement.maxHeightPx.toDp() }),
            enter = fadeIn(tween(150)) +
                scaleIn(
                    initialScale = 0.88f,
                    transformOrigin = TransformOrigin(placement.originX, placement.originY),
                    animationSpec = spring(dampingRatio = 0.74f, stiffness = 480f)
                ) +
                slideInVertically(
                    initialOffsetY = { it / 7 },
                    animationSpec = spring(dampingRatio = 0.74f, stiffness = 480f)
                ),
            exit = fadeOut(tween(110)) +
                scaleOut(
                    targetScale = 0.94f,
                    transformOrigin = TransformOrigin(placement.originX, placement.originY),
                    animationSpec = spring(dampingRatio = 0.82f, stiffness = 520f)
                ) +
                slideOutVertically(targetOffsetY = { it / 9 }, animationSpec = spring())
        ) {
            PrismalGlassSurface(
                backdrop = backdrop,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                shape = { PrismalRoundedRectangle(25.dp) },
                blurRadius = 17.dp,
                refractionHeightPx = 22f,
                refractionAmountPx = 24f,
                depthEffect = true,
                chromaticAberration = 0.24f,
                tint = if (isSystemInDarkTheme()) Color(0xFF263247) else Color(0xFFEAF0FA),
                tintAlpha = (glassAlpha * 0.82f).coerceIn(0.10f, 0.48f)
            ) {
                Column(
                    Modifier.padding(horizontal = 19.dp, vertical = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.weight(1f)) {
                            Text(word, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text("OFFLINE DICTIONARY", style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary, letterSpacing = 1.1.sp)
                        }
                        if (definition != null && definition.dictionaryAvailable &&
                            DictionaryMeaningPolicy.hasCopyableContent(
                                definition.meanings, definition.synonyms, definition.phonetic
                            )
                        ) {
                            TextButton(onClick = {
                                val copiedText = DictionaryMeaningPolicy.copyText(
                                    word = word,
                                    meanings = definition.meanings,
                                    synonyms = definition.synonyms,
                                    pronunciation = definition.phonetic
                                )
                                val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE)
                                    as? android.content.ClipboardManager
                                clipboard?.setPrimaryClip(
                                    android.content.ClipData.newPlainText("Definition of $word", copiedText)
                                )
                                android.widget.Toast.makeText(context, "Definition copied", android.widget.Toast.LENGTH_SHORT).show()
                            }) { Text("Copy") }
                        }
                        TextButton(onClick = { visibilityState.targetState = false }) { Text("Done") }
                    }
                    definition?.phonetic?.takeIf { it.isNotBlank() }?.let { pronunciation ->
                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                            Text(
                                "US PRONUNCIATION · CMUDICT",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.secondary,
                                letterSpacing = 0.8.sp
                            )
                            Text(pronunciation, style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    when {
                        loading -> CircularProgressIndicator()
                        definition == null ->
                            Text("The offline dictionary lookup could not be completed.",
                                style = MaterialTheme.typography.bodyMedium)
                        !definition.dictionaryAvailable ->
                            Text("The offline word database is not included in this build.",
                                style = MaterialTheme.typography.bodyMedium)
                        definition.meanings.isEmpty() && definition.phonetic.isNotBlank() ->
                            Text("Pronunciation found; no definition entry is available offline.",
                                style = MaterialTheme.typography.bodyMedium)
                        definition.meanings.isEmpty() ->
                            Text("No entry found in the offline English dictionary. Try the base form of the word.",
                                style = MaterialTheme.typography.bodyMedium)
                        else -> {
                            definition.meanings.take(3).forEach { meaning ->
                                val presentation = DictionaryMeaningPolicy.present(meaning)
                                Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                    presentation.partOfSpeech?.let { partOfSpeech ->
                                        Text(
                                            partOfSpeech.uppercase(Locale.ROOT),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.primary,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                    Text(
                                        presentation.definition,
                                        style = MaterialTheme.typography.bodyLarge,
                                        lineHeight = 23.sp
                                    )
                                }
                            }
                            if (definition.synonyms.isNotEmpty()) {
                                Text("RELATED WORDS", style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.secondary, letterSpacing = 1.sp)
                                Text(definition.synonyms.take(8).joinToString(" · "),
                                    style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                    Text("Open English WordNet 2025+ · CC BY 4.0",
                        style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    if (definition?.phonetic?.isNotBlank() == true) {
                        Text("US pronunciation · CMUdict · Carnegie Mellon University",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary)
                    }
                }
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
