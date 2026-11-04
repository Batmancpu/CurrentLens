package com.mangoloads.currentlens

import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderPresentationPolicyTest {
    @Test
    fun tableLayoutFitsViewportAndWrapsLongCellContent() {
        val css = ReaderPresentationPolicy.responsiveTableCss(dark = true)
        assertTrue(css.contains("width: 100%"))
        assertTrue(css.contains("table-layout: fixed"))
        assertTrue(css.contains("overflow-wrap: anywhere"))
        assertTrue(css.contains("border-radius: 13px"))
        assertTrue(css.contains("vertical-align: top"))
    }

    @Test
    fun lightAndDarkTablesUseSeparateReadableSurfaces() {
        assertTrue(ReaderPresentationPolicy.responsiveTableCss(true).contains("#202B3A"))
        assertTrue(ReaderPresentationPolicy.responsiveTableCss(false).contains("#EAF0F6"))
    }
}
