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

  public int countItem(Inventory inventory, ItemStack itemStack){

    int foundItems = 0;
    for (ItemStack item : inventory.getContents()) {
      if (item == null) {
        continue;
      }

        if (item.isSimilar(itemStack)) {
            foundItems += item.getAmount();
        }
    }

    return foundItems;
  }
}
