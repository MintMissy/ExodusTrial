package com.twodevsstudio.exodusdevelopmenttrial.npc.manager;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractSpawnedNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.ShopNpcTemplate;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.SpawnedAbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.SpawnedShopNpc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NpcManager {
  // Entity id -> player uuid
  private final Map<Integer, AbstractSpawnedNpc> spawnedNpcs = new HashMap<>();
  private final NpcPacketManager npcPacketManager;
  private final ExodusDevelopmentTrial plugin;

  // TODO async threading

  public NpcManager(ExodusDevelopmentTrial plugin) {
    this.plugin = plugin;
    this.npcPacketManager = new NpcPacketManager(plugin);
  }

  public void spawnExistingNpc(
      AbstractSpawnedNpc npc, Collection<? extends Player> packetReceivers) {

    spawn(npc, packetReceivers);
  }

  public void updateAllExistingNpcs(Collection<? extends Player> packetReceivers) {

    for (AbstractSpawnedNpc npc : spawnedNpcs.values()) {
      npcPacketManager.sendNpcSpawnPacket(npc, packetReceivers);
    }
  }

  public void spawnAllExistingNpcs(Collection<? extends Player> packetReceivers) {

    for (AbstractSpawnedNpc npc : spawnedNpcs.values()) {
      spawnExistingNpc(npc, packetReceivers);
    }
  }

  public SpawnedShopNpc spawnNewShopNpc(
      ShopNpcTemplate npcTemplate, Collection<? extends Player> packetReceivers) {

    UUID shopOwnerId = npcTemplate.getSkinOwner();

    AbstractSpawnedNpc spawnedNpc = spawnNewAbstractNpc(npcTemplate, packetReceivers);
    SpawnedShopNpc spawnedShopNpc = new SpawnedShopNpc(spawnedNpc, shopOwnerId);

    spawnedNpcs.put(spawnedShopNpc.getEntityId(), spawnedShopNpc);

    return spawnedShopNpc;
  }

  public AbstractSpawnedNpc spawnNewAbstractNpc(
      AbstractNpc npc, Collection<? extends Player> packetReceivers) {

    int entityId = (int) (Math.random() * Integer.MAX_VALUE);

    SpawnedAbstractNpc spawnedNpc = new SpawnedAbstractNpc(npc, entityId);
    spawnedNpcs.put(entityId, spawnedNpc);
    spawn(spawnedNpc, packetReceivers);

    return spawnedNpc;
  }

  public void updateNpcPosition(
      AbstractSpawnedNpc spawnedNpc,
      Location newLocation,
      Collection<? extends Player> packetReceivers) {

    spawnedNpc.setLocation(newLocation);
    int entityId = spawnedNpc.getEntityId();

    npcPacketManager.sendEntityTeleportPacket(spawnedNpc.getLocation(), entityId, packetReceivers);
    npcPacketManager.sendHeadRotationPacket(spawnedNpc, entityId, packetReceivers);
  }

  public void despawnNpc(
      AbstractSpawnedNpc spawnedNpc, Collection<? extends Player> packetReceivers) {

    npcPacketManager.sendDespawnEntityPacket(spawnedNpc.getEntityId(), packetReceivers);
    spawnedNpcs.remove(spawnedNpc.getEntityId());
  }

  @Nullable
  public AbstractSpawnedNpc getSpawnedNpc(int entityId) {

    return spawnedNpcs.get(entityId);
  }

  @Nullable
  public SpawnedShopNpc getSpawnedShopNpc(UUID shopOwner) {

    return spawnedNpcs.values().stream()
        .filter(spawnedNpc -> spawnedNpc instanceof SpawnedShopNpc)
        .map(spawnedNpc -> (SpawnedShopNpc) spawnedNpc)
        .filter(spawnedNpc -> spawnedNpc.getShopOwner().equals(shopOwner))
        .findFirst()
        .orElse(null);
  }

  private void spawn(AbstractSpawnedNpc npc, Collection<? extends Player> packetReceivers) {

    int entityId = npc.getEntityId();

    npcPacketManager.sendPlayerInfoPacket(npc, packetReceivers);
    npcPacketManager.sendNpcSpawnPacket(npc, packetReceivers);
    npcPacketManager.sendEntityTeleportPacket(npc.getLocation(), entityId, packetReceivers);
    npcPacketManager.sendHeadRotationPacket(npc, entityId, packetReceivers);

    // Give the time to the client to load the skin and npc
    Bukkit.getScheduler()
        .runTaskLater(
            plugin,
            () ->
                Bukkit.getScheduler()
                    .runTaskAsynchronously(
                        plugin, () -> npcPacketManager.sendTabRemovePacket(npc, packetReceivers)),
            5L);
  }
}
