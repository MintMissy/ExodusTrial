package com.twodevsstudio.exodusdevelopmenttrial.api.config;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.interfaces.Loadable;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class Config implements Loadable {

  private final ExodusDevelopmentTrial plugin;
  private final File file;
  protected String fileName;

  public Config(ExodusDevelopmentTrial plugin, String fileName) {
    this.plugin = plugin;
    this.fileName = fileName;
    this.file = new File(plugin.getDataFolder(), fileName);
  }

  protected abstract void deserializeYamlConfiguration(YamlConfiguration configuration);

  @Override
  public void load() {

    if (!file.exists()) {
      create();
    }

    YamlConfiguration yamlConfiguration = new YamlConfiguration();
    try {
      yamlConfiguration.load(file);
    } catch (IOException | InvalidConfigurationException e) {
      throw new RuntimeException(e);
    }

    deserializeYamlConfiguration(yamlConfiguration);
  }

  private void create() {

    file.getParentFile().mkdirs();
    plugin.saveResource(fileName, false);
  }
}
