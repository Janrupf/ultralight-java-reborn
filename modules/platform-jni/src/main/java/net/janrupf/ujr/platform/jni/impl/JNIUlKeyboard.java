package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.core.platform.abstraction.UlKeyboard;

public class JNIUlKeyboard implements UlKeyboard {
    @Override
    public String keyIdentifierFromVirtualKeyCode(int virtualKeyCode) {
        return nativeKeyIdentifierFromVirtualKeyCode(virtualKeyCode);
    }

    private native String nativeKeyIdentifierFromVirtualKeyCode(int virtualKeyCode);

    @Override
    public String keyFromVirtualKeyCode(int virtualKeyCode, boolean shift) {
        return nativeKeyFromVirtualKeyCode(virtualKeyCode, shift);
    }

    private native String nativeKeyFromVirtualKeyCode(int virtualKeyCode, boolean shift);
}
