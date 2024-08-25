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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import io.github.aivruu.repoviewer.api.release.LatestReleaseModel;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

/**
 * {@link JsonDeserializer} implementation used for manual deserialization with {@link LatestReleaseModel}s.
 *
 * @since 0.0.1
 */
public enum RepositoryReleaseCodec implements JsonDeserializer<LatestReleaseModel> {
  /** Used for rapid-access to the enum's instance. */
  INSTANCE;
  /** Used to concatenate values from the {@code json} provided the requests responses. */
  public static final StringBuilder BUILDER = new StringBuilder();

  @Override
  public @Nullable LatestReleaseModel deserialize(final JsonElement jsonElement, final Type type,
                                                  final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    final var providedJsonObject = jsonElement.getAsJsonObject();
    if (providedJsonObject.get("message") != null) { // Checks if the latest-release exists.
      return null;
    }
    final var providedAssets = providedJsonObject.getAsJsonArray("assets").asList();
    final var assetsArray = new String[providedAssets.size()];
    for (int i = 0; i < assetsArray.length; i++) {
      final var assetJsonObject = providedAssets.get(i).getAsJsonObject();
      assetsArray[i] = BUILDER.append(assetJsonObject.get("name").getAsString())
        .append("->")
        .append(assetJsonObject.get("browser_download_url").getAsString())
        .toString();
    }
    return new LatestReleaseModel(providedJsonObject.get("tag_name").getAsString(), assetsArray);
  }
}
