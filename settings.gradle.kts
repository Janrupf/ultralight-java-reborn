rootProject.name = "ultralight-java-reborn"

fun addExample(name: String) {
    val projName = ":examples:${name}"

    include(projName)
    project(projName).apply {
        this.name = "ultralight-java-reborn-example-${name}"
    }
}

fun addModule(name: String) {
    val projName = ":modules:${name}"

    include(projName)
    project(projName).apply {
        this.name = "ultralight-java-reborn-${name}"
    }
}

addExample("glfw")
addExample("javascript")
addExample("png")

addModule("core")
addModule("native-ap")
addModule("platform-jni")
