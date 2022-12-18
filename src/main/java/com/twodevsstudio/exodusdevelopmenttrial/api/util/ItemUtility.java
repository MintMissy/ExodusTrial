package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class ItemUtility {

  @NotNull
  public Component getItemName(@Nullable ItemStack itemStack) {

    if (itemStack == null) {
      return Component.text("null");
    }

    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return Component.text(getNameFromMaterial(itemStack.getType()));
    }

    Component displayName = itemMeta.displayName();
    if (displayName == null) {
      return Component.text(getNameFromMaterial(itemStack.getType()));
    }

    return displayName;
  }

  private String getNameFromMaterial(Material material) {

    return material.name().toLowerCase().replace("_", " ");
  }

  @NotNull
  public Map<String, String> getTagsFromItemStack(@Nullable ItemStack itemStack) {

    if (itemStack == null) {
      return Map.of();
    }

    ItemMeta itemMeta = itemStack.getItemMeta();
    if (itemMeta == null) {
      return Map.of();
    }

    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
    Set<NamespacedKey> keys = container.getKeys();

    HashMap<String, String> tags = new HashMap<>();
    for (NamespacedKey key : keys) {
      String value = container.get(key, PersistentDataType.STRING);
      tags.put(key.getKey(), value);
    }

    return tags;
  }
}
