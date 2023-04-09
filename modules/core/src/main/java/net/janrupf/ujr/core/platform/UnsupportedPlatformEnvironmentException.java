package net.janrupf.ujr.core.platform;

import net.janrupf.ujr.core.exception.UltralightJavaRebornRuntimeException;

/**
 * Unchecked exception thrown when a platform is not supported.
 * <p>
 * If this exception is thrown you should check whether you have the required libraries on the path!
 */
public class UnsupportedPlatformEnvironmentException extends UltralightJavaRebornRuntimeException {
    public UnsupportedPlatformEnvironmentException(String message) {
        super(message);
    }
}
