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
