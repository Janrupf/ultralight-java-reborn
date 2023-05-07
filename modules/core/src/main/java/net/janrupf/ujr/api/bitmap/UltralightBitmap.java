package net.janrupf.ujr.api.bitmap;

import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.api.util.UltralightBuffer;
import net.janrupf.ujr.core.platform.abstraction.UlBitmap;
import net.janrupf.ujr.core.platform.abstraction.UlBitmapFactory;
import net.janrupf.ujr.core.util.ApiProvider;

import java.nio.ByteBuffer;

/**
 * Bitmap container with basic blitting and conversion routines.
 */
public class UltralightBitmap {
    // Used for constructing bitmaps
    private static final ApiProvider<UlBitmapFactory> BITMAP_FACTORY_PROVIDER = new ApiProvider<>(UlBitmapFactory.class);

    private final UlBitmap bitmap;

    // Internal use
    public UltralightBitmap(UlBitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Create an empty bitmap. No pixels will be allocated.
     */
    public UltralightBitmap() {
        this(BITMAP_FACTORY_PROVIDER.require().create());
    }

    /**
     * Create a Bitmap with a certain configuration. Pixels will be allocated but not initialized.
     *
     * @param width  the width in pixels
     * @param height the height in pixels
     * @param format the pixel format to use
     */
    public UltralightBitmap(long width, long height, UlBitmapFormat format) {
        this(BITMAP_FACTORY_PROVIDER.require().create(width, height, format));
    }

    /**
     * Create an aligned Bitmap with a certain configuration. Pixels will be allocated but not
     * initialized. Row bytes will be padded to reach the specified alignment.
     *
     * @param width     the width in pixels
     * @param height    the height in pixels
     * @param format    the pixel format to use
     * @param alignment the alignment in bytes to use; row bytes will be padded to reach a
     *                  multiple of this value and the underlying storage will be allocated with
     *                  this alignment
     */
    public UltralightBitmap(long width, long height, UlBitmapFormat format, long alignment) {
        this(BITMAP_FACTORY_PROVIDER.require().create(width, height, format, alignment));
    }

    /**
     * Create a bitmap from a deep copy of another bitmap.
     *
     * @param other the bitmap to copy from
     */
    public UltralightBitmap(UltralightBitmap other) {
        this(BITMAP_FACTORY_PROVIDER.require().create(other.bitmap));
    }

    /**
     * Retrieves the width of the bitmap.
     *
     * @return the width in pixels
     */
    public long width() {
        return bitmap.width();
    }

    /**
     * Retrieves the height of the bitmap.
     *
     * @return the height in pixels
     */
    public long height() {
        return bitmap.height();
    }

    /**
     * Retrieves the bounds as an IntRect.
     *
     * @return the bounds
     */
    public IntRect bounds() {
        return bitmap.bounds();
    }

    /**
     * Retrieves the pixel format of the bitmap.
     *
     * @return the pixel format
     */
    public UlBitmapFormat format() {
        return bitmap.format();
    }

    /**
     * Retrieves the number of bytes per pixel.
     *
     * @return the number of bytes per pixel
     */
    public long bpp() {
        return bitmap.bpp();
    }

    /**
     * Retrieves the number of bytes between each row of pixels.
     *
     * @return the number of bytes between each row
     */
    public long rowBytes() {
        return bitmap.rowBytes();
    }

    /**
     * Retrieves the size in bytes of the pixel buffer.
     *
     * @return the size in bytes of the pixel buffer
     * @implNote This is equivalent to {@code rowBytes() * height()}.
     */
    public long size() {
        return bitmap.size();
    }

    /**
     * Determines whether this Bitmap owns the pixel buffer and will destroy it at the end of its
     * lifetime.
     *
     * @return true if this Bitmap owns the pixel buffer, false otherwise
     */
    public boolean ownsPixels() {
        return bitmap.ownsPixels();
    }

    /**
     * Lock the pixel buffer for reading/writing.
     * <p>
     * The pixels are unlocked when the buffer is closed.
     *
     * @return the pixel buffer
     */
    public UltralightBuffer lockPixels() {
        return bitmap.lockPixels();
    }

    /**
     * Retrieves the raw pixel buffer.
     * <p>
     * This may only be called while the pixel buffer is locked.
     *
     * @return the raw pixel buffer
     */
    public ByteBuffer rawPixels() {
        return bitmap.rawPixels();
    }

    /**
     * Determines whether this bitmap is empty (no pixels are allocated).
     *
     * @return true if this bitmap is empty, false otherwise
     */
    public boolean isEmpty() {
        return bitmap.isEmpty();
    }

    /**
     * Erase the bitmap (set all pixels to 0).
     */
    public void erase() {
        bitmap.erase();
    }

    /**
     * Assign another bitmap to this one.
     *
     * @param other the bitmap to copy from
     */
    public void set(UltralightBitmap other) {
        bitmap.set(other.bitmap);
    }

    /**
     * Draw another bitmap to this bitmap.
     *
     * @param srcRect   the source rectangle, relative to src bitmap
     * @param destRect  the destination rectangle, relative to this bitmap
     * @param src       the source bitmap
     * @param padRepeat whether we should pad the drawn bitmap by one pixel of repeated
     *                  edge pixels from the source bitmap
     * @return true if the operation succeeded, false otherwise (this can fail if srcRect or destRect
     * are invalid)
     * @implNote Formats do not need to match. Bitmap formats will be converted to one another
     * automatically. Note that when converting from {@link UlBitmapFormat#BGRA8_UNORM_SRGB} to
     * {@link UlBitmapFormat#A8_UNORM}, only the blue channel will be used.
     */
    public boolean drawBitmap(IntRect srcRect, IntRect destRect, UltralightBitmap src, boolean padRepeat) {
        return bitmap.drawBitmap(srcRect, destRect, src.bitmap, padRepeat);
    }

    /**
     * Write this bitmap out to a PNG image.
     *
     * @param path the filepath to write to
     * @return true if the operation succeeded, false otherwise
     */
    public boolean writePNG(String path) {
        return writePNG(path, true);
    }

    /**
     * Write this bitmap out to a PNG image.
     *
     * @param path          the filepath to write to
     * @param convertToRGBA PNG expects RGBA, but Ultralight uses BGRA, if true, this conversion
     *                      will be performed automatically
     * @return true if the operation succeeded, false otherwise
     */
    public boolean writePNG(String path, boolean convertToRGBA) {
        return writePNG(path, convertToRGBA, true);
    }

    /**
     * Write this bitmap out to a PNG image.
     *
     * @param path                   the filepath to write to
     * @param convertToRGBA          PNG expects RGBA, but Ultralight uses BGRA, if true, this conversion
     *                               will be performed automatically
     * @param convertToStraightAlpha PNG expects straight alpha, but Ultralight uses premultiplied alpha,
     *                               if true, this conversion will be performed automatically
     * @return true if the operation succeeded, false otherwise
     */
    public boolean writePNG(String path, boolean convertToRGBA, boolean convertToStraightAlpha) {
        return bitmap.writePNG(path, convertToRGBA, convertToStraightAlpha);
    }

    /**
     * Make a resized copy of this bitmap by writing to a pre-allocated destination bitmap.
     * <p>
     * Both bitmaps need to be non-empty and of the format {@link UlBitmapFormat#BGRA8_UNORM_SRGB}.
     *
     * @param destination the bitmap to store the result in, the width and height of the
     *                    destination bitmap will be used
     * @param highQuality whether to use high quality resampling during the resize
     *                    (otherwise, nearest-neighbor will be used)
     * @return true if the operation succeeded, false otherwise
     */
    public boolean resample(UltralightBitmap destination, boolean highQuality) {
        return bitmap.resample(destination.bitmap, highQuality);
    }

    /**
     * Convert a BGRA bitmap to a RGBA bitmap and vice-versa by swapping the
     * red and blue channels.
     * <p>
     * This is only valid for {@link UlBitmapFormat#BGRA8_UNORM_SRGB} bitmaps.
     */
    public void swapRedBlueChannels() {
        bitmap.swapRedBlueChannels();
    }

    /**
     * Convert a BGRA bitmap from premultiplied alpha (the default) to straight alpha.
     * <p>
     * This is only valid for {@link UlBitmapFormat#BGRA8_UNORM_SRGB} bitmaps.
     */
    public void convertToStraightAlpha() {
        bitmap.convertToStraightAlpha();
    }

    /**
     * Convert a BGRA bitmap from straight alpha to premultiplied alpha (the default).
     * <p>
     * This is only valid for {@link UlBitmapFormat#BGRA8_UNORM_SRGB} bitmaps.
     */
    public void convertToPremultipliedAlpha() {
        bitmap.convertToPremultipliedAlpha();
    }
}
