package net.janrupf.ujr.api.bitmap;

import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.api.util.UltralightBuffer;
import net.janrupf.ujr.core.platform.abstraction.UlBitmapSurface;

public class UltralightBitmapSurface implements UltralightSurface {
    private final UlBitmapSurface surface;

    // Internal
    public UltralightBitmapSurface(UlBitmapSurface surface) {
        this.surface = surface;
    }

    @Override
    public long width() {
        return surface.width();
    }

    @Override
    public long height() {
        return surface.height();
    }

    @Override
    public long rowBytes() {
        return surface.rowBytes();
    }

    @Override
    public long size() {
        return surface.size();
    }

    @Override
    public UltralightBuffer lockPixels() {
        return surface.lockPixels();
    }

    @Override
    public void resize(long width, long height) {
        surface.resize(width, height);
    }

    @Override
    public void setDirtyBounds(IntRect bounds) {
        surface.setDirtyBounds(bounds);
    }

    @Override
    public IntRect dirtyBounds() {
        return surface.dirtyBounds();
    }

    @Override
    public void clearDirtyBounds() {
        surface.clearDirtyBounds();
    }

    /**
     * Retrieves the underlying bitmap of this surface.
     *
     * @return the underlying bitmap of this surface
     */
    public UltralightBitmap bitmap() {
        return surface.bitmap();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UltralightBitmapSurface)) {
            return false;
        }

        return surface.equals(((UltralightBitmapSurface) obj).surface);
    }

    @Override
    public int hashCode() {
        return surface.hashCode();
    }
}
