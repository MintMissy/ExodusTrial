package com.twodevsstudio.exodusdevelopmenttrial.npc.manager;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.PacketUtility;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractSpawnedNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.player.PlayerSkin;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class NpcPacketManager {
  private final ProtocolManager protocolManager;
  private final NpcSkinManager npcSkinManager;

  public NpcPacketManager(ExodusDevelopmentTrial plugin) {
    this.protocolManager = plugin.getProtocolManager();
    this.npcSkinManager = new NpcSkinManager();
  }

  // TODO cleanup - move to packet utils
  public void sendDespawnNpcPackets(int entityId, Collection<? extends Player> players) {

    PacketContainer packet = getDespawnNpcPacket(entityId);

    try {
      for (Player player : players) {
        protocolManager.sendServerPacket(player, packet);
      }
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private PacketContainer getDespawnNpcPacket(int entityId) {

    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);

    packet.getModifier().writeDefaults();
    packet.getModifier().write(0, new IntArrayList(new int[] {entityId}));

    return packet;
  }

  public void sendHeadRotationPacket(
      AbstractNpc npc, int entityId, Collection<? extends Player> players) {

    PacketContainer packet = getHeadRotationPacket(npc, entityId);

    try {
      for (Player player : players) {
        protocolManager.sendServerPacket(player, packet);
      }
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private PacketContainer getHeadRotationPacket(AbstractNpc npc, int entityId) {

    PacketContainer packet =
        protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);

    packet.getIntegers().write(0, entityId);
    packet.getBytes().write(0, PacketUtility.convertAngleToByte(npc.getLocation().getYaw()));

    return packet;
  }

  public void sendNpcSpawnPacket(AbstractSpawnedNpc npc, Collection<? extends Player> players) {

    PacketContainer packet = getNpcSpawnPacket(npc, npc.getEntityId());

    try {
      for (Player player : players) {
        protocolManager.sendServerPacket(player, packet);
      }
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public void sendEntityTeleportPacket(
      Location location, int entityId, Collection<? extends Player> players) {

    PacketContainer packet = getEntityTeleportPacket(location, entityId);

    try {
      for (Player player : players) {
        protocolManager.sendServerPacket(player, packet);
      }
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private PacketContainer getEntityTeleportPacket(Location location, int entityId) {

    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_TELEPORT);
    packet.getIntegers().write(0, entityId);

    packet
        .getDoubles()
        .write(0, location.getX())
        .write(1, location.getY())
        .write(2, location.getZ());
    packet
        .getBytes()
        .write(0, PacketUtility.convertAngleToByte(location.getYaw()))
        .write(1, PacketUtility.convertAngleToByte(location.getPitch()));

    return packet;
  }

  private PacketContainer getNpcSpawnPacket(AbstractNpc npc, int entityId) {

    Location location = npc.getLocation();

    PacketContainer packet =
        protocolManager.createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
    packet.getIntegers().write(0, entityId);
    packet.getSpecificModifier(UUID.class).write(0, npc.getUuid());
    packet
        .getDoubles()
        .write(0, location.getX())
        .write(1, location.getY())
        .write(2, location.getZ());
    packet
        .getBytes()
        .write(0, PacketUtility.convertAngleToByte(location.getPitch()))
        .write(1, PacketUtility.convertAngleToByte(location.getYaw()));

    return packet;
  }

  public void sendTabRemovePacket(AbstractNpc npc, Collection<? extends Player> players) {

    if (npc.getSkin() == null) {
      npc.setSkin(npcSkinManager.getNpcSkin(npc));
    }

    PacketContainer packet = getTabRemovePacket(npc, npc.getSkin());

    try {
      for (Player player : players) {
        protocolManager.sendServerPacket(player, packet);
      }
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private PacketContainer getTabRemovePacket(AbstractNpc npc, @NotNull PlayerSkin skin) {

    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);

    packet.getPlayerInfoAction().writeDefaults();
    packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);

    PlayerInfoData playerInfoData = getWrappedGameProfile(npc, skin);
    packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

    return packet;
  }

  public void sendPlayerInfoPacket(AbstractNpc npc, Collection<? extends Player> players) {

    if (npc.getSkin() == null) {
      npc.setSkin(npcSkinManager.getNpcSkin(npc));
    }

    PacketContainer packet = getPlayerInfoPacket(npc, npc.getSkin());

    try {
      for (Player player : players) {
        protocolManager.sendServerPacket(player, packet);
      }
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  /** Info about packet: https://wiki.vg/Protocol#Player_Info */
  private PacketContainer getPlayerInfoPacket(AbstractNpc npc, @NotNull PlayerSkin skin) {

    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);

    packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

    PlayerInfoData playerInfoData = getWrappedGameProfile(npc, skin);
    packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

    return packet;
  }

  @NotNull
  private static PlayerInfoData getWrappedGameProfile(AbstractNpc npc, @NotNull PlayerSkin skin) {
    // Create player profile
    WrappedGameProfile profile = new WrappedGameProfile(npc.getUuid(), npc.getName());

    // Set skin
    WrappedSignedProperty skinProperty =
        new WrappedSignedProperty("textures", skin.getValue(), skin.getSignature());
    profile.getProperties().removeAll("textures");
    profile.getProperties().put("textures", skinProperty);

    // Create player info data
    WrappedChatComponent displayName = WrappedChatComponent.fromText(npc.getName());

    PlayerInfoData playerInfoData =
        new PlayerInfoData(profile, 0, EnumWrappers.NativeGameMode.CREATIVE, displayName);
    return playerInfoData;
  }
}
