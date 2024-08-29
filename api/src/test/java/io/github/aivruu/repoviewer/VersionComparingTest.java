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

import io.github.aivruu.repoviewer.api.release.ReleaseModelBuilder;
import io.github.aivruu.repoviewer.api.release.VersionComparingOperators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VersionComparingTest {
  @Test
  void comparingTest() {
    final var deprecatedRelease = ReleaseModelBuilder.newBuilder()
      .author("aivruu")
      .tagName("v2.3.4")
      .releaseName("v2.3.4 - Full Recode and Improvements")
      .uniqueId(10_230_123 /* Random release-id. */)
      .assets(new String[]{})
      .build();
    final var latestReleaseForComparing = ReleaseModelBuilder.newBuilder()
      .author("aivruu")
      .tagName("v3.3.4")
      .releaseName("v3.3.4 - Improvements and Features")
      .uniqueId(49_032_123 /* Random release-id. */)
      .assets(new String[]{})
      .build();
    // We compare if the version for the 'latest-release-for-comparing' is newer than the 'deprecated-release'.
    Assertions.assertTrue(
      latestReleaseForComparing.compareVersionFromString(VersionComparingOperators.LESS, deprecatedRelease.tagName()),
      "The compared version isn't greater than this release-model's version.");
  }
}
