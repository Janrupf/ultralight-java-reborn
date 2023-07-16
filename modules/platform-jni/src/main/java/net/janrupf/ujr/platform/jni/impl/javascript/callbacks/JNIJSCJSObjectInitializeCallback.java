package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectInitializeCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSObjectInitializeCallback {
    private final JSObjectInitializeCallback delegate;

    public JNIJSCJSObjectInitializeCallback(JSObjectInitializeCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public void initialize(JSCJSContext context, JSCJSObject object) {
        delegate.onInitialize(new JSContext(context), new JSObject(object));
    }
}
