package com.twodevsstudio.exodusdevelopmenttrial.api.model.item;

import com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder.Placeholder;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ConfigUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class Item implements ConfigurationSerializable {
  protected String displayName;
  protected Material material;

  @Singular("addLore")
  protected List<String> lore;

  @Singular("addTag")
  protected Map<String, String> tags;

  public Item(Map<String, Object> deserializedMap) {

    this.displayName = (String) deserializedMap.get("displayName");
    this.material = Material.valueOf((String) deserializedMap.get("material"));
    this.lore = ConfigUtility.getList(deserializedMap, "lore", (Object o) -> (String) o);
    this.tags = ConfigUtility.getMap(deserializedMap, "tags", (Object o) -> (String) o);
  }

  public ItemStack toItemStack() {

    ItemStack itemStack = new ItemStack(material);

    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.displayName(TextUtility.colorize(displayName));
    itemMeta.lore(TextUtility.colorize(lore));

    PersistentDataContainer container = itemMeta.getPersistentDataContainer();
    for (Map.Entry<String, String> entry : tags.entrySet()) {
      NamespacedKey namespacedKey = new NamespacedKey("exodus", entry.getKey());
      container.set(namespacedKey, PersistentDataType.STRING, entry.getValue());
    }

    itemStack.setItemMeta(itemMeta);

    return itemStack;
  }

  public void replacePlaceholdersInLore(List<Placeholder> placeholders) {

    lore =
        lore.stream()
            .map(
                loreLine -> {
                  for (Placeholder placeholder : placeholders) {
                    loreLine = loreLine.replace(placeholder.getKey(), placeholder.getReplacement());
                  }
                  return loreLine;
                })
            .collect(Collectors.toList());
  }

  @Override
  public @NotNull Map<String, Object> serialize() {

    HashMap<String, Object> serializedMap = new HashMap<>();

    serializedMap.put("displayName", displayName);
    serializedMap.put("material", material.toString());
    serializedMap.put("lore", lore);
    serializedMap.put("tags", tags);

    return serializedMap;
  }

  public Item clone() {

    return new Item(displayName, material, lore, tags);
  }
}
