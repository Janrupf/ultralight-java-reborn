package net.janrupf.ujr.core.platform;

import net.janrupf.ujr.core.exception.UltralightJavaRebornRuntimeException;

/**
 * Exception thrown when a platform environment does not support a required API.
 */
public class UnsupportedPlatformApiException extends UltralightJavaRebornRuntimeException {
    public UnsupportedPlatformApiException(String message) {
        super(message);
    }

    public UnsupportedPlatformApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedPlatformApiException(Throwable cause) {
        super(cause);
    }
}
