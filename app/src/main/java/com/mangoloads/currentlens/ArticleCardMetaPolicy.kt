package com.mangoloads.currentlens

/** Keeps the feed's syllabus hint concise and exposes both its evidence and syllabus connection. */
internal object ArticleCardMetaPolicy {
    fun syllabusHint(
        gsPaper: String,
        subject: String,
        matchedTerms: List<String>,
        syllabusLink: String
    ): String? {
        if (matchedTerms.isEmpty()) return null
        if (gsPaper.isBlank() || gsPaper.equals("Unmapped", ignoreCase = true)) return null
        if (subject.isBlank() || subject.equals("Unmapped", ignoreCase = true)) return null
        if (syllabusLink.isBlank()) return null

        val signals = matchedTerms.map { it.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
            .take(2)
        if (signals.isEmpty()) return null

        return "Indicative · $gsPaper · $subject\nWhy it may fit: ${signals.joinToString(", ")} are signals for this syllabus theme: $syllabusLink"
    }
}
