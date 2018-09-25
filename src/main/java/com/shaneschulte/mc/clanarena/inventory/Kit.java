package com.shaneschulte.mc.clanarena.inventory;

import org.bukkit.Material;
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
    private Material icon;
    private final int xp;

    public Kit(Player player) {
        PlayerInventory inv = player.getInventory();
        this.inventory = inv.getStorageContents().clone();
        this.armorContents = inv.getArmorContents().clone();
        this.offHand = inv.getItemInOffHand().clone();
        this.xp = player.getTotalExperience();
        this.icon = null;
    }

    public Kit(Player player, Material icon) {
        this(player);
        this.icon = icon;
    }

    @SuppressWarnings("unchecked")
    public Kit(Map<String, Object> map) {
        this.inventory = new ItemStack[36];
        this.armorContents = new ItemStack[4];
        try {
            ((List<ItemStack>) map.get("inv")).toArray(this.inventory);
            ((List<ItemStack>) map.get("armor")).toArray(this.armorContents);
        }
        catch(ClassCastException e) {
            e.printStackTrace();
        }
        this.offHand = (ItemStack) map.get("hand");
        this.icon = Material.valueOf((String)map.get("icon"));
        this.xp = 0;
    }

    public ItemStack[] getStorageContents() {
        return inventory;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public ItemStack getItemInOffHand() {
        return offHand;
    }

    public int getTotalExperience() {
        return xp;
    }

    public Material getIcon() {
        return icon;
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
        result.put("icon", icon.toString());
        return result;
    }

    public static Kit deserialize(Map<String, Object> map) {
        return new Kit(map);
    }

    public static Kit valueOf(Map<String, Object> map) {
        return new Kit(map);
    }
}
