# How to download assets from the requested repository's latest-release?
A [LatestReleaseModel](https://github.com/aivruu/repo-viewer/blob/recode/api/src/main/java/io/github/aivruu/repoviewer/api/release/LatestReleaseModel.java) have the possibility of download one, or all the assets adjunted by the developer at the current latest-release, at a pre-defined directory.

This model provide two methods, `downloadFrom(File, int)` & `downloadAll(File)`. Both functions requires a `File` object which represent the directory
where the asset, or assets will be downloaded. The first method also require an `int` which is the asset's position at the assets-array to be downloaded.

This functions internally uses the class [DownloaderUtils](https://github.com/aivruu/repo-viewer/blob/recode/api/src/main/java/io/github/aivruu/repoviewer/api/download/DownloaderUtils.java) for this processes. To use these functions we invoke them
given their required parameters, and handle them for possible results:

```java
// ...
requestHttpModel.thenAccept(latestReleaseModel -> {
  if (latestReleaseModel == null) return;

  final var downloadedFileDirectory = new File("downloads");
  if (!downloadedFileDirectory.exists()) downloadedFileDirectory.mkdir();

  latestReleaseModel.downloadFrom(downloadedFileDirectory, 0)}
    .whenComplete((downloaded, exception) ->
      if (exception != null) System.out.println("An exception has happened during download!");
    )
    .thenAccept(downloaded ->
      if (downloaded) System.out.println("File downloaded"); 
    );
});
// ...
```
An example downloading a single file into an expected directory.
```java
// ...
requestHttpModel.thenAccept(latestReleaseModel -> {
  if (latestReleaseModel == null) return;

  final var filesDirectory = new File("downloads");
  if (!filesDirectory.exists()) filesDirectory.mkdir();

  latestReleaseModel.downloadAll(downloadedFileDirectory)}
    .whenComplete((downloaded, exception) ->
      if (exception != null) System.out.println("An exception has happened during download!");
    )
    .thenAccept(downloaded -> {
      if (downloaded) System.out.println("Files downloaded");
      else System.out.println("No files downloaded.");
    });
});
// ...
```
