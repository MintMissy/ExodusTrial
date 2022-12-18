package com.twodevsstudio.exodusdevelopmenttrial.api.model;

import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.GuiItem;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ConfigUtility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GuiConfiguration implements ConfigurationSerializable {
  private String title;
  private int size;
  private Map<String, GuiItem> layout;

  public GuiConfiguration(Map<String, Object> deserializedMap) {

    this.title = (String) deserializedMap.get("title");
    this.size = (int) deserializedMap.get("size");
    this.layout =
        ConfigUtility.getMap(
            deserializedMap,
            "layout",
            (Object o) -> new GuiItem(((ConfigurationSection) o).getValues(false)));
  }

  @Override
  public @NotNull Map<String, Object> serialize() {

    HashMap<String, Object> serializedMap = new HashMap<>();

    serializedMap.put("title", title);
    serializedMap.put("size", size);
    serializedMap.put("layout", layout);

    return serializedMap;
  }
}
