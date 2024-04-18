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
import me.qeklydev.downloader.codec.DeserializationUtils;
import me.qeklydev.downloader.logger.LoggerUtils;
import me.qeklydev.downloader.repository.GitHubRepositoryModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This record class is used to proportionate handling
 * about the requests for repositories information.
 *
 * @param httpClient the {@link HttpClient} for the
 *                   request.
 * @param repository the requested repository.
 * @since 0.0.1
 */
public record HTTPRepositoryModelRequest(@NotNull HttpClient httpClient, @NotNull String repository) implements HTTPModelRequest<GitHubRepositoryModel> {
  @Override
  public @Nullable GitHubRepositoryModel provideModel() {
    final var jsonResponseOrNull = this.executeGETRequest();
    /*
     * Wait until request have been completed, then request
     * the response of the HTTP request.
     */
    return (jsonResponseOrNull == null) ? null : DeserializationUtils.withRepositoryCodec(jsonResponseOrNull);
  }

  @Override
  public @Nullable String executeGETRequest() {
    try {
      final var request = HttpRequest.newBuilder()
          .GET()
          .uri(new URI(this.repository))
          .build();
      final var asyncResponse = this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
      /*
       * Wait until async request is completed and a response is returned,
       * we check if status code is 404, at that case return null, otherwise
       * return the json body for the request.
       */
      return asyncResponse.thenApply(providedRequestResponse -> {
        final var responseStatusCode = providedRequestResponse.statusCode();
        /*
         * We need to check if the requested repository exists,
         * in this case the HTTP request will respond with a 404
         * status code.
         */
        return (responseStatusCode == 404) ? null : providedRequestResponse.body();
      }).get();
    } catch (final Exception exception) {
      LoggerUtils.error("Http request for repository could not be completed.");
      return null;
    }
  }
}
