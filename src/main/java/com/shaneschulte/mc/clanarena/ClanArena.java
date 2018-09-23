package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("ClanArena").setExecutor(new CommandHandler());
        MsgUtils.log("~Commands registered!~");
    }

    @Override
    public void onDisable() {

    }
}
