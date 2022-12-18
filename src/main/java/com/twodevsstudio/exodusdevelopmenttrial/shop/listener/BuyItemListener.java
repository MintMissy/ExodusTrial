package com.twodevsstudio.exodusdevelopmenttrial.shop.listener;

import com.twodevsstudio.exodusdevelopmenttrial.api.model.ConfigurableTitle;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder.Placeholder;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder.PlaceholderContainer;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ItemUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.config.MessagesConfig;
import com.twodevsstudio.exodusdevelopmenttrial.shop.event.BuyItemEvent;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

@AllArgsConstructor
public class BuyItemListener implements Listener {

  private final MessagesConfig messagesConfig;

  @EventHandler
  public void onBuyItem(BuyItemEvent event) {

    BuyableItem boughtItem = event.getBoughtItem();

    Player sellerPlayer = boughtItem.getSellerPlayer().getPlayer();
    if (sellerPlayer != null) {
      sendSellerMessages(event.getBuyer(), sellerPlayer, boughtItem);
    }

    sendBuyerMessages(event.getBuyer(), boughtItem);
  }

  private void sendBuyerMessages(Player buyer, BuyableItem boughtItem) {

    String itemName =
        TextUtility.getComponentContent(ItemUtility.getItemName(boughtItem.getItemStack()));

    List<Placeholder> placeholdersList =
        List.of(
            new Placeholder("{itemName}", itemName),
            new Placeholder("{price}", String.valueOf(boughtItem.getPrice())));
    PlaceholderContainer placeholders = new PlaceholderContainer(placeholdersList);

    String buyItemMessage = placeholders.replaceIn(messagesConfig.getBuyItemMessage());
    buyer.sendMessage(TextUtility.colorize(buyItemMessage));

    ConfigurableTitle buyItemTitle = messagesConfig.getBuyItemTitle().clone();
    buyItemTitle.replacePlaceholders(placeholders);
    buyItemTitle.sendTo(buyer);
  }

  private void sendSellerMessages(Player buyer, Player seller, BuyableItem boughtItem) {

    String itemName =
        TextUtility.getComponentContent(ItemUtility.getItemName(boughtItem.getItemStack()));

    List<Placeholder> placeholdersList =
        List.of(
            new Placeholder("{playerName}", buyer.getName()),
            new Placeholder("{itemName}", itemName),
            new Placeholder("{price}", String.valueOf(boughtItem.getPrice())));
    PlaceholderContainer placeholders = new PlaceholderContainer(placeholdersList);

    String sellItemMessage = placeholders.replaceIn(messagesConfig.getSellItemMessage());
    seller.sendMessage(TextUtility.colorize(sellItemMessage));

    ConfigurableTitle sellItemTitle = messagesConfig.getSellItemTitle().clone();
    sellItemTitle.replacePlaceholders(placeholders);
    sellItemTitle.sendTo(seller);
  }
}
