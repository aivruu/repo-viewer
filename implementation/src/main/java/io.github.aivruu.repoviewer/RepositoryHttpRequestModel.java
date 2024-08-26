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
import io.github.aivruu.repoviewer.api.repository.GithubRepositoryModel;
import io.github.aivruu.repoviewer.codec.CodecProviderImpl;
import org.jetbrains.annotations.Nullable;

import java.net.http.HttpClient;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * This {@link GithubHttpRequestModel} implementation is used to handle http-request for the
 * specified repository.
 *
 * @param url the requested repository's url.
 * @since 0.0.1
 */
public record RepositoryHttpRequestModel(String url) implements GithubHttpRequestModel<GithubRepositoryModel> {
  @Override
  public CompletableFuture<ResponseStatusProvider<
    @Nullable GithubRepositoryModel>> requestUsing(final HttpClient httpClient, final int timeout
  ) {
    return RequestUtils.response(httpClient, timeout, this.url).thenApply(response ->
      RequestUtils.verifyAndProvideResponse(response, null, CodecProviderImpl.INSTANCE, GithubRepositoryModel.class));
  }

  @Override
  public CompletableFuture<ResponseStatusProvider<
    @Nullable GithubRepositoryModel>> requestUsingThen(final HttpClient httpClient, final int timeout, final Consumer<GithubRepositoryModel> consumer
  ) {
    return RequestUtils.response(httpClient, timeout, this.url).thenApply(response ->
      RequestUtils.verifyAndProvideResponse(response, consumer, CodecProviderImpl.INSTANCE, GithubRepositoryModel.class));
  }
}
