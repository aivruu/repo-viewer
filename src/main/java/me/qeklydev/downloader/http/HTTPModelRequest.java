/*
 * This file is part of release-downloader - https://github.com/aivruu/release-downloader
 * Copyright (C) 2020-2024 Aivruu (https://github.com/aivruu)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.qeklydev.downloader.http;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import me.qeklydev.downloader.codec.ReleaseModelDeserializer;
import me.qeklydev.downloader.release.ReleaseModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This record is used for each HTTP request triggered
 * to the repository URL given, and provides handling
 * about the latest release for this repository.
 *
 * @param repository the repository url.
 * @since 0.0.1
 */
public record HTTPModelRequest(@NotNull String repository) {
  /**
   * The HTTP client used for the requests.
   *
   * @since 0.0.1
   */
  private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
  /**
   * The maximum time-out for the HTTP request.
   *
   * @since 0.0.1
   */
  private static final Duration TIME_OUT = Duration.ofSeconds(60);

  /**
   * Executes the request to the API for receive a json
   * file with the release information, this file is deserialized
   * and is returned with the completable-future.
   *
   * @return The {@link CompletableFuture} with a
   *     {@link ReleaseModel} with the latest
   *     release information. Otherwise {@code null}.
   * @since 0.0.1
   * @see HTTPModelRequest#executeGETRequest()
   */
  public @NotNull CompletableFuture<@Nullable ReleaseModel> provideLatestRelease() {
    return this.executeGETRequest().thenApply(json -> {
      if (json == null) {
        return null;
      }
      return ReleaseModelDeserializer.GSON.fromJson(json, ReleaseModel.class);
    });
  }

  /**
   * Executes a GET type request to the API with a specific
   * time-out, the response can be a Json text. Or empty if
   * an exception is triggered during the operation.
   *
   * @return The {@link CompletableFuture} with the final
   *     request response.
   * @since 0.0.1
   */
  public @NotNull CompletableFuture<@Nullable String> executeGETRequest() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        final var request = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(this.repository + "/releases/latest"))
            .timeout(TIME_OUT)
            .build();
        final var response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        final var responseStatusCode = response.statusCode();
        /*
         * We need to check if the requested repository exists,
         * in this case the HTTP request will respond with a 404
         * status code.
         */
        return (responseStatusCode == 404) ? null : response.body();
      } catch (final Exception exception) {
        exception.printStackTrace();
        return null;
      }
    });
  }
}
