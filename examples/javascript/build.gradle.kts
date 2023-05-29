import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    id("application")
}

repositories {
    mavenCentral()
}

application {
    mainClass.set("net.janrupf.ujr.example.javascript.JavaScriptExample")
    applicationDefaultJvmArgs = listOf("-Xcheck:jni")

    if (Os.isFamily(Os.FAMILY_MAC)) {
        applicationDefaultJvmArgs += listOf("-XstartOnFirstThread")
    }
}

dependencies {
    implementation(project(":modules:ultralight-java-reborn-core"))
    runtimeOnly(project(":modules:ultralight-java-reborn-platform-jni"))

    // Only used in the example, not required for Ultralight Java Reborn
    implementation(platform("org.apache.logging.log4j:log4j-core:2.20.0"))
    implementation("org.apache.logging.log4j:log4j-api")
    runtimeOnly("org.apache.logging.log4j:log4j-core")
}
