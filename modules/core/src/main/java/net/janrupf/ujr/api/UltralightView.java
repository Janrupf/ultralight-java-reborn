package net.janrupf.ujr.api;

// TODO: Fix doc comment references

import net.janrupf.ujr.api.exception.JavascriptException;
import net.janrupf.ujr.api.filesystem.UltralightFilesystem;
import net.janrupf.ujr.core.platform.abstraction.UlView;

/**
 * The View class is used to load and display web content.
 * <p>
 * View is an offscreen web-page container that can be used to display web-content in your
 * application.
 * <p>
 * You can load content into a View via View::LoadURL() or View::LoadHTML() and interact with it
 * via View::FireMouseEvent() and similar API.
 * <p>
 * When displaying a View, the API is different depending on whether you are using the CPU
 * renderer or the GPU renderer:
 * <p>
 * When using the CPU renderer, you would get the underlying pixel-buffer surface for a View via
 * View::surface().
 * <p>
 * When using the GPU renderer, you would get the underlying render target and texture information
 * via View::render_target().
 *
 * @implNote The API is not currently thread-safe, all calls must be made on the same thread that the
 * Renderer/App was created on.
 */
public class UltralightView {
    private final UlView view;

    /* package */ UltralightView(UlView view) {
        this.view = view;
    }

    /**
     * Retrieves the URL of the current page loaded into this View.
     *
     * @return the url of the current page loaded into this View, if any
     */
    public String url() {
        return view.url();
    }

    /**
     * Retrieves the title of the current page loaded into this View.
     *
     * @return the title of the current page loaded into this View, if any
     */
    public String title() {
        return view.title();
    }

    /**
     * Retrieves the width of this View.
     *
     * @return the width of this View in pixels
     */
    public long width() {
        return view.width();
    }

    /**
     * Retrieves the height of this View.
     *
     * @return the height of this View in pixels
     */
    public long height() {
        return view.height();
    }

    /**
     * Retrieves  the device scale, ie. the amount to scale page units to screen pixels.
     * <p>
     * For example, a value of 1.0 is equivalent to 100% zoom. A value of 2.0 is 200% zoom.
     *
     * @return the device scale of this View
     */
    public double deviceScale() {
        return view.deviceScale();
    }

    /**
     * Sets the device scale.
     *
     * @param scale the new device scale
     */
    public void setDeviceScale(double scale) {
        view.setDeviceScale(scale);
    }

    /**
     * Determines whether the View is GPU-accelerated. If this is false, the page will be rendered
     * via the CPU renderer.
     *
     * @return true if the View is GPU-accelerated, false otherwise
     */
    public boolean isAccelerated() {
        return view.isAccelerated();
    }

    /**
     * Determines whether the View supports transparent backgrounds.
     *
     * @return true if the View supports transparent backgrounds, false otherwise
     */
    public boolean isTransparent() {
        return view.isTransparent();
    }

    /**
     * Determines whether the main frame of the View is currently loading.
     *
     * @return true if the main frame of the View is currently loading, false otherwise
     */
    public boolean isLoading() {
        return view.isLoading();
    }

    // TODO: renderTarget

    // TODO: surface

    /**
     * Load a raw string of HTML, the View will navigate to it as a new page.
     *
     * @param html the raw HTML string to load
     */
    public void loadHTML(String html) {
        loadHTML(html, "");
    }

    /**
     * Load a raw string of HTML, the View will navigate to it as a new page.
     *
     * @param html the raw HTML string to load
     * @param url  an optional URL for this load (to make it appear as if we loaded this HTML
     *             from a certain URL). Can be used for resolving relative URLs and cross-origin
     *             rules.
     */
    public void loadHTML(String html, String url) {
        loadHTML(html, url, false);
    }

    /**
     * Load a raw string of HTML, the View will navigate to it as a new page.
     *
     * @param html         the raw HTML string to load
     * @param url          an optional URL for this load (to make it appear as if we loaded this HTML
     *                     from a certain URL). Can be used for resolving relative URLs and cross-origin
     *                     rules.
     * @param addToHistory whether this load should be added to the session's history
     *                     (eg, the back/forward list).
     */
    public void loadHTML(String html, String url, boolean addToHistory) {
        view.loadHTML(html, url, addToHistory);
    }

    /**
     * Load a URL, the View will navigate to it as a new page.
     *
     * @param url the URL to load
     * @implNote You can use File URLs (eg, file:///page.html) but you must define your own FileSystem
     * implementation. {@link UltralightPlatform#setFilesystem(UltralightFilesystem)}
     */
    public void loadURL(String url) {
        view.loadURL(url);
    }

