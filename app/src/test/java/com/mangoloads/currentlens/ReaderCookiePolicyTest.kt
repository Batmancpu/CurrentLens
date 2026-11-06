package com.mangoloads.currentlens

import org.junit.Assert.assertEquals
import org.junit.Test

class ReaderCookiePolicyTest {
    @Test
    fun disablesBothCookieClassesBeforeClearingExistingWebViewState() {
        val calls = mutableListOf<String>()

        ReaderCookiePolicy.enforce(
            setAcceptCookie = { calls += "first-party:$it" },
            setAcceptThirdPartyCookies = { calls += "third-party:$it" },
            clearCookies = { calls += "clear" }
        )

        assertEquals(
            listOf("first-party:false", "third-party:false", "clear"),
            calls
        )
    }
}
