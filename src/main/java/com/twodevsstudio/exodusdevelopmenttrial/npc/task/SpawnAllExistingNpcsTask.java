package com.twodevsstudio.exodusdevelopmenttrial.npc.task;

import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;

@RequiredArgsConstructor
public class SpawnAllExistingNpcsTask extends BukkitRunnable {

  private final NpcManager npcManager;
  private final Player packetReceiver;

  @Override
  public void run() {

    npcManager.spawnAllExistingNpcs(Collections.singletonList(packetReceiver));
  }
}
