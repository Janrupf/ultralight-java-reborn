package net.janrupf.ujr.api;

import net.janrupf.ujr.api.event.UlKeyEvent;
import net.janrupf.ujr.api.event.UlKeyEventModifiers;
import net.janrupf.ujr.api.event.UlKeyEventType;

import java.util.EnumSet;

/**
 * Helper class to create a new {@link UlKeyEvent} instance with a fluent API.
 */
public class UltralightKeyEventBuilder extends UlKeyEvent {
    public UltralightKeyEventBuilder(UlKeyEventType type) {
        this.type = type;
    }

    /**
     * Creates a new key down event.
     * <p>
     * Prefer using {@link #rawDown()} if possible.
     *
     * @return the builder for the event
     */
    public static UltralightKeyEventBuilder down() {
        return new UltralightKeyEventBuilder(UlKeyEventType.DOWN);
    }

    /**
     * Creates a new key up event.
     *
     * @return the builder for the event
     */
    public static UltralightKeyEventBuilder up() {
        return new UltralightKeyEventBuilder(UlKeyEventType.UP);
    }

    /**
     * Creates a new raw key down event.
     *
     * @return the builder for the event
     */
    public static UltralightKeyEventBuilder rawDown() {
        return new UltralightKeyEventBuilder(UlKeyEventType.RAW_DOWN);
    }

    /**
     * Creates a new character event.
     *
     * @return the builder for the event
     */
    public static UltralightKeyEventBuilder character() {
        return new UltralightKeyEventBuilder(UlKeyEventType.CHAR);
    }

    /**
     * Sets the event modifiers.
     * <p>
     * See {@link UlKeyEvent#modifiers} for more information.
     *
     * @param modifiers the modifiers to use
     * @return this
     */
    public UltralightKeyEventBuilder modifiers(EnumSet<UlKeyEventModifiers> modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    /**
     * Adds a modifier to the event.
     * <p>
     * See {@link UlKeyEvent#modifiers} for more information.
     *
     * @param modifier the modifier to add
     * @return this
     */
    public UltralightKeyEventBuilder modifier(UlKeyEventModifiers modifier) {
        if (this.modifiers == null) {
            this.modifiers = EnumSet.of(modifier);
        } else {
            this.modifiers.add(modifier);
        }
        return this;
    }

    /**
     * Sets the virtual key code.
     * <p>
     * See {@link UlKeyEvent#virtualKeyCode} for more information.
     *
     * @param virtualKeyCode the virtual key code to use
     * @return this
     */
    public UltralightKeyEventBuilder virtualKeyCode(int virtualKeyCode) {
        this.virtualKeyCode = virtualKeyCode;
        return this;
    }

    /**
     * Sets the native key code.
     * <p>
     * See {@link UlKeyEvent#nativeKeyCode} for more information.
     *
     * @param nativeKeyCode the native key code to use
     * @return this
     */
    public UltralightKeyEventBuilder nativeKeyCode(int nativeKeyCode) {
        this.nativeKeyCode = nativeKeyCode;
        return this;
    }

    /**
     * Sets the key identifier.
     * <p>
     * See {@link UlKeyEvent#keyIdentifier} for more information.
     *
     * @param keyIdentifier the key identifier to use
     * @return this
     */
    public UltralightKeyEventBuilder keyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
        return this;
    }

    /**
     * Sets the text.
     * <p>
     * See {@link UlKeyEvent#text} for more information.
     *
     * @param text the text to use
     * @return this
     */
    public UltralightKeyEventBuilder text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets the unmodified text.
     * <p>
     * See {@link UlKeyEvent#unmodifiedText} for more information.
     *
     * @param unmodifiedText the unmodified text to use
     * @return this
     */
    public UltralightKeyEventBuilder unmodifiedText(String unmodifiedText) {
        this.unmodifiedText = unmodifiedText;
        return this;
    }

    /**
     * Sets whether the event is a keypad event.
     * <p>
     * See {@link UlKeyEvent#isKeypad} for more information.
     *
     * @param isKeypad whether the event is a keypad event
     * @return this
     */
    public UltralightKeyEventBuilder keypad(boolean isKeypad) {
        this.isKeypad = isKeypad;
        return this;
    }

    /**
     * Sets whether the event is an auto-repeat event.
     * <p>
     * See {@link UlKeyEvent#isAutoRepeat} for more information.
     *
     * @param isAutoRepeat whether the event is an auto-repeat event
     * @return this
     */
    public UltralightKeyEventBuilder autoRepeat(boolean isAutoRepeat) {
        this.isAutoRepeat = isAutoRepeat;
        return this;
    }

    /**
     * Sets whether the event is a system key event.
     * <p>
     * See {@link UlKeyEvent#isSystemKey} for more information.
     *
     * @param isSystemKey whether the event is a system key event
     * @return this
     */
    public UltralightKeyEventBuilder systemKey(boolean isSystemKey) {
        this.isSystemKey = isSystemKey;
        return this;
    }

    /**
     * Finishes building the event.
     * <p>
     * This method is a no-op and is only present for consistency with the other builders.
     *
     * @return this
     */
    public UlKeyEvent build() {
        return this;
    }
}
