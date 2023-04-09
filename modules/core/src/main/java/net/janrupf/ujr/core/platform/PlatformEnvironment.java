package net.janrupf.ujr.core.platform;

import net.janrupf.ujr.core.platform.option.PlatformEnvironmentOption;
import net.janrupf.ujr.core.platform.option.PlatformEnvironmentOptionContainer;
import net.janrupf.ujr.core.platform.option.std.CommonPlatformOptions;
import net.janrupf.ujr.core.platform.provider.PlatformEnvironmentProvider;
import net.janrupf.ujr.core.platform.provider.PlatformEnvironmentProviderFactory;

import java.util.Objects;
import java.util.ServiceLoader;

/**
 * This represents the environment the Ultralight Java Reborn is operating on.
 * <p>
 * The environment describes which libraries should be loaded, in which order and what
 * other initialization steps to perform.
 * <p>
 */
public class PlatformEnvironment {
    /**
     * Attempts to automatically load the environment.
     * <p>
     * Please note that this method may load additional libraries from the classpath. If this is a security
     * concern for you, use the {@link #loadWith} method.
     *
     * @return the loaded platform environment
     * @throws UnsupportedPlatformEnvironmentException if no suitable platform environment has been found
     * @throws InvalidPlatformEnvironmentException     if a platform was supported but failed to load due to some error
     */
    public static PlatformEnvironment load()
            throws UnsupportedPlatformEnvironmentException, InvalidPlatformEnvironmentException {
        return load(new PlatformEnvironmentOptionContainer());
    }

    /**
     * Attempts to automatically load the environment.
     * <p>
     * Please note that this method may load additional libraries from the classpath. If this is a security
     * concern for you, use the {@link #loadWith} method.
     * <p>
     * The options may be frozen already, but this is not required. If they are not frozen, they will be
     * frozen automatically.
     *
     * @param options the options to pass to the environment factories
     * @return the loaded platform environment
     * @throws UnsupportedPlatformEnvironmentException if no suitable platform environment has been found
     * @throws InvalidPlatformEnvironmentException     if a platform was supported but failed to load due to some error
     */
    public static PlatformEnvironment load(PlatformEnvironmentOptionContainer options)
            throws UnsupportedPlatformEnvironmentException, InvalidPlatformEnvironmentException {
        Objects.requireNonNull(options, "options must not be null");

        if (!options.has(CommonPlatformOptions.class)) {
            // Add the common options if they are not already present
            options.addOption(new PlatformEnvironmentOption<>(CommonPlatformOptions.class, new CommonPlatformOptions()));
        }

        // No further modifications to the options are allowed
        options.freeze();

        ServiceLoader<PlatformEnvironmentProviderFactory> loader = ServiceLoader.load(PlatformEnvironmentProviderFactory.class);

        for (PlatformEnvironmentProviderFactory factory : loader) {
            PlatformEnvironmentProvider provider = factory.create(options);

            if (provider.supportsThisEnvironment()) {
                // Found a provider that supports this environment, this concludes the search
                return loadWith(provider, options);
            }
        }

        throw new UnsupportedPlatformEnvironmentException("No suitable platform environment provider found");
    }

    /**
     * Loads a platform environment using a specific provider.
     *
     * @param provider the provider to use for loading
     * @throws UnsupportedPlatformEnvironmentException if the given provider does not support this environment
     * @throws InvalidPlatformEnvironmentException     if the provider supports this platform but failed to load
     */
    public static PlatformEnvironment loadWith(PlatformEnvironmentProvider provider)
            throws UnsupportedPlatformEnvironmentException, InvalidPlatformEnvironmentException {
        return loadWith(provider, new PlatformEnvironmentOptionContainer());
    }

    /**
     * Loads a platform environment using a specific provider.
     *
     * @param provider the provider to use for loading
     * @param options  the options to pass to the environment factories
     * @return the loaded platform environment
     * @throws UnsupportedPlatformEnvironmentException if the given provider does not support this environment
     * @throws InvalidPlatformEnvironmentException     if the provider supports this platform but failed to load
     */
    public static PlatformEnvironment loadWith(
            PlatformEnvironmentProvider provider,
            PlatformEnvironmentOptionContainer options
    ) throws UnsupportedPlatformEnvironmentException, InvalidPlatformEnvironmentException {
        Objects.requireNonNull(options, "options must not be null");

        if (!options.has(CommonPlatformOptions.class)) {
            // Add the common options if they are not already present
            options.addOption(new PlatformEnvironmentOption<>(CommonPlatformOptions.class, new CommonPlatformOptions()));
        }

        options.freeze();

        // Load NOW
        provider.performLoading();

        return new PlatformEnvironment(provider);
    }

    private PlatformEnvironmentProvider provider;

    private PlatformEnvironment(PlatformEnvironmentProvider provider) {
        // provider is loaded already
        this.provider = provider;
    }

    /**
     * Queries the provider for a specific API. If the provider does not support the API, it
     * should return {@code null}. Exceptions are only to be thrown in case of an error.
     *
     * @param interfaceClass the interface class of the API
     * @param <T>            the type of the API
     * @return the API implementation or {@code null} if the provider does not support it
     */
    public <T> T tryProvideApi(Class<T> interfaceClass) {
        return provider.tryProvideApi(interfaceClass);
    }

    /**
     * Queries the provider for a specific API. If the provider does not support the API,
     * an exception is thrown.
     *
     * @param interfaceClass the interface class of the API
     * @param <T>            the type of the API
     * @return the API implementation
     * @throws UnsupportedPlatformApiException if the provider does not support the API
     */
    public <T> T requireApi(Class<T> interfaceClass) throws UnsupportedPlatformApiException {
        T api = tryProvideApi(interfaceClass);

        if (api == null) {
            throw new UnsupportedPlatformApiException(
                    "The platform environment does not support the API " + interfaceClass.getName());
        }

        return api;
    }

    /**
     * Instructs the provider to delete temporary files and perform other resource cleanup.
     * <p>
     * Please note that the cleanup operation may silently fail if the provider does not support
     * it or if it is not possible to perform the cleanup due to OS limitations.
     */
    public void cleanup() {
        provider.cleanup();
    }
}
