package net.janrupf.ujr.api.cursor;

import net.janrupf.ujr.api.UltralightView;

/**
 * Cursor types.
 *
 * @see net.janrupf.ujr.api.listener.UltralightViewListener#onChangeCursor(UltralightView, UlCursor)
 */
public enum UlCursor {
    POINTER,
    CROSS,
    HAND,
    I_BEAM,
    WAIT,
    HELP,
    EAST_RESIZE,
    NORTH_RESIZE,
    NORTH_EAST_RESIZE,
    NORTH_WEST_RESIZE,
    SOUTH_RESIZE,
    SOUTH_EAST_RESIZE,
    SOUTH_WEST_RESIZE,
    WEST_RESIZE,
    NORTH_SOUTH_RESIZE,
    EAST_WEST_RESIZE,
    NORTH_EAST_SOUTH_WEST_RESIZE,
    NORTH_WEST_SOUTH_EAST_RESIZE,
    COLUMN_RESIZE,
    ROW_RESIZE,
    MIDDLE_PANNING,
    EAST_PANNING,
    NORTH_PANNING,
    NORTH_EAST_PANNING,
    NORTH_WEST_PANNING,
    SOUTH_PANNING,
    SOUTH_EAST_PANNING,
    SOUTH_WEST_PANNING,
    WEST_PANNING,
    MOVE,
    VERTICAL_TEXT,
    CELL,
    CONTEXT_MENU,
    ALIAS,
    PROGRESS,
    NO_DROP,
    COPY,
    NONE,
    NOT_ALLOWED,
    ZOOM_IN,
    ZOOM_OUT,
    GRAB,
    GRABBING,
    CUSTOM
}
