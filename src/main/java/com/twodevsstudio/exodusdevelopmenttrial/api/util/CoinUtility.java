package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.Item;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@UtilityClass
public class CoinUtility {

    public ItemStack getCoinItem() {

        return Item.builder()
                .material(Material.GOLD_INGOT)
                .displayName("&e⭐ &eM&ay&9s&bt&5e&cr&6i&eo&au&9s &5C&co&6i&en &e⭐")
                .addLore("")
                .addLore("&7You can use this coin to buy")
                .addLore("&7items from shops")
                .build()
                .toItemStack();
    }

    public boolean hasEnoughCoins(Player player, int requiredCoins){

        return getCoins(player) >= requiredCoins;
    }

    public void removeCoins(Player player, int coinsToRemove){

        ItemStack coinItem = getCoinItem();
        coinItem.setAmount(coinsToRemove);
        player.getInventory().removeItem(coinItem);
    }

    public void addCoins(Player player, int coinsToAdd){

        ItemStack coinItem = getCoinItem();
        coinItem.setAmount(coinsToAdd);
        InventoryUtility.giveOrDropItem(player, coinItem);
    }

    public int getCoins(Player player){

        return InventoryUtility.countItem(player.getInventory(), getCoinItem());
    }
}
