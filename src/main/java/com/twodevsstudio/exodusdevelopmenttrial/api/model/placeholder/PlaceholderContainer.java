package com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder;

import net.kyori.adventure.text.Component;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderContainer {
  private final List<Placeholder> placeholders;

  public PlaceholderContainer(List<Placeholder> placeholders) {
    this.placeholders = new ArrayList<>(placeholders);
  }

  public PlaceholderContainer addPlaceholder(Placeholder placeholder) {

    placeholders.add(placeholder);
    return this;
  }

  public String replaceIn(String text) {

    for (Placeholder placeholder : placeholders) {
      text = placeholder.replaceIn(text);
    }

    return text;
  }

  public List<String> replaceIn(List<String> text) {

    for (Placeholder placeholder : placeholders) {
      text = placeholder.replaceIn(text);
    }

    return text;
  }

  public Component replaceIn(Component text) {

    for (Placeholder placeholder : placeholders) {
      text = placeholder.replaceIn(text);
    }

    return text;
  }
}
