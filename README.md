# repo-viewer

[![](https://jitpack.io/v/aivruu/repo-viewer.svg)](https://jitpack.io/#aivruu/repo-viewer)

`repo-viewer` is a library that provides utilities to obtain GitHub repositories information since HTTP requests, and allow download assets of the releases for the requested repositories of an easily way.

> [!NOTE]\
> This is a library that I created to testing HTTP utilities with Java. I taken as base [OcZi/release-watcher](https://github.com/OcZi/release-watcher) for the main idea of this project.

### Features
* View specified repository's information.
* Easy to usage.
* Download assets from each repository release.

### Download
```kotlin
repositories {
  maven("https://jitpack.io/")
}

dependencies {
  // You can visualize the latest version on the document header.
  implementation("com.github.aivruu:repo-viewer:VERSION")
}
```

### Building
This project require Gradle for building and management, and Java 21 as minimum.
```
git clone https://github.com/aivruu/repo-viewer.git
cd repo-viewer
./gradlew build
```
