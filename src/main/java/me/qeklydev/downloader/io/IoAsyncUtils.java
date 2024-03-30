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
import java.net.URL;
import java.nio.channels.Channels;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to perform async I/O operations.
 *
 * @since 0.0.1
 */
public final class IoAsyncUtils {
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
   *
   *     - {@code byte-amount} will provide the amount of bytes
   *     read for this operation.
   *     <p></p>
   *
   *     - {@code 0} if there wasn't any byte read or an exception
   *     was triggered.
   * @since 0.0.1
   */
  public static @NotNull CompletableFuture<@NotNull Long> downloadOf(final @NotNull String fileName, final @NotNull URL requestedUrl) {
    return CompletableFuture.supplyAsync(() -> {
      try (final var readableByteChannel = Channels.newChannel(requestedUrl.openStream());
           final var fileOutputStream = new FileOutputStream(fileName)) {
        return fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
      } catch (final IOException exception) {
        exception.printStackTrace();
        return 0L;
      }
    }, IO_POOL_THREAD);
  }
}
