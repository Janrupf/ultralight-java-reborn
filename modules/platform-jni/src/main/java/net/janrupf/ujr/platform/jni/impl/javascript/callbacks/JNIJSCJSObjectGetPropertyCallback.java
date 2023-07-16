package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectGetPropertyCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSObjectGetPropertyCallback {
    private final JSObjectGetPropertyCallback delegate;

    public JNIJSCJSObjectGetPropertyCallback(JSObjectGetPropertyCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public JSCJSValue getProperty(JSCJSContext context, JSCJSValue object, String propertyName) throws JavaScriptValueException {
        JSValue result = delegate.onGetProperty(new JSContext(context), new JSValue(object), propertyName);
        Objects.requireNonNull(result, "JSObjectGetPropertyCallback should never return Java null\n" +
                "If you want to return JavaScript null, use context.makeNull() instead");

        return result.getValue();
    }
}
