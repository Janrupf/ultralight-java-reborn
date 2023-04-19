package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.api.config.UlConfig;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlPlatform implements UlPlatform {
    @NativeAccess
    private final long handle;

    JNIUlPlatform(long handle) {
        this.handle = handle;
    }

    @Override
    public void setConfig(UlConfig config) {
        nativeSetConfig(config);
    }

    private native void nativeSetConfig(UlConfig config);
}
