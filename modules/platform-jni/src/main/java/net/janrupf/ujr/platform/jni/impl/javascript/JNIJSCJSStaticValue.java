package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.api.javascript.JSPropertyAttribute;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.impl.javascript.callbacks.JNIJSCJSObjectGetPropertyCallback;
import net.janrupf.ujr.platform.jni.impl.javascript.callbacks.JNIJSCJSObjectSetPropertyCallback;

public class JNIJSCJSStaticValue {
    @NativeAccess
    public String name;

    @NativeAccess
    public JNIJSCJSObjectGetPropertyCallback getProperty;

    @NativeAccess
    public JNIJSCJSObjectSetPropertyCallback setProperty;

    @NativeAccess
    public JSPropertyAttribute[] attributes;
}
