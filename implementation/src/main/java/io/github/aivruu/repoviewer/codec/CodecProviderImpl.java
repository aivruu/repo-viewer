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
package io.github.aivruu.repoviewer.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.aivruu.repoviewer.api.codec.CodecProvider;
import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import io.github.aivruu.repoviewer.api.release.RepositoryReleaseModel;
import io.github.aivruu.repoviewer.api.repository.GithubRepositoryModel;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * {@link CodecProvider} implementation for usage of codecs for {@link RepositoryReleaseModel}s and
 * {@link GithubRepositoryModel}s.
 *
 * @since 2.3.4
 */
public enum CodecProviderImpl implements CodecProvider {
  /** Used for rapid-access to the implementation. */
  INSTANCE;

  /**
   * A {@link Gson} instance with defined-attributes for custom-codecs for {@link RepositoryReleaseModel}
   * and {@link GithubRepositoryModel} types for deserialization.
   */
  private static final Gson GSON_PROVIDER = new GsonBuilder()
    .registerTypeAdapter(RepositoryReleaseModel.class, RepositoryReleaseCodec.INSTANCE)
    .registerTypeAdapter(GithubRepositoryModel.class, RepositoryCodec.INSTANCE)
    .create();

  @Override
  public <Model extends RequestableModel> @Nullable Model from(final Type type, final String json) {
    return GSON_PROVIDER.fromJson(json, type);
  }
}
