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
package io.github.aivruu.repoviewer.api.download;

import io.github.aivruu.repoviewer.api.download.status.DownloadStatusProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.Channels;

/**
 * This class is used to perform download-operations from given URLs.
 *
 * @since 2.3.4
 */
public final class DownloaderUtils {
  private DownloaderUtils() {
    throw new UnsupportedOperationException("This class is used for utility and cannot be instantiated.");
  }

  /**
   * Uses the URL object provided to download the content of that URL and be written into
   * a specified file.
   *
   * @param fileName the name of the file to be written.
   * @param from the url for the download.
   * @return A {@link DownloadStatusProvider} with the {@code status-code} and the read {@code bytes-amount}
   *     for the operation.
   * @see #fromUrlToFile(File, String)
   * @since 2.3.4
   */
  public static DownloadStatusProvider fromUrlToFileWithDirectory(final File directory, final String fileName,
                                                                  final String from) {
    return fromUrlToFile(new File(directory, fileName), from);
  }

  /**
   * Downloads the content of the given url to write it into the given {@link File}.
   *
   * @param file the file where download's content will be written.
   * @param from the url from where the content is going to be downloaded.
   * @return A {@link DownloadStatusProvider} with the {@code status-code} and the read {@code bytes-amount}
   *     for the operation ending.
   *     <p>
   *     - {@link DownloadStatusProvider#assetDownloadFinished(long)} if the download was successful, will
   *     return it with the read-bytes amount.
   *     <p>
   *     - {@link DownloadStatusProvider#unknownAssetToDownload()} if any asset was downloaded, nothing to
   *     download.
   *     <p>
   *     - {@link DownloadStatusProvider#assetDownloadError()} if an error occurred during the downloading
   * @since 3.3.4
   */
  public static DownloadStatusProvider fromUrlToFile(final File file, final String from) {
    // Using the provided URL we create a new URI object with this
    // same url, this method will throw an exception if given url
    // syntax is not valid, and will provide a message for error
    // description.
    final var uriFromGiven = URI.create(from);
    // We convert the URI instance into a URL object, and then we open
    // the stream for channel data reading and write bytes to this channel
    // at the given position.
    try (final var readableByteChannel = Channels.newChannel(uriFromGiven.toURL().openStream());
         final var fileOutputStream = new FileOutputStream(file)) {
      final var readBytesNumber = fileOutputStream.getChannel().transferFrom(readableByteChannel,
        /* The initial position for bytes transfer. */ 0, Long.MAX_VALUE);
      return (readBytesNumber == 0)
        ? DownloadStatusProvider.unknownAssetToDownload()
        : DownloadStatusProvider.assetDownloadFinished(readBytesNumber);
    } catch (final IOException exception) {
      return DownloadStatusProvider.assetDownloadError();
    }
  }
}
