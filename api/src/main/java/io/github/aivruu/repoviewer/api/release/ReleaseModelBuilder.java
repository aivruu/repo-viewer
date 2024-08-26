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
package io.github.aivruu.repoviewer.api.release;

public class ReleaseModelBuilder {
  private String author;
  private String tagName;
  private String releaseName;
  private int uniqueId;
  private String[] assets;

  public static ReleaseModelBuilder newBuilder() {
    return new ReleaseModelBuilder();
  }

  public ReleaseModelBuilder author(final String author) {
    this.author = author;
    return this;
  }

  public ReleaseModelBuilder tagName(final String tagName) {
    this.tagName = tagName;
    return this;
  }

  public ReleaseModelBuilder releaseName(final String releaseName) {
    this.releaseName = releaseName;
    return this;
  }

  public ReleaseModelBuilder uniqueId(final int uniqueId) {
    this.uniqueId = uniqueId;
    return this;
  }

  public ReleaseModelBuilder assets(final String[] assets) {
    this.assets = assets;
    return this;
  }

  public RepositoryReleaseModel build() {
    return new RepositoryReleaseModel(this.author, this.tagName, this.releaseName, this.uniqueId, this.assets);
  }
}
