//
// Copyright (C) 2024-2025 aivruu - repo-viewer
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.
//
package io.github.aivruu.repoviewer.http.domain;

import io.github.aivruu.repoviewer.aggregate.domain.AggregateRoot;
import io.github.aivruu.repoviewer.executor.application.ExecutorHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Represents a request-model that processes received-information to build them into a {@link AggregateRoot}.
 *
 * @param <A> an object which inherits the {@link AggregateRoot} class.
 * @since 4.0.0
 */
public abstract class AbstractRequest<A extends AggregateRoot> {
  /** A {@link Logger} used for exceptions-details providing. */
  private static final Logger LOGGER = Logger.getLogger("repo-viewer");
  /**
   * A default {@link HttpClient} object with a custom {@link java.util.concurrent.Executor} defined.
   * This client uses a custom-executor provided by {@link ExecutorHelper#get()}, which must be initialized priorly.
   */
  private static final HttpClient DEFAULT_CLIENT = HttpClient.newBuilder()
    .executor(ExecutorHelper.get())
    .build();
  /** The default timeout-value (in seconds) to use if none is provided. */
  private static final byte DEFAULT_TIMEOUT_IN_SECONDS = 5;
  protected final URI uri;
  protected final HttpClient client;
  protected final int timeout;

  /**
   * Creates a new {@link AbstractRequest} with the given parameters.
   *
   * @param uri the url for the request.
   * @param client the {@link HttpClient} to use, {@code null} for use {@link #DEFAULT_CLIENT}.
   * @param timeout the max-timeout for the request.
   * @since 4.0.0
   */
  protected AbstractRequest(final @NotNull URI uri, final @Nullable HttpClient client, final int timeout) {
    this.uri = uri;
    this.client = (client == null) ? DEFAULT_CLIENT : client;
    this.timeout = timeout;
  }

  /**
   * Returns the {@link URI} used for this request.
   *
   * @return This request's uri.
   * @since 4.0.0
   */
  public final @NotNull URI uri() {
    return this.uri;
  }

  /**
   * Returns the {@link HttpClient} used for this request.
   *
   * @return The request's http-client.
   * @since 4.0.0
   */
  public final @NotNull HttpClient client() {
    return this.client;
  }

  /**
   * Returns the request's timeout.
   *
   * @return The request's timeout.
   * @since 4.0.0
   */
  public final int timeout() {
    return this.timeout;
  }

  /**
   * Makes a request to the provided url and handles it asynchronously to provide a {@link RequestResponseStatus}
   * based-on the request's response's status-code.
   *
   * @return A {@link CompletableFuture} with a {@link RequestResponseStatus}.
   * @see #request()
   * @see #validateAndProvideResponse(HttpResponse)
   * @since 4.0.0
   */
  public final @NotNull CompletableFuture<@NotNull RequestResponseStatus<@Nullable A>> requestAndHandle() {
    final var future = this.request();
    return future.exceptionally(exception -> {
      LOGGER.severe("Unexpected exception when making request to the url: %s with the following message: %s".formatted(
        this.uri.toString(), exception.getMessage()));
      return null;
    }).thenApply(this::validateAndProvideResponse);
  }

  /**
   * Makes a request to the specified url using the client and timeout provided.
   *
   * @return A {@link CompletableFuture} with a {@link HttpResponse}.
   * @since 4.0.0
   */
  public @NotNull CompletableFuture<@Nullable HttpResponse<String>> request() {
    final var request = HttpRequest.newBuilder()
      .GET()
      .timeout(Duration.ofSeconds((this.timeout < 0) ? DEFAULT_TIMEOUT_IN_SECONDS : this.timeout))
      .uri(this.uri)
      .build();
    return this.client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
  }

  /**
   * Validates the provided {@link HttpResponse}'s status-code to provide a {@link RequestResponseStatus}.
   *
   * @param response the response to process.
   * @return A {@link RequestResponseStatus} which can be:
   * <ul>
   * <li>{@link RequestResponseStatus#invalid()} if the response is null, or status-code is unknown.</li>
   * <li>{@link RequestResponseStatus#unauthorized()} for status-code {@code 401}, result is null.</li>
   * <li>{@link RequestResponseStatus#moved()} for status-code {@code 301}, result is null.</li>
   * <li>{@link RequestResponseStatus#forbidden()} for status-code {@code 403}, result is null.</li>
   * <li>{@link RequestResponseStatus#pending()} for status-code {@code 200}, result is still null.</li>
   * </ul>
   * @since 4.0.0
   */
  public @NotNull RequestResponseStatus<@Nullable A> validateAndProvideResponse(
    final @Nullable HttpResponse<String> response
  ) {
    return (response == null) ? RequestResponseStatus.invalid() : switch (response.statusCode()) {
      case 401 -> RequestResponseStatus.unauthorized();
      case 301 -> RequestResponseStatus.moved();
      case 403 -> RequestResponseStatus.forbidden();
      case 200 -> RequestResponseStatus.pending();
      default -> RequestResponseStatus.invalid();
    };
  }

  /**
   * Closes the provide {@link HttpClient} instance for this request.
   *
   * @since 4.0.0
   */
  public final void close() {
    this.client.close();
  }
}
