package net.janrupf.ujr.core.exception;

/**
 * Base class for all unchecked Ultralight Java Reborn exceptions.
 * <p>
 * This class is provided as a convenience "catch-alL" solution to catch <b>unchecked</b> exceptions
 * from this library. Consider using more concrete catch clauses where possible!
 * <p>
 * It's checked counterpart is {@link UltralightJavaRebornException}.
 */
public class UltralightJavaRebornRuntimeException extends RuntimeException {
    public UltralightJavaRebornRuntimeException(String message) {
        super(message);
    }

    public UltralightJavaRebornRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UltralightJavaRebornRuntimeException(Throwable cause) {
        super(cause);
    }
}
