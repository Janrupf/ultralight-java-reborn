package net.janrupf.ujr.api;

import net.janrupf.ujr.api.config.UlViewConfig;
import net.janrupf.ujr.api.logger.UltralightLogger;
import net.janrupf.ujr.core.platform.abstraction.UlRenderer;

import java.net.InetAddress;

/**
 * This class manages the lifetime of all Views ({@link View}) and coordinates
 * painting, network requests, and event dispatch.
 */
public class UltralightRenderer {
    // Cache the instance per thread, as only one can exist
    private static final ThreadLocal<UltralightRenderer> THREAD_INSTANCE = new ThreadLocal<>();

    // TODO: Adjust references in the documentation once the other classes are implemented

    /**
     * Create the Ultralight Renderer directly.
     * <p>
     * You should set up your {@link net.janrupf.ujr.api.config.UlConfig},
     * {@link net.janrupf.ujr.api.filesystem.UltralightFilesystem}, font loader, surface-factories, and
     * gpu-drivers before calling this function.
     *
     * @return the created Ultralight Renderer
     */
    public static UltralightRenderer getOrCreate() {
        UltralightRenderer instance = THREAD_INSTANCE.get();
        if (instance != null) {
            return instance;
        }

        // If no instance is active, create a new one
        instance = new UltralightRenderer(UltralightPlatform.instance().createRenderer());

        THREAD_INSTANCE.set(instance);

        return instance;
    }

    private final UlRenderer renderer;

    private UltralightRenderer(UlRenderer renderer) {
        this.renderer = renderer;
    }

    // TODO: CreateSession

    // TODO: default_session

    // TODO: Support session parameter

    /**
     * Create a new View.
     *
     * @param width  the initial width, in pixels
     * @param height the initial height, in pixels
     * @param config configuration details for the View
     * @return the created view
     */
    public UltralightView createView(int width, int height, UlViewConfig config) {
        return new UltralightView(renderer.createView(width, height, config));
    }

    /**
     * Update timers and dispatch internal callbacks. You should call this often
     * from your main application loop.
     */
    public void update() {
        renderer.update();
    }

    /**
     * Render all active views to their respective render-targets/surfaces.
     * <p>
     * You should call this once per frame (usually in synchrony with the
     * monitor's refresh rate).
     *
     * @implNote Views are only repainted if they actually need painting.
     */
    public void render() {
        renderer.render();
    }

    /**
     * Render only the specified views to their respective render-targets/surfaces.
     * <p>
     * You should call this once per frame (usually in synchrony with the
     * monitor's refresh rate).
     *
     * @param views the views to render
     * @implNote Views are only repainted if they actually need painting.
     */
    public void renderOnly(UltralightView... views) {
        renderer.renderOnly(views);
    }

    /**
     * Attempt to release as much memory as possible. Don't call this from any
     * callbacks or driver code.
     */
    public void purgeMemory() {
        renderer.purgeMemory();
    }

    /**
     * Print detailed memory usage statistics to the log.
     * <p>
     * (see {@link UltralightPlatform#setLogger(UltralightLogger)})
     */
    public void logMemoryUsage() {
        renderer.logMemoryUsage();
    }

    /**
     * Start the remote inspector server, Views that are loaded into this renderer
     * will be able to be remotely inspected either locally (another app on same machine) or
     * remotely (over the network) by navigating a View to inspector://ADDRESS:PORT
     *
     * @param address the address to listen on
     * @param port    the port to listen on
     * @return whether the server started successfully or not
     */
    public boolean startRemoteInspectorServer(InetAddress address, int port) {
        return renderer.startRemoteInspectorServer(address, port);
    }

    /**
     * Describe the details of a gamepad, to be used with FireGamepadEvent and related
     * events below. This can be called multiple times with the same index if the details change.
     *
     * @param index       the unique index (or "connection slot") of the gamepad. For example,
     *                    controller #1 would be "1", controller #2 would be "2" and so on.
     * @param id          a string ID representing the device, this will be made available
     *                    in JavaScript as {@code gamepad.id}
     * @param axisCount   the number of axes on the device.
     * @param buttonCount the number of buttons on the device.
     */
    public void setGamepadDetails(long index, String id, long axisCount, long buttonCount) {
        renderer.setGamepadDetails(index, id, axisCount, buttonCount);
    }

    // TODO: FireGamepadEvent

    // TODO: FireGamepadAxisEvent

    // TODO: FireGamepadButtonEvent
}
