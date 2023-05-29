package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSGlobalContext;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

@NativeAccess
public class JNIJSCJSGlobalContext extends JNIJSCJSContext implements JSCJSGlobalContext {
    private JNIJSCJSGlobalContext() {
        super();
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public void setName(String name) {
        nativeSetName(name);
    }

    private native void nativeSetName(String name);

    @Override
    public String getName() {
        return nativeGetName();
    }

    private native String nativeGetName();
}
