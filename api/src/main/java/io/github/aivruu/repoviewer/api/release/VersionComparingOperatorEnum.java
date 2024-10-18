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
package io.github.aivruu.repoviewer.api.release;

/**
 * This enum is used to specify what operator-type would be used for comparing operations
 * at {@link RepositoryReleaseModel#compareVersionFromNumber(VersionComparingOperatorEnum, int)}.
 *
 * @since 3.3.4
 */
public enum VersionComparingOperatorEnum {
  /** Uses the equality operator. */
  EQUAL,
  /** Uses the inequality operator. */
  DIFFERENT,
  /** Uses the less-than operator. */
  LESS,
  /** Uses the less-than or equal operator. */
  LESS_OR_EQUAL,
  /** Uses the greater operator. */
  GREATER,
  /** Uses the greater or equal operator. */
  GREATER_OR_EQUAL
}
