package com.twodevsstudio.exodusdevelopmenttrial.shop.event;

import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public class BuyItemEvent extends Event {
  private static final HandlerList HANDLERS_LIST = new HandlerList();
  private final BuyableItem boughtItem;
  private final Player buyer;

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS_LIST;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS_LIST;
  }
}
