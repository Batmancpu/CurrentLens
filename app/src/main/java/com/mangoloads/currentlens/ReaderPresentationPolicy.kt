package com.mangoloads.currentlens

/** App-owned responsive layout and editorial styling for publisher content inside the reader. */
internal object ReaderPresentationPolicy {
    fun responsiveTableCss(dark: Boolean): String {
        val line = if (dark) "#354050" else "#D9E0E9"
        val header = if (dark) "#202B3A" else "#EAF0F6"
        val alternate = if (dark) "#151C27" else "#F7F9FC"
        val muted = if (dark) "#AAB4C5" else "#667181"
        return """
            .cl-table-scroll {
                width: 100%; max-width: 100%; overflow-x: auto; overflow-y: hidden;
                overscroll-behavior-x: contain; -webkit-overflow-scrolling: touch;
                margin: 1.35em 0; border-radius: 13px;
            }
            .cl-table-scroll table {
                width: max-content; min-width: 100%; max-width: none;
                table-layout: auto; margin: 0;
            }
            .cl-table-scroll th, .cl-table-scroll td {
                min-width: 96px; max-width: min(68vw, 320px);
            }
            table {
                display: table; width: 100%; max-width: 100%; table-layout: fixed;
                border-collapse: separate; border-spacing: 0; overflow: hidden;
                border: 1px solid $line; border-radius: 13px; margin: 1.35em 0;
                font: 0.78em/1.48 system-ui, sans-serif; color: inherit;
            }
            thead th { background: $header; font-weight: 700; }
            th, td {
                min-width: 0; padding: 10px 9px; border-right: 1px solid $line;
                border-bottom: 1px solid $line; text-align: left; vertical-align: top;
                white-space: normal; overflow-wrap: anywhere; word-break: normal;
            }
            th:last-child, td:last-child { border-right: 0; }
            tbody tr:last-child td, tbody tr:last-child th { border-bottom: 0; }
            tbody tr:nth-child(even) td { background: $alternate; }
            td p, th p { margin: 0; }
            caption {
                caption-side: bottom; padding: 8px 2px; color: $muted;
                font-size: 0.92em; text-align: left;
            }
            table img { max-width: 100%; height: auto; border-radius: 8px; }
        """.trimIndent()
    }

    fun editorialContentCss(dark: Boolean): String {
        val muted = if (dark) "#AAB4C5" else "#667181"
        val divider = if (dark) "#354050" else "#D9E0E9"
        return """
            main { width: 100%; max-width: 72ch; margin-inline: auto; }
            main p, main li { text-wrap: pretty; overflow-wrap: break-word; }
            main h2 {
                font-size: 1.28em; line-height: 1.22; letter-spacing: -0.025em;
                margin: 1.8em 0 0.62em;
            }
            main h3 {
                font-size: 1.08em; line-height: 1.28; letter-spacing: -0.012em;
                margin: 1.5em 0 0.52em;
            }
            main figure {
                max-width: 100%; margin: 1.55em 0 1.75em; text-align: center;
            }
            main img {
                display: block; width: auto; max-width: 100%; max-height: 68vh;
                height: auto; object-fit: contain; margin: 1.25em auto;
                border-radius: 14px;
            }
            main figure img { margin: 0 auto; }
            main figcaption {
                max-width: 64ch; margin: 0.68em auto 0; color: $muted;
                font: 0.76em/1.55 system-ui, sans-serif; text-align: left;
                text-wrap: pretty;
            }
            main hr { border: 0; border-top: 1px solid $divider; margin: 2.1em 0; }
            @media (min-width: 640px) {
                body {
                    padding-left: clamp(32px, 6vw, 72px);
                    padding-right: clamp(32px, 6vw, 72px);
                }
            }
        """.trimIndent()
    }
}
