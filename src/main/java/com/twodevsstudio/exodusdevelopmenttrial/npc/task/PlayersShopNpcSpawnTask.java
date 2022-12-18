package com.twodevsstudio.exodusdevelopmenttrial.npc.task;

import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.ShopNpcTemplate;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class PlayersShopNpcSpawnTask extends BukkitRunnable {

  private final NpcManager npcManager;
  private final ShopNpcTemplate npcToSpawn;

  @Override
  public void run() {

    npcManager.spawnNewShopNpc(npcToSpawn, Bukkit.getOnlinePlayers());
  }
}
