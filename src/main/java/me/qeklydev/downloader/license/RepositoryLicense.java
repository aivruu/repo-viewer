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
package me.qeklydev.downloader.license;

import org.jetbrains.annotations.NotNull;

/**
 * This enum is used to represent every license type
 * that can be used for a GitHub repository.
 *
 * @since 0.0.1
 */
public enum RepositoryLicense {
  GPL("GNU General Public License v3.0","https://www.gnu.org/licenses/gpl-3.0.html"),
  MIT("MIT License", "https://opensource.org/licenses/MIT"),
  APACHE("Apache License 2.0", "https://www.apache.org/licenses/LICENSE-2.0"),
  MOZILLA("Mozilla Public License 2.0", "https://www.mozilla.org/en-US/MPL/2.0/"),
  BSD("BSD 3-Clause License", "https://opensource.org/licenses/BSD-3-Clause"),
  UNLICENSE("The Unlicense", "https://unlicense.org/"),
  ECLIPSE("Eclipse Public License 2.0", "https://www.eclipse.org/legal/epl-2.0/"),
  CC("Creative Commons Zero v1.0 Universal", "https://creativecommons.org/publicdomain/zero/1.0/");

  private final String fullName;
  private final String url;

  RepositoryLicense(final @NotNull String fullName, final @NotNull String url) {
    this.fullName = fullName;
    this.url = url;
  }

  /**
   * Returns the name of the license.
   *
   * @return the license name.
   * @since 0.0.1
   */
  public @NotNull String fullName() {
    return this.fullName;
  }

  /**
   * Returns the URL for the license
   * visualization.
   *
   * @return the license URL.
   * @since 0.0.1
   */
  public @NotNull String url() {
    return this.url;
  }
}
