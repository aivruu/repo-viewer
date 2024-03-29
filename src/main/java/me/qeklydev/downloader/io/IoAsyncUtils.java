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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
   * @param fileName the file for the bytes writing.
   * @param requestedUrl the url for processing.
   * @return The {@link CompletableFuture} with a boolean
   *     status provided for this operation.
   * @since 0.0.1
   */
  public static @NotNull CompletableFuture<@NotNull Boolean> downloadOf(final @NotNull String fileName, final @NotNull URL requestedUrl) {
    return CompletableFuture.supplyAsync(() -> {
      try (final var inputStream = new BufferedInputStream(requestedUrl.openStream());
           final var outputStream = new FileOutputStream(fileName)) {
        final var bytesDataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(bytesDataBuffer, 0, 1024)) != -1) {
          outputStream.write(bytesDataBuffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
        return true;
      } catch (final IOException exception) {
        exception.printStackTrace();
        return false;
      }
    }, IO_POOL_THREAD);
  }
}
