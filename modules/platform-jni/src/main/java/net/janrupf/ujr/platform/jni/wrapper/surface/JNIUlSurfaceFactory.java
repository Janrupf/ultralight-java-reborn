package net.janrupf.ujr.platform.jni.wrapper.surface;

import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.api.surface.UltralightSurfaceFactory;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlSurfaceFactory {
    private final UltralightSurfaceFactory delegate;

    public JNIUlSurfaceFactory(UltralightSurfaceFactory delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public UltralightSurface createSurface(long width, long height) {
        return delegate.createSurface(width, height);
    }

    @NativeAccess
    public void destroySurface(UltralightSurface surface) {
        delegate.destroySurface(surface);
    }
}
