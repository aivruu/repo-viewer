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
import io.github.aivruu.repoviewer.api.http.status.ResponseStatusProvider;
import io.github.aivruu.repoviewer.api.release.LatestReleaseModel;
import io.github.aivruu.repoviewer.codec.CodecProviderImpl;
import org.jetbrains.annotations.Nullable;

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
public record ReleaseHttpRequestModel(String repository) implements GithubHttpRequestModel<LatestReleaseModel> {
  @Override
  public CompletableFuture<ResponseStatusProvider<
    @Nullable LatestReleaseModel>> requestUsing(final HttpClient httpClient, final int timeout
  ) {
    return this.response(httpClient, timeout).thenApply(response ->
      GithubHttpRequestModel.verifyAndProvideResponse(response, null, CodecProviderImpl.INSTANCE, LatestReleaseModel.class));
  }

  @Override
  public CompletableFuture<ResponseStatusProvider<
    @Nullable LatestReleaseModel>> requestUsingThen(final HttpClient httpClient, final int timeout, final Consumer<LatestReleaseModel> consumer
  ) {
    return this.response(httpClient, timeout).thenApply(response ->
      GithubHttpRequestModel.verifyAndProvideResponse(response, consumer, CodecProviderImpl.INSTANCE, LatestReleaseModel.class));
  }

  private CompletableFuture<@Nullable HttpResponse<String>> response(final HttpClient httpClient, final int timeout) {
    try {
      final var request = HttpRequest.newBuilder().GET()
        .timeout(Duration.ofSeconds(timeout))
        .uri(new URI(this.repository + "/releases/latest"))
        .build();
      return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
    } catch (final URISyntaxException exception) {
      return CompletableFuture.supplyAsync(() -> null, EXECUTOR);
    }
  }
}
