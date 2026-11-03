package com.mangoloads.currentlens

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.isSystemInDarkTheme
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val dark = isSystemInDarkTheme()
            MaterialTheme(
                colorScheme = if (dark) darkColorScheme(primary = Color(0xFF9CB8FF), background = Color(0xFF10131A), surface = Color(0xFF191E28))
                else lightColorScheme(primary = Color(0xFF2459A6), background = Color(0xFFF5F7FB), surface = Color.White)
            ) { CurrentLensApp() }
        }
    }
}

@Composable
private fun CurrentLensApp() {
    val context = LocalContext.current
    var articles by remember { mutableStateOf<List<Article>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var refreshing by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("For you") }
    var selectedArticle by remember { mutableStateOf<Article?>(null) }
    var quizMode by remember { mutableStateOf(false) }
    var refreshToken by remember { mutableStateOf(0) }

    LaunchedEffect(refreshToken) {
        loading = articles.isEmpty()
        refreshing = articles.isNotEmpty()
        error = false
        val loaded = NewsRepository.fetchAll()
        if (loaded.isNotEmpty()) articles = loaded else error = true
        loading = false
        refreshing = false
    }
    BackHandler(enabled = selectedArticle != null) {
        if (quizMode) quizMode = false else selectedArticle = null
    }

    if (selectedArticle != null) {
        if (quizMode) QuizScreen(selectedArticle!!, onBack = { quizMode = false })
        else ReaderScreen(selectedArticle!!, onBack = { selectedArticle = null }, onQuiz = { quizMode = true })
        return
    }

    val saved = LocalStore.bookmarks(context)
    val visible = when (selectedTab) {
        "India" -> articles.filter { it.section.equals("India", true) || it.section.equals("Explained", true) || it.section.equals("UPSC", true) }
        "World" -> articles.filter { it.section.equals("World", true) }
        "UPSC" -> articles.filter { UpscEngine.classify(it).score >= 38 }
        "Saved" -> articles.filter { saved.contains(it.url) }
        else -> articles
    }

    Scaffold(bottomBar = {
        NavigationBar {
            listOf("For you", "India", "World", "UPSC", "Saved").forEach { item ->
                NavigationBarItem(
                    selected = selectedTab == item,
                    onClick = { selectedTab = item },
                    icon = { Text(when (item) { "For you" -> "⌂"; "India" -> "IN"; "World" -> "◎"; "UPSC" -> "GS"; else -> "♡" }, fontSize = 13.sp, fontWeight = FontWeight.Bold) },
                    label = { Text(item, fontSize = 10.sp) }
                )
            }
        }
    }) { padding ->
        Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(padding)) {
            Row(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 17.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("CurrentLens", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("News with a syllabus view", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                }
                TextButton(onClick = { refreshToken++ }, enabled = !refreshing) { Text(if (refreshing) "Updating…" else "Refresh") }
            }
            when {
                loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(Modifier.height(12.dp))
                        Text("Fetching the latest headlines")
                    }
                }
                error && articles.isEmpty() -> Column(Modifier.fillMaxSize().padding(28.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("News could not be loaded", style = MaterialTheme.typography.titleLarge)
                    Text("Check your connection and try again.", color = MaterialTheme.colorScheme.secondary)
                    Button(onClick = { refreshToken++ }) { Text("Retry") }
                }
                visible.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(if (selectedTab == "Saved") "No saved articles yet." else "No stories in this section yet.")
                }
                else -> LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(start = 16.dp, end = 16.dp, bottom = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("LATEST", color = MaterialTheme.colorScheme.primary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text("· Headlines and publisher excerpts", color = MaterialTheme.colorScheme.secondary, fontSize = 11.sp)
                        }
                    }
                    items(visible, key = { it.id }) { article ->
                        ArticleCard(
                            article, context,
                            onOpen = { LocalStore.markRead(context, article.url); selectedArticle = article },
                            onBookmark = { LocalStore.toggleBookmark(context, article.url); refreshToken++ }
                        )
                    }
                    item {
                        Text(
                            "Stories are supplied by publishers' feeds. Full articles remain on their original websites.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArticleCard(article: Article, context: Context, onOpen: () -> Unit, onBookmark: () -> Unit) {
    val relevance = remember(article.id) { UpscEngine.classify(article) }
    val saved = LocalStore.isBookmarked(context, article.url)
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onOpen() },
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(17.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
            if (!article.imageUrl.isNullOrBlank()) AsyncImage(model = article.imageUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(170.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(article.source.uppercase(), color = MaterialTheme.colorScheme.primary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text("  ·  " + article.section, color = MaterialTheme.colorScheme.secondary, fontSize = 10.sp)
                Spacer(Modifier.weight(1f))
                TextButton(onClick = onBookmark) { Text(if (saved) "Saved" else "Save", fontSize = 11.sp) }
            }
            SelectionContainer {
                Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
                    Text(article.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp)
                    if (article.description.isNotBlank()) Text(article.description, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 4, overflow = TextOverflow.Ellipsis)
                }
            }
            Divider(color = MaterialTheme.colorScheme.outlineVariant)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("UPSC  " + relevance.score + "/100", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.weight(1f))
                Text(relevance.gsPaper + " · " + relevance.subject, fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            Box(Modifier.fillMaxWidth().height(5.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)) {
                Box(Modifier.fillMaxWidth(relevance.score / 100f).height(5.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
            }
            Text("Read full article  →", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
        }
    }
}

@Composable
private fun ReaderScreen(article: Article, onBack: () -> Unit, onQuiz: () -> Unit) {
    var reachedBottom by remember(article.url) { mutableStateOf(false) }
    var pageError by remember(article.url) { mutableStateOf(false) }
    var pageLoaded by remember(article.url) { mutableStateOf(false) }
    val context = LocalContext.current
    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 11.dp), verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = onBack) { Text("← Back") }
            Column(Modifier.weight(1f)) {
                Text(article.source, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text("Original publisher page", fontSize = 10.sp, color = MaterialTheme.colorScheme.secondary)
            }
            TextButton(onClick = { androidx.browser.customtabs.CustomTabsIntent.Builder().build().launchUrl(context, Uri.parse(article.url)) }) { Text("Browser") }
        }
        Divider()
        AndroidView(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            factory = { ctx ->
                EndAwareWebView(ctx) { reachedBottom = true }.apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadsImagesAutomatically = true
                    settings.javaScriptCanOpenWindowsAutomatically = false
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) { pageLoaded = true }
                        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: android.webkit.WebResourceError?) {
                            if (request?.isForMainFrame == true) pageError = true
                        }
                    }
                    loadUrl(article.url)
                }
            }
        )
        if (!pageLoaded && !pageError) Text("Loading publisher article…", color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(8.dp))
        if (pageError) Text("This publisher may block in-app reading. Use the Browser option above.", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(12.dp))
        if (reachedBottom) {
            Card(Modifier.fillMaxWidth().padding(12.dp), shape = RoundedCornerShape(22.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                Row(Modifier.padding(15.dp), verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Finished reading?", fontWeight = FontWeight.Bold)
                        Text("Test your understanding with five UPSC-oriented questions.", fontSize = 12.sp)
                    }
                    Button(onClick = onQuiz) { Text("Start") }
                }
            }
        }
    }
}

