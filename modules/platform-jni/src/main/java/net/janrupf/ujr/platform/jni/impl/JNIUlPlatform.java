package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.core.platform.abstraction.UlPlatform;

import java.lang.annotation.Native;

public class JNIUlPlatform implements UlPlatform {
    @Native
    private final long handle;

    JNIUlPlatform(long handle) {
        this.handle = handle;
    }
}
