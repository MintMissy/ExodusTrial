package com.twodevsstudio.exodusdevelopmenttrial.api.interfaces;

import org.bukkit.entity.Player;

public interface Pageable {
  void openNextPage(Player player);

  void openPreviousPage(Player player);

  int getMaximumPageNumber();
}
