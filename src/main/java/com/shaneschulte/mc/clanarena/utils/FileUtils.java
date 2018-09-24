package com.shaneschulte.mc.clanarena.utils;

import com.shaneschulte.mc.clanarena.ClanArena;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static void saveDefaultFile(String name) {
        File customConfigFile = new File(ClanArena.getPlugin().getDataFolder(), name);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            ClanArena.getPlugin().saveResource(name, false);
        }
    }

    public static FileConfiguration loadCustomFile(String name) {
        File customConfigFile = new File(ClanArena.getPlugin().getDataFolder(), name);
        FileConfiguration customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        return customConfig;
    }

    public static void saveCustomFile(String name, FileConfiguration file) {
        File customConfigFile = new File(ClanArena.getPlugin().getDataFolder(), name);
        try {
            file.save(customConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
