package net.janrupf.ujr.platform.jni.wrapper.buffer;

import net.janrupf.ujr.api.util.UltralightBuffer;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * Helper class to wrap a {@link UltralightBuffer} and provide a native interface to it.
 */
public class JNIUlDelegatedBuffer {
    private UltralightBuffer delegate;

    public JNIUlDelegatedBuffer(UltralightBuffer delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public ByteBuffer getByteBuffer() {
        Objects.requireNonNull(delegate, "The buffer has already been released");

        return delegate.asByteBuffer();
    }

    @NativeAccess
    public void release() {
        if (delegate != null) {
            delegate.close();
            delegate = null;
        }
    }
}
