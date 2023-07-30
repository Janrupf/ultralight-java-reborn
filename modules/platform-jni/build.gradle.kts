import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest
import kotlin.io.path.isDirectory

plugins {
    id("java-library")
    id("ujr.publish-conventions")
}

/**
 * Finds a program on the system.
 */
fun findProgram(program: String): Path {
    val programEnvName = program.uppercase().replace('-', '_')
    val programEnv = System.getenv(programEnvName)

    if (programEnv != null && programEnv.isNotEmpty()) {
        // Overwritten by user
        logger.debug("Using $program from $programEnvName environment variable: $programEnv")
        return Paths.get(programEnv)
    }

    val pathEnv = System.getenv("PATH")
    if (pathEnv == null || pathEnv.isEmpty()) {
        logger.debug("PATH environment variable is empty")
        throw IllegalStateException("PATH environment variable is empty")
    }

    // May not be present, typically only on Windows, so fall back to a list with a single, empty string
    val pathExt = System.getenv("PATHEXT")?.split(File.pathSeparator) ?: listOf("")

    for (entry in pathEnv.split(File.pathSeparator)) {
        for (ext in pathExt) {
            val path = Paths.get(entry, "$program$ext")
            if (Files.exists(path)) {
                logger.debug("Found {} at {}", program, path)
                return path
            }
        }
    }

    throw IllegalStateException("Could not find $program in PATH")
}

/**
 * Attempts to find CMake on the system.
 */
fun findCMake(): Path = findProgram("cmake")

/**
 * Calculates the hash of a file.
 */
fun calculateFileHash(file: Path): ByteArray = MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(file))

dependencies {
    compileOnlyApi(project(":modules:ultralight-java-reborn-core"))
    annotationProcessor(project(":modules:ultralight-java-reborn-native-ap"))
}

publishing {
    publications {
        named<MavenPublication>("mavenJava") {
            pom {
                licenses {
                    license {
                        name.set("LicenseRef-Ultralight")
                        url.set("https://github.com/ultralight-ux/Ultralight/blob/master/license/LICENSE.txt")
                        comments.set("Proprietary Ultralight License")
                    }
                }
            }
        }
    }
}

configurations {
    val nativesElements = create("nativesElements") {
        isCanBeResolved = false
        isCanBeConsumed = true
        isVisible = false

        attributes {
            attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.LIBRARY))
            attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }

    named("runtimeOnly") {
        extendsFrom(nativesElements)
    }
}

tasks.named<JavaCompile>("compileJava") {
    val minCompilerVersion = JavaVersion.VERSION_11

    if (javaCompiler.get()
            .metadata
            .languageVersion.asInt() < JavaLanguageVersion.of(minCompilerVersion.majorVersion).asInt()
    ) {
        throw GradleException("Do to a bug in the Java 8 compiler, you need to use at least Java 11 to compile this project even though Java 8 is supported at runtime")
    }
}

// Sometimes we want to disable the native build, for example when cross-compiling
val prebuiltNativesDir =
    project.properties["ujr.prebuiltNativesDir"]?.toString()?.let { file(it) } ?: file(
        buildDir.resolve("prebuilt-natives")
    )
val importPrebuiltNatives = project.properties["ujr.importPrebuiltNatives"]?.toString()?.toBoolean() ?: false

