package com.mangoloads.currentlens

import android.graphics.Rect
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Refresh
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
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
import org.json.JSONTokener
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

@Composable
private fun CurrentLensApp() {
    val context = LocalContext.current
    val backdrop = rememberPrismalGlassLayer()
    val dark = isSystemInDarkTheme()
    var articles by remember { mutableStateOf(LocalStore.cachedArticles(context)) }
    var loading by remember { mutableStateOf(articles.isEmpty()) }
    var refreshing by remember { mutableStateOf(false) }
    var loadError by remember { mutableStateOf(false) }
    var refreshKey by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }
    var quizMode by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var glassAlpha by remember { mutableFloatStateOf(LocalStore.glassAlpha(context)) }

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

    BackHandler(enabled = selectedArticle != null || showSettings) {
        when {
            quizMode -> quizMode = false
            selectedArticle != null -> selectedArticle = null
            showSettings -> showSettings = false
        }
    }

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(Modifier.fillMaxSize().prismalGlassLayer(backdrop)) {
            when {
                selectedArticle != null && quizMode -> QuizScreen(selectedArticle!!, onBack = { quizMode = false })
                selectedArticle != null -> ReaderScreen(
                    article = selectedArticle!!,
                    backdrop = backdrop,
                    glassAlpha = glassAlpha,
                    dark = dark,
                    onBack = { selectedArticle = null },
                    onQuiz = { quizMode = true }
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
                    onRefresh = { if (!refreshing) refreshKey++ },
                    refreshing = refreshing,
                    loading = loading,
                    loadError = loadError,
                    todaysArticles = articles.filter { articleDate(it) == LocalDate.now(ZoneId.of(INDIA_ZONE)) }
                        .sortedByDescending { it.publishedAtMillis ?: 0L },
                    allCachedArticles = articles,
                    bookmarks = LocalStore.bookmarks(context),
                    readHistory = LocalStore.readHistory(context),
                    onSettings = { showSettings = true },
                    onOpenArticle = {
                        LocalStore.markRead(context, it.url)
                        selectedArticle = it
                    },
                    onToggleBookmark = {
                        LocalStore.toggleBookmark(context, it.url)
                        refreshKey++
                    }
                )
            }
        }

        if (selectedArticle == null && !showSettings) {
            Box(
                Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                    .navigationBarsPadding().padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                GlassTabBar(backdrop, selectedTab, glassAlpha) { selectedTab = it }
            }
        }
    }
}

private fun articleDate(article: Article): LocalDate? =
    article.publishedAtMillis?.let {
        runCatching { Instant.ofEpochMilli(it).atZone(ZoneId.of(INDIA_ZONE)).toLocalDate() }.getOrNull()
    }

