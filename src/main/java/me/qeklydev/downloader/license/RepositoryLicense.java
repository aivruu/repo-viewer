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
  /**
   * Represents the GPL 3.0 license type.
   *
   * @since 0.0.1
   */
  GPL_3_0("GNU General Public License v3.0","https://www.gnu.org/licenses/gpl-3.0.html"),
  /**
   * Represents the GPL 2.0 license type.
   * 
   * @since 0.2.3
   */
  GPL_2_0("GNU General Public License v2.0", "https://www.gnu.org/licenses/gpl-2.0.html"),
  /**
   * Represents the MIT license type.
   *
   * @since 0.0.1
   */
  MIT("MIT License", "https://opensource.org/licenses/MIT"),
  /**
   * Represents the Apache 2.0 license type.
   *
   * @since 0.0.1
   */
  APACHE("Apache License 2.0", "https://www.apache.org/licenses/LICENSE-2.0"),
  /**
   * Represents the Mozilla Public 2.0 license type.
   *
   * @since 0.0.1
   */
  MOZILLA("Mozilla Public License 2.0", "https://www.mozilla.org/en-US/MPL/2.0/"),
  /**
   * Represents the BSD license type.
   *
   * @since 0.0.1
   */
  BSD_3("BSD 3-Clause License", "https://opensource.org/licenses/BSD-3-Clause"),
  /**
   * Represents the BSD 2-Clause Simplified License type.
   * 
   * @since 0.2.3
   */
  BSD_2("BSD 2-Clause Simplified License", "https://opensource.org/licenses/BSD-2-Clause"),
  /**
   * Indicates that non-license have been specified.
   *
   * @since 0.0.1
   */
  UNLICENSE("The Unlicense", "https://unlicense.org/"),
  /**
   * Represents the Eclipse Public 2.0 license type.
   *
   * @since 0.0.1
   */
  ECLIPSE("Eclipse Public License 2.0", "https://www.eclipse.org/legal/epl-2.0/"),
  /**
   * Represents the Creative C-Z 1.0 license type.
   *
   * @since 0.0.1
   */
  CC("Creative Commons Zero v1.0 Universal", "https://creativecommons.org/publicdomain/zero/1.0/"),
  /**
   * Represents the Boost Software License 1.0 license type.
   * 
   * @since 0.2.3
   */
  BOOST_SOFTWARE("Boost Software License 1.0", "https://www.boost.org/LICENSE_1_0.txt"),
  /**
   * Represents the GNU Lesser General Public License v3.0 license type.
   * 
   * @since 0.2.3
   */
  LESSER_GPL("GNU Lesser General Public License v3.0", "https://www.gnu.org/licenses/lgpl-3.0.html"),
  /**
   * Represents the GNU Affero General Public License v3.0 license type.
   * 
   * @since 0.2.3
   */
  AFFERO_GPL("GNU Affero General Public License v3.0", "https://www.gnu.org/licenses/agpl-3.0.html");

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
