package com.twodevsstudio.exodusdevelopmenttrial.shop.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.twodevsstudio.exodusdevelopmenttrial.config.DatabaseConfig;
import lombok.Getter;
import org.bson.Document;

public class ShopDatabase {
  @Getter private MongoClient mongoClient;
  @Getter private MongoDatabase shopsDatabase;
  @Getter private MongoCollection<Document> shopsCollection;

  public void initialize(DatabaseConfig config) {

    mongoClient = MongoClients.create(config.getUri());
    shopsDatabase = mongoClient.getDatabase(config.getShopsDatabaseName());
    shopsCollection = shopsDatabase.getCollection(config.getShopsCollectionName());
  }
}
