import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    id("application")
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("net.janrupf.ujr.example.glfw.GLFWExample")
    applicationDefaultJvmArgs = listOf("-Xcheck:jni")

    if (Os.isFamily(Os.FAMILY_MAC)) {
        applicationDefaultJvmArgs += listOf("-XstartOnFirstThread")
    }
}

fun lwjglDep(name: String? = null): String {
    return if (name == null) {
        "org.lwjgl:lwjgl"
    } else {
        "org.lwjgl:lwjgl-$name"
    }
}

fun lwjglNativeDep(name: String? = null): String {
    val libName = if (name == null) {
        "lwjgl"
    } else {
        "lwjgl-$name"
    }

    return if (Os.isFamily(Os.FAMILY_UNIX)) {
        "org.lwjgl:$libName::natives-linux"
    } else if (Os.isFamily(Os.FAMILY_WINDOWS)) {
        "org.lwjgl:$libName::natives-windows"
    } else if (Os.isFamily(Os.FAMILY_MAC)) {
        "org.lwjgl:$libName::natives-macos"
    } else {
        throw IllegalStateException("Unsupported OS")
    }
}

dependencies {
    implementation(project(":modules:ultralight-java-reborn-core"))
    runtimeOnly(project(":modules:ultralight-java-reborn-platform-jni"))

    // LWJGL
    implementation(platform("org.lwjgl:lwjgl-bom:3.3.2"))

    implementation(lwjglDep())
    runtimeOnly(lwjglNativeDep())
    implementation(lwjglDep("glfw"))
    runtimeOnly(lwjglNativeDep("glfw"))
    implementation(lwjglDep("opengles"))
    runtimeOnly(lwjglNativeDep("opengles"))
    implementation(lwjglDep("opengl"))
    runtimeOnly(lwjglNativeDep("opengl"))

    // Only used in the example, not required for Ultralight Java Reborn
    implementation(platform("org.apache.logging.log4j:log4j-core:2.20.0"))
    implementation("org.apache.logging.log4j:log4j-api")
    runtimeOnly("org.apache.logging.log4j:log4j-core")
}
