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
import me.qeklydev.downloader.release.ReleaseModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record HTTPReleaseModelRequest(@NotNull HttpClient httpClient, @NotNull String repository) implements HTTPModelRequest<ReleaseModel> {
  @Override
  public @Nullable ReleaseModel provideModel() {
    final var jsonResponseOrNull = this.executeGETRequest();
    /*
     * Wait until request have been completed, then request
     * the response of the HTTP request.
     */
    return (jsonResponseOrNull == null) ? null : DeserializationUtils.withReleaseCodec(jsonResponseOrNull);
  }

  @Override
  public @Nullable String executeGETRequest() {
    try {
      final var request = HttpRequest.newBuilder()
          .GET()
          .uri(new URI(this.repository + "/releases/latest"))
          .version(HttpClient.Version.HTTP_1_1)
          .timeout(TIME_OUT)
          .build();
      final var asyncResponse = this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
      /*
       * The repository request to the API is performed early, this request
       * only is performed if the requested repository exists, so we can skip
       * the check for non-found repositories, then return the JSON body once
       * the future is complete.
       */
      return asyncResponse.thenApply(HttpResponse::body).get();
    } catch (final Exception exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
