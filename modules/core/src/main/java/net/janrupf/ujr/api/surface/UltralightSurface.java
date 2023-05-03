package net.janrupf.ujr.api.surface;

import net.janrupf.ujr.api.UltralightRenderer;
import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.api.util.UltralightBuffer;

// TODO: fix doc comment references

/**
 * Offscreen pixel buffer surface. (Premultiplied BGRA 32-bit format)
 * <p>
 * When using the CPU renderer, each View is painted to its own {@link UltralightSurface}.
 * <p>
 * You can provide your own Surface implementation to make the renderer paint directly to a block
 * of memory controlled by you (this is useful for lower-latency uploads to GPU memory or other
 * platform-specific bitmaps).
 * <p>
 * A default Surface implementation, BitmapSurface, is automatically provided by the library when
 * you call {@link UltralightRenderer#getOrCreate()} without defining a custom {@link SurfaceFactory}.
 * <p>
 * To provide your own custom Surface implementation, you should inherit from this class, handle
 * the member functions, and then define a custom {@link SurfaceFactory} that creates/destroys an
 * instance of your class. After that, you should pass an instance of your custom {@link SurfaceFactory}
 * class to `Platform::instance().set_font_loader()` before calling App::Create() or
 * Renderer::Create().
 */
public interface UltralightSurface {
    /**
     * Retrieves the width of the surface.
     *
     * @return the width of the surface in pixels
     */
    long width();

    /**
     * Retrieves the height of the surface.
     *
     * @return the height of the surface in pixels
     */
    long height();

    /**
     * Retrieves the number of bytes per row.
     *
     * @return the number of bytes per row
     */
    long rowBytes();

    /**
     * Retrieves the size of the surface.
     *
     * @return the size of the surface in bytes
     */
    long size();

    /**
     * Lock the pixel buffer for reading/writing.
     * <p>
     * Native pixel format is premultiplied BGRA 32-bit (8 bits per channel).
     * <p>
     * When the buffer is closed, the pixels should be unlocked.
     *
     * @return the locked pixel buffer
     */
    UltralightBuffer lockPixels();

    /**
     * Resize the pixel buffer to a certain width and height (both in pixels).
     * <p>
     * This should never be called while pixels are locked.
     *
     * @param width  the new width of the surface in pixels
     * @param height the new height of the surface in pixels
     */
    void resize(long width, long height);

    /**
     * Set the dirty bounds to a certain value.
     * <p>
     * This is called after the Renderer paints to an area of the pixel buffer. (The new value will
     * be joined with the existing {@link #dirtyBounds()})
     *
     * @param bounds the new dirty bounds
     */
    void setDirtyBounds(IntRect bounds);

    /**
     * Retrieves the dirty bounds.
     * <p>
     * This value can be used to determine which portion of the pixel buffer has been updated since
     * the last call to {@link #clearDirtyBounds()}.
     * <p>
     * The general algorithm to determine if a {@link UltralightSurface} needs display is:
     * <pre>
     *     <code>
     * if (!surface.dirtyBounds().isEmpty()) {
     *      // Surface pixels are dirty and needs display.
     *      // Cast UltralightSurface to your surface implementation and use it here (pseudo code)
     *      displaySurface((MySurface) surface);
     *      // Once you're done, clear the dirty bounds:
     *      surface.clearDirtyBounds();
     * }
     *     </code>
     * </pre>
     *
     * @return the current dirty bounds
     */
    IntRect dirtyBounds();

    /**
     * Clear the dirty bounds.
     * <p>
     * You should call this after you're done displaying the Surface.
     */
    void clearDirtyBounds();
}
