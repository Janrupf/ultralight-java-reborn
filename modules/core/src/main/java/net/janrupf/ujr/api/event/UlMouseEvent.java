package net.janrupf.ujr.api.event;


// TODO: fix doc comment references

/**
 * A generic mouse event.
 *
 * @see View::FireMouseEvent
 */
public class UlMouseEvent {
    /**
     * The type of this mouse event.
     */
    public UlMouseEventType type;

    /**
     * The current X position of the mouse, relative to the view.
     */
    public int x;

    /**
     * The current Y position of the mouse, relative to the view.
     */
    public int y;

    /**
     * The mouse button that was pressed or released, or {@link UlMouseButton#NONE} if none.
     */
    public UlMouseButton button;
}
