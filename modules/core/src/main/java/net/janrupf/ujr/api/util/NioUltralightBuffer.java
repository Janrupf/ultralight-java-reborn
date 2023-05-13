package net.janrupf.ujr.api.util;

import java.nio.ByteBuffer;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NioUltralightBuffer)) return false;
        NioUltralightBuffer that = (NioUltralightBuffer) o;
        return Objects.equals(buffer, that.buffer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buffer);
    }
}
