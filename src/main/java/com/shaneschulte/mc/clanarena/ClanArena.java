package com.shaneschulte.mc.clanarena;

import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    public static MessageManager messageManager = new MessageManager();

    @Override
    public void onEnable() {
        this.getCommand("ClanArena").setExecutor(new CommandManager());
        messageManager.logMessage("~Commands registered!~");
    }

    @Override
    public void onDisable() {

    }
}
