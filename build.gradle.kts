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
  apply(plugin = "io.github.goooler.shadow")

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
      indentWithSpaces(2)
    }
    kotlinGradle {
      trimTrailingWhitespace()
      indentWithSpaces(2)
    }
  }

  dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
  }

  tasks {
    compileJava {
      dependsOn("spotlessApply")
      options.compilerArgs.add("-parameters")
    }
    shadowJar {
      archiveFileName.set(rootProject.name)
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
        groupId = "io.github.aivruu.repoviewer"
        artifactId = project.name
        version = project.version.toString()

        from(components["java"])
      }
    }
  }
}
