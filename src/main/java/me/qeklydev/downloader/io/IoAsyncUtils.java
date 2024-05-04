/*
 * This file is part of release-downloader - https://github.com/aivruu/release-downloader
 * Copyright (C) 2020-2024 aivruu (https://github.com/aivruu)
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
import java.net.URI;
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
  public static @NotNull CompletableFuture<
      @NotNull Long> downloadOf(final @NotNull String fileName, final @NotNull String requestedUrl) {
    return CompletableFuture.supplyAsync(() -> {
      // Using the provided URL we create a new URI object with this
      // same url, this method will throw an exception if given url
      // syntax is not valid, and will provide a message for error
      // description.
      final var uri = URI.create(requestedUrl);
      // We convert the URI instance into a URL object, and then we open
      // the stream for channel data reading and write bytes to this channel
      // at the given position.
      try (final var readableByteChannel = Channels.newChannel(uri.toURL().openStream());
           final var fileOutputStream = new FileOutputStream(fileName)) {
        return fileOutputStream.getChannel().transferFrom(
            readableByteChannel, /* The initial position for bytes transfer. */ 0, Long.MAX_VALUE);
      } catch (final IOException exception) {
        LoggerUtils.error("URL content download has suffered an exception during the process.");
        return SINGLE_RETURN_VALUE;
      }
    }, IO_POOL_THREAD);
  }
}
