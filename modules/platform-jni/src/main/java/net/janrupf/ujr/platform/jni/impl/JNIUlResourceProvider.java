package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.core.platform.PlatformIdentification;
import net.janrupf.ujr.core.platform.abstraction.UlResourceProvider;
import net.janrupf.ujr.platform.jni.bundled.BundledResources;
import net.janrupf.ujr.platform.jni.bundled.HashedResource;

import java.net.URI;

public class JNIUlResourceProvider implements UlResourceProvider {
    private final PlatformIdentification platformIdentification;
    private final BundledResources bundledResources;

    public JNIUlResourceProvider(PlatformIdentification platformIdentification, BundledResources bundledResources) {
        this.platformIdentification = platformIdentification;
        this.bundledResources = bundledResources;
    }


    @Override
    public URI getResource(String path) {
        return bundledResources.getForPlatform(platformIdentification)
                .stream()
                .filter(nativeResource -> nativeResource.names().contains(path))
                .map(HashedResource::bundledLocation)
                .findFirst()
                .orElse(null);
    }
}
