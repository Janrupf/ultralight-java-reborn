package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;

/**
 * A JavaScript value.
 */
public class JSValue {
    private final JSCJSValue value;

    public JSValue(JSCJSValue value) {
        this.value = value;
    }

    /**
     * Determines the JavaScript type of this value.
     *
     * @return the JavaScript type
     */
    public JSType getType() {
        return value.getType();
    }

    /**
     * Determines whether this value is undefined.
     *
     * @return whether this value is undefined
     */
    public boolean isUndefined() {
        return value.isUndefined();
    }

    /**
     * Determines whether this value is null.
     *
     * @return whether this value is null
     */
    public boolean isNull() {
        return value.isNull();
    }

    /**
     * Determines whether this value is a boolean.
     *
     * @return whether this value is a boolean
     */
    public boolean isBoolean() {
        return value.isBoolean();
    }

    /**
     * Determines whether this value is a number.
     *
     * @return whether this value is a number
     */
    public boolean isNumber() {
        return value.isNumber();
    }

    /**
     * Determines whether this value is a string.
     *
     * @return whether this value is a string
     */
    public boolean isString() {
        return value.isString();
    }

    /**
     * Determines whether this value is a symbol.
     *
     * @return whether this value is a symbol
     */
    public boolean isSymbol() {
        return value.isSymbol();
    }

    /**
     * Determines whether this value is an object.
     *
     * @return whether this value is an object
     */
    public boolean isObject() {
        return value.isObject();
    }

    /**
     * Determines whether this values is an object with the given class in its class chain.
     *
     * @param clazz the class to check for
     * @return whether this value is an object with the given class in its class chain
     */
    public boolean isObjectOfClass(JSClass clazz) {
        return value.isObjectOfClass(clazz.getClazz());
    }

    /**
     * Determines whether this value is an array.
     *
     * @return whether this value is an array
     */
    public boolean isArray() {
        return value.isArray();
    }

    /**
     * Determines whether this value is a date.
     *
     * @return whether this value is a date
     */
    public boolean isDate() {
        return value.isDate();
    }

    /**
     * Determines the typed array type of this value.
     *
     * @return the typed array type
     * @throws JavaScriptValueException if an error occurs while determining the typed array type
     */
    public JSTypedArrayType getTypedArrayType() throws JavaScriptValueException {
        return value.getTypedArrayType();
    }

    /**
     * Determines whether this value is equal to another value, as compared by the JavaScript {@code ==} operator.
     *
     * @param other the other value
     * @return whether this value is equal to the other value
     * @throws JavaScriptValueException if an error occurs while determining equality
     */
    public boolean isEqual(JSValue other) throws JavaScriptValueException {
        return value.isEqual(other.value);
    }

    /**
     * Determines whether this value is strictly equal to another value, as compared by the JavaScript {@code ===}
     * operator.
     *
     * @param other the other value
     * @return whether this value is strictly equal to the other value
     */
    public boolean isStrictEqual(JSValue other) {
        return value.isStrictEqual(other.value);
    }

    /**
     * Determines whether this value is an object constructed by the given constructor, as compared by the JavaScript
     * {@code instanceof} operator.
     *
     * @param constructor the constructor to check for
     * @return whether this value is an object constructed by the given constructor
     * @throws JavaScriptValueException if an error occurs while determining if this value is an instance of the given
     *                                  constructor
     */
    public boolean isInstanceOfConstructor(JSObject constructor) throws JavaScriptValueException {
        return value.isInstanceOfConstructor(constructor.getObject());
    }

    /**
     * Creates a string containing the JSON representation of this value.
     *
     * @return the JSON string
     * @throws JavaScriptValueException if an error occurs while creating the JSON string
     */
    public String createJSONString() throws JavaScriptValueException {
        return createJSONString(0);
    }

    /**
     * Creates a string containing the JSON representation of this value.
     *
     * @param indent the indentation level
     * @return the JSON string
     * @throws JavaScriptValueException if an error occurs while creating the JSON string
     * @implNote The indentation level is clamped to the range {@code [0, 10]}.
     */
    public String createJSONString(int indent) throws JavaScriptValueException {
        return value.createJSONString(indent);
    }

    /**
     * Converts this value to a boolean.
     *
     * @return the boolean value
     */
    public boolean toBoolean() {
        return value.toBoolean();
    }

    /**
     * Converts this value to a number.
     *
     * @return the number value
     * @throws JavaScriptValueException if an error occurs while converting this value to a number
     */
    public double toNumber() throws JavaScriptValueException {
        return value.toNumber();
    }

    /**
     * Converts this value to a string.
     *
     * @return the string value
     */
    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Converts this value to an object.
     *
     * @return the object value
     * @throws JavaScriptValueException if an error occurs while converting this value to an object
     */
    public JSObject toObject() throws JavaScriptValueException {
        return new JSObject(value.toObject());
    }

    /**
     * Retrieves the context which this value is associated with.
     *
     * @return the context this value is associated with
     */
    public JSContext getContext() {
        return new JSContext(value.getContext());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JSValue)) {
            return false;
        }

        return isStrictEqual((JSValue) obj);
    }

    // Internal use only
    public JSCJSValue getValue() {
        return value;
    }
}
