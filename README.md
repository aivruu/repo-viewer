# release-downloader

`release-downloader` is a library that provides utilities to obtain GitHub repositories information since HTTP requests, and allow download assets of the releases for the requested repositories of an easily way.

> [!NOTE]\
> This is a library that I created to testing HTTP utilities with Java. At a principle I based me on the [OcZi/release-watcher](https://github.com/OcZi/release-watcher) (with several internal changes) as
> main idea for this project, later I decided to implement a downloader function type **(BETA)**.

### Features
* Obtain and view the repositories information easily with requests to the GitHub API.
* Download assets of the releases for requested repositories.

### Download
```kotlin
dependencies {
  implementation("com.github.aivruu:release-downloader:0.0.1")
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
