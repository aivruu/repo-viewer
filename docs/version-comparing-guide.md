# How to compare a release's tag-name/version with another

For this, the [`ReleaseValueObject`](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/release/domain/ReleaseValueObject.java) provides a few methods (which are accessible from its aggregate-root) to compare the
current release's tag-name with another, using an specific operator-type for the comparison, check [`ComparisonOperator`](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/release/domain/compare/ComparisonOperator.java).

The [`ReleaseAggregateRoot#compareVersionNumber(ComparisonOperator, int)`](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/release/domain/ReleaseAggregateRoot.java#L96) will compare the release's tag with the given version-number using
the defined operator-type for the comparison, and will return a `boolean` result for comparing-function's completion.

The method [`ReleaseAggregateRoot#compareVersionString(ComparisonOperator, String)`](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/release/domain/ReleaseAggregateRoot.java#L109) will execute the same logic, with the difference that the
provided version-string will be parsed into a numeric to proceed with the comparison.

```java
// We verify that provided version is LOWER than the version of the release that we're using for the comparison.
return this.releaseAggregateRoot.compareVersionNumber(ComparisonOperator.LESS, 347);
```
