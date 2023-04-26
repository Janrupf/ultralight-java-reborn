package net.janrupf.ujr.platform.jni.exception;

import net.janrupf.ujr.api.exception.JavascriptException;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JniJavascriptException extends JavascriptException {
    @NativeAccess
    public JniJavascriptException(String message) {
        super(message);
    }
}
