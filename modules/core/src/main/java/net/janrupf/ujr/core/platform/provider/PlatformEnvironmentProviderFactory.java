package net.janrupf.ujr.core.platform.provider;

import net.janrupf.ujr.core.platform.option.PlatformEnvironmentOptionContainer;

/**
 * Factory for generating instances of {@link PlatformEnvironmentProvider}.
 * <p>
 * This is a Java service class, which can be used to create platform environment providers
 * with specific options.
 */
public interface PlatformEnvironmentProviderFactory {
    /**
     * Creates a new instance of the platform environment provider.
     *
     * @param options the options to pass to the environment provider, never null
     * @return the new instance
     */
    PlatformEnvironmentProvider create(PlatformEnvironmentOptionContainer options);
}
