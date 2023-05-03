package net.janrupf.ujr.platform.jni.wrapper.surface;

import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.api.util.UltralightBuffer;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlSurfaceNative implements UltralightSurface {
    @NativeAccess
    private final long handle;

    @NativeAccess
    private long lockedPixels;

    private JNIUlSurfaceNative() {
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
        return new JNIUlSurfaceNativePixelBuffer(this, nativeLockPixels());
    }

    private native /* ByteBuffer or byte[] */ Object nativeLockPixels();

    /* package */ native void nativeUnlockPixels(byte[] storage);

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
}
