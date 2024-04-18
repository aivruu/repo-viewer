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
package me.qeklydev.downloader.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import me.qeklydev.downloader.logger.LoggerUtils;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to perform async I/O operations.
 *
 * @since 0.0.1
 */
public final class IoAsyncUtils {
  /**
   * Represents the value that will be returned if operation
   * has suffered a mishap.
   *
   * @since 0.1.2
   */
  private static final long SINGLE_RETURN_VALUE = 0L;
  /**
   * A cached-thread-pool used to perform I/O writing or
   * reading operations.
   *
   * @since 0.0.1
   */
  private static final ExecutorService IO_POOL_THREAD = Executors.newCachedThreadPool();

  private IoAsyncUtils() {
    throw new UnsupportedOperationException("This class is used for utility and cannot be instantiated.");
  }

  /**
   * Uses the URL object provided to download the content
   * of that URL and be written into a specified file.
   *
   * @param fileName the name of the file to be written.
   * @param requestedUrl the url for processing.
   * @return The {@link CompletableFuture} with a boolean
   *     status provided for this operation.
   *     <p></p>
   *     - {@code byte-amount} will provide the amount of bytes
   *     read for this operation.
   *     <p></p>
   *     - {@link IoAsyncUtils#SINGLE_RETURN_VALUE} if there wasn't any byte read or an exception
   *     was triggered.
   * @since 0.0.1
   * @see IoAsyncUtils#SINGLE_RETURN_VALUE
   */
  public static @NotNull CompletableFuture<@NotNull Long> downloadOf(final @NotNull String fileName,
                                                                     final @NotNull String requestedUrl) {
    return CompletableFuture.supplyAsync(() -> {
      URL url;
      /*
       * We try to create a new URL object
       * and try to catch an exception if
       * provided url isn't valid.
       */
      try {
        url = new URL(requestedUrl);
      } catch (final MalformedURLException exception) {
        LoggerUtils.error("URL provided for asset-download is not valid.");
        return SINGLE_RETURN_VALUE;
      }
      try (final var readableByteChannel = Channels.newChannel(url.openStream());
           final var fileOutputStream = new FileOutputStream(fileName)) {
        return fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
      } catch (final IOException exception) {
        exception.printStackTrace();
        return SINGLE_RETURN_VALUE;
      }
    }, IO_POOL_THREAD);
  }
}
