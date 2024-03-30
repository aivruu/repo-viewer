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
import java.util.concurrent.CompletableFuture;
import me.qeklydev.downloader.codec.DeserializationUtils;
import me.qeklydev.downloader.repository.GitHubRepositoryModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record HTTPRepositoryModelRequest(@NotNull HttpClient httpClient, @NotNull String repository) implements HTTPModelRequest<GitHubRepositoryModel> {
  @Override
  public @Nullable GitHubRepositoryModel provideModel() {
    final var jsonResponseOrNull = this.executeGETRequest().join();
    /*
     * Wait until request have been completed, then request
     * the response of the HTTP request.
     */
    return (jsonResponseOrNull == null) ? null : DeserializationUtils.withRepositoryCodec(jsonResponseOrNull);
  }

  @Override
  public @NotNull CompletableFuture<@Nullable String> executeGETRequest() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        final var request = HttpRequest.newBuilder()
            .GET()
            .uri(new URI(this.repository))
            .version(HttpClient.Version.HTTP_1_1)
            .timeout(TIME_OUT)
            .build();
        final var response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
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
