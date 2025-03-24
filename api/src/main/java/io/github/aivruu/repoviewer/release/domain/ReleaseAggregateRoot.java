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
package io.github.aivruu.repoviewer.release.domain;

import io.github.aivruu.repoviewer.aggregate.domain.AggregateRoot;
import io.github.aivruu.repoviewer.release.domain.compare.ComparisonOperator;
import org.jetbrains.annotations.NotNull;

/**
 * An {@link AggregateRoot} implementation for {@link ReleaseValueObject}s.
 *
 * @since 4.0.0
 */
public final class ReleaseAggregateRoot extends AggregateRoot {
  private final ReleaseValueObject releaseValueObject;

  /**
   * Creates a new {@link ReleaseAggregateRoot} with the given parameters.
   *
   * @param id the aggregate-root's id.
   * @param releaseValueObject the {@link ReleaseValueObject}.
   * @since 4.0.0
   */
  public ReleaseAggregateRoot(final @NotNull String id, final @NotNull ReleaseValueObject releaseValueObject) {
    super(id);
    this.releaseValueObject = releaseValueObject;
  }

  /**
   * Returns the release's author's.
   *
   * @return The author.
   * @see ReleaseValueObject#author()
   * @since 4.0.0
   */
  public @NotNull String author() {
    return this.releaseValueObject.author();
  }

  /**
   * Returns the release's name.
   *
   * @return The name.
   * @see ReleaseValueObject#name()
   * @since 4.0.0
   */
  public @NotNull String name() {
    return this.releaseValueObject.name();
  }

  /**
   * Returns the release's tag.
   *
   * @return The tag.
   * @see ReleaseValueObject#tag()
   * @since 4.0.0
   */
  public @NotNull String tag() {
    return this.releaseValueObject.tag();
  }

  /**
   * Returns the release's assets-array.
   *
   * @return The assets.
   * @see ReleaseValueObject#assets()
   * @since 4.0.0
   */
  public @NotNull String[] assets() {
    return this.releaseValueObject.assets();
  }

  /**
   * Performs a comparison between this release's version and the given one.
   *
   * @param operator the operator-type for the comparison.
   * @param version the version to compare with.
   * @return {@code true} if the condition is met, {@code false otherwise}.
   * @see ReleaseValueObject#compareVersionFromNumber(ComparisonOperator, int)
   * @since 4.0.0
   */
  public boolean compareVersionNumber(final @NotNull ComparisonOperator operator, final int version) {
    return this.releaseValueObject.compareVersionFromNumber(operator, version);
  }

  /**
   * Performs a comparison between this release's version and the given one.
   *
   * @param operator the operator-type for the comparison.
   * @param version the version to compare with.
   * @return {@code true} if the condition is met, {@code false otherwise}.
   * @see ReleaseValueObject#compareVersionFromString(ComparisonOperator, String)
   * @since 4.0.0
   */
  public boolean compareVersionString(final @NotNull ComparisonOperator operator, final @NotNull String version) {
    return this.releaseValueObject.compareVersionFromString(operator, version);
  }
}
