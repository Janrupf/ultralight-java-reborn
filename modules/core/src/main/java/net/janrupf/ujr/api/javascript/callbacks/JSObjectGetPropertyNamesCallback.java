package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;

/**
 * A callback that is called when the property names of an object of a user-defined class are enumerated.
 */
@FunctionalInterface
public interface JSObjectGetPropertyNamesCallback {
    /**
     * Called when the property names of an object of a user-defined class are enumerated.
     *
     * @param context the JavaScript context
     * @param object  the object being enumerated
     * @return the property names
     */
    String[] onGetPropertyNames(JSContext context, JSObject object);
}
