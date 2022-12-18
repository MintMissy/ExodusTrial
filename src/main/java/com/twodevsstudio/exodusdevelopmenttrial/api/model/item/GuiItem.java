package com.twodevsstudio.exodusdevelopmenttrial.api.model.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GuiItem extends Item {

  private int slot;

  @Builder(builderMethodName = "guiItemBuilder")
  public GuiItem(
      String displayName,
      Material material,
      List<String> lore,
      Map<String, String> tags,
      int slot) {

    super(displayName, material, lore, tags);

    this.slot = slot;
  }

  public GuiItem(Map<String, Object> deserializedMap) {

    super(deserializedMap);

    this.slot = (int) deserializedMap.get("slot");
  }

  @Override
  public @NotNull Map<String, Object> serialize() {

    Map<String, Object> serializedMap = super.serialize();
    serializedMap.put("slot", slot);

    return serializedMap;
  }

  public GuiItem clone() {

    return GuiItem.guiItemBuilder()
        .displayName(displayName)
        .material(material)
        .lore(lore)
        .tags(tags)
        .slot(slot)
        .build();
  }
}
