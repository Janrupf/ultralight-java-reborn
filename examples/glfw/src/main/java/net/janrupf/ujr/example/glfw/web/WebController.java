package net.janrupf.ujr.example.glfw.web;

import net.janrupf.ujr.api.*;
import net.janrupf.ujr.core.UltralightJavaReborn;
import net.janrupf.ujr.core.platform.PlatformEnvironment;
import net.janrupf.ujr.example.glfw.window.WindowController;
import net.janrupf.ujr.example.glfw.bridge.FilesystemBridge;
import net.janrupf.ujr.example.glfw.bridge.GlfwClipboardBridge;
import net.janrupf.ujr.example.glfw.bridge.LoggerBridge;
import net.janrupf.ujr.example.glfw.surface.GlfwSurfaceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class WebController implements AutoCloseable {
    private static final Logger LOGGER = LogManager.getLogger(WebController.class);

    private final WindowController windowController;
    private final Set<WebWindow> windows;
    private final UltralightJavaReborn ujr;


    public WebController(WindowController windowController) {
        this.windowController = windowController;
        this.windows = new HashSet<>();

        // Load the platform and create the Ultralight Java Reborn instance
        PlatformEnvironment environment = PlatformEnvironment.load();
        LOGGER.info("Platform environment has been loaded!");

        ujr = new UltralightJavaReborn(environment);
        ujr.activate();
        LOGGER.info("Ultralight Java Reborn has been activated!");

        // Activate global bridge instances for Ultralight
        UltralightPlatform platform = UltralightPlatform.instance();
        platform.usePlatformFontLoader();
        platform.setFilesystem(new FilesystemBridge());
        platform.setClipboard(new GlfwClipboardBridge());
        platform.setLogger(new LoggerBridge());

        // Note the usage of the GlfwSurfaceFactory here.
        // This is required to make Ultralight Java Reborn work custom surfaces.
        // Technically, we could also use the default surface factory, but that would
        // require us to deal with bitmaps and then upload them to OpenGLES.
        // Using a custom surface factory allows us to directly use pixel buffer objects.
        platform.setSurfaceFactory(new GlfwSurfaceFactory());

        platform.setConfig(
                new UltralightConfigBuilder()
                        .cachePath(System.getProperty("java.io.tmpdir") + File.separator + "ujr-example-glfw")
                        // Set a custom prefix to distinguish from other file systems
                        .resourcePathPrefix(FilesystemBridge.RESOURCE_PREFIX)
                        .build());
    }

    public void terminate() {
        LOGGER.debug("Cleaning up Ultralight Java Reborn...");
        ujr.cleanup();
    }

    public WebWindow createWindow(long width, long height, String title) {
        // Create a new view for the window
        UltralightView view = UltralightRenderer.getOrCreate().createView((int) width, (int) height,
                new UltralightViewConfigBuilder()
                        .transparent(true)
                        .build());

        WebWindow window = new WebWindow(this.windowController.createWindow(width, height, title), view);
        windows.add(window);

        return window;
    }

    public void update() {
        UltralightRenderer.getOrCreate().update();
    }

    public void render() {
        UltralightRenderer.getOrCreate().render();

        for (WebWindow window : windows) {
            // Make sure to always start with the root context when drawing
            windowController.activateRootContext();
            window.renderToFramebuffer();
        }

        // We also want to make sure that the root context is active after rendering
        windowController.activateRootContext();
    }

    @Override
    public void close() {
        terminate();
    }
}
