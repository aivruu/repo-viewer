package me.qeklydev.downloader.release;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * This record is used to store information about
 * the requested repository release.
 *
 * @param version the release version.
 * @since 0.0.1
 */
public record SerializedReleaseModel(@NotNull String version) {
  /**
   * Returns a string array with every value
   * of the version.
   *
   * @return The semantic string version.
   * @since 0.0.1
   */
  public @NotNull String[] semanticVersion() {
    // e.g 2.10.1 -> ["2", "10", "1"]
    return this.version.split("\\.");
  }

  /**
   * Returns an int array with every value
   * of the version.
   *
   * @return The semantic integer version.
   * @since 0.0.1
   */
  public int[] semanticIntegerVersion() {
    var integersArray = new int[0];
    var nonZeroIntegersArray = new int[0];
    for (int i = 0; i < this.version.length(); i++) {
      final var symbol = this.version.charAt(i);
      if (!Character.isDigit(symbol)) {
        continue;
      }
      integersArray = Arrays.copyOf(integersArray, integersArray.length + 1);
      final var numericValue = Character.getNumericValue(symbol);
      if (numericValue != 0) {
        nonZeroIntegersArray = integersArray;
      }
      integersArray[integersArray.length - 1] = numericValue;
    }
    return (integersArray.length > 1) && (integersArray[integersArray.length - 1]) == 0
        ? nonZeroIntegersArray : integersArray;
  }

  /**
   * Compares the given values to determinate if the semantic
   * version is equal.
   *
   * @param parts the version parts.
   * @return The boolean status for this operation,
   *     {@code true} if any value is similar, otherwise return
   *     {@code false}.
   * @since 0.0.1
   */
  public boolean semanticEqualsTo(final String @NotNull... parts) {
    final var version = this.semanticVersion();
    for (int i = 0; i < parts.length; i++) {
      if (parts[i].equals(version[i])) {
        /*
         * This value is similar on the current iterated
         * semantic version element.
         */
        continue;
      }
      // Any value is similar so return false.
      return false;
    }
    return true;
  }
}
