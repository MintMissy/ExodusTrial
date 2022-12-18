package com.twodevsstudio.exodusdevelopmenttrial.shop.gui;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.GuiItem;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.InventoryUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopOwnerGui extends ShopGui {

  public ShopOwnerGui(ExodusDevelopmentTrial plugin, PlayerShop playerShop, Player viewer) {
    super(plugin, playerShop, viewer);
  }

    @Override
    public void update() {

        super.update();

        GuiItem addNewItemBtn = layout.get("add-item");
        inventory.setItem(addNewItemBtn.getSlot(), addNewItemBtn.toItemStack());
    }

    @Override
  protected void onBuyableItemClick(BuyableItem item, boolean isShiftClick) {

    InventoryUtility.giveOrDropItem(viewer, item.getItemStack());

    Bukkit.getScheduler()
        .runTaskAsynchronously(
            plugin,
            () -> {
              shopsRepository.removeItemFromShop(playerShop, item);
              Bukkit.getScheduler().runTask(plugin, this::update);
            });
  }

  @Override
  protected List<Component> getBuyableItemFooter(BuyableItem buyableItem) {

    return TextUtility.colorize(
        replacePlaceholdersInItemLore(guiConfig.getSoldItemOwnerFooter(), buyableItem));
  }
}
