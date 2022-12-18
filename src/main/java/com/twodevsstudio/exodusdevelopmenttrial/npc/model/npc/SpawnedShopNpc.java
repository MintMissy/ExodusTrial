package com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc;

import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractSpawnedNpc;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SpawnedShopNpc extends ShopNpcTemplate implements AbstractSpawnedNpc {

  private int entityId;

  public SpawnedShopNpc(ShopNpcTemplate template) {
    super(template);
  }

  public SpawnedShopNpc(AbstractSpawnedNpc spawnedNpc, UUID shopOwner) {
    super(spawnedNpc, shopOwner);
    this.entityId = spawnedNpc.getEntityId();
  }
}
