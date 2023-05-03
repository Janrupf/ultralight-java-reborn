package net.janrupf.ujr.platform.jni.wrapper.listener;

import net.janrupf.ujr.api.listener.UltralightLoadListener;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlLoadListener {
    private final UltralightLoadListener delegate;

    public JNIUlLoadListener(UltralightLoadListener delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public void onBeginLoading(long frameId, boolean isMainFrame, String url) {
        delegate.onBeginLoading(frameId, isMainFrame, url);
    }

    @NativeAccess
    public void onFinishLoading(long frameId, boolean isMainFrame, String url) {
        delegate.onFinishLoading(frameId, isMainFrame, url);
    }

    @NativeAccess
    public void onFailLoading(
            long frameId,
            boolean isMainFrame,
            String url,
            String description,
            String errorDomain,
            int errorCode
    ) {
        delegate.onFailLoading(frameId, isMainFrame, url, description, errorDomain, errorCode);
    }

    @NativeAccess
    public void onWindowObjectReady(long frameId, boolean isMainFrame, String url) {
        delegate.onWindowObjectReady(frameId, isMainFrame, url);
    }

    @NativeAccess
    public void onDOMReady(long frameId, boolean isMainFrame, String url) {
        delegate.onDOMReady(frameId, isMainFrame, url);
    }

    @NativeAccess
    public void onUpdateHistory() {
        delegate.onUpdateHistory();
    }
}
