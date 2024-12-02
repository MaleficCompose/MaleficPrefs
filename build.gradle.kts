import cn.lalaki.pub.BaseCentralPortalPlusExtension.PublishingType

val v = "1.2.1"
val localMavenRepo = uri(layout.buildDirectory.dir("repo").get())

plugins {
  kotlin("jvm")
  id("org.jetbrains.compose")
  id("org.jetbrains.kotlin.plugin.compose")
  id("maven-publish")
  alias(libs.plugins.spotless)
  alias(libs.plugins.central)
  alias(libs.plugins.dokka)
  signing
}

group = "xyz.malefic"
version = v

repositories {
  mavenCentral()
  maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  google()
}

dependencies {
  implementation(compose.desktop.common)
  testImplementation(kotlin("test"))
}

spotless {
  kotlin {
    ktfmt().googleStyle()
  }
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
      groupId = "xyz.malefic"
      artifactId = "maleficprefs"
      version = v

      from(components["java"])

      pom {
        name.set("MaleficPrefs")
        description.set("A Compose Desktop library for managing preferences and settings based on Java's Preferences API")
        url.set("https://github.com/MaleficCompose/MaleficPrefs")
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
          connection.set("scm:git:git://github.com/MaleficCompose/MaleficPrefs.git")
          developerConnection.set("scm:git:ssh://github.com/MaleficCompose/MaleficPrefs.git")
          url.set("https://github.com/MaleficCompose/MaleficPrefs")
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

tasks.dokkaHtml {
  outputDirectory.set(layout.buildDirectory.get().asFile.resolve("dokka"))
}

tasks.test {
  useJUnitPlatform()
}
