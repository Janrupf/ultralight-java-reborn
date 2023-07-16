package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSObject;

/**
 * Callback called when an object of a user-defined class is finalized.
 */
@FunctionalInterface
public interface JSObjectFinalizeCallback {
    /**
     * Called when an object of a user-defined class is finalized.
     *
     * @param object the object being finalized
     */
    void onFinalize(JSObject object);
}
