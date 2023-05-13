package net.janrupf.ujr.platform.jni.wrapper.clipboard;

import net.janrupf.ujr.api.clipboard.UltralightClipboard;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIUlClipboardNative implements UltralightClipboard {
    // Note that this class is allocated by native code without calling the constructor
    private JNIUlClipboardNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @NativeAccess
    private long handle;

    @Override
    public void clear() {
        nativeClear();
    }

    private native void nativeClear();

    @Override
    public String readPlainText() {
        return nativeReadPlainText();
    }

    private native String nativeReadPlainText();

    @Override
    public void writePlainText(String text) {
        nativeWritePlainText(text);
    }

    private native void nativeWritePlainText(String text);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlClipboardNative)) return false;
        JNIUlClipboardNative that = (JNIUlClipboardNative) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
