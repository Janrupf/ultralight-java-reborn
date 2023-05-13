package net.janrupf.ujr.platform.jni.wrapper.listener;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.listener.UltralightLoadListener;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.impl.JNIUlView;

import java.util.Objects;

public class JNIUlLoadListenerNative implements UltralightLoadListener {
    @NativeAccess
    private final long handle;

    private JNIUlLoadListenerNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public void onBeginLoading(UltralightView view, long frameId, boolean isMainFrame, String url) {
        nativeOnBeginLoading((JNIUlView) view.getImplementation(), frameId, isMainFrame, url);
    }

    private native void nativeOnBeginLoading(JNIUlView view, long frameId, boolean isMainFrame, String url);

    @Override
    public void onFinishLoading(UltralightView view, long frameId, boolean isMainFrame, String url) {
        nativeOnFinishLoading((JNIUlView) view.getImplementation(), frameId, isMainFrame, url);
    }

    private native void nativeOnFinishLoading(JNIUlView implementation, long frameId, boolean isMainFrame, String url);

    @Override
    public void onFailLoading(
            UltralightView view,
            long frameId,
            boolean isMainFrame,
            String url,
            String description,
            String errorDomain,
            int errorCode
    ) {
        nativeOnFailLoading((JNIUlView) view.getImplementation(), frameId, isMainFrame, url, description, errorDomain, errorCode);
    }

    private native void nativeOnFailLoading(
            JNIUlView implementation,
            long frameId,
            boolean isMainFrame,
            String url,
            String description,
            String errorDomain,
            int errorCode
    );

    @Override
    public void onWindowObjectReady(UltralightView view, long frameId, boolean isMainFrame, String url) {
        nativeOnWindowObjectReady((JNIUlView) view.getImplementation(), frameId, isMainFrame, url);
    }

    private native void nativeOnWindowObjectReady(
            JNIUlView implementation,
            long frameId,
            boolean isMainFrame,
            String url
    );

    @Override
    public void onDOMReady(UltralightView view, long frameId, boolean isMainFrame, String url) {
        nativeOnDOMReady((JNIUlView) view.getImplementation(), frameId, isMainFrame, url);
    }

    private native void nativeOnDOMReady(JNIUlView implementation, long frameId, boolean isMainFrame, String url);

    @Override
    public void onUpdateHistory(UltralightView view) {
        nativeOnUpdateHistory((JNIUlView) view.getImplementation());
    }

    private native void nativeOnUpdateHistory(JNIUlView implementation);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlLoadListenerNative)) return false;
        JNIUlLoadListenerNative that = (JNIUlLoadListenerNative) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
