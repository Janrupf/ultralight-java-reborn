package net.janrupf.ujr.example.png;

import net.janrupf.ujr.api.*;
import net.janrupf.ujr.api.bitmap.UltralightBitmap;
import net.janrupf.ujr.api.bitmap.UltralightBitmapSurface;
import net.janrupf.ujr.core.UltralightJavaReborn;
import net.janrupf.ujr.core.platform.PlatformEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UJRFullExample {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(UJRFullExample.class);
        logger.info("Starting Ultralight Java Reborn full example...");

        // Ultralight Java Reborn uses the concept of platform environments to abstract
        // away the loading and management of the native API's. Our examples depend on
        // the JNI platform environment, which is provided by the platform-jni module.
        //
        // The platform-jni module provides a bundled version of the Ultralight library
        // for Windows, Linux and macOS. This is the recommended way to use the library
        // in production, as it allows you to ship a single binary without any additional
        // dependencies.
        //
        // Other platform environment implementations are possible, for example a platform
        // environment that binds to the Ultralight library using project Panama instead of
        // JNI.
        //
        // With that being said, let's create a new Ultralight Java Reborn instance:

        // We use the PlatformEnvironment.load() method to load the platform environment
        // and have it automatically detect all the correct options. If you want to customize
        // the options, you can pass a PlatformEnvironmentOptionContainer to the load method.
        PlatformEnvironment environment = PlatformEnvironment.load();
        logger.info("Platform environment has been loaded!");

        // Now that the environment is loaded, we can create the actual library instance
        // and start using it.
        UltralightJavaReborn ujr = new UltralightJavaReborn(environment);

        // Ultralight itself is not thread-safe, so we need to make sure that we only use it
        // from a single thread. This is why we need to activate the current thread as the
        // Ultralight Java Reborn thread.
        ujr.activate();
        logger.info("Ultralight Java Reborn has been activated on the thread {}!", Thread.currentThread().getName());

        // We can now start using the API implementation which is implemented on top of the lower
        // level platform abstraction.
        UltralightPlatform platform = UltralightPlatform.instance();

        // Let's set a logger, so we can receive log messages from Ultralight.
        platform.setLogger(new LoggerBridge(LogManager.getLogger("Ultralight")));

        // For now, we will use the platform font loader.
        platform.usePlatformFontLoader();

        // Ultralight also needs a custom file system implementation, so we will provide one
        platform.setFilesystem(new FilesystemBridge());

        // The clipboard is also required, so we will provide a dummy implementation
        platform.setClipboard(new ClipboardBridge());

        // Now it is time to set a few configuration options. We simply use a builder to create
        // a new configuration object and then pass it to the platform. Here we use the defaults
        // for all options (where available), but you can customize them as you wish. The cache
        // path is the only required option.
        platform.setConfig(
                new UltralightConfigBuilder()
                        .cachePath(System.getProperty("java.io.tmpdir"))
                        // Set a custom prefix to distinguish from other file systems
                        .resourcePathPrefix(FilesystemBridge.RESOURCE_PREFIX)
                        .build());

        // Now we can create a renderer as all configuration has been finished.
        // The renderer instance is responsible for managing views and executing the
        // rendering process.
        //
        // Only one instance can exist per platform instance (and thus per thread).
        UltralightRenderer renderer = UltralightRenderer.getOrCreate();

        // With the renderer we can create a view. A view is a single window that can
        // display a web page. It is also responsible for handling input events.
        UltralightView view = renderer.createView(1280, 720, new UltralightViewConfigBuilder().build());

        // In order to know the state of the view, we can attach a listener to it.
        view.setViewListener(new ViewListener());

        // Loading progress can be tracked by attaching a load listener to the view.
        view.setLoadListener(new LoadListener());

        // Load an URL into the view and spin the event loop until the view is loaded
        view.loadURL("https://www.google.com/");

        // Now we can spin the event loop.
        while (view.isLoading()) {
            // We can also use the renderer to update all views and execute the event loop.
            // This is useful if we have multiple views and want to update them all at once.
            renderer.update();
        }

        // The view has loaded, lets render it once to obtain an image
        renderer.render();

        // Save the PNG of the view to a file
        //
        // Because we never set a custom surface factory, the default one providing
        // bitmap surfaces will be used.
        UltralightBitmapSurface surface = (UltralightBitmapSurface) view.surface();
        UltralightBitmap bitmap = surface.bitmap();
        bitmap.writePNG("test.png");

        // After we are done using the library, we should tell it to perform cleanup
        ujr.cleanup();
        logger.info("Cleanup finished!");
    }
}
