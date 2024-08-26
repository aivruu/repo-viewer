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
import io.github.aivruu.repoviewer.api.http.RequestUtils;
import io.github.aivruu.repoviewer.api.http.status.ResponseStatusProvider;
import io.github.aivruu.repoviewer.api.release.RepositoryReleaseModel;
import io.github.aivruu.repoviewer.codec.CodecProviderImpl;
import org.jetbrains.annotations.Nullable;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * This {@link GithubHttpRequestModel} implementation is used to handle http-requests of the
 * specific release for the requested repository.
 *
 * @param url the specified repository's url.
 * @since 3.3.4
 */
public record ReleaseHttpRequestModel(String url) implements GithubHttpRequestModel<RepositoryReleaseModel> {
  @Override
  public CompletableFuture<ResponseStatusProvider<
    @Nullable RepositoryReleaseModel>> requestUsing(final HttpClient httpClient, final int timeout
  ) {
    return RequestUtils.response(httpClient, timeout, this.url).thenApply(response ->
      RequestUtils.verifyAndProvideResponse(response, null, CodecProviderImpl.INSTANCE, RepositoryReleaseModel.class));
  }

  @Override
  public CompletableFuture<ResponseStatusProvider<
    @Nullable RepositoryReleaseModel>> requestUsingThen(final HttpClient httpClient, final int timeout, final Consumer<RepositoryReleaseModel> consumer
  ) {
    return RequestUtils.response(httpClient, timeout, this.url).thenApply(response ->
      RequestUtils.verifyAndProvideResponse(response, consumer, CodecProviderImpl.INSTANCE, RepositoryReleaseModel.class));
  }
}
