package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.core.platform.abstraction.UlPlatformProvider;

public class JNIUlPlatformProvider implements UlPlatformProvider {
    private JNIUlPlatform platform;

    @Override
    public UlPlatform instance() {
        if (platform == null) {
            // Not cached yes, so we need to retrieve the native instance
            platform = new JNIUlPlatform(nativeInstance());
        }

        return platform;
    }

    /**
     * Retrieves the native instance of the underlying Ultralight Platform.
     * <p>
     * This corresponds to the native function {@code ultralight::Platform::instance()}.
     *
     * @return the native instance of the underlying Ultralight Platform
     */
    private native long nativeInstance();
}
