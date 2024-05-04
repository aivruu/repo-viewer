/*
 * This file is part of release-downloader - https://github.com/aivruu/release-downloader
 * Copyright (C) 2020-2024 aivruu (https://github.com/aivruu)
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
package me.qeklydev.downloader.logger;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to function as a fast-utility
 * for logs sending.
 *
 * @since 0.1.2
 */
public final class LoggerUtils {
  /**
   * A Logger instance with the identifier "release-downloader"
   * that will be used for lgs sending.
   *
   * @since 0.1.2
   */
  private static final Logger LOGGER = Logger.getLogger("release-downloader");

  private LoggerUtils() {
    throw new UnsupportedOperationException("This class is for utility and cannot be instantiated.");
  }

  /**
   * Sends a log message with a custom level type.
   *
   * @param levelType the log {@link Level} designed.
   * @param message the log message.
   * @since 0.1.2
   */
  public static void of(final @NotNull Level levelType, final @NotNull String message) {
    LOGGER.log(levelType, message);
  }

  /**
   * Sends a log message and marks it as INFO.
   *
   * @param message the log message.
   * @since 0.1.2
   */
  public static void info(final @NotNull String message) {
    LOGGER.log(Level.INFO, message);
  }

  /**
   * Sends a log message and marks it as WARNING.
   *
   * @param message the log message.
   * @since 0.1.2
   */
  public static void warn(final @NotNull String message) {
    LOGGER.log(Level.WARNING, message);
  }

  /**
   * Sends a log message and marks it as ERROR.
   *
   * @param message the log message.
   * @since 0.1.2
   */
  public static void error(final @NotNull String message) {
    LOGGER.log(Level.SEVERE, message);
  }
}
