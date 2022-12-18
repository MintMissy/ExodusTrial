package com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder;

import lombok.Data;
import net.kyori.adventure.text.Component;

import java.util.List;

@Data
public class Placeholder {
  private final String key;
  private final String replacement;

  public String replaceIn(String text) {

    return text.replace(key, replacement);
  }

  public List<String> replaceIn(List<String> textList) {

    return textList.stream().map(this::replaceIn).toList();
  }

  public Component replaceIn(Component text) {

    return text.replaceText(builder -> builder.match(key).replacement(replacement));
  }
}
