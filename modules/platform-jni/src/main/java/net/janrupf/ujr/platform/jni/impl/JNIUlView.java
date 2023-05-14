package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.bitmap.UltralightBitmapSurface;
import net.janrupf.ujr.api.event.UlKeyEvent;
import net.janrupf.ujr.api.event.UlMouseEvent;
import net.janrupf.ujr.api.event.UlScrollEvent;
import net.janrupf.ujr.api.exception.JavascriptException;
import net.janrupf.ujr.api.listener.UltralightLoadListener;
import net.janrupf.ujr.api.listener.UltralightViewListener;
import net.janrupf.ujr.api.surface.UltralightSurface;
import net.janrupf.ujr.core.platform.abstraction.UlBitmapSurface;
import net.janrupf.ujr.core.platform.abstraction.UlView;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.wrapper.listener.JNIUlLoadListener;
import net.janrupf.ujr.platform.jni.wrapper.listener.JNIUlViewListener;
import net.janrupf.ujr.platform.jni.wrapper.surface.JNIUlSurface;

import java.util.Objects;

public class JNIUlView implements UlView {
    @NativeAccess
    private final long handle;

    @NativeAccess
    private final long nativeCollector;

    private JNIUlView() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public String url() {
        return nativeUrl();
    }

    private native String nativeUrl();

    @Override
    public String title() {
        return nativeTitle();
    }

    private native String nativeTitle();

    @Override
    public long width() {
        return nativeWidth();
    }

    private native long nativeWidth();

    @Override
    public long height() {
        return nativeHeight();
    }

    private native long nativeHeight();

    @Override
    public double deviceScale() {
        return nativeDeviceScale();
    }

    private native double nativeDeviceScale();

    @Override
    public void setDeviceScale(double scale) {
        nativeSetDeviceScale(scale);
    }

    private native void nativeSetDeviceScale(double scale);

    @Override
    public boolean isAccelerated() {
        return nativeIsAccelerated();
    }

    private native boolean nativeIsAccelerated();

    @Override
    public boolean isTransparent() {
        return nativeIsTransparent();
    }

    private native boolean nativeIsTransparent();

    @Override
    public boolean isLoading() {
        return nativeIsLoading();
    }

    private native boolean nativeIsLoading();

    @Override
    public UltralightSurface surface() {
        Object surface = nativeSurface();

        if (surface instanceof JNIUlBitmapSurface) {
            surface = new UltralightBitmapSurface((UlBitmapSurface) surface);
        } else if (surface instanceof JNIUlSurface) {
            return ((JNIUlSurface) surface).getDelegate();
        }

        return (UltralightSurface) surface;
    }

    private native Object nativeSurface();

    @Override
    public void loadHTML(String html, String url, boolean addToHistory) {
        nativeLoadHTML(html, url, addToHistory);
    }

    private native void nativeLoadHTML(String html, String url, boolean addToHistory);

    @Override
    public void loadURL(String url) {
        nativeLoadURL(url);
    }

    private native void nativeLoadURL(String url);

    @Override
    public void resize(long width, long height) {
        nativeResize(width, height);
    }

    private native void nativeResize(long width, long height);

    @Override
    public String evaluateScript(String script) throws JavascriptException {
        return nativeEvaluateScript(script);
    }

    private native String nativeEvaluateScript(String script) throws JavascriptException;

    @Override
    public boolean canGoBack() {
        return nativeCanGoBack();
    }

    private native boolean nativeCanGoBack();

    @Override
    public boolean canGoForward() {
        return nativeCanGoForward();
    }

    private native boolean nativeCanGoForward();

    @Override
    public void goBack() {
        nativeGoBack();
    }

    private native void nativeGoBack();

    @Override
    public void goForward() {
        nativeGoForward();
    }

    private native void nativeGoForward();

    @Override
    public void goToHistoryOffset(int offset) {
        nativeGoToHistoryOffset(offset);
    }

    private native void nativeGoToHistoryOffset(int offset);

    @Override
    public void reload() {
        nativeReload();
    }

    private native void nativeReload();

    @Override
    public void stop() {
        nativeStop();
    }

    private native void nativeStop();

    @Override
    public void focus() {
        nativeFocus();
    }

    private native void nativeFocus();

    @Override
    public void unfocus() {
        nativeUnfocus();
    }

    private native void nativeUnfocus();

    @Override
    public boolean hasFocus() {
        return nativeHasFocus();
    }

    private native boolean nativeHasFocus();

    @Override
    public boolean hasInputFocus() {
        return nativeHasInputFocus();
    }

    private native boolean nativeHasInputFocus();

    @Override
    public void fireKeyEvent(UlKeyEvent event) {
        nativeFireKeyEvent(event);
    }

    private native void nativeFireKeyEvent(UlKeyEvent event);

    @Override
    public void fireMouseEvent(UlMouseEvent event) {
        nativeFireMouseEvent(event);
    }

    private native void nativeFireMouseEvent(UlMouseEvent event);

    @Override
    public void fireScrollEvent(UlScrollEvent event) {
        nativeFireScrollEvent(event);
    }

    private native void nativeFireScrollEvent(UlScrollEvent event);

    @Override
    public void setViewListener(UltralightViewListener listener) {
        nativeSetViewListener(new JNIUlViewListener(listener));
    }

    private native void nativeSetViewListener(JNIUlViewListener listener);

    @Override
    public UltralightViewListener viewListener() {
        return nativeViewListener();
    }

    private native UltralightViewListener nativeViewListener();

    @Override
    public void setLoadListener(UltralightLoadListener listener) {
        nativeSetLoadListener(new JNIUlLoadListener(listener));
    }

    private native void nativeSetLoadListener(JNIUlLoadListener listener);

    @Override
    public UltralightLoadListener loadListener() {
        return nativeLoadListener();
    }

    private native UltralightLoadListener nativeLoadListener();

    @Override
    public void setNeedsPaint(boolean needsPaint) {
        nativeSetNeedsPaint(needsPaint);
    }

    private native void nativeSetNeedsPaint(boolean needsPaint);

    @Override
    public boolean needsPaint() {
        return nativeNeedsPaint();
    }

    private native boolean nativeNeedsPaint();

    @Override
    public void createLocalInspectorView() {
        nativeCreateLocalInspectorView();
    }

    private native void nativeCreateLocalInspectorView();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlView)) return false;
        JNIUlView jniUlView = (JNIUlView) o;
        return handle == jniUlView.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
