# release-downloader

[![](https://jitpack.io/v/aivruu/release-downloader.svg)](https://jitpack.io/#aivruu/release-downloader)

`release-downloader` is a library that provides utilities to obtain GitHub repositories information since HTTP requests, and allow download assets of the releases for the requested repositories of an easily way.

> [!NOTE]\
> This is a library that I created to testing HTTP utilities with Java. I taken as base [OcZi/release-watcher](https://github.com/OcZi/release-watcher) for the main idea of this project, later I decided to implement a downloader function type **(BETA)**.

### Documentation
- [Javadoc](https://jitpack.io/com/github/aivruu/release-downloader/latest/javadoc/)

### Features
* Obtain and view the repositories information easily with requests to the GitHub API.
* Download assets of the releases for requested repositories.

### Download
```kotlin
repositories {
  maven("https://jitpack.io/")
}

dependencies {
  // You can visualize the latest version on the document header.
  implementation("com.github.aivruu:release-downloader:VERSION")
}

tasks {
  shadowJar {
    // Relocating gson-lib dependency and release-downloader into specified packages.
    relocate("com.google.gson", "com.yourPackage.com")
    relocate("me.qeklydev.downloader", "com.yourPackage.com")
  }
}
```

```xml
<repositories>
  <repository>
    <id>jitpack-repo</id>
    <url>https://jitpack.io/</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.aivruu</groupId>
  <artifactId>release-downloader</artifactId>
  <version>0.0.1</version>
  <scope>compile</scope>
</dependency>
```

### Requirements
- Java 17 or higher.

### Building
This library use Gradle-Kotlin for project.
```
git clone https://github.com/aivruu/release-downloader.git
cd release-downloader
./gradlew build
```

JDK 17 or newer is fully required.
