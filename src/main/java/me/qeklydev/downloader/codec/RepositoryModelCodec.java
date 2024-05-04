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
package me.qeklydev.downloader.codec;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Locale;
import me.qeklydev.downloader.GitHubURLProvider;
import me.qeklydev.downloader.http.HTTPReleaseModelRequest;
import me.qeklydev.downloader.license.RepositoryLicense;
import me.qeklydev.downloader.logger.LoggerUtils;
import me.qeklydev.downloader.release.ReleaseModel;
import me.qeklydev.downloader.repository.GitHubRepositoryModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum RepositoryModelCodec implements JsonDeserializer<GitHubRepositoryModel> {
  INSTANCE;

  @Override
  public @NotNull GitHubRepositoryModel deserialize(final @NotNull JsonElement jsonElement, final @NotNull Type type,
                                                    final @NotNull JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    final var jsonObject = jsonElement.getAsJsonObject();
    final var repositoryOwner = jsonObject.get("owner").getAsJsonObject().get("login").getAsString();
    final var repositoryName = jsonObject.get("name").getAsString();
    final var description = jsonObject.get("description").getAsString();
    final var licenseKey = jsonObject.get("license").getAsJsonObject().get("key").getAsString();
    // Verifies if the license key for this repository is "gpl-3.0",
    // if it is true, we will replace the character '-' and '.' by a
    // '_' character and make it uppercase to be parsed into a valid enum.
    var licenseType = RepositoryLicense.UNLICENSE; // Default license type for parsing.
    try {
      // We use a try/catch due to possible failed parsing for licenses
      // keys from GitHub-API, and the already defined on the licenses enum.
      licenseType = RepositoryLicense.valueOf(licenseKey.equals("gpl-3.0")
          ? licenseKey.replace("-", "_")
          .replace(".", "_")
          .toUpperCase(Locale.ROOT)
          : licenseKey.toUpperCase(Locale.ROOT));
    } catch (final IllegalArgumentException exception) {
      // Yeah, something went wrong.
      LoggerUtils.error("Failed to parse license type since GitHub-API request.");
      // So, license for this repository have not been provided.
      licenseType = null;
    }
    final var canBeForked = jsonObject.get("allow_forking").getAsBoolean();
    final var starsAmount = jsonObject.get("stargazers_count").getAsInt();
    final var forksAmount = jsonObject.get("forks_count").getAsInt();
    final var isForked = jsonObject.get("fork").getAsBoolean();
    // If this repository is a fork of another repository, we need to
    // get the name of the owner of the original repository.
    final var repositoryParent = isForked
        ? jsonObject.get("parent").getAsJsonObject().get("owner").getAsJsonObject().get("login").getAsString()
        : null;
    final var isPrivate = jsonObject.get("private").getAsBoolean();
    final var isArchived = jsonObject.get("archived").getAsBoolean();
    final var isDisabled = jsonObject.get("disabled").getAsBoolean();
    final var mostUsedLanguage = jsonObject.get("language").getAsString();
    final var providedTopicsArray = jsonObject.get("topics").getAsJsonArray();
    final var topicsList = new ArrayList<String>(providedTopicsArray.size());
    for (final var topicElement : providedTopicsArray) {
      topicsList.add(topicElement.getAsString());
    }
    // We need to request the release model for this repository,
    // if the value is null, the latest release doesn't exist.
    // Other-wise just use the given model.
    final var latestReleaseModel = this.provideReleaseModel(GitHubURLProvider.of(repositoryOwner, repositoryName));
    // After obtain all required information from JSON-body provided,
    // we create a new object that represents this requested repository
    // with all needed information.
    return new GitHubRepositoryModel(
        repositoryOwner, repositoryName, description, licenseType, latestReleaseModel,
        isForked, repositoryParent, canBeForked, starsAmount, forksAmount,
        !isPrivate, isArchived, isDisabled, mostUsedLanguage, topicsList);
  }

  /**
   * Creates a new {@code HTTPReleaseModelRequest} and request the model
   * for the latest repository release, this could be {@code null}
   * if the repository doesn't have any release.
   *
   * @param repository the repository url.
   * @return The {@link ReleaseModel}, or {@code null}.
   * @since 0.0.1
   * @see HTTPReleaseModelRequest#provideModel()
   */
  private @Nullable ReleaseModel provideReleaseModel(final @NotNull String repository) {
    final var httpReleaseModelRequest = new HTTPReleaseModelRequest(HttpClient.newHttpClient(), repository);
    return httpReleaseModelRequest.provideModel();
  }
}
