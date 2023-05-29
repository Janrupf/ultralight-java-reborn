package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroup;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSContextGroup implements JSCJSContextGroup {
    @NativeAccess
    private final long handle;

    private JNIJSCJSContextGroup() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIJSCJSContextGroup)) return false;
        JNIJSCJSContextGroup that = (JNIJSCJSContextGroup) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
