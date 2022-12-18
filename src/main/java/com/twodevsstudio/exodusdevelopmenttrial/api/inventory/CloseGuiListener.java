package com.twodevsstudio.exodusdevelopmenttrial.api.inventory;

import com.twodevsstudio.exodusdevelopmenttrial.api.listener.InventoryCloseListener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface CloseGuiListener {
    void onClose(InventoryCloseEvent event);
}