@Composable
private fun HomeScreen(
    selectedTab: Int,
    onRefresh: () -> Unit,
    refreshing: Boolean,
    loading: Boolean,
    loadError: Boolean,
    todaysArticles: List<Article>,
    allCachedArticles: List<Article>,
    bookmarks: Set<String>,
    readHistory: Set<String>,
    onSettings: () -> Unit,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit
) {
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val dateLabel = remember(today) { today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)) }
    var exploreCategory by remember { mutableStateOf("All") }
    var archiveWindow by remember { mutableIntStateOf(7) }

    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(start = 20.dp, end = 12.dp, top = 12.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    when (selectedTab) { 0 -> "Today"; 1 -> "Explore"; 2 -> "Archive"; else -> "Saved" },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(if (selectedTab == 0) dateLabel else "Your daily reading, beautifully organised",
                    style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            }
            IconButton(onClick = onRefresh, enabled = !refreshing) { Icon(Icons.Outlined.Refresh, contentDescription = "Refresh news") }
            IconButton(onClick = onSettings) { Icon(Icons.Outlined.Settings, contentDescription = "Appearance settings") }
        }

        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = { fadeIn(tween(190)) togetherWith fadeOut(tween(120)) },
            label = "CurrentLens tab transition"
        ) { tab ->
            when (tab) {
                0 -> TodayFeed(todaysArticles, allCachedArticles, loading, loadError, refreshing, bookmarks, onOpenArticle, onToggleBookmark)
                1 -> ExploreFeed(todaysArticles, exploreCategory, { exploreCategory = it }, bookmarks, onOpenArticle, onToggleBookmark)
                2 -> ArchiveFeed(allCachedArticles, archiveWindow, { archiveWindow = it }, bookmarks, onOpenArticle, onToggleBookmark)
                else -> SavedFeed(allCachedArticles, bookmarks, readHistory, onOpenArticle, onToggleBookmark)
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
    val weekendStories = if (weekend) {
        weekendArticles.filter { article ->
            article.weekendRead && articleDate(article)?.let { it >= today.minusDays(2) && it <= today } == true
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
            articles.isEmpty() -> item { EmptyPanel("No articles dated today yet", "Older stories are kept separately in Archive.") }
            else -> items(articles.take(45), key = { it.id }) { article ->
                NewsCard(article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark)
            }
        }
        if (articles.size > 45) item {
            Text("Showing today's 45 most recent stories. Older stories are in Archive.",
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
    val categories = listOf("All", "India", "World", "Politics", "Economy", "Explained", "Science", "UPSC", "Opinion")
    val filtered = articles.filter { article ->
        selectedCategory == "All" ||
            article.section.contains(selectedCategory, ignoreCase = true) ||
            (selectedCategory == "Politics" && article.title.contains("government", true)) ||
            (selectedCategory == "Opinion" && (article.section.contains("editorial", true) || article.section.contains("opinion", true)))
    }
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(horizontal = 14.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp)) {
            categories.forEach { category ->
                FilterChip(selected = selectedCategory == category, onClick = { onCategory(category) }, label = { Text(category, fontSize = 12.sp) })
            }
        }
        LazyColumn(Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)) {
            if (filtered.isEmpty()) item { EmptyPanel("No stories in this topic today", "Try another topic or check again after the next feed update.") }
            else items(filtered.take(45), key = { it.id }) { article ->
                NewsCard(article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark)
            }
        }
    }
}

@Composable
private fun ArchiveFeed(
    articles: List<Article>,
    days: Int,
    onDays: (Int) -> Unit,
    bookmarks: Set<String>,
    onOpenArticle: (Article) -> Unit,
    onToggleBookmark: (Article) -> Unit
) {
    val today = LocalDate.now(ZoneId.of(INDIA_ZONE))
    val filtered = articles.filter {
        val date = articleDate(it)
        date != null && date.isBefore(today) && !date.isBefore(today.minusDays(days.toLong()))
    }.sortedByDescending { it.publishedAtMillis ?: 0L }

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(horizontal = 16.dp, vertical = 5.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = days == 7, onClick = { onDays(7) }, label = { Text("7 days") })
            FilterChip(selected = days == 30, onClick = { onDays(30) }, label = { Text("30 days") })
        }
        LazyColumn(Modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 110.dp),
            verticalArrangement = Arrangement.spacedBy(11.dp)) {
            if (filtered.isEmpty()) item { EmptyPanel("Your archive is empty", "CurrentLens keeps recently fetched headlines on this device for up to 30 days.") }
            else filtered.groupBy { articleDate(it) }.entries.sortedByDescending { it.key }.forEach { (date, group) ->
                item {
                    Text(date?.format(DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)) ?: "Earlier",
                        style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 10.dp, bottom = 2.dp))
                }
                items(group, key = { it.id }) { article ->
                    NewsCard(article, bookmarks.contains(article.url), onOpenArticle, onToggleBookmark)
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
    val saved = articles.filter { bookmarks.contains(it.url) }.sortedByDescending { it.publishedAtMillis ?: 0L }
    val recent = articles.filter { readHistory.contains(it.url) && !bookmarks.contains(it.url) }
        .sortedByDescending { it.publishedAtMillis ?: 0L }.take(15)
    LazyColumn(Modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 110.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("BOOKMARKS", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp) }
        if (saved.isEmpty()) item { EmptyPanel("Nothing saved yet", "Tap the bookmark on any story to keep it here.") }
        else items(saved, key = { it.id }) { article -> NewsCard(article, true, onOpenArticle, onToggleBookmark) }
        if (recent.isNotEmpty()) {
            item { Text("RECENTLY READ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 10.sp, letterSpacing = 1.4.sp, modifier = Modifier.padding(top = 12.dp)) }
            items(recent, key = { "read-" + it.id }) { article -> NewsCard(article, false, onOpenArticle, onToggleBookmark) }
        }
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("UPSC " + relevance.score + "/100", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.weight(1f))
                Text(relevance.gsPaper + " · " + relevance.subject, fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.secondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Box(Modifier.fillMaxWidth().height(4.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant)) {
                Box(Modifier.fillMaxWidth(relevance.score / 100f).height(4.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary))
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
private fun GlassTabBar(backdrop: PrismalBackdrop, selectedTab: Int, glassAlpha: Float, onSelect: (Int) -> Unit) {
    PrismalGlassBottomTabs(
        selectedTabIndex = { selectedTab },
        onTabSelected = onSelect,
        backdrop = backdrop,
        tabsCount = 4,
        tintDropletContent = false,
        blurRadius = 5.dp,
        refractionHeight = 21.dp,
        refractionAmount = 21.dp,
        dropletChromaticAberration = 0.28f,
        pressGlow = 0.22f,
        modifier = Modifier.fillMaxWidth().height(70.dp).graphicsLayer { alpha = (0.76f + glassAlpha * 0.4f).coerceIn(0.80f, 0.98f) }
    ) {
        GlassTab(0, "Today", Icons.Outlined.Today, onSelect)
        GlassTab(1, "Explore", Icons.Outlined.Explore, onSelect)
        GlassTab(2, "Archive", Icons.Outlined.Archive, onSelect)
        GlassTab(3, "Saved", Icons.Outlined.BookmarkBorder, onSelect)
    }
}

@Composable
private fun RowScope.GlassTab(index: Int, label: String, icon: ImageVector, onSelect: (Int) -> Unit) {
    val highlighted = LocalPrismalBottomTabHighlightedIndex.current()
    val selected = highlighted == index
    val tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    PrismalGlassBottomTab(onClick = { onSelect(index) }) {
        Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(21.dp))
        Text(label, fontSize = 10.sp, lineHeight = 12.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal, color = tint)
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
private fun ReaderScreen(article: Article, backdrop: PrismalBackdrop, glassAlpha: Float, dark: Boolean, onBack: () -> Unit, onQuiz: () -> Unit) {
    var extractedHtml by remember(article.url) { mutableStateOf<String?>(null) }
    var extracting by remember(article.url) { mutableStateOf(true) }
    var fallbackToPublisher by remember(article.url) { mutableStateOf(false) }
    var pageLoaded by remember(article.url) { mutableStateOf(false) }
    var reachedBottom by remember(article.url) { mutableStateOf(false) }
    var activeWord by remember(article.url) { mutableStateOf<String?>(null) }
    var fontSize by remember(article.url) { mutableIntStateOf(20) }

    LaunchedEffect(article.url) {
        extracting = true
        extractedHtml = ArticleReaderRepository.extractAccessibleArticle(article.url)
        fallbackToPublisher = extractedHtml == null
        extracting = false
    }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 7.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Outlined.ArrowBack, contentDescription = "Back") }
            Column(Modifier.weight(1f)) {
                Text(article.source, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(if (extractedHtml != null) "Public publisher content · attributed" else "In-app publisher view",
                    style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
            }
            IconButton(onClick = { fontSize = (fontSize - 1).coerceIn(16, 28) }) { Icon(Icons.Outlined.TextDecrease, contentDescription = "Smaller text") }
            IconButton(onClick = { fontSize = (fontSize + 1).coerceIn(16, 28) }) { Icon(Icons.Outlined.TextIncrease, contentDescription = "Larger text") }
        }
        if (extracting || !pageLoaded) androidx.compose.material3.LinearProgressIndicator(Modifier.fillMaxWidth())
        Box(Modifier.weight(1f).fillMaxWidth()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    ReaderWebView(ctx, { reachedBottom = true }, { activeWord = it }).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        settings.loadsImagesAutomatically = true
                        settings.javaScriptCanOpenWindowsAutomatically = false
                        settings.allowFileAccess = false
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                pageLoaded = true
                                if (extractedHtml == null) {
                                    val size = fontSize
                                    evaluateJavascript("(function(){document.body.style.fontSize='" + size + "px';document.body.style.lineHeight='1.7';})();", null)
                                }
                            }
                            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: android.webkit.WebResourceError?) {
                                if (request?.isForMainFrame == true) pageLoaded = true
                            }
                        }
                    }
                },
                update = { webView ->
                    val cleanHtml = extractedHtml
                    if (cleanHtml != null) {
                        val key = "reader:" + article.url + ":" + fontSize + ":" + dark
                        if (webView.loadedKey != key) {
                            webView.loadedKey = key
                            pageLoaded = false
                            webView.loadDataWithBaseURL(article.url, readerDocument(article, cleanHtml, fontSize, dark), "text/html", "UTF-8", null)
                        }
                    } else if (fallbackToPublisher) {
                        val key = "publisher:" + article.url + ":" + fontSize
                        if (webView.loadedKey != key) {
                            webView.loadedKey = key
                            pageLoaded = false
                            webView.loadUrl(article.url)
                        }
                    }
                }
            )
            if (extracting) {
                Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background.copy(alpha = 0.90f)), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        CircularProgressIndicator()
                        Text("Checking for publicly accessible article content", color = MaterialTheme.colorScheme.secondary)
                    }
                }
            }
        }
        if (fallbackToPublisher && !extracting) {
            Text("This publisher requires its own page for this story. It remains inside CurrentLens; subscription limits are unchanged.",
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
                        Text("Review the story with five UPSC-oriented questions.", fontSize = 12.sp)
                    }
                    Button(onClick = onQuiz) { Text("Test") }
                }
            }
        }
        if (activeWord != null) DefinitionGlassOverlay(activeWord!!, backdrop, glassAlpha) { activeWord = null }
    }
}

