package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;

/**
 * A callback that is called when the existence of a property on object of a user-defined class is checked.
 */
@FunctionalInterface
public interface JSObjectHasPropertyCallback {
    /**
     * Called when the existence of a property on object of a user-defined class is checked.
     *
     * @param context the JavaScript context
     * @param object the object being checked
     * @param name the name of the property
     * @return whether the object has a property with the specified name
     */
    boolean onHasProperty(JSContext context, JSObject object, String name);
}
