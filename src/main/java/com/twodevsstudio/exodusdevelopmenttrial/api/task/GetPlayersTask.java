package com.twodevsstudio.exodusdevelopmenttrial.api.task;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class GetPlayersTask extends BukkitRunnable {

  private final ExodusDevelopmentTrial plugin;

  /** This is an async callback that will be called when the players are fetched */
  private final Consumer<Collection<? extends Player>> asyncCallback;

  @Override
  public void run() {

    Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> asyncCallback.accept(onlinePlayers));
  }
}
