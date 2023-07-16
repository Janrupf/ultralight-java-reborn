package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectHasInstanceCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSObjectHasInstanceCallback {
    private final JSObjectHasInstanceCallback delegate;

    public JNIJSCJSObjectHasInstanceCallback(JSObjectHasInstanceCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public boolean hasInstance(JSCJSContext context, JSCJSObject constructor, JSCJSValue possibleInstance) throws JavaScriptValueException {
        return delegate.onHasInstance(
                new JSContext(context),
                new JSObject(constructor),
                new JSValue(possibleInstance)
        );
    }
}
