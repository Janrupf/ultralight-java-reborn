package net.janrupf.ujr.platform.jni.wrapper.listener;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.listener.UltralightLoadListener;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlLoadListener {
    private final UltralightLoadListener delegate;

    public JNIUlLoadListener(UltralightLoadListener delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public void onBeginLoading(UltralightView view, long frameId, boolean isMainFrame, String url) {
        delegate.onBeginLoading(view, frameId, isMainFrame, url);
    }

    @NativeAccess
    public void onFinishLoading(UltralightView view, long frameId, boolean isMainFrame, String url) {
        delegate.onFinishLoading(view, frameId, isMainFrame, url);
    }

    @NativeAccess
    public void onFailLoading(
            UltralightView view,
            long frameId,
            boolean isMainFrame,
            String url,
            String description,
            String errorDomain,
            int errorCode
    ) {
        delegate.onFailLoading(view, frameId, isMainFrame, url, description, errorDomain, errorCode);
    }

    @NativeAccess
    public void onWindowObjectReady(UltralightView view, long frameId, boolean isMainFrame, String url) {
        delegate.onWindowObjectReady(view, frameId, isMainFrame, url);
    }

    @NativeAccess
    public void onDOMReady(UltralightView view, long frameId, boolean isMainFrame, String url) {
        delegate.onDOMReady(view, frameId, isMainFrame, url);
    }

    @NativeAccess
    public void onUpdateHistory(UltralightView view) {
        delegate.onUpdateHistory(view);
    }
}
