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

    // Hard exclusions use unmistakable promotion/drama/result phrases. Single
    // words such as "celebrity", "trailer", "premiere" and "contestant" can also
    // occur in legitimate consumer/media-regulation headlines and are handled by
    // the soft noise gate plus the strict institution-action-policy requirement.
    private val nonOverrideableNoiseTerms = listOf(
        "celebrity gossip", "gossip column", "celebrity news",
        "box office", "box-office", "opening weekend",
        "trailer launch", "trailer released", "official trailer",
        "teaser launch", "teaser released", "official teaser",
        "film release date", "movie release date", "streaming release",
        "series premiere", "season premiere", "season finale",
        "show promotion", "series promotion", "cast announcement",
        "red carpet", "paparazzi", "film review", "movie review",
        "reality show drama", "reality tv drama", "reality television drama",
        "reality show contestant", "contestant feud", "contestant fight",
        "bigg boss episode", "bigg boss contestant", "bigg boss drama",
        "tv show promotion", "tv series promotion", "television show promotion", "television series promotion",
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
        "sports ministry", "olympic charter", "sports federation reform",
        "consumer protection", "advertising regulation", "misleading advertisement",
        "celebrity endorsement", "endorsement guidelines"
    )

    // Build a phrase-aware subject expression so an institutional action must
    // concern the same media/sports/consumer-policy subject, not an unrelated action
    // elsewhere in the headline.
    private fun policyPhrasePattern(value: String): String {
        val words = EditorialTextMatcher.normalize(value).split(" ").filter { it.isNotBlank() }
        if (words.isEmpty()) return ""
        val last = words.last()
        val plural = when {
            last.length > 1 && last.endsWith("y") && last[last.length - 2] !in "aeiou" ->
                last.dropLast(1) + "ies"
            last.endsWith("s") -> last
            last.endsWith("x") || last.endsWith("ch") || last.endsWith("sh") -> last + "es"
            else -> last + "s"
        }
        val finalWord = if (plural == last) Regex.escape(last)
            else "(?:${Regex.escape(last)}|${Regex.escape(plural)})"
        return (words.dropLast(1).map { Regex.escape(it) } + finalWord).joinToString("\\s+")
    }

    private val policySubjectPattern = policyExceptions
        .map(::policyPhrasePattern)
        .distinct()
        .sortedByDescending { it.length }
        .joinToString("|")

    private const val POLICY_INSTITUTION_PATTERN =
        """union government|central government|state government|government|centre|central board of film certification|cbfc|censor board|central consumer protection authority|ccpa|advertising standards council of india|asci|ministry|department|parliament|lok sabha|rajya sabha|supreme court|high court|regulatory authority|regulator|commission|cabinet|sports ministry|standing committee|joint parliamentary committee|committee|bcci|sports federation|national anti doping agency|nada|wada|international olympic committee|ioc"""

    private const val ACTIVE_POLICY_ACTION_PATTERN =
        """issues?|issued|notifies?|notified|introduces?|introduced|passes?|passed|amends?|amended|revises?|revised|adopts?|adopted|enacts?|enacted|reviews?|reviewed|debates?|debated|strikes down|struck down|quashes?|quashed|upholds?|upheld|orders?|ordered|directs?|directed|withdraws?|withdrawn|bans?|banned|prohibits?|prohibited|gazettes?|gazetted|announces?|announced|raises?|raised|hikes?|hiked|increases?|increased|cuts?|cut|reduces?|reduced|imposes?|imposed|levies?|levied|exempts?|exempted|repeals?|repealed|suspends?|suspended|extends?|extended|proposes?|proposed|clears?|cleared|approves?|approved|rejects?|rejected"""

    private const val PASSIVE_POLICY_ACTION_PATTERN =
        """issued|notified|gazetted|amended|revised|adopted|passed|introduced|quashed|struck down|upheld|proposed|approved|imposed|enacted|repealed|suspended"""

    // Active: institution -> action -> the specific policy subject.
    private val institutionalPolicyActionPattern = Regex(
        """\b(?:$POLICY_INSTITUTION_PATTERN)\b(?:\s+\w+){0,6}\s+\b(?:$ACTIVE_POLICY_ACTION_PATTERN)\b(?:\s+\w+){0,6}\s+\b(?:$policySubjectPattern)\b"""
    )
    // Passive: a current-change cue -> policy subject -> action.
    private val currentPassivePolicyActionPattern = Regex(
        """\b(?:new|draft|revised|updated|fresh|proposed|final|recently|20\d{2})\b(?:\s+\w+){0,1}\s+\b(?:$policySubjectPattern)\b(?:\s+\w+){0,4}\s+\b(?:$PASSIVE_POLICY_ACTION_PATTERN)\b"""
    )
    // Passive with a named institution: policy subject -> action -> by institution.
    // Passive actions attributed to an institution need a recency cue too.
    // Without it, a retrospective such as "Cinematograph Act enacted by Parliament
    // in 1952" can masquerade as a current policy development.
    private val passivePolicyActionByInstitutionPattern = Regex(
        """\b(?:$policySubjectPattern)\b(?:\s+\w+){0,4}\s+\b(?:$PASSIVE_POLICY_ACTION_PATTERN)\b(?:\s+\w+){0,3}\s+by\s+(?:the\s+)?(?:$POLICY_INSTITUTION_PATTERN)\b(?:\s+\w+){0,4}\s+\b(?:today|yesterday|recently|this week|this month|this year|20\d{2})\b"""
    )

    // An original enactment/passage dated to the 19th century or earlier 20th century
    // is historical context, not a current regulatory action. A separate explicit
    // current-time cue can still establish a present review or change in the same title.
    private val historicalOriginalPolicyActionPattern = Regex(
        """\b(?:$POLICY_INSTITUTION_PATTERN)\b(?:\s+\w+){0,6}\s+\b(?:enacted|passed|introduced|adopted)\b(?:\s+\w+){0,6}\s+\b(?:$policySubjectPattern)\b(?:\s+\w+){0,3}\s+\b(?:in|of)\s+(?:18|19)\d{2}\b"""
    )
    private val explicitCurrentTemporalCuePattern = Regex(
        """\b(?:today|yesterday|recently|this week|this month|this year|20\d{2})\b"""
    )

    // Institution names identify actors, not syllabus themes. A routine appointment,
    // anniversary or staff event must not qualify merely because it mentions one.
    // A second, non-institutional signal from the selected GS mapping is required.
    private val institutionOnlySignals = setOf(
        "supreme court", "high court", "election commission", "gst council",
        "reserve bank", "rbi", "isro", "archaeological survey", "united nations",
        "lokpal", "comptroller and auditor general", "central vigilance commission"
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
        "civil services reform", "consumer protection", "misleading advertisement",
        "advertising regulation", "celebrity endorsement", "endorsement guidelines",
        "foreign policy", "bilateral relations",
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
        val historicalOriginalActionWithoutCurrentCue =
            historicalOriginalPolicyActionPattern.containsMatchIn(title) &&
                !explicitCurrentTemporalCuePattern.containsMatchIn(title)
        val hasPolicyException = !historicalOriginalActionWithoutCurrentCue && (
            institutionalPolicyActionPattern.containsMatchIn(title) ||
                currentPassivePolicyActionPattern.containsMatchIn(title) ||
                passivePolicyActionByInstitutionPattern.containsMatchIn(title)
            )
        // Film promotion stays noise; only a clearly technical thin-film solar headline is exempt.
        val hasThinFilmScienceException =
            EditorialTextMatcher.contains(title, "thin film") &&
                listOf("photovoltaic", "solar cell").any { EditorialTextMatcher.contains(title, it) }

        if (hasHardNoise || (hasNoise && !hasPolicyException && !hasThinFilmScienceException)) return false

        val relevance = UpscEngine.classify(article)
        if (relevance.gsPaper == "Unmapped" || relevance.matchedTerms.isEmpty()) return false

        // A specific signal from an unrelated topic in the excerpt must not
        // rescue a broad match from the selected GS mapping. The card explains
        // the selected mapping, so eligibility must be supported by that same rule.
        val hasSpecificSignalInSelectedMapping = relevance.matchedTerms.any { matchedTerm ->
            matchedTerm !in institutionOnlySignals &&
                specificSyllabusSignals.any { specificSignal -> specificSignal == matchedTerm }
        }

        return hasPolicyException || hasSpecificSignalInSelectedMapping
    }
}
