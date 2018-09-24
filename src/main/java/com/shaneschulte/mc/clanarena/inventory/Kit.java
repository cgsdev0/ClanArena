package com.shaneschulte.mc.clanarena.inventory;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a player loadout.
 */
public class Kit implements ConfigurationSerializable {

    private final ItemStack[] inventory;
    private final ItemStack[] armorContents;
    private final ItemStack offHand;
    private final int xp;

    public Kit(Player player) {
        PlayerInventory inv = player.getInventory();
        this.inventory = inv.getStorageContents().clone();
        this.armorContents = inv.getArmorContents().clone();
        this.offHand = inv.getItemInOffHand().clone();
        this.xp = player.getTotalExperience();
    }

    public Kit(Map<String, Object> map) {
        this.inventory = new ItemStack[36];
        ((List<ItemStack>) map.get("inv")).toArray(this.inventory);
        this.armorContents = new ItemStack[4];
        ((List<ItemStack>) map.get("armor")).toArray(this.armorContents);
        this.offHand = (ItemStack) map.get("hand");
        this.xp = 0;
    }

    public ItemStack[] getStorageContents() {
        return inventory.clone();
    }

    public ItemStack[] getArmorContents() {
        return armorContents.clone();
    }

    public ItemStack getItemInOffHand() {
        return offHand.clone();
    }

    public int getTotalExperience() {
        return xp;
    }

    /**
     * Swaps the player's current kit with a new one.
     * @param player Player to operate on
     * @param kit Kit to be given to the player
     * @return Player's current kit before the swap
     */
    static public Kit swap(Player player, Kit kit) {

        // Create a new kit from the player's current state
        Kit result = new Kit(player);

        // Give player the specified kit
        player.setTotalExperience(kit.getTotalExperience());
        PlayerInventory inv = player.getInventory();
        inv.setArmorContents(kit.getArmorContents());
        inv.setStorageContents(kit.getStorageContents());
        inv.setItemInOffHand(kit.getItemInOffHand());

        return result;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> result = new HashMap<>();
        result.put("armor", armorContents);
        result.put("inv", inventory);
        result.put("hand", offHand);
        return result;
    }

    public static Kit deserialize(Map<String, Object> map) {
        return new Kit(map);
    }

    public static Kit valueOf(Map<String, Object> map) {
        return new Kit(map);
    }
}
