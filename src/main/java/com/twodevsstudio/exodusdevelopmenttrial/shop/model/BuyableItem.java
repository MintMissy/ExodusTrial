package com.twodevsstudio.exodusdevelopmenttrial.shop.model;

import com.twodevsstudio.exodusdevelopmenttrial.api.util.Base64Utility;
import lombok.Getter;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
public class BuyableItem {
  private ObjectId id;

  private UUID seller;

  private ItemStack itemStack;
  private String itemBase64;

  private double price;

  public BuyableItem(@NotNull ItemStack itemStack, @NotNull UUID seller, double price) {
    this.id = new ObjectId();
    this.itemStack = itemStack;
    this.seller = seller;
    this.price = price;
    this.itemBase64 = Base64Utility.convertItemToBase64(itemStack);
  }

  public BuyableItem(Document document) {
    this.id = new ObjectId(document.get("_id").toString());
    this.seller = UUID.fromString(document.get("seller").toString());
    this.itemBase64 = document.get("itemBase64").toString();
    this.price = Double.parseDouble(document.get("price").toString());
  }

  public OfflinePlayer getSellerPlayer() {

    return Bukkit.getOfflinePlayer(getSeller());
  }

  @Nullable
  public ItemStack getItemStack() {

    if (itemStack == null) {
      itemStack = Base64Utility.convertFromBase64ToItem(itemBase64);
    }

    return itemStack;
  }

  public Document getAsDocument() {

    Document document = new Document();

    document.append("_id", id.toString());
    document.append("seller", seller.toString());
    document.append("itemBase64", itemBase64);
    document.append("price", price);

    return document;
  }
}
