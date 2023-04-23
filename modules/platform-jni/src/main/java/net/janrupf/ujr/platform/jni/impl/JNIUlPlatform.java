package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.api.config.UlConfig;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.wrapper.filesystem.JNIUlFilesystem;
import net.janrupf.ujr.platform.jni.wrapper.logger.JNIUlLogger;

public class JNIUlPlatform implements UlPlatform {
    @NativeAccess
    private final long handle;

    @NativeAccess
    private long logger; // Pointer to a native logger, TODO: Garbage collect?

    @NativeAccess
    private long filesystem; // Pointer to a native filesystem, TODO: Garbage collect?

    JNIUlPlatform(long handle) {
        this.handle = handle;
    }

    @Override
    public void setConfig(UlConfig config) {
        nativeSetConfig(config);
    }

    private native void nativeSetConfig(UlConfig config);

    @Override
    public void usePlatformFontLoader() {
        nativeUsePlatformFontLoader();
    }

    private native void nativeUsePlatformFontLoader();

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

    @Override
    public void setFilesystem(UltralightFilesystem filesystem) {
        nativeSetFilesystem(new JNIUlFilesystem(filesystem));
    }

    private native void nativeSetFilesystem(JNIUlFilesystem filesystem);

    @Override
    public UltralightFilesystem getFilesystem() {
        return nativeGetFilesystem();
    }

    private native UltralightFilesystem nativeGetFilesystem();
}
