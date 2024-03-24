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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import me.qeklydev.downloader.codec.ReleaseModelDeserializer;
import me.qeklydev.downloader.release.ReleaseModel;
import org.jetbrains.annotations.NotNull;

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
   * Maximum time for the connection and read HTTP
   * request time-outs.
   *
   * @since 0.0.1
   */
  private static final byte TIME_OUT = (byte) TimeUnit.MILLISECONDS.toSeconds(60);

  /**
   * Executes the request to the API for receive a json
   * file with the release information, this file is deserialized
   * and is returned with the completable-future.
   *
   * @return The {@link CompletableFuture} with a
   *     {@link ReleaseModel} with the latest
   *     release information.
   * @since 0.0.1
   */
  public @NotNull CompletableFuture<@NotNull ReleaseModel> provideLatestRelease() {
    return this.executeGETRequest().thenApply(json ->
        ReleaseModelDeserializer.GSON.fromJson(json, ReleaseModel.class));
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
  public @NotNull CompletableFuture<@NotNull String> executeGETRequest() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        final var urlContentBuilder = new StringBuilder();
        final var url = new URL(this.repository + "/releases/latest");
        final var urlConnection = url.openConnection();
        final var httpConnection = (HttpURLConnection) urlConnection;
        httpConnection.setRequestMethod("GET");
        httpConnection.setReadTimeout(TIME_OUT);
        httpConnection.setConnectTimeout(TIME_OUT);
        try (final var requestReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()))) {
          String line;
          while ((line = requestReader.readLine()) != null) {
            urlContentBuilder.append(line.trim());
          }
        }
        return urlContentBuilder.toString();
      } catch (final Exception exception) {
        exception.printStackTrace();
        return "";
      }
    });
  }
}
