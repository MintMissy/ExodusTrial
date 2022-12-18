package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@UtilityClass
public class Base64Utility {

  @Nullable
  public String convertItemToBase64(ItemStack item) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      BukkitObjectOutputStream bukkitOutputStream = new BukkitObjectOutputStream(outputStream);

      bukkitOutputStream.writeObject(item);

      return Base64Coder.encodeLines(outputStream.toByteArray());

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Nullable
  public ItemStack convertFromBase64ToItem(String base64) {
    try {
      ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(base64));
      BukkitObjectInputStream bukkitInputStream = new BukkitObjectInputStream(inputStream);

      return (ItemStack) bukkitInputStream.readObject();

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
