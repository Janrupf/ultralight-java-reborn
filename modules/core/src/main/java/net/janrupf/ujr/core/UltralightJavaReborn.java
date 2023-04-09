package net.janrupf.ujr.core;

import net.janrupf.ujr.core.platform.PlatformEnvironment;

/**
 * Main entry point for the Ultralight Java Reborn library.
 */
public class UltralightJavaReborn {
    private final PlatformEnvironment platformEnvironment;

    /**
     * Creates a new Ultralight Java Reborn instance.
     * <p>
     * While Java technically allows you to call this multiple times, doing so is not recommended at
     * the moment. This may change in the future.
     *
     * @param platformEnvironment the platform environment to use
     */
    public UltralightJavaReborn(PlatformEnvironment platformEnvironment) {
        this.platformEnvironment = platformEnvironment;
    }
}
