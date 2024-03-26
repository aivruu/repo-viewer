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
package me.qeklydev.downloader;

import org.jetbrains.annotations.NotNull;

/**
 * This class is used to create formatted GitHub API
 * HTTPS urls.
 *
 * @since 0.0.1
 */
public final class GitHubURLProvider {
  /**
   * GitHub API URL used for repositories requests.
   *
   * @since 0.0.1
   */
  private static final String GITHUB_API_URL = "https://api.github.com/repos/%s/%s";

  private GitHubURLProvider() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Returns the {@link GitHubURLProvider#GITHUB_API_URL} formatted with
   * the values provided to create a usable URL for HTTPS requests.
   *
   * @param user the github user.
   * @param repository the repository of the user.
   * @return A URL formatted for HTTPS requests to GitHub API.
   * @since 0.0.1
   */
  public static @NotNull String of(final @NotNull String user, final @NotNull String repository) {
    return GITHUB_API_URL.formatted(user, repository);
  }
}
