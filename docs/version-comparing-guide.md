# How to compare a release's tag-name/version with another

For this, the ReleaseValueObject provides a few methods (which are accessible from its aggregate-root) to compare the
current release's tag-name with another, using an specific operator-type for the comparison, check ComparisonOperator.

The `compareVersionNumber(ComparisonOperator, int)` will compare the release's tag with the given version-number using
the defined operator-type for the comparison, and will return a `boolean` result for comparing-function's completion.

The method `compareVersionString(ComparisonOperator, String)` will execute the same logic, with the difference that the
provided version-string will be parsed into a numeric to proceed with the comparison.

```java
// We verify that provided version is LOWER than the version of the release that we're using for the comparison.
return this.releaseAggregateRoot.compareVersionNumber(ComparisonOperator.LESS, 347);
```
