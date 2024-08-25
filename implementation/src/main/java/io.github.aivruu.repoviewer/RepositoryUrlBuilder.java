//
// Copyright (C) 2024 Aivruu - repo-viewer
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

import io.github.aivruu.repoviewer.api.RequestConstants;

/**
 * This class is used to create formatted GitHub's API usable URLs.
 *
 * @since 0.0.1
 */
public final class RepositoryUrlBuilder {
  private RepositoryUrlBuilder() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Returns a new {@link String} using the GitHub's API url as base for given parameters
   * merging for that url.
   *
   * @param user the user to search.
   * @param repository the repository of the user.
   * @return A URL formatted for HTTPS requests to GitHub API.
   * @since 2.3.4
   */
  public static String from(final String user, final String repository) {
    return RequestConstants.GITHUB_API_URL.formatted(user, repository);
  }
}
