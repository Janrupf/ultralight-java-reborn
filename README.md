[Ultralight Discord](https://chat.ultralig.ht) | [Ultralight Upstream](https://github.com/Ultralight-ux/Ultralight)

# Ultralight Java Reborn

###### Complete rewrite of the original Java wrapper for the [Ultralight](https://ultralig.ht) web engine

# About

This project is a Java wrapper for the [Ultralight](https://ultralig.ht) web engine which focuses on providing a 
simple, easy-to-use, and lightweight API for embedding web content in Java applications.

## What is Ultralight?

Ultralight is a lightweight, cross-platform, standards-compliant HTML UI engine. It's not a full browser, it's
designed specifically for creating native applications with HTML, CSS, and JavaScript. Ultralight is written in C++
and meant to be embedded into native applications (on desktop or console platforms). It's a great solution for
projects that want a UI that runs off of HTML but doesn't want to deal with the complexity of embedding a full
browser like Chromium or WebKit.

## What makes Ultralight Java Reborn different from other solutions?

Other than solutions like JCEF and the JavaFX WebView, Ultralight is meant to be embedded into applications and
games. As such, it focuses on speed and integration with custom code. Ultralight Java Reborn is a wrapper around
the Ultralight C++ API.

## Contact

If you have any questions, feel free to join the [Ultralight Discord](https://chat.ultralig.ht) and ask in the
`#java` channel.

# Using the library

Snapshot builds are published to https://s01.oss.sonatype.org/content/repositories/snapshots.

## Gradle

Add the repository:
```kotlin
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}
```

Add the dependencies:
```kotlin
dependencies {
    // Java libraries, these are needed for compilation and runtime
    implementation("net.janrupf.ultralight:ultralight-java-reborn-core:0.0.2-SNAPSHOT")
    implementation("net.janrupf.ultralight:ultralight-java-reborn-platform-jni:0.0.2-SNAPSHOT")

    // The native libraries, these are needed for runtime only (they contain no Java code)
    runtimeOnly("net.janrupf.ultralight:ultralight-java-reborn-platform-jni:0.0.2-SNAPSHOT:linux-x64")
    // or win-x64, mac-x64, or just add all 3 as dependencies
}
```

## Maven

Add the repository:
```xml
<repositories>
    <repository>
        <id>sonatype-snapshots</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots</url>
    </repository>
</repositories>
```

Add the dependencies:
```xml
<dependencies>
    <dependency>
        <!-- Core library -->
        <groupId>net.janrupf.ultralight</groupId>
        <artifactId>ultralight-java-reborn-core</artifactId>
        <version>0.0.2-SNAPSHOT</version>
    </dependency>
    <dependency>
        <!-- Platform JNI library -->
        <groupId>net.janrupf.ultralight</groupId>
        <artifactId>ultralight-java-reborn-platform-jni</artifactId>
        <version>0.0.2-SNAPSHOT</version>
    </dependency>
    <dependency>
        <!-- Platform JNI natives -->
        <groupId>net.janrupf.ultralight</groupId>
        <artifactId>ultralight-java-reborn-platform-jni</artifactId>
        <version>0.0.2-SNAPSHOT</version>
        <classifier>linux-x64</classifier>
        <scope>runtime</scope> <!-- Only required at runtime, contains no Java code -->
        <!-- or win-x64, mac-x64, or just add all 3 as dependencies -->
    </dependency>
</dependencies>
```

# Licensing

This wrapper is licensed under the LGPLv3 license. See the [LICENSE](LICENSE) file for more information.

**However, Ultralight itself requires a commercial license for use in commercial products.** See the
official [Ultralight website](https://ultralig.ht) for more information.

Moreover, some of the Jar files produced by this project contain the non-free proprietary binaries of Ultralight.