if (!importPrebuiltNatives) {
    val nativeConfiguration =
        if (project.properties["ujr.nativeReleaseBuild"]?.toString()?.toBoolean() == true) "Release" else "Debug"

    // During gradle configuration, also perform CMake configuration
    val nativeDir = buildDir.toPath().resolve("native")

    val cmakeRunDir = nativeDir.resolve("cmake")
    val cmake = findCMake()

    val cmakeSource = file("src/main/c").path
    val cmakeBinaryDir = cmakeRunDir.toString()
    val installDir = nativeDir.resolve("prefix").toString()
    val libsDir = file("$installDir/lib")
    val compactedDir = nativeDir.resolve("compacted")

    val buildNativeTask = tasks.register<Exec>("buildNative") {
        dependsOn("compileJava")
        executable = cmake.toString()
        args = listOf(
            "--build", cmakeBinaryDir,
            "--parallel", Runtime.getRuntime().availableProcessors().toString(),
            "--config", nativeConfiguration,
        )
    }

    val installNativeTask = tasks.register<Exec>("installNative") {
        dependsOn(buildNativeTask)

        executable = cmake.toString()
        args = listOf(
            "--install", cmakeBinaryDir,
            "--prefix", installDir,
            "--config", nativeConfiguration,
        )
    }

    val compactNativeTask = tasks.register("compactNative") {
        dependsOn(installNativeTask)
        inputs.dir(installDir)
        outputs.dir(compactedDir)

        doFirst {
            delete(compactedDir)
        }

        doLast {
            // Helper class which contains the path of the file and the names of the files with the same hash
            data class FileInformation(val path: Path, val type: String, val names: MutableList<String>)

            val knownFiles = hashMapOf<String, FileInformation>()
            val systemIdent = Files.readAllLines(cmakeRunDir.resolve("ultralight.ident")).first()

            val addFiles = { type: String, dir: File ->
                val dirPath = dir.toPath()

                Files.walk(dirPath).forEach { found ->
                    if (found.isDirectory()) {
                        return@forEach
                    }

                    val hash = calculateFileHash(found).joinToString("") { "%02x".format(it) }
                    val knownFile = knownFiles[hash]

                    if (knownFile != null) {
                        // We know a file with the same hash already, add its name to the list
                        knownFile.names.add(dirPath.relativize(found).toString())
                    } else {
                        // New file found, add it to the map
                        knownFiles[hash] = FileInformation(found, type, mutableListOf(dirPath.relativize(found).toString()))
                    }
                }
            }

            // Find all files in the lib and resources directories
            addFiles("library", libsDir)
            addFiles("resource", file("$installDir/share"))

            if (!Files.isDirectory(compactedDir)) {
                Files.createDirectories(compactedDir)
            }

            val metaOut = file("$compactedDir/ultralight-natives.dat").bufferedWriter()

            // Generate a very simple meta information file
            // this text format was chosen, as it does not require any dependencies at runtime to decode
            for ((hash, fileInformation) in knownFiles) {
                // Write the platform, hash and its file names to the meta file
                metaOut.write(systemIdent)
                metaOut.write(" ")
                metaOut.write(fileInformation.type)
                metaOut.write(" ")
                metaOut.write(hash)
                metaOut.write(" ")
                metaOut.write(fileInformation.names.joinToString(","))
                metaOut.write("\n")

                val outFile = compactedDir.resolve(hash)

                // Copy the file to the compacted directory
                Files.copy(fileInformation.path, outFile)
            }

            metaOut.close()
        }
    }

    tasks.named<Copy>("processResources") {
        dependsOn(compactNativeTask)
        from(compactedDir) {
            into("META-INF/resources/net.janrupf.ujr")
        }
    }

    gradle.afterProject {
        val javaHome = tasks.getByName<JavaCompile>("compileJava")
            .javaCompiler.get()
            .metadata
            .installationPath
            .toString()
            .replace(File.separatorChar, '/')

        // Run CMake after project evaluation and configuration has finished
        exec {
            executable = cmake.toString()
            args = listOf(
                "-S", cmakeSource,
                "-B", cmakeBinaryDir,
                "-DUJR_JNI_HEADER_DIR=${tasks.getByName<JavaCompile>("compileJava").options.headerOutputDirectory.get()}",
                "-DJAVA_HOME=$javaHome",
                "-DCMAKE_BUILD_TYPE=$nativeConfiguration",
            )
        }

        tasks.register<Copy>("exportPrebuiltNatives") {
            val systemIdent = Files.readAllLines(cmakeRunDir.resolve("ultralight.ident")).first()
            destinationDir = prebuiltNativesDir.resolve(systemIdent)

            dependsOn(compactNativeTask)
            from(compactedDir)
        }
    }
} else {
    val javaComponent = project.components["java"] as AdhocComponentWithVariants

    // Add the jar to the runtime classpath
    javaComponent.addVariantsFromConfiguration(configurations["nativesElements"]) {
        mapToMavenScope("runtime")
    }

    Files.list(prebuiltNativesDir.toPath()).forEach {
        // The directory name is the system identifier
        val systemIdent = it.fileName.toString()

        // Create a jar from all the files in the directory
        val jarTaskForSystem = tasks.register<Jar>("jar-$systemIdent") {
            from(it) {
                into("META-INF/resources/net.janrupf.ujr")
            }

            archiveClassifier.set(systemIdent)
        }
        
        tasks.named("assemble") {
            dependsOn(jarTaskForSystem)
        }

        artifacts {
            add("nativesElements", jarTaskForSystem)
        }
    }
}
