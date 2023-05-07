package net.janrupf.ujr.api.bitmap;

import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.api.surface.UltralightSurfaceFactory;
import net.janrupf.ujr.core.platform.abstraction.UlBitmapSurface;
import net.janrupf.ujr.core.platform.abstraction.UlBitmapSurfaceFactoryProvider;
import net.janrupf.ujr.core.util.ApiProvider;

/**
 * An implementation of {@link UltralightSurfaceFactory} that creates {@link UltralightBitmapSurface}s.
 */
public class UltralightBitmapSurfaceFactory implements UltralightSurfaceFactory {
    private static final ApiProvider<UlBitmapSurfaceFactoryProvider> FACTORY_PROVIDER =
            new ApiProvider<>(UlBitmapSurfaceFactoryProvider.class);

    private static UltralightBitmapSurfaceFactory instance;

    /**
     * Retrieves the singleton instance of this factory.
     * <p>
     * This method will return null if the current platform does not support the bitmap surface factory.
     *
     * @return the singleton instance of this factory, or null if the current platform does
     * not support the bitmap surface factory
     */
    public static UltralightBitmapSurfaceFactory getInstance() {
        if (instance == null) {
            UlBitmapSurfaceFactoryProvider provider = FACTORY_PROVIDER.tryProvide();
            if (provider == null) {
                return null;
            }

            instance = new UltralightBitmapSurfaceFactory(provider.getBitmapSurfaceFactory());
        }

        return instance;
    }

    private final UltralightSurfaceFactory impl;

    private UltralightBitmapSurfaceFactory(UltralightSurfaceFactory impl) {
        this.impl = impl;
    }

    @Override
    public UltralightBitmapSurface createSurface(long width, long height) {
        return new UltralightBitmapSurface((UlBitmapSurface) impl.createSurface(width, height));
    }

    @Override
    public void destroySurface(UltralightSurface surface) {
        impl.destroySurface(surface);
    }
}
