package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import lombok.experimental.UtilityClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@UtilityClass
public class TextUtility {
  public Component colorize(String text) {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
  }

  public List<Component> colorize(@Nullable List<String> text) {

    if (text == null) {
      return new ArrayList<>();
    }

    return text.stream().map(TextUtility::colorize).collect(Collectors.toList());
  }

  public String getComponentContent(Component component) {
    return PlainTextComponentSerializer.plainText().serialize(component);
  }

  public UUID parseTrimmedUuid(String trimmedUuid) {

    String uuid =
        trimmedUuid.substring(0, 8)
            + '-'
            + trimmedUuid.substring(8, 12)
            + '-'
            + trimmedUuid.substring(12, 16)
            + '-'
            + trimmedUuid.substring(16, 20)
            + '-'
            + trimmedUuid.substring(20, 32);

    return UUID.fromString(uuid);
  }
}
