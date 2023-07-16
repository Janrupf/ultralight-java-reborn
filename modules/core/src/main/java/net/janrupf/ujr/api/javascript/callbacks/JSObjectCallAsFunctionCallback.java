package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;

/**
 * A callback that is called when an object of a user-defined class is called as a function.
 */
@FunctionalInterface
public interface JSObjectCallAsFunctionCallback {
    /**
     * Called when an object of a user-defined class is called as a function.
     *
     * @param context      the JavaScript context
     * @param functionName the name of the function being called
     * @param function     the function being called
     * @param thisObject   the this object
     * @param arguments    the arguments passed to the function
     * @return the return value of the function
     * @throws JavaScriptValueException if the function throws an exception
     */
    JSValue onCallAsFunction(
            JSContext context,
            String functionName,
            JSObject function,
            JSObject thisObject,
            JSValue[] arguments
    ) throws JavaScriptValueException;
}
