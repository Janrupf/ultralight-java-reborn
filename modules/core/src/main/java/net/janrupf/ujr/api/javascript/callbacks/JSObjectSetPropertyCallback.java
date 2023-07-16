package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;

/**
 * A callback that is called when a property on object of a user-defined class is set.
 */
@FunctionalInterface
public interface JSObjectSetPropertyCallback {
    /**
     * Called when a property on object of a user-defined class is set.
     *
     * @param context the JavaScript context
     * @param object  the object on which the property is being set
     * @param name    the name of the property
     * @param value   the new value of the property
     * @return {@code false} to delegate setting the property to the parent class, {@code true} to indicate that the
     * property was set successfully
     * @throws JavaScriptValueException if setting the property fails
     */
    boolean onSetProperty(JSContext context, JSObject object, String name, JSValue value) throws JavaScriptValueException;
}
