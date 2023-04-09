package net.janrupf.ujr.example.full;

import net.janrupf.ujr.core.UltralightJavaReborn;
import net.janrupf.ujr.core.platform.PlatformEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UJRFullExample {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(UJRFullExample.class);
        logger.info("Starting Ultralight Java Reborn full example...");

        // Ultralight Java Reborn uses the concept of platform environments to abstract
        // away the loading and management of the native API's. Our examples depends on
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
    }
}
