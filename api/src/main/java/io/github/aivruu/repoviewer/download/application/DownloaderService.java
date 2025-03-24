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

import io.github.aivruu.repoviewer.executor.application.ExecutorHelper;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.channels.Channels;
import java.util.concurrent.CompletableFuture;

/**
 * This service-class provides functionality to download files since URLs.
 *
 * @since 4.0.0
 */
public final class DownloaderService {
  /** Default file-size for a non-completed download. */
  public static final long FILE_DOWNLOAD_ERROR_DEFAULT_SIZE = -1;
  private static DownloaderService instance;

  private DownloaderService() {}

  /**
   * Returns the {@link DownloaderService}'s instance, it will be created if necessary.
   *
   * @return The {@link DownloaderService} instance.
   * @since 4.0.0
   */
  public static @NotNull DownloaderService get() {
    if (instance == null) {
       instance = new DownloaderService();
    }
    return instance;
  }

  /**
   * Downloads the requested file from the given URL, gave it the provided name and saves it to the given
   * directory.
   *
   * @param directory the file's destination directory.
   * @param fileName the file's name.
   * @param url the url for download.
   * @return A {@link CompletableFuture} with the file's read bytes-amount.
   * @see #toDirectory(File, String)
   * @since 4.0.0
   */
  public @NotNull CompletableFuture<@NotNull Long> toDirectory(
    final @NotNull File directory, final @NotNull String fileName, final @NotNull String url
  ) {
    return this.toDirectory(new File(directory, fileName), url);
  }

  /**
   * Downloads the requested file from the given URL and saves it to the given directory.
   *
   * @param file the file's destination.
   * @param url the url for download.
   * @return A {@link CompletableFuture} with the file's read bytes-amount.
   * @since 4.0.0
   */
  public @NotNull CompletableFuture<@NotNull Long> toDirectory(final @NotNull File file, final @NotNull String url) {
    return CompletableFuture.supplyAsync(() -> {
      try (final var readableByteChannel = Channels.newChannel(URI.create(url).toURL().openStream());
           final var fileOutputStream = new FileOutputStream(file)
      ) {
        return fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
      } catch (final IOException exception) {
        return FILE_DOWNLOAD_ERROR_DEFAULT_SIZE;
      }
    }, ExecutorHelper.get());
  }
}
