package com.mangoloads.currentlens

import java.util.Locale

/**
 * Conservative eligibility gate for the default editorial feeds.
 * A keyword-based GS mapping alone is insufficient: a story needs a mapped
 * subject and a specific signal, or multiple weaker signals.
 */
internal object UpscFeedPolicy {
    private val noiseTerms = listOf(
        "ranbir kapoor", "salman khan", "celebrity", "celebrity news",
        "box office", "box-office", "opening weekend", "entertainment",
        "film", "movie", "cinema", "actor", "actress", "trailer", "teaser",
        "reality show", "reality tv", "reality television", "contestant", "bigg boss",
        "red carpet", "paparazzi", "celebrity gossip", "film review", "movie review",
        "streaming release", "cricket", "ipl", "football match", "match highlights",
        "sports score", "transfer window", "player signs", "tournament result", "league table"
    )

    // Exceptions are deliberately narrow: the entertainment/sport domain must
    // be the subject of a public law, regulatory framework or institution.
    private val policyExceptions = listOf(
        "film certification", "cinematograph act", "censor board",
        "central board of film certification", "broadcasting regulation",
        "media regulation", "ott regulation", "copyright act", "press freedom",
        "entertainment tax", "gst on movie tickets", "gst on cinema tickets",
        "sports governance", "anti-doping", "national sports policy",
        "sports ministry", "olympic charter", "sports federation reform"
    )

    private val specificSyllabusSignals = listOf(
        "supreme court", "high court", "constitution", "constitutional amendment",
        "parliament", "election commission", "federalism", "centre-state",
        "inter-state", "panchayat", "municipal corporation", "gst council",
        "reserve bank", "rbi", "repo rate", "inflation", "gross domestic product",
        "gdp", "fiscal deficit", "monetary policy", "current account deficit",
        "minimum support price", "msp", "food security", "public distribution system",
        "crop insurance", "irrigation project", "fertiliser subsidy",
        "climate change", "biodiversity", "air pollution", "water pollution",
        "forest conservation", "wildlife protection", "greenhouse gas",
        "renewable energy", "environmental impact assessment",
        "isro", "space mission", "satellite launch", "semiconductor",
        "quantum computing", "biotechnology", "genome", "artificial intelligence",
        "cybersecurity", "data protection", "digital public infrastructure",
        "deepfake", "border security", "terrorism", "insurgency",
        "left wing extremism", "caste census", "tribal rights", "migration",
        "urbanisation", "poverty", "inequality", "social justice",
        "public health", "national education policy", "archaeological survey",
        "world heritage", "freedom struggle", "probity", "corruption",
        "conflict of interest", "civil services reform", "foreign policy",
        "bilateral relations", "united nations", "international treaty",
        "geopolitical", "film certification", "cinematograph act",
        "broadcasting regulation", "media regulation", "anti-doping",
        "national sports policy", "sports governance", "entertainment tax"
    )

    private val weakSignals = setOf(
        "economy", "fiscal", "budget", "tax", "trade", "employment", "unemployment",
        "agriculture", "farmer", "crop", "environment", "climate", "sustainability",
        "technology", "digital", "research", "science", "security", "defence", "military",
        "society", "women", "gender", "education", "health", "inequality", "history",
        "culture", "ethics", "integrity", "moral", "governance", "scheme", "policy",
        "public service", "accountability", "transparency", "welfare", "social justice"
    )

    fun shouldInclude(article: Article): Boolean {
        val text = (article.title + " " + article.description + " " + article.section)
            .lowercase(Locale.ROOT)

        val hasNoise = noiseTerms.any { term -> text.contains(term) }
        val hasPolicyException = policyExceptions.any { term -> text.contains(term) }
        if (hasNoise && !hasPolicyException) return false

        val relevance = UpscEngine.classify(article)
        if (relevance.gsPaper == "Unmapped" || relevance.matchedTerms.isEmpty()) return false

        if (specificSyllabusSignals.any { signal -> text.contains(signal) }) return true

        val substantiveMatches = relevance.matchedTerms
            .map { it.lowercase(Locale.ROOT) }
            .distinct()
            .count { it !in weakSignals }
        if (substantiveMatches > 0) return true

        val weakMatchCount = relevance.matchedTerms
            .map { it.lowercase(Locale.ROOT) }
            .distinct()
            .count { it in weakSignals }
        val editorialSource = article.section.contains("UPSC", ignoreCase = true) ||
            article.section.contains("Explained", ignoreCase = true)

        return weakMatchCount >= 2 || (editorialSource && weakMatchCount >= 1)
    }
}
