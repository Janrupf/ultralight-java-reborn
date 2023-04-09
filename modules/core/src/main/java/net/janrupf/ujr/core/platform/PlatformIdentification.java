package net.janrupf.ujr.core.platform;

/**
 * Helper for detecting the platform the library is running on.
 */
public class PlatformIdentification {
    private final String name;
    private final String arch;

    /**
     * Creates a new platform identification.
     *
     * @param name the name of the platform the library is running on
     * @param arch the architecture of the platform the library is running on
     */
    public PlatformIdentification(String name, String arch) {
        this.name = name;
        this.arch = arch;
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
     * Detects the platform the library is running on.
     *
     * @return the platform the library is running on
     */
    public static PlatformIdentification detect() {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = remapOsArch(System.getProperty("os.arch").toLowerCase());

        if (osName.contains("win")) {
            return new PlatformIdentification("windows", osArch);
        } else if (osName.contains("mac")) {
            return new PlatformIdentification("macos", osArch);
        } else if (osName.contains("linux")) {
            return new PlatformIdentification("linux", osArch);
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return new PlatformIdentification("unix", osArch);
        } else {
            // Fallback, this will probably not work
            return new PlatformIdentification(osName, osArch);
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
