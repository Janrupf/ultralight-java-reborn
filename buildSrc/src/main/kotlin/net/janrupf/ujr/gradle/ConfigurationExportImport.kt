package net.janrupf.ujr.gradle

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.gradle.api.GradleException
import org.gradle.api.artifacts.ConfigurablePublishArtifact
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.artifacts.ConfigurationVariant
import org.gradle.api.artifacts.PublishArtifact
import org.gradle.api.file.Directory
import kotlin.collections.HashMap
import kotlin.collections.HashSet

object ConfigurationExportImport {
    @Serializable
    data class ArtifactProperties(
        val file: String,
        val artifactName: String,
        val artifactExtension: String,
        val artifactClassifier: String?,
        val artifactType: String,
        val appearsIn: MutableSet<ConfigurationVariantEntry>
    )

    @Serializable
    data class ConfigurationVariantEntry(
        val configuration: String,
        val variant: String?,
    )

    fun exportConfigurations(configurations: Set<Configuration>, target: Directory) {
        val seenArtifacts = HashMap<PublishArtifact, ArtifactProperties>();
        for (configuration in configurations) {
            configuration.outgoing.variants.forEach { variant ->
                variant.artifacts.forEach { exportArtifact(configuration, it, variant, seenArtifacts) }
            }

            configuration.outgoing.artifacts.forEach {
                exportArtifact(configuration, it, null, seenArtifacts)
            }
        }

        for ((artifact, artifactProperties) in seenArtifacts) {
            val targetFile = target.file(artifactProperties.file).asFile
            artifact.file.copyTo(targetFile, overwrite = true)

            val artifactMetadataFile = target.file("${artifactProperties.file}.artifact").asFile
            artifactMetadataFile.writeText(Json.encodeToString(artifactProperties))
        }
    }

    private fun exportArtifact(
        configuration: Configuration,
        artifact: PublishArtifact,
        variant: ConfigurationVariant?,
        seenArtifacts: MutableMap<PublishArtifact, ArtifactProperties>
    ) {
        val artifactProperties = seenArtifacts.computeIfAbsent(artifact) {
            ArtifactProperties(
                artifact.file.name,
                artifact.name,
                artifact.extension,
                artifact.classifier,
                artifact.type,
                HashSet()
            )
        }

        artifactProperties.appearsIn.add(ConfigurationVariantEntry(configuration.name, variant?.name))
    }

    fun importConfigurations(source: Directory, configurationsContainer: ConfigurationContainer) {
        val artifactFiles = source.asFile.listFiles { _, name -> name.endsWith(".artifact") }
        if (artifactFiles.isNullOrEmpty()) {
            return
        }

        val clearedConfigurations = HashSet<Configuration>()

        for (artifactFile in artifactFiles) {
            val artifactProperties = Json.decodeFromString(ArtifactProperties.serializer(), artifactFile.readText())

            for (appearsIn in artifactProperties.appearsIn) {
                val configuration = configurationsContainer.findByName(appearsIn.configuration)
                if (configuration == null) {
                    throw GradleException("Could not find configuration ${appearsIn.configuration}")
                }

                if (!clearedConfigurations.contains(configuration)) {
                    configuration.outgoing.variants.clear()
                    configuration.outgoing.artifacts.clear()

                    clearedConfigurations.add(configuration)
                }

                val variant = if (appearsIn.variant != null) configuration.outgoing.variants.maybeCreate(appearsIn.variant) else null

                val artifactConfigurer = { artifact: ConfigurablePublishArtifact ->
                    artifact.name = artifactProperties.artifactName
                    artifact.extension = artifactProperties.artifactExtension
                    artifact.classifier = artifactProperties.artifactClassifier
                    artifact.type = artifactProperties.artifactType
                }

                val file = source.file(artifactProperties.file)

                if (variant != null) {
                    variant.artifact(file, artifactConfigurer)
                } else {
                    configuration.outgoing.artifact(file, artifactConfigurer)
                }
            }
        }
    }
}