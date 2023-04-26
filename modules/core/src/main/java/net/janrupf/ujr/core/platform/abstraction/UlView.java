package net.janrupf.ujr.core.platform.abstraction;

import net.janrupf.ujr.api.exception.JavascriptException;

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

    // TODO: Surface

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

    // TODO: FireKeyEvent

    // TODO: FireMouseEvent

    // TODO: FireScrollEvent

    // TODO: set_view_listener

    // TODO: view_listener

    // TODO: set_load_listener

    // TODO: load_listener

    void setNeedsPaint(boolean needsPaint);

    boolean needsPaint();

    void createLocalInspectorView();
}
