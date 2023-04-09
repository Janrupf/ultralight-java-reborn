package net.janrupf.ujr.platform.jni;

import net.janrupf.ujr.core.platform.InvalidPlatformEnvironmentException;
import net.janrupf.ujr.core.platform.option.PlatformEnvironmentOptionContainer;
import net.janrupf.ujr.core.platform.provider.PlatformEnvironmentProvider;

/**
 * The JNI platform provider.
 * <p>
 * This provider is responsible for loading the JNI based Ultralight library and providing the JNI
 * Ultralight API.
 */
public class UJRJniPlatformProvider implements PlatformEnvironmentProvider {
    private final PlatformEnvironmentOptionContainer options;

    UJRJniPlatformProvider(PlatformEnvironmentOptionContainer options) {
        this.options = options;
    }

    @Override
    public boolean supportsThisEnvironment() {
        return false;
    }

    @Override
    public void performLoading() throws InvalidPlatformEnvironmentException {

    }

    @Override
    public <T> T tryProvideApi(Class<T> interfaceClass) {
        return null;
    }
}
