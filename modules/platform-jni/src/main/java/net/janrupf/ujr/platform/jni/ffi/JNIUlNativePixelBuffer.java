package net.janrupf.ujr.platform.jni.ffi;

import net.janrupf.ujr.api.util.UltralightBuffer;

import java.nio.ByteBuffer;

public class JNIUlNativePixelBuffer implements UltralightBuffer {
    private final NativePixelBufferHolder holder;

    private final ByteBuffer buffer;
    private final byte[] array;

    public JNIUlNativePixelBuffer(NativePixelBufferHolder holder, Object storage) {
        this.holder = holder;

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
        holder.unlockPixels(array);
    }
}
