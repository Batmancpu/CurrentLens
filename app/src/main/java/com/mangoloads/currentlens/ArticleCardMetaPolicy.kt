package com.mangoloads.currentlens

/** Keeps the feed's syllabus hint concise and suppresses negative classifier diagnostics. */
internal object ArticleCardMetaPolicy {
    fun syllabusHint(
        gsPaper: String,
        subject: String,
        matchedTerms: List<String>
    ): String? {
        if (matchedTerms.isEmpty()) return null
        if (gsPaper.isBlank() || gsPaper.equals("Unmapped", ignoreCase = true)) return null
        if (subject.isBlank() || subject.equals("Unmapped", ignoreCase = true)) return null
        return "Indicative · $gsPaper · $subject"
    }
}
