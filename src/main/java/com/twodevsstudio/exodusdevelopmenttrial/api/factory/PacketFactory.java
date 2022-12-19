package com.twodevsstudio.exodusdevelopmenttrial.api.factory;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.twodevsstudio.exodusdevelopmenttrial.api.interfaces.Factory;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.PacketUtility;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.player.PlayerSkin;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
public class PacketFactory implements Factory {

  private final ProtocolManager protocolManager;

  public PacketContainer createEntityTeleportPacket(Location location, int entityId) {

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

  public PacketContainer createDespawnEntityPacket(int entityId) {

    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);

    packet.getModifier().writeDefaults();
    packet.getModifier().write(0, new IntArrayList(new int[] {entityId}));

    return packet;
  }

  public PacketContainer createHeadRotationPacket(AbstractNpc npc, int entityId) {

    PacketContainer packet =
        protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION);

    packet.getIntegers().write(0, entityId);
    packet.getBytes().write(0, PacketUtility.convertAngleToByte(npc.getLocation().getYaw()));

    return packet;
  }

  public PacketContainer createNpcSpawnPacket(AbstractNpc npc, int entityId) {

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

  public PacketContainer createTabRemovePacket(AbstractNpc npc, @NotNull PlayerSkin skin) {

    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);

    packet.getPlayerInfoAction().writeDefaults();
    packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);

    PlayerInfoData playerInfoData = createWrappedGameProfile(npc, skin);
    packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

    return packet;
  }

  /** Info about packet: https://wiki.vg/Protocol#Player_Info */
  public PacketContainer createPlayerInfoPacket(AbstractNpc npc, @NotNull PlayerSkin skin) {

    PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);

    packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

    PlayerInfoData playerInfoData = createWrappedGameProfile(npc, skin);
    packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

    return packet;
  }

  @NotNull
  private PlayerInfoData createWrappedGameProfile(AbstractNpc npc, @NotNull PlayerSkin skin) {
    // Create player profile
    WrappedGameProfile profile = new WrappedGameProfile(npc.getUuid(), npc.getName());

    // Set skin
    WrappedSignedProperty skinProperty =
        new WrappedSignedProperty("textures", skin.getValue(), skin.getSignature());
    profile.getProperties().removeAll("textures");
    profile.getProperties().put("textures", skinProperty);

    // Create player info data
    WrappedChatComponent displayName = WrappedChatComponent.fromText(npc.getName());

    return new PlayerInfoData(profile, 0, EnumWrappers.NativeGameMode.CREATIVE, displayName);
  }
}
