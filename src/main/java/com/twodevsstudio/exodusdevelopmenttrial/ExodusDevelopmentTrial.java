package com.twodevsstudio.exodusdevelopmenttrial;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.twodevsstudio.exodusdevelopmenttrial.api.listener.InventoryClickListener;
import com.twodevsstudio.exodusdevelopmenttrial.api.listener.InventoryCloseListener;
import com.twodevsstudio.exodusdevelopmenttrial.config.ConfigManager;
import com.twodevsstudio.exodusdevelopmenttrial.config.MessagesConfig;
import com.twodevsstudio.exodusdevelopmenttrial.npc.listener.NpcClickListener;
import com.twodevsstudio.exodusdevelopmenttrial.npc.manager.NpcManager;
import com.twodevsstudio.exodusdevelopmenttrial.shop.command.ShopCommand;
import com.twodevsstudio.exodusdevelopmenttrial.shop.command.ShopTabCompletion;
import com.twodevsstudio.exodusdevelopmenttrial.shop.database.ShopDatabase;
import com.twodevsstudio.exodusdevelopmenttrial.shop.listener.BuyItemListener;
import com.twodevsstudio.exodusdevelopmenttrial.shop.listener.PlayerJoinListener;
import com.twodevsstudio.exodusdevelopmenttrial.shop.listener.PlayerQuitListener;
import com.twodevsstudio.exodusdevelopmenttrial.shop.listener.ShopNpcClickListener;
import com.twodevsstudio.exodusdevelopmenttrial.shop.repository.ShopsRepository;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class ExodusDevelopmentTrial extends JavaPlugin {

  private ProtocolManager protocolManager;
  private ConfigManager configManager;

  private NpcManager npcManager;
  private ShopDatabase shopDatabase;
  private ShopsRepository shopsRepository;

  @Override
  public void onEnable() {

    configManager = new ConfigManager(this);
    protocolManager = ProtocolLibrary.getProtocolManager();
    npcManager = new NpcManager(this);

    initializeDatabases();
    registerListeners();
    registerCommands();

    registerPacketListeners(protocolManager);
  }

  @Override
  public void onDisable() {

    Bukkit.getScheduler().cancelTasks(this);
  }

  private void registerListeners() {

    PluginManager pluginManager = getServer().getPluginManager();
    MessagesConfig messagesConfig = configManager.getMessagesConfig();

    pluginManager.registerEvents(new InventoryClickListener(), this);
    pluginManager.registerEvents(new InventoryCloseListener(), this);
    pluginManager.registerEvents(new BuyItemListener(messagesConfig), this);
    pluginManager.registerEvents(new ShopNpcClickListener(this), this);
    pluginManager.registerEvents(new PlayerJoinListener(this), this);
    pluginManager.registerEvents(new PlayerQuitListener(this), this);
  }

  private void registerPacketListeners(ProtocolManager protocolManager) {

    protocolManager.addPacketListener(new NpcClickListener(this));
  }

  private void registerCommands() {

    getCommand("shop").setExecutor(new ShopCommand(this));
    getCommand("shop").setTabCompleter(new ShopTabCompletion());
  }

  private void initializeDatabases() {

    shopDatabase = new ShopDatabase();
    shopDatabase.initialize(configManager.getDatabaseConfig());

    shopsRepository = new ShopsRepository(shopDatabase);
  }
}
