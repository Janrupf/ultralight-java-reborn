package net.janrupf.ujr.api;

import net.janrupf.ujr.core.platform.abstraction.config.UlConfig;
import net.janrupf.ujr.core.platform.abstraction.config.UlFaceWinding;
import net.janrupf.ujr.core.platform.abstraction.config.UlFontHinting;

/**
 * Helper class to create a new {@link UlConfig} instance with a fluent API.
 */
public class UltralightConfigBuilder extends UlConfig {
    /**
     * Creates a new {@link UltralightConfigBuilder} instance with the default configuration.
     */
    public UltralightConfigBuilder() {
        super();
    }

    /**
     * Sets the cache path.
     * <p>
     * See {@link UlConfig#cachePath} for more information.
     *
     * @param cachePath the cache path to use
     * @return this
     */
    public UltralightConfigBuilder cachePath(String cachePath) {
        this.cachePath = cachePath;
        return this;
    }

    /**
     * Sets the resource path prefix.
     * <p>
     * See {@link UlConfig#resourcePathPrefix} for more information.
     *
     * @param resourcePathPrefix the resource path prefix to use
     * @return this
     */
    public UltralightConfigBuilder resourcePathPrefix(String resourcePathPrefix) {
        this.resourcePathPrefix = resourcePathPrefix;
        return this;
    }

    /**
     * Sets the face winding.
     * <p>
     * See {@link UlConfig#faceWinding} for more information.
     *
     * @param faceWinding the face winding to use
     * @return this
     */
    public UltralightConfigBuilder faceWinding(UlFaceWinding faceWinding) {
        this.faceWinding = faceWinding;
        return this;
    }

    /**
     * Sets the font hinting.
     * <p>
     * See {@link UlConfig#fontHinting} for more information.
     *
     * @param fontHinting the font hinting to use
     * @return this
     */
    public UltralightConfigBuilder fontHinting(UlFontHinting fontHinting) {
        this.fontHinting = fontHinting;
        return this;
    }

    /**
     * Sets the font gamma.
     * <p>
     * See {@link UlConfig#fontGamma} for more information.
     *
     * @param fontGamma the font gamma to use
     * @return this
     */
    public UltralightConfigBuilder fontGamma(double fontGamma) {
        this.fontGamma = fontGamma;
        return this;
    }

    /**
     * Sets the user stylesheet.
     * <p>
     * See {@link UlConfig#userStylesheet} for more information.
     *
     * @param userStylesheet the user stylesheet to use
     * @return this
     */
    public UltralightConfigBuilder userStylesheet(String userStylesheet) {
        this.userStylesheet = userStylesheet;
        return this;
    }

    /**
     * Sets the force repaint flag.
     * <p>
     * See {@link UlConfig#forceRepaint} for more information.
     *
     * @param forceRepaint the force repaint flag to use
     * @return this
     */
    public UltralightConfigBuilder forceRepaint(boolean forceRepaint) {
        this.forceRepaint = forceRepaint;
        return this;
    }

    /**
     * Sets the animation timer delay.
     * <p>
     * See {@link UlConfig#animationTimerDelay} for more information.
     *
     * @param animationTimerDelay the animation timer delay to use
     * @return this
     */
    public UltralightConfigBuilder animationTimerDelay(double animationTimerDelay) {
        this.animationTimerDelay = animationTimerDelay;
        return this;
    }

    /**
     * Sets the scroll timer delay.
     * <p>
     * See {@link UlConfig#scrollTimerDelay} for more information.
     *
     * @param scrollTimerDelay the scroll timer delay to use
     * @return this
     */
    public UltralightConfigBuilder scrollTimerDelay(double scrollTimerDelay) {
        this.scrollTimerDelay = scrollTimerDelay;
        return this;
    }

    /**
     * Sets the recycle delay.
     * <p>
     * See {@link UlConfig#recycleDelay} for more information.
     *
     * @param recycleDelay the recycle delay to use
     * @return this
     */
    public UltralightConfigBuilder recycleDelay(double recycleDelay) {
        this.recycleDelay = recycleDelay;
        return this;
    }

    /**
     * Sets the memory cache size.
     * <p>
     * See {@link UlConfig#memoryCacheSize} for more information.
     *
     * @param memoryCacheSize the memory cache size to use
     * @return this
     */
    public UltralightConfigBuilder memoryCacheSize(long memoryCacheSize) {
        this.memoryCacheSize = memoryCacheSize;
        return this;
    }

    /**
     * Sets the page cache size.
     * <p>
     * See {@link UlConfig#pageCacheSize} for more information.
     *
     * @param pageCacheSize the page cache size to use
     * @return this
     */
    public UltralightConfigBuilder pageCacheSize(long pageCacheSize) {
        this.pageCacheSize = pageCacheSize;
        return this;
    }

    /**
     * Sets the overwritten ram size.
     * <p>
     * See {@link UlConfig#overrideRamSize} for more information.
     *
     * @param overrideRamSize the ram size to use
     * @return this
     */
    public UltralightConfigBuilder overrideRamSize(long overrideRamSize) {
        this.overrideRamSize = overrideRamSize;
        return this;
    }

    /**
     * Sets the minimum large heap size.
     * <p>
     * See {@link UlConfig#minLargeHeapSize} for more information.
     *
     * @param minLargeHeapSize the minimum large heap size to use
     * @return this
     */
    public UltralightConfigBuilder minLargeHeapSize(long minLargeHeapSize) {
        this.minLargeHeapSize = minLargeHeapSize;
        return this;
    }

    /**
     * Sets the minimum small heap size.
     * <p>
     * See {@link UlConfig#minSmallHeapSize} for more information.
     *
     * @param minSmallHeapSize the minimum small heap size to use
     * @return this
     */
    public UltralightConfigBuilder minSmallHeapSize(long minSmallHeapSize) {
        this.minSmallHeapSize = minSmallHeapSize;
        return this;
    }

    /**
     * Sets the number of render threads.
     * <p>
     * See {@link UlConfig#numRenderThreads} for more information.
     *
     * @param numRenderThreads the number of render threads to use
     * @return this
     */
    public UltralightConfigBuilder numRenderThreads(long numRenderThreads) {
        this.numRenderThreads = numRenderThreads;
        return this;
    }

    /**
     * Sets the maximum update time.
     * <p>
     * See {@link UlConfig#maxUpdateTime} for more information.
     *
     * @param maxUpdateTime the maximum update time to use
     * @return this
     */
    public UltralightConfigBuilder maxUpdateTime(double maxUpdateTime) {
        this.maxUpdateTime = maxUpdateTime;
        return this;
    }

    /**
     * Sets the bitmap alignment.
     * <p>
     * See {@link UlConfig#bitmapAlignment} for more information.
     *
     * @param bitmapAlignment the bitmap alignment to use
     * @return this
     */
    public UltralightConfigBuilder bitmapAlignment(long bitmapAlignment) {
        this.bitmapAlignment = bitmapAlignment;
        return this;
    }

    /**
     * Finishes building the configuration.
     * <p>
     * This method is a no-op and is only present for consistency with the other builders.
     *
     * @return this
     */
    public UlConfig build() {
        return this;
    }
}
