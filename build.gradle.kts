plugins {
  `java-library`
  `maven-publish`
  alias(libs.plugins.indra)
  alias(libs.plugins.spotless)
  alias(libs.plugins.shadow)
}

subprojects {
  apply(plugin = "java-library")
  apply(plugin = "maven-publish")
  apply(plugin = "net.kyori.indra")
  apply(plugin = "com.diffplug.spotless")
  apply(plugin = "com.gradleup.shadow")

  indra {
    javaVersions {
      target(21)
      minimumToolchain(21)
    }
  }

  repositories {
    mavenCentral()
  }

  spotless {
    java {
      licenseHeaderFile("$rootDir/header/header.txt")
      removeUnusedImports()
      trimTrailingWhitespace()
    }
    kotlinGradle {
      trimTrailingWhitespace()
    }
  }

  dependencies {
    implementation("org.jetbrains:annotations:26.0.2")
  }

  tasks {
    compileJava {
      dependsOn("spotlessApply")
      options.compilerArgs.add("-parameters")
    }
    shadowJar {
      archiveBaseName.set(project.name)
      minimize()

      // Package expected to use as final directory for dependencies used.
      val relocationFinalPackage = "io.github.aivruu.repoviewer.libs"

      relocate("org.jetbrains.annotations", "$relocationFinalPackage.org.jetbrains.annotations")
      relocate("com.google.gson", "$relocationFinalPackage.com.google.gson")
    }
  }

  publishing {
    publications {
      create<MavenPublication>("maven") {
        from(components["java"])
      }
    }
  }
}
