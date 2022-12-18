package com.twodevsstudio.exodusdevelopmenttrial.shop.task;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.task.GetPlayersTask;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.ShopNpcTemplate;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import org.bukkit.entity.Player;

import java.util.Collection;
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

    new GetPlayersTask(
            plugin,
            (Collection<? extends Player> onlinePlayers) ->
                npcManager.spawnNewShopNpc(npcTemplate, onlinePlayers))
        .runTask(plugin);

    sendResponseMessage(player, messagesConfig.getCreateShop());
  }
}
