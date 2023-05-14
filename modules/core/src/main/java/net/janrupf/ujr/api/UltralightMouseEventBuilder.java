package net.janrupf.ujr.api;

import net.janrupf.ujr.api.event.UlMouseButton;
import net.janrupf.ujr.api.event.UlMouseEvent;
import net.janrupf.ujr.api.event.UlMouseEventType;

/**
 * Helper class to create a new {@link UlMouseEvent} instance with a fluent API.
 */
public class UltralightMouseEventBuilder extends UlMouseEvent {
    public UltralightMouseEventBuilder(UlMouseEventType type) {
        this.type = type;
    }

    public UltralightMouseEventBuilder(UlMouseEventType type, UlMouseButton button) {
        this.type = type;
        this.button = button;
    }

    /**
     * Creates a new mouse moved event.
     *
     * @return the builder for the event
     */
    public static UltralightMouseEventBuilder moved() {
        return new UltralightMouseEventBuilder(UlMouseEventType.MOVED);
    }

    /**
     * Creates a new mouse button-down event.
     *
     * @param button the button that was pressed
     * @return the builder for the event
     */
    public static UltralightMouseEventBuilder down(UlMouseButton button) {
        return new UltralightMouseEventBuilder(UlMouseEventType.DOWN, button);
    }

    /**
     * Creates a new mouse button-up event.
     *
     * @param button the button that was released
     * @return the builder for the event
     */
    public static UltralightMouseEventBuilder up(UlMouseButton button) {
        return new UltralightMouseEventBuilder(UlMouseEventType.UP, button);
    }

    /**
     * Sets the X position of the event.
     * <p>
     * See {@link UlMouseEvent#x} for more information.
     *
     * @param x the X position
     * @return this
     */
    public UltralightMouseEventBuilder x(int x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the Y position of the event.
     * <p>
     * See {@link UlMouseEvent#y} for more information.
     *
     * @param y the Y position
     * @return this
     */
    public UltralightMouseEventBuilder y(int y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the button of the event.
     * <p>
     * See {@link UlMouseEvent#button} for more information.
     *
     * @param button the button
     * @return this
     */
    public UltralightMouseEventBuilder button(UlMouseButton button) {
        this.button = button;
        return this;
    }

    /**
     * Finishes building the event.
     * <p>
     * This method is a no-op and is only present for consistency with the other builders.
     *
     * @return this
     */
    public UlMouseEvent build() {
        return this;
    }
}
