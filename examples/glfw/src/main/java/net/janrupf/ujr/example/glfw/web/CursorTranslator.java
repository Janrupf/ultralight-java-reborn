package net.janrupf.ujr.example.glfw.web;

import net.janrupf.ujr.api.cursor.UlCursor;
import org.lwjgl.glfw.GLFW;

/**
 * Helper class to translate Ultralight cursors to GLFW cursors.
 */
public class CursorTranslator {
    /**
     * Translates an Ultralight cursor to a GLFW cursor.
     *
     * @param cursor the Ultralight cursor
     * @return the GLFW standard cursor
     */
    public static int ultralightToGlfwCursor(UlCursor cursor) {
        switch (cursor) {
            case POINTER:
                return GLFW.GLFW_ARROW_CURSOR;
            case CROSS:
                return GLFW.GLFW_CROSSHAIR_CURSOR;
            case HAND:
                return GLFW.GLFW_POINTING_HAND_CURSOR;
            case I_BEAM:
                return GLFW.GLFW_IBEAM_CURSOR;
            case NORTH_SOUTH_RESIZE:
                return GLFW.GLFW_RESIZE_NS_CURSOR;
            case EAST_WEST_RESIZE:
                return GLFW.GLFW_RESIZE_EW_CURSOR;
            default:
                return 0;
        }
    }
}
