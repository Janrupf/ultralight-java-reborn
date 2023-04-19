package net.janrupf.ujr.platform.jni.wrapper.logger;

import net.janrupf.ujr.api.logger.UltralightLogLevel;
import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlLogger {
    private final UltralightLogger delegate;

    public JNIUlLogger(UltralightLogger delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public void logMessage(UltralightLogLevel logLevel, String message) {
        delegate.logMessage(logLevel, message);
    }
}
