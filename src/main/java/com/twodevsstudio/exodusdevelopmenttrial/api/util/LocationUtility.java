package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class LocationUtility {

  public String stringifyLocation(Location location) {

    String worldName = location.getWorld().getName();
    double x = location.getX();
    double y = location.getY();
    double z = location.getZ();
    double yaw = location.getYaw();
    double pitch = location.getPitch();

    return worldName + ":" + x + ":" + y + ":" + z + ":" + yaw + ":" + pitch;
  }

  public Location parseLocation(String location) {

    String[] split = location.split(":");

    World world = Bukkit.getWorld(split[0]);
    double x = Double.parseDouble(location.split(":")[1]);
    double y = Double.parseDouble(location.split(":")[2]);
    double z = Double.parseDouble(location.split(":")[3]);
    float pitch = Float.parseFloat(location.split(":")[4]);
    float yaw = Float.parseFloat(location.split(":")[5]);

    return new Location(world, x, y, z, yaw, pitch);
  }
}
