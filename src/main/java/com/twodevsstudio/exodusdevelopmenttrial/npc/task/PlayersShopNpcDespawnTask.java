package com.twodevsstudio.exodusdevelopmenttrial.npc.task;

import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractSpawnedNpc;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class PlayersShopNpcDespawnTask extends BukkitRunnable {

  private final NpcManager npcManager;
  private final AbstractSpawnedNpc npcToDespawn;

  @Override
  public void run() {
    npcManager.despawnNpc(npcToDespawn, Bukkit.getOnlinePlayers());
  }
}
