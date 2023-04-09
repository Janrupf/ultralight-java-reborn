package net.janrupf.ujr.core;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The very core entry point of this library, provides the required methods for setting
 * everything up.
 */
public class UltralightJavaRebornConfigurator {
    private Path binDir;

    /**
     * Constructs the library instance and initializes sensible (if possible) defaults.
     */
    public UltralightJavaRebornConfigurator() {
        this.binDir = Paths.get(".");
    }

    /**
     * Sets the directory to load Ultralight binaries from.
     * <p>
     * This directory should include all the required DLL's, this library will then
     * load them in the correct order.
     *
     * @param binDir the directory to load the binaries from
     * @return this
     */
    public UltralightJavaRebornConfigurator binDir(Path binDir) {
        this.binDir = binDir;
        return this;
    }
}
