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
    public void onChangeTitle(UltralightView view, String title) {
        delegate.onChangeTitle(view, title);
    }

    @NativeAccess
    public void onChangeURL(UltralightView view, String url) {
        delegate.onChangeURL(view, url);
    }

    @NativeAccess
    public void onChangeTooltip(UltralightView view, String tooltip) {
        delegate.onChangeTooltip(view, tooltip);
    }

    @NativeAccess
    public void onChangeCursor(UltralightView view, UlCursor cursor) {
        delegate.onChangeCursor(view, cursor);
    }

    @NativeAccess
    public void onAddConsoleMessage(
            UltralightView view,
            UlMessageSource source,
            UlMessageLevel level,
            String message,
            long lineNumber,
            long columnNumber,
            String sourceId
    ) {
        delegate.onAddConsoleMessage(view, source, level, message, lineNumber, columnNumber, sourceId);
    }

    @NativeAccess
    public JNIUlView onCreateChildView(
            UltralightView view,
            String openerUrl,
            String targetUrl,
            boolean isPopup,
            IntRect popupRect
    ) {
        return (JNIUlView) delegate.onCreateChildView(view, openerUrl, targetUrl, isPopup, popupRect).getImplementation();
    }

    @NativeAccess
    public JNIUlView onCreateInspectorView(UltralightView view, boolean isLocal, String inspectedUrl) {
        return (JNIUlView) delegate.onCreateInspectorView(view, isLocal, inspectedUrl).getImplementation();
    }

    @NativeAccess
    public void onRequestClose(UltralightView view) {
        delegate.onRequestClose(view);
    }
}
