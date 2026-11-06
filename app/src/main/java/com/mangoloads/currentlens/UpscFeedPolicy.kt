package com.mangoloads.currentlens

/**
 * Conservative eligibility gate for the default editorial feeds.
 *
 * A story must first map to a GS paper and then contain an explicit syllabus
 * theme. Broad category words and publisher section labels are never sufficient
 * on their own. Entertainment/sports exceptions require the policy subject and
 * a concrete institutional or regulatory action in the headline itself.
 */
internal object UpscFeedPolicy {
    private val noiseTerms = listOf(
        "ranbir kapoor", "salman khan", "celebrity", "celebrity news",
        "box office", "box-office", "opening weekend", "entertainment",
        "film", "movie", "cinema", "actor", "actress", "trailer", "teaser",
        "reality show", "reality tv", "reality television", "contestant", "bigg boss",
        "tv show", "tv series", "television show", "television series",
        "series premiere", "season premiere", "season finale", "show promotion",
        "cast announcement", "match result", "scorecard", "live score",
        "red carpet", "paparazzi", "celebrity gossip", "film review", "movie review",
        "streaming release", "cricket", "ipl", "football match", "match highlights",
        "sports score", "transfer window", "player signs", "tournament result", "league table"
    )

    private val policyExceptions = listOf(
        "film certification", "cinematograph act", "censor board",
        "central board of film certification", "broadcasting regulation",
        "media regulation", "ott regulation", "copyright act", "press freedom",
        "entertainment tax", "gst on movie tickets", "gst on cinema tickets",
        "sports governance", "anti-doping", "national sports policy",
        "sports ministry", "olympic charter", "sports federation reform"
    )

    // The exception must describe an actual rule, decision, review or institutional
    // change in the headline, not a passing reference in the RSS excerpt.
    private val policyActionTerms = listOf(
        "act", "bill", "rule", "rules", "regulation", "regulations",
        "notification", "order", "judgment", "judgement", "court",
        "parliament", "amendment", "draft", "guideline", "guidelines",
        "tax", "gst", "licence", "license", "policy", "reform",
        "framework", "charter", "ban", "review", "overhaul"
    )

    private val specificSyllabusSignals = listOf(
        "supreme court", "high court", "constitution", "constitutional amendment",
        "parliament", "election commission", "federalism", "centre-state",
        "inter-state", "panchayat", "municipal corporation", "gst council",
        "reserve bank", "rbi", "repo rate", "inflation", "gross domestic product",
        "gdp", "fiscal deficit", "monetary policy", "current account deficit",
        "financial inclusion", "banking sector", "banking regulation",
        "non performing assets", "payment system", "gst", "taxation",
        "union budget", "government budgeting", "finance bill", "money bill", "amendment bill", "bill introduced in parliament", "bill passed by parliament", "bill introduced in lok sabha", "bill passed by lok sabha", "bill passed by rajya sabha", "trade agreement",
        "free trade agreement", "world trade organization", "foreign exchange reserves",
        "employment guarantee", "unemployment rate", "labour code", "labor code",
        "labour force participation", "plfs",
        "minimum support price", "msp", "food security", "public distribution system",
        "crop insurance", "irrigation", "agricultural marketing", "food processing",
        "land reforms", "fertiliser subsidy",
        "climate change", "biodiversity", "air pollution", "water pollution",
        "forest conservation", "wildlife protection", "greenhouse gas",
        "renewable energy", "environmental impact assessment",
        "isro", "space mission", "satellite launch", "semiconductor",
        "quantum computing", "biotechnology", "genome", "artificial intelligence",
        "cybersecurity", "data protection", "digital public infrastructure",
        "deepfake", "border security", "terrorism", "insurgency",
        "left wing extremism", "caste census", "tribal rights", "migration",
        "urbanisation", "poverty", "inequality", "social justice",
        "public health", "national health mission", "maternal mortality",
        "malnutrition", "antimicrobial resistance", "epidemic", "pandemic",
        "gender based violence", "scheduled castes", "scheduled tribes",
        "national education policy", "archaeological survey", "world heritage",
        "freedom struggle", "probity", "corruption", "conflict of interest",
        "civil services reform", "foreign policy", "bilateral relations",
        "united nations", "international treaty", "geopolitical",
        "right to information", "social audit", "e governance",
        "direct benefit transfer", "public service delivery", "lokpal",
        "comptroller and auditor general", "central vigilance commission",
        "disaster management", "earthquake", "cyclone", "flood", "drought",
        "landslide", "heatwave", "heat wave", "heat action plan", "heat wave action plan"
    )

    fun shouldInclude(article: Article): Boolean {
        val title = EditorialTextMatcher.normalize(article.title)
        val text = EditorialTextMatcher.normalize(
            article.title + " " + article.description + " " + article.section
        )

        val hasNoise = noiseTerms.any { EditorialTextMatcher.contains(text, it) }
        val hasPolicyException =
            policyExceptions.any { EditorialTextMatcher.contains(title, it) } &&
                policyActionTerms.any { EditorialTextMatcher.contains(title, it) }

        if (hasNoise && !hasPolicyException) return false

        val relevance = UpscEngine.classify(article)
        if (relevance.gsPaper == "Unmapped" || relevance.matchedTerms.isEmpty()) return false

        return hasPolicyException ||
            specificSyllabusSignals.any { EditorialTextMatcher.contains(text, it) }
    }
}
