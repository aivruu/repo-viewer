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
    implementation("org.jetbrains:annotations:24.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.1")
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
      relocate("com.google", "$relocationFinalPackage.com.google")
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
