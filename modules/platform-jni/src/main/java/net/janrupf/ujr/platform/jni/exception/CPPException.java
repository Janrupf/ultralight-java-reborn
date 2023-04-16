package net.janrupf.ujr.platform.jni.exception;

import net.janrupf.ujr.core.exception.UltralightJavaRebornRuntimeException;

/**
 * An uncaught exception thrown by the native code which could not be associated with a specific
 * java exception.
 */
public class CPPException extends UltralightJavaRebornRuntimeException {
    public CPPException(String message) {
        super(message);
    }
}
