package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.events.OnJoin;
import com.shaneschulte.mc.clanarena.utils.ConstructTabCompleter;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        this.getCommand("ClanArena").setExecutor(new CommandHandler());
        this.getCommand("ClanArena").setTabCompleter(new ConstructTabCompleter());
        MsgUtils.log("~Commands registered!~");

        getServer().getPluginManager().registerEvents(new ClanArenaListener(), this);
    }

    @Override
    public void onDisable() {

    }
}
