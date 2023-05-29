package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSGlobalContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSGlobalContextFactory;
import net.janrupf.ujr.core.util.ApiProvider;

import java.util.Objects;

/**
 * A global JavaScript execution context.
 */
public class JSGlobalContext extends JSContext {
    private static final ApiProvider<JSCJSGlobalContextFactory> FACTORY_PROVIDER =
            new ApiProvider<>(JSCJSGlobalContextFactory.class);

    private final JSCJSGlobalContext context;

    /**
     * Creates a new global JavaScript execution context within its own group.
     *
     * @param clazz the class of the global object
     */
    public JSGlobalContext(JSClass clazz) {
        this(FACTORY_PROVIDER.require().create(clazz != null ? clazz.getClazz() : null));
    }

    /**
     * Creates a new global JavaScript execution context within the specified group.
     *
     * @param group the group to create the context in
     * @param clazz the class of the global object
     */
    public JSGlobalContext(JSContextGroup group, JSClass clazz) {
        this(FACTORY_PROVIDER.require().createInGroup(group.getGroup(), clazz != null ? clazz.getClazz() : null));
    }

    public JSGlobalContext(JSCJSGlobalContext context) {
        super(context);
        this.context = context;
    }

    @Override
    public JSGlobalContext getGlobalContext() {
        return this;
    }

    /**
     * Sets the remote debugging name of the context.
     *
     * @param name the new remote debugging name
     */
    public void setName(String name) {
        context.setName(name);
    }

    /**
     * Retrieves the remote debugging name of the context.
     *
     * @return the remote debugging name
     */
    public String getName() {
        return context.getName();
    }

    // Internal use only
    @Override
    public JSCJSGlobalContext getContext() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSGlobalContext)) return false;
        if (!super.equals(o)) return false;
        JSGlobalContext that = (JSGlobalContext) o;
        return Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), context);
    }
}
