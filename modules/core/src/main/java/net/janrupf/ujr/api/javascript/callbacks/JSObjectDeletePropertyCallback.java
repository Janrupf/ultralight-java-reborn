package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;

/**
 * Callback called when a property of an object of a user-defined class is deleted.
 */
@FunctionalInterface
public interface JSObjectDeletePropertyCallback {
    /**
     * Called when a property of an object of a user-defined class is deleted.
     *
     * @param context the JavaScript context
     * @param object  the object being deleted
     * @param name    the name of the property
     * @return whether the property was deleted
     * @throws JavaScriptValueException if deleting the property fails
     */
    boolean onDeleteProperty(JSContext context, JSObject object, String name) throws JavaScriptValueException;
}
