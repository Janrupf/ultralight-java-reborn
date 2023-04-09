package net.janrupf.ujr.core.platform;

/**
 * Features that are supported by the platform the library is running on.
 */
public enum PlatformFeatures {
    /**
     * The platform supports symbolic links, and they're usage us usually not restricted.
     * <p>
     * On Windows this for example won't be set, as symbolic links are only supported with elevated
     * privileges or developer mode enabled.
     */
    SYMBOLIC_LINKS,
}
