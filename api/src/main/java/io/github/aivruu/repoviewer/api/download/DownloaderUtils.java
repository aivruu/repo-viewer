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
   * @return The amount of read bytes for this operation, following expected logic for the
   *     {@link #fromUrlToFile(File, String)} function.
   * @see #fromUrlToFile(File, String)
   * @since 2.3.4
   */
  public static long fromUrlToFile(final File directory, final String fileName, final String from) {
    final var expectedFile = new File(directory, fileName);
    return fromUrlToFile(expectedFile, from);
  }

  /**
   * Downloads the content of the given url to write it into the given {@link File}.
   *
   * @param file the file where download's content will be written.
   * @param from the url from where the content is going to be downloaded.
   * @return The amount of read bytes for this operation, could return {@code 0} if nothing
   *     was downloaded, or {@code -1} if something went wrong during the process.
   * @since 2.3.4
   */
  public static long fromUrlToFile(final File file, final String from) {
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
      return fileOutputStream.getChannel().transferFrom(readableByteChannel,
        /* The initial position for bytes transfer. */ 0, Long.MAX_VALUE);
    } catch (final IOException exception) {
      return -1;
    }
  }
}
