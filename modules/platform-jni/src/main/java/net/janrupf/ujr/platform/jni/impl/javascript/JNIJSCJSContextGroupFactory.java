package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroup;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroupFactory;

public class JNIJSCJSContextGroupFactory implements JSCJSContextGroupFactory {
    @Override
    public JSCJSContextGroup create() {
        return nativeCreate();
    }

    private native JNIJSCJSContextGroup nativeCreate();
}
