package net.janrupf.ujr.platform.jni;

import net.janrupf.ujr.core.platform.option.PlatformEnvironmentOptionContainer;
import net.janrupf.ujr.core.platform.provider.PlatformEnvironmentProvider;
import net.janrupf.ujr.core.platform.provider.PlatformEnvironmentProviderFactory;

/**
 * Provider factory for the JNI platform provider.
 * <p>
 * This class is provided as service in META-INF/services.
 */
public class UJRJniPlatformProviderFactory implements PlatformEnvironmentProviderFactory {
    @Override
    public PlatformEnvironmentProvider create(PlatformEnvironmentOptionContainer options) {
        return new UJRJniPlatformProvider(options);
    }
}
