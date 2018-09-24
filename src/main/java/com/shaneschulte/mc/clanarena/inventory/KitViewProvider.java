package com.shaneschulte.mc.clanarena.inventory;

import com.shaneschulte.mc.clanarena.utils.ItemUtils;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitViewProvider implements InventoryProvider {

    private final int page;
    private final String name;
    private final Kit kit;

    public KitViewProvider(Kit kit, String name, int page) {
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

        // Load kit
        ClickableItem[] armor = new ClickableItem[4];
        for(int i = armor.length - 1; i >= 0; i--)
            contents.set(0, i, ClickableItem.empty(kit.getArmorContents()[i]));

        if (kit.getItemInOffHand() != null && kit.getItemInOffHand().getType() != Material.AIR) {
            contents.set(0, 4, ClickableItem.of(kit.getItemInOffHand(), e->KitManager.kitMenu.open(player, page)));
        }

        ClickableItem[] inv = new ClickableItem[36];
        for(int i = 0; i < inv.length; i++) {
            ItemStack is = kit.getStorageContents()[(i+9) % 36];
            if(is == null) is = ItemUtils.makeNamed(Material.WHITE_STAINED_GLASS_PANE, ChatColor.GRAY + "Back");
            contents.set((18 + i) / 9, (18 + i) % 9, ClickableItem.of(is, e->KitManager.kitMenu.open(player, page)));
        }

        // UI elements
        contents.set(0, 7, ClickableItem.of(ItemUtils.makeNamed(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Select"),
                e -> {
                    MsgUtils.sendMessage(player, "You selected kit " + name);
                    KitManager.kitMenu.close(player);
        }));
        contents.set(0, 8, ClickableItem.of(ItemUtils.makeNamed(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Quit"),
                e -> KitManager.kitMenu.close(player)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
