package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.core.platform.abstraction.UlResourceProvider;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class JNIUlResourceProvider implements UlResourceProvider {
    @Override
    public URI getResource(String path) {
        try {
            URL url = getClass().getResource("/META-INF/resources/pkg/" + path);

            if (url == null) {
                return null;
            }

            return url.toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Failed to convert URL to URI", e);
        }
    }
}
