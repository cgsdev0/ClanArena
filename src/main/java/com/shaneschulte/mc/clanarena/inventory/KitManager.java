package com.shaneschulte.mc.clanarena.inventory;

import com.shaneschulte.mc.clanarena.MsgUtils;
import com.shaneschulte.mc.clanarena.utils.FileUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Provides an abstraction to checking out gear for battle.
 * The player's current inventory will be safely stored until the gear is checked back in.
 */
public class KitManager {
    private static HashMap<UUID, Kit> playerKits = new HashMap<>();
    private static HashMap<String, Kit> loadoutKits = new HashMap<>();

    private static final String loadoutsFile = "kits.yml";

    private static boolean registered = false;

    /**
     * Give a kit to a player.
     * Checks in player's current inventory to storage.
     * @param player affected player
     * @param kit unique name of desired kit
     */
    public static void checkOut(Player player, String kit) {

        if(playerKits.containsKey(player.getUniqueId())) {
            throw new IllegalStateException("Player " + player.getName() + " already has a kit checked out!");
        }

        if(!loadoutKits.containsKey(kit.toLowerCase())) {
            throw new IllegalArgumentException("No such kit: " + kit);
        }

        playerKits.put(player.getUniqueId(), Kit.swap(player, loadoutKits.get(kit)));
    }

    /**
     * Returns the player's checked in kit.
     * Destroys the player's previously checked out kit.
     * @param player affected player
     */
    public static void checkIn(Player player) {

        if(!playerKits.containsKey(player.getUniqueId())) {
            throw new IllegalStateException("Player " + player.getName() + " has nothing checked in!");
        }

        Kit.swap(player, playerKits.get(player.getUniqueId()));
        playerKits.remove(player.getUniqueId());
    }

    /**
     * Creates a kit from the given player's current inventory.
     * @param player player with desired kit
     * @param name unique name of the new kit
     */
    public static void createKit(Player player, String name) {
        if(loadoutKits.containsKey(name.toLowerCase())) {
            throw new IllegalArgumentException("Kit already exists: " + name);
        }

        loadoutKits.put(name.toLowerCase(), new Kit(player));
        saveLoadouts();
    }

    public static void loadLoadouts() {
        if(!registered) {
            ConfigurationSerialization.registerClass(Kit.class);
            registered = true;
        }
        FileUtils.saveDefaultFile(loadoutsFile);
        FileConfiguration config = FileUtils.loadCustomFile(loadoutsFile);
        config.getConfigurationSection("kits")
                .getValues(true).forEach((name, obj) -> loadoutKits.put(name, (Kit)obj));
        loadoutKits.forEach((name, kit) -> MsgUtils.logMessage("Loaded kit: " + name));
    }

    public static void saveLoadouts() {
        FileConfiguration config = new YamlConfiguration();
        config.set("kits", loadoutKits);

        FileUtils.saveCustomFile(loadoutsFile, config);
    }
}
