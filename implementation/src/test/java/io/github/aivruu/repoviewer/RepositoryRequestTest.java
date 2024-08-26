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

public class RepositoryRequestTest {
  @Test
  void repositoryRequest() {
    final var repositoryHttpRequest = new RepositoryHttpRequestModel(
      RepositoryUrlBuilder.fromRepository("aivruu", "repo-viewer"));
    System.out.println("Requesting repository from: " + repositoryHttpRequest.url());
    final var responseStatusProvider = repositoryHttpRequest.requestThen(10, repositoryModel ->
      System.out.println("Requested repository %s deserialized correctly".formatted(repositoryModel.name()))
    ).join();
    Assertions.assertTrue(responseStatusProvider.valid(),
      "Failed to deserialize the json response into a GithubRepositoryModel.");
  }
}
