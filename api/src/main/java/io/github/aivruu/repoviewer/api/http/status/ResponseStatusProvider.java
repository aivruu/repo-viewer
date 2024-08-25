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
  /** The response indicates a 'bad' status-code. */
  public static final byte REQUEST_BAD_RESPONSE = 1;
  /** The response indicates a 'unknown' status-code. */
  public static final byte REQUEST_UNKNOWN_RESPONSE = 2;
  /** The response wasn't provided. */
  public static final byte REQUEST_INVALID_RESPONSE = 3;

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
   * Creates a new instance of {@link ResponseStatusProvider} with the {@link #REQUEST_BAD_RESPONSE}
   * status-code, and a {@code null} model-type.
   *
   * @param <Model> an object which implements the {@link RequestableModel} interface.
   * @return The {@link ResponseStatusProvider} with the {@link #REQUEST_BAD_RESPONSE} status-code.
   * @since 2.3.4
   */
  public static <Model extends RequestableModel> ResponseStatusProvider<@Nullable Model> requestBadResponse() {
    return new ResponseStatusProvider<>(REQUEST_BAD_RESPONSE, null);
  }

  /**
   * Creates a new instance of {@link ResponseStatusProvider} with the {@link #REQUEST_UNKNOWN_RESPONSE}
   * status-code, and a {@code null} model-type.
   *
   * @param <Model> an object which implements the {@link RequestableModel} interface.
   * @return The {@link ResponseStatusProvider} with the {@link #REQUEST_UNKNOWN_RESPONSE} status-code.
   * @since 2.3.4
   */
  public static <Model extends RequestableModel> ResponseStatusProvider<@Nullable Model> requestUnknownResponse() {
    return new ResponseStatusProvider<>(REQUEST_UNKNOWN_RESPONSE, null);
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
   * Returns a boolean-state indicating if this provider's current status-code is {@link #REQUEST_BAD_RESPONSE}.
   *
   * @return Whether this provider's current status-code is {@link #REQUEST_BAD_RESPONSE}.
   * @since 2.3.4
   */
  public boolean bad() {
    return this.status == REQUEST_BAD_RESPONSE;
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #REQUEST_UNKNOWN_RESPONSE}.
   *
   * @return Whether this provider's current status-code is {@link #REQUEST_UNKNOWN_RESPONSE}.
   * @since 2.3.4
   */
  public boolean unknown() {
    return this.status == REQUEST_UNKNOWN_RESPONSE;
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
