group = "net.janrupf"
version = "1.0-SNAPSHOT"

subprojects {
    plugins.withId("java") {
        extensions.getByType(JavaPluginExtension::class.java).apply {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}
