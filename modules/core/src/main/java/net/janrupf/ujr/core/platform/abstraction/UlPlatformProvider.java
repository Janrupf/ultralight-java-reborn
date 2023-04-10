package net.janrupf.ujr.core.platform.abstraction;

/**
 * Platform environment interface for providing the underlying Ultralight Platform instance.
 */
public interface UlPlatformProvider {
    /**
     * Retrieves the underlying Ultralight Platform instance.
     *
     * @return the underlying Ultralight Platform instance
     */
    UlPlatform instance();
}
