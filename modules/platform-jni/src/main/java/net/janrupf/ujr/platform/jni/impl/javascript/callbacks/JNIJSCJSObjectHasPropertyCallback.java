package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectHasPropertyCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSObjectHasPropertyCallback {
    private final JSObjectHasPropertyCallback delegate;

    public JNIJSCJSObjectHasPropertyCallback(JSObjectHasPropertyCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public boolean hasProperty(JSCJSContext context, JSCJSObject object, String name) {
        return delegate.onHasProperty(new JSContext(context), new JSObject(object), name);
    }
}
