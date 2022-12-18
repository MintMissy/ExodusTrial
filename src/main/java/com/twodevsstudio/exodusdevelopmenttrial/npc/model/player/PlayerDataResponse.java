package com.twodevsstudio.exodusdevelopmenttrial.npc.model.player;

import com.twodevsstudio.exodusdevelopmenttrial.api.util.TextUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Text;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PlayerDataResponse {
  private UUID uuid;
  private String name;
  private PlayerProperty skinProperty;

  public PlayerDataResponse(JSONObject jsonObject) {
    UUID id = TextUtility.parseTrimmedUuid(jsonObject.get("id").toString());
    this.uuid = id;
    this.name = jsonObject.get("name").toString();

    JSONArray properties = (JSONArray) jsonObject.get("properties");
    JSONObject property = (JSONObject) properties.get(0);

    this.skinProperty =
        new PlayerProperty(
            property.get("name").toString(),
            property.get("value").toString(),
            property.get("signature").toString());
  }

  public PlayerSkin getPlayerSkin() {

    return skinProperty == null
        ? null
        : new PlayerSkin(skinProperty.getValue(), skinProperty.getSignature());
  }
}
