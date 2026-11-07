package com.mangoloads.currentlens

import java.util.Locale

/**
 * Whole-token and whole-phrase matching for RSS metadata.
 * Punctuation and hyphen variants are treated as separators; substrings inside
 * unrelated words (for example, "rbi" inside "arbitrary") are never signals.
 */
internal object EditorialTextMatcher {
    private val separators = Regex("[^\\p{L}\\p{N}]+")

    fun normalize(value: String): String =
        separators.replace(value.lowercase(Locale.ROOT), " ").trim()

    fun contains(normalizedText: String, term: String): Boolean {
        val normalizedTerm = normalize(term)
        if (normalizedTerm.isEmpty()) return false
        val paddedText = " $normalizedText "
        if (paddedText.contains(" $normalizedTerm ")) return true

        // Match ordinary English plurals without reverting to unsafe substring matching.
        val words = normalizedTerm.split(" ")
        val last = words.last()
        val plural = when {
            last.length > 1 && last.endsWith("y") && last[last.length - 2] !in "aeiou" ->
                last.dropLast(1) + "ies"
            last.endsWith("s") || last.endsWith("x") || last.endsWith("ch") || last.endsWith("sh") ->
                last + "es"
            else -> last + "s"
        }
        val pluralTerm = (words.dropLast(1) + plural).joinToString(" ")
        return paddedText.contains(" $pluralTerm ")
    }
}

data class Relevance(
    val gsPaper: String,
    val subject: String,
    val syllabusLink: String,
    val matchedTerms: List<String>,
    val signalLabel: String,
    val topicKey: String
)
data class QuizQuestion(
    val level: String,
    val type: String,
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val provenance: String
)

