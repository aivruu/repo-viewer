//
// Copyright (C) 2024-2025 aivruu - repo-viewer
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
package io.github.aivruu.repoviewer.codec.infrastructure.type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import io.github.aivruu.repoviewer.release.domain.ReleaseAggregateRoot;
import io.github.aivruu.repoviewer.release.domain.ReleaseValueObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public enum ReleaseJsonCodecAdapter implements JsonDeserializer<ReleaseAggregateRoot> {
  INSTANCE;

  private static final String ASSET_FORMAT_STRING = "%s:%s";

  @Override
  public @NotNull ReleaseAggregateRoot deserialize(
    final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext
  ) {
    final var jsonObject = jsonElement.getAsJsonObject();
    final var providedAssets = jsonObject.getAsJsonArray("assets").asList();
    final var assetsArray = new String[providedAssets.size()];
    for (byte i = 0; i < assetsArray.length; i++) {
      final var assetJsonObject = providedAssets.get(i).getAsJsonObject();
      assetsArray[i] = ASSET_FORMAT_STRING.formatted(
        assetJsonObject.get("name").getAsString(),
        assetJsonObject.get("browser_download_url").getAsString());
    }
    return new ReleaseAggregateRoot(jsonObject.get("id").getAsString(), new ReleaseValueObject(
      jsonObject.get("author").getAsJsonObject().get("login").getAsString(),
      jsonObject.get("tag_name").getAsString(),
      jsonObject.get("name").getAsString(),
      assetsArray));
  }
}
