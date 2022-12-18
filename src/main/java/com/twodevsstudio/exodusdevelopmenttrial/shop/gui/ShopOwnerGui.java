package com.twodevsstudio.exodusdevelopmenttrial.shop.gui;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import net.kyori.adventure.text.Component;

import java.util.List;

public class ShopOwnerGui extends ShopGui {

  public ShopOwnerGui(ExodusDevelopmentTrial plugin, PlayerShop playerShop) {
    super(plugin, playerShop);
  }

  @Override
  protected List<Component> getBuyableItemFooter(BuyableItem buyableItem) {

    List<Component> footer = super.getBuyableItemFooter(buyableItem);
    footer.addAll(TextUtility.colorize(guiConfig.getSoldItemOwnerFooter()));

    return footer;
  }
}
