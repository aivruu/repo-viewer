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
import com.google.gson.JsonObject;
import io.github.aivruu.repoviewer.repository.domain.RepositoryAggregateRoot;
import io.github.aivruu.repoviewer.repository.domain.RepositoryValueObject;
import io.github.aivruu.repoviewer.repository.domain.RepositoryPropertiesValueObject;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public enum RepositoryJsonCodecAdapter implements JsonDeserializer<RepositoryAggregateRoot> {
  INSTANCE;

  @Override
  public @NotNull RepositoryAggregateRoot deserialize(
    final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext
  ) {
    final var jsonObject = jsonElement.getAsJsonObject();
    final var id = jsonObject.get("id").getAsString();
    final var licenseNode = jsonObject.get("license");
    var license = "unknown";
    if (!licenseNode.isJsonNull()) {
      license = licenseNode.getAsJsonObject().get("name").getAsString();
    }
    return new RepositoryAggregateRoot(id, new RepositoryValueObject(
      jsonObject.get("owner").getAsJsonObject().get("login").getAsString(),
      jsonObject.get("name").getAsString(),
      jsonObject.get("description").getAsString(),
      license,
      this.createAttributesContainer(jsonObject)));
  }

  private @NotNull RepositoryPropertiesValueObject createAttributesContainer(final @NotNull JsonObject jsonObject) {
    final var forked = jsonObject.get("fork").getAsBoolean();
    final var parent = forked
      ? jsonObject.get("parent").getAsJsonObject().get("owner").getAsJsonObject().get("login").getAsString()
      : null;
    final var providedTopicsArray = jsonObject.get("topics").getAsJsonArray();
    final var topicsArray = new String[providedTopicsArray.size()];
    for (byte i = 0; i < topicsArray.length; i++) {
      topicsArray[i] = providedTopicsArray.get(i).getAsString();
    }
    return new RepositoryPropertiesValueObject(
      forked, parent,
      jsonObject.get("allow_forking").getAsBoolean(),
      jsonObject.get("stargazers_count").getAsInt(),
      jsonObject.get("forks_count").getAsInt(),
      jsonObject.get("private").getAsBoolean(),
      jsonObject.get("archived").getAsBoolean(),
      jsonObject.get("disabled").getAsBoolean(),
      jsonObject.get("language").getAsString(),
      topicsArray);
  }
}
