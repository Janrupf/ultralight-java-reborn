rootProject.name = "ultralight-java-reborn"

fun addModule(name: String) {
    val projName = ":modules:${name}"

    include(projName)
    project(projName).apply {
        this.name = "ultralight-java-reborn-${name}"
    }
}

addModule("core")
addModule("platform-jni")
