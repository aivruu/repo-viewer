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
package io.github.aivruu.repoviewer.http.infrastructure;

import io.github.aivruu.repoviewer.http.infrastructure.type.ReleaseRequest;
import io.github.aivruu.repoviewer.http.infrastructure.type.RepositoryRequest;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.http.HttpClient;

/**
 * A builder-class used for request-types creation for with GitHub API usage.
 *
 * @since 4.0.0
 */
public final class Request {
  private String url;
  private HttpClient client;
  private int timeout;

  private Request() {}

  /**
   * Creates a new {@link Request} instance.
   *
   * @return A new {@link Request} object.
   * @since 4.0.0
   */
  public static @NotNull Request create() {
    return new Request();
  }

  /**
   * Sets the URL where this request is going.
   *
   * @param url the request's url.
   * @return This {@link Request} instance.
   * @since 4.0.0
   */
  public @NotNull Request url(final @NotNull String url) {
    this.url = url;
    return this;
  }

  /**
   * Sets the {@link HttpClient} object to be used for this request.
   *
   * @param client a {@link HttpClient} instance or {@code null} to use a default client.
   * @return This {@link Request} instance.
   * @since 4.0.0
   */
  public @NotNull Request client(final @NotNull HttpClient client) {
    this.client = client;
    return this;
  }

  /**
   * Sets the max-timeout for this request to be completed.
   *
   * @param timeout the request's timeout.
   * @return This {@link Request} instance.
   * @since 4.0.0
   */
  public @NotNull Request timeout(final int timeout) {
    this.timeout = timeout;
    return this;
  }

  /**
   * Validates if an url is provided and then provides a new {@link URI} object with the provided url.
   *
   * @throws IllegalStateException if an url was not provided.
   * @since 4.0.0
   */
  private @NotNull URI validateAndProvideUrl() {
    if (this.url == null) {
      throw new IllegalStateException("The request's url have not been specified");
    }
    return URI.create(this.url);
  }

  /**
   * Creates a new {@link ReleaseRequest} object based-on the information provided, it could throw an {@link IllegalStateException}
   * if the url is not set.
   *
   * @return A {@link ReleaseRequest}.
   * @see #validateAndProvideUrl()
   * @since 4.0.0
   */
  public @NotNull ReleaseRequest release() {
    return new ReleaseRequest(this.validateAndProvideUrl(), this.client, this.timeout);
  }

  /**
   * Creates a new {@link RepositoryRequest} object based-on the information provided, it could throw an {@link IllegalStateException}
   * if the url is not set.
   *
   * @return A {@link RepositoryRequest}.
   * @see #validateAndProvideUrl()
   * @since 4.0.0
   */
  public @NotNull RepositoryRequest repository() {
    return new RepositoryRequest(this.validateAndProvideUrl(), this.client, this.timeout);
  }
}
