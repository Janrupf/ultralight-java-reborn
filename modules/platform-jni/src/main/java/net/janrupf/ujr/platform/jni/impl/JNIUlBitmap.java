package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.bitmap.UlBitmapFormat;
import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.api.util.UltralightBuffer;
import net.janrupf.ujr.core.platform.abstraction.UlBitmap;
import net.janrupf.ujr.platform.jni.ffi.JNIUlNativePixelBuffer;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.ffi.NativePixelBufferHolder;

import java.nio.ByteBuffer;

public class JNIUlBitmap implements UlBitmap, NativePixelBufferHolder {
    @NativeAccess
    private final long handle;

    @NativeAccess
    private long lockedPixels;

    private UltralightBuffer lockedPixelsBuffer;

    private JNIUlBitmap() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public long width() {
        return nativeWidth();
    }

    private native long nativeWidth();

    @Override
    public long height() {
        return nativeHeight();
    }

    private native long nativeHeight();

    @Override
    public IntRect bounds() {
        return nativeBounds();
    }

    private native IntRect nativeBounds();

    @Override
    public UlBitmapFormat format() {
        return nativeFormat();
    }

    private native UlBitmapFormat nativeFormat();

    @Override
    public long bpp() {
        return nativeBpp();
    }

    private native long nativeBpp();

    @Override
    public long rowBytes() {
        return nativeRowBytes();
    }

    private native long nativeRowBytes();

    @Override
    public long size() {
        return nativeSize();
    }

    private native long nativeSize();

    @Override
    public boolean ownsPixels() {
        return nativeOwnsPixels();
    }

    private native boolean nativeOwnsPixels();

    @Override
    public UltralightBuffer lockPixels() {
        return (lockedPixelsBuffer = new JNIUlNativePixelBuffer(this, nativeLockPixels()));
    }

    private native Object nativeLockPixels();

    @Override
    public void unlockPixels(byte[] storage) {
        lockedPixelsBuffer = null;
        nativeUnlockPixels(storage);
    }

    private native void nativeUnlockPixels(byte[] storage);

    @Override
    public ByteBuffer rawPixels() {
        if (lockedPixelsBuffer == null) {
            throw new IllegalStateException("Pixels are not locked");
        }

        return lockedPixelsBuffer.asByteBuffer();
    }

    @Override
    public boolean isEmpty() {
        return nativeIsEmpty();
    }

    private native boolean nativeIsEmpty();

    @Override
    public void erase() {
        nativeErase();
    }

    private native void nativeErase();

    @Override
    public void set(UlBitmap bitmap) {
        nativeSet(bitmap);
    }

    private native void nativeSet(UlBitmap bitmap);

    @Override
    public boolean drawBitmap(IntRect srcRect, IntRect destRect, UlBitmap bitmap, boolean padRepeat) {
        return nativeDrawBitmap(srcRect, destRect, bitmap, padRepeat);
    }

    private native boolean nativeDrawBitmap(IntRect srcRect, IntRect destRect, UlBitmap bitmap, boolean padRepeat);

    @Override
    public boolean writePNG(String path, boolean convertToRGBA, boolean convertToStraightAlpha) {
        return nativeWritePNG(path, convertToRGBA, convertToStraightAlpha);
    }

    private native boolean nativeWritePNG(String path, boolean convertToRGBA, boolean convertToStraightAlpha);

    @Override
    public boolean resample(UlBitmap bitmap, boolean highQuality) {
        return nativeResample(bitmap, highQuality);
    }

    private native boolean nativeResample(UlBitmap bitmap, boolean highQuality);

    @Override
    public void swapRedBlueChannels() {
        nativeSwapRedBlueChannels();
    }

    private native void nativeSwapRedBlueChannels();

    @Override
    public void convertToStraightAlpha() {
        nativeConvertToStraightAlpha();
    }

    private native void nativeConvertToStraightAlpha();

    @Override
    public void convertToPremultipliedAlpha() {
        nativeConvertToPremultipliedAlpha();
    }

    private native void nativeConvertToPremultipliedAlpha();
}
