package net.janrupf.ujr.platform.jni.wrapper.surface;

import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.wrapper.buffer.JNIUlDelegatedBuffer;

public class JNIUlSurface {
    private final UltralightSurface delegate;

    public JNIUlSurface(UltralightSurface delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public long width() {
        return delegate.width();
    }

    @NativeAccess
    public long height() {
        return delegate.height();
    }

    @NativeAccess
    public long rowBytes() {
        return delegate.rowBytes();
    }

    @NativeAccess
    public long size() {
        return delegate.size();
    }

    @NativeAccess
    public JNIUlDelegatedBuffer lockPixels() {
        return new JNIUlDelegatedBuffer(delegate.lockPixels());
    }

    @NativeAccess
    public void resize(long width, long height) {
        delegate.resize(width, height);
    }

    @NativeAccess
    public void setDirtyBounds(IntRect bounds) {
        delegate.setDirtyBounds(bounds);
    }

    @NativeAccess
    public IntRect dirtyBounds() {
        return delegate.dirtyBounds();
    }

    @NativeAccess
    public void clearDirtyBounds() {
        delegate.clearDirtyBounds();
    }
}
