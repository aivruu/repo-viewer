package me.qeklydev.downloader.io;

import org.jetbrains.annotations.NotNull;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
  private static final ExecutorService IO_POOL_THREAD =
      Executors.newCachedThreadPool(r -> new Thread("release-downloader-io-thread"));

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
  public static @NotNull CompletableFuture<@NotNull Boolean> fromURL(final @NotNull String fileName, final @NotNull URL requestedUrl) {
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
