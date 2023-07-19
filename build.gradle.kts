import java.io.ByteArrayOutputStream
import java.io.IOException

group = "net.janrupf"
version = determineProjectVersion()

fun determineProjectVersion(): String {
    // Attempt to read the version from the project properties
    val propertyVersion = project.properties["ujr.version"]?.toString()
    if (!propertyVersion.isNullOrEmpty()) {
        return propertyVersion
    }

    // Else attempt to read the version from the commit hash
    try {
        val revStdoutCollector = ByteArrayOutputStream()
        val revExecution = exec {
            executable = "git"
            args = listOf("rev-parse", "--short=16", "HEAD")
            isIgnoreExitValue = true
            standardOutput = revStdoutCollector
        }

        if (revExecution.exitValue == 0) {
            val diffStdoutCollector = ByteArrayOutputStream()
            val diffExecution = exec {
                executable = "git"
                args = listOf("diff", "--stat")
                isIgnoreExitValue = true
                standardOutput = diffStdoutCollector
            }

            val isDirty = diffExecution.exitValue != 0 || String(diffStdoutCollector.toByteArray()).trim().isNotEmpty()

            return "0.0.0${if (isDirty) "-DIRTY" else ""}-${String(revStdoutCollector.toByteArray()).trim()}"
        }
    } catch (e: IOException) {
        logger.warn("Failed to determine project version from git commit hash", e)
    }

    return "0.0.0-UNKNOWN"
}

subprojects {
    plugins.withId("java") {
        extensions.getByType(JavaPluginExtension::class.java).apply {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8

            withJavadocJar()
            withSourcesJar()
        }

        tasks.withType(JavaCompile::class.java) {
            options.encoding = "UTF-8"
        }

        tasks.named<Javadoc>("javadoc") {
            (options as StandardJavadocDocletOptions).addBooleanOption("Xdoclint:-missing", true)
        }
    }

    group = rootProject.group
    version = rootProject.version
}
