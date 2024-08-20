# How to download assets from the requested repository's latest-release?
A [LatestReleaseModel](https://github.com/aivruu/repo-viewer/blob/recode/api/src/main/java/io/github/aivruu/repoviewer/api/release/LatestReleaseModel.java) have the
possibility of download one, or all the assets adjunted by the developer at the current latest-release, at a pre-defined directory.

This model provide two methods, `downloadFrom(File, int)` & `downloadAll(File)`. Both functions requires a `File` object which represent the directory
where the asset, or assets will be downloaded. The first method also require the asset's position at the assets-array to be downloaded.
