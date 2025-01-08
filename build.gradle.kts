import cn.lalaki.pub.BaseCentralPortalPlusExtension.PublishingType

val user = "MaleficCompose"
val repo = "MaleficPrefs"
val g = "xyz.malefic.compose"
val artifact = "prefs"
val v = "1.3.1"
val desc = "A library for managing preferences and settings based on Java's Preferences API"

val localMavenRepo = uri(layout.buildDirectory.dir("repo").get())

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinter)
    alias(libs.plugins.central)
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
}

group = g
version = v

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain {
        this.languageVersion.set(JavaLanguageVersion.of(17))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = g
            artifactId = artifact
            version = v

            from(components["java"])

            pom {
                name.set(repo)
                description.set(desc)
                url.set("https://github.com/$user/$repo")
                developers {
                    developer {
                        name.set("Om Gupta")
                        email.set("ogupta4242@gmail.com")
                    }
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/$user/$repo.git")
                    developerConnection.set("scm:git:ssh://github.com/$user/$repo.git")
                    url.set("https://github.com/$user/$repo")
                }
            }
        }
        repositories {
            maven {
                url = localMavenRepo
            }
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

centralPortalPlus {
    url = localMavenRepo
    username = System.getenv("centralPortalUsername") ?: ""
    password = System.getenv("centralPortalPassword") ?: ""
    publishingType = PublishingType.AUTOMATIC
}

tasks.apply {
    create("formatAndLintKotlin") {
        group = "formatting"
        description = "Fix Kotlin code style deviations with kotlinter"
        dependsOn(formatKotlin)
        dependsOn(lintKotlin)
    }
    build {
        dependsOn(dokkaGenerate)
        dependsOn(named("formatAndLintKotlin"))
    }
}

dokka {
    dokkaPublications.html {
        outputDirectory.set(layout.buildDirectory.dir("dokka"))
    }
}
