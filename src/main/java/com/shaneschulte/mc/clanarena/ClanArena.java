package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.events.OnJoin;
import com.shaneschulte.mc.clanarena.inventory.KitManager;
import com.shaneschulte.mc.clanarena.listeners.ClanArenaListener;
import com.shaneschulte.mc.clanarena.commands.CommodoreRegistrar;
import com.shaneschulte.mc.clanarena.utils.ConstructTabCompleter;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import me.lucko.commodore.Commodore;
import me.lucko.commodore.CommodoreProvider;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    private static ClanArena instance;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        instance = this;
        MsgUtils.log("~Commands registered!~");
        KitManager.loadLoadouts();

        this.getCommand("ClanArena").setExecutor(new CommandHandler());
        this.getCommand("ClanArena").setTabCompleter(new ConstructTabCompleter());
        MsgUtils.log("~Commands registered!~");

        getServer().getPluginManager().registerEvents(new ClanArenaListener(this), this);

        // TODO: Use commodore
        /* register your command executor as normal.
        PluginCommand command = getCommand("mycommand");

        // check if brigadier is supported
        if (CommodoreProvider.isSupported()) {

            // get a commodore instance
            Commodore commodore = CommodoreProvider.getCommodore(this);

            // register your completions.
            CommodoreRegistrar.registerCompletions(commodore, command);
        }
        */
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static ClanArena getPlugin() {
        return instance;
    }

}
