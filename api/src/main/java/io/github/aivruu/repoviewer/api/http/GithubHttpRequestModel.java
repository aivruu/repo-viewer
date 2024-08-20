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

import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import org.jetbrains.annotations.Nullable;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;

/**
 * This base-model for another request-implementations such as for releases or repositories.
 *
 * @param <Model> The model-type to use for this request.
 * @since 1.0.0
 */
public interface GithubHttpRequestModel<Model extends RequestableModel> {
  /**
   * Executes the {@code GET} request-type using the specified {@link HttpClient}, and return
   * the serialized-model if a {@code json-body} was provided, otherwise result will be a {@code null}.
   *
   * @param httpClient the {@link HttpClient} to use for this request.
   * @param timeout the time-out for the request.
   * @return The {@link CompletableFuture} with a {@code nullable} value.
   * @since 2.3.4
   */
  CompletableFuture<@Nullable Model> requestUsing(final HttpClient httpClient, final int timeout);

  /**
   * Executes the {@code GET} request-type using a default new {@link HttpClient}, once the
   * request has ended, the early created {@link HttpClient} is closed.
   *
   * @param timeout the time-out for this request.
   * @return The {@link CompletableFuture} with a {@code nullable} value.
   * @see #requestUsing(HttpClient, int)
   * @since 2.3.4
   */
  default CompletableFuture<@Nullable Model> request(final int timeout) {
    final var httpClient = HttpClient.newHttpClient();
    final var requestModelResponse = this.requestUsing(httpClient, timeout);
    httpClient.close(); // Close http-client after the request.
    return requestModelResponse;
  }
}
