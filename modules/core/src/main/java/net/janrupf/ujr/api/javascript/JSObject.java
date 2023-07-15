package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;

import java.util.List;
import java.util.Set;

/**
 * A JavaScript object.
 */
public class JSObject extends JSValue {
    public JSObject(JSCJSObject object) {
        super(object);
    }

    /**
     * Retrieves the prototype of this object.
     *
     * @return the prototype
     */
    public JSValue getPrototype() {
        return new JSValue(getObject().getPrototype());
    }

    /**
     * Sets the prototype of this object.
     *
     * @param prototype the prototype
     */
    public void setPrototype(JSValue prototype) {
        getObject().setPrototype(prototype.getValue());
    }

    /**
     * Determines whether this object has a property with the specified name.
     *
     * @param name the name of the property
     * @return whether this object has a property with the specified name
     */
    public boolean hasProperty(String name) {
        return getObject().hasProperty(name);
    }

    /**
     * Retrieves the value of the property with the specified name.
     *
     * @param name the name of the property
     * @return the value of the property
     * @throws JavaScriptValueException if retrieving the property fails
     */
    public JSValue getProperty(String name) throws JavaScriptValueException {
        return new JSValue(getObject().getProperty(name));
    }

    /**
     * Sets the value of the property with the specified name.
     *
     * @param name  the name of the property
     * @param value the new value of the property
     * @throws JavaScriptValueException if setting the property fails
     */
    public void setProperty(String name, JSValue value) throws JavaScriptValueException {
        setProperty(name, value, null);
    }

    /**
     * Sets the value of the property with the specified name.
     *
     * @param name       the name of the property
     * @param value      the new value of the property
     * @param attributes the attributes of the property
     * @throws JavaScriptValueException if setting the property fails
     */
    public void setProperty(String name, JSValue value, Set<JSPropertyAttribute> attributes) throws JavaScriptValueException {
        getObject().setProperty(name, value.getValue(), attributes);
    }

    /**
     * Deletes the property with the specified name.
     *
     * @param name the name of the property
     * @return whether the property was deleted
     * @throws JavaScriptValueException if deleting the property fails
     */
    public boolean deleteProperty(String name) throws JavaScriptValueException {
        return getObject().deleteProperty(name);
    }

    /**
     * Determines whether this object has a property with the specified key.
     * <p>
     * Calling this is equivalent to running {@code key in object} from JavaScript.
     *
     * @param key the key of the property
     * @return whether this object has a property with the specified key
     * @throws JavaScriptValueException if determining whether the property exists fails
     */
    public boolean hasPropertyForKey(JSValue key) throws JavaScriptValueException {
        return getObject().hasPropertyForKey(key.getValue());
    }

    /**
     * Retrieves the value of the property with the specified key.
     * <p>
     * Calling this is equivalent to running {@code object[key]} from JavaScript.
     *
     * @param key the key of the property
     * @return the value of the property
     * @throws JavaScriptValueException if retrieving the property fails
     */
    public JSValue getPropertyForKey(JSValue key) throws JavaScriptValueException {
        return new JSValue(getObject().getPropertyForKey(key.getValue()));
    }

    /**
     * Sets the value of the property with the specified key.
     * <p>
     * Calling this is equivalent to running {@code object[key] = value} from JavaScript.
     *
     * @param key   the key of the property
     * @param value the new value of the property
     * @throws JavaScriptValueException if setting the property fails
     */
    public void setPropertyForKey(JSValue key, JSValue value) throws JavaScriptValueException {
        setPropertyForKey(key, value, null);
    }

    /**
     * Sets the value of the property with the specified key.
     * <p>
     * Calling this is equivalent to running {@code object[key] = value} from JavaScript.
     *
     * @param key        the key of the property
     * @param value      the new value of the property
     * @param attributes the attributes of the property
     * @throws JavaScriptValueException if setting the property fails
     */
    public void setPropertyForKey(JSValue key, JSValue value, Set<JSPropertyAttribute> attributes) throws JavaScriptValueException {
        getObject().setPropertyForKey(key.getValue(), value.getValue(), attributes);
    }

    /**
     * Deletes the property with the specified key.
     * <p>
     * Calling this is equivalent to running {@code delete object[key]} from JavaScript.
     *
     * @param key the key of the property
     * @return whether the property was deleted
     * @throws JavaScriptValueException if deleting the property fails
     */
    public boolean deletePropertyForKey(JSValue key) throws JavaScriptValueException {
        return getObject().deletePropertyForKey(key.getValue());
    }

    /**
     * Retrieves a property of this object at the specified index.
     *
     * @param index the index of the property
     * @return the property at the specified index
     * @throws JavaScriptValueException if retrieving the property fails
     * @implNote This is effectively equivalent to calling {@link #getProperty(String)} with the
     * string representation of the index, but this method is more efficient for numeric property keys.
     */
    public JSValue getPropertyAtIndex(int index) throws JavaScriptValueException {
        return new JSValue(getObject().getPropertyAtIndex(index));
    }

    /**
     * Sets a property of this object at the specified index.
     *
     * @param index the index of the property
     * @param value the new value of the property
     * @throws JavaScriptValueException if setting the property fails
     * @implNote This is effectively equivalent to calling {@link #setProperty(String, JSValue)} with the
     * string representation of the index, but this method is more efficient for numeric property keys.
     */
    public void setPropertyAtIndex(int index, JSValue value) throws JavaScriptValueException {
        getObject().setPropertyAtIndex(index, value.getValue());
    }

    /**
     * Determines whether this object can be called as a function.
     *
     * @return whether this object can be called as a function
     */
    public boolean isFunction() {
        return getObject().isFunction();
    }

    /**
     * Calls this object as a function.
     *
     * @param thisObject the {@code this} object to use when calling this object as a function
     * @param arguments the arguments to pass to the function
     * @return the result of calling this object as a function
     * @throws JavaScriptValueException if calling this object as a function fails or the function throws
     */
    public JSValue callAsFunction(JSObject thisObject, JSValue... arguments) throws JavaScriptValueException {
        JSCJSValue[] engineArguments = new JSCJSValue[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            engineArguments[i] = arguments[i].getValue();
        }

        JSCJSObject thisObjectNative = thisObject == null ? null : thisObject.getObject();
        return new JSValue(getObject().callAsFunction(thisObjectNative, engineArguments));
    }

    /**
     * Determines whether this object can be called as a constructor.
     *
     * @return whether this object can be called as a constructor
     */
    public boolean isConstructor() {
        return getObject().isConstructor();
    }

    /**
     * Calls this object as a constructor.
     *
     * @param arguments the arguments to pass to the constructor
     * @return the result of calling this object as a constructor
     * @throws JavaScriptValueException if calling this object as a constructor fails or the constructor throws
     */
    public JSValue callAsConstructor(JSValue... arguments) throws JavaScriptValueException {
        JSCJSValue[] engineArguments = new JSCJSValue[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            engineArguments[i] = arguments[i].getValue();
        }

        return new JSValue(getObject().callAsConstructor(engineArguments));
    }

    /**
     * Retrieves the enumerable property names of this object.
     *
     * @return the enumerable property names of this object
     */
    public List<String> getPropertyNames() {
        return getObject().getPropertyNames();
    }

    // Internal use only
    public JSCJSObject getObject() {
        return (JSCJSObject) getValue();
    }
}
