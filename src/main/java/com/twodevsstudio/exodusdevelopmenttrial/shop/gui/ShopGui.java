package com.twodevsstudio.exodusdevelopmenttrial.shop.gui;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.inventory.PageableAbstractGui;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.GuiItem;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder.Placeholder;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.InventoryUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShopGui extends PageableAbstractGui {

  private final PlayerShop playerShop;
  private List<BuyableItem> buyableItemsOnPage;

  public ShopGui(ExodusDevelopmentTrial plugin, PlayerShop playerShop) {
    super(plugin);

    this.playerShop = playerShop;
  }

  @Override
  public void update() {

    InventoryUtility.fillInventory(
        inventory, InventoryUtility.getFillItem(), inventory.getSize() - 9, inventory.getSize());

    super.update();

    updateBuyableItemsData();
    setBuyableItems();
  }

  @Override
  public void onClick(InventoryClickEvent event) {

    super.onClick(event);
    event.setCancelled(true);

    int clickedSlot = event.getRawSlot();
    if (isBuyableAreaSlot(clickedSlot) && clickedSlot < buyableItemsOnPage.size()) {
      onBuyableItemClick(event);
      return;
    }

    GuiItem clickedItem = getClickedLayoutItem(event.getRawSlot());
    if (clickedItem == null) {
      return;
    }

    if (clickedItem.getTags().containsKey("add_item")) {
      new PriceConfigurationGui(plugin).open((Player) event.getWhoClicked());
    }
  }

  protected void onBuyableItemClick(InventoryClickEvent event) {

    // TODO additional owner check
    if (event.isShiftClick()) {
      // TODO cancel item sale

      return;
    }

    // TODO open purchase item inventory
    return;
  }

  private void setBuyableItems() {

    for (int i = 0; i < buyableItemsOnPage.size(); i++) {

      BuyableItem buyableItem = buyableItemsOnPage.get(i);
      ItemStack itemstack = buyableItem.getItemStack();

      inventory.setItem(i, formatBuyableItem(itemstack, buyableItem));
    }
  }

  private ItemStack formatBuyableItem(ItemStack item, BuyableItem buyableItem) {

    ItemMeta itemMeta = item.getItemMeta();

    List<Component> lore =
        itemMeta.lore() == null ? new ArrayList<>() : new ArrayList<>(itemMeta.lore());

    lore.addAll(getBuyableItemFooter(buyableItem));
    itemMeta.lore(lore);

    item.setItemMeta(itemMeta);

    return item;
  }

  protected List<Component> getBuyableItemFooter(BuyableItem buyableItem) {

    List<String> soldItemFooter =
        new Placeholder("{price}", String.valueOf(buyableItem.getPrice()))
            .replaceIn(guiConfig.getSoldItemFooter());

    return TextUtility.colorize(soldItemFooter);
  }

  protected void updateBuyableItemsData() {
    int buyableAreaSize = inventory.getSize() - 9;
    int minIndex = (currentPage - 1) * buyableAreaSize;
    int maxIndex = currentPage * buyableAreaSize;

    List<BuyableItem> soldItems = playerShop.getSoldItems();
    buyableItemsOnPage = soldItems.subList(minIndex, Math.min(maxIndex, soldItems.size()));
  }

  protected boolean isBuyableAreaSlot(int slot) {

    return slot >= 0 && slot < inventory.getSize() - 9;
  }

  @Override
  public int getMaximumPageNumber() {

    return 1;
  }

  @Override
  public int getGuiSize() {

    return guiConfig.getShopGui().getSize();
  }

  @Override
  public Component getGuiTitle() {

    String shopOwnerName = Bukkit.getOfflinePlayer(playerShop.getOwner()).getName();
    return TextUtility.colorize(
        new Placeholder("{player}", shopOwnerName).replaceIn(guiConfig.getShopGui().getTitle()));
  }

  @Override
  protected Map<String, GuiItem> getLayout() {

    return guiConfig.getShopGui().getLayout();
  }
}
