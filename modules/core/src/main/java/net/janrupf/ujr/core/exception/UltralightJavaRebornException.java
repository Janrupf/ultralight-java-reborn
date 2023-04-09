package net.janrupf.ujr.core.exception;

/**
 * Base class for all checked Ultralight Java Reborn exceptions.
 * <p>
 * This class is provided as a convenience "catch-alL" solution to catch <b>checked</b> exceptions
 * from this library. Consider using more concrete catch clauses where possible!
 * <p>
 * It's unchecked counterpart is {@link UltralightJavaRebornRuntimeException}.
 */
public class UltralightJavaRebornException extends Exception {
    public UltralightJavaRebornException(String message) {
        super(message);
    }

    public UltralightJavaRebornException(String message, Throwable cause) {
        super(message, cause);
    }

    public UltralightJavaRebornException(Throwable cause) {
        super(cause);
    }
}
