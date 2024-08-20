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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReleaseRequestTest {
  @Test
  void releaseRequest() {
    final var releaseHttpRequest = new ReleaseHttpRequestModel(RepositoryUrlBuilder.from("aivruu", "repo-viewer"));
    System.out.println("Requesting latest-release for repository: " + releaseHttpRequest.repository());
    final var requestResponse = releaseHttpRequest.request(10);
    System.out.println("Made request for repository correctly.");
    requestResponse.thenAccept(latestReleaseModel -> {
      if (latestReleaseModel == null) {
        Assertions.fail("Failed to deserialize json response into a LatestReleaseModel.");
      }
      System.out.println("Requested repository's latest-release %s deserialized correctly: %s"
        .formatted(latestReleaseModel.version(), latestReleaseModel.assets()[0]));
    });
  }
}
