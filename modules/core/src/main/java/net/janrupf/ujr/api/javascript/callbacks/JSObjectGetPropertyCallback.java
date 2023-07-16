package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;

/**
 * Callback called when a property of an object of a user-defined class is accessed.
 */
@FunctionalInterface
public interface JSObjectGetPropertyCallback {
    /**
     * Called when a property of an object of a user-defined class is accessed.
     *
     * @param context the JavaScript context
     * @param object  the object being accessed
     * @param name    the name of the property
     * @return the value of the property
     * @throws JavaScriptValueException if retrieving the property fails
     */
    JSValue onGetProperty(JSContext context, JSValue object, String name) throws JavaScriptValueException;
}
