package net.janrupf.ujr.api.surface;

// TODO: fix doc comment references

/**
 * An {@link UltralightSurfaceFactory} can be used to provide your own {@link UltralightSurface} implementation.
 * <p>
 * This can be used to wrap a platform-specific GPU texture, Windows DIB, macOS CGImage, or any
 * other pixel buffer target for display on screen.
 * <p>
 * The default factory creates/destroys a {@link net.janrupf.ujr.api.bitmap.UltralightBitmapSurface}
 * but you can override this by providing your own factory to
 * {@link net.janrupf.ujr.api.UltralightPlatform#setSurfaceFactory(UltralightSurfaceFactory)}.
 */
public interface UltralightSurfaceFactory {
    /**
     * Create a native Surface with a certain width and height (in pixels).
     *
     * @param width  the width of the surface in pixels
     * @param height the height of the surface in pixels
     * @return the created surface
     */
    UltralightSurface createSurface(long width, long height);

    /**
     * Destroy a native Surface previously created by {@link #createSurface(long, long)}.
     *
     * @param surface the surface to destroy
     */
    void destroySurface(UltralightSurface surface);
}
