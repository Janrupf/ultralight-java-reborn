package net.janrupf.ujr.platform.jni.impl.javascript;

import net.janrupf.ujr.api.javascript.JSPropertyAttribute;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@NativeAccess
public class JNIJSCJSObject extends JNIJSCJSValue implements JSCJSObject {
    @Override
    public JSCJSValue getPrototype() {
        return nativeGetPrototype();
    }

    private native JSCJSValue nativeGetPrototype();

    @Override
    public void setPrototype(JSCJSValue value) {
        nativeSetPrototype(value);
    }

    private native void nativeSetPrototype(JSCJSValue value);

    @Override
    public boolean hasProperty(String name) {
        return nativeHasProperty(name);
    }

    private native boolean nativeHasProperty(String name);

    @Override
    public JSCJSValue getProperty(String name) throws JavaScriptValueException {
        return nativeGetProperty(name);
    }

    private native JSCJSValue nativeGetProperty(String name) throws JavaScriptValueException;

    @Override
    public void setProperty(String name, JSCJSValue value, Set<JSPropertyAttribute> attributes) throws JavaScriptValueException {
        JSPropertyAttribute[] nativeAttributes = attributes != null ? attributes.toArray(new JSPropertyAttribute[0]) : null;
        nativeSetProperty(name, value, nativeAttributes);
    }

    private native void nativeSetProperty(String name, JSCJSValue value, JSPropertyAttribute[] attributes) throws JavaScriptValueException;

    @Override
    public boolean deleteProperty(String name) throws JavaScriptValueException {
        return nativeDeleteProperty(name);
    }

    private native boolean nativeDeleteProperty(String name) throws JavaScriptValueException;

    @Override
    public boolean hasPropertyForKey(JSCJSValue value) throws JavaScriptValueException {
        return nativeHasPropertyForKey(value);
    }

    private native boolean nativeHasPropertyForKey(JSCJSValue value) throws JavaScriptValueException;

    @Override
    public JSCJSValue getPropertyForKey(JSCJSValue value) throws JavaScriptValueException {
        return nativeGetPropertyForKey(value);
    }

    private native JSCJSValue nativeGetPropertyForKey(JSCJSValue value) throws JavaScriptValueException;

    @Override
    public void setPropertyForKey(JSCJSValue key, JSCJSValue value, Set<JSPropertyAttribute> attributes) throws JavaScriptValueException {
        nativeSetPropertyForKey(key, value, attributes.toArray(new JSPropertyAttribute[0]));
    }

    private native void nativeSetPropertyForKey(JSCJSValue key, JSCJSValue value, JSPropertyAttribute[] attributes) throws JavaScriptValueException;

    @Override
    public boolean deletePropertyForKey(JSCJSValue value) throws JavaScriptValueException {
        return nativeDeletePropertyForKey(value);
    }

    private native boolean nativeDeletePropertyForKey(JSCJSValue value) throws JavaScriptValueException;

    @Override
    public JSCJSValue getPropertyAtIndex(int index) throws JavaScriptValueException {
        return nativeGetPropertyAtIndex(index);
    }

    private native JSCJSValue nativeGetPropertyAtIndex(int index) throws JavaScriptValueException;

    @Override
    public void setPropertyAtIndex(int index, JSCJSValue value) throws JavaScriptValueException {
        nativeSetPropertyAtIndex(index, value);
    }

    private native void nativeSetPropertyAtIndex(int index, JSCJSValue value) throws JavaScriptValueException;

    @Override
    public boolean isFunction() {
        return nativeIsFunction();
    }

    private native boolean nativeIsFunction();

    @Override
    public JSCJSValue callAsFunction(JSCJSObject thisObject, JSCJSValue[] arguments) throws JavaScriptValueException {
        return nativeCallAsFunction(thisObject, arguments);
    }

    private native JSCJSValue nativeCallAsFunction(JSCJSValue thisObject, JSCJSValue[] arguments) throws JavaScriptValueException;

    @Override
    public boolean isConstructor() {
        return nativeIsConstructor();
    }

    private native boolean nativeIsConstructor();

    @Override
    public JSCJSValue callAsConstructor(JSCJSValue[] arguments) throws JavaScriptValueException {
        return nativeCallAsConstructor(arguments);
    }

    private native JSCJSValue nativeCallAsConstructor(JSCJSValue[] arguments) throws JavaScriptValueException;

    @Override
    public List<String> getPropertyNames() {
        return Collections.unmodifiableList(Arrays.asList(nativeGetPropertyNames()));
    }

    private native String[] nativeGetPropertyNames();
}
