package net.janrupf.ujr.api.listener;

import net.janrupf.ujr.api.UltralightView;

/**
 * Interface for load related events.
 */
public interface UltralightLoadListener {
    /**
     * Called when the page begins loading a new URL into a frame.
     *
     * @param view        the view that fired the event
     * @param frameId     a unique ID for the frame
     * @param isMainFrame whether this is the main frame
     * @param url         the URL for the load
     */
    default void onBeginLoading(UltralightView view, long frameId, boolean isMainFrame, String url) {
    }

    /**
     * Called when the page finishes loading a URL into a frame.
     *
     * @param view        the view that fired the event
     * @param frameId     a unique ID for the frame
     * @param isMainFrame whether this is the main frame
     * @param url         the URL for the load
     */
    default void onFinishLoading(UltralightView view, long frameId, boolean isMainFrame, String url) {
    }

    /**
     * Called when an error occurs while loading a URL into a frame.
     *
     * @param view        the view that fired the event
     * @param frameId     a unique ID for the frame
     * @param isMainFrame whether this is the main frame
     * @param url         the URL for the load
     * @param description a human-readable description of the error
     * @param errorDomain the name of the module that triggered the error
     * @param errorCode   internal error code generated by the module
     */
    default void onFailLoading(
            UltralightView view,
            long frameId,
            boolean isMainFrame,
            String url,
            String description,
            String errorDomain,
            int errorCode
    ) {
    }

    /**
     * Called when the JavaScript window object is reset for a new page load.
     * <p>
     * This is called before any scripts are executed on the page and is the earliest time to setup
     * any initial JavaScript state or bindings.
     * <p>
     * The document is not guaranteed to be loaded/parsed at this point. If you need to make any
     * JavaScript calls that are dependent on DOM elements or scripts on the page, use OnDOMReady
     * instead.
     * <p>
     * The window object is lazily initialized (this will not be called on pages with no scripts).
     *
     * @param view        the view that fired the event
     * @param frameId     a unique ID for the frame
     * @param isMainFrame whether this is the main frame
     * @param url         the URL for the load
     */
    default void onWindowObjectReady(UltralightView view, long frameId, boolean isMainFrame, String url) {
    }

    /**
     * Called when all JavaScript has been parsed and the document is ready.
     * <p>
     * This is the best time to make any JavaScript calls that are dependent on DOM elements or
     * scripts on the page.
     *
     * @param view        the view that fired the event
     * @param frameId     a unique ID for the frame
     * @param isMainFrame whether this is the main frame
     * @param url         the URL for the load
     */
    default void onDOMReady(UltralightView view, long frameId, boolean isMainFrame, String url) {
    }

    /**
     * Called when the session history (back/forward state) is modified.
     *
     * @param view the view that fired the event
     */
    default void onUpdateHistory(UltralightView view) {
    }
}
