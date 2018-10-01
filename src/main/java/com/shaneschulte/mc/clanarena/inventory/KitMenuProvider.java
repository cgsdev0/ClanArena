package com.shaneschulte.mc.clanarena.inventory;

import com.shaneschulte.mc.clanarena.utils.ItemUtils;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class KitMenuProvider implements InventoryProvider {
    @Override
    public void init(Player player, InventoryContents contents) {

        // Create borders
        ItemStack edge = ItemUtils.makeNamed(Material.BLACK_STAINED_GLASS_PANE,ChatColor.DARK_GRAY + "Choose a kit");

        contents.fillBorders(ClickableItem.empty(edge));
        contents.fillRow(2, ClickableItem.empty(edge));

        // List kits
        Pagination pagination = contents.pagination();

        ArrayList<ClickableItem> items = new ArrayList<>();
        KitManager.getLoadouts().forEach((name, kit) -> {
            ItemStack is = ItemUtils.makeNamed(kit.getIcon(), name);
            ItemMeta meta = is.getItemMeta();
            meta.setLore(Arrays.asList(
                    ChatColor.GREEN + "Left click to select",
                    ChatColor.AQUA + "Right click to inspect"
            ));
            is.setItemMeta(meta);
            items.add(ClickableItem.of(is, e -> {
                if(e.isLeftClick()) {
                    MsgUtils.sendMessage(player, "You selected kit " + name);
                    KitManager.kitMenu.close(player);
                }
                else if(e.isRightClick()) {
                    KitManager.getKitView(kit, name, pagination.getPage()).open(player);
                }
            }));
        });

        pagination.setItems(items.toArray(new ClickableItem[items.size()]));
        pagination.setItemsPerPage(7);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));

        // Show controls
        contents.set(3, 3, ClickableItem.of(ItemUtils.makeNamed(Material.ARROW, "Previous"),
                e -> KitManager.kitMenu.open(player, pagination.previous().getPage())));
        contents.set(3, 4, ClickableItem.of(ItemUtils.makeNamed(Material.BARRIER, ChatColor.RED + "Exit"),
                e -> KitManager.kitMenu.close(player)));
        contents.set(3, 5, ClickableItem.of(ItemUtils.makeNamed(Material.ARROW, "Next"),
                e -> KitManager.kitMenu.open(player, pagination.next().getPage())));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
