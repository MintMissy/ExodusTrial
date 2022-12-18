package com.twodevsstudio.exodusdevelopmenttrial.shop.model;

import com.twodevsstudio.exodusdevelopmenttrial.npc.model.npc.ShopNpcTemplate;
import lombok.Data;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PlayerShop {

  private ObjectId id = new ObjectId();

  private final UUID owner;

  private final ShopNpcTemplate npc;
  private List<BuyableItem> soldItems = new ArrayList<>();

  public PlayerShop(UUID owner, ShopNpcTemplate npc) {
    this.owner = owner;
    this.npc = npc;
  }

  public PlayerShop(Document document) {
    this.id = new ObjectId(document.get("id").toString());
    this.owner = UUID.fromString(document.get("owner").toString());
    this.npc = new ShopNpcTemplate((Document) document.get("npc"));

    this.soldItems = ((List<Document>) document.get("soldItems")).stream().map(BuyableItem::new).toList();
    this.soldItems = new ArrayList<>(this.soldItems);
  }

  public void buyItem(ObjectId itemId, Player buyer) {

    soldItems.removeIf(soldItem -> soldItem.getId().equals(itemId));
  }

  public void removeSale(ObjectId itemId) {

    soldItems.removeIf(soldItem -> soldItem.getId().equals(itemId));
  }

  public void addNewSale(BuyableItem soldItem) {

    soldItems.add(soldItem);
  }

  public Document getAsDocument() {

    Document document = new Document("id", id);

    document.append("owner", owner.toString());
    document.append("npc", npc.getAsDocument());
    document.append("soldItems", soldItems.stream().map(BuyableItem::getAsDocument).toList());

    return document;
  }
}
