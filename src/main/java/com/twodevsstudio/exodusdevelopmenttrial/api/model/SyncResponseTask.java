package com.twodevsstudio.exodusdevelopmenttrial.api.model;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import com.twodevsstudio.exodusdevelopmenttrial.config.MessagesConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class SyncResponseTask extends BukkitRunnable {

  protected final ExodusDevelopmentTrial plugin;
  protected final MessagesConfig messagesConfig;

  public SyncResponseTask(ExodusDevelopmentTrial plugin) {

    this.plugin = plugin;
    this.messagesConfig = plugin.getConfigManager().getMessagesConfig();
  }

  protected void sendResponseMessage(Player player, String message) {

    Bukkit.getScheduler().runTask(plugin, () -> player.sendMessage(TextUtility.colorize(message)));
  }
}
