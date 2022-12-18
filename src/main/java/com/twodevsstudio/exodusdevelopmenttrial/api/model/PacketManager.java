package com.twodevsstudio.exodusdevelopmenttrial.api.model;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.factory.PacketFactory;
import com.twodevsstudio.exodusdevelopmenttrial.api.task.SendPacketTask;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collection;

@RequiredArgsConstructor
public abstract class PacketManager{

    protected final ProtocolManager protocolManager;
    protected final ExodusDevelopmentTrial plugin;
    protected final PacketFactory packetFactory;

    public PacketManager(ExodusDevelopmentTrial plugin) {
        this.plugin = plugin;
        this.protocolManager = plugin.getProtocolManager();
        this.packetFactory = new PacketFactory(protocolManager);
    }

    public void sendDespawnEntityPacket(int entityId, Collection<? extends Player> players) {

        PacketContainer packet = packetFactory.createDespawnEntityPacket(entityId);
        new SendPacketTask(protocolManager, packet, players).runTaskAsynchronously(plugin);
    }

    public void sendEntityTeleportPacket(
            Location location, int entityId, Collection<? extends Player> players) {

        PacketContainer packet = packetFactory.createEntityTeleportPacket(location, entityId);
        new SendPacketTask(protocolManager, packet, players).runTaskAsynchronously(plugin);
    }
}
