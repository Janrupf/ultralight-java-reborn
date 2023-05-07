package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.surface.UltralightSurfaceFactory;
import net.janrupf.ujr.core.platform.abstraction.UlBitmapSurfaceFactoryProvider;

public class JNIUlBitmapSurfaceFactoryProvider implements UlBitmapSurfaceFactoryProvider {
    @Override
    public UltralightSurfaceFactory getBitmapSurfaceFactory() {
        return nativeGetBitmapSurfaceFactory();
    }

    private native UltralightSurfaceFactory nativeGetBitmapSurfaceFactory();
}
