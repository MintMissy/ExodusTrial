package com.twodevsstudio.exodusdevelopmenttrial.npc.manager;

import com.comphenix.protocol.events.PacketContainer;
import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.PacketManager;
import com.twodevsstudio.exodusdevelopmenttrial.api.task.SendPacketTask;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractSpawnedNpc;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

public class NpcPacketManager extends PacketManager {
  private final NpcSkinManager npcSkinManager;


  public NpcPacketManager(ExodusDevelopmentTrial plugin) {
    super(plugin);

    this.npcSkinManager = new NpcSkinManager();
  }

  public void sendHeadRotationPacket(
      AbstractNpc npc, int entityId, Collection<? extends Player> players) {

    PacketContainer packet = packetFactory.createHeadRotationPacket(npc, entityId);
    new SendPacketTask(protocolManager, packet, players).runTaskAsynchronously(plugin);
  }

  public void sendNpcSpawnPacket(AbstractSpawnedNpc npc, Collection<? extends Player> players) {

    PacketContainer packet = packetFactory.createNpcSpawnPacket(npc, npc.getEntityId());
    new SendPacketTask(protocolManager, packet, players).runTaskAsynchronously(plugin);
  }

  public void sendTabRemovePacket(AbstractNpc npc, Collection<? extends Player> players) {

    if (npc.getSkin() == null) {
      npc.setSkin(npcSkinManager.getNpcSkin(npc));
    }

    PacketContainer packet = packetFactory.createTabRemovePacket(npc, npc.getSkin());
    new SendPacketTask(protocolManager, packet, players).runTaskAsynchronously(plugin);
  }

  public void sendPlayerInfoPacket(AbstractNpc npc, Collection<? extends Player> players) {

    if (npc.getSkin() == null) {
      npc.setSkin(npcSkinManager.getNpcSkin(npc));
    }

    PacketContainer packet = packetFactory.createPlayerInfoPacket(npc, npc.getSkin());
    new SendPacketTask(protocolManager, packet, players).runTaskAsynchronously(plugin);
  }
}
