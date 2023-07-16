package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.api.javascript.JSPropertyAttribute;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.impl.javascript.callbacks.JNIJSCJSObjectCallAsFunctionCallback;

public class JNIJSCJSStaticFunction {
    @NativeAccess
    public String name;

    @NativeAccess
    public JNIJSCJSObjectCallAsFunctionCallback callAsFunction;

    @NativeAccess
    public JSPropertyAttribute[] attributes;
}
