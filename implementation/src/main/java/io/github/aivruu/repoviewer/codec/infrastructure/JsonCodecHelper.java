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
package io.github.aivruu.repoviewer.codec.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.aivruu.repoviewer.aggregate.domain.AggregateRoot;
import io.github.aivruu.repoviewer.codec.infrastructure.type.RepositoryJsonCodecAdapter;
import io.github.aivruu.repoviewer.codec.infrastructure.type.ReleaseJsonCodecAdapter;
import io.github.aivruu.repoviewer.release.domain.ReleaseAggregateRoot;
import io.github.aivruu.repoviewer.repository.domain.RepositoryAggregateRoot;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public final class JsonCodecHelper {
  private static final Gson GSON = new GsonBuilder()
    .setPrettyPrinting()
    .registerTypeAdapter(ReleaseAggregateRoot.class, ReleaseJsonCodecAdapter.INSTANCE)
    .registerTypeAdapter(RepositoryAggregateRoot.class, RepositoryJsonCodecAdapter.INSTANCE)
    .create();

  private JsonCodecHelper() {
    throw new UnsupportedOperationException("This class is for utility.");
  }

  public static <A extends AggregateRoot> @Nullable A read(final Type type, final String json) {
    return GSON.fromJson(json, type);
  }
}
