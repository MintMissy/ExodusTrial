package com.twodevsstudio.exodusdevelopmenttrial.api.model;

import com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder.PlaceholderContainer;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.title.Title;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ConfigurableTitle implements ConfigurationSerializable {
  private String title;
  @Builder.Default private String subTitle = "";
  @Builder.Default private int fadeIn = 500;
  @Builder.Default private int stay = 3000;
  @Builder.Default private int fadeOut = 1000;

  public ConfigurableTitle(Map<String, Object> deserializedMap) {
    this.title = (String) deserializedMap.get("title");
    this.subTitle = (String) deserializedMap.get("subTitle");
    this.fadeIn = (int) deserializedMap.get("fadeIn");
    this.stay = (int) deserializedMap.get("stay");
    this.fadeOut = (int) deserializedMap.get("fadeOut");
  }

  public void sendTo(Player player) {
    player.showTitle(getTitle());
  }

  public Title getTitle() {

    return Title.title(
        TextUtility.colorize(title),
        TextUtility.colorize(subTitle),
        Title.Times.times(
            Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut)));
  }

  @Override
  public @NotNull Map<String, Object> serialize() {

    HashMap<String, Object> serializedMap = new HashMap<>();

    serializedMap.put("title", title);
    serializedMap.put("subTitle", subTitle);
    serializedMap.put("fadeIn", fadeIn);
    serializedMap.put("stay", stay);
    serializedMap.put("fadeOut", fadeOut);

    return serializedMap;
  }

  public void replacePlaceholders(PlaceholderContainer placeholders) {

    title = placeholders.replaceIn(title);
    subTitle = placeholders.replaceIn(subTitle);
  }

  public ConfigurableTitle clone() {

    return ConfigurableTitle.builder()
        .title(title)
        .subTitle(subTitle)
        .fadeIn(fadeIn)
        .stay(stay)
        .fadeOut(fadeOut)
        .build();
  }
}
