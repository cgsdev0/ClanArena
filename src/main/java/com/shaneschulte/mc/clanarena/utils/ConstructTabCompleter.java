package com.shaneschulte.mc.clanarena.utils;

import com.shaneschulte.mc.clanarena.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ConstructTabCompleter implements TabCompleter {

    /**
     * Adds a tab completion system to the command
     * @return list of options for level 0 tab completion
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] arguments) {

        // if only auto completing base sub commands
        if (arguments.length == 1 && sender instanceof Player) {
            Player p = (Player) sender;

            return CommandHandler.getListOfAllAvailableCommandsForACertainPlayer(p);
        }

        // If has autocomplete past level 0
        if (CommandHandler.getCommandPropertiesFromName(arguments[0]) instanceof AutoCompletable) {

            // get command properties
            CmdProperties cmdProperties = CommandHandler.getCommandPropertiesFromName(arguments[0]);

            // get list of options from the auto completable command
            List<ArrayList<String>> allOptions = ((AutoCompletable) cmdProperties).getArrayListOfAutocompleteStringListsOrganizedByIndex();

            // if no more options return
            if (arguments.length -2 >= allOptions.size()) return null;

            else return allOptions.get(arguments.length -2);
        }

        return null;
    }
}