private class EndAwareWebView(context: Context, private val onReachedBottom: () -> Unit) : WebView(context) {
    private var notified = false
    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY)
        val total = (contentHeight * scale).toInt()
        if (!notified && total > height + 120 && scrollY + height >= total - 100) {
            notified = true
            onReachedBottom()
        }
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
            TextButton(onClick = onBack) { Text("← Article") }
            Text("Your reading check", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        Text("Five questions · Basic to application", color = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(horizontal = 20.dp))
        LazyColumn(
            Modifier.weight(1f),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(article.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(relevance.gsPaper + " · " + relevance.subject + " · relevance " + relevance.score + "/100", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
            }
            items(questions.size) { index ->
                val q = questions[index]
                Card(shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                        Text(q.level + " / " + q.type.uppercase(), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        Text(q.prompt, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        q.options.forEachIndexed { optionIndex, option ->
                            val chosen = selected[index] == optionIndex
                            val correct = submitted && optionIndex == q.correctIndex
                            OutlinedButton(
                                onClick = { if (!submitted) selected = selected + (index to optionIndex) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(13.dp),
                                enabled = !submitted
                            ) {
                                Text((if (chosen) "●  " else "○  ") + option, modifier = Modifier.fillMaxWidth(), color = if (correct) Color(0xFF168653) else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                        if (submitted) {
                            Text(if (selected[index] == q.correctIndex) "Correct" else "Review this concept", fontWeight = FontWeight.Bold, color = if (selected[index] == q.correctIndex) Color(0xFF168653) else MaterialTheme.colorScheme.error)
                            Text(q.explanation, style = MaterialTheme.typography.bodyMedium)
                            Text(q.provenance, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
            if (submitted) {
                item {
                    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Column(Modifier.padding(17.dp)) {
                            Text("Your result: " + score + "/5", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Text("Use the explanations to revise the syllabus connection. These are practice questions, not predictions of future UPSC papers.")
                        }
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
