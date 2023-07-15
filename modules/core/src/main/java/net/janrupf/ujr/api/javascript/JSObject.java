package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;

/**
 * A JavaScript object.
 */
public class JSObject extends JSValue {
    public JSObject(JSCJSObject object) {
        super(object);
    }

    // Internal use only
    public JSCJSObject getObject() {
        return (JSCJSObject) getValue();
    }
}
