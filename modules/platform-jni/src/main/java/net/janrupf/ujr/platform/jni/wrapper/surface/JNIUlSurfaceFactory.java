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
    public JNIUlSurface createSurface(long width, long height) {
        return new JNIUlSurface(delegate.createSurface(width, height));
    }

    @NativeAccess
    public void destroySurface(JNIUlSurface surface) {
        delegate.destroySurface(surface.getDelegate());
    }
}