private fun readerDocument(article: Article, content: String, fontSize: Int, dark: Boolean): String {
    val background = if (dark) "#10141B" else "#FFFFFF"
    val foreground = if (dark) "#E9EDF5" else "#20242B"
    val muted = if (dark) "#AAB4C5" else "#717985"
    val title = article.title.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    val source = article.source.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
    return "<!doctype html><html><head><meta charset=\\"utf-8\\"><meta name=\\"viewport\\" content=\\"width=device-width, initial-scale=1, maximum-scale=1\\"><style>" +
        "html{background:" + background + ";color:" + foreground + ";}body{margin:0;padding:22px 20px 44px;background:" + background + ";color:" + foreground + ";font-family:Georgia,'Iowan Old Style',serif;font-size:" + fontSize + "px;line-height:1.78;-webkit-text-size-adjust:100%;}" +
        ".cl-source{font:700 11px/1.4 system-ui,sans-serif;letter-spacing:1.2px;text-transform:uppercase;color:" + muted + ";margin:4px 0 14px;}" +
        "h1{font:700 1.65em/1.15 system-ui,sans-serif;letter-spacing:-.035em;margin:0 0 18px;color:" + foreground + ";}" +
        "h2,h3{font-family:system-ui,sans-serif;line-height:1.25;margin:1.55em 0 .55em;}p,li{margin:0 0 1.1em;}img,figure,video{max-width:100%;height:auto;border-radius:14px;}figure{margin:1.4em 0;}figcaption{font:12px/1.5 system-ui,sans-serif;color:" + muted + ";}" +
        "a{color:" + (dark ? "#9CB9FF" : "#2859A8") + ";text-decoration:none;}blockquote{margin:1.4em 0;padding-left:1em;border-left:3px solid " + (dark ? "#52658D" : "#A9BCE0") + ";color:" + muted + ";}" +
        "table{display:block;max-width:100%;overflow-x:auto;border-collapse:collapse;font:14px/1.5 system-ui,sans-serif;}td,th{padding:8px;border:1px solid " + muted + ";}iframe{max-width:100%;}pre{white-space:pre-wrap;overflow-wrap:anywhere;}" +
        "</style></head><body><div class=\\"cl-source\\">" + source + " · CurrentLens Reader</div><h1>" + title + "</h1><main>" + content + "</main></body></html>"
}

