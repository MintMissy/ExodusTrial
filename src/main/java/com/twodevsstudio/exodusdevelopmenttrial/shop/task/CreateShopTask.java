package com.twodevsstudio.exodusdevelopmenttrial.shop.task;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.ShopNpcTemplate;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CreateShopTask extends ShopCommandTask {

  public CreateShopTask(ExodusDevelopmentTrial plugin, Player player) {
    super(plugin, player);
  }

  @Override
  public void run() {

    UUID uniqueId = player.getUniqueId();

    PlayerShop shop = shopsRepository.getShop(uniqueId);
    if (shop != null) {
      sendResponseMessage(player, messagesConfig.getCannotCreateShopHaveShop());
      return;
    }

    ShopNpcTemplate npcTemplate =
        new ShopNpcTemplate(player.getName(), uniqueId, player.getLocation());
    shop = new PlayerShop(uniqueId, npcTemplate);
    shopsRepository.createShop(shop);

    Bukkit.getScheduler()
        .runTask(
            plugin,
            () -> {
              npcManager.spawnNewShopNpc(npcTemplate, Bukkit.getOnlinePlayers());
            });

    sendResponseMessage(player, messagesConfig.getCreateShop());
  }
}
