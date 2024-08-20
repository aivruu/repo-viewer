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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.aivruu.repoviewer.api.codec.CodecProvider;
import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import io.github.aivruu.repoviewer.api.release.LatestReleaseModel;
import io.github.aivruu.repoviewer.api.repository.GithubRepositoryModel;
import io.github.aivruu.repoviewer.codec.type.RepositoryCodec;
import io.github.aivruu.repoviewer.codec.type.RepositoryReleaseCodec;
import org.jetbrains.annotations.Nullable;

/**
 * {@link CodecProvider} implementation for usage of codecs for {@link LatestReleaseModel}s and
 * {@link GithubRepositoryModel}s.
 *
 * @since 2.3.4
 */
public enum CodecProviderImpl implements CodecProvider {
  /** Used for rapid-access to the implementation. */
  INSTANCE;

  /**
   * A {@link Gson} instance with defined-attributes for custom-codecs for {@link LatestReleaseModel}
   * and {@link GithubRepositoryModel} types for deserialization.
   */
  private static final Gson GSON_PROVIDER = new GsonBuilder()
    .registerTypeAdapter(LatestReleaseModel.class, RepositoryReleaseCodec.INSTANCE)
    .registerTypeAdapter(GithubRepositoryModel.class, RepositoryCodec.INSTANCE)
    .create();

  /**
   * Deserializes the {@code json} given into a specified-type {@link RequestableModel}.
   *
   * @param modelType the model-type to create since the given json.
   * @param json the json to deserialize into a new model.
   * @param <Model> the model-type specified that must be a {@link RequestableModel} implementation.
   * @return The deserialized model, or {@code null} if something went wrong, or there's nothing
   *     to deserialize.
   * @since 2.3.4
   */
  public <Model extends RequestableModel> @Nullable Model from(final Class<Model> modelType, final String json) {
    return GSON_PROVIDER.fromJson(json, modelType);
  }
}
