package com.twodevsstudio.exodusdevelopmenttrial.api.inventory;

import com.twodevsstudio.exodusdevelopmenttrial.ExodusDevelopmentTrial;
import com.twodevsstudio.exodusdevelopmenttrial.api.interfaces.Pageable;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.item.GuiItem;
import com.twodevsstudio.exodusdevelopmenttrial.api.model.placeholder.Placeholder;
import com.twodevsstudio.exodusdevelopmenttrial.api.util.ItemUtility;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public abstract class PageableAbstractGui extends AbstractGui implements Pageable {

  protected int currentPage = 1;

  public PageableAbstractGui(ExodusDevelopmentTrial plugin, Player viewer) {
    super(plugin, viewer);
  }

  @Override
  public void update() {

    super.update();

    setPreviousPageItem();
    setNextPageItem();
  }

  @Override
  public void onClick(InventoryClickEvent event) {

    Map<String, String> itemTags = ItemUtility.getTagsFromItemStack(event.getCurrentItem());
    if (itemTags.containsKey("previous_page")) {
      openPreviousPage(viewer);
      event.setCancelled(true);
    } else if (itemTags.containsKey("next_page")) {
      openNextPage(viewer);
      event.setCancelled(true);
    }
  }

  public void open(Player player, int page) {

    currentPage = page;
    Inventory receivedInventory = getInventory();
    update();
    player.openInventory(receivedInventory);
  }

  @Override
  public void open(Player player) {

    open(player, 1);
  }

  @Override
  public void openNextPage(Player player) {

    open(player, currentPage + 1);
  }

  @Override
  public void openPreviousPage(Player player) {

    open(player, currentPage - 1);
  }

  private void setNextPageItem() {

    GuiItem nextPageItem = layout.get("next-page");
    if (nextPageItem != null && currentPage < getMaximumPageNumber()) {
      GuiItem itemClone = nextPageItem.clone();

      Placeholder placeholder = new Placeholder("{page}", String.valueOf(currentPage + 1));
      itemClone.setDisplayName(placeholder.replaceIn(itemClone.getDisplayName()));

      inventory.setItem(itemClone.getSlot(), itemClone.toItemStack());
    }
  }

  private void setPreviousPageItem() {

    GuiItem previousPageItem = layout.get("previous-page");
    if (previousPageItem != null && currentPage > 1) {
      GuiItem itemClone = previousPageItem.clone();

      Placeholder placeholder = new Placeholder("{page}", String.valueOf(currentPage - 1));
      itemClone.setDisplayName(placeholder.replaceIn(itemClone.getDisplayName()));

      inventory.setItem(itemClone.getSlot(), itemClone.toItemStack());
    }
  }
}
