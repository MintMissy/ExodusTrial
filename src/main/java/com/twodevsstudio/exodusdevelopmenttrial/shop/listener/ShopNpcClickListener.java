package com.twodevsstudio.exodusdevelopmenttrial.shop.listener;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.event.NpcClickEvent;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractSpawnedNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.SpawnedShopNpc;
import com.twodevsstudio.exodusdevelopmenttrial.shop.gui.ShopGui;
import com.twodevsstudio.exodusdevelopmenttrial.shop.gui.ShopOwnerGui;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class ShopNpcClickListener implements Listener {

  private final ExodusDevelopmentTrial plugin;
  private final ShopsRepository shopsRepository;

  public ShopNpcClickListener(ExodusDevelopmentTrial plugin) {
    this.plugin = plugin;
    this.shopsRepository = plugin.getShopsRepository();
  }

  @EventHandler
  public void onShopNpcClick(NpcClickEvent event) {

    AbstractSpawnedNpc npc = event.getNpc();
    if (!(npc instanceof SpawnedShopNpc spawnedShopNpc)) {
      return;
    }

    PlayerShop shop = shopsRepository.getShop(spawnedShopNpc.getShopOwner());
    if (shop == null) {
      return;
    }

    List<BuyableItem> itemsInShop = shop.getSoldItems();

    Player player = event.getClicker();
    if (player.getUniqueId().equals(shop.getOwner())) {
      new ShopOwnerGui(plugin, shop, player).open(player);
    } else {
      new ShopGui(plugin, shop, player).open(player);
    }
  }
}