private class ReaderWebView(
    context: android.content.Context,
    private val onReachedBottom: () -> Unit,
    private val onDefineWord: (String) -> Unit
) : WebView(context) {
    var loadedKey: String? = null
    private var endNotified = false

    @Suppress("DEPRECATION")
    override fun startActionMode(callback: ActionMode.Callback, type: Int): ActionMode? =
        super.startActionMode(wrapSelectionCallback(callback), type)

    @Suppress("DEPRECATION")
    override fun startActionMode(callback: ActionMode.Callback): ActionMode? =
        super.startActionMode(wrapSelectionCallback(callback))

    private fun wrapSelectionCallback(original: ActionMode.Callback): ActionMode.Callback2 =
        object : ActionMode.Callback2() {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                val created = original.onCreateActionMode(mode, menu)
                if (created) menu.add(0, MENU_DEFINE_WORD, menu.size(), "Define").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                return created
            }
            override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean = original.onPrepareActionMode(mode, menu)
            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                if (item.itemId == MENU_DEFINE_WORD) {
                    evaluateJavascript("(function(){return JSON.stringify(window.getSelection().toString());})()") { encoded ->
                        val selected = runCatching { JSONTokener(encoded ?: "\\""\\"").nextValue() as? String }.getOrNull().orEmpty()
                        val word = selected.trim().take(100)
                        if (word.isNotBlank()) onDefineWord(word)
                    }
                    mode.finish()
                    return true
                }
                return original.onActionItemClicked(mode, item)
            }
            override fun onDestroyActionMode(mode: ActionMode) = original.onDestroyActionMode(mode)
            override fun onGetContentRect(mode: ActionMode, view: View, outRect: Rect) {
                if (original is ActionMode.Callback2) original.onGetContentRect(mode, view, outRect) else super.onGetContentRect(mode, view, outRect)
            }
        }

    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY)
        val fullHeight = (contentHeight * scale).toInt()
        if (!endNotified && fullHeight > height + 120 && scrollY + height >= fullHeight - 100) {
            endNotified = true
            onReachedBottom()
        }
    }

    companion object { private const val MENU_DEFINE_WORD = 0x43555252 }
}

