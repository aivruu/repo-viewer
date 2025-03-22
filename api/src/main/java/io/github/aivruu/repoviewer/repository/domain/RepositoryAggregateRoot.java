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
package io.github.aivruu.repoviewer.repository.domain;

import io.github.aivruu.repoviewer.aggregate.domain.AggregateRoot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An {@link AggregateRoot} implementation for {@link RepositoryValueObject}s.
 *
 * @since 4.0.0
 */
public final class RepositoryAggregateRoot extends AggregateRoot {
  private final RepositoryValueObject repository;

  /**
   * Creates a new {@link RepositoryAggregateRoot} with the given parameters.
   *
   * @param id the aggregate-root's id.
   * @param repository the {@link RepositoryValueObject}.
   * @since 4.0.0
   */
  public RepositoryAggregateRoot(final @NotNull String id, final @NotNull RepositoryValueObject repository) {
    super(id);
    this.repository = repository;
  }

  /**
   * Returns the repository's owner.
   *
   * @return The name.
   * @see RepositoryValueObject#owner()
   * @since 4.0.0
   */
  public @NotNull String owner() {
    return this.repository.owner();
  }

  /**
   * Returns the repository's name.
   *
   * @return The name.
   * @see RepositoryValueObject#name()
   * @since 4.0.0
   */
  public @NotNull String description() {
    return this.repository.description();
  }

  /**
   * Returns the repository's license.
   *
   * @return The license or {@code null} if there's none.
   * @see RepositoryValueObject#license()
   * @since 4.0.0
   */
  public @Nullable String license() {
    return this.repository.license();
  }

  /**
   * Returns the repository's properties.
   *
   * @return The properties.
   * @see RepositoryValueObject#properties()
   * @since 4.0.0
   */
  public @NotNull RepositoryPropertiesValueObject properties() {
    return this.repository.properties();
  }
}
