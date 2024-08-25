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

import io.github.aivruu.repoviewer.api.repository.attribute.RepositoryAttributes;

/**
 * This class uses the builder-pattern for create new {@link GithubRepositoryModel} instances.
 *
 * @since 2.3.4
 */
public class RepositoryModelBuilder {
  private String owner;
  private String name;
  private String description;
  private String license;
  private RepositoryAttributes attributes;

  public static RepositoryModelBuilder newBuilder() {
    return new RepositoryModelBuilder();
  }

  public RepositoryModelBuilder owner(final String repositoryCreator) {
    this.owner = repositoryCreator;
    return this;
  }

  public RepositoryModelBuilder name(final String repositoryName) {
    this.name = repositoryName;
    return this;
  }

  public RepositoryModelBuilder description(final String description) {
    this.description = description;
    return this;
  }

  public RepositoryModelBuilder license(final String license) {
    this.license = license;
    return this;
  }

  public RepositoryModelBuilder attributes(final RepositoryAttributes attributes) {
    this.attributes = attributes;
    return this;
  }

  public GithubRepositoryModel build() {
    return new GithubRepositoryModel(this.owner, this.name, this.description, this.license, this.attributes);
  }
}
