package com.twodevsstudio.exodusdevelopmenttrial.shop.task;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.ShopNpcTemplate;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.SpawnedShopNpc;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RelocateShopTask extends ShopCommandTask {

  private final Location newLocation;

  public RelocateShopTask(ExodusDevelopmentTrial plugin, Player player) {
    super(plugin, player);
    this.newLocation = player.getLocation();
  }

  @Override
  public void run() {

    UUID uniqueId = player.getUniqueId();

    PlayerShop shop = shopsRepository.getShop(uniqueId);
    if (shop == null) {
      sendResponseMessage(player, messagesConfig.getCannotRelocateShopNoShop());
      return;
    }

    ShopNpcTemplate npcTemplate = shop.getNpc();
    shopsRepository.relocateShop(shop, newLocation);
    SpawnedShopNpc spawnedShopNpc = npcManager
            .getSpawnedShopNpc(npcTemplate.getShopOwner());

    Bukkit.getScheduler()
        .runTask(
            plugin,
            () -> {
              npcManager.updateNpcPosition(spawnedShopNpc, newLocation, Bukkit.getOnlinePlayers());
            });

    sendResponseMessage(player, messagesConfig.getRelocateShop());
  }
}
