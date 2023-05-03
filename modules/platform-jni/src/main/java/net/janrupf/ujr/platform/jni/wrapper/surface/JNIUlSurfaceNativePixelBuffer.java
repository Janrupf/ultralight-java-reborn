package net.janrupf.ujr.platform.jni.wrapper.surface;

import net.janrupf.ujr.api.util.UltralightBuffer;

import java.nio.ByteBuffer;

public class JNIUlSurfaceNativePixelBuffer implements UltralightBuffer {
    private final JNIUlSurfaceNative surface;

    private final ByteBuffer buffer;
    private final byte[] array;

    /* package */ JNIUlSurfaceNativePixelBuffer(JNIUlSurfaceNative surface, Object storage) {
        this.surface = surface;

        if (storage instanceof ByteBuffer) {
            // Got a direct buffer
            buffer = (ByteBuffer) storage;
            array = null;
        } else {
            // Got a byte array, we need to store both
            buffer = ByteBuffer.wrap((byte[]) storage);
            array = (byte[]) storage;
        }
    }

    @Override
    public ByteBuffer asByteBuffer() {
        return buffer;
    }

    @Override
    public void close() {
        // Unlock the pixels when closing the buffer.
        //
        // Passing null as the array here is fine, as this signals to the native
        // code that no writeback is necessary.
        surface.nativeUnlockPixels(array);
    }
}
