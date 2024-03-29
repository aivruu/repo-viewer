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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import me.qeklydev.downloader.release.ReleaseModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Json deserializer implementation for handle provided
 * json data files.
 *
 * @since 0.0.1
 */
public enum ReleaseModelCodec implements JsonDeserializer<ReleaseModel> {
  INSTANCE;

  /**
   * Used to concatenate values from the JSON body provided
   * by HTTP requests.
   *
   * @since 0.0.1
   */
  public static final StringBuilder BUILDER = new StringBuilder();

  @Override
  public @Nullable ReleaseModel deserialize(final @NotNull JsonElement jsonElement, final @NotNull Type type,
                                            final @NotNull JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    final var providedJsonObject = jsonElement.getAsJsonObject();
    final var possibleMessage = providedJsonObject.get("message");
    if (possibleMessage != null) { // Checks if the latest repository exists.
      return null;
    }
    final var providedAssets = providedJsonObject.getAsJsonArray("assets").asList();
    final var assetsList = new ArrayList<String>(providedAssets.size());
    for (final var element : providedAssets) {
      final var assetObject = element.getAsJsonObject();
      assetsList.add(BUILDER.append(assetObject.get("name").getAsString())
          .append(": ")
          .append(assetObject.get("url").getAsString())
          .toString());
    }
    return new ReleaseModel(providedJsonObject.get("tag_name").getAsString(), assetsList);
  }
}
