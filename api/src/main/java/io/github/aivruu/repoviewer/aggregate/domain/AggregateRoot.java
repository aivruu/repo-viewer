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
package io.github.aivruu.repoviewer.aggregate.domain;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an aggregate-root model.
 *
 * @since 4.0.0
 */
public abstract class AggregateRoot {
  private final String id;

  protected AggregateRoot(final @NotNull String id) {
    this.id = id;
  }

  /**
   * Returns the aggregate-root's id.
   *
   * @return The identifier.
   * @since 4.0.0
   */
  public final @NotNull String id() {
    return this.id;
  }
}
