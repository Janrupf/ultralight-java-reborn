package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClass;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSClass implements JSCJSClass {
    @NativeAccess
    private final long handle;

    private JNIJSCJSClass() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }
}
