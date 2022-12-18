package com.twodevsstudio.exodusdevelopmenttrial.npc.model.player;

import lombok.Data;
import org.bson.Document;

@Data
public class PlayerSkin {
  private final String value;
  private final String signature;

  public PlayerSkin(String value, String signature) {
    this.value = value;
    this.signature = signature;
  }

  public PlayerSkin(Document skin) {
    this.value = skin.get("value").toString();
    this.signature = skin.get("signature").toString();
  }

  public Document getAsDocument() {

    Document document = new Document();

    document.put("value", value);
    document.put("signature", signature);

    return document;
  }
}
