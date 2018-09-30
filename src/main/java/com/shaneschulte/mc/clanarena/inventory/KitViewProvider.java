package com.shaneschulte.mc.clanarena.inventory;

import com.shaneschulte.mc.clanarena.utils.ItemUtils;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class KitViewProvider implements InventoryProvider {

    private final int page;
    private final String name;
    private final Kit kit;

    private static final int INVENTORY_COLS = 9;
    private static final int INVENTORY_ROWS = 4;
    private static final int INVENTORY_SLOTS = INVENTORY_COLS * INVENTORY_ROWS;
    private static final int ARMOR_SLOTS = 4;

    KitViewProvider(Kit kit, String name, int page) {
        this.page = page;
        this.name = name;
        this.kit = kit;
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        // Create borders
        ItemStack edge = ItemUtils.makeNamed(Material.BLACK_STAINED_GLASS_PANE, ChatColor.GRAY + "Back");

        contents.fillRow(0, ClickableItem.of(edge, e->KitManager.kitMenu.open(player, page)));
        contents.fillRow(1, ClickableItem.of(edge, e->KitManager.kitMenu.open(player, page)));

        // Create lambda for going back a page
        Consumer<InventoryClickEvent> back = e->KitManager.kitMenu.open(player, page);

        // Load kit armor slots
        for(int i = 0; i < ARMOR_SLOTS; i++)
            contents.set(0, i, ClickableItem.of(kit.getArmorContents()[i], back));

        // Load kit offhand slot
        if (kit.getItemInOffHand() != null && kit.getItemInOffHand().getType() != Material.AIR) {
            contents.set(0, 4, ClickableItem.of(kit.getItemInOffHand(), back));
        }

        // Load kit inventory slots
        for(int i = 0; i < INVENTORY_SLOTS; i++) {
            ItemStack is = kit.getStorageContents()[(i + INVENTORY_COLS) % INVENTORY_SLOTS];
            if(is == null) is = ItemUtils.makeNamed(Material.WHITE_STAINED_GLASS_PANE, ChatColor.GRAY + "Back");

            // Re-order the inventory
            int index = i + (INVENTORY_COLS * 2);
            contents.set(index / INVENTORY_COLS,
                    index % INVENTORY_COLS,
                    ClickableItem.of(is, back));
        }

        // UI elements
        contents.set(0, 7,
                ClickableItem.of(ItemUtils.makeNamed(Material.LIME_STAINED_GLASS_PANE,
                        ChatColor.GREEN + "Select"),
                e -> {
                    MsgUtils.sendMessage(player, "You selected kit " + name);
                    KitManager.kitMenu.close(player);
        }));
        contents.set(0, 8,
                ClickableItem.of(ItemUtils.makeNamed(Material.RED_STAINED_GLASS_PANE,
                        ChatColor.RED + "Quit"),
                e -> KitManager.kitMenu.close(player)));
    }

    @Override
    public void update(Player player, InventoryContents contents) { }
}
