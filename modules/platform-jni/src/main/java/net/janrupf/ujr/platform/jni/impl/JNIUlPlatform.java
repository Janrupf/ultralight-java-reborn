package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.clipboard.UltralightClipboard;
import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.api.surface.UltralightSurfaceFactory;
import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.api.config.UlConfig;
import net.janrupf.ujr.core.platform.abstraction.UlRenderer;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.wrapper.filesystem.JNIUlFilesystem;
import net.janrupf.ujr.platform.jni.wrapper.logger.JNIUlLogger;

import java.util.Objects;

public class JNIUlPlatform implements UlPlatform {
    @NativeAccess
    private final long handle;

    @NativeAccess
    private final long nativeCollector;

    private JNIUlPlatform() {
        throw new RuntimeException("Allocate in native code without calling constructor");
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

    @Override
    public void setClipboard(UltralightClipboard clipboard) {
        nativeSetClipboard(clipboard);
    }

    private native void nativeSetClipboard(UltralightClipboard clipboard);

    @Override
    public UltralightClipboard getClipboard() {
        return nativeGetClipboard();
    }

    private native UltralightClipboard nativeGetClipboard();

    @Override
    public void setSurfaceFactory(UltralightSurfaceFactory surfaceFactory) {
        nativeSetSurfaceFactory(surfaceFactory);
    }

    private native void nativeSetSurfaceFactory(UltralightSurfaceFactory surfaceFactory);

    @Override
    public UltralightSurfaceFactory surfaceFactory() {
        return nativeSurfaceFactory();
    }

    private native UltralightSurfaceFactory nativeSurfaceFactory();

    @Override
    public UlRenderer createRenderer() {
        return nativeCreateRenderer();
    }

    private native UlRenderer nativeCreateRenderer();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlPlatform)) return false;
        JNIUlPlatform that = (JNIUlPlatform) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
