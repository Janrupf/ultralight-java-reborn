package net.janrupf.ujr.platform.jni.wrapper.filesystem;

import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.wrapper.buffer.JNIUlDelegatedBuffer;

import java.io.IOException;

public class JNIUlFilesystem {
    private final UltralightFilesystem delegate;

    public JNIUlFilesystem(UltralightFilesystem delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public boolean fileExists(String path) {
        return delegate.fileExists(path);
    }

    @NativeAccess
    String getFileMimeType(String path) {
        return delegate.getFileMimeType(path);
    }

    @NativeAccess
    String getFileCharset(String path) {
        return delegate.getFileCharset(path);
    }

    @NativeAccess
    JNIUlDelegatedBuffer openFile(String path) throws IOException {
        return new JNIUlDelegatedBuffer(delegate.openFile(path));
    }
}
