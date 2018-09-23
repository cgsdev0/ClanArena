package com.shaneschulte.mc.clanarena;

import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("ClanArena").setExecutor(new CommandManager());
        MsgUtils.logMessage("~Commands registered!~");
    }

    @Override
    public void onDisable() {

    }
}
