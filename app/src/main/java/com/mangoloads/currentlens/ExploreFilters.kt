package com.mangoloads.currentlens

/** Deterministic Explore matching over publisher sections and RSS metadata only. */
internal object ExploreFilters {
    fun matches(article: Article, category: String, query: String): Boolean =
        matchesCategory(article, category) && matchesSearchTerms(article, query)

    fun matchesCategory(article: Article, category: String): Boolean {
        if (category.equals("All", true)) return true
        val section = article.section.lowercase()
        val text = (article.title + " " + article.description + " " + article.section).lowercase()
        val normalizedText = EditorialTextMatcher.normalize(text)
        return when (category.lowercase()) {
            "india" -> section.contains("india") || listOf("india", "indian", "new delhi").any { text.contains(it) }
            "world" -> section.contains("world") || section.contains("international") ||
                listOf("international", "global", "geopolitical", "united nations").any { text.contains(it) }
            "politics" -> section.contains("politic") ||
                listOf("government", "parliament", "election", "minister", "political", "legislature", "cabinet", "opposition", "constitution").any { text.contains(it) }
            "geography" -> listOf(
                "physical geography", "monsoon", "river basin", "river systems", "glacier",
                "ocean current", "tectonic plate", "earthquake", "tsunami", "cyclone",
                "volcanic activity", "coastal erosion", "mineral resources",
                "industrial location", "rainfall distribution", "groundwater aquifer"
            ).any { EditorialTextMatcher.contains(normalizedText, it) }
            "economy" -> section.contains("econom") ||
                listOf("econom", "rbi", "inflation", "budget", "fiscal", "monetary", "banking", "taxation", "trade", "employment", "gdp").any { text.contains(it) }
            "science" -> section.contains("science") || section.contains("technology") ||
                listOf("science", "technology", "space", "isro", "artificial intelligence", "semiconductor", "biotechnology", "quantum", "research", "satellite").any { text.contains(it) }
            "environment" -> section.contains("environment") ||
                listOf("environment", "climate", "biodiversity", "pollution", "forest", "wildlife", "emission", "renewable energy", "conservation", "ecosystem").any { text.contains(it) }
            "explained" -> section.contains("explained")
            "upsc" -> section.contains("upsc")
            "opinion" -> listOf("opinion", "editorial", "lead", "op-ed", "column", "comment").any { section.contains(it) }
            else -> section.contains(category.lowercase())
        }
    }

    fun matchesSearchTerms(article: Article, query: String): Boolean {
        val terms = query.split(",").map { it.trim() }.filter { it.isNotBlank() }
        if (terms.isEmpty()) return true
        val text = (article.title + " " + article.description + " " + article.section + " " + article.source).lowercase()
        return terms.all { raw ->
            when (raw.lowercase().replace(" ", "")) {
                "gs1", "gsi", "gs-i" -> UpscEngine.classify(article).gsPaper == "GS I"
                "gs2", "gsii", "gs-ii" -> UpscEngine.classify(article).gsPaper == "GS II"
                "gs3", "gsiii", "gs-iii" -> UpscEngine.classify(article).gsPaper == "GS III"
                "gs4", "gsiv", "gs-iv" -> UpscEngine.classify(article).gsPaper == "GS IV"
                "india", "indian" -> text.contains("india") || text.contains("indian") || text.contains("new delhi")
                "world", "global", "international" -> article.section.contains("world", true) || article.section.contains("international", true) || text.contains("global")
                "economy", "economic" -> text.contains("econom") || text.contains("rbi") || text.contains("inflation")
                "environment", "climate" -> text.contains("environment") || text.contains("climate") || text.contains("biodiversity")
                "geography", "geographic", "geographical" -> UpscEngine.classify(article).subject == "Geography"
                "science", "technology", "tech" -> text.contains("science") || text.contains("technology") || text.contains("space") || text.contains("isro") || text.contains("semiconductor")
                else -> text.contains(raw.lowercase())
            }
        }
    }
}
