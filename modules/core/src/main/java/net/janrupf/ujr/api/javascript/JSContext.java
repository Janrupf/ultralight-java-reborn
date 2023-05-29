package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;

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
}
