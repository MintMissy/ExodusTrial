package com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc;

import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractSpawnedNpc;
import lombok.Getter;

@Getter
public class SpawnedAbstractNpc extends NpcTemplate implements AbstractSpawnedNpc {
  private int entityId;

  public SpawnedAbstractNpc(AbstractNpc npc, int entityId) {
    super(npc);

    this.entityId = entityId;
  }
}
