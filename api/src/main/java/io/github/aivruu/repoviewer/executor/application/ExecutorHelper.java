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
package io.github.aivruu.repoviewer.executor.application;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Custom thread-pool creator utility-class for asynchronous-operations.
 *
 * @since 4.0.0
 */
public final class ExecutorHelper {
  private static Executor pool;

  private ExecutorHelper() {
    throw new UnsupportedOperationException("This class is for utility.");
  }

  /**
   * Returns the {@link Executor} instance.
   *
   * @return The {@link Executor}.
   * @throws IllegalStateException if the thread-pool is not initialized.
   * @since 4.0.0.
   */
  public static @NotNull Executor get() {
    if (pool == null) {
      throw new IllegalStateException("The thread-pool has not been initialized.");
    }
    return pool;
  }

  /**
   * Initializes the thread-pool with the given amount of threads.
   *
   * @param threads the amount of threads to use.
   * @throws IllegalStateException if the thread-pool is already initialized.
   * @since 4.0.0
   */
  public static void build(final int threads) {
    if (pool != null) {
      throw new IllegalStateException("The thread-pool is already initialized.");
    }
    pool = Executors.newFixedThreadPool(threads, r -> new Thread(r, "RepoViewer-Thread-Pool"));
  }
}
