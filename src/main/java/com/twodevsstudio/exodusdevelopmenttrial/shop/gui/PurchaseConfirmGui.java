package com.twodevsstudio.exodusdevelopmenttrial.shop.gui;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.inventory.AbstractGui;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.GuiItem;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ItemUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;

public class PurchaseConfirmGui extends AbstractGui {

  private final ShopsRepository shopsRepository;
  private final PlayerShop playerShop;
  private final BuyableItem buyableItem;

  public PurchaseConfirmGui(ExodusDevelopmentTrial plugin, PlayerShop playerShop, BuyableItem item) {
    super(plugin);

    this.shopsRepository = plugin.getShopsRepository();
    this.playerShop = playerShop;
    this.buyableItem = item;
  }

  @Override
  public void onClick(InventoryClickEvent event) {

    event.setCancelled(true);

    Map<String, String> itemTags = ItemUtility.getTagsFromItemStack(event.getCurrentItem());
    if (itemTags.containsKey("CONFIRM_PURCHASE")) {
      confirmPurchase();
    } else if (itemTags.containsKey("CANCEL_PURCHASE")) {
      event.getWhoClicked().closeInventory();
    }
  }

  private void confirmPurchase() {
    // TODO buy item
  }

  @Override
  public void update() {

    super.update();
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
