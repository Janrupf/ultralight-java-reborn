package net.janrupf.ujr.platform.jni.wrapper.listener;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.cursor.UlCursor;
import net.janrupf.ujr.api.listener.UlMessageLevel;
import net.janrupf.ujr.api.listener.UlMessageSource;
import net.janrupf.ujr.api.listener.UltralightViewListener;
import net.janrupf.ujr.api.math.IntRect;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.impl.JNIUlView;

public class JNIUlViewListener {
    private final UltralightViewListener delegate;

    public JNIUlViewListener(UltralightViewListener delegate) {
        this.delegate = delegate;
    }

    @NativeAccess
    public void onChangeTitle(String title) {
        delegate.onChangeTitle(title);
    }

    @NativeAccess
    public void onChangeURL(String url) {
        delegate.onChangeURL(url);
    }

    @NativeAccess
    public void onChangeTooltip(String tooltip) {
        delegate.onChangeTooltip(tooltip);
    }

    @NativeAccess
    public void onChangeCursor(UlCursor cursor) {
        delegate.onChangeCursor(cursor);
    }

    @NativeAccess
    public void onAddConsoleMessage(
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    ) {
        delegate.onAddConsoleMessage(source, level, message, lineNumber, columnNumber, sourceId);
    }

    @NativeAccess
    public JNIUlView onCreateChildView(
            String openerUrl,
            String targetUrl,
            boolean isPopup,
            IntRect popupRect
    ) {
        return (JNIUlView) delegate.onCreateChildView(openerUrl, targetUrl, isPopup, popupRect).getImplementation();
    }

    @NativeAccess
    public JNIUlView onCreateInspectorView(boolean isLocal, String inspectedUrl) {
        return (JNIUlView) delegate.onCreateInspectorView(isLocal, inspectedUrl).getImplementation();
    }

    @NativeAccess
    public void onRequestClose() {
        delegate.onRequestClose();
    }
}
