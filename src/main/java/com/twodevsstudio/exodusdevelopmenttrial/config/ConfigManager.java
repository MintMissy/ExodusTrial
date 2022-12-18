package com.twodevsstudio.exodusdevelopmenttrial.config;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.config.Config;
import com.twodevsstudio.exodusdevelopmenttrial.api.enums.ConfigType;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
  private final Map<ConfigType, Config> configs;

  public ConfigManager(ExodusDevelopmentTrial plugin) {
    configs = new HashMap<>();
    configs.put(ConfigType.DATABASE, new DatabaseConfig(plugin));
    configs.put(ConfigType.GUI, new GuiConfig(plugin));
    configs.put(ConfigType.MESSAGES, new MessagesConfig(plugin));

    reloadAll();
  }

  public void reloadAll() {
    configs.forEach((configType, config) -> config.load());
  }

  public Config getConfig(ConfigType configType) {
    return configs.get(configType);
  }

  public DatabaseConfig getDatabaseConfig() {
    return (DatabaseConfig) configs.get(ConfigType.DATABASE);
  }

  public MessagesConfig getMessagesConfig() {
    return (MessagesConfig) configs.get(ConfigType.MESSAGES);
  }

  public GuiConfig getGuiConfig() {
    return (GuiConfig) configs.get(ConfigType.GUI);
  }
}
