package net.janrupf.ujr.api.bitmap;

/**
 * The various Bitmap formats.
 */
public enum UlBitmapFormat {
    /**
     * Alpha channel only, 8-bits per pixel.
     * <p>
     * Encoding: 8-bits per channel, unsigned normalized.
     * <p>
     * Color-space: Linear (no gamma), alpha-coverage only.
     */
    A8_UNORM,

    /**
     * Blue Green Red Alpha channels, 32-bits per pixel.
     * <p>
     * Encoding: 8-bits per channel, unsigned normalized.
     * <p>
     * Color-space: sRGB gamma with premultiplied linear alpha channel.
     */
    BGRA8_UNORM_SRGB;

    /**
     * Retrieves the number of bytes per pixel for this format.
     *
     * @return the number of bytes per pixel
     */
    public int bytesPerPixel() {
        switch (this) {
            case A8_UNORM:
                return 1;
            case BGRA8_UNORM_SRGB:
                return 4;
            default:
                throw new IllegalStateException("Unknown format: " + this);
        }
    }
}
