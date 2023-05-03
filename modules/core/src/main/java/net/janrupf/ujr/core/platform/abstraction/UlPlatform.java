package net.janrupf.ujr.core.platform.abstraction;

import net.janrupf.ujr.api.clipboard.UltralightClipboard;
import net.janrupf.ujr.api.config.UlConfig;
import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.api.surface.UltralightSurfaceFactory;

public interface UlPlatform {
    void setConfig(UlConfig config);

    void usePlatformFontLoader();

    void setLogger(UltralightLogger logger);

    UltralightLogger getLogger();

    void setFilesystem(UltralightFilesystem filesystem);

    UltralightFilesystem getFilesystem();

    void setClipboard(UltralightClipboard clipboard);

    UltralightClipboard getClipboard();

    void setSurfaceFactory(UltralightSurfaceFactory surfaceFactory);

    UltralightSurfaceFactory surfaceFactory();

    UlRenderer createRenderer();
}
