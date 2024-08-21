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
package io.github.aivruu.repoviewer.api.release;

import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import io.github.aivruu.repoviewer.api.download.DownloaderUtils;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.util.concurrent.CompletableFuture;

/**
 * This {@link RequestableModel} implementation is used to proportionate access to the
 * latest-release information for the requested-repository.
 *
 * @param version the release version.
 * @param assets the list with the url and name of the assets for this release.
 * @since 0.0.1
 */
public record LatestReleaseModel(String version, String[] assets) implements RequestableModel {
  @Override
  public String urlForRequest() {
    throw new UnsupportedOperationException("This function is expected to be implemented in a future version.");
  }

  @Override
  public String browserUrlForRequest() {
    throw new UnsupportedOperationException("This function is expected to be implemented in a future version.");
  }

  /**
   * Creates a new {@link CompletableFuture} and tries to download the asset located
   * at the specified position of the assets-array.
   *
   * @param directory the directory to where the asset is going to be downloaded.
   * @param position the asset's position at the array.
   * @return The {@link CompletableFuture} with the boolean-result for this operation.
   * @since 2.3.4
   */
  public CompletableFuture<Boolean> downloadFrom(final File directory, final int position) {
    return CompletableFuture.supplyAsync(() -> {
      if ((position < 0) || position > this.assets.length) {
        return false;
      }
      final var parts = this.assets[position].split("->", 2);
      final var readBytesAmount = DownloaderUtils.fromUrlToFile(directory, /* File-name with extension. */ parts[0],
        /* URL without extra-spaces. */ parts[1].trim());
      // If something went wrong during the process, or there's nothing to download,
      // will return false, otherwise, return true.
      return readBytesAmount > 0;
    });
  }

  /**
   * Creates a new {@link CompletableFuture} and tries to download all the available-assets
   * located at the array for this release of the specified repository.
   *
   * @param directory the directory to where assets is going to be downloaded.
   * @return The {@link CompletableFuture} with the boolean-result for this operation.
   * @see DownloaderUtils#fromUrlToFile(File, String, String)
   * @since 1.0.0
   */
  public CompletableFuture<Boolean> downloadAll(final File directory) {
    return CompletableFuture.supplyAsync(() -> {
      var downloadedAssetsAmount = 0;
      for (final var asset : this.assets) {
        final var assetParts = asset.split("->", 2);
        final var readBytesAmount = DownloaderUtils.fromUrlToFile(directory, assetParts[0], assetParts[1].trim());
        if (readBytesAmount <= 0) {
          continue;
        }
        // Another asset was downloaded correctly.
        downloadedAssetsAmount++;
      }
      return downloadedAssetsAmount > 0;
    });
  }

  /**
   * Returns a string-array with every char of the version-string.
   *
   * @return The semantic string version.
   * @since 0.0.1
   */
  @Contract(pure = true)
  public String[] semanticVersion() {
    // e.g 2.10.1 -> ["2", "10", "1"]
    return this.version.split("\\.");
  }
}
