package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;

import java.util.Objects;

public class JSContext {
    private final JSCJSContext context;

    public JSContext(JSCJSContext context) {
        this.context = context;
    }

    /**
     * Retrieves the group of this context.
     *
     * @return the group
     */
    public JSContextGroup getGroup() {
        return new JSContextGroup(context.getGroup());
    }

    /**
     * Retrieves the global context of this context.
     *
     * @return the global context
     */
    public JSGlobalContext getGlobalContext() {
        return new JSGlobalContext(context.getGlobalContext());
    }

    // Internal use only
    public JSCJSContext getContext() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSContext)) return false;
        JSContext jsContext = (JSContext) o;
        return Objects.equals(context, jsContext.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context);
    }
}
