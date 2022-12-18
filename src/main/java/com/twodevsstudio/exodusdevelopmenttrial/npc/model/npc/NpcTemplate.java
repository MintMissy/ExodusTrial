package com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc;

import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.player.PlayerSkin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Data
@AllArgsConstructor
public class NpcTemplate implements AbstractNpc {
  protected String name;
  protected UUID skinOwner;
  @Nullable protected PlayerSkin skin;
  protected UUID uuid = UUID.randomUUID();
  @Setter protected Location location;

  public NpcTemplate(String name, UUID skinOwner, Location location) {
    this.name = name;
    this.skinOwner = skinOwner;
    this.location = location;
  }

  public NpcTemplate(AbstractNpc npc) {
    this.name = npc.getName();
    this.skinOwner = npc.getSkinOwner();
    this.skin = npc.getSkin();
    this.uuid = npc.getUuid();
    this.location = npc.getLocation();
  }

  public NpcTemplate(Document document) {
    this.name = document.get("name").toString();
    this.skinOwner = UUID.fromString(document.get("skinOwner").toString());
    this.skin =
        document.get("skin") != null ? new PlayerSkin((Document) document.get("skin")) : null;
  }

  public Document getAsDocument() {

    Document document = new Document();

    document.put("name", name);
    document.put("skinOwner", skinOwner.toString());
    if (skin != null) {
      document.put("skin", skin.getAsDocument());
    }

    return document;
  }
}