@Composable
private fun DefinitionGlassOverlay(word: String, backdrop: PrismalBackdrop, glassAlpha: Float, onClose: () -> Unit) {
    val context = LocalContext.current
    var result by remember(word) { mutableStateOf<Definition?>(null) }
    var loading by remember(word) { mutableStateOf(true) }
    LaunchedEffect(word) {
        result = DictionaryRepository.lookup(context, word)
        loading = false
    }
    Box(Modifier.fillMaxSize()) {
        Box(Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.24f)).clickable { onClose() })
        PrismalGlassSurface(
            backdrop = backdrop,
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().padding(13.dp),
            shape = { PrismalRoundedRectangle(28.dp) },
            blurRadius = 14.dp,
            tint = if (isSystemInDarkTheme()) Color(0xFF293347) else Color(0xFFEAF0FA),
            tintAlpha = glassAlpha
        ) {
            Column(Modifier.padding(22.dp), verticalArrangement = Arrangement.spacedBy(11.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(word, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                        if (result?.phonetic?.isNotBlank() == true) Text(result!!.phonetic, color = MaterialTheme.colorScheme.secondary)
                    }
                    TextButton(onClick = onClose) { Text("Close") }
                }
                when {
                    loading -> CircularProgressIndicator()
                    result == null || result!!.meanings.isEmpty() -> Text("No definition found. Try selecting a single word or its base form.")
                    else -> result!!.meanings.forEachIndexed { index, meaning ->
                        Text((index + 1).toString() + ". " + meaning, style = MaterialTheme.typography.bodyLarge, lineHeight = 23.sp)
                    }
                }
                Text("Free Dictionary API · Cached on this device", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
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
private fun QuizScreen(article: Article, onBack: () -> Unit) {
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
        Text("Five questions · Basic to application", color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(horizontal = 20.dp))
        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            item {
                Text(article.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(relevance.gsPaper + " · " + relevance.subject + " · relevance " + relevance.score + "/100", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
            }
            items(questions.size) { index ->
                val question = questions[index]
                Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        Text(question.level + " / " + question.type.uppercase(), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        Text(question.prompt, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        question.options.forEachIndexed { optionIndex, option ->
                            val chosen = selected[index] == optionIndex
                            val correct = submitted && optionIndex == question.correctIndex
                            OutlinedButton(onClick = { if (!submitted) selected = selected + (index to optionIndex) },
                                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(13.dp), enabled = !submitted) {
                                Text((if (chosen) "●  " else "○  ") + option, modifier = Modifier.fillMaxWidth(),
                                    color = if (correct) Color(0xFF168653) else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                        if (submitted) {
                            Text(if (selected[index] == question.correctIndex) "Correct" else "Review this concept",
                                fontWeight = FontWeight.Bold,
                                color = if (selected[index] == question.correctIndex) Color(0xFF168653) else MaterialTheme.colorScheme.error)
                            Text(question.explanation, style = MaterialTheme.typography.bodyMedium)
                            Text(question.provenance, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
            if (submitted) item {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Column(Modifier.padding(17.dp)) {
                        Text("Your result: " + score + "/5", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text("Use the explanations to revise the syllabus connection. These are practice questions, not predictions of future UPSC papers.")
                    }
                }
            }
        }
        Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(onClick = onBack, modifier = Modifier.weight(1f)) { Text("Done") }
            Button(onClick = { submitted = true }, modifier = Modifier.weight(1f), enabled = selected.size == questions.size && !submitted) { Text("Check answers") }
        }
    }
}
