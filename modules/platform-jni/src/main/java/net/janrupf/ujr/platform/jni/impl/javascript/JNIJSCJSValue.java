package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.api.javascript.JSType;
import net.janrupf.ujr.api.javascript.JSTypedArrayType;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClass;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIJSCJSValue implements JSCJSValue {
    @NativeAccess
    private final long handle;

    @NativeAccess
    private final long context;

    @NativeAccess
    private final long collector;

    protected JNIJSCJSValue() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public JSType getType() {
        validateContext();
        return nativeGetType();
    }

    private native JSType nativeGetType();

    @Override
    public boolean isUndefined() {
        validateContext();
        return nativeIsUndefined();
    }

    private native boolean nativeIsUndefined();

    @Override
    public boolean isNull() {
        validateContext();
        return nativeIsNull();
    }

    private native boolean nativeIsNull();

    @Override
    public boolean isBoolean() {
        validateContext();
        return nativeIsBoolean();
    }

    private native boolean nativeIsBoolean();

    @Override
    public boolean isNumber() {
        validateContext();
        return nativeIsNumber();
    }

    private native boolean nativeIsNumber();

    @Override
    public boolean isString() {
        validateContext();
        return nativeIsString();
    }

    private native boolean nativeIsString();

    @Override
    public boolean isSymbol() {
        validateContext();
        return nativeIsSymbol();
    }

    private native boolean nativeIsSymbol();

    @Override
    public boolean isObject() {
        validateContext();
        return nativeIsObject();
    }

    private native boolean nativeIsObject();

    @Override
    public boolean isObjectOfClass(JSCJSClass clazz) {
        validateContext();
        return nativeIsObjectOfClass(clazz);
    }

    private native boolean nativeIsObjectOfClass(JSCJSClass clazz);

    @Override
    public boolean isArray() {
        validateContext();
        return nativeIsArray();
    }

    private native boolean nativeIsArray();

    @Override
    public boolean isDate() {
        validateContext();
        return nativeIsDate();
    }

    private native boolean nativeIsDate();

    @Override
    public JSTypedArrayType getTypedArrayType() throws JavaScriptValueException {
        validateContext();
        return nativeGetTypedArrayType();
    }

    private native JSTypedArrayType nativeGetTypedArrayType() throws JavaScriptValueException;

    @Override
    public boolean isEqual(JSCJSValue value) throws JavaScriptValueException {
        validateContext();
        return nativeIsEqual(value);
    }

    private native boolean nativeIsEqual(JSCJSValue value) throws JavaScriptValueException;

    @Override
    public boolean isStrictEqual(JSCJSValue value) {
        validateContext();
        return nativeIsStrictEqual(value);
    }

    private native boolean nativeIsStrictEqual(JSCJSValue value);

    @Override
    public boolean isInstanceOfConstructor(JSCJSObject object) throws JavaScriptValueException {
        validateContext();
        return nativeIsInstanceOfConstructor(object);
    }

    private native boolean nativeIsInstanceOfConstructor(JSCJSObject object) throws JavaScriptValueException;

    @Override
    public String createJSONString(int indent) throws JavaScriptValueException {
        validateContext();
        return nativeCreateJSONString(indent);
    }

    private native String nativeCreateJSONString(int indent) throws JavaScriptValueException;

    @Override
    public boolean toBoolean() {
        validateContext();
        return nativeToBoolean();
    }

    private native boolean nativeToBoolean();

    @Override
    public double toNumber() throws JavaScriptValueException {
        validateContext();
        return nativeToNumber();
    }

    private native double nativeToNumber() throws JavaScriptValueException;

    @Override
    public String toString() {
        try {
            return nativeToString();
        } catch (JavaScriptValueException e) {
            return "toString() exception: " + e.getMessage();
        }
    }

    private native String nativeToString() throws JavaScriptValueException;

    @Override
    public JSCJSObject toObject() throws JavaScriptValueException {
        validateContext();
        return nativeToObject();
    }

    private native JSCJSObject nativeToObject() throws JavaScriptValueException;

    @Override
    public JSCJSContext getContext() {
        validateContext();
        return nativeGetContext();
    }

    private native JSCJSContext nativeGetContext();

    protected void validateContext() {
        if (context == 0) {
            throw new IllegalStateException(
                    "This value is not associated with a context (or a temporary context has been destroyed)"
            );
        }
    }
}
