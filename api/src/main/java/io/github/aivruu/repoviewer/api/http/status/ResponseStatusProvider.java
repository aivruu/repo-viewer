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
package io.github.aivruu.repoviewer.api.http.status;

import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import org.jetbrains.annotations.Nullable;

/**
 * This record is used as provider for status-code w/ results creation used for http-requests'
 * responses validation operations at {@link io.github.aivruu.repoviewer.api.http.GithubHttpRequestModel}.
 *
 * @param status the status-code for the instance.
 * @param result the model to return as result for this instance, it could be {@code null}.
 * @param <Model> an object which implements the {@link RequestableModel} interface.
 * @since 2.3.4
 */
public record ResponseStatusProvider<Model>(byte status, @Nullable Model result) {
  /** The request's response was valid. */
  public static final byte REQUEST_VALID_RESPONSE = 0;
  /** The response indicates a 'unauthorized' (401) status-code, only used on release-requests. */
  public static final byte REQUEST_UNAUTHORIZED_RESPONSE = 1;
  /** The response indicates a 'moved-permanently' (301) status-code, only used on repository-requests. */
  public static final byte REQUEST_MOVED_PERMANENTLY_RESPONSE = 2;
  /** The response indicates a 'forbidden' (403) status-code, only used on repository-requests. */
  public static final byte REQUEST_FORBIDDEN_RESPONSE = 3;
  /** The response wasn't provided, or the status-code type is 'not-found' (404). */
  public static final byte REQUEST_INVALID_RESPONSE = 4;

  /**
   * Creates a new instance of {@link ResponseStatusProvider} with the {@link #REQUEST_VALID_RESPONSE}
   * status-code.
   *
   * @param result the model to return as result for the new provider instance.
   * @param <Model> an object which implements the {@link RequestableModel} interface.
   * @return The {@link ResponseStatusProvider} with the {@link #REQUEST_VALID_RESPONSE} status-code.
   * @since 2.3.4
   */
  public static <Model extends RequestableModel> ResponseStatusProvider<Model> requestValidResponse(final Model result) {
    return new ResponseStatusProvider<>(REQUEST_VALID_RESPONSE, result);
  }

  /**
   * Creates a new instance of {@link ResponseStatusProvider} with the {@link #REQUEST_UNAUTHORIZED_RESPONSE}
   * status-code, and a {@code null} model-type.
   *
   * @param <Model> an object which implements the {@link RequestableModel} interface.
   * @return The {@link ResponseStatusProvider} with the {@link #REQUEST_UNAUTHORIZED_RESPONSE} status-code.
   * @since 3.3.4
   */
  public static <Model extends RequestableModel> ResponseStatusProvider<@Nullable Model> requestUnauthorizedResponse() {
    return new ResponseStatusProvider<>(REQUEST_UNAUTHORIZED_RESPONSE, null);
  }

  /**
   * Creates a new instance of {@link ResponseStatusProvider} with the {@link #REQUEST_MOVED_PERMANENTLY_RESPONSE}
   * status-code, and a {@code null} model-type.
   *
   * @param <Model> an object which implements the {@link RequestableModel} interface.
   * @return The {@link ResponseStatusProvider} with the {@link #REQUEST_MOVED_PERMANENTLY_RESPONSE} status-code.
   * @since 3.3.4
   */
  public static <Model extends RequestableModel> ResponseStatusProvider<@Nullable Model> requestMovedPermanentlyResponse() {
    return new ResponseStatusProvider<>(REQUEST_MOVED_PERMANENTLY_RESPONSE, null);
  }

  /**
   * Creates a new instance of {@link ResponseStatusProvider} with the {@link #REQUEST_FORBIDDEN_RESPONSE}
   * status-code, and a {@code null} model-type.
   *
   * @param <Model> an object which implements the {@link RequestableModel} interface.
   * @return The {@link ResponseStatusProvider} with the {@link #REQUEST_FORBIDDEN_RESPONSE} status-code.
   * @since 3.3.4
   */
  public static <Model extends RequestableModel> ResponseStatusProvider<@Nullable Model> requestForbiddenResponse() {
    return new ResponseStatusProvider<>(REQUEST_FORBIDDEN_RESPONSE, null);
  }

  /**
   * Creates a new instance of {@link ResponseStatusProvider} with the {@link #REQUEST_INVALID_RESPONSE}
   * status-code, and a {@code null} model-type.
   *
   * @param <Model> an object which implements the {@link RequestableModel} interface.
   * @return The {@link ResponseStatusProvider} with the {@link #REQUEST_INVALID_RESPONSE} status-code.
   * @since 2.3.4
   */
  public static <Model extends RequestableModel> ResponseStatusProvider<@Nullable Model> requestInvalidResponse() {
    return new ResponseStatusProvider<>(REQUEST_INVALID_RESPONSE, null);
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #REQUEST_VALID_RESPONSE}.
   *
   * @return Whether this provider's current status-code is {@link #REQUEST_VALID_RESPONSE}.
   * @since 2.3.4
   */
  public boolean valid() {
    return this.status == REQUEST_VALID_RESPONSE;
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #REQUEST_UNAUTHORIZED_RESPONSE}.
   *
   * @return Whether this provider's current status-code is {@link #REQUEST_UNAUTHORIZED_RESPONSE}.
   * @since 3.3.4
   */
  public boolean unauthorized() {
    return this.status == REQUEST_UNAUTHORIZED_RESPONSE;
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #REQUEST_MOVED_PERMANENTLY_RESPONSE}.
   *
   * @return Whether this provider's current status-code is {@link #REQUEST_MOVED_PERMANENTLY_RESPONSE}.
   * @since 3.3.4
   */
  public boolean moved() {
    return this.status == REQUEST_MOVED_PERMANENTLY_RESPONSE;
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #REQUEST_FORBIDDEN_RESPONSE}.
   *
   * @return Whether this provider's current status-code is {@link #REQUEST_FORBIDDEN_RESPONSE}.
   * @since 3.3.4
   */
  public boolean forbidden() {
    return this.status == REQUEST_FORBIDDEN_RESPONSE;
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #REQUEST_INVALID_RESPONSE}.
   *
   * @return Whether this provider's current status-code is {@link #REQUEST_INVALID_RESPONSE}.
   * @since 2.3.4
   */
  public boolean invalid() {
    return this.status == REQUEST_INVALID_RESPONSE;
  }
}
