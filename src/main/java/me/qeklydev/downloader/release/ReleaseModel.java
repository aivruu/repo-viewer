/*
 * This file is part of release-downloader - https://github.com/aivruu/release-downloader
 * Copyright (C) 2020-2024 Aivruu (https://github.com/aivruu)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.qeklydev.downloader.release;

import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * This record is used to store information about
 * the requested repository release.
 *
 * @param version the release version.
 * @param assets the list with the url and name
 *               of the assets for this release.
 * @since 0.0.1
 */
public record ReleaseModel(@NotNull String version, @NotNull List<@NotNull String> assets) {
  /**
   * Returns a string array with every value
   * of the version.
   *
   * @return The semantic string version.
   * @since 0.0.1
   */
  public @NotNull String[] semanticVersion() {
    // e.g 2.10.1 -> ["2", "10", "1"]
    return this.version.split("\\.");
  }

  /**
   * Returns an int array with every value
   * of the version.
   *
   * @return The semantic integer version.
   * @since 0.0.1
   */
  public int[] semanticIntegerVersion() {
    var integersArray = new int[0];
    var nonZeroIntegersArray = new int[0];
    for (int i = 0; i < this.version.length(); i++) {
      final var symbol = this.version.charAt(i);
      if (!Character.isDigit(symbol)) {
        continue;
      }
      integersArray = Arrays.copyOf(integersArray, integersArray.length + 1);
      final var numericValue = Character.getNumericValue(symbol);
      if (numericValue != 0) {
        nonZeroIntegersArray = integersArray;
      }
      integersArray[integersArray.length - 1] = numericValue;
    }
    return (integersArray.length > 1) && (integersArray[integersArray.length - 1]) == 0
        ? nonZeroIntegersArray : integersArray;
  }

  /**
   * Compares the given values to determinate if the semantic
   * version is equal.
   *
   * @param parts the version parts.
   * @return The boolean status for this operation,
   *     {@code true} if any value is similar, otherwise return
   *     {@code false}.
   * @since 0.0.1
   */
  public boolean semanticEqualsTo(final String @NotNull... parts) {
    final var version = this.semanticVersion();
    for (int i = 0; i < parts.length; i++) {
      if (parts[i].equals(version[i])) {
        /*
         * This value is similar on the current iterated
         * semantic version element.
         */
        continue;
      }
      // Any value is similar so return false.
      return false;
    }
    return true;
  }
}
