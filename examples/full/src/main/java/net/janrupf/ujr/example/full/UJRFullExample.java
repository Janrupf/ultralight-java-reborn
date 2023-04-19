package net.janrupf.ujr.example.full;

import net.janrupf.ujr.api.UltralightConfigBuilder;
import net.janrupf.ujr.api.UltralightPlatform;
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

        // Now it is time to set a few configuration options. We simply use a builder to create
        // a new configuration object and then pass it to the platform. Here we use the defaults
        // for all options (where available), but you can customize them as you wish. The cache
        // path is the only required option.
        platform.setConfig(new UltralightConfigBuilder().cachePath(System.getProperty("java.io.tmpdir")).build());

        // After we are done using the library, we should tell it to perform cleanup
        ujr.cleanup();
        logger.info("Cleanup finished!");
    }
}
