package net.janrupf.ujr.core.platform;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Helper for detecting the platform the library is running on.
 */
public class PlatformIdentification {
    private final String name;
    private final String arch;

    private final EnumSet<PlatformFeatures> supportedFeatures;

    /**
     * Creates a new platform identification.
     *
     * @param name the name of the platform the library is running on
     * @param arch the architecture of the platform the library is running on
     * @param supportedFeatures the features supported by the platform the library is running on
     */
    public PlatformIdentification(String name, String arch, EnumSet<PlatformFeatures> supportedFeatures) {
        this.name = name;
        this.arch = arch;
        this.supportedFeatures = supportedFeatures;
    }

    /**
     * Retrieves the name of the platform the library is running on.
     *
     * @return the name of the platform the library is running on
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the architecture of the platform the library is running on.
     *
     * @return the architecture of the platform the library is running on
     */
    public String getArch() {
        return arch;
    }

    /**
     * Retrieves the features supported by the platform the library is running on.
     *
     * @return the features supported by the platform the library is running on
     */
    public Set<PlatformFeatures> getSupportedFeatures() {
        return Collections.unmodifiableSet(supportedFeatures);
    }

    /**
     * Determines whether the platform the library is running on supports the given feature.
     *
     * @param feature the feature to check for
     * @return true if the feature is supported, false otherwise
     */
    public boolean supportsFeature(PlatformFeatures feature) {
        return supportedFeatures.contains(feature);
    }

    /**
     * Detects the platform the library is running on.
     *
     * @return the platform the library is running on
     */
    public static PlatformIdentification detect() {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = remapOsArch(System.getProperty("os.arch").toLowerCase());

        EnumSet<PlatformFeatures> supportedFeatures = EnumSet.noneOf(PlatformFeatures.class);

        if (osName.contains("win")) {
            return new PlatformIdentification("windows", osArch, supportedFeatures);
        } else if (osName.contains("mac")) {
            supportedFeatures.add(PlatformFeatures.SYMBOLIC_LINKS);
            return new PlatformIdentification("macos", osArch, supportedFeatures);
        } else if (osName.contains("linux")) {
            supportedFeatures.add(PlatformFeatures.SYMBOLIC_LINKS);
            return new PlatformIdentification("linux", osArch, supportedFeatures);
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            supportedFeatures.add(PlatformFeatures.SYMBOLIC_LINKS); // Pretty sure all unixes support symlinks
            return new PlatformIdentification("unix", osArch, supportedFeatures);
        } else {
            // Fallback, this will probably not work
            return new PlatformIdentification(osName, osArch, supportedFeatures);
        }
    }

    /**
     * Helper function to remap the os.arch property to the values used by the bundled natives.
     *
     * @param input the input value
     * @return the remapped value
     */
    private static String remapOsArch(String input) {
        switch (input) {
            case "amd64":
            case "x86_64":
                return "x64";
            case "i386":
                return "x86";
            default:
                return input;
        }
    }
}
