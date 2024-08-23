# How to make a request to a Repository, or get their Latest Release
For this, we have the [GithubHttpRequestModel](https://github.com/aivruu/repo-viewer/blob/recode/api/src/main/java/io/github/aivruu/repoviewer/api/http/GithubHttpRequestModel.java)
interface, which is used as main-model for another implementations responsable of handling the requests made to the specified GitHub repository,
and the latest-release for that repository. You can create your own implementation for make custom-requests to the API.

This interface provides four methods (two default): `requestUsing(HttpClient, int)`, `request(int)`, `requestUsingThen(HttpClient, int, Consumer<Model>)`, `requestThen(int, Consumer<Model>)`. The methods that ends with ___Then___ will require a Consumer which their defined-type will correspond to the model
for the request, which can be a `GithubRepositoryModel` or `LatestReleaseModel`, that logic
will be executed only if a response was provided and the json-body was deserialized into respective model, otherwise, if due to some reason a `null-value` was returned, the consumer will be ignored. The functions that have ___Using___ will require a configured http-client that will be used to perform the requests to the specified repository/release. All these functions also will require an `int-type` parameter which is the max-timeout for every made http-request.

This interface have two implementations, [ReleaseHttpRequestModel](https://github.com/aivruu/repo-viewer/blob/recode/implementation/src/main/java/io.github.aivruu.repoviewer/ReleaseHttpRequestModel.java)
used for repository's latest-release request, this implementation will return a `LatestReleaseModel`. [RepositoryHttpRequestModel](https://github.com/aivruu/repo-viewer/blob/recode/implementation/src/main/java/io.github.aivruu.repoviewer/RepositoryHttpRequestModel.java)
is used for repository requests, and will return a `GithubRepositoryModel` for the made request.

To use this implementations only we need to give them the url for the repository which we are searching for, for get an usable-url for the request,
we use the [RepositoryUrlBuilder](https://github.com/aivruu/repo-viewer/blob/recode/implementation/src/main/java/io.github.aivruu.repoviewer/RepositoryUrlBuilder.java)
class, which provides the method `from(String, String)` that require the repository's creator username, and the repository's name.

This examples also applies for the [RepositoryHttpRequestModel](https://github.com/aivruu/repo-viewer/blob/recode/implementation/src/main/java/io.github.aivruu.repoviewer/RepositoryHttpRequestModel.java),
where the process is the same for repository requests, with the difference that this implementation returns a `CompletableFuture<GithubRepositoryModel>`.

```java
final var releaseHttpRequestModel = new ReleaseHttpRequestModel(RepositoryUrlBuilder.from("aivruu", "repo-viewer"));
releaseHttpRequest.request(/** Timeout will be 5 seconds. */ 5).thenAccept(latestReleaseModel -> {
  // The deserialized-model could be null, so we need to check first.
  if (latestReleaseModel == null) return;
  System.out.println("Viewing latest-release with version: " + latestReleaseModel.version());
});
```
An example using a pre-configured http-client for make the request:
```java
final var customHttpClient = HttpClient.newBuilder()
  .version(HttpClient.Version.HTTP_1_1) // We define http-protocol 1.1 to use.
  .build();
final var releaseHttpRequestModel = new ReleaseHttpRequestModel(RepositoryUrlBuilder.from("aivruu", "repo-viewer"));
releaseHttpRequest.requestUsing(customHttpClient, 5) // Timeout will be 5 seconds.
  .thenAccept(latestReleaseModel -> {
    // The deserialized-model could be null, so we need to check first.
    if (latestReleaseModel == null) return;
    System.out.println("Viewing latest-release with version: " + latestReleaseModel.version());
  })
  .thenRun(customHttpClient::close); // This http-client will be closed once the completable-future has been completed.
```
Examples using a Consumer to define the logic to execute in case that a response was provided and a model was deserialized
with the `json-body` from that response.
```java
final var releaseHttpRequestModel = new ReleaseHttpRequestModel(RepositoryUrlBuilder.from("aivruu", "repo-viewer"));
releaseHttpRequest.requestThen(/** Timeout will be 5 seconds. */ 5, latestReleaseModel ->
  System.out.println("Viewing latest-release with version: " + latestReleaseModel.version()));
```
Example with a configured http-client:
```java
final var customHttpClient = HttpClient.newBuilder()
  .version(HttpClient.Version.HTTP_1_1) // We define http-protocol 1.1 to use.
  .build();
final var releaseHttpRequestModel = new ReleaseHttpRequestModel(RepositoryUrlBuilder.from("aivruu", "repo-viewer"));
releaseHttpRequest.requestUsingThen(customHttpClient, /** Timeout will be 5 seconds. */ 5, latestReleaseModel -> cache.put("release-http-request", latestReleaseModel))
  .thenAccept(latestReleaseModel ->
    System.out.println("Repository's latest-release with version: " + latestReleaseModel.version());
  )
  .thenRun(customHttpClient::close); // This http-client will be closed once the completable-future has been completed.
```
