package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.bitmap.UlBitmapFormat;
import net.janrupf.ujr.core.platform.abstraction.UlBitmap;
import net.janrupf.ujr.core.platform.abstraction.UlBitmapFactory;

public class JNIUlBitmapFactory implements UlBitmapFactory {
    @Override
    public UlBitmap create() {
        return nativeCreate0();
    }

    private native JNIUlBitmap nativeCreate0();

    @Override
    public UlBitmap create(long width, long height, UlBitmapFormat format) {
        return nativeCreate1(width, height, format);
    }

    private native JNIUlBitmap nativeCreate1(long width, long height, UlBitmapFormat format);

    @Override
    public UlBitmap create(long width, long height, UlBitmapFormat format, long alignment) {
        return nativeCreate2(width, height, format, alignment);
    }

    private native JNIUlBitmap nativeCreate2(long width, long height, UlBitmapFormat format, long alignment);

    @Override
    public UlBitmap create(UlBitmap bitmap) {
        return nativeCreate3((JNIUlBitmap) bitmap);
    }

    private native JNIUlBitmap nativeCreate3(JNIUlBitmap bitmap);
}
