package net.janrupf.ujr.api;

import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.core.platform.abstraction.UlPlatformProvider;
import net.janrupf.ujr.api.config.UlConfig;
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
}
