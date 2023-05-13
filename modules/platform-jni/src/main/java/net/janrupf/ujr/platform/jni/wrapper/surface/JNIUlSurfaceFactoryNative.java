package net.janrupf.ujr.platform.jni.wrapper.surface;

import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.api.surface.UltralightSurfaceFactory;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlSurfaceFactoryNative)) return false;
        JNIUlSurfaceFactoryNative that = (JNIUlSurfaceFactoryNative) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
