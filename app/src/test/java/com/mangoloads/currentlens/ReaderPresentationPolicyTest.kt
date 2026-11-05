package com.mangoloads.currentlens

import org.junit.Assert.assertTrue
import org.junit.Test

class ReaderPresentationPolicyTest {
    @Test
    fun tableLayoutFitsViewportAndWrapsLongCellContent() {
        val css = ReaderPresentationPolicy.responsiveTableCss(true)
        assertTrue(css.contains("width: 100%"))
        assertTrue(css.contains("table-layout: fixed"))
        assertTrue(css.contains("overflow-wrap: anywhere"))
        assertTrue(css.contains("border-radius: 13px"))
        assertTrue(css.contains("vertical-align: top"))
        assertTrue(css.contains(".cl-table-scroll"))
        assertTrue(css.contains("overflow-x: auto"))
        assertTrue(css.contains("width: max-content"))
        assertTrue(css.contains("table-layout: auto"))
        assertTrue(css.contains("max-width: min(68vw, 320px)"))
    }

    @Test
    fun lightAndDarkTablesUseSeparateReadableSurfaces() {
        assertTrue(ReaderPresentationPolicy.responsiveTableCss(true).contains("#202B3A"))
        assertTrue(ReaderPresentationPolicy.responsiveTableCss(false).contains("#EAF0F6"))
    }

    @Test
    fun editorialStylesConstrainLineLengthAndPolishFiguresAndCaptions() {
        val css = ReaderPresentationPolicy.editorialContentCss(true)
        assertTrue(css.contains("max-width: 72ch"))
        assertTrue(css.contains("text-wrap: pretty"))
        assertTrue(css.contains("max-height: 68vh"))
        assertTrue(css.contains("object-fit: contain"))
        assertTrue(css.contains("main figcaption"))
        assertTrue(css.contains("#AAB4C5"))
        assertTrue(css.contains("padding-left: clamp(32px, 6vw, 72px)"))
    }

    @Test
    fun editorialCaptionAndDividerColoursAdaptToLightAndDarkThemes() {
        assertTrue(ReaderPresentationPolicy.editorialContentCss(true).contains("#354050"))
        assertTrue(ReaderPresentationPolicy.editorialContentCss(false).contains("#D9E0E9"))
        assertTrue(ReaderPresentationPolicy.editorialContentCss(false).contains("#667181"))
    }
}
