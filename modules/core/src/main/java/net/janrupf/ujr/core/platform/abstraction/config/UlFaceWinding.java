package net.janrupf.ujr.core.platform.abstraction.config;

/**
 *  The winding order for front-facing triangles. (Only used when the GPU renderer is used)
 */
public enum UlFaceWinding {
    /**
     * Clockwise Winding (Direct3D, etc.)
     */
    CLOCKWISE,

    /**
     * Counter-Clockwise Winding (OpenGL, etc.)
     */
    COUNTER_CLOCKWISE
}
