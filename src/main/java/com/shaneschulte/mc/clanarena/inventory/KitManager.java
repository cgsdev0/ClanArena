package com.shaneschulte.mc.clanarena.inventory;

import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import com.shaneschulte.mc.clanarena.utils.FileUtils;
import fr.minuskube.inv.SmartInventory;
import javafx.util.Pair;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
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
    public static final SmartInventory kitMenu = SmartInventory.builder()
            .id("customInventory")
            .provider(new KitMenuProvider())
            .size(4, 9)
            .title(ChatColor.RED + "Choose a kit")
            .build();

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
    public static void createKit(Player player, String name, Material icon) {
        if(loadoutKits.containsKey(name.toLowerCase())) {
            throw new IllegalArgumentException("Kit already exists: " + name);
        }

        loadoutKits.put(name.toLowerCase(), new Kit(player, icon));
        saveLoadouts();
    }

    public static Map<String, Kit> getLoadouts() {
       return loadoutKits;
    }

    public static SmartInventory getKitView(Kit kit, String name, int returnPage) {
        return SmartInventory.builder()
                .provider(new KitViewProvider(kit, name, returnPage))
                .size(6, 9)
                .title("Kit: " + name)
                .build();
    }

    public static void loadLoadouts() {
        if(!registered) {
            ConfigurationSerialization.registerClass(Kit.class);
            registered = true;
        }
        FileUtils.saveDefaultFile(loadoutsFile);
        ConfigurationSection config = FileUtils.loadCustomFile(loadoutsFile).getConfigurationSection("kits");
        if(config != null) {
            Map<String, Object> map = config.getValues(true);
            map.forEach((name, obj) -> loadoutKits.put(name, (Kit) obj));
            loadoutKits.forEach((name, kit) -> MsgUtils.log("Loaded kit: " + name));
        }
    }

    public static void saveLoadouts() {
        FileConfiguration config = new YamlConfiguration();
        config.set("kits", loadoutKits);

        FileUtils.saveCustomFile(loadoutsFile, config);
    }
}
