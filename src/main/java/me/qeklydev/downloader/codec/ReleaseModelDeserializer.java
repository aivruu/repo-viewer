package me.qeklydev.downloader.codec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import me.qeklydev.downloader.release.ReleaseModel;
import org.jetbrains.annotations.NotNull;

/**
 * Json deserializer implementation for handle provided
 * json data files.
 *
 * @since 0.0.1
 */
public enum ReleaseModelDeserializer implements JsonDeserializer<ReleaseModel> {
  INSTANCE;

  /**
   * Gson instance that uses this custom adapter for
   * perform deserialization to upcoming json data files.
   *
   * @since 0.0.1
   */
  public static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(ReleaseModel.class, INSTANCE)
      .create();

  @Override
  public @NotNull ReleaseModel deserialize(final @NotNull JsonElement jsonElement, final @NotNull Type type,
                                           final @NotNull JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
    final var providedJsonObject = jsonElement.getAsJsonObject();
    return new ReleaseModel(providedJsonObject.getAsString());
  }
}
