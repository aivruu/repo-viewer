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
package io.github.aivruu.repoviewer.api.download.status;

/**
 * This record is used as provider for status-code w/ results creation used for
 * assets-download operations at {@link io.github.aivruu.repoviewer.api.download.DownloaderUtils}.
 *
 * @param status the status-code for the instance.
 * @param result the read-bytes number to return as result for this instance.
 * @since 2.3.4
 */
public record DownloadStatusProvider(byte status, long result) {
  /** The asset was downloaded correctly. */
  public static final byte ASSET_DOWNLOAD_FINISHED = 0;
  /** The asset wasn't downloaded because of non-existing. */
  public static final byte UNKNOWN_ASSET_TO_DOWNLOAD = 1;
  /** The asset wasn't downloaded due to an error. */
  public static final byte ASSET_DOWNLOAD_ERROR = 2;
  /** Cached default-size to return for non-existing assets. */
  public static final long UNEXISTING_ASSET_DEFAULT_SIZE = 0;
  /** Cached default-size to return for non-downloaded assets. */
  public static final long INVALID_ASSET_DEFAULT_SIZE = -1;

  /**
   * Creates a new {@link DownloadStatusProvider} with the {@link #ASSET_DOWNLOAD_FINISHED}
   * status-code.
   *
   * @param result the read-bytes amount for the downloaded asset.
   * @return The {@link DownloadStatusProvider} with the {@link #ASSET_DOWNLOAD_FINISHED} status-code.
   * @since 2.3.4
   */
  public static DownloadStatusProvider assetDownloadFinished(final long result) {
    return new DownloadStatusProvider(ASSET_DOWNLOAD_FINISHED, result);
  }

  /**
   * Creates a new {@link DownloadStatusProvider} with the {@link #UNKNOWN_ASSET_TO_DOWNLOAD}
   * status-code, and using the default {@link #UNEXISTING_ASSET_DEFAULT_SIZE}.
   *
   * @return The {@link DownloadStatusProvider} with the {@link #UNEXISTING_ASSET_DEFAULT_SIZE} status-code.
   * @since 2.3.4
   */
  public static DownloadStatusProvider unknownAssetToDownload() {
    return new DownloadStatusProvider(UNKNOWN_ASSET_TO_DOWNLOAD, UNEXISTING_ASSET_DEFAULT_SIZE);
  }

  /**
   * Creates a new {@link DownloadStatusProvider} with the {@link #ASSET_DOWNLOAD_ERROR}
   * status-code, and using the default {@link #INVALID_ASSET_DEFAULT_SIZE}.
   *
   * @return The {@link DownloadStatusProvider} with the {@link #ASSET_DOWNLOAD_ERROR} status-code.
   * @since 2.3.4
   */
  public static DownloadStatusProvider assetDownloadError() {
    return new DownloadStatusProvider(ASSET_DOWNLOAD_ERROR, INVALID_ASSET_DEFAULT_SIZE);
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #ASSET_DOWNLOAD_FINISHED}.
   *
   * @return Whether this provider's current status-code is {@link #ASSET_DOWNLOAD_FINISHED}.
   * @since 2.3.4
   */
  public boolean finished() {
    return this.status == ASSET_DOWNLOAD_FINISHED;
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #UNKNOWN_ASSET_TO_DOWNLOAD}.
   *
   * @return Whether this provider's current status-code is {@link #UNKNOWN_ASSET_TO_DOWNLOAD}.
   * @since 2.3.4
   */
  public boolean unknown() {
    return this.status == UNKNOWN_ASSET_TO_DOWNLOAD;
  }

  /**
   * Returns a boolean-state indicating if this provider's current status-code is {@link #ASSET_DOWNLOAD_ERROR}.
   *
   * @return Whether this provider's current status-code is {@link #ASSET_DOWNLOAD_ERROR}.
   * @since 2.3.4
   */
  public boolean error() {
    return this.status == ASSET_DOWNLOAD_ERROR;
  }
}
