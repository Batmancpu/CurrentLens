package com.mangoloads.currentlens

/** App-owned, responsive editorial styling for publisher tables inside the reader. */
internal object ReaderPresentationPolicy {
    fun responsiveTableCss(dark: Boolean): String {
        val line = if (dark) "#354050" else "#D9E0E9"
        val header = if (dark) "#202B3A" else "#EAF0F6"
        val alternate = if (dark) "#151C27" else "#F7F9FC"
        val muted = if (dark) "#AAB4C5" else "#667181"
        return """
            /* Wide publisher tables scroll as a single semantic table instead of
               compressing every column into an unreadably narrow mobile layout. */
            .cl-table-scroll {
                width: 100%;
                max-width: 100%;
                overflow-x: auto;
                overflow-y: hidden;
                overscroll-behavior-x: contain;
                -webkit-overflow-scrolling: touch;
                margin: 1.35em 0;
                border-radius: 13px;
            }
            .cl-table-scroll table {
                width: max-content;
                min-width: 100%;
                max-width: none;
                table-layout: auto;
                margin: 0;
            }
            .cl-table-scroll th,
            .cl-table-scroll td {
                min-width: 96px;
                max-width: min(68vw, 320px);
            }
            table {
                display: table;
                width: 100%;
                max-width: 100%;
                table-layout: fixed;
                border-collapse: separate;
                border-spacing: 0;
                overflow: hidden;
                border: 1px solid $line;
                border-radius: 13px;
                margin: 1.35em 0;
                font: 0.78em/1.48 system-ui, sans-serif;
                color: inherit;
            }
            thead th {
                background: $header;
                font-weight: 700;
            }
            th, td {
                min-width: 0;
                padding: 10px 9px;
                border-right: 1px solid $line;
                border-bottom: 1px solid $line;
                text-align: left;
                vertical-align: top;
                white-space: normal;
                overflow-wrap: anywhere;
                word-break: normal;
            }
            th:last-child, td:last-child { border-right: 0; }
            tbody tr:last-child td, tbody tr:last-child th { border-bottom: 0; }
            tbody tr:nth-child(even) td { background: $alternate; }
            td p, th p { margin: 0; }
            caption {
                caption-side: bottom;
                padding: 8px 2px;
                color: $muted;
                font-size: 0.92em;
                text-align: left;
            }
            table img { max-width: 100%; height: auto; border-radius: 8px; }
        """.trimIndent()
    }
}
