package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.Item;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@UtilityClass
public class InventoryUtility {

  public void giveOrDropItem(Player player, ItemStack item) {

    PlayerInventory inventory = player.getInventory();
    if (inventory.firstEmpty() == -1) {
      player.getWorld().dropItem(player.getLocation(), item);
    } else {
      inventory.addItem(item);
    }
  }

  public void fillInventory(Inventory inventory, ItemStack fillItem) {

    fillInventory(inventory, fillItem, 0, inventory.getSize());
  }

  public void fillInventory(Inventory inventory, ItemStack fillItem, int start, int end) {

    if (end > inventory.getSize()) {
      throw new RuntimeException("End index is bigger than inventory size");
    }

    for (int i = start; i < end; i++) {
      inventory.setItem(i, fillItem);
    }
  }

  public ItemStack getFillItem() {

    return Item.builder()
        .material(Material.BLACK_STAINED_GLASS_PANE)
        .displayName("")
        .build()
        .toItemStack();
  }

  public ItemStack getCoinItem() {

    return Item.builder()
        .material(Material.GOLD_INGOT)
        .displayName("&e⭐ &eM&ay&9s&bt&5e&cr&6i&eo&au&9s &5C&co&6i&en &e⭐")
        .addLore("")
        .addLore("&7You can use this coin to buy")
        .addLore("&7items from shops")
        .build()
        .toItemStack();
  }
}
