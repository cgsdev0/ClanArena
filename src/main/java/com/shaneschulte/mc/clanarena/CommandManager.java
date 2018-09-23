package com.shaneschulte.mc.clanarena;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor {

    // base ClanArena command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ClanArena.messageManager.sendMessage(sender, "Welcome to ClanArena!");
        return true;
    }
}
