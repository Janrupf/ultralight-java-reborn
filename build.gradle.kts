group = "net.janrupf"
version = "1.0-SNAPSHOT"

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

    version = rootProject.version
}
