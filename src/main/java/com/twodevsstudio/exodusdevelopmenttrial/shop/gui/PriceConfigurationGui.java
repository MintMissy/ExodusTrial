package com.twodevsstudio.exodusdevelopmenttrial.shop.gui;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.inventory.AbstractGui;
import com.twodevsstudio.exodusdevelopmenttrial.api.inventory.CloseGuiListener;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.GuiItem;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder.Placeholder;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.InventoryUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ItemUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class PriceConfigurationGui extends AbstractGui implements CloseGuiListener {

  private final ShopsRepository shopRepository;
  private ItemStack selectedItem;
  private int price = 5;

  public PriceConfigurationGui(ExodusDevelopmentTrial plugin, Player viewer) {
    super(plugin, viewer);
    shopRepository = plugin.getShopsRepository();
  }

  @Override
  public void onClick(InventoryClickEvent event) {

    event.setCancelled(true);

    int clickedSlot = event.getRawSlot();
    ItemStack clickedItem = event.getCurrentItem();

    Map<String, String> itemTags = ItemUtility.getTagsFromItemStack(clickedItem);
    if (clickedSlot >= event.getView().getTopInventory().getSize()) {
      handleItemSwap(clickedItem, event.getView().getBottomInventory(), event.getSlot());
      update();
      return;
    }

    if (clickedSlot == layout.get("dummy-item").getSlot()) {
      InventoryUtility.giveOrDropItem(viewer, selectedItem);
      selectedItem = null;
      update();
      return;
    }

    if (itemTags.containsKey("confirm_sell") && selectedItem != null) {
      listItemOnSale();
      viewer.closeInventory();
      return;
    }

    handlePriceEditButtons(itemTags);
    update();
  }

  @Override
  public void onClose(InventoryCloseEvent event) {

    if (selectedItem != null) {
      InventoryUtility.giveOrDropItem(viewer, selectedItem);
    }
  }

  private void handleItemSwap(ItemStack clickedItem, Inventory bottomInventory, int clickedSlot) {

    if (clickedItem == null) {
      return;
    }

    if (selectedItem != null) {
      InventoryUtility.giveOrDropItem(viewer, selectedItem);
    }

    selectedItem = clickedItem.clone();
    inventory.setItem(layout.get("dummy-item").getSlot(), selectedItem);
    bottomInventory.setItem(clickedSlot, null);
  }

  private void handlePriceEditButtons(Map<String, String> itemTags) {

    String priceChangeKey = getPriceChangeKey(itemTags);
    if (priceChangeKey == null) {
      return;
    }

    int priceChange = Integer.parseInt(itemTags.get(priceChangeKey));

    if (priceChange != 0) {
      price = Math.max(price + priceChange, 0);
    }
  }

  @Nullable
  private String getPriceChangeKey(Map<String, String> keys) {

    for (String key : keys.keySet()) {
      if (key.startsWith("price_change")) {
        return key;
      }
    }

    return null;
  }

  private void listItemOnSale() {

    PlayerShop shop = shopRepository.getShop(viewer.getUniqueId());
    if (shop == null) {
      return;
    }

    BuyableItem buyableItem = new BuyableItem(selectedItem.clone(), viewer.getUniqueId(), price);
    shopRepository.addItemToShop(shop, buyableItem);

    selectedItem = null;
  }

  @Override
  public void update() {

    InventoryUtility.fillInventory(inventory, InventoryUtility.getFillItem());
    super.update();

    inventory.setItem(
        13, selectedItem == null ? layout.get("dummy-item").toItemStack() : selectedItem);

    GuiItem confirmSellItem = layout.get("confirm-sell").clone();
    List<String> confirmSellLore =
        new Placeholder("{price}", String.valueOf(price)).replaceIn(confirmSellItem.getLore());
    confirmSellItem.setLore(confirmSellLore);
    inventory.setItem(confirmSellItem.getSlot(), confirmSellItem.toItemStack());
  }

  @Override
  public int getGuiSize() {
    return guiConfig.getPriceConfigurationGui().getSize();
  }

  @Override
  public Component getGuiTitle() {
    return TextUtility.colorize(guiConfig.getPriceConfigurationGui().getTitle());
  }

  @Override
  protected Map<String, GuiItem> getLayout() {
    return guiConfig.getPriceConfigurationGui().getLayout();
  }
}
