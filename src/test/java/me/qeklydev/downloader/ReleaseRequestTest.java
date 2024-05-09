/*
 * This file is part of release-downloader - https://github.com/aivruu/release-downloader
 * Copyright (C) 2020-2024 aivruu (https://github.com/aivruu)
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

import java.net.http.HttpClient;
import java.time.Duration;
import me.qeklydev.downloader.http.HTTPReleaseModelRequest;
import org.junit.jupiter.api.Test;

public class ReleaseRequestTest {
  @Test
  void test() {
    final var httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .connectTimeout(Duration.ofSeconds(5))
        .build();
    final var releaseModelRequest = new HTTPReleaseModelRequest(
        httpClient, GitHubURLProvider.of("aivruu", "release-downloader"));
    final var releaseModel = releaseModelRequest.provideModel();
    // Check if the repository have a release.
    if (releaseModel == null) {
      System.out.println("Something went wrong, and release information was not provided.");
      return;
    }
    System.out.println("Version: " + releaseModel.version());
    releaseModel.assets().forEach(System.out::println);
  }
}
