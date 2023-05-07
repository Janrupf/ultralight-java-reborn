package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.bitmap.UltralightBitmap;
import net.janrupf.ujr.core.platform.abstraction.UlBitmapSurface;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.wrapper.surface.JNIUlSurfaceNative;

@NativeAccess
public class JNIUlBitmapSurface extends JNIUlSurfaceNative implements UlBitmapSurface {
    @Override
    public UltralightBitmap bitmap() {
        return new UltralightBitmap(nativeBitmap());
    }

    private native JNIUlBitmap nativeBitmap();
}
