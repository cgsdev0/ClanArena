package com.shaneschulte.mc.clanarena.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Collection of useful item related methods.
 */
public class ItemUtils {

    /**
     * Makes a named ItemStack.
     * @param mat Material of the item
     * @param name Display name of the item
     * @return New ItemStack
     */
    public static ItemStack makeNamed(Material mat, String name) {
        ItemStack result = new ItemStack(mat);
        ItemMeta meta = result.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + name);
        meta.addItemFlags(
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);
        result.setItemMeta(meta);
        return result;
    }
}
