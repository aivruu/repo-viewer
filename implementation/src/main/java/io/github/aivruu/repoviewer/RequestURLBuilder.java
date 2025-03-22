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
package io.github.aivruu.repoviewer;

import org.jetbrains.annotations.NotNull;

/**
 * This class is used to create formatted GitHub's API usable URLs.
 *
 * @since 4.0.0
 */
public final class RequestURLBuilder {
  /** The url used for https-requests to GitHub's API. */
  public static final String GITHUB_API_URL = "https://api.github.com/repos/%s/%s";
  /** The url used for http-requests to the release of the requested-repository. */
  public static final String GITHUB_API_RELEASE_URL = GITHUB_API_URL + "/releases/tags/%s";

  private RequestURLBuilder() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Returns a new {@link String} using the GitHub's API url as base for given parameters
   * merging for that url.
   *
   * @param user the user to search.
   * @param repository the repository of the user.
   * @return A URL formatted for HTTPS requests to GitHub API.
   * @since 4.0.0
   */
  public static @NotNull String forRepository(final @NotNull String user, final @NotNull String repository) {
    return GITHUB_API_URL.formatted(user, repository);
  }

  /**
   * Returns a new {@link String} using the GitHub's API url for releases-getting using the given
   * parameters for merging into the url.
   *
   * @param user the user to search.
   * @param repository the repository of the user.
   * @param release the release to get from the repository.
   * @return A URL formatted for http-requests to that repository's release.
   * @since 4.0.0
   */
  public static @NotNull String forRelease(
    final @NotNull String user, final @NotNull String repository, final @NotNull String release
  ) {
    var apiUrl = GITHUB_API_RELEASE_URL.formatted(user, repository, release);
    if (release.equals("latest")) {
      apiUrl = apiUrl.replace("tags/", "");
    }
    return apiUrl;
  }
}
