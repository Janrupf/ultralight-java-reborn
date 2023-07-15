package net.janrupf.ujr.platform.jni.exception;

import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JniJavaScriptValueException extends JavaScriptValueException {
    @NativeAccess
    public JniJavaScriptValueException(JSCJSValue value) {
        super(new JSValue(value), value.toString());
    }
}
