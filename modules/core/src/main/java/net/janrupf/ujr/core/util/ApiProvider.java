package net.janrupf.ujr.core.util;

import net.janrupf.ujr.core.UltralightJavaReborn;
import net.janrupf.ujr.core.platform.UnsupportedPlatformApiException;

/**
 * This is a helper class meant for API libraries built on top of Ultralight Java Reborn.
 * <p>
 * The provider gives you access API implementations of the underlying platform based on
 * the currently active Ultralight Java Reborn instance. This is effectively a (cached)
 * shortcode to the {@link net.janrupf.ujr.core.platform.PlatformEnvironment#tryProvideApi(Class)}
 * mechanism.
 */
public class ApiProvider<T> {
    private final Class<T> apiClass;

    private final ThreadLocal<T> cached;

    /**
     * Initializes the API provider with the given API class.
     * <p>
     * The API is not yet requested from the platform environment. This is done lazily when
     * {@link #require()} or {@link #tryProvide()} is called.
     *
     * @param apiClass the API class to provide
     */
    public ApiProvider(Class<T> apiClass) {
        this.apiClass = apiClass;
        this.cached = new ThreadLocal<>();
    }

    /**
     * Retrieves the API implementation from the platform environment.
     *
     * @return the API implementation or null if the platform environment does not provide one
     */
    public T tryProvide() {
        T cached = this.cached.get();

        if (cached == null) {
            cached = UltralightJavaReborn.getActiveInstance().getPlatformEnvironment().tryProvideApi(apiClass);
            this.cached.set(cached);
        }

        return cached;
    }

    /**
     * Retrieves the API implementation from the platform environment.
     *
     * @return the API implementation
     * @throws UnsupportedPlatformApiException if the platform environment does not provide one
     */
    public T require() throws UnsupportedPlatformApiException {
        T cached = this.cached.get();

        if (cached == null) {
            cached = UltralightJavaReborn.getActiveInstance().getPlatformEnvironment().requireApi(apiClass);
            this.cached.set(cached);
        }

        return cached;
    }
}
