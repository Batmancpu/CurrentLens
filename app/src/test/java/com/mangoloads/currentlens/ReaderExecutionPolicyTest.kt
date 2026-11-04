package com.mangoloads.currentlens

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderExecutionPolicyTest {
    @Test
    fun enablesSelectionScriptForSanitisedAppOwnedArticleOnly() {
        assertTrue(ReaderExecutionPolicy.javascriptEnabled(
            hasSanitizedArticle = true,
            hasAllowlistedPublisherFallback = false
        ))
    }

    @Test
    fun enablesPublisherFallbackOnlyWhenSeparatelyAllowlisted() {
        assertTrue(ReaderExecutionPolicy.javascriptEnabled(
            hasSanitizedArticle = false,
            hasAllowlistedPublisherFallback = true
        ))
    }

    @Test
    fun disablesScriptWhenNoTrustedReaderContentExists() {
        assertFalse(ReaderExecutionPolicy.javascriptEnabled(
            hasSanitizedArticle = false,
            hasAllowlistedPublisherFallback = false
        ))
    }
}
