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
    private final long view;

    @NativeAccess
    private final long handle;

    private JNIUlViewListenerNative() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public void onChangeTitle(String title) {
        nativeOnChangeTitle(title);
    }

    private native void nativeOnChangeTitle(String title);

    @Override
    public void onChangeURL(String url) {
        nativeOnChangeURL(url);
    }

    private native void nativeOnChangeURL(String url);

    @Override
    public void onChangeTooltip(String tooltip) {
        nativeOnChangeTooltip(tooltip);
    }

    private native void nativeOnChangeTooltip(String tooltip);

    @Override
    public void onChangeCursor(UlCursor cursor) {
        nativeOnChangeCursor(cursor);
    }

    private native void nativeOnChangeCursor(UlCursor cursor);

    @Override
    public void onAddConsoleMessage(
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    ) {
        nativeOnAddConsoleMessage(source, level, message, lineNumber, columnNumber, sourceId);
    }

    private native void nativeOnAddConsoleMessage(
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    );

    @Override
    public UltralightView onCreateChildView(String openerUrl, String targetUrl, boolean isPopup, IntRect popupRect) {
        return new UltralightView(nativeOnCreateChildView(openerUrl, targetUrl, isPopup, popupRect));
    }

    private native JNIUlView nativeOnCreateChildView(String openerUrl, String targetUrl, boolean isPopup, IntRect popupRect);

    @Override
    public UltralightView onCreateInspectorView(boolean isLocal, String inspectedUrl) {
        return new UltralightView(nativeOnCreateInspectorView(isLocal, inspectedUrl));
    }

    private native JNIUlView nativeOnCreateInspectorView(boolean isLocal, String inspectedUrl);

    @Override
    public void onRequestClose() {
        nativeOnRequestClose();
    }

    private native void nativeOnRequestClose();
}
