package com.twodevsstudio.exodusdevelopmenttrial.api.task;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class SendPacketTask extends BukkitRunnable {

    private final ProtocolManager protocolManager;
    private final PacketContainer packet;
    private final Collection<? extends Player> packetReceivers;

    @Override
    public void run() {

        try {
            for (Player player : packetReceivers) {
                protocolManager.sendServerPacket(player, packet);
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
