package com.mangoloads.currentlens

/**
 * Enforces a stateless public-page reader: no first-party or third-party cookies,
 * and no previously stored WebView session state.
 */
internal object ReaderCookiePolicy {
    fun enforce(
        setAcceptCookie: (Boolean) -> Unit,
        setAcceptThirdPartyCookies: (Boolean) -> Unit,
        clearCookies: () -> Unit
    ) {
        // Disable sending/accepting before asynchronous deletion of any old cookies.
        setAcceptCookie(false)
        setAcceptThirdPartyCookies(false)
        clearCookies()
    }
}
