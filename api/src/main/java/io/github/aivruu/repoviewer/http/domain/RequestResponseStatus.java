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
package io.github.aivruu.repoviewer.http.domain;

import io.github.aivruu.repoviewer.aggregate.domain.AggregateRoot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides response-status based-on status-codes provided and returns a result for it.
 *
 * @param status the status-code.
 * @param result the response's result.
 * @param <A> An object which inherits the {@link AggregateRoot} class.
 * @since 4.0.0
 */
public record RequestResponseStatus<A extends AggregateRoot>(byte status, @Nullable A result) {
  /** The request's response was valid. */
  public static final byte REQUEST_VALID_RESPONSE = 0;
  /** Similar to {@link #REQUEST_VALID_RESPONSE} but is waiting for received-data's processing. */
  public static final byte REQUEST_PENDING_PROCESSING_RESPONSE = 1;
  /** The response indicates an 'unauthorized' (401) status-code, only used on release-requests. */
  public static final byte REQUEST_UNAUTHORIZED_RESPONSE = 2;
  /** The response indicates a 'moved-permanently' (301) status-code, only used on repository-requests. */
  public static final byte REQUEST_MOVED_PERMANENTLY_RESPONSE = 3;
  /** The response indicates a 'forbidden' (403) status-code, only used on repository-requests. */
  public static final byte REQUEST_FORBIDDEN_RESPONSE = 4;
  /** The response wasn't provided, or the status-code type is 'not-found' (404). */
  public static final byte REQUEST_INVALID_RESPONSE = 5;

  /**
   * Creates a new {@link RequestResponseStatus} with the {@link #REQUEST_VALID_RESPONSE} code and the
   * given result.
   *
   * @param <A> an object which inherits the {@link AggregateRoot} class.
   * @param result the result for this response type.
   * @return A {@link RequestResponseStatus}.
   * @since 4.0.0
   */
  public static <A extends AggregateRoot> @NotNull RequestResponseStatus<@NotNull A> valid(final @NotNull A result) {
    return new RequestResponseStatus<>(REQUEST_VALID_RESPONSE, result);
  }

  /**
   * Creates a new {@link RequestResponseStatus} with the {@link #REQUEST_PENDING_PROCESSING_RESPONSE} code.
   *
   * @param <A> an object which inherits the {@link AggregateRoot} class.
   * @return A {@link RequestResponseStatus}.
   * @since 4.0.0
   */
  public static <A extends AggregateRoot> @NotNull RequestResponseStatus<@NotNull A> pending() {
    return new RequestResponseStatus<>(REQUEST_PENDING_PROCESSING_RESPONSE, null);
  }

  /**
   * Creates a new {@link RequestResponseStatus} with the {@link #REQUEST_UNAUTHORIZED_RESPONSE} code.
   *
   * @param <A> an object which inherits the {@link AggregateRoot} class.
   * @return A {@link RequestResponseStatus}.
   * @since 4.0.0
   */
  public static <A extends AggregateRoot> @NotNull RequestResponseStatus<@Nullable A> unauthorized() {
    return new RequestResponseStatus<>(REQUEST_UNAUTHORIZED_RESPONSE, null);
  }

  /**
   * Creates a new {@link RequestResponseStatus} with the {@link #REQUEST_MOVED_PERMANENTLY_RESPONSE} code.
   *
   * @param <A> an object which inherits the {@link AggregateRoot} class.
   * @return A {@link RequestResponseStatus}.
   * @since 4.0.0
   */
  public static <A extends AggregateRoot> @NotNull RequestResponseStatus<@Nullable A> moved() {
    return new RequestResponseStatus<>(REQUEST_MOVED_PERMANENTLY_RESPONSE, null);
  }

  /**
   * Creates a new {@link RequestResponseStatus} with the {@link #REQUEST_FORBIDDEN_RESPONSE} code.
   *
   * @param <A> an object which inherits the {@link AggregateRoot} class.
   * @return A {@link RequestResponseStatus}.
   * @since 4.0.0
   */
  public static <A extends AggregateRoot> @NotNull RequestResponseStatus<@Nullable A> forbidden() {
    return new RequestResponseStatus<>(REQUEST_FORBIDDEN_RESPONSE, null);
  }

  /**
   * Creates a new {@link RequestResponseStatus} with the {@link #REQUEST_INVALID_RESPONSE} code.
   *
   * @param <A> an object which inherits the {@link AggregateRoot} class.
   * @return A {@link RequestResponseStatus}.
   * @since 4.0.0
   */
  public static <A extends AggregateRoot> @NotNull RequestResponseStatus<@Nullable A> invalid() {
    return new RequestResponseStatus<>(REQUEST_INVALID_RESPONSE, null);
  }

  /**
   * Returns whether the status-code was {@link #REQUEST_VALID_RESPONSE}.
   *
   * @return {@code true} if the code was {@link #REQUEST_VALID_RESPONSE}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasValid() {
    return this.status == REQUEST_VALID_RESPONSE;
  }

  /**
   * Returns whether the status-code was {@link #REQUEST_PENDING_PROCESSING_RESPONSE}.
   *
   * @return {@code true} if the code was {@link #REQUEST_PENDING_PROCESSING_RESPONSE}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasPending() {
    return this.status == REQUEST_PENDING_PROCESSING_RESPONSE;
  }

  /**
   * Returns whether the status-code was {@link #REQUEST_UNAUTHORIZED_RESPONSE}.
   *
   * @return {@code true} if the code was {@link #REQUEST_UNAUTHORIZED_RESPONSE}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasUnauthorized() {
    return this.status == REQUEST_UNAUTHORIZED_RESPONSE;
  }

  /**
   * Returns whether the status-code was {@link #REQUEST_MOVED_PERMANENTLY_RESPONSE}.
   *
   * @return {@code true} if the code was {@link #REQUEST_MOVED_PERMANENTLY_RESPONSE}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasMoved() {
    return this.status == REQUEST_MOVED_PERMANENTLY_RESPONSE;
  }

  /**
   * Returns whether the status-code was {@link #REQUEST_FORBIDDEN_RESPONSE}.
   *
   * @return {@code true} if the code was {@link #REQUEST_FORBIDDEN_RESPONSE}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasForbidden() {
    return this.status == REQUEST_FORBIDDEN_RESPONSE;
  }

  /**
   * Returns whether the status-code was {@link #REQUEST_INVALID_RESPONSE}.
   *
   * @return {@code true} if the code was {@link #REQUEST_INVALID_RESPONSE}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasInvalid() {
    return this.status == REQUEST_INVALID_RESPONSE;
  }
}
