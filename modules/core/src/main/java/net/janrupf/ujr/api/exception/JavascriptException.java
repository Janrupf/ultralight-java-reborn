package net.janrupf.ujr.api.exception;

import net.janrupf.ujr.core.exception.UltralightJavaRebornException;

/**
 * Exception thrown when a Javascript error occurs and propagates back to Java.
 */
public class JavascriptException extends UltralightJavaRebornException {
    public JavascriptException(String message) {
        super(message);
    }
}
