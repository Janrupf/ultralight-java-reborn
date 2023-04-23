package net.janrupf.ujr.api;

import net.janrupf.ujr.core.platform.abstraction.UlResourceProvider;
import net.janrupf.ujr.core.util.ApiProvider;

import java.net.URI;

/**
 * Helper to provide quick access to the bundled Ultralight resources.
 */
public class UltralightResources {
    private static final ApiProvider<UlResourceProvider> API_PROVIDER = new ApiProvider<>(UlResourceProvider.class);

    private UltralightResources() {
        throw new UnsupportedOperationException("This class may not be instantiated");
    }

    /**
     * Retrieves an Ultralight resource.
     *
     * @param path the path of the resource
     * @return the resource as a URI, or null, if the resource does not exist or no resource provider is available
     */
    public static URI getResource(String path) {
        UlResourceProvider provider = API_PROVIDER.tryProvide();
        if (provider == null) {
            return null;
        }

        return provider.getResource(path);
    }
}
