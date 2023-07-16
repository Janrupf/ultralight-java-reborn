package net.janrupf.ujr.platform.jni.impl.javascript.callbacks;

import net.janrupf.ujr.api.javascript.JSContext;
import net.janrupf.ujr.api.javascript.JSObject;
import net.janrupf.ujr.api.javascript.callbacks.JSObjectGetPropertyNamesCallback;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSContext;
import net.janrupf.ujr.core.platform.abstraction.javascript.JSCJSObject;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

import java.util.Objects;

public class JNIJSCJSObjectGetPropertyNamesCallback {
    private final JSObjectGetPropertyNamesCallback delegate;

    public JNIJSCJSObjectGetPropertyNamesCallback(JSObjectGetPropertyNamesCallback delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public String[] getPropertyNames(JSCJSContext context, JSCJSObject object) {
        String[] result = delegate.onGetPropertyNames(new JSContext(context), new JSObject(object));
        Objects.requireNonNull(result, "The result of the JSObjectGetPropertyNamesCallback must not be null");

        return result;
    }
}
