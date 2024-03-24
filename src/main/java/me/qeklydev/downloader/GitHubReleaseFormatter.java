package me.qeklydev.downloader;

import org.jetbrains.annotations.NotNull;

/**
 * This class is used to create formatted GitHub API
 * HTTPS urls.
 *
 * @since 0.0.1
 */
public final class GitHubReleaseFormatter {
  /**
   * GitHub API URL used for repositories releases
   * requests.
   *
   * @since 0.0.1
   */
  private static final String GITHUB_API_URL = "https://api.github.com/repos/%s/%s/releases/latest";

  private GitHubReleaseFormatter() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Returns the {@link GitHubReleaseFormatter#GITHUB_API_URL} formatted with
   * the values provided to create a usable URL for HTTPS requests.
   *
   * @param user the github user.
   * @param repository the repository of the user.
   * @return A URL formatted for HTTPS requests to GitHub API.
   * @since 0.0.1
   */
  public @NotNull String of(final @NotNull String user, final @NotNull String repository) {
    return GITHUB_API_URL.formatted(user, repository);
  }
}
