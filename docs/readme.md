# repo-viewer | [![Codacy Badge](https://app.codacy.com/project/badge/Grade/2af8dd31da77439ea518ee1df8d725be)](https://app.codacy.com/gh/aivruu/repo-viewer/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![](https://jitpack.io/v/aivruu/repo-viewer.svg)](https://jitpack.io/#aivruu/repo-viewer)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/aivruu/repo-viewer/build.yml)
![GitHub License](https://img.shields.io/github/license/aivruu/repo-viewer)
![GitHub commit activity](https://img.shields.io/github/commit-activity/t/aivruu/repo-viewer)

`repo-viewer` is a library that provides utilities to request information from GitHub repositories and those repositories' releases, as well
download any asset that is included in the release.

> [!NOTE]\
> I started this project with the only purpose of testing HTTP utilities with Java. I took as base [OcZi/release-watcher](https://github.com/OcZi/release-watcher) for the main idea of this project.

## Features
* View specified repository's information.
* View each repository's releases' information.
* Permits comparing between releases' defined tag-names.
* Easy to usage.
* Download assets from each repository release.

## Guides
* [Download assets from any repository releases.](https://github.com/aivruu/repo-viewer/blob/main/docs/download-assets-guide.md)
* [Make a request to an specified repository.](https://github.com/aivruu/repo-viewer/blob/main/docs/make-request-guide.md)
* [Compare a release's tag-name with another.](https://github.com/aivruu/repo-viewer/blob/main/docs/version-comparing-guide.md)

## Download
```kotlin
repositories {
  maven("https://jitpack.io/")
}

dependencies {
  // You can visualize the latest version on the document header.
  implementation("com.github.aivruu:repo-viewer:VERSION")
}
```

## Building
This project require Gradle for building and management, and Java 21 as minimum.
```
git clone https://github.com/aivruu/repo-viewer.git
cd repo-viewer
./gradlew shadowJar
```
