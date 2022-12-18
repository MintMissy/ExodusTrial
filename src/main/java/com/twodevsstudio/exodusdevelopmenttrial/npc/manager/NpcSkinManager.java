package com.twodevsstudio.exodusdevelopmenttrial.npc.manager;

import com.twodevsstudio.exodusdevelopmenttrial.api.util.HttpUtility;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.interfaces.AbstractNpc;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.player.PlayerDataResponse;
import com.twodevsstudio.exodusdevelopmenttrial.npc.model.player.PlayerSkin;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.UUID;

public class NpcSkinManager {

  private final HashMap<UUID, PlayerSkin> cachedSkins = new HashMap<>();

  public PlayerSkin getNpcSkin(AbstractNpc npc) {
    UUID skinOwner = npc.getSkinOwner();

    if (!cachedSkins.containsKey(skinOwner)) {
      cachedSkins.put(skinOwner, fetchPlayerSkin(skinOwner));
    }

    return cachedSkins.get(skinOwner);
  }

  /**
   * API INFO: https://wiki.vg/Mojang_API#UUID_to_Profile_and_Skin.2FCape EXAMPLE API USAGE:
   * https://sessionserver.mojang.com/session/minecraft/profile/72009ad70edd4378a23ed57fb592320d
   */
  private PlayerSkin fetchPlayerSkin(UUID playerUuid) {
    String endpoint =
        "https://sessionserver.mojang.com/session/minecraft/profile/"
            + playerUuid.toString()
            + "?unsigned=false";

    PlayerDataResponse playerData;
    try {
      JSONObject jsonObject = HttpUtility.sendJsonGetRequest(endpoint);
      playerData = new PlayerDataResponse(jsonObject);

    } catch (IOException | URISyntaxException | InterruptedException | ParseException e) {
      throw new RuntimeException(e);
    }

    return playerData.getPlayerSkin();
  }
}
