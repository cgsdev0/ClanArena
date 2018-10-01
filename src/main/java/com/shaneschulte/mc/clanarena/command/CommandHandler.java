package com.shaneschulte.mc.clanarena.command;

import com.shaneschulte.mc.clanarena.commands.*;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private static HashMap<String, BaseCommand> commandClasses = new HashMap<>();

    /**
     * Commands need to be registered here in order to work! Also put in plugin.yml pls
     * any new command can simply implement CmdProperties and generate the needed values
     */
    public CommandHandler() {
        registerCommand(new ClanArenaCommand());
    }

    /**
     * On base command (/clanarena, /ca, /arena)
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        // If the sender is a PLAYER
        if (sender instanceof Player) {
            final Player p = (Player) sender;

            // check for permission i think? idk whats entirely going on here, review later
            if (commandClasses.containsKey(cmd.getName())) {
                List<String> newArgs = new LinkedList<>();
                if(args != null) {
                    newArgs.addAll(Arrays.asList(args));
                }
                commandClasses.get(cmd.getName()).perform(p, newArgs);
            }
            else {
                return false;
            }
        }
        else {
            MsgUtils.error(sender, "Command not supported from console");
        }
        return true;
    }

    /**
     * registers commands with their alises in the hashmap
     * @param baseCmd The command with properties, uses command variable to register
     */
    private void registerCommand(final BaseCommand baseCmd) {
        commandClasses.put(baseCmd.getCommand(), baseCmd);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> arguments = new ArrayList<>();
        if(args != null)
            Collections.addAll(arguments, args);
        if(commandClasses.containsKey(command.getName())) {
            BaseCommand base = commandClasses.get(command.getName());

            // Traverse the argument string
            while(arguments.size() > 1) {
                if(base instanceof LeafCommand) break; // goto leaf;
                String next = arguments.remove(0);
                if(base instanceof BranchCommand) {
                    BranchCommand branchBase = (BranchCommand)base;
                    if(branchBase.getSubCommands().containsKey(next)) {
                        base = branchBase.getSubCommands().get(next);
                    }
                    else return new ArrayList<>(); // Uncharted territory
                }
            }

            // Auto generate suggestions of sub commands
            if(base instanceof BranchCommand) {
                BranchCommand branchBase = (BranchCommand)base;
                List<String> result = new ArrayList<>();
                StringUtil.copyPartialMatches(arguments.get(0), branchBase.getSubCommands().keySet(), result);
                Collections.sort(result);
                return result;
            }
/* leaf: */ else if(base instanceof LeafCommand) {
                LeafCommand leafBase = (LeafCommand)base;
                return leafBase.onTabComplete(arguments); // leaves require custom logic
            }
        }
        return null;
    }
}