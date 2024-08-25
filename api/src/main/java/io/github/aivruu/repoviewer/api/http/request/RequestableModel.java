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
package io.github.aivruu.repoviewer.api.http.request;

import io.github.aivruu.repoviewer.api.http.GithubHttpRequestModel;

/**
 * This interface is used to mark models which can be used for {@link GithubHttpRequestModel}s.
 *
 * @since 2.3.4
 */
public interface RequestableModel {
  /**
   * Returns the url where the request is sent.
   *
   * @return The url for the request.
   * @since 2.3.4
   */
  String urlForRequest();

  /**
   * Returns the url (for browsers) where the request is sent.
   *
   * @return The url for browsers where the request is sent.
   * @since 2.3.4
   */
  String browserUrlForRequest();
}
