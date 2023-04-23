package net.janrupf.ujr.api.util;

import java.nio.ByteBuffer;

/**
 * Helper interface around Ultralight byte buffers.
 * <p>
 * See {@link NioUltralightBuffer} for an implementation which allows you to
 * wrap any {@link ByteBuffer} as an Ultralight buffer.
 */
public interface UltralightBuffer extends AutoCloseable {
    /**
     * Converts this buffer to a {@link ByteBuffer}.
     * <p>
     * It is recommended that this method returns a direct byte buffer for faster access.
     * However, any type of byte buffer is allowed but may incur a performance penalty.
     *
     * @return this buffer as a byte buffer
     */
    ByteBuffer asByteBuffer();

    /**
     * Closes the buffer and releases any resources associated with it.
     * <p>
     * If closing fails, the error should be logged and either ignored or
     * thrown as an unchecked exception.
     */
    @Override
    void close();
}
