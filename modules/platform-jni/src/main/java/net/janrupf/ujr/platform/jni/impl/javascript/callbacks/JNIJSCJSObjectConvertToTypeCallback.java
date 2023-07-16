package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.*;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectConvertToTypeCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSObjectConvertToTypeCallback {
    private final JSObjectConvertToTypeCallback delegate;

    public JNIJSCJSObjectConvertToTypeCallback(JSObjectConvertToTypeCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public JSCJSValue convertToType(JSCJSContext context, JSCJSObject object, JSType type) throws JavaScriptValueException {
        JSValue result = delegate.onConvertToType(new JSContext(context), new JSObject(object), type);

        Objects.requireNonNull(result, "JNIJSCJSObjectConvertToTypeCallback should never return Java null\n" +
                "If you want to return JavaScript null, use context.makeNull() instead");

        return result.getValue();
    }
}
