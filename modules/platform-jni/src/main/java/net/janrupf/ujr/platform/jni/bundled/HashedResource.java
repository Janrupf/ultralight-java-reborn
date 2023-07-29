package net.janrupf.ujr.platform.jni.bundled;

import java.net.URI;
import java.util.List;

public class HashedResource {
    private final URI bundledLocation;
    private final String hash;
    private final String type;
    private final List<String> names;

    /**
     * Creates a new hashed native.
     * <p>
     * A hashed native is a file required for the native libraries (or a library itself), that
     * is bundled with this java library. The hash is used to identify the native.
     *
     * @param bundledLocation the location of the native in the bundled jar
     * @param hash the hash of the native
     * @param type the type of the native
     * @param names the names of the native
     */
    public HashedResource(URI bundledLocation, String hash, String type, List<String> names) {
        this.bundledLocation = bundledLocation;
        this.hash = hash;
        this.type = type;
        this.names = names;
    }

    /**
     * Retrieves the location of the native in the bundled jar.
     *
     * @return the location of the native in the bundled jar
     */
    public URI bundledLocation() {
        return bundledLocation;
    }

    /**
     * Retrieves the hash of the native.
     *
     * @return the hash of the native
     */
    public String hash() {
        return hash;
    }

    /**
     * Retrieves the type of the native.
     *
     * @return the type of the native
     */
    public String type() {
        return type;
    }

    /**
     * Retrieves the names of the native.
     *
     * @return the names of the native
     */
    public List<String> names() {
        return names;
    }
}
