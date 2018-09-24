package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.inventory.KitManager;
import com.shaneschulte.mc.clanarena.listeners.ClanArenaListener;
import com.shaneschulte.mc.clanarena.utils.ConstructTabCompleter;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    private static ClanArena instance;

    @Override
    public void onEnable() {
        instance = this;
        MsgUtils.log("~Commands registered!~");
        KitManager.loadLoadouts();

        this.getCommand("ClanArena").setExecutor(new CommandHandler());
        this.getCommand("ClanArena").setTabCompleter(new ConstructTabCompleter());
        MsgUtils.log("~Commands registered!~");

        getServer().getPluginManager().registerEvents(new ClanArenaListener(this), this);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static ClanArena getPlugin() {
        return instance;
    }

}
