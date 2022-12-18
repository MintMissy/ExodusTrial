package com.twodevsstudio.exodusdevelopmenttrial.api.inventory;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.GuiItem;
import com.twodevsstudio.exodusdevelopmenttrial.config.GuiConfig;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public abstract class AbstractGui implements InventoryHolder {

  protected Player viewer;
  protected Inventory inventory;
  protected Map<String, GuiItem> layout;
  protected GuiConfig guiConfig;
  protected ExodusDevelopmentTrial plugin;

  public AbstractGui(ExodusDevelopmentTrial plugin) {
    this.plugin = plugin;
    this.guiConfig = plugin.getConfigManager().getGuiConfig();
    this.layout = getLayout();
  }

  @NotNull
  public Inventory getInventory() {

    if (inventory == null) {
      inventory = Bukkit.createInventory(this, getGuiSize(), getGuiTitle());
    }

    return inventory;
  }

  public abstract int getGuiSize();

  public abstract Component getGuiTitle();

  protected abstract Map<String, GuiItem> getLayout();

  public abstract void onClick(InventoryClickEvent event);

  public void update() {

    for (GuiItem item : layout.values()) {
      if (item.getSlot() >= inventory.getSize()) {
        throw new RuntimeException(
            "Slot "
                + item.getSlot()
                + " item "
                + item.getDisplayName()
                + " is out of bounds for inventory. Please, fix configuration issue");
      }
      if (item.getTags().containsKey("next_page") || item.getTags().containsKey("previous_page")) {
        continue;
      }

      inventory.setItem(item.getSlot(), item.toItemStack());
    }
  }

  @Nullable
  protected GuiItem getClickedLayoutItem(int clickedSlot) {
    return layout.values().stream()
        .filter(item -> item.getSlot() == clickedSlot)
        .findFirst()
        .orElse(null);
  }

  public void open(Player player) {

    viewer = player;

    Inventory receivedInventory = getInventory();
    update();
    player.openInventory(receivedInventory);
  }
}
