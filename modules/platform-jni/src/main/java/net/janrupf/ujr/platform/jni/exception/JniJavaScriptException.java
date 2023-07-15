package net.janrupf.ujr.platform.jni.exception;

import net.janrupf.ujr.api.javascript.JavaScriptException;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JniJavaScriptException extends JavaScriptException {
    @NativeAccess
    public JniJavaScriptException(String message) {
        super(message);
    }
}
