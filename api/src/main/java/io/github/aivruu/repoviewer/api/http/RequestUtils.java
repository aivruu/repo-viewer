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

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * This class is used to proportionate utilities about http-requests handling, and providing
 * response-status.
 *
 * @since 3.3.4
 */
public class RequestUtils {
  /** Four fixed-thread-pool used for http-requests operations. */
  private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(4,
    r -> new Thread(r, "HttpRequest-Thread"));

  /**
   * Performs a http-request to the specified url using the given {@link HttpClient}, and the
   * timeout for this request, and provides a response for the request made.
   *
   * @param httpClient the http-client for this request.
   * @param timeout the max-timeout for the request.
   * @param url the url to request.
   * @return The {@link CompletableFuture} with a {@link HttpResponse} which can be {@code null}.
   * @since 3.3.4
   */
  public static CompletableFuture<@Nullable HttpResponse<String>> response(
    final HttpClient httpClient, final int timeout, final String url
  ) {
    try {
      final var request = HttpRequest.newBuilder().GET()
        .timeout(Duration.ofSeconds(timeout))
        .uri(new URI(url))
        .build();
      return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    } catch (final URISyntaxException exception) {
      return CompletableFuture.supplyAsync(() -> null, EXECUTOR);
    }
  }

  /**
   * Returns a {@link ResponseStatusProvider} based on the response-disponibility, and their status-code.
   *
   * @param response the response provided by the http-request.
   * @param consumer the consumer for the deserialized-model, {@code null} on functions that doesn't
   *                 execute additional logic with deserialized-models.
   * @param codecProvider a {@link CodecProvider} implementation.
   * @param modelType the model-type to deserialize if response and status-code are valid.
   * @param <Model> an object which implements the {@link RequestableModel} interface.
   * @return The {@link ResponseStatusProvider} with the deserialized-model, or {@code null}.
   *     <p>
   *     - {@link ResponseStatusProvider#requestValidResponse(RequestableModel)} if the response is valid
   *     and model was deserialized.
   *     <p>
   *     - {@link ResponseStatusProvider#requestInvalidResponse()} if the response was not provided, the
   *     status-code was '404', wasn't valid, or if the model couldn't be deserialized.
   *     <p>
   *     - {@link ResponseStatusProvider#requestUnauthorizedResponse()} if the status-code was '401', only
   *     used during requests to repositories' releases.
   *     <p>
   *     - {@link ResponseStatusProvider#requestMovedPermanentlyResponse()} if the status-code was '301', only
   *     used during requests to repositories.
   *     <p>
   *     - {@link ResponseStatusProvider#requestForbiddenResponse()} if the status-code was '403', only used
   *     during requests to repositories.
   * @since 3.3.4
   */
  public static <Model extends RequestableModel> ResponseStatusProvider<@Nullable Model> verifyAndProvideResponse(
    final @Nullable HttpResponse<String> response, final @Nullable Consumer<Model> consumer, final CodecProvider codecProvider,
    final Class<Model> modelType
  ) {
    if (response == null) return ResponseStatusProvider.requestInvalidResponse();
    // Provided status-code type verification.
    return switch (response.statusCode()) {
      case 404 -> ResponseStatusProvider.requestInvalidResponse();
      case 401 -> ResponseStatusProvider.requestUnauthorizedResponse();
      case 301 -> ResponseStatusProvider.requestMovedPermanentlyResponse();
      case 403 -> ResponseStatusProvider.requestForbiddenResponse();
      case 200 -> {
        // Json-body deserialization and verification for response and consumer's logic execution.
        final var model = codecProvider.from(modelType, response.body());
        if (model == null) {
          yield ResponseStatusProvider.requestInvalidResponse();
        }
        if (consumer != null) {
          consumer.accept(model);
        }
        yield ResponseStatusProvider.requestValidResponse(model);
      }
      // Should not happen this.
      default -> ResponseStatusProvider.requestInvalidResponse();
    };
  }
}
