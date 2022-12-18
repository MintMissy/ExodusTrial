package com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces;

import com.twodevsstudio.exodusdevelopmenttrial.npc.model.player.PlayerSkin;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface AbstractNpc {
  String getName();

  UUID getSkinOwner();

  @Nullable
  PlayerSkin getSkin();

  void setSkin(PlayerSkin skin);

  UUID getUuid();

  Location getLocation();
}
