package com.twodevsstudio.exodusdevelopmenttrial.shop.repository;

import com.mongodb.client.MongoCollection;
import com.twodevsstudio.exodusdevelopmenttrial.shop.database.ShopDatabase;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.BuyableItem;
import com.twodevsstudio.exodusdevelopmenttrial.shop.model.PlayerShop;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
public class ShopsRepository {

  private final ShopDatabase database;

  private final HashMap<UUID, PlayerShop> cachedShops = new HashMap<>();

  @Nullable
  public PlayerShop getShop(UUID ownerUuid) {

    return cachedShops.get(ownerUuid);
  }

  public Collection<PlayerShop> getAllShops() {

    return cachedShops.values();
  }

  public void createShop(PlayerShop playerShop) {

    cachedShops.put(playerShop.getOwner(), playerShop);
    MongoCollection<Document> shopsCollection = database.getShopsCollection();

    shopsCollection.insertOne(playerShop.getAsDocument());
  }

  public void removeShop(UUID ownerUuid) {

    cachedShops.remove(ownerUuid);
    database.getShopsCollection().deleteOne(new Document("owner", ownerUuid.toString()));
  }

  public void relocateShop(PlayerShop playerShop, Location location) {

    playerShop.getNpc().setLocation(location);

    Document filter = new Document("owner", playerShop.getOwner().toString());
    database.getShopsCollection().replaceOne(filter, playerShop.getAsDocument());
  }

  public void loadShop(UUID ownerUuid) {

    Document filter = new Document("owner", ownerUuid.toString());
    Document record = database.getShopsCollection().find(filter).first();

    if (record != null) {
      PlayerShop playerShop = new PlayerShop(record);
      cachedShops.put(ownerUuid, playerShop);
    }
  }

  public void unloadShop(UUID ownerUuid) {

    cachedShops.remove(ownerUuid);
  }

  public void addItemToShop(PlayerShop shop, BuyableItem item) {

    shop.addNewSale(item);
    Document filter = new Document("owner", shop.getOwner().toString());
    database.getShopsCollection().replaceOne(filter, shop.getAsDocument());
  }

  public void removeItemFromShop(PlayerShop shop, BuyableItem item) {

    shop.removeSale(item.getId());
    Document filter = new Document("owner", shop.getOwner().toString());
    database.getShopsCollection().replaceOne(filter, shop.getAsDocument());
  }
}
