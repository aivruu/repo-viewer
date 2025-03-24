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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a published-repository in GitHub.
 *
 * @param owner the repository's owner.
 * @param name the repository's name.
 * @param description the repository's description.
 * @param license the repository's license, {@code null} if it don't have one.
 * @param properties the repository's properties.
 * @since 4.0.0
 */
public record RepositoryValueObject(
  @NotNull String owner, @NotNull String name, @NotNull String description,
  @Nullable String license, @NotNull RepositoryPropertiesValueObject properties
) {}
