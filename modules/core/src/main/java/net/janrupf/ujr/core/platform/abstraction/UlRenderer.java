package net.janrupf.ujr.core.platform.abstraction;

import net.janrupf.ujr.api.UltralightView;
import net.janrupf.ujr.api.config.UlViewConfig;

import java.net.InetAddress;

public interface UlRenderer {
    UlSession createSession(boolean isPersistent, String name);

    UlSession defaultSession();

    UlView createView(int width, int height, UlViewConfig config, UlSession session);

    void update();

    void render();

    void renderOnly(UltralightView[] views);

    void purgeMemory();

    void logMemoryUsage();

    boolean startRemoteInspectorServer(InetAddress address, int port);

    void setGamepadDetails(long index, String id, long axisCount, long buttonCount);
}
