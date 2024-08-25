# How to download assets from the requested repository's latest-release?
A [LatestReleaseModel](https://github.com/aivruu/repo-viewer/blob/recode/api/src/main/java/io/github/aivruu/repoviewer/api/release/LatestReleaseModel.java) have the possibility of download one, or all the assets adjunted by the developer at the current latest-release, at a pre-defined directory.

This model provide two methods, `downloadFrom(File, int)` & `downloadAll(File)`. Both functions requires a `File` object which represent the directory
where the asset, or assets will be downloaded. The first method require an `int-value` which is the asset's position at the assets-array to be downloaded, and will return a `DownloadStatusProvider`. The second only require the destination-directory for downloaded-files, and will return the number of assets downloaded.

This functions internally uses the class [DownloaderUtils](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/api/download/DownloaderUtils.java) for this processes, and provides a [DownloadStatusProvider](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/api/download/status/DownloadStatusProvider.java) which contains the current status-code and result (that are the read-bytes number) for the download operation:

```java
// ...
if (latestReleaseModel == null) return;
final var downloadedFileDirectory = new File("downloads");
if (!downloadedFileDirectory.exists()) downloadedFileDirectory.mkdir();

latestReleaseModel.downloadFrom(downloadedFileDirectory, 0).thenAccept(downloadStatusProvider -> {
  if (downloadStatusProvider.finished()) System.out.println("File downloaded"); 
});
// ...
```
An example downloading a single file into an expected directory.
```java
// ...
if (latestReleaseModel == null) return;
final var filesDirectory = new File("downloads");
if (!filesDirectory.exists()) filesDirectory.mkdir();

latestReleaseModel.downloadAll(downloadedFileDirectory).thenAccept(assetsAmount -> {
  if (assetsAmount >= 1) System.out.println("Files downloaded");
  else System.out.println("No files downloaded.");
});
// ...
```
