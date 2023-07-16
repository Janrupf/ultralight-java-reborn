package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;

/**
 * Callback called when an object of a user-defined class is initialized.
 */
@FunctionalInterface
public interface JSObjectInitializeCallback {
    /**
     * Called when an object of a user-defined class is initialized.
     *
     * @param context the JavaScript context
     * @param object the object being initialized
     */
    void onInitialize(JSContext context, JSObject object);
}
