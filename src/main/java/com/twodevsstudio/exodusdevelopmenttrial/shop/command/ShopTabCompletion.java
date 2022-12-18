package com.twodevsstudio.exodusdevelopmenttrial.shop.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopTabCompletion implements TabCompleter {

  @Override
  public @Nullable List<String> onTabComplete(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {

    ArrayList<String> completions = new ArrayList<>();

    if (args.length != 1) {
      return Collections.emptyList();
    }

    if (sender.hasPermission("exodusdevelopmenttrial.shop.create")) {
      completions.add("create");
    }
    if (sender.hasPermission("exodusdevelopmenttrial.shop.remove")) {
      completions.add("remove");
    }
    if (sender.hasPermission("exodusdevelopmenttrial.shop.relocate")) {
      completions.add("relocate");
    }

    return completions;
  }
}
