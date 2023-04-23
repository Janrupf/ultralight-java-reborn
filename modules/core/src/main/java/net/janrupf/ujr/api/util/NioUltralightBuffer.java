package net.janrupf.ujr.api.util;

import java.nio.ByteBuffer;

/**
 * Wraps an existing {@link ByteBuffer} as an {@link UltralightBuffer}.
 */
public class NioUltralightBuffer implements UltralightBuffer {
    private final ByteBuffer buffer;

    /**
     * Constructs a new {@link NioUltralightBuffer} from the given {@link ByteBuffer}.
     *
     * @param buffer the buffer to wrap
     */
    public NioUltralightBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public ByteBuffer asByteBuffer() {
        return buffer;
    }

    @Override
    public void close() {
        /* no-op */
    }
}
