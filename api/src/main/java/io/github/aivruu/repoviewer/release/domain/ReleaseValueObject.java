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

import io.github.aivruu.repoviewer.release.domain.compare.ComparisonOperator;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a published-release of a GitHub repository.
 *
 * @param author the release's author.
 * @param tag the release's tag-name.
 * @param name the release's name/title.
 * @param assets the release's included assets.
 * @since 4.0.0
 */
public record ReleaseValueObject(@NotNull String author, @NotNull String tag, @NotNull String name, @NotNull String[] assets) {
  /**
   * Compares this release's version with the given one using the provided operator-type.
   *
   * @param operator the operator-type for the comparison.
   * @param targetVersion the version to compare with.
   * @return {@code true} if condition is met, otherwise {@code false}.
   * @since 4.0.0
   */
  boolean compareVersionFromNumber(final @NotNull ComparisonOperator operator, final int targetVersion) {
    final var versionWithoutDots = this.tag.replace(".", "");
    final var versionStringToNumber = Integer.parseInt(versionWithoutDots.startsWith("v")
      ? versionWithoutDots.substring(1) : versionWithoutDots);
    return switch (operator) {
      case EQUALITY -> targetVersion == versionStringToNumber;
      case DISTINCTION -> targetVersion != versionStringToNumber;
      case LESS -> targetVersion < versionStringToNumber;
      case LESS_OR_EQUAL -> targetVersion <= versionStringToNumber;
      case GREATER -> targetVersion > versionStringToNumber;
      case GREATER_OR_EQUAL -> targetVersion >= versionStringToNumber;
    };
  }

  /**
   * Compare this release's version with the given one using the provided operator-type.
   * <br>
   * This method will convert the target-version into a number before comparison.
   *
   * @param operator the operator-type for the comparison.
   * @param targetVersion the version to compare with.
   * @return {@code true} if condition is met, otherwise {@code false}.
   * @see #compareVersionFromNumber(ComparisonOperator, int)
   * @since 4.0.0
   */
  boolean compareVersionFromString(final @NotNull ComparisonOperator operator, @NotNull String targetVersion) {
    targetVersion = targetVersion.replace(".", "");
    // We don't use a try-catch block because we know these values always will have numerics, so we can parse them
    // securely.
    return this.compareVersionFromNumber(operator, Integer.parseInt(targetVersion.startsWith("v")
      ? targetVersion.substring(1) : targetVersion));
  }
}
