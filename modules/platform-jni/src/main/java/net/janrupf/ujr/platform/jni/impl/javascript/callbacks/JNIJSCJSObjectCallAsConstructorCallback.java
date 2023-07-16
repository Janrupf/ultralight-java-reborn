package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectCallAsConstructorCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSObjectCallAsConstructorCallback {
    private final JSObjectCallAsConstructorCallback delegate;

    public JNIJSCJSObjectCallAsConstructorCallback(JSObjectCallAsConstructorCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    JSCJSObject callAsConstructor(
            JSCJSContext context,
            JSCJSObject constructor,
            JSCJSValue[] arguments
    ) throws JavaScriptValueException {
        JSValue[] argumentValues = new JSValue[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            argumentValues[i] = new JSValue(arguments[i]);
        }

        JSObject result = delegate.onCallAsConstructor(
                new JSContext(context),
                new JSObject(constructor),
                argumentValues
        );

        Objects.requireNonNull(result, "JSObjectCallAsConstructorCallback should never return Java null\n" +
                "If you want to return JavaScript null, use context.makeNull() instead");

        return result.getObject();
    }
}
