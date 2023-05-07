package net.janrupf.ujr.core.platform.abstraction;

import net.janrupf.ujr.api.bitmap.UlBitmapFormat;
import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.api.util.UltralightBuffer;

import java.nio.ByteBuffer;

public interface UlBitmap {
    long width();

    long height();

    IntRect bounds();

    UlBitmapFormat format();

    long bpp();

    long rowBytes();

    long size();

    boolean ownsPixels();

    UltralightBuffer lockPixels();

    ByteBuffer rawPixels();

    boolean isEmpty();

    void erase();

    void set(UlBitmap bitmap);

    boolean drawBitmap(IntRect srcRect, IntRect destRect, UlBitmap bitmap, boolean padRepeat);

    boolean writePNG(String path, boolean convertToRGBA, boolean convertToStraightAlpha);

    boolean resample(UlBitmap bitmap, boolean highQuality);

    void swapRedBlueChannels();

    void convertToStraightAlpha();

    void convertToPremultipliedAlpha();
}
