package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;

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
