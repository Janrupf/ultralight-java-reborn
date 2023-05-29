package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClass;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroup;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSGlobalContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSGlobalContextFactory;

public class JNIJSCJSGlobalContextFactory implements JSCJSGlobalContextFactory {
    @Override
    public JSCJSGlobalContext create(JSCJSClass globalObjectClass) {
        return nativeCreate((JNIJSCJSClass) globalObjectClass);
    }

    private native JNIJSCJSGlobalContext nativeCreate(JNIJSCJSClass globalObjectClass);

    @Override
    public JSCJSGlobalContext createInGroup(JSCJSContextGroup group, JSCJSClass globalObjectClass) {
        return nativeCreateInGroup((JNIJSCJSContextGroup) group, (JNIJSCJSClass) globalObjectClass);
    }

    private native JNIJSCJSGlobalContext nativeCreateInGroup(
            JNIJSCJSContextGroup group,
            JNIJSCJSClass globalObjectClass
    );
}
