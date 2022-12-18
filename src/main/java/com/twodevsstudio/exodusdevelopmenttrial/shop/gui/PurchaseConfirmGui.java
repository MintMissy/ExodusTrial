package com.twodevsstudio.exodusdevelopmenttrial.shop.gui;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.inventory.AbstractGui;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.GuiItem;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.CoinUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.InventoryUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ItemUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.shop.event.BuyItemEvent;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class PurchaseConfirmGui extends AbstractGui {

  private final ShopsRepository shopsRepository;
  private final PlayerShop playerShop;
  private final BuyableItem buyableItem;

  public PurchaseConfirmGui(ExodusDevelopmentTrial plugin, PlayerShop playerShop, BuyableItem item, Player viewer) {
    super(plugin, viewer);

    this.shopsRepository = plugin.getShopsRepository();
    this.playerShop = playerShop;
    this.buyableItem = item;
  }

  @Override
  public void onClick(InventoryClickEvent event) {

    event.setCancelled(true);

    Map<String, String> itemTags = ItemUtility.getTagsFromItemStack(event.getCurrentItem());
    if (itemTags.containsKey("confirm_purchase")) {
      confirmPurchase((Player) event.getWhoClicked());
    } else if (itemTags.containsKey("cancel_purchase")) {
      event.getWhoClicked().closeInventory();
    }
  }

  private void confirmPurchase(Player buyer) {

    if (!CoinUtility.hasEnoughCoins(viewer, buyableItem.getPrice())) {
      viewer.sendMessage(TextUtility.colorize(messagesConfig.getNotEnoughCoinsShop()));
      return;
    }

    BuyItemEvent buyItemEvent = new BuyItemEvent(playerShop, buyableItem, buyer);
    Bukkit.getPluginManager().callEvent(buyItemEvent);

    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
      shopsRepository.removeItemFromShop(playerShop, buyableItem);
    });

    CoinUtility.removeCoins(buyer, buyableItem.getPrice());
    buyer.getInventory().addItem(buyableItem.getItemStack());

    Player seller = Bukkit.getPlayer(playerShop.getOwner());
    CoinUtility.addCoins(seller, buyableItem.getPrice());
  }

  @Override
  public void update() {

    InventoryUtility.fillInventory(inventory, InventoryUtility.getFillItem());
    super.update();
    inventory.setItem(guiConfig.getPurchasedItemPreviewSlot(), buyableItem.getItemStack());
  }

  @Override
  public int getGuiSize() {

    return guiConfig.getConfirmPurchaseGui().getSize();
  }

  @Override
  public Component getGuiTitle() {

    return TextUtility.colorize(guiConfig.getConfirmPurchaseGui().getTitle());
  }

  @Override
  protected Map<String, GuiItem> getLayout() {

    return guiConfig.getConfirmPurchaseGui().getLayout();
  }
}
