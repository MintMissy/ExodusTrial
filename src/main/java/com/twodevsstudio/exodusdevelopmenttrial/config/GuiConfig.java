package com.twodevsstudio.exodusdevelopmenttrial.config;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.config.Config;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.GuiConfiguration;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ConfigUtility;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

@Getter
public class GuiConfig extends Config {

  public GuiConfig(ExodusDevelopmentTrial plugin) {
    super(plugin, "gui.yml");
  }

  private int purchasedItemPreviewSlot;
  private GuiConfiguration confirmPurchaseGui;

  private GuiConfiguration shopGui;
  private List<String> soldItemFooter;
  private List<String> soldItemOwnerFooter;

  private GuiConfiguration priceConfigurationGui;

  @Override
  protected void deserializeYamlConfiguration(YamlConfiguration configuration) {
    purchasedItemPreviewSlot = configuration.getInt("purchased-item-preview-slot");
    confirmPurchaseGui =
        new GuiConfiguration(ConfigUtility.getSection(configuration, "confirm-purchase-gui"));
    shopGui = new GuiConfiguration(ConfigUtility.getSection(configuration, "shop-gui"));
    priceConfigurationGui =
        new GuiConfiguration(ConfigUtility.getSection(configuration, "price-configuration-gui"));
    soldItemFooter = configuration.getStringList("shop-item-footer");
    soldItemOwnerFooter = configuration.getStringList("shop-item-owner-footer");
  }
}
