package net.janrupf.ujr.api.event;

/**
 * The various {@link UlKeyEvent} types.
 */
public enum UlKeyEventType {
    /**
     * Key-Down event type. (Does not trigger accelerator commands in WebCore)
     *
     * @apiNote You should probably use {@link #RAW_DOWN} instead when a physical key is pressed.
     * This type is only here for historic compatibility with WebCore's key event types.
     */
    DOWN,

    /**
     * Key-Up event type. Use this when a physical key is released.
     */
    UP,

    /**
     * Raw Key-Down type. Use this when a physical key is pressed.
     *
     * @apiNote You should use this for physical key presses since it allows the renderer to
     * handle accelerator command translation.
     */
    RAW_DOWN,

    /**
     * Character input event type. Use this when the OS generates text from a physical key being
     * pressed (for example, this maps to WM_CHAR on Windows).
     */
    CHAR
}
