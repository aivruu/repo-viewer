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
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import me.qeklydev.downloader.release.ReleaseModel;
import org.jetbrains.annotations.NotNull;

/**
 * Json deserializer implementation for handle provided
 * json data files.
 *
 * @since 0.0.1
 */
public enum ReleaseModelDeserializer implements JsonDeserializer<ReleaseModel> {
  INSTANCE;

  /**
   * Gson instance that uses this custom adapter for
   * perform deserialization to upcoming json data files.
   *
   * @since 0.0.1
   */
  public static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(ReleaseModel.class, INSTANCE)
      .create();

  @Override
  public @NotNull ReleaseModel deserialize(final @NotNull JsonElement jsonElement, final @NotNull Type type,
                                           final @NotNull JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    final var providedJsonObject = jsonElement.getAsJsonObject();
    return new ReleaseModel(providedJsonObject.getAsString());
  }
}
