package net.janrupf.ujr.api.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.JSValue;
import net.janrupf.ujr.api.javascript.JavaScriptValueException;

/**
 * Callback called when an object of a user-defined class is checked for being an instance of another object.
 */
@FunctionalInterface
public interface JSObjectHasInstanceCallback {
    /**
     * Called when an object of a user-defined class is checked for being an instance of another object.
     *
     * @param context          the JavaScript context
     * @param constructor      the constructor of the class
     * @param possibleInstance the object that is checked for being an instance of the class
     * @return whether the object is an instance of the class
     * @throws JavaScriptValueException if checking fails
     */
    boolean onHasInstance(JSContext context, JSObject constructor, JSValue possibleInstance) throws JavaScriptValueException;
}
