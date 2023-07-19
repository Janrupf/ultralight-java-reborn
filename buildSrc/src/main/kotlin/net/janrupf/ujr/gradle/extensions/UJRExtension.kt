package net.janrupf.ujr.gradle.extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.Directory
import java.util.*
import javax.inject.Inject
import net.janrupf.ujr.gradle.ConfigurationExportImport
import org.gradle.api.internal.file.FileFactory

abstract class UJRExtension @Inject constructor(
    private val project: Project,
    private val fileFactory: FileFactory,
) {
    /**
     * Overwrites the outgoing artifacts of a gradle configuration with prebuilt artifacts.
     *
     * This effectively disables the build of the configuration and replaces it with the prebuilt
     * libraries. In this project this is used to import the prebuilt jars from different platform
     * configurations.
     */
    fun importConfigurations(prebuilts: Any) {
        ConfigurationExportImport.importConfigurations(fileFactory.dir(project.file(prebuilts)), project.configurations)
    }
}