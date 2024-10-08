//
// Copyright (C) 2024 Aivruu - repo-viewer
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
package io.github.aivruu.repoviewer.api.http;

import io.github.aivruu.repoviewer.api.codec.CodecProvider;
import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import io.github.aivruu.repoviewer.api.http.status.ResponseStatusProvider;
import org.jetbrains.annotations.Nullable;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * This interface is used for provide functions to work with requests to the GitHub API.
 *
 * @param <Model> The model-type to use for the request.
 * @since 3.3.4
 */
public interface GithubHttpRequestModel<Model extends RequestableModel> {
  /**
   * Executes the {@code GET} request-type using the specified {@link HttpClient}, and return
   * the serialized-model if a {@code json-body} was provided, otherwise result will be a {@code null}.
   *
   * @param httpClient the {@link HttpClient} to use for this request.
   * @param timeout the time-out for the request.
   * @return The {@link CompletableFuture} with a {@link ResponseStatusProvider}, check possible responses
   *     at {@link RequestUtils#verifyAndProvideResponse(HttpResponse, Consumer, CodecProvider, Class)}.
   * @since 2.3.4
   */
  CompletableFuture<ResponseStatusProvider<@Nullable Model>> requestUsing(final HttpClient httpClient, final int timeout);

  /**
   * Executes the {@code GET} request-type using the specified {@link HttpClient}, then, if the
   * deserialized-model isn't null, the consumer's logic will be called with the model as argument, then
   * the model will be returned only if a {@code json-body} was provided, otherwise will
   * return {@code null} and the consumer's logic will not be executed.
   *
   * @param httpClient the {@link HttpClient} to use for this request.
   * @param timeout the time-out for the request.
   * @param consumer the logic to execute with the given model, if the request produced a
   *                 response ({@code json-body}).
   * @return The {@link CompletableFuture} with a {@link ResponseStatusProvider}, check possible responses
   *     at {@link RequestUtils#verifyAndProvideResponse(HttpResponse, Consumer, CodecProvider, Class)}.
   * @since 2.3.4
   */
  CompletableFuture<ResponseStatusProvider<@Nullable Model>> requestUsingThen(final HttpClient httpClient, final int timeout,
                                                                              final Consumer<Model> consumer);

  /**
   * Executes the {@code GET} request-type using a default new {@link HttpClient}, once the
   * request has ended, the early created {@link HttpClient} is closed.
   *
   * @param timeout the time-out for this request.
   * @return The {@link CompletableFuture} with a {@link ResponseStatusProvider}, this provider will have
   *     a possible {@code null} model.
   * @see #requestUsing(HttpClient, int)
   * @since 2.3.4
   */
  default CompletableFuture<ResponseStatusProvider<@Nullable Model>> request(final int timeout) {
    final var httpClient = HttpClient.newHttpClient();
    final var requestResponseStatusProvider = this.requestUsing(httpClient, timeout);
    httpClient.close(); // Close http-client after the request.
    return requestResponseStatusProvider;
  }

  /**
   * Realizes an internal execution to {@link #requestUsingThen(HttpClient, int, Consumer)} using the
   * given parameters, and a non-configured http-client for the request handling that will be closed
   * once the request has been made.
   *
   * @param timeout the time-out for this request.
   * @param consumer the logic to execute with the given model, if the request produced a
   *                 response ({@code json-body}).
   * @return The {@link CompletableFuture} with a {@link ResponseStatusProvider}, this provider will have
   *     a possible {@code null} model.
   * @see #requestUsingThen(HttpClient, int, Consumer)
   * @since 2.3.4
   */
  default CompletableFuture<ResponseStatusProvider<@Nullable Model>> requestThen(final int timeout, final Consumer<Model> consumer) {
    final var httpClient = HttpClient.newHttpClient();
    final var requestResponseStatusProvider = this.requestUsingThen(httpClient, timeout, consumer);
    httpClient.close(); // Close http-client after the request.
    return requestResponseStatusProvider;
  }
}
