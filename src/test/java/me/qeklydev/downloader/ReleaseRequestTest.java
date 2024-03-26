/*
 * This file is part of release-downloader - https://github.com/aivruu/release-downloader
 * Copyright (C) 2020-2024 Aivruu (https://github.com/aivruu)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package me.qeklydev.downloader;

import me.qeklydev.downloader.http.HTTPModelRequest;
import org.junit.jupiter.api.Test;

public class ReleaseRequestTest {
  @Test
  void test() {
    final var httpModelRequest = new HTTPModelRequest(
        GitHubRepositoryUrlFormatter.of("FrozzMC", "title")); // Provides a formatted URL for request.
    final var releaseModel = httpModelRequest.provideLatestRelease().join();
    if (releaseModel == null) {
      System.out.println("HTTP request body not received, or the repository don't have any release yet.");
      return;
    }
    System.out.println("HTTP request response received.");
    System.out.println("version: " + releaseModel.version());
    final var releaseAssets = releaseModel.assets();
    System.out.println("assets:");
    for (final var element : releaseAssets) {
      System.out.println("- " + element);
    }
  }
}
