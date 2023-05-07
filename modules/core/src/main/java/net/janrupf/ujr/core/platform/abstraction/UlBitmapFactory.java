package net.janrupf.ujr.core.platform.abstraction;

import net.janrupf.ujr.api.bitmap.UlBitmapFormat;

public interface UlBitmapFactory {
    UlBitmap create();

    UlBitmap create(long width, long height, UlBitmapFormat format);

    UlBitmap create(long width, long height, UlBitmapFormat format, long alignment);

    UlBitmap create(UlBitmap bitmap);
}
