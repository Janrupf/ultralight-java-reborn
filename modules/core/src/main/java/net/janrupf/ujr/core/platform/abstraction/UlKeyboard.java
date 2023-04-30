package net.janrupf.ujr.core.platform.abstraction;

public interface UlKeyboard {
    /**
     * Generates a key identifier from a virtual key code.
     *
     * @param virtualKeyCode the virtual key code
     * @return the key identifier, or null, if none
     */
    String keyIdentifierFromVirtualKeyCode(int virtualKeyCode);

    /**
     * Generates a key from a virtual key code.
     * <p>
     * This function assumes a US layout.
     *
     * @param virtualKeyCode the virtual key code
     * @param shift          whether the shift key is pressed
     * @return the key, or null, if none
     */
    String keyFromVirtualKeyCode(int virtualKeyCode, boolean shift);
}
