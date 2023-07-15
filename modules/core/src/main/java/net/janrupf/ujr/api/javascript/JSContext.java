package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSValue;

import java.util.Objects;

public class JSContext {
    private final JSCJSContext context;

    public JSContext(JSCJSContext context) {
        this.context = context;
    }

    /**
     * Retrieves the group of this context.
     *
     * @return the group
     */
    public JSContextGroup getGroup() {
        return new JSContextGroup(context.getGroup());
    }

    /**
     * Retrieves the global context of this context.
     *
     * @return the global context
     */
    public JSGlobalContext getGlobalContext() {
        return new JSGlobalContext(context.getGlobalContext());
    }

    /**
     * Creates a JavaScript value of the undefined type.
     *
     * @return the created value
     */
    public JSValue makeUndefined() {
        return new JSValue(context.makeUndefined());
    }

    /**
     * Creates a JavaScript value of the null type.
     *
     * @return the created value
     */
    public JSValue makeNull() {
        return new JSValue(context.makeNull());
    }

    /**
     * Creates a JavaScript value of the boolean type.
     *
     * @param value the value
     * @return the created value
     */
    public JSValue makeBoolean(boolean value) {
        return new JSValue(context.makeBoolean(value));
    }

    /**
     * Creates a JavaScript value of the number type.
     *
     * @param value the value
     * @return the created value
     */
    public JSValue makeNumber(double value) {
        return new JSValue(context.makeNumber(value));
    }

    /**
     * Creates a JavaScript value of the string type.
     *
     * @param value the value
     * @return the created value
     */
    public JSValue makeString(String value) {
        return new JSValue(context.makeString(value));
    }

    /**
     * Creates a JavaScript value of the symbol type.
     *
     * @param description the description
     * @return the created value
     */
    public JSValue makeSymbol(String description) {
        return new JSValue(context.makeSymbol(description));
    }

    /**
     * Creates a JavaScript value from a JSON fromatted string.
     *
     * @param jsonValue the JSON value to be parsed
     * @return the created value
     */
    public JSValue makeFromJSONString(String jsonValue) {
        return new JSValue(context.makeFromJSONString(jsonValue));
    }

    /**
     * Creates a JavaScript array.
     *
     * @param values the values to be added to the array
     * @return the created array
     * @throws JavaScriptValueException if an exception is thrown during the array creation
     */
    public JSObject makeArray(JSValue... values) throws JavaScriptValueException {
        JSCJSValue[] nativeValues = new JSCJSValue[values.length];
        for (int i = 0; i < values.length; i++) {
            nativeValues[i] = values[i].getValue();
        }

        return new JSObject(context.makeArray(nativeValues));
    }

    /**
     * Creates a JavaScript date.
     *
     * @param arguments the arguments to be passed to the date constructor
     * @return the created date
     * @throws JavaScriptValueException if an exception is thrown during the date creation
     */
    public JSObject makeDate(JSValue... arguments) throws JavaScriptValueException {
        JSCJSValue[] nativeArguments = new JSCJSValue[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            nativeArguments[i] = arguments[i].getValue();
        }

        return new JSObject(context.makeDate(nativeArguments));
    }

    /**
     * Creates a JavaScript error.
     *
     * @param message the error message
     * @return the created error
     * @throws JavaScriptValueException if an exception is thrown during the error creation
     */
    public JSObject makeError(String message) throws JavaScriptValueException {
        return makeError(makeString(message));
    }

    /**
     * Creates a JavaScript error.
     *
     * @param arguments the arguments to be passed to the error constructor
     * @return the created error
     * @throws JavaScriptValueException if an exception is thrown during the error creation
     */
    public JSObject makeError(JSValue... arguments) throws JavaScriptValueException {
        JSCJSValue[] nativeArguments = new JSCJSValue[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            nativeArguments[i] = arguments[i].getValue();
        }

        return new JSObject(context.makeError(nativeArguments));
    }

    /**
     * Creates a JavaScript regular expression.
     *
     * @param arguments the arguments to be passed to the regular expression constructor
     * @return the created regular expression
     * @throws JavaScriptValueException if an exception is thrown during the regular expression creation
     */
    public JSObject makeRegExp(JSValue... arguments) throws JavaScriptValueException {
        JSCJSValue[] nativeArguments = new JSCJSValue[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            nativeArguments[i] = arguments[i].getValue();
        }

        return new JSObject(context.makeRegExp(nativeArguments));
    }

    /**
     * Creates a JavaScript function.
     *
     * @param name               the function name, or {@code null} for anonymous functions
     * @param parameterNames     the parameter names
     * @param body               the function body
     * @param sourceURL          the URL of the script's source file, if any
     * @param startingLineNumber the base line number to use for error reporting (starting with 1)
     * @return the created function
     * @throws JavaScriptValueException if an exception is thrown during the function creation
     */
    public JSObject makeFunction(
            String name,
            String[] parameterNames,
            String body,
            String sourceURL,
            int startingLineNumber
    ) throws JavaScriptValueException {
        return new JSObject(context.makeFunction(name, parameterNames, body, sourceURL, startingLineNumber));
    }

    /**
     * Evaluates a JavaScript script.
     *
     * @param script             the script source code
     * @param thisObject         the object to be used as the this object, or {@code null} for the global object as this
     * @param sourceURL          the URL of the script's source file, if any
     * @param startingLineNumber the base line number to use for error reporting (starting with 1)
     * @return the result of the script evaluation
     * @throws JavaScriptValueException if an exception is thrown during the script evaluation
     */
    public JSValue evaluateScript(
            String script,
            JSObject thisObject,
            String sourceURL,
            int startingLineNumber
    ) throws JavaScriptValueException {
        JSCJSObject thisObjectNative = thisObject != null ? thisObject.getObject() : null;

        return new JSValue(context.evaluateScript(script, thisObjectNative, sourceURL, startingLineNumber));
    }

    /**
     * Checks if a JavaScript script is syntactically valid.
     *
     * @param script             the script source code
     * @param sourceURL          the URL of the script's source file, if any
     * @param startingLineNumber the base line number to use for error reporting (starting with 1)
     * @throws JavaScriptValueException if syntax errors are found
     */
    public void checkScriptSyntax(
            String script,
            String sourceURL,
            int startingLineNumber
    ) throws JavaScriptValueException {
        context.checkScriptSyntax(script, sourceURL, startingLineNumber);
    }

    /**
     * Performs an explicit garbage collection.
     * <p>
     * This method is not required to be called, as the garbage collection is
     * performed automatically. In case of memory pressure, this method can be
     * used to force a garbage collection.
     */
    public void collectGarbage() {
        context.collectGarbage();
    }

    // Internal use only
    public JSCJSContext getContext() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSContext)) return false;
        JSContext jsContext = (JSContext) o;
        return Objects.equals(context, jsContext.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context);
    }
}
