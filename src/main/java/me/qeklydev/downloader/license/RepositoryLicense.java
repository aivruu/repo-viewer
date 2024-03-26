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

  private final String licenseName;
  private final String url;

  RepositoryLicense(final @NotNull String licenseName, final @NotNull String url) {
    this.licenseName = licenseName;
    this.url = url;
  }

  /**
   * Returns the name of the license.
   *
   * @return the license name.
   * @since 0.0.1
   */
  public @NotNull String licenseName() {
    return this.licenseName;
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
