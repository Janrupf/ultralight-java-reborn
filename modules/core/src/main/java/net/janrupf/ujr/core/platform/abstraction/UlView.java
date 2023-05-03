package net.janrupf.ujr.core.platform.abstraction;

import net.janrupf.ujr.api.event.UlKeyEvent;
import net.janrupf.ujr.api.event.UlMouseEvent;
import net.janrupf.ujr.api.event.UlScrollEvent;
import net.janrupf.ujr.api.exception.JavascriptException;
import net.janrupf.ujr.api.listener.UltralightLoadListener;
import net.janrupf.ujr.api.listener.UltralightViewListener;
import net.janrupf.ujr.api.surface.UltralightSurface;

public interface UlView {
    String url();

    String title();

    long width();

    long height();

    double deviceScale();

    void setDeviceScale(double scale);

    boolean isAccelerated();

    boolean isTransparent();

    boolean isLoading();

    // TODO: RenderTarget

    UltralightSurface surface();

    void loadHTML(String html, String url, boolean addToHistory);

    void loadURL(String url);

    void resize(long width, long height);

    // TODO: LockJSContext

    // Skipped: JavaScriptVM

    String evaluateScript(String script) throws JavascriptException;

    boolean canGoBack();

    boolean canGoForward();

    void goBack();

    void goForward();

    void goToHistoryOffset(int offset);

    void reload();

    void stop();

    void focus();

    void unfocus();

    boolean hasFocus();

    boolean hasInputFocus();

    void fireKeyEvent(UlKeyEvent event);

    void fireMouseEvent(UlMouseEvent event);

    void fireScrollEvent(UlScrollEvent event);

    void setViewListener(UltralightViewListener listener);

    UltralightViewListener viewListener();

    void setLoadListener(UltralightLoadListener listener);

    UltralightLoadListener loadListener();

    void setNeedsPaint(boolean needsPaint);

    boolean needsPaint();

    void createLocalInspectorView();
}
