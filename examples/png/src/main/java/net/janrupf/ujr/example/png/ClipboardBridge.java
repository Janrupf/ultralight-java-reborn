package net.janrupf.ujr.example.png;

import net.janrupf.ujr.api.clipboard.UltralightClipboard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dummy implementation of a clipboard for Ultralight.
 * <p>
 * This implementation does nothing and is only used for demonstration purposes.
 */
public class ClipboardBridge implements UltralightClipboard {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void clear() {
        LOGGER.debug("Ultralight requested clipboard clear");
    }

    @Override
    public String readPlainText() {
        LOGGER.debug("Ultralight requested clipboard text");
        return null;
    }

    @Override
    public void writePlainText(String text) {
        LOGGER.debug("Ultralight wanted to write the following to the clipboard: {}", text);
    }
}
