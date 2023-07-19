package net.janrupf.ujr.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import net.janrupf.ujr.gradle.extensions.UJRExtension

class UJRGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.create("ujr", UJRExtension::class.java)
    }
}