package com.twodevsstudio.exodusdevelopmenttrial.shop.command;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.CoinUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.InventoryUtility;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.config.MessagesConfig;
import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import com.twodevsstudio.exodusdevelopmenttrial.shop.task.CreateShopTask;
import com.twodevsstudio.exodusdevelopmenttrial.shop.task.RelocateShopTask;
import com.twodevsstudio.exodusdevelopmenttrial.shop.task.RemoveShopTask;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {

  private final ExodusDevelopmentTrial plugin;
  private final ShopsRepository shopsRepository;
  private final MessagesConfig messagesConfig;
  private final NpcManager npcManager;

  public ShopCommand(ExodusDevelopmentTrial plugin) {
    this.plugin = plugin;
    this.shopsRepository = plugin.getShopsRepository();
    this.messagesConfig = plugin.getConfigManager().getMessagesConfig();
    this.npcManager = plugin.getNpcManager();
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {

    if (!(sender instanceof Player player)) {
      sender.sendMessage(TextUtility.colorize(messagesConfig.getOnlyPlayerCommand()));
      return true;
    }

    if (args.length != 1) {
      onHelp(player);
      return true;
    }

    switch (args[0]) {
      case "create" -> onCreateShop(player);
      case "remove" -> onRemoveShop(player);
      case "relocate" -> onRelocateShop(player);
      case "coin" -> onCoin(player);
      default -> onHelp(player);
    }

    return true;
  }

  private void onCreateShop(Player player) {

    if (!player.hasPermission("exodusdevelopmenttrial.shop.create")) {
      player.sendMessage(TextUtility.colorize(messagesConfig.getNoPermission()));
      return;
    }

    new CreateShopTask(plugin, player).runTaskAsynchronously(plugin);
  }

  private void onRelocateShop(Player player) {

    if (!player.hasPermission("exodusdevelopmenttrial.shop.relocate")) {
      player.sendMessage(TextUtility.colorize(messagesConfig.getNoPermission()));
      return;
    }

    new RelocateShopTask(plugin, player).runTaskAsynchronously(plugin);
  }

  private void onCoin(Player player) {

    if (!player.hasPermission("exodusdevelopmenttrial.shop.coin")) {
      player.sendMessage(TextUtility.colorize(messagesConfig.getNoPermission()));
      return;
    }

    InventoryUtility.giveOrDropItem(player, CoinUtility.getCoinItem());
  }

  private void onRemoveShop(Player player) {

    if (!player.hasPermission("exodusdevelopmenttrial.shop.remove")) {
      player.sendMessage(TextUtility.colorize(messagesConfig.getNoPermission()));
      return;
    }

    new RemoveShopTask(plugin, player).runTaskAsynchronously(plugin);
  }

  private void onHelp(Player player) {

    if (!player.hasPermission("exodusdevelopmenttrial.shop.help")) {
      player.sendMessage(TextUtility.colorize(messagesConfig.getNoPermission()));
      return;
    }

    for (Component component : TextUtility.colorize(messagesConfig.getShopHelp())) {
      player.sendMessage(component);
    }
  }
}
