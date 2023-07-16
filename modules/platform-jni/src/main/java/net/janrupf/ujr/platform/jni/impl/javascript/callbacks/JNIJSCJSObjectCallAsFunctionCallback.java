package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectCallAsFunctionCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSObjectCallAsFunctionCallback {
    private final JSObjectCallAsFunctionCallback delegate;

    public JNIJSCJSObjectCallAsFunctionCallback(JSObjectCallAsFunctionCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public JSCJSValue callAsFunction(
            JSCJSContext context,
            String functionName,
            JSCJSObject function,
            JSCJSObject thisObject,
            JSCJSValue[] arguments
    ) throws JavaScriptValueException {
        JSValue[] argumentValues = new JSValue[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argumentValues[i] = new JSValue(arguments[i]);
        }

        JSValue result = delegate.onCallAsFunction(
                new JSContext(context),
                functionName,
                new JSObject(function),
                new JSObject(thisObject),
                argumentValues
        );

        Objects.requireNonNull(result, "JSObjectCallAsFunctionCallback should never return Java null\n" +
                "If you want to return JavaScript null, use context.makeNull() instead");

        return result.getValue();
    }
}
