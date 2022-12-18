package com.twodevsstudio.exodusdevelopmenttrial.shop.listener;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.npc.task.PlayersShopNpcSpawnTask;
import com.twodevsstudio.exodusdevelopmenttrial.npc.task.SpawnAllExistingNpcsTask;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

  private final ExodusDevelopmentTrial plugin;
  private final ShopsRepository shopsRepository;
  private final NpcManager npcManager;

  public PlayerJoinListener(ExodusDevelopmentTrial plugin) {
    this.plugin = plugin;
    this.shopsRepository = plugin.getShopsRepository();
    this.npcManager = plugin.getNpcManager();
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {

    Player player = event.getPlayer();
    UUID playerUuid = player.getUniqueId();

    Bukkit.getScheduler()
        .runTaskAsynchronously(
            plugin,
            () -> {
              shopsRepository.loadShop(playerUuid);
              PlayerShop shop = shopsRepository.getShop(playerUuid);

              if (shop == null) {
                return;
              }

              // TODO fix shop npc on join - no skin & head
              new SpawnAllExistingNpcsTask(npcManager, player).runTask(plugin);
              new PlayersShopNpcSpawnTask(npcManager, shop.getNpc()).runTask(plugin);
            });
  }
}