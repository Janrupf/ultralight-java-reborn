package net.janrupf.ujr.example.full;

import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.api.util.UltralightBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class FilesystemBridge implements UltralightFilesystem {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean fileExists(String path) {
        LOGGER.debug("Checking if {} exists", path);
        return false;
    }

    @Override
    public String getFileMimeType(String path) {
        LOGGER.debug("Retrieving mime type of {}", path);
        return null;
    }

    @Override
    public String getFileCharset(String path) {
        LOGGER.debug("Retrieving charset of {}", path);
        return null;
    }

    @Override
    public UltralightBuffer openFile(String path) throws IOException {
        LOGGER.debug("Attempting to open {}", path);
        return null;
    }
}
