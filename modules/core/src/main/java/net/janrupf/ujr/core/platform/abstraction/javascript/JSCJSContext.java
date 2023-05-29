package net.janrupf.ujr.core.platform.abstraction.javascript;

public interface JSCJSContext {
    JSCJSGlobalContext getGlobalContext();

    JSCJSContextGroup getGroup();
}
