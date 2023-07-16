package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectSetPropertyCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSObjectSetPropertyCallback {
    private final JSObjectSetPropertyCallback delegate;

    public JNIJSCJSObjectSetPropertyCallback(JSObjectSetPropertyCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public boolean setProperty(JSCJSContext context, JSCJSObject object, String name, JSCJSValue value) throws JavaScriptValueException {
        return delegate.onSetProperty(new JSContext(context), new JSObject(object), name, new JSValue(value));
    }
}
