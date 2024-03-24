package me.qeklydev.downloader.http;

import me.qeklydev.downloader.release.SerializedReleaseModel;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public record HTTPModelRequest(@NotNull String repository) {
  /**
   * Maximum time for the connection and read HTTP
   * request time-outs.
   *
   * @since 0.0.1
   */
  private static final byte TIME_OUT = (byte) TimeUnit.MILLISECONDS.toSeconds(60);

  public @NotNull CompletableFuture<@NotNull SerializedReleaseModel[]> provideLatestRelease() {
    return this.executeGETRequest();
  }

  public @NotNull CompletableFuture<@NotNull String> executeGETRequest() {
    return CompletableFuture.supplyAsync(() -> {
      try {
        final var urlContentBuilder = new StringBuilder();
        final var url = new URL(this.repository);
        final var urlConnection = url.openConnection();
        final var httpConnection = (HttpURLConnection) urlConnection;
        httpConnection.setRequestMethod("GET");
        httpConnection.setReadTimeout(TIME_OUT);
        httpConnection.setConnectTimeout(TIME_OUT);
        try (final var requestReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()))) {
          String line;
          while ((line = requestReader.readLine()) != null) {
            urlContentBuilder.append(line.trim());
          }
        }
        return urlContentBuilder.toString();
      } catch (final Exception exception) {
        exception.printStackTrace();
        return "";
      }
    });
  }
}
