package com.twodevsstudio.exodusdevelopmenttrial.api.listener;

import com.twodevsstudio.exodusdevelopmenttrial.api.inventory.AbstractGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

  @EventHandler
  public void onClick(InventoryClickEvent event) {

    if (!(event.getInventory().getHolder() instanceof AbstractGui abstractGui)) {
      return;
    }

    abstractGui.onClick(event);
  }
}
