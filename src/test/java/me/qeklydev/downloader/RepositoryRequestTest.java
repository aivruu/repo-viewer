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

import java.net.http.HttpClient;
import java.time.Duration;
import me.qeklydev.downloader.http.HTTPRepositoryModelRequest;
import org.junit.jupiter.api.Test;

public class RepositoryRequestTest {
  @Test
  void test() {
    final var httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .connectTimeout(Duration.ofSeconds(60))
        .build();
    final var httpRepositoryRequest = new HTTPRepositoryModelRequest(
        httpClient, GitHubURLProvider.of("aivruu", "AnnounceMessages"));
    final var repositoryModel = httpRepositoryRequest.provideModel();
    if (repositoryModel == null) {
      System.out.println("Repository doesn't exists, or HTTP request has failed.");
      return;
    }
    final var license = repositoryModel.license();
    System.out.println("Information about aivruu/AnnounceMessages repository:");
    System.out.println("Owner: " + repositoryModel.owner());
    System.out.println("Name: " + repositoryModel.name());
    System.out.println("Description: " + repositoryModel.description());
    System.out.println("License: {");
    System.out.println(" Key: " + license.name());
    System.out.println(" Name: " + license.fullName());
    System.out.println(" Url: " + license.url());
    System.out.println("}");
    /*
     * The release model can be null, but this repository does
     * have a release published, so we can skip the null check.
     */
    System.out.println("Latest release: v" + repositoryModel.releaseModel().version());
    System.out.println("Forked: " + repositoryModel.isForked());
    System.out.println("Accept Forks: " + repositoryModel.canBeForked());
    System.out.println("Stars: " + repositoryModel.stars());
    System.out.println("Forks: " + repositoryModel.forks());
    System.out.println("Public: " + repositoryModel.isPublic());
    System.out.println("Archived: " + repositoryModel.isArchived());
    System.out.println("Most-Used Language: " + repositoryModel.language());
  }
}
