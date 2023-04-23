package net.janrupf.ujr.core.platform.abstraction;

import java.net.InetAddress;

public interface UlRenderer {
    void update();

    void render();

    void purgeMemory();

    void logMemoryUsage();

    boolean startRemoteInspectorServer(InetAddress address, int port);

    void setGamepadDetails(long index, String id, long axisCount, long buttonCount);
}
