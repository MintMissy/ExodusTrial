package com.twodevsstudio.exodusdevelopmenttrial.config;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.config.Config;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.ConfigurableTitle;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ConfigUtility;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

@Getter
public class MessagesConfig extends Config {

  private String onlyPlayerCommand;
  private String noPermission;

  private String createShop;
  private String cannotCreateShopHaveShop;
  private String relocateShop;
  private String cannotRelocateShopNoShop;
  private String removeShop;
  private String cannotRemoveShopWithItems;
  private String cannotRemoveShopNoShop;
  private String notEnoughCoinsShop;
  private List<String> shopHelp;

  private String buyItemMessage;
  private ConfigurableTitle buyItemTitle;

  private String sellItemMessage;
  private ConfigurableTitle sellItemTitle;

  public MessagesConfig(ExodusDevelopmentTrial plugin) {
    super(plugin, "messages.yml");
  }

  @Override
  protected void deserializeYamlConfiguration(YamlConfiguration configuration) {

    onlyPlayerCommand = configuration.getString("generic.only-player-command");
    noPermission = configuration.getString("generic.no-permission");

    createShop = configuration.getString("shop.create");
    cannotCreateShopHaveShop = configuration.getString("shop.cannot-create-have-shop");
    relocateShop = configuration.getString("shop.relocate");
    cannotRelocateShopNoShop = configuration.getString("shop.cannot-relocate-no-shop");
    removeShop = configuration.getString("shop.remove");
    cannotRemoveShopWithItems = configuration.getString("shop.cannot-remove-with-items");
    cannotRemoveShopNoShop = configuration.getString("shop.cannot-remove-no-shop");
    notEnoughCoinsShop = configuration.getString("not-enough-coins");
    shopHelp = configuration.getStringList("shop.help");

    buyItemMessage = configuration.getString("buy-item.message");
    buyItemTitle = new ConfigurableTitle(ConfigUtility.getSection(configuration, "buy-item.title"));

    sellItemMessage = configuration.getString("sell-item.message");
    sellItemTitle =
        new ConfigurableTitle(ConfigUtility.getSection(configuration, "sell-item.title"));
  }
}
