package net.janrupf.ujr.platform.jni.wrapper.listener;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.cursor.UlCursor;
import net.janrupf.ujr.api.listener.UlMessageLevel;
import net.janrupf.ujr.api.listener.UlMessageSource;
import net.janrupf.ujr.api.listener.UltralightViewListener;
import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.impl.JNIUlView;

public class JNIUlViewListenerNative implements UltralightViewListener {
    @NativeAccess
    private final long handle;

    private JNIUlViewListenerNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public void onChangeTitle(UltralightView view, String title) {
        nativeOnChangeTitle((JNIUlView) view.getImplementation(), title);
    }

    private native void nativeOnChangeTitle(JNIUlView implementation, String title);

    @Override
    public void onChangeURL(UltralightView view, String url) {
        nativeOnChangeURL((JNIUlView) view.getImplementation(), url);
    }

    private native void nativeOnChangeURL(JNIUlView implementation, String url);

    @Override
    public void onChangeTooltip(UltralightView view, String tooltip) {
        nativeOnChangeTooltip((JNIUlView) view.getImplementation(), tooltip);
    }

    private native void nativeOnChangeTooltip(JNIUlView implementation, String tooltip);

    @Override
    public void onChangeCursor(UltralightView view, UlCursor cursor) {
        nativeOnChangeCursor((JNIUlView) view.getImplementation(), cursor);
    }

    private native void nativeOnChangeCursor(JNIUlView implementation, UlCursor cursor);

    @Override
    public void onAddConsoleMessage(
            UltralightView view,
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    ) {
        nativeOnAddConsoleMessage(
                (JNIUlView) view.getImplementation(),
                source,
                level,
                message,
                lineNumber,
                columnNumber,
                sourceId
        );
    }

    private native void nativeOnAddConsoleMessage(
            JNIUlView implementation,
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    );

    @Override
    public UltralightView onCreateChildView(
            UltralightView view,
            String openerUrl,
            String targetUrl,
            boolean isPopup,
            IntRect popupRect
    ) {
        return new UltralightView(nativeOnCreateChildView(
                (JNIUlView) view.getImplementation(),
                openerUrl,
                targetUrl,
                isPopup,
                popupRect
        ));
    }

    private native JNIUlView nativeOnCreateChildView(
            JNIUlView implementation,
            String openerUrl,
            String targetUrl,
            boolean isPopup,
            IntRect popupRect
    );

    @Override
    public UltralightView onCreateInspectorView(UltralightView view, boolean isLocal, String inspectedUrl) {
        return new UltralightView(nativeOnCreateInspectorView(
                (JNIUlView) view.getImplementation(),
                isLocal,
                inspectedUrl
        ));
    }

    private native JNIUlView nativeOnCreateInspectorView(JNIUlView implementation, boolean isLocal, String inspectedUrl);

    @Override
    public void onRequestClose(UltralightView view) {
        nativeOnRequestClose((JNIUlView) view.getImplementation());
    }

    private native void nativeOnRequestClose(JNIUlView implementation);
}
