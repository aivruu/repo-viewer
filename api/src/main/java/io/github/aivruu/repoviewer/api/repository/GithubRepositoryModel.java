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
package io.github.aivruu.repoviewer.api.repository;

import io.github.aivruu.repoviewer.api.RequestConstants;
import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import io.github.aivruu.repoviewer.api.repository.attribute.RepositoryAttributes;

/**
 * This {@link RequestableModel} implementation is used to proportionate access to the
 * requested-repository's information.
 *
 * @param owner the owner of the repository.
 * @param name the name of the repository.
 * @param description the description of the repository.
 * @param license the license's identifier selected for this repository.
 * @param attributes a {@link RepositoryAttributes} which contains the most of the attributes
 *                   for this repository.
 * @since 0.0.1
 */
public record GithubRepositoryModel(String owner, String name, String description, String license,
                                    RepositoryAttributes attributes) implements RequestableModel {
  @Override
  public String urlForRequest() {
    return RequestConstants.GITHUB_API_URL.formatted(this.owner, this.name);
  }

  @Override
  public String browserUrlForRequest() {
    return RequestConstants.GITHUB_NORMAL_URL.formatted(this.owner, this.name);
  }
}
