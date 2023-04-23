package net.janrupf.ujr.example.full;

import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.api.util.UltralightBuffer;

import java.io.IOException;

public class FilesystemBridge implements UltralightFilesystem {
    @Override
    public boolean fileExists(String path) {
        return false;
    }

    @Override
    public String getFileMimeType(String path) {
        return null;
    }

    @Override
    public String getFileCharset(String path) {
        return null;
    }

    @Override
    public UltralightBuffer openFile(String path) throws IOException {
        return null;
    }
}
