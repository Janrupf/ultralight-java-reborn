package net.janrupf.ujr.core.platform.abstraction.javascript;

public interface JSCJSGlobalContextFactory {
    JSCJSGlobalContext create(JSCJSClass globalObjectClass);

    JSCJSGlobalContext createInGroup(JSCJSContextGroup group, JSCJSClass globalObjectClass);
}
