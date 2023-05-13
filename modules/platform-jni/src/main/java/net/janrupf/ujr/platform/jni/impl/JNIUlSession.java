package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.core.platform.abstraction.UlSession;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIUlSession implements UlSession {
    @NativeAccess
    private final long handle;

    private JNIUlSession() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public boolean isPersistent() {
        return nativeIsPersistent();
    }

    private native boolean nativeIsPersistent();

    @Override
    public String name() {
        return nativeName();
    }

    private native String nativeName();

    @Override
    public long id() {
        return nativeId();
    }

    private native long nativeId();

    @Override
    public String diskPath() {
        return nativeDiskPath();
    }

    private native String nativeDiskPath();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlSession)) return false;
        JNIUlSession that = (JNIUlSession) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
