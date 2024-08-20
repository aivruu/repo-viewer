# How to make a request to a Repository, or get their Latest Release
For this, we have the [GithubHttpRequestModel](https://github.com/aivruu/repo-viewer/blob/recode/api/src/main/java/io/github/aivruu/repoviewer/api/http/GithubHttpRequestModel.java)
interface, which is used as main-model for another implementations responsable of handling the requests made to the specified GitHub repository,
and the latest-release for that repository. You can create your own implementation for make custom-requests to the API.

This interface provides two methods (one default), `requestUsing(HttpClient, int)` or `request(int)`. The first requires an `HttpClient` object
which will be used for the request handling, and the max-timeout (on seconds) for this request. The second method only require the timeout (on seconds) 
for the request.

This interface have two implementations, [ReleaseHttpRequestModel](https://github.com/aivruu/repo-viewer/blob/recode/implementation/src/main/java/io.github.aivruu.repoviewer/ReleaseHttpRequestModel.java)
used for repository's latest-release request, this implementation will return a `LatestReleaseModel`. [RepositoryHttpRequestModel](https://github.com/aivruu/repo-viewer/blob/recode/implementation/src/main/java/io.github.aivruu.repoviewer/RepositoryHttpRequestModel.java)
is used for repository requests, and will return a `GithubRepositoryModel` for the made request.

To use this implementations only we need to give them the url for the repository which we are searching for, for get an usable-url for the request,
we use the [RepositoryUrlBuilder](https://github.com/aivruu/repo-viewer/blob/recode/implementation/src/main/java/io.github.aivruu.repoviewer/RepositoryUrlBuilder.java)
class, which provides the method `from(String, String)` that require the repository's creator username, and the repository's name.

This examples also applies for the [RepositoryHttpRequestModel](https://github.com/aivruu/repo-viewer/blob/recode/implementation/src/main/java/io.github.aivruu.repoviewer/RepositoryHttpRequestModel.java),
where the process is the same for repository requests, with the difference that this implementation returns a `GithubRepositoryModel`.

```java
// ...
final var releaseHttpRequestModel = new ReleaseHttpRequestModel(RepositoryUrlBuilder.from("aivruu", "repo-viewer"));
releaseHttpRequest.request(5) // Timeout will be 5 seconds.
  .thenAccept(latestReleaseModel -> {
    // The deserialized-model could be null, so we need to check first.
    if (latestReleaseModel == null) return;

    System.out.println("Viewing latest-release with version: " + latestReleaseModel.version());
  });
// ...
```

An example using a pre-configured http-client for make the request:
```java
// ...
final var customHttpClient = HttpClient.newBuilder()
  .version(HttpClient.Version.HTTP_1_1) // We define http-protocol 1.1 to use.
  .build();
final var releaseHttpRequestModel = new ReleaseHttpRequestModel(RepositoryUrlBuilder.from("aivruu", "repo-viewer"));
releaseHttpRequest.requestUsing(customHttpClient, 5) // Timeout will be 5 seconds.
  .thenAccept(latestReleaseModel -> {
    // The deserialized-model could be null, so we need to check first.
    if (latestReleaseModel == null) return;

    System.out.println("Viewing latest-release with version: " + latestReleaseModel.version());
  });
customHttpClient.close();
// ...
```
