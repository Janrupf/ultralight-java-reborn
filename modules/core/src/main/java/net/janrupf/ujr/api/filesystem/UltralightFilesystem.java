package net.janrupf.ujr.api.filesystem;

import net.janrupf.ujr.api.util.UltralightBuffer;

import java.io.IOException;

/**
 * FileSystem interface.
 * <p>
 * This is used for loading File URLs (eg, {@code file:///page.html>}).
 * <p>
 * You can provide the library with your own FileSystem implementation so that file assets are
 * loaded from your own pipeline (useful if you would like to encrypt/compress your file assets or
 * ship it in a custom format).
 * <p>
 * If you are using {@link Renderer::Create()} instead, you will need to provide your own implementation
 * via {@link net.janrupf.ujr.api.UltralightPlatform#setFilesystem(UltralightFilesystem)}.
 */
public interface UltralightFilesystem {
    /**
     * Check if a file path exists.
     *
     * @param path the path to check
     * @return true if the file exists, false otherwise
     */
    boolean fileExists(String path);

    /**
     * Retrieves the mime-type of the file (eg "text/html").
     * <p>
     * This is usually determined by analyzing the file extension.
     * <p>
     * If a mime-type cannot be determined, this should return "application/unknown".
     *
     * @param path the path to the file
     * @return the mime-type of the file
     */
    String getFileMimeType(String path);

    /**
     * Get the charset / encoding of the file (eg "utf-8", "iso-8859-1").
     * <p>
     * This is only applicable for text-based files (eg, "text/html", "text/plain") and is usually
     * determined by analyzing the contents of the file.
     * <p>
     * If a charset cannot be determined, a safe default to return is "utf-8".
     *
     * @param path the path to the file
     * @return the charset of the file
     */
    String getFileCharset(String path);

    /**
     * Opens a file and returns a buffer containing the file contents.
     *
     * @param path the path to the file
     * @return the opened file as a buffer
     * @throws IOException if an I/O error occurs
     */
    UltralightBuffer openFile(String path) throws IOException;
}
