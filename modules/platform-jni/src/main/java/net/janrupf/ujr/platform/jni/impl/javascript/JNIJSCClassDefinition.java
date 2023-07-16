package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.api.javascript.JSClassAttribute;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClass;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.impl.javascript.callbacks.*;

public class JNIJSCClassDefinition {
    @NativeAccess
    public String name;

    @NativeAccess
    public JSClassAttribute[] attributes;

    @NativeAccess
    public JSCJSClass parentClass;

    @NativeAccess
    public JNIJSCJSStaticValue[] staticValues;

    @NativeAccess
    public JNIJSCJSStaticFunction[] staticFunctions;

    @NativeAccess
    public JNIJSCJSObjectInitializeCallback initialize;

    @NativeAccess
    public JNIJSCJSObjectFinalizeCallback finalize;

    @NativeAccess
    public JNIJSCJSObjectHasPropertyCallback hasProperty;

    @NativeAccess
    public JNIJSCJSObjectGetPropertyCallback getProperty;

    @NativeAccess
    public JNIJSCJSObjectSetPropertyCallback setProperty;

    @NativeAccess
    public JNIJSCJSObjectDeletePropertyCallback deleteProperty;

    @NativeAccess
    public JNIJSCJSObjectGetPropertyNamesCallback getPropertyNames;

    @NativeAccess
    public JNIJSCJSObjectCallAsFunctionCallback callAsFunction;

    @NativeAccess
    public JNIJSCJSObjectCallAsConstructorCallback callAsConstructor;

    @NativeAccess
    public JNIJSCJSObjectHasInstanceCallback hasInstance;

    @NativeAccess
    public JNIJSCJSObjectConvertToTypeCallback convertToType;
}
