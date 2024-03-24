plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.indra)
    alias(libs.plugins.indra.checkstyle)
    alias(libs.plugins.spotless)
    alias(libs.plugins.shadow)
}

repositories {
    gradlePluginPortal()
    mavenCentral()
    mavenLocal()
}
    
indra {
    javaVersions {
        target(17)
        minimumToolchain(17)
    }
    checkstyle("10.13.0")
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
    checkstyle("ca.stellardrift:stylecheck:0.2.1")
    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("com.google.code.gson:gson:2.10.1")
}

tasks {
    compileJava {
        dependsOn("spotlessApply")
        dependsOn("checkstyleMain")
        options.compilerArgs.add("-parameters")
    }
    shadowJar {
        archiveFileName.set(rootProject.name)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
