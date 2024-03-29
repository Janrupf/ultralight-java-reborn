package net.janrupf.ujr.api.javascript;

import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroup;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContextGroupFactory;
import net.janrupf.ujr.core.util.ApiProvider;

import java.util.Objects;

/**
 * A group that associates JavaScript contexts with one another.
 * Contexts in the same group may share and exchange JavaScript objects.
 * <p>
 * Instances of this class represent JavaScript VM's
 */
public class JSContextGroup {
    private static final ApiProvider<JSCJSContextGroupFactory> FACTORY_PROVIDER =
            new ApiProvider<>(JSCJSContextGroupFactory.class);

    private final JSCJSContextGroup group;

    /**
     * Creates a new JavaScript context group.
     * <p>
     * A {@link JSContextGroup} associates JavaScript contexts with one another.
     * Contexts in the same group may share and exchange JavaScript objects. Sharing and/or exchanging
     * JavaScript objects between contexts in different groups will produce undefined behavior.
     * When objects from the same context group are used in multiple threads, explicit
     * synchronization is required.
     */
    public JSContextGroup() {
        this(FACTORY_PROVIDER.require().create());
    }

    public JSContextGroup(JSCJSContextGroup group) {
        this.group = group;
    }

    // Internal use only
    public JSCJSContextGroup getGroup() {
        return group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JSContextGroup)) return false;
        JSContextGroup that = (JSContextGroup) o;
        return Objects.equals(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(group);
    }
}
