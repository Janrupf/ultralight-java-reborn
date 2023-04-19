package net.janrupf.ujr.core.platform.abstraction.config;

import java.lang.annotation.Native;

/**
 * Configuration settings for Ultralight.
 * <p>
 * This is intended to be implemented by users and defined before creating the Renderer.
 * See {@link net.janrupf.ujr.core.platform.abstraction.UlPlatform#setConfig(UlConfig)}
 */
public class UlConfig {
    /**
     * The file path to a writable directory that will be used to store cookies, cached resources,
     * and other persistent data.
     * <p>
     * Files are only written to disk when using a persistent Session (see Renderer::CreateSession).
     */
    @Native
    public String cachePath;

    /**
     * The library loads bundled resources (eg, cacert.pem and other localized resources) from the
     * FileSystem API (eg, {@code file:///resources/cacert.pem}). You can customize the prefix to use when
     * loading resource URLs by modifying this setting.
     */
    @Native
    public String resourcePathPrefix = "resources/";

    /**
     * The winding order for front-facing triangles. See {@link UlFaceWinding}
     * <p>
     * Note: This is only used when the GPU renderer is enabled.
     */
    @Native
    public UlFaceWinding faceWinding = UlFaceWinding.COUNTER_CLOCKWISE;

    /**
     * The font hinting mode to use when rendering text. See {@link UlFontHinting}
     */
    @Native
    public UlFontHinting fontHinting = UlFontHinting.NORMAL;

    /**
     * The gamma to use when compositing font glyphs, change this value to adjust contrast (Adobe
     * and Apple prefer 1.8, others may prefer 2.2).
     */
    @Native
    public double fontGamma = 1.8;

    /**
     * Default user stylesheet. You should set this to your own custom CSS string to define default
     * styles for various DOM elements, scrollbars, and platform input widgets.
     */
    @Native
    public String userStylesheet = "";

    /**
     * Whether or not we should continuously repaint any Views or compositor layers, regardless if
     * they are dirty or not. This is mainly used to diagnose painting/shader issues.
     */
    @Native
    public boolean forceRepaint = false;

    /**
     * When a CSS animation is active, the amount of time (in seconds) to wait before triggering
     * another repaint. Default is 60 Hz.
     */
    @Native
    public double animationTimerDelay = 1.0 / 60.0;

    /**
     * When a smooth scroll animation is active, the amount of time (in seconds) to wait before
     * triggering another repaint. Default is 60 Hz.
     */
    @Native
    public double scrollTimerDelay = 1.0 / 60.0;

    /**
     * The amount of time (in seconds) to wait before running the recycler (will attempt to return
     * excess memory back to the system).
     */
    @Native
    public double recycleDelay = 4.0;

    /**
     * Size of WebCore's memory cache in bytes.
     *
     * @implNote You should increase this if you anticipate handling pages with large resources, Safari
     * typically uses 128+ MiB for its cache.
     */
    @Native
    public long memoryCacheSize = 64 * 1024 * 1024;

    /**
     * Number of pages to keep in the cache. Defaults to 0 (none).
     *
     * @implNote Safari typically caches about 5 pages and maintains an on-disk cache to support
     * typical web-browsing activities. If you increase this, you should probably increase
     * the memory cache size as well.
     */
    @Native
    public long pageCacheSize = 0;

    /**
     * JavaScriptCore tries to detect the system's physical RAM size to set reasonable allocation
     * limits. Set this to anything other than 0 to override the detected value. Size is in bytes.
     * <p>
     * This can be used to force JavaScriptCore to be more conservative with its allocation strategy
     * (at the cost of some performance).
     */
    @Native
    public long overrideRamSize = 0;

    /**
     * The minimum size of large VM heaps in JavaScriptCore. Set this to a lower value to make these
     * heaps start with a smaller initial value.
     */
    @Native
    public long minLargeHeapSize = 32 * 1024 * 1024;

    /**
     * The minimum size of small VM heaps in JavaScriptCore. Set this to a lower value to make these
     * heaps start with a smaller initial value.
     */
    @Native
    public long minSmallHeapSize = 1024 * 1024;

    /**
     * The number of threads to use in the Renderer (for parallel painting on the CPU, etc.).
     * <p>
     * You can set this to a certain number to limit the number of threads to spawn.
     *
     * @implNote If this value is 0 (the default), the number of threads will be determined at runtime
     * using the following formula:
     * {@code max(Runtime.getRuntime().availableProcessors() - 1, 1)}
     */
    @Native
    public long numRendererThreads = 0;

    // TODO: Replace Renderer::Update with a javadoc link once this API has a Java binding.
    /**
     * The max amount of time (in seconds) to allow repeating timers to run during each call to
     * Renderer::Update. The library will attempt to throttle timers and/or reschedule work if this
     * time budget is exceeded.
     */
    @Native
    public double maxUpdateTime = 1.0 / 200.0;

    /**
     * The alignment (in bytes) of the BitmapSurface when using the CPU renderer.
     * <p>
     * The underlying bitmap associated with each BitmapSurface will have row_bytes padded to reach
     * this alignment.
     * <p>
     * Aligning the bitmap helps improve performance when using the CPU renderer. Determining the
     * proper value to use depends on the CPU architecture and max SIMD instruction set used.
     * <p>
     * We generally target the 128-bit SSE2 instruction set across most PC platforms so '16' is
     * a safe value to use.
     * <p>
     * You can set this to '0' to perform no padding (row_bytes will always be width * 4) at a
     * slight cost to performance.
     */
    @Native
    public long bitmapAlignment = 16;
}
