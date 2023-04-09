package net.janrupf.ujr.platform.jni;

import net.janrupf.ujr.core.platform.InvalidPlatformEnvironmentException;
import net.janrupf.ujr.core.platform.option.PlatformEnvironmentOptionContainer;
import net.janrupf.ujr.core.platform.option.std.CommonPlatformOptions;
import net.janrupf.ujr.core.platform.provider.PlatformEnvironmentProvider;
import net.janrupf.ujr.platform.jni.bundled.BundledNatives;

/**
 * The JNI platform provider.
 * <p>
 * This provider is responsible for loading the JNI based Ultralight library and providing the JNI
 * Ultralight API.
 */
public class UJRJniPlatformProvider implements PlatformEnvironmentProvider {
    private final CommonPlatformOptions commonPlatformOptions;
    private final BundledNatives bundledNatives;

    UJRJniPlatformProvider(PlatformEnvironmentOptionContainer options) {
        this.commonPlatformOptions = options.require(CommonPlatformOptions.class); // Always present
        this.bundledNatives = new BundledNatives();
    }

    @Override
    public boolean supportsThisEnvironment() {
        return bundledNatives.supports(this.commonPlatformOptions.platformIdentification());
    }

    @Override
    public void performLoading() throws InvalidPlatformEnvironmentException {

    }

    @Override
    public <T> T tryProvideApi(Class<T> interfaceClass) {
        return null;
    }
}
