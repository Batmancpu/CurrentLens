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
        "box office", "box-office", "opening weekend", "entertainment", "film",
        "movie", "cinema", "actor", "actress", "trailer", "teaser",
        "reality show", "reality tv", "reality television", "contestant", "bigg boss",
        "tv show", "tv series", "television show", "television series",
        "series premiere", "season premiere", "season finale", "show promotion",
        "cast announcement", "match result", "scorecard", "live score",
        "red carpet", "paparazzi", "celebrity gossip", "film review", "movie review",
        "streaming release", "cricket", "ipl", "football match", "match highlights",
        "sports score", "transfer window", "player signs", "tournament result", "league table",
        "final score", "match score", "points table", "league standings", "medal tally",
        "viral challenge", "social media trend", "trending now", "internet sensation",
        "meme", "memes", "influencer feud", "influencer drama"
    )

    // These headline-level signals are never overridden by a policy noun. They
    // represent promotion, celebrity, routine-score or generic-viral coverage.
    private val nonOverrideableNoiseTerms = listOf(
        "celebrity", "celebrity news", "celebrity gossip", "gossip",
        "box office", "box-office", "opening weekend",
        "trailer", "teaser", "premiere", "premieres", "premiered",
        "film release", "movie release", "streaming release",
        "series premiere", "season premiere", "season finale", "show promotion",
        "cast announcement", "red carpet", "paparazzi", "film review", "movie review",
        "reality show", "reality tv", "reality television", "contestant", "bigg boss",
        "tv show", "tv series", "television show", "television series",
        "actor", "actress",
        "match result", "scorecard", "live score", "match highlights", "sports score",
        "transfer window", "player signs", "tournament result", "league table",
        "final score", "match score", "points table", "league standings", "medal tally",
        "viral challenge", "viral video", "viral post", "viral clip", "goes viral",
        "social media trend", "social media sensation", "trending now", "internet sensation",
        "meme", "memes", "influencer feud", "influencer drama"
    )

    private val policyExceptions = listOf(
        "film certification", "cinematograph act", "censor board",
        "central board of film certification", "broadcasting regulation",
        "media regulation", "ott regulation", "copyright act", "press freedom",
        "entertainment tax", "gst on movie tickets", "gst on cinema tickets",
        "sports governance", "anti-doping", "national sports policy",
        "sports ministry", "olympic charter", "sports federation reform"
    )

    // Require an institution before an active action, or an explicit current-change
    // cue / named institution for passive headlines. A historical Act mention is not
    // itself evidence that a current policy action occurred.
    private val institutionalPolicyActionPattern = Regex(
        """\b(?:union government|central government|state government|government|centre|central board of film certification|cbfc|censor board|ministry|department|parliament|lok sabha|rajya sabha|supreme court|high court|regulatory authority|regulator|commission|cabinet|sports ministry|standing committee|joint parliamentary committee|committee|bcci|sports federation|national anti doping agency|nada|wada)\b(?:\s+\w+){0,6}\s+\b(?:issues?|issued|notifies?|notified|introduces?|introduced|passes?|passed|amends?|amended|revises?|revised|adopts?|adopted|enacts?|enacted|reviews?|reviewed|debates?|debated|strikes down|struck down|quashes?|quashed|upholds?|upheld|orders?|ordered|directs?|directed|withdraws?|withdrawn|bans?|banned|prohibits?|prohibited|gazettes?|gazetted|announces?|announced|raises?|raised|hikes?|hiked|increases?|increased|cuts?|cut|reduces?|reduced|imposes?|imposed|levies?|levied|exempts?|exempted|repeals?|repealed|suspends?|suspended|extends?|extended|proposes?|proposed|clears?|cleared|approves?|approved|rejects?|rejected)\b(?:\s+\w+){0,4}\s+\b(?:rules?|regulations?|guidelines?|notifications?|bills?|acts?|amendments?|polic(?:y|ies)|frameworks?|charters?|bans?|orders?|tax|gst|licen[cs]es?|reforms?|laws?)\b"""
    )
    private val currentPassivePolicyActionPattern = Regex(
        """\b(?:new|draft|revised|updated|fresh|proposed|final|recently|2025|2026|2027)\b(?:\s+\w+){0,3}\s+\b(?:rules?|regulations?|guidelines?|notifications?|bills?|amendments?|polic(?:y|ies)|frameworks?|charters?|bans?|orders?|tax|gst|licen[cs]es?|reforms?|laws?)\b(?:\s+\w+){0,2}\s+\b(?:issued|notified|gazetted|amended|revised|adopted|passed|introduced|quashed|struck down|upheld)\b"""
    )
    private val passivePolicyActionByInstitutionPattern = Regex(
        """\b(?:rules?|regulations?|guidelines?|notifications?|bills?|amendments?|polic(?:y|ies)|frameworks?|charters?|bans?|orders?|tax|gst|licen[cs]es?|reforms?|laws?)\b(?:\s+\w+){0,3}\s+\b(?:issued|notified|gazetted|amended|revised|adopted|passed|introduced|quashed|struck down|upheld)\b(?:\s+\w+){0,3}\s+\bby\s+(?:the\s+)?(?:union government|central government|state government|government|centre|central board of film certification|cbfc|censor board|ministry|department|parliament|lok sabha|rajya sabha|supreme court|high court|regulatory authority|regulator|commission|cabinet|sports ministry|standing committee|joint parliamentary committee|committee|bcci|sports federation|national anti doping agency|nada|wada)\b"""
    )

    private val specificSyllabusSignals = listOf(
        "supreme court", "high court", "constitution", "constitutional amendment",
        "election commission", "federalism", "centre-state",
        "inter-state", "panchayat", "municipal corporation", "gst council",
        "reserve bank", "rbi", "repo rate", "inflation", "gross domestic product",
        "gdp", "fiscal deficit", "monetary policy", "current account deficit",
        "financial inclusion", "banking sector", "banking regulation",
        "non performing assets", "payment system", "gst", "taxation",
        "union budget", "government budgeting", "finance bill", "money bill", "amendment bill",
        "bill introduced in parliament", "bill passed by parliament", "bill introduced in lok sabha",
        "bill passed by lok sabha", "bill passed by rajya sabha", "parliament passes a bill", "parliament passes bill", "parliament passed a bill", "parliament passed bill", "parliament introduces a bill", "parliament introduces bill", "parliament introduced a bill", "parliament introduced bill", "parliament clears a bill", "parliament clears bill", "parliament cleared a bill", "parliament cleared bill", "parliament approves a bill", "parliament approves bill", "parliament approved a bill", "parliament approved bill", "parliament debates a bill", "parliament debates bill", "parliament debated a bill", "parliament debated bill", "parliamentary committee", "standing committee", "joint parliamentary committee", "question hour", "zero hour", "parliamentary privilege", "parliamentary procedure", "legislative scrutiny", "legislative oversight", "motion of no confidence", "no confidence motion", "motion of thanks", "budget session", "monsoon session", "winter session", "joint session", "representation of the people act", "representation of people act", "trade agreement",
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
        "landslide", "heatwave", "heat wave", "heat action plan", "heat wave action plan",
        "physical geography", "monsoon", "river basin", "river systems", "glacier",
        "ice cap", "ocean current", "tectonic plate", "plate boundary",
        "tsunami", "volcano", "volcanoes", "volcanic activity", "coastal erosion",
        "natural resource distribution", "resource distribution", "mineral resources",
        "industrial location", "location of industries", "rainfall distribution",
        "groundwater aquifer", "geomorphology", "landform", "geophysical phenomena",
        "weathering", "desertification", "photovoltaic", "solar cell", "thin film"
    )

    fun shouldInclude(article: Article): Boolean {
        val title = EditorialTextMatcher.normalize(article.title)
        val text = EditorialTextMatcher.normalize(
            article.title + " " + article.description + " " + article.section
        )

        val hasNoise = noiseTerms.any { EditorialTextMatcher.contains(text, it) }
        val hasHardNoise = nonOverrideableNoiseTerms.any { EditorialTextMatcher.contains(title, it) }
        val hasPolicySubject = policyExceptions.any { EditorialTextMatcher.contains(title, it) }
        val hasInstitutionalAction =
            institutionalPolicyActionPattern.containsMatchIn(title) ||
                currentPassivePolicyActionPattern.containsMatchIn(title) ||
                passivePolicyActionByInstitutionPattern.containsMatchIn(title)
        val hasPolicyException = hasPolicySubject && hasInstitutionalAction
        // Film promotion stays noise; only a clearly technical thin-film solar headline is exempt.
        val hasThinFilmScienceException =
            EditorialTextMatcher.contains(title, "thin film") &&
                listOf("photovoltaic", "solar cell").any { EditorialTextMatcher.contains(title, it) }

        if (hasHardNoise || (hasNoise && !hasPolicyException && !hasThinFilmScienceException)) return false

        val relevance = UpscEngine.classify(article)
        if (relevance.gsPaper == "Unmapped" || relevance.matchedTerms.isEmpty()) return false

        return hasPolicyException ||
            specificSyllabusSignals.any { EditorialTextMatcher.contains(text, it) }
    }
}
