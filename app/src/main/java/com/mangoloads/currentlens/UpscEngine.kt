package com.mangoloads.currentlens

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
        Rule("federalism", "GS II", "Polity & Governance", "Federal structure, devolution of powers and finances, and challenges therein.", listOf("federalism", "centre-state", "centre state", "inter-state", "fiscal federal", "governor", "state government")),
        Rule("constitution", "GS II", "Constitution", "Indian Constitution, its features, amendments, significant provisions and basic structure.", listOf("constitution", "constitutional", "supreme court", "high court", "parliament", "bill", "ordinance", "judiciary", "election commission")),
        Rule("governance", "GS II", "Governance", "Government policies, interventions, transparency, accountability and e-governance.", listOf("governance", "scheme", "policy", "public service", "accountability", "transparency", "welfare", "social justice")),
        Rule("international-relations", "GS II", "International Relations", "India and its neighbourhood, bilateral and global groupings, international institutions.", listOf("foreign policy", "bilateral", "diplomacy", "india-us", "india china", "united nations", "treaty", "geopolitics")),
        Rule("economy", "GS III", "Indian Economy", "Indian economy, mobilisation of resources, growth, development and employment.", listOf("economy", "inflation", "gdp", "fiscal", "monetary", "budget", "tax", "trade", "employment", "unemployment", "rbi", "banking")),
        Rule("agriculture", "GS III", "Agriculture", "Major crops, irrigation, agricultural marketing, e-technology and food security.", listOf("agriculture", "farmer", "crop", "irrigation", "food security", "fertiliser", "msp", "minimum support price")),
        Rule("environment", "GS III", "Environment", "Conservation, environmental pollution and degradation, environmental impact assessment.", listOf("climate", "environment", "biodiversity", "pollution", "forest", "wildlife", "emission", "renewable energy", "sustainability")),
        Rule("science-tech", "GS III", "Science & Technology", "Science and technology developments and applications in everyday life.", listOf("artificial intelligence", "technology", "semiconductor", "space", "isro", "biotechnology", "quantum", "cyber", "digital")),
        Rule("security", "GS III", "Internal Security", "Internal security, cyber security, border management and extremism.", listOf("security", "terrorism", "cybersecurity", "cyber security", "border", "defence", "military", "insurgency")),
        Rule("society", "GS I", "Indian Society", "Salient features of Indian Society, diversity, social empowerment and urbanisation.", listOf("society", "women", "gender", "caste", "tribal", "migration", "urbanisation", "education", "health", "inequality")),
        Rule("history-culture", "GS I", "History & Culture", "Indian heritage, modern Indian history and the freedom struggle.", listOf("history", "heritage", "culture", "archaeology", "monument", "freedom struggle")),
        Rule("ethics", "GS IV", "Ethics", "Ethics, integrity and aptitude; probity in governance and case studies.", listOf("ethics", "integrity", "probity", "corruption", "conflict of interest", "moral"))
    )

    fun classify(article: Article): Relevance {
        // This is a transparent keyword signal over RSS metadata, not a validated relevance score.
        val text = (article.title + " " + article.description).lowercase()
        val matches = rules.map { rule -> Pair(rule, rule.keywords.filter { text.contains(it) }) }
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
