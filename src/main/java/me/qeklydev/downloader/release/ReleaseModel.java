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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import me.qeklydev.downloader.io.IoAsyncUtils;
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
   * Starts the requested asset download using the URL requested
   * since the assets list based-on the given position value. An
   * {@code IllegalArgumentException} could be triggered if requested
   * position is a negative value, or is higher than the assets list size.
   *
   * @param position the position of the file URL to download
   *                 of the assets list.
   * @return A boolean value depending on operation result,
   *     {@code true} if operation was successful. Otherwise
   *     {@code false} if there was not bytes read, or the url
   *     isn't valid.
   * @since 0.0.1
   */
  public boolean downloadAsset(final int position) {
    if ((position < 0) || (position > this.assets.size())) {
      throw new IllegalArgumentException("Requested URL position cannot be negative, or be greater than the release assets amount!");
    }
    final var providerParts = this.assets.get(position).split(":", 2);
    final URL url;
    try {
      url = new URL(providerParts[1].trim());
    } catch (final MalformedURLException exception) {
      exception.printStackTrace();
      return false;
    }
    final var bytesAmountReadDuringOperation = IoAsyncUtils.downloadOf(providerParts[0].substring(0, providerParts[0].length() - 1), url).join();
    /*
     * Wait until operation is completed and request the final status.
     * then check if the operation was successful or not.
     */
    return bytesAmountReadDuringOperation > 0;
  }

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
