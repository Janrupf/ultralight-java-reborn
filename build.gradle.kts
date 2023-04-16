group = "net.janrupf"
version = "1.0-SNAPSHOT"

subprojects {
    if (plugins.hasPlugin("java")) {
        this.extensions.getByType(JavaPluginExtension::class.java).apply {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}
