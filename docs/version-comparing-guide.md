# How to compare a release's tag-name/version with another
The [RepositoryReleaseModel](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/api/release/RepositoryReleaseModel.java)
implements functions to permit releases' tags comparing using specific operator-types for this tasks, this types are provided
by the [VersionComparingOperators](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/api/release/VersionComparingOperators.java)
enum.

There're two functions with the same-proposite, but requirements different.

The `compareVersionWithNumber(VersionComparingOperators, int)` will compare the release-model's
version with the given version-number using the specified operator-type **(EQUAL, LESS, GREATER)**, returning a `boolean-value` indicating comparing-function's final result.
By another way, The `compareVersionWithString(VersionComparingOperators, String)` perform the same logic using the version-string given for the comparing, normally this string will be
the release-model's tag-name, which will be parsed into a number before comparing-process.

```java
// Comparing using a version-number.
repositoryReleaseModel.compareVersionWithNumber(VersionComparingOperators.GREATER, 354);

// Comparing using a version-string.
repositoryReleaseModel.compareVersionWithString(VersionComparingOperators.GREATER, "v3.5.4");
```
