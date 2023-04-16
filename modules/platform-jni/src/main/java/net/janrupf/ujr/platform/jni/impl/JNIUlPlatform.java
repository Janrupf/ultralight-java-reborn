package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.core.platform.abstraction.config.UlConfig;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIUlPlatform implements UlPlatform {
    @NativeAccess
    private final long handle;

    JNIUlPlatform(long handle) {
        this.handle = handle;
    }

    @Override
    public void setConfig(UlConfig config) {
        Objects.requireNonNull(config, "config must not be null");
        nativeSetConfig(config);
    }

    private native void nativeSetConfig(UlConfig config);
}
