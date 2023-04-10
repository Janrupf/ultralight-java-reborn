package net.janrupf.ujr.platform.jni;

import net.janrupf.ujr.core.platform.InvalidPlatformEnvironmentException;
import net.janrupf.ujr.core.platform.PlatformFeatures;
import net.janrupf.ujr.core.platform.abstraction.UlPlatformProvider;
import net.janrupf.ujr.core.platform.option.PlatformEnvironmentOptionContainer;
import net.janrupf.ujr.core.platform.option.std.CommonPlatformOptions;
import net.janrupf.ujr.core.platform.provider.PlatformEnvironmentProvider;
import net.janrupf.ujr.platform.jni.bundled.BundledNatives;
import net.janrupf.ujr.platform.jni.bundled.HashedNative;
import net.janrupf.ujr.platform.jni.impl.JNIUlPlatformProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * The JNI platform provider.
 * <p>
 * This provider is responsible for loading the JNI based Ultralight library and providing the JNI
 * Ultralight API.
 */
public class UJRJniPlatformProvider implements PlatformEnvironmentProvider {
    private final CommonPlatformOptions commonPlatformOptions;
    private final BundledNatives bundledNatives;

    UJRJniPlatformProvider(PlatformEnvironmentOptionContainer options) {
        this.commonPlatformOptions = options.require(CommonPlatformOptions.class); // Always present
        this.bundledNatives = new BundledNatives();
    }

    @Override
    public boolean supportsThisEnvironment() {
        return bundledNatives.supports(this.commonPlatformOptions.platformIdentification());
    }

    @Override
    public void performLoading() throws InvalidPlatformEnvironmentException {
        List<HashedNative> natives = bundledNatives.getNatives(this.commonPlatformOptions.platformIdentification());

        // We need to extract the natives to a temporary directory and load them from there
        Path nativesDir = this.commonPlatformOptions.temporaryDirectory().resolve("natives");
        boolean useSymlinks = this.commonPlatformOptions.platformIdentification().supportsFeature(PlatformFeatures.SYMBOLIC_LINKS);

        if (!Files.isDirectory(nativesDir)) {
            try {
                Files.createDirectories(nativesDir);
            } catch (IOException e) {
                throw new InvalidPlatformEnvironmentException("Failed to create natives directory", e);
            }
        }

        for (HashedNative nat : natives) {
            try (InputStream in = nat.bundledLocation().toURL().openStream()) {
                if (useSymlinks) {
                    // Extract to a file named after the hash
                    Path targetFile = nativesDir.resolve(nat.hash());

                    // Copy the native to the target file
                    Files.copy(in, targetFile, StandardCopyOption.REPLACE_EXISTING);

                    // Now create symlinks for all names to the target file
                    for (String name : nat.names()) {
                        Path link = nativesDir.resolve(name);

                        Files.deleteIfExists(link);
                        Files.createSymbolicLink(link, targetFile);
                    }
                } else {
                    // Copy the file to each of its respective names
                    for (String name : nat.names()) {
                        Path targetFile = nativesDir.resolve(name);

                        // TODO: This possible runs into file locking issues on Windows when multiple applications
                        //       using Ultralight Java Reborn are running at the same time.
                        Files.copy(in, targetFile, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            } catch (IOException e) {
                throw new InvalidPlatformEnvironmentException("Failed to set up natives", e);
            }
        }

        // Specific load order
        List<String> librariesToLoad = Arrays.asList(
                "UltralightCore", // Core library, no dependencies
                "glib-2.0", // GLib, no dependencies
                "gio-2.0", // GIO, depends on GLib
                "gobject-2.0", // GObject, depends on GLib
                "gmodule-2.0", // GModule, depends on GLib
                "gthread-2.0", // GThread, depends on GLib
                "gstreamer-full-1.0", // GStreamer, depends on GLib
                "WebCore", // WebCore, depends on all previous libraries
                "Ultralight", // Main library, depends on all previous libraries
                "AppCore", // AppCore, depends on all previous libraries
                "ultralight-java-reborn" // JNI library, depends on all previous libraries
        );

        // And finally: load the libraries
        for (String library : librariesToLoad) {
            String mappedName = System.mapLibraryName(library);
            Path libraryPath = nativesDir.resolve(mappedName);

            // Some files don't exist on all platforms, so we need to check for their existence
            if (Files.exists(libraryPath)) {
                try {
                    System.load(libraryPath.toAbsolutePath().toString());
                } catch (Throwable e) {
                    throw new InvalidPlatformEnvironmentException("Failed to load native library", e);
                }
            }
        }
    }

    @Override
    public <T> T tryProvideApi(Class<T> interfaceClass) {
        if (interfaceClass == UlPlatformProvider.class) {
            return interfaceClass.cast(new JNIUlPlatformProvider());
        } else {
            return null;
        }
    }

    @Override
    public void cleanup() {
        try (Stream<Path> files = Files.walk(this.commonPlatformOptions.temporaryDirectory())) {
            //noinspection ResultOfMethodCallIgnored
            files.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (IOException e) {
            // Ignore
        }
    }
}
