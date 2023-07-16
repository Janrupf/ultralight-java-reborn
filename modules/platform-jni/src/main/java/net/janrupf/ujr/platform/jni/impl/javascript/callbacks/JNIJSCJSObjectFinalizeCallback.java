package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectFinalizeCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSObjectFinalizeCallback {
    private final JSObjectFinalizeCallback delegate;

    public JNIJSCJSObjectFinalizeCallback(JSObjectFinalizeCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public void doFinalize(JSCJSObject object) {
        delegate.onFinalize(new JSObject(object));
    }
}
