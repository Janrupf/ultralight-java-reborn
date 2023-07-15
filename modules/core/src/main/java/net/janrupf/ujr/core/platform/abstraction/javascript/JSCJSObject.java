package net.janrupf.ujr.core.platform.abstraction.javascript;

import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSPropertyAttribute;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;

import java.util.List;
import java.util.Set;

public interface JSCJSObject extends JSCJSValue {
    JSCJSValue getPrototype();

    void setPrototype(JSCJSValue value);

    boolean hasProperty(String name);

    JSCJSValue getProperty(String name) throws JavaScriptValueException;

    void setProperty(String name, JSCJSValue value, Set<JSPropertyAttribute> attributes) throws JavaScriptValueException;

    boolean deleteProperty(String name) throws JavaScriptValueException;

    boolean hasPropertyForKey(JSCJSValue value) throws JavaScriptValueException;

    JSCJSValue getPropertyForKey(JSCJSValue value) throws JavaScriptValueException;

    void setPropertyForKey(JSCJSValue key, JSCJSValue value, Set<JSPropertyAttribute> attributes) throws JavaScriptValueException;

    boolean deletePropertyForKey(JSCJSValue value) throws JavaScriptValueException;

    JSCJSValue getPropertyAtIndex(int index) throws JavaScriptValueException;

    void setPropertyAtIndex(int index, JSCJSValue value) throws JavaScriptValueException;

    boolean isFunction();

    JSCJSValue callAsFunction(JSCJSObject thisObject, JSCJSValue[] arguments) throws JavaScriptValueException;

    boolean isConstructor();

    JSCJSValue callAsConstructor(JSCJSValue[] arguments) throws JavaScriptValueException;

    List<String> getPropertyNames();
}
