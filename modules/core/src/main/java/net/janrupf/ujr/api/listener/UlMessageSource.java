package net.janrupf.ujr.api.listener;

/**
 * Message source types.
 *
 * @see UltralightViewListener#onAddConsoleMessage
 */
public enum UlMessageSource {
    XML,
    JS,
    NETWORK,
    CONSOLE_API,
    STORAGE,
    APP_CACHE,
    RENDERING,
    CSS,
    SECURITY,
    CONTENT_BLOCKER,
    OTHER
}
