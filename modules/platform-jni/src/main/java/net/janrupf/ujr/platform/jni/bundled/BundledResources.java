package net.janrupf.ujr.platform.jni.bundled;

import net.janrupf.ujr.core.exception.UltralightJavaRebornRuntimeException;
import net.janrupf.ujr.core.platform.PlatformIdentification;
import net.janrupf.ujr.core.platform.UnsupportedPlatformEnvironmentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * This class is used to access the bundled natives.
 */
public class BundledResources {
    private static final String BUNDLED_RESOURCES_PREFIX = "META-INF/resources/net.janrupf.ujr/";

    private final Map<String, List<HashedResource>> bundled;

    public BundledResources() {
        this.bundled = loadBundledResources();
    }

    /**
     * Determines whether the given platform is supported by the bundled natives.
     *
     * @param identification the platform identification
     * @return true if the platform is supported, false otherwise
     */
    public boolean supports(PlatformIdentification identification) {
        String platformKey = platformKey(identification);
        return bundled.containsKey(platformKey);
    }

    private String platformKey(PlatformIdentification identification) {
        String osName = identification.getName().toLowerCase();
        if (osName.equals("windows")) {
            osName = "win"; // Ultralight uses "win" as the platform name
        }

        return osName + "-" + identification.getArch().toLowerCase();
    }

    /**
     * Retrieves the bundled resources for the given platform.
     *
     * @param identification the platform identification
     * @return the bundled natives for the given platform
     */
    public List<HashedResource> getForPlatform(PlatformIdentification identification) {
        String platformKey = platformKey(identification);
        List<HashedResource> natives = bundled.get(platformKey);

        if (natives == null) {
            throw new UnsupportedPlatformEnvironmentException("No bundled natives for platform " + platformKey);
        }

        return natives;
    }

    /**
     * Reads the internal metadata file and returns a map of all bundled natives.
     *
     * @return a map of all bundled natives
     */
    private static Map<String, List<HashedResource>> loadBundledResources() {
        Map<String, List<HashedResource>> bundled = new HashMap<>();

        // We use the class loader of this class to load the bundled resources
        ClassLoader thisLoader = BundledResources.class.getClassLoader();

        Enumeration<URL> nativesMetadataFiles;
        try {
            nativesMetadataFiles = thisLoader.getResources(BUNDLED_RESOURCES_PREFIX + "ultralight-natives.dat");
        } catch (IOException e) {
            throw new UltralightJavaRebornRuntimeException("Failed to query classloader for bundled resources metadata", e);
        }

        // Index all bundled native files
        while (nativesMetadataFiles.hasMoreElements()) {
            URL nativesMetadataFile = nativesMetadataFiles.nextElement();

            try (
                    InputStream stream = nativesMetadataFile.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty()) {
                        // Empty lines are allowed and ignored
                        continue;
                    }

                    String[] parts = line.split(" ");
                    if (parts.length < 2) {
                        // Invalid line, this means the meta data was generated incorrectly
                        throw new IllegalStateException("Invalid line in bundled natives meta data: " + line);
                    }

                    String system = parts[0];
                    String type = parts[1];

                    if (!type.equals("library") && !type.equals("resource")) {
                        throw new IllegalStateException("Invalid type in bundled natives meta data: " + type);
                    }

                    if (parts.length != 4) {
                        // Invalid line, this means the meta data was generated incorrectly
                        throw new IllegalStateException("Invalid line in bundled natives meta data: " + line);
                    }

                    String hash = parts[2];
                    String[] names = parts[3].split(",");

                    if (names.length < 1) {
                        // Invalid line, this means the meta data was generated incorrectly
                        throw new IllegalStateException(
                                "Invalid line in bundled natives meta data, no names specified: " + line);
                    }

                    URL resourceUrl = thisLoader.getResource(BUNDLED_RESOURCES_PREFIX + hash);
                    if (resourceUrl == null) {
                        throw new IllegalStateException("Missing bundled resource: " + hash);
                    }

                    List<HashedResource> natives = bundled.computeIfAbsent(system, k -> new ArrayList<>());
                    natives.add(new HashedResource(
                            resourceUrl.toURI(),
                            hash,
                            type,
                            Arrays.asList(names)
                    ));
                }
            } catch (IOException e) {
                throw new UltralightJavaRebornRuntimeException("Failed to read bundled resources metadata", e);
            } catch (URISyntaxException e) {
                throw new UltralightJavaRebornRuntimeException("Failed to parse bundled resources metadata URI", e);
            }
        }

        return bundled;
    }
}
