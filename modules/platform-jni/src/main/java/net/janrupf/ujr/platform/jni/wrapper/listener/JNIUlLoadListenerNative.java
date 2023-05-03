package net.janrupf.ujr.platform.jni.wrapper.listener;

import net.janrupf.ujr.api.listener.UltralightLoadListener;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;

public class JNIUlLoadListenerNative implements UltralightLoadListener {
    @NativeAccess
    private final long view;

    @NativeAccess
    private final long handle;

    private JNIUlLoadListenerNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public void onBeginLoading(long frameId, boolean isMainFrame, String url) {
        nativeOnBeginLoading(frameId, isMainFrame, url);
    }

    private native void nativeOnBeginLoading(long frameId, boolean isMainFrame, String url);

    @Override
    public void onFinishLoading(long frameId, boolean isMainFrame, String url) {
        nativeOnFinishLoading(frameId, isMainFrame, url);
    }

    private native void nativeOnFinishLoading(long frameId, boolean isMainFrame, String url);

    @Override
    public void onFailLoading(
            long frameId,
            boolean isMainFrame,
            String url,
            String description,
            String errorDomain,
            int errorCode
    ) {
        nativeOnFailLoading(frameId, isMainFrame, url, description, errorDomain, errorCode);
    }

    private native void nativeOnFailLoading(
            long frameId,
            boolean isMainFrame,
            String url,
            String description,
            String errorDomain,
            int errorCode
    );

    @Override
    public void onWindowObjectReady(long frameId, boolean isMainFrame, String url) {
        nativeOnWindowObjectReady(frameId, isMainFrame, url);
    }

    private native void nativeOnWindowObjectReady(long frameId, boolean isMainFrame, String url);

    @Override
    public void onDOMReady(long frameId, boolean isMainFrame, String url) {
        nativeOnDOMReady(frameId, isMainFrame, url);
    }

    private native void nativeOnDOMReady(long frameId, boolean isMainFrame, String url);

    @Override
    public void onUpdateHistory() {
        nativeOnUpdateHistory();
    }

    private native void nativeOnUpdateHistory();
}
