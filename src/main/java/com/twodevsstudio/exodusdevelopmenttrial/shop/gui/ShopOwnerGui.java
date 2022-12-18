package com.twodevsstudio.exodusdevelopmenttrial.shop.gui;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.InventoryUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class ShopOwnerGui extends ShopGui {

  public ShopOwnerGui(ExodusDevelopmentTrial plugin, PlayerShop playerShop, Player viewer) {
    super(plugin, playerShop, viewer);
  }

  @Override
  protected void onBuyableItemClick(BuyableItem item, boolean isShiftClick) {

    if (isShiftClick && viewer.getUniqueId().equals(playerShop.getOwner())) {
      InventoryUtility.giveOrDropItem(viewer, item.getItemStack());
      shopsRepository.removeItemFromShop(playerShop, item);
      update();
      return;
    }

    super.onBuyableItemClick(item, isShiftClick);
  }

  @Override
  protected List<Component> getBuyableItemFooter(BuyableItem buyableItem) {

    List<Component> footer = super.getBuyableItemFooter(buyableItem);
    footer.addAll(TextUtility.colorize(guiConfig.getSoldItemOwnerFooter()));

    return footer;
  }
}
