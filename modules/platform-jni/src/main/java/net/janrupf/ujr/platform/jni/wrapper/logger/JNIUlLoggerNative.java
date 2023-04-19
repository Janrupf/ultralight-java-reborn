package net.janrupf.ujr.platform.jni.wrapper.logger;

import net.janrupf.ujr.api.logger.UltralightLogLevel;
import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlLoggerNative implements UltralightLogger {
    @NativeAccess
    private long handle;

    @Override
    public void logMessage(UltralightLogLevel logLevel, String message) {
        nativeLogMessage(logLevel, message);
    }

    private native void nativeLogMessage(UltralightLogLevel logLevel, String message);
}
