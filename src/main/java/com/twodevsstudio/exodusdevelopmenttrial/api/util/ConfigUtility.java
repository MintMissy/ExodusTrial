package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class ConfigUtility {
  public Map<String, Object> getSection(ConfigurationSection configuration, String path) {
    return configuration.getConfigurationSection(path).getValues(false);
  }

  public <T> Map<String, T> getMap(
      Map<String, Object> deserializedMap, String key, Function<Object, T> mapFunction) {
    ConfigurationSection configuration = (ConfigurationSection) deserializedMap.get(key);
    return castMap(configuration.getValues(false), mapFunction);
  }

  public <T> Map<String, T> castMap(Map<String, Object> map, Function<Object, T> mapFunction) {
    return map.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, mapEntry -> mapFunction.apply(mapEntry.getValue())));
  }

  public <T> List<T> getList(
      Map<String, Object> deserializedMap, String key, Function<Object, T> mapFunction) {
    List<Object> list = (List<Object>) deserializedMap.get(key);
    return castList(list, mapFunction);
  }

  public <T> List<T> castList(List<Object> list, Function<Object, T> mapFunction) {
    return list.stream().map(mapFunction).collect(Collectors.toList());
  }
}
