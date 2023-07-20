plugins {
    `maven-publish`
    signing
}

val ossrhUser = System.getenv("UJR_OSSRH_USER")
val pssrhPassword = System.getenv("UJR_OSSRH_PASSWORD")

val isSnapshot = rootProject.version.toString().endsWith("-SNAPSHOT")

publishing {
    repositories {
        if (ossrhUser != null && pssrhPassword != null) {
            maven {
                name = "ossrh-ultralight-java-reborn"

                if (isSnapshot) {
                    url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                } else {
                    url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                }

                credentials {
                    username = ossrhUser
                    password = pssrhPassword
                }
            }
        } else {
            maven {
                name = "github-maven-ultralight-java-reborn"
                url = uri("file://${rootProject.buildDir}/maven-repo")
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
