package com.twodevsstudio.exodusdevelopmenttrial.shop.task;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.SyncResponseTask;
import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import org.bukkit.entity.Player;

public abstract class ShopCommandTask extends SyncResponseTask {

  protected final ShopsRepository shopsRepository;
  protected final NpcManager npcManager;
  protected final Player player;

  public ShopCommandTask(ExodusDevelopmentTrial plugin, Player player) {
    super(plugin);
    this.shopsRepository = plugin.getShopsRepository();
    this.npcManager = plugin.getNpcManager();
    this.player = player;
  }
}
