package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.*;

/**
 * Callback called when an object of a user-defined class is converted to a different type.
 */
@FunctionalInterface
public interface JSObjectConvertToTypeCallback {
    /**
     * Called when an object of a user-defined class is converted to a different type.
     *
     * @param context the JavaScript context
     * @param object  the object being converted
     * @param type    the type to convert to
     * @return the converted value
     * @throws JavaScriptValueException if the conversion fails
     */
    JSValue onConvertToType(JSContext context, JSObject object, JSType type) throws JavaScriptValueException;
}
