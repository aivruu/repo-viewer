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

import io.github.aivruu.repoviewer.api.release.LatestReleaseModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class AssetsDownloadTest {
  @Test
  void downloadTest() {
    // We create a custom release-model for this test.
    final var latestReleaseModel = new LatestReleaseModel("1.3.4",
      new String[]{ "https://github.com/aivruu/repo-viewer/releases/download/1.3.4/release-downloader-1.3.4.jar" });
    final var destinationDirectory = new File("downloads");
    if (!destinationDirectory.exists()) destinationDirectory.mkdir();

    latestReleaseModel.downloadAll(destinationDirectory)
      .whenComplete((downloaded, exception) -> Assertions.assertNull(exception, "Failed to download assets."))
      .thenAccept(downloaded ->
        Assertions.assertTrue(downloaded, "Appears that all, or some assets, where not downloaded."));
  }
}
