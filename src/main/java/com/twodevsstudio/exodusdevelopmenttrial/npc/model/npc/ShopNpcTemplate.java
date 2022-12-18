package com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc;

import com.twodevsstudio.exodusdevelopmenttrial.api.util.LocationUtility;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.player.PlayerSkin;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ShopNpcTemplate extends NpcTemplate {
  @Getter private final UUID shopOwner;
  private String stringifiedLocation;

  public ShopNpcTemplate(String name, UUID shopOwner, Location location) {
    super(name, shopOwner, location);

    this.shopOwner = shopOwner;
    this.stringifiedLocation = LocationUtility.stringifyLocation(location);
  }

  public ShopNpcTemplate(AbstractNpc npc, UUID shopOwner) {
    super(npc);

    this.stringifiedLocation = LocationUtility.stringifyLocation(location);
    this.shopOwner = shopOwner;
  }

  public ShopNpcTemplate(Document document) {
    super(document);
    this.shopOwner = UUID.fromString(document.get("shopOwner").toString());
    this.stringifiedLocation = document.get("stringifiedLocation").toString();
  }

  public ShopNpcTemplate(ShopNpcTemplate template) {
    super(template);

    this.stringifiedLocation = LocationUtility.stringifyLocation(location);
    this.shopOwner = template.shopOwner;
  }

  @Override
  public String getName() {
    return "§e⭐ §b" + super.getName();
  }

  public Location getLocation() {

    if (location == null) {
      location = LocationUtility.parseLocation(stringifiedLocation);
    }

    return location;
  }

  @Override
  public void setLocation(Location location) {

    super.setLocation(location);
    this.stringifiedLocation = LocationUtility.stringifyLocation(location);
  }

  @Override
  public Document getAsDocument(){

    Document document = super.getAsDocument();

    document.put("shopOwner", shopOwner.toString());
    document.put("stringifiedLocation", stringifiedLocation);

    return document;
  }
}
