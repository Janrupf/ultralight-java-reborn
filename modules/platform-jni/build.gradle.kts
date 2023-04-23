import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.MessageDigest

plugins {
    id("java-library")
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

// Sometimes we want to disable the native build, for example when cross-compiling
val disableNativeBuild = project.properties["ujr.disableNativeBuild"]?.toString()?.toBoolean() ?: false

if (!disableNativeBuild) {
    // During gradle configuration, also perform CMake configuration
    val nativeDir = buildDir.toPath().resolve("native")

    val cmakeRunDir = nativeDir.resolve("cmake")
    val cmake = findCMake()

    val cmakeSource = file("src/main/c").path
    val cmakeBinaryDir = cmakeRunDir.toString()
    val installDir = nativeDir.resolve("prefix").toString()
    val compactedDir = nativeDir.resolve("compacted")

    val buildNativeTask = tasks.register<Exec>("buildNative") {
        mustRunAfter("compileJava")
        executable = cmake.toString()
        args = listOf(
                "--build", cmakeBinaryDir,
                "--parallel", Runtime.getRuntime().availableProcessors().toString(),
        )
    }

    val installNativeTask = tasks.register<Exec>("installNative") {
        dependsOn(buildNativeTask)

        executable = cmake.toString()
        args = listOf(
                "--install", cmakeBinaryDir,
                "--prefix", installDir,
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
            data class FileInformation(val path: Path, val names: MutableList<String>)

            val knownFiles = hashMapOf<String, FileInformation>()
            val libsDir = file("$installDir/lib")
            val systemIdent = Files.readAllLines(libsDir.toPath().resolve("ultralight.ident")).first()

            // Find all files in the lib directory
            val files = libsDir.listFiles()
            for (file in files!!) {
                if (file.name == "ultralight.ident") {
                    // We do not want to include the ident file
                    continue
                }

                val hash = calculateFileHash(file.toPath()).joinToString("") { "%02x".format(it) }
                val knownFile = knownFiles[hash]

                if (knownFile != null) {
                    // We know a file with the same hash already, add its name to the list
                    knownFile.names.add(file.name)
                } else {
                    // New file found, add it to the map
                    knownFiles[hash] = FileInformation(file.toPath(), mutableListOf(file.name))
                }
            }

            if (!Files.isDirectory(compactedDir)) {
                Files.createDirectories(compactedDir)
            }

            val metaOut = file("$compactedDir/meta.dat").bufferedWriter()

            // Generate a very simple meta information file
            // this text format was chosen, as it does not require any dependencies at runtime to decode
            for ((hash, fileInformation) in knownFiles) {
                // Write the platform, hash and its file names to the meta file
                metaOut.write(systemIdent)
                metaOut.write(" library ")
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
            into("META-INF/resources/native")
        }
        from("$installDir/share") {
            into("META-INF/resources/pkg")
        }
    }

    gradle.afterProject {
        val javaHome = tasks.getByName<JavaCompile>("compileJava").javaCompiler.get().metadata.installationPath

        // Run CMake after project evaluation and configuration has finished
        exec {
            executable = cmake.toString()
            args = listOf(
                    "-S", cmakeSource,
                    "-B", cmakeBinaryDir,
                    "-DUJR_JNI_HEADER_DIR=${tasks.getByName<JavaCompile>("compileJava").options.headerOutputDirectory.get()}",
                    "-DJAVA_HOME=$javaHome",
            )
        }
    }
}