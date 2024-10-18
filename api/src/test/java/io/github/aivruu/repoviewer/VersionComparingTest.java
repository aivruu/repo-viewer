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

import io.github.aivruu.repoviewer.api.release.RepositoryReleaseModel;
import io.github.aivruu.repoviewer.api.release.VersionComparingOperatorEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VersionComparingTest {
  @Test
  void comparingTest() {
    final var deprecatedRelease = new RepositoryReleaseModel("aivruu", "v2.3.4", "v2.3.4 - Full Recode and Improvements",
      10_230_123, new String[]{});
    final var latestRelease = new RepositoryReleaseModel("aivruu", "v3.3.4", "v3.3.4 - Improvements and Features",
      49_032_123, new String[]{});
    // We compare if the version for the 'latest-release-for-comparing' is newer than the 'deprecated-release'.
    Assertions.assertTrue(
      latestRelease.compareVersionFromString(VersionComparingOperatorEnum.LESS, deprecatedRelease.tagName()),
      "The compared version isn't greater than this release-model's version.");
  }
}
