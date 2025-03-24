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
package io.github.aivruu.repoviewer.download.application;

import org.jetbrains.annotations.NotNull;

/**
 * Provides response-status based-on status-codes provided and returns a result for it.
 *
 * @param status the status-code.
 * @param result the response's result.
 * @since 4.0.0
 */
public record DownloadOperationStatus(byte status, long result) {
  /** The asset was downloaded correctly. */
  public static final byte ASSET_DOWNLOADED_SUCCESSFULLY = 0;
  /** The asset wasn't downloaded because of non-existing. */
  public static final byte UNKNOWN_ASSET_TO_DOWNLOAD = 1;
  /** The asset wasn't downloaded due to an error. */
  public static final byte ASSET_DOWNLOAD_ERROR = 2;
  /** Default-size for non-existing assets. */
  public static final long UNKNOWN_ASSET_DEFAULT_SIZE = -ASSET_DOWNLOAD_ERROR;
  /** Default-size for non-downloaded assets. */
  public static final long INVALID_ASSET_DEFAULT_SIZE = -UNKNOWN_ASSET_TO_DOWNLOAD;

  /**
   * Creates a new {@link DownloadOperationStatus} with the {@link #ASSET_DOWNLOADED_SUCCESSFULLY} code and the
   * given result.
   *
   * @param result the result for this response type.
   * @return A {@link DownloadOperationStatus}.
   * @since 4.0.0
   */
  public static @NotNull DownloadOperationStatus downloaded(final long result) {
    return new DownloadOperationStatus(ASSET_DOWNLOADED_SUCCESSFULLY, result);
  }

  /**
   * Creates a new {@link DownloadOperationStatus} with the {@link #UNKNOWN_ASSET_TO_DOWNLOAD} code.
   *
   * @return A {@link DownloadOperationStatus}.
   * @since 4.0.0
   */
  public static @NotNull DownloadOperationStatus unknown() {
    return new DownloadOperationStatus(UNKNOWN_ASSET_TO_DOWNLOAD, UNKNOWN_ASSET_DEFAULT_SIZE);
  }

  /**
   * Creates a new {@link DownloadOperationStatus} with the {@link #ASSET_DOWNLOAD_ERROR} code.
   *
   * @return A {@link DownloadOperationStatus}.
   * @since 4.0.0
   */
  public static @NotNull DownloadOperationStatus error() {
    return new DownloadOperationStatus(ASSET_DOWNLOAD_ERROR, INVALID_ASSET_DEFAULT_SIZE);
  }

  /**
   * Returns whether the status-code was {@link #ASSET_DOWNLOADED_SUCCESSFULLY}.
   *
   * @return {@code true} if the code was {@link #ASSET_DOWNLOADED_SUCCESSFULLY}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasDownloaded() {
    return this.status == ASSET_DOWNLOADED_SUCCESSFULLY;
  }

  /**
   * Returns whether the status-code was {@link #UNKNOWN_ASSET_TO_DOWNLOAD}.
   *
   * @return {@code true} if the code was {@link #UNKNOWN_ASSET_TO_DOWNLOAD}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasUnknown() {
    return this.status == UNKNOWN_ASSET_TO_DOWNLOAD;
  }

  /**
   * Returns whether the status-code was {@link #ASSET_DOWNLOAD_ERROR}.
   *
   * @return {@code true} if the code was {@link #ASSET_DOWNLOAD_ERROR}, {@code false} otherwise.
   * @since 4.0.0
   */
  public boolean wasError() {
    return this.status == ASSET_DOWNLOAD_ERROR;
  }
}
