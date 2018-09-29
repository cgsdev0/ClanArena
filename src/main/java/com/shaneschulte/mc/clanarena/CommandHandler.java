package com.shaneschulte.mc.clanarena;

import com.shaneschulte.mc.clanarena.commands.ChallengeCmd;
import com.shaneschulte.mc.clanarena.commands.CreateCmd;
import com.shaneschulte.mc.clanarena.utils.CmdProperties;
import com.shaneschulte.mc.clanarena.utils.MsgUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {

    private static HashMap<String, CmdProperties> commandClasses = new HashMap<>();

    /**
     * Commands need to be registered here in order to work! Also put in plugin.yml pls
     * any new command can simply implement CmdProperties and generate the needed values
     */
    CommandHandler() {
        registerArgument(new ChallengeCmd());
        registerArgument(new CreateCmd());
    }

    /**
     * Returns a list of all available commands that a certain player can use
     * @param p the player in question
     * @return the list of permissible commands
     */
    public static ArrayList<String> getListOfAllAvailableCommandsForACertainPlayer(Player p) {
        ArrayList<String> list = new ArrayList<>();

        for (final Map.Entry<String, CmdProperties> entry : commandClasses.entrySet()) {
            if (p.hasPermission(entry.getValue().getPermission())) {
                list.add(entry.getKey());
            }
        }

        return list;
    }

    /**
     * Returns CmdProperties from command name
     * @param name the name of the command to look up
     * @return the command properties
     */
    public static CmdProperties getCommandPropertiesFromName(String name) {
        return commandClasses.get(name);
    }

    /**
     * On base command (/clanarena, /ca, /arena)
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {

        // If the sender is a PLAYER
        if (sender instanceof Player) {
            final Player p = (Player) sender;

            // If there are arguments and not the main command alone
            if (args.length > 0) {
                boolean cont = false;
                CmdProperties cmdClass = null;

                // Assign all commands to their name in the hashmap
                for (final Map.Entry<String, CmdProperties> entry : commandClasses.entrySet()) {
                    if (!cont) {
                        if (entry.getKey().equalsIgnoreCase(args[0])) {
                            cont = true;
                            cmdClass = entry.getValue();
                        }
                    }
                }

                // check for permission i think? idk whats entirely going on here, review later
                if (cont) {
                    final int argsNeeded = cmdClass.getLength();
                    if (args.length - 1 >= argsNeeded) {
                        if (p.hasPermission(cmdClass.getPermission())) {
                            if (args[argsNeeded] == null) {
                                args[argsNeeded] = "Nothing";
                            }
                            final StringBuilder sb = new StringBuilder();
                            for (int i = argsNeeded; i < args.length; i++) {
                                sb.append(args[i]).append(" ");
                            }

                            final String allArgs = sb.toString().trim();
                            if (cmdClass.isAlias())
                                cmdClass.getAlias().perform(p, allArgs, args);
                            else
                                cmdClass.perform(p, allArgs, args);
                            return true;
                        } else {
                            // D: no permission!
                            MsgUtils.error (sender, "(no permission)");
                            return true;
                        }
                    } else {
                        // Not the right amount of arguments for the command. Maybe put something like cmdClass.getUsage() here to show the player how to use the command
                        MsgUtils.error (sender, cmdClass.getUsage());
                        return true;
                    }
                } else {
                    // The argument doesn't exist.
                    MsgUtils.error (sender, "/ca " + MsgUtils.Colors.VARIABLE + args[0] + MsgUtils.Colors.ERROR + " is not a command");
                    return true;
                }
            } else {
                // Not enough arguments (show help here)
                MsgUtils.raw(p, "&e-=-=-=- &7Availible &bClanArena &7Commands: &e-=-=-=-");
                for (final Map.Entry<String, CmdProperties> entry : commandClasses.entrySet()) {
                    if (p.hasPermission(entry.getValue().getPermission())) {
                        MsgUtils.sendMessage(p, "/ca " + MsgUtils.Colors.VARIABLE + entry.getKey() + MsgUtils.Colors.INFO + ": " + entry.getValue().getHelpMessage());
                    }
                }
                return true;
            }
        } else {
            // Sender isn't a player
            MsgUtils.error (sender, "Sorry! No console commands are available yet :(");
            return true;
        }
    }

    /**
     * registers commands with their alises in the hashmap
     * @param baseCmd The command with properties, uses command variable to register
     */
    private void registerArgument(final CmdProperties baseCmd) {
        commandClasses.put(baseCmd.getCommand(), baseCmd);
    }
}