package com.twodevsstudio.exodusdevelopmenttrial.shop.listener;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.SpawnedShopNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.task.PlayersShopNpcDespawnTask;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuitListener implements Listener {

  private final ExodusDevelopmentTrial plugin;
  private final ShopsRepository shopsRepository;
  private final NpcManager npcManager;

  public PlayerQuitListener(ExodusDevelopmentTrial plugin) {
    this.plugin = plugin;
    this.shopsRepository = plugin.getShopsRepository();
    this.npcManager = plugin.getNpcManager();
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {

    UUID player = event.getPlayer().getUniqueId();

    Bukkit.getScheduler()
        .runTaskAsynchronously(
            plugin,
            () -> {
              PlayerShop shop = shopsRepository.getShop(player);
              if (shop == null) {
                return;
              }

              shopsRepository.unloadShop(player);

              SpawnedShopNpc spawnedNpc = npcManager.getSpawnedShopNpc(player);
              if (spawnedNpc != null) {
                new PlayersShopNpcDespawnTask(npcManager, spawnedNpc).runTask(plugin);
              }
            });
  }
}
