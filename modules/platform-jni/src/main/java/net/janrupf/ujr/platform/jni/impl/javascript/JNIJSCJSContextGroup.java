package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroup;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSContextGroup implements JSCJSContextGroup {
    @NativeAccess
    private final long handle;

    private JNIJSCJSContextGroup() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }
}
