package net.janrupf.ujr.platform.jni.bundled;

import net.janrupf.ujr.core.exception.UltralightJavaRebornRuntimeException;
import net.janrupf.ujr.core.platform.PlatformIdentification;
import net.janrupf.ujr.core.platform.UnsupportedPlatformEnvironmentException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * This class is used to access the bundled natives.
 */
public class BundledNatives {
    private final Map<String, List<HashedNative>> bundled;

    public BundledNatives() {
        this.bundled = loadBundledNatives();
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
        return identification.getName().toLowerCase() + "-" + identification.getArch().toLowerCase();
    }

    /**
     * Retrieves the bundled natives for the given platform.
     *
     * @param identification the platform identification
     * @return the bundled natives for the given platform
     */
    public List<HashedNative> getNatives(PlatformIdentification identification) {
        String platformKey = platformKey(identification);
        List<HashedNative> natives = bundled.get(platformKey);

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
    private static Map<String, List<HashedNative>> loadBundledNatives() {
        Map<String, List<HashedNative>> bundled = new HashMap<>();

        try (
                InputStream inputStream = BundledNatives.class.getResourceAsStream(
                        "/META-INF/resources/native/meta.dat"
                );
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(inputStream, "Missing bundled natives meta data")
                ))
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

                if (!type.equals("library")) {
                    // We only support libraries for now
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

                URL resourceUrl = BundledNatives.class.getResource(
                        "/META-INF/resources/native/" + hash
                );
                if (resourceUrl == null) {
                    throw new IllegalStateException("Missing bundled native: " + hash);
                }

                List<HashedNative> natives = bundled.computeIfAbsent(system, k -> new ArrayList<>());
                natives.add(new HashedNative(
                        resourceUrl.toURI(),
                        hash,
                        type,
                        Arrays.asList(names)
                ));
            }
        } catch (Throwable e) {
            // This should NEVER happen, but if it does, we want to know about it.
            throw new UltralightJavaRebornRuntimeException("Failed to load bundled natives meta data", e);
        }

        return bundled;
    }
}
