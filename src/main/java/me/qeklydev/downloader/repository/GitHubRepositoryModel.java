package me.qeklydev.downloader.repository;

import me.qeklydev.downloader.license.RepositoryLicense;
import me.qeklydev.downloader.release.ReleaseModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This record represents a GitHub repository model based-on
 * the information provided by the HTTP request to the API.
 *
 * @param owner the owner of the repository.
 * @param id the id/name of the repository.
 * @param description the description of the repository.
 * @param license the license type of the repository.
 * @param releaseModel the abstract model for the latest
 *                     repository release.
 * @param isForked if the repository is a fork.
 * @param canBeForked if the repository can be forked.
 * @param stars the amount of stars the repository has.
 * @param forks the amount of forks the repository has.
 * @param isPublic if the repository is public.
 * @param isArchived if the repository is archived.
 * @param isDisabled if the repository is disabled.
 * @param languages the programming languages used on
 *                  this repository, {@code null}
 *                  if there's not used languages.
 * @param topics the topics of the repository, {@code null}
 *               if there's no topics.
 * @since 0.0.1
 */
public record GitHubRepositoryModel(
    @NotNull String owner, @NotNull String id, @NotNull String description, @NotNull RepositoryLicense license,
    @NotNull ReleaseModel releaseModel, boolean isForked, boolean canBeForked, int stars, int forks, boolean isPublic,
    boolean isArchived, boolean isDisabled, @Nullable String[] languages, @Nullable String[] topics) {
}