    /**
     * Resize View to a certain size.
     *
     * @param width  the new width, in pixels
     * @param height the new height, in pixel.
     */
    public void resize(long width, long height) {
        view.resize(width, height);
    }

    // TODO: lockJSContext

    // Skipped javascriptVM

    // TODO: fix doc comment references

    /**
     * Helper function to evaluate a raw string of JavaScript and return the result as a String.
     *
     * @param script a string of Javascript to evaluate in the main frame
     * @return the javascript result typecast to a String
     * @throws JavascriptException if an exception occurred while evaluating the script
     * @implNote You do not need to lock the JS context, it is done automatically.
     * @apiNote If you need lower-level access to native JavaScript values, you should instead lock
     * the JS context and call JSEvaluateScript() in the JavaScriptCore C API.
     */
    public String evaluateScript(String script) throws JavascriptException {
        return view.evaluateScript(script);
    }

    /**
     * Determines whether we can navigate backwards in history.
     *
     * @return true if we can navigate backwards in history, false otherwise
     */
    public boolean canGoBack() {
        return view.canGoBack();
    }

    /**
     * Determines whether we can navigate forwards in history.
     *
     * @return true if we can navigate forwards in history, false otherwise
     */
    public boolean canGoForward() {
        return view.canGoForward();
    }

    /**
     * Navigate backwards in history.
     */
    public void goBack() {
        view.goBack();
    }

    /**
     * Navigate forwards in history.
     */
    public void goForward() {
        view.goForward();
    }

    /**
     * Navigate to an arbitrary offset in history.
     *
     * @param offset the offset to navigate to, where a negative value indicates a backwards offset
     */
    public void goToHistoryOffset(int offset) {
        view.goToHistoryOffset(offset);
    }

    /**
     * Reload current page.
     */
    public void reload() {
        view.reload();
    }

    /**
     * Stop all page loads.
     */
    public void stop() {
        view.stop();
    }

    /**
     * Give focus to the View.
     * <p>
     * You should call this to give visual indication that the View has input focus (changes active
     * text selection colors, for example).
     */
    public void focus() {
        view.focus();
    }

    /**
     * Remove focus from the View and unfocus any focused input elements.
     * <p>
     * You should call this to give visual indication that the View has lost input focus.
     */
    public void unfocus() {
        view.unfocus();
    }

    /**
     * Determines whether the View has focus.
     *
     * @return true if the View has focus, false otherwise
     */
    public boolean hasFocus() {
        return view.hasFocus();
    }

    /**
     * Determines whether the View has an input element with visible keyboard focus (indicated by a
     * blinking caret).
     * <p>
     * You can use this to decide whether the View should consume keyboard input events
     * (useful in games with mixed UI and key handling).
     *
     * @return true if the View has an input element with keyboard focus, false otherwise
     */
    public boolean hasInputFocus() {
        return view.hasInputFocus();
    }

    // TODO: fireKeyEvent

    // TODO: fireMouseEvent

    // TODO: fireScrollEvent

    // TODO: setViewListener

    // TODO: viewListener

    // TODO: setLoadListener

    // TODO: loadListener

    /**
     * Set whether this View should be repainted during the next call to {@link UltralightRenderer#render()}.
     *
     * @param needsPaint true if this View should be repainted during the next call to {@link UltralightRenderer#render()},
     *                   false otherwise
     * @implNote This flag is automatically set whenever the page content changes but you can set it
     * directly in case you need to force a repaint.
     */
    public void setNeedsPaint(boolean needsPaint) {
        view.setNeedsPaint(needsPaint);
    }

    /**
     * Determines whether this View should be repainted during the next call to
     * {@link UltralightRenderer#render()}.
     *
     * @return true if this View should be repainted during the next call to {@link UltralightRenderer#render()},
     * false otherwise
     */
    public boolean needsPaint() {
        return view.needsPaint();
    }

    // TODO: fix doc comment references

    /**
     * Create an Inspector View to inspect / debug this View locally.
     * <p>
     * This will only succeed if you have the inspector assets in your filesystem - the inspector
     * will look for {@code file:///inspector/Main.html} when it first loads.
     * <p>
     * You must handle ViewListener::OnCreateInspectorView so that the library has a View to display
     * the inspector in. This function will call this event only if an inspector view is not
     * currently active.
     */
    public void createLocalInspectorView() {
        view.createLocalInspectorView();
    }
}
