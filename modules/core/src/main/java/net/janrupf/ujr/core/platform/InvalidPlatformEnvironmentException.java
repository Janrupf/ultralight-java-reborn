package net.janrupf.ujr.core.platform;

import net.janrupf.ujr.core.exception.UltralightJavaRebornRuntimeException;
import net.janrupf.ujr.core.platform.provider.PlatformEnvironmentProvider;

/**
 * Exception thrown when a platform environment can not be loaded due to some failure except generally
 * missing support.
 * <p>
 * This exception should only occur when required libraries are missing or are corrupted. If you are writing a
 * {@link PlatformEnvironmentProvider} and do not support the platform you are being called on, throw a
 * {@link UnsupportedPlatformEnvironmentException} instead!
 */
public class InvalidPlatformEnvironmentException extends UltralightJavaRebornRuntimeException {
    public InvalidPlatformEnvironmentException(String message) {
        super(message);
    }

    public InvalidPlatformEnvironmentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPlatformEnvironmentException(Throwable cause) {
        super(cause);
    }
}
