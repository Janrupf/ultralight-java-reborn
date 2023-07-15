package net.janrupf.ujr.core.platform.abstraction.javascript;

import net.janrupf.ujr.api.javascript.JavaScriptValueException;

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

    JSCJSObject makeArray(JSCJSValue[] nativeValues) throws JavaScriptValueException;

    JSCJSObject makeDate(JSCJSValue[] nativeArguments) throws JavaScriptValueException;

    JSCJSObject makeError(JSCJSValue[] nativeArguments) throws JavaScriptValueException;

    JSCJSObject makeRegExp(JSCJSValue[] nativeArguments) throws JavaScriptValueException;

    JSCJSObject makeFunction(String name, String[] parameterNames, String body, String sourceURL, int startingLineNumber) throws JavaScriptValueException;

    JSCJSValue evaluateScript(String script, JSCJSObject thisObject, String sourceURL, int startingLineNumber) throws JavaScriptValueException;

    void checkScriptSyntax(String script, String sourceURL, int startingLineNumber) throws JavaScriptValueException;

    void collectGarbage();
}
