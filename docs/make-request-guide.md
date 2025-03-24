# How to make a request to a Repository, or get a specific-release

To make a request initially, we need to create one, for this, we use the Request class which is a factory for the
requests creation, for the url-building we can use the RequestURLBuilder for it, a basic-example of how to create a request
for a repository's latest-release:

```java
final var releaseRequest = Request.create()
  // Or use forRepository for a specific-repository request.
  .url(RequestURLBuilder.forRelease("aivruu", "repo-viewer", "latest"))
  // We can provide a custom-client, or not define a custom-one for  use the default-one the library provides.
  .client(HttpClient.newBuilder()
    .executor(Executors.newSingleThreadExecutor())
    .build()
  )
  // We specify the timeout, this value is always represented in-seconds.
  .timeout(10)
  // In case you want to request a repository, you should use the repository() method to get a RepositoryRequest object.
  .release();
```

There's two implementations to handle repository and release http-requests, ReleaseRequest & RepositoryRequest, both
inherits from super-class AbstractRequest, you can make your own implementations from this if you want to.

Once the object is created, there's a few methods you can use to make the request, receive and handle the information.
* `requestAndHandle()` - It'll make the request, handle the result for null-values or exceptions-throwing, and using the 
  `validateAndProvideResponse(HttpRequest<String>)` will provide a specific `RequestResponseStatus` for the operation.
  This method will return a `CompletableFuture` that will provide a non-null `RequestResponseStatus`object.
* `request()` - This method will make the request and return a `CompletableFuture` with a non-null `HttpResponse<String>`
  object, but it'll not provide any handling for the request itself.
* `close()` - It'll close the `HttpClient` used to perform the request.

The previously-mentioned implementations override the `validateAndProvideResponse(HttpRequest<String>)` method, to proportionate
a specific `RequestResponseStatus` object for that operation, this object could contain a null-value, or a AggregateRoot
inheritor, such as RequestAggregateRoot or RepositoryAggregateRoot, which will provide access to the information provided
by the request after it be processed.

After the request was made and processed, you can manipulate the object provided, but, before we need to verify that a valid-response
was provided using `RequestResponseStatus#wasValid`, otherwise, it will mean that the response's result is `null`.

```java
// ...
final var requestResponseStatus = releaseRequest.requestAndHandle().join;
if (!requestResponseStatus.isValid()) {
  this.logger.error("The request was made, but the response is not valid, status-code: {}", responseStatus.status());
  return;
}
// Assuming we're requesting a release, otherwise, we should receive a RepositoryAggregateRoot.
final var releaseAggregateRoot = responseStatus.result();
// Now we can manipulate the result to access to its information.
this.logger.info("Request successfull, received information for release with id: {}", releaseAggregateRoot.id());
// We close the http-client used for the request.
releaseRequest.close();
```
