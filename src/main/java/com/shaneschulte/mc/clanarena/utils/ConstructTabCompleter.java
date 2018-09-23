package com.shaneschulte.mc.clanarena.utils;

import com.shaneschulte.mc.clanarena.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class ConstructTabCompleter implements TabCompleter {

    /**
     * Adds a tab completion system to the command
     * @return list of options for level 0 tab completion
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] arguments) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            return CommandHandler.getListOfAllAvailableCommandsForACertainPlayer(p);
        }

        return null;
    }
}
