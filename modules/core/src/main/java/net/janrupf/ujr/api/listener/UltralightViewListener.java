package net.janrupf.ujr.api.listener;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.config.UlViewConfig;
import net.janrupf.ujr.api.cursor.UlCursor;
import net.janrupf.ujr.api.math.IntRect;

/**
 * Interface for {@link net.janrupf.ujr.api.UltralightView} related events.
 */
public interface UltralightViewListener {
    /**
     * Called when the page title changes.
     *
     * @param view  the view that fired the event
     * @param title the new title
     */
    default void onChangeTitle(UltralightView view, String title) {
    }

    /**
     * Called when the page URL changes.
     *
     * @param view the view that fired the event
     * @param url  the new URL
     */
    default void onChangeURL(UltralightView view, String url) {
    }

    /**
     * Called when the tooltip changes.
     *
     * @param view    the view that fired the event
     * @param tooltip the new tooltip
     */
    default void onChangeTooltip(UltralightView view, String tooltip) {
    }

    /**
     * Called when the cursor changes.
     *
     * @param view   the view that fired the event
     * @param cursor the new cursor
     */
    default void onChangeCursor(UltralightView view, UlCursor cursor) {
    }

    /**
     * Called when a message is added to the console (useful for errors / debug).
     *
     * @param view         the view that fired the event
     * @param source       the message source
     * @param level        the message level
     * @param message      the message
     * @param lineNumber   the line number the message originated from
     * @param columnNumber the column number the message originated from
     * @param sourceId     the id of the source that fired the message
     */
    default void onAddConsoleMessage(
            UltralightView view,
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    ) {
    }

    /**
     * Called when the page wants to create a new View.
     * <p>
     * This is usually the result of a user clicking a link with {@code target="_blank"}
     * or by JavaScript calling {@code window.open(url)}.
     * <p>
     * To allow creation of these new Views, you should create a new View in this callback (eg,
     * {@link net.janrupf.ujr.api.UltralightRenderer#createView(int, int, UlViewConfig)}), resize it to your
     * container, and return it. You are responsible for displaying the returned View.
     *
     * @param view      the view that fired the event
     * @param openerUrl the URL of the page that initiated this request
     * @param targetUrl the URL that the new View will navigate to
     * @param isPopup   whether the new View is a popup
     * @param popupRect popups can optionally request certain dimensions and coordinates via
     *                  {@code window.open()}. You can choose to respect these or not by resizing/moving
     *                  the View to this rect
     * @return the new View, or {@code null} to cancel the request
     */
    default UltralightView onCreateChildView(
            UltralightView view,
            String openerUrl,
            String targetUrl,
            boolean isPopup,
            IntRect popupRect
    ) {
        return null;
    }

    /**
     * Called when a page wants to create a new inspector View.
     *
     * @param view         the view that fired the event
     * @param isLocal      whether the inspected page is local
     * @param inspectedUrl the URL of the page that is inspected
     * @return the new inspector View, or {@code null} to cancel the request
     */
    default UltralightView onCreateInspectorView(UltralightView view, boolean isLocal, String inspectedUrl) {
        return null;
    }

    /**
     * Called when the View wants to close.
     *
     * @param view the view that fired the event
     */
    default void onRequestClose(UltralightView view) {
    }
}
