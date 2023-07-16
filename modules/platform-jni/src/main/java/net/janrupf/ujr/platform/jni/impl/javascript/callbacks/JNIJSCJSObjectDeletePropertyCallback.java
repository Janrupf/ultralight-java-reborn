package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectDeletePropertyCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSObjectDeletePropertyCallback {
    private final JSObjectDeletePropertyCallback delegate;

    public JNIJSCJSObjectDeletePropertyCallback(JSObjectDeletePropertyCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public boolean deleteProperty(JSCJSContext context, JSCJSObject object, String name) throws JavaScriptValueException {
        return delegate.onDeleteProperty(new JSContext(context), new JSObject(object), name);
    }
}
