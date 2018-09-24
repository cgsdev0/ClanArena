package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.inventory.KitManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    private static ClanArena instance;

    @Override
    public void onEnable() {
        instance = this;
        MsgUtils.logMessage("~Commands registered!~");
        KitManager.loadLoadouts();
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static ClanArena getPlugin() {
        return instance;
    }

}
