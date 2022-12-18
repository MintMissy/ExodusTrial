package com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces;

import org.bukkit.Location;

public interface AbstractSpawnedNpc extends AbstractNpc {

  Location getLocation();
  void setLocation(Location location);

  int getEntityId();
}
