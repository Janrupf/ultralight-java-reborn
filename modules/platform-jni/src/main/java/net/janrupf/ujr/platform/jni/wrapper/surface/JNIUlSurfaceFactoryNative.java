package net.janrupf.ujr.platform.jni.wrapper.surface;

import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.api.surface.UltralightSurfaceFactory;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlSurfaceFactoryNative implements UltralightSurfaceFactory {
    @NativeAccess
    private final long handle;

    private JNIUlSurfaceFactoryNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public UltralightSurface createSurface(long width, long height) {
        return nativeCreateSurface(width, height);
    }


    private native UltralightSurface nativeCreateSurface(long width, long height);

    @Override
    public void destroySurface(UltralightSurface surface) {
        nativeDestroySurface((JNIUlSurfaceNative) surface);
    }

    private native void nativeDestroySurface(JNIUlSurfaceNative surface);
}
