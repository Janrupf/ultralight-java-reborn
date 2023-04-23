package net.janrupf.ujr.core.platform.abstraction;

import java.net.URI;

/**
 * Platform environment interface for providing bundled Ultralight resources.
 */
public interface UlResourceProvider {
    /**
     * Retrieves an Ultralight resource.
     *
     * @param path the path of the resource
     * @return the resource as a URI, or null, if the resource does not exist
     */
    URI getResource(String path);
}
