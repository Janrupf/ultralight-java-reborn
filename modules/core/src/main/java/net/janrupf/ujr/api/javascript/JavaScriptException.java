package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.exception.UltralightJavaRebornException;

/**
 * Exception thrown when a Javascript error occurs and propagates back to Java.
 */
public class JavaScriptException extends UltralightJavaRebornException {
    public JavaScriptException(String message) {
        super(message);
    }
}
