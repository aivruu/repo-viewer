# How to download assets from the requested repository's specified-release
The API provides two services for download-purposes, the [`DownloaderService`](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/download/application/DownloaderService.java) & [`AssetDownloaderService`](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/release/application/AssetDownloaderService.java), the first provides a singleton-method
for get a global-instance of it, the second requires an instance of DownloaderService to work, and needs to be initialized.

It should be said that the `DownloaderService` uses a custom thread-pool for the download-operations (which is provided by [`ExecutorHelper`](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/executor/application/ExecutorHelper.java)),
this thread-pool should be initialized prior to perform any download-operation, as well for the requests if you're going to
use the [`AbstractRequest#DEFAULT_CLIENT`](https://github.com/aivruu/repo-viewer/blob/main/api/src/main/java/io/github/aivruu/repoviewer/http/domain/AbstractRequest.java#L45), which uses the same thread-pool too.

```java
// We create a fixed-sized pool of 4 threads, it can throw an exception if the pool is already initialized.
ExecutorHelper.build(4);
// We get the executor for the download-operations, it can throw an exception if the pool is not initialized.
final var executor = ExecutorHelper.get();
```
```java
private final AssetDownloaderService assetDownloaderService = new AssetDownloaderService(DownloaderService.get());
```

For assets-download purposes, is required to use the `AssetDownloaderService`, this class provides a method `download(String[], File, int)`, which its parameters
are `String[] -> Assets | File -> Destination | int -> Asset-Index`. The class uses the `DownloaderService` internally for this purpose, and proportionate
additional handling logic for assets-access and parameters-providing.

```java
// ...
final var destination = new File("downloads");
if (!destination.exists() && !destination.mkdir()) {
  return;
}
final var downloadStatus = this.assetDownloaderService.download(releaseAggregateRoot.assets(), destination, 1);
if (!downloadStatus.wasDownloaded()) {
  return;
}
this.logger.info("The file has been downloaded successfully!");
// ...
```


