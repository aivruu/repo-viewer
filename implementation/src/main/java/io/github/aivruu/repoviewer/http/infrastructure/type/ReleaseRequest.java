//
// Copyright (C) 2024-2025 aivruu - repo-viewer
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
package io.github.aivruu.repoviewer.http.infrastructure.type;

import io.github.aivruu.repoviewer.aggregate.domain.AggregateRoot;
import io.github.aivruu.repoviewer.codec.infrastructure.JsonCodecHelper;
import io.github.aivruu.repoviewer.http.domain.AbstractRequest;
import io.github.aivruu.repoviewer.http.domain.RequestResponseStatus;
import io.github.aivruu.repoviewer.release.domain.ReleaseAggregateRoot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

/**
 * An {@link AbstractRequest} implementation for data-processing into {@link ReleaseAggregateRoot} objects.
 *
 * @since 4.0.0
 */
public final class ReleaseRequest extends AbstractRequest<ReleaseAggregateRoot> {
  /**
   * Creates a new {@link ReleaseRequest} with the given parameters.
   *
   * @param uri the url for the request.
   * @param client the {@link HttpClient} to use, {@code null} for use a default-client.
   * @param timeout the max-timeout for the request.
   */
  public ReleaseRequest(final @NotNull URI uri, final @Nullable HttpClient client, final int timeout) {
    super(uri, client, timeout);
  }

  /**
   * {@inheritDoc}
   * <br>
   * Provides additional-logic for json-deserialization into {@link ReleaseAggregateRoot} object.
   *
   * @return {@inheritDoc}
   * <li>{@link RequestResponseStatus#valid(AggregateRoot)} if the json was valid and deserialized.</li>
   * @since 4.0.0
   */
  @Override
  public @NotNull RequestResponseStatus<@Nullable ReleaseAggregateRoot> validateAndProvideResponse(
    final @Nullable HttpResponse<String> response
  ) {
    final var originalResponse = super.validateAndProvideResponse(response);
    // Check if the response returns 'pending' (200 status-code) to proceed with json-reading.
    if (!originalResponse.wasPending()) {
      return originalResponse;
    }
    final var releaseAggregateRoot = JsonCodecHelper.read(ReleaseAggregateRoot.class, response.body());
    return (releaseAggregateRoot == null)
      ? RequestResponseStatus.invalid() : RequestResponseStatus.valid((ReleaseAggregateRoot) releaseAggregateRoot);
  }
}
