package com.shaneschulte.mc.clanarena;

import com.comphenix.protocol.ProtocolLibrary;
import com.shaneschulte.mc.clanarena.command.CommandHandler;
import com.shaneschulte.mc.clanarena.listeners.PlayerJoinListener;
import com.shaneschulte.mc.clanarena.inventory.KitManager;
import com.shaneschulte.mc.clanarena.protocols.RespawnHandler;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class ClanArena extends JavaPlugin {

    private static ClanArena instance;

    @Override
    public void onEnable() {
        instance = this;

        // Register Event Listeners
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // Register Packet Listeners
        ProtocolLibrary.getProtocolManager().addPacketListener(new RespawnHandler(this));

        // Kits
        KitManager.loadLoadouts();

        // Register Commands
        CommandHandler handler = new CommandHandler();
        this.getCommand("clanarena").setExecutor(handler);
        this.getCommand("clanarena").setTabCompleter(handler);

        // Console Output
        MsgUtils.log("~Commands registered!~");

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

    /**
     * get instance of ClanArena plugin
     * @return ClanArena instance
     */
    public static ClanArena getPlugin() {
        return instance;
    }

}
