package net.janrupf.ujr.platform.jni.wrapper.filesystem;

import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.api.util.UltralightBuffer;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.wrapper.buffer.JNIUlBufferNative;

import java.io.IOException;
import java.util.Objects;

/**
 * Wrapper for a native Ultralight filesystem instance.
 */
public class JNIUlFilesystemNative implements UltralightFilesystem {
    // Note that this class is allocated by native code without calling the constructor
    private JNIUlFilesystemNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @NativeAccess
    private long handle;

    @Override
    public boolean fileExists(String path) {
        return nativeFileExists(path);
    }

    private native boolean nativeFileExists(String path);

    @Override
    public String getFileMimeType(String path) {
        return nativeGetFileMimeType(path);
    }

    private native String nativeGetFileMimeType(String path);

    @Override
    public String getFileCharset(String path) {
        return nativeGetFileCharset(path);
    }

    private native String nativeGetFileCharset(String path);

    @Override
    public UltralightBuffer openFile(String path) throws IOException {
        return nativeOpenFile(path);
    }

    private native JNIUlBufferNative nativeOpenFile(String path) throws IOException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlFilesystemNative)) return false;
        JNIUlFilesystemNative that = (JNIUlFilesystemNative) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
