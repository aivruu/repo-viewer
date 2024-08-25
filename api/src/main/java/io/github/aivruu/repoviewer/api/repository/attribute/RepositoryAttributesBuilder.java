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
package io.github.aivruu.repoviewer.api.repository.attribute;

/**
 * This class uses the builder-pattern for create new {@link RepositoryAttributes} instances.
 *
 * @since 2.3.4
 */
public class RepositoryAttributesBuilder {
  private boolean isForked;
  private String parent;
  private boolean canBeForked;
  private int stars;
  private int forks;
  private boolean isPublic;
  private boolean isArchived;
  private boolean isDisabled;
  private String language;
  private String[] topics;

  public static RepositoryAttributesBuilder newBuilder() {
    return new RepositoryAttributesBuilder();
  }

  public RepositoryAttributesBuilder fork(final boolean repositoryIsFork) {
    this.isForked = repositoryIsFork;
    return this;
  }

  public RepositoryAttributesBuilder parent(final String originalRepository) {
    this.parent = originalRepository;
    return this;
  }

  public RepositoryAttributesBuilder canBeForked(final boolean canBeForked) {
    this.canBeForked = canBeForked;
    return this;
  }

  public RepositoryAttributesBuilder stars(final int stars) {
    this.stars = stars;
    return this;
  }

  public RepositoryAttributesBuilder forksAmount(final int forks) {
    this.forks = forks;
    return this;
  }

  public RepositoryAttributesBuilder visible(final boolean isPublic) {
    this.isPublic = isPublic;
    return this;
  }

  public RepositoryAttributesBuilder archived(final boolean isArchived) {
    this.isArchived = isArchived;
    return this;
  }

  public RepositoryAttributesBuilder disabled(final boolean isDisabled) {
    this.isDisabled = isDisabled;
    return this;
  }

  public RepositoryAttributesBuilder language(final String language) {
    this.language = language;
    return this;
  }

  public RepositoryAttributesBuilder topics(final String[] topics) {
    this.topics = topics;
    return this;
  }

  public RepositoryAttributes build() {
    return new RepositoryAttributes(this.isForked, this.parent, this.canBeForked, this.stars, this.forks, this.isPublic,
      this.isArchived, this.isDisabled, this.language, this.topics);
  }
}
