package net.janrupf.ujr.platform.jni.impl;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.config.UlViewConfig;
import net.janrupf.ujr.core.platform.abstraction.UlRenderer;
import net.janrupf.ujr.core.platform.abstraction.UlSession;
import net.janrupf.ujr.core.platform.abstraction.UlView;
import net.janrupf.ujr.platform.jni.ffi.NativeAccess;
import net.janrupf.ujr.platform.jni.gc.ObjectCollector;

import java.net.InetAddress;
import java.util.Objects;

public class JNIUlRenderer implements UlRenderer {
    @NativeAccess
    private final long handle;

    private JNIUlRenderer() {
        throw new RuntimeException("Allocate in native code without calling constructor");
    }

    @Override
    public UlSession createSession(boolean isPersistent, String name) {
        return nativeCreateSession(isPersistent, name);
    }

    private native JNIUlSession nativeCreateSession(boolean isPersistent, String name);

    @Override
    public UlSession defaultSession() {
        return nativeDefaultSession();
    }

    private native JNIUlSession nativeDefaultSession();

    @Override
    public UlView createView(int width, int height, UlViewConfig config, UlSession session) {
        return nativeCreateView(width, height, config, (JNIUlSession) session);
    }

    private native UlView nativeCreateView(int width, int height, UlViewConfig config, JNIUlSession session);

    @Override
    public void update() {
        nativeUpdate();

        // Do garbage collection after each update
        ObjectCollector.processRound();
    }

    private native void nativeUpdate();

    @Override
    public void render() {
        nativeRender();
    }

    private native void nativeRender();

    @Override
    public void renderOnly(UltralightView[] views) {
        nativeRenderOnly(views);
    }

    private native void nativeRenderOnly(UltralightView[] views);

    @Override
    public void purgeMemory() {
        nativePurgeMemory();
    }

    private native void nativePurgeMemory();

    @Override
    public void logMemoryUsage() {
        nativeLogMemoryUsage();
    }

    private native void nativeLogMemoryUsage();

    @Override
    public boolean startRemoteInspectorServer(InetAddress address, int port) {
        return nativeStartRemoteInspectorServer(address.getHostAddress(), port);
    }

    private native boolean nativeStartRemoteInspectorServer(String address, int port);

    @Override
    public void setGamepadDetails(long index, String id, long axisCount, long buttonCount) {
        nativeSetGamepadDetails(index, id, axisCount, buttonCount);
    }

    private native void nativeSetGamepadDetails(long index, String id, long axisCount, long buttonCount);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JNIUlRenderer)) return false;
        JNIUlRenderer that = (JNIUlRenderer) o;
        return handle == that.handle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(handle);
    }
}
