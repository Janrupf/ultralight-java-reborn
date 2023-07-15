package net.janrupf.ujr.core.platform.abstraction.javascript;

import net.janrupf.ujr.api.javascript.JSType;
import net.janrupf.ujr.api.javascript.JSTypedArrayType;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;

public interface JSCJSValue {
    JSType getType();

    boolean isUndefined();

    boolean isNull();

    boolean isBoolean();

    boolean isNumber();

    boolean isString();

    boolean isSymbol();

    boolean isObject();

    boolean isObjectOfClass(JSCJSClass clazz);

    boolean isArray();

    boolean isDate();

    JSTypedArrayType getTypedArrayType() throws JavaScriptValueException;

    boolean isEqual(JSCJSValue value) throws JavaScriptValueException;

    boolean isStrictEqual(JSCJSValue value);

    boolean isInstanceOfConstructor(JSCJSObject object) throws JavaScriptValueException;

    String createJSONString(int indent) throws JavaScriptValueException;

    boolean toBoolean();

    double toNumber() throws JavaScriptValueException;

    JSCJSObject toObject() throws JavaScriptValueException;

    JSCJSContext getContext();
}
