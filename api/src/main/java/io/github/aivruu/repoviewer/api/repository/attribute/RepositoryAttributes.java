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

import org.jetbrains.annotations.Nullable;

public record RepositoryAttributes(
  boolean isForked, @Nullable String parent, boolean canBeForked, int stars, int forks, boolean isPublic,
  boolean isArchived, boolean isDisabled, @Nullable String language, @Nullable String[] topics) {}
