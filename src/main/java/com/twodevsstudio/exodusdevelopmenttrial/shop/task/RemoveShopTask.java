package com.twodevsstudio.exodusdevelopmenttrial.shop.task;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.task.GetPlayersTask;
import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.SpawnedShopNpc;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import org.bukkit.entity.Player;

import java.util.Collection;
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
    new GetPlayersTask(
            plugin,
            (Collection<? extends Player> players) ->
                npcManager.despawnNpc(spawnedShopNpc, players))
        .runTask(plugin);

    sendResponseMessage(player, messagesConfig.getRemoveShop());
  }
}
