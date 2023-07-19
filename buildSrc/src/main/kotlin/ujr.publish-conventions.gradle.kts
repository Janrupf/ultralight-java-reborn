plugins {
    `maven-publish`
    signing
}

val githubActor = System.getenv("GITHUB_ACTOR")?.toString()
val githubToken = System.getenv("GITHUB_TOKEN")?.toString()

publishing {
    repositories {
        if (githubActor != null && githubToken != null) {
            maven {
                name = "github-maven-ultralight-java-reborn"
                url = uri("https://maven.pkg.github.com/Janrupf/ultralight-java-reborn")

                credentials {
                    username = githubActor
                    password = githubToken
                }
            }
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
