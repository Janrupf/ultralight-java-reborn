package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;

/**
 * A callback that is called when an object of a user-defined class is constructed.
 */
@FunctionalInterface
public interface JSObjectCallAsConstructorCallback {
    /**
     * Called when an object of a user-defined class is constructed.
     *
     * @param context     the JavaScript context
     * @param constructor the constructor being called
     * @param arguments   the arguments passed to the constructor
     * @return the constructed object
     * @throws JavaScriptValueException if the constructor throws an exception
     */
    JSObject onCallAsConstructor(JSContext context, JSObject constructor, JSValue[] arguments) throws JavaScriptValueException;
}