object UpscEngine {
    private data class Rule(
        val key: String, val paper: String, val subject: String,
        val syllabus: String, val keywords: List<String>
    )
    private val rules = listOf(
        Rule("federalism", "GS II", "Polity & Governance",
            "Federal structure, devolution of powers and finances, and challenges therein.",
            listOf("federalism", "centre-state", "centre state", "inter-state", "fiscal federal", "governor", "state government")),
        Rule("constitution", "GS II", "Constitution",
            "Indian Constitution, its features, amendments, significant provisions and basic structure.",
            listOf("constitution", "constitutional", "supreme court", "high court", "parliament passes a bill", "parliament passes bill", "parliament passed a bill", "parliament passed bill", "parliament introduces a bill", "parliament introduces bill", "parliament introduced a bill", "parliament introduced bill", "parliament clears a bill", "parliament clears bill", "parliament cleared a bill", "parliament cleared bill", "parliament approves a bill", "parliament approves bill", "parliament approved a bill", "parliament approved bill", "parliament debates a bill", "parliament debates bill", "parliament debated a bill", "parliament debated bill", "parliamentary committee", "standing committee", "joint parliamentary committee", "question hour", "zero hour", "parliamentary privilege", "parliamentary procedure", "legislative scrutiny", "legislative oversight", "motion of no confidence", "no confidence motion", "motion of thanks", "budget session", "monsoon session", "winter session", "joint session", "money bill", "amendment bill", "bill introduced in parliament", "bill passed by parliament", "bill introduced in lok sabha", "bill passed by lok sabha", "bill passed by rajya sabha", "ordinance", "judiciary", "election commission", "gst council", "representation of the people act", "representation of people act", "constitutional body", "statutory body", "regulatory body")),
        Rule("governance", "GS II", "Governance",
            "Government policies, interventions, transparency, accountability and e-governance.",
            listOf("governance", "scheme", "policy", "public service", "accountability", "transparency", "welfare", "social justice", "panchayat", "municipal corporation", "right to information", "rti", "social audit", "direct benefit transfer", "lokpal", "comptroller and auditor general", "central vigilance commission", "civil services reform", "film certification", "cinematograph act", "censor board", "central board of film certification", "broadcasting regulation", "media regulation", "ott regulation", "copyright act", "press freedom", "sports governance", "anti-doping", "national sports policy", "sports ministry", "olympic charter", "sports federation reform", "consumer protection", "central consumer protection authority", "ccpa", "misleading advertisement", "advertising regulation", "celebrity endorsement", "endorsement guidelines")),
        Rule("international-relations", "GS II", "International Relations",
            "India and its neighbourhood, bilateral and global groupings, international institutions.",
            listOf("foreign policy", "bilateral", "bilateral relations", "diplomacy", "india-us", "india china", "united nations", "treaty", "international treaty", "geopolitics", "geopolitical")),
        Rule("economy", "GS III", "Indian Economy",
            "Indian economy, mobilisation of resources, growth, development and employment.",
            listOf("economy", "inflation", "gdp", "finance bill", "fiscal", "monetary", "budget", "tax", "taxation", "trade", "employment", "unemployment", "rbi", "reserve bank", "repo rate", "banking", "banking sector", "banking regulation", "current account deficit", "financial inclusion", "non performing assets", "payment system", "gst", "goods and services tax", "union budget", "government budgeting", "fiscal responsibility", "trade agreement", "free trade agreement", "world trade organization", "wto", "foreign exchange reserves", "employment guarantee", "unemployment rate", "labour code", "labor code", "labour force participation", "plfs")),
        Rule("agriculture", "GS III", "Agriculture",
            "Major crops, irrigation, agricultural marketing, e-technology and food security.",
            listOf("agriculture", "agricultural", "farmer", "crop", "irrigation", "food security", "public distribution system", "fertiliser", "fertiliser subsidy", "msp", "minimum support price", "crop insurance", "agricultural marketing", "food processing", "land reforms")),
        Rule("geography", "GS I", "Geography",
            "Salient features of physical geography; distribution of key natural resources; factors responsible for the location of industries; important geophysical phenomena and changes in critical geographical features.",
            listOf("physical geography", "world geography", "monsoon", "river basin", "river systems", "glacier", "ice cap", "ice caps", "ocean current", "tectonic plate", "plate boundary", "earthquake", "tsunami", "cyclone", "volcano", "volcanoes", "volcanic activity", "coastal erosion", "natural resource distribution", "resource distribution", "mineral resources", "industrial location", "location of industries", "rainfall distribution", "groundwater aquifer", "geomorphology", "landform", "geophysical phenomena", "weathering", "desertification")),
        Rule("environment", "GS III", "Environment",
            "Conservation, environmental pollution and degradation, environmental impact assessment.",
            listOf("climate", "environment", "biodiversity", "pollution", "forest", "wildlife", "emission", "renewable energy", "sustainability", "greenhouse gas", "environmental impact assessment", "air pollution", "water pollution", "climate change", "forest conservation", "wildlife protection")),
        Rule("disaster-management", "GS III", "Disaster Management",
            "Disaster and disaster management.",
            listOf("disaster management", "earthquake", "cyclone", "flood", "drought", "landslide", "heatwave", "heat wave", "heat action plan", "heat wave action plan", "early warning system")),
        Rule("science-tech", "GS III", "Science & Technology",
            "Science and technology developments and applications in everyday life.",
            listOf("artificial intelligence", "technology", "semiconductor", "photovoltaic", "solar cell", "thin film", "space", "isro", "biotechnology", "quantum", "cyber", "digital", "genome", "satellite launch", "data protection", "deepfake", "digital public infrastructure")),
        Rule("security", "GS III", "Internal Security",
            "Internal security, cyber security, border management and extremism.",
            listOf("security", "terrorism", "cybersecurity", "cyber security", "border", "defence", "military", "insurgency", "left wing extremism")),
        Rule("public-health", "GS II", "Social Sector & Public Health",
            "Issues relating to development and management of social sector services, including health.",
            listOf("public health", "national health mission", "maternal mortality", "malnutrition", "antimicrobial resistance", "epidemic", "pandemic")),
        Rule("society", "GS I", "Indian Society",
            "Salient features of Indian Society, diversity, social empowerment and urbanisation.",
            listOf("society", "women", "gender", "caste", "tribal", "migration", "urbanisation", "education", "health", "inequality", "poverty", "caste census", "tribal rights", "scheduled castes", "scheduled tribes", "gender based violence", "social empowerment")),
        Rule("history-culture", "GS I", "History & Culture",
            "Indian heritage, modern Indian history and the freedom struggle.",
            listOf("history", "heritage", "culture", "archaeology", "archaeological survey", "monument", "world heritage", "freedom struggle")),
        Rule("ethics", "GS IV", "Ethics",
            "Ethics, integrity and aptitude; probity in governance and case studies.",
            listOf("ethics", "integrity", "probity", "corruption", "conflict of interest", "moral"))
    )

