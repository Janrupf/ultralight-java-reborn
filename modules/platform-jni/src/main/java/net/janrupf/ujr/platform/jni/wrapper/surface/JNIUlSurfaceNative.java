package net.janrupf.ujr.platform.jni.wrapper.surface;

import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.api.util.UltralightBuffer;
import net.janrupf.ujr.platform.jni.ffi.JNIUlNativePixelBuffer;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.ffi.NativePixelBufferHolder;

import java.util.Objects;

public class JNIUlSurfaceNative implements UltralightSurface, NativePixelBufferHolder {
    @NativeAccess
    private final long handle;

    @NativeAccess
    private long lockedPixels;

    protected JNIUlSurfaceNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public long width() {
        return nativeWidth();
    }

    private native long nativeWidth();

    @Override
    public long height() {
        return nativeHeight();
    }

    private native long nativeHeight();

    @Override
    public long rowBytes() {
        return nativeRowBytes();
    }

    private native long nativeRowBytes();

    @Override
    public long size() {
        return nativeSize();
    }

    private native long nativeSize();

    @Override
    public UltralightBuffer lockPixels() {
        return new JNIUlNativePixelBuffer(this, nativeLockPixels());
    }

    private native /* ByteBuffer or byte[] */ Object nativeLockPixels();

    @Override
    public void unlockPixels(byte[] storage) {
        nativeUnlockPixels(storage);
    }

    public native void nativeUnlockPixels(byte[] storage);

    @Override
    public void resize(long width, long height) {
        nativeResize(width, height);
    }

    private native void nativeResize(long width, long height);

    @Override
    public void setDirtyBounds(IntRect bounds) {
        nativeSetDirtyBounds(bounds);
    }

    private native void nativeSetDirtyBounds(IntRect bounds);

    @Override
    public IntRect dirtyBounds() {
        return nativeDirtyBounds();
    }

    private native IntRect nativeDirtyBounds();

    @Override
    public void clearDirtyBounds() {
        nativeClearDirtyBounds();
    }

    private native void nativeClearDirtyBounds();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlSurfaceNative)) return false;
        JNIUlSurfaceNative that = (JNIUlSurfaceNative) o;
        return handle == that.handle && lockedPixels == that.lockedPixels;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle, lockedPixels);
    }
}
