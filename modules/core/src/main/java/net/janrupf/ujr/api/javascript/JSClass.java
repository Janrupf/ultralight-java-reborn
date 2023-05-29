package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClass;

public class JSClass {
    private final JSCJSClass clazz;

    public JSClass(JSCJSClass clazz) {
        this.clazz = clazz;
    }

    // Internal use only
    public JSCJSClass getClazz() {
        return clazz;
    }
}
