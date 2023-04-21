package net.janrupf.ujr.platform.jni.wrapper.logger;

import net.janrupf.ujr.api.logger.UltralightLogLevel;
import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

/**
 * Wrapper for a native Ultralight logger instance.
 */
public class JNIUlLoggerNative implements UltralightLogger {
    // Note that this class is allocated by native code without calling the constructor
    private JNIUlLoggerNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @NativeAccess
    private long handle;

    @Override
    public void logMessage(UltralightLogLevel logLevel, String message) {
        nativeLogMessage(logLevel, message);
    }

    private native void nativeLogMessage(UltralightLogLevel logLevel, String message);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlLoggerNative)) return false;
        JNIUlLoggerNative other = (JNIUlLoggerNative) o;
        return handle == other.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
