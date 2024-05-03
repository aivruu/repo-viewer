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
import java.lang.reflect.Type;
import me.qeklydev.downloader.release.ReleaseModel;
import me.qeklydev.downloader.repository.GitHubRepositoryModel;
import org.jetbrains.annotations.NotNull;

/**
 * Provide a builder pattern for models deserialization
 * handling.
 *
 * @since 0.2.2
 */
public final class DeserializationProvider {
  /**
   * The GSON provider with the custom codecs
   * for release and repository models deserialization.
   *
   * @since 0.0.1
   */
  private static final Gson GSON_PROVIDER = new GsonBuilder()
      .registerTypeAdapter(ReleaseModel.class, ReleaseModelCodec.INSTANCE)
      .registerTypeAdapter(GitHubRepositoryModel.class, RepositoryModelCodec.INSTANCE)
      .create();

  /**
   * Creates a new builder for deserialization model
   * request.
   *
   * @return A new {@link Builder}.
   * @since 0.2.2
   */
  public static @NotNull Builder builder() {
    return new Builder();
  }

  /**
   * This class is used to request new deserialization
   * to needed models.
   *
   * @since 0.2.2
   */
  public static class Builder {
    private String jsonBody;
    private CodecType codecType;

    /**
     * Sets the json-body for this builder instance.
     *
     * @param specifiedJsonBody The json required.
     * @return This builder instance.
     * @since 0.2.2
     */
    public @NotNull Builder jsonBody(final @NotNull String specifiedJsonBody) {
      this.jsonBody = specifiedJsonBody;
      return this;
    }

    /**
     * Sets the codec-type for this builder instance.
     *
     * @param specifiedCodecType The {@link CodecType} needed.
     * @return This builder instance.
     * @since 0.2.2
     */
    public @NotNull Builder codecType(final @NotNull CodecType specifiedCodecType) {
      this.codecType = specifiedCodecType;
      return this;
    }

    /**
     * Creates a new model with the specified json body
     * and codec type. Could throw an {@code IllegalStateException}
     * if the json-body or codec-type wasn't specified.
     *
     * @param <T> A generic type, can be cast to any needed
     *            model.
     * @return The deserialized model.
     * @since 0.2.2
     */
    public <T> @NotNull T build() {
      /*
       * Check if json-body or codec-type have been specified
       * on builder.
       */
      if (this.jsonBody == null || this.codecType == null) {
        throw new IllegalStateException("Json body or codec type for the deserialization have not been specified!");
      }
      return switch (this.codecType) {
        case RELEASE, REPOSITORY -> GSON_PROVIDER.fromJson(jsonBody, codecType.typeClass());
      };
    }
  }

  /**
   * This enum is used to specify and provide the selected type
   * for the deserialization requested.
   *
   * @since 0.2.2
   */
  public enum CodecType {
    /**
     * Used for {@link ReleaseModel} types.
     *
     * @since 0.2.2
     */
    RELEASE(ReleaseModel.class),
    /**
     * Used for {@link GitHubRepositoryModel} types.
     *
     * @since 0.2.2
     */
    REPOSITORY(GitHubRepositoryModel.class);

    private final Type type;

    CodecType(final @NotNull Type type) {
      this.type = type;
    }

    /**
     * Returns the type for the enum constant
     * used.
     *
     * @since 0.2.2
     */
    public @NotNull Type typeClass() {
      return this.type;
    }
  }
}
