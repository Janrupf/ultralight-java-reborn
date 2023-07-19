plugins {
    `maven-publish`
    signing
}

publishing {
    repositories {
        maven {
            name = "TestDirectory"
            url = uri("file://${rootProject.buildDir}/repo")
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            pom {
                url.set("https://github.com/Janrupf/ultralight-java-reborn")

                licenses {
                    license {
                        name.set("LGPLv3")
                        url.set("https://www.gnu.org/licenses/lgpl-3.0.en.html")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        id.set("Janrupf")
                        email.set("business.janrupf@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/Janrupf/ultralight-java-reborn.git")
                    developerConnection.set("scm:git:ssh://github.com/Janrupf/ultralight-java-reborn.git")
                    url.set("https://github.com/Janrupf/ultralight-java-reborn")
                }
            }
        }
    }
}

if (rootProject.properties["ujr.enableSigning"]?.toString()?.toBoolean() == true) {
    signing {
        val signingKey = System.getenv("UJR_SIGNING_KEY")
        val signingPassword = System.getenv("UJR_SIGNING_PASSWORD")
        useInMemoryPgpKeys(signingKey, signingPassword)

        sign(publishing.publications["mavenJava"])
    }
}
