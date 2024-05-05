plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.indra)
    alias(libs.plugins.spotless)
    alias(libs.plugins.shadow)
}

repositories {
    mavenCentral()
}

indra {
    javaVersions {
        target(17)
        minimumToolchain(17)
    }
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

        relocate("com.google.gson", "me.qeklydev.downloader.gson")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
