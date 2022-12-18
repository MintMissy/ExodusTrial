package com.twodevsstudio.exodusdevelopmenttrial.api.listener;

import com.twodevsstudio.exodusdevelopmenttrial.api.inventory.CloseGuiListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {

  @EventHandler
  public void onClose(InventoryCloseEvent event) {

    if (!(event.getInventory().getHolder() instanceof CloseGuiListener closeGuiListener)) {
      return;
    }

    closeGuiListener.onClose(event);
  }
}
