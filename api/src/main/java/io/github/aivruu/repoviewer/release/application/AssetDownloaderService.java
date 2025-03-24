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
package io.github.aivruu.repoviewer.release.application;

import io.github.aivruu.repoviewer.download.application.DownloadOperationStatus;
import io.github.aivruu.repoviewer.download.application.DownloaderService;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * This class allows download any asset from a {@link io.github.aivruu.repoviewer.release.domain.ReleaseAggregateRoot}.
 *
 * @since 4.0.0
 */
public final class AssetDownloaderService {
  private final DownloaderService downloaderService;

  /**
   * Creates a new {@link AssetDownloaderService} with the given parameters.
   *
   * @param downloaderService a {@link DownloaderService} instance;
   * @since 4.0.0
   */
  public AssetDownloaderService(final @NotNull DownloaderService downloaderService) {
    this.downloaderService = downloaderService;
  }

  /**
   * Downloads the asset with the specified-index from the provided array, and saves it at the given
   * directory.
   *
   * @param assets the release's published assets.
   * @param directory the asset's destination directory.
   * @param index the asset's position.
   * @return A {@link DownloadOperationStatus} which can be:
   * @see DownloaderService#toDirectory(File, String, String)
   * @since 4.0.0
   */
  public @NotNull DownloadOperationStatus download(final @NotNull String[] assets, final @NotNull File directory, final int index) {
    if (index < 1 || index > assets.length) {
      return DownloadOperationStatus.error();
    }
    final var sections = assets[index - 1].split(":", 2);
    final long readBytesAmount = this.downloaderService.toDirectory(directory, sections[0], sections[1].trim()).join();
    return (readBytesAmount == 0) ? DownloadOperationStatus.unknown() : DownloadOperationStatus.downloaded(readBytesAmount);
  }
}