    fun classify(article: Article): Relevance {
        // This is a transparent keyword signal over RSS metadata, not a validated relevance score.
        val text = EditorialTextMatcher.normalize(article.title + " " + article.description)
        val matches = rules.map { rule ->
            Pair(rule, rule.keywords.filter { EditorialTextMatcher.contains(text, it) })
        }
            .filter { it.second.isNotEmpty() }
            .sortedByDescending { it.second.size }
        val best = matches.firstOrNull()
            ?: return Relevance(
                gsPaper = "Unmapped",
                subject = "No clear syllabus match",
                syllabusLink = "No mapped keywords were found in the RSS headline or excerpt. Review the publisher article and official syllabus manually.",
                matchedTerms = emptyList(),
                signalLabel = "No keyword match",
                topicKey = "general"
            )

        val matchedTerms = best.second.distinct()
        val countLabel = if (matchedTerms.size == 1) "1 matched keyword" else "${matchedTerms.size} matched keywords"
        return Relevance(
            gsPaper = best.first.paper,
            subject = best.first.subject,
            syllabusLink = best.first.syllabus,
            matchedTerms = matchedTerms,
            signalLabel = countLabel,
            topicKey = best.first.key
        )
    }

    fun questions(article: Article, relevance: Relevance): List<QuizQuestion> {
        val gs = listOf("GS I", "GS II", "GS III", "GS IV", "No clear match")
        val gsIndex = gs.indexOf(relevance.gsPaper).let { if (it < 0) gs.lastIndex else it }
        return listOf(
            QuizQuestion("01 · Basic", "Headline comprehension", "Which development is named in the publisher's RSS headline?",
                listOf(article.title.take(100), "A separate event not mentioned in the headline", "A general definition unrelated to the headline", "A historical episode unrelated to the headline"),
                0, "The selected option repeats the publisher-supplied headline. It identifies the story topic but does not establish every detail in the full article.", "RSS headline only; not full-article comprehension."),
            QuizQuestion("02 · Concept", "Syllabus mapping", "Which GS paper is the strongest initial syllabus match?",
                gs, gsIndex, "The initial mapping is " + relevance.gsPaper + " — " + relevance.subject + ". Syllabus connection: " + relevance.syllabusLink,
                "Rule-based headline and excerpt classification."),
            QuizQuestion("03 · Prelims", "Source literacy", "When a report describes a new law, policy or government decision, what should you verify first?",
                listOf("The primary law, Gazette notification or official government document", "The number of social-media shares", "An unsourced screenshot", "A headline without its publication date"),
                0, "Primary documents establish the text, authority, date and scope. A news report provides context but should not replace the original source.", "Original practice question; not an official UPSC question."),
            QuizQuestion("04 · Mains", "Analytical framing", "Which structure is most useful for analysing this issue in a GS answer?",
                listOf("Context, causes, stakeholders, impacts, challenges and a balanced way forward", "Only repeat the headline", "Give an unreferenced personal opinion", "List unrelated facts without connecting them"),
                0, "A strong answer connects the event to the syllabus, explains perspectives and ends with a practical, evidence-based way forward.", "Original practice question; not an official UPSC question."),
            QuizQuestion("05 · PYQ-style", "Application", "Which approach best turns this current-affairs issue into a UPSC-style analytical question?",
                listOf("Examine the " + relevance.subject.lowercase() + " dimensions, implications and institutional response", "Memorise the headline without understanding it", "Discuss an unrelated subject", "Predict the exact wording of a future UPSC question"),
                0, "This is an original PYQ-style prompt, not a verbatim previous-year question. An actual PYQ will only be labelled as such when its year, paper and official reference have been verified.",
                "PYQ-style practice from syllabus mapping; not an official UPSC question or PYQ.")
        )
    }
}
