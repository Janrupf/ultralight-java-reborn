package net.janrupf.ujr.core.platform.abstraction;

public class UlConfig {
    /**
     * The file path to a writable directory that will be used to store cookies, cached resources,
     * and other persistent data.
     * <p>
     * Files are only written to disk when using a persistent Session (see Renderer::CreateSession).
     */
    public String cachePath;

    /**
     * The library loads bundled resources (eg, cacert.pem and other localized resources) from the
     * FileSystem API (eg, {@code file:///resources/cacert.pem}). You can customize the prefix to use when
     * loading resource URLs by modifying this setting.
     */
    public String resourcePathPrefix;
}
