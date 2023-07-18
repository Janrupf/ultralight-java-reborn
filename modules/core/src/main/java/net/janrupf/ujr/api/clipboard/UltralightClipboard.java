package net.janrupf.ujr.api.clipboard;

/**
 * Clipboard interface.
 * <p>
 * This is used for reading and writing data to the platform Clipboard.
 * <p>
 * You will need to provide your own implementation of this.
 * {@link net.janrupf.ujr.api.UltralightPlatform#setClipboard(UltralightClipboard)}.
 */
public interface UltralightClipboard {
    /**
     * Clear the clipboard.
     */
    void clear();

    /**
     * Read plain text from the clipboard.
     *
     * @return the clipboard contents as a string
     */
    String readPlainText();

    /**
     * Write plain text to the clipboard.
     *
     * @param text the text to write to the clipboard
     */
    void writePlainText(String text);
}
