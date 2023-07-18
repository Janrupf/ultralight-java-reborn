package net.janrupf.ujr.api;

import net.janrupf.ujr.api.clipboard.UltralightClipboard;
import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.api.surface.UltralightSurfaceFactory;
import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.core.platform.abstraction.UlPlatformProvider;
import net.janrupf.ujr.api.config.UlConfig;
import net.janrupf.ujr.core.platform.abstraction.UlRenderer;
import net.janrupf.ujr.core.util.ApiProvider;

public class UltralightPlatform {
    // We need the provider API to get the native platform instance
    private static final ApiProvider<UlPlatformProvider> PROVIDER_API = new ApiProvider<>(UlPlatformProvider.class);

    // Cache the instance per thread, as only one can exist
    private static final ThreadLocal<UltralightPlatform> THREAD_INSTANCE = new ThreadLocal<>();

    // The underlying platform instance
    private final UlPlatform platform;

    /**
     * Retrieves the active Ultralight platform instance for the current thread.
     *
     * @return the active Ultralight platform instance
     */
    public static UltralightPlatform instance() {
        UltralightPlatform instance = THREAD_INSTANCE.get();
        if (instance != null) {
            return instance;
        }

        // If no instance is active, create a new one
        UlPlatform platform = PROVIDER_API.require().instance();
        instance = new UltralightPlatform(platform);

        THREAD_INSTANCE.set(instance);

        return instance;
    }

    /**
     * Creates a new Ultralight platform instance with the underlying platform provided by the
     * {@link UlPlatformProvider} API.
     *
     * @param platform the underlying platform
     */
    private UltralightPlatform(UlPlatform platform) {
        this.platform = platform;
    }

    /**
     * Sets the configuration for the platform.
     *
     * @param config the configuration
     * @throws IllegalArgumentException if the configuration is invalid
     */
    public void setConfig(UlConfig config) {
        platform.setConfig(config);
    }

    // TODO: get/set Font loader with Java objects

    /**
     * Sets the font loader to be the platform font loader.
     */
    public void usePlatformFontLoader() {
        platform.usePlatformFontLoader();
    }

    /**
     * Set the Logger (to handle error messages and debug output).
     *
     * @param logger the logger
     */
    public void setLogger(UltralightLogger logger) {
        platform.setLogger(logger);
    }

    /**
     * Get the Logger.
     *
     * @return the current logger, or {@code null}, if none is set
     */
    public UltralightLogger getLogger() {
        return platform.getLogger();
    }


    /**
     * Sets the filesystem to be used by Ultralight.
     *
     * @param filesystem the filesystem
     */
    public void setFilesystem(UltralightFilesystem filesystem) {
        platform.setFilesystem(filesystem);
    }

    /**
     * Retrieves the filesystem used by Ultralight.
     *
     * @return the filesystem, or {@code null}, if none is set
     */
    public UltralightFilesystem getFilesystem() {
        return platform.getFilesystem();
    }

    /**
     * Set the Clipboard (will be used for all clipboard operations)
     *
     * @param clipboard a user-defined Clipboard implementation
     */
    public void setClipboard(UltralightClipboard clipboard) {
        platform.setClipboard(clipboard);
    }

    /**
     * Retrieves the clipboard used by Ultralight.
     *
     * @return the clipboard, or {@code null}, if none is set
     */
    public UltralightClipboard getClipboard() {
        return platform.getClipboard();
    }

    // TODO: fix doc comment references

    /**
     * Set the {@link UltralightSurfaceFactory}.
     * <p>
     * This can be used to provide a platform-specific bitmap surface for View to paint into when
     * the CPU renderer is enabled. See {@link UltralightView#surface()}.
     * <p>
     * A default BitmapSurfaceFactory is defined if you never call this,  {@link UltralightView#surface()} can
     * be safely cast to {@link net.janrupf.ujr.api.bitmap.UltralightBitmapSurface}.
     *
     * @param surfaceFactory the surface factory to use
     */
    public void setSurfaceFactory(UltralightSurfaceFactory surfaceFactory) {
        platform.setSurfaceFactory(surfaceFactory);
    }

    /**
     * Retrieves the surface factory used by Ultralight.
     *
     * @return the surface factory, or {@code null}, if none is set
     */
    public UltralightSurfaceFactory surfaceFactory() {
        return platform.surfaceFactory();
    }

    /* package */ UlRenderer createRenderer() {
        return platform.createRenderer();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UltralightPlatform)) {
            return false;
        }

        return platform.equals(((UltralightPlatform) obj).platform);
    }

    @Override
    public int hashCode() {
        return platform.hashCode();
    }
}
