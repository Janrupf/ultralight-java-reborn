package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.config.UlViewConfig;
import net.janrupf.ujr.api.event.UlKeyEvent;
import net.janrupf.ujr.api.event.UlKeyEventModifiers;
import net.janrupf.ujr.api.event.UlKeyEventType;
import net.janrupf.ujr.api.logger.UltralightLogLevel;
import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.core.platform.abstraction.UlPlatformProvider;
import net.janrupf.ujr.api.config.UlConfig;
import net.janrupf.ujr.api.config.UlFaceWinding;
import net.janrupf.ujr.api.config.UlFontHinting;
import net.janrupf.ujr.platform.jni.ffi.NativeAccessOther;

@NativeAccessOther({
        UlConfig.class,
        UlViewConfig.class,
        UlFaceWinding.class,
        UlFontHinting.class,
        UltralightLogLevel.class,
        UlKeyEvent.class,
        UlKeyEventType.class,
        UlKeyEventModifiers.class,
}) // We need native access to instances of Ul[View]Config and related classes
public class JNIUlPlatformProvider implements UlPlatformProvider {
    private JNIUlPlatform platform;

    @Override
    public UlPlatform instance() {
        if (platform == null) {
            // Not cached yet, so we need to retrieve the native instance
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
