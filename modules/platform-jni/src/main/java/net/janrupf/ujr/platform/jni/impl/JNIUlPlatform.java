package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.api.config.UlConfig;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.wrapper.logger.JNIUlLogger;

public class JNIUlPlatform implements UlPlatform {
    @NativeAccess
    private final long handle;

    @NativeAccess
    private long logger; // Pointer to a native logger, TODO: Garbage collect?

    JNIUlPlatform(long handle) {
        this.handle = handle;
    }

    @Override
    public void setConfig(UlConfig config) {
        nativeSetConfig(config);
    }

    private native void nativeSetConfig(UlConfig config);

    @Override
    public void setLogger(UltralightLogger logger) {
        nativeSetLogger(new JNIUlLogger(logger));
    }

    private native void nativeSetLogger(JNIUlLogger logger);

    @Override
    public UltralightLogger getLogger() {
        return nativeGetLogger();
    }

    private native UltralightLogger nativeGetLogger();
}
