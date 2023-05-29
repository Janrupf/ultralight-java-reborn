package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroup;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSGlobalContext;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSContext implements JSCJSContext {
    @NativeAccess
    private final long handle;

    protected JNIJSCJSContext() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public JSCJSGlobalContext getGlobalContext() {
        return nativeGetGlobalContext();
    }

    private native JNIJSCJSGlobalContext nativeGetGlobalContext();

    @Override
    public JSCJSContextGroup getGroup() {
        return nativeGetGroup();
    }

    private native JNIJSCJSContextGroup nativeGetGroup();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIJSCJSContext)) return false;
        JNIJSCJSContext that = (JNIJSCJSContext) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
