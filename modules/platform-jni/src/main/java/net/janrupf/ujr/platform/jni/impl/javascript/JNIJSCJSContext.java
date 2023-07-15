package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroup;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSGlobalContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSContext implements JSCJSContext {
    @NativeAccess
    private final long handle;

    protected JNIJSCJSContext() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public JSCJSGlobalContext getGlobalContext() {
        return nativeGetGlobalContext();
    }

    private native JNIJSCJSGlobalContext nativeGetGlobalContext();

    @Override
    public JSCJSContextGroup getGroup() {
        return nativeGetGroup();
    }

    private native JNIJSCJSContextGroup nativeGetGroup();

    @Override
    public JSCJSValue makeUndefined() {
        return nativeMakeUndefined();
    }

    private native JNIJSCJSValue nativeMakeUndefined();

    @Override
    public JSCJSValue makeNull() {
        return nativeMakeNull();
    }

    private native JNIJSCJSValue nativeMakeNull();

    @Override
    public JSCJSValue makeBoolean(boolean value) {
        return nativeMakeBoolean(value);
    }

    private native JNIJSCJSValue nativeMakeBoolean(boolean value);

    @Override
    public JSCJSValue makeNumber(double value) {
        return nativeMakeNumber(value);
    }

    private native JNIJSCJSValue nativeMakeNumber(double value);

    @Override
    public JSCJSValue makeString(String value) {
        return nativeMakeString(value);
    }

    private native JNIJSCJSValue nativeMakeString(String value);

    @Override
    public JSCJSValue makeSymbol(String description) {
        return nativeMakeSymbol(description);
    }

    private native JNIJSCJSValue nativeMakeSymbol(String description);

    @Override
    public JSCJSValue makeFromJSONString(String jsonValue) {
        return nativeMakeFromJSONString(jsonValue);
    }

    private native JNIJSCJSValue nativeMakeFromJSONString(String jsonValue);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIJSCJSContext)) return false;
        JNIJSCJSContext that = (JNIJSCJSContext) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
