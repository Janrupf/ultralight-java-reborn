package net.janrupf.ujr.core.platform.provider;

import net.janrupf.ujr.core.platform.InvalidPlatformEnvironmentException;
import net.janrupf.ujr.core.platform.UnsupportedPlatformApiException;
import net.janrupf.ujr.core.platform.UnsupportedPlatformEnvironmentException;

/**
 * Abstraction over the loading mechanism of platform specific libraries.
 */
public interface PlatformEnvironmentProvider {
    /**
     * Determines whether this environment is actually supported by this provider.
     * <p>
     * This is used by the automatic detection to differentiate between different providers
     * and decide which platform to load.
     * <p>
     * Once a platform provider returns {@code true}, it must <b>not</b> throw a
     * {@link UnsupportedPlatformEnvironmentException} later!
     *
     * @return true, if this platform is supported, false otherwise
     */
    boolean supportsThisEnvironment();

    /**
     * Performs the actual loading of the platform environment.
     * <p>
     * This method may throw an {@link InvalidPlatformEnvironmentException} if the platform
     * environment can not be loaded due to some error.
     * <p>
     * This method may throw an {@link UnsupportedPlatformEnvironmentException} if the platform
     * environment can not be loaded due to the platform not being supported. However, this is
     * only allowed if {@link #supportsThisEnvironment()} returns {@code false}.
     * <p>
     * This is the chance to check for the presence of required libraries and throw an
     * exception if they are missing. Once this method returns it is assumed that the environment
     * was loaded successfully.
     *
     * @throws UnsupportedPlatformEnvironmentException if the current environment is not supported
     * @throws InvalidPlatformEnvironmentException     if the platform environment can not be loaded
     */
    void performLoading() throws InvalidPlatformEnvironmentException;

    /**
     * Queries the provider for a specific API. If the provider does not support the API, it
     * should return {@code null}. Exceptions are only to be thrown in case of an error.
     *
     * @param interfaceClass the interface class of the API
     * @param <T>            the type of the API
     * @return the API implementation or {@code null} if the provider does not support it
     */
    <T> T tryProvideApi(Class<T> interfaceClass);

    /**
     * Instructs the provider to delete temporary files and perform other resource cleanup.
     * <p>
     * Please note that the cleanup operation may silently fail if the provider does not support
     * it or if it is not possible to perform the cleanup due to OS limitations.
     */
    void cleanup();
}
