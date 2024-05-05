/*
 * This file is part of release-downloader - https://github.com/aivruu/release-downloader
 * Copyright (C) 2020-2024 aivruu (https://github.com/aivruu)
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
import me.qeklydev.downloader.codec.DeserializationProvider;
import me.qeklydev.downloader.logger.LoggerUtils;
import me.qeklydev.downloader.release.ReleaseModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This record class is used to proportionate handling
 * about the latest requests of any GitHub repository.
 *
 * @param httpClient the {@link HttpClient} for the
 *                   request.
 * @param repository the requested repository.
 * @since 0.0.1
 */
public record HTTPReleaseModelRequest(@NotNull HttpClient httpClient, @NotNull String repository) implements HTTPModelRequest<ReleaseModel> {
  @Override
  public @Nullable ReleaseModel provideModel() {
    final var bodyOrNull = this.executeGETRequest();
    // Once the request have been completed and future has ended,
    // we obtain the response that will be a JSON-body, or null in
    // case that an exception was triggered.
    return (bodyOrNull == null) ? null : (ReleaseModel) DeserializationProvider.builder()
        .jsonBody(bodyOrNull)
        .codecType(DeserializationProvider.CodecType.RELEASE)
        .build();
  }

  @Override
  public @Nullable String executeGETRequest() {
    try {
      final var request = HttpRequest.newBuilder()
          .GET()
          .uri(new URI(this.repository + "/releases/latest"))
          .build();
      final var asyncResponse = this.httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
      // The repository request to the API is performed before this request
      // execution, this request only will be executed if the requested repository
      // exists, so, we can skip the non-found repositories check, and return the single
      // JSON-body once the completable-future has ended for this request.
      return asyncResponse.thenApply(HttpResponse::body).get();
    } catch (final Exception exception) {
      LoggerUtils.error("Http request for repository latest release could not be completed.");
      return null;
    }
  }
}
