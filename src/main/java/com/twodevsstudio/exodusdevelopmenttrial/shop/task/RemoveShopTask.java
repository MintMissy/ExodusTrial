package com.twodevsstudio.exodusdevelopmenttrial.shop.task;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.SpawnedShopNpc;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RemoveShopTask extends ShopCommandTask {

  public RemoveShopTask(ExodusDevelopmentTrial plugin, Player player) {
    super(plugin, player);
  }

  @Override
  public void run() {

    UUID uniqueId = player.getUniqueId();

    PlayerShop shop = shopsRepository.getShop(uniqueId);
    if (shop == null) {
      sendResponseMessage(player, messagesConfig.getCannotRemoveShopNoShop());
      return;
    }

    if (shop.getSoldItems().size() > 0) {
      sendResponseMessage(player, messagesConfig.getCannotRemoveShopWithItems());
      return;
    }

    NpcManager npcManager = plugin.getNpcManager();
    SpawnedShopNpc spawnedShopNpc = npcManager.getSpawnedShopNpc(player.getUniqueId());

    shopsRepository.removeShop(shop.getOwner());
    Bukkit.getScheduler().runTask(plugin, () -> {
      npcManager.despawnNpc(spawnedShopNpc, Bukkit.getOnlinePlayers());
    });

    sendResponseMessage(player, messagesConfig.getRemoveShop());
  }
}
