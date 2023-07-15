package net.janrupf.ujr.core.platform.abstraction.javascript;

public interface JSCJSContext {
    JSCJSGlobalContext getGlobalContext();

    JSCJSContextGroup getGroup();

    JSCJSValue makeUndefined();

    JSCJSValue makeNull();

    JSCJSValue makeBoolean(boolean value);

    JSCJSValue makeNumber(double value);

    JSCJSValue makeString(String value);

    JSCJSValue makeSymbol(String description);

    JSCJSValue makeFromJSONString(String jsonValue);
}
