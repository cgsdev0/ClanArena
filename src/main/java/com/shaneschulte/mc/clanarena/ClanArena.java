package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.utils.ConstructTabCompleter;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("ClanArena").setExecutor(new CommandHandler());
        this.getCommand("ClanArena").setTabCompleter(new ConstructTabCompleter());
        MsgUtils.log("~Commands registered!~");
    }

    @Override
    public void onDisable() {

    }
}
