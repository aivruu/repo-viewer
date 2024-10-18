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
 * specified release's information for the requested-repository.
 *
 * @param author the release's author.
 * @param tagName the release version.
 * @param name the release name.
 * @param uniqueId the release's unique id.
 * @param assets the list with the url and name of the assets for this release.
 * @since 3.3.4
 */
public record RepositoryReleaseModel(String author, String tagName, String name, int uniqueId, String[] assets)
  implements RequestableModel
{
  /** Used when the download-operations for this release's assets (not a single) failed. */
  public static final int INVALID_DOWNLOADED_ASSETS_AMOUNT = -1;
  /** Used for download-operations for this release's assets. */
  private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2,
    r -> new Thread(r, "ReleaseAssetsDownloader-Thread"));

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
      return INVALID_DOWNLOADED_ASSETS_AMOUNT;
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
    // or v2.3.4 -> ["v2", "3", "4"]
    return this.tagName.split("\\.");
  }

  /**
   * Compares the current version for this release-model, with the given version, using the specified operator-type
   * for comparing process.
   *
   * @param comparingOperator the operator-type to use for this comparing.
   * @param targetVersion the version to compare.
   * @return A boolean-state indicating if the version-comparing for the specified operator-type returned true,
   *     otherwise false.
   * @since 3.4.4
   */
  public boolean compareVersionFromNumber(final VersionComparingOperatorEnum comparingOperator, final int targetVersion) {
    // e.g. v2.10.1 -> v2101 -> 2101
    // or 1.3.4 -> 134
    final var versionWithoutDots = this.tagName.replace(".", "");
    final var versionStringToNumber = Integer.parseInt(versionWithoutDots.startsWith("v")
      ? versionWithoutDots.substring(1) : versionWithoutDots);
    return switch (comparingOperator) {
      case EQUAL -> targetVersion == versionStringToNumber;
      case LESS -> targetVersion < versionStringToNumber;
      case LESS_OR_EQUAL -> targetVersion <= versionStringToNumber;
      case GREATER -> targetVersion > versionStringToNumber;
      case GREATER_OR_EQUAL -> targetVersion >= versionStringToNumber;
      // Should not happen never, but we define it to avoid syntax errors.
      default -> false;
    };
  }

  /**
   * Realizes a comparing for this release-model's current version using the specified {@link VersionComparingOperatorEnum}
   * type, and the given version is parsed into a {@code int} for internal comparing at
   * {@link #compareVersionFromNumber(VersionComparingOperatorEnum, int)}.
   *
   * @param comparingOperators the operator-type to use for this comparing.
   * @param targetVersion the version to compare.
   * @return A boolean-state indicating if the version-comparing for the specified operator-type returned true,
   *     otherwise false.
   * @see #compareVersionFromNumber(VersionComparingOperatorEnum, int)
   * @since 3.4.4
   */
  public boolean compareVersionFromString(final VersionComparingOperatorEnum comparingOperators, String targetVersion) {
    // e.g. v2.10.1 -> v2101 -> 2101
    // or 1.3.4 -> 134
    targetVersion = targetVersion.replace(".", "");
    final var targetVersionToNumber = Integer.parseInt(targetVersion.startsWith("v")
      ? targetVersion.substring(1) : targetVersion);
    return this.compareVersionFromNumber(comparingOperators, targetVersionToNumber);
  }
}
