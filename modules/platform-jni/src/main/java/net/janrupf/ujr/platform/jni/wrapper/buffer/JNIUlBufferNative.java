package net.janrupf.ujr.platform.jni.wrapper.buffer;

import net.janrupf.ujr.api.util.UltralightBuffer;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.nio.ByteBuffer;
import java.util.Objects;

// TODO: This class leaks if close() is not called, figure out how to fix this
public class JNIUlBufferNative implements UltralightBuffer {
    // Note that this class is allocated by native code without calling the constructor
    private JNIUlBufferNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @NativeAccess
    private long handle;
    private ByteBuffer buffer;

    @Override
    public ByteBuffer asByteBuffer() {
        if (handle == 0) {
            throw new IllegalStateException("The buffer has already been released");
        }

        if (buffer == null) {
            buffer = nativeAsByteBuffer();
        }

        return buffer;
    }

    private native ByteBuffer nativeAsByteBuffer();

    @Override
    public void close() {
        if (buffer != null) {
            nativeClose();
            handle = 0;
        }
    }

    private native void nativeClose();

    @Override
    public boolean equals(Object o) {
        // Ignores buffer, only compares handle

        if (this == o) return true;
        if (!(o instanceof JNIUlBufferNative)) return false;
        JNIUlBufferNative that = (JNIUlBufferNative) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        // Ignores buffer, only hashes handle
        return Objects.hash(handle);
    }
}
