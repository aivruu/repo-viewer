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

import io.github.aivruu.repoviewer.api.download.status.DownloadStatusProvider;
import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import io.github.aivruu.repoviewer.api.download.DownloaderUtils;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This {@link RequestableModel} implementation is used to proportionate access to the
 * latest-release information for the requested-repository.
 *
 * @param version the release version.
 * @param assets the list with the url and name of the assets for this release.
 * @since 0.0.1
 */
public record LatestReleaseModel(String version, String[] assets) implements RequestableModel {
  /** Used for download-operations for this release's assets. */
  private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2,
    r -> new Thread(r, "ReleaseAssetsDownloader-Thread"));

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
   * @return The {@link CompletableFuture} with the {@link DownloadStatusProvider}.
   * @see DownloaderUtils#fromUrlToFileWithDirectory(File, String, String)
   * @since 2.3.4
   */
  public CompletableFuture<DownloadStatusProvider> downloadFrom(final File directory, final int position) {
    return CompletableFuture.supplyAsync(() -> {
      if (position < 0 || position > this.assets.length) {
        return DownloadStatusProvider.unknownAssetToDownload();
      }
      final var parts = this.assets[position].split("->", 2);
      return DownloaderUtils.fromUrlToFileWithDirectory(directory,
        /* File-name with extension. */ parts[0], /* URL without extra-spaces. */ parts[1].trim());
    }, EXECUTOR).exceptionally(exception -> {
      exception.printStackTrace();
      return DownloadStatusProvider.assetDownloadError();
    });
  }

  /**
   * Creates a new {@link CompletableFuture} and tries to download all the available-assets
   * located at the array for this release of the specified repository.
   *
   * @param directory the directory to where assets is going to be downloaded.
   * @return The {@link CompletableFuture} with a boolean-state for this operation.
   * @see DownloaderUtils#fromUrlToFileWithDirectory(File, String, String)
   * @since 2.3.4
   */
  public CompletableFuture<Integer> downloadAll(final File directory) {
    return CompletableFuture.supplyAsync(() -> {
      var downloadedAssetsAmount = 0;
      for (final var asset : this.assets) {
        final var assetParts = asset.split("->", 2);
        final var downloadStatusProvider = DownloaderUtils.fromUrlToFileWithDirectory(directory, assetParts[0], assetParts[1].trim());
        if (!downloadStatusProvider.finished()) {
          continue;
        }
        // Current iterated asset was downloaded correctly.
        downloadedAssetsAmount++;
      }
      return downloadedAssetsAmount;
    }, EXECUTOR).exceptionally(exception -> {
      exception.printStackTrace();
      return -1;
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
