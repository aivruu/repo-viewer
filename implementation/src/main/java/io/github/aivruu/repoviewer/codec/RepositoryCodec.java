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
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.github.aivruu.repoviewer.api.repository.GithubRepositoryModel;
import io.github.aivruu.repoviewer.api.repository.RepositoryModelBuilder;
import io.github.aivruu.repoviewer.api.repository.attribute.RepositoryAttributes;
import io.github.aivruu.repoviewer.api.repository.attribute.RepositoryAttributesBuilder;

import java.lang.reflect.Type;

/**
 * {@link JsonDeserializer} used for manual deserialization with {@link GithubRepositoryModel}s.
 *
 * @since 0.0.1
 */
public enum RepositoryCodec implements JsonDeserializer<GithubRepositoryModel> {
  /** Used for rapid-access to the enum's instance. */
  INSTANCE;

  @Override
  public GithubRepositoryModel deserialize(final JsonElement jsonElement, final Type type,
                                           final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    final var jsonObject = jsonElement.getAsJsonObject();
    if (jsonObject.get("message") != null) { // Checks if the repository exists.
      return null;
    }
    final var repositoryOwner = jsonObject.get("owner").getAsJsonObject().get("login").getAsString();
    final var licenseNode = jsonObject.get("license");
    var license = "";
    // Avoid failures due to non-detected or unavailable license for the repository.
    if (licenseNode.isJsonNull()) {
      license = "unknown";
    } else {
      license = licenseNode.getAsJsonObject().get("name").getAsString();
    }
    return RepositoryModelBuilder.newBuilder()
      .owner(repositoryOwner)
      .name(jsonObject.get("name").getAsString())
      .description(jsonObject.get("description").getAsString())
      .license(license)
      .attributes(this.createAttributesContainer(jsonObject))
      .build();
  }

  private RepositoryAttributes createAttributesContainer(final JsonObject jsonObject) {
    final var isForked = jsonObject.get("fork").getAsBoolean();
    // If this repository is a fork of another repository, we need to
    // get the name of the owner of the original repository.
    final var repositoryParent = isForked
      ? jsonObject.get("parent").getAsJsonObject().get("owner").getAsJsonObject().get("login").getAsString()
      : null;
    final var providedTopicsArray = jsonObject.get("topics").getAsJsonArray();
    final var topicsArray = new String[providedTopicsArray.size()];
    for (int i = 0; i < topicsArray.length; i++) {
      topicsArray[i] = providedTopicsArray.get(i).getAsString();
    }
    return RepositoryAttributesBuilder.newBuilder()
      .canBeForked(jsonObject.get("allow_forking").getAsBoolean())
      .stars(jsonObject.get("stargazers_count").getAsInt())
      .forksAmount(jsonObject.get("forks_count").getAsInt())
      .fork(jsonObject.get("fork").getAsBoolean())
      .parent(repositoryParent)
      .visible(jsonObject.get("private").getAsBoolean())
      .archived(jsonObject.get("archived").getAsBoolean())
      .disabled(jsonObject.get("disabled").getAsBoolean())
      .language(jsonObject.get("language").getAsString())
      .topics(topicsArray)
      .build();
  }
}
