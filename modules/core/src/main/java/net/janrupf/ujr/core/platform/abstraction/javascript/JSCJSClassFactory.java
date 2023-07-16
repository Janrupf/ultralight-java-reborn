package net.janrupf.ujr.core.platform.abstraction.javascript;

import net.janrupf.ujr.api.javascript.JSClassBuilder;

public interface JSCJSClassFactory {
    JSCJSClass newClass(JSClassBuilder builder);
}
