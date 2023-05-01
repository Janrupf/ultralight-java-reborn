package net.janrupf.ujr.api;

import net.janrupf.ujr.api.event.UlScrollEvent;
import net.janrupf.ujr.api.event.UlScrollEventType;

/**
 * Helper class to create a new {@link UlScrollEvent} instance with a fluent API.
 */
public class UltralightScrollEventBuilder extends UlScrollEvent {
    public UltralightScrollEventBuilder(UlScrollEventType type) {
        this.type = type;
    }

    /**
     * Sets the X delta of the event.
     * <p>
     * See {@link UlScrollEvent#deltaX} for more information.
     *
     * @param deltaX the X delta
     * @return this
     */
    public UltralightScrollEventBuilder deltaX(int deltaX) {
        this.deltaX = deltaX;
        return this;
    }

    /**
     * Sets the Y delta of the event.
     * <p>
     * See {@link UlScrollEvent#deltaY} for more information.
     *
     * @param deltaY the Y delta
     * @return this
     */
    public UltralightScrollEventBuilder deltaY(int deltaY) {
        this.deltaY = deltaY;
        return this;
    }

    /**
     * Finishes building the event.
     * <p>
     * This method is a no-op and is only present for consistency with the other builders.
     *
     * @return this
     */
    public UlScrollEvent build() {
        return this;
    }
}
