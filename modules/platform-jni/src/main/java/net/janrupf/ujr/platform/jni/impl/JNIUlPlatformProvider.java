package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.config.UlViewConfig;
import net.janrupf.ujr.api.event.*;
import net.janrupf.ujr.api.logger.UltralightLogLevel;
import net.janrupf.ujr.core.platform.abstraction.UlPlatform;
import net.janrupf.ujr.core.platform.abstraction.UlPlatformProvider;
import net.janrupf.ujr.api.config.UlConfig;
import net.janrupf.ujr.api.config.UlFaceWinding;
import net.janrupf.ujr.api.config.UlFontHinting;
import net.janrupf.ujr.platform.jni.ffi.NativeAccessOther;

// Below is a list of classes for which it was not worth creating wrappers,
// so instead they are directly targeted with @NativeAccessOther
@NativeAccessOther({
        // We need native access to instances of Ul[View]Config and related classes
        UlConfig.class,
        UlViewConfig.class,
        UlFaceWinding.class,
        UlFontHinting.class,
        UltralightLogLevel.class,

        // We also need access to event classes
        UlKeyEvent.class,
        UlKeyEventType.class,
        UlKeyEventModifiers.class,
        UlMouseEvent.class,
        UlMouseEventType.class,
        UlMouseButton.class,
        UlScrollEvent.class,
        UlScrollEventType.class,
})
public class JNIUlPlatformProvider implements UlPlatformProvider {
    private JNIUlPlatform platform;

    @Override
    public UlPlatform instance() {
        if (platform == null) {
            // Not cached yet, so we need to retrieve the native instance
            platform = nativeInstance();
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
    private native JNIUlPlatform nativeInstance();
}
