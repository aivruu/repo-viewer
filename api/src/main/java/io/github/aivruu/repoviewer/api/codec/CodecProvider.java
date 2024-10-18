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
package io.github.aivruu.repoviewer.api.codec;

import io.github.aivruu.repoviewer.api.http.request.RequestableModel;
import org.jetbrains.annotations.Nullable;

/**
 * This class is used for the deserialization handling of {@link RequestableModel}s.
 *
 * @since 2.3.4
 */
public interface CodecProvider {
  /**
   * Deserializes the {@code json} given into a specified-type {@link RequestableModel}.
   *
   * @param type the model to create since the given json.
   * @param json the json to deserialize into a new model.
   * @param <Model> the model-type specified that must be a {@link RequestableModel} implementation.
   * @return The deserialized model, or {@code null} if something went wrong, or there's nothing
   *     to deserialize.
   * @since 2.3.4
   */
  <Model extends RequestableModel> @Nullable Model from(final Class<Model> type, final String json);
}
