package net.janrupf.ujr.core.platform.option.std;

import net.janrupf.ujr.core.exception.UltralightJavaRebornRuntimeException;
import net.janrupf.ujr.core.platform.PlatformIdentification;
import net.janrupf.ujr.core.platform.option.PlatformEnvironmentOption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A collection of standard platform options.
 */
public final class CommonPlatformOptions implements PlatformEnvironmentOption.PreparationHook {
    private Path temporaryDirectory; // If null, will be automatically determined and created
    private Path persistentDirectory; // If null, will default to the temporary directory

    private PlatformIdentification platformIdentification; // If null, will be auto detected

    /**
     * Creates the common platform options and sets the default values.
     */
    public CommonPlatformOptions() {
        this.temporaryDirectory = null;
        this.persistentDirectory = null;
    }

    /**
     * Sets the temporary directory.
     * <p>
     * This directory will be used to save things such as shared libraries, cache's and so on.
     *
     * @param temporaryDirectory the temporary directory to use
     * @return this
     */
    public CommonPlatformOptions temporaryDirectory(Path temporaryDirectory) {
        this.temporaryDirectory = temporaryDirectory;
        return this;
    }

    /**
     * Retrieves the temporary directory.
     *
     * @return the temporary directory
     */
    public Path temporaryDirectory() {
        return temporaryDirectory;
    }

    /**
     * Sets the persistent directory.
     * <p>
     * This directory will be used to save persistent data such as cookies, local storage and so on.
     * <p>
     * If not set or set to null, the temporary directory will be used instead.
     *
     * @param persistentDirectory the persistent directory to use
     * @return this
     */
    public CommonPlatformOptions persistentDirectory(Path persistentDirectory) {
        this.persistentDirectory = persistentDirectory;
        return this;
    }

    /**
     * Retrieves the persistent directory.
     *
     * @return the persistent directory
     */
    public Path persistentDirectory() {
        return persistentDirectory;
    }

    /**
     * Sets the platform identification.
     * <p>
     * You usually don't want to call this, as the correct value will be automatically detected.
     * If this ever fails, you can manually set the value, but please report the issue.
     *
     * @param platformIdentification the platform identification to use
     * @return this
     */
    public CommonPlatformOptions platformIdentification(PlatformIdentification platformIdentification) {
        this.platformIdentification = platformIdentification;
        return this;
    }

    /**
     * Retrieves the platform identification.
     *
     * @return the platform identification
     */
    public PlatformIdentification platformIdentification() {
        return platformIdentification;
    }

    @Override
    public void prepare() {
        if (this.temporaryDirectory == null) {
            try {
                this.temporaryDirectory = Files.createTempDirectory("ujr");
            } catch (IOException e) {
                throw new UltralightJavaRebornRuntimeException("Failed to create temporary directory", e);
            }
        }

        if (this.persistentDirectory == null) {
            // Save data in the temporary directory by default
            this.persistentDirectory = this.temporaryDirectory;
        }

        if (this.platformIdentification == null) {
            this.platformIdentification = PlatformIdentification.detect();
        }
    }
}
