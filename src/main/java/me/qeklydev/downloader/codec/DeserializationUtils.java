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
package me.qeklydev.downloader.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.qeklydev.downloader.release.ReleaseModel;
import me.qeklydev.downloader.repository.GitHubRepositoryModel;
import org.jetbrains.annotations.NotNull;

/**
 * Provide fast-utils for release and repository models
 * deserialization with custom codecs.
 *
 * @since 0.0.1
 */
public final class DeserializationUtils {
  /**
   * The GSON provider with the custom codecs
   * for release and repository models deserialization.
   *
   * @since 0.0.1
   */
  public static final Gson GSON_PROVIDER = new GsonBuilder()
      .registerTypeAdapter(ReleaseModel.class, ReleaseModelCodec.INSTANCE)
      .registerTypeAdapter(GitHubRepositoryModel.class, RepositoryModelCodec.INSTANCE)
      .create();

  private DeserializationUtils() {
    throw new UnsupportedOperationException("This class cannot be instantiated");
  }

  /**
   * Deserializes the provided JSON string into a ReleaseModel
   * using the release-codec.
   *
   * @param json the json body.
   * @return The {@link ReleaseModel}.
   * @since 0.0.1
   */
  public static @NotNull ReleaseModel withReleaseCodec(final @NotNull String json) {
    return GSON_PROVIDER.fromJson(json, ReleaseModel.class);
  }

  /**
   * Deserializes the provided JSON string into a GitHubRepositoryModel
   * using the repository-codec.
   *
   * @param json the json body.
   * @return The {@link GitHubRepositoryModel}.
   * @since 0.0.1
   */
  public static @NotNull GitHubRepositoryModel withRepositoryCodec(final @NotNull String json) {
    return GSON_PROVIDER.fromJson(json, GitHubRepositoryModel.class);
  }
}
