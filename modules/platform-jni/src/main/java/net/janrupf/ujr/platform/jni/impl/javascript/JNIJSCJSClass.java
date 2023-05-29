package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClass;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSClass implements JSCJSClass {
    @NativeAccess
    private final long handle;

    private JNIJSCJSClass() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIJSCJSClass)) return false;
        JNIJSCJSClass that = (JNIJSCJSClass) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
