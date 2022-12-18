package com.twodevsstudio.exodusdevelopmenttrial.npc.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.event.NpcClickEvent;
import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractSpawnedNpc;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NpcClickListener extends PacketAdapter {

  private final NpcManager npcManager;
  private final Map<UUID, Long> lastClick = new HashMap<>();


  public NpcClickListener(ExodusDevelopmentTrial plugin) {
    super(plugin, PacketType.Play.Client.USE_ENTITY);
    this.npcManager = plugin.getNpcManager();
  }

  @Override
  public void onPacketReceiving(PacketEvent event) {

    PacketContainer packet = event.getPacket();
    if (event.getPacketType() != PacketType.Play.Client.USE_ENTITY) {
      return;
    }

    Player player = event.getPlayer();

    long now = System.currentTimeMillis();
    Long lastNpcClick = lastClick.getOrDefault(player.getUniqueId(), 0L);

    // Click limit - max 1 click per 500ms
    if (now - lastNpcClick < 500) {
      return;
    }
    lastClick.put(player.getUniqueId(), now);

    Integer entityId = packet.getIntegers().read(0);
    AbstractSpawnedNpc spawnedNpc = npcManager.getSpawnedNpc(entityId);

    if (spawnedNpc == null) {
      return;
    }

    NpcClickEvent npcClickEvent = new NpcClickEvent(spawnedNpc, player);

    Bukkit.getScheduler()
        .runTask(
            plugin,
            () -> {
              Bukkit.getPluginManager().callEvent(npcClickEvent);
            });
  }
}
