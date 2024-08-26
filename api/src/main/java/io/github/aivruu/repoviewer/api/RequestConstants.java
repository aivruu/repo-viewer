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
package io.github.aivruu.repoviewer.api;

/**
 * This class is used to store constants used for https-requests to GitHub's API.
 *
 * @since 2.3.4
 */
public class RequestConstants {
  /** The url used for https-requests to GitHub's API. */
  public static final String GITHUB_API_URL = "https://api.github.com/repos/%s/%s";
  /** The url used for http-requests to the release of the requested-repository. */
  public static final String GITHUB_API_RELEASE_URL = GITHUB_API_URL + "/releases/tags/%s";

  private RequestConstants() {
    throw new UnsupportedOperationException("This class is for utility and cannot be instantiated.");
  }
}
