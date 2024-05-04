plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.spotless)
    alias(libs.plugins.shadow)
}

java {
  toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
}

spotless {
    java {
        licenseHeaderFile("$rootDir/header.txt")
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
    compileOnly("org.jetbrains:annotations:24.0.1")
    implementation("com.google.code.gson:gson:2.10.1")

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
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
