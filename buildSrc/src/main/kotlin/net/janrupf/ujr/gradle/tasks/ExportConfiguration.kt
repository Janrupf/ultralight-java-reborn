package net.janrupf.ujr.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import net.janrupf.ujr.gradle.ConfigurationExportImport
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Internal

abstract class ExportConfiguration : DefaultTask() {
    @get:OutputDirectory
    abstract val destination: DirectoryProperty

    @get:Internal
    abstract val sources: ListProperty<Configuration>

    fun from(configuration: Configuration) {
        sources.add(configuration)
    }

    @TaskAction
    fun performExport() {
        ConfigurationExportImport.exportConfigurations(HashSet(sources.get()), destination.get())
    }
}