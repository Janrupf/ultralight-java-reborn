package net.janrupf.ujr.example.full;

import net.janrupf.ujr.api.UltralightResources;
import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.api.util.NioUltralightBuffer;
import net.janrupf.ujr.api.util.UltralightBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FilesystemBridge implements UltralightFilesystem {
    public static final String RESOURCE_PREFIX = "$built-in/resources/";

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public boolean fileExists(String path) {
        LOGGER.debug("Checking if {} exists", path);

        if (path.startsWith(RESOURCE_PREFIX)) {
            // Built in resource, attempt look up
            String resourcePath = path.substring(RESOURCE_PREFIX.length());
            URI resource = UltralightResources.getResource(resourcePath);

            // If the resource does not exist, log a warning
            if (resource == null) {
                LOGGER.warn("Resource {} does not exist", resourcePath);
            }

            return resource != null;
        }

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

        if (path.startsWith(RESOURCE_PREFIX)) {
            // Built in resource, attempt look up
            String resourcePath = path.substring(RESOURCE_PREFIX.length());
            URI resource = UltralightResources.getResource(resourcePath);

            // If the resource does not exist, log a throw an I/O exception
            if (resource == null) {
                // This should never happen, as we already checked for the existence of the resource
                // in the fileExists method, but we still check here to be safe
                throw new IOException("Resource " + resourcePath + " does not exist");
            }

            // Open the resource
            URLConnection connection = resource.toURL().openConnection();
            int length = connection.getContentLength();

            if (length != -1) {
                // If the length is known, allocate a buffer with the correct size upfront.
                // We also allocate a direct byte buffer, which will avoid copies later
                ByteBuffer buffer = ByteBuffer.allocateDirect(length);
                try (ReadableByteChannel channel = Channels.newChannel(connection.getInputStream())) {
                    //noinspection StatementWithEmptyBody
                    while (channel.read(buffer) > 0) { /* keep going */ }
                }

                // We are done, the resource is in memory
                return new NioUltralightBuffer(buffer.flip());
            }

            // Size is not known, so we will have to take the slower path of a buffer
            try (ByteArrayOutputStream out = new ByteArrayOutputStream(); InputStream in = connection.getInputStream()) {
                // Classic copy
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) > 0) {
                    out.write(buffer, 0, read);
                }

                // Wrap the array in a buffer
                //
                // The native code later will have to copy this again as the buffer is not a direct buffer.
                // However, Ultralight Java Reborn handles this detail internally for you.
                return new NioUltralightBuffer(ByteBuffer.wrap(out.toByteArray()));
            }
        }

        return null;
    }
}
