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
package io.github.aivruu.repoviewer.repository.domain;

import org.jetbrains.annotations.Nullable;

/**
 * A value-object that represents the additional-properties of a GitHub repository.
 *
 * @param isForked if the repository is a fork.
 * @param parent if the repository is the original repository, if there are forks.
 * @param canBeForked if the repository can be forked.
 * @param stars the repository's amount of stars.
 * @param forks the repository's amount of forks.
 * @param isPublic if the repository is public.
 * @param isArchived if the repository is archived.
 * @param isDisabled if the repository is disabled.
 * @param language the repository's most-used programming-language.
 * @param topics the repository's topics.
 * @since 4.0.0
 */
public record RepositoryPropertiesValueObject(
  boolean isForked, @Nullable String parent, boolean canBeForked, int stars, int forks, boolean isPublic,
  boolean isArchived, boolean isDisabled, @Nullable String language, @Nullable String[] topics
) {}
