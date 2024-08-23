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
package io.github.aivruu.repoviewer;

import io.github.aivruu.repoviewer.api.http.GithubHttpRequestModel;
import io.github.aivruu.repoviewer.api.logger.LoggerUtils;
import io.github.aivruu.repoviewer.api.release.LatestReleaseModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * This {@link GithubHttpRequestModel} implementation is used to handle http-requests for the
 * latest-release of the specified repository.
 *
 * @param repository the specified repository's url.
 * @since 0.0.1
 */
public record ReleaseHttpRequestModel(@NotNull String repository) implements GithubHttpRequestModel<LatestReleaseModel> {
  @Override
  public CompletableFuture<@Nullable LatestReleaseModel> requestUsing(final HttpClient httpClient, final int timeout) {
    final var responseJsonBody = this.response(httpClient, timeout);
    return responseJsonBody.thenApply(response -> CodecProviderImpl.INSTANCE.from(LatestReleaseModel.class, response));
  }

  @Override
  public CompletableFuture<@Nullable LatestReleaseModel> requestUsingThen(final HttpClient httpClient, final int timeout,
                                                                          final Consumer<LatestReleaseModel> consumer) {
    final var responseJsonBody = this.response(httpClient, timeout);
    return responseJsonBody.thenApply(response -> {
      final var releaseModel = CodecProviderImpl.INSTANCE.from(LatestReleaseModel.class, response);
      if (releaseModel != null) consumer.accept(releaseModel);
      return releaseModel;
    });
  }

  private CompletableFuture<String> response(final HttpClient httpClient, final int timeout) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        final var request = HttpRequest.newBuilder().GET()
          .timeout(Duration.ofSeconds(timeout))
          .uri(new URI(this.repository + "/releases/latest"))
          .build();
        final var requestReceivedResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return requestReceivedResponse.body(); // Returns the json-body provided by the request's response.
      } catch (final IOException | InterruptedException | URISyntaxException exception) {
        return null;
      }
    }, EXECUTOR).exceptionally(exception -> {
      LoggerUtils.error("Request for latest-release of '%s' repository could not be completed."
        .formatted(this.repository));
      return null;
    });
  }
}
