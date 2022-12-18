package com.twodevsstudio.exodusdevelopmenttrial.config;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.config.Config;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

@Getter
public class DatabaseConfig extends Config {

  private String host;
  private int port;
  private String user;
  private String password;

  private String configuratedUri;

  private String shopsDatabaseName;
  private String shopsCollectionName;

  public DatabaseConfig(ExodusDevelopmentTrial plugin) {
    super(plugin, "database.yml");
  }

  @Override
  protected void deserializeYamlConfiguration(YamlConfiguration configuration) {
    host = configuration.getString("host");
    port = configuration.getInt("port");
    user = configuration.getString("user");
    password = configuration.getString("password");

    configuratedUri = configuration.getString("uri");

    shopsDatabaseName = configuration.getString("shops-database-name");
    shopsCollectionName = configuration.getString("shops-collection-name");
  }

  public String getUri() {
    if (configuratedUri != null && !configuratedUri.isEmpty()) {
      return configuratedUri;
    }

    return "mongodb://"
        + user
        + ":"
        + password
        + "@"
        + host
        + ":"
        + port
        + "/?retryWrites=true&w=majority";
  }
}
