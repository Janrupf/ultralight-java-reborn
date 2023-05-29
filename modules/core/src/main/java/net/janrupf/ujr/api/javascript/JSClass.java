package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSClass;

import java.util.Objects;

public class JSClass {
    private final JSCJSClass clazz;

    public JSClass(JSCJSClass clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSClass)) return false;
        JSClass jsClass = (JSClass) o;
        return Objects.equals(clazz, jsClass.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz);
    }

    // Internal use only
    public JSCJSClass getClazz() {
        return clazz;
    }
}
